<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="blog-post">
	<div class="post-item">
		<header
			class="panel-heading b-l-l-none b-t-l-none b-b-l-1 b-r-l-none b-s-s">
			<c:if test="${menu[6].isshow==-1}">
				<spring:url value="/institution/vtl" var="vtl" />
				<a href="${vtl}" class="btn btn-s-sm btn-primary"><fmt:message key="tiles.institution.vtl.group.action.group.label"/></a>
			</c:if>
			<c:if test="${menu[7].isshow==-1}">
				<spring:url value="/institution/vtl/member" var="member" />
				<a href="${member}"
					class="btn btn-s-sm ${menu[6].isshow!=-1?'btn-primary':''}"><fmt:message key="tiles.institution.vtl.group.action.member.label"/></a>
			</c:if> 
		</header>
		<section class="wrapper-md ">
			<div class="row">
				<div class="scrollable aside-md col-sm-3">
					<div class="btn-toolbar">
						<c:if test="${softtek_manager.user==null || softtek_manager.auth > 0 }"><button
							class="btn btn-default btn-sm btn-bg b-none text-info-ipt myModal"
							data-toggle="modal" data-target="#myModal" onclick="addModal()">
							<i class="fa fa-plus"></i> <span><fmt:message key="tiles.institution.vtl.group.add.label"/></span> 
						</button>
						<button
							class="btn btn-default btn-sm btn-bg b-none text-info-ipt pull-right m-r btn-delete"
							>
							<i class="fa fa-times"></i> <span><fmt:message key="tiles.institution.vtl.group.delete.label"/></span>
						</button>
						</c:if>
					</div>
					<div id="tree">
						<c:forEach items="${vtlcollist}" var="vtlcol" varStatus="i">

							<ul onclick="liClick(this);" vtlcolid="${vtlcol.id}"
								id="tree-list" class="">
								<li data-selected="${vtlcol.multiple}" create-by="${vtlcol.createBy}" data-type="parent"
									data-id="${vtlcol.id}" vtlcolName="${vtlcol.name}"
									id="select${i.index}" class="list-group"
									style="font-size: 20px;"><span
									class="icon expand-icon glyphicon glyphicon-plus"
									plusid="${vtlcol.id}"></span> <span
									class="icon expand-icon glyphicon glyphicon-minus hidden"
									minusid="${vtlcol.id}"></span> <strong>${vtlcol.name}</strong></li>
							</ul>
							<ul id="tree-gourp-list" class="hidden">
								<c:forEach items="${vtllist}" var="vtl">
									<c:if test="${vtlcol.id eq vtl.virtualCollection.id}">
										<ul>
											<li onclick="liClick2(this);" create-by="${vtl.createBy}"data-type="children"
												parentId="${vtlcol.id}" data-id="${vtl.id}"
												data-name="${vtl.name}" id="selectChild${vtlcol.id}"
												style="font-size: 15px;">${vtl.name}</li>
										</ul>
									</c:if>
								</c:forEach>
							</ul>

						</c:forEach>
					</div>
				</div>
				<div class="col-sm-9">
					<section class="panel panel-default">
						<header class="panel-heading font-bold"><fmt:message key="tiles.institution.vtl.group.basic.label"/></header>
						<div class="panel-body">
							<div class="d_base hidden">
								<spring:url value="/institution/vtl/update" var="update" />
								<form class="bs-example form-horizontal" method="post"
									id="update" action="${update}" data-parsley-validate
									onkeydown="if(event.keyCode==13)return false;">
									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" /> <label
										class="col-lg-2 control-label"><fmt:message key="tiles.institution.vtl.group.group.name.label"/></label>
									<div class="col-lg-10">
										<input type="text" class="form-control"
											data-parsley-required="true" data-parsley-maxlength="50"
											data-parsley-remote
											data-parsley-remote-validator="existsValidate2"
											data-parsley-trigger="blur"
											data-parsley-remote-message="<fmt:message key="parsley.account.exists.vtl.group"/>"
											id="virtlgroup_name" 
											 name="name"> <input
											type="hidden" id="virtlgroup_id" name="id" value="">
										<div class="form-group">
											<div class="col-lg-offset-2 col-lg-10">
												<c:if test="${softtek_manager.auth > 0 || softtek_manager.user==null}">
												<input type="submit" class="btn btn-md btn-primary "
													id="updateLs" value="<fmt:message key='tiles.institution.vtl.group.modify.label'/>">
											    </c:if>
											</div>
										</div>
									</div>
								</form>
							</div>

							<spring:url value="/institution/vtl/updateCol" var="updateCol" />
							<form class="bs-example form-horizontal hidden" method="post"
								id="updateColfrm" action="${updateCol}"
								onkeydown="if(event.keyCode==13)return false;">
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
								<div class="form-group">
									<label class="col-lg-2 control-label"><fmt:message key="tiles.institution.vtl.group.collection.name.label"/><span
										class="text-danger">*</span></label>
									<div class="col-lg-10">
										<input type="hidden" id="virtualCol_id"
											name="virtualCollection.id" value=""> <input
											type="text" class="form-control" data-parsley-required="true"
											data-parsley-maxlength="50" data-parsley-remote
											data-parsley-remote-validator="existsValidate1"
											data-parsley-trigger="blur"
											data-parsley-remote-message="<fmt:message key='parsley.account.exists.vtl'/>"
											name="virtualCollection.name" id="virtualCol_name">
									</div>
								</div>
								<div class="form-group">
									<label class="col-lg-2 control-label"><fmt:message key="tiles.institution.vtl.group.radio.label"/>
									 <i
										class="i i-question" data-toggle="tooltip" data-placement="top" title="<fmt:message key='tiles.institution.vtl.group.radio.tags.label'/>"></i>
									</label>
									<div class="col-lg-10">
										<div class="radio i-checks col-lg-3">
											<label> <input type="radio"
												name="virtualCollection.multiple" value="1"> <i></i>
												<fmt:message key="tiles.institution.vtl.group.radio.yes.label"/>
											</label>
										</div>
										<div class="radio i-checks col-lg-3">
											<label> <input type="radio"
												name=virtualCollection.multiple value="0" checked> <i></i>
												<fmt:message key="tiles.institution.vtl.group.radio.no.label"/>
											</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-lg-2 control-label"><fmt:message key="tiles.institution.vtl.group.group.name.label"/></label>
									<div class="col-lg-10">
										<div class="input-group">
											<input type="text" class="form-control text-add-group-1"
												data-parsley-required="true" data-parsley-maxlength="50">
											<span class="input-group-btn">
												<button class="btn btn-default btn-add-group-1"
													type="button">
													<i class="fa fa-plus"></i><fmt:message key="tiles.institution.vtl.group.group.name.add.label"/>
												</button>
											</span>
										</div>
										<span class="help-block m-b-none"><fmt:message key="tiles.institution.vtl.group.group.name.sort.tags.label"/></span>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.institution.vtl.group.group.name.sort.label"/></label> <input
											type="hidden" id="namelist" name="name" value="">
										<div class="col-lg-8">
											<div class="padder"
												style="min-height: 100px; max-height: 200px;">
												<!-- note list -->
												<ul id="note-items-1" class="list-group select-sort">

												</ul>
											</div>
										</div>
										<div class="col-lg-2 m-l-n-md">
											<div class="btn-group-vertical m-b-sm">
												<button type="button" class="btn btn-default btn-arrow-up-1">
													<i class="fa fa-arrow-up"></i><fmt:message key="tiles.institution.vtl.group.up.label"/>
												</button>
												<button type="button"
													class="btn btn-default btn-arrow-down-1">
													<i class="fa fa-arrow-down"></i><fmt:message key="tiles.institution.vtl.group.down.label"/>
												</button>
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="col-lg-offset-2 col-lg-10">
										<c:if test="${softtek_manager.auth > 0 || softtek_manager.user==null}">
											<input type="button" class="btn btn-md btn-primary "
												id="updateL" value="<fmt:message key='tiles.institution.vtl.group.modify.label'/>"> 
											<button type="button" class="btn btn-md btn-primary "
												id="btn-reset" onclick="btnReset()"><fmt:message key="tiles.institution.vtl.group.reset.label"/></button>
										</c:if>
										</div>
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

