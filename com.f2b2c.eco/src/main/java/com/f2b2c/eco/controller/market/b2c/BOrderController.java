package com.f2b2c.eco.controller.market.b2c;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.f2b2c.eco.jpush.JPush;
import com.f2b2c.eco.model.market.BGoodsToCModel;
import com.f2b2c.eco.model.market.BSalesOrderDetailsModel;
import com.f2b2c.eco.model.market.BSalesOrderModel;
import com.f2b2c.eco.model.market.BShopInfoModel;
import com.f2b2c.eco.model.market.BToCOrderModel;
import com.f2b2c.eco.model.market.BUserModel;
import com.f2b2c.eco.model.market.CLogisticsCodeModel;
import com.f2b2c.eco.service.market.BSalesOrderService;
import com.f2b2c.eco.service.market.BShopInfoService;
import com.f2b2c.eco.status.StorageStatus;
import com.f2b2c.eco.util.PropertiesUtil;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;

@Controller("bOrderController")
@RequestMapping(value = "/market/border")
public class BOrderController {
    
    @Autowired
    private BSalesOrderService bSalesOrderService;
    
    @Autowired
    private BShopInfoService bShopInfoService;
    
    
    @RequestMapping(value = "orderlist")
    public String index() {
        return "market/borderlist";
    }
    
    @RequestMapping(value="/getOrderList",method=RequestMethod.POST)
    @ResponseBody
    public Page<BSalesOrderModel> querySaleOrderByBUserId(final Pageable pageable,HttpServletRequest request,HttpSession session){
             Map<String, Object> map = new HashMap<String, Object>();
             String orderNo = request.getParameter("orderNo");
             String receiverName = request.getParameter("receiverName");
             String catalog = request.getParameter("catalog");
             String payType = request.getParameter("payType");
             String status = request.getParameter("status");
             BUserModel user = (BUserModel) session.getAttribute(StorageStatus.MARKET_USER.name());
             map.put("userId", user.getId());
             map.put("orderNo", orderNo);
             map.put("receiverName", receiverName);
             map.put("catalog", (catalog.equals("") || catalog == null)? null : Integer.valueOf(catalog));
             map.put("payType", (payType.equals("") || payType == null)? null : Integer.valueOf(payType));
             map.put("status", (status.equals("") || status == null)? null : Integer.valueOf(status));
             Page<BSalesOrderModel> page = bSalesOrderService.querySaleOrderByBUserId(pageable, map);
             return page;
         }
    
    /**
     * 获取物流
     * 
     * @return
     */
	@RequestMapping(value = "/getLogisticsInfo", method = RequestMethod.POST)
    @ResponseBody
    public Page<CLogisticsCodeModel> getLogisticsInfo(final Pageable pageable,HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        String name = request.getParameter("name");
        map.put("name", name);
        Page<CLogisticsCodeModel> page = bSalesOrderService.getLogisticsInfo(pageable, map);
        return page;
    }
    
    /**
     * 保存物流信息
     * 
     * @return
     */
    @RequestMapping(value="/saveLogisticsInfo",method=RequestMethod.GET)
    @ResponseBody
    public Object saveLogisticsInfo(HttpServletRequest request){
        String orderNo = request.getParameter("orderNo");
        String code = request.getParameter("code");
        String number = request.getParameter("number");
        String userId = request.getParameter("userId");
        int result = bSalesOrderService.saveLogisticsInfo(orderNo, code, number);
        if(result == 1){
        	JPush jpush = new JPush();
        	try {
                jpush.buildPayloadWithAliaId(userId, "单号：" + orderNo + "的订单已发货", "好享吃消息提示");
			} catch (APIConnectionException e) {
				e.printStackTrace();
			} catch (APIRequestException e) {
				e.printStackTrace();
			}
        }
        return result;
    }
    
