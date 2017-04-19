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
<title>外包管理详情</title>
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
							<li><a href="projectsManagement/outsourcingManage/outsourcingManage.do">外包管理列表</a>
								<span class="icon-angle-right"></span></li>
							<li>外包管理详情</li>
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
									<i class="icon-reorder"></i> <span class="hidden-480">外包管理详情</span>
								</div>
							</div>
							<div class="portlet-body form">
								<br />
								<!-- BEGIN FORM-->
								<form action=""
									id="viewImplementForm" method="post" class="form-horizontal"
									enctype="multipart/form-data">
									<div class="control-group">
										<label class="control-label">姓名 </label>
										<div class="controls">
											<input type="text" placeholder="" id="name"
												name="name" class="m-wrap span6"
												value="${om.name }" disabled="disabled" /> <span
												class="help-inline"></span>
										</div>
									</div>

									<div class="control-group">
										<label class="control-label">邮箱 </label>
										<div class="controls">
											<input type="text" placeholder="" id="email"
												name="email" class="m-wrap span6"
												value="${om.email }" disabled="disabled" /> <span
												class="help-inline"></span>
										</div>
									</div>

									<div class="control-group">
										<label class="control-label">电话 </label>
										<div class="controls">
											<input type="text" placeholder="" id="phone"
												name="phone" class="m-wrap span6"
												value="${om.phone }" disabled="disabled" /> <span
												class="help-inline"></span>
										</div>
									</div>


									<div class="control-group">
										<label class="control-label">添加时间 </label>
										<div class="controls">
											<div id="medial_time_calendar" data-date=""
												data-date-format="yyyy-mm-dd" data-date-viewmode="years">
												<input class="m-wrap m-ctrl-medium date-picker"
													value='<fmt:formatDate value="${om.createTime }" type="date" pattern="yyyy-MM-dd" />'
													placeholder="" id="createTime" name="createTime"
													class="m-wrap medium" type="text" readonly="readonly"
													disabled="disabled" /> <span class="add-on"></span>
											</div>
										</div>
									</div>

									<c:if test="${sessionScope.user.role.roleName=='general_manager' or sessionScope.user.role.roleName=='admin' or sessionScope.user.userId == om.creatorId }">
										<div class="form-actions">
											<a href="projectsManagement/outsourcingManage/edit.do?outsourcingManageId=${om.outsourcingManageId}"
												class="btn  purple"><i class='icon-edit'></i> 编辑</a>
										</div>
									</c:if>
								</form>
								<!-- END FORM-->
								<c:if test="${fn:length(list)>0 }">
									<div id="commentsDiv">
										<h3>评论</h3>
										<hr />
										<div class="commentsList">
											<c:forEach items="${list}" var="li">
												<div class="control-group">
													评论人：<i class="blue">@${li.critic}&nbsp;&nbsp;</i>评论时间：<i
														class=""><fmt:formatDate value="${li.createTime}"
															pattern="yyyy-MM-dd HH:mm:ss" /></i>
													<p>${li.comment}</p>
													<hr />
												</div>
											</c:forEach>
										</div>
									</div>
								</c:if>
								<c:if test="${fn:length(list)<=0 }">
									<div id="commentsDiv"></div>
								</c:if>
								<div class="post-comment">
									<h3>添加评论</h3>
									<form id="addOutsourcingManageCommentForm">
										<input type="hidden" value="${om.outsourcingManageId}" id="outsourcingManageId"
											name="outsourcingManageId" />
										<div class="control-group">
											<div class="controls">
												<textarea class="span8 m-wrap" rows="8" id="comment"
													name="comment"></textarea>
											</div>
										</div>
										<p>
											<button class="btn blue" type="submit">评论</button>
										</p>
									</form>
								</div>
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
    <script type="text/javascript" src="js/outsourcingManage/viewOutsourcingManage.js"></script>
	<!-- END BODY -->
</body>
</html>