package com.softtek.mdm.util;

import org.dom4j.Document;
import org.dom4j.Element;

import com.softtek.mdm.util.XMLUtil;
import com.softtek.mdm.util.Constant.ElementType;
import com.softtek.mdm.util.Constant.IDeviceInfo;
import com.softtek.mdm.util.Constant.IIosMetadata.IGeneral.ICommand;

public class IosDeviceInfoPlistCreateServiceUtil {

	public static String createPlist(String commandUUID) {
		Document document = XMLUtil.createDocument();
		// 添加根节点plist元素
		Element rootElement = document.addElement(ElementType.PLIST);
		// 在根节点下添加dict元素
		Element dictElement = XMLUtil.createElement(ElementType.DICT);
		XMLUtil.addElement(rootElement,dictElement);
        // 在dict节点下添加key
		Element keyElement = XMLUtil.createElement(ElementType.KEY,ICommand._KEY_COMMAND_STR);
		XMLUtil.addElement(dictElement, keyElement);
		// 在dict节点下添加dict
		Element subDictElement = XMLUtil.createElement(ElementType.DICT);
		XMLUtil.addElement(dictElement, subDictElement);
		//----------------------RequestType----------------------------
		// 在sub dict节点下添加key
		Element subKeyElement = XMLUtil.createElement(ElementType.KEY,ICommand._KEY_REQUESTTYPE_STR);
		XMLUtil.addElement(subDictElement, subKeyElement);
		// 在sub dict节点下添加string
		Element subStringElement = XMLUtil.createElement(ElementType.STRING,IDeviceInfo.DeviceInformation);
		XMLUtil.addElement(subDictElement, subStringElement);
		// 在sub dict节点下添加key
		subKeyElement = XMLUtil.createElement(ElementType.KEY,IDeviceInfo.Queries);
		XMLUtil.addElement(subDictElement, subKeyElement);
		// 在sub dict节点下添加arr
		Element subArrayElement = XMLUtil.createElement(ElementType.ARRAY);
		XMLUtil.addElement(subDictElement, subArrayElement);
		//在sub array節點下添加String
		subKeyElement = XMLUtil.createElement(ElementType.STRING,IDeviceInfo.ModelName);
		XMLUtil.addElement(subArrayElement, subKeyElement);
		//在sub array節點下添加String
		subKeyElement = XMLUtil.createElement(ElementType.STRING,IDeviceInfo.SerialNumber);
		XMLUtil.addElement(subArrayElement, subKeyElement);
		//在sub array節點下添加String
		subKeyElement = XMLUtil.createElement(ElementType.STRING,"IMEI");
		XMLUtil.addElement(subArrayElement, subKeyElement);
		//在sub array節點下添加String
		subKeyElement = XMLUtil.createElement(ElementType.STRING,IDeviceInfo.OSVersion);
		XMLUtil.addElement(subArrayElement, subKeyElement);
		//在sub array節點下添加String
		subKeyElement = XMLUtil.createElement(ElementType.STRING,IDeviceInfo.PhoneNumber);
		XMLUtil.addElement(subArrayElement, subKeyElement);
		//在sub array節點下添加number
		subKeyElement = XMLUtil.createElement(ElementType.STRING,IDeviceInfo.BatteryLevel);
		XMLUtil.addElement(subArrayElement, subKeyElement);
		//在sub array節點下添加number
		subKeyElement = XMLUtil.createElement(ElementType.STRING,IDeviceInfo.DeviceCapacity);
		XMLUtil.addElement(subArrayElement, subKeyElement);
		//在sub array節點下添加number
		subKeyElement = XMLUtil.createElement(ElementType.STRING,IDeviceInfo.AvailableDeviceCapacity);
		XMLUtil.addElement(subArrayElement, subKeyElement);
		//在sub array節點下添加String
		subKeyElement = XMLUtil.createElement(ElementType.STRING,IDeviceInfo.SIMCarrierNetwork);
		XMLUtil.addElement(subArrayElement, subKeyElement);
		//在sub array節點下添加String
		subKeyElement = XMLUtil.createElement(ElementType.STRING,IDeviceInfo.SubscriberCarrierNetwork);
		XMLUtil.addElement(subArrayElement, subKeyElement);
		//在sub array節點下添加String
		subKeyElement = XMLUtil.createElement(ElementType.STRING,IDeviceInfo.ICCID);
		XMLUtil.addElement(subArrayElement, subKeyElement);
		//在sub array節點下添加String
		subKeyElement = XMLUtil.createElement(ElementType.STRING,IDeviceInfo.WiFiMAC);
		XMLUtil.addElement(subArrayElement, subKeyElement);
		//在sub array節點下添加String
		subKeyElement = XMLUtil.createElement(ElementType.STRING,IDeviceInfo.BluetoothMAC);
		XMLUtil.addElement(subArrayElement, subKeyElement);
		//在sub array節點下添加String
		subKeyElement = XMLUtil.createElement(ElementType.STRING,IDeviceInfo.VoiceRoamingEnabled);
		XMLUtil.addElement(subArrayElement, subKeyElement);
		//在sub array節點下添加String
		subKeyElement = XMLUtil.createElement(ElementType.STRING,IDeviceInfo.PersonalHotspotEnabled);
		XMLUtil.addElement(subArrayElement, subKeyElement);
		//在sub array節點下添加String
		subKeyElement = XMLUtil.createElement(ElementType.STRING,IDeviceInfo.DataRoamingEnabled);
		XMLUtil.addElement(subArrayElement, subKeyElement);
		//----------------------commandUUID------------------------
		// 在sub dict节点下添加key
		keyElement = XMLUtil.createElement(ElementType.KEY,ICommand._KEY_COMMANDUUID_STR);
		XMLUtil.addElement(dictElement, keyElement);
		// 在sub dict节点下添加string
		Element stringElement = XMLUtil.createElement(ElementType.STRING,commandUUID);
		XMLUtil.addElement(dictElement, stringElement);
		return document.asXML();
	}
	
