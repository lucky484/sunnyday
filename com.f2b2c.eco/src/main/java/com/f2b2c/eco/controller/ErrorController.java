package com.f2b2c.eco.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller(value = "errorController")
@RequestMapping(value = "/error")
public class ErrorController {
	
	@RequestMapping(value={"","/"})
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "error";
	}
}
