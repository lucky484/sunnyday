package com.softtek.mdm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.softtek.mdm.abs.AbsServerUrl;
import com.softtek.mdm.abs.AbstractIosPush;
import com.softtek.mdm.dao.CommandDao;
import com.softtek.mdm.dao.DeletedDeviceDao;
import com.softtek.mdm.dao.DeviceManagerDao;
import com.softtek.mdm.dao.TokenUpdateInfoDao;
import com.softtek.mdm.model.Command;
import com.softtek.mdm.model.DeviceManagerModel;
import com.softtek.mdm.model.TokenUpdateInfo;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.IosPushUtil;
import com.softtek.mdm.util.ios.plist.PlistUtil;
import com.softtek.mdm.web.http.BaseDTO;

import jodd.util.StringUtil;

@Service("IosDeviceSettingServiceImpl")
public class IosDeviceSettingServiceImpl extends AbsServerUrl{
	
	private static Logger logger = Logger.getLogger(IosDeviceSettingServiceImpl.class);
     
	@Autowired
	private CommandDao commandDao;
	
	@Autowired
	private TokenUpdateInfoDao tokenUpdateInfoDao;
	
	@Autowired
    private DeletedDeviceDao deletedDeviceDao;

    @Autowired
	private DeviceManagerDao deviceManagerDao;
	
	@Autowired
	@Qualifier("IosDeviceSettingNotifyServiceImpl")
	private AbstractIosPush abstractIosPush;
	
	@Override
	public String operateIdleStatus(Map<String, Object> map){
		try {
		String status = (String) map.get(Constant.MobileConfig.STATUS);
		String udid = (String) map.get(Constant.MobileConfig.UDID);
		if(Constant.getStatus.IDLE.equals(status)){
		     if(StringUtil.isNotEmpty(udid)){
		    	List<Command> commandList = commandDao.selectCommandByUdid(udid);
				List<TokenUpdateInfo> tokenUpdateInfo =  tokenUpdateInfoDao.selectTokenInfoByUdid(udid);
				if(CollectionUtils.isNotEmpty(commandList)){
					for(Command command : commandList){
						String plistStr = "";
						    if("DeviceInformation".equals(command.getCommand())){
						    	plistStr = PlistUtil.getDeviceInformationPlist(command.getId());
						    }else if("RemoveProfile".equals(command.getCommand())){
						    	plistStr = PlistUtil.getRemoveProfilePlist(tokenUpdateInfo.get(0).getPayloadIdentifier(), command.getId());
						    	//MDMProtocolUtils.removeProfile(command.getId(),"mdm.softtek.com" + tokenUpdateInfo.get(0).getPayloadIdentifier());
						    }else if(command.getCommand().equals("ClearPasscode")){
						    	plistStr = PlistUtil.getClearPasscodePlist(tokenUpdateInfo.get(0).getUnlockToken(), command.getId());
						    	//IosDeviceInfoPlistCreateServiceUtil.clearPasscode(command.getId(),tokenUpdateInfo.get(0).getUnlockToken());
						    }else{
						    	plistStr = PlistUtil.getDeviceLockPlist(command.getId());
						    	//IosDeviceInfoPlistCreateServiceUtil.lockDevicePlist(command.getId(), command.getCommand());
						    }		
							command.setDeviceId(udid);
							command.setDoit(Constant.Result.PENDING);
							command.setResult(Constant.getStatus.PENDING);
							command.setUpdateDate(new Date());
							int result = commandDao.updateByPrimaryKeySelective(command);
							if(result == 0){
								return BaseDTO.ERROR;
							}
							return plistStr;
					}
				}
				
		     }
		}
		 return BaseDTO.SUCCESS;
		} catch (Exception e){
			logger.error("ios device manage operate idle status is error,error message is " + e.getMessage());
			return BaseDTO.ERROR;
		}
	}

	@Override
	public String operateAcknowledgedStatus(Map<String, Object> map) {
		try{
			String commandUUID = (String) map.get(Constant.MobileConfig.COMMAND_UUID);
			Command command = commandDao.selectByPrimaryKey(commandUUID);
			if("RemoveProfile".equals(command.getCommand())){
				DeviceManagerModel deviceManager = deviceManagerDao.queryDeviceInfoByUdid(command.getDeviceId());
                deletedDeviceDao.insertDevice(deviceManager);
				deviceManagerDao.deleteDeviceByUdid(deviceManager.getUdid());
				
				List<String> deviceToken = new ArrayList<String>();
				deviceToken.add(deviceManager.getDeviceToken().replaceAll(" ", ""));
				
				Map<String,String> paraMap = new HashMap<String, String>();
				paraMap.put("loginOut","1");
				
				IosPushUtil.pushDataToIos(deviceToken, null, 1, "default", paraMap);
			}
			
			command.setId(commandUUID);
			command.setDoit(Constant.Result.ACKNOWLEDGED);
			command.setResult(Constant.getStatus.ACKNOWLEDGED);
			command.setUpdateDate(new Date());
			int result = commandDao.updateByPrimaryKeySelective(command);
			if(result==0){
				return BaseDTO.ERROR;
			}
			return BaseDTO.SUCCESS;
		} catch(Exception e){
			logger.error("ios device manage operate Acknowledged status is error,error message is " + e.getMessage());
			return BaseDTO.ERROR;
		}
	}

	/**
     * 通知APNS 是否空闲以便执行命令
     * 
     * @param udid
     * @param doit
     * @param type
     */
	private void iosNotifyed(String udid){
		List<String> udids=new ArrayList<>();
		udids.add(udid);
		List<TokenUpdateInfo> tokens=abstractIosPush.getTokenUpdateInfos(udids);
		abstractIosPush.iosNotify(tokens);
	}
}
