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
			<a href="javascript:void(0);" class="btn btn-s-sm btn-primary" onclick="javascript:loadPersonal();"><fmt:message key="tiles.views.admin.personal.head.personalInfo"/></a>
			<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadPassword();"><fmt:message key="tiles.views.admin.personal.head.password"/></a>
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
									<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.personal.basic.name"/><span class="text-danger">*</span></label>
									<div class="col-lg-8">
										<input type="text" name="name"
											class="form-control" value="${personInfo.name}" data-parsley-required="true" data-parsley-trigger="blur" data-parsley-maxlength="20">
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group">
									<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.personal.basic.phone"/></label>
									<div class="col-lg-8">
										<input type="text" name="phone" class="form-control"  value="${personInfo.phone}">
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group">
									<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.personal.basic.email"/><span class="text-danger">*</span></label>
									<div class="col-lg-8">
										<input type="email" name="email" data-parsley-type="email" data-parsley-required="true" data-parsley-pattern="/^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/" class="form-control m_email" value="${personInfo.email}">
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group">
									<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.personal.basic.mark"/></label>
									<div class="col-lg-8">
										<input type="text" class="form-control m_mark" name="mark"
											value="${personInfo.mark}">
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-4 col-sm-offset-5 col-md-4 col-md-offset-5 ">
									<input type="button"
										class="btn btn-s-md btn-primary btn-modify-user" onclick="modifySubmit();" value='<fmt:message key="tiles.views.admin.personal.basic.update"/>'>
								</div>
							</div>
					</form>
				</div>
			</section>
		</div>
	</div>
</div>

