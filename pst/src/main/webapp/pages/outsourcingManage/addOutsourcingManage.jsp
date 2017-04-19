<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
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
<title>添加用户</title>
<jsp:include page="../global.jsp"></jsp:include>
<link rel="stylesheet" href="plugins/framework/css/chosen.css" />
<link rel="stylesheet" href="plugins/framework/css/DT_bootstrap.css" />
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
							<li><a href="projectsManagement/outsourcingManage/outsourcingManage.do">外包管理</a> <span
								class="icon-angle-right"></span></li>
							<li>添加外包管理</li>
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
									<i class="icon-reorder"></i> <span class="hidden-480">添加外包管理</span>
								</div>
							</div>
							<div class="portlet-body form">
								<br />
								<!-- BEGIN FORM-->
								<form id="addOutsourcingManage" class="form-horizontal"
									enctype="multipart/form-data">
									<div class="alert alert-error hide">
										<button class="close" data-dismiss="alert"></button>
										您的表单包含错误，请检查
									</div>
									<div class="control-group">
										<label class="control-label">姓名<span
											class="required">*</span></label>
										<div class="controls">
											<input type="text" placeholder="" id="name"
												name="name" class="m-wrap span6" />
										</div>
									</div>

									<div class="control-group">
										<label class="control-label">邮箱<span
											class="required">*</span></label>
										<div class="controls">
											<input type="text" placeholder="" id="email"
												name="email" class="m-wrap span6" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">电话<span
											class="required">*</span></label>
										<div class="controls">
											<input type="text" placeholder="" id="phone"
												name="phone" class="m-wrap span6" />
										</div>
									</div>

									<div class="form-actions">
										<button type="submit" class="btn blue" id="add-submit">
											<i class="icon-ok"></i> 提交
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
		src="plugins/framework/js/jquery.gritter.js"></script>
	<script type="text/javascript"
		src="plugins/framework/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/outsourcingManage/addOutsoucringManage.js"></script>
	<script>var basePath = '<%=basePath%>';</script>
	<!-- END BODY -->
</body>
</html>