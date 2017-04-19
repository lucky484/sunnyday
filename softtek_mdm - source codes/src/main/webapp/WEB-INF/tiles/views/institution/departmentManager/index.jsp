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
					<div class="col-sm-12 ">
						<div class="panel panel-default">
							<div class="panel-body">
							<div class="search-toggle">
						     <a hidfocus="true"><fmt:message key="tiles.institution.comm.expand.search.tip" /></a>
						   </div>
						   <div class="search-mod" style="display: none;">
			   					<ul class="search-list">
			   						<li class="search-item ">
			   							<label class="search-label" style="width:76px"><fmt:message key="web.institution.dpt.username.label"/></label>
			   							<input type="text" id="searchaccountname" name="searchaccountname" class="search-text" style="width:120px;">
			   						</li>
			   					</ul>
			   					<ul class="search-list">
			   						<li class="search-item ">
			   							<label class="search-label" style="width:76px"><fmt:message key="web.institution.dpt.realname.label"/></label>
			   							<input type="text" id="searchrealname" name="searchrealname" class="search-text" style="width:120px;">
			   						</li>
			   					</ul>
								<div class="type-choice">
									<a class="button-search" type="button" onclick="javascript:searchDeptLists();"><i class="fa fa-search"></i><span><fmt:message key="tiles.institution.policy.policy.search" /></span></a>
									<a class="button-search" type="button" onclick="javascript:cleanDeptLists();"><i class="fa fa-trash-o"></i><span><fmt:message key="tiles.institution.policy.policy.clean" /></span></a>
								</div>
			   			</div>
								<div class="table-responsive">
                <%--            <div class="col-sm-12 search_part" >
						   				<div class="col-sm-1 searchName"><fmt:message key="web.institution.dpt.username.label"/></div>
										<div class="col-sm-2 searchName_val">
											<input id="searchaccountname" type="text" class="input-sm form-control" />
										</div>
										<div class="col-sm-1 searchName"><fmt:message key="web.institution.dpt.realname.label"/></div>
										<div class="col-sm-2 searchName_val">
											<input id="searchrealname" type="text" class="input-sm form-control" />
										</div>
										<div class="col-sm-2 search_button">
											<button class="btn btn-sm btn-default search_icon" type="button" onclick="searchDeptLists()"><fmt:message key="web.institution.dpt.index.search.label"/></button>
											<button class="btn btn-sm btn-default reset_icon" type="button" onclick="cleanDeptLists()"><fmt:message key="web.institution.dpt.index.clean.label"/></button>
										</div>
							</div> --%>
  							<div class="table-responsive">
                            <table class="table table-striped b-t b-light" id="dptmanager">
                              <thead>
                                <tr>
                                  <th><fmt:message key="web.institution.dpt.table.username.label"/></th>
                                  <th><fmt:message key="web.institution.dpt.table.realename.label"/></th>
                                   <th><fmt:message key="web.institution.dpt.table.phone.label"/></th>
                                  <th><fmt:message key="web.institution.dpt.table.role.label"/></th>
                                  <th><fmt:message key="web.institution.dpt.table.deparynames.label"/></th>
                                  <th><fmt:message key="web.institution.dpt.table.islock.label"/></th>
                                  <th><fmt:message key="web.institution.dpt.table.edit.label"/></th>
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
