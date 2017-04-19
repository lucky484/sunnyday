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
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 需求变更列表<span class="c-gray en">&gt;</span> 需求变更详情</nav>
<div class="page-container">
   <div class="porlet box blur">
      <div class="portlet-title">
			<div class="caption">
				<i class="Hui-iconfont"> &#xe667;</i> <span>需求变更详情</span>
			</div>
		</div>
		<form class="form form-horizontal" style="line-height: 40px;margin-bottom: 60px;margin-left:20px;">
			<div class="row cl" style="display:none;">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>需求ID：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${crm.crId}" disabled="true" placeholder="" id="crId" name="crId" style="width:50%;">
				</div>
			</div>
			<div class="row cl" style="display:none;">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>项目ID：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${crm.projectId}" disabled="true" placeholder="" id="projectId" name="projectId" style="width:50%;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>需求描述：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${crm.crDescribe}" disabled="true" placeholder="" id="crDescribe" name="crDescribe" style="width:50%;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>预估（小时）：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${crm.estimatedWorkload}" disabled="true" placeholder="" id="estimatedWorkload" name="estimatedWorkload" style="width:50%;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>提交人：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${crm.presenter}" disabled="true" placeholder="" id="presenter" name="presenter" style="width:50%;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>确认人：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${crm.confirmor}" disabled="true" placeholder="" id="confirmor" name="confirmor" style="width:50%;">
				</div>
			</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>报价：</label>
					<div class="formControls col-xs-8 col-sm-9">
					    <span id="confirmQuotationDiv">
						    <span class="add-on">￥</span>
							<input type="text" class="input-text" value="${crm.confirmQuotation}" disabled="true" placeholder="" id="confirmQuotation" name="confirmQuotation" style="width:13%;;margin-left:-4px;margin-top: -5px;border-left:0px;" onblur="formatNumber(this)" >
						</span>
					</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">备注：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<textarea class="textarea" id="remark" name="remark" disabled="true" style="width:37%;">${crm.remark}</textarea>
				</div>
			</div>
			<c:if test="${fn:length(fileList) > 0 }">
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-2">附件下载：</label>
						<div class="formControls col-xs-8 col-sm-9">
							<c:forEach items="${fileList }" var="file">
								<div>
									<a href="download.do?projectFileId=${file.crFileId }" style="color:#06c;">
										${file.fileName } </a>
								</div>
							</c:forEach>
						</div>
					</div>
			</c:if>
			<c:if test="${sessionScope.user.role.roleName=='general_manager' or sessionScope.user.role.roleName=='admin' or sessionScope.user.userId == pm.creatorId}">
				<div class="row cl">
					<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
						<a href="projectsManagement/projects/Requirement/editCRList.do?crId=${crm.crId}" class="btn btn-primary" type="button"><i class="Hui-iconfont">&#xe60c;</i> 编辑</a>
					</div>
				</div>
			 </c:if>
		</form>
		<c:if test="${fn:length(list)>0 }">
			<div id="commentsDiv" style="margin-left:20px;">
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
			<form id="addCommentForm">
				<input type="hidden" value="${crm.crId}" id="crrid" name="crrid" />
				<div class="">
					<textarea class="textarea" id="comment" name="comment" style="width:45%;"></textarea>
				</div>
					<button class="btn btn-primary" type="submit"><i class="Hui-iconfont">&#xe647;</i>评论</button>
			</form>
		</div>
	</div>
</div>
<script>var basePath = '<%=basePath%>';</script>
<script type="text/javascript" src="plugins/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="plugins/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="plugins/static/h-ui.admin/js/H-ui.admin.js"></script>
<script type="text/javascript" src="js/date-format.js"></script>
<script type="text/javascript" src="plugins/datatables/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="plugins/framework/js/jquery.gritter.js"></script>
<script type="text/javascript" src="plugins/framework/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/requirement/viewCRList.js"></script>
<script type="text/javascript" src="plugins/framework/js/jquery.pulsate.js"></script>
</body>
</html>