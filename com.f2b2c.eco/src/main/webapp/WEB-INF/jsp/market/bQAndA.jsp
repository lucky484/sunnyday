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
  <title>问答详情</title>
  <meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" /> 
  <meta content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport"/>
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
  <spring:url value="/resources/market/img/help/question.png" var="questionUrl"/><!-- question.png -->
  <spring:url value="/resources/market/img/help/answer.png" var="answerUrl"/><!-- answer.png -->  
  
  	<!--标准mui.css-->
  	<spring:url value="/resources/market/css/mui/mui.min.css" var="muiCss" />
  	<link rel="stylesheet" href="${muiCss }">
	<!--App自定义的css-->
  	<spring:url value="/resources/market/css/mui/app.css" var="appCss" />
	<link rel="stylesheet" href="${appCss }">

	<style>
	.title {
		padding: 20px 15px 7px;
		color: #6d6d72;
		font-size: 15px;
		background-color: #fff;
	}

	.content {
		padding: 20px 15px 7px;
		background-color: #fff;
	}

	.content-child img {
		width: 260px;
	}
	
	.float_r {
		float: right;
	}
	
	.title-img {
		width: 20px;
	}
	
	span {
		vertical-align: top;
	}
	
	hr {
		margin: 0;
	}
</style>
</head>

<body>

	<div class="picAndWord">
		<div class="title">
			<img class="title-img" src="${questionUrl}">
			<span>${model.question }</span>
		</div>
		<hr/>
		<div class="content">
			<img class="title-img" src="${answerUrl}">
			<div class="content-child">
			<c:out value="${model.answer }" escapeXml="false"/>
			</div>
		</div>
	</div>

<spring:url value="/resources/market/js/jquery.min.js" var="jqueryJsUrl"/>
<script src="${jqueryJsUrl}"></script>
<spring:url value="/resources/market/js/mui/mui.min.js" var="muiJS" />
<script src="${muiJS }"></script>
<script>
		mui.init({
			swipeBack:true //启用右滑关闭功能
		});
</script>
<script type="text/javascript">
  $(function(){
	 var width=$(window).width();
	 $(".picAndWord").attr("width",width);
	 var imgdefereds=[];
	 $('img').each(function(){
	  var dfd=$.Deferred();
	  $(this).bind('load',function(){
	   dfd.resolve();
	  }).bind('error',function(){
	  //图片加载错误，加入错误处理
	  // dfd.resolve();
	  })
	  if(this.complete) setTimeout(function(){
	   dfd.resolve();
	  },1000);
	  imgdefereds.push(dfd);
	 })
	 $.when.apply(null,imgdefereds).done(function(){
		 $("img").each(function(){
				if($(this).width()>width){
					var img_h = $(this).height();
					var img_w = $(this).width()
					$(this).attr("width",width);
					var height = img_h * width / img_w;
					$(this).attr("height",height);
				};
			 });
		 
	 });
	//$("img")
});  
</script>
</body>
</html>