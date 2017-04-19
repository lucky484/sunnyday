package com.hd.pfirs.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hd.pfirs.model.ParamSet;
import com.hd.pfirs.service.FaceAndCardResultService;
import com.hd.pfirs.service.ParamSetService;

/**
 * 提供查询人证是否合一的请求
 * 
 * @author ligang.yang
 *
 */
@Controller
@RequestMapping("/result")
public class FCResultController {

	/**
	 * 注入查询服务
	 */
	@Autowired
	private FaceAndCardResultService FCResult;

	@Autowired
	private ParamSetService service;

	@RequestMapping(value = "updatefc")
	@ResponseBody
	public Map<String, Object> saveIdCardInfo() {
		
		ParamSet set = service.getParamSet();
		ParamSet setting = new ParamSet();
		setting = new ParamSet();
		setting.setParamId(1L);
		setting.setCtrlSyncCycle(1);
		setting.setFaceCardCompAlarmVal(90);
		setting.setTotalSyncCycle(1);
		setting.setFaceCompAlarmVal(52);
		setting.setCardCompareFlag("1");
		setting.setFaceCardCompFlag("1");
		setting.setFaceCompareFlag("1");
		if(set==null||set.getParamId() == null){
			setting.setRemarkDesc("测试insert");
			service.insertParamSet(setting);
		}else{
			setting.setRemarkDesc("测试update");
			service.updateParamSet(setting);
		}
		return null;
	}
}
