package com.softtek.mdm.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.softtek.mdm.abs.AbsServerUrl;
import com.softtek.mdm.abs.AbstractIosPush;
import com.softtek.mdm.dao.AuthenticateInfoDao;
import com.softtek.mdm.dao.CommandDao;
import com.softtek.mdm.dao.DeviceBasicInfoDao;
import com.softtek.mdm.dao.DeviceManagerDao;
import com.softtek.mdm.dao.DeviceSaftyDao;
import com.softtek.mdm.dao.TokenUpdateInfoDao;
import com.softtek.mdm.dao.UserDao;
import com.softtek.mdm.model.AuthenticateInfo;
import com.softtek.mdm.model.Command;
import com.softtek.mdm.model.DeviceBasicInfoModel;
import com.softtek.mdm.model.DeviceManagerModel;
import com.softtek.mdm.model.DeviceSaftyModel;
import com.softtek.mdm.model.TokenUpdateInfo;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.service.IosDevicePushService;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.FileUtils;
import com.softtek.mdm.util.IosPushUtil;
import com.softtek.mdm.util.XmlUtils;
import com.softtek.mdm.web.http.BaseDTO;

/**
 * ios设备推送
 * 
 * @author jane.hui
 *
 */
@Service("iosDevicePushService")
public class IosDevicePushServiceImpl implements IosDevicePushService {

	private static Logger logger = Logger.getLogger(IosDevicePushServiceImpl.class);

	@Autowired
	private AuthenticateInfoDao authenticateInfoDao;

	@Autowired
	private TokenUpdateInfoDao tokenUpdateInfoDao;

	@Autowired
	private CommandDao commandDao;

	@Autowired
	@Qualifier("iosDeviceInfomationService")
	private AbstractIosPush abstractIosPush;

	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private DeviceManagerDao deviceManagerDao;
	
	@Autowired
	private DeviceBasicInfoDao deviceBasicInfoDao;
	
	@Autowired 
	private MessageSource messageSourceService;
	
	@Autowired
	private DeviceSaftyDao deviceSaftyDao;
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 禁止，权限不足
	 */
	private final String forbiddenCode="401";

