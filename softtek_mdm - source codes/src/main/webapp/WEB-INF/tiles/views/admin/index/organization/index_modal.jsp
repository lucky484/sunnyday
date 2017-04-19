<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- 添加机构的modal -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header b-b-l-none">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body" style="max-height: 800px;">
				<div class="row">
					<div class="col-sm-12">
						<section class="panel panel-default">
							<header class="panel-heading font-bold">
								<fmt:message key="tiles.views.admin.index.organization.modal.add.header"/><span class="text-warning pull-right">(<fmt:message key="tiles.views.admin.index.manager.modal.add.tip1"/><span
									class="text-danger">*</span><fmt:message key="tiles.views.admin.index.manager.modal.add.tip2"/>)
								</span>
							</header>
							<div class="panel-body">
								<%-- <spring:url value="/institution/user/save" var="saveUrl" /> --%>
								<form id="addFrm" class="bs-example form-horizontal">
									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" />
									<h5>
										<span class="badge bg-primary"><fmt:message key="tiles.views.admin.index.manager.modal.add.basic"/></span>
									</h5>
									<!-- <input type="hidden" id="defaultSelectNode" name="defaultSelectNode" value="0"> -->
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.organization.orgType"/><span
											class="text-danger">*</span></label>
										<div class="col-lg-8">
											<input type="text" name="orgType" id="orgType"
												class="form-control add-user-name" autocomplete="off"
												data-parsley-required="true" data-parsley-maxlength="20"
												data-parsley-remote
												data-parsley-remote-validator="existsValidate"
												data-parsley-trigger="focusout"
												data-parsley-remote-message="<fmt:message key="tiles.views.admin.index.organization.modal.add.orgType.parsley"/>">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.organization.orgName"/><span
											class="text-danger">*</span></label>
										<div class="col-lg-8">
											<input type="text" name="name" class="form-control" id = "name"
												autocomplete="off" data-parsley-required="true"
												data-parsley-maxlength="20" 
												data-parsley-remote
												data-parsley-remote-validator="existsNameValidate"
												data-parsley-trigger="focusout" 
												data-parsley-remote-message="<fmt:message key="tiles.views.admin.index.organization.modal.add.orgName.parsley"/>">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.organization.table.licenseCount"/><span
											class="text-danger">*</span></label>
										<div class="col-lg-8">
											<input type="text" name="licenseCount" id ="licenseCount" class="form-control"
												autocomplete="off" data-parsley-required="true" 
												data-parsley-pattern="^([1-9]\d*)$"
												data-parsley-maxlength="20" data-parsley-trigger="blur">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"></label>
										<div class="col-lg-8">
											<fmt:message key="jsp.admin.index.organization.remainder.label"/><span id="remainder"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.organization.modal.add.mark"/></label>
										<div class="col-lg-8">
											<textarea id="mark" name="mark" class="form-control mark" autocomplete="off"></textarea>
										</div>
									</div>
									<h5>
										<span class="badge bg-primary"><fmt:message key="tiles.views.admin.index.organization.modal.add.managerLists"/></span>
									</h5>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.organization.modal.add.manager"/></label>
										<div class="col-lg-10 row"
											style="max-height: 300px;">
											<input type="hidden" value="" name="vtls">
												<section class="panel panel-default col-lg-9 panel-left"
													style="height: 150px; max-height: 150px; overflow: scroll;">
														<div class="checkbox i-checks" id="managerLists">
															
														</div>
												</section>
										</div>
									</div>
									<!-- <div class="form-group">
										<input type="button" class="btn btn-s-md btn-primary pull-right" value="新增" id="add">&nbsp;&nbsp;
										<input type="button" class="btn btn-s-md btn-primary pull-right" value="取消" id="cancel">
									</div> -->
									<div class="row">
										<div class="col-sm-4 col-sm-offset-4 col-md-4 col-md-offset-4">
											<input type="button" class="btn btn-s-md btn-primary " value="<fmt:message key="tiles.views.admin.index.manager.modal.add.add"/>" id="add">&nbsp;&nbsp;
											<input type="button" class="btn btn-s-md btn-primary pull-right" value="<fmt:message key="tiles.views.admin.index.manager.modal.add.cancel"/>" id="cancelAdd">
										</div>
									</div>	
								</form>
							</div>
						</section>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- 修改机构的modal -->
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<input type="hidden" id="org_id" value="">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header b-b-l-none">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body" style="max-height: 800px; overflow: scroll;">
				<div class="row">
					<div class="col-sm-12">
						<section class="panel panel-default">
							<header class="panel-heading font-bold">
								<fmt:message key="tiles.views.admin.index.organization.modal.update.header"/><span class="text-warning pull-right">(<fmt:message key="tiles.views.admin.index.manager.modal.add.tip1"/><span
									class="text-danger">*</span><fmt:message key="tiles.views.admin.index.manager.modal.add.tip2"/>)
								</span>
							</header>
							<div class="panel-body">
								<%-- <spring:url value="/institution/user/save" var="saveUrl" /> --%>
								<form id="updateFrm" class="bs-example form-horizontal">
									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" />
									<input type="hidden" name="id" id="orgId">
									<h5>
										<span class="badge bg-primary"><fmt:message key="tiles.views.admin.index.manager.modal.add.basic"/></span>
									</h5>
									<!-- <input type="hidden" id="defaultSelectNode" name="defaultSelectNode" value="0"> -->
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.organization.table.orgType"/><span
											class="text-danger">*</span></label>
										<div class="col-lg-8">
											<input type="text" name="orgType" id="editOrgType"
												class="form-control add-user-name" autocomplete="off"
												data-parsley-required="true" data-parsley-maxlength="20"
												data-parsley-remote
												data-parsley-remote-validator="existsValidate"
												data-parsley-trigger="focusout"
												data-parsley-excluded=true
												data-parsley-remote-message="<fmt:message key="tiles.views.admin.index.organization.modal.add.orgType.parsley"/>">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.organization.table.orgName"/><span
											class="text-danger">*</span></label>
										<div class="col-lg-8">
											<input type="text" name="name" class="form-control" id="editName"
												autocomplete="off" data-parsley-required="true" 
												data-parsley-maxlength="20" 
												data-parsley-remote
												data-parsley-remote-validator="existsNameValidate"
												data-parsley-trigger="focusout"
												data-parsley-excluded=true
												data-parsley-remote-message="<fmt:message key="tiles.views.admin.index.organization.modal.add.orgName.parsley"/>">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.organization.table.licenseCount"/><span
											class="text-danger">*</span></label>
										<div class="col-lg-8">
											<input type="text" name="licenseCount" id ="editLicenseCount" class="form-control"
												autocomplete="off" data-parsley-required="true"
												data-parsley-pattern="^([1-9]\d*)$"
												data-parsley-maxlength="20" data-parsley-trigger="focusout">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"></label>
										<div class="col-lg-8">
											<fmt:message key="tiles.views.admin.index.organization.modal.update.useLicense"/>：<span id="useLicense"></span>，<fmt:message key="tiles.views.admin.index.organization.modal.update.remainLicense"/>：<span id="editRemainder"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.organization.modal.add.mark"/></label>
										<div class="col-lg-8">
											<textarea id="editMark" name="mark" class="form-control mark" autocomplete="off"></textarea>
										</div>
									</div>
									<h5>
										<span class="badge bg-primary"><fmt:message key="tiles.views.admin.index.organization.modal.add.managerLists"/></span>
									</h5>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.organization.modal.add.manager"/></label>
										<div class="col-lg-10 row"
											style="max-height: 300px;">
											<input type="hidden" value="" name="vtls">
												<section class="panel panel-default col-lg-9 panel-left"
													style="height: 150px; max-height: 150px; overflow: scroll;">
														<div class="checkbox i-checks" id="editManagerLists">
															
														</div>
												</section>
										</div>
									</div>
									<!-- <div class="form-group">
										<input type="button" class="btn btn-s-md btn-primary pull-right" value="新增" id="add">&nbsp;&nbsp;
										<input type="button" class="btn btn-s-md btn-primary pull-right" value="取消" id="cancel">
									</div> -->
									<div class="row">
										<div class="col-sm-4 col-sm-offset-4 col-md-4 col-md-offset-4">
											<input type="button" class="btn btn-s-md btn-primary " value="<fmt:message key="tiles.views.admin.index.manager.model.success"/>" id="update">&nbsp;&nbsp;
											<input type="button" class="btn btn-s-md btn-primary pull-right" value="<fmt:message key="tiles.views.admin.index.manager.modal.add.cancel"/>" id="cancelUpdate">
										</div>
									</div>	
								</form>
							</div>
						</section>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 查看 机构的modal-->
