<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<jsp:include page="../_header.jsp"></jsp:include>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 项目管理 <span class="c-gray en">&gt;</span> 客户列表</nav>
<div class="cl pd-5 bg-1 mt-20" style="margin-top:0px;"> 
<span style="float:right;"><a href="addProjectManager.do" class="btn btn-primary"><i class="Hui-iconfont"></i> 新增</a>
<a href="javascript:;" class="btn btn-success"><i class="Hui-iconfont">&#xe644;</i> 导出Excle</a></span>
</div>
<div class="page-container">
	<div class="mt-20">
		<table class="table table-border table-bordered table-bg table-hover table-sort"" id="projectManager" width="100%">
			<thead>
				<tr class='text-c'>
					    <th>创建时间</th>
						<th>姓名</th>
						<th>邮箱</th>
						<th>电话</th>
						<th>添加时间</th>
						<th>操作</th>
				</tr>
			</thead>
			<tbody>
			 <tr class="text-c">
			 </tr>
			</tbody>
		</table>
	</div>
</div>
<jsp:include page="../_footer.jsp"></jsp:include>
<script>var basePath = '<%=basePath%>';</script>
<script type="text/javascript" src="../../plugins/datatables/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="../../js/projectManager/projectManagerList.js"></script>
<script type="text/javascript" src="../../plugins/framework/js/jquery.gritter.js"></script>
</body>
</html>