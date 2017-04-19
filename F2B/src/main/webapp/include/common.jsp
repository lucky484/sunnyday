<%--
  公共JS,CSS样式引入
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% response.setHeader("Cache-Control","no-cache");response.setHeader("Pragma","no-cache");response.setDateHeader ("Expires", -1); %>
<%--<script type="text/javascript" src="<c:url value="/resources/jquery/jquery-1.7.2.min.js" />"></script>--%>

<%--<link rel="stylesheet" type="text/css" href="<c:url value="/resources/framework/jquery-easyui-1.3.3/themes/metro/easyui.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/framework/jquery-easyui-1.3.3/themes/icon.css" />" />
<script type="text/javascript" src="<c:url value="/resources/framework/jquery-easyui-1.3.3/jquery.easyui.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/framework/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js" />"></script>--%>
<script type="text/javascript" src="<c:url value="/resources/lib/jquery/jquery-1.11.0.min.js" />"></script>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/lib/jquery-easyui-1.4.1/themes/gray/easyui.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/lib/jquery-easyui-1.4.1/themes/icon.css" />" />
<script type="text/javascript" src="<c:url value="/resources/lib/jquery-easyui-1.4.1/jquery.easyui.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/lib/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/global.js?ver=20130813" />"></script>
<script type="text/javascript" >
    //定义全局变量（web根路径）
    var ccm_webRoot = '<c:url value="/" />';
    var scpt = document.getElementsByTagName('script')[0];

    var elem2 = document.createElement('link');
    elem2.rel = "stylesheet";
    elem2.type = "text/css";
    elem2.href = "<c:url value="/resources/css/global.css" />?random=" + Math.floor(Math.random()*100000) ;
    scpt.parentNode.insertBefore(elem2, scpt);
</script>