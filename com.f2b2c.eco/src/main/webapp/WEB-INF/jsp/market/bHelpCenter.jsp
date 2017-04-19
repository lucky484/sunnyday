<%@page import="org.springframework.context.MessageSource"%>
<%@page import="org.springframework.security.core.AuthenticationException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>  
  <title>帮助中心</title>
  <meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" /> 
 <%--  <spring:url value="/resources/css/bootstrap.css" var="bootstrapCss" /> --%>
 <%--  <link href="${bootstrapCss}" rel="stylesheet" /> --%>
    <!--[if lt IE 9]>
	<spring:url value="/resources/js/ie/html5shiv.js" var="html5shivJs"/>    
	<script src="${html5shivJs}"></script>
	<spring:url value="/resources/js/ie/respond.min.js" var="respondJs"/>   
    <script src="${respondJs}"></script>
    <spring:url value="/resources/js/ie/excanvas.js" var="excanvasJs"/>   .re    <script src="${excanvasJs}"></script>
  <![endif]-->
  <spring:url value="/resources/market/img/help/more.png" var="moreUrl"/><!-- 更多.png -->
  <spring:url value="/resources/market/img/help/advice.png" var="adviceUrl"/>
  <spring:url value="/resources/market/img/help/pay.png" var="payUrl"/>
  <spring:url value="/resources/market/img/help/log.png" var="logUrl"/>
  <spring:url value="/resources/market/img/help/other.png" var="otherUrl"/>
  <spring:url value="/resources/market/img/help/hot.png" var="hotUrl"/><!-- answer.png -->  
  
  
  <spring:url value="/api/bhelp/query-help-advice" var="AdviceUrl" />
  <spring:url value="/api/bhelp/query-help-pay" var="PayUrl" />
  <spring:url value="/api/bhelp/query-help-log" var="LogUrl" />
  <spring:url value="/api/bhelp/query-help-other" var="OtherUrl" />
  
  <spring:url value="/api/bhelp/query-help-qa" var="QAndAUrl" />
  	<!--标准mui.css-->
  	<spring:url value="/resources/market/css/mui/mui.min.css" var="muiCss" />
  	<link rel="stylesheet" href="${muiCss }">
	<!--App自定义的css-->
  	<spring:url value="/resources/market/css/mui/app.css" var="appCss" />
	<link rel="stylesheet" href="${appCss }">

	<style>
		.title{
 			padding: 20px 15px 7px; 
			color: #6d6d72;
			font-size: 15px;
			background-color: #fff;
		}
		.title > img {
			margin-top: 5px;
		}
		
		.title-img {
			margin-top: 0px !important;
			width: 20px;
		}
		
		.title-span {
			vertical-align: top;
		}
		
		.float_r {
			float: right;
		}
		.float_l {
			float: left;
		}
		.magrin_l_8{
			margin-left: 8%;
		}
		
		.bar{
			padding: 20px 0px 50px; 
			color: #6d6d72;
			font-size: 13px;
			background-color: #fff;
			width: 100%;
		}
		.bar_child {
			width: 25%;
			text-align: center;
		}
		.bar_child div {
			width: 100%;
    		text-align: center;
		}
		.bar_child img {
			width: 25px;
		}
	</style>
</head>

<body>
	<div class="">
		<div class="title">
			<img class="title-img" src="${hotUrl }" />
			<span class="title-span">热点问题</span>
			<img class="float_r" alt="" src="${moreUrl}" width="20px" height="5px">
		</div>
		<c:forEach items="${model }" var="model">
			<ul class="mui-table-view">
				<li class="mui-table-view-cell">
					<a class="" href="${QAndAUrl }?id=${model.id}">
						${model.question }
					</a>
				</li>
			</ul>
		</c:forEach>
<%-- 	 <c:out value="${model.answer }" escapeXml="false"/> --%>
	</div>
	<br/>
	<div class="bar">
		<div class="float_l bar_child">
			<a href="${AdviceUrl }">
				<div class="">
					<img alt="购买咨询" src="${adviceUrl}">
				</div>
				<div class="">
					购买咨询
				</div>
			</a>		
		</div>
		<div class="float_l bar_child">
			<a href="${PayUrl }">
				<div class="">
					<img alt="支付问题" src="${payUrl}">
				</div>
				<div class="">
					支付问题
				</div>
			</a>		
		</div>
		<div class="float_l bar_child">
			<a href="${LogUrl }">
				<div class="">
					<img alt="物流与售后" src="${logUrl}">
				</div>
				<div class="">
					物流与售后
				</div>
			</a>		
		</div>
		<div class="float_l bar_child">
			<a href="${OtherUrl }">
				<div class="">
					<img alt="其他" src="${otherUrl}">
				</div>
				<div class="">
					其他
				</div>
			</a>		
		</div>
	</div>
<script>
</script>
</body>
</html>