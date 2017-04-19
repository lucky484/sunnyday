<%--
  User: Mozzie.chu
  Date: 2016/8/18
  Time: 10:20
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<c:url value="/css/farmProduce.css"/>" type="text/css"
	rel="stylesheet" />
<link href="<c:url value="/css/bootstrap.min.css"/>" type="text/css"
	rel="stylesheet" />
<link href="<c:url value="/css/weui.css"/>" type="text/css" rel="stylesheet" />
<title>阿克苏苹果冰糖心</title>
<style type="text/css">
.hide {
	display: none;
}

a:link,a:visited {
	text-decoration: none!important;
}

.select, .select:link {
	width: 123px;
	border: 0px solid #ccc;
	padding: 10px 20px;
	text-decoration: none;
	color: #666;
	font-weight: 700;
	text-align: -webkit-center;
}

.selected, .selected:link {
	width: 123px;
	border: 1px solid #ccc;
	padding: 10px 20px;
	background-color: #620d0a;
	color: #e8d586;
	font-weight: 700;
	text-align: -webkit-center;
}

.clickdraw {
	position: fixed;
	bottom: 160px;
	right: 0px;
}

.bottom {
	height: 120px;
	padding-top: 24px;
}

.padding_lm2_1 {
	padding-left: 20%;
}

.padding_lm2_3 {
	padding-left: 23%;
}

.weui_dialog {
	height: 250px;
}

.weui_dialog_hd {
	height: 30%;
}

.weui_dialog_bd {
	white-space:nowrap;
	overflow:-webkit-marquee;
	font-size: 35px;
}

.weui_dialog_title {
	height: 100%;
	font-size: 35px;
}

.weui_dialog_ft a {
	line-height:250%;
	font-size: 35px;
}

p > span {
	color: red;
}

.itm a{
	color: #666;
}
</style>
</head>
<body>
	<div>
		<div>
			<img id="apple_reward"
				src="<c:url value="/resources/images/farmOrder/apple/header_spring.jpg"/>"
				title="阿克苏苹果冰糖心" style="width: 100%; height: auto;" /> 
<!-- 			<img id="apple_logo" -->
<%-- 				src="<c:url value="/resources/images/farmOrder/apple/apple_logo.png"/>" --%>
<!-- 				title="阿克苏苹果冰糖心 -- 小苹果" style="position: absolute; left: 42%; top: 57%;" /> -->
		</div>

		<div class="clear margin_1">
			<div class="float_l" style="width: 80%;">
				<p class="font_45 margin_l3" id="appleTitle"
					style="font-weight: 700; z-index: 2; position: relative;">阿克苏苹果
				</p>
				<p class="margin_l3" id="appleLog" style="font-weight: 700; z-index: 2; position: relative;font-size: 32px;"> (江浙沪皖包邮/其他地区加20元快递费)</p>
				<div class="magrin_t1">
					<div class="font_40 margin_l3" id="">
						<div class="" id="applePrice">
							<strong class='tb-promo-price'>
										<em class='tb-rmb'>¥</em>
										<em id='' class='font_40 tb-rmb'>78</em></strong><font style='font-weight: 100;'>8.5公斤/箱</font> <span id=""
								class="tb-promo-type">产地直销</span>
						</div>
						<div class="tb-promo-item-ft"></div>
					</div>
				</div>
			</div>
			<div class="float_r" style="z-index: -1;">
				<img src="<c:url value="/resources/images/farmOrder/logo.jpg"/>"
					title="logo" />
			</div>
		</div>

		<div class="clear margin_1">
			<div class="float_l" style="width: 30%;">
				<span class="font_40 margin_l3" style="font-weight: 700;">果园直供
				</span>
			</div>
			<div class="float_l" style="width: 32%;">
				<span class="font_40 margin_l3" style="font-weight: 700;">已销售&nbsp;<span>${volume }</span>&nbsp;笔
				</span>
			</div>
			<div class="float_l" style="width: 36%; text-align: right;">
				<span class="font_40 " style="font-weight: 700;">产地:新疆阿克苏</span>
			</div>
			<br>
			<hr class="clear hr margin_1">
		</div>

		<div class="clear margin_1">
			<table style="width: 100%;">
				<tr>
					<td class="font_26 "
						style="width: 33%; text-align: center; border-right: 1px solid #999;">
						品种</td>
					<td class="font_26 "
						style="width: 33%; text-align: center; border-right: 1px solid #999;">上市时间</td>
					<td class="font_26 " style="width: 33%; text-align: center;">客户评分</td>
				</tr>
				<tr>
					<td class="font_26 "
						style="width: 33%; text-align: center; border-right: 1px solid #999; padding-top: 5px;">阿克苏冰糖心</td>
					<td class="font_26 "
						style="width: 33%; text-align: center; border-right: 1px solid #999;">2016年12月08日</td>
					<td class="font_26 " style="width: 33%; text-align: center;">★★★★★</td>
				</tr>
			</table>
		</div>

		<div class="clear margin_1">
			<hr class="clear hr margin_1">
			<div class="float_l" style="margin: 40px 0 10px 0;">
				<span class="font_40 padding_l3">选择：</span>
			</div>
			<div class="float_l">
				<span style="display: -webkit-box;"> &nbsp;&nbsp;
					<div class="itm" style="margin: 20px 20px 10px 0;">
