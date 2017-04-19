package com.f2b2c.eco.controller.platform.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.f2b2c.eco.model.market.BNoticeModel;
import com.f2b2c.eco.service.market.BNoticeService;


/**
 * 商品操作
 * 
 * @author jing.liu
 *
 */
@Controller("BNoticeApiController")
@RequestMapping(value = "/api/bnotice")
public class BNoticeController {
    /**
	 * 日志记录器
	 */

	@Autowired
	private BNoticeService bNoticeService;

	/**
	 * 发布b端公告
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public final String bnoticeDetail(Integer id, HttpServletRequest request,
			HttpServletResponse response) {
		BNoticeModel bNoticeModel = bNoticeService.bnoticeDetail(id);
		request.setAttribute("model", bNoticeModel);
		return "bnotice/detail";
	}

}