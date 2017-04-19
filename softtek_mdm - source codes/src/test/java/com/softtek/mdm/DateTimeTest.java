package com.softtek.mdm;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.softtek.mdm.model.DeviceResultModel;
import com.softtek.mdm.status.DirectionStatus;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.MDMProtocolUtils;

import net.sf.json.JSONObject;

public class DateTimeTest {

	public void test4() {
		// 获取默认的mobileconfig文件
		String strXML = MDMProtocolUtils.getInitMobileConfig();
		System.out.println("原.mobileconfig文件内容：\n" + strXML);

		// 修改mobileconfig文件的任何key节点的内容
		Map<String, Entry<DirectionStatus, String>> map = new HashMap<>();
		// 修改过配置设备限制模块的PayloadDisplayName的名字
		map.put("/plist/dict/array/dict/PayloadDisplayName",
				new AbstractMap.SimpleEntry<DirectionStatus, String>(DirectionStatus.NEXT, "这个是修改过后的名字"));
		map.put("/plist/dict/array/dict/allowAssistant",
				new AbstractMap.SimpleEntry<DirectionStatus, String>(DirectionStatus.NEXT, "false"));
		map.put("/plist/dict/array/dict[2]/maxFailedAttempts",
				new AbstractMap.SimpleEntry<DirectionStatus, String>(DirectionStatus.NEXT, "10"));

		strXML = MDMProtocolUtils.getMobileConfigWithMap(map);
		System.out.println("修改之后的.mobileconfig文件内容：\n" + strXML);

		/*********************************
		 * 下面是指令plist文件获取
		 *********************************/
		System.out.println("\n\n");
		strXML = MDMProtocolUtils.getInstalledProfiles(CommUtil.generate32UUID());
		System.out.println(strXML);

	}

	@Test
	public void test2() {
		Integer i=null;
		if(i>(Integer)0){
			System.out.println("0000");
		}else{
			System.out.println("1111");
		}
	}
	
}
