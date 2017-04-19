<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="blog-post">
	<div class="post-item">
		<header class="panel-heading b-l-l-none b-t-l-none b-b-l-1 b-r-l-none b-s-s">
			<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadPersonal();"><fmt:message key="tiles.views.admin.personal.head.personalInfo"/></a>
			<a href="javascript:void(0);" class="btn btn-s-sm btn-primary" onclick="javascript:loadPassword();"><fmt:message key="tiles.views.admin.personal.head.password"/></a>
		</header>
	</div>
<div class="row">
		<div class="col-sm-12">
			<section class="panel panel-default">
				<div class="panel-body">
					<form id="modifyFrm" class="bs-example form-horizontal">
						<input type="hidden" class="m_id" name="id" value="${personInfo.id}">
						<input type="hidden" class="m_id" name="token" value="${token}">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							<div class="row">
								<div class="form-group">
									<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.personal.basic.username"/></label>
									<div class="col-lg-8">
										<input type="text" name="username"
											class="form-control" value="${personInfo.username}" disabled>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group">
									<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.personal.password.password"/><span class="text-danger">*</span></label>
									<div class="col-lg-8">
										<input type="password" id ="old_password" name="password" autocomplete="off"
											class="form-control" value="" data-parsley-required="true" >
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group">
									<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.personal.password.newPass"/><span class="text-danger">*</span></label>
									<div class="col-lg-8">
										<input type="password" id="password_confirm" name="newPassword" autocomplete="off"
											class="form-control" data-parsley-required="true" >
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group">
									<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.personal.password.rePass"/><span class="text-danger">*</span></label>
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
										class="btn btn-s-md btn-primary pull-right btn-modify-user" onclick="modifySubmit();" value='<fmt:message key="tiles.views.admin.personal.basic.update"/>'>
								</div>
							</div>	
					</form>
				</div>
			</section>
		</div>
	</div>
</div>

