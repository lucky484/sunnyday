package com.f2b2c.eco.controller.platform.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.f2b2c.eco.model.common.ApiResultModel;

@Controller(value = "fShopCarApiController")
@RequestMapping(value = "/farm/api/shopcar")
public class FShopCarApiController {

	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryorderbyid", method = RequestMethod.GET)
	public final ApiResultModel<Object> queryOrderById(HttpServletRequest request, HttpServletResponse response, String userId)
	{
		return null;
	}
}
