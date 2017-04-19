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
							<span onclick="addNewBwList()" class="btn btn-sm btn-success btn-rounded"  style="cursor:pointer"><i class="fa fa-plus"></i>&nbsp;
							<fmt:message key="tiles.views.netbehaviormanager.blackwhitelist"/></span>
							</c:if>
							</header>
							<div class="panel-body">
							      <div class="search-toggle">
								     <a hidfocus="true"><fmt:message key="tiles.institution.comm.expand.search.tip" /></a>
								   </div>
								   <div class="search-mod" style="display: none;">
								    <ul class="search-list">
					   						<li class="search-item ">
					   							<label class="search-label"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.kind.condition"/></label>
					   							<div class="Js_dropMod select-box inline-select select-200" style="width:130px;">
													<input type="hidden" class="Js_hiddenVal" id="type" name="type" value="" />
													<span class="Js_curVal"><input type="text" value="<fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.kind.all.condition"/>"></span>
													<ul class="select-list" style="width:130px;">
														<li class="select-item"><a href="javascript:;" rel=""><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.kind.all.condition"/></a></li>
														<li class="select-item"><a href="javascript:;" rel="0"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.black"/></a></li>
														<li class="select-item"><a href="javascript:;" rel="1"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.white"/></a></li>
													</ul>
												</div>
					   						</li>
					   					</ul>
					   					<ul class="search-list">
					   						<li class="search-item ">
					   							<label class="search-label"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.name.condition"/></label>
					   							<input type="text" id="name" name="name" class="search-text" style="width:120px;">
					   						</li>
					   					</ul>
										<div class="type-choice">
											<a class="button-search" type="button" onclick="javascript:searchBwLists();"><i class="fa fa-search"></i><span><fmt:message key="tiles.institution.policy.policy.search" /></span></a>
											<a class="button-search" type="button" onclick="javascript:cleanBwLists();"><i class="fa fa-trash-o"></i><span><fmt:message key="tiles.institution.policy.policy.clean" /></span></a>
										</div>
					   			</div>
							<%-- 		<div class="col-sm-12 search_part">
										<div class="col-sm-1 searchName"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.kind.condition"/></div>
										<div class="col-sm-2 searchName_val">
											<select id="type" class="input-sm form-control">
												<option value=""><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.kind.all.condition"/></option>
												<option value="0"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.black"/></option>
												<option value="1"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.white"/></option>
											</select>
										</div>
										<div class="col-sm-1 searchName"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.name.condition"/></div>
										<div class="col-sm-2 searchName_val">
											<input id="name" type="text" class="input-sm form-control" />
										</div>
										<div class="col-sm-2 search_button">
											<button class="btn btn-sm btn-default search_icon" type="button" onclick="searchBwLists()"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.query"/></button>
											<button class="btn btn-sm btn-default reset_icon" type="button" onclick="cleanBwLists()"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.empty"/></button>
										</div>
									</div> --%>
									<div class="table-responsive">
										<table class="table table-striped b-t b-light" id="bWListTb">
											<thead>
												<tr>
												   <th><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.kind"/></th>
												   <th><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.name"/></th>
												   <th><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.description"/></th>
												   <th><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.net.count"/></th>
												   <th><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.creater"/></th>
												   <th><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.createtime"/></th>
												   <th><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.operate"/></th>
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
	`
</section>
