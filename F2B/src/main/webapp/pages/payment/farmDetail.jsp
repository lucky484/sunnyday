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
<meta content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport" />
<meta name="x5-orientation"content="portrait">
<link href="<c:url value="/css/farmOrder.css"/>" type="text/css"
	rel="stylesheet" />
<link href="<c:url value="/css/farmProduce.css"/>" type="text/css"
	rel="stylesheet" />
<link href="<c:url value="/css/style.css"/>" type="text/css"
	rel="stylesheet" />
<link href="<c:url value="/css/bootstrap.min.css"/>" type="text/css"
	rel="stylesheet" />
<title>订单查看页</title>
<style type="text/css">
body {
	margin: 0;
}
.margin_lm12 {
	margin-left: 12%;
}

.magrin_tm10 {
	margin-top: 10px;
}

.tb-promo-text {
	padding: 0 4px;
	line-height: 18px;
	color: #999;
}

.padding_l1{
	padding-left: 35px;
}

.padding_l3t15 {
	padding-left: 10px;
/* 	padding-top: 15px; */
}

.padding_l15 {
	padding-left: 15px;
}

.font_26 {
	font-size: 26px;
}

.font_30 {
	font-size: 30px;
}

.line_search {
/* 	border: 0;  */
/* 	border-bottom: 1px solid #ccc;  */
/* 	line-height: 30px;  */
	padding-left: 25px;
} 

.col-lg-6{
	width: 60%;
}

.ad {
	width: 91px; 
	height: 91px;
}
/* max-width */ 
@media screen and (max-width: 375px) {
	.ad {
		width: 72px; 
		height: 72px; 
	}
	table {
		font-size: 12px;
	}
}
.line2{
	border: 0;
/*     border-bottom: 1px solid #ccc; */
}

.hr_1 {
	margin: 0 0 5px;
    border: 0;
    border-bottom: 1px solid #f7f7f7;
    z-index: 5;
    background: transparent;
    padding: 5px;
}
.img_middle{
    	margin-top:35%;
    	text-align: center;
    	color: #999;
    }
    .middle{
    	text-align: center;
    	color: #999;
    }
