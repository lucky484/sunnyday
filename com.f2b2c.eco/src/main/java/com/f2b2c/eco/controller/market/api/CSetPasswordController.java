package com.f2b2c.eco.controller.market.api;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.f2b2c.eco.model.common.ApiResultModel;
import com.f2b2c.eco.service.market.AccountService;
import com.f2b2c.eco.service.market.CSetPasswordService;
import jodd.util.StringUtil;

/**
 * 设置密码功能
 * @author jane.hui
 *
 */
@Controller("cSetPasswordController")
@RequestMapping(value = "/api/password")
public class CSetPasswordController {

    @Autowired
    private CSetPasswordService cSetPasswordService;
    
    @Autowired
    private AccountService accountService;
    
    /**
     * 支付密码是否存在
     * @param request
     * @return 返回是否存在
     */ 
    @RequestMapping(value = "/isExists",method = RequestMethod.POST)
    @ResponseBody
    public ApiResultModel<String> isExists(HttpServletRequest request){
        ApiResultModel<String> result = new ApiResultModel<String>();
        String userId = request.getParameter("userId");
        if(StringUtil.isNotEmpty(userId)){
            Integer isExists = accountService.isPayPasswordExists(Integer.valueOf(userId));
            if(isExists==0){
                result.setMsg("您还没有设置支付密码，请先设置您的支付密码");
                result.setStatus(500);
                return result;
            }
        } else {
            result.setMsg("请求参数为空");
            result.setStatus(400);
            return result;
        }
        result.setMsg("用户支付密码存在");
        result.setStatus(200);
        return result;
    } 
    
    /**
     * 设置密码功能
     * @param request:密码和确认密码
     * @return 返回设置结果
     */
    @RequestMapping(value ="/set",method = RequestMethod.POST)
    @ResponseBody
    public ApiResultModel<String> setPassword(HttpServletRequest request){
        ApiResultModel<String> result = new ApiResultModel<String>();
        String password = request.getParameter("password");
        String userId = request.getParameter("userId");
        if(StringUtil.isNotBlank(password)&&StringUtil.isNotBlank(userId)){
            return cSetPasswordService.setPassword(Integer.valueOf(userId), password);
        } else {
            result.setMsg("请求参数为空");
            result.setStatus(400);
            return result;
        }
    }
    
    /**
     * 输入密码校验功能
     * @param request:密码
     * @return 返回设置支付结果
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public ApiResultModel<String> savePassword(HttpServletRequest request){
        ApiResultModel<String> result = new ApiResultModel<String>();
        String password = request.getParameter("password");
        String userId = request.getParameter("userId");
        String mergeOrderPrice = request.getParameter("mergeOrderPrice");
        if(StringUtil.isNotBlank(password)&&StringUtil.isNotBlank(userId)){
             Integer intUserId = Integer.valueOf(userId);
             result = cSetPasswordService.savePassword(intUserId, password,mergeOrderPrice);
             if(result.getStatus()==200&&StringUtil.isNotEmpty(mergeOrderPrice)){
                 String[]arr = mergeOrderPrice.split(",");
                return accountService.walletPay(intUserId, Integer.valueOf(arr[1]), arr[0]);
             } 
            return result;
        } else {
            result.setMsg("请求参数为空");
            result.setStatus(400);
            return result;
        }
    }
    
    /**
     * 修改密码
     * @param request:请求参数
     * @return 返回修改结果
     */
    @RequestMapping(value = "/modify",method = RequestMethod.POST)
    @ResponseBody
    public ApiResultModel<String> modifyPassword(HttpServletRequest request){
        ApiResultModel<String> result = new ApiResultModel<String>();
        String oldPassword = request.getParameter("oldPassword");
        String password = request.getParameter("password");
        String userId = request.getParameter("userId");
        if(StringUtil.isNotEmpty(password)&&StringUtil.isNotEmpty(userId)&&StringUtil.isNotEmpty(oldPassword)){
            return cSetPasswordService.modifyPassword(Integer.valueOf(userId), oldPassword, password);
        } else {
            result.setMsg("请求参数为空");
            result.setStatus(400);
            return result;
        }
    }
}