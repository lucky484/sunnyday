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
<script type="text/javascript" src="../../plugins/My97DatePicker/4.8/WdatePicker.js"></script>
<title>新增项目</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 项目管理 <span class="c-gray en">&gt;</span> 项目更新</nav>
<div class="page-container">
    <div class="porlet box blur">
         <div class="portlet-title">
			<div class="caption">
				<i class="Hui-iconfont"> &#xe667;</i> <span>项目更新</span>
			</div>
		</div>
		<form class="form form-horizontal" id="" enctype="multipart/form-data" style="margin-bottom: 15px;">
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>项目编号：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${pm.projectCode }" disabled="true" placeholder="" id="projectCode" name="projectCode" style="width:25%;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>项目名称：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${pm.projectName}" disabled="true" placeholder="" id="projectName" name="projectName" style="width:25%;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>项目经理：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${pm.projectManager}" disabled="true" placeholder="" id="projectManager" name="projectManager" style="width:25%;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>客户经理：</label>
					<div class="formControls col-xs-8 col-sm-9">
					    <input type="text" class="input-text" value="${pm.implementManager}" disabled="true" placeholder="" id="implementManager" name="implementManager" style="width:25%;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>开始时间：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" disabled="true" value='<fmt:formatDate value="${pm.startTime }" type="date" pattern="yyyy-MM-dd" />' readonly id="startTime" name="start_time" class="input-text Wdate" style="width:15%;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>内测时间：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" disabled="true" value='<fmt:formatDate value="${pm.medialTime }" type="date" pattern="yyyy-MM-dd" />'  readonly id="medialTime" name="medial_time" class="input-text Wdate" style="width:15%;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>交付时间：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" disabled="true" value='<fmt:formatDate value="${pm.leadTime }" type="date" pattern="yyyy-MM-dd" />' readonly id="leadTime" name="lead_time" class="input-text Wdate" style="width:15%;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>性质：</label>
				<div class="formControls col-xs-8 col-sm-9 skin-minimal">
					<div class="radio-box">
						<input name="projectNature" type="radio" value="0" disabled="true" <c:if test="${pm.projectNature == 0}">checked</c:if>>
						<label>自研</label>
					</div>
					<div class="radio-box">
						<input type="radio" name="projectNature" value="1" disabled="true"  <c:if test="${pm.projectNature == 1}">checked</c:if>>
						<label>外包</label>
					</div>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>项目报价：</label>
				<div class="formControls col-xs-8 col-sm-9">
				    <span id="projectQuotationDiv">
					    <span class="add-on">￥</span>
						<input type="text" class="input-text" value="${pm.projectQuotation}" placeholder="" disabled="true" id="projectQuotation" name="projectQuotation" style="width:13%;;margin-left:-4px;margin-top: -5px;border-left:0px;" onblur="formatNumber(this)" >
					</span>
				</div>
			</div>
			<c:if test="${pm.projectNature == 1}">
			<div class="row cl" id="outsourcingQuotationParentDiv">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>外包价格：</label>
				<div class="formControls col-xs-8 col-sm-9">
					     <span id="outsourcingQuotationDiv">
							<span class="add-on">￥</span>
							<input type="text" class="input-text" value="${pm.outsourcingQuotation }" disabled="true" placeholder=""  id="outsourcingQuotation" name="outsourcingQuotation" style="width:13%;margin-left:-4px;margin-top: -5px;border-left:0px;" onblur="formatNumber(this)" >
						 </span>
				</div>
			</div>
			</c:if>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>项目状态：</label>
				<div class="formControls col-xs-8 col-sm-9">
			         <input type="text" class="input-text" value="${pm.projectStatus}" disabled="true" placeholder="" id="projectStatus" name="projectStatus" style="width:25%;">
			    </div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>客户名称：</label>
				<div class="formControls col-xs-8 col-sm-9">
					 <input type="text" class="input-text" value="${pm.customerName}" disabled="true" placeholder="" id="customerName" name="customerName" style="width:25%;">
			 	</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">备注：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<textarea class="textarea" id="remark" name="remark" disabled="true" style="width:37%;">${pm.remark }</textarea>
				</div>
			</div>
			
			<c:if test="${fn:length(pf) > 0 }">
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-2">附件下载：</label>
						<div class="formControls col-xs-8 col-sm-9">
							<c:forEach items="${pf }" var="file">
								<div>
									<a href="download.do?projectFileId=${file.projectFileId }" style="color:#06c;">
										${file.fileName } </a>
								</div>
							</c:forEach>
						</div>
					</div>
				</c:if>
			<div class="row cl">
				<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
					<a href="edit.do?projectId=${pm.projectId}" class="btn btn-primary" type="button"><i class="Hui-iconfont">&#xe60c;</i> 编辑</a>
				</div>
			</div>
		</form>
		<c:if test="${fn:length(list)>0 }">
			<div id="commentsDiv" style="margin-left:20px;margin-top:50px;">
				<h4>评论</h4>
				<hr style="width:45%;"/>
				<div class="commentsList">
					<c:forEach items="${list}" var="li">
						<div class="control-group" style="width:45%">
							评论人：<i>@${li.critic}&nbsp;&nbsp;</i>评论时间：<i
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
			<div id="commentsDiv" style="margin-left:20px;"></div>
		</c:if>
		<div class="" style="margin-bottom:100px;margin-left:20px;margin-top:20px;">
			<h4 style="text-align:">添加评论</h4>
			<form id="addProjectCommentForm">
				<input type="hidden" value="${pm.projectId}" id="projectId" name="projectId" />
				<div class="">
					<textarea class="textarea" id="comment" name="comment" style="width:45%;"></textarea>
				</div>
					<button class="btn btn-primary" type="submit"><i class="Hui-iconfont">&#xe647;</i>评论</button>
			</form>
		</div>
	</div>
</div>
<jsp:include page="../_footer.jsp"></jsp:include>
<script>var basePath = '<%=basePath%>';</script>
<script type="text/javascript" src="../../plugins/jquery.form.js"></script>
<script type="text/javascript" src="../../plugins/framework/js/jquery.pulsate.js"></script>
<script type="text/javascript" src="../../plugins/framework/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="../../js/project/viewProject.js"></script>
<script type="text/javascript" src="../../plugins/framework/js/jquery.gritter.js"></script>
<script>var outsourcingQuotation = ${pm.outsourcingQuotation}</script>
<script>var projectQuotation = ${pm.projectQuotation}</script>
</body>
</html>