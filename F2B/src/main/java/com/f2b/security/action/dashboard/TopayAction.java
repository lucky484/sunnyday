package com.f2b.security.action.dashboard;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.f2b.security.business.biz.FarmOrderBiz;
import com.f2b.security.domain.FarmOrder;
import com.f2b.sugar.tools.SignUtil;
import com.f2b.sugar.tools.pay.CommonUtil;
import com.f2b.sugar.tools.pay.GetWxOrderno;
import com.f2b.sugar.tools.pay.RequestHandler;
import com.f2b.sugar.tools.pay.Sha1Util;
import com.f2b.sugar.tools.pay.TenpayUtil;
import com.f2b.sugar.tools.pay.http.UrlHelp;
import com.f2b.sugar.wxlib.ParamesAPI.AccessToken;
import com.f2b.sugar.wxlib.ParamesAPI.ParamesAPI;
import com.f2b.sugar.wxlib.ParamesAPI.WeixinUtil;

import net.sf.json.JSONObject;

/**
 * 
 * @ClassName: TopayAction
 * @Description: 用户微信支付之统一支付接口调用, 并返回支付页面
 * @author ligang.yang@softtek.com
 * @date 2016年8月18日 上午10:32:57
 *
 */
@Controller
@RequestMapping("wxpay/pay-2") // 支付状态为产生预支付账单号
public class TopayAction {
	@Autowired
	private FarmOrderBiz farmOrderBiz;

