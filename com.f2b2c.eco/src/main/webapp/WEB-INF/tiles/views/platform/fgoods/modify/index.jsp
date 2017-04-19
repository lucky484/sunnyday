<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>商品发布</title>
<spring:url value="/farm/goods/insert-fgoods" var="insertFGoodsUrl" />
<spring:url value="/farm/goods/modifyfgoods" var="modifyFGoodsUrl" />
<spring:url value="/farm/goods/insert-fgoods-pic"
	var="uploadGoodsPicUrl" />
<spring:url value="/resources/platform/js/jquery.min.js" var="jqueryUrl" />
<spring:url
	value="/resources/platform/js/plugins/ueditor1_4_3_3/ueditor.config.js"
	var="ueditorconfigJsUrl" />
<spring:url
	value="/resources/platform/js/plugins/ueditor1_4_3_3/ueditor.all.js"
	var="ueditorAllJsUrl" />
<spring:url
	value="/resources/platform/js/plugins/easyUI/jquery.easyui.min.js"
	var="easyUIUrl" />
<spring:url
	value="/resources/platform/css/plugins/easyUI/themes/metro/easyui.css"
	var="easyUICssUrl" />
<spring:url
	value="/resources/platform/js/plugins/fileUpload/jquery.fileupload.js"
	var="fileUploadUrl" />
<spring:url
	value="/resources/platform/js/plugins/fileUpload/vendor/jquery.ui.widget.js"
	var="widgetUrl" />
<spring:url
	value="/resources/platform/js/plugins/fileUpload/jquery.iframe-transport.js"
	var="transportUrl" />
<link rel="stylesheet" type="text/css" href="${easyUICssUrl}" />
<style>
.half {
	width: 50%;
	float: left;
}

.picDiv {
	width: 100px;
	height: 100px;
	border: 1px solid #ccc;
	text-align: center;
	color: #23527c;
	cursor: pointer;
	position: relative;
	float: left;
}

.picHint {
	vertical-align: middle;
	position: absolute;
	left: 0;
	width: 100%;
	height: 100%;
}

.picInput {
	width: 100px;
	height: 100px;
	position: absolute;
	opacity: 0;
	cursor: pointer;
}

.goodsImg {
	width:100%;
	height:100%;
}

#sortable {
	list-style-type: none;
	margin: 0;
	padding: 0;
}

#sortable li {
	float: left;
	width: 100px;
	height: 100px;
	margin: 0 10px 0 0;
}

