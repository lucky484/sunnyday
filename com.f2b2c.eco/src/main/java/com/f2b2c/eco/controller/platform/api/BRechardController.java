package com.f2b2c.eco.controller.platform.api;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.f2b2c.eco.model.market.BUserBalanceRecordModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.platform.BRechardOrderModel;
import com.f2b2c.eco.service.platform.FRechardService;
import com.f2b2c.eco.share.pay.alipay.util.AlipayCore;
import com.f2b2c.eco.share.pay.alipay.util.AlipayNotify;
import com.f2b2c.eco.share.pay.wechat.util.BWeixinUtil;
import com.f2b2c.eco.share.pay.wechat.util.WeixinUtil;
import com.f2b2c.eco.share.pay.wechat.util.XMLUtil;
import com.f2b2c.eco.util.PageUtil;
import jodd.util.StringUtil;

/**
 * 充值页面
 * @author jane.hui
 *
 */
@Controller(value="bRechardController")
@RequestMapping(value="/api/bRechard")
public class BRechardController {

    
    private static final Logger logger = LoggerFactory.getLogger(BRechardController.class);
	
	@Autowired
	private FRechardService fRechardService;
	
    /**
     * 根据用户id获取我的帐户余额
     * @param userId:用户id
     * @return 返回我的帐户余额值
     */
    @RequestMapping(value = "/getMyBalance", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<Integer> getMyBalance(HttpServletRequest request){
        DataToCResultModel<Integer> result = new DataToCResultModel<Integer>();
        String userId = request.getParameter("userId");
        if(StringUtil.isNotEmpty(userId)){
            Integer balance = fRechardService.getMyBalance(Integer.valueOf(userId));
            result.setMsg("获取我的帐户余额操作成功");
            result.setStatus(200);
            result.setData(balance);
        } else {
            result.setMsg("获取我的帐户余额操作失败");
            result.setStatus(400);
        }
        return result;
    }
    
    /**
     * 获取我的佣金记录
     * @return
     */
    @RequestMapping(value = "/getMyCommission", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<List<BUserBalanceRecordModel>> getMyCommission(HttpServletRequest request,HttpServletResponse response){
        DataToCResultModel<List<BUserBalanceRecordModel>> result = new DataToCResultModel<List<BUserBalanceRecordModel>>();
        String userId = request.getParameter("userId");
        String page = request.getParameter("page");
        String pageSize = request.getParameter("pageSize");
        Integer intPage = Integer.valueOf(page);
        Integer intPageSize = Integer.valueOf(pageSize);
        Integer start = PageUtil.getStart(intPage, intPageSize);
        Integer length = intPageSize;
        if(StringUtil.isNotEmpty(userId)&&StringUtil.isNotEmpty(page)&&StringUtil.isNotEmpty(pageSize)){
            result.setMsg("获取充值记录信息操作成功");
            result.setStatus(200);
            result.setData(fRechardService.getMyCommissionRechord(userId,start,length));
        } else {
            result.setMsg("请求参数为空");
            result.setStatus(400);
            return result;
        }
        return result;
    }
    
    /**
     * 余额变动记录
     * @return
     */
    @RequestMapping(value = "/balanceRecord", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<List<BUserBalanceRecordModel>> selectBalanceRecordByUserId(HttpServletRequest request,HttpServletResponse response){
        DataToCResultModel<List<BUserBalanceRecordModel>> result = new DataToCResultModel<List<BUserBalanceRecordModel>>();
        String userId = request.getParameter("userId");
        String page = request.getParameter("page");
        String pageSize = request.getParameter("pageSize");
        Integer intPage = Integer.valueOf(page);
        Integer intPageSize = Integer.valueOf(pageSize);
        Integer start = PageUtil.getStart(intPage, intPageSize);
        Integer length = intPageSize;
        if(StringUtil.isNotEmpty(userId)&&StringUtil.isNotEmpty(page)&&StringUtil.isNotEmpty(pageSize)){
            result.setMsg("获取充值记录信息操作成功");
            result.setStatus(200);
            result.setData(fRechardService.balanceRecord(userId,start,length));
        } else {
            result.setMsg("请求参数为空");
            result.setStatus(400);
            return result;
        }
        return result;
    }
    
    /**
     * 充值
     * @param request
     * @return 充值是否成功
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<String> save(HttpServletRequest request,HttpServletResponse response) {
        DataToCResultModel<String> result = new DataToCResultModel<String>();
        String money = request.getParameter("money");
        String payType =request.getParameter("payType");
        String userId = request.getParameter("userId");
        try {
            if(StringUtil.isNotEmpty(money)&&StringUtil.isNotEmpty(userId)){
                return fRechardService.save(request,response,money,payType,Integer.valueOf(userId));
            } else {
                logger.warn("用户id:"+userId+"和充值金额:"+money+"请求参数为空");
                result.setMsg("请求参数为空");
                result.setStatus(400);
                return result; 
            }
        } catch (Exception e){
            logger.error("充值时出现异常，异常信息为:"+e.toString());
            result.setMsg("充值失败");
            result.setStatus(400);
            return result;
        }
    }
    
    /**
     * 充值支付宝异步回调
     * @param request
     * @return 返回合并订单号
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/notify_url", method = RequestMethod.POST)
    @ResponseBody
    public void notifyUrl(HttpServletRequest request,HttpServletResponse response){
        try {
            //获取支付宝POST过来反馈信息
            Map<String,String> params = new HashMap<String,String>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//              valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
                params.put(name, valueStr);
            }
            // 应用id
            String app_id = request.getParameter("app_id");
            // 商户订单号
            String out_trade_no = request.getParameter("out_trade_no");
            // 交易内容  
            String body = request.getParameter("body");
            // 买家帐号   
            String buyer_id = request.getParameter("buyer_id");
            // 卖家帐号    
            String seller_id = request.getParameter("seller_id");
            // 交易金额
            String subject = request.getParameter("subject");
            //支付宝交易号    
            String total_amount = request.getParameter("total_amount");
            // 支付宝交易号    
            String trade_no = request.getParameter("trade_no");
            // 交易状态
            String trade_status = request.getParameter("trade_status");
            // 异步通知ID
            String notify_id=request.getParameter("notify_id");
            // sign
            String sign=request.getParameter("sign");
            logger.info("商户订单号:"+out_trade_no+",签名信息:"+sign);
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(s以上仅供参考)
            //判断接受的post通知中有无notify_id，如果有则是异步通知。
            if(StringUtil.isNotEmpty(notify_id)){
                if(AlipayNotify.verifyResponse(notify_id).equals("true")){
                    if(AlipayNotify.getSignVeryfy(params, sign)){
                    //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
                        if(trade_status.equals("TRADE_FINISHED")){
                            //判断该笔订单是否在商户网站中已经做过处理
                                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                                //如果有做过处理，不执行商户的业务程序
                            //注意：
                            //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                            //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                            fRechardService.notifyUrl(app_id, out_trade_no, body, buyer_id, seller_id, subject, total_amount, trade_no, params.toString());
                            logger.warn("支付宝回调时候验证签名成功:商户订单号："+out_trade_no);
                        } else if (trade_status.equals("TRADE_SUCCESS")){
                            //判断该笔订单是否在商户网站中已经做过处理
                            //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                            //如果有做过处理，不执行商户的业务程序
                            //注意：
                            //付款完成后，支付宝系统发送该交易状态通知
                            //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                            fRechardService.notifyUrl(app_id, out_trade_no, body, buyer_id, seller_id, subject, total_amount, out_trade_no, params.toString());
                            logger.warn("支付宝回调时候验证签名失败:商户订单号："+out_trade_no);
                        }
                        //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
                        response.getWriter().write("success");
                        //请不要修改或删除
                        //调试打印log
                        AlipayCore.logResult("notify_url success!","notify_url");
                    } else {
                        response.getWriter().write("sign fail");
                    }
                } else {
                    response.getWriter().write("response fail");
                }
            } else {
                response.getWriter().write("no notify message");
            }
        } catch (Exception e){
            logger.error("支付宝回调出现异常，异常信息为:"+e.toString());
        }
    } 
    
    /**
     * 微信异步回调
     * @param request
     */
    @SuppressWarnings({"unchecked"})
    @RequestMapping(value = "/wx_notify_url", method = RequestMethod.POST)
    @ResponseBody
    public String wxNotifyUrl(HttpServletRequest request,HttpServletResponse response){
       try {
           BufferedReader reader = request.getReader();
           String line = "";
           StringBuffer inputString = new StringBuffer(); 
           String xmlString = null;
           while ((line = reader.readLine()) != null) {
               inputString.append(line);
           }
           xmlString = inputString.toString();
           request.getReader().close();
           System.out.println("----接收到的数据如下：---" + xmlString);
           logger.info("---微信回调接受到的数据如下：---"+xmlString);
           Map<String, String> map = new HashMap<String, String>();
           String result_code = "";
           map = XMLUtil.doXMLParse(xmlString);
           result_code = map.get("result_code");
           if (WeixinUtil.checkSign(xmlString)) {
               fRechardService.wxNotifyUrl(map);
               return BWeixinUtil.returnXML(result_code);
           } else {
               return BWeixinUtil.returnXML("FAIL");
           }
       } catch(Exception ex){
           logger.error("微信回调出现异常，异常信息为:"+ex.toString());
       }
       return BWeixinUtil.returnXML("FAIL");
    } 
    
    /**
     * 充值记录
     * @param request
     * @return 充值是否成功
     */
    @RequestMapping(value = "/rechardRecord", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<List<BRechardOrderModel>> rechardRecord(HttpServletRequest request,HttpServletResponse response) {
        DataToCResultModel<List<BRechardOrderModel>> result = new DataToCResultModel<List<BRechardOrderModel>>();
        String page = request.getParameter("page");
        String pageSize = request.getParameter("pageSize");
        String userId = request.getParameter("userId");
        if(StringUtil.isNotEmpty(userId)&&StringUtil.isNotEmpty(page)&&StringUtil.isNotEmpty(pageSize)){
            Integer intPage = Integer.valueOf(page);
            Integer intPageSize = Integer.valueOf(pageSize);
            Integer start = PageUtil.getStart(intPage, intPageSize);
            Integer length = intPageSize;
            result.setData(fRechardService.rechardRecord(userId,start,length));
            result.setMsg("获取充值记录信息操作成功");
            result.setStatus(200);
        } else {
            result.setMsg("请求参数为空");
            result.setStatus(400);
            return result;
        }
        return result;
    }
}