<div class="modal fade" id="viewModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header b-b-l-none">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body" style="max-height: 800px; overflow: scroll;">
				<div class="row">
					<div class="col-sm-12">
						<section class="panel panel-default">
							<header class="panel-heading font-bold">
								<fmt:message key="tiles.views.admin.index.organization.modal.view.header"/><span class="text-warning pull-right">
								</span>
							</header>
							<div class="panel-body">
								<%-- <spring:url value="/institution/user/save" var="saveUrl" /> --%>
								<form id="viewFrm" class="bs-example form-horizontal">
									<h5>
										<span class="badge bg-primary"><fmt:message key="tiles.views.admin.index.manager.modal.add.basic"/></span>
									</h5>
									<!-- <input type="hidden" id="defaultSelectNode" name="defaultSelectNode" value="0"> -->
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.organization.orgType"/></label>
										<div class="col-lg-8 span_sep">
											<span name="orgType" id="viewOrgType"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.organization.orgName"/></label>
										<div class="col-lg-8 span_sep">
											<span name="name" id="vieName"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.organization.modal.view.create_time"/></label>
										<div class="col-lg-8 span_sep">
											<span name="createTime" id="viewCreateTime"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.organization.modal.view.totalUsers"/></label>
										<div class="col-lg-8 span_sep">
											<span name="totalUser" id="totalUsers"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.organization.modal.view.totalDevices"/></label>
										<div class="col-lg-8 span_sep">
											<span name="totalDevice" id="totalDevices"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.organization.modal.view.viewRemainLicense"/></label>
										<div class="col-lg-8 span_sep">
											<span name="remainLicense" id="viewRemainLicense"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.organization.modal.view.useLicenses"/></label>
										<div class="col-lg-8 span_sep">
											<span name="useLicense" id="useLicenses"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.organization.modal.view.status"/></label>
										<div class="col-lg-8 span_sep">
											<span name="status" id="viewStatus"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.organization.modal.view.mark"/></label>
										<div class="col-lg-8 span_sep">
											<span name="mark" id="viewMark"></span>
										</div>
									</div>
									<div style="margin-top:20px;">
										
									</div>
									<h5>
										<span class="badge bg-primary"><fmt:message key="tiles.views.admin.index.organization.modal.add.managerLists"/></span>
									</h5>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.organization.modal.add.manager"/></label>
										<div class="col-lg-10 row"
											style="max-height: 300px;">
												<section class="panel panel-default col-lg-9 panel-left view"
													style="height: 150px; max-height: 150px; overflow: scroll;">
														<div id="viewManagerLists">
															
														</div>
												</section>
										</div>
									</div>
									<!-- <div class="form-group">
										<input type="button" class="btn btn-s-md btn-primary pull-right" value="新增" id="add">&nbsp;&nbsp;
										<input type="button" class="btn btn-s-md btn-primary pull-right" value="取消" id="cancel">
									</div> -->
									<div class="row">
										<div class="col-sm-3 col-sm-offset-3 col-md-4 col-md-offset-4">
											<input type="button" class="btn btn-s-md btn-primary pull-right" value="<fmt:message key="tiles.views.admin.index.manager.modal.view.close"/>" id="cancelView">
										</div>
									</div>	
								</form>
							</div>
						</section>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 弹出框 -->
