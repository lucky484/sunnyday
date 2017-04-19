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
							<li>收款历史详情</li>
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
									<i class="icon-reorder"></i> <span class="hidden-480">收款历史详情</span>
								</div>
							</div>
							<div class="portlet-body form">
								<br />
								<table class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
										    <th width="30%">金额</th>
										    <th width="50%">备注</th>
										    <th>收款时间</th>
										</tr>
									</thead>
									<tbody>
									   <c:if test="${not empty list}">
										   <c:forEach var="list" items="${list}">
											   <tr>
											      <td style="text-align:center;" class="formatNumber">￥${list.receive}</td>
											      <td style="text-align:center;">${list.remark}</td>
											      <td style="text-align:center;">${list.createTimeStr}</td>
											   </tr>
										   </c:forEach>
									   </c:if>
									   <c:if test="${empty list}">
									   		   <tr>
									   		     <td colspan="3">暂无收款记录</td>
									   		   </tr>
									   </c:if>
									</tbody>
								</table>
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
	<script>var basePath = '<%=basePath%>';</script>
	<script>
	  $(function(){
	    $(".formatNumber").each(function(i,item){
	    	$(this).text("￥" + ($(this).text().substring(1,$(this).text().length).replace(/(\d)(?=(?:\d{3})+$)/g, '$1,')));
	    });
	  });
	</script>
	<!-- END BODY -->
</body>
</html>