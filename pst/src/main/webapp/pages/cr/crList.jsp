<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<base href="<%=basePath%>">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" href="plugins/framework/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="plugins/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="plugins/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="plugins/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="plugins/static/h-ui.admin/skin/default/skin.css"/>
<link rel="stylesheet" type="text/css" href="plugins/static/h-ui.admin/css/style.css" />
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 项目管理 <span class="c-gray en">&gt;</span> 需求变更列表</nav>
<div class="page-container">
<div class="portlet">
	<div class="gray-title">
		<div class="caption" style="font-size:18px;">
			<i class=""></i>项目详情
		</div>
		<div>
			<a href="javascript:;" class="collapse" onclick="show_table(this);"></a>
		</div>
	</div>
	<div class="portlet-body">
		<table class="table table-border table-bordered table-bg mt-20">
			<tr>
				<th><div class="text-c">项目编号</div></th>
				<td colspan="3">${project.projectCode }</td>
			</tr>
			<tr>
				<th><div class="text-c">项目名称</div></th>
				<td colspan="3">${project.projectName }</td>
			</tr>
			<tr>
				<th><div class="text-c">项目经理</div></th>
				<td>${project.projectManager }</td>
				<th><div class="text-c">客户</div></th>
				<td>${project.customerName }</td>
			</tr>
			<tr>
				<th><div class="text-c">开始日期</div></th>
				<td><fmt:formatDate value="${project.startTime }"
						type="date" pattern="yyyy-MM-dd" /></td>
				<th><div class="text-c">内测日期</div></th>
				<td><fmt:formatDate value="${project.medialTime }"
						type="date" pattern="yyyy-MM-dd" /></td>
			</tr>
			<tr>
				<th><div class="text-c">交付日期</div></th>
				<td><fmt:formatDate value="${project.leadTime }"
						type="date" pattern="yyyy-MM-dd" /></td>
			  <c:if test="${sessionScope.user.role.roleName=='general_manager' or sessionScope.user.userId == project.creatorId }">
				<th><div class="text-c">项目报价</div></th>
					<td id="projectQuotation"></td>
			  </c:if>
			</tr>
			<tr>
				<th><div class="text-c">项目状态</div></th>
				<td>${project.projectStatus }</td>
				<td colspan="2"></td>
			</tr>
			<tr>
				<th><div class="text-c">备注</div></th>
				<td colspan="3">${project.remark }</td>
			</tr>
		</table>
	</div>
</div>

	<div class="cl pd-5 mt-50" style="background-color:#e5e5e5;height:30px;">
		<div class="caption" style="font-size:18px;">
			<i class="Hui-iconfont">&#xe63e;</i>需求变更列表
		<span style="float:right;"><a href="projectsManagement/projects/Requirement/addCRList.do?projectId=${param.projectId }" class="btn btn-primary"><i class="Hui-iconfont"></i> 新增</a>
		</div>
	</div>
	<div>
		<table class="table table-border table-bordered table-bg table-hover table-sort"" id="projectsTable" width="100%">
			<thead>
				<tr class="text-c">
				       <th>创建时间</th>
					   <th width="25%">需求描述</th>
					   <th>预估(小时)</th>
					   <th>提交人</th>
					   <th>确认人</th>
					   <th>报价</th>
					   <th width="15%">操作</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<td></td>
					<td id="weightsum" width="25%"></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td><div class="text-c" width="15%">合计</div></td>
				</tr>
			</tfoot>
		</table>
		<input type="hidden" id="projectId" value="${param.projectId }" />
	</div>
</div>
<script type="text/javascript" src="plugins/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="plugins/lay/layer.js"></script>
<script type="text/javascript" src="plugins/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="plugins/static/h-ui.admin/js/H-ui.admin.js"></script>
<script type="text/javascript" src="js/date-format.js"></script>
<script type="text/javascript" src="plugins/datatables/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="plugins/framework/js/jquery.gritter.js"></script>
<script type="text/javascript" src="js/requirement/crList.js"></script>
<script>var projectQuotation = ${project.projectQuotation};</script>
<script>var basePath = '<%=basePath%>';</script>
</body>
</html>