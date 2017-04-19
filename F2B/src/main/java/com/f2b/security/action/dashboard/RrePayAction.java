package com.f2b.security.action.dashboard;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.f2b.security.business.biz.FarmOrderBiz;
import com.f2b.security.domain.FarmOrder;
import com.f2b.sugar.tools.OrderCofig;
import com.f2b.sugar.tools.pay.http.UrlHelp;
import com.f2b.sugar.wxlib.ParamesAPI.ParamesAPI;

/**
 * url: www.softtek.com/F2B/wxpay/pay-1/pre.action
 * 
 * @ClassName: RrePayAction
 * @Description: 用户微信支付之授权操作
 * @author ligang.yang@softtek.com
 * @date 2016年8月18日 上午10:32:27
 *
 */
@Controller
@RequestMapping("wxpay/pay-1") // -1代表支付过程中的预支付
public class RrePayAction {

	@Autowired
	private FarmOrderBiz farmOrderBiz;

	@RequestMapping("pre")
	// 网页授权获取用户信息
	public void pre2Pay(HttpServletRequest request, HttpServletResponse response) {

		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		// 获取支付的相关信息
		String phone = request.getParameter("phone"); // 手机号
		String money = request.getParameter("total"); // 总价
		String product = request.getParameter("produceName"); // 商品名
		String orderNo = OrderCofig.obtionOrderNo(); // 订单号
		String address = request.getParameter("address");// 地址
		String province = request.getParameter("province");// 地址
		String city = request.getParameter("city");// 地址
		String town = request.getParameter("town");// 地址
		String weight = request.getParameter("weight"); // 数量
		String unitPrice = request.getParameter("unitPrice"); // 单价
		String merchant = request.getParameter("merchant");// 店主

		String comments = request.getParameter("comments");// 备注

		String freight = request.getParameter("freight");// 运费
		String openid = request.getParameter("openid"); // 当前购买者的openid
		// 将订单存到数据库
		FarmOrder farmOrder = farmOrderBiz.getUnPayOrderByOpenid(openid);
		if (farmOrder == null) {
			farmOrder = new FarmOrder();
			farmOrder.setAddress(province + city + town + address);
			farmOrder.setComments(comments);
			farmOrder.setCreateDate(new Date());
			farmOrder.setFreight(new BigDecimal(freight));
			farmOrder.setMerchant(merchant);
			farmOrder.setOpenId(openid);
			farmOrder.setPhone(phone);
			farmOrder.setProduceName(product);
			farmOrder.setSku(orderNo);
			farmOrder.setStatus(-2); // 未支付
			farmOrder.setTotal(new BigDecimal(money));
			farmOrder.setUnitPrice(new BigDecimal(unitPrice));
			farmOrder.setWeight(weight);
			farmOrderBiz.addFarmOrder(farmOrder);
		} else {
			farmOrder.setAddress(province + city + town + address);
			farmOrder.setComments(comments);
			farmOrder.setCreateDate(new Date());
			farmOrder.setFreight(new BigDecimal(freight));
			farmOrder.setMerchant(merchant);
			farmOrder.setPhone(phone);
			farmOrder.setProduceName(product);
			farmOrder.setSku(orderNo);
			farmOrder.setStatus(-2); // 未支付
			farmOrder.setTotal(new BigDecimal(money));
			farmOrder.setUnitPrice(new BigDecimal(unitPrice));
			farmOrder.setWeight(weight);
			farmOrderBiz.updateOrder(farmOrder);
		}

		// 共账号及商户相关参数
		String appid = ParamesAPI.appId;
		String backUri = UrlHelp.getUrlPathWithContextNoPort(request) + "/wxpay/pay-2/pre.action";
		// 授权后要跳转的链接所需的参数一般有会员号，金额，订单号之类，
		// 最好自己带上一个加密字符串将金额加上一个自定义的key用MD5签名或者自己写的签名,
		// 比如 Sign = %3D%2F%CS%
		backUri = backUri + "?orderId=" + farmOrder.getOrderId();// 备注暂时没有
		// URLEncoder.encode 后可以在backUri 的url里面获取传递的所有参数
		try {
			backUri = URLEncoder.encode(backUri, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		// scope 参数视各自需求而定，这里用scope=snsapi_base 不弹出授权页面直接授权目的只获取统一支付接口的openid
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" + "appid=" + appid + "&redirect_uri="
				+ backUri + "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			// TODO logger io exception
			e.printStackTrace();
		}
	}

	/**
	 * 通过链接 的用户 所打开的 立减15元 的 
	 * @param request
	 * @param response
	 */
		@RequestMapping("pre_share")
		// 网页授权获取用户信息
		public void pre2Pay_share(HttpServletRequest request, HttpServletResponse response) {

			try {
				request.setCharacterEncoding("utf-8");
			} catch (UnsupportedEncodingException e2) {
				e2.printStackTrace();
			}
			// 获取支付的相关信息
			String phone = request.getParameter("phone"); // 手机号
			String money = request.getParameter("total"); // 总价
			String product = request.getParameter("produceName"); // 商品名
			String orderNo = OrderCofig.obtionOrderNo(); // 订单号
			String address = request.getParameter("address");// 地址
			String province = request.getParameter("province");// 地址
			String city = request.getParameter("city");// 地址
			String town = request.getParameter("town");// 地址
			String weight = request.getParameter("weight"); // 数量
			String unitPrice = request.getParameter("unitPrice"); // 单价
			String merchant = request.getParameter("merchant");// 店主

			String comments = request.getParameter("comments");// 备注

			String freight = request.getParameter("freight");// 运费
			String openid = request.getParameter("openid"); // 当前购买者的openid
			// 将订单存到数据库
			FarmOrder farmOrder = farmOrderBiz.getUnPayOrderByOpenid(openid);
			if (farmOrder == null) {
				farmOrder = new FarmOrder();
				farmOrder.setAddress(province + city + town + address);
				farmOrder.setComments(comments);
				farmOrder.setCreateDate(new Date());
				farmOrder.setFreight(new BigDecimal(freight));
				farmOrder.setMerchant(merchant);
				farmOrder.setOpenId(openid);
				farmOrder.setPhone(phone);
				farmOrder.setProduceName(product);
				farmOrder.setSku(orderNo);
				farmOrder.setStatus(-2); // 未支付
				farmOrder.setTotal(new BigDecimal(money));
				farmOrder.setUnitPrice(new BigDecimal(unitPrice));
				farmOrder.setWeight(weight);
				farmOrderBiz.addFarmOrder(farmOrder);
			} else {
				farmOrder.setAddress(province + city + town + address);
				farmOrder.setComments(comments);
				farmOrder.setCreateDate(new Date());
				farmOrder.setFreight(new BigDecimal(freight));
				farmOrder.setMerchant(merchant);
				farmOrder.setPhone(phone);
				farmOrder.setProduceName(product);
				farmOrder.setSku(orderNo);
				farmOrder.setStatus(-2); // 未支付
				farmOrder.setTotal(new BigDecimal(money));
				farmOrder.setUnitPrice(new BigDecimal(unitPrice));
				farmOrder.setWeight(weight);
				farmOrderBiz.updateOrder(farmOrder);
			}

			// 共账号及商户相关参数
			String appid = ParamesAPI.appId;
			String backUri = UrlHelp.getUrlPathWithContextNoPort(request) + "/wxpay/pay-2/pre_share.action";
			// 授权后要跳转的链接所需的参数一般有会员号，金额，订单号之类，
			// 最好自己带上一个加密字符串将金额加上一个自定义的key用MD5签名或者自己写的签名,
			// 比如 Sign = %3D%2F%CS%
			backUri = backUri + "?orderId=" + farmOrder.getOrderId();// 备注暂时没有
			// URLEncoder.encode 后可以在backUri 的url里面获取传递的所有参数
			try {
				backUri = URLEncoder.encode(backUri, "utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			// scope 参数视各自需求而定，这里用scope=snsapi_base 不弹出授权页面直接授权目的只获取统一支付接口的openid
			String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" + "appid=" + appid + "&redirect_uri="
					+ backUri + "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
			try {
				response.sendRedirect(url);
			} catch (IOException e) {
				// TODO logger io exception
				e.printStackTrace();
			}
		}
		
		/**
		 * 圣诞节
		 * @param request
		 * @param response
		 */
		@RequestMapping("pre_chris")
		// 网页授权获取用户信息
		public void pre2Pay_chris(HttpServletRequest request, HttpServletResponse response) {

			try {
				request.setCharacterEncoding("utf-8");
			} catch (UnsupportedEncodingException e2) {
				e2.printStackTrace();
			}
			// 获取支付的相关信息
			String phone = request.getParameter("phone"); // 手机号
			String money = request.getParameter("total"); // 总价
			String product = request.getParameter("produceName"); // 商品名
			String orderNo = OrderCofig.obtionOrderNo(); // 订单号
			String address = request.getParameter("address");// 地址
			String province = request.getParameter("province");// 地址
			String city = request.getParameter("city");// 地址
			String town = request.getParameter("town");// 地址
			String weight = request.getParameter("weight"); // 数量
			String unitPrice = request.getParameter("unitPrice"); // 单价
			String merchant = request.getParameter("merchant");// 店主

			String comments = request.getParameter("comments");// 备注

			String freight = request.getParameter("freight");// 运费
			String openid = request.getParameter("openid"); // 当前购买者的openid
			// 将订单存到数据库
			FarmOrder farmOrder = farmOrderBiz.getUnPayOrderByOpenid(openid);
			if (farmOrder == null) {
				farmOrder = new FarmOrder();
				farmOrder.setAddress(province + city + town + address);
				farmOrder.setComments(comments);
				farmOrder.setCreateDate(new Date());
				farmOrder.setFreight(new BigDecimal(freight));
				farmOrder.setMerchant(merchant);
				farmOrder.setOpenId(openid);
				farmOrder.setPhone(phone);
				farmOrder.setProduceName(product);
				farmOrder.setSku(orderNo);
				farmOrder.setStatus(-2); // 未支付
				farmOrder.setTotal(new BigDecimal(money));
				farmOrder.setUnitPrice(new BigDecimal(unitPrice));
				farmOrder.setWeight(weight);
				farmOrderBiz.addFarmOrder(farmOrder);
			} else {
				farmOrder.setAddress(province + city + town + address);
				farmOrder.setComments(comments);
				farmOrder.setCreateDate(new Date());
				farmOrder.setFreight(new BigDecimal(freight));
				farmOrder.setMerchant(merchant);
				farmOrder.setPhone(phone);
				farmOrder.setProduceName(product);
				farmOrder.setSku(orderNo);
				farmOrder.setStatus(-2); // 未支付
				farmOrder.setTotal(new BigDecimal(money));
				farmOrder.setUnitPrice(new BigDecimal(unitPrice));
				farmOrder.setWeight(weight);
				farmOrderBiz.updateOrder(farmOrder);
			}

			// 共账号及商户相关参数
			String appid = ParamesAPI.appId;
			String backUri = UrlHelp.getUrlPathWithContextNoPort(request) + "/wxpay/pay-2/pre_chris.action";
			// 授权后要跳转的链接所需的参数一般有会员号，金额，订单号之类，
			// 最好自己带上一个加密字符串将金额加上一个自定义的key用MD5签名或者自己写的签名,
			// 比如 Sign = %3D%2F%CS%
			backUri = backUri + "?orderId=" + farmOrder.getOrderId();// 备注暂时没有
			// URLEncoder.encode 后可以在backUri 的url里面获取传递的所有参数
			try {
				backUri = URLEncoder.encode(backUri, "utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			// scope 参数视各自需求而定，这里用scope=snsapi_base 不弹出授权页面直接授权目的只获取统一支付接口的openid
			String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" + "appid=" + appid + "&redirect_uri="
					+ backUri + "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
			try {
				response.sendRedirect(url);
			} catch (IOException e) {
				// TODO logger io exception
				e.printStackTrace();
			}
		}
}
