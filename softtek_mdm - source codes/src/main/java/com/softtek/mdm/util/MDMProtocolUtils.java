package com.softtek.mdm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import com.softtek.mdm.status.DirectionStatus;

/**
 * 专门处理iOS推送描述文件或者指令的时候，生成相应的描述文件或者指令的字符串对象
 * 移动设备管理（MDM）协议提供了一种方式来告诉设备执行一定的管理
 * 远程命令。它的工作方式是简单的。
 * @author color.wu
 *
 */
public class MDMProtocolUtils {
	
	/**
	 * Plist文件的初始化最简单的XML
	 */
	private static String PLIST_DOCUMENT_XML=
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+"<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">"
			+"<plist version=\"1.0\">"
			+"<dict>"
			+"<key>Command</key>"
			+"<dict>"
			+"<key>RequestType</key>"
			+"<string>command</string>"
			+"</dict>"
			+"<key>CommandUUID</key>"
			+"<string>command</string>"
			+"</dict>"
			+"</plist>";
	/**
	 * 日志记录器
	 */
	private static Logger logger = Logger.getLogger(MDMProtocolUtils.class);
	
	/**
	 * 获取.mobileconfig配置文件filepath
	 * @return
	 */
	public static String getMobileConfigPath() {
		return CommUtil.TEMPLATE_MOBIILECONFIG_PATH;
	}

	/**
	 * 获取初始化的.mobileConfig文件配置内容
	 * @return
	 */
	public static String getInitMobileConfig(){
		String filePath=getMobileConfigPath();
		if(StringUtils.isEmpty(filePath)){
			return null;
		}
		return XMLUtil.getDefaultXML(filePath);
	}

	/**
	 * 根据Map的内容，将mobileconfig文件中<key/>标签对应的key值对应的内容设置为key对应的value
	 * @param map 如果key是以/开头表示按层级递进，
	 * /A/B表示从根节点开始定位到A节点下的B节点，
	 * 如果key是A表示定位到更节点下的A节点，
	 * 如果匹配出来的节点有多个，则会处理第一个匹配的节点
	 * .e.g 
	 * map=new Map<String, Entry<String, String>>();
	 * :--yourKey 指定的是mobileconfig文件中key的值 如果能够确定是唯一值，则可以直接写键值名，如果不是唯一值则需要包含节点层次，比如 /plist/dict/array/dict/PayloadDisplayName
	 * :--keyParam指的是DirectionStatus枚举值
	 * :--valueParam指的是修改的内容，如果内容是true或者false将会直接替换标签而不会体会标签中的内容，其他情况将会替换标签中的内容
	 * @return 返回mobileConfig document对象
	 */
	public static String getMobileConfigWithMap(Map<String, Entry<DirectionStatus, String>> map){
		String filePath=getMobileConfigPath();
		if(StringUtils.isEmpty(filePath)){
			return null;
		}
		Document document=XMLUtil.getDefault(filePath);
		if(null!=document){
			Set<String> keySets=map.keySet();
			if(CollectionUtils.isEmpty(keySets)){
				return document.asXML();
			}
			//存放需要修改key的值
			String txt=null;
			for (String keyStr : keySets) {
				//需要修改过的内容
				txt=map.get(keyStr).getValue();
				DirectionStatus direct=map.get(keyStr).getKey();
				//根据keyStr在document中定位到节点
				Element element=XMLUtil.findElement(document.getRootElement(), keyStr,direct);
				if(element!=null){
					if(StringUtils.isNotBlank(txt)){
						if(!"true".equals(txt)&&!"false".equals(txt)){
							element.setText(txt);
						}else{
							element.setName(txt);
							element.clearContent();
						}
					}
				}
			}
			return document.asXML();
		}
		return null;
	}
	
