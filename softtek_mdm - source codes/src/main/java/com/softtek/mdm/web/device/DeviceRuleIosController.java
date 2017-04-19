package com.softtek.mdm.web.device;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.model.DeviceResultModel;
import com.softtek.mdm.model.DeviceRuleItemRelationModel;
import com.softtek.mdm.model.DeviceRuleModel;
import com.softtek.mdm.model.DeviceRuleOperationItemRelationModel;
import com.softtek.mdm.service.DeviceRuleItemRelationService;
import com.softtek.mdm.service.DeviceRuleOperationItemRelationService;
import com.softtek.mdm.service.DeviceRuleService;

@Controller
@RequestMapping(value="/terminal/deviceRuleIos")
public class DeviceRuleIosController {
        
	    @Autowired
	    private DeviceRuleService deviceRuleService; 
	    
	    @Autowired
	    private DeviceRuleItemRelationService deviceRuleItemRelationService;
	    
	    @Autowired
	    private DeviceRuleOperationItemRelationService deviceRuleOperationItemRelationService;
	
		@RequestMapping(value="/getDeviceRule",method = RequestMethod.POST)
		@ResponseBody
	    public DeviceResultModel<Map<String,Object>> getDeviceRule(HttpServletRequest request){
	    	DeviceResultModel<Map<String,Object>> deviceResult = new DeviceResultModel<Map<String,Object>>();
	    	Map<String,Object> map = new HashMap<String,Object>();
	    	String deviceRuleId = request.getParameter("deviceRuleId");
	    	if(deviceRuleId != null && !deviceRuleId.equals("")){
	    		DeviceRuleModel test_rule = deviceRuleService.findOne(Integer.valueOf(deviceRuleId));
	    		List<DeviceRuleItemRelationModel> itemList = deviceRuleItemRelationService.findAllByRuleId(Integer.valueOf(deviceRuleId));
	    		List<DeviceRuleOperationItemRelationModel> operationItemList = deviceRuleOperationItemRelationService.findAllByRuleId(Integer.valueOf(deviceRuleId));
	    		map.put("test_rule", test_rule);  //设备规则
	    		map.put("itemList", itemList);    //设备规则的明细
	    		map.put("operationItemList", operationItemList);   //设备规则的操作明细
	    		deviceResult.setData(map);
	    		deviceResult.setMsg("success");
	    		deviceResult.setStatus(200);
	    	}else{
	    		deviceResult.setMsg("fail");
	    		deviceResult.setStatus(500);
	    	}
	    	
	    	return deviceResult;
	    }
}
