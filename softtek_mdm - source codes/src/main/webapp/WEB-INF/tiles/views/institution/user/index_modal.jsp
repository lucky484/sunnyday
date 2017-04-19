<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<spring:url value="/resources/images/a0.png" var="defaultFaceUrl" />

<!-- Modal start-->
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
			<div class="modal-body" style="max-height: 800px; overflow: scroll;">
				<div class="row">
					<div class="col-sm-12">
						<section class="panel panel-default">
							<header class="panel-heading font-bold">
								<fmt:message key="tiles.views.user.index.modal.newly.add" />
								<span class="text-warning pull-right">(<fmt:message
										key="tiles.views.institution.urule.newly.has" /><span
									class="text-danger">*</span>
								<fmt:message key="tiles.views.institution.urule.newly.required" />)
								</span>
							</header>
							<div class="panel-body">
								<form id="addFrm" class="bs-example form-horizontal">
									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" />
									<h5>
										<span class="badge bg-primary"><fmt:message
												key="tiles.views.user.index.table.baseinfo" /></span>
									</h5>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message
												key="tiles.views.user.index.search.account" /><span
											class="text-danger">*</span></label>
										<div class="col-lg-10">
											<input type="text" name="username"
												class="form-control add-user-name" autocomplete="off"
												data-parsley-required="true" data-parsley-maxlength="20"
												data-parsley-remote
												data-parsley-remote-validator="existsValidate"
												data-parsley-trigger="blur"
												data-parsley-remote-message="<fmt:message key='tiles.views.user.index.table.account.exists'/>">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message
												key="tiles.views.user.index.table.password" /><span
											class="text-danger">*</span></label>
										<div class="col-lg-10">
											<input type="password" id="password_confirm" name="password"
												class="form-control" data-parsley-required="true"
												data-parsley-alphanumber>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message
												key="tiles.views.user.index.table.confrimpwd" /><span
											class="text-danger">*</span></label>
										<div class="col-lg-10">
											<input type="password" class="form-control"
												data-parsley-alphanumber data-parsley-required="true"
												data-parsley-alphanumber
												data-parsley-equalto="#password_confirm">
										</div>
									</div>

									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message
												key="tiles.views.user.index.table.realname" /><span
											class="text-danger">*</span></label>
										<div class="col-lg-10">
											<input type="text" name="realname" class="form-control"
												autocomplete="off" data-parsley-required="true"
												data-parsley-maxlength="20">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message
												key="tiles.views.user.index.table.belongdepart" /><span
											class="text-danger">*</span></label>
										<div class="col-lg-10">
											<input type="hidden" class="belong_structure"
												name="structure.id" value="">
											<div id="department"
												style="max-height: 200px; overflow: scroll; min-height: 100px;"></div>
											<span id="depart-error" class="hidden">
												<ul class="parsley-errors-list filled">
													<li class=""><fmt:message
															key="tiles.views.user.index.table.required" /></li>
												</ul>
											</span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message
												key="tiles.views.user.index.table.phone" /></label>
										<div class="col-lg-10">
											<input type="text" name="phone" class="form-control"
												data-parsley-cellphone autocomplete="off">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message
												key="tiles.views.user.index.table.email" /></label>
										<div class="col-lg-10">
											<input type="email" name="email" class="form-control"
												autocomplete="off" data-parsley-type="email"
												data-parsley-pattern="/^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/">
										</div>
									</div>

									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message
												key="tiles.views.institution.device.rule.memo" /></label>
										<div class="col-lg-10">
											<input type="text" class="form-control" name="mark"
												autocomplete="off" maxlength="30">
										</div>
									</div>

									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message
												key="tiles.views.user.index.table.belongvtl" /></label>
										<div class="col-lg-10 row"
											style="max-height: 300px; overflow-y: scroll;">
											<input type="hidden" value="" name="vtls">
											<c:forEach var="vtlcol" items="${vtlcols}">
												<section class="panel panel-default col-lg-4"
													style="height: 150px; max-height: 150px; overflow: scroll;">
													<header class="panel-heading font-bold">${vtlcol.name}</header>
													<c:forEach var="vtl" items="${vtls}">
														<c:choose>
															<c:when test="${vtlcol.multiple == 1}">
																<c:if test="${vtl.virtualCollection.id==vtlcol.id}">
																	<div class="checkbox i-checks">
																		<label> <input type="checkbox" class="vtls"
																			value="${vtl.id}"> <i></i> ${vtl.name}
																		</label>
																	</div>
																</c:if>
															</c:when>
															<c:otherwise>
																<c:if test="${vtl.virtualCollection.id==vtlcol.id}">
																	<div class="radio i-checks">
																		<label> <input type="radio" class="vtls"
																			name="rad_${vtlcol.id}" value="${vtl.id}"><i></i>
																			${vtl.name}
																		</label>
																	</div>
																</c:if>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</section>
											</c:forEach>
										</div>
									</div>
									<hr>
									<h5>
										<span class="badge bg-primary"><fmt:message
												key="tiles.views.user.index.table.customer" /></span>
									</h5>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message
												key="tiles.views.user.index.table.gender" /></label>
										<div class="col-lg-10">
											<div class="radio i-checks col-lg-2">
												<label> <input type="radio" name="gender" value="1"
													checked="checked"> <i></i> <fmt:message
														key="tiles.views.user.index.table.gender.man" />
												</label>
											</div>
											<div class="radio i-checks col-lg-2">
												<label> <input type="radio" name="gender" value="0">
													<i></i> <fmt:message
														key="tiles.views.user.index.table.gender.woman" />
												</label>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message
												key="tiles.views.user.index.table.sign" /></label>
										<div class="col-lg-10">
											<input type="text" name="sign" class="form-control"
												autocomplete="off" maxlength="30">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message
												key="tiles.views.user.index.table.address" /></label>
										<div class="col-lg-10">
											<input type="text" name="address" class="form-control"
												autocomplete="off" maxlength="30">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message
												key="tiles.views.user.index.table.telephone" /></label>
										<div class="col-lg-10">
											<input type="text" name="office_phone" class="form-control"
												autocomplete="off" maxlength="30">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message
												key="tiles.views.user.index.table.position" /></label>
										<div class="col-lg-10">
											<input type="text" name="position" class="form-control"
												autocomplete="off" maxlength="30">
										</div>
									</div>
									<input type="button"
										class="btn btn-s-md btn-primary pull-right"
										value="<fmt:message key='tiles.views.institution.urule.newly.add'/>">
								</form>
							</div>
						</section>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--Modal end-->

