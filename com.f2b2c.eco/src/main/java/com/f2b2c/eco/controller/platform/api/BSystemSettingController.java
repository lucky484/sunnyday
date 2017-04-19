package com.f2b2c.eco.controller.platform.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.f2b2c.eco.model.platform.FHelpModel;

@Controller("BSystemSettingController")
@RequestMapping(value = "/api/bsystem")
public class BSystemSettingController {

	/*
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(BSystemSettingController.class);
	
	@RequestMapping(value = "/query-system-about", method = RequestMethod.GET)
	public final String querySystemAbout(HttpServletRequest request, HttpServletResponse response) {
		
		return "market/bAbout";
		
	}
	
	@RequestMapping(value = "/query-system-aboutus", method = RequestMethod.GET)
	public final String querySystemAboutUs(HttpServletRequest request, HttpServletResponse response) {
		
		return "market/bAboutUs";
		
	}
}