	/**
	 * 获取最简单的plist文件，其他的命令都是再次基础之上添加或者修改
	 * @param commandUUID 
	 * @param RequestType
	 * @return Document对象
	 */
	public static Document getInitCommandPlist(String commandUUID,String requestType){
		try{
			Document document=DocumentHelper.parseText(PLIST_DOCUMENT_XML);
			XMLUtil.findElement(document.getRootElement(), "/plist/dict/CommandUUID", DirectionStatus.NEXT).setText(commandUUID);
			XMLUtil.findElement(document.getRootElement(), "/plist/dict/dict/RequestType", DirectionStatus.NEXT).setText(requestType);
			return document;
		} catch(DocumentException e){
			logger.error(e.getMessage());
			return null;
		}
	}
	
	/**
	 * 指令plist文件---获取客户手机安装的Profiles
	 * @return
	 */
	public static String getInstalledProfiles(String commandUUID){
		Document document=getInitCommandPlist(commandUUID, Constant.RequestType.PROFILELIST);
		if(null==document){
			return null;
		}
		return document.asXML();
	}
	
	/**
	 * 指令plist文件---安装描述文件
	 * @param commandUUID
	 * @param dataWithoutBase64 描述文件的内容字符串（未加密）
	 * @return
	 */
	public static String InstallProfiles(String commandUUID,String dataWithoutBase64){
		Document document=getInitCommandPlist(commandUUID, Constant.RequestType.INSTALLPROFILE);
		if(null==document){
			return null;
		}
		Element dictTag=XMLUtil.findElement(document.getRootElement(), "/plist/dict/Command", DirectionStatus.NEXT);
		dictTag.add(XMLUtil.createElement("key", "Payload"));
		dictTag.add(XMLUtil.createElement("data",Base64.getBase64(dataWithoutBase64)));
		return document.asXML();
	}
	
	/**
	 * 指令plist文件---从设备移除描述文件
	 * @param commandUUID
	 * @param identifier 描述文件唯一标识
	 * @return
	 */
	public static String removeProfile(String commandUUID,String identifier){
		Document document=getInitCommandPlist(commandUUID, Constant.RequestType.REMOVEPROFILE);
		if(null==document){
			return null;
		}
		Element dictTag=XMLUtil.findElement(document.getRootElement(), "/plist/dict/Command", DirectionStatus.NEXT);
		dictTag.add(XMLUtil.createElement("key", Constant.RequestType.IDENTIFIER));
		dictTag.add(XMLUtil.createElement("string", identifier));
		return document.asXML();
	}
	
	/**
	 * 指令plist文件---获取安装的证书
	 * @param commandUUID
	 * @param identifier
	 * @return
	 */
	public static String getInstalledCertificates(String commandUUID){
		Document document=getInitCommandPlist(commandUUID, Constant.RequestType.CERTIFICATELIST);
		if(null==document){
			return null;
		}
		return document.asXML();
	}
	
	/**
	 *  指令plist文件---获取设备安装的app
	 * @param commandUUID
	 * @param identifiers 不需要填null，Optional. An array of app identifiers as strings. If provided, the response contains only the status of apps whose identifiers appear in this array. Available in iOS 7 and later.
	 * @param managedAppsOnly 不需要填null，Optional. If true, only managed app identifiers are returned. Available in iOS 7 and later.
	 * @return
	 */
	public static String getThridPartyApplications(String commandUUID,List<String> identifiers,Boolean managedAppsOnly){
		Document document=getInitCommandPlist(commandUUID, Constant.RequestType.INSTALLEDAPPLICATIONLIST);
		if(null==document){
			return null;
		}
		Element dictTag=XMLUtil.findElement(document.getRootElement(), "/plist/dict/Command", DirectionStatus.NEXT);
		if(CollectionUtils.isNotEmpty(identifiers)){
			dictTag.add(XMLUtil.createElement("key","Identifiers"));
			Element appArr=XMLUtil.createElement("array");
			for (String identifier : identifiers) {
				appArr.add(XMLUtil.createElement("string",identifier));
			}
			dictTag.add(appArr);
		}
		if(null!=managedAppsOnly){
			dictTag.add(XMLUtil.createElement("key","ManagedAppsOnly"));
			dictTag.add(XMLUtil.createElement(managedAppsOnly?"true":"false"));
		}
		return document.asXML();
	}
	
