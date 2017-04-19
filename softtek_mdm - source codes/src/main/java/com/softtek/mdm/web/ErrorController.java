package com.softtek.mdm.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping(value="/error")
public class ErrorController extends BaseController{

	@RequestMapping(method=RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			RedirectAttributes attrs, Model model) {
	String type=request.getParameter("type");
		switch (type) {
		case "401":
			model.addAttribute("error_msg", "禁止未授权访问！");
			break;
		case "404":
			model.addAttribute("error_msg", "404:对不起，您所访问的页面不存在.");
			break;
		default:
			break;
		}
		return "error/index";
	
	}
}
