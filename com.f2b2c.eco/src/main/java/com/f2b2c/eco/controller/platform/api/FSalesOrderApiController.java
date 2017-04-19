package com.f2b2c.eco.controller.platform.api;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.apimodel.FSalesOrder;
import com.f2b2c.eco.model.common.ApiResultModel;
import com.f2b2c.eco.model.market.BGoodsToCModel;
import com.f2b2c.eco.model.market.BShopInfoModel;
import com.f2b2c.eco.model.market.BToCOrderModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.market.Page;
import com.f2b2c.eco.model.market.PageResultModel;
import com.f2b2c.eco.model.platform.FGoodsModel;
import com.f2b2c.eco.model.platform.FSalesOrderDetailsModel;
import com.f2b2c.eco.model.platform.FSalesOrderModel;
import com.f2b2c.eco.service.common.AreaService;
import com.f2b2c.eco.service.market.BShopInfoService;
import com.f2b2c.eco.service.platform.FGoodsService;
import com.f2b2c.eco.service.platform.FSalesOrderService;
import com.f2b2c.eco.share.pay.alipay.config.BAlipayConfig;
import com.f2b2c.eco.share.pay.alipay.util.AlipayCore;
import com.f2b2c.eco.share.pay.alipay.util.AlipayNotify;
import com.f2b2c.eco.share.pay.alipay.util.BPayUtil;
import com.f2b2c.eco.share.pay.alipay.util.MoneyUtil;
import com.f2b2c.eco.share.pay.wechat.config.BWeixinConfig;
import com.f2b2c.eco.share.pay.wechat.util.BWeixinUtil;
import com.f2b2c.eco.share.pay.wechat.util.XMLUtil;
import com.f2b2c.eco.status.OrderStatusEnum;
import com.f2b2c.eco.status.SequenceType;
import com.f2b2c.eco.util.CommonUtil;
import com.f2b2c.eco.util.PropertiesUtil;
import com.f2b2c.eco.util.SequenceUtil;

/**
 * 订单接口控制器类
 * 
 * @author brave.chen
 *
 */
@Controller(value = "fSalesOrderApiController")
@RequestMapping(value = "/api/forder")
public class FSalesOrderApiController {
	
	/**
	 * 日志记录器
	 */
	private Logger logger=LoggerFactory.getLogger(FSalesOrderApiController.class);
	/**
	 * 订单服务类
	 */
	@Autowired
	private FSalesOrderService fSalesOrderService; 
	@Autowired
	private AreaService areaService;
	@Autowired
	private BShopInfoService bShopInfoService;
	@Autowired
	private FGoodsService fGoodsService;
	
	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryorderbyid", method = RequestMethod.GET)
	public final ApiResultModel<List<FSalesOrder>> queryOrderById(HttpServletRequest request, HttpServletResponse response, String userId)
	{
		return null;
	}
	
	@RequestMapping(value="/createOrder",method=RequestMethod.POST)
	@ResponseBody
	public DataToCResultModel<Object> createOrder(HttpServletRequest request,String fSalesOrder){
		JSONObject object = JSONObject.fromObject(fSalesOrder);
		FSalesOrderModel fSalesOrderObj = (FSalesOrderModel) JSONObject.toBean(object, FSalesOrderModel.class);
		DataToCResultModel<Object> dataToCResult = new DataToCResultModel<Object>();
		int result = fSalesOrderService.createOrder(fSalesOrderObj);
		if(result == 1){
			//申请支付要用的东西，由于现在条件不足，只能先生成订单
			dataToCResult.setStatus(200);
			dataToCResult.setMsg("success");
		}else{
			dataToCResult.setStatus(400);
			dataToCResult.setMsg("fail");
		}
		return dataToCResult;
	}
	
