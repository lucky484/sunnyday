<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!-- Modal start-->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
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
							<header class="panel-heading font-bold"><fmt:message key="tiles.institution.vtl.group.model.label"/></header>
							<div class="panel-body">
								<spring:url value="/institution/vtl/save" var="modal_saveUrl" />
								<form id="addfrm" class="bs-example form-horizontal"
									action="${modal_saveUrl}" method="post"
									onkeydown="if(event.keyCode==13)return false;">
									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" />
									<div class="form-group">
										<label class="col-lg-3 control-label"><fmt:message key="tiles.institution.vtl.group.collection.name.label"/><span
											class="text-danger">*</span></label>
										<div class="col-lg-9">
											<input id="v_name" data-parsley-required="true"
												data-parsley-maxlength="50" data-parsley-remote
												data-parsley-remote-validator="existsValidate"
												data-parsley-trigger="blur"
												data-parsley-remote-message="<fmt:message key="parsley.account.exists.vtl"/>"
												type="text" name="virtualCollection.name"
												class="form-control new_name">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-3 control-label"><fmt:message key="tiles.institution.vtl.group.radio.label"/> <i
											class="i i-question" data-toggle="tooltip"
											data-placement="top"  data-original-title="<fmt:message key='tiles.institution.vtl.group.radio.tags.label'/>"></i>
										</label>
										<div class="col-lg-9">
											<div class="radio i-checks col-lg-3">
												<label> <input type="radio"
													name="virtualCollection.multiple" value="1"> <i></i>
													<fmt:message key="tiles.institution.vtl.group.radio.yes.label"/>
												</label>
											</div>
											<div class="radio i-checks col-lg-3">
												<label> <input type="radio"
													name="virtualCollection.multiple" value="0" checked>
													<i></i><fmt:message key="tiles.institution.vtl.group.radio.no.label"/>
												</label>
											</div>
										</div>
									</div>
									<div class="form-group">

										<input type="hidden" id="namelistmodal"
											name="virtualCollection.mark" value="" /> <label
											class="col-lg-3 control-label"><fmt:message key="tiles.institution.vtl.group.group.name.label"/></label>
										<div class="col-lg-9">
											<div class="input-group">
												<input type="text" class="form-control text-add-group"
													data-parsley-required="true" data-parsley-maxlength="50">
												<span class="input-group-btn">
													<button class="btn btn-default btn-add-group" type="button">
														<i class="fa fa-plus"></i><fmt:message key="tiles.institution.vtl.group.group.name.add.label"/>
													</button>
												</span>
											</div>
											<span class="help-block m-b-none"><fmt:message key="tiles.institution.vtl.group.group.name.sort.tags.label"/></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-3 control-label"><fmt:message key="tiles.institution.vtl.group.group.name.sort.label"/></label>
										<div class="col-lg-7">
											<div class=""
												style="min-height: 100px; max-height: 200px;">
												<!-- note list -->
												<ul id="note-items" class="list-group select-sort">
												</ul>
												<!-- <ul class="parsley-errors-list filled hidden" id="virgroupName"><li class="parsley-maxlength">虚拟组名称已经存在</li></ul> -->
											</div>
										</div>
										<div class="col-lg-2 m-l-n-md">
											<div class="btn-group-vertical m-b-sm">
												<button type="button" class="btn btn-default btn-arrow-up">
													<i class="fa fa-arrow-up"></i><fmt:message key="tiles.institution.vtl.group.up.label"/>
												</button>
												<button type="button" class="btn btn-default btn-arrow-down">
													<i class="fa fa-arrow-down"></i><fmt:message key="tiles.institution.vtl.group.down.label"/>
												</button>
											</div>
										</div>

									</div>
									<div class="form-group">
										<div class="col-lg-offset-2 col-lg-10">
											<input type="submit" id="submitaddfrm"
												class="btn btn-md btn-primary" value="<fmt:message key='tiles.institution.vtl.group.add.label'/>">
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
<!--Modal end-->

<!-- Modal start-->
<div class="modal fade" id="delModalTree" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
					<fmt:message key="tip.del.title" />
				</h4>
			</div>
			<div class="modal-body">
				<h3 class="text-danger"></h3>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<fmt:message key="tip.del.cancel" />
				</button>
				<button type="button" class="btn btn-danger" id="delcol">
					<fmt:message key="tip.del.confirm" />
				</button>
			</div>
		</div>
	</div>
</div>
<!--Modal end-->


<!-- Modal start-->
<div class="modal fade" id="modifytip" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
					<fmt:message key="tip.del.title" />
				</h4>
			</div>
			<div class="modal-body">
				<h3 class="text-danger"><fmt:message key='tiles.institution.vtl.group.delete.manager.messages'/></h3>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<fmt:message key="tip.del.cancel" />
				</button>
				<input type="submit" class="btn btn-danger" value="<fmt:message key='tip.del.confirm'/>" id="modifyColTips" form="updateColfrm" >
			</div>
		</div>
	</div>
</div>
<!--Modal end-->


<div class="modal fade" id="warningModalvtl" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
					<fmt:message key="tiles.views.user.index.modal.warning.title" />
				</h4>
			</div>
			<div class="modal-body">
				<h3 class="text-primary">
					<fmt:message key="tiles.views.user.index.modal.warning.content" />
				</h3>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"
					onclick="">
					<fmt:message
						key="tiles.views.institution.device.rule.delete.tip.cancel" />
				</button>
				<input type="submit" class="btn btn-primary" value="<fmt:message key='tip.del.confirm'/>" id="modifyColTips" form="updateColfrm" >
			</div>
		</div>
	</div>
</div>
<!--Modal end-->

<div class="modal fade" id="warningModalgroup" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
					<fmt:message key="tiles.views.user.index.modal.warning.title" />
				</h4>
			</div>
			<div class="modal-body">
				<h3 class="text-primary">
					<fmt:message key="tiles.views.user.index.modal.warning.content" />
				</h3>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"
					onclick="">
					<fmt:message
						key="tiles.views.institution.device.rule.delete.tip.cancel" />
				</button>
				<input type="submit" class="btn btn-primary" value="<fmt:message key='tip.del.confirm'/>" id="modifyTips" form="update" >
			</div>
		</div>
	</div>
</div>
<!--Modal end-->
