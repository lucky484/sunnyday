<%--
  User: Mozzie.chu
  Date: 2016/8/18
  Time: 10:20
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<c:url value="/css/farmProduce.css"/>" type="text/css"
	rel="stylesheet" />
<link href="<c:url value="/css/farmOrder.css"/>" type="text/css"
	rel="stylesheet" />
<link href="<c:url value="/css/style.css"/>" type="text/css"
	rel="stylesheet" />
<link href="<c:url value="/css/bootstrap.min.css"/>" type="text/css"
	rel="stylesheet" />
<link href="<c:url value="/css/weui.css"/>" type="text/css"
	rel="stylesheet" />
<style type="text/css">
.font_30{
	font-size:30px;
	color:#999;
}
.btn-default-a {
    color: #333;
    background-color: #fff;
    border-color: #ccc;
}

.btn-group-lg_a>.btn-a, .btn-lg-a {
    padding: 10px 16px;
    font-size: 18px;
    line-height: 1.3333333;
    border-radius: 6px;
}

.btn-a {
    display: inline-block;
    /* padding: 6px 12px; */
    margin-bottom: 0;
    font-size: 40px;
    color: #e8d586;
    background-color: #620d0a;
    font-weight: 400;
    /* line-height: 1.42857143; */
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    -ms-touch-action: manipulation;
    touch-action: manipulation;
    cursor: pointer;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    background-image: none;
    border: 1px solid transparent;
    /* border-radius: 4px; */
}

.button_back_btn {
	width: 100%;
    height: 190px;
    background-color: #e5e5e5;
}

.hide {
	display: none;
}
</style>
<title>支付成功</title>
</head>
<body>

	<div style="background-color: #620d0a; width: 100%;display: table;">
		<img
			src="<c:url value="/resources/images/farmOrder/order_success_1.jpg"/>"
			title="已付款"
			style="float: left; width:50%;" />
		<img
			src="<c:url value="/resources/images/farmOrder/order_success_2.jpg"/>"
			title="已付款了" style="width:50%;" />
	</div>
	<div>
		<img src="<c:url value="/resources/images/farmOrder/order_4.jpg"/>" style="width: 100%;" />
	</div>
	<br/>
	<div class="">
		<table class="margin_l5" style="width:90%;">
			<tr>
				<td class="font_36" style="width:20%;">
					收  &nbsp;货  &nbsp;人：
				</td>
				<td class="font_30" style="width:70%;">
					<input type="text" class="line" name="merchant" id="merchant" required value="${farmOrder.merchant }"  readOnly="true" style="width:100%;word-break:inherit; border: 0; height:40px;">  
					<input type="text" class="line" name="sku" id="sku" required value="${farmOrder.sku }" style="display: none;"> 
				</td>
			</tr>
			<tr>
				<td class="font_36 " style="width:20%;">收货地址：</td>
				<td class="font_30 " style="width:70%;line-height: 20px;">
					<span name="address" class=""  id="address" style="width:100%;word-break:break-all;line-height:36px;">${farmOrder.address }</span>
				</td>
			</tr>
		</table>
		<hr class="clear hr margin_1"><br/>
	</div>
	
	<div style="margin-bottom:20px;">
		<div class="font_36 margin_l5" id="" >
			<div class="float_l">
				<span>总 价：</span>
			</div>
			<div class="">
				<strong class="tb-promo-price">
					<em class="tb-rmb">¥</em>
					<em id="" class="tb-rmb">${farmOrder.total}</em>
				</strong>
				<span id="" class="tb-promo-type">产地直销</span>
			</div>
			<div class="tb-promo-item-ft"></div>
		</div>
	</div>
	
	<div class="button_back_btn">
		<!-- <button type="button" class="btn btn-default btn-lg">查看订单</button>
		<button type="button" class="btn btn-default btn-lg">返回首页</button> -->
			<a class="btn-a btn-default-a btn-lg-a" href="<c:url value='/web/farm/getOrderListBySku.action?sku=${farmOrder.sku }' />" style="margin-left: 20%; margin-top: 6%;">订单详情</a>
			<a class="btn-a btn-default-a btn-lg-a" href="<c:url value="/web/farm/getFarmProducePage_share.action"/>" style="margin-left: 20%; margin-top: 6%;">返回分享首页</a> 
	</div>                                      
	<br>
	
	<div>
		<p class="font_36" style="margin-left: 50px;margin-right: 50px;">安全提醒</p>
		<p class="font_36" style="margin-left: 50px;margin-right: 50px;">付款成功后，我们不会以付款异常、卡单、系统升级为由联系您。<em class="tb-rmb">请勿泄露银行卡号、手机验证码，否则会造成钱款损失。谨防电话诈骗！</em></p>
		<%-- <p style="text-align: center;"><img src="<c:url value="/images/kuaidi.png"/>" /></p> --%>
	</div>
	<%-- <div class="clear bottom">
		<a class="padding_lm4" href="<c:url value='/lottery.action?orderNo=${farmOrder.sku }' />">进入抽奖</a>
	</div> --%>
	
	<input type="text" id="phone" name="phone" value="${farmOrder.phone }"  style="display:none;"/>
	
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script>
		/*
		 * 注意：
		 * 1. 所有的JS接口只能在公众号绑定的域名下调用，公众号开发者需要先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。
		 * 2. 如果发现在 Android 不能分享自定义内容，请到官网下载最新的包覆盖安装，Android 自定义分享接口需升级至 6.0.2.58 版本及以上。
		 * 3. 常见问题及完整 JS-SDK 文档地址：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html
		 *
		 * 开发中遇到问题详见文档“附录5-常见错误及解决办法”解决，如仍未能解决可通过以下渠道反馈：
		 * 邮箱地址：weixin-open@qq.com
		 * 邮件主题：【微信JS-SDK反馈】具体问题
		 * 邮件内容说明：用简明的语言描述问题所在，并交代清楚遇到该问题的场景，可附上截屏图片，微信团队会尽快处理你的反馈。
		 */
		wx.config({
			debug : false,
			appId : 'wx80bc1ec970681073',
			timestamp : '${timestamp}',
			nonceStr : '${nonceStr}',
			signature : '${signature}',
			jsApiList : ['hideAllNonBaseMenuItem']
		});
		wx.ready(function() {
	 		wx.hideAllNonBaseMenuItem();
 		});
	</script>
</body>
<html>