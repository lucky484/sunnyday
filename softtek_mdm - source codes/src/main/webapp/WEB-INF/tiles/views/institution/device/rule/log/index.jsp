<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<section class="row">
	<div class="blog-post">
		<div class="post-item">
			<header
				class="panel-heading b-l-l-none b-t-l-none b-b-l-1 b-r-l-none b-s-s">
				<c:if test="${menu[15].isshow==-1}">
				<spring:url value="/institution/device/rules"  var="rulesUrl"/>
				<a href="${rulesUrl}" class="btn btn-s-sm "><fmt:message key="tiles.views.institution.device.rule.heander.rule"/></a>
				</c:if>
				<c:if test="${menu[18].isshow==-1}">
				<spring:url value="/institution/device/rules/log"  var="rulesLogUrl"/>
				<a href="${rulesLogUrl}" class="btn btn-s-sm btn-primary"><fmt:message key="tiles.views.institution.device.rule.heander.records"/></a>
				</c:if>
						 <c:if test="${softtek_manager.user==null || softtek_manager.auth > 0 }">
						<button class="btn btn-sm btn-danger pull-right m-r btn-delete" type="button" data-toggle="modal" data-target="#delModal"><fmt:message key="tiles.views.institution.device.rule.log.deleteall"/></button>
						</c:if>
			</header>
			<section class="wrapper-md ">
				<div class="row">
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="table-responsive">
										<table id="table-rule-log" class="table table-striped b-t b-light">
											<thead>
												<tr class="">
													<th><fmt:message key="tiles.views.institution.device.rule.history.table.username"/></th>
													<th><fmt:message key="tiles.views.institution.device.rule.history.table.deviceno"/></th>
													<th><fmt:message key="tiles.views.institution.application.indexscript.device.sn"/></th>
													<th><fmt:message key="tiles.views.institution.device.rule.name"/></th>
													<th><fmt:message key="tiles.views.institution.device.rule.illeage.detail"/></th>
													<th><fmt:message key="tiles.views.institution.device.rule.illeage.pwd"/></th>
													<th><fmt:message key="tiles.views.institution.device.rule.illeage.time"/></th>
												</tr>
											</thead>
											<tbody>

											</tbody>
										</table>
								</div>

						</div>
					</div>
				</div>
			</section>
		</div>
	</div>
</section>
