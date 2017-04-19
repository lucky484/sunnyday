<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
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
<title>更新项目财务信息</title>
<jsp:include page="../global.jsp"></jsp:include>
<link rel="stylesheet" href="plugins/framework/css/DT_bootstrap.css" />
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
							<li><a href="projectsManagement/finance/financeList.do">财务管理</a>
								<span class="icon-angle-right"></span></li>
							<li>添加项目付款</li>
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
									<i class="icon-reorder"></i> <span class="hidden-480">添加项目付款</span>
								</div>
							</div>
							<div class="portlet-body form">
								<br />
								<!-- BEGIN FORM-->
								<form id="addFinanceFormPayment" class="form-horizontal">
									<div class="alert alert-error hide">
										<button class="close" data-dismiss="alert"></button>
										您的表单包含错误，请检查
									</div>
									<input type="hidden" name="projectId" value="${projectId }" />

									<div class="control-group">
										<label class="control-label">金额<span class="required">*</span></label>
										<div class="controls">
											<div id="paymentDiv"
												class="input-prepend input-append">
												<span class="add-on">￥</span> <input type="text"
													placeholder="" id="payment1" 
													name="payment1" class="m-wrap" value="" onblur="formatNumber(this)"/>
											</div>
										</div>
										<input type="hidden" id="payment" name="payment" />
									</div>
                                    <div class="control-group">
										<label class="control-label">备注<span class="required">*</span></label>
										<div class="controls">
											<textarea class="span6 m-wrap" rows="5" id="remark"
												name="remark"></textarea>
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
		src="plugins/framework/js/jquery.gritter.js"></script>
	<script type="text/javascript"
		src="plugins/framework/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/finance/addFinancePayment.js"></script>
	<script>var basePath = '<%=basePath%>';</script>
	<!-- END BODY -->
</body>
</html>