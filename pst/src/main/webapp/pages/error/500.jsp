<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<title>服务器端异常</title>
<jsp:include page="../global.jsp"></jsp:include>
<link href="plugins/framework/css/error.css" rel="stylesheet"
	type="text/css" />
</head>
<body class="page-404-full-page">
	<div class="row-fluid">
		<div class="span12 page-404">
			<div class="number">500</div>
			<div class="details">
				<h3>出错了！</h3>
				<p>
					服务器端出现异常，请稍候再试或联系管理员。<a href="<%=basePath%>index.do">返回首页</a>
				</p>
			</div>
		</div>
	</div>
</body>
</html>