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
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 项目管理<span class="c-gray en">&gt;</span> 项目经理详情</nav>
<div class="page-container">
   <div class="porlet box blur">
      <div class="portlet-title">
			<div class="caption">
				<i class="Hui-iconfont"> &#xe667;</i> <span>项目经理详情</span>
			</div>
		</div>
		<form class="form form-horizontal" style="line-height: 40px;margin-bottom: 60px;margin-left:20px;">
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>姓名：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${pm.name }" disabled="true" placeholder="" id="name" name="name" style="width:50%;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>邮箱：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${pm.email }" disabled="true" placeholder="" id="email" name="email" style="width:50%;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>电话：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${pm.phone }" disabled="true" placeholder="" id="phone" name="phone" style="width:50%;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>添加时间：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value='<fmt:formatDate value="${pm.createTime }" type="date" pattern="yyyy-MM-dd" />' disabled="true" placeholder="" id="createTime" name="createTime" style="width:50%;">
				</div>
			</div>
			<c:if test="${sessionScope.user.role.roleName=='general_manager' or sessionScope.user.role.roleName=='admin' or sessionScope.user.userId == pm.creatorId}">
				<div class="row cl">
					<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
						<a href="edit.do?projectManagerId=${pm.projectManagerId}" class="btn btn-primary" type="button"><i class="Hui-iconfont">&#xe60c;</i> 编辑</a>
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
			<form id="addProjectManagerCommentForm">
				<input type="hidden" value="${pm.projectManagerId}" id="projectManagerId" name="projectManagerId" />
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
<script type="text/javascript" src="../../js/projectManager/viewProjectManager.js"></script>
</body>
</html>