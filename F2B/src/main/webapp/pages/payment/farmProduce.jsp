<%--
  User: Mozzie.chu
  Date: 2016/8/18
  Time: 10:20
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<c:url value="/css/farmProduce.css"/>" type="text/css"
	rel="stylesheet" />
<%-- <link href="<c:url value="/css/style.css"/>" type="text/css" --%>
<!-- 	rel="stylesheet" /> -->
<link href="<c:url value="/css/bootstrap.min.css"/>" type="text/css"
	rel="stylesheet" />
<link href="<c:url value="/resources/lib/lazyloading/echo.css"/>" type="text/css"
	rel="stylesheet" />
<title>敦煌二墩村大漠葡萄</title>
<style type="text/css">
/* .selected{ */
/* 	border: 2px solid #e3393c; */
/* 	white-space: nowrap; */
/* 	width: 123px; */
/* 	text-align: center; */
/*     text-decoration: none; */
/* } */
/* .itm { */
/* 	border: 1px solid #ccc; */
/* 	white-space: nowrap; */
/* 	width: 123px; */
/* 	text-align: center; */
/* } */
/* .itm :focus,.itm :hover{ */
/* 	border: 2px solid #e3393c; */
/*     color: #000; */
/* } */
.select,.select:link {
	width: 123px;
	border: 1px solid #ccc;
	padding: 10px 20px;
    text-decoration: none;
    color: #666;
	
}
.selected,.selected:link {
	border: 2px solid #e3393c;
    color: #000;
    width: 123px;
    padding: 10px 20px;
    text-decoration: none;
}
</style>
</head>
<body>
<%-- <c:forEach items="${list }" var="farmProduce"> --%>
	<div>
		<form action="">
			<div>
				<img id="grapeImg"
					src="<c:url value="/resources/images/farmOrder/grape1.jpg"/>"
					title="青提top"
					style="width: 100%; height: auto; margin-bottom: 20px;" />
			</div>

			<div class="clear margin_1">
				<div class="float_l">
					<span class="font_40 margin_l3" id="grapeTitle" style="font-weight: 700;">敦煌二墩村大漠葡萄 - 青提</span>
					<div class="magrin_t1">
						<div class="font_40 margin_l3" id="" >
							<div class="">
								<strong class="tb-promo-price">
									<em class="tb-rmb">¥</em>
									<em id="" class="tb-rmb">210</em>
								</strong>
								<span id="" class="tb-promo-type">公益直销</span>
							</div>
							<div class="tb-promo-item-ft"></div>
						</div>
					</div>
				</div>
				<div class="float_r">
					<img src="<c:url value="/resources/images/farmOrder/logo.jpg"/>"
					title="logo"/>
				</div>
			</div>

			<div class="clear margin_1">
				<div class="float_l" style="width:50%;">
					<span class="font_40 margin_l3">已销&nbsp;<span>${volume }</span>&nbsp;笔</span>
				</div>
				<div class="float_l" style="width:50%;">
					<span class="font_40 margin_l5">敦煌二墩村</span>
				</div>
			<br><hr class="clear hr margin_1">
			</div>
			
			<div class="clear margin_1">
				<table style="width: 100%;">
					<tr>
						<td class="font_26 " style="width:33%; text-align:center;border-right: 1px solid #999;">
							品种</td>
						<td class="font_26 " style="width:33%; text-align:center;border-right: 1px solid #999;">上市时间</td>
						<td class="font_26 " style="width:33%; text-align:center;">客户评分</td>
					</tr>
					<tr>
						<td class="font_26 " style="width:33%; text-align:center;border-right: 1px solid #999;">青提无核白鸡心</td>
						<td class="font_26 " style="width:33%; text-align:center;border-right: 1px solid #999;">2016年08月20日</td>
						<td class="font_26 " style="width:33%; text-align:center;">★★★★★</td>
					</tr>
				</table>
			</div>

			<div class="clear margin_1"><hr class="clear hr margin_1">
				<div class="float_l">
					<span class="font_40 padding_l3">选择商品分类：</span>
				</div>
				<div class="float_l">
					<span style="display: flex;">
					&nbsp;&nbsp;<div class="itm" style="margin-right: 25px;"><a class="font_36 selected" id="type1" href="javascript:selectGrape(1)">青提</a></div>
					&nbsp;&nbsp;&nbsp;&nbsp;<div class="itm "><a class="font_36 select" id="type2" href="javascript:selectGrape(2)">红提</a></div>
					</span>
				</div>
				<hr class="clear hr margin_1">
			</div>
			
			<div class="clear bottom">
				<a class="padding_lm2_2" id="buyBtn" href="<c:url value="/web/farm/getFarmOrderPage.action?type=1&produceId=${farmProduce.produceId }" />">立即购买</a>
				<a class="padding_lm2_2 demo" href="/F2B/web/farm/getOrderList.action?type=1"> 查看订单</a> <%--  --%>
			</div>

		</form>
	</div>
	<div>
		<img data-echo="<c:url value="/resources/images/farmOrder/grape/181952655461522983_01.gif"/>" style="width:100%;" src="<c:url value="/resources/lib/lazyloading/blank.gif"/>" />
		<img data-echo="<c:url value="/resources/images/farmOrder/grape/181952655461522983_02.gif"/>" style="width:100%;" src="<c:url value="/resources/lib/lazyloading/blank.gif"/>" />
		<img data-echo="<c:url value="/resources/images/farmOrder/grape/181952655461522983_03.gif"/>" style="width:100%;" src="<c:url value="/resources/lib/lazyloading/blank.gif"/>" />
		<img data-echo="<c:url value="/resources/images/farmOrder/grape/181952655461522983_04.gif"/>" style="width:100%;" src="<c:url value="/resources/lib/lazyloading/blank.gif"/>" />
		<img data-echo="<c:url value="/resources/images/farmOrder/grape/181952655461522983_05.gif"/>" style="width:100%;" src="<c:url value="/resources/lib/lazyloading/blank.gif"/>" />
		<img data-echo="<c:url value="/resources/images/farmOrder/grape/181952655461522983_06.gif"/>" style="width:100%;" src="<c:url value="/resources/lib/lazyloading/blank.gif"/>" />
		<img data-echo="<c:url value="/resources/images/farmOrder/grape/181952655461522983_07.gif"/>" style="width:100%;" src="<c:url value="/resources/lib/lazyloading/blank.gif"/>" />
		<img data-echo="<c:url value="/resources/images/farmOrder/grape/181952655461522983_08.gif"/>" style="width:100%;" src="<c:url value="/resources/lib/lazyloading/blank.gif"/>" />
		<img data-echo="<c:url value="/resources/images/farmOrder/grape/181952655461522983_09.gif"/>" style="width:100%;" src="<c:url value="/resources/lib/lazyloading/blank.gif"/>" />
		<img data-echo="<c:url value="/resources/images/farmOrder/grape/181952655461522983_10.gif"/>" style="width:100%;" src="<c:url value="/resources/lib/lazyloading/blank.gif"/>" />
		<img data-echo="<c:url value="/resources/images/farmOrder/grape/181952655461522983_11.gif"/>" style="width:100%;" src="<c:url value="/resources/lib/lazyloading/blank.gif"/>" />
		<img data-echo="<c:url value="/resources/images/farmOrder/grape/181952655461522983_12.gif"/>" style="width:100%;" src="<c:url value="/resources/lib/lazyloading/blank.gif"/>" />
		<img data-echo="<c:url value="/resources/images/farmOrder/grape/181952655461522983_13.gif"/>" style="width:100%;" src="<c:url value="/resources/lib/lazyloading/blank.gif"/>" />
		<img data-echo="<c:url value="/resources/images/farmOrder/grape/181952655461522983_14.gif"/>" style="width:100%;" src="<c:url value="/resources/lib/lazyloading/blank.gif"/>" />
		<img data-echo="<c:url value="/resources/images/farmOrder/grape/181952655461522983_15.gif"/>" style="width:100%;" src="<c:url value="/resources/lib/lazyloading/blank.gif"/>" />
		<img data-echo="<c:url value="/resources/images/farmOrder/grape/181952655461522983_16.gif"/>" style="width:100%;" src="<c:url value="/resources/lib/lazyloading/blank.gif"/>" />
		<img data-echo="<c:url value="/resources/images/farmOrder/grape/181952655461522983_17.gif"/>" style="width:100%;" src="<c:url value="/resources/lib/lazyloading/blank.gif"/>" />
		<img data-echo="<c:url value="/resources/images/farmOrder/grape/181952655461522983_18.jpg"/>" style="width:100%;" src="<c:url value="/resources/lib/lazyloading/blank.gif"/>" />
		<img data-echo="<c:url value="/resources/images/farmOrder/grape/181952655461522983_19.gif"/>" style="width:100%;" src="<c:url value="/resources/lib/lazyloading/blank.gif"/>" />
	</div>
