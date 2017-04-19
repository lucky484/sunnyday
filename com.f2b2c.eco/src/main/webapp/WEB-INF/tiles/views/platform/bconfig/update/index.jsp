<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
 <spring:url value="/resources/market/js/plugins/ueditor1_4_3_3/ueditor.config.js" var="ueditorconfigJsUrl"/>
 <spring:url value="/resources/market/js/plugins/ueditor1_4_3_3/ueditor.config.js" var="ueditorconfigJsUrl"/>
 <script type="text/javascript" src="${ueditorconfigJsUrl}"></script>
    <!-- 编辑器源码文件 -->
    <spring:url value="/resources/market/js/plugins/ueditor1_4_3_3/ueditor.all.js" var="ueditorAllJsUrl"/>
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
                	<!-- <div class="col-sm-12">
	                	<a class="btn btn-primary radius" data-title="添加门店" onclick="shop_add();" href="javascript:void(0);">
	                		<i class="fa fa-plus">添加门店</i>
	                	</a>
                	</div> -->
                	 <div class="ibox-title">
                                    <h5>修改常见问题</h5>
                                </div>
                      <div class="ibox-content">
                        <form method="post" class="form-horizontal">
                        	<input type="hidden" value="${problem.id}" id="id" name="id"/>
                            <div class="form-group">
								<div class="col-md-2 control-label">
									<label>问题名称：<span class="text-danger">*</span></label> 
								</div> 
								<div class="col-md-10">
									<input type="text" id="problem" name="problem" class="form-control"
										placeholder="请输入问题名称..." required maxlength="40" value="${problem.problem}">
								</div>
							</div>
                            <div class="form-group">
								<div class="col-md-2 control-label">
									<label>问题回答：<span class="text-danger">*</span></label> 
								</div> 
								<div class="col-md-10">
									<input type="text" id="response" name="response" class="form-control"
										placeholder="请输入问题回答..." required maxlength="40" value="${problem.response}">
								</div>
							</div>
                            <div class="form-group">
                                <label class="col-md-2 control-label"></label>
                                <div class="col-md-10">
                                   <button class="btn btn-primary" type="button" id="publish">修改</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
      </div>
      
  
    
  