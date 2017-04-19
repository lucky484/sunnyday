/**
* @ClassName: HomeController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Jacob Shen
* @date Apr 7, 2016 10:26:11 AM
*/
package com.softtek.pst.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
	@RequestMapping("index")
	public String index(HttpServletRequest request) {
		request.setAttribute("url", request.getServletPath());
		return "index";
	}

	@RequestMapping("error/404")
	public String fourZeroFour() {
		return "error/404";
	}

	@RequestMapping("error/401")
	public String fourZeroOne() {
		return "error/401";
	}
	
	@RequestMapping("error/500")
	public String FiveZeroZero() {
		return "error/500";
	}

}
