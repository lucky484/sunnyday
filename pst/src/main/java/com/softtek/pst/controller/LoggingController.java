package com.softtek.pst.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.pst.model.LoggingModel;
import com.softtek.pst.service.LoggingService;
import com.softtek.pst.util.Page;

@Controller
@RequestMapping("/logsManagement/logging")
public class LoggingController {

	@Autowired
	private LoggingService loggingService;

	@RequestMapping(value = "loggingList")
	public String getloggingList(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("url", request.getServletPath());
		return "logging/loggingList";
	}

	@RequestMapping(value = "getLoggings")
	@ResponseBody
	public Page<LoggingModel> getLoggings(Integer start, Integer length, String column, String dir, HttpServletRequest request) {
		Page<LoggingModel> page = loggingService.getLoggings(start, length, column, dir);
		return page;
	}
}
