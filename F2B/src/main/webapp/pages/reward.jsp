<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport" />
<title>抽奖结果</title>
<link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet">
<link href="<c:url value="/css/bootstrap-theme.min.css"/>"
	rel="stylesheet">
<link href="<c:url value="/css/style.css"/>" type="text/css" rel="stylesheet" />
<link href="<c:url value="/css/weui.css"/>" type="text/css" rel="stylesheet" />
<style>
.hide {
	display: none;
}

.sharePngDiv {
	width: 60px;
	height: 60px;
	display: inline-block;
}

.sharePng {
	width: 100%;
	height: 100%;
}
</style>
<script type="text/javascript"
	src="<c:url value="/Javascript/jquery-1.9.1.min.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/Javascript/bootstrap.min.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/Javascript/snowfall.jquery.min.js"/>"></script>
<script type="text/javascript">
	$(function() {
		$('#formbackground').height($(window).height());
		$('#formbackground').width($(window).width());
		$('#share_guide').height($(window).height());
		$('#share_guide').width($(window).width());
	});
</script>
</head>
<body>
	<div id="formbackground">
		<img src="<c:url value='/images/prize/prize_top_1.jpg'/>"
			style="width: 100%; margin-top: 0;" />
		<div style="margin: -15px auto 0;">
			<c:if test="${reward == 0 }">
				<img src="<c:url value='/images/prize/sorry.jpg'/>" style="width: 100%; margin-bottom: -3px;" /> 
				<img src="<c:url value='/images/prize/no_prize.jpg'/>" style="width: 100%;margin:0 auto;" />
			</c:if>
			<c:if test="${reward ne 0 }">
				<img src="<c:url value='/images/prize/prize_middle_1.jpg'/>" style="width: 100%; margin-bottom: -3px;" /> 
				<img src="<c:url value='/images/prize/${reward }.jpg'/>"
					style="width: 100%;margin:0 auto;" />
				<div id="rewardHint" class="weui_dialog_alert">
				    <div class="weui_mask"></div>
				    <div class="weui_dialog">
				        <div class="weui_dialog_hd"><strong class="weui_dialog_title">恭喜</strong></div>
				        <div class="weui_dialog_bd">恭喜您中奖了，奖品会在一个工作日内审核通过后发放，请耐心等待</div>
				        <div class="weui_dialog_ft">
				            <a href="javascript:close()" class="weui_btn_dialog primary">确定</a>
				        </div>
				    </div>
				</div>
			</c:if>
			<img src="<c:url value='/images/prize/prize_middle_2.jpg'/>"
				style="width: 100%;margin:0 auto;" />
		</div>
		<c:if test="${sessionScope.rewardTimes<=0 }">
			<div style="width:100%;text-align: center;">
				<img src="<c:url value='/images/prize/share.png'/>" id="shareBtn"
					style="left: 35%; margin: 0 auto; position: absolute; width: 100px; height: 40px;" />
			</div>
		</c:if>
		<c:if test="${sessionScope.rewardTimes>0 }">
			<div style="width:100%;text-align: center;">
				<img src="<c:url value='/images/prize/continue_reward.png'/>"
					id="continueRewardBtn" style="width: 100px; height: 35px;" /> <img
					src="<c:url value='/images/prize/share.png'/>" id="shareBtn"
					style="width: 100px; height: 35px;" />
			</div>
		</c:if>
		<img src="<c:url value='/images/prize/prize_bottom.jpg'/>"
			style="width: 100%; margin-bottom: 0; position: absolute; bottom: 0;z-index:-1;" />
	</div>
	<div id="share_guide" class="hide" style="position: absolute;top:0;background-color: rgba(153,153,153,0.7);">
		<img style="z-index:99;width:100%;height:100%;position: absolute;top:0;" src="<c:url value='/images/share/share_guide.png'/>" />
	</div>
	<!-- 根据奖项级别，设置雪花效果的数量 -->
	<c:if test="${reward==5 or reward==10 }">
		<script>
			$(document).snowfall({
				image : "<c:url value='/images/redpack.png'/>",
				flakeCount : 10,
				minSize : 10,
				maxSize : 32,
			});
		</script>
	</c:if>
	<c:if test="${reward==40 or reward==100 }">
		<script>
			$(document).snowfall({
				image : "<c:url value='/images/redpack.png'/>",
				flakeCount : 20,
				minSize : 10,
				maxSize : 32,
			});
		</script>
	</c:if>
	<c:if test="${reward==500 or reward==1000 }">
		<script>
			$(document).snowfall({
				image : "<c:url value='/images/redpack.png'/>",
				flakeCount : 40,
				minSize : 10,
				maxSize : 32,
			});
		</script>
	</c:if>
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
				imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/apple_goods.jpg"/>',
				trigger : function(res) {
				},
				success : function(res) {
					$("#share_guide").addClass("hide");
				},
				cancel : function(res) {
					$("#share_guide").addClass("hide");
				},
				fail : function(res) {
					$("#share_guide").addClass("hide");
				}
			});

			wx.onMenuShareTimeline({
				title : '阿克苏苹果冰糖心！',
				link : localhostPaht + '<c:url value="/web/farm/getFarmProducePage.action?share_openid=${openid}"/>',
				imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/apple_goods.jpg"/>',
				trigger : function(res) {
				},
				success : function(res) {
					$("#share_guide").addClass("hide");
				},
				cancel : function(res) {
					$("#share_guide").addClass("hide");
				},
				fail : function(res) {
					$("#share_guide").addClass("hide");
				}
			});

			wx.onMenuShareQQ({
				title : '阿克苏苹果冰糖心',
				desc : '阿克苏苹果冰糖心！',
				link : localhostPaht + '<c:url value="/web/farm/getFarmProducePage.action?share_openid=${openid}"/>',
				imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/apple_goods.jpg"/>',
				success : function() {
					// 用户确认分享后执行的回调函数
					$("#share_guide").addClass("hide");
				},
				cancel : function() {
					// 用户取消分享后执行的回调函数
					$("#share_guide").addClass("hide");
				}
			});

			wx.onMenuShareWeibo({
				title : '阿克苏苹果冰糖心',
				desc : '阿克苏苹果冰糖心！',
				link : localhostPaht + '<c:url value="/web/farm/getFarmProducePage.action?share_openid=${openid}"/>',
				imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/apple_goods.jpg"/>',
				success : function() {
					// 用户确认分享后执行的回调函数
					$("#share_guide").addClass("hide");
				},
				cancel : function() {
					// 用户取消分享后执行的回调函数
					$("#share_guide").addClass("hide");
				}
			});

			wx.onMenuShareQZone({
				title : '阿克苏苹果冰糖心',
				desc : '阿克苏苹果冰糖心！',
				link : localhostPaht + '<c:url value="/web/farm/getFarmProducePage.action?share_openid=${openid}"/>',
				imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/apple_goods.jpg"/>',
				success : function() {
					// 用户确认分享后执行的回调函数
				},
				cancel : function() {
					// 用户取消分享后执行的回调函数
				}
			});
		});

		// 分享按钮
		$("#shareBtn").click(function() {
			$("#share_guide").removeClass("hide");
		});

		// 继续抽奖
		$("#continueRewardBtn").click(function() {
			window.location.href = "<c:url value='/lottery.action?orderNo=${orderNo}'/>";
		});
		
		function close() {
			$("#rewardHint").addClass("hide");
		}
	</script>
</body>
</html>