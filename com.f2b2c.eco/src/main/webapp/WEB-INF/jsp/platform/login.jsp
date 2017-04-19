<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 <spring:url value="/farm/login" var="loginUrl" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>系统管理 - 登录</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <spring:url value="/resources/platform/favicon.ico" var="faviconUrl" />
    <link rel="shortcut icon" href="${faviconUrl}">
    <spring:url value="/resources/platform/css/bootstrap.min14ed.css" var="bootstrapCssUrl" />
    <link href="${bootstrapCssUrl}" rel="stylesheet">
    <spring:url value="/resources/platform/css/font-awesome.min93e3.css" var="fontAwesomeCssUrl" />
    <link href="${fontAwesomeCssUrl}" rel="stylesheet">
    <spring:url value="/resources/platform/css/animate.min.css" var="animateCssUrl" />
    <link href="${animateCssUr}" rel="stylesheet">
    <spring:url value="/resources/platform/css/style.min862f.css" var="styleCssUrl" />
    <link href="${styleCssUrl}" rel="stylesheet">
    <spring:url value="/resources/platform/css/common/jquery.pnotify.default.css" var="pnotifyCssUrl"/>
    <link href="${pnotifyCssUrl}" rel="stylesheet">
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
    <script>if(window.top !== window.self){ window.top.location = window.location;}</script>
</head>
<body class="gray-bg">
    <div class="middle-box text-center loginscreen  animated fadeInDown">
        <div>
            <div>
                <h1 class="logo-name">Hello</h1>
            </div>
            <h3>欢迎使用</h3>
            <form id="loginForm" class="m-t" role="form" action="${loginUrl}" method="post">
                <div class="form-group">
                    <input type="text" name="accountName" class="form-control" placeholder="用户名" >
                </div>
                <div class="form-group">
                    <input type="password" name="password" class="form-control" placeholder="密码">
                </div>
                <button id="submit" class="btn btn-primary block full-width m-b">登 录</button>
               <!--  <p class="text-muted text-center"> <a href="login.html#"><small>忘记密码了？</small></a> | <a href="register.html">注册一个新账号</a>
                </p> -->
            </form>
        </div>
    </div>
    <spring:url value="/resources/platform/js/jquery.min.js" var="jqueryJsUrl" />
    <script src="${jqueryJsUrl}"></script>
    <spring:url value="/resources/platform/js/bootstrap.min.js" var="bootstrapJsUrl" />
    <script src="${bootstrapJsUrl}"></script>
    <spring:url value="/resources/platform/js/common/jquery.pnotify.js" var="pnotifyJsUrl"/>
	<script src="${pnotifyJsUrl}"></script>
	<spring:url value="/resources/platform/js/common/color.message.js" var="colorMessageJsUrl"/>
	<script src="${colorMessageJsUrl}"></script>
     <c:if test="${!empty REQ_TIP}">
		<script type="text/javascript">
			$(document).ready(function(){
				$.notify("error", "${REQ_TIP}");
			});
		</script>
	</c:if> 
	<script type="text/javascript">
			$("#submit").click(function(){
				if($("input[name='accountName']").val() == "" || $("input[name='accountName']").val() == null){
					$.notify("error", "请输入用户名!");
					return false;
				}else if($("input[name='password']").val() == "" || $("input[name='password']").val() == null){
					$.notify("error", "请输入密码!");
					return false;
				}else{
					$("#loginForm").submit();
				}
			});
	</script>
</body>
</html>
