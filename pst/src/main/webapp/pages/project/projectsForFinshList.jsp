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
									<i class="icon-table"></i>项目列表
								</div>
								<div class="actions">
									<c:if
										test="${fn:contains(sessionScope.authorities, 'export_projectCodeList')}">
										<a href="projectsManagement/projects/exportForFinsh.do"
											class="btn green"><i class="icon-file"></i> 导出Excel</a>
									</c:if>
								</div>
							</div>
							<div class="portlet-body">
								<div class="searchCondition">
									<%-- <div class="condition">
										<c:import var="types" charEncoding="utf-8"
											url="../../xml/project_type.xml" />
										<x:parse var="doc" xml="${types}" />
										<label>项目类型：</label> <select id="projectTypeNumber"
											name="projectTypeNumber">
											<option value="">--</option>
											<x:forEach select="$doc/project_type/type" var="type">
												<option value="<x:out select="$type/value" />"><x:out
														select="$type/name" /></option>
											</x:forEach>
										</select>
									</div>
									<div class="condition">
										<c:import var="status" charEncoding="utf-8"
											url="../../xml/project_status.xml" />
										<x:parse var="doc" xml="${status}" />
										<label>项目状态：</label> <select id="projectStatusNumber"
											name="projectStatusNumber">
											<option value="">--</option>
											<x:forEach select="$doc/project_status/status" var="status">
												<option value="<x:out select="$status/value" />"><x:out
														select="$status/name" /></option>
											</x:forEach>
										</select>
									</div> --%>
								<!-- 	<div class="condition">
										<label>项目编号：</label> <input id="projectNumber" name="projectNumber" />
									</div> -->
									<div class="condition">
										<label>项目名称：</label> <input id="projectName" name="projectName" />
									</div>
									<div class="condition">
										<label>项目经理：</label>
									    <select id="projectManager"
											name="projectManager">
											<option value=""></option>
											    <c:forEach items="${list}" var="list">
													<option value="${list.projectManagerId }">${list.name }</option>
												</c:forEach>
										</select>
									</div>
						<!-- 			<div class="condition">
											<label class="control-label">开始日期：</label>
												<div id="lead_time_calendar"
													class="input-append date date-picker" data-date=""
													data-date-format="yyyy-mm-dd" data-date-viewmode="years">
													<input class="m-wrap m-ctrl-medium date-picker" style="height:20px;margin-top:-2px;width:128px;"
														placeholder="" id="startTime" name="start_time"
														class="m-wrap medium" type="text" readonly="readonly" /> <span
														class="add-on" style="margin-top:-2px;"><i class="icon-calendar"></i></span>
											</div>
									</div>
									<div class="condition">
											<label class="control-label">内测日期：</label>
												<div id="lead_time_calendar"
													class="input-append date date-picker" data-date=""
													data-date-format="yyyy-mm-dd" data-date-viewmode="years">
													<input class="m-wrap m-ctrl-medium date-picker" style="height:20px;margin-top:-2px;width:128px;"
														placeholder="" id="medialTime" name="medial_time"
														class="m-wrap medium" type="text" readonly="readonly" /> <span
														class="add-on" style="margin-top:-2px;"><i class="icon-calendar"></i></span>
											</div>
									</div>
									<div class="condition">
											<label class="control-label">交付日期：</label>
												<div id="lead_time_calendar"
													class="input-append date date-picker" data-date=""
													data-date-format="yyyy-mm-dd" data-date-viewmode="years">
													<input class="m-wrap m-ctrl-medium date-picker" style="height:20px;margin-top:-2px;width:128px;"
														placeholder="" id="leadTime" name="lead_time"
														class="m-wrap medium" type="text" readonly="readonly" /> <span
														class="add-on" style="margin-top:-2px;"><i class="icon-calendar"></i></span>
											</div>
									</div> -->
									<div class="search">
									  <button class="btn blue" id="search" name="search" ><i class="icon-search"></i>查询</button>
									  <button class="btn" id="clean" name="clean" ><i class="icon-trash"></i>清空</button>
									</div>
								</div>
								<table id="projectsForFinshTable"
									class="table table-striped table-bordered table-hover"style="width:100%">
									<thead>
										<tr>
											<th>创建日期</th>
											<th>项目编号</th>
											<th>项目名称</th>
											<th>项目经理</th>
											<th>客户经理</th>
											<th>开始日期</th>
											<th>内测日期</th>
											<th>交付日期</th>
											<th>项目状态</th>
											<th>客户名称</th>
											<th>变更数量</th>
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
											id="deleteProject">确定</button>
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
    <script type="text/javascript"
		src="plugins/framework/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="js/project/projectsForFinshList.js"></script>
	<!-- END BODY -->
</body>
</html>