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
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 项目管理 <span class="c-gray en">&gt;</span> 财务管理列表</nav>
<div class="cl pd-5 bg-1 mt-20" style="margin-top:0px;"> 
<span style="float:right;"><a href="javascript:;" class="btn btn-success"><i class="Hui-iconfont">&#xe644;</i> 导出Excle</a></span>
</div>
<div class="page-container">
	<div class="mt-20">
		<table class="table table-border table-bordered table-bg table-hover table-sort"" id="projectFinance" width="100%">
			<thead>
				<tr class='text-c'>
						<th>修改日期</th>
						<th>项目编号</th>
						<th>项目名称</th>
						<th>项目经理</th>
						<th>客户经理</th>
						<th>开始时间</th>
						<th>结束时间</th>
						<th>总价</th>
						<th>已收款</th>
						<th>未收款</th>
						<th>外包款</th>
						<th>已付款</th>
						<th>未付款</th>
						<th>操作</th>
				</tr>
			</thead>
			<tbody>
			 <tr>
			 </tr>
			</tbody>
		</table>
	</div>
</div>
<jsp:include page="../_footer.jsp"></jsp:include>
<script>var basePath = '<%=basePath%>';</script>
<script type="text/javascript" src="../../plugins/datatables/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="../../js/finance/financeList.js"></script>
<script type="text/javascript" src="../../plugins/framework/js/jquery.gritter.js"></script>
</body>
</html>