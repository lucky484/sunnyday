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
				<a href="${rulesUrl}" class="btn btn-s-sm btn-primary"><fmt:message key="tiles.views.institution.device.rule.heander.rule" /></a>
				</c:if>
				<c:if test="${menu[18].isshow==-1}">
				<spring:url value="/institution/device/rules/log"  var="rulesLogUrl"/>
				<a href="${rulesLogUrl}" class="btn btn-s-sm"><fmt:message key="tiles.views.institution.device.rule.heander.records"/></a>
				</c:if>
			</header>
			<section class="wrapper-md ">
				<div class="row">
					<div class="col-sm-12">
							<div class="panel panel-default">
								 <c:if test="${softtek_manager.user==null || softtek_manager.auth > 0 }">
								<header class="panel-heading">
									<a href="javascript:void(0);"
										class="btn btn-primary btn btn-sm btn-success btn-rounded" onclick="addNewly()"> <i
										class="fa fa-plus m-r-xs"></i><fmt:message key="tiles.views.institution.device.rule.heander.rule" />
									</a>
								</header>
								</c:if>
								<div class="panel-body">
								  <input type="hidden" id="userIds" /> 
								  <input type="hidden"id="userNames" />
						   <div class="search-toggle">
						     <a hidfocus="true"><fmt:message key="tiles.institution.comm.expand.search.tip" /></a>
						   </div>
						   <div class="search-mod" style="display: none;">
			   					<ul class="search-list">
			   						<li class="search-item ">
			   							<label class="search-label"><fmt:message key="tiles.views.institution.device.rule.name"/>:</label>
			   							<input type="text" id="deviceRuleName" name="deviceRuleName" class="search-text" style="width:120px;">
			   						</li>
			   					</ul>
			   					<ul class="search-list">
			   						<li class="search-item">
			   							<label class="search-label"><fmt:message key="tiles.views.institution.device.rule.status"/>:</label>
			   							<div class="Js_dropMod select-box inline-select select-200" style="width:130px;">
											<input type="hidden" class="Js_hiddenVal" id="roletype" name="roletype" value="" />
											<span class="Js_curVal"><input type="text" value="<fmt:message key="tiles.views.institution.device.rule.status.all"/>"></span>
											<ul class="select-list" style="width:130px;">
												<li class="select-item"><a href="javascript:;" rel=""><fmt:message key="tiles.views.institution.device.rule.status.all"/></a></li>
												<li class="select-item"><a href="javascript:;" rel="0"><fmt:message key="tiles.views.institution.device.rule.status.disable"/></a></li>
												<li class="select-item"><a href="javascript:;" rel="1"><fmt:message key="tiles.views.institution.device.rule.status.enable"/></a></li>
											</ul>
										</div>
			   						</li>
			   					</ul>
			   					<ul class="search-list">
			   						<li class="search-item">
			   							<label class="search-label"><fmt:message key="tiles.views.institution.device.rule.platform"/>:</label>
			   							<div class="Js_dropMod select-box inline-select select-200" style="width:130px;">
			   							<input type="hidden" class="Js_hiddenVal" id="e_platform" name="e_platform" value="" />
			   							<span class="Js_curVal"><input type="text" value="-----"></span>
											<ul class="select-list" style="width:130px;">
												<li class="select-item"><a href="javascript:;" rel="1"><fmt:message key="tiles.views.institution.device.rule.add.common"/></a></li>
												<li class="select-item"><a href="javascript:;" rel="2"><fmt:message key="tiles.views.institution.device.rule.add.android"/></a></li>
												<li class="select-item"><a href="javascript:;" rel="3"><fmt:message key="tiles.views.institution.device.rule.add.ios"/></a></li>
											</ul>
										</div>
			   						</li>
			   					</ul>
								<div class="type-choice">
									<a class="button-search" type="button" onclick="javascript:searchRole();"><i class="fa fa-search"></i><span><fmt:message key="tiles.institution.policy.policy.search" /></span></a>
									<a class="button-search" type="button" onclick="javascript:cleanRole();"><i class="fa fa-trash-o"></i><span><fmt:message key="tiles.institution.policy.policy.clean" /></span></a>
								</div>
			   			</div>
								<div class="table-responsive">
										<table id="table-rule" class="table table-striped b-t b-light">
											<thead>
												<tr class="">
													<th class=""><fmt:message key="tiles.views.institution.device.rule.status"/></th>
													<th><fmt:message key="tiles.views.institution.device.rule.name"/></th>
													<th><fmt:message key="tiles.views.institution.device.rule.memo"/></th>
													<th><fmt:message key="tiles.views.institution.device.rule.platform"/></th>
													<th><fmt:message key="tiles.views.institution.device.rule.threestatus"/></th>
													<th><fmt:message key="tiles.views.institution.device.rule.updated_at"/></th>
													<th><fmt:message key="tiles.views.institution.device.rule.nexttest"/></th>
													<th><fmt:message key="tiles.views.institution.device.rule.opera"/></th>
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
			</section>
		</div>
	</div>
</section>
