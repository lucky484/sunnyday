<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="blog-post">
	<div class="post-item">
                          <header class="panel-heading b-l-l-none b-t-l-none b-b-l-1 b-r-l-none b-s-s">
                          <spring:url value="/institution/org" var="orgUrl" />
                          <spring:url value="/institution/dptPolicy" var="dptPolicy" />
                            <a href="${orgUrl}" class="btn btn-s-sm"><fmt:message key="tiles.institution.org.department.manager" /></a>
                            <a href="${dptPolicy}" class="btn btn-s-sm btn-primary"><fmt:message key="tiles.institution.org.department.policy" /></a>
                          </header>
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
                                <div id="tree"></div>
                              </div>
                              <input type="hidden" id="policyId" value="${policyId}" name="policyId" />
                              <input type="hidden" id="defaultPolicyId" value="${defaultPolicyId}" name="defaultPolicyId" />
                              <input type="hidden" id="orgId" name="orgId" value="${orgId}">
                                <div class="col-sm-9">
                                  <section class="panel panel-default">
                                    <div class="table-responsive">
                                      <table class="table table-striped b-t b-light">
                                        <thead>
                                          <tr>
                                            <th width="20"></th>
                                            <th class="" width="100"><fmt:message key="tiles.institution.department.policy.name" /></th>
                                            <th><fmt:message key="tiles.institution.department.policy.mark" /></th>
                                          </tr>
                                        </thead>
                                        <tbody id="departmentPolicy">
                                         <tr>
                                            <td>
                                              <label class="radio i-checks m-l m-t-none m-b-none" id="extendFather">
                                                <input type="radio" name="a" value="" <c:if test="${policyId==defaultPolicyId}"> checked </c:if>/>
                                                <i></i>
                                              </label>
                                            </td>
                                            <td><fmt:message key="tiles.institution.department.policy.extends.superior" /></td>
                                            <td style='text-align: left;padding: 10px;'><fmt:message key="tiles.institution.department.policy.extends.superior.mark" /></td>
                                          </tr>
                                        <c:forEach var="list" items="${dataList}">
                                          <tr>
                                            <td>
                                              <label class="radio i-checks m-l m-t-none m-b-none">
                                                <c:if test="${policyId != defaultPolicyId}">
                                                <input type="radio" name="a" value="${list.id}" <c:if test="${policyId==list.id}"> checked </c:if>>
                                                <i></i>
                                                </c:if>
                                                  <c:if test="${policyId==defaultPolicyId}">
                                                <input type="radio" name="a" value="${list.id}" />
                                                <i></i>
                                                </c:if>
                                              </label>
                                            </td>
                                            <td>${list.name}</td>
                                           	<td>${list.flag}</td>
                                          </tr>
                                          </c:forEach> 
                                        </tbody>
                                      </table>
                                    </div>
                                  </section>
                                  </div>
                                  <div class="col-sm-9">
                                  <c:if test="${softtek_manager.user==null || softtek_manager.auth > 0 }">
                                  <button class="btn btn-primary btn-md" id="updatePolicy"><fmt:message key="tiles.institution.department.policy.setting" /></button>
                                  </c:if>
                                  		 <div class="pull-left">
				                               <c:if test="${page.totalPage > 0}">
				                             	<span class="page-info" id="pageCount"><fmt:message key="tiles.institution.department.policy.page"><fmt:param value="${page.totalCount}" /><fmt:param value="${page.page}" /><fmt:param value="${page.totalPage}" /></fmt:message></span>
<%-- 共${page.totalCount}条 每页10条 页次： ${page.page}/${page.totalPage} --%>
				                               </c:if>
				                                <c:if test="${page.totalPage <= 0}">
				                             	<span class="page-info" id="pageCount"><fmt:message key="tiles.institution.department.policy.page"><fmt:param value="${page.totalCount}" /><fmt:param value="${page.page}" /><fmt:param value="${page.totalPage+1}" /></fmt:message></span>
				                               </c:if>
					                         </div>
					                            <div class="pull-right">
					                                <ul class="pagination">
					                               <c:if test="${page.page > 1}">
					                                <li id="beforePage"><a href="javascript:getAjax(${page.page-1})"><fmt:message key="tiles.institution.department.policy.before.page" /></a></li>
					                               </c:if>
					                               <c:if test="${page.page <= 1}">
					                                <li id="beforePage"><a href="javascript:void(0)"><fmt:message key="tiles.institution.department.policy.before.page" /></a></li>
					                               </c:if>
					                                <c:if test="${page.totalPage > page.page}">
					                                <li id="nextPage"><a href="javascript:getAjax(${page.page+1})"><fmt:message key="tiles.institution.department.policy.next.page" /></a></li>
					                                </c:if>
					                                <c:if test="${page.totalPage <= page.page}">
					                                <li id="nextPage"><a href="javascript:void(0)"><fmt:message key="tiles.institution.department.policy.next.page" /></a></li>
					                                </c:if>
													</ul>
					                            </div>
                                  </div>
                              </div>
                              </section>
                              </div>
</div>