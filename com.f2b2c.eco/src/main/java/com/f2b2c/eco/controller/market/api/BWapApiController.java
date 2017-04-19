package com.f2b2c.eco.controller.market.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.f2b2c.eco.model.platform.FSliderWapsModel;
import com.f2b2c.eco.service.platform.BSliderWapService;


/**
 * @author Josen.yang
 * @date Dec 1, 2016 3:00:54 PM
 */

@Controller("BWapApiController")
@RequestMapping(value = "/api/bwap")
public class BWapApiController {
    /**
     * 日志记录器
     */
    private Logger logger=LoggerFactory.getLogger(BWapApiController.class);

    @Autowired
	private BSliderWapService bSliderWapService;
    
    
	/**
     * 根据Wapid返回商品详情页面
     */
	@RequestMapping(value = "/query-bwap-details-byid", method = RequestMethod.GET)
	public final String queryDetailsById(Integer id, HttpServletRequest request, HttpServletResponse response) {
		FSliderWapsModel model;
        try {
            model = bSliderWapService.querySliderWapById(id);
            request.setAttribute("model", model);
        } catch (Exception e) {
            logger.error(e.getMessage());
            request.setAttribute("model", null);
        }
		return "market/bWapDetails";
	}

}