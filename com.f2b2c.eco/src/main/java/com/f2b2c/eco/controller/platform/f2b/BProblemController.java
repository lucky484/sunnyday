package com.f2b2c.eco.controller.platform.f2b;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.model.platform.BProblemModel;
import com.f2b2c.eco.service.platform.BProblemService;

/**
 * 通知设置中的常见问题
 * @author jzhu
 *
 */
@Controller
@RequestMapping("/farm/problem")
public class BProblemController {

	private static final Logger logger = LoggerFactory.getLogger(BProblemController.class);
	@Autowired
	private BProblemService bProblemService;
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(HttpServletRequest request,HttpServletResponse response){
		
		return "farm/problem/index";
		
	}
	
	@RequestMapping(value="/page",method=RequestMethod.GET)
	@ResponseBody
	public Page<BProblemModel> pagination(final Pageable pageable,HttpServletRequest request,HttpServletResponse response){
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Page<BProblemModel> pages = bProblemService.findWithPagination(pageable,paramMap);
		return pages;
		
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> delete(HttpServletRequest request,HttpServletResponse response){
		
		Map<String,Object> result = new HashMap<String,Object>();
		String id = request.getParameter("id");
		bProblemService.deleteProblemById(Integer.parseInt(id));
		result.put("status", "200");
		result.put("type", "success");
		result.put("msg", "删除成功");
		return result;
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(HttpServletRequest request,HttpServletResponse response,Model model){
		
		return "farm/problem/add";
		
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> add(HttpServletRequest request,HttpServletResponse response,BProblemModel problemModel){
		
		Map<String,Object> result = new HashMap<String,Object>();
		int count = bProblemService.addProblem(problemModel);
		if(count>0){
			result.put("status",200);
			result.put("msg","添加常见问题成功");
		}else{
			result.put("status",400);
			result.put("msg","添加常见问题失败");
		}
		return result;
		
	}
	
	@RequestMapping(value="/show",method=RequestMethod.GET)
	public String show(HttpServletRequest request,HttpServletResponse response,Model model){
		String id = request.getParameter("id");
		BProblemModel problemModel = bProblemService.getProblemById(Integer.parseInt(id));
		model.addAttribute("problem", problemModel);
		return "farm/problem/show";
		
	}
	
	@RequestMapping(value="/update",method=RequestMethod.GET)
	public String update(HttpServletRequest request,HttpServletResponse response,Model model){
		String id = request.getParameter("id");
		BProblemModel problemModel = bProblemService.getProblemById(Integer.parseInt(id));
		model.addAttribute("problem", problemModel);
		return "farm/problem/update";
		
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> update(HttpServletRequest request,HttpServletResponse response,BProblemModel problemModel){
		
		Map<String,Object> result = new HashMap<String,Object>();
		int count = bProblemService.updateProblem(problemModel);
		if(count>0){
			result.put("status",200);
			result.put("msg","修改常见问题成功");
		}else{
			result.put("status",400);
			result.put("msg","修改常见问题失败");
		}
		return result;
		
	}
	
	
	
}
