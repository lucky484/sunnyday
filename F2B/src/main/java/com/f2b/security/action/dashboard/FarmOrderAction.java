package com.f2b.security.action.dashboard;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.f2b.security.action.HttpsRequest;
import com.f2b.security.action.WechatPayUtil;
import com.f2b.security.action.converter.FarmOrderConverter;
import com.f2b.security.business.biz.FarmOrderBiz;
import com.f2b.security.business.biz.FarmUserBiz;
import com.f2b.security.business.biz.RecordBiz;
import com.f2b.security.business.biz.ShareRecordBiz;
import com.f2b.security.domain.FarmOrder;
import com.f2b.security.domain.FarmUser;
import com.f2b.security.domain.ShareRecord;
import com.f2b.sugar.tools.SignUtil;
import com.f2b.sugar.tools.StringConverters;
import com.f2b.sugar.tools.pay.http.UrlHelp;
import com.f2b.sugar.wxlib.ParamesAPI.AccessToken;
import com.f2b.sugar.wxlib.ParamesAPI.ParamesAPI;
import com.f2b.sugar.wxlib.ParamesAPI.WeixinUtil;

import net.sf.json.JSONObject;

/**
 * 
 * @author mozzie.chu
 *
 */
@Controller
@RequestMapping("/web/farm")
public class FarmOrderAction {

	private final static Logger logger = LoggerFactory.getLogger(FarmOrderAction.class);
	private final static Integer DEFAULT_PAGE_LIST_NUM = 20;

	@Autowired
	private FarmOrderBiz farmOrderBiz;
	@Autowired
	private RecordBiz recordBiz;
	@Autowired
	private ShareRecordBiz shareRecordBiz;
	@Autowired
	private FarmUserBiz farmUserBiz;

	// ******************************************************************************
	// ********************************* CRUD START
	// *********************************
	// ******************************************************************************

	/**
	 * 打开页面
	 */
	@RequestMapping("/getFarmOrderPage")
	public ModelAndView getFarmOrderPage(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/wxpay/farmOrder");
		modelAndView.addObject("type", request.getParameter("type"));
		modelAndView.addObject("openid", request.getParameter("openid"));
//		modelAndView.addObject("status",request.getParameter("status"));
		// 分享参数
		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
		String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
		String url2 = UrlHelp.getFullRequestURL(request);
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);

