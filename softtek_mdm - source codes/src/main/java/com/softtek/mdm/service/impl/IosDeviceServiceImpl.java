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
import org.springframework.transaction.annotation.Transactional;
import com.softtek.mdm.abs.AbsServerUrl;
import com.softtek.mdm.abs.AbstractIosPush;
import com.softtek.mdm.dao.CommandDao;
import com.softtek.mdm.dao.DeviceBasicInfoDao;
import com.softtek.mdm.dao.IosDevicePolicyDao;
import com.softtek.mdm.dao.IosWifiConfigureDao;
import com.softtek.mdm.dao.TokenUpdateInfoDao;
import com.softtek.mdm.model.Command;
import com.softtek.mdm.model.IosDevicePolicy;
import com.softtek.mdm.model.IosWifiConfigure;
import com.softtek.mdm.model.TokenUpdateInfo;
import com.softtek.mdm.service.DeviceService;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.IosPushUtil;
import com.softtek.mdm.web.http.BaseDTO;
import jodd.util.StringUtil;

/**
 * ios设备策略处理空闲和Acknowleged状态
 * @author jane.hui
 */
@Service("IosDeviceServiceImpl")
@Transactional
public class IosDeviceServiceImpl extends AbsServerUrl{

	/**
	 * 日志记录对象
	 */
	private static Logger logger = Logger.getLogger(IosDeviceServiceImpl.class);
	
	@Autowired
	private CommandDao commandDao;
	
	@Autowired
	private TokenUpdateInfoDao tokenUpdateInfoDao;
	
	@Autowired
	private IosWifiConfigureDao iosWifiConfigureDao;
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	@Qualifier("iosDeviceServiceNotifyService")
	private AbstractIosPush abstractIosPush;
	
	@Autowired
	private IosDevicePolicyDao iosDevicePolicyDao;
	
	@Autowired
	private DeviceBasicInfoDao deviceBasicInfoDao;
	
	/**
	 * 当设备处于空闲状态时
	 */
	@Override
	public String operateIdleStatus(Map<String, Object> map) {
		try{
			String status = (String) map.get(Constant.MobileConfig.STATUS);
			String udid = (String) map.get(Constant.MobileConfig.UDID);
			if(Constant.getStatus.IDLE.equals(status)){
				if(StringUtil.isNotBlank(udid)){
					//发送--未处理的并且类型是设备规则的指令
					//注意命令只能发送一条，剩余的命令应该在成功或者失败或者notnow的时候进行继续想apns发送查询
					List<Command> commandList = commandDao.selectCommands(udid, Constant.Result.NONE,Constant.ICommandType.DevicePolicy);
					if(CollectionUtils.isNotEmpty(commandList)){
						Command command = commandList.get(0);
						command.setDeviceId(udid);
						Integer policyId = command.getCommandId();
						if(policyId!=null){
							List<TokenUpdateInfo> tokenList = tokenUpdateInfoDao.selectTokenInfoByUdid(udid);
							if(policyId==0) {
								// 将设备策略生成mobileconfig内容
							    if(CollectionUtils.isNotEmpty(tokenList)){
							    	TokenUpdateInfo tokenUpdateInfo = tokenList.get(0);
									String mobileconfigStr = deviceService.createMobileConfig(tokenUpdateInfo);
							    	// 将mobileconfig内容创建成plist推送命令
							        String plistStr = deviceService.createCommand(mobileconfigStr, command.getId());
							        return plistStr;
							    }
							} else {
								List<IosWifiConfigure> list = iosWifiConfigureDao.selectIosWifiByPolicyId(policyId);
								// 将设备策略生成mobileconfig内容
							    if(CollectionUtils.isNotEmpty(tokenList)){
							    	TokenUpdateInfo tokenUpdateInfo = tokenList.get(0);
							    	String mobileconfigStr = deviceService.createMobileConfig(policyId, list,tokenUpdateInfo);
							    	// 将mobileconfig内容创建成plist推送命令
						            return deviceService.createCommand(mobileconfigStr, command.getId());
							       /* String date = DateUtil.getyyyyMMddHHmmss(new Date());
							        // 原文件路径
									String filePath = String.format("mobileconfig/unSign_%s.mobileconfig", date);
									// 将存放已签名文件的路径
								    String newFilePath = String.format("new_mobileconfig/unSign_%s.mobileconfig", date);
								    
								    // 将未签名文件的内容写入已签名的文件(用openssl签名)
								    SignFileUtil.signFile(mobileconfigStr, filePath, newFilePath);
							        command.setDoit(Constant.Result.PENDING);
									command.setUpdateDate(new Date());
									command.setResult(Constant.getStatus.PENDING);
									int result = commandDao.updateByPrimaryKeySelective(command);
									if(result==0){
										return BaseDTO.ERROR;
									}
									File file = new File(CommUtil.SIGN_PATH + newFilePath);
									String str = FileUtils.file2String(file);
									if(StringUtil.isNotEmpty(str)){
										String data = Base64.getBase64(str);
										return PlistUtil.getInstallProfilePlist(data, command.getId());
									} else {
										return BaseDTO.ERROR;
									}
							        */
							    } else {
							    	return BaseDTO.ERROR;
							    }
							}
						}
					}
				}
			} 
			return BaseDTO.SUCCESS;
		} catch (Exception e){
			logger.error("ios device policy operate idle status is error,error message is " + e.getMessage());
			return BaseDTO.ERROR;
		}
	}

	/**
	 * 当设备处于Acknowleged状态时
	 */
	@Override
	public String operateAcknowledgedStatus(Map<String, Object> map) {
		try{
			String commandUUID = (String) map.get(Constant.MobileConfig.COMMAND_UUID);
			Command command = commandDao.selectByPrimaryKey(commandUUID);
			command.setId(commandUUID);
			command.setDoit(Constant.Result.ACKNOWLEDGED);
			command.setResult(Constant.getStatus.ACKNOWLEDGED);
			command.setUpdateDate(new Date());
			int result = commandDao.updateByPrimaryKeySelective(command);
			if(result==0){
				return BaseDTO.ERROR;
			}
			if(command.getCommandId()!=null) {
				IosDevicePolicy iosDevicePolicy = iosDevicePolicyDao.selectByPrimaryKey(command.getCommandId());
				List<Integer> list = iosDevicePolicyDao.selectUserListByPoicyId(iosDevicePolicy.getId());
				List<String> deviceTokenList = deviceBasicInfoDao.findDeviceTokenListByUserIds(list);
				Map<String, String> tokenMap = new HashMap<String, String>();
				tokenMap.put("allowAssistant", iosDevicePolicy.getAllowSiri());
				tokenMap.put("allowCloudBackup", iosDevicePolicy.getAllowBackup());
				tokenMap.put("allowCamera", iosDevicePolicy.getAllowUseCamera());
				tokenMap.put("allowiTunes", iosDevicePolicy.getAllowUseItunes());
				tokenMap.put("policyId", iosDevicePolicy.getId().toString());
                IosPushUtil.pushIosPolicyLimit("", 1, "", deviceTokenList, tokenMap);
			}
			String udid = (String) map.get(Constant.MobileConfig.UDID);
			iosNotifyed(udid);
			return BaseDTO.SUCCESS;
		} catch(Exception e){
			logger.error("ios device policy operate Acknowledged status is error,error message is " + e.getMessage());
			return BaseDTO.ERROR;
		}
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
