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
			      <c:if test="${softtek_manager.user==null || softtek_manager.auth > 0 }">
                       <header class="panel-heading font-bold">
                         <button class="btn btn-sm btn-success btn-rounded" data-toggle="modal" onclick="addIosPolicy()">
	                     <i class="fa fa-plus"></i>
	                     <fmt:message key="tiles.views.institution.devicepolicy.index.iostitle"/>
	                   </button> &nbsp;
	                   <button class="btn btn-sm btn-success btn-rounded" data-toggle="modal" onclick="addPolicy()">
	                     <i class="fa fa-plus"></i>
	                     <fmt:message key="tiles.views.institution.devicepolicy.index.title"/>
	                   </button>
                        </header>
                         </c:if>
                        <div class="panel-body">
               			  <input type="hidden" id="userIds" /> <input type="hidden"id="userNames" />
                          <div class="search-toggle">
						     <a hidfocus="true"><fmt:message key="tiles.institution.comm.expand.search.tip" /></a>
						   </div>
						   <div class="search-mod" style="display: none;">
			   					<ul class="search-list">
			   						<li class="search-item ">
			   							<label class="search-label" style="width:130px"><fmt:message key="tiles.views.institution.devicepolicy.index.policynm"/></label>
			   							<input type="text" id="devicePolicyName" name="devicePolicyName" class="search-text" style="width:120px;">
			   						</li>
			   					</ul>
			   					<ul class="search-list">
			   						<li class="search-item">
			   							<label class="search-label" style="width:130px"><fmt:message key="tiles.views.institution.devicepolicy.index.policyst"/></label>
			   							<div class="Js_dropMod select-box inline-select select-200" style="width:130px;">
											<input type="hidden" class="Js_hiddenVal" id="policytype" name="policytype" value="" />
											<span class="Js_curVal"><input type="text" value="<fmt:message key="tiles.views.institution.devicepolicy.index.allstate"/>"></span>
											<ul class="select-list" style="width:130px;">
												<li class="select-item" style="margin-left:9px;"><a href="javascript:;" rel=""><fmt:message key="tiles.views.institution.devicepolicy.index.allstate"/></a></li>
												<li class="select-item" style="margin-left:9px;"><a href="javascript:;" rel="0"><fmt:message key="tiles.views.institution.devicepolicy.index.disable"/></a></li>
												<li class="select-item" style="margin-left:9px;"><a href="javascript:;" rel="1"><fmt:message key="tiles.views.institution.devicepolicy.index.enable"/></a></li>
											</ul>
										</div>
			   						</li>
			   					</ul>
								<div class="type-choice">
									<a class="button-search" type="button" onclick="javascript:searchPolicy();"><i class="fa fa-search"></i><span><fmt:message key="tiles.institution.policy.policy.search" /></span></a>
									<a class="button-search" type="button" onclick="javascript:cleanPolicy();"><i class="fa fa-trash-o"></i><span><fmt:message key="tiles.institution.policy.policy.clean" /></span></a>
								</div>
			   			</div>
						  <div class="table-responsive">
				<%-- 			<div class="col-sm-12 search_part">
						      <div class="col-sm-1 searchName"><fmt:message key="tiles.views.institution.devicepolicy.index.policynm"/></div>
							  <div class="col-sm-2 searchName_val">
								<input id="devicePolicyName" type="text" class="form-control" />
							  </div>
							  <div class="col-sm-1 searchAccount"><fmt:message key="tiles.views.institution.devicepolicy.index.policyst"/></div>
								<div class="col-sm-2 searchAccount_val">
									<select id="policytype" class="form-control">
										<option value=""><fmt:message key="tiles.views.institution.devicepolicy.index.allstate"/></option>
										<option value="0"><fmt:message key="tiles.views.institution.devicepolicy.index.disable"/></option>
										<option value="1"><fmt:message key="tiles.views.institution.devicepolicy.index.enable"/></option>
									</select>
								</div>
							  <div class="col-sm-2 search_button">
							  	<button class="btn btn-sm btn-default search_icon" type="button" onclick="searchPolicy()"><fmt:message key="general.jsp.search.label"/></button>
								<button class="btn btn-sm btn-default reset_icon" type="button" onclick="cleanPolicy()"><fmt:message key="general.jsp.clean.label"/></button>
							  </div>
							</div> --%>
							<div class="table-responsive">
							<table id="devicePolicy" class="table table-striped b-t b-light">
								<thead>
									<tr>
										<th><fmt:message key="tiles.views.institution.devicepolicy.index.policyname"/></th>
										<th><fmt:message key="tiles.views.institution.devicepolicy.index.description"/></th>
										<th><fmt:message key="tiles.views.institution.devicepolicy.index.applicationplatform"/></th>
										<th><fmt:message key="tiles.views.institution.devicepolicy.index.assigned"/></th>
										<th><fmt:message key="tiles.views.institution.devicepolicy.index.updatetime"/></th>
										<th><fmt:message key="tiles.views.institution.devicepolicy.index.policystate"/></th>
										<th><fmt:message key="tiles.views.institution.devicepolicy.index.operate"/></th>
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
</section> 
