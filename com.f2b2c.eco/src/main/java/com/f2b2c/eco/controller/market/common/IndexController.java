package com.f2b2c.eco.controller.market.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.f2b2c.eco.controller.BaseController;
import com.f2b2c.eco.service.market.BSalesOrderService;

/**
 * 用户完成登录之后首页
 * 
 * @author color.wu
 *
 */
@Controller(value="bIndexController")
@RequestMapping(value="/market/admin")
public class IndexController extends BaseController {
	
	@Autowired
	private BSalesOrderService bSalesOrderService;
	
	/**
	 * 显示登录界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"","/"},method=RequestMethod.GET)
	public final String index(HttpServletRequest request, HttpServletResponse response,HttpSession session){
		String userId = (String) session.getAttribute("userId");
		if(userId != null && !userId.equals("")){
			//将未处理的订单总数绑到前台
			int count = bSalesOrderService.queryUnDealOrderByUserId(Integer.valueOf(userId));
			request.setAttribute("count", count);
			request.getSession().setAttribute("userId", "");
		}
		return "market/bgoodslist";
	}
	
}
