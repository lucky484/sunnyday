<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport"/>
    <title>好享吃</title>
    <script type="text/javascript" src="<c:url value="/Javascript/bootstrap.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/Javascript/jquery-1.9.1.min.js"/>"></script>
    <link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/bootstrap-theme.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/style.css"/>" type="text/css" rel="stylesheet" />
    <style type="text/css">
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
	<div  class="img_middle">
	<img src="<c:url value="/resources/images/error.png"/>" style="width:100px;">
	</div>
	<h3 class="middle">${error }
</body>
</html>