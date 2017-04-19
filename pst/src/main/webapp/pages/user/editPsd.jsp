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
<title>修改密码</title>
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
									<i class="icon-reorder"></i> <span class="hidden-480">修改密码</span>
								</div>
							</div>
							<div class="portlet-body form">
								<br />
								<!-- BEGIN FORM-->
								<form id="editPsdForm" class="form-horizontal"
									enctype="multipart/form-data">
									<div class="alert alert-error hide">
										<button class="close" data-dismiss="alert"></button>
										您的表单包含错误，请检查
									</div>

									<div class="control-group">
										<label class="control-label">初始密码<span
											class="required">*</span></label>
										<div class="controls">
											<input type="hidden" placeholder="" id="userId"
												name="userId" value="${um.userId}" class="m-wrap span6" />
											<input type="hidden" placeholder="" id="userName"
												name="userName" value="${um.userName}" class="m-wrap span6" />
											<input type="hidden" placeholder="" id="englishName"
												name="englishName" value="${um.englishName}" class="m-wrap span6" />
											<input type="hidden" placeholder="" id="chineseName"
												name="chineseName" value="${um.chineseName}" class="m-wrap span6" />
											<input type="hidden" placeholder="" id="roleId"
												name="roleId" value="${um.roleId}" class="m-wrap span6" />
											<input type="password" placeholder="" id="initPassword"
												name="initPassword" class="m-wrap span3" />
										</div>
									</div>
									
									<div class="control-group">
										<label class="control-label">修改密码<span
											class="required">*</span></label>
										<div class="controls">
											<input type="password" placeholder="" id="userPassword"
												name="userPassword" class="m-wrap span3" />
										</div>
									</div>
									
									<div class="control-group">
										<label class="control-label">确认密码<span
											class="required">*</span></label>
										<div class="controls">
											<input type="password" placeholder="" id="psd"
												name="psd" class="m-wrap span3" />
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
	<script src="js/user/editPsd.js"></script>
	<script>var basePath = '<%=basePath%>';</script>
	<!-- END BODY -->
</body>
</html>