	@RequestMapping("pre")
	public ModelAndView topay2(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		ModelAndView view = new ModelAndView();
		// 网页授权后获取传递的参数
		// String userId = request.getParameter("userId");
		// String orderNo = request.getParameter("orderNo");
		// String money = request.getParameter("money");

		// 获取支付的相关信息
		String orderId = request.getParameter("orderId");
		// String userId = "b88001";
		String code = request.getParameter("code");
		String appid = ParamesAPI.appId;

		// 商户相关资料
		String appsecret = ParamesAPI.appsecret;
		String partner = ParamesAPI.partner;
		String partnerkey = ParamesAPI.partnerkey;

		// 获取openId
		String openId = "";
		String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + appsecret
				+ "&code=" + code + "&grant_type=authorization_code";

		JSONObject jsonObject = CommonUtil.httpsRequest(URL, "GET", null);
		if (null != jsonObject) {
			try {
				openId = jsonObject.getString("openid");
			} catch (Exception e) {
				view.addObject("ErrorMsg", "获取预付订单号出错");
				view.setViewName("wxpay/error");
				return view;
			}
		}
		FarmOrder order = farmOrderBiz.getById(Long.valueOf(orderId));
		String money = order.getTotal().toString();
		// -- test-- //
		// money = "0.01"; // 测试暂时设为1分钱

		if (openId.equals("oQiB5wIJxsgObghXZuwV5JvTDR0U") || openId.equals("oQiB5wOIzxE7mOqxHUttx84a8kIY")
				|| openId.equals("oQiB5wEf8ngI2Nk4doDAk5OsvY2U") || openId.equals("oQiB5wMOHhRL-FQSOh4LrItUjKvE")
				|| openId.equals("oQiB5wCRTVSj1ahL-CQpd6HwjdmM") || openId.equals("oQiB5wMSs3Lk4slhPIIT0DH2RTmA")
				|| openId.equals("oQiB5wPC1BuF8C3mlwlYwflWCAiI") || openId.equals("oQiB5wFn0Nk91IfBj0y9g4RIWlkA")
				|| openId.equals("oQiB5wIywKkQKth56FnmU_NmN-d4")) {
			money = "0.01";
		}

		// -- end test-- //
		// 金额转化为分为单位
		float sessionmoney = Float.parseFloat(money);
		String finalmoney = String.format("%.2f", sessionmoney);
		finalmoney = finalmoney.replace(".", "");

		// 获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		String strReq = strTime + strRandom;

		// 商户号
		String mch_id = partner;
		// 子商户号 非必输
		// String sub_mch_id="";
		// 设备号 非必输
		String device_info = "";
		// 随机数
		String nonce_str = strReq;
		// 商品描述
		// String body = describe;

		// 商品描述根据情况修改
		String body = order.getProduceName();
		// 附加数据
		String attach = order.getPhone();
		// 商户订单号
		String out_trade_no = order.getSku();
		int intMoney = Integer.parseInt(finalmoney);

		// 总金额以分为单位，不带小数点
		int total_fee = intMoney;
		// 订单生成的机器 IP
		String spbill_create_ip = request.getRemoteAddr();
		// 订 单 生 成 时 间 非必输
		// String time_start ="";
		// 订单失效时间 非必输
		// String time_expire = "";
		// 商品标记 非必输
		// String goods_tag = "";

		// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
		String notify_url = UrlHelp.getUrlPathWithContextNoPort(request) + "/wxpay/pay-3/notify.action";

		String trade_type = "JSAPI";
		String openid = openId;
		// 非必输
		// String product_id = "";
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("body", body);
		packageParams.put("attach", attach);
		packageParams.put("out_trade_no", out_trade_no);

		// 这里写的金额为1 分到时修改
		packageParams.put("total_fee", intMoney + "");
		packageParams.put("spbill_create_ip", spbill_create_ip);
		packageParams.put("notify_url", notify_url);

		packageParams.put("trade_type", trade_type);
		packageParams.put("openid", openid);

		RequestHandler reqHandler = new RequestHandler(request, response);
		reqHandler.init(appid, appsecret, partnerkey);

		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>" + mch_id + "</mch_id>" + "<nonce_str>"
				+ nonce_str + "</nonce_str>" + "<sign>" + sign + "</sign>" + "<body><![CDATA[" + body + "]]></body>"
				+ "<attach>" + attach + "</attach>" + "<out_trade_no>" + out_trade_no + "</out_trade_no>" +
				// 金额，这里写的1 分到时修改
				"<total_fee>" + total_fee + "</total_fee>" +
				// "<total_fee>"+finalmoney+"</total_fee>"+
				"<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>" + "<notify_url>" + notify_url
				+ "</notify_url>" + "<trade_type>" + trade_type + "</trade_type>" + "<openid>" + openid + "</openid>"
				+ "</xml>";
		System.out.println(xml);
		String allParameters = "";
		try {
			allParameters = reqHandler.genPackage(packageParams);
		} catch (Exception e) {
			// TODO logger obtion all params error
			e.printStackTrace();
		}
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String prepay_id = "";
		try {
			prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
			if (prepay_id.equals("")) {
				view.addObject("ErrorMsg", "统一支付接口获取预支付订单出错");
				view.setViewName("wxpay/error");
				return view;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block`
			e1.printStackTrace();
		}
		SortedMap<String, String> finalpackage = new TreeMap<String, String>();
		String appid2 = appid;
		String timestamp = Sha1Util.getTimeStamp();
		String nonceStr2 = nonce_str;
		String prepay_id2 = "prepay_id=" + prepay_id;
		String packages = prepay_id2;
		finalpackage.put("appId", appid2);
		finalpackage.put("timeStamp", timestamp);
		finalpackage.put("nonceStr", nonceStr2);
		finalpackage.put("package", packages);
		finalpackage.put("signType", "MD5");
		String finalsign = reqHandler.createSign(finalpackage);
		view.addObject("appid", appid2);
		view.addObject("timeStamp", timestamp);
		view.addObject("nonceStr", nonceStr2);
		view.addObject("packageVal", packages);
		view.addObject("sign", finalsign);
		view.addObject("openId", openId);
		view.addObject("orderNo", order.getSku());
		view.addObject("phone", order.getPhone());
		view.addObject("address", order.getAddress());
		view.addObject("weight", order.getWeight());
		view.addObject("unitPrice", order.getUnitPrice());
		view.addObject("merchant", order.getMerchant());
		view.addObject("money", money);
		view.addObject("product", order.getProduceName());
		view.addObject("openId", openId);
		view.addObject("comments", order.getComments());
		view.addObject("freight", order.getFreight());

		// 分享参数
		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
		String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
		String url2 = UrlHelp.getFullRequestURL(request);
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);

		view.addObject("shareTimestamp", ret.get("timestamp"));
		view.addObject("shareNonceStr", ret.get("nonceStr"));
		view.addObject("shareSignature", ret.get("signature"));

		view.setViewName("redirect:/wxpay/farmList.jsp");
		// view.setViewName("redirect:/wxpay/farmList.jsp");
		return view;
		// response.sendRedirect(baseUrl + "/" + project +
		// "/pages/pay/pay.jsp");

	}

	@RequestMapping("pre_share")
	public ModelAndView topay2_share(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		ModelAndView view = new ModelAndView();
		// 网页授权后获取传递的参数
		// String userId = request.getParameter("userId");
		// String orderNo = request.getParameter("orderNo");
		// String money = request.getParameter("money");

		// 获取支付的相关信息
		String orderId = request.getParameter("orderId");
		// String userId = "b88001";
		String code = request.getParameter("code");
		String appid = ParamesAPI.appId;

		// 商户相关资料
		String appsecret = ParamesAPI.appsecret;
		String partner = ParamesAPI.partner;
		String partnerkey = ParamesAPI.partnerkey;

		// 获取openId
		String openId = "";
		String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + appsecret
				+ "&code=" + code + "&grant_type=authorization_code";

		JSONObject jsonObject = CommonUtil.httpsRequest(URL, "GET", null);
		if (null != jsonObject) {
			try {
				openId = jsonObject.getString("openid");
			} catch (Exception e) {
				view.addObject("ErrorMsg", "获取预付订单号出错");
				view.setViewName("wxpay/error");
				return view;
			}
		}
		FarmOrder order = farmOrderBiz.getById(Long.valueOf(orderId));
		String money = order.getTotal().toString();
		// -- test-- //
		// money = "0.01"; // 测试暂时设为1分钱

		if (openId.equals("oQiB5wIJxsgObghXZuwV5JvTDR0U") || openId.equals("oQiB5wOIzxE7mOqxHUttx84a8kIY")
				|| openId.equals("oQiB5wEf8ngI2Nk4doDAk5OsvY2U") || openId.equals("oQiB5wMOHhRL-FQSOh4LrItUjKvE")
				|| openId.equals("oQiB5wCRTVSj1ahL-CQpd6HwjdmM") || openId.equals("oQiB5wMSs3Lk4slhPIIT0DH2RTmA")
				|| openId.equals("oQiB5wPC1BuF8C3mlwlYwflWCAiI") || openId.equals("oQiB5wFn0Nk91IfBj0y9g4RIWlkA")
				|| openId.equals("oQiB5wIywKkQKth56FnmU_NmN-d4")) {
			money = "0.01";
		}

		// -- end test-- //
		// 金额转化为分为单位
		float sessionmoney = Float.parseFloat(money);
		String finalmoney = String.format("%.2f", sessionmoney);
		finalmoney = finalmoney.replace(".", "");

		// 获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		String strReq = strTime + strRandom;

		// 商户号
		String mch_id = partner;
		// 子商户号 非必输
		// String sub_mch_id="";
		// 设备号 非必输
		String device_info = "";
		// 随机数
		String nonce_str = strReq;
		// 商品描述
		// String body = describe;

		// 商品描述根据情况修改
		String body = order.getProduceName();
		// 附加数据
		String attach = order.getPhone();
		// 商户订单号
		String out_trade_no = order.getSku();
		int intMoney = Integer.parseInt(finalmoney);

		// 总金额以分为单位，不带小数点
		int total_fee = intMoney;
		// 订单生成的机器 IP
		String spbill_create_ip = request.getRemoteAddr();
		// 订 单 生 成 时 间 非必输
		// String time_start ="";
		// 订单失效时间 非必输
		// String time_expire = "";
		// 商品标记 非必输
		// String goods_tag = "";

		// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
		String notify_url = UrlHelp.getUrlPathWithContextNoPort(request) + "/wxpay/pay-3/notify.action";

		String trade_type = "JSAPI";
		String openid = openId;
		// 非必输
		// String product_id = "";
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("body", body);
		packageParams.put("attach", attach);
		packageParams.put("out_trade_no", out_trade_no);

		// 这里写的金额为1 分到时修改
		packageParams.put("total_fee", intMoney + "");
		packageParams.put("spbill_create_ip", spbill_create_ip);
		packageParams.put("notify_url", notify_url);

		packageParams.put("trade_type", trade_type);
		packageParams.put("openid", openid);

		RequestHandler reqHandler = new RequestHandler(request, response);
		reqHandler.init(appid, appsecret, partnerkey);

		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>" + mch_id + "</mch_id>" + "<nonce_str>"
				+ nonce_str + "</nonce_str>" + "<sign>" + sign + "</sign>" + "<body><![CDATA[" + body + "]]></body>"
				+ "<attach>" + attach + "</attach>" + "<out_trade_no>" + out_trade_no + "</out_trade_no>" +
				// 金额，这里写的1 分到时修改
				"<total_fee>" + total_fee + "</total_fee>" +
				// "<total_fee>"+finalmoney+"</total_fee>"+
				"<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>" + "<notify_url>" + notify_url
				+ "</notify_url>" + "<trade_type>" + trade_type + "</trade_type>" + "<openid>" + openid + "</openid>"
				+ "</xml>";
		System.out.println(xml);
		String allParameters = "";
		try {
			allParameters = reqHandler.genPackage(packageParams);
		} catch (Exception e) {
			// TODO logger obtion all params error
			e.printStackTrace();
		}
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String prepay_id = "";
		try {
			prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
			if (prepay_id.equals("")) {
				view.addObject("ErrorMsg", "统一支付接口获取预支付订单出错");
				view.setViewName("wxpay/error");
				return view;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block`
			e1.printStackTrace();
		}
		SortedMap<String, String> finalpackage = new TreeMap<String, String>();
		String appid2 = appid;
		String timestamp = Sha1Util.getTimeStamp();
		String nonceStr2 = nonce_str;
		String prepay_id2 = "prepay_id=" + prepay_id;
		String packages = prepay_id2;
		finalpackage.put("appId", appid2);
		finalpackage.put("timeStamp", timestamp);
		finalpackage.put("nonceStr", nonceStr2);
		finalpackage.put("package", packages);
		finalpackage.put("signType", "MD5");
		String finalsign = reqHandler.createSign(finalpackage);
		view.addObject("appid", appid2);
		view.addObject("timeStamp", timestamp);
		view.addObject("nonceStr", nonceStr2);
		view.addObject("packageVal", packages);
		view.addObject("sign", finalsign);
		view.addObject("openId", openId);
		view.addObject("orderNo", order.getSku());
		view.addObject("phone", order.getPhone());
		view.addObject("address", order.getAddress());
		view.addObject("weight", order.getWeight());
		view.addObject("unitPrice", order.getUnitPrice());
		view.addObject("merchant", order.getMerchant());
		view.addObject("money", money);
		view.addObject("product", order.getProduceName());
		view.addObject("openId", openId);
		view.addObject("comments", order.getComments());
		view.addObject("freight", order.getFreight());

		// 分享参数
		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
		String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
		String url2 = UrlHelp.getFullRequestURL(request);
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);

		view.addObject("shareTimestamp", ret.get("timestamp"));
		view.addObject("shareNonceStr", ret.get("nonceStr"));
		view.addObject("shareSignature", ret.get("signature"));

		view.setViewName("redirect:/wxpay/farmList_share.jsp");
		// view.setViewName("redirect:/wxpay/farmList.jsp");
		return view;
		// response.sendRedirect(baseUrl + "/" + project +
		// "/pages/pay/pay.jsp");
	}

