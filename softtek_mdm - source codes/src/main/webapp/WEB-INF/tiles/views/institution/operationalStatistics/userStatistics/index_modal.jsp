<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- 用户总数列表 -->
<div class="modal fade" id="viewTotalUserModel" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header b-b-l-none">
				<header class="col-sm-6 font-bold model-left">
					<span><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.span"/></span>
				</header>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body" style="max-height: 800px; overflow: scroll;">
				<div class="row">
					<div class="col-sm-12">
						<div class="panel panel-default">
							<header class="panel-heading font-bold">
								<span onclick="exportTotalUser();" class="btn btn-sm btn-success btn-rounded"  style="cursor:pointer"><i class="fa fa-external-link"></i>&nbsp;
									<fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.export"/></span>
							</header>
							<div class="panel-body">
								<div class="table-responsive">
									<input type="hidden" name="searchDate" id="totalUserSearchDate"/>
								 <div class="col-sm-12">
										<div class="col-sm-2 searchName"><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.username"/>：</div>
										<div class="col-sm-2 searchName_val">
											<input id="totalUser_real_name" type="text" class="input-sm form-control" size="16"/>
										</div>
										<div class="col-sm-2 searchName"><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.realname"/>：</div>
										<div class="col-sm-2 searchName_val">
											<input id="totalUser_user_name" type="text" class="input-sm form-control" size="16"/>
										</div>
										<div class="search_button head-right">
											<button class="btn btn-sm btn-default search_icon " type="button" onclick="searchTotalUser();"><span class="glyphicon glyphicon-search"></span>&nbsp;<fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.query"/></button>&nbsp;&nbsp;
											<button class="btn btn-sm btn-default reset_icon button_left" type="button" onclick="clearTotalUser();"><i class="fa fa-trash"></i>&nbsp;<fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.clear"/></button>
										</div>
									</div>
									<div class="col-sm-12">
										<table class="table table-striped b-t b-light" id="totalUserList">
											<thead>
												<tr>
												   <th><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.username"/></th>
												   <th><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.realname"/></th>
												   <th><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.depart"/></th>
												   <th><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.createTime"/></th>
												</tr>
											</thead>
											<tbody>
												
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 激活用户数列表 -->
<div class="modal fade" id="viewActiveUserModel" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header b-b-l-none">
				<header class="col-sm-6 font-bold model-left">
					<span><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.active.span"/></span>
				</header>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body" style="max-height: 800px; overflow: scroll;">
				<div class="row">
					<div class="col-sm-12">
						<div class="panel panel-default">
							<header class="panel-heading font-bold">
								<span onclick="exportActiveUser();" class="btn btn-sm btn-success btn-rounded"  style="cursor:pointer"><i class="fa fa-external-link"></i>&nbsp;
									<fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.export"/></span>
							</header>
							<div class="panel-body">
								<div class="table-responsive">
									<input type="hidden" name="searchDate" id="activeUserSearchDate"/>
								 <div class="col-sm-12 search_part">
										<div class="col-sm-2 searchName"><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.username"/>：</div>
										<div class="col-sm-2 searchName_val">
											<input id="activeUser_real_name" type="text" class="input-sm form-control" size="16"/>
										</div>
										<div class="col-sm-2 searchName"><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.realname"/>：</div>
										<div class="col-sm-2 searchName_val">
											<input id="activeUser_user_name" type="text" class="input-sm form-control" size="16"/>
										</div>
										<div class="search_button head-right">
											<button class="btn btn-sm btn-default search_icon " type="button" onclick="searchActiveUser();"><span class="glyphicon glyphicon-search"></span>&nbsp;<fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.query"/></button>&nbsp;&nbsp;
											<button class="btn btn-sm btn-default reset_icon button_left" type="button" onclick="clearActiveUser();"><i class="fa fa-trash"></i>&nbsp;<fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.clear"/></button>
										</div>
									</div>
									<div class="col-sm-12">
										<table class="table table-striped b-t b-light" id="activeUserList">
											<thead>
												<tr>
												   <th><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.username"/></th>
												   <th><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.realname"/></th>
												   <th><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.depart"/></th>
												   <th><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.createTime"/></th>
												</tr>
											</thead>
											<tbody>
												
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 未激活用户数列表 -->
<div class="modal fade" id="viewInActiveUserModel" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header b-b-l-none">
				<header class="col-sm-6 font-bold model-left">
					<span><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.inactive.span"/></span>
				</header>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body" style="max-height: 800px; overflow: scroll;">
				<div class="row">
					<div class="col-sm-12">
						<div class="panel panel-default">
							<header class="panel-heading font-bold">
								<span onclick="exportInActiveUser();" class="btn btn-sm btn-success btn-rounded"  style="cursor:pointer"><i class="fa fa-external-link"></i>&nbsp;
									<fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.export"/></span>
							</header>
							<div class="panel-body">
								<div class="table-responsive">
									<input type="hidden" name="searchDate" id="inActiveUserSearchDate"/>
								 <div class="col-sm-12 search_part">
										<div class="col-sm-2 searchName"><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.username"/>：</div>
										<div class="col-sm-2 searchName_val">
											<input id="inActiveUser_real_name" type="text" class="input-sm form-control" size="16"/>
										</div>
										<div class="col-sm-2 searchName"><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.realname"/>：</div>
										<div class="col-sm-2 searchName_val">
											<input id="inActiveUser_user_name" type="text" class="input-sm form-control" size="16"/>
										</div>
										<div class="search_button head-right">
											<button class="btn btn-sm btn-default search_icon " type="button" onclick="searchInActiveUser();"><span class="glyphicon glyphicon-search"></span>&nbsp;<fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.query"/></button>&nbsp;&nbsp;
											<button class="btn btn-sm btn-default reset_icon button_left" type="button" onclick="clearInActiveUser();"><i class="fa fa-trash"></i>&nbsp;<fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.clear"/></button>
										</div>
									</div>
									<div class="col-sm-12">
										<table class="table table-striped b-t b-light" id="inActiveUserList">
											<thead>
												<tr>
												  <th><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.username"/></th>
												  <th><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.realname"/></th>
												  <th><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.depart"/></th>
												  <th><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.createTime"/></th>
												</tr>
											</thead>
											<tbody>
												
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 删除用户数列表 -->
<div class="modal fade" id="viewDeleteUserModel" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header b-b-l-none">
				<header class="col-sm-6 font-bold model-left">
					<span><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.delete.span"/></span>
				</header>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body" style="max-height: 800px; overflow: scroll;">
				<div class="row">
					<div class="col-sm-12">
						<div class="panel panel-default">
							<header class="panel-heading font-bold">
								<span onclick="exportDeleteUser();" class="btn btn-sm btn-success btn-rounded"  style="cursor:pointer"><i class="fa fa-external-link"></i>&nbsp;
									<fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.export"/></span>
							</header>
							<div class="panel-body">
								<div class="table-responsive">
									<input type="hidden" name="searchDate" id="deleteUserSearchDate"/>
								 <div class="col-sm-12 search_part">
										<div class="col-sm-2 searchName"><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.username"/>：</div>
										<div class="col-sm-2 searchName_val">
											<input id="deleteUser_real_name" type="text" class="input-sm form-control" size="16"/>
										</div>
										<div class="col-sm-2 searchName"><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.realname"/>：</div>
										<div class="col-sm-2 searchName_val">
											<input id="deleteUser_user_name" type="text" class="input-sm form-control" size="16"/>
										</div>
										<div class="search_button head-right">
											<button class="btn btn-sm btn-default search_icon " type="button" onclick="searchDeleteUser();"><span class="glyphicon glyphicon-search"></span>&nbsp;<fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.query"/></button>&nbsp;&nbsp;
											<button class="btn btn-sm btn-default reset_icon button_left" type="button" onclick="clearDeleteUser();"><i class="fa fa-trash"></i>&nbsp;<fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.clear"/></button>
										</div>
									</div>
									<div class="col-sm-12">
										<table class="table table-striped b-t b-light" id="deleteUserList">
											<thead>
												<tr>
												   <th><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.username"/></th>
												   <th><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.realname"/></th>
												   <th><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.depart"/></th>
												   <th><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.modal.total.createTime"/></th>
												</tr>
											</thead>
											<tbody>
												
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

