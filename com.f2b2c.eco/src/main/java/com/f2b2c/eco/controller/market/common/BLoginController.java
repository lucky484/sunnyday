package com.f2b2c.eco.controller.market.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.f2b2c.eco.controller.BaseController;
import com.f2b2c.eco.model.market.BUserModel;
import com.f2b2c.eco.service.market.BUserService;
import com.f2b2c.eco.status.StorageStatus;
import com.f2b2c.eco.util.Pbkdf2Encryption;
import com.f2b2c.eco.util.UrlUtil;

/**
 * 处理商户后台登录
 * 
 * @author color.wu
 *
 */
@Controller(value="marketLoginController")
@RequestMapping(value="/market")
public class BLoginController extends BaseController {

	@Autowired
	private BUserService bUserService;
	
	/**
	 * 显示登录界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"","/"},method=RequestMethod.GET)
	public final String index(HttpServletRequest request, HttpServletResponse response){
		return "market/login";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public final String login(HttpServletRequest request, HttpServletResponse response,@Valid BUserModel user,BindingResult result,RedirectAttributes attributes){
		// 如果后台字段校验有问题，需要给出提示
		if(result.hasErrors()){
			attributes.addFlashAttribute(StorageStatus.REQ_TIP.name(), result.getFieldError().getDefaultMessage());
			return "redirect:/market";
		}
		
		boolean isExists = bUserService.checkIfExist(StringUtils.trimToEmpty(user.getAccountName()));
		if(!isExists){
			attributes.addFlashAttribute(StorageStatus.REQ_TIP.name(), "用户名不存在！");
			return "redirect:/market";
		}
		BUserModel bUserModel = bUserService.findOneByAccount(user.getAccountName());
		if(null == bUserModel){
			attributes.addFlashAttribute(StorageStatus.REQ_TIP.name(), "用户名或密码错误！");
			return "redirect:/market";
		}
		if (!Pbkdf2Encryption.checkPassword(user.getPassword(), bUserModel.getPassword())) {
			attributes.addFlashAttribute(StorageStatus.REQ_TIP.name(), "用户名或密码错误！");
			return "redirect:/market";
		}
		if(bUserModel.getIsActive()>0){
			attributes.addFlashAttribute(StorageStatus.REQ_TIP.name(), "用户名未激活！");
			return "redirect:/farm";
		}
		// 将用户信息存入session，权限还没有存入，稍后处理
		request.getSession().setAttribute(StorageStatus.MARKET_USER.name(), bUserModel);
		String url = UrlUtil.getUrlPathWithContext(request) + "/api/user/regist"
				+ (bUserModel.getId() == null ? "" : "?userId=" + bUserModel.getId() + "&userType=1");
		request.getSession().setAttribute("qrcodeUrl", url);
		request.getSession().setAttribute("userId", bUserModel.getId().toString());
	    return "redirect:/market/admin";
	}
	
	/**
	 * 注销
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public final String logout(HttpServletRequest request, HttpServletResponse response,HttpSession session){
		/*
		 * 法一 Enumeration<String> enumeration=session.getAttributeNames(); while
		 * (enumeration.hasMoreElements()) { String attrName = (String)
		 * enumeration.nextElement(); session.removeAttribute(attrName); }
		 */
		
		// 法二
		session.invalidate();
		return "redirect:/market";
	}
	
}
