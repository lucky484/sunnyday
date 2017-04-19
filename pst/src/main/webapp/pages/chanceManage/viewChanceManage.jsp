<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<!--[if IE 8]> <html class="ie8"> <![endif]-->
<!--[if IE 9]> <html class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<base href="<%=basePath%>">
<title>项目详情</title>
<jsp:include page="../global.jsp"></jsp:include>
<link rel="stylesheet" href="plugins/framework/css/DT_bootstrap.css" />
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<jsp:include page="../header.jsp"></jsp:include>
	<div class="page-container row-fluid">
		<jsp:include page="../menu.jsp"></jsp:include>
		<!-- BEGIN PAGE -->
		<div class="page-content">
			<!-- BEGIN PAGE CONTAINER-->
			<div class="container-fluid">
				<!-- BEGIN PAGE HEADER-->
				<div class="row-fluid">
					<div class="span12">
						<!-- BEGIN PAGE TITLE & BREADCRUMB-->
						<br />
						<ul class="breadcrumb">
							<li><a href="projectsManagement/chanceManage/chanceManage.do">机会管理列表</a>
								<span class="icon-angle-right"></span></li>
							<li>机会管理详情</li>
						</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<!-- BEGIN PAGE CONTENT-->
				<div class="row-fluid">
					<div class="span12">
						<!-- BEGIN SAMPLE FORM PORTLET-->
						<div class="portlet box blue">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-reorder"></i> <span class="hidden-480">机会管理详情</span>
								</div>
							</div>
							<div class="portlet-body form">
								<br />
								<!-- BEGIN FORM-->
								<form action="" method="post" class="form-horizontal"
									enctype="multipart/form-data">

									<div class="control-group">
										<label class="control-label">项目名称 </label>
										<div class="controls">
											<input type="text" placeholder="" id="projectName"
												name="projectName" class="m-wrap span6"
												value="${cm.projectName }" disabled="disabled" /> <span
												class="help-inline"></span>
										</div>
									</div>

									<div class="control-group">
										<label class="control-label">项目经理 </label>
										<div class="controls">
											<input type="text" placeholder="" id="projectManagerName"
												name="projectManagerName" class="m-wrap span6"
												value="${cm.projectManagerName }" disabled="disabled" /> <span
												class="help-inline"></span>
										</div>
									</div>

									<div class="control-group">
										<label class="control-label">开始时间 </label>
										<div class="controls">
											<div id="start_time_calendar" data-date=""
												data-date-viewmode="years">
												<input class="m-wrap m-ctrl-medium date-picker"
													value='<fmt:formatDate value="${cm.startTime }" type="date" pattern="yyyy-MM-dd" />'
													placeholder="" id="startTime" name="start_time"
													class="m-wrap medium" type="text" readonly="readonly"
													disabled="disabled" /><span class="add-on"></span>
											</div>
										</div>
									</div>

									<div class="control-group">
										<label class="control-label">交付时间 </label>
										<div class="controls">
											<div id="lead_time_calendar" data-date=""
												data-date-format="yyyy-mm-dd" data-date-viewmode="years">
												<input class="m-wrap m-ctrl-medium date-picker"
													value='<fmt:formatDate value="${cm.leadTime }" type="date" pattern="yyyy-MM-dd" />'
													placeholder="" id="leadTime" name="lead_time"
													class="m-wrap medium" type="text" readonly="readonly"
													disabled="disabled" /> <span class="add-on"></span>
											</div>
										</div>
									</div>
									<c:if test="${fn:contains(sessionScope.authorities, 'projects_price_read') or user.userId == cm.creatorId}">
										<div class="control-group">
											<label class="control-label">预估报价 </label>
											<div class="controls">
												<div id="forecastQuotationDiv"
													class="input-prepend input-append">
													<span class="add-on">￥</span> <input type="text"
														placeholder="" id="forecastQuotation"
														name="forecastQuotation" class="m-wrap"
														value="${cm.forecastQuotation }" readonly="readonly" />
												</div>
											</div>
										</div>
									 </c:if>
										<c:if test="${sessionScope.user.role.roleName=='general_manager' or sessionScope.user.role.roleName=='admin' or sessionScope.user.userId == cm.creatorId }">
										<div class="form-actions">
											<a href="projectsManagement/chanceManage/edit.do?chanceManageId=${cm.chanceManageId}"
												class="btn  purple"><i class='icon-edit'></i> 编辑</a>
										</div>
									</c:if>
								</form>
							</div>
							<!-- END PAGE CONTENT-->
						</div>
					</div>
				</div>
			</div>
			<!-- END PAGE CONTENT-->
		</div>
		<!-- END PAGE CONTAINER-->
	</div>
	<!-- END PAGE -->
	<!-- END CONTAINER -->
	<jsp:include page="../footer.jsp"></jsp:include>
	<script src="plugins/jquery.form.js" type="text/javascript"></script>
	<script type="text/javascript"
		src="plugins/framework/js/jquery.pulsate.min.js"></script>
	<script type="text/javascript"
		src="plugins/framework/js/jquery.validate.min.js"></script>
	<script>
	var forecastQuotation = ${cm.forecastQuotation};
	$(function(){
		$("#forecastQuotation").val("￥" + (forecastQuotation.toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,')));
	});
	</script>
	<!-- END BODY -->
</body>
</html>