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
<spring:url
	value="/resources/platform/js/plugins/ueditor1_4_3_3/ueditor.config.js"
	var="ueditorconfigJsUrl" />
<spring:url
	value="/resources/platform/js/plugins/ueditor1_4_3_3/ueditor.all.js"
	var="ueditorAllJsUrl" />
<spring:url
	value="/resources/platform/css/plugins/easyUI/themes/metro/easyui.css"
	var="easyUICssUrl" />
<spring:url value="/farm/goods/fshoplist" var="fgoodslistUrl" />
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
	width: 100%;
	height: 100%;
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
						<h5>添加商品</h5>
					</div>
					<div class="ibox-content">
						<!--  <div class="alert alert-info">
                            	这边放错误提示
                        </div> -->
						<form class="form-horizontal m-t" enctype="multipart/form-data"
							action="${insertFGoodsUrl}" method="post" id="form">
							<div class="form-group draggable">
								<label class="col-sm-3 control-label">名称<span class="flower">*</span>：
								</label>
								<div class="col-sm-6">
									<input type="text" name="name" class="form-control" id="name">
								</div>
							</div>
							<div class="form-group draggable">
								<label class="col-sm-3 control-label">商品类型：</label>
								<div class="col-sm-6">
									<select class="form-control" name="type">
                                        <option value="1">国产商品</option>
                                        <option value="2">进口商品</option>
                                        <option value="3">活动商品 </option>
                                        <option value="4">其他商品 </option>
									</select>
								</div>
							</div>
							<div class="form-group draggable">
								<label class="col-sm-3 control-label">分类<span class="flower">*</span>：
								</label>
								<div class="col-sm-6">
									<input id="kindTree" name="kind.id" class="form-control" value="${data.kind.id }">
								</div>
							</div>
							<div class="form-group draggable">
								<label class="col-sm-3 control-label">国产/进口：</label>
								<div class="col-sm-6">
									<select id="" name="madeInChina" class="form-control">
										<option value="0">国产</option>
										<option value="1">进口</option>
									</select>
								</div>
							</div>
							<div class="form-group draggable">
								<label class="col-sm-3 control-label">图片<span class="flower">*</span>：
								</label>
								<div class="col-sm-6 logoUrlDiv">
									<span class="help-block m-b-none">第一张图为封面图；建议尺寸：120 x
										120 像素</span>
									<ul id="sortable">
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
								<label class="col-sm-3 control-label">单价(元)<span class="flower">*</span>：
								</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" name="pricex"
										id="pricex" />
								</div>
							</div>
							<div class="form-group draggable">
								<label class="col-sm-3 control-label">单价单位<span class="flower">*</span>：
								</label>
								<div class="col-sm-6">
									<select class="form-control m-b" name="unit" id="unit">
										<option value="0">箱</option>
										<option value="1">斤</option>
										<option value="2">公斤</option>
									</select>
								</div>
							</div>
							<div class="form-group draggable">
								<label class="col-sm-3 control-label">库存<span class="flower">*</span>：
								</label>
								<div class="col-sm-6">
									<input type="text" name="remain" class="form-control"
										placeholder="" id="remain">
								</div>
							</div>
							<div class="form-group draggable">
								<label class="col-sm-3 control-label">产地<span class="flower">*</span>：
								</label>
								<div class="col-sm-6">
									<input type="text" name="producePlace" class="form-control"
										placeholder="" id="producePlace">
								</div>
							</div>
							<div class="form-group draggable">
								<label class="col-sm-3 control-label">投放市场 省<span class="flower">*</span>：
								</label>
								<div class="col-sm-3">
									<select class="form-control provinceSelect">
										<c:forEach items="${provinces}" var="p">
											<option value="${p.id}"
												ref="<c:forEach items="${p.cityModelList}" var="c" varStatus="index">${c.id}-${c.name}&<c:forEach items="${c.areaModelList}" var="a" varStatus="areaindex">${a.id}-${a.name}<c:if test="${areaindex.count!=c.areaModelList.size()}">,</c:if></c:forEach><c:if test="${index.count!=p.cityModelList.size()}">|</c:if></c:forEach>">${p.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group draggable">
								<label class="col-sm-3 control-label">市<span class="flower">*</span>：
								</label>
								<div class="col-sm-3">
									<select class="form-control citySelect" id="cityId"
										name="cityId">
										<option value="1">北京市</option>
									</select>
								</div>
							</div>
							<div class="form-group draggable">
								<label class="col-sm-3 control-label">规格<span class="flower">*</span>：</label>
								<div class="col-sm-6">
									<input type="text" name="spec" class="form-control" id="spec"> 
								</div>
							</div>
							<div class="form-group draggable">
								<label class="col-sm-3 control-label">简介<span class="flower">*</span>：
								</label>
								<div class="col-sm-6">
									<input type="text" name="intro" class="form-control" id="intro">
								</div>
							</div>
							<!-- <div class="form-group draggable">
								<label class="col-sm-3 control-label">描述：</label>
								<div class="col-sm-6">
									加载编辑器的容器
									<script id="container" name="detail" type="text/plain"></script>
									配置文件
									<script type="text/javascript">
										var ue = UE.getEditor('container');
									</script>
								</div>
							</div> -->
						</form>
						<div class="form-group draggable">
							<div class="col-sm-9 col-sm-offset-3">
								<button class="btn btn-primary" onclick="submitForm()" id="publish">新增</button>
							</div>
						</div>

						<div class="clearfix"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%-- <script type="text/javascript" src="${jqueryUrl}"></script> --%>
</body>

</html>
