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
<spring:url value="/resources/market/img/system/logo-180px.png"
	var="logoUrl"></spring:url>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>关于我们</title>
<meta name="description"
	content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta
	content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"
	name="viewport" />
<style>
body {
	margin: 0px;
	font-family: "Microsoft Yahei"
}

center img {
	width: 90px;
	margin-top: 10%;
	margin-bottom: 7%;
}

.title {
	margin: 15px;
}

.content {
	margin: 15px;
	font-size: 14px;
	line-height: 20px;
	color: #595959;
}
p {
	text-indent: 28px;
	line-height: 21px;
}
h5{
	color: #ff9000;
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
		<h4>关于秋润公司</h4>
	</div>
	<div class="content">
		<p>秋润农业科技（上海）有限公司，坐落于上海市张江高科园区。
			作为一家产业互联网企业，我们自主开发了“好享吃”系统平台，将安全农产品从基地通过B端直配到消费者餐桌。升国网络科技（上海）有限公司专业为“好享吃”系统提供互联网技术和基地可追溯体系服务。
			公司拥有中国领先的水果供应链运营技术，是国家物联网标识二级解析服务商，建立了基地源头到消费者终端全产业链的可控体系。
		</p>
		<h5>“好享吃”只提供“安全”的食品， 我们一直关注您的健康！
		</h5>
	</div>
	<script>
		
	</script>
</body>
</html>