<!-- Modal start-->
<div class="modal fade" id="modifyModal" tabindex="-1" role="dialog"
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

			</div>
		</div>
	</div>
</div>
<!--Modal end-->

<!-- Modal start-->
<div class="modal fade" id="importModal" tabindex="-1" role="dialog"
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
							<header class="panel-heading font-bold">
								<fmt:message key="tiles.views.user.index.table.importuser.title" />
							</header>
							<div class="panel-body">
								<form class="bs-example form-horizontal">
									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" />
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message
												key="tiles.views.user.index.table.importuser.choosexls" /></label>
										<div class="col-lg-10">
											<input type="file" class="form-control">
										</div>
									</div>
									<input type="submit" class="btn btn-md btn-primary pull-right"
										value="<fmt:message key='tiles.views.user.index.table.importuser.begin'/>">
								</form>
							</div>
						</section>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--Modal end-->

<!-- Modal start-->
<div class="modal fade" id="strategyModal" tabindex="-1" role="dialog"
	data-backdrop="static">
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
							<header class="panel-heading font-bold">
								<fmt:message key="tiles.views.user.index.table.modify.title" />
							</header>
							<div class="panel-body">
								<div class="bs-example form-horizontal">
									<div class="form-group">
										<label class="col-lg-3 control-label"><fmt:message
												key="tiles.views.user.index.table.modify.android" /></label>
										<div class="col-lg-9">
											<input type="hidden" id="android_uid" value="">
											<section class="panel panel-default android-strategy"
												style="max-height: 200px; overflow-y: scroll;"></section>
										</div>
									</div>
									 <div class="form-group">
										<label class="col-lg-3 control-label"><fmt:message
												key="tiles.views.user.index.table.modify.ios.policy" /></label>
										<div class="col-lg-9">
										<input type="hidden" id="ios_uid" value="">
										<section class="panel panel-default ios-strategy" style="max-height: 200px; overflow-y: scroll;"></section>
										</div>
									</div> 
									<input type="button"
										class="btn btn-s-md btn-primary pull-right"
										value="<fmt:message key='tiles.views.institution.urule.modify'/>"
										onclick="submitStrategy()">
								</div>
							</div>
						</section>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--Modal end-->

<!-- Modal start-->
<div class="modal fade" id="userStrategyModal" tabindex="-1"
	role="dialog" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header b-b-l-none">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body"></div>
		</div>
	</div>
</div>
<!--Modal end-->

<!-- Modal start-->
<div class="modal fade" id="infoModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header b-b-l-none">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body" style="max-height: 500px; overflow: scroll;">

			</div>
		</div>
	</div>
</div>
<!--Modal end-->

<!-- Modal start-->
<div class="modal fade" id="delModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
					<fmt:message
						key="tiles.views.institution.device.rule.delete.tip.header" />
				</h4>
			</div>
			<div class="modal-body">
				<h3 class="text-danger">
					<fmt:message key="tiles.views.user.index.table.delete" />
				</h3>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<fmt:message
						key="tiles.views.institution.device.rule.delete.tip.cancel" />
				</button>
				<input type="button" class="btn btn-danger btn-delete-users"
					data-dismiss="modal"
					value="<fmt:message key='tiles.views.institution.device.rule.delete.tip.confirm'/>">
			</div>
		</div>
	</div>
