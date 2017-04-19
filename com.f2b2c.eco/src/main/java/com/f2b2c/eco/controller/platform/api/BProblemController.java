package com.f2b2c.eco.controller.platform.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.service.platform.BProblemService;

@Controller("frontProblemController")
@RequestMapping("/api/problem")
public class BProblemController {

	private static final Logger logger = LoggerFactory.getLogger(BProblemController.class);
	
	@Autowired
	private BProblemService bProblemService;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getAllProblemList(HttpServletRequest request,HttpServletResponse response){
		
		Map<String,Object> result = new HashMap<String,Object>();
		List<Problem> lists = bProblemService.getAllProblemList();
		result.put("data", lists);
		result.put("status",200);
		result.put("msg", "获取常见问题列表成功");
		return result;
		
	}
	
	
	
	
	
	
}