<!-- 						<a class="font_36 selected" id="type1" href="javascript:selectApple(0)">8.5公斤/箱</a> -->
						<a class="font_36" id="type1" href="javascript:selectApple(0)"><img alt="8.5公斤/箱" src="<c:url value="/resources/images/farmOrder/apple/norms4.jpg"/>"></a>
					</div>
					
<!-- 					<div class="itm" style="margin: 20px 20px 10px 0;"> -->
<!-- 						<a class="font_36" id="type2" href="javascript:selectApple(1)"><div style="margin: 25px 0 10px 0;">圣诞组合</div></a> -->
<%-- 						<img alt="圣诞组合" src="<c:url value="/resources/images/farmOrder/apple/guige1.jpg"/>"> --%>
<!-- 					</div> -->
					
<!-- 					<div class="itm" style="margin: 20px 20px 10px 0;"> -->
<!-- 						<a class="font_36" id="type3" href="javascript:selectApple(2)"><div style="margin: 25px 0 10px 0;">精装</div></a> -->
<%-- 						<img alt="精装" src="<c:url value="/resources/images/farmOrder/apple/guige2.jpg"/>"> --%>
<!-- 					</div> -->
					<%-- <c:if test="${successPromoteOrderNo <= 30 }">
					<div class="itm" style="margin: 20px 20px 10px 0;">
						<a class="font_36" id="type2" href="javascript:selectApple(1)"><div style="margin: 25px 0 10px 0;">2.5公斤/箱(限量特惠)</div></a>
					</div>
					</c:if> --%>
					
					<div class="itm" style="margin: 20px 20px 10px 0;">
						<a class="font_36" id="type4" href="javascript:selectApple(3)"><div style="margin: 25px 0 10px 0;">2.5公斤/箱</div></a>
					</div>
				</span>
			</div>
			<hr class="clear hr margin_1">
		</div>

		<div class="clear bottom">
			<a class="padding_lm2_1" id="buyBtn"
				href="<c:url value="/web/farm/getFarmOrderPage.action?type=0&openid=${openid }" />">立即购买</a>
			<a class="padding_lm2_3"
				href="<c:url value="/web/farm/getOrderList.action?type=3&openId=${openid }" />">
				查看订单</a> 
		</div>
	</div>
	<div style="margin-top:-20px;">
		<img data-echo="<c:url value="/resources/images/farmOrder/apple/detail1_spring.jpg"/>"
			style="width: 100%;" src="<c:url value="/resources/lib/lazyloading/blank.gif"/>" /> 
		<img data-echo="<c:url value="/resources/images/farmOrder/apple/detail2_spring.jpg"/>"
			style="width: 100%;" src="<c:url value="/resources/lib/lazyloading/blank.gif"/>" /> 
	</div>
	<%-- </c:forEach> --%>
	<script type="text/javascript"
		src="<c:url value="/Javascript/jquery-1.9.1.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/js/flavr.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/lib/lazyloading/echo.min.js"/>"></script>
	<script>
		echo.init(); //图片延迟加载
		function selectApple(type) {
			if (type==0) {
				//$("#appleImg").attr("src","<c:url value='/resources/images/farmOrder/apple/apple_goods.jpg'/>");
				$("#appleTitle").html("阿克苏苹果");
				$("#applePrice").html("<strong class='tb-promo-price'>"
										+"<em class='tb-rmb'>¥</em>"
										+"<em id='' class='font_40 tb-rmb'>78</em></strong>"
										+"<font style='font-weight: 100;'>8.5公斤/箱</font>"
										+"<span id='' class='tb-promo-type'>产地直销</span>");
				$("#buyBtn").attr("href","<c:url value='/web/farm/getFarmOrderPage.action?type=0&openid=${openid }' />");
				$("#type1").html("<img alt='8.5公斤/箱' src='<c:url value="/resources/images/farmOrder/apple/norms4.jpg"/>'>");
				$("#type4").html("<div style='margin: 25px 0 10px 0;'>2.5公斤/箱</div>");
				/* $("#type2").html("<div style='margin: 25px 0 10px 0;'>2.5公斤/箱</div>"); */
				$("#appleLog").html("(江浙沪皖包邮/其他地区加20元快递费)");
			} else if (type==1) {
				//$("#appleImg").attr("src","<c:url value='/resources/images/farmOrder/apple/apple_goods.jpg'/>");
				$("#appleTitle").html("阿克苏苹果 2.5公斤/箱");
				$("#applePrice").html("<em id='' class='font_40 tb-rmb'>¥ 28.8（限量特惠）</em>");
				$("#buyBtn").attr("href","<c:url value='/web/farm/getFarmOrderPage.action?type=1' />");
				//$("#type1").removeClass("selected");
				//$("#type2").removeClass("selected");
				//$("#type3").removeClass("selected");
				//$("#type4").removeClass("selected");
				$("#type1").html("<div style='margin: 25px 0 10px 0;'>8.5公斤/箱</div>")
// 				$("#type2").html("<div style='margin: 25px 0 10px 0;'>圣诞组合</div>")
// 				$("#type3").html("<div style='margin: 25px 0 10px 0;'>精装</div>");
				$("#type4").html("<div style='margin: 25px 0 10px 0;'>2.5公斤/箱</div>");
				$("#type2").html("<img alt='2.5公斤/箱' src='<c:url value="/resources/images/farmOrder/apple/guige2.jpg"/>'>");
				$("#appleLog").html("(江浙沪皖包邮/其他地区加8元快递费)");
			} else if (type==3) {
				//$("#appleImg").attr("src","<c:url value='/resources/images/farmOrder/apple/apple_goods.jpg'/>");
				$("#appleTitle").html("阿克苏苹果 2.5公斤/箱");
				$("#applePrice").html("<em id='' class='font_40 tb-rmb'>¥ 28.8</em>");
				$("#buyBtn").attr("href","<c:url value='/web/farm/getFarmOrderPage.action?type=3' />");
				//$("#type1").removeClass("selected");
				/* $("#type2").removeClass("selected"); */
				//$("#type3").removeClass("selected");
				//$("#type4").addClass("selected");
				$("#type1").html("<div style='margin: 25px 0 10px 0;'>8.5公斤/箱</div>")
// 				$("#type2").html("<div style='margin: 25px 0 10px 0;'>圣诞组合</div>")
// 				$("#type3").html("<div style='margin: 25px 0 10px 0;'>精装</div>");
				/* $("#type2").html("<div style='margin: 25px 0 10px 0;'>2.5公斤/箱</div>"); */
				$("#type4").html("<img alt='2.5公斤/箱' src='<c:url value="/resources/images/farmOrder/apple/norms5.jpg"/>'>");
				$("#appleLog").html("(江浙沪皖包邮/其他地区加8元快递费)");
			}
		}
		function close() {
			$("#shareHint").addClass("hide");
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
		timestamp : '${timestamp}',
		nonceStr : '${nonceStr}',
		signature : '${signature}',
		jsApiList : [ 'checkJsApi', 'onMenuShareTimeline', 'onMenuShareAppMessage', 'onMenuShareQQ', 'onMenuShareWeibo', 'onMenuShareQZone', ]
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

	wx.ready(function() {
		wx.onMenuShareAppMessage({
			title : '阿克苏苹果冰糖心',
			desc : '阿克苏苹果冰糖心！',
			link : localhostPaht + '<c:url value="/web/farm/getFarmProducePage.action?share_openid=${openid}"/>',
			imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/header_spring.jpg"/>',
			trigger : function(res) {
			},
			success : function(res) {
			},
			cancel : function(res) {
			},
			fail : function(res) {
				alert(JSON.stringify(res));
			}
		});

		wx.onMenuShareTimeline({
			title : '阿克苏苹果冰糖心！',
			link : localhostPaht + '<c:url value="/web/farm/getFarmProducePage.action?share_openid=${openid}"/>',
			imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/header_spring.jpg"/>',
			trigger : function(res) {
			},
			success : function(res) {
			},
			cancel : function(res) {
			},
			fail : function(res) {
				alert(JSON.stringify(res));
			}
		});

		wx.onMenuShareQQ({
			title : '阿克苏苹果冰糖心',
			desc : '阿克苏苹果冰糖心！',
			link : localhostPaht + '<c:url value="/web/farm/getFarmProducePage.action?share_openid=${openid}"/>',
			imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/header_spring.jpg"/>',
			success : function() {
				// 用户确认分享后执行的回调函数
			},
			cancel : function() {
				// 用户取消分享后执行的回调函数
			}
		});

		wx.onMenuShareWeibo({
			title : '阿克苏苹果冰糖心',
			desc : '阿克苏苹果冰糖心！',
			link : localhostPaht + '<c:url value="/web/farm/getFarmProducePage.action?share_openid=${openid}"/>',
			imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/header_spring.jpg"/>',
			success : function() {
				// 用户确认分享后执行的回调函数
			},
			cancel : function() {
				// 用户取消分享后执行的回调函数
			}
		});

		wx.onMenuShareQZone({
			title : '阿克苏苹果冰糖心',
			desc : '阿克苏苹果冰糖心！',
			link : localhostPaht + '<c:url value="/web/farm/getFarmProducePage.action?share_openid=${openid}"/>',
			imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/header_spring.jpg"/>',
			success : function() {
				// 用户确认分享后执行的回调函数
			},
			cancel : function() {
				// 用户取消分享后执行的回调函数
			}
		});
	});
</script>
</body>
</html>
