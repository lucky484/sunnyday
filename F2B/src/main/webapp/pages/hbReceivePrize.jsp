<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/25
  Time: 12:56
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
            top: 40%;
            margin-left: 44%;
            z-index: 6;
        }

        .img-money {
            position: absolute;
            top: 40%;
        }

        .btn-primary {
            width: 45%;
            height: 35%;
            position: absolute;
            top: 66%;
            left: 29%;
            font-size: 5vw;
        }

        #mess_share img {
            width: 22px;
            height: 22px;
        }

        #cover {
            display: none;
            position: absolute;
            left: 0;
            top: 0;
            z-index: 18888;
            background-color: #000000;
            opacity: 0.7;
        }

        #guide {
            display: none;
            position: absolute;
            right: 18px;
            top: 5px;
            z-index: 19999;
        }

        #guide img {
            width: 100%;
            height: 100%;
        }
    </style>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript" src="<c:url value="/Javascript/jquery-1.9.1.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/Javascript/jquery.base64.js"/>"></script>
    <script type="text/javascript">
        $(function () {
            var prizeId =${prizeId};
            $("#imgBg").attr("src", "<c:url value="/images/bg"/>/hb" + prizeId + ".png")
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
    <img id="imgBg" height="100%" width="100%" />
    <div style="top:52%;height:20%;text-shadow:none;position:absolute;z-index:9;text-align:center;width:100%;display:block;">
        <img id="sub" src="<c:url value="/images/apple/btn_before.png"/>" class="btn-sub3" onclick="_system._guide(true)"/>

    </div>
</div>
<script type="text/javascript">

    var _system = {

        $: function (id) { return document.getElementById(id); },

        _client: function () {

            return { w: document.documentElement.scrollWidth, h: document.documentElement.scrollHeight, bw: document.documentElement.clientWidth, bh: document.documentElement.clientHeight };

        },

        _scroll: function () {

            return { x: document.documentElement.scrollLeft ? document.documentElement.scrollLeft : document.body.scrollLeft, y: document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop };

        },

        _cover: function (show) {

            if (show) {

                this.$("cover").style.display = "block";

                this.$("cover").style.width = (this._client().bw > this._client().w ? this._client().bw : this._client().w) + "px";

                this.$("cover").style.height = (this._client().bh > this._client().h ? this._client().bh : this._client().h) + "px";

            } else {

                this.$("cover").style.display = "none";

            }

        },

        _guide: function (click) {

            this._cover(true);

            this.$("guide").style.display = "block";

            this.$("guide").style.top = (_system._scroll().y + 5) + "px";

            window.onresize = function () { _system._cover(true); _system.$("guide").style.top = (_system._scroll().y + 5) + "px"; };

            if (click) {
                _system.$("cover").onclick = function () {

                    _system._cover();

                    _system.$("guide").style.display = "none";

                    _system.$("cover").onclick = null;

                    window.onresize = null;

                };
            }

        },

        _zero: function (n) {

            return n < 0 ? 0 : n;

        }

    }

</script>
<div id="cover"></div>
<div id="guide">
    <img src="<c:url value="/images/guide22.png"/>">
</div>
</body>
<script>
    function receive()
    {
        alert("请分享后领取！");
    }
</script>

<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

<!-- <script>
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
</script> -->
<!-- <script type="text/javascript">
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
                $.post("<c:url value="/updateState.action"/>", {award:${prizeId}},
                        function (result) {
                            if (parseInt(result) == 1) {
                                window.location.href = "<c:url value="/hasReceivedhbPrize.action?prizeId=${prizeId}"/>";
                            }
                            else if(parseInt(result)==-1)
                            {
                                alert("服务器繁忙，稍后领取");
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
            title: '哇，我中了维多麦发放的${text}，听说你也有份奖品，快来领取！',
            link: 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9b96982e6d5b1c58&redirect_uri=http%3A%2F%2Fweetabix.tnbchina.com%2Fwdm1%2FshitWechat.action%3Fname%3D1&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect',
            imgUrl: localhostPaht+'<c:url value="/images/share1.jpg"/>',
            trigger: function (res) {
            },
            success: function (res) {
                $.post("<c:url value="/updateState.action"/>", {award:${prizeId}},
                        function (result) {
                            if (parseInt(result) == 1) {
                                window.location.href = "<c:url value="/hasReceivedhbPrize.action?prizeId=${prizeId}"/>";
                            }
                            else if(parseInt(result)==-1)
                            {
                                alert("服务器繁忙，稍后领取");
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
</script> -->
</html>