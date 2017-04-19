package com.softtek.mdm.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.softtek.mdm.abs.AbsServerUrl;
import com.softtek.mdm.abs.AbstractIosPush;
import com.softtek.mdm.dao.CommandDao;
import com.softtek.mdm.dao.DeviceBasicInfoDao;
import com.softtek.mdm.dao.DeviceManagerDao;
import com.softtek.mdm.dao.DeviceNetworkStatusDao;
import com.softtek.mdm.dao.DeviceSaftyDao;
import com.softtek.mdm.dao.TokenUpdateInfoDao;
import com.softtek.mdm.dao.UserDao;
import com.softtek.mdm.model.Command;
import com.softtek.mdm.model.DeviceBasicInfoModel;
import com.softtek.mdm.model.DeviceManagerModel;
import com.softtek.mdm.model.DeviceNetworkStatusModel;
import com.softtek.mdm.model.DeviceSaftyModel;
import com.softtek.mdm.model.TokenUpdateInfo;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.Constant.IDeviceInfo;
import com.softtek.mdm.util.ios.plist.PlistUtil;
import com.softtek.mdm.web.http.BaseDTO;

import jodd.util.StringUtil;

@Service("IosDeviceInfoPlistServiceImpl")
public class IosDeviceInfoPlistServiceImpl extends AbsServerUrl  {
    
	@Autowired
	private CommandDao commandDao;
	@Autowired
	private TokenUpdateInfoDao tokenUpdateInfoDao;
	
	@Autowired
	private DeviceManagerDao deviceManagerDao;
	
	@Autowired
	private DeviceBasicInfoDao deviceBasicInfoDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private DeviceNetworkStatusDao deviceNetworkStatusDao;
	
    @Autowired
	private MessageSource messageSourceService;
    
    @Autowired
    private DeviceSaftyDao deviceSaftyDao;
    
    @Autowired
    @Qualifier("IosDeviceAppListServiceImpl")
    private AbstractIosPush abstractIosPush;
	
	@Override
	public String operateIdleStatus(Map<String, Object> map) {
		String status = (String) map.get(Constant.MobileConfig.STATUS);
		String udid = (String) map.get(Constant.MobileConfig.UDID);
		if(Constant.getStatus.IDLE.equals(status)){
			if(StringUtil.isNotEmpty(udid)){
				Command command = getFirstCommand(udid,Constant.Result.NONE, Constant.ICommandType.DeviceManage);
				if(command==null||!PlistUtil.RequestType.DeviceInformation.equals(command.getCommand())){
					return BaseDTO.ERROR;
				}
				String plistStr = PlistUtil.getPlist(PlistUtil.RequestType.DeviceInformation, command.getId());
				if(StringUtils.isNotEmpty(plistStr)){
					command.setDeviceId(udid);
					command.setDoit(Constant.Result.PENDING);
	        		command.setUpdateDate(new Date());
	        		int result = commandDao.updateByPrimaryKeySelective(command);
	        		if(result == 0){
	        			return BaseDTO.ERROR;
	        		}
				}
        		return plistStr;
			}
		}
		return BaseDTO.SUCCESS;
	}