    /**
     * 接单
     * 
     * @param request
     * @return
     */
    @RequestMapping(value="/receiveOrder",method=RequestMethod.POST)
    @ResponseBody
    public int receiveOrder(HttpServletRequest request){
        String orderId = request.getParameter("orderId");
        String status = request.getParameter("status");
        String receiveOrder = request.getParameter("receiveOrder");
        String userId = request.getParameter("userId");
        String orderNo = request.getParameter("orderNo");
        String realPay = request.getParameter("realPay");
        BSalesOrderModel bSalesOrder = new BSalesOrderModel();
        bSalesOrder.setId(orderId);
        bSalesOrder.setStatus(Integer.valueOf(status));
        if(receiveOrder.equals("2")){
        	bSalesOrder.setRealPay(Integer.valueOf(realPay));
        }
        bSalesOrder.setReceiveOrder(Integer.valueOf(receiveOrder));
        bSalesOrder.setUpdatedTime(new Date());
        bSalesOrder.setReceiveOrderTime(new Date());
        int result = bSalesOrderService.refuseOrder(bSalesOrder);
        if(result == 1){
        	JPush jpush = new JPush();
        	try {
        		if(receiveOrder.equals("1")){
                    jpush.buildPayloadWithAliaId(userId, "单号：" + orderNo + "的订单，商家已接单", "好享吃消息提示");
        		}else{
                    jpush.buildPayloadWithAliaId(userId, "单号：" + orderNo + "的订单，商家拒绝接单", "好享吃消息提示");
        		}
			} catch (APIConnectionException e) {
				e.printStackTrace();
			} catch (APIRequestException e) {
				e.printStackTrace();
			}
        }
    	return result;
    }
    
    
    @RequestMapping(value = "/getReplyOrder")
    public String getReplyOrder(){
    	return "market/returnborderlist";
    }
    
    @RequestMapping(value="/queryReturnOrder",method=RequestMethod.POST)
    @ResponseBody
    public Page<BSalesOrderDetailsModel> queryReturnOrder(final Pageable pageable,HttpServletRequest request,HttpSession session){
    	 Map<String, Object> map = new HashMap<String, Object>();
    	 String orderNo = request.getParameter("orderNo");
   	     String receiverName = request.getParameter("receiverName");
   	     String goodsName = request.getParameter("goodsName");
   	     String goodsStatus = request.getParameter("goodsStatus");
         BUserModel user = (BUserModel) session.getAttribute(StorageStatus.MARKET_USER.name());
         map.put("userId", user.getId());
         map.put("orderNo", orderNo);
         map.put("receiverName",receiverName);
         map.put("goodsName", goodsName);
         map.put("goodsStatus",(goodsStatus == null || goodsStatus.equals("")) ? null : Integer.valueOf(goodsStatus));
         Page<BSalesOrderDetailsModel> page = bSalesOrderService.queryReturnOrder(pageable,map);
		 return page;
    	
    }
    
    /**
     * 同意退款
     * 
     * @param request
     * @return
     */
   @RequestMapping(value="/agreeReturn",method=RequestMethod.POST)
   @ResponseBody
   public Map<String,Object> agreeReturn(HttpServletRequest request){
	   Map<String,Object> map = new HashMap<String, Object>();
	   String orderId = request.getParameter("orderId");
	   String orderDetailId = request.getParameter("orderDetailId");
	   String returnType = request.getParameter("returnType");
	   String userId = request.getParameter("userId");
	   String returnAmount = request.getParameter("returnAmount");
	   String orderNo = request.getParameter("orderNo");
	   String goodsName = request.getParameter("goodsName");
	   int result = bSalesOrderService.agreeReturn(orderId,orderDetailId, Integer.valueOf(returnType), Integer.valueOf(userId), Integer.valueOf(returnAmount));
	   if(result == 1){
		   JPush jpush = new JPush();
		   if(returnType.equals("1")){
			   try {
                    jpush.buildPayloadWithAliaId(userId, "订单：" + orderNo + "中的" + goodsName + "商品退款成功", "好享吃消息提示");
			} catch (APIConnectionException e) {
				e.printStackTrace();
			} catch (APIRequestException e) {
				e.printStackTrace();
			}
		   }else{
			   try {
                    jpush.buildPayloadWithAliaId(userId, "订单：" + orderNo + "中的" + goodsName + "商品退货成功", "好享吃消息提示");
			} catch (APIConnectionException e) {
				e.printStackTrace();
			} catch (APIRequestException e) {
				e.printStackTrace();
			}
		   }
	   }
	   map.put("result", result);
	   return map;
   }
   