</div>
<!--Modal end-->

<!-- Modal start-->
<div class="modal fade" id="delUserModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
					<fmt:message
						key="tiles.views.institution.device.rule.delete.tip.header" />
				</h4>
			</div>
			<div class="modal-body">
				<input type="hidden" class="del-ids" value="">
				<h3 class="text-danger del-text">
					<fmt:message key="tiles.views.user.index.table.delete.current" />
				</h3>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<fmt:message
						key="tiles.views.institution.device.rule.delete.tip.cancel" />
				</button>
				<input type="button" class="btn btn-danger" data-dismiss="modal"
					onclick="truncateUser()"
					value="<fmt:message key='tiles.views.institution.device.rule.delete.tip.confirm'/>">
			</div>
		</div>
	</div>
</div>
<!--Modal end-->

<!-- Modal start-->
<div class="modal fade" id="deviceModal" tabindex="-1" role="dialog"
	data-backdrop="static">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<span class="font-bold"><fmt:message
						key="tiles.views.user.index.table.device.info" /></span> <input
					type="hidden" class="device-modal-uid" value="">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body"></div>
		</div>
	</div>
</div>
<!--Modal end-->

<!-- Modal start-->
<div class="modal fade" id="managerModal" tabindex="-1" role="dialog"
	data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<span class="font-bold"><fmt:message
						key="tiles.views.user.index.table.user.promote" /></span>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<section class="panel panel-default">
					<form id="uRDFrm">
						<header class="panel-heading font-bold">
							<fmt:message key="tiles.views.user.index.table.user.promote" />
							<span class="text-warning pull-right">(<fmt:message
									key="tiles.views.institution.urule.newly.has" /><span
								class="text-danger">*</span>
							<fmt:message key="tiles.views.institution.urule.newly.required" />)
							</span>
						</header>
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" /> <input type="hidden" value=""
							name="user.id" class="promote-uid"> <input type="hidden"
							value="" name="departmentIds" class="department_ids">
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-2 control-label"><fmt:message
										key="tiles.views.user.index.table.user.promote.role" /><span
									class="text-danger">*</span></label>
								<div class="col-sm-10 ck-tmpl"
									style="max-height: 200px; overflow-y: scroll;"></div>
							</div>
							<div class="line line-dashed b-b line-lg pull-in"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label"><fmt:message
										key="tiles.views.user.index.table.user.promote.departs" /><span
									class="text-danger">*</span></label>
								<div id="promoteTree" class="col-sm-10"
									style="max-height: 200px; overflow-y: scroll;"></div>
								<div id="promote-tree-error" class="hidden col-sm-offset-2">
									<ul class="parsley-errors-list filled">
										<li><fmt:message
												key="tiles.views.institution.urule.newly.rule.mustrequired" /></li>
									</ul>
								</div>
							</div>
							<div class="line line-dashed b-b line-lg pull-in"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label"><fmt:message
										key="tiles.views.institution.device.rule.memo" /></label>
								<div class="col-sm-10">
									<textarea name="mark" rows="5" cols="20" class="form-control"
										style="resize: none;" maxlength="100"></textarea>
								</div>
							</div>
							<div class="line line-dashed b-b line-lg pull-in"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label"><fmt:message
										key="tiles.views.user.index.modal.manager.auth.title" /></label>
								<div class="col-sm-10">
									<div class="radio i-checks">
										<label> <input type="radio" name="auth" value="0">
											<i></i> <fmt:message
												key="tiles.views.user.index.modal.manager.auth.readonly.title" />
										</label>
									</div>
									<div class="radio i-checks">
										<label> <input type="radio" name="auth" value="1"
											checked> <i></i> <fmt:message
												key="tiles.views.user.index.modal.manager.auth.all.title" />
										</label>
									</div>
								</div>
							</div>
							<input type="button" onclick="subPromote(0,0)"
								class="btn btn-md btn-primary m-t pull-right"
								value="<fmt:message key='tiles.views.user.index.table.user.promote'/>">
						</div>
					</form>
				</section>

			</div>
		</div>
	</div>
</div>
<!--Modal end-->



<!-- Jquery Tmplates  start-->
<script id="checkboxTmpl" type="text/x-jquery-tmpl">
	{{each(i,e) data}}
		 <div class="radio i-checks">
		 	<label>
		 	<input type="radio" name="role.id" value="{{= e.id}}" {{if i==0}} checked {{/if}}>
		 	<i></i>
		 	{{= e.name}}
		 	</label>
		 </div> 
	{{/each}}