.close-modal {
	top: -8px;
	right: -8px;
	width: 18px;
	height: 18px;
	font-size: 14px;
	line-height: 16px;
	border-radius: 9px;
	color: #fff;
	text-align: center;
	cursor: pointer;
	position: absolute;
	background: rgba(153, 153, 153, 0.6);
}
/* 必填项样式 */
.flower {
	color: red;
	margin-left: 2px;
	font-size: 13px;
	font-style: italic;
}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
		<button class="btn btn-info" onclick="fgoodslistUrl()" style="">返回商品列表</button>
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>修改商品</h5>
					</div>
					<div class="ibox-content">
						<!--  <div class="alert alert-info">
                            	这边放错误提示
                        </div> -->
						<form class="form-horizontal m-t"
							enctype="multipart/form-data" action="${modifyFGoodsUrl}" method="post" id="form">
							<div class="form-group draggable">
								<label class="col-sm-3 control-label">名称<span class="flower">*</span>：</label>
								<div class="col-sm-6">
									<input type="text" name="name" class="form-control" value="${data.name}" id="name"> 
									<input type="hidden" id="id" name="id"value="${data.id}">
								</div>
							</div>
							<div class="form-group draggable">
								<label class="col-sm-3 control-label">商品类型：</label>
								<div class="col-sm-6">
									<select class="form-control" name="type">
                                        <option value="1" <c:if test="${data.type == '1'}"> selected=true</c:if>>国产商品</option>
                                        <option value="2" <c:if test="${data.type == '2'}"> selected=true</c:if>>进口商品</option>
                                        <option value="3" <c:if test="${data.type == '3'}"> selected=true</c:if>>活动商品 </option>
                                        <option value="4" <c:if test="${data.type == '4'}"> selected=true</c:if>>其他商品 </option>
									</select>
								</div>
							</div>
							<div class="form-group draggable">
								<label class="col-sm-3 control-label">分类<span class="flower">*</span>：</label>
								<div class="col-sm-6">
									<select id="kindTree" name="kind.id" class="form-control"></select>
								</div>
							</div>
							<div class="form-group draggable">
								<label class="col-sm-3 control-label">国产/进口：</label>
								<div class="col-sm-6">
									<select id="" name="madeInChina" class="form-control">
										 <c:if test="${data.madeInChina == 0}">														
											<option value="0">国产</option>
											<option value="1">进口</option>
										</c:if>
										 <c:if test="${data.madeInChina == 1}">	
											<option value="1">进口</option>													
											<option value="0">国产</option>
										</c:if>
									</select>
								</div>
							</div>
							<div class="form-group draggable">
								<label class="col-sm-3 control-label">图片<span class="flower">*</span>：</label>
								<div class="col-sm-6 logoUrlDiv">
								<span class="help-block m-b-none">第一张图为封面图；建议尺寸：120 x 120 像素</span>
									<ul id="sortable">
										
										<c:forEach items="${fn:split(data.logoUrl,'|') }" var="i" varStatus="index">
											<li>
											<div class="picDiv" id="picDiv${index.count }">
												<input type="hidden" name="logoUrl" id="logoUrl${index.count }" value="${i}"/>
												<spring:url value="${path}${i}" var="picUrl" />
												<span class="picHint" id="picHint${index.count }"><img class="goodsImg" src="${picUrl }" /></span>
												<a href='javascript:deletePic(${index.count })' class='close-modal'>×</a>
											</div>
										</li>
										</c:forEach>
										<li>
											<div class="picDiv" id="picDiv0">
												<input type="hidden" name="logoUrl" id="logoUrl0" /> <span
													class="picHint" id="picHint0"><br />
												<br />+ 加图</span> <input type="file" name="file" class="picInput"
													multiple="multiple" id="picInput0" />
											</div>
										</li>
									</ul>
								</div>
							</div>
							<div class="form-group draggable">
								<label class="col-sm-3 control-label">单价(元)<span class="flower">*</span>：</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" name="pricex"  value="${data.price / 100}" id="pricex"/>
								</div>
							</div>
							<div class="form-group draggable">
								<label class="col-sm-3 control-label">单价单位<span class="flower">*</span>：</label>
								<div class="col-sm-6">
								 <select class="form-control m-b" name="unit" id="unit">
                                        <option value="0" <c:if test="${data.unit == '0'}"> selected=true</c:if>>箱</option>
                                        <option value="1" <c:if test="${data.unit == '1'}"> selected=true</c:if> >斤</option>
                                        <option value="2"<c:if test="${data.unit == '2'}"> selected=true</c:if>>公斤</option>
                                    </select> 
								</div>
							</div>
							<div class="form-group draggable">
								<label class="col-sm-3 control-label">库存<span class="flower">*</span>：</label>
								<div class="col-sm-6">
									<input type="text" name="remain" class="form-control" placeholder="" value="${data.remain}" id="remain">
								</div>
							</div>
							<div class="form-group draggable">
								<label class="col-sm-3 control-label">产地<span class="flower">*</span>：</label>
								<div class="col-sm-6">
									<input type="text" name="producePlace" class="form-control" placeholder="" value="${data.producePlace}" id="producePlace">
								</div>
							</div>
							<div class="form-group draggable">
								<label class="col-sm-3 control-label">投放市场 省<span class="flower">*</span>：</label>
								
								<div class="col-sm-3">
									<select class="form-control provinceSelect">
										<c:forEach items="${provinces}" var="p">
											<option value="${p.id}" ref="<c:forEach items="${p.cityModelList}" var="c" varStatus="index">${c.id}-${c.name}&<c:forEach items="${c.areaModelList}" var="a" varStatus="areaindex">${a.id}-${a.name}<c:if test="${areaindex.count!=c.areaModelList.size()}">,</c:if></c:forEach><c:if test="${index.count!=p.cityModelList.size()}">|</c:if></c:forEach>">${p.name}</option>
										</c:forEach>
									</select>
									</div>
								
							</div>
							<div class="form-group draggable">
								<label class="col-sm-3 control-label">市<span class="flower">*</span>：</label>
								
								<div class="col-sm-3">
									<select class="form-control citySelect" id="cityId" name="cityId">
										<option value="${data.cityId}">${data.cityName}</option>
									</select>
									</div>
								
							</div>
							
							<div class="form-group draggable">
								<label class="col-sm-3 control-label">规格<span class="flower">*</span>：</label>
								<div class="col-sm-6">
									<input type="text" name="spec" class="form-control" value="${data.spec}" id="spec"> 
								</div>
							</div>
							<div class="form-group draggable">
								<label class="col-sm-3 control-label">简介<span class="flower">*</span>：</label>
								<div class="col-sm-6">
									<input type="text" name="intro" class="form-control" value="${data.intro}" id="intro"> 
								</div>
							</div>
						</form>
							<div class="form-group draggable">
								<div class="col-sm-9 col-sm-offset-3">
									<button class="btn btn-primary" onclick="modify()" id="modify">保存</button>
								</div>
							</div>
						
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
