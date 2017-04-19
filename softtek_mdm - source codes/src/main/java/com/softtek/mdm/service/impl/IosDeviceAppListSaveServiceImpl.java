package com.softtek.mdm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.softtek.mdm.abs.AbsServerUrl;
import com.softtek.mdm.abs.AbstractIosPush;
import com.softtek.mdm.dao.CommandDao;
import com.softtek.mdm.dao.DeviceAppInfoDao;
import com.softtek.mdm.dao.DeviceBasicInfoDao;
import com.softtek.mdm.model.Command;
import com.softtek.mdm.model.DeviceAppInfoModel;
import com.softtek.mdm.model.DeviceBasicInfoModel;
import com.softtek.mdm.model.TokenUpdateInfo;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.ios.plist.PlistUtil;
import com.softtek.mdm.web.http.BaseDTO;

import jodd.util.StringUtil;

@Service("IosDeviceAppListSaveServiceImpl")
public class IosDeviceAppListSaveServiceImpl extends AbsServerUrl{
    
	@Autowired
	private CommandDao commandDao;
	
	@Autowired
	private DeviceBasicInfoDao deviceBasicInfoDao;
	
	@Autowired
	private DeviceAppInfoDao deviceAppInfoDao;
	
	@Autowired
	@Qualifier("IosDeviceAppListServiceImpl")
	private AbstractIosPush abstractIosPush;
	
	
	@Override
	public String operateIdleStatus(Map<String, Object> map) {
		String status = (String) map.get(Constant.MobileConfig.STATUS);
		String udid = (String) map.get(Constant.MobileConfig.UDID);
		if(Constant.getStatus.IDLE.equals(status)){
			if(StringUtil.isNotBlank(udid)){
				Command command = getFirstCommand(udid,Constant.Result.NONE,Constant.ICommandType.AppList);
				if(command!=null&&PlistUtil.RequestType.InstalledApplicationList.equals(command.getCommand())){
					String plistStr = PlistUtil.getInstalledApplicationListPlist(command.getId());
					command.setDeviceId(udid);
					command.setDoit(Constant.Result.PENDING);
	        		command.setUpdateDate(new Date());
	        		command.setResult(Constant.getStatus.PENDING);
	        		int result = commandDao.updateByPrimaryKeySelective(command);
	        		if(result == 0){
	        			return BaseDTO.ERROR;
	        		}
	        		return plistStr;
				}
			}
		}
		return BaseDTO.SUCCESS;
	}

	@Override
	public String operateAcknowledgedStatus(Map<String, Object> map) {
		String commandUUID = (String) map.get(Constant.MobileConfig.COMMAND_UUID);
		String udid = (String) map.get(Constant.MobileConfig.UDID);
		
		@SuppressWarnings("unchecked")
		List<DeviceAppInfoModel> list = (List<DeviceAppInfoModel>) map.get("appInfoList");
		
		DeviceBasicInfoModel device = deviceBasicInfoDao.queryDeviceInfoByUdid(udid);
		Command command = commandDao.selectByPrimaryKey(commandUUID);
		command.setId(commandUUID);
		command.setDoit(Constant.Result.ACKNOWLEDGED);
		command.setResult(Constant.getStatus.ACKNOWLEDGED);
		command.setUpdateDate(new Date());
	    commandDao.updateByPrimaryKeySelective(command);
	    
		for(DeviceAppInfoModel deviceApp : list){
			deviceApp.setBelongDevice(device);
		}
		// 已存在，删除应用信息
		deviceAppInfoDao.truncateWithDeviceId(device.getId());
		//批量保存app信息
		deviceAppInfoDao.saveRecordsBatch(list);
		
		iosNotifyed(udid);
		return BaseDTO.SUCCESS;
	}

	/**
	 * 获取第一个命令
	 * @param udid udid
	 * @param doit 状态
	 * @param type 类型
	 * @return
	 */
	private Command getFirstCommand(String udid,String doit,String type){
		List<Command> cmdList = commandDao.selectCommands(udid, doit,type);
		if(CollectionUtils.isEmpty(cmdList)){
			return null;
		}
		return cmdList.get(0);
	}
	
	/**
	 * 通知APNS 是否空闲以便执行命令
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
