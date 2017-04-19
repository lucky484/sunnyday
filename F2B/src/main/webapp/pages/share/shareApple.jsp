<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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
			link : localhostPaht + '<c:url value="/web/farm/getFarmProducePage.action?share_openid=${sessionScope.openid}"/>',
			imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/apple_goods.jpg"/>',
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
			link : localhostPaht + '<c:url value="/web/farm/getFarmProducePage.action?share_openid=${sessionScope.openid}"/>',
			imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/apple_goods.jpg"/>',
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
			link : localhostPaht + '<c:url value="/web/farm/getFarmProducePage.action?share_openid=${sessionScope.openid}"/>',
			imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/apple_goods.jpg"/>',
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
			link : localhostPaht + '<c:url value="/web/farm/getFarmProducePage.action?share_openid=${sessionScope.openid}"/>',
			imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/apple_goods.jpg"/>',
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
			link : localhostPaht + '<c:url value="/web/farm/getFarmProducePage.action?share_openid=${sessionScope.openid}"/>',
			imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/apple_goods.jpg"/>',
			success : function() {
				// 用户确认分享后执行的回调函数
			},
			cancel : function() {
				// 用户取消分享后执行的回调函数
			}
		});
	});
</script>