	/**
	 * 指令plist文件---查询设备的信息
	 * @param commandUUID
	 * @param queries 需要查询的字段的list集合
	 * 下面列出所有的集合，如果集合传递为null，则查询所有的信息
	 * @return
	 */
	public static String getDeviceInformation(String commandUUID,List<String> queries){
		Document document=getInitCommandPlist(commandUUID, Constant.RequestType.DEVICEINFORMATION);
		if(null==document){
			return null;
		}
		
		if(CollectionUtils.isEmpty(queries)){
			queries=getQueries();
		}
		Element dictTag=XMLUtil.findElement(document.getRootElement(), "/plist/dict/Command", DirectionStatus.NEXT);
		dictTag.add(XMLUtil.createElement("key","Queries"));
		Element eleArr=XMLUtil.createElement("array");
		for (String query : queries) {
			eleArr.add(XMLUtil.createElement("string",query));
		}
		dictTag.add(eleArr);
		return document.asXML();
	}
	
	/**
	 * 获取能够查询设备信息的所有字段的list集合
	 * @return
	 */
	private static List<String> getQueries(){
		List<String> queries=new ArrayList<String>();
		queries.add("UDID");
		queries.add("Languages");
		queries.add("Locales");
		queries.add("DeviceID");
		queries.add("OrganizationInfo");
		queries.add("LastCloudBackupDate");
		queries.add("Awaiting-Configuration");
		queries.add("DeviceName");
		queries.add("OSVersion");
		queries.add("BuildVersion");
		queries.add("ModelName");
		queries.add("Model");
		queries.add("ProductName");
		queries.add("SerialNumber");
		queries.add("DeviceCapacity");
		queries.add("AvailableDevice-Capacity");
		queries.add("BatteryLevel");
		queries.add("CellularTechnology");
		queries.add("IMEI");
		queries.add("MEID");
		queries.add("ModemFirmwareVersion");
		queries.add("IsSupervised");
		queries.add("IsDeviceLocator-ServiceEnabled");
		queries.add("IsActivation-LockEnabled");
		queries.add("IsDoNotDisturb-InEffect");
		queries.add("DeviceID");
		queries.add("EASDeviceIdentifier");
		queries.add("IsCloudBackupEnabled");
		queries.add("OSUpdateSettings");
		queries.add("LocalHostName");
		queries.add("HostName");
		queries.add("ICCID");
		queries.add("BluetoothMAC");
		queries.add("WiFiMAC");
		queries.add("EthernetMACs");
		queries.add("CurrentCarrier-Network");
		queries.add("SIMCarrierNetwork");
		queries.add("SubscriberCarrier-Network");
		queries.add("CarrierSettings-Version");
		queries.add("PhoneNumber");
		queries.add("VoiceRoamingEnabled");
		queries.add("DataRoamingEnabled");
		queries.add("IsRoaming");
		queries.add("PersonalHotspot-Enabled");
		queries.add("SubscriberMCC");
		queries.add("SubscriberMNC");
		queries.add("CurrentMCC");
		queries.add("CurrentMNC");

		return queries;
	}
	
	/**
	 * 指令plist文件---查询设备安全信息
	 * @param commandUUID
	 * @return
	 */
	public static String getSecurityInformation(String commandUUID){
		Document document=getInitCommandPlist(commandUUID, Constant.RequestType.SECURITYINFO);
		if(null==document){
			return null;
		}
		return document.asXML();
	}
	