	@RequestMapping("pre_chris")
	public ModelAndView topay2_chris(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		ModelAndView view = new ModelAndView();
		// 网页授权后获取传递的参数
		// String userId = request.getParameter("userId");
		// String orderNo = request.getParameter("orderNo");
		// String money = request.getParameter("money");

		// 获取支付的相关信息
		String orderId = request.getParameter("orderId");
		// String userId = "b88001";
		String code = request.getParameter("code");
		String appid = ParamesAPI.appId;

		// 商户相关资料
		String appsecret = ParamesAPI.appsecret;
		String partner = ParamesAPI.partner;
		String partnerkey = ParamesAPI.partnerkey;

		// 获取openId
		String openId = "";
		String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + appsecret
				+ "&code=" + code + "&grant_type=authorization_code";

		JSONObject jsonObject = CommonUtil.httpsRequest(URL, "GET", null);
		if (null != jsonObject) {
			try {
				openId = jsonObject.getString("openid");
			} catch (Exception e) {
				view.addObject("ErrorMsg", "获取预付订单号出错");
				view.setViewName("wxpay/error");
				return view;
			}
		}
		FarmOrder order = farmOrderBiz.getById(Long.valueOf(orderId));
		String money = order.getTotal().toString();
		// -- test-- //
		// money = "0.01"; // 测试暂时设为1分钱

		if (openId.equals("oQiB5wIJxsgObghXZuwV5JvTDR0U") || openId.equals("oQiB5wOIzxE7mOqxHUttx84a8kIY")
				|| openId.equals("oQiB5wEf8ngI2Nk4doDAk5OsvY2U") || openId.equals("oQiB5wMOHhRL-FQSOh4LrItUjKvE")
				|| openId.equals("oQiB5wCRTVSj1ahL-CQpd6HwjdmM") || openId.equals("oQiB5wMSs3Lk4slhPIIT0DH2RTmA")
				|| openId.equals("oQiB5wPC1BuF8C3mlwlYwflWCAiI") || openId.equals("oQiB5wFn0Nk91IfBj0y9g4RIWlkA")
				|| openId.equals("oQiB5wIywKkQKth56FnmU_NmN-d4")) {
			money = "0.01";
		}

		// -- end test-- //
		// 金额转化为分为单位
		float sessionmoney = Float.parseFloat(money);
		String finalmoney = String.format("%.2f", sessionmoney);
		finalmoney = finalmoney.replace(".", "");

		// 获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		String strReq = strTime + strRandom;

		// 商户号
		String mch_id = partner;
		// 子商户号 非必输
		// String sub_mch_id="";
		// 设备号 非必输
		String device_info = "";
		// 随机数
		String nonce_str = strReq;
		// 商品描述
		// String body = describe;

		// 商品描述根据情况修改
		String body = order.getProduceName();
		// 附加数据
		String attach = order.getPhone();
		// 商户订单号
		String out_trade_no = order.getSku();
		int intMoney = Integer.parseInt(finalmoney);

		// 总金额以分为单位，不带小数点
		int total_fee = intMoney;
		// 订单生成的机器 IP
		String spbill_create_ip = request.getRemoteAddr();
		// 订 单 生 成 时 间 非必输
		// String time_start ="";
		// 订单失效时间 非必输
		// String time_expire = "";
		// 商品标记 非必输
		// String goods_tag = "";

		// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
		String notify_url = UrlHelp.getUrlPathWithContextNoPort(request) + "/wxpay/pay-3/notify.action";

		String trade_type = "JSAPI";
		String openid = openId;
		// 非必输
		// String product_id = "";
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("body", body);
		packageParams.put("attach", attach);
		packageParams.put("out_trade_no", out_trade_no);

		// 这里写的金额为1 分到时修改
		packageParams.put("total_fee", intMoney + "");
		packageParams.put("spbill_create_ip", spbill_create_ip);
		packageParams.put("notify_url", notify_url);

		packageParams.put("trade_type", trade_type);
		packageParams.put("openid", openid);

		RequestHandler reqHandler = new RequestHandler(request, response);
		reqHandler.init(appid, appsecret, partnerkey);

		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>" + mch_id + "</mch_id>" + "<nonce_str>"
				+ nonce_str + "</nonce_str>" + "<sign>" + sign + "</sign>" + "<body><![CDATA[" + body + "]]></body>"
				+ "<attach>" + attach + "</attach>" + "<out_trade_no>" + out_trade_no + "</out_trade_no>" +
				// 金额，这里写的1 分到时修改
				"<total_fee>" + total_fee + "</total_fee>" +
				// "<total_fee>"+finalmoney+"</total_fee>"+
				"<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>" + "<notify_url>" + notify_url
				+ "</notify_url>" + "<trade_type>" + trade_type + "</trade_type>" + "<openid>" + openid + "</openid>"
				+ "</xml>";
		System.out.println(xml);
		String allParameters = "";
		try {
			allParameters = reqHandler.genPackage(packageParams);
		} catch (Exception e) {
			// TODO logger obtion all params error
			e.printStackTrace();
		}
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String prepay_id = "";
		try {
			prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
			if (prepay_id.equals("")) {
				view.addObject("ErrorMsg", "统一支付接口获取预支付订单出错");
				view.setViewName("wxpay/error");
				return view;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block`
			e1.printStackTrace();
		}
		SortedMap<String, String> finalpackage = new TreeMap<String, String>();
		String appid2 = appid;
		String timestamp = Sha1Util.getTimeStamp();
		String nonceStr2 = nonce_str;
		String prepay_id2 = "prepay_id=" + prepay_id;
		String packages = prepay_id2;
		finalpackage.put("appId", appid2);
		finalpackage.put("timeStamp", timestamp);
		finalpackage.put("nonceStr", nonceStr2);
		finalpackage.put("package", packages);
		finalpackage.put("signType", "MD5");
		String finalsign = reqHandler.createSign(finalpackage);
		view.addObject("appid", appid2);
		view.addObject("timeStamp", timestamp);
		view.addObject("nonceStr", nonceStr2);
		view.addObject("packageVal", packages);
		view.addObject("sign", finalsign);
		view.addObject("openId", openId);
		view.addObject("orderNo", order.getSku());
		view.addObject("phone", order.getPhone());
		view.addObject("address", order.getAddress());
		view.addObject("weight", order.getWeight());
		view.addObject("unitPrice", order.getUnitPrice());
		view.addObject("merchant", order.getMerchant());
		view.addObject("money", money);
		view.addObject("product", order.getProduceName());
		view.addObject("openId", openId);
		view.addObject("comments", order.getComments());
		view.addObject("freight", order.getFreight());

		// 分享参数
		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
		String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
		String url2 = UrlHelp.getFullRequestURL(request);
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);

		view.addObject("shareTimestamp", ret.get("timestamp"));
		view.addObject("shareNonceStr", ret.get("nonceStr"));
		view.addObject("shareSignature", ret.get("signature"));

		view.setViewName("redirect:/wxpay/farmList_chris.jsp");
		// view.setViewName("redirect:/wxpay/farmList.jsp");
		return view;
		// response.sendRedirect(baseUrl + "/" + project +
		// "/pages/pay/pay.jsp");

	}
}