</style>
</head>
<body>
<%-- 	<header>
		<form action="<c:url value="/web/farm/getOrderListByPhone.action" />">
			<div class="magrin_tm10">
				<div class="col-lg-6 float_l margin_l3">
					<div class="input-group">
						<span class="input-group-addon">
							<img src="<c:url value="/resources/images/search.png"/>"
								style="width: 17px; height: 17px;">
						</span>
						<input type="text" class="form-control" name="phone"
							id="phone" required value="${farmOrder.phone }"
							placeholder="请输入手机号" />
					</div>
				</div>
				<div class="col-lg-2 float_l">
					<div class="input-group">
						<input type="submit" class="btn btn-default float_l"
							id="search" value="查询"
							style="padding: 5px 12px;background-color: #e5e5e5;" />
					</div>
				</div>
				<hr class="clear hr">
			</div>
		</form>
	</header> --%>
		<c:forEach items="${list }" var="farmOrder">

			<div class="margin_1">
			<a href="<c:url value="/web/farm/getFarmDetailMap.action?sku=${farmOrder.sku }" />" style="color:#000;">
				<div class="float_l">
					<c:choose>
						<c:when test="${farmOrder.produceName=='敦煌二墩村大漠葡萄 - 青提' }">
							<img src="<c:url value="/resources/images/farmOrder/grape1.jpg"/>" class="ad" />
						</c:when>
						<c:when test="${farmOrder.produceName=='敦煌二墩村大漠葡萄 - 红提' }">
							<img src="<c:url value="/resources/images/farmOrder/grape2.jpg"/>" class="ad" />
						</c:when>
						<c:when test="${farmOrder.produceName=='阿克苏苹果' }">
							<img src="<c:url value="/resources/images/farmOrder/apple/header_spring.jpg"/>" class="ad"  />
						</c:when>
						<c:otherwise>
							<img src="<c:url value="/resources/images/farmOrder/apple/header_spring.jpg"/>" class="ad" />
						</c:otherwise>
					</c:choose>
				</div>
				<div class="padding_l1">
					<table class="float_l padding_lt3" style="width: 80%;">
						<tr>
							<td colspan="2" class="">
								<span class="font_ padding_l15" id="" name="produceName"
										style="font-weight: 700; border: 0;"> ${farmOrder.produceName }</span>
								<span style="color: #ccc;">(
								<c:choose>
									<c:when test="${farmOrder.status eq -1 }"> 未发货 </c:when>
									<c:when test="${farmOrder.status eq 0 }"><span style="color: green;">已发货</span></c:when>
									<c:otherwise> 未发货 </c:otherwise>
								</c:choose>)</span></td>
						</tr>
						<tr>
							<td colspan="2" class="padding_l3t15"><span id=""
								class="tb-promo-text padding_lt3 font_">收货人：${farmOrder.merchant }</span>
							</td>
							<%-- <td class="padding_l3t15" style="width: 30%;"><span id=""
								class="tb-promo-text padding_lt3 font_">状态： 
								<c:choose>
									<c:when test="${farmOrder.status eq -1 }">未发货</c:when>
									<c:when test="${farmOrder.status eq 0 }">已发货</c:when>
									<c:otherwise> - </c:otherwise>
								</c:choose>
							</span></td> --%>
						</tr>
						<tr>
							<td class="padding_l3t15" style="width: 60%;"><span id=""
								class="tb-promo-text padding_t3 font_">单价
									¥：${farmOrder.unitPrice }</span></td>
							<td class="" style="width: 70%;"><span id=""
								class="tb-promo-text padding_lt3 font_">数量：
									${farmOrder.weight }</span></td>
							<%-- <td class="padding_l3t15" style="width: 30%;"><span id=""
								class="tb-promo-text padding_t3 font_">单价
									¥：${farmOrder.unitPrice }</span></td> --%>
						</tr>
						<!-- <tr> <td> &nbsp;</td></tr> -->
						<tr>
							<td colspan="2" align="right" class="padding_l3t15">
								<span class="tb-promo-text" style="font-weight: 700; border: 0; color: #949494;">总价：¥</span> 
								<input class="line2" id="total" name="total" value="${farmOrder.total }" 
								style="font-weight: 700; border: 0; width:38%; color: #949494;" readonly="true" />
							</td>
<!-- 							<td colspan="3" class="padding_l3t15"><span id="" -->
<!-- 								class="tb-promo-text padding_lt3 font_">订单号：</span></td> -->
<!-- 							<td> -->
<%-- 									${farmOrder.sku }product_14717721471772518</span></td> --%>
						</tr>
					</table>
				</div>
<!-- 				<hr class="clear hr"> -->
<!-- 				<div align="right"> -->
<!-- 						<em class="" style="font-weight: 700; border: 0; color: #949494;">总价： ¥</em>  -->
<%-- 						<input class="line2" id="total" name="total" value="${farmOrder.total }"  --%>
<!-- 							style="font-weight: 700; border: 0; width:19%; color: #949494;" readonly="true" /> -->
<!-- 				</div> -->
			</a>
			</div>
			<hr class="clear hr_1">
			
		</c:forEach>

		<div class="" id="sch-info">
			<c:if test="${isEmpty == true }">
				<div id="formbackground" style="position:absolute; z-index:99;height:100%; width:100%">
					<div  class="img_middle">
					<img src="<c:url value="/resources/images/error.png"/>" style="width:100px;">
					</div>
					<h3 class="middle">暂无订单</h3>
				</div>
			</c:if>
		</div>

		<script type="text/javascript"
			src="<c:url value="/Javascript/jquery-1.9.1.min.js"/>"></script>
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