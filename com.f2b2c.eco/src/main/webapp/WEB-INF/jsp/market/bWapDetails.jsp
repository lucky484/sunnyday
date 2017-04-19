<%@page import="org.springframework.context.MessageSource"%>
<%@page import="org.springframework.security.core.AuthenticationException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

    <spring:url value="/resources/market/js/jquery.min.js" var="jqueryJsUrl"/>
    <script src="${jqueryJsUrl}"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>  
  <meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
 <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<!-- <meta name="viewport" content="width=device-width">
 <meta name="viewport" content="initial-scale=1"> --> 
 <%--  <spring:url value="/resources/css/bootstrap.css" var="bootstrapCss" /> --%>
 <%--  <link href="${bootstrapCss}" rel="stylesheet" /> --%>
    <!--[if lt IE 9]>
	<spring:url value="/resources/js/ie/html5shiv.js" var="html5shivJs"/>    
	<script src="${html5shivJs}"></script>
	<spring:url value="/resources/js/ie/respond.min.js" var="respondJs"/>   
    <script src="${respondJs}"></script>
    <spring:url value="/resources/js/ie/excanvas.js" var="excanvasJs"/>   .re    <script src="${excanvasJs}"></script>
  <![endif]-->
 <style>
 h3 {
 	text-align: center;
 }
 p {
 	color:#52514C;
 	line-height: 23px;
 }

 </style>
</head>
<div class="picAndWord" style="word-wrap:break-word;word-break:break-all;overflow: hidden;">
 <div>
 	 <input type="hidden" id="type" name="type" value="1" />
 </div>
 <c:out value="${model.content}" escapeXml="false"/>
</div>
<!-- <body class="bg-signin" >
  <div>
  
  </div>
</body> -->

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
</html>

