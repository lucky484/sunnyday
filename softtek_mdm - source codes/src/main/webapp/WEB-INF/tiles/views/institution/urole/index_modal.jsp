<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>



<!-- Modal start-->
<div class="modal fade" id="addRoleModal" tabindex="-1" role="dialog"
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
							<header class="panel-heading font-bold"><fmt:message key="tiles.views.institution.urule.newly"/><span class="text-warning pull-right">(<fmt:message key="tiles.views.institution.urule.newly.has"/><span class="text-danger">*</span><fmt:message key="tiles.views.institution.urule.newly.required"/>)</span></header>
							<div class="panel-body">
							<spring:url value="/institution/urole/create" var="addRoleUrl"/>
								<form id="addRoleFrm" class="bs-example form-horizontal" action="${addRoleUrl}" method="post">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
									<input type="hidden" value="" name="rids">
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.urule.rulename"/><span class="text-danger">*</span></label>
										<div class="col-lg-10">
											<input type="text" autocomplete="off" class="form-control role-name" name="name" data-parsley-required="true" data-parsley-maxlength="20"
											data-parsley-remote data-parsley-remote-validator="existsValidate" data-parsley-trigger="blur" data-parsley-remote-message="<fmt:message key='tiles.views.institution.urule.newly.name.exists'/>">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.urule.describe"/></label>
										<div class="col-lg-10">
											<textarea rows="5" cols="20" class="form-control"
												style="resize: none;" name="mark"></textarea>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.urule.newly.rule.choose"/><span class="text-danger">*</span>
										<br>
										<span id="ckrequire-error" class="text-danger hidden"><fmt:message key="tiles.views.institution.urule.newly.rule.mustrequired"/></span>
										</label>
										<div class="col-lg-10">
										<c:if test="${menuTree!=null}">
											<c:forEach items="${menuTree.children}" var="menu">
												<div>
													<div class="checkbox i-checks col-lg-12">
														<i class="fa fa-chevron-right m-r-none" style="border: 0px;cursor:pointer;" onclick="chooseAuth(this)"></i>
														<label><input type="checkbox" data-head="true" data-last="false" value="${menu.current.id}" onclick="chooseMenu(this)"><i></i>
														<span class="font-bold">${menu.current.name}</span>
														</label>
													</div>
													<c:if test="${menu.children!=null}">
														<c:forEach items="${menu.children}" var="m">
															<div class="hidden">
																<div class="checkbox i-checks col-lg-12 m-l-lg">
																	<label><input type="checkbox" onclick="chooseMenu(this)" data-head="false" data-last="false" value="${m.current.id}"><i></i>${m.current.name}</label>
																</div>
																<c:if test="${m.children!=null}">
																	<div>
																		<c:forEach items="${m.children}" var="c" varStatus="k">
																			<div class="checkbox i-checks pull-left ${k.index!=0?'m-l-lg':'' }" style="${k.index==0?'margin-left:60px;':''}">
																				<label><input type="checkbox" onclick="chooseMenu(this)" data-head="false" data-last="true" value="${c.current.id}"><i></i>${c.current.name}</label>
																			</div>
																		</c:forEach>
																	</div>
																</c:if>
															</div>
														</c:forEach>
													</c:if>
												</div>
											</c:forEach>
										</c:if>
										</div>
									</div>
									<input type="submit"
										class="btn btn-s-md btn-primary pull-right add-Role" value="<fmt:message key='tiles.views.institution.urule.newly.add'/>">
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
<div class="modal fade" id="queryRoleModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
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
							<header class="panel-heading font-bold"><fmt:message key="tiles.views.institution.urule.query.info"/></header>
							<div class="panel-body">
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.urule.rulename"/></label>
										<div class="col-lg-10">
											<input type="text" autocomplete="off" class="form-control q-r-n" disabled readonly>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.device.rule.memo"/></label>
										<div class="col-lg-10">
											<textarea rows="5" cols="20" class="form-control q-r-m"
												style="resize: none;" disabled readonly></textarea>
										</div>
									</div>
									<div class="form-group">
									<label class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.urule.newly.rule.choose"/></label>
										<div class="col-lg-10">
										<c:if test="${menuTree!=null}">
											<c:forEach items="${menuTree.children}" var="menu">
												<div>
													<div class="checkbox i-checks col-lg-12">
														<label> <input type="checkbox" disabled="disabled" data-head="true" data-last="false" value="${menu.current.id}" onclick="chooseMenu(this)"><i></i>
														<span class="font-bold">${menu.current.name}</span>
														</label>
													</div>
													<c:if test="${menu.children!=null}">
														<c:forEach items="${menu.children}" var="m">
															<div>
																<div class="checkbox i-checks col-lg-12 m-l-lg">
																	<label><input type="checkbox" disabled onclick="chooseMenu(this)" data-head="false" data-last="false" value="${m.current.id}"><i></i>${m.current.name}</label>
																</div>
																<c:if test="${m.children!=null}">
																	<div>
																		<c:forEach items="${m.children}" var="c" varStatus="k">
																			<div class="checkbox i-checks pull-left ${k.index!=0?'m-l-lg':'' }" style="${k.index==0?'margin-left:60px;':''}">
																				<label><input type="checkbox" disabled data-head="false" data-last="true" value="${c.current.id}"><i></i>${c.current.name}</label>
																			</div>
																		</c:forEach>
																	</div>
																</c:if>
															</div>
														</c:forEach>
													</c:if>
												</div>
											</c:forEach>
										</c:if>
										</div>
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
<div class="modal fade" id="modifyRoleModal" tabindex="-1" role="dialog"
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
							<header class="panel-heading font-bold"><fmt:message key="tiles.views.institution.urule.modify.title"/><span class="text-warning pull-right">(<fmt:message key="tiles.views.institution.urule.newly.has"/><span class="text-danger">*</span><fmt:message key="tiles.views.institution.urule.newly.required"/>)</span></header>
							<div class="panel-body">
							<spring:url value="/institution/urole/update" var="updateRoleUrl"/>
								<form id="updateRoleFrm" class="bs-example form-horizontal" action="${updateRoleUrl}" method="post">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
									<input type="hidden" name="id" class="r_id" value="">
									<input type="hidden" value="" name="m_rids">
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.urule.rulename"/><span class="text-danger">*</span></label>
										<div class="col-lg-10">
											<input type="text" autocomplete="off" class="form-control m-r-n role-modify-name"  name="name" data-parsley-required="true"
											data-parsley-remote data-parsley-remote-validator="modifyExistsValidate" data-parsley-trigger="blur" data-parsley-remote-message="<fmt:message key='tiles.views.institution.urule.newly.name.exists'/>">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.device.rule.memo"/></label>
										<div class="col-lg-10">
											<textarea rows="5" cols="20" class="form-control m-r-m"
												style="resize: none;" name="mark"></textarea>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.urule.newly.rule.choose"/><span class="text-danger">*</span>
										<br>
										<span id="ckrequire-modify-error" class="text-danger hidden"><fmt:message key="tiles.views.institution.urule.newly.rule.mustrequired"/></span>
										</label>
										<div class="col-lg-10">
										<c:if test="${menuTree!=null}">
											<c:forEach items="${menuTree.children}" var="menu">
												<div>
													<div class="checkbox i-checks col-lg-12">
													<i class="fa fa-chevron-right m-r-none" style="border: 0px;cursor:pointer;" onclick="chooseAuth(this)"></i>
														<label> <input type="checkbox"  data-head="true" data-last="false" value="${menu.current.id}" onclick="chooseMenu(this)"><i></i>
														<span class="font-bold">${menu.current.name}</span>
														</label>
													</div>
													<c:if test="${menu.children!=null}">
														<c:forEach items="${menu.children}" var="m">
															<div class="hidden">
																<div class="checkbox i-checks col-lg-12 m-l-lg">
																	<label><input type="checkbox" onclick="chooseMenu(this)" data-head="false" data-last="false" value="${m.current.id}"><i></i>${m.current.name}</label>
																</div>
																<c:if test="${m.children!=null}">
																	<div>
																		<c:forEach items="${m.children}" var="c" varStatus="k">
																			<div class="checkbox i-checks pull-left ${k.index!=0?'m-l-lg':'' }" style="${k.index==0?'margin-left:60px;':''}">
																				<label><input type="checkbox"  onclick="chooseMenu(this)" data-head="false" data-last="true" value="${c.current.id}"><i></i>${c.current.name}</label>
																			</div>
																		</c:forEach>
																	</div>
																</c:if>
															</div>
														</c:forEach>
													</c:if>
												</div>
											</c:forEach>
										</c:if>
										</div>
									</div>
									<input type="submit"
										class="btn btn-s-md btn-primary pull-right add-Role" value="<fmt:message key='tiles.views.customer.personal.index.updbtn'/>">
								</form>
							</div>
						</section>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- Modal start-->
<div class="modal fade" id="delModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
	<input type="hidden" value="" class="role-del-pk">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.views.institution.device.rule.delete.tip.header"/></h4>
			</div>
			<div class="modal-body">
				<h3 class="text-danger"><fmt:message key="tiles.views.institution.urule.delete.content"/></h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.views.institution.device.rule.delete.tip.cancel"/></button>
		        <button type="button" class="btn btn-danger" onclick="truncateRole()"><fmt:message key="tiles.views.institution.device.rule.delete.tip.confirm"/></button>
		      </div>
		</div>
	</div>
</div>
<!--Modal end-->
<!--Modal end-->
