<%@page import="org.springframework.context.MessageSource"%>
<%@page import="org.springframework.security.core.AuthenticationException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<!DOCTYPE html>
<html lang="en">
<head>  
  <meta charset="utf-8" />
  <title><fmt:message key="jsp.login.title" /></title>
  <meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" /> 
  <spring:url value="/resources/css/bootstrap.css" var="bootstrapCss" />
  <link href="${bootstrapCss}" rel="stylesheet" />
  <spring:url value="/resources/css/animate.css" var="animateCss" />
  <link rel="stylesheet" href="${animateCss}"/>
  <spring:url value="/resources/css/font-awesome.min.css" var="fontAwesomeCss" />
  <link rel="stylesheet" href="${fontAwesomeCss}"/>
  <spring:url value="/resources/css/icon.css" var="iconCss" />
  <link rel="stylesheet" href="${iconCss}"/>
  <spring:url value="/resources/css/font.css" var="fontCss" />
  <link rel="stylesheet" href="${fontCss}"/>
  <spring:url value="/resources/css/app.css" var="appCss" />
  <link rel="stylesheet" href="${appCss}"/> 
    <!--[if lt IE 9]>
	<spring:url value="/resources/js/ie/html5shiv.js" var="html5shivJs"/>    
	<script src="${html5shivJs}"></script>
	<spring:url value="/resources/js/ie/respond.min.js" var="respondJs"/>   
    <script src="${respondJs}"></script>
    <spring:url value="/resources/js/ie/excanvas.js" var="excanvasJs"/> 
	<script src="${excanvasJs}"></script>
  <![endif]-->
</head>
<body class="bg-signin" >
<section id="content">
    <div class="row m-n">
      <div class="col-sm-10 col-sm-offset-1">
        <div class="text-center m-b-lg">
          <h1 class="h text-white animated fadeInDownBig error_limit_visit">Error</h1>
          <ul>
          	<li>exception:${exception}</li>
          	<li>url:${url}</li>
          	<li>timestamp:${timestamp}</li>
          	<li>status:${status}</li>
          </ul>
          	<spring:url value="/" var="homeUrl"/>
          	<h3 class="h text-white animated fadeInDownBig gohome_page"><a href="${homeUrl}"><fmt:message key="jsp.error.backtohome"/></a></h3>
        </div>
      </div>
    </div>
  </section>
  <!-- footer -->
  <footer id="footer">
    <div class="text-center padder">
      <p>
        <small><fmt:message key="jsp.manager.login.footer.copyright" /></small>
      </p>
    </div>
  </footer>
  <spring:url value="/resources/js/jquery.min.js" var="jqueryJs"/>   
  <script src="${jqueryJs}"></script>
  <!-- Bootstrap -->
  <spring:url value="/resources/js/bootstrap.js" var="bootstrapJs"/>   
  <script src="${bootstrapJs}"></script>
  <!-- App -->
  <spring:url value="/resources/js/app.js" var="appJs"/>   
  <script src="${appJs}"></script>
  <spring:url value="/resources/js/slimscroll/jquery.slimscroll.min.js" var="jquerySlimscrollJs"/>   
  <script src="${jquerySlimscrollJs}"></script>
  <spring:url value="/resources/js/app.plugin.js" var="appPluginJs"/>   
  <script src="${appPluginJs}"></script>
</body>
</html>