</script>

<script id="userInfo" type="text/x-jquery-tmpl">
					<div class="row">
						<div class="col-sm-12">
							<section class="panel panel-default">
								<header class="panel-heading font-bold"><fmt:message key="tiles.views.user.index.table.userinfo.title"/></header>
								<div class="panel-body">
									<div class="bs-example form-horizontal">
										<h5>
											<span class="badge bg-primary"><fmt:message key="tiles.views.user.index.table.baseinfo"/></span>
										</h5>
										<div class="form-group">
											<label class="col-lg-3 control-label font-bold"><fmt:message key="tiles.views.user.index.table.account"/></label>
											<div class="col-lg-9">
												<label class="control-label">{{= username}}</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label  font-bold"><fmt:message key="tiles.views.user.index.table.realname"/></label>
											<div class="col-lg-9">
												<label class="control-label">{{= realname}}</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label font-bold"><fmt:message key="tiles.views.user.index.table.belongdepart"/></label>
											<div class="col-lg-9">
												<label class="control-label">{{= department}}</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label  font-bold"><fmt:message key="tiles.views.user.index.table.phone"/></label>
											<div class="col-lg-9">
												<label class="control-label">{{= phone}}</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label  font-bold"><fmt:message key="tiles.views.user.index.table.email"/></label>
											<div class="col-lg-9">
												<label class="control-label">{{= email}}</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label  font-bold"><fmt:message key="tiles.views.user.index.table.userinfo.createdat"/></label>
											<div class="col-lg-9">
												<label class="control-label">{{= createtime}}</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label font-bold"><fmt:message key="tiles.views.institution.device.rule.memo"/></label>
											<div class="col-lg-9">
												<label class="control-label">{{= mark}}</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label  font-bold"><fmt:message key="tiles.views.user.index.table.userinfo.createdtype"/></label>
											<div class="col-lg-9">
												<label class="control-label"><fmt:message key="tiles.views.user.index.table.userinfo.createdtype.admin"/></label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label font-bold"><fmt:message key="tiles.views.user.index.table.userinfo.group"/></label>
											<div class="col-lg-9">
												<label class="control-label">{{= vtl}}</label>
											</div>
										</div>
										<hr>
										<h5>
											<span class="badge bg-primary"><fmt:message key="tiles.views.user.index.table.customer"/></span>
										</h5>

										<div class="form-group">
											<label class="col-lg-3 control-label  font-bold"><fmt:message key="tiles.views.user.index.table.gender"/></label>
											<div class="col-lg-9">
												<label class="control-label">{{if gender==1}}<fmt:message key="tiles.views.user.index.table.gender.man"/>{{else}}<fmt:message key="tiles.views.user.index.table.gender.woman"/>{{/if}}</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label font-bold"><fmt:message key="tiles.views.user.index.table.sign"/></label>
											<div class="col-lg-9">
												<label class="control-label">{{= sign}}</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label font-bold"><fmt:message key="tiles.views.user.index.table.address"/></label>
											<div class="col-lg-9">
												<label class="control-label">{{= address}}</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label font-bold"><fmt:message key="tiles.views.user.index.table.telephone"/></label>
											<div class="col-lg-9">
												<label class="control-label">{{= office}}</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label font-bold"><fmt:message key="tiles.views.user.index.table.position"/></label>
											<div class="col-lg-9">
												<label class="control-label">{{= position}}</label>
											</div>
										</div>
									</div>
								</div>
							</section>
						</div>
					</div>
</script>

