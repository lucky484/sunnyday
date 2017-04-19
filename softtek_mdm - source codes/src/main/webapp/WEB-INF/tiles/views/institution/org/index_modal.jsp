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
							<header class="panel-heading font-bold"><fmt:message key="tiles.institution.org.department.insert" /></header>
							<div class="panel-body">
							<spring:url value="/institution/org/save" var="saveUrl" />
								<form role="form" action="${saveUrl}" method="post" data-parsley-validate id="insertForm">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
									<input type="hidden" name="parent.id" class="modal-frm-id" value="">
									<div class="form-group">
										<label class="control-label"><span class="font-bold"><fmt:message key="tiles.institution.org.department.parent.name"/>:</span><span
											class="modal-frm-sub"></span></label>
									</div>
									<div class="form-group">
										<label class="font-bold"><fmt:message key="tiles.institution.org.department.name"/><span style="color:red">*</span></label> 
										<input type="text" id="name" name="name" class="form-control" data-parsley-remote data-parsley-required="true" data-parsley-maxlength="50" data-parsley-trigger="focusout" data-parsley-remote-validator="checkname"
										 data-parsley-remote-message="<fmt:message key="parsley.department.name.exists"/>" data-parsley-maxlength-message="<fmt:message key="parsley.department.name.length" />"/>
									</div>
									<div class="form-group">
										<label class="font-bold"><fmt:message key="tiles.institution.org.department.mark"/></label>
										<textarea name="mark" rows="3" class="form-control" style="resize: none;"></textarea>
									</div>
									<div class="form-group">
										<label class="font-bold"><fmt:message key="tiles.institution.org.department.email"/></label> 
										<input type="email" name="email" class="form-control" data-parsley-type="email" data-parsley-trigger="focusout" data-parsley-maxlength="50"/>
									</div>
									<input type="submit" class="btn btn-md btn-primary pull-right"
										value="<fmt:message key='tiles.institution.org.department.insert'/>">
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
<div class="modal fade" id="moveModal" tabindex="-1" role="dialog"
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
							<header class="panel-heading font-bold"><fmt:message key="tiles.institution.org.department.move_pre"/><span class="department_name"></span><fmt:message key="tiles.institution.org.department.move_next"/></header>
							<div class="panel-body">
								<div id="moveTree"></div>
							</div>
						</section>
						<input type="submit" class="btn btn-md btn-primary pull-right"
							value="<fmt:message key="tiles.institution.org.department.confirm" />" id="submit">
					</div>
				</div>
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
				<h4 class="modal-title"><fmt:message key="tiles.institution.org.department.del.title"/></h4>
			</div>
			<div class="modal-body">
				<h3 class="text-danger"><fmt:message key="tiles.institution.org.department.del.msg" /></h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.del.cancel"/></button>
		        <button type="button" class="btn btn-danger" id="delBtn"><fmt:message key="tiles.institution.org.department.confirm"/></button>
		      </div>
		</div>
	</div>
</div>
<!--Modal end-->
<!-- Modal start-->
<div class="modal fade" id="show" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.org.department.del.title"/></h4>
			</div>
			<div class="modal-body">
				<h3 class="text-danger"><fmt:message key="tiles.institution.org.department.del.msg.done" /></h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.close" /></button>
		      </div>
		</div>
	</div>
</div>
<!--Modal end-->
<!-- Modal start-->
<div class="modal fade" id="moveDepartment" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.org.department.msg" /></h4>
			</div>
			<div class="modal-body">
				<h3 class="text-danger"><fmt:message key="tiles.institution.org.department.move.fail.msg" /></h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.close" /></button>
		      </div>
		</div>
	</div>
</div>
<!--Modal end-->
<!-- Modal start-->
<div class="modal fade" id="moveDepartmentP" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.org.department.msg" /></h4>
			</div>
			<div class="modal-body">
				<h3 class="text-danger"><fmt:message key="tiles.institution.org.department.move.fail.msg.two" /></h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.close" /></button>
		      </div>
		</div>
	</div>
</div>
<!--Modal end-->
<!-- Modal start-->
<div class="modal fade" id="moveDepartmentPr" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.org.department.msg" /></h4>
			</div>
			<div class="modal-body">
				<h3 class="text-danger"><fmt:message key="tiles.institution.org.department.move.fail.msg.two" /></h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.close" /></button>
		      </div>
		</div>
	</div>
</div>
<!--Modal end-->
<!-- Modal start-->
<div class="modal fade" id="sameNameMsg" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.org.department.msg" /></h4>
			</div>
			<div class="modal-body">
				<h3 class="text-danger"><fmt:message key="tiles.institution.org.department.move.fail.msg.tree" /></h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.close" /></button>
		      </div>
		</div>
	</div>
</div>
<!--Modal end-->
<!-- Modal start-->
<div class="modal fade" id="updateTip" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.org.department.msg" /></h4>
			</div>
			<div class="modal-body">
				<h3 class="text-danger"><fmt:message key="tiles.institution.org.department.update.msg" /></h3>
			</div>
			 <div class="modal-footer">
			 	<button type="button" class="btn btn-default" id="confirm" data-dismiss="modal"><fmt:message key="tiles.fragments.consumer.nav.sendmessage.success.confirm"/></button>
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.close" /></button>
		      </div>
		</div>
	</div>
</div>
<!--Modal end-->
<div class="modal fade" id="moveTip" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.org.department.msg" /></h4>
			</div>
			<div class="modal-body">
				<h3 class="text-danger"><fmt:message key="tiles.institution.org.department.move.msg" /></h3>
			</div>
			 <div class="modal-footer">
			 	<button type="button" class="btn btn-default" id="moveOrg" data-dismiss="modal"><fmt:message key="tiles.fragments.consumer.nav.sendmessage.success.confirm"/></button>
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.close" /></button>
		      </div>
		</div>
	</div>
</div>

<div class="modal fade" id="deleteTip" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.org.department.msg" /></h4>
			</div>
			<div class="modal-body">
				<h3 class="text-danger"><fmt:message key="tiles.institution.org.department.delete.msg" /></h3>
			</div>
			 <div class="modal-footer">
			 	<button type="button" class="btn btn-default" id="deleteOrg" data-dismiss="modal"><fmt:message key="tiles.fragments.consumer.nav.sendmessage.success.confirm"/></button>
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.close" /></button>
		      </div>
		</div>
	</div>
</div>