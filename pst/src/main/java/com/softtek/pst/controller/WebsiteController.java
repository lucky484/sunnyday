package com.softtek.pst.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.pst.model.ProjectModel;
import com.softtek.pst.service.ProjectService;

@Controller
@RequestMapping("/projectsManagement/website")
public class WebsiteController {
    
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value="/queryProjectsList",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryProjectsList(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		List<ProjectModel> list = projectService.queryProjectsList();
		map.put("list",list);
		return map;
	}
}
