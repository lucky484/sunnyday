
package com.f2b2c.eco.controller.market.api;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;
import com.f2b2c.eco.model.bean.BShopCarBean;
import com.f2b2c.eco.model.bean.CLogisticBean;
import com.f2b2c.eco.model.common.ApiResultModel;
import com.f2b2c.eco.model.market.BGoodsToPurchaseOrderModel;
import com.f2b2c.eco.model.market.BSalesOrderModel;
import com.f2b2c.eco.model.market.BShopToOrderModel;
import com.f2b2c.eco.model.market.BtoCBSalesOrderModel;
import com.f2b2c.eco.model.market.CDeliveryAddressModel;
import com.f2b2c.eco.model.market.CShopCarModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.market.InsertCDeliveryAddressModel;
import com.f2b2c.eco.service.market.AccountService;
import com.f2b2c.eco.service.market.BOrderService;
import com.f2b2c.eco.share.pay.alipay.util.AlipayCore;
import com.f2b2c.eco.share.pay.alipay.util.AlipayNotify;
import com.f2b2c.eco.share.pay.wechat.util.WeixinUtil;
import com.f2b2c.eco.share.pay.wechat.util.XMLUtil;
import com.f2b2c.eco.util.ConstantUtil.PayType;
import com.f2b2c.eco.websocket.SystemWebSocketHandler;
import jodd.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * B2C模块订单管理
 * 
 * @author jane.hui
 *
 */
@Controller(value="businessOrderController")
@RequestMapping(value="/api/business/order")
public class BOrderController {

    /**
     * 日志记录器
     */
    private Logger logger=LoggerFactory.getLogger(BOrderController.class);
    
    @Autowired
    private BOrderService orderService;
    
    @Autowired
    private BOrderService bOrderService;
    
    @Autowired
    private AccountService accountService;
    
    @Bean
    public SystemWebSocketHandler systemWebSocketHandler() {
        return new SystemWebSocketHandler();
    }
    
