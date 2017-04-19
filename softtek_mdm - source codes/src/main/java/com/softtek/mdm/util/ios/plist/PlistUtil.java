package com.softtek.mdm.util.ios.plist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 用于生成iOS发送各种MDM指令所需要使用的plist文件
 * @author color.wu
 *
 */
public class PlistUtil {
	/**
	 * plist文件
	 * @param requestType e.g. DeviceLock
	 * @param extra 附带的其他参数，键值对字符串，无需你参数时传入""或者null将会被忽略
	 * @param commandUUID 32为commandUUID
	 * @return
	 */
	private static String template(String requestType,String extra,String commandUUID){
		StringBuilder builder=new StringBuilder();
		builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		builder.append("<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">");
    	builder.append("<plist version=\"1.0\">");
		builder.append("<dict>");
		builder.append("<key>Command</key>");
		builder.append("<dict>");
		builder.append("<key>RequestType</key>");
		builder.append("<string>"+requestType+"</string>");
		if(StringUtils.isNotEmpty(extra))
			builder.append(extra);
		builder.append("</dict>");
		builder.append("<key>CommandUUID</key>");
		builder.append("<string>"+commandUUID+"</string>");
		builder.append("</dict>");
		builder.append("</plist>");

		return builder.toString();
	}
	
	/**
	 * 1.
	 * ProfileList Commands Return a List of Installed Profiles
	 * @param commandUUID
	 * @return
	 */
	public static String getProfileListPlist(String commandUUID){
		return template(RequestType.ProfileList, null, commandUUID);
	}
	
	/**
	 * 2.
	 * InstallProfile Commands Install a Configuration Profile
	 * @param data The profile to install. May be signed and/or encrypted for any identity installed on the device.
	 * @param commandUUID
	 * @return
	 */
	public static String getInstallProfilePlist(String data,String commandUUID){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Payload", new PlistEle("data", data));
		String extra=mapToKeyValStr(map);
		return template(RequestType.InstallProfile, extra, commandUUID);
	}
	
	/**
	 * 3.
	 * RemoveProfile Commands Remove a Profile From the Device
	 * @param identifier The PayloadIdentifier value for the profile to remove.
	 * @param commandUUID
	 * @return
	 */
	public static String getRemoveProfilePlist(String identifier,String commandUUID){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Identifier", identifier);
		String extra=mapToKeyValStr(map);
		return template(RequestType.RemoveProfile, extra, commandUUID);
	}
	
	/**
	 * 4.
	 * ProvisioningProfileList Commands Get a List of Installed Provisioning Profiles
	 * @param commandUUID
	 * @return
	 */
	public static String getProvisioningProfileListPlist(String commandUUID){
		return template(RequestType.ProvisioningProfileList, null, commandUUID);
	}
	
	/**
	 * 5.
	 * InstallProvisioningProfile Commands Install Provisioning Profiles
	 * @param data The provisioning profile to install.
	 * @param commandUUID
	 * @return
	 */
	public static String getInstallProvisioningProfilePlist(String data,String commandUUID){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ProvisioningProfile", new PlistEle("data", data));
		String extra=mapToKeyValStr(map);
		return template(RequestType.InstallProvisioningProfile, extra, commandUUID);
	}
	
	/**
	 * 6.
	 * RemoveProvisioningProfile Commands Remove Installed Provisioning Profiles
	 * @param uuid The UUID of the provisioning profile to remove
	 * @param commandUUID
	 * @return
	 */
	public static String getRemoveProvisioningProfilePlist(String uuid,String commandUUID){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("UUID", uuid);
		String extra=mapToKeyValStr(map);
		return template(RequestType.RemoveProvisioningProfile, extra, commandUUID);
	}
	
	/**
	 * 7.
	 * CertificateList Commands Get a List of Installed Certificates
	 * @param commandUUID
	 * @return
	 */
	public static String getCertificateListPlist(String commandUUID){
		return template(RequestType.CertificateList, null, commandUUID);
	}
	
	/**
	 * 8.
	 * InstalledApplicationList Commands Get a List of Third-Party Applications
	 * @param commandUUID
	 * @return
	 */
	public static String getInstalledApplicationListPlist(String commandUUID){
		return template(RequestType.InstalledApplicationList, null, commandUUID);
	}
	
	/**
	 * 9.
	 * DeviceInformation Commands Get Information About the Device
	 * @param commandUUID
	 * @return
	 */
	public static String getDeviceInformationPlist(String commandUUID){
		List<String> queries=new ArrayList<>();
		queries.add("UDID");
		queries.add("DeviceName");
		queries.add("OSVersion");
		queries.add("BuildVersion");
		queries.add("ModelName");
		queries.add("Model");
		queries.add("ProductName");
		queries.add("SerialNumber");
		queries.add("DeviceCapacity");
		queries.add("AvailableDeviceCapacity");
		queries.add("BatteryLevel");
		queries.add("CellularTechnology");
		queries.add("IMEI");
		queries.add("MEID");
		queries.add("ModemFirmwareVersion");
		queries.add("ICCID");
		queries.add("BluetoothMAC");
		queries.add("WiFiMAC");
		queries.add("CurrentCarrierNetwork");
		queries.add("SIMCarrierNetwork");
		queries.add("SubscriberCarrier-Network");
		queries.add("CarrierSettingsVersion");
		queries.add("PhoneNumber");
		queries.add("VoiceRoamingEnabled");
		queries.add("isRoaming");
		queries.add("SubscriberMCC");
		queries.add("SubscriberMNC");
		queries.add("SIMMCC");
		queries.add("SIMMNC");
		queries.add("CurrentMCC");
		queries.add("CurrentMNC");
		Map<String, Object> map=new HashMap<>();
		map.put("Queries", queries);
		String extra=mapToKeyValStr(map);
		return template(RequestType.DeviceInformation, extra, commandUUID);
	}
	
	/**
	 * 10.
	 * SecurityInfo Commands Request Security-Related Information
	 * @param commandUUID
	 * @return
	 */
	public static String getSecurityInfoPlist(String commandUUID){
		return template(RequestType.SecurityInfo, null, commandUUID);
	}
	
	/**
	 * 11.
	 * DeviceLock Command Locks the Device Immediately
	 * @param commandUUID
	 * @return
	 */
	public static String getDeviceLockPlist(String commandUUID){
		return template(RequestType.DeviceLock, null, commandUUID);
	}
	
	/**
	 * 12.
	 * ClearPasscode Commands Clear the Passcode for a Device
	 * @param UnlockToken 可选，不然为null. If the device has given an UnlockToken value in the “TokenUpdate”  check-in message, the server must pass the data blob back to the device for this command to work.
	 * @param commandUUID
	 * @return
	 */
	public static String getClearPasscodePlist(String UnlockToken,String commandUUID){
		if(StringUtils.isNotEmpty(UnlockToken)){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("UnlockToken", new PlistEle("data", UnlockToken));
			String extra=mapToKeyValStr(map);
			return template(RequestType.ClearPasscode, extra, commandUUID);
		}
		return template(RequestType.ClearPasscode, null, commandUUID);
	}
	
	/**
	 * 13.
	 * EraseDevice Commands Remotely Erase a Device
	 * @param commandUUID
	 * @return
	 */
	public static String getEraseDevicePlist(String commandUUID){
		return template(RequestType.EraseDevice, null, commandUUID);
	}
	
	/**
	 * 14.
	 * Restrictions Commands Get a List of Installed Restrictions
	 * @param ProfileRestrictions 可选，或者未null .If true, the device will report restrictions enforced by each profile.
	 * @param commandUUID 
	 * @return
	 */
	public static String getRestrictionsPlist(Boolean ProfileRestrictions,String commandUUID){
		if(ProfileRestrictions!=null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ProfileRestrictions", ProfileRestrictions);
			String extra=mapToKeyValStr(map);
			return template(RequestType.Restrictions, extra, commandUUID);
		}
		return template(RequestType.Restrictions, null, commandUUID);
	}
	
	/**
	 * 15. iTunesStoreID/manifestURL至少存在一个值
	 * InstallApplication Commands Install a Third-Party Application
	 * @param iTunesStoreID The application's iTunes Store ID.
	 * @param manifestURL The URL where the manifest of an enterprise application can be found
	 * @param managementFlags null - null
	 * 						  1 - Remove app when MDM profile is removed.
	 * 						  4 - Prevent backup of the app data.
	 * @param commandUUID
	 * @return
	 */
	public static String getInstallApplicationPlist(String changeManagementState,String iTunesStoreID,String Identifier,String manifestURL,Integer managementFlags,String commandUUID){
		Map<String, Object> map=new HashMap<>();
		if(StringUtils.isNotEmpty(changeManagementState)) {
			map.put("ChangeManagementState", changeManagementState);
		}
/*		if(StringUtils.isNotEmpty(iTunesStoreID)) {
			map.put("iTunesStoreID", new PlistEle("integer", iTunesStoreID));
		}*/
		if(StringUtils.isNotEmpty(manifestURL)) {
			map.put("ManifestURL", manifestURL);
		}
		if(StringUtils.isNoneEmpty(Identifier)) {
			map.put("Identifier", Identifier);
		}
		if(managementFlags!=null) {
			map.put("ManagementFlags", managementFlags);
		}
		String extra=mapToKeyValStr(map);
		return template(RequestType.InstallApplication, extra, commandUUID);
	}
	
	/**
	 * 16.
	 * ApplyRedemptionCode Commands Install Paid Applications via Redemption Code
	 * @param Identifier The App ID returned by the InstallApplication command
	 * @param RedemptionCode The redemption code that applies to the app being installed.
	 * @param commandUUID
	 * @return
	 */
	public static String getApplyRedemptionCodePlist(String identifier,String redemptionCode,String commandUUID){
		Map<String, Object> map=new HashMap<>();
		map.put("Identifier",identifier);
		map.put("RedemptionCode", redemptionCode);
		String extra=mapToKeyValStr(map);
		return template(RequestType.ApplyRedemptionCode, extra, commandUUID);
	}
	
	/**
	 * 17.
	 * ManagedApplicationList Commands Provide the Status of Managed Applications
	 * @param commandUUID
	 * @return
	 */
	public static String getManagedApplicationListPlist(String commandUUID,String manifestURL){
		String extra=null;
		if(StringUtils.isNotEmpty(manifestURL)){
			Map<String, Object> map=new HashMap<>();
			map.put("ManifestURL",manifestURL);
			extra=mapToKeyValStr(map);
		}
		return template(RequestType.ManagedApplicationList, extra, commandUUID);
	}
	
	/**
	 * 18.
	 * RemoveApplication Commands Remove Installed Managed Applications
	 * @param identifier
	 * @param commandUUID
	 * @return
	 */
	public static String getRemoveApplicationPlist(String identifier,String commandUUID){
		Map<String, Object> map=new HashMap<>();
		map.put("Identifier",identifier);
		String extra=mapToKeyValStr(map);
		return template(RequestType.RemoveApplication, extra, commandUUID);
	}
	
	/**
	 * 19.
	 * Managed Settings
	 * @param enableVoiceRoaming  是否启用语音漫游
	 * @param enableDataRoaming	  是否启用数据漫游
	 * @param commandUUID
	 * @return
	 */
	public static String getSettingsPlist(Boolean enableVoiceRoaming,Boolean enableDataRoaming,String commandUUID){
		List<PlistEle> list=new ArrayList<>();
		//VoiceRoaming Modifies the Voice Roaming Setting
		Map<String, Object> voiceRoamingMap=new HashMap<>();
		voiceRoamingMap.put("Item", "VoiceRoaming");
		voiceRoamingMap.put("Enabled", enableVoiceRoaming==null?true:false);
		list.add(new PlistEle("dict", voiceRoamingMap));
		
		Map<String, Object> DataRoamingMap=new HashMap<>();
		DataRoamingMap.put("Item", "DataRoaming");	
		DataRoamingMap.put("Enabled", enableDataRoaming==null?true:false);
		list.add(new PlistEle("dict", DataRoamingMap));
		
		Map<String, Object> settingsMap=new HashMap<>();
		settingsMap.put("Settings", list);
		String extra=mapToKeyValStr(settingsMap);
		return template(RequestType.Settings, extra, commandUUID);
	}
	
	/**
	 * 根据不同的指令获取不同的Plist文件
	 * 注意，实际上这种多个else if的写法是不可取的，应该采用状态模式设计，或者直接使用独立的函数，例如上面的单个命令的函数。
	 * @param requestType 
	 * 指令名称：
	 * |-1.ProfileList
	 * |-2.InstallProfile
	 * |-3.RemoveProfile
	 * |-4.ProvisioningProfileList
	 * |-5.InstallProvisioningProfile
	 * |-6.RemoveProvisioningProfile
	 * |-7.CertificateList
	 * |-8.InstalledApplicationList
	 * |-9.DeviceInformation
	 * |-10.SecurityInfo
	 * |-11.DeviceLock
	 * |-12.ClearPasscode
	 * |-13.EraseDevice
	 * |-14.Restrictions
	 * |-15.InstallApplication
	 * |-16.ApplyRedemptionCode
	 * |-17.ManagedApplicationList
	 * |-18.RemoveApplication
	 * |-19.Settings
	 * @param objects
	 * 指令参数：见相应的方法 getXXXPlist(...)
	 * 注意参数顺序，必须严格按照相应的方法进行设置值
	 * @return
	 */
	public static String getPlist(String requestType,Object...objects){
		if(RequestType.ProfileList.equals(requestType)){
			return objects!=null&&objects.length>0?getProfileListPlist(String.valueOf(objects[0])):null;
		}else if (RequestType.InstallProfile.equals(requestType)) {
			return objects!=null&&objects.length>1?getInstallProfilePlist(String.valueOf(objects[0]), String.valueOf(objects[1])):null;
		}else if (RequestType.RemoveProfile.equals(requestType)) {
			return objects!=null&&objects.length>1?getRemoveProfilePlist(String.valueOf(objects[0]), String.valueOf(objects[1])):null;
		}else if (RequestType.ProvisioningProfileList.equals(requestType)) {
			return objects!=null&&objects.length>0?getProvisioningProfileListPlist(String.valueOf(objects[0])):null;
		}else if (RequestType.InstallProvisioningProfile.equals(requestType)) {
			return objects!=null&&objects.length>1?getInstallProvisioningProfilePlist(String.valueOf(objects[0]),String.valueOf(objects[1])):null;
		}else if (RequestType.RemoveProvisioningProfile.equals(requestType)) {
			return objects!=null&&objects.length>1?getRemoveProvisioningProfilePlist(String.valueOf(objects[0]),String.valueOf(objects[1])):null;
		}else if (RequestType.CertificateList.equals(requestType)) {
			return objects!=null&&objects.length>0?getCertificateListPlist(String.valueOf(objects[0])):null;
		}else if (RequestType.InstalledApplicationList.equals(requestType)) {
			return objects!=null&&objects.length>0?getInstalledApplicationListPlist(String.valueOf(objects[0])):null;
		}else if (RequestType.DeviceInformation.equals(requestType)) {
			return objects!=null&&objects.length>0?getDeviceInformationPlist(String.valueOf(objects[0])):null;
		}else if (RequestType.SecurityInfo.equals(requestType)) {
			return objects!=null&&objects.length>0?getSecurityInfoPlist(String.valueOf(objects[0])):null;
		}else if (RequestType.DeviceLock.equals(requestType)) {
			return objects!=null&&objects.length>0?getDeviceLockPlist(String.valueOf(objects[0])):null;
		}else if (RequestType.ClearPasscode.equals(requestType)) {
			return objects!=null&&objects.length>1?getClearPasscodePlist(String.valueOf(objects[0]),String.valueOf(objects[0])):null;
		}else if (RequestType.EraseDevice.equals(requestType)) {
			return objects!=null&&objects.length>1?getEraseDevicePlist(String.valueOf(objects[0])):null;
		}else if (RequestType.Restrictions.equals(requestType)) {
			return objects!=null&&objects.length>1?getRestrictionsPlist((Boolean) objects[0],String.valueOf(objects[1])):null;
		}else if (RequestType.InstallApplication.equals(requestType)) {
			return objects!=null&&objects.length>3?getInstallApplicationPlist(String.valueOf(objects[0]),String.valueOf(objects[1]),String.valueOf(objects[2]),String.valueOf(objects[3]),(Integer)objects[4],String.valueOf(objects[5])):null;
		}else if (RequestType.ApplyRedemptionCode.equals(requestType)) {
			return objects!=null&&objects.length>2?getApplyRedemptionCodePlist(String.valueOf(objects[0]),String.valueOf(objects[1]),String.valueOf(objects[2])):null;
		}else if (RequestType.ManagedApplicationList.equals(requestType)) {
			return objects!=null&&objects.length>0?getManagedApplicationListPlist(String.valueOf(objects[0]),String.valueOf(objects[1])):null;
		}else if (RequestType.RemoveApplication.equals(requestType)) {
			return objects!=null&&objects.length>1?getRemoveApplicationPlist(String.valueOf(objects[0]),String.valueOf(objects[1])):null;
		}else if (RequestType.Settings.equals(requestType)) {
			return objects!=null&&objects.length>2?getSettingsPlist((Boolean) objects[0],(Boolean) objects[1],String.valueOf(objects[2])):null;
		}
		return null;
	}
	
	/**
	 * 所有的Request Types值
	 * @author color.wu
	 *
	 */
	public interface RequestType{
		/**
		 * 1.ProfileList Commands Return a List of Installed Profiles
		 */
		String ProfileList="ProfileList";
		/**
		 * 2.InstallProfile Commands Install a Configuration Profile
		 */
		String InstallProfile="InstallProfile";
		/**
		 * 3.RemoveProfile Commands Remove a Profile From the Device
		 */
		String RemoveProfile="RemoveProfile";
		/**
		 * 4.ProvisioningProfileList Commands Get a List of Installed Provisioning Profiles
		 */
		String ProvisioningProfileList="ProvisioningProfileList";
		/**
		 * 5.InstallProvisioningProfile Commands Install Provisioning Profiles
		 */
		String InstallProvisioningProfile="InstallProvisioningProfile";
		/**
		 * 6.RemoveProvisioningProfile Commands Remove Installed Provisioning Profiles
		 */
		String RemoveProvisioningProfile="RemoveProvisioningProfile";
		/**
		 * 7.CertificateList Commands Get a List of Installed Certificates
		 */
		String CertificateList="CertificateList";
		/**
		 * 8.InstalledApplicationList Commands Get a List of Third-Party Applications
		 */
		String InstalledApplicationList="InstalledApplicationList";
		/**
		 * 9.DeviceInformation Commands Get Information About the Device
		 */
		String DeviceInformation="DeviceInformation";
		/**
		 * 10.SecurityInfo Commands Request Security-Related Information
		 */
		String SecurityInfo="SecurityInfo";
		/**
		 * 11.DeviceLock Command Locks the Device Immediately
		 */
		String DeviceLock="DeviceLock";
		/**
		 * 12.ClearPasscode Commands Clear the Passcode for a Device
		 */
		String ClearPasscode="ClearPasscode";
		/**
		 * 13.EraseDevice Commands Remotely Erase a Device
		 */
		String EraseDevice="EraseDevice";
		/**
		 * 14.Restrictions Commands Get a List of Installed Restrictions
		 */
		String Restrictions="Restrictions";
		/**
		 * 15.InstallApplication Commands Install a Third-Party Application
		 */
		String InstallApplication="InstallApplication";
		/**
		 * 16.ApplyRedemptionCode Commands Install Paid Applications via Redemption Code
		 */
		String ApplyRedemptionCode="ApplyRedemptionCode";
		/**
		 * 17.ManagedApplicationList Commands Provide the Status of Managed Applications
		 */
		String ManagedApplicationList="ManagedApplicationList";
		/**
		 * 18.RemoveApplication Commands Remove Installed Managed Applications
		 */
		String RemoveApplication="RemoveApplication";
		/**
		 * 19.Managed Settings
		 */
		String Settings="Settings";
	}
	
	/**
	 * 将map转换为键值对的字符串，并使用<>包含起来
	 * @param map
	 * @return
	 */
	private static String mapToKeyValStr(Map<String, Object> map){
		if(MapUtils.isEmpty(map)){
			return null;
		}
		Set<String> keys=map.keySet();
		StringBuilder builder=new StringBuilder();
		for (String key : keys) {
			builder.append(String.format("<key>%s</key>", key));
			Object obj = map.get(key);
			if(obj instanceof Map<?, ?>){
				Map<String, Object> tempMap=(Map<String, Object>)obj;
				builder.append(mapToKeyValStr(tempMap));
			}else {
				builder.append(transferType(obj));
			}
		}
		return builder.toString().length()>0?builder.toString():null;
	}
	
	/**
	 * 转换obj对象类型为相应的字符串
	 * @param obj
	 * @return
	 */
	private static String transferType(Object obj){
		if(obj instanceof PlistEle){
			PlistEle pe=(PlistEle)obj;
			String tag=pe.getTag();
			Object o=pe.getVal();
			if(StringUtils.isNotEmpty(tag)){
				if(o instanceof String||o instanceof Integer){
					return String.format("<%s>%s</%s>", tag,String.valueOf(o),tag);
				}else if(o instanceof Boolean){
					return String.format("<%s>%s</%s>", tag,(Boolean)o?"true":"false",tag);
				}else if(o instanceof ArrayList<?>){
					List<Object> list=(ArrayList<Object>)o;
					if(CollectionUtils.isNotEmpty(list)){
						StringBuilder builder=new StringBuilder();
						builder.append("<array>");
						for (Object ob : list) {
							builder.append(transferType(ob));
						}
						builder.append("</array>");
						return builder.toString();
					}
					return "";
				}else if(o instanceof Map<?, ?>){
					Map<String, Object> map=(Map<String, Object>)o;
					return String.format("<%s>%s</%s>", tag,mapToKeyValStr(map),tag);
				}
			}
		}else if(obj instanceof String){
			return String.format("<string>%s</string>", String.valueOf(obj));
		}else if (obj instanceof Integer) {
			return String.format("<integer>%s</integer>", String.valueOf(obj));
		}else if (obj instanceof Boolean) {
			return String.format("<boolean>%s</boolean>", (Boolean)obj?"true":"false");
		}else if(obj instanceof ArrayList<?>){
			List<Object> list=(ArrayList<Object>)obj;
			if(CollectionUtils.isNotEmpty(list)){
				StringBuilder builder=new StringBuilder();
				builder.append("<array>");
				for (Object o : list) {
					builder.append(transferType(o));
				}
				builder.append("</array>");
				return builder.toString();
			}
			return "";
		}
		return "";
	}
	
	/**
	 * 内置PlistElement对象
	 * @author color.wu
	 *
	 */
	public static class PlistEle extends Object{
		private String tag;
		private Object val;
		
		public PlistEle(String tag,Object val){
			this.tag=tag;
			this.val=val;
		}

		public String getTag() {
			return tag;
		}

		public void setTag(String tag) {
			this.tag = tag;
		}

		public Object getVal() {
			return val;
		}
		public void setVal(Object val) {
			this.val = val;
		}
	}
}