<script id="modifyInfo" type="text/x-jquery-tmpl">
				<div class="row">
					<div class="col-sm-12">
						<section class="panel panel-default">
							<header class="panel-heading font-bold"><fmt:message key="tiles.views.user.index.table.modifyinfo.title"/><span class="text-warning pull-right">(<fmt:message key="tiles.views.institution.urule.newly.has"/><span class="text-danger">*</span><fmt:message key="tiles.views.institution.urule.newly.required"/>)</span></header>
							<div class="panel-body">
								<form id="modifyFrm" class="bs-example form-horizontal">
									<h5>
										<span class="badge bg-primary"><fmt:message key="tiles.views.user.index.table.baseinfo"/></span>
									</h5>
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
									<input type="hidden" class="m_id" name="id" value="{{= id}}">
									<div class="form-group">
										<label class="col-lg-3 control-label"><fmt:message key="tiles.views.user.index.table.account"/><span class="text-danger">*</span></label>
										<div class="col-lg-9">
											<input type="text"
												class="form-control modify-user-name" autocomplete="off" data-parsley-required="true" data-parsley-maxlength="20" ref="{{= username}}" value="{{= username}}" disabled>
										</div>
									</div>
								<div class="form-group">
									<button href="#moreless" class="btn btn-sm btn-default pull-right m-r-lg" data-toggle="class:show" onclick="toggleExclued()">
		                  				<i class="fa fa-plus text"></i>
		                  				<span class="text"><fmt:message key="tiles.views.user.index.table.modifyinfo.changepwd"/></span>
		                  				<i class="fa fa-minus text-active"></i>
		                  				<span class="text-active"><fmt:message key="tiles.views.user.index.table.modifyinfo.changepwd.no"/></span>
		                			</button>
								</div>
								<div id="moreless" class="hide">
										<div class="form-group m_pwd">
														<label class="col-lg-3 control-label"><fmt:message key="tiles.views.user.index.table.password"/><span class="text-danger">*</span></label>
														<div class="col-lg-9">
															<input type="password" id="pwd_modify_confirm" name="password"
																 class="form-control">
														</div>
													</div>
										<div class="form-group m_pwd">
														<label class="col-lg-3 control-label"><fmt:message key="tiles.views.user.index.table.confrimpwd"/><span class="text-danger">*</span></label>
														<div class="col-lg-9">
															<input type="password" id="password_modify_confirm" name="confirm_password"
																 data-parsley-equalto="#pwd_modify_confirm" class="form-control">
														</div>
													</div>
									</div>
									
									<div class="form-group">
										<label class="col-lg-3 control-label"><fmt:message key="tiles.views.user.index.table.realname"/><span class="text-danger">*</span></label>
										<div class="col-lg-9">
											<input type="text" name="realname" autocomplete="off"
												class="form-control" value="{{= realname}}" data-parsley-required="true" maxlength="20">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-3 control-label"><fmt:message key="tiles.views.user.index.table.belongdepart"/><span class="text-danger">*</span></label>
										<div class="col-lg-9">
											<input type="hidden" class="belong_ste" name="structure.id"
												value="{{= departmentid}}">
											<div id="modify_depart"
												style="max-height: 200px; overflow: scroll; min-height: 100px;"></div>
												<span id="depart-modify-error" class="hidden">
												 <ul class="parsley-errors-list filled"><li class=""><fmt:message key="tiles.views.user.index.table.modifyinfo.belong"/></li></ul>
												</span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-3 control-label"><fmt:message key="tiles.views.user.index.table.phone"/></label>
										<div class="col-lg-9">
											<input type="text" name="phone" autocomplete="off" class="form-control"  value="{{= phone}}" data-parsley-cellphone>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-3 control-label"><fmt:message key="tiles.views.user.index.table.email"/></label>
										<div class="col-lg-9">
											<input type="email" name="email" autocomplete="off" data-parsley-type="email" data-parsley-pattern="/^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/" class="form-control m_email" value="{{= email}}">
										</div>
									</div>

									<div class="form-group">
										<label class="col-lg-3 control-label"><fmt:message key="tiles.views.institution.device.rule.memo"/></label>
										<div class="col-lg-9">
											<input type="text" class="form-control m_mark" name="mark"
												value="{{= mark}}" maxlength="30">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-3 control-label"><fmt:message key="tiles.views.user.index.table.belongvtl"/></label>
										<div class="col-lg-9 row" style="max-height:300px;overflow-y:scroll;">
											<input type="hidden" value="" name="vtlids">
											{{each(i,vls) vtlcols}}
												<section class="panel panel-default m_ckrd col-lg-4"
													style="height: 150px; max-height: 150px; overflow: scroll;">
													<header class="panel-heading font-bold">{{= vls.name}}</header>
													{{each(j,vs) vtls}}
														{{if vls.multiple==1}}
																{{if vs.virtualCollection.id==vls.id}}
																	<div class="checkbox i-checks">
																		<label> <input type="checkbox" class="vtls" name="ck[]"
																			value="{{= vs.id}}" {{if vs.weight==-1}} checked {{/if}}> <i></i> {{= vs.name}}
																		</label>
																	</div>
																{{/if}}
														{{else}}
																{{if vs.virtualCollection.id==vls.id}}
																	<div class="radio i-checks">
																		<label> <input type="radio" class="vtls"
																			name="rad_{{= vls.id}}" value="{{= vs.id}}" {{if vs.weight==-1}} checked {{/if}}><i></i>
																			{{= vs.name}}
																		</label>
																	</div>
																{{/if}}
														{{/if}}
													{{/each}}
												</section>
											{{/each}}
										</div>
									</div>
									<hr>
									<h5>
										<span class="badge bg-primary"><fmt:message key="tiles.views.user.index.table.customer"/></span>
									</h5>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.user.index.table.gender"/></label>
										<div class="col-lg-10">
											<div class="radio i-checks col-lg-2">
												<label> <input type="radio"
													name="gender" value="1" {{if gender==1}} checked {{/if}}> <i></i> <fmt:message key="tiles.views.user.index.table.gender.man"/>
												</label>
											</div>
											<div class="radio i-checks col-lg-2">
												<label> <input type="radio" name="gender"
													class="m_gender_n" value="0" {{if gender==0}} checked {{/if}}> <i></i> <fmt:message key="tiles.views.user.index.table.gender.woman"/>
												</label>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.user.index.table.sign"/></label>
										<div class="col-lg-10">
											<input type="text" name="sign" class="form-control m_sign" maxlength="30"
												value="{{= sign}}" autocomplete="off">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.user.index.table.address"/></label>
										<div class="col-lg-10">
											<input type="text" name="address"
												class="form-control m_address" autocomplete="off" value="{{= address}}" maxlength="30">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.user.index.table.telephone"/></label>
										<div class="col-lg-10">
											<input type="text" name="office_phone"
												class="form-control m_office_phone" autocomplete="off" value="{{= office}}" maxlength="30">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.user.index.table.position"/></label>
										<div class="col-lg-10">
											<input type="text" name="position"
												class="form-control m_position" autocomplete="off" value="{{= position}}" maxlength="30">
										</div>
									</div>
									<input type="button"
										class="btn btn-s-md btn-primary pull-right btn-modify-user" onclick="modifySubmit(this)" value="<fmt:message key='tiles.views.institution.urule.modify'/>">
								</form>
							</div>
						</section>
					</div>
				</div>
