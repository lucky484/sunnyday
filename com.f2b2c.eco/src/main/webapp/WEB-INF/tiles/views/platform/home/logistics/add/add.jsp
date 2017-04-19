<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<style>

</style>
<div class="wrapper wrapper-content animated fadeInRight">
	<div class="row">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>
						物流方式 <small> / 添加新的物流方式 </small>
					</h5>
				</div>

				<div class="ibox-content">
					<form id="addForm" class="form-horizontal m-t">
						<div class="form-group">
							<div class="col-md-2 control-label">
								<label>物流方式：<span class="text-danger">*</span></label> 
							</div> 
							<div class="col-md-10">
								<input type="text" name="name" class="form-control" id="name"
									placeholder="请输入物流方式" required="required" maxlength="40" value="${FLogisticsModel.name }">
							</div>
						</div>
						<footer class="row">
							<div class="col-sm-4 col-sm-offset-4 col-md-4 col-md-offset-4">
								 <a class="btn btn-primary radius" id="publish"
								data-title="添加物流方式" onclick="add();"> <i class="fa fa-plus"> 添加物流方式
								</i>
								</a>
							</div>
						</footer>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>