   	/**
     * 拒绝退款
     * 
     * @return
     */
    @RequestMapping(value="/refuseReturn",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> refuseReturn(HttpServletRequest request){
    	Map<String,Object> map = new HashMap<String, Object>();
    	String orderId = request.getParameter("orderId");
    	String orderDetailId = request.getParameter("orderDetailId");
    	String returnType = request.getParameter("returnType");
    	String orderNo = request.getParameter("orderNo");
    	String goodsName = request.getParameter("goodsName");
    	String userId = request.getParameter("userId");
    	int result = bSalesOrderService.refuseReturn(orderId,orderDetailId, Integer.valueOf(returnType));
    	if(result == 1){
    		JPush jpush = new JPush();
    		if(returnType.equals("1")){
    			try {
                    jpush.buildPayloadWithAliaId(userId, "订单：" + orderNo + "中的商品" + goodsName + "商家拒绝退款", "好享吃消息提示");
				} catch (APIConnectionException e) {
					e.printStackTrace();
				} catch (APIRequestException e) {
					e.printStackTrace();
				}
    		}else{
    			try {
                    jpush.buildPayloadWithAliaId(userId, "订单：" + orderNo + "中的商品" + goodsName + "商家拒绝退货", "好享吃消息提示");
				} catch (APIConnectionException e) {
					e.printStackTrace();
				} catch (APIRequestException e) {
					e.printStackTrace();
				}
    		}
    	}
    	map.put("result", result);
    	return map;
    }
    
    /**
     * 水果类型的订单发货
     * 
     * @param request
     * @return
     */
    @RequestMapping(value="/deliverOrder",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deliverOrder(HttpServletRequest request){
    	Map<String,Object> map = new HashMap<String, Object>();
    	String orderId = request.getParameter("orderId");
    	String orderNo = request.getParameter("orderNo");
    	String userId = request.getParameter("userId");
    	BSalesOrderModel bSalesOrder = new BSalesOrderModel();
    	bSalesOrder.setId(orderId);
    	bSalesOrder.setStatus(4);
    	bSalesOrder.setUpdatedTime(new Date());
    	bSalesOrder.setDeliverTime(new Date());
    	int result = bSalesOrderService.updateStatusTo(bSalesOrder);
    	if(result == 1){
    		JPush jpush = new JPush();
    		try {
                jpush.buildPayloadWithAliaId(userId, "单号：" + orderNo + "的订单，商家已发货", "好享吃消息提示");
			} catch (APIConnectionException e) {
				e.printStackTrace();
			} catch (APIRequestException e) {
				e.printStackTrace();
			}
    	}
    	map.put("result", result);
    	return map;
    }
    
    /**
     * 订单详情
     * 
     * @param request
     * @return
     */
   @RequestMapping(value="/getOrderDetail",method=RequestMethod.GET)
    public ModelAndView getOrderDetail(HttpServletRequest request){
	    Map<String,Object> map = new HashMap<String, Object>();
	    String orderId = request.getParameter("orderId");
        BToCOrderModel btc = bSalesOrderService.queryAllOrderDetail(orderId); // 订单详情
	    for(BGoodsToCModel bGoods : btc.getList()){
	    	String[] logoUrls = bGoods.getLogoUrl().split("\\|");
	    	bGoods.setLogoUrl(PropertiesUtil.getValue("path") + logoUrls[0]);
	    }
	    BShopInfoModel bShopInfo = bShopInfoService.queryShopNameByUserId(btc.getbUserId());
	    if(bShopInfo.getProvinceCode().equals("110000") || bShopInfo.getProvinceCode().equals("120000") || bShopInfo.getProvinceCode().equals("310000") || bShopInfo.getProvinceCode().equals("500000") || bShopInfo.getProvinceCode().equals("810000") || bShopInfo.getProvinceCode().equals("820000")){
	    	bShopInfo.setAreaName(bShopInfo.getCityName());
	    }else{
	    	bShopInfo.setAreaName(bShopInfo.getProvinceName()+bShopInfo.getCityName());
	    }
	    map.put("btc",btc);
	    map.put("bShopInfo", bShopInfo);
    	return new ModelAndView("market/orderdetail", map);
    }
}