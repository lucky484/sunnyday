package com.f2b2c.eco.controller.platform.api;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.controller.BaseController;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.service.platform.FUserService;

@Controller("/farmUserApiController")
@RequestMapping("/api/farm/user")
public class FUserController extends BaseController {

	/**
	 * 日志工具类
	 */
	private static final Logger logger = LoggerFactory.getLogger(FUserController.class);

	@Autowired
	private FUserService fUserService;

	/**
	 * 根据商铺id查询担保人信息
	 * 
	 * @param shopId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/get-create-user-info-by-shopid", method = RequestMethod.POST)
	@ResponseBody
	public DataToCResultModel<Object> getCreateUserInfoByShopId(Integer shopId, HttpServletRequest request) {
		return new DataToCResultModel<Object>(200, "success", fUserService.getCreateUserInfoByShopId(shopId));
	}

}
