<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
<title>项目列表</title>
<jsp:include page="../global.jsp"></jsp:include>
<link rel="stylesheet" href="plugins/framework/css/DT_bootstrap.css" />
<link rel="stylesheet" href="plugins/framework/css/jquery.gritter.css" />
<link rel="stylesheet" href="css/projectsList.css" />
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
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<!-- BEGIN PAGE CONTENT-->
				<div class="row-fluid">
					<div class="span12">
						<div class="portlet box grey">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-table"></i>外包管理列表
								</div>
								<div class="actions">
										<a href="projectsManagement/outsourcingManage/addOutsourcingManage.do"
										class="btn blue"><i class="icon-plus"></i> 新增</a>
									<c:if
										test="${fn:contains(sessionScope.authorities, 'outsourcingManage_export')}">
										<a href="projectsManagement/outsourcingManage/export.do"
											class="btn green"><i class="icon-file"></i> 导出Excel</a>
									</c:if>
								</div>
							</div>
							<div class="portlet-body">
								<table id="outsourcingManage"
									class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
										    <th>创建时间</th>
											<th>姓名</th>
											<th>邮箱</th>
											<th>电话</th>
											<th>添加时间</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
								<div id="deleteConfirm" class="modal fade" tabindex="-1"
									role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true"></button>
										<h3 id="myModalLabel">删除确认</h3>
									</div>
									<div class="modal-body">
										<p>您确认要删除此项目吗？</p>
									</div>
									<div class="modal-footer">
										<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
										<button data-dismiss="modal" class="btn blue"
											id="deleteOutSourcingManage">确定</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- END PAGE CONTENT-->
			</div>
			<!-- END PAGE CONTAINER-->
		</div>
		<!-- END PAGE -->
	</div>
	<!-- END CONTAINER -->
	<jsp:include page="../footer.jsp"></jsp:include>
	<script type="text/javascript"
		src="plugins/framework/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript"
		src="plugins/framework/js/DT_bootstrap.js"></script>
	<script type="text/javascript"
		src="plugins/framework/js/jquery.gritter.js"></script>
	<script type="text/javascript" src="js/outsourcingManage/outsourcingManageList.js"></script>
	<!-- END BODY -->
</body>
</html>