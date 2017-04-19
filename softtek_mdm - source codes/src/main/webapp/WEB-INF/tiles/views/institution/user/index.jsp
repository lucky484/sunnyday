<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<div class="blog-post">
	<div class="post-item">
		<section class="wrapper-md ">
			<div class="row">
				<div class="scrollable aside-md col-sm-3">
					<div class="btn-toolbar">
						<button class="btn btn-default b-none node-states" id="btn-1"
							href="#btn-1" data-toggle="class:btn-default">
							<i class="fa fa-plus-square text"></i> <span class="text"><fmt:message key="tiles.views.user.index.toolbar.expand"/></span>
							<i class="fa fa-minus-square text-active"></i> <span
								class="text-active"><fmt:message key="tiles.views.user.index.toolbar.collapse"/></span>
						</button>
					</div>
					<div id="tree" style="overflow-y:scorll;max-height:768px;"></div>
				</div>
				<div class="col-sm-9">
					<section class="panel panel-default">
					<c:if test="${softtek_manager.user==null || softtek_manager.auth > 0 }">
						<header class="panel-heading font-bold">
							<div class="doc-buttons">
								<button class="btn add_user btn-sm btn-success btn-rounded" onclick="showModel()">
									<i class="fa fa-plus"></i> &nbsp;<fmt:message key="tiles.views.institution.urule.newly.add"/>
								</button>
								<a href="javascript:void(0);"
									class="btn btn-sm btn-danger btn-rounded btn-del-users"> <i class="fa fa-trash-o"></i>
									&nbsp;<fmt:message key="tiles.views.institution.device.rule.add.type.rule.delete"/>
								</a> <a href="#" class="import btn btn-sm btn-info btn-rounded"
									data-toggle="modal" data-target="#importModal"> <i
									class="glyphicon glyphicon-import"></i> &nbsp;<fmt:message key="tiles.views.user.index.import"/>
								</a> <a href="#" class="export btn btn-sm btn-warning btn-rounded"> <i
									class="glyphicon glyphicon-export"></i> &nbsp;<fmt:message key="tiles.views.user.index.export"/>
								</a> <a href="#" class="notice btn btn-sm btn-dark btn-rounded"> <i
									class="fa fa-bell-o"></i> &nbsp;<fmt:message key="tiles.views.user.index.inform"/>
								</a> 
								<a href="javascript:void(0);" class="btn btn-sm btn-default btn-rounded btn-active-users"> <i
									class="fa fa-child" ></i> &nbsp;<fmt:message key="tiles.views.user.index.active"/>
								</a>
								<a href="javascript:void(0);" class="btn btn-sm btn-default btn-rounded "onclick="exportUsers()"  id="exportUser"> <i
									class="fa  fa-upload " ></i> &nbsp;<fmt:message key="tiles.views.user.index.exportuser"/>
								</a>
								<a href="javascript:void(0);" class="btn btn-sm btn-default btn-rounded "onclick="openExcelModal()" id="importUser"> <i
									class="fa fa-download"></i> &nbsp;<fmt:message key="tiles.views.user.index.importuser"/>
								</a>
							</div>
						</header>
						</c:if>
						<div class="panel-body">
						   <div class="search-toggle">
						     <a><fmt:message key="tiles.institution.comm.expand.search.tip" /></a>
						   </div>
						   <div class="search-mod" style="display: none;">
			   					<ul class="search-list">
			   						<li class="search-item ">
			   							<label class="search-label" style="width:76px"><fmt:message key="tiles.views.user.index.search.username"/>:</label>
			   							<input type="text" id="searchusername" name="searchusername" class="search-text" style="width:120px;">
			   						</li>
			   						<li class="search-item ">
			   							<label class="search-label" style="width:76px"><fmt:message key="tiles.views.user.index.search.account"/>:</label>
			   							<input type="text" id="searchaccountname" name="searchaccountname" class="search-text" style="width:120px;">
			   						</li>
			   					</ul>
			   					<ul class="search-list">
			   						<li class="search-item ">
			   							<label class="search-label" style="width:76px"><fmt:message key="tiles.views.user.index.search.active"/>:</label>
			   							<div class="Js_dropMod select-box inline-select select-200" style="width:130px;">
											<input type="hidden" class="Js_hiddenVal" id="searchactivestatus" name="searchactivestatus" value="" />
											<span class="Js_curVal1"><input type="text" value="<fmt:message key="tiles.views.user.index.search.active.all"/>"></span>
											<ul class="select-list" style="width:130px;">
												<li class="select-item"><a href="javascript:;" rel=""><fmt:message key="tiles.views.user.index.search.active.all"/></a></li>
												<li class="select-item"><a href="javascript:;" rel="0"><fmt:message key="tiles.views.user.index.search.active.no"/></a></li>
												<li class="select-item"><a href="javascript:;" rel="1"><fmt:message key="tiles.views.user.index.search.active.yes"/></a></li>
											</ul>
										</div>
			   						</li>
			   					</ul>
								<div class="type-choice">
									<a class="button-search" type="button" onclick="javascript:searchUserLists();"><i class="fa fa-search"></i><span><fmt:message key="tiles.institution.policy.policy.search" /></span></a>
									<a class="button-search" type="button" onclick="javascript:cleanUserLists();"><i class="fa fa-trash-o"></i><span><fmt:message key="tiles.institution.policy.policy.clean" /></span></a>
								</div>
			   			</div>
				<%-- 		   <div class="col-sm-12 search_part">
						   				<div class="col-sm-1 searchName"><fmt:message key="tiles.views.user.index.search.username"/>:</div>
										<div class="col-sm-2 searchName_val">
											<input id="searchusername" type="text" class="input-sm form-control" />
										</div>
										
										<div class="col-sm-1 searchAccount"><fmt:message key="tiles.views.user.index.search.account"/>:</div>
										<div class="col-sm-2 searchAccount_val">
											<input id="searchaccountname" type="text" class="input-sm form-control" />
										</div>
										<div class="col-sm-1 searchStatus"><fmt:message key="tiles.views.user.index.search.active"/>:</div>
										<div class="col-sm-2 searchStatus_val">
											<select id="searchactivestatus" class="input-sm form-control">
												<option value=""><fmt:message key="tiles.views.user.index.search.active.all"/></option>
												<option value="0"><fmt:message key="tiles.views.user.index.search.active.no"/></option>
												<option value="1"><fmt:message key="tiles.views.user.index.search.active.yes"/></option>
											</select>
										</div>
										<div class="col-sm-2 search_button">
											<button class="btn btn-sm btn-default search_icon" type="button" onclick="searchUserLists()"><fmt:message key="tiles.views.institution.device.rule.query"/></button>
											<button class="btn btn-sm btn-default reset_icon" type="button" onclick="cleanUserLists()"><fmt:message key="tiles.views.institution.device.rule.reset"/></button>
										</div>
							</div> --%>
							<div class="table-responsive">
								<table id="myTable" class="table table-striped b-t b-light" style="width:100%;">
									<thead>
										<tr>
											<th >
											<select id="fck" class="text-primary" style="min-width: 80px;">
					                          <option value="0" selected><fmt:message key="tiles.views.user.index.table.select"/></option>
					                          <option value="1"><fmt:message key="tiles.views.user.index.table.selectpage"/></option>
					                          <option value="2"><fmt:message key="tiles.views.user.index.table.allselect"/></option>
					                        </select>
											<!-- <label
												class="checkbox m-l m-t-none m-b-none i-checks"> <input
													type="checkbox" name="_ck" onclick="ckbx(this)"> <i></i>
											</label> --></th>
											<th><fmt:message key="tiles.views.user.index.table.username"/></th>
											<th><fmt:message key="tiles.views.user.index.table.account"/></th>
											<th><fmt:message key="tiles.views.user.index.table.phone"/></th>
											<th><fmt:message key="tiles.views.user.index.table.devicenum"/></th>
											<th><fmt:message key="tiles.views.user.index.table.stragy"/></th>
											<th><fmt:message key="tiles.views.user.index.table.userstatus"/></th>
											<th><fmt:message key="tiles.views.institution.device.rule.opera"/></th>
										</tr>
									</thead>
									<tbody>
										
									</tbody>
								</table>
							</div>
						</div>
					</section>
				</div>
			</div>
		</section>
	</div>
</div>

