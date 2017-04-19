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
  <title>注册协议</title>
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
<div class="picAndWord">
 <div>
 	 <input type="hidden" id="type" name="type" value="1" />
 </div>
 <c:out value="${protocol.content }" escapeXml="false"/>
</div>
<!-- <body class="bg-signin" >
  <div>
  
  </div>
</body> -->
<script>
</script>
</html>