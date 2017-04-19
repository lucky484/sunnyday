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
<title>新增项目</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 项目管理 <span class="c-gray en">&gt;</span> 客户详情</nav>
<div class="page-container" style="line-height:50px;">
	<form class="form form-horizontal">
	    <input type="hidden" placeholder="" id="customerId" name="customerId" value="${cm.customerId}" />
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>公司名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${cm.companyName}" disabled="true" placeholder="" id="companyName" name="companyName" style="width:600px;">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>客户名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${cm.customerName}" disabled="true" placeholder="" id="customerName" name="customerName" style="width:600px;">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>客户简称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${cm.customerNameShort}" disabled="true" placeholder="" id="customerNameShort" name="customerNameShort" style="width:600px;">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>客户头衔：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${cm.customerTitle}" disabled="true" placeholder="" id="customerTitle" name="customerTitle" style="width:600px;">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>客户电话：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${cm.customerPhone}" disabled="true" placeholder="" id="customerPhone" name="customerPhone" style="width:600px;">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">公司地址：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${cm.companyAddress}" disabled="true" placeholder="" id="companyAddress" name="companyAddress" style="width:600px;">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">公司电话：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${cm.companyPhone }" disabled="true" placeholder="" id="companyPhone" name="companyPhone" style="width:600px;">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">统一社会信用代码：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${cm.companyIdNumber }" disabled="true" placeholder="" id="companyIdNumber" name="companyIdNumber" style="width:600px;">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">开户银行：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${cm.bank }" disabled="true" placeholder="" id="bank" name="bank" style="width:600px;">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">付款账号：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${cm.paymentAccount }" disabled="true" placeholder="" id="paymentAccount" name="paymentAccount" style="width:600px;">
			</div>
		</div>
		<c:if test="${fn:contains(sessionScope.authorities, 'customer_update')}">
				<div class="row cl">
					<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
						<a href="editCustomer.do?customerId=${cm.customerId}" class="btn btn-primary" type="button"><i class="Hui-iconfont">&#xe60c;</i> 编辑</a>
					</div>
				</div>
		</c:if>
	</form>
</div>
<jsp:include page="../_footer.jsp"></jsp:include>
<script>var basePath = '<%=basePath%>';</script>
<script type="text/javascript" src="../../plugins/jquery.form.js"></script>
<script type="text/javascript" src="../../plugins/framework/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="../../js/customer/editCustomer.js"></script>
</body>
</html>