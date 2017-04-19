<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="content">
              <div class="hbox stretch">
                <div>
                  <div class="vbox">
                    <div class="scrollable padder">
                      <div class="panel panel-default">
                       <c:if test="${softtek_manager.user==null || softtek_manager.auth > 0 }">
                        <header class="panel-heading font-bold">
                          <button class="btn btn-sm btn-success btn-rounded" id="insert">
                            <i class="fa fa-plus"></i>
                            &nbsp;<fmt:message key="tiles.institution.policy.user.policy" />
                          </button>
                        </header>
                         </c:if>
                        <div class="panel-body">
                            <div class="search-toggle">
						     <a hidfocus="true"><fmt:message key="tiles.institution.comm.expand.search.tip" /></a>
						   </div>
						   <div class="search-mod" style="display: none;">
			   					<ul class="search-list">
			   						<li class="search-item ">
			   							<label class="search-label" style="width:76px"><fmt:message key="tiles.institution.policy.policy.name" />:</label>
			   							<input type="text" id="searchpolicyname" name="searchpolicyname" class="search-text" style="width:120px;">
			   						</li>
			   					</ul>
			   					<ul class="search-list">
			   						<li class="search-item ">
			   							<label class="search-label" style="width:90px"><fmt:message key="tiles.institution.policy.policy.is_default" />:</label>
			   							<div class="Js_dropMod select-box inline-select select-200" style="width:130px;">
											<input type="hidden" class="Js_hiddenVal" id="searchpolicytype" name="searchpolicytype" value="" />
											<span class="Js_curVal"><input type="text" value="<fmt:message key="tiles.institution.policy.all.policy" />"></span>
											<ul class="select-list" style="width:130px;">
												<li class="select-item"><a href="javascript:;" rel=""><fmt:message key="tiles.institution.policy.all.policy" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="1"><fmt:message key="tiles.institution.policy.policy.yes" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="0"><fmt:message key="tiles.institution.policy.policy.no" /></a></li>
											</ul>
										</div>
			   						</li>
			   					</ul>
								<div class="type-choice">
									<a class="button-search" type="button" onclick="javascript:searchPolicyLists();"><i class="fa fa-search"></i><span><fmt:message key="tiles.institution.policy.policy.search" /></span></a>
									<a class="button-search" type="button" onclick="javascript:cleanPolicyLists();"><i class="fa fa-trash-o"></i><span><fmt:message key="tiles.institution.policy.policy.clean" /></span></a>
								</div>
			   			</div>
                          <div class="table-responsive">
                          <%--  <div class="col-sm-12 search_part">
						   				<div class="col-sm-1 searchName"><fmt:message key="tiles.institution.policy.policy.name" />:</div>
										<div class="col-sm-2 searchName_val">
											<input id="searchpolicyname" type="text" class="input-sm form-control" />
										</div>
										<div class="col-sm-1 searchAccount"><fmt:message key="tiles.institution.policy.policy.is_default" />:</div>
										<div class="col-sm-2 searchAccount_val">
											<select id="searchpolicytype" class="input-sm form-control">
												<option value=""><fmt:message key="tiles.institution.policy.all.policy" /></option>
												<option value="1"><fmt:message key="tiles.institution.policy.policy.yes" /></option>
												<option value="0"><fmt:message key="tiles.institution.policy.policy.no" /></option>
											</select>
										</div>
										<div class="col-sm-2 search_button">
											<button class="btn btn-sm btn-default search_icon" type="button" onclick="searchPolicyLists()"><fmt:message key="tiles.institution.policy.policy.search" /></button>
											<button class="btn btn-sm btn-default reset_icon" type="button" onclick="cleanPolicyLists()"><fmt:message key="tiles.institution.policy.policy.clean" /></button>
										</div>
						  </div> --%>
                            <table class="table table-striped b-t b-light" id="policy">
                              <thead>
                                <tr>
                                  <th class="th-sortable"><fmt:message key="tiles.institution.policy.policy.name" /></th>
                                  <th><fmt:message key="tiles.institution.policy.belong.department" /></th>
                                  <th><fmt:message key="tiles.institution.policy.policy.is_default" /></th>
                                  <th><fmt:message key="tiles.institution.policy.policy.createDate" /></th>
                                  <th><fmt:message key="tiles.institution.policy.user.count" /></th>
                                  <th><fmt:message key="tiles.institution.policy.policy.operate" /></th>
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
              </div>
            </div>