		modelAndView.addObject("timestamp", ret.get("timestamp"));
		modelAndView.addObject("nonceStr", ret.get("nonceStr"));
		modelAndView.addObject("signature", ret.get("signature"));
		return modelAndView;

	}

	/**
	 * 添加订单/提交订单
	 * 
	 * @param farmOrder
	 * @param request
	 * @return
	 */
	@RequestMapping("/addFarmOrder")
	public ModelAndView addFarmOrder(String sku, String buyOpenId, HttpServletRequest request,HttpServletResponse response) {
		String code = request.getParameter("code");
		logger.info("code is {}", code);
		if (StringUtils.isEmpty(code)) {
			String url = null;
			try {
				url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + ParamesAPI.appId + "&redirect_uri="
						+ URLEncoder.encode(UrlHelp.getUrlPathWithContextNoPort(request) + "/web/farm/addFarmOrder.action?buyOpenId="
								+buyOpenId + "&sku=" + sku, "utf-8") + "&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
						response.sendRedirect(url);
						return null;
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + ParamesAPI.appId + "&secret="
				+ ParamesAPI.secret + "&code=" + code + "&grant_type=authorization_code";
		JSONObject result = WeixinUtil.httpRequest(url, "GET", null);
		String openid = "";
		try {
			openid = (String) result.get("openid");
		} catch (Exception e) {
			logger.info("code失效！");
		}
		logger.info("open id is {}", openid);
		logger.info("sku is {}", sku);
		logger.info("buyOpenId is {}", buyOpenId);
		FarmOrder farmOrder = farmOrderBiz.getOrderByNo(sku, buyOpenId);
		ModelAndView modelAndView = new ModelAndView();
		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
		if (farmOrder != null && farmOrder.getStatus() == -2) {
			//主动调接口查询订单状态
			String sign = WechatPayUtil.signForQuery(sku);
			String params = "<xml>\n" + "<appid><![CDATA[" + ParamesAPI.appId + "]]></appid>\n" + "<mch_id><![CDATA[" + ParamesAPI.partner
					+ "]]></mch_id>\n" + "<nonce_str><![CDATA[D380BFC2BAD727A6B6845193519E3AD6]]></nonce_str>\n"
					+ "<out_trade_no><![CDATA[" + sku + "]]></out_trade_no>\n"
					+ "<sign><![CDATA["+sign+"]]></sign>\n</xml>";
			String queryResult = null;
			try {
				queryResult = new HttpsRequest().sendPost("https://api.mch.weixin.qq.com/pay/orderquery", params);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//支付成功
			if (queryResult.contains("<trade_state><![CDATA[SUCCESS]]></trade_state>")) {
				farmOrder.setCreateDate(new Date());
				farmOrder.setUpdateDate(new Date());
				farmOrder.setStatus(-1);
				farmOrderBiz.updateOrder(farmOrder);
				
				//减去抵用券
				FarmUser user = farmUserBiz.getByOpenId(openid).get(0);
				user.setUpdateDate(new Date());
				user.setStatus("0");
				farmUserBiz.updateFarmUser(user);
				
				/* 2016.12.05 注释 取消分佣 */
				logger.info("开始添加佣金信息");
				List<ShareRecord> list = shareRecordBiz.get(openid); // 当前购买者所有的分享人,现在理论只能有一个，最早的一个
				if (!list.isEmpty()) {
					ShareRecord share = list.get(list.size() - 1);  // 只拿首次分享给他的人
					ShareRecord model = new ShareRecord();
					model.setOpenId(openid);
					model.setShareOpenId(share.getShareOpenId());
					model.setShareNickName(share.getShareNickName());
					model.setCreateDate(new Date());
					// 记录购买数量
					model.setNumber(Integer.valueOf(farmOrder.getWeight()));
					model.setSendRedPack(0);
					model.setNickname(farmOrder.getNickname());
					model.setSku(farmOrder.getSku());
					shareRecordBiz.addOrUpdate(model);
				} else {
					if (farmOrder.getShareOpenId() != null) {
						// 购买者通过他人分享的链接进行购买
						ShareRecord shareRecord = new ShareRecord();
						shareRecord.setOpenId(openid);
						// 获取分享人的昵称
						shareRecord.setShareNickName(farmOrder.getShareNickname());
						shareRecord.setShareOpenId(farmOrder.getShareOpenId());
						shareRecord.setCreateDate(new Date());
						// 记录购买数量
						shareRecord.setNumber(Integer.valueOf(farmOrder.getWeight()));
						shareRecord.setSendRedPack(0);
						shareRecord.setNickname(farmOrder.getNickname());
						shareRecord.setSku(farmOrder.getSku());
						shareRecordBiz.addOrUpdate(shareRecord);
					}
				}
				modelAndView.setViewName("/pages/payment/farmSuccess");
				modelAndView.addObject("farmOrder", farmOrder);
				// 分享参数
				String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
				String url2 = UrlHelp.getFullRequestURL(request);
				Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);
				modelAndView.addObject("timestamp", ret.get("timestamp"));
				modelAndView.addObject("nonceStr", ret.get("nonceStr"));
				modelAndView.addObject("signature", ret.get("signature"));
				
				// 下单完成立马发放5元红包,2016.12.9
				//recordBiz.payMoney(openid, Integer.valueOf(farmOrder.getWeight()) * 5);
			} else {
				modelAndView.addObject("error", "支付失败或取消支付(如您已付款成功，请十分钟后再次回到首页查询订单)!");
				modelAndView.setViewName("pages/error");
			}
		} else if (farmOrder != null) {
			modelAndView.setViewName("/pages/payment/farmSuccess");
			modelAndView.addObject("farmOrder", farmOrder);
			// 分享参数
			String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
			String url2 = UrlHelp.getFullRequestURL(request);
			Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);
			modelAndView.addObject("timestamp", ret.get("timestamp"));
			modelAndView.addObject("nonceStr", ret.get("nonceStr"));
			modelAndView.addObject("signature", ret.get("signature"));
			
			// 下单完成立马发放5元红包,2016.12.9
			//recordBiz.payMoney(openid, Integer.valueOf(farmOrder.getWeight()) * 5);
		} else {
			modelAndView.addObject("error", "支付失败或取消支付(如您已付款成功，请十分钟后再次回到首页查询订单)!");
			modelAndView.setViewName("pages/error");
		}
		return modelAndView;
	}

	/**
	 * 根据openid查询订单
	 * 
	 * @param phone
	 * @return
	 */
	@RequestMapping("/getOrderList")
	public ModelAndView getOrderList(String openId, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/pages/payment/farmDetail");
		List<FarmOrder> resultList = new ArrayList<>();
		List<FarmOrder> list = farmOrderBiz.getOrderList(openId);
		for (FarmOrder order : list) {
			if (order.getStatus() == -2 && order.getSku() != null) {
				String sign = WechatPayUtil.signForQuery(order.getSku());
				String params = "<xml>\n" + "<appid><![CDATA[" + ParamesAPI.appId + "]]></appid>\n" + "<mch_id><![CDATA[" + ParamesAPI.partner
						+ "]]></mch_id>\n" + "<nonce_str><![CDATA[D380BFC2BAD727A6B6845193519E3AD6]]></nonce_str>\n"
						+ "<out_trade_no><![CDATA[" + order.getSku() + "]]></out_trade_no>\n"
						+ "<sign><![CDATA["+sign+"]]></sign>\n</xml>";
				String queryResult = null;
				try {
					queryResult = new HttpsRequest().sendPost("https://api.mch.weixin.qq.com/pay/orderquery", params);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// 微信端支付成功
				if (queryResult.contains("<trade_state><![CDATA[SUCCESS]]></trade_state>")) {
					order.setStatus(-1);
					order.setUpdateDate(new Date());
					farmOrderBiz.updateOrder(order);
					resultList.add(order);
				}
			} else if (order.getSku() != null) {
				resultList.add(order);
			}
		}
		if (resultList == null || resultList.isEmpty()) {
			modelAndView.addObject("isEmpty", true);
		}
		modelAndView.addObject("list", resultList);
		modelAndView.addObject("openid", openId);
		// 分享参数
		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
		String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
		String url2 = UrlHelp.getFullRequestURL(request);
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);

		modelAndView.addObject("timestamp", ret.get("timestamp"));
		modelAndView.addObject("nonceStr", ret.get("nonceStr"));
		modelAndView.addObject("signature", ret.get("signature"));
		return modelAndView;
	}

	/**
	 * 根据手机号查询订单
	 * 
	 * @param phone
	 * @return
	 */
	@RequestMapping("/getOrderListByPhone")
	public ModelAndView getOrderListByPhone(String phone, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/pages/payment/farmDetail");

		List<FarmOrder> list = farmOrderBiz.getOrderListByPhone(phone);
		if (list == null || list.isEmpty()) {
			modelAndView.addObject("isEmpty", true);
		}
		modelAndView.addObject("list", list);
		// 分享参数
		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
		String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
		String url2 = UrlHelp.getFullRequestURL(request);
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);

		modelAndView.addObject("timestamp", ret.get("timestamp"));
		modelAndView.addObject("nonceStr", ret.get("nonceStr"));
		modelAndView.addObject("signature", ret.get("signature"));
		return modelAndView;
	}

	/**
	 * 传订单号，到转盘
	 * 
	 * @param sku
	 * @return
	 */
	@RequestMapping("/getOrderNoToLottery")
	public ModelAndView getOrderNoToLottery(String sku, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/pages/lottery");
		// 分享参数
		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
		String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
		String url2 = UrlHelp.getFullRequestURL(request);
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);

		modelAndView.addObject("timestamp", ret.get("timestamp"));
		modelAndView.addObject("nonceStr", ret.get("nonceStr"));
		modelAndView.addObject("signature", ret.get("signature"));
		return modelAndView;
	}

	/**
	 * 跳转到成功页面,通过手机查收货人、地址
	 * 
	 * @param sku
	 * @return
	 */
	@RequestMapping("/getFarmSuccessPage")
	public ModelAndView getFarmSuccessPage(String phone, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/pages/payment/farmSuccess");

		List<FarmOrder> list = farmOrderBiz.getOrderList(phone);
		modelAndView.addObject("list", list);
		// 分享参数
		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
		String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
		String url2 = UrlHelp.getFullRequestURL(request);
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);

		modelAndView.addObject("timestamp", ret.get("timestamp"));
		modelAndView.addObject("nonceStr", ret.get("nonceStr"));
		modelAndView.addObject("signature", ret.get("signature"));
		return modelAndView;
	}

	/**
	 * 订单详情页（带着sku跳）
	 * 
	 * @param phone
	 * @return
	 */
	@RequestMapping("/getFarmDetailMap")
	public ModelAndView getFarmDetailMap(String sku, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/pages/payment/farmDetailMap");

		List<FarmOrder> list = farmOrderBiz.getDetailMap(sku);
		modelAndView.addObject("list", list);
		modelAndView.addObject("openid", list.get(0).getOpenId());
		// 分享参数
		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
		String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
		String url2 = UrlHelp.getFullRequestURL(request);
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);

		modelAndView.addObject("timestamp", ret.get("timestamp"));
		modelAndView.addObject("nonceStr", ret.get("nonceStr"));
		modelAndView.addObject("signature", ret.get("signature"));
		return modelAndView;
	}
	
	/**
	 * 根据sku查询当前所下的订单
	 * 
	 * @param phone
	 * @return
	 */
	@RequestMapping("/getOrderListBySku")
	public ModelAndView getOrderListBySku(String sku, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/pages/payment/farmDetailMap");

		List<FarmOrder> list = farmOrderBiz.getOrderListBySku(sku);
		modelAndView.addObject("list", list);
		modelAndView.addObject("openid", list.get(0).getOpenId());
		// 分享参数
		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret, request);
		String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
		String url2 = UrlHelp.getFullRequestURL(request);
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);

		modelAndView.addObject("timestamp", ret.get("timestamp"));
		modelAndView.addObject("nonceStr", ret.get("nonceStr"));
		modelAndView.addObject("signature", ret.get("signature"));
		return modelAndView;
	}

	/**
	 * PC 订单页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getWebFarmOrder")
	public ModelAndView getWebFarmOrder(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/pages/farmorder/orderList");
		return modelAndView;
	}

	/**
	 * 分页获取JSON数据
	 */
	@RequestMapping("/getFarmOrderListJSON")
	@ResponseBody
	public JSONObject getFarmOrderListJSON(@RequestParam(value = "page", required = false) String pageNowParam,
			@RequestParam(value = "rows", required = false) String pageSizeParam) {

		Integer pageNow = StringConverters.ToInteger(pageNowParam);
		Integer pageSize = StringConverters.ToInteger(pageSizeParam);

		if (pageNow == null || pageSize == null) {
			pageNow = 1;
			pageSize = DEFAULT_PAGE_LIST_NUM;
		}

		List<FarmOrder> farmorderList = farmOrderBiz.findFarmOrder(pageNow, pageSize);// 分页展示
		Long totalNum = farmOrderBiz.totalGrapeVolume();// 总数

		return FarmOrderConverter.getJson(farmorderList, totalNum);
	}

	/**
	 * 导出未发货
	 * 
	 * @param response
	 */
	@RequestMapping(value = "/FarmOrderExcl1", method = RequestMethod.GET)
	@ResponseBody
	public void getFarmOrderExcl1(HttpServletResponse response) {
		farmOrderBiz.getFarmOrderExport1(response);

	}

	/**
	 * 导出全部
	 * 
	 * @param response
	 */
	@RequestMapping(value = "/FarmOrderExcl", method = RequestMethod.GET)
	@ResponseBody
	public void getFarmOrderExcl(HttpServletResponse response) {
		farmOrderBiz.getFarmOrderExport(response);

	}
	
	/**
	 * 通过链接 的用户 所打开的 立减15元 的页面
	 */
	@RequestMapping("/getFarmOrderPage_share")
	public ModelAndView getFarmOrderPage_share(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/wxpay/farmOrder_share");
		modelAndView.addObject("type", request.getParameter("type"));
		modelAndView.addObject("openid", request.getParameter("openid"));
		// 分享参数
		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
		String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
		String url2 = UrlHelp.getFullRequestURL(request);
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);

		modelAndView.addObject("timestamp", ret.get("timestamp"));
		modelAndView.addObject("nonceStr", ret.get("nonceStr"));
		modelAndView.addObject("signature", ret.get("signature"));
		return modelAndView;

	}
	
	/**
	 * 通过链接 的用户 所打开的 立减15元 的 添加订单/提交订单
	 * 
	 * @param farmOrder
	 * @param request
	 * @return
	 */
	@RequestMapping("/addFarmOrder_share")
	public ModelAndView addFarmOrder_share(String sku, String buyOpenId, HttpServletRequest request,HttpServletResponse response) {
		String code = request.getParameter("code");
		logger.info("code is {}", code);
		if (StringUtils.isEmpty(code)) {
			String url = null;
			try {
				url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + ParamesAPI.appId + "&redirect_uri="
						+ URLEncoder.encode(UrlHelp.getUrlPathWithContextNoPort(request) + "/web/farm/addFarmOrder_share.action?buyOpenId="
								+buyOpenId + "&sku=" + sku, "utf-8") + "&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
						response.sendRedirect(url);
						return null;
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + ParamesAPI.appId + "&secret="
				+ ParamesAPI.secret + "&code=" + code + "&grant_type=authorization_code";
		JSONObject result = WeixinUtil.httpRequest(url, "GET", null);
		String openid = "";
		try {
			openid = (String) result.get("openid");
		} catch (Exception e) {
			logger.info("code失效！");
		}
		logger.info("open id is {}", openid);
		logger.info("sku is {}", sku);
		logger.info("buyOpenId is {}", buyOpenId);
		FarmOrder farmOrder = farmOrderBiz.getOrderByNo(sku, buyOpenId);
		ModelAndView modelAndView = new ModelAndView();
		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
		if (farmOrder != null && farmOrder.getStatus() == -2) {
			//主动调接口查询订单状态
			String sign = WechatPayUtil.signForQuery(sku);
			String params = "<xml>\n" + "<appid><![CDATA[" + ParamesAPI.appId + "]]></appid>\n" + "<mch_id><![CDATA[" + ParamesAPI.partner
					+ "]]></mch_id>\n" + "<nonce_str><![CDATA[D380BFC2BAD727A6B6845193519E3AD6]]></nonce_str>\n"
					+ "<out_trade_no><![CDATA[" + sku + "]]></out_trade_no>\n"
					+ "<sign><![CDATA["+sign+"]]></sign>\n</xml>";
			String queryResult = null;
			try {
				queryResult = new HttpsRequest().sendPost("https://api.mch.weixin.qq.com/pay/orderquery", params);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//支付成功
			if (queryResult.contains("<trade_state><![CDATA[SUCCESS]]></trade_state>")) {
				farmOrder.setCreateDate(new Date());
				farmOrder.setUpdateDate(new Date());
				farmOrder.setStatus(-1);
				farmOrderBiz.updateOrder(farmOrder);
				/* 2016.12.05 注释 取消分佣 */
				/*logger.info("开始添加佣金信息");
				List<ShareRecord> list = shareRecordBiz.get(openid); // 当前购买者所有的分享人,现在理论只能有一个，最早的一个
				if (!list.isEmpty()) {
					ShareRecord share = list.get(list.size() - 1);  // 只拿首次分享给他的人
					ShareRecord model = new ShareRecord();
					model.setOpenId(openid);
					model.setShareOpenId(share.getShareOpenId());
					model.setShareNickName(share.getShareNickName());
					model.setCreateDate(new Date());
					// 记录购买数量
					model.setNumber(Integer.valueOf(farmOrder.getWeight()));
					model.setSendRedPack(0);
					model.setNickname(farmOrder.getNickname());
					model.setSku(farmOrder.getSku());
					shareRecordBiz.addOrUpdate(model);
				} else {
					if (farmOrder.getShareOpenId() != null) {
						// 购买者通过他人分享的链接进行购买
						ShareRecord shareRecord = new ShareRecord();
						shareRecord.setOpenId(openid);
						// 获取分享人的昵称
						shareRecord.setShareNickName(farmOrder.getShareNickname());
						shareRecord.setShareOpenId(farmOrder.getShareOpenId());
						shareRecord.setCreateDate(new Date());
						// 记录购买数量
						shareRecord.setNumber(Integer.valueOf(farmOrder.getWeight()));
						shareRecord.setSendRedPack(0);
						shareRecord.setNickname(farmOrder.getNickname());
						shareRecord.setSku(farmOrder.getSku());
						shareRecordBiz.addOrUpdate(shareRecord);
					}
				}*/
				modelAndView.setViewName("/pages/payment/farmSuccess_share");
				modelAndView.addObject("farmOrder", farmOrder);
				// 分享参数
				String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
				String url2 = UrlHelp.getFullRequestURL(request);
				Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);
				modelAndView.addObject("timestamp", ret.get("timestamp"));
				modelAndView.addObject("nonceStr", ret.get("nonceStr"));
				modelAndView.addObject("signature", ret.get("signature"));
				
				// 下单完成立马发放5元红包,2016.12.9
				//recordBiz.payMoney(openid, Integer.valueOf(farmOrder.getWeight()) * 5);
			} else {
				modelAndView.addObject("error", "支付失败或取消支付(如您已付款成功，请十分钟后再次回到首页查询订单)!");
				modelAndView.setViewName("pages/error");
			}
		} else if (farmOrder != null) {
			modelAndView.setViewName("/pages/payment/farmSuccess_share");
			modelAndView.addObject("farmOrder", farmOrder);
			// 分享参数
			String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
			String url2 = UrlHelp.getFullRequestURL(request);
			Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);
			modelAndView.addObject("timestamp", ret.get("timestamp"));
			modelAndView.addObject("nonceStr", ret.get("nonceStr"));
			modelAndView.addObject("signature", ret.get("signature"));
			
			// 下单完成立马发放5元红包,2016.12.9
			//recordBiz.payMoney(openid, Integer.valueOf(farmOrder.getWeight()) * 5);
		} else {
			modelAndView.addObject("error", "支付失败或取消支付(如您已付款成功，请十分钟后再次回到首页查询订单)!");
			modelAndView.setViewName("pages/error");
		}
		return modelAndView;
	}
	
	/**
	 * 通过链接 的用户 所打开的 圣诞活动 的 添加订单/提交订单
	 * 
	 * @param farmOrder
	 * @param request
	 * @return
	 */
	@RequestMapping("/addFarmOrder_chris")
	public ModelAndView addFarmOrder_chris(String sku, String buyOpenId, HttpServletRequest request,HttpServletResponse response) {
		String code = request.getParameter("code");
		logger.info("code is {}", code);
		if (StringUtils.isEmpty(code)) {
			String url = null;
			try {
				url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + ParamesAPI.appId + "&redirect_uri="
						+ URLEncoder.encode(UrlHelp.getUrlPathWithContextNoPort(request) + "/web/farm/addFarmOrder_chris.action?buyOpenId="
								+buyOpenId + "&sku=" + sku, "utf-8") + "&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
						response.sendRedirect(url);
						return null;
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + ParamesAPI.appId + "&secret="
				+ ParamesAPI.secret + "&code=" + code + "&grant_type=authorization_code";
		JSONObject result = WeixinUtil.httpRequest(url, "GET", null);
		String openid = "";
		try {
			openid = (String) result.get("openid");
		} catch (Exception e) {
			logger.info("code失效！");
		}
		logger.info("open id is {}", openid);
		logger.info("sku is {}", sku);
		logger.info("buyOpenId is {}", buyOpenId);
		FarmOrder farmOrder = farmOrderBiz.getOrderByNo(sku, buyOpenId);
		ModelAndView modelAndView = new ModelAndView();
		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
		if (farmOrder != null && farmOrder.getStatus() == -2) {
			//主动调接口查询订单状态
			String sign = WechatPayUtil.signForQuery(sku);
			String params = "<xml>\n" + "<appid><![CDATA[" + ParamesAPI.appId + "]]></appid>\n" + "<mch_id><![CDATA[" + ParamesAPI.partner
					+ "]]></mch_id>\n" + "<nonce_str><![CDATA[D380BFC2BAD727A6B6845193519E3AD6]]></nonce_str>\n"
					+ "<out_trade_no><![CDATA[" + sku + "]]></out_trade_no>\n"
					+ "<sign><![CDATA["+sign+"]]></sign>\n</xml>";
			String queryResult = null;
			try {
				queryResult = new HttpsRequest().sendPost("https://api.mch.weixin.qq.com/pay/orderquery", params);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//支付成功
			if (queryResult.contains("<trade_state><![CDATA[SUCCESS]]></trade_state>")) {
				farmOrder.setCreateDate(new Date());
				farmOrder.setUpdateDate(new Date());
				farmOrder.setStatus(-1);
				farmOrderBiz.updateOrder(farmOrder);
				/* 2016.12.05 注释 取消分佣 */
				/*logger.info("开始添加佣金信息");
				List<ShareRecord> list = shareRecordBiz.get(openid); // 当前购买者所有的分享人,现在理论只能有一个，最早的一个
				if (!list.isEmpty()) {
					ShareRecord share = list.get(list.size() - 1);  // 只拿首次分享给他的人
					ShareRecord model = new ShareRecord();
					model.setOpenId(openid);
					model.setShareOpenId(share.getShareOpenId());
					model.setShareNickName(share.getShareNickName());
					model.setCreateDate(new Date());
					// 记录购买数量
					model.setNumber(Integer.valueOf(farmOrder.getWeight()));
					model.setSendRedPack(0);
					model.setNickname(farmOrder.getNickname());
					model.setSku(farmOrder.getSku());
					shareRecordBiz.addOrUpdate(model);
				} else {
					if (farmOrder.getShareOpenId() != null) {
						// 购买者通过他人分享的链接进行购买
						ShareRecord shareRecord = new ShareRecord();
						shareRecord.setOpenId(openid);
						// 获取分享人的昵称
						shareRecord.setShareNickName(farmOrder.getShareNickname());
						shareRecord.setShareOpenId(farmOrder.getShareOpenId());
						shareRecord.setCreateDate(new Date());
						// 记录购买数量
						shareRecord.setNumber(Integer.valueOf(farmOrder.getWeight()));
						shareRecord.setSendRedPack(0);
						shareRecord.setNickname(farmOrder.getNickname());
						shareRecord.setSku(farmOrder.getSku());
						shareRecordBiz.addOrUpdate(shareRecord);
					}
				}*/
				modelAndView.setViewName("/pages/payment/farmSuccess_chris");
				modelAndView.addObject("farmOrder", farmOrder);
				// 分享参数
				String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
				String url2 = UrlHelp.getFullRequestURL(request);
				Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);
				modelAndView.addObject("timestamp", ret.get("timestamp"));
				modelAndView.addObject("nonceStr", ret.get("nonceStr"));
				modelAndView.addObject("signature", ret.get("signature"));
				
				// 下单完成立马发放5元红包,2016.12.9
				//recordBiz.payMoney(openid, Integer.valueOf(farmOrder.getWeight()) * 5);
			} else {
				modelAndView.addObject("error", "支付失败或取消支付(如您已付款成功，请十分钟后再次回到首页查询订单)!");
				modelAndView.setViewName("pages/error");
			}
		} else if (farmOrder != null) {
			modelAndView.setViewName("/pages/payment/farmSuccess_chris");
			modelAndView.addObject("farmOrder", farmOrder);
			// 分享参数
			String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
			String url2 = UrlHelp.getFullRequestURL(request);
			Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);
			modelAndView.addObject("timestamp", ret.get("timestamp"));
			modelAndView.addObject("nonceStr", ret.get("nonceStr"));
			modelAndView.addObject("signature", ret.get("signature"));
			
			// 下单完成立马发放5元红包,2016.12.9
			//recordBiz.payMoney(openid, Integer.valueOf(farmOrder.getWeight()) * 5);
		} else {
			modelAndView.addObject("error", "支付失败或取消支付(如您已付款成功，请十分钟后再次回到首页查询订单)!");
			modelAndView.setViewName("pages/error");
		}
		return modelAndView;
	}
}
