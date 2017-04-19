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
}

input[type="radio"] {
	margin-top: 8px;
}
</style>
<div class="wrapper wrapper-content animated fadeInRight">
	<div class="row">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<!-- <div class="col-sm-12">
	                	<a class="btn btn-primary radius" data-title="添加门店" onclick="shop_add();" href="javascript:void(0);">
	                		<i class="fa fa-plus">添加门店</i>
	                	</a>
                	</div> -->
				<div class="ibox-title">
					<h5>支付问题</h5>
				</div>
				<div class="ibox-content">
					<form method="post" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-3 control-label">
								选择支付问题类别 <span class="flower">*</span>：
							</label> 
							
							<label class="control-label"> 支付相关问题 </label> 
							<input type="radio" class="control-label" id="" name="status" value="1" checked="true"> 
							&nbsp;&nbsp;&nbsp;&nbsp;
							<label class="control-label"> 积分卡券使用 </label> 
							<input type="radio" class="control-label" id="" name="status" value="2"> 
							&nbsp;&nbsp;&nbsp;&nbsp;
							<label class="control-label"> 支付异常 </label> 
							<input type="radio" class="control-label" id="" name="status" value="3">
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label"> 
								支付问题 <span class="flower">*</span>：
							</label>
							<div class="col-sm-6">
								<input type="text" id="question" class="form-control" onkeydown="if(event.keyCode==13){return false;}">
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label">解决方案<span
								class="flower">*</span>：
							</label>
							<div class="col-sm-6">
								<div class="row">
									<script id="container" name="content" type="text/plain"></script>
									<script type="text/javascript">
										
									</script>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label"> 是否为热点问题 <span
								class="flower">*</span>：
							</label> 
							<label class="control-label">是</label> 
							<input type="radio" class="control-label" id="" name="style" value="1"> 
							<label class="control-label">否</label> 
							<input type="radio" class="control-label" id="" name="style" value="0" checked="true">
						</div>

						<!-- 提交按钮 -->
						<div class="form-group">
							<label class="col-sm-3 control-label"></label>
							<div class="col-sm-6">
								<input type="hidden" id="type" name="type" value="1">
								<button class="btn btn-primary" type="button" id="publish">提交</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>