<div class="modal fade" id="openTips" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.views.admin.index.manager.model.delTipTitle"/></h4>
			</div>
			<div class="modal-body">
			    <h3 class="text-danger" id="tips"></h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default" id="successTitle"><fmt:message key="tiles.views.admin.index.manager.model.success"/></button>
		      </div> 
		</div>
	</div>
</div>

<div class="modal fade" id="lockTips" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.views.admin.index.manager.model.delTipTitle"/></h4>
			</div>
			<div class="modal-body">
			    <h3 class="text-danger" id="locktips"></h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default" id="falseLock"><fmt:message key="tiles.views.admin.index.manager.model.success"/></button>
		      </div> 
		</div>
	</div>
</div>

<div class="modal fade" id="delOrganization" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog" role="document">
		<input type="hidden" value="" id="orgId">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">
						<fmt:message key="tiles.views.admin.index.manager.model.delTipTitle"/>
					</h4>
				</div>
				<div class="modal-body">
					<h3 class="text-danger del-text" id="organizationTip"><fmt:message key="tiles.views.admin.index.organization.modal.delOrg"/></h3>
				</div>
				<div class="modal-footer">
					<a href="javascript:void(0);" onclick="deleteOrganization();" class="btn btn-success"><fmt:message key="tiles.views.admin.index.manager.model.success"/></a>
	     			<a href="javascript:void(0);" class="btn btn-success" data-dismiss="modal"><fmt:message key="tiles.views.admin.index.manager.modal.add.cancel"/></a>
				</div>
		</div>
	</div>
</div>

<div class="modal fade" id="lockOrganizationModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog" role="document">
		<input type="hidden" value="" id="lockOrgId">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">
						<fmt:message key="tiles.views.admin.index.manager.model.delTipTitle"/>
					</h4>
				</div>
				<div class="modal-body">
					<h3 class="text-danger del-text" id="organizationTip"><fmt:message key="tiles.views.admin.index.organization.modal.lockOrg"/></h3>
				</div>
				<div class="modal-footer">
					<a href="javascript:void(0);" onclick="lockOrganizationBtn();" class="btn btn-success"><fmt:message key="tiles.views.admin.index.manager.model.success"/></a>
	     			<a href="javascript:void(0);" class="btn btn-success" data-dismiss="modal"><fmt:message key="tiles.views.admin.index.manager.modal.add.cancel"/></a>
				</div>
		</div>
	</div>
</div>

<div class="modal fade" id="unLockOrganizationModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog" role="document">
		<input type="hidden" value="" id="unLockOrgId">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">
						<fmt:message key="tiles.views.admin.index.manager.model.delTipTitle"/>
					</h4>
				</div>
				<div class="modal-body">
					<h3 class="text-danger del-text" id="organizationTip"><fmt:message key="tiles.views.admin.index.organization.modal.unlockOrg"/></h3>
				</div>
				<div class="modal-footer">
					<a href="javascript:void(0);" onclick="unLockOrganizationBtn();" class="btn btn-success"><fmt:message key="tiles.views.admin.index.manager.model.success"/></a>
	     			<a href="javascript:void(0);" class="btn btn-success" data-dismiss="modal"><fmt:message key="tiles.views.admin.index.manager.modal.add.cancel"/></a>
				</div>
		</div>
	</div>
</div>