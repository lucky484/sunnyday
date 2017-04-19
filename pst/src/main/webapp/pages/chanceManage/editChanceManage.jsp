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
<title>项目更新</title>
<jsp:include page="../global.jsp"></jsp:include>
<link rel="stylesheet" href="plugins/framework/css/chosen.css" />
<link rel="stylesheet" href="plugins/framework/css/DT_bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="plugins/framework/css/bootstrap-fileupload.css" />
<link rel="stylesheet" href="plugins/framework/css/datepicker.css" />
<link rel="stylesheet" href="plugins/framework/css/check.css" />
<link rel="stylesheet" href="plugins/framework/css/jquery.gritter.css" />
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
							<li>机会管理更新</li>
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
									<i class="icon-reorder"></i> <span class="hidden-480">机会管理更新</span>
								</div>
							</div>
							<div class="portlet-body form">
								<br />
								<!-- BEGIN FORM-->
								<form id="editChanceManageForm" class="form-horizontal"
									enctype="multipart/form-data">
									<div class="alert alert-error hide">
										<button class="close" data-dismiss="alert"></button>
										您的表单包含错误，请检查
									</div>
									<div class="control-group">
										<label class="control-label">项目名称<span
											class="required">*</span></label>
										<div class="controls">
											<input type="text" placeholder="" id="projectName"
												name="projectName" class="m-wrap span6"
												value="${cm.projectName }" /> <span class="help-inline"></span>
										</div>
									</div>

                                   	<div class="control-group">
										<label class="control-label">项目经理 <span class="required">*</span></label>
										<div class="controls chzn-controls">
											<select id="projectManager" name="projectManager"
												class="span6 chosen" data-placeholder="请选择项目经理">
												<c:forEach items="${list}" var="list">
													<option value="${list.projectManagerId }"
														<c:if test="${cm.projectManagerName == list.name }">selected="selected"</c:if>>${list.name }</option>
												</c:forEach>
											</select>
											<input type="hidden" id="projectManagerName" name="projectManagerName" value="${cm.projectManagerName}"/>
										</div>
									</div>
                                    
									<div class="control-group">
										<label class="control-label">开始时间 <span
											class="required">*</span></label>
										<div class="controls">
											<div id="start_time_calendar"
												class="input-append date date-picker" data-date=""
												data-date-format="yyyy-mm-dd" data-date-viewmode="years">
												<input class="m-wrap m-ctrl-medium date-picker"
													value='<fmt:formatDate value="${cm.startTime }" type="date" pattern="yyyy-MM-dd" />'
													placeholder="" id="startTime" name="start_time"
													class="m-wrap medium" type="text" readonly="readonly" /><span
													class="add-on"><i class="icon-calendar"></i></span>
											</div>
										</div>
									</div>


									<div class="control-group">
										<label class="control-label">交付时间 <span
											class="required">*</span></label>
										<div class="controls">
											<div id="lead_time_calendar"
												class="input-append date date-picker" data-date=""
												data-date-format="yyyy-mm-dd" data-date-viewmode="years">
												<input class="m-wrap m-ctrl-medium date-picker"
													value='<fmt:formatDate value="${cm.leadTime }" type="date" pattern="yyyy-MM-dd" />'
													placeholder="" id="leadTime" name="lead_time"
													class="m-wrap medium" type="text" readonly="readonly" /> <span
													class="add-on"><i class="icon-calendar"></i></span>
											</div>
										</div>
									</div>

										<div class="control-group">
											<label class="control-label">预估报价 <span
												class="required">*</span></label>
											<div class="controls">
												<div id="forecastQuotationDiv"
													class="input-prepend input-append">
													<span class="add-on">￥</span> <input type="text"
														placeholder="" value="${cm.forecastQuotation }" id="forecastQuotation1"
														name="forecastQuotation1" class="m-wrap" onblur="formatNumber(this);"/> 
												</div>
											</div>
											<input type="hidden" id="forecastQuotation" name="forecastQuotation" />
										</div>
										
									<div class="control-group" style="display: none">
										<label class="control-label">机会管理ID</label>
										<div class="controls">
											<input type="text" placeholder="" id="chanceManageId"
												name="chanceManageId" class="m-wrap medium"
												value="${cm.chanceManageId }" /> <span class="help-inline"></span>
										</div>
									</div>

									<div class="form-actions">
										<button type="submit" class="btn blue" id="edit-submit">
											<i class="icon-ok"></i> 保存
										</button>
										<button type="button" class="btn"
											onclick="window.history.back(-1)">取消</button>
									</div>
								</form>
								<!-- END FORM-->
							</div>
						</div>
					</div>
				</div>
				<!-- END PAGE CONTENT-->
			</div>
			<!-- END PAGE CONTAINER-->
		</div>
	</div>
	<!-- END PAGE -->
	<!-- END CONTAINER -->
	<jsp:include page="../footer.jsp"></jsp:include>
	<script src="plugins/jquery.form.js" type="text/javascript"></script>
	<script type="text/javascript"
		src="plugins/framework/js/chosen.jquery.min.js"></script>
	<script type="text/javascript"
		src="plugins/framework/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript"
		src="plugins/framework/js/bootstrap-fileupload.js"></script>
	<script type="text/javascript"
		src="plugins/framework/js/jquery.gritter.js"></script>
	<script type="text/javascript"
		src="plugins/framework/js/jquery.validate.min.js"></script>
	<script src="js/chanceManage/editChanceManage.js"></script>
	<script>var basePath = '<%=basePath%>';</script>
	<script>var forecastQuotation = ${cm.forecastQuotation};</script>
	<!-- END BODY -->
</body>
</html>