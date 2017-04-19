<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mdm" uri="http://mdm.softtek.com"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.softtek.mdm.util.CommUtil"%>
<!DOCTYPE html>
<html lang="en" class="app">
<head>
<tiles:importAttribute name="title" />
<title><fmt:message key="${title}" /></title>
<meta charset="utf-8" />
<meta name="description"
	content="<fmt:message key='jsp.meta.description'/>" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<meta name="x-csrf-header" content="${_csrf.token}" />
<spring:url value="/resources/favicon.ico" var="icoUrl" />
<link rel="shortcut icon" href="${icoUrl}" mce_href="${icoUrl}"
	type="image/x-icon">
<spring:url value="/resources/css/bootstrap.css" var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<spring:url value="/resources/css/animate.css" var="animateCss" />
<link rel="stylesheet" href="${animateCss}" />
<spring:url value="/resources/css/font-awesome.min.css"
	var="fontAwesomeCss" />
	<spring:url value="/resources/images/newlogo.png" var="newlogo" />
<link rel="stylesheet" href="${fontAwesomeCss}" />
<spring:url value="/resources/css/icon.css" var="iconCss" />
<link rel="stylesheet" href="${iconCss}" />
<spring:url value="/resources/css/font.css" var="fontCss" />
<spring:url value="/institution/aside" var="asideUrl" />
<link rel="stylesheet" href="${fontCss}" />
<spring:url value="/resources/css/app.css" var="appCss" />
<link rel="stylesheet" href="${appCss}" />
<spring:url
	value="/resources/js/datepicker/bootstrap-datetimepicker.css"
	var="datetimepicker" />
<link rel="stylesheet" href="${datetimepicker}" />
<spring:url value="/resources/js/calendar/bootstrap_calendar.css"
	var="bootstrapCalendarCss" />
<link rel="stylesheet" href="${bootstrapCalendarCss}" />
<spring:url value="/resources/js/parsley-2.3.7/parsley.css"
	var="parsleyCss" />
<link rel="stylesheet" href="${parsleyCss}" />
<spring:url
	value="/resources/js/bootstrap-notify/css/bootstrap-notify.css"
	var="notifyCss" />
<link rel="stylesheet" href="${notifyCss}" />
<!--[if lt IE 9]>
	<spring:url value="/resources/js/ie/html5shiv.js" var="html5shivJs"/>    
	<script src="${html5shivJs}"></script>
	<spring:url value="/resources/js/ie/respond.min.js" var="respondJs"/>   
    <script src="${respondJs}"></script>
    <spring:url value="/resources/js/ie/excanvas.js" var="excanvasJs"/>   
    <script src="${excanvasJs}"></script>
  	<![endif]-->
  	<style>
.datatb-max-width {
	max-width: 400px !important;
}

.tooltip-inner {
	max-width: 200px;
	padding: 3px 8px;
	color: #fff;
	text-align: center;
	text-decoration: none;
	background-color: #3494D2;
	border-radius: 4px;
}

.tooltip.bottom .tooltip-arrow {
	top: 0;
	left: 50%;
	margin-left: -5px;
	border-width: 0 5px 5px;
	border-bottom-color: #3494D2;
}

.tooltip-inner {
	padding: 3px 8px;
	color: #fff;
	text-align: center;
	text-decoration: none;
	background-color: #3494D2;
	border-radius: 4px;
}
</style>
<tiles:insertAttribute name="style" />
</head>
<body>
	<section class="vbox">
		<header
			class="bg-white header header-md navbar navbar-fixed-top-xs box-shadow">
			<div class="navbar-header aside-md dk">
				<a class="btn btn-link visible-xs"
					data-toggle="class:nav-off-screen,open" data-target="#nav,html">
					<i class="fa fa-bars"></i>
				</a>
				<spring:url value="/admin" var="indexUrl" />
				<a href="${indexUrl}" class="navbar-brand" onclick="activeSub(0);">
					<spring:url value="/resources/images/logo.png" var="logo" /> <img
					 id="logoUrl" src="${logo}" class="m-r-sm" alt="scale"> <span
					class="hidden-nav-xs">MDM Platform</span>
				</a> <a class="btn btn-link visible-xs" data-toggle="dropdown"
					data-target=".user"> <i class="fa fa-cog"></i>
				</a>
			</div>
			<ul class="nav navbar-nav navbar-right m-n hidden-xs nav-user user">
				<li class="dropdown"><a href="javascript:void(0);"
					class="dropdown-toggle" data-toggle="dropdown"> <span
						class="thumb-sm avatar pull-left"> <spring:url
								value="/resources/images/a0.png" var="thumb" /> <img
							src="${thumb}" alt="logo">
					</span> ${softtek_manager.name}<b class="caret"></b>
				</a>
					<ul class="dropdown-menu animated fadeInRight">
						<li><spring:url value="/admin/personal"
								var="personalUrl" /> <a href="${personalUrl}"><fmt:message
									key="jsp.header.person.center" /></a></li>
						<li class="divider"></li>
						<li><spring:url value="/manager/j_spring_security_logout"
								var="logout" /> 
							<a href="${logout}"><fmt:message key="jsp.header.logout" /></a>
						</li>
					</ul></li>
			</ul>
		</header>
		<section>
			<section class="hbox stretch">
				<!-- .aside -->
				<tiles:insertTemplate template="../../fragments/admin/aside.jsp" />
				<!-- /.aside -->
				<section id="content">
					<section class="hbox stretch">
						<section>
							<section class="vbox">
								<section class="scrollable padder">
									<mdm:breadcrumb />
									<tiles:insertAttribute name="content" />
									<div class='notifications bottom-right notify'></div>
								</section>
							</section>
						</section>
					</section>
				</section>
			</section>
		</section>
	</section>
	<tiles:insertAttribute name="modal" />
