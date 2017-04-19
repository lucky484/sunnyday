package com.f2b2c.eco.controller.platform.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.model.common.ApiResultModel;
import com.f2b2c.eco.service.market.CCommissionService;

import jodd.util.StringUtil;

/**
 * 佣金功能
 * @author jane.hui
 *
 */
@Controller("bCommissionController")
@RequestMapping(value="api/bcommission")
public class BCommissionController {

	@Autowired
	private CCommissionService cCommissionService;
	
    /**
     * 转出功能
     * @param request
     * @return 返回转账操作结果
     */
    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    @ResponseBody
    public ApiResultModel<String> transfer(HttpServletRequest request){
        ApiResultModel<String> result = new ApiResultModel<String>();
        String userId = request.getParameter("userId");
        String money = request.getParameter("money");
        if(StringUtil.isNotEmpty(userId)&&StringUtil.isNotEmpty(money)){
            return cCommissionService.transfer(Integer.valueOf(userId), Integer.valueOf(money));
        } else {
            result.setMsg("请求参数不能为空");
            result.setStatus(400);
            return result;
        }
    }
}
