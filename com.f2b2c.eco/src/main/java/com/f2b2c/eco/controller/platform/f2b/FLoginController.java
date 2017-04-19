package com.f2b2c.eco.controller.platform.f2b;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.f2b2c.eco.annotation.LogAnnotation;
import com.f2b2c.eco.model.platform.FMenuModel;
import com.f2b2c.eco.model.platform.FUserModel;
import com.f2b2c.eco.service.platform.FMenuService;
import com.f2b2c.eco.service.platform.FRoleService;
import com.f2b2c.eco.service.platform.FUserService;
import com.f2b2c.eco.status.StorageStatus;
import com.f2b2c.eco.util.Pbkdf2Encryption;

/**
 * 处理Farm端登录事宜
 * @author color.wu
 *
 */
@Controller(value = "fLoginController")
@RequestMapping(value = "/farm")
public class FLoginController {

	@Autowired 
	private FMenuService fMenuService;
	@Autowired
	private FUserService fUserService;
	@Autowired
	private FRoleService fRoleService;
	
	/**
	 * 日志记录器
	 */
	private final static Logger logger=LoggerFactory.getLogger(FLoginController.class);

	/**
	 * 显示登录界面
	 * 
	 * @param request
	 * @param response
	 * @return 返回登录视图名称
	 */
	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public final String index(HttpServletRequest request, HttpServletResponse response) {
		return "platform/login";
	}
    
	@RequestMapping(value = "/401", method = RequestMethod.GET)
	public String errorJsp(HttpServletRequest request){
		return "error/401";
	}
	
	/**
	 * 登录验证
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response,@Valid FUserModel user, final BindingResult result,final RedirectAttributes attr) {
		
		//检测用户帐号是否存在
		boolean isExists=fUserService.checkIsExist(StringUtils.trimToEmpty(user.getAccountName()));
		if(!isExists){
			attr.addFlashAttribute(StorageStatus.REQ_TIP.name(),"用户不存在！");
			return "redirect:/farm";
		}
		FUserModel fUser = fUserService.findOneByAccount(user.getAccountName().trim());
		if(null==fUser){
			attr.addFlashAttribute(StorageStatus.REQ_TIP.name(),"用户名或密码错误！");
			return "redirect:/farm";
		}
		if(!Pbkdf2Encryption.checkPassword(user.getPassword(), fUser.getPassword())){
			attr.addFlashAttribute(StorageStatus.REQ_TIP.name(),"用户名或密码错误！");
			return "redirect:/farm";
		}
		if(fUser.getIsActive()!=null&&fUser.getIsActive()>0){
			attr.addFlashAttribute(StorageStatus.REQ_TIP.name(),"用户名未激活！");
			return "redirect:/farm";
		}
		//将用户信息存入session，权限还没有存入，稍后处理
		request.getSession().setAttribute(StorageStatus.USER_INSESSION.name(), fUser);
		
		List<String> list = fRoleService.queryAllUrlByUserId(fUser.getId());
		
		request.getSession().setAttribute("authenUrl", list);
		
		List<String> urlList = fRoleService.queryAllUrl();
		
		request.getSession().setAttribute("urlList", urlList);
		/*List<FMenuModel> list=fUserService.getAuthcationMenus(fUser.getId().toString());
		if(CollectionUtils.isEmpty(list)){
			attr.addFlashAttribute(StorageStatus.REQ_TIP.name(),"未知角色，不能登录！");
			return "redirect:/farm";
		}*/
		
//		FRoleModel frole=fRoleService.findOneByUserId(fUser.getId().toString());
//		if(null==frole){
//			attr.addFlashAttribute(StorageStatus.REQ_TIP.name(),"未知角色，不能登录！");
//			return "redirect:/farm";
//		}
//		request.getSession().setAttribute(StorageStatus.AUTHCATION_ROLE.name(), frole);
		return "redirect:/farm/admin";
	}

	/*
	 * 显示登录界面
	 * 
	 * @param request
	 * 
	 * @param response
	 * 
	 * @return 返回登录视图名称
	 */
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public final String loginResult(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<FMenuModel> menus = fMenuService.queryFMenuModelList();
		model.addAttribute("menus", menus);
		return "platform/layout/classic";
	}
	
	/**
	 * 注销登录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public final String logout(HttpServletRequest request, HttpServletResponse response,HttpSession session){
		/*法一
		Enumeration<String> enumeration=session.getAttributeNames();
		while (enumeration.hasMoreElements()) {
			String attrName = (String) enumeration.nextElement();
			session.removeAttribute(attrName);
		}*/
		
		//法二
		session.invalidate();
		return "redirect:/farm";
	}
	
	@RequestMapping(value="/404",method=RequestMethod.GET)
	public String error(HttpServletRequest request, HttpServletResponse response) {
		return "error/404";
	}
}