</body>

<script type="text/javascript">
var ctx = '<%=request.getContextPath() %>';
</script>
<spring:url value="/resources/js/jquery.min.js" var="jqueryJs" />
<script src="${jqueryJs}"></script>
<!-- Bootstrap -->
<spring:url value="/resources/js/bootstrap.js" var="bootstrapJs" />
<script src="${bootstrapJs}"></script>
<!-- App -->
<spring:url value="/resources/js/app.js" var="appJs" />
<script src="${appJs}"></script>
<spring:url value="/resources/js/slimscroll/jquery.slimscroll.min.js"
	var="jquerySlimscrollJs" />
<script src="${jquerySlimscrollJs}"></script>
<spring:url
	value="/resources/js/charts/easypiechart/jquery.easy-pie-chart.js"
	var="easyPieChartJs" />
<script src="${easyPieChartJs}"></script>
<spring:url
	value="/resources/js/charts/sparkline/jquery.sparkline.min.js"
	var="sparklineJs" />
<script src="${sparklineJs}"></script>
<spring:url value="/resources/js/charts/flot/jquery.flot.min.js"
	var="flotJs" />
<script src="${flotJs}"></script>
<spring:url value="/resources/js/charts/flot/jquery.flot.tooltip.min.js"
	var="tooltipJs" />
<script src="${tooltipJs}"></script>
<spring:url value="/resources/js/charts/flot/jquery.flot.spline.js"
	var="splineJs" />
<script src="${splineJs}"></script>
<spring:url value="/resources/js/charts/flot/jquery.flot.pie.min.js"
	var="flotPieJs" />
<script src="${flotPieJs}"></script>
<spring:url value="/resources/js/charts/flot/jquery.flot.resize.js"
	var="flotResizeJs" />
<script src="${flotResizeJs}"></script>
<spring:url value="/resources/js/charts/flot/jquery.flot.grow.js"
	var="flotGrowJs" />
<script src="${flotGrowJs}"></script>
<spring:url value="/resources/js/charts/flot/demo.js" var="flotDemoJs"/> 
  <script src="${flotDemoJs}"></script>
<spring:url value="/resources/js/calendar/bootstrap_calendar.js"
	var="bootstrapCalendarJs" />
<script src="${bootstrapCalendarJs}"></script>
<spring:url value="/resources/js/calendar/demo.js" var="calendarDemoJs" />
<script src="${calendarDemoJs}"></script>
<spring:url value="/resources/js/sortable/jquery.sortable.js"
	var="sortableJs" />
<script src="${sortableJs}"></script>
<spring:url value="/resources/js/app.plugin.js" var="appPluginJs"/>   
  <script src="${appPluginJs}"></script>
<spring:url value="/resources/js/bootstrap-treeview.js"
	var="bootstrapTreeviewJs" />
<script src="${bootstrapTreeviewJs}"></script>
<spring:url value="/resources/js/datepicker/bootstrap-datetimepicker.js"
	var="datetimepicker" />
<script src="${datetimepicker}"></script>
<spring:url value="/resources/js/datatables/jquery.dataTables.min.js"
	var="dataTables" />
<script src="${dataTables}"></script>
<spring:url value="/resources/js/parsley-2.3.7/parsley.js"
	var="parsleyUrl" />
<script src="${parsleyUrl}"></script>
<spring:url value="/resources/js/parsley-2.3.7/parsley.extend.js"
	var="parsleyExtUrl" />
<script src="${parsleyExtUrl}"></script>
<spring:url value="/resources/js/parsley-2.3.7/customer_parsley.js"
	var="customerParsleyUrl" />
<script src="${customerParsleyUrl}"></script>
<spring:url value="/resources/js/parsley-2.3.7/i18n/zh_cn.js"
	var="parsleyZhUrl" />
<script src="${parsleyZhUrl}"></script>
<spring:url value="/resources/js/parsley-2.3.7/i18n/en.js" var="parsleyEnUrl" />
<script src="${parsleyEnUrl}"></script>
<spring:url value="/resources/js/parsley-2.3.7/i18n/zh_cn.extra.js"
	var="parsleyZhExtraUrl" />
<script src="${parsleyZhExtraUrl}"></script>
<spring:url value="/resources/js/parsley-2.3.7/i18n/en.extra.js" var="parsleyEnExtraUrl"/>
<script src="${parsleyEnExtraUrl}"></script>
<spring:url
	value="/resources/js/bootstrap-notify/js/bootstrap-notify.js"
	var="notifyJs" />
<script src="${notifyJs}"></script>
<spring:url value="/resources/js/jquery.form.js" var="jquryFrmdata" />
<script src="${jquryFrmdata}"></script>
<!-- 将form表单对象转换成Json格式 -->
<spring:url value="/resources/js/form2json.js" var="form2Json" />
<script src="${form2Json}"></script>
<spring:url value="/resources/js/jquery.binddata.js" var="binddata" />
<script src="${binddata}"></script>
<spring:url value="/resources/js/datatables-1.10.11/lang/language.lang" var="dtLangUrl" />
<script>
 $(function(){
	 $("#aside-nav").find("[data-menu]").each(function(){
		 if($(this).attr("data-menu")==window.sessionStorage.admin_menu){
			 $(this).addClass("active");
		 }
	 });
	 $("#aside-nav").find("[data-sub]").each(function(){
		 if($(this).attr("data-sub")==window.sessionStorage.admin_sub){
			 $(this).addClass("active");
		 }
	 });
 });

function activeMenu(value){
	var storage=window.sessionStorage;
	storage.admin_menu=value;
}
function activeSub(value){
	var storage=window.sessionStorage;
	storage.admin_sub=value;
}
 </script>
<tiles:insertAttribute name="script" />
</html>