    /**
     * 
     * @Description 加入购物车
     * @author Josen.yang
     * @param bGoodsNo
     *            商品编号
     * @param userId
     *            用户id
     * @param number
     *            数量
     */
    @RequestMapping(value = "/insertbgoods-shop-car", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<Object> insertBGoodsShopCar(String bGoodsNo, Integer userId, Integer number,
            HttpServletRequest request) {
        try {
            if (StringUtils.isNotBlank(bGoodsNo) && userId != null && number != null) {
                DataToCResultModel<Object> retult = orderService.insertBGoodsShopCar(bGoodsNo, number, userId);
                return retult;
            } else {
                return new DataToCResultModel<Object>(400, "请求参数不能为空", null);
            }
        } catch (Exception e) {
            logger.error("加入购物车时出现异常，异常信息为:" + e.toString());
            return new DataToCResultModel<Object>(400, "加入购物车失败", null);
        }
    }
    
    /**
     * 获取购物车中所有商品
     * 
     * @return 返回购物车中所有商品
     */
    @RequestMapping(value = "/get-shop-cart", method = RequestMethod.POST)
    @ResponseBody
    public ApiResultModel<BShopCarBean> getShopCart(HttpServletRequest request){
        ApiResultModel<BShopCarBean> result = new ApiResultModel<BShopCarBean>();
        try {
            String userId = request.getParameter("userId");
            if(StringUtil.isEmpty(userId)){
                result.setMsg("用户id不能为空");
                result.setStatus(401);
                return result;
            }
            List<CShopCarModel> list = orderService.getShopCarByUserId(Integer.valueOf(userId),null);
            List<CShopCarModel> newList= new ArrayList<CShopCarModel>();
            for (CShopCarModel cShopCarModel : list) {
                newList.add(cShopCarModel);
            }
            result.setStatus(200);
            result.setData(orderService.parseShopCartToJson(newList));
            result.setMsg("获取购物车商品数据成功");
            return result;
        } catch (Exception e){
            result.setStatus(400);
            result.setMsg("获取购物车商品数据失败");
            return result;
        }
    }

    /**
     * 删除购物车中的商品
     * 
     * @param request
     * @return 返回删除购物车是否成功状态(200:成功,401:失败)
     */
    @RequestMapping(value = "/delete-shop-cart", method = RequestMethod.POST)
    @ResponseBody
    public ApiResultModel<String> deleteShopCart(HttpServletRequest request){
        ApiResultModel<String> result = new ApiResultModel<String>();
        String id = request.getParameter("id");
        if(StringUtil.isNotEmpty(id)){
            try {
                int size = orderService.deleteShopCartById(id);
                if(size==0){
                    result.setStatus(400);
                    result.setMsg("删除购物车商品失败");
                    return result;
                }
            } catch(Exception e){
                logger.error("删除购物车商品时出现异常，异常信息为:" + e.toString());
                result.setStatus(400);
                result.setMsg("删除购物车商品失败");
                return result;
            }
        } else {
            result.setStatus(400);
            result.setMsg("删除购物车商品失败");
            return result;
        }
        result.setStatus(200);
        result.setMsg("删除购物车商品成功");
        return result;
    }
    
    /**
     * 删除购物车商品成功 修改购物车中的商品
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/modify-shop-cart", method = RequestMethod.POST)
    @ResponseBody
    public ApiResultModel<Integer> modifyShopCart(HttpServletRequest request){
        ApiResultModel<Integer> result = new ApiResultModel<Integer>();
        String strQuantity = request.getParameter("strQuantity");
        if(StringUtil.isNotEmpty(strQuantity)){
            try { 
               return orderService.modifyShopCart(strQuantity);
            } catch(Exception e){
                logger.error("修改购物车商品时出现异常，异常信息为:" + e.toString());
                result.setStatus(400);
                result.setMsg("修改购物车商品失败");
                return result;
            }
        } else {
            result.setStatus(400);
            result.setMsg("请求参数为空");
            return result;
        }
    }
    
    /**
     * 结算购物车的商品
     * 
     * @param request
     * @return 返回订单页面中的商品和收货地址json字符串
     */
    @RequestMapping(value = "/settlement-shop-cart", method = RequestMethod.POST)
    @ResponseBody
    public ApiResultModel<Map<String, Object>> settlementShopCart(HttpServletRequest request){
        String userId = request.getParameter("userId");
        ApiResultModel<Map<String, Object>> result = new ApiResultModel<Map<String, Object>>();
        // 购物车主键
        String shopCartStr = request.getParameter("shopCartStr");
        logger.info("结算购物商品信息为:" + shopCartStr);
        String addressId = request.getParameter("addressId");
        if(StringUtil.isNotEmpty(shopCartStr)&&StringUtil.isNotEmpty(userId)){
            try {
                return orderService.checkShopCart(shopCartStr,Integer.valueOf(userId),addressId);
            } catch (Exception e){
                logger.error("结算购物车商品时出现异常，异常信息为:" + e.toString());
                result.setStatus(400);
                result.setMsg("结算购物车商品操作失败");
            }
         } else {
            result.setStatus(401);
            result.setMsg("用户id和结算商品不能为空");
         }
        return result;
    }
    
    /**
     * 将订单页面中的收货转换成json
     * 
     * @param list
     * @return 返回订单页面中收货地址转换成json
     */
    public String parseDeliveryAddressToJson(List<CDeliveryAddressModel> deliveryAddressList){
         JSONArray addressArray = new JSONArray();
        for (CDeliveryAddressModel deliverAddress : deliveryAddressList) {
            JSONObject addressObject = new JSONObject();
            addressObject.put("id", deliverAddress.getId());
            addressObject.put("areaName", "");
            addressObject.put("address", deliverAddress.getAddress());
            addressObject.put("mobile", deliverAddress.getMobile());
            addressObject.put("telephone", deliverAddress.getTelephone());
            addressObject.put("email", deliverAddress.getEmail());
            addressObject.put("isDefault", deliverAddress.getIsDefault());
            addressArray.add(addressObject);
        }
        return addressArray.toString();
    }
    
    /**
     * 立即购买
     * 
     * @param request
     * @return 返回立即购买跳转订单需要加载的商品和收货地址json字符串
     */
    @RequestMapping(value = "/purchase", method = RequestMethod.POST)
    @ResponseBody
    public ApiResultModel<Map<String, Object>> purchase(HttpServletRequest request){
        ApiResultModel<Map<String, Object>> result = new ApiResultModel<Map<String, Object>>();
        String userId = request.getParameter("userId");
        String id = request.getParameter("id");
        String quantity = request.getParameter("quantity");
        String addressId = request.getParameter("addressId");
        if(StringUtil.isEmpty(userId)&&StringUtil.isEmpty(id)){
            result.setStatus(401);
            result.setMsg("用户id不能为空");
        } else if(StringUtil.isEmpty(quantity)){
            result.setStatus(401);
            result.setMsg("购买数量不能为0");
        } else {
            try {
                return orderService.purchase(Integer.valueOf(id), Integer.valueOf(userId),Integer.valueOf(quantity),addressId);
            } catch (Exception e){
                logger.error("立即购买时出现异常，异常信息为:" + e.toString());
                result.setStatus(400);
                result.setMsg("立即购买操作失败");
            }
        } 
        return result;
    }
    
    /**
     * 获取收货地址
     * 
     * @param request
     * @return 返回收货地址列表
     */
    @RequestMapping(value = "/getDeliveryAddress", method = RequestMethod.POST)
    @ResponseBody
    public ApiResultModel<String> getDeliveryAddress(HttpServletRequest request){
        ApiResultModel<String> result = new ApiResultModel<String>();
        String userId = request.getParameter("userId");
        if(StringUtil.isEmpty(userId)){
            result.setStatus(401);
            result.setMsg("用户id不能为空");
        } else {
           return orderService.getDeliveryAddress(Integer.valueOf(userId));
        } 
        return result;
    }
    
    /**
     * 插入收货地址
     * 
     * @param request
     * @return 插入收货地址表，并返回新的地址
     * 
     */
    @RequestMapping(value = "/insertDeliveryAddress", method = RequestMethod.POST)
    @ResponseBody
    public ApiResultModel<String> insertDeliveryAddress(HttpServletRequest request){
        ApiResultModel<String> result = new ApiResultModel<String>();
        // 获取要插入或者修改的收货地址
        String strAddress = request.getParameter("strAddress");
        String userId = request.getParameter("userId");
        if(StringUtil.isEmpty(strAddress)){
            result.setStatus(401);
            result.setMsg("收货地址不能为空");
        } else if(StringUtil.isEmpty(userId)){
            result.setStatus(401);
            result.setMsg("用户id不能为空");
        } else {
            try {
                JSONObject object = JSONObject.fromObject(strAddress);
                Object addressObj = JSONObject.toBean(object, InsertCDeliveryAddressModel.class);
                if(addressObj!=null){
                    return orderService.insertDeliveryAddress((InsertCDeliveryAddressModel)addressObj,Integer.valueOf(userId));
                } else {
                    result.setStatus(400);
                    result.setMsg("立即购买操作失败");
                }
            } catch (Exception e){
                logger.error("插入收货地址出现异常，异常信息为:" + e.toString());
                result.setStatus(400);
                result.setMsg("立即购买操作失败");
            }
        } 
        return result;
    }
    
    /**
     * 提交订单
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/submit_order", method = RequestMethod.POST)
    @ResponseBody
    public ApiResultModel<String> submitOrder(HttpServletRequest request,HttpServletResponse response){
        ApiResultModel<String> result = new ApiResultModel<String>();
        String strOrderInfo = request.getParameter("strOrderInfo");
        String userId = request.getParameter("userId");
        String strShopInfo = request.getParameter("strShopInfo");
        String tag = request.getParameter("tag");
        String payType = request.getParameter("payType");
        try {
            if(StringUtil.isEmpty(userId)){
                result.setStatus(401);
                result.setMsg("用户未授权");
            } else if(StringUtil.isEmpty(payType)){
                result.setStatus(400);
                result.setMsg("请选择支付方式");
            } else if(StringUtil.isEmpty(strOrderInfo)||StringUtil.isEmpty(strShopInfo)||StringUtil.isEmpty(tag)){
                result.setStatus(400);
                result.setMsg("提交订单操作失败");
            } else {
                if(PayType.WALLET_PAY.toString().equals(payType)){
                	// 判断
                    Integer isExist = accountService.isPayPasswordExists(Integer.valueOf(userId));
                    if(isExist==0){
                        result.setMsg("您还没有设置支付密码，请先设置您的支付密码");
                        result.setStatus(500);
                        return result;
                    }
                }
                // 将json转换成订单对象
                JSONObject object = JSONObject.fromObject(strOrderInfo);
                Object addressObj = JSONObject.toBean(object, BtoCBSalesOrderModel.class);
                if("1".equals(tag)){
                    // 将json转换成订单详情
                    JSONArray jsonArray = JSONArray.fromObject(strShopInfo);
                    @SuppressWarnings({ "unchecked", "deprecation" })
                    List<BShopToOrderModel> orderDetailList = JSONArray.toList(jsonArray,BShopToOrderModel.class);
                    if(addressObj!=null&&CollectionUtils.isNotEmpty(orderDetailList)){    
                        result =  orderService.submitOrder(payType,request,response,(BtoCBSalesOrderModel)addressObj, orderDetailList, Integer.valueOf(userId));
                        if(result.getStatus()==200||result.getStatus().toString().equals(200)){
                            if(payType.equals("2")){
                                List<BSalesOrderModel> list = bOrderService.selectSalesOrderByMergeNo(result.getData());
                                for(BSalesOrderModel bSalesOrder : list){
                                       if(bSalesOrder.getCatalog().equals("0")){
                                        systemWebSocketHandler().sendMessageToUser(bSalesOrder.getbUserId().toString(),
                                                new TextMessage("您有新的订单未接,请前往接单"));
                                        }
                                   }
                            }
                            return result;
                        }
                    }
                } else {
                    // 将json转换成商品对象
                    JSONObject object2 = JSONObject.fromObject(strShopInfo);
                    Object shopObj = JSONObject.toBean(object2,BGoodsToPurchaseOrderModel.class);
                    result = orderService.submitOneOrder(payType,request,response,(BtoCBSalesOrderModel)addressObj, (BGoodsToPurchaseOrderModel)shopObj, Integer.valueOf(userId));
                    if(result.getStatus()==200||result.getStatus().toString().equals(200)){
                    if(payType.equals("2")){
                        
                            List<BSalesOrderModel> list = bOrderService.selectSalesOrderByMergeNo(result.getData());
                            for(BSalesOrderModel bSalesOrder : list){
                                if(bSalesOrder.getCatalog().equals("0")){
                                    systemWebSocketHandler().sendMessageToUser(bSalesOrder.getbUserId().toString(),
                                            new TextMessage("您有新的订单未接,请前往接单"));
                                }
                            }
                        }
                        return result;
                    }
                }
            }
        } catch (Exception e){
            logger.error("提交订单时出现异常，异常信息为:" + e.toString());
            result.setStatus(400);
            result.setMsg("提交订单操作失败!");
        }
        return result;
    }
    
    /**
     * 支付宝异步回调
     * 
     * @param request
     * @return 返回合并订单号
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/notify_url", method = RequestMethod.POST)
    @ResponseBody
    public void notifyUrl(HttpServletRequest request,HttpServletResponse response){
        try {
            // 获取支付宝POST过来反馈信息
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
                // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
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
            // 支付宝交易号
            String total_amount = request.getParameter("total_amount");
            // 支付宝交易号
            String trade_no = request.getParameter("trade_no");
            // 交易状态
            String trade_status = request.getParameter("trade_status");
            // 异步通知ID
            String notify_id=request.getParameter("notify_id");
            // sign
            String sign=request.getParameter("sign");
            logger.info("支付宝回调信息:out_trade_no:" + out_trade_no + ",body:" + body + "buyer_id:" + buyer_id
                    + ",seller_id:" + seller_id + ",subject:" + subject + ",total_amount:" + total_amount);
            // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(s以上仅供参考)
            // 判断接受的post通知中有无notify_id，如果有则是异步通知。
            if(StringUtil.isNotEmpty(notify_id)){
                if(AlipayNotify.verifyResponse(notify_id).equals("true")){
                    if(AlipayNotify.getSignVeryfy(params, sign)){
                        // ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
                        List<BSalesOrderModel> list = new ArrayList<BSalesOrderModel>();
                        if(trade_status.equals("TRADE_FINISHED")){
                            // 判断该笔订单是否在商户网站中已经做过处理
                            // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                            // 如果有做过处理，不执行商户的业务程序
                            // 注意：
                            // 退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                            // 请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                            list = orderService.notifyUrl(app_id, out_trade_no, body, buyer_id, seller_id, subject, total_amount, trade_no, params.toString());
                            logger.info("商户订单号out_trade_no=" + out_trade_no + "验证签名成功");
                        } else if (trade_status.equals("TRADE_SUCCESS")){
                            // 判断该笔订单是否在商户网站中已经做过处理
                            // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                            // 如果有做过处理，不执行商户的业务程序
                            // 注意：
                            // 付款完成后，支付宝系统发送该交易状态通知
                            // 请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                            list = orderService.notifyUrl(app_id, out_trade_no, body, buyer_id, seller_id, subject, total_amount, out_trade_no, params.toString());
                            logger.info("商户订单号out_trade_no=" + out_trade_no + "验证签名失败");
                        }
                        if(list != null && list.size() > 0){
                            for(BSalesOrderModel bSalesOrder : list){
                                if(bSalesOrder.getCatalog().equals("0")){
                                    systemWebSocketHandler().sendMessageToUser(bSalesOrder.getbUserId().toString(),
                                            new TextMessage("您有新的订单未接,请前往接单"));
                                }
                            }
                        }
                        // ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
                        response.getWriter().write("success");
                        // 请不要修改或删除
                        // 调试打印log
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
            logger.error("支付宝回调时出现异常，异常信息为:" + e.toString());
        }
    } 
    
    /**
     * 微信异步回调
     * 
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
            logger.info("---微信回调时候接受到的数据如下:---" + xmlString);
           Map<String, String> map = new HashMap<String, String>();
           String result_code = "";
           map = XMLUtil.doXMLParse(xmlString);
           result_code = map.get("result_code");
           if (WeixinUtil.checkSign(xmlString)) {
               List<BSalesOrderModel> list = orderService.wxNotifyUrl(map);
               if(list != null && list.size() > 0){
                   for(BSalesOrderModel bSalesOrder : list){
                       if(bSalesOrder.getCatalog().equals("0")){
                            systemWebSocketHandler().sendMessageToUser(bSalesOrder.getbUserId().toString(),
                                    new TextMessage("您有新的订单未接,请前往接单"));
                       }
                   }
               }
               return WeixinUtil.returnXML(result_code);
           } else {
               return WeixinUtil.returnXML("FAIL");
           }
       } catch(Exception ex){
            logger.error("微信回调时出现异常，异常信息为:" + ex.toString());
       }
       return WeixinUtil.returnXML("FAIL");
    } 
    
    /**
     * 查询购物车商品的总数量和种类
     * 
     * @param request
     * @return 返回购物车总数量
     */
    @RequestMapping(value = "/getCarCount",method = RequestMethod.GET)
    @ResponseBody
    public DataToCResultModel<Map<String,Object>> getCarCount(HttpServletRequest request){
        DataToCResultModel<Map<String,Object>> dataToCResult = new DataToCResultModel<Map<String,Object>>();
        String userId = request.getParameter("userId");
        if(userId != null && !userId.equals("")){
            Map<String,Object> map = orderService.queryCarCountAndKindByUserId(Integer.valueOf(userId));
            dataToCResult.setData(map);
            dataToCResult.setMsg("success");
            dataToCResult.setStatus(200);
        } else {
            dataToCResult.setMsg("fail");
            dataToCResult.setStatus(400);
        }
        return dataToCResult;
    }
    
    /**
     * 根据订单号获取物流信息
     * 
     * @param request
     * @return 返回物流信息
     */
    @RequestMapping(value = "/getLogisticsInfo",method = RequestMethod.GET)
    @ResponseBody
    public DataToCResultModel<CLogisticBean> getLogisticsInfo(HttpServletRequest request){
        DataToCResultModel<CLogisticBean> result = new DataToCResultModel<CLogisticBean>();
        // 订单主键id
        String orderNo = request.getParameter("orderNo");
        if(StringUtil.isNotEmpty(orderNo)){
            result.setStatus(200);
            result.setMsg("获取物流信息操作成功");
            result.setData(orderService.getLogisticsInfo(orderNo));
        } else {
            result.setStatus(400);
            result.setMsg("请求参数为空");
        }
        return result;
    }
}