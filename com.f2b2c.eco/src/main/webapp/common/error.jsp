<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 <spring:url value="/market/login" var="loginUrl" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>系统管理 - 登录</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <spring:url value="/resources/market/favicon.ico" var="faviconUrl" />
    <link rel="shortcut icon" href="${faviconUrl}">
    <spring:url value="/resources/market/css/bootstrap.min14ed.css" var="bootstrapCssUrl" />
    <link href="${bootstrapCssUrl}" rel="stylesheet">
    <spring:url value="/resources/market/css/font-awesome.min93e3.css" var="fontAwesomeCssUrl" />
    <link href="${fontAwesomeCssUrl}" rel="stylesheet">
    <spring:url value="/resources/market/css/animate.min.css" var="animateCssUrl" />
    <link href="${animateCssUr}" rel="stylesheet">
    <spring:url value="/resources/market/css/style.min862f.css" var="styleCssUrl" />
    <link href="${styleCssUrl}" rel="stylesheet">
    <spring:url value="/resources/market/css/common/jquery.pnotify.default.css" var="pnotifyCssUrl"/>
    <link href="${pnotifyCssUrl}" rel="stylesheet">
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
    <script>if(window.top !== window.self){ window.top.location = window.location;}</script>
</head>
<body class="gray-bg">
   <div class="middle-box text-center animated fadeInDown">
        <h1>404</h1>
        <h3 class="font-bold">页面未找到！</h3>

        <div class="error-desc">
            抱歉，页面好像去火星了~
            <form class="form-inline m-t" role="form">
                <div class="form-group">
                    <input type="email" class="form-control" placeholder="请输入您需要查找的内容 …">
                </div>
                <button type="submit" class="btn btn-primary">搜索</button>
            </form>
        </div>
    </div>
    <spring:url value="/resources/market/js/jquery.min.js" var="jqueryJsUrl" />
    <script src="${jqueryJsUrl}"></script>
    <spring:url value="/resources/market/js/bootstrap.min.js" var="bootstrapJsUrl" />
    <script src="${bootstrapJsUrl}"></script>
    <spring:url value="/resources/market/js/common/jquery.pnotify.js" var="pnotifyJsUrl"/>
	<script src="${pnotifyJsUrl}"></script>
	<spring:url value="/resources/market/js/common/color.message.js" var="colorMessageJsUrl"/>
	<script src="${colorMessageJsUrl}"></script>
    <c:if test="${!empty REQ_TIP}">
		<script type="text/javascript">
			$(document).ready(function(){
				$.notify("error", "${REQ_TIP}");
			});
		</script>
	</c:if>
</body>
</html>














