package com.f2b2c.eco.controller.platform.f2b;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.controller.BaseController;
import com.f2b2c.eco.model.platform.FRoleModel;
import com.f2b2c.eco.service.platform.FRoleService;

@Controller("fRoleController")
@RequestMapping("/farm/role")
public class FRoleController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(FRoleController.class);
	@Autowired
	private FRoleService fRoleService;
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(HttpServletRequest request,HttpServletResponse response){
		
		return "platform/role";
		
	}
	
	
	@RequestMapping(value="/page",method=RequestMethod.GET)
	@ResponseBody
	public final Page<FRoleModel> pagination(final Pageable pageable,HttpServletRequest request, HttpServletResponse response,HttpSession session){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Page<FRoleModel> pages = fRoleService.findWithPagination(pageable, paramMap);
		return pages;
	}
	
	
}
