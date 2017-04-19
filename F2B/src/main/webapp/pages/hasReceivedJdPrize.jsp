<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/25
  Time: 16:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport" />
    <title>好享吃抽奖</title>
    <link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/lottery.css"/>" type="text/css" rel="stylesheet" />
    <link href="<c:url value="/css/style.css"/>" type="text/css" rel="stylesheet" />
    <style>
        #imgMoney {
            width: 13%;
            height: 6%;
            position: absolute;
            top: 52%;
            margin-left: 38%;
            z-index: 6;
        }

        .img-money {
            position: absolute;
            top: 40%;
        }
    </style>

    <script type="text/javascript" src="../Javascript/lottery.js"></script>
    <script type="text/javascript" src="../Javascript/jquery-1.9.1.min.js"></script>
    <script type="text/javascript">
        var t;
        var i=3;
        $(function () {
            var prizeId = window.location.href.split('id=')[1];
            $("#imgBg").attr("src", "<c:url value="/images/bg"/>/jd_after" + prizeId + ".png")
            $('#formbackground').height($(window).height());
            $('#formbackground').width($(window).width());
            time();

        });
        function time()
        {
            if(i==0)
            {
                clearTimeout(t);
                window.location.href = "http://coupon.m.jd.com/coupons/show.action?key=e5a37a43dc754d26ba3dea52ce2a1ab3&roleId=2493327&to=sale.jd.com/act/nrz3le8ubt6wpyv.html";
            }
            else
            {
                i--;
                t= setTimeout(time,1000);
            }
        }
    </script>
</head>
<body>
<div id="formbackground" style="position:absolute;height:100%; width:100%">
    <img id="imgBg" height="100%" width="100%" />
</div>

</body>


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
            title: '关注维多麦',
            desc: '关注维多麦',
            link: 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9b96982e6d5b1c58&redirect_uri=http%3A%2F%2Fwl.chenlichun.me%2FshitWechat.action&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect',
            imgUrl: 'http://yq.jdfgold.com/lotteryCar/share.jpg',
            trigger: function (res) {
            },
            success: function (res) {
                $.post("<c:url value="/updateState.action"/>", {award:${prizeId}},
                        function (result) {
                            if (result == 1) {
                                window.location.href = "http://coupon.m.jd.com/coupons/show.action?key=e5a37a43dc754d26ba3dea52ce2a1ab3&roleId=2493327&to=sale.jd.com/act/nrz3le8ubt6wpyv.html";
                            }
                            else {
                                alert("不能重复领取");
                            }
                        }
                );
            },
            cancel: function (res) {
                alert('已取消');
            },
            fail: function (res) {
                alert(JSON.stringify(res));
            }
        });

        wx.onMenuShareTimeline({
            title: '关注维多麦',
            desc: '关注维多麦',
            link: 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9b96982e6d5b1c58&redirect_uri=http%3A%2F%2Fwl.chenlichun.me%2FshitWechat.action&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect',
            imgUrl: 'http://yq.jdfgold.com/lotteryCar/share.jpg',
            trigger: function (res) {
            },
            success: function (res) {
                $.post("<c:url value="/updateState.action"/>", {award:${prizeId}},
                        function (result) {
                            if (result == 1) {
                                window.location.href = "http://coupon.m.jd.com/coupons/show.action?key=e5a37a43dc754d26ba3dea52ce2a1ab3&roleId=2493327&to=sale.jd.com/act/nrz3le8ubt6wpyv.html";
                            }
                            else {
                                alert("不能重复领取");
                            }
                        }
                );
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


</html>