</script>

<script id="strategyInfo" type="text/x-jquery-tmpl">
				<div class="row">
					<div class="col-sm-12">
						<section class="panel panel-default">
							<header class="panel-heading font-bold"><fmt:message key="tiles.views.user.index.table.strategyinfo.title"/></header>
							<div class="panel-body">
									<div class="form-group">
										<div class="col-lg-12">
											<input type="hidden" name="strategy_uid" value=""/>
											<select name="strategy_select" class="form-control m-b">
												{{each(i,data) list}}
													{{if data.id==sid}}
													<option  selected value="{{= data.id}}">{{= data.name}}</option>
													{{else}}
														<option value="{{= data.id}}">{{= data.name}}</option>
													{{/if}}
												{{/each}}
											</select>
										</div>
									</div>
									<button type="button"
										class="btn btn-s-md btn-primary pull-right" onclick="strategy_modify()" data-dismiss="modal"><fmt:message key="tiles.views.user.index.table.strategyinfo.btn"/></button>
							</div>
						</section>
					</div>
				</div>
</script>


<!-- 设备详情 -->
	<div class="modal fade" id="deviceInfo" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog" role="document" style="width:1000px;">
			<div class="modal-content" >
				<div style="min-height: 16.42857143px;padding: 15px;">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">设备的详细信息</h4>
				</div>
				<div style="background:#F0F0F0">
				   <div class="pop-aside detail-aside" style="height: 470px;">
					<div class="Js_dropMod select-box inline-select select-200" style="width: 145px; margin: 10px 0px 10px 20px; z-index: 1;">
						<input type="hidden" class="Js_hiddenVal" id="handsetUuid" name="handsetUuid" value="">
						<span class="Js_curVal"><input type="text" value="" style="margin-top:1px;"></span>
						<ul class="select-list" id="handsetUuidUL" style="width: 145px; display: none;"></ul>
					</div>
					<div style="margin:0;padding:0;height:1px;width:100%;border-bottom:1px solid #D2DFF4"></div>
					<ul class=" aside-list pop-asidelist" id="Js-optAsideList" style="padding-right: 0px;padding-bottom: 0px;padding-left: 0px;padding-top:35px;">
					
						<li class="aside-item"><a href="javascript:baseInfo();" class="current clicked"><span>通用</span></a></li>
						<li class="aside-item"><a href="javascript:appInfo();" class=""><span>应用</span></a></li>
						
						<li class="aside-item"><a href="javascript:illegalInfo();" class=""><span>合规性</span></a></li>
						<li class="aside-item"><a href="javascript:locationInfo();" class=""><span>位置</span></a></li>
						
						
						<li class="aside-item"><a href="javascript:net();" class=""><span>网络</span></a></li>
						<li class="aside-item"><a href="javascript:savety();" class=""><span>安全性</span></a></li>
						
						<li class="aside-item"><a href="javascript:userInfo();" class=""><span>用户信息</span></a></li>
						
						<li class="aside-item"><a href="javascript:recordsInfo();" class=""><span>违规历史</span></a></li>
						
						<li class="aside-item"><a href="javascript:logsInfo();" class=""><span>设备日志</span></a></li>
					</ul>
				</div>
				<div class="pop-cons fixed-pop-cons">
				  <div class="pop-main" style="height:468px;overflow-y:hidden">
				     <div class="main-item" style="height:471px;display:block;">
				     <div class="temp-scroll-box">
				       <div class="temp-bd" style="width:799px;">
				        <div class="tempbd-item" style="width: 799px;margin-left: 0px;height: 468px;background:#F0F0F0">
				         <input type="hidden" id="userIdDeviceInfo"/>
				         <input type="hidden" id="deviceStatus" />
				        <div class="main-cons" id="deviceInfoContent">
						</div>
					    </div>
					</div>
				     </div>
				     </div>
				  </div>
				</div>
				</div> 
				 <div class="modal-footer" style="background:#F0F0F0;height:55px;">
				     <button type="button" class="btn btn-default" data-dismiss="modal" style="margin-right:470px;margin-top:-8px"><fmt:message key="tiles.institution.org.department.confirm" /></button>
				     <button type="button" class="btn btn-default" data-dismiss="modal" style="margin-right:385px;margin-top:-56px">取消</button>
			      </div> 
			</div>
		</div>
	</div>