	/**
	 * 采购单
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/purchase",method=RequestMethod.GET)
	public final DataToCResultModel<PageResultModel> purchase(HttpServletRequest request,HttpServletResponse response){
		String userIdStr=StringUtils.trimToNull(request.getParameter("userId"));
		String pageStr=StringUtils.trimToNull(request.getParameter("page"));
		String pageSizeStr=StringUtils.trimToNull(request.getParameter("pageSize"));
		String statusStr=StringUtils.trimToNull(request.getParameter("status"));
		
		if(null==userIdStr||null==pageStr||null==pageSizeStr){
			return new DataToCResultModel<PageResultModel>(400,"请求参数不能为空",null);
		}
		
		Map<String, Object> map=new HashMap<>();
		map.put("userId", Integer.valueOf(userIdStr));
		map.put("status", statusStr==null?null:Integer.valueOf(statusStr));
		int totalCount = fSalesOrderService.findBusinessPurchesOrderCount(map);
		
		Page p = new Page(totalCount,Integer.valueOf(pageStr),Integer.valueOf(pageSizeStr));
		
		map.put("num", p.getStart());
		map.put("pageSize", p.getPageSize());
		List<BToCOrderModel> list = fSalesOrderService.findBusinessPurchesOrder(map);
		   if(list != null && list.size() > 0){
			   for(BToCOrderModel bToCOrder : list){
				   for(BGoodsToCModel bGoodsToC : bToCOrder.getList()){
					   if(bGoodsToC.getLogoUrl() != null && !bGoodsToC.getLogoUrl().equals("")){
						   String[] logoUrls = bGoodsToC.getLogoUrl().split("\\|");
						   bGoodsToC.setLogoUrl(PropertiesUtil.getValue("path") + logoUrls[0]);
					   }
				   }
			   }
		   }
		PageResultModel pageResult = new PageResultModel();
		/*if(null!=page&&CollectionUtils.isNotEmpty(page.getContent())){
			JSONArray jsonObject=new JSONArray();
			for (BToCOrderModel object : page.getContent()) {
				JSONObject jObject=new JSONObject();
			}
			jsonObject.put("id", value)
		}*/
		pageResult.setRows(list);
		pageResult.setTotalCount(totalCount);
		return new DataToCResultModel<PageResultModel>(200,"success",pageResult);
	}
	
	/**
	 * delete采购单
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public final DataToCResultModel<Object> delete(HttpServletRequest request,HttpServletResponse response){
		String orderId = StringUtils.trimToNull(request.getParameter("orderId"));
		String status = StringUtils.trimToNull(request.getParameter("status"));
		if(null != orderId && null != status){
			FSalesOrderModel fSalesOrder = new FSalesOrderModel();
			fSalesOrder.setId(orderId);
			fSalesOrder.setStatus(Integer.valueOf(status));
			fSalesOrderService.deleteOrderByOrderId(fSalesOrder);
			return new DataToCResultModel<Object>(200,"success",null);
		}
		return new DataToCResultModel<Object>(400,"failed",null);
	}
	
	/**
	 * F2B的提交订单
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/order",method=RequestMethod.POST)
	public DataToCResultModel<String> order(HttpServletRequest request,HttpServletResponse response){
		
		DataToCResultModel<String> dataToCResult = new DataToCResultModel<String>();
		//userId用户编号
		String userId=StringUtils.trimToNull(request.getParameter("userId"));
		//订单信息
		String strOrderInfo =StringUtils.trimToNull(request.getParameter("strOrderInfo"));
		//购物车信息或者直接购买商品
		String strShopInfo = StringUtils.trimToNull(request.getParameter("strShopInfo"));
		//0-直接购买，1-提交购物车
		String tag = StringUtils.trimToNull(request.getParameter("tag"));
		
	    String payType = StringUtils.trimToNull(request.getParameter("payType"));
		//第一步：判定时间是否在有操作范围内
//		OrderStatusEnum flag=isInRange();
//		if(OrderStatusEnum.NONE.equals(flag)){
//			return new DataToCResultModel<String>(401, "该时间段，不允许操作订单！", null);
//		}
		
		//第二步：根据business Id查询当前用户今天是否有订单,有也只可能有一条订单
		if(null==userId||null==tag||null==strOrderInfo||null==strShopInfo){
			dataToCResult.setMsg("参数不正确");
			dataToCResult.setStatus(400);
			return dataToCResult;
		}
		FSalesOrderModel unPayOrder = fSalesOrderService.queryUnPayOrder(Integer.valueOf(userId));
		if(unPayOrder != null){
			dataToCResult.setMsg("您有未完成的订单，订单号为"+unPayOrder.getOrderId()+",请先完成订单再下单");
			dataToCResult.setStatus(400);
			return dataToCResult;
		}
		int result=0;
		int status = 0;
		FSalesOrderModel fSalesOrder=fSalesOrderService.findOneOrder(Integer.valueOf(userId), DateTime.now().toString("yyyy-MM-dd"));
		if(null==fSalesOrder){
			fSalesOrder=new FSalesOrderModel();
		}else{
			if(!fSalesOrder.getPayType().equals(Integer.valueOf(payType))){
				if(fSalesOrder.getPayType().equals("0")){
					dataToCResult.setMsg("您当天已经下过订单，本次的订单和上次的订单支付类型不一致，您上次订单支付类型为担保货到付款，请重新选择支付类型，提交订单！");
					}else if(fSalesOrder.getPayType().equals("1")){
						dataToCResult.setMsg("您当天已经下过订单，本次的订单和上次的订单支付类型不一致，您上次订单支付类型为微信支付，请重新选择支付类型，提交订单！");
					}else{
						dataToCResult.setMsg("您当天已经下过订单，本次的订单和上次的订单支付类型不一致，您上次订单支付类型为支付宝支付，请重新选择支付类型，提交订单！");
					}
					dataToCResult.setStatus(400);
					return dataToCResult;
			}else{
				if(fSalesOrder.getPayType() == 0 && fSalesOrder.getStatus() == 2){
					status = 1;
				}else{
					status = 0;
				}
			}
		}
		fSalesOrder = parseFromJsonStr(strOrderInfo,strShopInfo,fSalesOrder,Integer.valueOf(tag),Integer.valueOf(userId),Integer.valueOf(payType));
//		if(null != fSalesOrder){
			//更新订单(直接购买&购物车)
			result=fSalesOrderService.save(fSalesOrder,status);
			//如果订单创建成功,去支付页面
			if(result == 1){
				String orderInfo = "";
				String goodsName = "";
				for(FSalesOrderDetailsModel fSalesOrderDetails : fSalesOrder.getfSalesOrderDetailsModel()){
					goodsName += fSalesOrderDetails.getGoodsName() + ",";
				}
				if(payType.equals("0")){        //担保支付,不做任何的业务处理，直接返回成功
					dataToCResult.setMsg("支付成功");
					dataToCResult.setStatus(200);
				}else if(payType.equals("1")){   //微信支付，调出微信支付，现在没有做微信支付，不处理逻辑
					orderInfo = BWeixinUtil.pay(request, response, fSalesOrder.getTotal(), "平台订单支付",fSalesOrder.getMergeOrderNo(),BWeixinConfig.notify_url);
				}else if(payType.equals("2")){   //支付宝支付，调出支付宝支付
					orderInfo = BPayUtil.getInfo("店家订单支付", goodsName.substring(0,goodsName.length()-1), MoneyUtil.fromFenToYuan(fSalesOrder.getTotal().toString()), fSalesOrder.getMergeOrderNo(),BAlipayConfig.notify_url);
				}
				dataToCResult.setData(orderInfo);
				dataToCResult.setMsg("提交成功");
				dataToCResult.setStatus(200);
			}else{
				dataToCResult.setMsg("提交失败");
				dataToCResult.setStatus(400);
			}
		return dataToCResult;
	}
	
	/**
	 * 从客户端请求的一些数据中获取对象
	 * @param orderInfo 订单的简单信息
	 * @param strShopInfo 购物车或者单个商品的信息
	 * @param temp 如果是更新原先订单，就会传入原先订单，否则传入创建的新订单对象
	 * @param tag 1：购物车提交 0：直接购买
	 * @param userId 
	 * @return 返回从数据中获取的订单对象
	 */
	private FSalesOrderModel parseFromJsonStr(String orderInfo,String shopInfo,FSalesOrderModel temp,Integer tag,Integer userId,Integer payType){
		JSONObject object = JSONObject.fromObject(orderInfo);
		
		List<FSalesOrderDetailsModel> fsdList=new ArrayList<FSalesOrderDetailsModel>();
		
		Integer total=0;
		
		Integer count = 0;
		
		temp.setCreatedTime(new Date());
		
		BShopInfoModel bshopInfo=bShopInfoService.findShopInfoByUserId(Integer.valueOf(userId));
		temp.setDistributionArea(bshopInfo==null?null:bshopInfo.getArea()==null?null:areaService.findArea(bshopInfo.getArea().getCode()));
		
		//注意订单列表编号没有填入
		if(temp.getPayType() != null && temp.getStatus() == 2){
			if(temp.getPayType() == 0 && temp.getStatus() == 2){
				temp.setId(temp.getId() == null ? CommonUtil.generate32UUID() : temp.getId());
				temp.setOrderId(temp.getOrderId() == null ? SequenceUtil.nextId(SequenceType.B.toString()) : temp.getOrderId());
			}
		}else{
			temp.setId(CommonUtil.generate32UUID());
			temp.setOrderId(SequenceUtil.nextId(SequenceType.B.toString()));
		}
		temp.setMergeOrderNo(SequenceUtil.nextId(SequenceType.M.toString()));   //合并订单号
		temp.setPayType(payType);
		temp.setReceiverAddress(StringUtils.trimToNull(object.getString("receiverAddress")));
		temp.setReceiverName(StringUtils.trimToNull(object.getString("receiverName")));
		temp.setReceiverPhone(StringUtils.trimToNull(object.getString("receiverPhone")));
		//temp.setReceiverPostalCode(StringUtils.trimToNull(object.getString("receiverPostalCode")));
		temp.setReceiveTimeType(3);
		temp.setRemark(StringUtils.trimToNull(object.getString("remark")));
		temp.setShopName(bshopInfo.getShopName());
		temp.setStatus(2);
		temp.setUpdatedTime(new Date());
		temp.setUserId(userId);
		
		if(tag>0){
			//goods list
//			JSONObject shopInfoObject=JSONObject.fromObject(shopInf o);
			JSONArray array = JSONArray.fromObject(shopInfo);
//			JSONArray array=(JSONArray) shopInfoObject.get("goodsList");
			if(null!=array&&array.size()>0){
				for(int i=0;i<array.size();i++){
					JSONObject jObject=(JSONObject) array.get(i);
					FSalesOrderDetailsModel fsOrder = new FSalesOrderDetailsModel();
					FGoodsModel fGoods = fGoodsService.findFgoodsById(jObject.getInt("goodsId"));
					// same with the order
					fsOrder.setCreatedTime(null==temp.getCreatedTime()?new Date():temp.getCreatedTime());
					fsOrder.setId(CommonUtil.generate32UUID());
					fsOrder.setUpdatedTime(new Date());
					fsOrder.setGoodsId(jObject.getInt("goodsId"));
					fsOrder.setGoodsName(fGoods.getName());
					fsOrder.setLogoUrl(fGoods.getLogoUrl());
					fsOrder.setGoodsQty(jObject.getInt("goodsQty"));
					fsOrder.setGoodsVersion(jObject.getInt("version"));
					fsOrder.setPrice(jObject.getInt("price"));
					fsOrder.setCartId(jObject.getInt("cartId"));
					fsOrder.setOrder(temp);
					total+=(fsOrder.getGoodsQty()*fsOrder.getPrice());
					count += fsOrder.getGoodsQty();
					fsdList.add(fsOrder);
				}
			}
		}else{
			JSONObject shopInfoObject=JSONObject.fromObject(shopInfo);
			FSalesOrderDetailsModel fsOrder = new FSalesOrderDetailsModel();
			FGoodsModel fGoods = fGoodsService.findFgoodsById(shopInfoObject.getInt("goodsId"));
			// same with the order
			fsOrder.setCreatedTime(null==temp.getCreatedTime()?new Date():temp.getCreatedTime());
			fsOrder.setId(CommonUtil.generate32UUID());
			fsOrder.setUpdatedTime(new Date());
			fsOrder.setGoodsId(shopInfoObject.getInt("goodsId"));
			fsOrder.setGoodsName(fGoods.getName());
			fsOrder.setLogoUrl(fGoods.getLogoUrl());
			fsOrder.setGoodsQty(shopInfoObject.getInt("goodsQty"));
			fsOrder.setGoodsVersion(shopInfoObject.getInt("version"));
			fsOrder.setPrice(shopInfoObject.getInt("price"));
			fsOrder.setOrder(temp);
			total+=(fsOrder.getGoodsQty()*fsOrder.getPrice());
			count += fsOrder.getGoodsQty();
			fsdList.add(fsOrder);
		}
		temp.setTotal(total);
		temp.setRealPay(total);
		temp.setGoodsCount(count);
		temp.setfSalesOrderDetailsModel(fsdList);
		return temp;
	}
	
	/**
	 * 判断当前时间是否是下单和补单时间内容
	 * @return
	 */
	private OrderStatusEnum isInRange(){
		String oStart=StringUtils.trimToNull(PropertiesUtil.getValue("delimitOrderStart"));
		String oEnd=StringUtils.trimToNull(PropertiesUtil.getValue("delimitOrderEnd"));
		String mpStart=StringUtils.trimToNull(PropertiesUtil.getValue("delimitMakeupStart"));
		String mpEnd=StringUtils.trimToNull(PropertiesUtil.getValue("delimitMakeupEnd"));
		try{
			if(null==oStart||null==oEnd||null==mpStart||null==mpEnd){
				return OrderStatusEnum.NONE;
			}
			
			DateTime dTimeStart=CommonUtil.generateTime(String.format("%s %s", DateTime.now().toString("yyyy-MM-dd"),oStart), "yyyy-MM-dd hh:mm:ss");
			DateTime dTimeEnd=CommonUtil.generateTime(String.format("%s %s", DateTime.now().toString("yyyy-MM-dd"),oEnd), "yyyy-MM-dd hh:mm:ss");
			if(dTimeStart.isBeforeNow()&&dTimeEnd.isAfterNow()){
				return OrderStatusEnum.PURCHASE;
			}
			
			dTimeStart=CommonUtil.generateTime(String.format("%s %s", DateTime.now().toString("yyyy-MM-dd"),mpStart), "yyyy-MM-dd hh:mm:ss");
			dTimeEnd=CommonUtil.generateTime(String.format("%s %s", DateTime.now().toString("yyyy-MM-dd"),mpEnd), "yyyy-MM-dd hh:mm:ss");
			if(dTimeStart.isBeforeNow()&&dTimeEnd.isAfterNow()){
				return OrderStatusEnum.MAKEUP;
			}
			
			return OrderStatusEnum.NONE;
		}catch(Exception e){
			logger.error(e.getMessage());
			return OrderStatusEnum.NONE;
		}
	}
	
	   /**
	    * 订单立即支付
	    * @param request
	    * @return
	    */
	   @RequestMapping(value="/payorder",method=RequestMethod.POST)
	   @ResponseBody
	   public DataToCResultModel<String> payOrder(HttpServletRequest request,HttpServletResponse response){
		   DataToCResultModel<String> dataToCResult = new DataToCResultModel<String>();
		   String orderId = request.getParameter("orderId");
		   String payType = request.getParameter("payType");
		   if(orderId != null && !orderId.equals("")){
			   BToCOrderModel bToCOrder = fSalesOrderService.findBusinessPurchesOrderDetail(orderId);
			   String goodsName = "";
			   for(BGoodsToCModel bGoodsToC : bToCOrder.getList()){
				   goodsName += bGoodsToC.getName() + ",";
			   }
			   String orderInfo = "";
			   if(payType.equals("1")){   //微信支付
				   orderInfo = BWeixinUtil.pay(request, response, bToCOrder.getRealTotalPrice(),"平台订单支付",bToCOrder.getMergeOrderNo(),BWeixinConfig.notify_url);
			   }else if(payType.equals("2")){   //支付宝支付
				   orderInfo = BPayUtil.getInfo("订单支付", goodsName.substring(0,goodsName.length()-1),MoneyUtil.fromFenToYuan(bToCOrder.getRealTotalPrice().toString()),bToCOrder.getMergeOrderNo(),BAlipayConfig.notify_url);
			   }
			   dataToCResult.setData(orderInfo);
			   dataToCResult.setMsg("支付成功");
			   dataToCResult.setStatus(200);
		   }else{
			   dataToCResult.setMsg("支付失败");
			   dataToCResult.setStatus(400);
		   }
		   return dataToCResult;
	   }
	
	
	@RequestMapping(value = "/notify_ftb_url", method = RequestMethod.POST)
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
/*				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");*/
				params.put(name, valueStr);
			}
			// 应用id
			String app_id = request.getParameter("app_id");		
			// 商户订单号
			String out_trade_no = request.getParameter("out_trade_no");
			//支付宝交易号	
			String body = request.getParameter("body");
			//支付宝交易号	
			String buyer_id = request.getParameter("buyer_id");
			//支付宝交易号	
			String seller_id = request.getParameter("seller_id");
			//支付宝交易号	
			String subject = request.getParameter("subject");
			//支付宝交易号	
			String total_amount = request.getParameter("total_amount");
			//支付宝交易号	
			String trade_no = request.getParameter("trade_no");
			//交易状态
			String trade_status = request.getParameter("trade_status");
			//异步通知ID
			String notify_id=request.getParameter("notify_id");
		    // sign
			String sign=request.getParameter("sign");
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(s以上仅供参考)
			//判断接受的post通知中有无notify_id，如果有则是异步通知。
			if(StringUtil.isNotEmpty(notify_id)){
				if(AlipayNotify.verifyResponse(notify_id).equals("true")){
/*					if(AlipayNotify.getSignVeryfy(params, sign)){*/
					//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
						if(trade_status.equals("TRADE_FINISHED")){
							//判断该笔订单是否在商户网站中已经做过处理
								//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
								//如果有做过处理，不执行商户的业务程序
							//注意：
							//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
							//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
							fSalesOrderService.notifyUrl(app_id, out_trade_no, body, buyer_id, seller_id, subject, total_amount, trade_no, request.getParameterMap().toString());
						} else if (trade_status.equals("TRADE_SUCCESS")){
							//判断该笔订单是否在商户网站中已经做过处理
								//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
								//如果有做过处理，不执行商户的业务程序
							//注意：
							//付款完成后，支付宝系统发送该交易状态通知
							//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
							fSalesOrderService.notifyUrl(app_id, out_trade_no, body, buyer_id, seller_id, subject, total_amount, out_trade_no, request.getParameterMap().toString());
						}
						//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
						response.getWriter().write("success");
						//请不要修改或删除
						//调试打印log
						AlipayCore.logResult("notify_ftb_url success!","notify_ftb_url");
/*					} else {
						response.getWriter().write("sign fail");
					}*/
				} else {
					response.getWriter().write("response fail");
				}
			} else {
				response.getWriter().write("no notify message");
			}
		} catch (Exception e){
			logger.error(e.getMessage());
		}
	} 
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/wx_b_notify_url", method = RequestMethod.POST)
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
           Map<String, String> map = new HashMap<String, String>();
           String result_code = "";
           map = XMLUtil.doXMLParse(xmlString);
           result_code = map.get("result_code");
           if (BWeixinUtil.checkSign(xmlString)) {
        	   fSalesOrderService.wxNotifyUrl(map);
               return BWeixinUtil.returnXML(result_code);
           } else {
               return BWeixinUtil.returnXML("FAIL");
           }
       } catch(Exception ex){
           logger.error(ex.getMessage());
       }
       return BWeixinUtil.returnXML("FAIL");
    } 
	
}
  
