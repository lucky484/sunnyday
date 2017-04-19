<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spring:url value="/resources/images/search.png" var="search" />
<spring:url value="/resources/images/clean.png" var="clean" />
<div class="blog-post">
	<div class="post-item">
		<section class="wrapper-md ">
				<div class="row">
					<div class="col-sm-12">
						<div class="panel panel-default">
							<header class="panel-heading font-bold" style="border-bottom-color: #333">
							<span onclick="addOrganization();" class="btn btn-sm btn-success btn-rounded"  style="cursor:pointer"><i class="fa fa-plus"></i>&nbsp;
								<fmt:message key="tiles.views.admin.index.organization.add"/></span>
							<span onclick="exportOrganization();" class="btn btn-sm btn-success btn-rounded"  style="cursor:pointer"><i class="fa fa-external-link"></i>&nbsp;
								<fmt:message key="tiles.views.admin.index.organization.export"/></span>
							</header>
							<spring:url value="/admin/index/export" var="exportUrl"/>
							<form id="exportFrm" class="hidden" action="${exportUrl}" method="post">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<input id="export-org" type="hidden" name="orgType" value=""/>
								<input id="export-name" type="hidden" name="name" value=""/>
							</form>
							<div class="panel-body">
				    	   <div class="search-toggle">
						     <a hidfocus="true"><fmt:message key="tiles.institution.comm.expand.search.tip" /></a>
						   </div>
						   <div class="search-mod" style="display: none;">
						        <ul class="search-list" style="margin-left:98px;">
			   						<li class="search-item ">
			   							<label class="search-label" style="width:76px"><fmt:message key="tiles.views.admin.index.organization.orgType"/>:</label>
			   							<input type="text" id="orgType" name="orgType" class="search-text" style="width:120px;">
			   						</li>
			   					</ul>
			   					<ul class="search-list" style="margin-left:98px;">
			   						<li class="search-item ">
			   							<label class="search-label" style="width:76px"><fmt:message key="tiles.views.admin.index.organization.orgName"/>:</label>
			   							<input type="text" id="name" name="name" class="search-text" style="width:120px;">
			   						</li>
			   					</ul>
								<div class="type-choice">
									<a class="button-search" type="button" onclick="javascript:searchOrganizationLists();"><img src="${search}"><span><fmt:message key="tiles.institution.policy.policy.search" /></span></a>
									<a class="button-search" type="button" onclick="javascript:cleanOrganizationLists();"><img src="${clean}"><span><fmt:message key="tiles.institution.policy.policy.clean" /></span></a>
								</div>
			   				</div>
								<div class="table-responsive">
										<table class="table table-striped b-t b-light" id="organizationList">
											<thead>
												<tr>
												   <th><fmt:message key="tiles.views.admin.index.organization.table.orgType"/></th>
												   <th><fmt:message key="tiles.views.admin.index.organization.table.orgName"/></th>
												   <th><fmt:message key="tiles.views.admin.index.organization.table.create_by"/></th>
												   <th><fmt:message key="tiles.views.admin.index.organization.table.totalUsers"/></th>
												   <th><fmt:message key="tiles.views.admin.index.organization.table.totalDevices"/></th>
												   <th><fmt:message key="tiles.views.admin.index.organization.table.licenseCount"/></th>
												   <th><fmt:message key="tiles.views.admin.index.organization.table.useUsers"/></th>
												   <th><fmt:message key="tiles.views.admin.index.organization.table.status"/></th>
												   <th><fmt:message key="tiles.views.admin.index.organization.table.operation"/></th>
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
