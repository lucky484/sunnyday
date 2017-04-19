<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spring:url value="/institution/org/updateStructure" var="updateStructure" />
<div class="blog-post">
	<div class="post-item">
		<header
			class="panel-heading b-l-l-none b-t-l-none b-b-l-1 b-r-l-none b-s-s">
		<c:if test="${menu[3].isshow==-1}">
		    <spring:url value="/institution/org" var="orgUrl" />
			<a href="${orgUrl}" class="btn btn-s-sm btn-primary"><fmt:message key="tiles.institution.org.department.manager" /></a> 
		</c:if>
		<c:if test="${menu[4].isshow==-1}">
			<spring:url value="/institution/dptPolicy" var="dptPolicy" />
			<a href="${dptPolicy}" class="btn btn-s-sm"><fmt:message key="tiles.institution.org.department.policy" /></a>
		</c:if>
		</header>
		<section class="wrapper-md ">
			<div class="row">
				<div class="scrollable aside-md col-sm-3">
					<div class="btn-toolbar">
						<button class="btn btn-default btn-sm btn-bg b-none text-info-ipt"
							id="insertOrg">
							<i class="fa fa-plus"></i> <span><fmt:message key="tiles.institution.org.department.insert" /></span>
						</button>
						<input type="hidden" id="departmentId" name="departmentId" value="${departmentId}"/>
						<button
							class="btn btn-default btn-sm btn-bg b-none text-info-ipt pull-right m-r hidden btn-move" id="move">
							<i class="glyphicon glyphicon-transfer"></i> <span><fmt:message key="tiles.institution.org.department.move" /></span>
						</button>
					</div>
					<div id="tree"></div>
				</div>
				<div class="col-sm-9">
					<section class="panel panel-default">
						<header class="panel-heading font-bold">
							<fmt:message key="tiles.institution.org.department.div.name" />
							<button id="btn-del"
								class="btn btn-sm btn-danger pull-right m-t-n-xs hidden"><fmt:message key="tiles.institution.org.department.delete" /></button>
						</header>
						<div class="panel-body">
							<div class="d_base">
								<label class="font-bold"><fmt:message key="tiles.institution.org.department.name" />:</label> <span class="d_name"></span><br>
								<label class="font-bold"><fmt:message key="tiles.institution.org.department.parent.name" />:</label> <span class="d_master"></span><br>
								<label class="font-bold"><fmt:message key="tiles.institution.org.department.mark" />:</label> <span class="d_memo"></span>
							</div>
							<form id="ste_modify_frm"
								class="bs-example form-horizontal hidden" method="post"
								action="${updateStructure}"
								data-parsley-validate>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<input type="hidden" id="ste_modify_frm_id" name="id" value="">
								<input type="hidden" id="ste_modify_frm_name_hide" name="nameHidden" value="">
								<div class="form-group">
									<label class="col-lg-3 control-label"><fmt:message key="tiles.institution.org.department.name" /><span style="color:red">*</span></label>
									<div class="col-lg-9">
										<input id="ste_modify_frm_name" type="text" name="name"
											class="form-control" data-parsley-required="true"
											data-parsley-remote data-parsley-trigger="focusout"
											data-parsley-remote-validator="checknameUpdate" data-parsley-maxlength="10"
											data-parsley-remote-message="<fmt:message key="parsley.department.name.exists"/>"
											data-parsley-maxlength-message="<fmt:message key="parsley.department.name.length" />" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-lg-3 control-label"><fmt:message key="tiles.institution.org.department.parent.name" /></label>
									<div class="col-lg-9">
										<label class="control-label" id="ste_modify_frm_sub"></label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-lg-3 control-label"><fmt:message key="tiles.institution.org.department.mark" /></label>
									<div class="col-lg-9">
										<textarea id="ste_modify_frm_memo" name="mark"
											class="form-control" style="resize: none;"></textarea>
									</div>
								</div>
								<div class="form-group">
									<label class="col-lg-3 control-label"><fmt:message key="tiles.institution.org.department.email" /></label>
									<div class="col-lg-9">
										<input type="email" id="ste_modify_frm_email" name="email"
											class="form-control" data-parsley-type="email"
											data-parsley-trigger="focusout" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-lg-3 control-label"><fmt:message key="tiles.institution.org.department.createTime" /></label>
									<div class="col-lg-9">
										<label class="control-label" id="ste_modify_frm_time"></label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-lg-3 control-label"><fmt:message key="tiles.institution.org.department.createType" /></label>
									<div class="col-lg-9">
										<label class="control-label" id="ste_modify_frm_createtype"><fmt:message key="tiles.institution.org.department.createType.value" /></label>
									</div>
								</div>
								<div class="form-group">
									<div class="col-lg-offset-2 col-lg-10">
										<input type="button" class="btn btn-md btn-primary" id="updateOrg"
											value="<fmt:message key="tiles.institution.org.department.update" />" />
									</div> 
								</div>
							</form>
						</div>
					</section>
				</div>
			</div>
		</section>
	</div>
</div>
