<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="blog-post">
	<div class="row">
		<div class="col-sm-12">
			<section class="panel panel-default">
				<div class="panel-body">
					<form id="modifyFrm" class="bs-example form-horizontal">
						<input type="hidden" class="m_id" name="id" value="${personInfo.id}">
							<div class="row">
								<div class="form-group">
									<label class="col-lg-2 control-label">登录帐号：</label>
									<div class="col-lg-8">
										<input type="text" name="username"
											class="form-control" value="${personInfo.accountName}" disabled>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group">
									<label class="col-lg-2 control-label">原密码：<span class="text-danger">*</span></label>
									<div class="col-lg-8">
										<input type="password" id ="old_password" name="password" autocomplete="off"
											class="form-control" value="" data-parsley-required="true" >
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group">
									<label class="col-lg-2 control-label">新密码：<span class="text-danger">*</span></label>
									<div class="col-lg-8">
										<input type="password" id="password_confirm" name="newPassword" autocomplete="off"
											class="form-control" data-parsley-required="true" >
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group">
									<label class="col-lg-2 control-label">确认密码：<span class="text-danger">*</span></label>
									<div class="col-lg-8">
										<input type="password" name="reNewPassword" id="password_reconfirm" autocomplete="off"
											class="form-control" data-parsley-required="true" 
											data-parsley-equalto="#password_confirm">
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-4 col-sm-offset-5 col-md-4 col-md-offset-2">
									<input type="button"
										class="btn btn-s-md btn-primary pull-right btn-modify-user" onclick="modifySubmit();" value='修改'>
								</div>
							</div>	
					</form>
				</div>
			</section>
		</div>
	</div>
</div>

