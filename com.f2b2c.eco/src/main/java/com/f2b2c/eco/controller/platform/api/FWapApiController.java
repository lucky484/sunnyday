package com.f2b2c.eco.controller.platform.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.f2b2c.eco.model.platform.FSliderWapsModel;
import com.f2b2c.eco.service.platform.FSliderWapService;


/**
 * 商品操作
 * 
 * @author jing.liu
 *
 */
@Controller("FWapApiController")
@RequestMapping(value = "/api/fwap")
public class FWapApiController {
    /**
	 * 日志记录器
	 */
    private Logger logger=LoggerFactory.getLogger(FWapApiController.class);

    @Autowired
	private FSliderWapService fSliderWapService;
    
    
	/**
	 * 根据Wa{id返回商品详情页面
	 */
	@RequestMapping(value = "/query-fwap-details-byid", method = RequestMethod.GET)
	public final String queryDetailsById(Integer id, HttpServletRequest request, HttpServletResponse response) {
		FSliderWapsModel model = fSliderWapService.querySliderWapById(id);
		request.setAttribute("model", model);
		return "market/bWapDetails";
	}

}