	/**
	 * 推送指令	
	 * @see com.softtek.mdm.service.impl.IosDevicePushServiceImpl.serverUrl(Map<String, Object>)
	 */
	@Deprecated
	@Override
	public void serverUrl(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 确保将request XmlUtils.returnBody(request);解析后的数据放到key为res的map中
			String res = XmlUtils.returnBody(request);
			Map<String, Object> plist = XmlUtils.parseXmlByDom4j(res);
			String status = (String) plist.get(Constant.MobileConfig.STATUS);
			String udid = (String) plist.get(Constant.MobileConfig.UDID);
			 
			if (Constant.IIosInvokState.IDLE.equals(status)) {
				List<Command> commandList = commandDao.selectCommandByUdid(udid);
				if (CollectionUtils.isNotEmpty(commandList)) {
					Command command = commandList.get(0);
					AbsServerUrl absServerUrl = (AbsServerUrl) applicationContext.getBean(command.getClassName());
					
					//absServerUrl.operateIdleStatus(plist, response);
				}
			} else {
				String commandUUID = (String) plist.get(Constant.MobileConfig.COMMAND_UUID);
				Command command = commandDao.selectByPrimaryKey(commandUUID);
				AbsServerUrl absServerUrl = (AbsServerUrl) applicationContext.getBean(command.getClassName());
				if (Constant.IIosInvokState.ACKNOWLEDGED.equals(status)) {
					absServerUrl.operateAcknowledgedStatus(plist);
				} else if (Constant.IIosInvokState.COMMANDFORMATERROR.equals(status)) {
					absServerUrl.operateCommandFormatErrorStatus(plist);
				} else if (Constant.IIosInvokState.Error.equals(status)) {
					absServerUrl.operateErrorStatus(plist);
				} else if (Constant.IIosInvokState.NOTNOW.equals(status)) {
					absServerUrl.operateNotNowStatus(plist);
				}
			}
		} catch (Exception e) {
			logger.info("something wrong when apple install description file:" + e.getMessage());
		}
	}

	/**
	 * 认证
	 * @see com.softtek.mdm.service.impl.IosDevicePushServiceImpl.checkIn(Map<String, String>)
	 */
	@Deprecated
	@Override
	public void checkIn(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userId = request.getParameter("userId");
			String uuid = request.getParameter("uuid");
			String iosUuid = request.getParameter("iosUuid");
			String res = XmlUtils.returnBody(request);
			Map<String, String> plistMap = XmlUtils.parseAuthenticate(res);
			String messageType = plistMap.get(Constant.MobileConfig.MESSAGE_TYPE);
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String,String> extra = new HashMap<String, String>();
			if (messageType.equals(Constant.MESSAGE_TYPE.AUTHENTICATE)) {
				//在认证安装描述文件之前，先删除服务器上的描述文件
				UserModel user = userDao.findOne(Integer.valueOf(userId));
				boolean flag = deleteFile(user.getConfigFile(),user.getSignConfigFile());
				if(flag){
					user.setConfigFile(null);
					user.setSignConfigFile(null);
					userDao.updateConfigFile(user);
				}
				String topic = plistMap.get(Constant.MobileConfig.TOPIC);
				String udid = plistMap.get(Constant.MobileConfig.UDID);
				List<AuthenticateInfo> authenList = authenticateInfoDao.selectAuthenticateInfoByUdid(udid);
				AuthenticateInfo authenticateInfo = authenList.size() > 0 ? authenList.get(0) : new AuthenticateInfo();
				authenticateInfo.setUserId(Integer.valueOf(userId));
				authenticateInfo.setTopic(topic);
				authenticateInfo.setUdid(udid);
				authenticateInfo.setControl(Constant.YES);
				authenticateInfo.setUpdateDate(new Date());
				if (authenList.size() > 0) {
					authenticateInfoDao.updateByPrimaryKeySelective(authenticateInfo);
				} else {
					authenticateInfo.setCreateDate(new Date());
					authenticateInfoDao.insertSelective(authenticateInfo);
				}
				response.setHeader("content-type", "application/xml;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				String configTitle = "Mbaike_Blank";
				response.setHeader("Content-Disposition", "attachment; filename=" + configTitle + ".plist");
				PrintWriter sos = response.getWriter();
				sos.write(XmlUtils.getBlankPList());
				sos.flush();
				sos.close();
			} else if(messageType.equals(Constant.MESSAGE_TYPE.TokenUpdate)) {
				String pushMagic = plistMap.get(Constant.MobileConfig.PUSH_MAGIC);
				String oriToken = plistMap.get(Constant.MobileConfig.TOKEN);
				String topic = plistMap.get(Constant.MobileConfig.TOPIC);
				String udid = plistMap.get(Constant.MobileConfig.UDID);
				String unlockToken = plistMap.get(Constant.MobileConfig.UNLOCK_TOKEN);
				String token = XmlUtils.parseToken(oriToken);

				List<TokenUpdateInfo> tokenList = tokenUpdateInfoDao.selectTokenInfoByUdid(udid);
				TokenUpdateInfo tokenUpdateInfo = tokenList.size() > 0 ? tokenList.get(0) : new TokenUpdateInfo();
				tokenUpdateInfo.setPushMagic(pushMagic);
				tokenUpdateInfo.setToken(token);
				tokenUpdateInfo.setTopic(topic);
				tokenUpdateInfo.setUnlockToken(unlockToken);
				tokenUpdateInfo.setUdid(udid);
				tokenUpdateInfo.setUserId(Integer.valueOf(userId));
				tokenUpdateInfo.setUpdateDate(new Date());
				tokenUpdateInfo.setPayloadIdentifier(uuid);
				tokenUpdateInfo.setIosUuid(iosUuid);
				if (tokenList.size() > 0) {
					tokenUpdateInfo.setIsProfile(1);
					tokenUpdateInfoDao.updateByPrimaryKeySelective(tokenUpdateInfo);
					map.put("tokenUpdateInfo", tokenUpdateInfo);
					abstractIosPush.nofity(map);
				} else {
					tokenUpdateInfo.setIsProfile(1);
					tokenUpdateInfo.setCreateDate(new Date());
					tokenUpdateInfoDao.insertSelective(tokenUpdateInfo);
					map.put("tokenUpdateInfo", tokenUpdateInfo);
					abstractIosPush.nofity(map);
				}
			}else if(messageType.equals(Constant.MESSAGE_TYPE.CheckOut)){
				String udid = plistMap.get(Constant.MobileConfig.UDID);
				authenticateInfoDao.deleteAuthenticateInfoByUdid(udid);
				tokenUpdateInfoDao.deleteTokenUpdateInfoByUdid(udid);
				DeviceManagerModel deviceManager = deviceManagerDao.queryDeviceInfoByUdid(udid);
				//修改设备的监控状态
				if(deviceManager != null){
					DeviceSaftyModel deviceSafty = new DeviceSaftyModel();
					DeviceBasicInfoModel deviceBasicInfo = new DeviceBasicInfoModel();
					deviceBasicInfo.setDevice_status("3");
					deviceBasicInfo.setUdid(udid);
					deviceBasicInfoDao.updateDeviceStatusByUdid(deviceBasicInfo);
					deviceSafty.setMdm(1);
					deviceSafty.setDevice_id(deviceManager.getId());
					deviceSaftyDao.updateSaftyByDeviceId(deviceSafty);
				}
				//通知客户端，以不在监控中
				List<String> deviceToken = new ArrayList<String>();
				deviceToken.add(deviceManager.getDeviceToken().replaceAll(" ", ""));
				if(deviceToken.size() > 0){
					String alert = messageSourceService.getMessage("tiles.views.institution.ios.app.delete.profile",null, LocaleContextHolder.getLocale());
					IosPushUtil.pushDataToIosTo(deviceToken, alert,1,"default",extra);
				}
			}
		} catch (Exception e) {
			logger.info("something wrong when apple install description file:" + e.getMessage());
		}
	}	
	
	/**
	 * @see com.softtek.mdm.util.FileUtils.delete(String)
	 * 删除文件
	 * @param filePath
	 * @return
	 */
	@Deprecated
	private boolean deleteFile(String filePath,String signFilePath){
		String rooPath = CommUtil.SIGN_PATH;
		String aaptPath = CommUtil.AAPT_PATH;
		boolean flag = false;
	    File file = new File(aaptPath+filePath);
	    if(file.isFile() && file.exists()){
	    	file.delete();
	    }
	    File newFile = new File(rooPath + signFilePath);
	    if(newFile.isFile() && newFile.exists()){
	    	newFile.delete();
	    	flag = true;
	    }
	    return flag;
	}
	
	//-----------------add by color start---------------------------
	/**
	 * map
	 * |-userId
	 * |-uuid
	 * |-iosUuid
	 * |-+ plistMap
	 * 	 |-...
	 */
	@Override
	public String checkIn(Map<String, String> map) {
		try {
			String messageType =StringUtils.trimToEmpty(map.get(Constant.MobileConfig.MESSAGE_TYPE));
			
			if (messageType.equals(Constant.MESSAGE_TYPE.AUTHENTICATE)) {
				return authenticate(map);
			} else if(messageType.equals(Constant.MESSAGE_TYPE.TokenUpdate)) {
				return tokenUpdate(map);
			}else if(messageType.equals(Constant.MESSAGE_TYPE.CheckOut)){
				return checkOut(map);
			}
		} catch (Exception e) {
			logger.info("something wrong when apple install description file:" + e.getMessage());
		}
		return null;
	}
	
	/**
	 * 
	 */
	@Override
	public String serverUrl(Map<String, Object> map) {
		try {
			String status = (String) map.get(Constant.MobileConfig.STATUS);
			String udid = (String) map.get(Constant.MobileConfig.UDID);
			
			if (Constant.IIosInvokState.IDLE.equals(status)) {
				List<Command> commandList = commandDao.selectCommandByUdid(udid);
				if (CollectionUtils.isNotEmpty(commandList)) {
					Command command = commandList.get(0);
					AbsServerUrl absServerUrl = (AbsServerUrl) applicationContext.getBean(command.getClassName());
					return absServerUrl.operateIdleStatus(map);
				}
			} else {
				String commandUUID = (String) map.get(Constant.MobileConfig.COMMAND_UUID);
				Command command = commandDao.selectByPrimaryKey(commandUUID);
				AbsServerUrl absServerUrl = (AbsServerUrl) applicationContext.getBean(command.getClassName());
				if (Constant.IIosInvokState.ACKNOWLEDGED.equals(status)) {
					absServerUrl.operateAcknowledgedStatus(map);
				} else if (Constant.IIosInvokState.COMMANDFORMATERROR.equals(status)) {
					absServerUrl.operateCommandFormatErrorStatus(map);
				} else if (Constant.IIosInvokState.Error.equals(status)) {
					absServerUrl.operateErrorStatus(map);
				} else if (Constant.IIosInvokState.NOTNOW.equals(status)) {
					absServerUrl.operateNotNowStatus(map);
				}
			}
		} catch (Exception e) {
			logger.info("something wrong when apple install description file:" + e.getMessage());
		}
		return BaseDTO.ERROR;
	}
	
	/**
	 * 认证状态
	 * @param map
	 * @return
	 */
	private String authenticate(Map<String, String> map){
		String userId=map.get("userId");
		String udid = StringUtils.trimToEmpty(map.get(Constant.MobileConfig.UDID));
		
		//对udid的所属用户进行认证，确保当前的udid所属用户是当前这个userId
		if(deviceBasicInfoDao.checkIosRight(Integer.valueOf(userId), udid)<1){
			return forbiddenCode;
		}
		
		//在认证安装描述文件之前，先删除服务器上的描述文件
		UserModel user = userDao.findOne(Integer.valueOf(userId));
		if(FileUtils.delete(CommUtil.AAPT_PATH+StringUtils.trimToEmpty(user.getConfigFile()))){
			user.setConfigFile(null);
		}
		if(FileUtils.delete(CommUtil.SIGN_PATH+StringUtils.trimToEmpty(user.getSignConfigFile()))){
			user.setSignConfigFile(null);
		}
		userDao.updateConfigFile(user);
		
		String topic = StringUtils.trimToEmpty(map.get(Constant.MobileConfig.TOPIC));
		
		
		List<AuthenticateInfo> authenList = authenticateInfoDao.selectAuthenticateInfoByUdid(udid);
		
		AuthenticateInfo authenticateInfo = CollectionUtils.isNotEmpty(authenList) ? authenList.get(0) : new AuthenticateInfo();
		authenticateInfo.setUserId(Integer.valueOf(userId));
		authenticateInfo.setTopic(topic);
		authenticateInfo.setUdid(udid);
		authenticateInfo.setControl(Constant.YES);
		authenticateInfo.setUpdateDate(new Date());
		
		if (CollectionUtils.isNotEmpty(authenList)) {
			authenticateInfoDao.updateByPrimaryKeySelective(authenticateInfo);
		} else {
			authenticateInfo.setCreateDate(new Date());
			authenticateInfoDao.insertSelective(authenticateInfo);
		}
		return Constant.MESSAGE_TYPE.AUTHENTICATE;
	}

	/**
	 * tokenupdate
	 * @param map
	 * @return
	 */
	private String tokenUpdate(Map<String, String> map){
		String pushMagic = map.get(Constant.MobileConfig.PUSH_MAGIC);
		String topic = map.get(Constant.MobileConfig.TOPIC);
		String udid = map.get(Constant.MobileConfig.UDID);
		String unlockToken = map.get(Constant.MobileConfig.UNLOCK_TOKEN);
		String oriToken = map.get(Constant.MobileConfig.TOKEN);
		String token="";
		try {
			token = XmlUtils.parseToken(oriToken);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		String uuid = map.get("uuid");
		String iosUuid=map.get("iosUuid");
		
		List<TokenUpdateInfo> tokenList = tokenUpdateInfoDao.selectTokenInfoByUdid(udid);
		
		TokenUpdateInfo tokenUpdateInfo = CollectionUtils.isNotEmpty(tokenList)? tokenList.get(0) : new TokenUpdateInfo();
		tokenUpdateInfo.setPushMagic(pushMagic);
		tokenUpdateInfo.setToken(token);
		tokenUpdateInfo.setTopic(topic);
		tokenUpdateInfo.setUnlockToken(unlockToken);
		tokenUpdateInfo.setUdid(udid);
		tokenUpdateInfo.setUserId(Integer.valueOf(map.get("userId")));
		tokenUpdateInfo.setUpdateDate(new Date());
		tokenUpdateInfo.setUuid(uuid);
		tokenUpdateInfo.setPayloadIdentifier("mdm.softtek.com"+uuid);
		tokenUpdateInfo.setIosUuid(iosUuid);
		
		Map<String, Object> pushMap=new HashMap<String, Object>();
		if (CollectionUtils.isNotEmpty(tokenList)) {
			tokenUpdateInfo.setIsProfile(1);
			tokenUpdateInfoDao.updateByPrimaryKeySelective(tokenUpdateInfo);
			
			pushMap.put("tokenUpdateInfo", tokenUpdateInfo);
			abstractIosPush.nofity(pushMap);
		} else {
			tokenUpdateInfo.setIsProfile(1);
			tokenUpdateInfo.setCreateDate(new Date());
			tokenUpdateInfoDao.insertSelective(tokenUpdateInfo);
			
			pushMap.put("tokenUpdateInfo", tokenUpdateInfo);
			abstractIosPush.nofity(pushMap);
		}
		return Constant.MESSAGE_TYPE.TokenUpdate;
	}
	
	/**
	 * checkout
	 * @param map
	 * @return
	 */
	private String checkOut(Map<String, String> map){
		String udid = map.get(Constant.MobileConfig.UDID);
		
		authenticateInfoDao.deleteAuthenticateInfoByUdid(udid);
		tokenUpdateInfoDao.deleteTokenUpdateInfoByUdid(udid);
		
		DeviceManagerModel deviceManager = deviceManagerDao.queryDeviceInfoByUdid(udid);
		//修改设备的监控状态
		if(deviceManager != null){
			DeviceSaftyModel deviceSafty = new DeviceSaftyModel();
			DeviceBasicInfoModel deviceBasicInfo = new DeviceBasicInfoModel();
			deviceBasicInfo.setDevice_status("3");
			deviceBasicInfo.setIsActive(0);
			deviceBasicInfo.setUdid(udid);
			deviceBasicInfoDao.updateDeviceStatusByUdid(deviceBasicInfo);
			
			deviceSafty.setMdm(1);
			deviceSafty.setDevice_id(deviceManager.getId());
			deviceSaftyDao.updateSaftyByDeviceId(deviceSafty);
		}
		//通知客户端，以不在监控中
		List<String> deviceToken = new ArrayList<String>();
		deviceToken.add(deviceManager.getDeviceToken());
		if(deviceToken.size() > 0){
			String alert = messageSourceService.getMessage("tiles.views.institution.ios.app.delete.profile",null, LocaleContextHolder.getLocale());
			IosPushUtil.pushDataToIosTo(deviceToken, alert,1,"default",null);
		}
		return Constant.MESSAGE_TYPE.CheckOut;
	}
	
	//-----------------add by color end---------------------------

	

}