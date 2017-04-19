<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spring:url
	value="/resources/market/js/plugins/ueditor1_4_3_3/ueditor.config.js"
	var="ueditorconfigJsUrl" />
<spring:url
	value="/resources/market/js/plugins/ueditor1_4_3_3/ueditor.config.js"
	var="ueditorconfigJsUrl" />
<script type="text/javascript" src="${ueditorconfigJsUrl}"></script>
<!-- 编辑器源码文件 -->
<spring:url
	value="/resources/market/js/plugins/ueditor1_4_3_3/ueditor.all.js"
	var="ueditorAllJsUrl" />
<script type="text/javascript" src="${ueditorAllJsUrl}"></script>
<style>
/* 必填项样式 */
.flower {
	color: red;
	margin-left: 2px;
	font-size: 13px;
	font-style: italic;
}
</style>
<div class="wrapper wrapper-content animated fadeInRight">
	<div class="row">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5><c:if test="${model == null}">			
					新增B端首页轮播图片</c:if>	
					<c:if test="${model != null}">			
					修改B端首页轮播图片</c:if></h5>
				</div>
				<div class="ibox-content">
				<spring:url value="/farm/bwap/addorupdate" var="wapUrl"/>
					<form role="form" class="form-horizontal" action="${wapUrl}" enctype="multipart/form-data" method="post" id="wapform">
					<input id="wap-id" name="id" type="hidden" />
					<input id="contentStr" name="contentStr" type="hidden" />
						<div class="form-group">
							<label class="col-sm-3 control-label">图片<span
								class="flower">*</span>：
							</label>
							<div class="col-sm-6">
							<input type="file" name="file" id="file"class="form-control"  onchange="change_photo()"  />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">预览<span
								class="flower"></span>：
							</label>
							<div class="col-sm-6">
							<img id="Img" width="250px" height="80px" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">公告类型<span
								class="flower">*</span>：
							</label>
							<div class="col-sm-6">
							<div class="col-sm-3">
								<input type="radio" name="type"  checked="true" value="0">商品详情
							</div>
							<div class="col-sm-3">
								<input type="radio" name="type"  value="1">富文本信息	
							</div>
							<div class="col-sm-3">
								<input type="radio" name="type"  value="2">外部链接	
							</div>
							</div>
						</div>
						
						<div class="form-group " id="goodsNoDiv">
							<label class="col-sm-3 control-label ">商品编号 <span
								class="flower">*</span>：
							</label>
							<div class="col-sm-6">
								<div class="row">
									<input type="text" name="goodsNo" id="goodsNo"class="form-control" value=""/>
								</div>
							</div>
						</div>
						
						<div class="form-group " id="ueEdiorDiv">
							<label class="col-sm-3 control-label">详情 <span
								class="flower">*</span>：
							</label>
							<div class="col-sm-6">
								<div class="row">
									<script id="container" name="content2" type="text/plain">
										${model.status}
									</script>
									<script type="text/javascript">
   									    </script>
								</div>
							</div>
						</div>
						<div class="form-group " id="urlDiv">
							<label class="col-sm-3 control-label">外部链接 <span
								class="flower">*</span>：
							</label>
							<div class="col-sm-6">
								<div class="row ">
									<input type="text" name="url" id="url"class="form-control"   />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label"></label>
							<div class="col-sm-6">
								<button type="sumbit" class="btn btn-primary" id="publish">
										<span class="modal-oper">新增</span>
									</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>