	/**
	 * 指令plist---锁定设备
	 * @param commandUUID
	 * @param pin 可以为null
	 * @param message 显示在锁屏界面的消息，可以为null
	 * @param phoneNumber 如果提供则会将电话号码显示在锁屏上 可以为null
	 * @return
	 */
	public static String lockDevice(String commandUUID,String pin,String message,String phoneNumber){
		Document document=getInitCommandPlist(commandUUID, Constant.RequestType.DEVICELOCK);
		if(null==document){
			return null;
		}
		Element dictTag=XMLUtil.findElement(document.getRootElement(), "/plist/dict/Command", DirectionStatus.NEXT);
		if (StringUtils.isNotEmpty(pin)) {
			dictTag.add(XMLUtil.createElement("key","PIN"));
			dictTag.add(XMLUtil.createElement("string",pin));
		}
		if(StringUtils.isNotEmpty(message)){
			dictTag.add(XMLUtil.createElement("key","Message"));
			dictTag.add(XMLUtil.createElement("string",message));
		}
		if(StringUtils.isNotEmpty(phoneNumber)){
			dictTag.add(XMLUtil.createElement("key","PhoneNumber"));
			dictTag.add(XMLUtil.createElement("string",phoneNumber));
		}
		return document.asXML();
	}
	
	/**
	 * 指令plist---清楚设备密码
	 * @param commandUUID
	 * @param unlockToken TokenUpdate的时候上报上来的设备的unlockToken
	 * @return
	 */
	public static String clearPasscode(String commandUUID,String unlockToken){
		Document document=getInitCommandPlist(commandUUID, Constant.RequestType.CLEARPASSCODE);
		if(null==document||StringUtils.isEmpty(unlockToken)){
			return null;
		}
		
		Element dictTag=XMLUtil.findElement(document.getRootElement(), "/plist/dict/Command", DirectionStatus.NEXT);
		dictTag.add(XMLUtil.createElement("UnlockToken"));
		dictTag.add(XMLUtil.createElement("data",unlockToken));
		return document.asXML();
	}
	
	/**
	 * 指令plist--擦除设备数据
	 * @param commandUUID
	 * @param pin
	 * @return
	 */
	public static String eraseDevice(String commandUUID,String pin){
		Document document=getInitCommandPlist(commandUUID, Constant.RequestType.ERASEDEVICE);
		if(null==document){
			return null;
		}
		if(StringUtils.isNotEmpty(pin)){
			Element dictTag=XMLUtil.findElement(document.getRootElement(), "/plist/dict/Command", DirectionStatus.NEXT);
			dictTag.add(XMLUtil.createElement("key","PIN"));
			dictTag.add(XMLUtil.createElement("string",pin));
		}
		return document.asXML();
	}
	
	/**
	 * 指令plist---获取设备限制
	 * @param commandUUID
	 * @param profileRestrictions 可为null
	 * @return
	 */
	public static String getRestrictions(String commandUUID,Boolean profileRestrictions){
		Document document=getInitCommandPlist(commandUUID, Constant.RequestType.RESTRICTIONS);
		if(null==document){
			return null;
		}
		if (profileRestrictions!=null) {
			Element dictTag=XMLUtil.findElement(document.getRootElement(), "/plist/dict/Command", DirectionStatus.NEXT);
			dictTag.add(XMLUtil.createElement("key","ProfileRestrictions"));
			dictTag.add(XMLUtil.createElement(profileRestrictions==true?"true":"false"));
		}
		return document.asXML();
	}
	
	/**
	 * 指令plist---清除设备密码限制
	 * @param commandUUID
	 * @return
	 */
	public static String clearRestrictionsPassword(String commandUUID){
		Document document=getInitCommandPlist(commandUUID, Constant.RequestType.CLEARRETRICTIONSPASSWORD);
		if(null==document){
			return null;
		}
		return document.asXML();
	}
	
	/**
	 * 指令plist---安装第三方应用程序
	 * @param commandUUID
	 * @param iTunesStoreID
	 * @param identifier
	 * @return
	 */
	public static String installThirdParyApplication(String commandUUID,String iTunesStoreID,String identifier){
		Document document=getInitCommandPlist(commandUUID, Constant.RequestType.INSTALLAPPLICATION);
		if(null==document){
			return null;
		}
		Element dictTag=XMLUtil.findElement(document.getRootElement(), "/plist/dict/Command", DirectionStatus.NEXT);
		if(StringUtils.isNotEmpty(iTunesStoreID)){
			dictTag.add(XMLUtil.createElement("key","iTunesStoreID"));
			dictTag.add(XMLUtil.createElement("string",iTunesStoreID));
		}
		if(StringUtils.isNotEmpty(identifier)){
			dictTag.add(XMLUtil.createElement("key","Identifier"));
			dictTag.add(XMLUtil.createElement("string",identifier));
		}
		return document.asXML();
	}
	
