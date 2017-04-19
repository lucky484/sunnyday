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
import com.f2b2c.eco.model.platform.FAgentProfitModel;
import com.f2b2c.eco.service.platform.FAgentProfitService;

@Controller("fSettlementController")
@RequestMapping("/farm/admin/settlement")
public class FSettlementController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(FSettlementController.class);
	
	@Autowired
	private FAgentProfitService fAgentProfitService;
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(HttpServletRequest request,HttpServletResponse response){
		return "platform/settlement";
	}
	
	@RequestMapping(value="/page",method=RequestMethod.GET)
	@ResponseBody
	public final Page<FAgentProfitModel> pagination(final Pageable pageable,HttpServletRequest request, HttpServletResponse response,HttpSession session){
		//根据当前登录的用户的角色看到不同的订单分为区域管理员 市级管理员 省级管理员
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//FUserModel loginUser = (FUserModel) session.getAttribute("USER_INSESSION");
		//String name = request.getParameter("name");
		String timeStart = request.getParameter("timeStart");
		String timeEnd = request.getParameter("timeEnd");
		paramMap.put("timeStart", timeStart);
		paramMap.put("timeEnd", timeEnd);
		Page<FAgentProfitModel> pages = fAgentProfitService.findWithPagination(pageable, paramMap);
		return pages;
	}
	
	
	
}
