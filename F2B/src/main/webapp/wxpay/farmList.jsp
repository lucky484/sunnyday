<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta
	content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"
	name="viewport" />
<link href="<c:url value="/css/farmOrder.css"/>" type="text/css"
	rel="stylesheet" />
<link href="<c:url value="/css/farmProduce.css"/>" type="text/css"
	rel="stylesheet" />
<link href="<c:url value="/css/style.css"/>" type="text/css"
	rel="stylesheet" />
<link href="<c:url value="/css/bootstrap.min.css"/>" type="text/css"
	rel="stylesheet" />
<link href="<c:url value="/css/weui.css"/>" type="text/css" rel="stylesheet" />
<title>确认支付页</title>

<STYLE type="text/css">
.hide {
	display: none;
}

.bottom_order_list {
	width: 100%;
	bottom: 0;
	position: fixed;
}

.bottom_order_list>input {
	background-color: #620d0a;
	width: 100%;
	height: 50px;
	color: #e8d586;
	font-size: 18px;
	font-weight: 700;
	text-align: -webkit-center;
}

.font_20 {
	font-size: 20px;
}
/* max-height */ 
@media screen and (max-height: 568px) {
	body{
		height:598px;
	}
}

</STYLE>
<%
	request.setCharacterEncoding("utf-8");
	String appId = request.getParameter("appid");
	String timeStamp = request.getParameter("timeStamp");
	String shareTimestamp = request.getParameter("shareTimestamp");
	String shareNonceStr = request.getParameter("shareNonceStr");
	String shareSignature = request.getParameter("shareSignature");
	String nonceStr = request.getParameter("nonceStr");
	String packageValue = request.getParameter("packageVal");
	String paySign = request.getParameter("sign");
	String produceName = request.getParameter("product");
	String orderNo = request.getParameter("orderNo");
	String merchant = request.getParameter("merchant");
	String phone = request.getParameter("phone");
	String address = request.getParameter("address");
	String unitPrice = request.getParameter("unitPrice");
	String weight = request.getParameter("weight");
	String money = request.getParameter("money");
	String openId = request.getParameter("openId");
	String freight = request.getParameter("freight");
	String comments = request.getParameter("comments");
%>
</head>
<body>
	<div>

		<%-- appId: <input id="appId" type="text" disabled="disabled" value="${appid}"/><br>
	timeStamp: <input id="timeStamp" type="text" disabled="disabled" value="${timeStamp}"/><br>
	nonceStr: <input id="nonceStr" type="text" disabled="disabled" value="${nonceStr}"/><br>
	package: <input id="package" type="text" disabled="disabled" value="${packageVal}"/><br>
	paySign: <input id="paySign" type="text" disabled="disabled" value="${sign}"/><BR /> --%>
		<div>
			<div class="float_l magrin_t2">
				<img src="<c:url value="/resources/images/farmOrder/logo.jpg"/>"
					title="logo" style="width: 80px;" />
			</div>
			<div class="float_l magrin_t2">
				<h3 class="font_20"><%=produceName%></h3>
			</div>
		</div>
		<br />

		<div>
			<table class="table margin_1 clear" style="width: 93%;">
				<tr>
					<td class="font "
						style="text-align: left; border: 0; padding-top: 20px;width:30%;">付款总价：</td>
					<td class="font_26 " colspan="3"
						style="text-align: right; border: 0;"><%=money%></td>
				</tr>

				<tr>
					<td class="font " style="text-align: left; ">收 货 人
						：</td>
					<td class="font " style="text-align: right; "><%=merchant%></td>
				</tr>
				<tr>
					<td class="font " style="text-align: left;">手 机 号
						：</td>
					<td class="font " style="text-align: right;"><%=phone%></td>
				</tr>

				<tr>
					<td class="font " style="text-align: left; ">商品名称：</td>
					<td class="font " colspan="3" style="text-align: right;"><%=produceName%></td>
				</tr>

				<tr>
					<td class="font " style="text-align: left;">订 单 号
						：</td>
					<td class="font " colspan="3" style="text-align: right;"><%=orderNo%></td>
				</tr>

				<tr>
					<td class="font " style="text-align: left;">收货地址：</td>
					<td class="font " colspan="3" style="text-align: right;"><%=address%></td>
				</tr>
				<tr>
					<td class="font " style="text-align: left;">商品单价：</td>
					<td class="font " style="text-align: right;"><%=unitPrice%></td>
				<tr>
				<tr>
					<td class="font " style="text-align: left;">商品数量：</td>
					<td class="font " style="text-align: right;"><%=weight%></td>
				</tr>
				<tr>
					<td class="font " style="text-align: left;">运费</td>
					<td class="font " style="text-align: right;"><%=freight%></td>
				</tr>
				<tr>
					<td class="font " style="text-align: left;">买家留言：</td>
					<td class="font " colspan="3" style="text-align: right;word-break:break-all;"><%=comments %></td>
				</tr>
			</table>
		</div>
		<div class="clear bottom_order_list">
			<input id="callPay" type="submit" value="确认支付" onclick="callpay()">
		</div>
	</div>
	<div id="payHint" class="weui_dialog_alert hide">
	    <div class="weui_mask"></div>
	    <div class="weui_dialog">
	        <div class="weui_dialog_hd"><strong class="weui_dialog_title">提示</strong></div>
	        <div class="weui_dialog_bd">正在处理您的订单，请勿关闭页面...</div>
	        <div class="weui_dialog_ft">
	            <a href="javascript:close()" class="weui_btn_dialog primary">确定</a>
	        </div>
	    </div>
	</div>
</body>
<script type="text/javascript"
	src="<c:url value="/Javascript/jquery-1.9.1.min.js"/>"></script>
<script>
function callpay(){//支付功能
	$("#callPay").val("正在支付...");
	$("#callPay").attr("disabled", "disabled");
		 WeixinJSBridge.invoke('getBrandWCPayRequest',{
		 "appId" :"<%=appId %>",
		 "timeStamp" : "<%=timeStamp %>", 
		 "nonceStr" : "<%=nonceStr %>", 
		 "package" : "<%=packageValue %>",
		 "signType" : "MD5",
		 "paySign" : "<%=paySign %>"
			},function(res){
			WeixinJSBridge.log(res.err_msg);
	        addFarmOrder();
		});
	 //addFarmOrder();
}

function addFarmOrder(){//跳转
	$("#callPay").val("正在处理您的订单，请稍候...");
	$("#payHint").removeClass("hide");
	window.location.href="<c:url value='/web/farm/addFarmOrder.action' />" + "?buyOpenId=" + "<%=openId%>" + "&sku=" + "<%=orderNo%>";
}
</script>
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
		timestamp : "<%=shareTimestamp%>",
		nonceStr : "<%=shareNonceStr%>",
		signature : "<%=shareSignature%>",
		jsApiList : ['hideAllNonBaseMenuItem']
	});
		wx.ready(function() {
	 		wx.hideAllNonBaseMenuItem();
 		});
</script>
<script type="text/javascript">
	//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	var curWwwPath = window.document.location.href;
	//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	//获取主机地址，如： http://localhost:8083
	var localhostPaht = curWwwPath.substring(0, pos);
		wx.ready(function() {
 		//wx.hideOptionMenu();
 		wx.hideAllNonBaseMenuItem();
	});
</script>
</html>