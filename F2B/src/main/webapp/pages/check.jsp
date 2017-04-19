<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport"/>
    <title>好享吃抽奖</title>
    <script type="text/javascript" src="<c:url value="/Javascript/bootstrap.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/Javascript/jquery-1.9.1.min.js"/>"></script>
    <link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/bootstrap-theme.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/style.css"/>" type="text/css" rel="stylesheet" />
    <script type="text/javascript">
        $(function () {
            $('#formbackground').height($(window).height());
            $('#formbackground').width($(window).width());
        });
    </script>

    <script>
        var _hmt = _hmt || [];
        (function() {
            var hm = document.createElement("script");
            hm.src = "//hm.baidu.com/hm.js?bb3d9f7871f6122cd0cd7b8e3cc5d534";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
    </script>
    <script type="text/javascript">
        window.onload = function(){
            if(!isWeiXin()){
                $("body").html("");
                alert("请用微信打开！");
            }
        }
        function isWeiXin(){
            var ua = window.navigator.userAgent.toLowerCase();
            if(ua.match(/MicroMessenger/i) == 'micromessenger'){
                return true;
            }else{
                return false;
            }
        }
    </script>
</head>
<body>
<div id="formbackground" style="position:absolute; z-index:99;height:100%; width:100%">
    <img
            src="<c:url value="/images/share.png"/>" height="100%" width="100%"/>
    <div style="top:60%;height:20%;text-shadow:none;position:absolute;z-index:9;text-align:center;width:100%;display:block;">
        <img src="<c:url value="/images/erweima.png"/>" style="width:36%;">
    </div>
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
        debug: false,
        appId: 'wx9b96982e6d5b1c58',
        timestamp: ${timestamp},
        nonceStr: '${nonceStr}',
        signature: '${signature}',
        jsApiList: [
            'checkJsApi',
            'onMenuShareTimeline',
            'onMenuShareAppMessage',
            'onMenuShareQQ',
            'onMenuShareWeibo'
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
            title: '时尚季“轻”食尚，百万壕礼格中藏',
            desc: '哇，我中了维多麦发放的${text}，听说你也有份奖品，快来领取！',
            link: 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9b96982e6d5b1c58&redirect_uri=http%3A%2F%2Fweetabix.tnbchina.com%2Fwdm1%2FshitWechat.action%3Fname%3D1&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect',
            imgUrl: localhostPaht+'<c:url value="/images/share1.jpg"/>',
            trigger: function (res) {
            },
            success: function (res) {
            },
            cancel: function (res) {
                alert('已取消');
            },
            fail: function (res) {
                alert(JSON.stringify(res));
            }
        });

        wx.onMenuShareTimeline({
            title: '哇，我中了维多麦发放的${text}，听说你也有份奖品，快来领取！',
            link: 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9b96982e6d5b1c58&redirect_uri=http%3A%2F%2Fweetabix.tnbchina.com%2Fwdm1%2FshitWechat.action%3Fname%3D1&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect',
            imgUrl: localhostPaht+'<c:url value="/images/share1.jpg"/>',
            trigger: function (res) {
            },
            success: function (res) {
            },
            cancel: function (res) {
                alert('已取消');
            },
            fail: function (res) {
                alert(JSON.stringify(res));
            }
        });

    });
</script>
</body>
</html>