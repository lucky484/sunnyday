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
  <title>支付问题</title>
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
  <spring:url value="/resources/market/img/help/pay2.png" var="payUrl"/>
  <spring:url value="/resources/market/img/help/pay3.png" var="pay3Url"/>
  
  	<!--标准mui.css-->
  	<spring:url value="/resources/market/css/mui/mui.min.css" var="muiCss" />
  	<link rel="stylesheet" href="${muiCss }">
	<!--App自定义的css-->
  	<spring:url value="/resources/market/css/mui/app.css" var="appCss" />
	<link rel="stylesheet" href="${appCss }">
  
	<spring:url value="/api/bhelp/query-help-qa" var="QAndAUrl" />
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
	</style>
</head>

<body>
	<div class="">
		<div class="title">
			<img class="title-img" src="${payUrl }" />
			<span class="title-span">支付相关</span>
			<img class="float_r" alt="" src="${moreUrl}" width="20px" height="5px">
		</div>
		<c:forEach items="${model }" var="model">
		<c:if test="${model.status==1}">
			<ul class="mui-table-view">
				<li class="mui-table-view-cell">
					<a class="" href="${QAndAUrl }?id=${model.id}">
						${model.question }
					</a>
				</li>
			</ul>
		</c:if>
		</c:forEach>
	</div>
	<br/>
	<div class="">
		<div class="title">
			<img class="title-img" src="${pay3Url }" />
			<span class="title-span">积分卡券使用</span>
			<img class="float_r" alt="" src="${moreUrl}" width="20px" height="5px">
		</div>
		<c:forEach items="${model }" var="model">
		<c:if test="${model.status==2 }">
			<ul class="mui-table-view">
				<li class="mui-table-view-cell">
					<a class="" href="${QAndAUrl }?id=${model.id}">
						${model.question }
					</a>
				</li>
			</ul>
		</c:if>
		</c:forEach>
	</div>
	<br/>
	<div class="">
		<div class="title">
			<img class="title-img" src="${payUrl }" />
			<span class="title-span">支付异常</span>
			<img class="float_r" alt="" src="${moreUrl}" width="20px" height="5px">
		</div>
		<c:forEach items="${model }" var="model">
		<c:if test="${model.status==3 }">
			<ul class="mui-table-view">
				<li class="mui-table-view-cell">
					<a class="" href="${QAndAUrl }?id=${model.id}">
						${model.question }
					</a>
				</li>
			</ul>
		</c:if>
		</c:forEach>
	</div>
<spring:url value="/resources/market/js/mui/mui.min.js" var="muiJS" />
<script src="${muiJS }"></script>
<script>
		mui.init({
			swipeBack:true //启用右滑关闭功能
		});
	</script>
</body>
</html>