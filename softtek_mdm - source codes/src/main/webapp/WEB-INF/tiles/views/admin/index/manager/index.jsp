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
							<span onclick="addManager();" class="btn btn-sm btn-success btn-rounded"  style="cursor:pointer"><i class="fa fa-plus"></i>&nbsp;
								<fmt:message key="tiles.views.admin.index.manager.add"/></span>
							</header>
							<div class="panel-body">
							      <div class="search-toggle">
						     <a hidfocus="true"><fmt:message key="tiles.institution.comm.expand.search.tip" /></a>
						   </div>
						   <div class="search-mod" style="display: none;">
						        <ul class="search-list" style="margin-left:98px;">
			   						<li class="search-item ">
			   							<label class="search-label" style="width:76px"><fmt:message key="tiles.views.admin.index.manager.username"/>:</label>
			   							<input type="text" id="username" name="username" class="search-text" style="width:120px;">
			   						</li>
			   					</ul>
			   					<ul class="search-list">
			   						<li class="search-item ">
			   							<label class="search-label" style="width:76px"><fmt:message key="tiles.views.admin.index.manager.name"/>:</label>
			   							<input type="text" id="name" name="name" class="search-text" style="width:120px;">
			   						</li>
			   					</ul>
			   					<ul class="search-list">
			   						<li class="search-item ">
			   							<label class="search-label" style="width:90px"><fmt:message key="tiles.views.admin.index.manager.org"/>:</label>
			   							<div class="Js_dropMod select-box inline-select select-200" style="width:130px;">
											<input type="hidden" class="Js_hiddenVal" id="orgType" name="orgType" value="" />
											<span class="Js_curVal"><input type="text" value="<fmt:message key="tiles.views.admin.index.manager.selectOrg"/>"></span>
											<ul class="select-list" style="width:130px;">
											    <c:if test="${fn:length(organizationList) > 0}">
												<li class="select-item"><a href="javascript:;" rel=""><fmt:message key="tiles.views.admin.index.manager.selectOrg"/></a></li>
												<c:forEach var="organization" items="${organizationList}" >
												<li class="select-item"><a href="javascript:;" rel="${organization.id}">${organization.name}</a></li>
												</c:forEach>
												</c:if> 
											</ul>
										</div>
			   						</li>
			   					</ul>
								<div class="type-choice">
									<a class="button-search" type="button" onclick="javascript:searchManagerLists();"><img src="${search}"><span><fmt:message key="tiles.institution.policy.policy.search" /></span></a>
									<a class="button-search" type="button" onclick="javascript:cleanManagerLists();"><img src="${clean}"><span><fmt:message key="tiles.institution.policy.policy.clean" /></span></a>
								</div>
			   				</div>
								<div class="table-responsive">
					<%-- 			 <div class="col-sm-12 search_part">
										<div class="col-sm-1 searchName"><fmt:message key="tiles.views.admin.index.manager.username"/>:</div>
										<div class="col-sm-2 searchName_val">
											<input id="username" type="text" class="input-sm form-control"/>
										</div>
										<div class="col-sm-1 searchName"><fmt:message key="tiles.views.admin.index.manager.name"/>:</div>
										<div class="col-sm-2 searchName_val">
											<input id="name" type="text" class="input-sm form-control"/>
										</div>
										<div class="col-sm-1 searchName"><fmt:message key="tiles.views.admin.index.manager.org"/>:</div>
										<div class="col-sm-2 searchName_val">
											<select name="orgType" class="form-control input-md pos" id="orgType" >
						             			<c:if test="${fn:length(organizationList) > 0}">
							             			<option value=""><fmt:message key="tiles.views.admin.index.manager.selectOrg"/></option>
							             			<c:forEach var="organization" items="${organizationList}" >
							             				<option value="${organization.id}">${organization.name}</option>
							             			</c:forEach>
						             			</c:if> 
											</select>
										</div>
										<div class="col-sm-2 search_button">
											<button class="btn btn-sm btn-default search_icon" type="button" onclick="searchManagerLists();">&nbsp;<fmt:message key="tiles.views.admin.index.manager.search"/></button>&nbsp;
											<button class="btn btn-sm btn-default reset_icon" type="button" onclick="cleanManagerLists();">&nbsp;<fmt:message key="tiles.views.admin.index.manager.clear"/></button>
										</div>
									</div> --%>
										<table class="table table-striped b-t b-light" id="managerList">
											<thead>
												<tr>
												   <th><fmt:message key="tiles.views.admin.index.manager.table.username"/></th>
												   <th><fmt:message key="tiles.views.admin.index.manager.table.name"/></th>
												   <th><fmt:message key="tiles.views.admin.index.manager.table.role"/></th>
												   <th><fmt:message key="tiles.views.admin.index.manager.table.phone"/></th>
												   <th><fmt:message key="tiles.views.admin.index.manager.table.status"/></th>
												   <th><fmt:message key="tiles.views.admin.index.manager.table.last_login_time"/></th>
												   <th><fmt:message key="tiles.views.admin.index.manager.table.totalOrgs"/></th>
												   <th><fmt:message key="tiles.views.admin.index.manager.table.operation"/></th>
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
