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
			<section class="wrapper-md ">
				<div class="row">
					<div class="col-sm-12">
						<div class="panel panel-default">
							<header class="panel-heading font-bold">
							 <c:if test="${softtek_manager.user==null || softtek_manager.auth > 0 }">
							<span onclick="addAppNameList()" class="btn btn-sm btn-success btn-rounded" style="cursor:pointer"><i class="fa fa-plus"></i>&nbsp;<fmt:message key="tiles.views.institution.namelist.index.title"/></span>
							</c:if>
							</header>
							<div class="panel-body">
							 <div class="search-toggle">
						     <a hidfocus="true"><fmt:message key="tiles.institution.comm.expand.search.tip" /></a>
						   </div>
						   <div class="search-mod" style="display: none;">
						    <ul class="search-list">
			   						<li class="search-item ">
			   							<label class="search-label"><fmt:message key="tiles.views.institution.namelist.index.type"/></label>
			   							<div class="Js_dropMod select-box inline-select select-200" style="width:130px;">
											<input type="hidden" class="Js_hiddenVal" id="type" name="type" value="" />
											<span class="Js_curVal"><input type="text" value="<fmt:message key="tiles.views.institution.namelist.index.alltype"/>"></span>
											<ul class="select-list" style="width:130px;">
												<li class="select-item"><a href="javascript:;" rel=""><fmt:message key="tiles.views.institution.namelist.index.alltype"/></a></li>
												<li class="select-item"><a href="javascript:;" rel="1"><fmt:message key="tiles.views.institution.namelist.index.blacknamelist"/></a></li>
												<li class="select-item"><a href="javascript:;" rel="0"><fmt:message key="tiles.views.institution.namelist.index.whitenamelist"/></a></li>
											</ul>
										</div>
			   						</li>
			   					</ul>
			   					<ul class="search-list">
			   						<li class="search-item ">
			   							<label class="search-label"><fmt:message key="tiles.views.institution.namelist.index.titlename"/></label>
			   							<input type="text" id="name" name="name" class="search-text" style="width:120px;">
			   						</li>
			   					</ul>
								<div class="type-choice">
									<a class="button-search" type="button" onclick="javascript:searchPolicy();"><i class="fa fa-search"></i><span><fmt:message key="tiles.institution.policy.policy.search" /></span></a>
									<a class="button-search" type="button" onclick="javascript:cleanPolicy();"><i class="fa fa-trash-o"></i><span><fmt:message key="tiles.institution.policy.policy.clean" /></span></a>
								</div>
			   			</div>
								<div class="table-responsive">
							<%-- 		<div class="col-sm-12 search_part">
										<div class="col-sm-1 searchName"><fmt:message key="tiles.views.institution.namelist.index.type"/></div>
										<div class="col-sm-2 searchName_val">
											<select id="type" class="input-sm form-control">
												<option value=""><fmt:message key="tiles.views.institution.namelist.index.alltype"/></option>
												<option value="1"><fmt:message key="tiles.views.institution.namelist.index.blacknamelist"/></option>
												<option value="0"><fmt:message key="tiles.views.institution.namelist.index.whitenamelist"/></option>
											</select>
										</div>
										<div class="col-sm-1 searchName"><fmt:message key="tiles.views.institution.namelist.index.titlename"/></div>
										<div class="col-sm-2 searchName_val">
											<input id="name" type="text" class="input-sm form-control" />
										</div>
										<div class="col-sm-2 search_button">
											<button class="btn btn-sm btn-default search_icon" type="button" onclick="searchPolicy()"><fmt:message key="general.jsp.search.label"/></button>
											<button class="btn btn-sm btn-default reset_icon" type="button" onclick="cleanPolicy()"><fmt:message key="general.jsp.clean.label"/></button>
										</div>
									</div> --%>
									<div class="table-responsive">
										<table class="table table-striped b-t b-light" id="appNameList">
											<thead>
												<tr>
												   <th><fmt:message key="tiles.views.institution.namelist.index.nametype"/></th>
												   <th><fmt:message key="tiles.views.institution.namelist.index.name"/></th>
												   <th><fmt:message key="tiles.views.institution.namelist.index.appcount"/></th>
												   <th><fmt:message key="tiles.views.institution.namelist.index.description"/></th>
												   <th><fmt:message key="tiles.views.institution.namelist.index.operate"/></th>
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
			</section>
		</div>
	</div>
	`
</section>
