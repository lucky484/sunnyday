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
<title>新增项目</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 需求变更列表 <span class="c-gray en">&gt;</span> 添加需求变更</nav>
<div class="page-container">
    <div class="porlet box blur">
         <div class="portlet-title">
			<div class="caption">
				<i class="Hui-iconfont"> &#xe667;</i> <span>添加需求变更</span>
			</div>
		</div>
		<form class="form form-horizontal" id="CRForm" enctype="multipart/form-data" style="margin-bottom: 15px;">
		    <input type="hidden" placeholder="" value="${crm.projectId}" id="projectId" name="projectId" class="m-wrap huge" />
		    <input type="hidden" placeholder="" value="${crm.crId}" id="crId" name="crId" class="m-wrap huge" />
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>需求描述：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${crm.crDescribe}" placeholder="" id="crDescribe" name="crDescribe" style="width:25%;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>预估（小时）：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${crm.estimatedWorkload}" placeholder="" id="estimatedWorkload" name="estimatedWorkload" style="width:25%;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>提交人：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${crm.presenter}" placeholder="" id="presenter" name="presenter" style="width:25%;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>确认人：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${crm.confirmor}" placeholder="" id="confirmor" name="confirmor" style="width:25%;">
				</div>
			</div>
			<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>报价：</label>
					<div class="formControls col-xs-8 col-sm-9">
					    <span id="confirmQuotationDiv">
						    <span class="add-on">￥</span>
							<input type="text" class="input-text" placeholder="" id="confirmQuotation1" name="confirmQuotation1" value="${crm.confirmQuotation}" style="width:13%;;margin-left:-4px;margin-top: -5px;border-left:0px;" onblur="formatNumber(this)" >
						</span>
						<input type="hidden" id="confirmQuotation" name="confirmQuotation" value="${crm.confirmQuotation}"/>
					</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">备注：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<textarea class="textarea" id="remark" name="remark" style="width:37%;">${crm.remark}</textarea>
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
				<c:if test="${fn:length(fileList)>0 }">
					<c:forEach items="${fileList}" var="file">
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">附件：</label>
							<div class="formControls col-xs-8 col-sm-9"> 
								<span class="btn-upload form-group">
								<input type="hidden" name="existFileId" value="${file.crFileId }" />
								<input name="deleteFlag" type="hidden" value="0" />
								   <input class="input-text" type="text" value="${file.fileName }" style="width:200px">
								   <a href="javascript:void();" class="btn btn-primary upload-btn" style="display:none;"><i class="Hui-iconfont">&#xe642;</i> 浏览</a>
								   <a href="javascript:void();" class="btn btn-warning"><i class="Hui-iconfont">&#xe6df;</i> 变更</a>
								   <input type="file" multiple name="file" class="input-file" onchange="upload(this);" />
								</span>  
								<span href="javascript:void();" class="btn btn-danger" onclick="delFile(this);"><i class="Hui-iconfont">&#xe60b;</i> 删除</span>
								<span href="javascript:void();" class="btn btn-success" onclick="insertFile(this);"><i class="Hui-iconfont">&#xe604;</i> 继续添加</span>
							 </div>
						 </div>
					</c:forEach>
				</c:if>
				<c:if test="${fn:length(fileList)<=0 }">
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">附件：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
						<span class="btn-upload form-group">
								<input class="input-text" type="text" style="width:200px">
								<a href="javascript:void();" class="btn btn-primary upload-btn"><i class="Hui-iconfont">&#xe642;</i> 浏览</a>
								<a href="javascript:void();" class="btn btn-warning" style="display:none;"><i class="Hui-iconfont">&#xe6df;</i> 变更</a>
								<input type="file" multiple name="file" class="input-file" onchange="upload(this);" />
						</span>
						<span href="javascript:void();" class="btn btn-danger" onclick="delFile(this);"><i class="Hui-iconfont">&#xe60b;</i> 删除</span>
						<span href="javascript:void();" class="btn btn-success" style="display:none;"  onclick="insertFile(this);"><i class="Hui-iconfont">&#xe604;</i> 继续添加</span>
					 </div>
				</div>
				</c:if>
			<div class="row cl">
				<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
					<button type="submit" class="btn btn-primary" type="button"><i class="Hui-iconfont">&#xe632;</i> 保存</button>
					<button onClick="window.history.back(-1);" class="btn btn-default" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
				</div>
			</div>
		</form>
	</div>
</div>
<script>var basePath = '<%=basePath%>';</script>
<script type="text/javascript" src="plugins/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="plugins/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="plugins/static/h-ui.admin/js/H-ui.admin.js"></script>
<script type="text/javascript" src="js/date-format.js"></script>
<script type="text/javascript" src="plugins/datatables/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="plugins/framework/js/jquery.gritter.js"></script>
<script type="text/javascript" src="js/requirement/editCRList.js"></script>
<script type="text/javascript" src="plugins/framework/js/jquery.validate.min.js"></script>
</body>
</html>