<%-- </c:forEach> --%>
	<script type="text/javascript"
		src="<c:url value="/Javascript/jquery-1.9.1.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/lib/lazyloading/echo.min.js"/>"></script>
	<script type="text/javascript">
	echo.init();  //图片延迟加载
		function selectGrape(type) {
			if (type==1) {
				$("#grapeImg").attr("src","<c:url value='/resources/images/farmOrder/grape1.jpg'/>");
				$("#grapeTitle").html("敦煌二墩村大漠葡萄 - 青提");
				$("#buyBtn").attr("href","<c:url value='/web/farm/getFarmOrderPage.action?type=1' />");
				$("#type1").addClass("selected");
				$("#type2").removeClass("selected");
			} else {
				$("#grapeImg").attr("src","<c:url value='/resources/images/farmOrder/grape2.jpg'/>");
				$("#grapeTitle").html("敦煌二墩村大漠葡萄 - 红提");
				$("#buyBtn").attr("href","<c:url value='/web/farm/getFarmOrderPage.action?type=2' />");
				$("#type1").removeClass("selected");
				$("#type2").addClass("selected");
				$("#type1").addClass("select");
			}
		}
		
		function formatTimeMillis(dateTime) {
			if (!dateTime) {
				return "";
			}
			var date = new Date(dateTime);
			var y = date.getFullYear();
			var m = (date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
					+ (date.getMonth() + 1);
			var d = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
			var h = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
			var mi = date.getMinutes() < 10 ? "0" + date.getMinutes() : date
					.getMinutes();
			var s = date.getSeconds() < 10 ? "0" + date.getSeconds() : date
					.getSeconds();
			var datetime = y + "-" + m + "-" + d;
			return datetime;
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
        debug: false,
        appId: 'wx80bc1ec970681073',
        timestamp: '${timestamp}',
        nonceStr: '${nonceStr}',
        signature: '${signature}',
        jsApiList: [
            'checkJsApi',
            'onMenuShareTimeline',
            'onMenuShareAppMessage',
            'onMenuShareQQ',
            'onMenuShareWeibo',
            'onMenuShareQZone',
        ]
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
    /*$(document).ready(function(){
     $("img").click(function(){
     wx.checkJsApi({
     jsApiList: [
     'onMenuShareAppMessage'
     ],
     success: function (res) {
     alert(JSON.stringify(res));
     }
     });
     });
     });*/

    wx.ready(function () {

        wx.onMenuShareAppMessage({
            title: '敦煌二墩村大漠葡萄',
            desc: '欢迎购买大漠葡萄，付款后抽奖，100%中奖！',
            link: 'http://tizi.qiurunfarm.com/F2B/web/farm/getFarmProducePage.action',
            imgUrl: localhostPaht+'<c:url value="/resources/images/farmOrder/grape1.jpg"/>',
            trigger: function (res) {
            },
            success: function (res) {
            },
            cancel: function (res) {
            },
            fail: function (res) {
                alert(JSON.stringify(res));
            }
        });

        wx.onMenuShareTimeline({
        	title: '敦煌二墩村大漠葡萄',
            link: 'http://tizi.qiurunfarm.com/F2B/web/farm/getFarmProducePage.action',
            imgUrl: localhostPaht+'<c:url value="/resources/images/farmOrder/grape1.jpg"/>',
            trigger: function (res) {
            },
            success: function (res) {
            },
            cancel: function (res) {
            },
            fail: function (res) {
                alert(JSON.stringify(res));
            }
        });

        wx.onMenuShareQQ({
        	 title: '敦煌二墩村大漠葡萄',
             desc: '欢迎购买大漠葡萄，付款后抽奖，100%中奖！',
             link: 'http://tizi.qiurunfarm.com/F2B/web/farm/getFarmProducePage.action',
             imgUrl: localhostPaht+'<c:url value="/resources/images/farmOrder/grape1.jpg"/>',
            success: function () { 
               // 用户确认分享后执行的回调函数
            },
            cancel: function () { 
               // 用户取消分享后执行的回调函数
            }
        });
        
        wx.onMenuShareWeibo({
        	title: '敦煌二墩村大漠葡萄',
            desc: '欢迎购买大漠葡萄，付款后抽奖，100%中奖！',
            link: 'http://tizi.qiurunfarm.com/F2B/web/farm/getFarmProducePage.action',
            imgUrl: localhostPaht+'<c:url value="/resources/images/farmOrder/grape1.jpg"/>',
            success: function () {
               // 用户确认分享后执行的回调函数
            },
            cancel: function () { 
                // 用户取消分享后执行的回调函数
            }
        });
        
        wx.onMenuShareQZone({
        	title: '敦煌二墩村大漠葡萄',
            desc: '欢迎购买大漠葡萄，付款后抽奖，100%中奖！',
            link: 'http://tizi.qiurunfarm.com/F2B/web/farm/getFarmProducePage.action',
            imgUrl: localhostPaht+'<c:url value="/resources/images/farmOrder/grape1.jpg"/>',
            success: function () { 
               // 用户确认分享后执行的回调函数
            },
            cancel: function () { 
                // 用户取消分享后执行的回调函数
            }
        });
    });
</script>
</body>
</html>
