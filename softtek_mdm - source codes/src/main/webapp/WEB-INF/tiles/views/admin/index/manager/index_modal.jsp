<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- 添加机构管理员的modal -->
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
								<fmt:message key="tiles.views.admin.index.manager.modal.add.header"/><span class="text-warning pull-right">(<fmt:message key="tiles.views.admin.index.manager.modal.add.tip1"/><span
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
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.manager.modal.add.username"/><span
											class="text-danger">*</span></label>
										<div class="col-lg-8">
											<input type="text" name="username" id="username"
												class="form-control add-user-name" autocomplete="off"
												data-parsley-required="true" data-parsley-maxlength="20"
												data-parsley-remote
												data-parsley-remote-validator="existsValidate"
												data-parsley-trigger="blur"
												data-parsley-remote-message="<fmt:message key="tiles.views.admin.index.manager.modal.add.username.parsley"/>">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.manager.modal.add.password"/><span
											class="text-danger">*</span></label>
										<div class="col-lg-8">
											<input type="password" id="password_confirm" name="password" autocomplete="off"
												class="form-control" data-parsley-required="true">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.manager.modal.add.rePass"/><span
											class="text-danger">*</span></label>
										<div class="col-lg-8">
											<input type="password" class="form-control" autocomplete="off"
												data-parsley-required="true"
												data-parsley-equalto="#password_confirm">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.manager.modal.add.name"/><span
											class="text-danger">*</span></label>
										<div class="col-lg-8">
											<input type="text" name="name" class="form-control"
												autocomplete="off" data-parsley-required="true"
												data-parsley-maxlength="20">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.manager.modal.add.role"/></label>
										<div class="col-lg-8">
											<select name="roleType" class="form-control input-md pos" id="roleType" >
					             				<option value=""><fmt:message key="tiles.views.admin.index.manager.modal.add.role.name"/></option>
					             			</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.manager.modal.add.phone"/></label>
										<div class="col-lg-8">
											<input type="text" name="phone" class="form-control"
												data-parsley-cellphone autocomplete="off">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.manager.modal.add.email"/><span
											class="text-danger">*</span></label>
										<div class="col-lg-8">
											<input type="email" name="email" class="form-control" data-parsley-required="true"
												autocomplete="off" data-parsley-type="email" 
												data-parsley-pattern="/^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.manager.modal.add.mark"/></label>
										<div class="col-lg-8">
											<textarea id="mark" name="mark" class="form-control mark" autocomplete="off"></textarea>
										</div>
									</div>
									<h5>
										<span class="badge bg-primary"><fmt:message key="tiles.views.admin.index.manager.modal.add.totalOrgLists"/></span>
									</h5>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.manager.org"/><span
											class="text-danger">*</span></label>
										<div class="col-lg-10 row"
											style="max-height: 300px;">
											<input type="hidden" value="" name="vtls">
												<section class="panel panel-default col-lg-9 panel-left"
													style="height: 180px; max-height: 250px; overflow: scroll;">
														<div class="searchicon">
													      	<input type="text" class="form-control searchtext" id="searchOrg" onkeyup="searchOrgList();">
													      	<div class="basic_info basic_line">
																<span class="span_line"></span>
															</div>
													    </div>
														<div class="checkbox i-checks" id="orgList">
															
														</div>
												</section>
										</div>
									</div>
									<footer class="row">
										<div class="col-sm-4 col-sm-offset-4 col-md-4 col-md-offset-4">
											<input type="button" class="btn btn-s-md btn-primary " value="<fmt:message key="tiles.views.admin.index.manager.modal.add.add"/>" id="add">&nbsp;&nbsp;
											<input type="button" class="btn btn-s-md btn-primary pull-right" value="<fmt:message key="tiles.views.admin.index.manager.modal.add.cancel"/>" id="cancel">
										</div>
									</footer>
								</form>
							</div>
						</section>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- 修改机构管理员的modal -->
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog"
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
								<fmt:message key="tiles.views.admin.index.manager.modal.update.header"/><span class="text-warning pull-right">(<fmt:message key="tiles.views.admin.index.manager.modal.add.tip1"/><span
									class="text-danger">*</span><fmt:message key="tiles.views.admin.index.manager.modal.add.tip2"/>)
								</span>
							</header>
							<div class="panel-body">
								<%-- <spring:url value="/institution/user/save" var="saveUrl" /> --%>
								<form id="updateFrm" class="bs-example form-horizontal">
									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" />
									<input type="hidden" name="id" id="managerId">
									<h5>
										<span class="badge bg-primary"><fmt:message key="tiles.views.admin.index.manager.modal.add.basic"/></span>
									</h5>
									<!-- <input type="hidden" id="defaultSelectNode" name="defaultSelectNode" value="0"> -->
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.manager.modal.add.username"/><span
											class="text-danger">*</span></label>
										<div class="col-lg-8">
											<input type="text" name="username" id="editUsername" disabled
												class="form-control add-user-name" autocomplete="off"
												data-parsley-required="true" data-parsley-maxlength="20"
												data-parsley-trigger="blur"
												data-parsley-remote-message="<fmt:message key="tiles.views.admin.index.manager.modal.add.username.parsley"/>">
										</div>
									</div>
									<div class="form-group">
										<button href="#moreless" class="btn btn-sm btn-default pull-right m-r-lg" data-toggle="class:show" onclick="toggleExclued()" id="btn">
			                  				<i class="fa fa-plus text"></i>
			                  				<span class="text"><fmt:message key="tiles.views.admin.index.manager.modal.update.updatePass"/></span>
			                  				<i class="fa fa-minus text-active"></i>
			                  				<span class="text-active"><fmt:message key="tiles.views.admin.index.manager.modal.update.cancelPass"/></span>
			                			</button>
									</div>
									<div id="moreless" class="hide">
										<div class="form-group m_pwd">
											<label class="col-lg-3 control-label"><fmt:message key="tiles.views.admin.index.manager.modal.update.password"/><span class="text-danger">*</span></label>
											<div class="col-lg-7">
												<input type="password" id="pwd_modify_confirm" name="password" autocomplete="off"
													 class="form-control">
											</div>
										</div>
										<div class="form-group m_pwd">
											<label class="col-lg-3 control-label"><fmt:message key="tiles.views.admin.index.manager.modal.update.rePass"/><span class="text-danger">*</span></label>
											<div class="col-lg-7">
												<input type="password" id="password_modify_confirm" name="confirm_password"
													 data-parsley-equalto="#pwd_modify_confirm" class="form-control" autocomplete="off">
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.manager.modal.add.name"/><span
											class="text-danger">*</span></label>
										<div class="col-lg-8">
											<input type="text" name="name" class="form-control" id="editName"
												autocomplete="off" data-parsley-required="true"
												data-parsley-maxlength="20">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.manager.modal.add.role"/></label>
										<div class="col-lg-8">
											<select name="roleType" class="form-control input-md pos" id="roleType" >
					             				<option value=""><fmt:message key="tiles.views.admin.index.manager.modal.add.role.name"/></option>
					             			</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.manager.modal.add.phone"/></label>
										<div class="col-lg-8">
											<input type="text" name="phone" class="form-control" id="editPhone"
												data-parsley-cellphone autocomplete="off">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.manager.modal.add.email"/><span
											class="text-danger">*</span></label>
										<div class="col-lg-8">
											<input type="email" name="email" id="editEmail" class="form-control" data-parsley-required="true"
												autocomplete="off" data-parsley-type="email" 
												data-parsley-pattern="/^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.manager.modal.add.mark"/></label>
										<div class="col-lg-8">
											<textarea name="mark" id="editMark" class="form-control mark" autocomplete="off"></textarea>
										</div>
									</div>
									<h5>
										<span class="badge bg-primary"><fmt:message key="tiles.views.admin.index.manager.modal.add.totalOrgLists"/></span>
									</h5>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.manager.org"/><span
											class="text-danger">*</span></label>
										<div class="col-lg-10 row"
											style="max-height: 300px;">
											<input type="hidden" value="" name="vtls">
												<section class="panel panel-default col-lg-9 panel-left"
													style="height: 180px; max-height: 250px; overflow: scroll;">
														<div class="searchicon">
													      	<input type="text" class="form-control searchtext" id="editSearchOrg" onkeyup="editSearchOrgList();">
													      	<div class="basic_info basic_line">
																<span class="span_line"></span>
															</div>
													    </div>
														<div class="checkbox i-checks" id="editOrgList">
															
														</div>
												</section>
										</div>
									</div>
									<footer class="row">
										<div class="col-sm-4 col-sm-offset-4 col-md-4 col-md-offset-4">
											<input type="button" class="btn btn-s-md btn-primary " value="<fmt:message key="tiles.views.admin.index.manager.modal.update.update"/>" id="update">&nbsp;&nbsp;
											<input type="button" class="btn btn-s-md btn-primary pull-right" value="<fmt:message key="tiles.views.admin.index.manager.modal.add.cancel"/>" id="updateCancel">
										</div>
									</footer>
								</form>
							</div>
						</section>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- 查看 机构管理员的modal-->
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
								<fmt:message key="tiles.views.admin.index.manager.modal.show.header"/><span class="text-warning pull-right">
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
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.manager.modal.add.username"/></label>
										<div class="col-lg-8 span_sep">
											<span name="username" id="viewUsername"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.manager.modal.add.name"/></label>
										<div class="col-lg-8 span_sep">
											<span name="name" id="vieName"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.manager.modal.show.status"/></label>
										<div class="col-lg-8 span_sep">
											<span name="status" id="viewStatus"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.manager.modal.add.role"/></label>
										<div class="col-lg-8 span_sep">
											<span name="role" id="viewRole"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.manager.modal.add.phone"/></label>
										<div class="col-lg-8 span_sep">
											<span name="phone" id="viewPhone"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.manager.modal.add.email"/></label>
										<div class="col-lg-8 span_sep">
											<span name="email" id="viewEmail"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.manager.modal.add.mark"/></label>
										<div class="col-lg-8 span_sep">
											<span name="mark" id="viewMark"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.manager.modal.show.create_time"/></label>
										<div class="col-lg-8 span_sep">
											<span name="createTime" id="viewCreateTime"></span>
										</div>
									</div><div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.manager.modal.show.create_by"/></label>
										<div class="col-lg-8 span_sep">
											<span name="createBy" id="viewCreateBy"></span>
										</div>
									</div>
									<div style="margin-top:20px;">
										
									</div>
									<h5>
										<span class="badge bg-primary"><fmt:message key="tiles.views.admin.index.manager.modal.add.totalOrgLists"/></span>
									</h5>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.admin.index.manager.org"/></label>
										<div class="col-lg-10 row"
											style="max-height: 300px;">
												<section class="panel panel-default col-lg-9 panel-left view"
													style="height: 150px; max-height: 150px; overflow: scroll;">
														<div id="viewOrgs">
															
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

<div class="modal fade" id="delOrganizationModel" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog" role="document">
		<input type="hidden" value="" id="managerId">
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
					<h3 class="text-danger del-text" id="organizationTip"><fmt:message key="tiles.views.admin.index.manager.model.delTipContent"/></h3>
				</div>
				<div class="modal-footer">
					<a href="javascript:void(0);" onclick="deleteManager();" class="btn btn-success"><fmt:message key="tiles.views.admin.index.manager.model.success"/></a>
	     			<a href="javascript:void(0);" class="btn btn-success" data-dismiss="modal"><fmt:message key="tiles.views.admin.index.manager.modal.add.cancel"/></a>
				</div>
		</div>
	</div>
</div>

<div class="modal fade" id="lockManagerModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog" role="document">
		<input type="hidden" value="" id="lockManagerId">
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
					<h3 class="text-danger del-text" id="organizationTip"><fmt:message key="tiles.views.admin.index.manager.model.lockTipContent"/></h3>
				</div>
				<div class="modal-footer">
					<a href="javascript:void(0);" onclick="lockManagerBtn();" class="btn btn-success"><fmt:message key="tiles.views.admin.index.manager.model.success"/></a>
	     			<a href="javascript:void(0);" class="btn btn-success" data-dismiss="modal"><fmt:message key="tiles.views.admin.index.manager.modal.add.cancel"/></a>
				</div>
		</div>
	</div>
</div>

<div class="modal fade" id="unLockManagerModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog" role="document">
		<input type="hidden" value="" id="unLockManagerId">
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
					<h3 class="text-danger del-text" id="organizationTip"><fmt:message key="tiles.views.admin.index.manager.model.unlockTipContent"/></h3>
				</div>
				<div class="modal-footer">
					<a href="javascript:void(0);" onclick="unLockManagerBtn();" class="btn btn-success"><fmt:message key="tiles.views.admin.index.manager.model.success"/></a>
	     			<a href="javascript:void(0);" class="btn btn-success" data-dismiss="modal"><fmt:message key="tiles.views.admin.index.manager.modal.add.cancel"/></a>
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

