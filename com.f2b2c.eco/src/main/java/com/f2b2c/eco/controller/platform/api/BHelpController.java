package com.f2b2c.eco.controller.platform.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.f2b2c.eco.model.platform.FHelpModel;
import com.f2b2c.eco.service.platform.FHelpService;

@Controller("BHelpController")
@RequestMapping(value = "/api/bhelp")
public class BHelpController {

	/*
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(BHelpController.class);
	
	@Autowired
	private FHelpService fHelpService;
	
	/**
	 * 移动端·帮助中心
	 * @author mozzie.chu
	 * @param style
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/query-help-style", method = RequestMethod.GET)
	public final String queryDetailByStyle(String style, HttpServletRequest request,
			HttpServletResponse response) {
		List<FHelpModel> model = fHelpService.queryDetailByStyle("1");
		request.setAttribute("model", model);
		return "market/bHelpCenter";
		
	}
	
	/**
	 * 移动端·购买咨询
	 * @author mozzie.chu
	 * @return
	 */
	@RequestMapping(value = "/query-help-advice", method = RequestMethod.GET)
	private final String queryDetailByType0(String type, HttpServletRequest request,
			HttpServletResponse response) {
		List<FHelpModel> model = fHelpService.queryDetailByType("0");
		request.setAttribute("model", model);
		return "market/bAdvice";
		
	}

	/**
	 * 移动端·支付问题
	 * @author mozzie.chu
	 * @return
	 */
	@RequestMapping(value = "/query-help-pay", method = RequestMethod.GET)
	private final String queryDetailByType1(String type, HttpServletRequest request,
			HttpServletResponse response) {
		List<FHelpModel> model = fHelpService.queryDetailByType("1");
		request.setAttribute("model", model);
		return "market/bPay";
		
	}

	/**
	 * 移动端·物流与售后
	 * @author mozzie.chu
	 * @return
	 */
	@RequestMapping(value = "/query-help-log", method = RequestMethod.GET)
	private final String queryDetailByType2(String type, HttpServletRequest request,
			HttpServletResponse response) {
		List<FHelpModel> model = fHelpService.queryDetailByType("2");
		request.setAttribute("model", model);
		return "market/bLog";
		
	}

	/**
	 * 移动端·其他
	 * @author mozzie.chu
	 * @return
	 */
	@RequestMapping(value = "/query-help-other", method = RequestMethod.GET)
	private final String queryDetailByType3(String type, HttpServletRequest request,
			HttpServletResponse response) {
		List<FHelpModel> model = fHelpService.queryDetailByType("3");
		request.setAttribute("model", model);
		return "market/bOther";
		
	}
	
	/**
	 * 移动端·问答详情
	 * @author mozzie.chu
	 * @return
	 */
	@RequestMapping(value = "/query-help-qa", method = RequestMethod.GET)
	private final String queryDetailByQA(Integer id, HttpServletRequest request,HttpServletResponse response) {
		FHelpModel model = fHelpService.queryHelpById(Integer.valueOf(id));
		request.setAttribute("model", model);
		return "market/bQAndA";
	}
}
