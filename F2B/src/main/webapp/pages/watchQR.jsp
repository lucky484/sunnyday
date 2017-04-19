<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>无权限</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
    <style type="text/css">
        *{margin:0;padding:0;}
    </style>
</head>
<body>

<p style=""><br style="margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box !important; word-wrap: break-word !important;"></p>

<p style="margin-top: 25px; margin-bottom: 25px; white-space: normal; line-height: 1.75em; text-align: center;">
    <strong><span style="color: rgb(255, 255, 255); background-color: rgb(31, 73, 125);"></span></strong>
    <strong style="text-align: center;"><span style="margin: 0px; padding: 0px; max-width: 100%; color: rgb(192, 0, 0); box-sizing: border-box !important; word-wrap: break-word !important;">抱歉，只有关注后才能购买抽奖哦</span></strong>
</p>

<p style="text-align: center;"><span style="margin: 0px; padding: 0px; max-width: 100%; color: rgb(127, 127, 127); box-sizing: border-box !important; word-wrap: break-word !important;">请先关注公众号</span></p>

<p style="text-align: center;">
    <img data-s="300,640" data-type="jpeg" data-ratio="1" data-w="258"
         style="margin: 0px; padding: 0px; box-sizing: border-box !important; word-wrap: break-word !important; width: auto !important; visibility: visible !important; height: auto !important;" _width="auto"
         src="<c:url value="/images/erweima.jpg"/>">
    <br style="margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box !important; word-wrap: break-word !important;">
</p>

<p style="text-align: center;">▲</p>

<p style=""><br style="margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box !important; word-wrap: break-word !important;"></p>

<p style="text-align: center;"><span style="margin: 0px; padding: 0px; max-width: 100%; color: rgb(127, 127, 127); box-sizing: border-box !important; word-wrap: break-word !important;">
    长按图片，识别二维码</span></p>

<p style=""><br style="margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box !important; word-wrap: break-word !important;"></p>

<p style=""><br style="margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box !important; word-wrap: break-word !important;"></p>

<p style="text-align: center;"><span style="margin: 0px; padding: 0px; max-width: 100%; color: rgb(127, 127, 127); box-sizing: border-box !important; word-wrap: break-word !important;">谢谢！</span></p>

<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

<%--<script>--%>
    <%--/*--%>
     <%--* 注意：--%>
     <%--* 1. 所有的JS接口只能在公众号绑定的域名下调用，公众号开发者需要先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。--%>
     <%--* 2. 如果发现在 Android 不能分享自定义内容，请到官网下载最新的包覆盖安装，Android 自定义分享接口需升级至 6.0.2.58 版本及以上。--%>
     <%--* 3. 常见问题及完整 JS-SDK 文档地址：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html--%>
     <%--*--%>
     <%--* 开发中遇到问题详见文档“附录5-常见错误及解决办法”解决，如仍未能解决可通过以下渠道反馈：--%>
     <%--* 邮箱地址：weixin-open@qq.com--%>
     <%--* 邮件主题：【微信JS-SDK反馈】具体问题--%>
     <%--* 邮件内容说明：用简明的语言描述问题所在，并交代清楚遇到该问题的场景，可附上截屏图片，微信团队会尽快处理你的反馈。--%>
     <%--*/--%>
    <%--wx.config({--%>
        <%--debug: false,--%>
        <%--appId: 'wx0b28bc155b2e1e10',--%>
        <%--timestamp: ${timestamp},--%>
        <%--nonceStr: '${nonceStr}',--%>
        <%--signature: '${signature}',--%>
        <%--jsApiList: [--%>
            <%--'checkJsApi',--%>
            <%--'onMenuShareTimeline',--%>
            <%--'onMenuShareAppMessage',--%>
            <%--'onMenuShareQQ',--%>
            <%--'onMenuShareWeibo'--%>
        <%--]--%>
    <%--});--%>
<%--</script>--%>
<%--<script type="text/javascript">--%>
    <%--/*$(document).ready(function(){--%>
     <%--$("img").click(function(){--%>
     <%--wx.checkJsApi({--%>
     <%--jsApiList: [--%>
     <%--'onMenuShareAppMessage'--%>
     <%--],--%>
     <%--success: function (res) {--%>
     <%--alert(JSON.stringify(res));--%>
     <%--}--%>
     <%--});--%>
     <%--});--%>
     <%--});*/--%>

    <%--wx.ready(function () {--%>

        <%--wx.onMenuShareAppMessage({--%>
            <%--title: '关注一汽吉林抽大奖',--%>
            <%--desc: '关注一汽吉林,大奖等着你!',--%>
            <%--link: 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx0b28bc155b2e1e10&redirect_uri=http%3A%2F%2Fyq.jdfgold.com%2Flottery%2FshitWechat.action&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect',--%>
            <%--imgUrl: 'http://yq.jdfgold.com/lottery/share.jpg',--%>
            <%--trigger: function (res) {--%>
            <%--},--%>
            <%--success: function (res) {--%>
            <%--},--%>
            <%--cancel: function (res) {--%>
                <%--alert('已取消');--%>
            <%--},--%>
            <%--fail: function (res) {--%>
                <%--alert(JSON.stringify(res));--%>
            <%--}--%>
        <%--});--%>

        <%--wx.onMenuShareTimeline({--%>
            <%--title: '关注一汽吉林抽大奖',--%>
            <%--desc: '关注一汽吉林,大奖等着你!',--%>
            <%--link: 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx0b28bc155b2e1e10&redirect_uri=http%3A%2F%2Fyq.jdfgold.com%2Flottery%2FshitWechat.action&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect',--%>
            <%--imgUrl: 'http://yq.jdfgold.com/lottery/share.jpg',--%>
            <%--trigger: function (res) {--%>
            <%--},--%>
            <%--success: function (res) {--%>
            <%--},--%>
            <%--cancel: function (res) {--%>
                <%--alert('已取消');--%>
            <%--},--%>
            <%--fail: function (res) {--%>
                <%--alert(JSON.stringify(res));--%>
            <%--}--%>
        <%--});--%>

    <%--});--%>
<%--</script>--%>
</body>
</html>