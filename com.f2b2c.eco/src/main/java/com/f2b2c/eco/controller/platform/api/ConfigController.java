package com.f2b2c.eco.controller.platform.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.model.platform.BConfigModel;
import com.f2b2c.eco.service.platform.BConfigService;

@Controller
@RequestMapping(value="/api/config")
public class ConfigController {

	private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);
	
	@Autowired
	private BConfigService bConfigService;
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> config(HttpServletRequest request,HttpServletResponse response,Model model){
		
		Map<String,Object> result = new HashMap<String,Object>();
		String userId = request.getParameter("userId");
		BConfigModel configModel = bConfigService.getConfigByUserId(Integer.parseInt(userId));
		result.put("status", "200");
		result.put("data", configModel);
		result.put("msg", "获取系统通知设置成功");
		return result;
		
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> update(HttpServletRequest request,HttpServletResponse response,BConfigModel configModel){
		
		Map<String,Object> result = new HashMap<String,Object>();
		bConfigService.updateConfig(configModel);
		result.put("status", "200");
		result.put("msg", "修改系统通知成功");
		return result;
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