	@Override
	public String operateAcknowledgedStatus(Map<String, Object> map) {
		String commandUUID = (String) map.get(Constant.MobileConfig.COMMAND_UUID);
		Map<String,Object> paraMap = new HashMap<String, Object>();
		
		List<TokenUpdateInfo> list = tokenUpdateInfoDao.selectTokenInfoByUdid((String)map.get(Constant.MobileConfig.UDID));
		
		UserModel user = userDao.findOne(list.get(0).getUserId());
		
		Command command = commandDao.selectByPrimaryKey(commandUUID);
		command.setId(commandUUID);
		command.setDoit(Constant.Result.ACKNOWLEDGED);
		command.setResult(Constant.getStatus.ACKNOWLEDGED);
		command.setUpdateDate(new Date());
		int result = commandDao.updateByPrimaryKeySelective(command);
		
		DeviceManagerModel deviceManager = deviceManagerDao.queryIosIsExists(list.get(0).getIosUuid());
		
		DeviceBasicInfoModel device = new DeviceBasicInfoModel();
		DeviceNetworkStatusModel deviceNetworkStatus = new DeviceNetworkStatusModel();
		DeviceSaftyModel deviceSafty = new DeviceSaftyModel();
		
		BigDecimal power = new BigDecimal((String)map.get("BatteryLevel"));
		BigDecimal capacity = new BigDecimal((String)map.get("DeviceCapacity"));
		BigDecimal availableCapacity = new BigDecimal((String)map.get("AvailableDeviceCapacity") == null ? "0" :(String) map.get("AvailableDeviceCapacity"));
		BigDecimal freeMemory = capacity.subtract(availableCapacity);
		
		device.setIosUuid(list.get(0).getIosUuid());
		device.setDevice_name((String)map.get("DeviceName"));
		device.setOrgId(user.getOrganization().getId());
		device.setDevice_type("ios");
		device.setSn((String)map.get("SerialNumber"));
		device.setEsnorimei((String)map.get("IMEI"));
		device.setDevice_status("4");
		device.setIsActive(1);
		device.setUser(user);
		device.setOs_version((String)map.get("OSVersion"));
		device.setPhone_number((String)map.get("PhoneNumber"));
		device.setIp((String)map.get("ip"));
		device.setPower(Float.valueOf(power.setScale(2,BigDecimal.ROUND_HALF_UP).toString()) * 100 + "%");
		device.setUdid((String)map.get(Constant.MobileConfig.UDID));
		device.setCapacity(freeMemory.setScale(1,BigDecimal.ROUND_HALF_UP).toString() + "," + capacity.setScale(1,BigDecimal.ROUND_HALF_UP).toString());
		//device.setIrr_status(0);
		//device.setBreak_status(0);
		device.setIsActive(1);
		
		//修改设备的网络信息
		String opeanStatus = messageSourceService.getMessage("tiles.views.institution.device.rule.add.type.condition.gps.open", null, LocaleContextHolder.getLocale());
		String closeStatus = messageSourceService.getMessage("tiles.fragments.consumer.nav.updatedevice.close", null, LocaleContextHolder.getLocale());
		deviceNetworkStatus.setVendor((String)map.get(IDeviceInfo.SIMCarrierNetwork));
		deviceNetworkStatus.setNet_type_id((String)map.get(IDeviceInfo.SubscriberCarrierNetwork));
		deviceNetworkStatus.setPhone((String)map.get("PhoneNumber"));
		deviceNetworkStatus.setSim_number((String)map.get(IDeviceInfo.ICCID));
		deviceNetworkStatus.setWifi_mac((String)map.get(IDeviceInfo.WiFiMAC));
		deviceNetworkStatus.setBlue_tooth_mac((String)map.get(IDeviceInfo.BluetoothMAC));
		deviceNetworkStatus.setHot_point(map.get(IDeviceInfo.PersonalHotspotEnabled) == "true" ? opeanStatus : closeStatus);
		deviceNetworkStatus.setVoice_roam(map.get(IDeviceInfo.VoiceRoamingEnabled) == "true" ? opeanStatus : closeStatus);
		deviceNetworkStatus.setData_roam(map.get(IDeviceInfo.DataRoamingEnabled) == "true" ? opeanStatus : closeStatus);
		
		//设备安全信息
		deviceSafty.setMdm(4);
		deviceSafty.setVoice_roam(map.get(IDeviceInfo.VoiceRoamingEnabled) == "true" ? 1 : 0);
		deviceSafty.setData_roam(map.get(IDeviceInfo.DataRoamingEnabled) == "true" ? 1 : 0);
		
		if(deviceManager != null){
			device.setId(deviceManager.getId());
			device.setUpdate_time(new Date());
			deviceBasicInfoDao.updateIosDevice(device);
			
			//修改网络信息
			deviceNetworkStatus.setDevice_id(deviceManager.getId());
			deviceNetworkStatusDao.update(deviceNetworkStatus);
			
			//更新设备安全信息
			deviceSafty.setDevice_id(deviceManager.getId());
			deviceSaftyDao.updateSaftyByDeviceId(deviceSafty);
			
			//发送获取app list的指令
			paraMap.put("udid",map.get(Constant.MobileConfig.UDID));
			abstractIosPush.nofity(paraMap);
		}else{
			deviceBasicInfoDao.save(device);
		}
		if(result==0){
			return BaseDTO.ERROR;
		}
		return BaseDTO.SUCCESS;
	}
	
	

	@Override
	public void operateErrorStatus(Map<String, Object> map) {
		String commandUUID = (String) map.get(Constant.MobileConfig.COMMAND_UUID);
		if(StringUtils.isNotEmpty(commandUUID)){
			Command command = commandDao.selectByPrimaryKey(commandUUID);
			command.setId(commandUUID);
			command.setDoit(Constant.Result.ERROR);
			command.setResult(Constant.getStatus.ERROR);
			command.setUpdateDate(new Date());
		    commandDao.updateByPrimaryKeySelective(command);
		}
	}

	@Override
	public void operateCommandFormatErrorStatus(Map<String, Object> map) {
		String commandUUID = (String) map.get(Constant.MobileConfig.COMMAND_UUID);
		if(StringUtils.isNotEmpty(commandUUID)){
			Command command = commandDao.selectByPrimaryKey(commandUUID);
			command.setId(commandUUID);
			command.setDoit(Constant.Result.ERROR);
			command.setResult(Constant.getStatus.ERROR);
			command.setUpdateDate(new Date());
		    commandDao.updateByPrimaryKeySelective(command);
		}
	}

	@Override
	public void operateNotNowStatus(Map<String, Object> map) {
		// TODO Auto-generated method stub
		super.operateNotNowStatus(map);
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
}
