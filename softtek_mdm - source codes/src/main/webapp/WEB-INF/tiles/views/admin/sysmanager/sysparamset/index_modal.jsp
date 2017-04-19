<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- Modal start-->
<div class="modal fade" id="logoModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header b-b-l-none">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-sm-12">
						<section class="panel panel-default">
							<header class="panel-heading font-bold"><fmt:message key="jsp.admin.sysmanager.sysparamset.modal.header"/></header>
							<div class="panel-body">
								<spring:url value="/institution/vtl/save" var="modal_saveUrl" />
									<spring:url
										value="/resources/images/virtualMemberexcelmode.png"
										var="virtualMemberexcelmode"/>
										<label class="col-lg-12"><fmt:message key="jsp.admin.sysmanager.sysparamset.modal.label"/></label>
											 <spring:url value="/institution/user/importusers" var="importUsers" />
								<form action="${importUsers}" method="post" id="importUsers"
									enctype="multipart/form-data">
									<div class="col-sm-12 uploadvirmodel">
										<div class="col-sm-4">
											<a href="javascript:void();" class="a-upload"> <input
												type="file" name="file" id="file"><fmt:message key="tiles.views.user.index.table.excel.upload"/>
											</a> <input type="hidden" name="${_csrf.parameterName}"
												value="${_csrf.token}" />
										</div>
										<div class="col-sm-8">
											<input type="text" class="showFileName" disabled value="">
										</div>
									</div>
								</form>
										<div class="col-sm-12"><a class="fileerrorTip" style="color:red"></a></div>
										 <div class="col-lg-offset-2 col-lg-10">
										</div> 
							</div>
						</section>
						<div align="center">
						<input type="button" class="btn btn-md btn-primary"
											onclick="changeLogo()"	value="<fmt:message key='jsp.admin.sysmanager.sysparamset.modal.update'/>"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--Modal end-->