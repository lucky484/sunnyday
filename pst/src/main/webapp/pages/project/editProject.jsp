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
		<form class="form form-horizontal" id="editProject" enctype="multipart/form-data" style="margin-bottom: 15px;">
		    <input type="hidden" id="projectId" name="projectId" value="${pm.projectId}" />
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>项目编号：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${pm.projectCode }" placeholder="" id="projectCode" name="projectCode" style="width:25%;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>项目名称：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${pm.projectName}" placeholder="" id="projectName" name="projectName" style="width:25%;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>项目经理：</label>
				<div class="formControls col-xs-8 col-sm-9">
					 <span class="select-box" style="width:12%;">
							<select class="select" id="projectManagerId" name="projectManagerId" size="1">
							<option value="">请选择项目经理</option>
								<c:forEach items="${list1}" var="list">
									<option value="${list.projectManagerId }"
										<c:if test="${pm.projectManager == list.name }">selected="selected"</c:if>>${list.name }</option>
								</c:forEach>
							</select>
					  </span> 
					  <input type="hidden" id="projectManager" name="projectManager" value="${pm.projectManager}"/>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>客户经理：</label>
					<div class="formControls col-xs-8 col-sm-9">
						 <span class="select-box" style="width:12%;">
							<select class="select" id="implementManagerId" name="implementManagerId" size="1">
								<option value="">请选择客户经理</option>
								<c:forEach items="${list2 }" var="list">
									<option value="${list.implementManagerId }"
												<c:if test="${pm.implementManager == list.name }">selected="selected"</c:if>>${list.name }</option>
								</c:forEach>
							</select>
						 </span> 
						 <input type="hidden" id="implementManager" name="implementManager" value="${pm.implementManager}"/>
					</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>开始时间：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value='<fmt:formatDate value="${pm.startTime }" type="date" pattern="yyyy-MM-dd" />' readonly id="startTime" name="start_time" class="input-text Wdate" style="width:15%;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>内测时间：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value='<fmt:formatDate value="${pm.medialTime }" type="date" pattern="yyyy-MM-dd" />'  readonly id="medialTime" name="medial_time" class="input-text Wdate" style="width:15%;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>交付时间：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value='<fmt:formatDate value="${pm.leadTime }" type="date" pattern="yyyy-MM-dd" />' readonly id="leadTime" name="lead_time" class="input-text Wdate" style="width:15%;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>性质：</label>
				<div class="formControls col-xs-8 col-sm-9 skin-minimal">
					<div class="radio-box">
						<input name="projectNature" type="radio" value="0" <c:if test="${pm.projectNature == 0}">checked</c:if>>
						<label>自研</label>
					</div>
					<div class="radio-box">
						<input type="radio" name="projectNature" value="1" <c:if test="${pm.projectNature == 1}">checked</c:if>>
						<label>外包</label>
					</div>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>项目报价：</label>
				<div class="formControls col-xs-8 col-sm-9">
				    <span id="projectQuotationDiv">
					    <span class="add-on">￥</span>
						<input type="text" class="input-text" value="${pm.projectQuotation}" placeholder="" id="projectQuotation1" name="projectQuotation1" style="width:13%;;margin-left:-4px;margin-top: -5px;border-left:0px;" onblur="formatNumber(this)" >
					</span>
					<input type="hidden" id="projectQuotation" name="projectQuotation"/>
				</div>
			</div>
			<div class="row cl" style="display:none;" id="outsourcingQuotationParentDiv">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>外包价格：</label>
				<div class="formControls col-xs-8 col-sm-9">
					     <span id="outsourcingQuotationDiv">
							<span class="add-on">￥</span>
							<input type="text" class="input-text" value="${pm.outsourcingQuotation }" placeholder=""  id="outsourcingQuotationStr" name="outsourcingQuotationStr" style="width:13%;margin-left:-4px;margin-top: -5px;border-left:0px;" onblur="formatNumber(this)" >
						 </span>
					<input type="hidden" id="outsourcingQuotation" name="outsourcingQuotation" />
				</div>
			</div>
			<c:import var="status" charEncoding="utf-8"
				url="../../xml/project_status.xml" />
			<x:parse var="doc" xml="${status}" />
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>项目状态：</label>
				<div class="formControls col-xs-8 col-sm-9">
				        <span class="select-box" style="width:12%;">
				        <c:set var="projectStatusNumber" value="${pm.projectStatusNumber }" scope="page"></c:set>
							<select class="select" id="projectStatusNumber" name="projectStatusNumber" size="1">
								<x:forEach select="$doc/project_status/status" var="status">
									<option <x:if select="$pageScope:projectStatusNumber=$status/value">selected="selected"</x:if> value="<x:out select="$status/value" />"><x:out
															select="$status/name" /></option>
								</x:forEach>
							</select>
						</span>
						<input id="projectStatus" name="projectStatus" type="hidden" value="${pm.projectStatus}" />
			    </div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>客户名称：</label>
				<div class="formControls col-xs-8 col-sm-9"> <span class="select-box" style="width:12%;">
					<select class="select" id="customerId" name="customerId" size="1">
					    <option value="">请选择客户</option>
						<c:forEach items="${lcm }" var="customer">
						<option value="${customer.customerId }"
							<c:if test="${pm.customerId==customer.customerId }">selected="selected"</c:if>>${customer.customerNameShort }</option>
						</c:forEach>
					</select>
					</span>
			 	</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">备注：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<textarea class="textarea" id="remark" name="remark" style="width:37%;">${pm.remark }</textarea>
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
				<c:if test="${fn:length(pf)>0 }">
					<c:forEach items="${pf}" var="file">
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">附件：</label>
							<div class="formControls col-xs-8 col-sm-9"> 
								<span class="btn-upload form-group">
								   <input type="hidden" name="existFileId" value="${file.projectFileId }" />
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
				<c:if test="${fn:length(pf)<=0 }">
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
<jsp:include page="../_footer.jsp"></jsp:include>
<script>var basePath = '<%=basePath%>';</script>
<script type="text/javascript" src="../../plugins/jquery.form.js"></script>
<script type="text/javascript" src="../../plugins/framework/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="../../js/project/editProject.js"></script>
<script type="text/javascript" src="../../plugins/framework/js/jquery.gritter.js"></script>
</body>
</html>