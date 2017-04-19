package com.f2b2c.eco.controller.market.b2c;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.f2b2c.eco.model.bean.BFreightBean;
import com.f2b2c.eco.model.market.BShopInfoModel;
import com.f2b2c.eco.model.market.BUserModel;
import com.f2b2c.eco.service.market.BFreightService;
import com.f2b2c.eco.service.market.BShopInfoService;
import com.f2b2c.eco.status.StorageStatus;

/**
 * 运费管理
 * @author jane.hui
 *
 */
@Controller("BFreightController")
@RequestMapping(value = "/market/freight")
public class BFreightController {
    
    @Autowired
    private BShopInfoService bShopInfoService;
    
    @Autowired
    private BFreightService bFreightService;
    
    /**
     * 返回运费页面
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        ModelAndView mv = new ModelAndView("market/freight/index");
        BUserModel user = (BUserModel) session.getAttribute(StorageStatus.MARKET_USER.name());
        if (user != null) {
            BShopInfoModel bShop = bShopInfoService.findShopInfoByUserId(user.getId());
            List<BFreightBean> list  = bFreightService.queryFreight(bShop.getId());
            mv.addObject("list", list);
        }
        return mv;
    }
    
    /**
     * 修改运费
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public Object modify(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
    	String freightStr = request.getParameter("freightStr");
        BUserModel user = (BUserModel) session.getAttribute(StorageStatus.MARKET_USER.name());
        if (user != null) {
            BShopInfoModel bShop = bShopInfoService.findShopInfoByUserId(user.getId());
            return bFreightService.modify(freightStr, bShop.getId());
            
        }
    	return 0;
    }
}
