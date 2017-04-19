<%@page import="org.springframework.context.MessageSource"%>
<%@page
	import="org.springframework.security.core.AuthenticationException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spring:url value="/resources/market/img/system/logo-180px.png" var="logoUrl"></spring:url>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>关于</title>
<meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport"/>
<style>
	body {
		margin: 0px;	
		font-family : "Microsoft Yahei"
	}
	center img{
		width: 90px;
		margin-top: 10%;
		margin-bottom: 7%;
	}
	
	.title{
		margin: 15px;
	}
	
	.content {
		margin: 15px;
		font-size: 14px;
		line-height: 20px;
		color: #595959;
	}
	
</style>
</head>
<body>
	<header>
		<center>
			<img src="${logoUrl }" alt="好享吃logo">
		</center>
	</header>
	<div class="title">
		<h4>关于好享吃APP</h4>
	</div>
	<div class="content">
		<c:out value="测试数据-占位作用，届时请删除" escapeXml="false"/>
	</div>
	<script>
		
	</script>
</body>
</html>