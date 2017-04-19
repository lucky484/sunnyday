<%--
  User: Mozzie.chu
  Date: 2016/8/26
  Time: 17:53
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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
<title>订单详情页面</title>
<STYLE type="text/css">
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

</head>
<body>
	<div>
		<c:forEach items="${list }" var="farmOrder">
			<div>
				<div class="float_l">
					<img src="<c:url value="/resources/images/farmOrder/logo.jpg"/>"
						title="logo" style="width: 80px;" />
				</div>
				<div class="magrin_t2">
					<h3 class="font_20">
						<%-- <%=produceName %> --%>${farmOrder.produceName }</h3>
				</div>
			</div>
			<br />

			<div>
				<table class="table margin_1 clear" style="width: 96%;">
					<tr>
						<td class="font "
							style="text-align: left; border: 0;  padding-top: 20px; width: 30%;">付款总价：</td>
						<td class="font_26 " colspan="3"
							style="text-align: right; border: 0;">
							<%-- <%=money%> --%>${farmOrder.total }</td>
					</tr>

					<tr>
						<td class="font " style="text-align: left; ">收 货 人
							：</td>
						<td class="font " style="text-align: right; ">
							<%-- <%=merchant%> --%>${farmOrder.merchant}</td>
					</tr>
					<tr>
						<td class="font " style="text-align: left;">手 机 号
							：</td>
						<td class="font " style="text-align: right;">
							<%-- <%=phone%> --%>${farmOrder.phone}</td>
					</tr>

					<tr>
						<td class="font " style="text-align: left;">商品名称：</td>
						<td class="font " colspan="3"
							style="text-align: right; ">
							<%-- <%=produceName%> --%>${farmOrder.produceName}</td>
					</tr>

					<tr>
						<td class="font " style="text-align: left;">订 单 号
							：</td>
						<td class="font " colspan="3"
							style="text-align: right;">
							<%-- <%=orderNo %> --%>${farmOrder.sku}</td>
					</tr>

					<tr>
						<td class="font " style="text-align: left;">收货地址：</td>
						<td class="font " colspan="3"
							style="text-align: right;">
							<%-- <%=address%> --%>${farmOrder.address}</td>
					</tr>
					<tr>
						<td class="font " style="text-align: left;">商品单价：</td>
						<td class="font " style="text-align: right;">
							<%-- <%=unitPrice%> --%>${farmOrder.unitPrice}</td>
					<tr>
					<tr>
						<td class="font " style="text-align: left;">商品数量：</td>
						<td class="font " style="text-align: right;">
							<%-- <%=weight%> --%>${farmOrder.weight}</td>
					</tr>
					<tr>
						<td class="font " style="text-align: left; ">&nbsp;运&nbsp;&nbsp;&nbsp;&nbsp;费&nbsp;：</td>
						<td class="font " style="text-align: right; ">
							<%-- <%=weight%> --%>${farmOrder.freight}</td>
					</tr>

					<tr>
						<td class="font " style="text-align: left;">买家留言：</td>
						<td class="font " colspan="3" style="text-align: right;word-break:break-all;">
							<%-- <%=comments%> --%>${farmOrder.comments}</td>
					</tr>
				</table>
			</div>
		</c:forEach>
	</div>
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
</script>
<script type="text/javascript">
	//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	var curWwwPath = window.document.location.href;
	//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	var pathName = window.document.location.pathname;
	//21
	var pos = curWwwPath.indexOf(pathName);
	//获取主机地址，如： http://localhost:8083
	var localhostPaht = curWwwPath.substring(0, pos);

	wx.hideMenuItems({
	    menuList: ['menuItem:share:appMessage', 'menuItem:share:timeline', 'menuItem:share:qq', 'menuItem:share:weiboApp', 'menuItem:share:facebook', 'menuItem:share:QZone']
	});
// 	wx.ready(function() {
// 		wx.onMenuShareAppMessage({
// 			title : '阿克苏苹果冰糖心',
// 			desc : '阿克苏苹果冰糖心！',
// 			link : localhostPaht + '<c:url value="/web/farm/getFarmProducePage.action?share_openid=${openid}"/>',
// 			imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/apple_goods.jpg"/>',
// 			trigger : function(res) {
// 			},
// 			success : function(res) {
// 			},
// 			cancel : function(res) {
// 			},
// 			fail : function(res) {
// 				alert(JSON.stringify(res));
// 			}
// 		});

// 		wx.onMenuShareTimeline({
// 			title : '阿克苏苹果冰糖心！',
// 			link : localhostPaht + '<c:url value="/web/farm/getFarmProducePage.action?share_openid=${openid}"/>',
// 			imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/apple_goods.jpg"/>',
// 			trigger : function(res) {
// 			},
// 			success : function(res) {
// 			},
// 			cancel : function(res) {
// 			},
// 			fail : function(res) {
// 				alert(JSON.stringify(res));
// 			}
// 		});

// 		wx.onMenuShareQQ({
// 			title : '阿克苏苹果冰糖心',
// 			desc : '阿克苏苹果冰糖心！',
// 			link : localhostPaht + '<c:url value="/web/farm/getFarmProducePage.action?share_openid=${openid}"/>',
// 			imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/apple_goods.jpg"/>',
// 			success : function() {
// 				// 用户确认分享后执行的回调函数
// 			},
// 			cancel : function() {
// 				// 用户取消分享后执行的回调函数
// 			}
// 		});

// 		wx.onMenuShareWeibo({
// 			title : '阿克苏苹果冰糖心',
// 			desc : '阿克苏苹果冰糖心！',
// 			link : localhostPaht + '<c:url value="/web/farm/getFarmProducePage.action?share_openid=${openid}"/>',
// 			imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/apple_goods.jpg"/>',
// 			success : function() {
// 				// 用户确认分享后执行的回调函数
// 			},
// 			cancel : function() {
// 				// 用户取消分享后执行的回调函数
// 			}
// 		});

// 		wx.onMenuShareQZone({
// 			title : '阿克苏苹果冰糖心',
// 			desc : '阿克苏苹果冰糖心！',
// 			link : localhostPaht + '<c:url value="/web/farm/getFarmProducePage.action?share_openid=${openid}"/>',
// 			imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/apple_goods.jpg"/>',
// 			success : function() {
// 				// 用户确认分享后执行的回调函数
// 			},
// 			cancel : function() {
// 				// 用户取消分享后执行的回调函数
// 			}
// 		});
// 	});
</script>
</body>
</html>