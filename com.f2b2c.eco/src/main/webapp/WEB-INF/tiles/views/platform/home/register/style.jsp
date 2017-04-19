<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/resources/platform/css/plugins/dataTables/dataTables.bootstrap.css" var="dataTablesCssUrl"/>
<link href="${dataTablesCssUrl}" rel="stylesheet">
<!-- simditor组件 -->
<spring:url value="/resources/platform/css/plugins/simditor/app.css" var="appUrl"/>
<link href="${appUrl}" rel="stylesheet">