<script id="newModaTmpl" type="text/x-jquery-tmpl">
									<h5>
										<span class="badge bg-primary"><fmt:message key="tiles.views.user.index.table.baseinfo"/></span>
									</h5>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.user.index.table.account"/><span class="text-danger">*</span></label>
										<div class="col-lg-10">
											<input type="text" name="username" class="form-control add-user-name"
												 autocomplete="off" data-parsley-required="true" data-parsley-maxlength="20">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.user.index.table.password"/><span class="text-danger">*</span></label>
										<div class="col-lg-10">
											<input type="password" id="password_confirm" name="password" class="form-control"
												data-parsley-required="true" data-parsley-length="[6, 10]">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.user.index.table.confrimpwd"/><span class="text-danger">*</span></label>
										<div class="col-lg-10">
											<input type="password"
												class="form-control"
												data-parsley-required="true" data-parsley-length="[6, 10]"
												data-parsley-equalto="#password_confirm">
										</div>
									</div>

									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.user.index.table.realname"/></label>
										<div class="col-lg-10">
											<input type="text" autocomplete="off" name="realname" class="form-control">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.user.index.table.belongdepart"/><span class="text-danger">*</span></label>
										<div class="col-lg-10">
											<input type="hidden" class="belong_structure"
												name="structure.id" value="">
											<div 
												style="max-height: 200px; overflow: scroll; min-height: 100px;"></div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.user.index.table.phone"/></label>
										<div class="col-lg-10">
											<input type="text" autocomplete="off" name="phone" class="form-control">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.user.index.table.email"/></label>
										<div class="col-lg-10">
											<input type="email" name="email" class="form-control">
										</div>
									</div>

									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.device.rule.memo"/></label>
										<div class="col-lg-10">
											<input type="text" autocomplete="off" class="form-control" name="mark">
										</div>
									</div>

									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.user.index.table.belongvtl"/></label>
										<div class="col-lg-10 row" style="max-height:300px;overflow-y:scroll;">
											<input type="hidden" value="" name="vtls">
											<c:forEach var="vtlcol" items="${vtlcols}">
												<section class="panel panel-default col-lg-4"
													style="height: 150px; max-height: 150px; overflow: scroll;">
													<header class="panel-heading font-bold">${vtlcol.name}</header>
													<c:forEach var="vtl" items="${vtls}">
														<c:choose>
															<c:when test="${vtlcol.multiple == 1}">
																<c:if test="${vtl.virtualCollection.id==vtlcol.id}">
																	<div class="checkbox i-checks">
																		<label> <input type="checkbox" class="vtls"
																			value="${vtl.id}"> <i></i> ${vtl.name}
																		</label>
																	</div>
																</c:if>
															</c:when>
															<c:otherwise>
																<c:if test="${vtl.virtualCollection.id==vtlcol.id}">
																	<div class="radio i-checks">
																		<label> <input type="radio" class="vtls"
																			name="a" value="${vtl.id}"><i></i>
																			${vtl.name}
																		</label>
																	</div>
																</c:if>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</section>
											</c:forEach>
										</div>
									</div>
									<hr>
									<h5>
										<span class="badge bg-primary"><fmt:message key="tiles.views.user.index.table.customer"/></span>
									</h5>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.user.index.table.gender"/></label>
										<div class="col-lg-10">
											<div class="radio i-checks col-lg-2">
												<label> <input type="radio" name="gender" value="1">
													<i></i> <fmt:message key="tiles.views.user.index.table.gender.man"/>
												</label>
											</div>
											<div class="radio i-checks col-lg-2">
												<label> <input type="radio" name="gender" value="0"
													checked="checked"> <i></i> <fmt:message key="tiles.views.user.index.table.gender.woman"/>
												</label>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.user.index.table.sign"/></label>
										<div class="col-lg-10">
											<input type="text" autocomplete="off" name="sign" class="form-control" maxlength="30">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.user.index.table.address"/></label>
										<div class="col-lg-10">
											<input type="text" autocomplete="off" name="address" class="form-control" maxlength="30">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.user.index.table.telephone"/></label>
										<div class="col-lg-10">
											<input type="text" autocomplete="off" name="office_phone" class="form-control" maxlength="30">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.user.index.table.position"/></label>
										<div class="col-lg-10">
											<input type="text" autocomplete="off" name="position" class="form-control" maxlength="30">
										</div>
									</div>
									<input type="button"
										class="btn btn-s-md btn-primary pull-right" data-dismiss="modal" value="<fmt:message key="tiles.views.institution.urule.newly.add"/>">