	 public static String getCommandInfoPList(String commandUUID){
		 	StringBuffer sb = new StringBuffer();
		 	sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		 	sb.append("<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\"");
		 	sb.append("\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">");
		 	sb.append("<plist version=\"1.0\">");
		 	sb.append("<dict>");
		 	sb.append("<key>Command</key>");
		 	sb.append("<dict>");
		 	sb.append("<key>RequestType</key>");
		 	sb.append("<string>");
		 	sb.append("DeviceInformation");
		 	sb.append("</string>");
		 	sb.append("<key>Queries</key>");
		 	sb.append("<array>");
		 	sb.append("<string>IMEI</string>");
		 	sb.append("<string>BluetoothMAC</string>");
		 	sb.append("<string>PhoneNumber</string>");
		 	sb.append("</array>");
		 	sb.append("</dict>");
		 	sb.append("<key>CommandUUID</key>");
		 	sb.append("<string>");
		 	sb.append(commandUUID);
		 	sb.append("</string>");
		 	sb.append("</dict></plist>");
		 	return sb.toString();
	 }
	 
	 public static String lockDevicePlist(String commandUUID,String command) {
			Document document = XMLUtil.createDocument();
			// 添加根节点plist元素
			Element rootElement = document.addElement(ElementType.PLIST);
			// 在根节点下添加dict元素
			Element dictElement = XMLUtil.createElement(ElementType.DICT);
			XMLUtil.addElement(rootElement,dictElement);
	        // 在dict节点下添加key
			Element keyElement = XMLUtil.createElement(ElementType.KEY,ICommand._KEY_COMMAND_STR);
			XMLUtil.addElement(dictElement, keyElement);
			// 在dict节点下添加dict
			Element subDictElement = XMLUtil.createElement(ElementType.DICT);
			XMLUtil.addElement(dictElement, subDictElement);
	        // 在dict节点下添加key
		    keyElement = XMLUtil.createElement(ElementType.KEY,ICommand._KEY_REQUESTTYPE_STR);
			XMLUtil.addElement(subDictElement, keyElement);
			// 在dict节点下添加dict
			Element stringElement = XMLUtil.createElement(ElementType.STRING,command);
			XMLUtil.addElement(subDictElement, stringElement);
			//----------------------RequestType----------------------------
			// 在sub dict节点下添加key
			keyElement = XMLUtil.createElement(ElementType.KEY,ICommand._KEY_COMMANDUUID_STR);
			XMLUtil.addElement(dictElement, keyElement);
			// 在sub dict节点下添加string
			Element stringSubElement = XMLUtil.createElement(ElementType.STRING,commandUUID);
			XMLUtil.addElement(dictElement, stringSubElement);
			return document.asXML();
		}
	 
	 public static String clearPasscode(String commandUUID,String unlockToken) {
			Document document = XMLUtil.createDocument();
			// 添加根节点plist元素
			Element rootElement = document.addElement(ElementType.PLIST);
			// 在根节点下添加dict元素
			Element dictElement = XMLUtil.createElement(ElementType.DICT);
			XMLUtil.addElement(rootElement,dictElement);
	        // 在dict节点下添加key
			Element keyElement = XMLUtil.createElement(ElementType.KEY,ICommand._KEY_COMMAND_STR);
			XMLUtil.addElement(dictElement, keyElement);
			// 在dict节点下添加dict
			Element subDictElement = XMLUtil.createElement(ElementType.DICT);
			XMLUtil.addElement(dictElement, subDictElement);
	        // 在dict节点下添加key
		    keyElement = XMLUtil.createElement(ElementType.KEY,ICommand._KEY_REQUESTTYPE_STR);
			XMLUtil.addElement(subDictElement, keyElement);
			// 在dict节点下添加dict
			Element stringElement = XMLUtil.createElement(ElementType.STRING,ICommand.ClearPasscode);
			XMLUtil.addElement(subDictElement, stringElement);
			
			keyElement = XMLUtil.createElement(ElementType.KEY,ICommand.UnlockToken);
			XMLUtil.addElement(subDictElement, keyElement);
			
			Element dataElement = XMLUtil.createElement(ElementType.DATA,unlockToken);
			XMLUtil.addElement(subDictElement, dataElement);
			//----------------------RequestType----------------------------
			// 在sub dict节点下添加key
			keyElement = XMLUtil.createElement(ElementType.KEY,ICommand._KEY_COMMANDUUID_STR);
			XMLUtil.addElement(dictElement, keyElement);
			// 在sub dict节点下添加string
			Element stringSubElement = XMLUtil.createElement(ElementType.STRING,commandUUID);
			XMLUtil.addElement(dictElement, stringSubElement);
			return document.asXML();
		}
	 
	 public static String getDeviceAppList(String commandUUID){
		    Document document = XMLUtil.createDocument();
			// 添加根节点plist元素
			Element rootElement = document.addElement(ElementType.PLIST);
			// 在根节点下添加dict元素
			Element dictElement = XMLUtil.createElement(ElementType.DICT);
			XMLUtil.addElement(rootElement,dictElement);
	        // 在dict节点下添加key
			Element keyElement = XMLUtil.createElement(ElementType.KEY,ICommand._KEY_COMMAND_STR);
			XMLUtil.addElement(dictElement, keyElement);
			// 在dict节点下添加dict
			Element subDictElement = XMLUtil.createElement(ElementType.DICT);
			XMLUtil.addElement(dictElement, subDictElement);
	        // 在dict节点下添加key
		    keyElement = XMLUtil.createElement(ElementType.KEY,ICommand._KEY_REQUESTTYPE_STR);
			XMLUtil.addElement(subDictElement, keyElement);
			// 在dict节点下添加dict
			Element stringElement = XMLUtil.createElement(ElementType.STRING,ICommand.InstalledApplicationList);
			XMLUtil.addElement(subDictElement, stringElement);
			
			//----------------------RequestType----------------------------
			// 在sub dict节点下添加key
			keyElement = XMLUtil.createElement(ElementType.KEY,ICommand._KEY_COMMANDUUID_STR);
			XMLUtil.addElement(dictElement, keyElement);
			// 在sub dict节点下添加string
			Element stringSubElement = XMLUtil.createElement(ElementType.STRING,commandUUID);
			XMLUtil.addElement(dictElement, stringSubElement);
			return document.asXML();
	 }






}
