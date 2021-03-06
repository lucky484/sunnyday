<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 项目管理<span class="c-gray en">&gt;</span> 添加客户经理</nav>
<div class="page-container">
   <div class="porlet box blur">
      <div class="portlet-title">
			<div class="caption">
				<i class="Hui-iconfont"> &#xe667;</i> <span>添加客户经理</span>
			</div>
		</div>
		<form class="form form-horizontal" id="addImplementForm" style="line-height: 40px;margin-bottom: 15px;">
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>姓名：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="" placeholder="" id="name" name="name" style="width:600px;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>邮箱：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="" placeholder="" id="email" name="email" style="width:600px;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>电话：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="" placeholder="" id="phone" name="phone" style="width:600px;">
				</div>
			</div>
			<div class="row cl">
				<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
					<button type="submit" class="btn btn-primary" type="button"><i class="Hui-iconfont">&#xe632;</i> 保存</button>
					<button onClick="window.history.back(-1);" class="btn btn-default" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
				</div>
			</div>
		</form>
	</div>
</div>
<jsp:include page="../_footer.jsp"></jsp:include>
<script>var basePath = '<%=basePath%>';</script>
<script type="text/javascript" src="../../plugins/jquery.form.js"></script>
<script type="text/javascript" src="../../plugins/framework/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="../../js/implementManager/addImplementManager.js"></script>
</body>
</html>