<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spring:url value="/resources/platform/js/jquery.min.js" var="jqueryJsUrl"/>
<script src="${jqueryJsUrl}"></script>
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
				<div class="ibox-title">
					<h5>查看详情</h5>
				</div>
				<div class="ibox-content">
<%-- 					<spring:url value="/farm/bhelp/update" var="updateUrl" /> --%>
					<form method="post" class="form-horizontal">
					
						<!-- 购物咨询 -->
						<c:if test="${data.type==0 }">
							<div class="form-group">
								<label class="col-sm-3 control-label">
									购买相关问题<span class="flower">*</span>：
								</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" id="question" value="${data.question }">
								</div>
							</div>
							<div class="hidden">
									<input type="radio" class="control-label" id="" name="type" value="0" checked="true"> 
									<input type="radio" class="control-label" id="" name="status" value="1" checked="true"> 
									<input type="hidden" id="id" name="id" value="${data.id}"/>
									<input type="hidden" id="updatedTime" name="updatedTime" value="${data.updatedTime}"/>
								</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">
									问题解决方案<span class="flower">*</span>：
								</label>
								<div class="col-sm-6">
									<div class="row">
										<script type="text/plain" id="container" name="content">${data.answer }</script>
										<script type="text/javascript">
	   									</script>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">
									是否为热点问题 <span class="flower">*</span>：
								</label>
								<c:if test="${data.style==1 }">
									<label class="control-label">是</label>
									<input type="radio" class="control-label" id="" name="style" value="1" checked="true" >
									<label class="control-label">否</label>
									<input type="radio" class="control-label" id="" name="style" value="0" >
								</c:if>
								<c:if test="${data.style==0 }">
									<label class="control-label">是</label>
									<input type="radio" class="control-label" id="" name="style" value="1"  >
									<label class="control-label">否</label>
									<input type="radio" class="control-label" id="" name="style" value="0" checked="true">
								</c:if>
							</div>
						</c:if> 

						<!-- 支付问题 -->
						<c:if test="${data.type==1 }">
							<div class="form-group">
								<label class="col-sm-3 control-label">
									选择支付问题类别 <span class="flower">*</span>：
								</label> 
								<c:if test="${data.status==1 }">
									<label class="control-label"> 支付相关问题 </label> 
									<input type="radio" class="control-label" id="" name="status" value="1" checked="true"> 
									&nbsp;&nbsp;&nbsp;&nbsp;
									<label class="control-label"> 积分卡券使用 </label> 
									<input type="radio" class="control-label" id="" name="status" value="2"> 
									&nbsp;&nbsp;&nbsp;&nbsp;
									<label class="control-label"> 支付异常 </label> 
									<input type="radio" class="control-label" id="" name="status" value="3">
								</c:if>
								<c:if test="${data.status==2 }">
									<label class="control-label"> 支付相关问题 </label> 
									<input type="radio" class="control-label" id="" name="status" value="1"> 
									&nbsp;&nbsp;&nbsp;&nbsp;
									<label class="control-label"> 积分卡券使用 </label> 
									<input type="radio" class="control-label" id="" name="status" value="2" checked="true"> 
									&nbsp;&nbsp;&nbsp;&nbsp;
									<label class="control-label"> 支付异常 </label> 
									<input type="radio" class="control-label" id="" name="status" value="3">
								</c:if>
								<c:if test="${data.status==3 }">
									<label class="control-label"> 支付相关问题 </label> 
									<input type="radio" class="control-label" id="" name="status" value="1"> 
									&nbsp;&nbsp;&nbsp;&nbsp;
									<label class="control-label"> 积分卡券使用 </label> 
									<input type="radio" class="control-label" id="" name="status" value="2"> 
									&nbsp;&nbsp;&nbsp;&nbsp;
									<label class="control-label"> 支付异常 </label> 
									<input type="radio" class="control-label" id="" name="status" value="3" checked="true">
								</c:if>
							</div>
							<div class="hidden">
									<input type="radio" class="control-label" id="" name="type" value="1" checked="true"> 
									<input type="hidden" id="id" name="id" value="${data.id}"/>
									<input type="hidden" id="updatedTime" name="updatedTime" value="${data.updatedTime}"/>
								</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 
									支付问题 <span class="flower">*</span>：
								</label>
								<div class="col-sm-6">
									<input type="text" id="question" class="form-control" value="${data.question }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">解决方案<span
									class="flower">*</span>：
								</label>
								<div class="col-sm-6">
									<div class="row">
										<script id="container" name="content" type="text/plain">${data.answer }</script>
										<script type="text/javascript">
										</script>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 是否为热点问题 <span
									class="flower">*</span>：
								</label> 
								<c:if test="${data.style==1 }">
									<label class="control-label">是</label>
									<input type="radio" class="control-label" id="" name="style" value="1" checked="true" >
									<label class="control-label">否</label>
									<input type="radio" class="control-label" id="" name="style" value="0" >
								</c:if>
								<c:if test="${data.style==0 }">
									<label class="control-label">是</label>
									<input type="radio" class="control-label" id="" name="style" value="1"  >
									<label class="control-label">否</label>
									<input type="radio" class="control-label" id="" name="style" value="0" checked="true">
								</c:if>
							</div>
						</c:if>
						
						<!-- 物流与售后 -->
						<c:if test="${data.type==2 }">
							<div class="form-group">
								<label class="col-sm-3 control-label">
									选择物流或售后 <span class="flower">*</span>：
								</label> 
								<c:if test="${data.status==1 }">
									<label class="control-label"> 物流配送 </label> 
									<input type="radio" class="control-label" id="" name="status" value="1" checked="true"> 
									&nbsp;&nbsp;&nbsp;&nbsp;
									<label class="control-label"> 售后咨询 </label> 
									<input type="radio" class="control-label" id="" name="status" value="2"> 
								</c:if>
								<c:if test="${data.status==2 }">
									<label class="control-label"> 物流配送 </label> 
									<input type="radio" class="control-label" id="" name="status" value="1" > 
									&nbsp;&nbsp;&nbsp;&nbsp;
									<label class="control-label"> 售后咨询 </label> 
									<input type="radio" class="control-label" id="" name="status" value="2" checked="true"> 
								</c:if>
							</div>
							<div class="hidden">
									<input type="radio" class="control-label" id="" name="type" value="2" checked="true"> 
									<input type="hidden" id="id" name="id" value="${data.id}"/>
									<input type="hidden" id="updatedTime" name="updatedTime" value="${data.updatedTime}"/>
								</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 
									支付问题 <span class="flower">*</span>：
								</label>
								<div class="col-sm-6">
									<input type="text" id="question" class="form-control" value="${data.question }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">解决方案<span
									class="flower">*</span>：
								</label>
								<div class="col-sm-6">
									<div class="row">
										<script id="container" name="content" type="text/plain">${data.answer }</script>
										<script type="text/javascript">
										</script>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 是否为热点问题 <span
									class="flower">*</span>：
								</label> 
								<c:if test="${data.style==1 }">
									<label class="control-label">是</label>
									<input type="radio" class="control-label" id="" name="style" value="1" checked="true" >
									<label class="control-label">否</label>
									<input type="radio" class="control-label" id="" name="style" value="0" >
								</c:if>
								<c:if test="${data.style==0 }">
									<label class="control-label">是</label>
									<input type="radio" class="control-label" id="" name="style" value="1"  >
									<label class="control-label">否</label>
									<input type="radio" class="control-label" id="" name="style" value="0" checked="true">
								</c:if>
							</div>
						</c:if>
						
						<!-- 其他问题 -->
						<c:if test="${data.type==3 }">
							<div class="form-group">
								<label class="col-sm-3 control-label">其他问题<span
									class="flower">*</span>：
								</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" id="question" name="question" value="${data.question }">
								</div>
								<div class="hidden">
									<input type="radio" class="control-label" id="" name="type" value="3" checked="true"> 
									<input type="radio" class="control-label" id="" name="status" value="1" checked="true"> 
									<input type="hidden" id="id" name="id" value="${data.id}"/>
									<input type="hidden" id="updatedTime" name="updatedTime" value="${data.updatedTime}"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">回答 <span
									class="flower">*</span>：
								</label>
								<div class="col-sm-6">
									<div class="row">
										<script id="container" name="content" type="text/plain">${data.answer }</script>
										<script type="text/javascript">
	   									</script>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 是否为热点问题 <span
									class="flower">*</span>：
								</label> 
								<c:if test="${data.style==1 }">
									<label class="control-label">是</label>
									<input type="radio" class="control-label" id="" name="style" value="1" checked="true" >
									<label class="control-label">否</label>
									<input type="radio" class="control-label" id="" name="style" value="0" >
								</c:if>
								<c:if test="${data.style==0 }">
									<label class="control-label">是</label>
									<input type="radio" class="control-label" id="" name="style" value="1"  >
									<label class="control-label">否</label>
									<input type="radio" class="control-label" id="" name="style" value="0" checked="true">
								</c:if>
							</div>
						</c:if>
						
						<!-- 提交按钮 -->
						<div class="form-group">
							<label class="col-sm-3 control-label"></label>
							<div class="col-sm-6">
								<button class="btn btn-primary" type="button" id="publish" onclick="update(id)" href="javascript:void(0);">提交</button>
								<a class="btn btn-primary" id="" href="javascript:history.go(-1)">取消</a>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>