	/**
	 * 指令plist---一移除应用app
	 * @param commandUUID
	 * @param identifier
	 * @return
	 */
	public static String removeApplication(String commandUUID,String identifier){
		Document document=getInitCommandPlist(commandUUID, Constant.RequestType.REMOVEAPPLICATION);
		if(null==document||StringUtils.isEmpty(identifier)){
			return null;
		}
		Element dictTag=XMLUtil.findElement(document.getRootElement(), "/plist/dict/Command", DirectionStatus.NEXT);
		dictTag.add(XMLUtil.createElement("key","Identifier"));
		dictTag.add(XMLUtil.createElement("string",identifier));
		
		return document.asXML();
	}
	
	/**
	 * 指令plist文件---系统设置
	 * @param commandUUID
	 * @param elements 系统其他设置的节点集合，例如语音漫游（voidceRoamingDict），个人热点（personalHotspot），数据漫游（dataRoaming）
	 * @return
	 */
	public static String setting(String commandUUID,List<Element> elements){
		Document document=getInitCommandPlist(commandUUID, Constant.RequestType.SETTINGS);
		if(null==document){
			return null;
		}
		if(CollectionUtils.isNotEmpty(elements)){
			Element dictTag=XMLUtil.findElement(document.getRootElement(), "/plist/dict/Command", DirectionStatus.NEXT);
			dictTag.add(XMLUtil.createElement("key","Settings"));
			Element arr=XMLUtil.createElement("array");
			for (Element element : elements) {
				Element dict=XMLUtil.createElement("dict");
				dict.add(element);
				arr.add(dict);
			}
		}
		return document.asXML();
	}
	
	/**
	 * 指令plist文件--setting部分之语音漫游
	 * @param enable
	 * @return
	 */
	public static Element voidceRoamingDict(Boolean enable){
		if(enable!=null){
			Element dictTag=XMLUtil.createElement("dict");
			dictTag.add(XMLUtil.createElement("key", "Item"));
			dictTag.add(XMLUtil.createElement("string", Constant.RequestType.SETTINGS_VOICEROAMING));
			dictTag.add(XMLUtil.createElement("key", "Enabled"));
			dictTag.add(XMLUtil.createElement(enable==true?"true":"false"));
			return  dictTag;
		}
		return null;
	}
	
	/**
	 * 指令plist文件--setting部分之个人热点
	 * @param enable
	 * @return
	 */
	public static Element personalHotspot(Boolean enable){
		if(enable!=null){
			Element dictTag=XMLUtil.createElement("dict");
			dictTag.add(XMLUtil.createElement("key", "Item"));
			dictTag.add(XMLUtil.createElement("string", Constant.RequestType.SETTINGS_PERSONALHOTSPOT));
			dictTag.add(XMLUtil.createElement("key", "Enabled"));
			dictTag.add(XMLUtil.createElement(enable==true?"true":"false"));
			return  dictTag;
		}
		return null;
	}
	
	/**
	 * 指令plist文件--setting部分之数据漫游
	 * @param enable
	 * @return
	 */
	public static Element dataRoaming(Boolean enable){
		if(enable!=null){
			Element dictTag=XMLUtil.createElement("dict");
			dictTag.add(XMLUtil.createElement("key", "Item"));
			dictTag.add(XMLUtil.createElement("string", Constant.RequestType.SETTINGS_DATAROAMING));
			dictTag.add(XMLUtil.createElement("key", "Enabled"));
			dictTag.add(XMLUtil.createElement(enable==true?"true":"false"));
			return  dictTag;
		}
		return null;
	}
}
