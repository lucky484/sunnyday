package com.f2b.security.action.dashboard;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import org.springframework.web.servlet.ModelAndView;

import com.f2b.security.business.biz.FarmOrderBiz;
import com.f2b.security.business.biz.FarmUserBiz;
import com.f2b.security.business.biz.RecordBiz;
import com.f2b.security.domain.FarmOrder;
import com.f2b.security.domain.FarmProduce;
import com.f2b.sugar.tools.Base64;
import com.f2b.sugar.tools.SignUtil;
import com.f2b.sugar.tools.pay.http.UrlHelp;
import com.f2b.sugar.wxlib.ParamesAPI.AccessToken;
import com.f2b.sugar.wxlib.ParamesAPI.ParamesAPI;
import com.f2b.sugar.wxlib.ParamesAPI.WeixinUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/web/farm")
public class FarmProduceAction {

	private final static Logger logger = LoggerFactory.getLogger(FarmProduceAction.class);

	@Autowired
	private FarmOrderBiz farmOrderBiz;

	@Autowired
	private RecordBiz recordBiz;

	@Autowired
	private FarmUserBiz farmUserBiz;

	private static int idFlagSeed = 1;

	/**
	 * 打开商品列表页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getProduceList")
	public ModelAndView getProduceList(FarmProduce model) {
		ModelAndView view = new ModelAndView();
		view.setViewName("/pages/payment/produceList");
		// List<FarmProduce> list = farmProduceBiz.getProduceList(model);
		// view.addObject("list",list);
		return view;
	}

	/**
	 * 打开商品详情页面（提子） Date:16/08/20
	 */
	@RequestMapping("/getFarmProducePageForGrape")
	public ModelAndView getFarmOrderPage(HttpServletRequest request, HttpServletResponse response) {

		String idFlag = genNewIdFlag();
		request.getSession().setAttribute("id_flag", idFlag);

		ModelAndView modelAndView = new ModelAndView();

		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		logger.info("ip is {}", ip);

		// 部署服务器开启
		String code = request.getParameter("code");
		logger.info("{} code is {}", idFlag, code);
		if (StringUtils.isEmpty(code)) {
			String url = null;
			try {
				url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + ParamesAPI.appId + "&redirect_uri="
						+ URLEncoder.encode(UrlHelp.getUrlPathWithContextNoPort(request)
								+ "/web/farm/getFarmProducePageForGrape.action", "utf-8")
						+ "&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

			try {
				response.sendRedirect(url);
				return null;
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
		if (StringUtils.isBlank(openid)) {
			String openid1 = (String) request.getSession().getAttribute("openid");
			if (StringUtils.isNotEmpty(openid1)) {
				logger.info("session中有用户");
				openid = openid1;
			}
		}
		logger.info("{} open id is {}", idFlag, openid);
		request.getSession().setAttribute("openid", openid);
		String nickname = "";

		if (StringUtils.isEmpty(openid)) {
			logger.warn("{} openid must not null, so quit........", idFlag);
			modelAndView.setViewName("/pages/payment/farmProduce");
		} else {
			// 至此,OPENID一定找到了,下面判断是否为已关注
			AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
			String getInfoURL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken.getToken()
					+ "&openid=" + openid + "&lang=zh_CN";
			JSONObject result2 = WeixinUtil.httpRequest(getInfoURL, "GET", null);
			logger.info("{} user info:{}", idFlag, result2);

			try {
				nickname = result2.getString("nickname");
				request.getSession().setAttribute("nickname", Base64.getBase64(nickname));
				logger.info("Base64.getBase64(nickname) is {}", Base64.getBase64(nickname));
				logger.info("nickname is {}", nickname);
			} catch (Exception e) {
				logger.info("位置错误！");
			}
			/**
			 * 记录从哪个入口进的
			 */
			if ("1".equalsIgnoreCase((result2.get("subscribe") + ""))) {
				modelAndView.setViewName("/pages/payment/farmProduce");
				logger.info("{} Thanks, you are a subscriber.", idFlag);
			} else {
				modelAndView.setViewName("pages/watchQR");
				logger.warn("{} you are not a subscriber.", idFlag);
				return modelAndView;
			}
		}
		modelAndView.setViewName("/pages/payment/farmProduce");
		Long volume = farmOrderBiz.totalGrapeVolume();
		modelAndView.addObject("volume", volume);

		// List<FarmProduce> list =
		// farmProduceBiz.getFarmProducePage(produceId);
		// modelAndView.addObject("list", list);

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
	 * 打开商品详情页面（苹果） Date:16/08/20
	 */
	@RequestMapping("/getFarmProducePage")
	public ModelAndView getFarmProduceApplePage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		String shareOpenId = request.getParameter("share_openid"); // 分享人的openid或者店铺的id
		String code = request.getParameter("code");
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		logger.info("ip is {}", ip);
		// 部署服务器开启x
		logger.info("code is {}", code);
		logger.info("url:" + request.getRequestURI());
		if (StringUtils.isEmpty(code)) {
			logger.info("当前url后面的参数:" + request.getQueryString());
			if (request.getQueryString() == null) {
				logger.info("通过公众号链接进入首页");
			}
			String url = null;
			try {
				if (shareOpenId == null) {
					url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + ParamesAPI.appId
							+ "&redirect_uri="
							+ URLEncoder.encode(UrlHelp.getUrlPathWithContextNoPort(request)
									+ "/web/farm/getFarmProducePage.action", "utf-8")
							+ "&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
				} else {
					url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + ParamesAPI.appId
							+ "&redirect_uri="
							+ URLEncoder.encode(
									UrlHelp.getUrlPathWithContextNoPort(request)
											+ "/web/farm/getFarmProducePage.action?share_openid=" + shareOpenId,
									"utf-8")
							+ "&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
				}
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			try {
				response.sendRedirect(url);
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + ParamesAPI.appId + "&secret="
				+ ParamesAPI.secret + "&code=" + code + "&grant_type=authorization_code";
		JSONObject result = WeixinUtil.httpRequest(url, "GET", null);
		String openid = "";
		String token = null;
		try {
			openid = (String) result.get("openid");
			token = result.getString("access_token");
		} catch (Exception e) {
			logger.info("code失效！");
			try {
				response.sendRedirect(
						UrlHelp.getUrlPathWithContextNoPort(request) + "/web/farm/getFarmProducePage.action");
				return null;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (StringUtils.isBlank(openid)) {
			String openid1 = (String) request.getSession().getAttribute("openid");
			if (StringUtils.isNotEmpty(openid1)) {
				logger.info("session中有用户");
				openid = openid1;
			}
		}

		logger.info("open id is {}", openid);
		request.getSession().setAttribute("openid", openid);// 原本注释
		String nickname = null, shareNickname = null;
		// AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId,
		// ParamesAPI.secret);//原本注释
		if (StringUtils.isEmpty(openid)) {
			logger.warn("openid must not null, so quit........");
			modelAndView.setViewName("/pages/payment/farmProduceApple");
		} else {
			// 至此,OPENID一定找到了
			String getInfoURL = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openid
					+ "&lang=zh_CN";// 授权
			// String getInfoURL =
			// "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" +
			// accessToken.getToken() + "&openid=" + openid +
			// "&lang=zh_CN";//必须关注
			JSONObject result2 = WeixinUtil.httpRequest(getInfoURL, "GET", null);
			logger.info("user info:{}", result2);

			try {
				nickname = result2.getString("nickname");
				logger.info("Base64.getBase64(nickname) is {}", Base64.getBase64(nickname));
				logger.info("购买人昵称{}", nickname);
			} catch (Exception e) {
				logger.info("购买人昵称错误！");
			}

			FarmOrder farmOrder = new FarmOrder();
			farmOrder.setOpenId(openid);
			farmOrder.setNickname(Base64.getBase64(nickname));
			if (shareOpenId != null) {
				farmOrder.setShareOpenId(shareOpenId);
				List<FarmOrder> oldList = farmOrderBiz.getAllOrderListByOpenId(shareOpenId);
				if (oldList != null && !oldList.isEmpty()) {
					FarmOrder oldOrder = oldList.get(0);
					shareNickname = oldOrder.getNickname(); 
					farmOrder.setShareNickname(shareNickname);
				}
			}

			farmOrder.setStatus(-2);
			farmOrder.setCreateDate(new Date());
			farmOrderBiz.addFarmOrder(farmOrder);

			/**
			 * 记录从哪个入口进的
			 */
			/*
			 * if ("1".equalsIgnoreCase((result2.get("subscribe") + ""))) {
			 * modelAndView.setViewName("/pages/payment/farmProduceApple");
			 * logger.info("{} Thanks, you are a subscriber.", idFlag); } else {
			 * modelAndView.setViewName("pages/watchQR"); logger.warn(
			 * "{} you are not a subscriber.", idFlag); return modelAndView; }
			 */
		}
		modelAndView.setViewName("/pages/payment/farmProduceApple");
		Long volume = farmOrderBiz.totalGrapeVolume();
		modelAndView.addObject("volume", volume);
		modelAndView.addObject("openid", openid);
		// 当前微信号所有订单
		List<FarmOrder> orderList = farmOrderBiz.getAllSuccessPromoteOrder(openid);
		modelAndView.addObject("successPromoteOrderNo", orderList.size());
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
	 * 用于在日志前面加上唯一的ID.
	 */
	private static String genNewIdFlag() {
		return (idFlagSeed++) + "_";
	}

	/**
	 * 打开商品详情页面（苹果） Date:16/12/14
	 */
	// @RequestMapping("/getFarmProducePage_share")
	// public ModelAndView getFarmProduceApplePage_share(HttpServletRequest
	// request, HttpServletResponse response) {
	// ModelAndView modelAndView = new ModelAndView();
	// //String shareOpenId = request.getParameter("share_openid"); //
	// 分享人的openid或者店铺的id
	// String code = request.getParameter("code");
	// String ip = request.getHeader("x-forwarded-for");
	// if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	// ip = request.getHeader("Proxy-Client-IP");
	// }
	// if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	// ip = request.getHeader("WL-Proxy-Client-IP");
	// }
	// if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	// ip = request.getRemoteAddr();
	// }
	// logger.info("ip is {}", ip);
	// // 部署服务器开启x
	// logger.info("code is {}", code);
	// logger.info("url:" + request.getRequestURI());
	// if (StringUtils.isEmpty(code)) {
	// logger.info("当前url后面的参数:" + request.getQueryString());
	// if (request.getQueryString() == null) {
	// logger.info("通过公众号链接进入首页");
	// } /*else if (request.getQueryString().contains("share_openid") &&
	// (shareOpenId == null || shareOpenId.equals("") ||
	// shareOpenId.equals("null"))) {
	// logger.info("分享链接存在异常，找不到分享人，退出");
	// modelAndView.addObject("error","分享链接存在异常，找不到分享人，请联系分享人重新分享");
	// modelAndView.setViewName("/pages/error");
	// return modelAndView;
	// }*/
	// String url = null;
	// try {
	//// if (shareOpenId == null) {
	//// url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
	// ParamesAPI.appId + "&redirect_uri="
	//// + URLEncoder.encode(UrlHelp.getUrlPathWithContextNoPort(request) +
	// "/web/farm/getFarmProducePage.action", "utf-8")
	//// +
	// "&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
	//// } else {
	//// url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
	// ParamesAPI.appId + "&redirect_uri="
	//// + URLEncoder.encode(UrlHelp.getUrlPathWithContextNoPort(request) +
	// "/web/farm/getFarmProducePage.action?share_openid=" + shareOpenId,
	// "utf-8")
	//// +
	// "&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
	//// }
	// //禁用分享后的连接
	// url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
	// ParamesAPI.appId + "&redirect_uri="
	// + URLEncoder.encode(UrlHelp.getUrlPathWithContextNoPort(request) +
	// "/web/farm/getFarmProducePage_share.action", "utf-8")
	// + "&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
	// } catch (UnsupportedEncodingException e1) {
	// e1.printStackTrace();
	// }
	//
	// try {
	// response.sendRedirect(url);
	// return null;
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" +
	// ParamesAPI.appId + "&secret=" + ParamesAPI.secret + "&code=" + code +
	// "&grant_type=authorization_code";
	// JSONObject result = WeixinUtil.httpRequest(url, "GET", null);
	//
	// String openid = "";
	// String token = null;
	// try {
	// openid = (String) result.get("openid");
	// token = result.getString("access_token");
	// } catch (Exception e) {
	// logger.info("code失效！");
	//// try {
	//// response.sendRedirect(UrlHelp.getUrlPathWithContextNoPort(request) +
	// "/web/farm/getFarmProducePage.action?share_openid=" + shareOpenId);
	//// } catch (IOException e1) {
	//// e1.printStackTrace();
	//// }
	// }
	//// if (StringUtils.isBlank(openid)) {
	//// String openid1 = (String) request.getSession().getAttribute("openid");
	//// if (StringUtils.isNotEmpty(openid1)) {
	//// logger.info("session中有用户");
	//// openid = openid1;
	//// }
	//// }
	// logger.info("open id is {}", openid);
	// //request.getSession().setAttribute("openid", openid);
	// String nickname = null,shareNickname = null;
	// //AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId,
	// ParamesAPI.secret);
	// if (StringUtils.isEmpty(openid)) {
	// logger.warn("openid must not null, so quit........");
	// modelAndView.setViewName("/pages/payment/farmProduceApple_share");
	// } else {
	// // 至此,OPENID一定找到了
	// String getInfoURL =
	// "https://api.weixin.qq.com/sns/userinfo?access_token=" + token +
	// "&openid=" + openid + "&lang=zh_CN";
	// JSONObject result2 = WeixinUtil.httpRequest(getInfoURL, "GET", null);
	// logger.info("user info:{}", result2);
	//
	// try {
	// nickname = result2.getString("nickname");
	// logger.info("Base64.getBase64(nickname) is {}",
	// Base64.getBase64(nickname));
	// logger.info("购买人昵称{}", nickname);
	// } catch (Exception e) {
	// logger.info("购买人昵称错误！");
	// }
	//
	// FarmOrder farmOrder = new FarmOrder();
	// farmOrder.setOpenId(openid);
	// farmOrder.setNickname(Base64.getBase64(nickname));
	// // 有分享人
	//// if (shareOpenId != null) {
	//// farmOrder.setShareOpenId(shareOpenId);
	//// // 分享人访问过的记录，包括下过的订单
	//// List<FarmOrder> oldList =
	// farmOrderBiz.getAllOrderListByOpenId(shareOpenId);
	//// if (oldList != null && !oldList.isEmpty()) {
	//// FarmOrder oldOrder = oldList.get(0);
	//// shareNickname = oldOrder.getNickname(); //以前记录的昵称就是分享人的昵称
	//// farmOrder.setShareNickname(shareNickname);
	//// }
	//// }
	// farmOrder.setStatus(-2);
	// farmOrder.setCreateDate(new Date());
	// farmOrderBiz.addFarmOrder(farmOrder);
	// /**
	// * 记录从哪个入口进的
	// */
	// /*if ("1".equalsIgnoreCase((result2.get("subscribe") + ""))) {
	// modelAndView.setViewName("/pages/payment/farmProduceApple");
	// logger.info("{} Thanks, you are a subscriber.", idFlag);
	// } else {
	// modelAndView.setViewName("pages/watchQR");
	// logger.warn("{} you are not a subscriber.", idFlag);
	// return modelAndView;
	// }*/
	// }
	// modelAndView.setViewName("/pages/payment/farmProduceApple_share");
	// Long volume = farmOrderBiz.totalGrapeVolume();
	// modelAndView.addObject("volume", volume);
	// modelAndView.addObject("openid", openid);
	// // 当前微信号所有订单
	// List<FarmOrder> orderList = farmOrderBiz.getOrderListByOpenId(openid);
	// if (orderList == null || orderList.isEmpty()) {
	// modelAndView.addObject("hint", true);
	// } else {
	// for (FarmOrder order : orderList) {
	// if (order.getStatus() == -2 && !StringUtils.isEmpty(order.getSku())) {
	// String sign = WechatPayUtil.signForQuery(order.getSku());
	// String params = "<xml>\n" + "<appid><![CDATA[" + ParamesAPI.appId +
	// "]]></appid>\n" + "<mch_id><![CDATA[" + ParamesAPI.partner
	// + "]]></mch_id>\n" +
	// "<nonce_str><![CDATA[D380BFC2BAD727A6B6845193519E3AD6]]></nonce_str>\n"
	// + "<out_trade_no><![CDATA[" + order.getSku() + "]]></out_trade_no>\n"
	// + "<sign><![CDATA["+sign+"]]></sign>\n</xml>";
	// String queryResult = null;
	// try {
	// queryResult = new
	// HttpsRequest().sendPost("https://api.mch.weixin.qq.com/pay/orderquery",
	// params);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// // 微信端支付成功
	// if
	// (queryResult.contains("<trade_state><![CDATA[SUCCESS]]></trade_state>"))
	// {
	// order.setStatus(-1);
	// order.setUpdateDate(new Date());
	// farmOrderBiz.updateOrder(order);
	// modelAndView.addObject("orderNo", order.getSku());
	// break;
	// }
	// } else if (order.getSku() != null) {
	// // 订单相关抽奖次数
	// Long recordNum = recordBiz.findRecordNumByOrderNo(order.getSku());
	// // 抽奖没抽完
	// if (Integer.valueOf(order.getWeight()) > recordNum) {
	// // 没抽奖完的订单
	// modelAndView.addObject("orderNo", order.getSku());
	// break;
	// }
	// }
	// }
	// }
	// AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId,
	// ParamesAPI.secret);
	// String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(),
	// request);
	// String url2 = UrlHelp.getFullRequestURL(request);
	// Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);
	//
	// modelAndView.addObject("timestamp", ret.get("timestamp"));
	// modelAndView.addObject("nonceStr", ret.get("nonceStr"));
	// modelAndView.addObject("signature", ret.get("signature"));
	// return modelAndView;
	//
	// }
}
