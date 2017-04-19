<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <tiles:importAttribute name="title" />
    <title>${title}</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <spring:url value="/resources/platform/favicon.ico" var="faviconUrl"/>
    <link rel="shortcut icon" href="${faviconUrl}">
    <spring:url value="/resources/platform/css/bootstrap.min14ed.css" var="bootstrapCssUrl"/>
    <link href="${bootstrapCssUrl}" rel="stylesheet">
    <spring:url value="/resources/platform/css/font-awesome.min93e3.css" var="fontAwesomeCssUrl"/>
    <link href="${fontAwesomeCssUrl}" rel="stylesheet">
    <spring:url value="/resources/platform/css/animate.min.css" var="animateCssUrl"/>
    <link href="${animateCssUrl}" rel="stylesheet">
    <spring:url value="/resources/platform/css/style.min862f.css" var="styleCssUrl"/>
    <link href="${styleCssUrl}" rel="stylesheet">
    <spring:url value="/resources/platform/css/common/custom.css" var="customCssUrl"/>
    <link href="${customCssUrl}" rel="stylesheet">
    <spring:url value="/resources/platform/css/common/jquery.pnotify.default.css" var="pnotifyCssUrl"/>
    <link href="${pnotifyCssUrl}" rel="stylesheet">
    <spring:url value="/resources/platform/css/plugins/sweetalert/sweetalert.css" var="sweetalertCssUrl"/>
    <link href="${sweetalertCssUrl}" rel="stylesheet">
    <spring:url value="/resources/platform/css/plugins/simditor/simditor.css" var="simditorCssUrl" />
    <link href="${simditorCssUrl}" rel="stylesheet">
    <spring:url value="/resources/platform/css/plugins/dataTables/dataTables.bootstrap.css" var="dataTablesCssUrl"/>
    <link href="${dataTablesCssUrl}" rel="stylesheet">
    <spring:url value="/resources/platform/plugins/datepicker/bootstrap-datetimepicker.css" var="datetimePickerCss" />
    <link href="${datetimePickerCss}" rel="stylesheet" />
    <tiles:insertAttribute name="style" />
</head>
<body class="gray-bg">
<tiles:insertAttribute name="content" />
<tiles:insertAttribute name="modal" />     
<spring:url value="/resources/platform/js/jquery.min.js" var="jqueryJsUrl"/>
<script src="${jqueryJsUrl}"></script>
<spring:url value="/resources/platform/js/bootstrap.min.js" var="bootstrapJsUrl"/>
<script src="${bootstrapJsUrl}"></script>
<spring:url value="/resources/platform/js/common/date.js" var="dateJsUrl"/>
<script src="${dateJsUrl}"></script>
<spring:url value="/resources/platform/js/common/jquery.pnotify.js" var="pnotifyJsUrl"/>
<script src="${pnotifyJsUrl}"></script>
<spring:url value="/resources/platform/js/common/color.message.js" var="colorMessageJsUrl"/>
<script src="${colorMessageJsUrl}"></script>
<spring:url value="/resources/platform/js/plugins/sweetalert/sweetalert.min.js" var="sweetalertJsUrl"/>
<script src="${sweetalertJsUrl}"></script>
<spring:url value="/resources/platform/js/plugins/validate/jquery.validate.min.js" var="validateJsUrl"/>
<script src="${validateJsUrl}"></script>
<spring:url value="/resources/platform/js/plugins/validate/messages_zh.min.js" var="messages_zhJsUrl"/>
<script src="${messages_zhJsUrl }"></script>
<spring:url value="/resources/platform/js/plugins/simditor/module.js" var="moduleUrl" />
<script src="${moduleUrl }"></script>
<spring:url value="/resources/platform/js/plugins/simditor/hotkeys.js" var="hotkeysUrl" />
<script src="${hotkeysUrl }"></script>
<spring:url value="/resources/platform/js/plugins/simditor/uploader.js" var="uploaderUrl" />
<script src="${uploaderUrl }"></script>
<spring:url value="/resources/platform/js/plugins/simditor/simditor.js" var="simditorUrl" />
<script src="${simditorUrl }"></script>
<spring:url value="/resources/platform/js/plugins/dataTables/jquery.dataTables.js" var="dataTablesJsUrl"/>
<script src="${dataTablesJsUrl}"></script>
<spring:url value="/resources/platform/js/plugins/dataTables/dataTables.bootstrap.js" var="dataTablesBootstrapJsUrl"/>
<script src="${dataTablesBootstrapJsUrl}"></script>
<spring:url value="/resources/platform/plugins/layer/layer.js" var="layerJsUrl"/>
<script src="${layerJsUrl}"></script>
<spring:url value="/resources/platform/plugins/datepicker/bootstrap-datetimepicker.js" var="datetimePickerJs" />  
<script src="${datetimePickerJs}"></script>
<spring:url value="/resources/platform/js/jquery.jqprint-0.3.js" var="jqprintJs" />  
<script src="${jqprintJs}"></script>
<spring:url value="/resources/platform/js/jquery-migrate-1.1.0.js" var="jqprintMigrateJs" />  
<script src="${jqprintMigrateJs}"></script>
<%-- <c:if test="${!empty msg}">
    <script type="text/javascript">
        $(document).ready(function(){
            $.notify("${type}", "${msg}");
        });
    </script>
</c:if> --%>
<tiles:insertAttribute name="script" />
<script type="text/javascript">
var ctx = '<%=ctx%>';
</script>
</body>
</html>
