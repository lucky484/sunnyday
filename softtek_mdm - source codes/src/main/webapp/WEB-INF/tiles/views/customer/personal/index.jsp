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
			<c:if test="${fn:length(deviceBasicInfoList) > 0}">
				<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadIndex();"><fmt:message key="tiles.views.customer.index.head.deviceinfo"/></a>
				<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadAppList();"><fmt:message key="tiles.views.customer.index.head.applist"/></a>
				<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadposition();"><fmt:message key="tiles.views.customer.index.head.position"/></a>
				<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadCompliant();"><fmt:message key="tiles.views.customer.index.head.compliant"/></a>
				<!-- <a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadConfig();">配置描述文件</a> -->
				<a href="javascript:void(0);" class="btn btn-s-sm btn-primary" onclick="javascript:loadPersonal();"><fmt:message key="tiles.views.customer.index.head.personalinfo"/></a>
				<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadPassword();"><fmt:message key="tiles.views.customer.index.head.updpassword"/></a>
				<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadDeviceLog();"><fmt:message key="tiles.views.customer.index.head.devicelog"/></a>
			</c:if>
			<c:if test="${fn:length(deviceBasicInfoList) <= 0}">
				<a href="javascript:void(0);" class="btn btn-s-sm btn-primary" onclick="javascript:loadPersonal();"><fmt:message key="tiles.views.customer.index.head.personalinfo"/></a>
				<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadPassword();"><fmt:message key="tiles.views.customer.index.head.updpassword"/></a>
			</c:if>
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
									<label class="col-lg-2 control-label"><fmt:message key="tiles.views.customer.personal.index.username"/></label>
									<div class="col-lg-8">
										<input type="text" name="username"
											class="form-control" value="${personInfo.username}" disabled>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group">
									<label class="col-lg-2 control-label"><fmt:message key="tiles.views.customer.personal.index.realname"/><span class="text-danger">*</span></label>
									<div class="col-lg-8">
										<input type="text" name="realname"
											class="form-control" value="${personInfo.realname}" data-parsley-required="true" data-parsley-trigger="blur" data-parsley-maxlength="20">
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group">
									<label class="col-lg-2 control-label"><fmt:message key="tiles.views.customer.personal.index.phone"/></label>
									<div class="col-lg-8">
										<input type="text" name="phone" class="form-control"  value="${personInfo.phone}">
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group">
									<label class="col-lg-2 control-label"><fmt:message key="tiles.views.customer.personal.index.email"/></label>
									<div class="col-lg-8">
										<input type="email" name="email" data-parsley-type="email" data-parsley-pattern="/^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/" class="form-control m_email" value="${personInfo.email}">
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group">
									<label class="col-lg-2 control-label"><fmt:message key="tiles.views.customer.personal.index.mark"/></label>
									<div class="col-lg-8">
										<input type="text" class="form-control m_mark" name="mark"
											value="${personInfo.mark}">
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-4 col-sm-offset-5 col-md-4 col-md-offset-5 ">
									<input type="button"
										class="btn btn-s-md btn-primary btn-modify-user" onclick="modifySubmit();" value='<fmt:message key="tiles.views.customer.personal.index.updbtn"/>'>
								</div>
							</div>
					</form>
				</div>
			</section>
		</div>
	</div>
</div>