</script>
<!-- Tmplate end -->

<!-- Modal start-->
<div class="modal fade" id="excelModal" tabindex="-1" role="dialog"
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
							<header class="panel-heading font-bold">
								<fmt:message key="tiles.views.user.index.table.excel.import" />
							</header>
							<div class="panel-body">
								<spring:url value="/institution/vtl/save" var="modal_saveUrl" />
								<spring:url value="/resources/images/virtualMemberexcelmode.png"
									var="virtualMemberexcelmode" />

								<label class="col-lg-12"><fmt:message
										key="tiles.views.user.index.table.excel.tip" /> </label>
								<spring:url value="/institution/user/getuserexcelmodel"
									var="getUserModel" />
								<form action="${getUserModel}" method="get" id="getusermodel">
									<div class="col-sm-12">
										<div class="col-sm-5 exportvirtual">
											<a href="javascript:void(0)" onclick="exportUserModel();"
												class="clear text-ellipsis"><strong class="block">
													<i class="fa fa-download fa-2x"></i> <fmt:message
														key="tiles.views.user.index.table.excel.download" />
											</strong> </a>
										</div>
									</div>
								</form>
								<spring:url value="/institution/user/importusers"
									var="importUsers" />
								<form action="${importUsers}" method="post" id="importUsers"
									enctype="multipart/form-data">
									<div class="col-sm-12 uploadvirmodel">
										<div class="col-sm-4">
											<a href="javascript:void();" class="a-upload"> <input
												type="file" name="file" id="file">
											<fmt:message key="tiles.views.user.index.table.excel.upload" />
											</a> <input type="hidden" name="${_csrf.parameterName}"
												value="${_csrf.token}" />
										</div>
										<div class="col-sm-8">
											<input type="text" class="showFileName" disabled value="">
										</div>
									</div>
								</form>
								<!--   导出用户表单临时存放 -->
								<spring:url value="/institution/user/exportuser"
									var="exportUserUrl" />
								<form action="${exportUserUrl}" method="get" id="exportuserFrm">
									<input type="hidden" value="" id="groupid" name="groupid">
								</form>
								<div class="col-sm-12">
									<a class="fileerrorTip" style="color: red"></a>
								</div>
								<div class="col-lg-offset-2 col-lg-10"></div>
							</div>
						</section>
						<div align="center">
							<input type="button" class="btn btn-md btn-primary"
								onclick="importUsers()"
								value="<fmt:message key='tiles.views.user.index.import'/>">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--Modal end-->

<!-- Modal start-->
<div class="modal fade" id="loadingModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<spring:url value="/resources/images/loading.gif" var="loadingGif" />
			<img class="col-sm-7" src="${loadingGif}"
				style="width: 200px; margin-left: 30%; margin-top: 30%;" />

		</div>
	</div>
</div>

<!-- Modal start-->

<!-- Modal start-->
<div class="modal fade" id="successModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close" onclick="refresh()">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
					<fmt:message key="tiles.views.user.index.modal.success.title" />
				</h4>
			</div>

			<div class="col-sm-12">
				<h1 class="text-danger"></h1>
				<div id="rownumbers"
					style="width: 500px; height: 100px; overflow: auto; border: 2px solid #E5E5E5;"></div>
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-danger" data-dismiss="modal"
					onclick="refresh()">
					<fmt:message
						key="tiles.views.institution.device.rule.delete.tip.confirm" />
				</button>
			</div>
		</div>
	</div>
</div>
<!--Modal end-->

<!-- Modal start-->
<div class="modal fade" id="erroModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
					<fmt:message key="tiles.views.user.index.modal.success.title" />
				</h4>
			</div>

			<div class="col-sm-12">
				<h1 class="text-danger"></h1>
				<div id="rownumbers2"
					style="width: 500px; height: 100px; overflow: auto; border: 2px solid #E5E5E5;"></div>
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-danger" data-dismiss="modal">
					<fmt:message
						key="tiles.views.institution.device.rule.delete.tip.confirm" />
				</button>
			</div>
		</div>
	</div>
</div>
<!--Modal end-->

