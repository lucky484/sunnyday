package com.f2b2c.eco.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;



public class XmlUtil {

	 @SuppressWarnings("unchecked")
	public static Map<String,Object> parseXml(String xml){
		 Map<String,Object> map = new HashMap<String, Object>();
		 try {
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			List<Element> subElements = root.elements();
			for(Element ele : subElements){
				map.put(ele.getName(), ele.getText());
			}
		} catch (DocumentException e) {
			
			e.printStackTrace();
		}
		 return map;
	 }
	 public static void main(String[] args) {
		String xml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg><appid><![CDATA[wx2421b1c4370ec43b]]></appid>"+
                     "<mch_id><![CDATA[10000100]]></mch_id><nonce_str><![CDATA[IITRi8Iabbblz1Jc]]></nonce_str> <sign><![CDATA[7921E432F65EB8ED0CE9755F0E86D72F]]></sign>"+
                     "<result_code><![CDATA[SUCCESS]]></result_code><prepay_id><![CDATA[wx201411101639507cbf6ffd8b0779950874]]></prepay_id><trade_type><![CDATA[JSAPI]]></trade_type></xml>";
		parseXml(xml);
	}
}
