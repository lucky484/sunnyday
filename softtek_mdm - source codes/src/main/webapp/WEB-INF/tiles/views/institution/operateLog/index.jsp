<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="blog-post">
	<div class="post-item">
		<header
			class="panel-heading b-l-l-none b-t-l-none b-b-l-1 b-r-l-none b-s-s">
		    <spring:url value="/institution/sysmanageLog" var="sysmanageLog" />
			<a href="${sysmanageLog}" class="btn btn-s-sm btn-primary"><fmt:message key="tiles.institution.log.operate.logger" /></a> 
			<spring:url value="/institution/deviceLog" var="deviceLog" />
			<a href="${deviceLog}" class="btn btn-s-sm"><fmt:message key="tiles.institution.log.deivce.logger" /></a>
		</header>
		<section class="wrapper-md ">
			<div class="row">		
		
              <div class="hbox stretch">
                <div>
                  <div class="vbox">
                    <div class="scrollable padder">
                      <div class="panel panel-default logdefault">
                        <div class="panel-body">
                           <div class="search-toggle">
						     <a><fmt:message key="tiles.institution.comm.expand.search.tip" /></a>
						   </div>
						   <div class="search-mod" style="display: none;">
						    <ul class=" m-l pull-left">
			   						<li class="m-t-xs" style="list-style:none;">
			   							<label class="search-label"><fmt:message key="tiles.views.netbehaviormanager.logmanager.usertype" />:</label>
			   							<div class="Js_dropMod select-box inline-select select-200" style="width:130px;">
											<input type="hidden" class="Js_hiddenVal" id="seletedStatus" name="seletedStatus" value="" />
											<span class="Js_curVal"><input type="text" value="<fmt:message key="tiles.institution.log.security.log.all.type" />"></span>
											<ul class="select-list" style="width:130px;">
												<c:if test="${softtek_manager.user_type == 1}">
												<li class="select-item"><a href="javascript:;" rel="1"><fmt:message key="tiles.views.admin.index.manager.table.user_type.admin" /></a></li>
												</c:if>
												<li class="select-item"><a href="javascript:;" rel="2"><fmt:message key="tiles.views.admin.index.manager.table.user_type" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="3"><fmt:message key="web.institution.dptmanager.index.label" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="4"><fmt:message key="logs.user.type.custom" /></a></li>
											</ul>
										</div>
			   						</li>
			   					</ul>
			   					<ul class="search-list" style="margin-left:98px;">
			   						<li class="search-item ">
			   							<label class="search-label" style="width:76px"><fmt:message key="tiles.institution.device.manager.user.name" />:</label>
			   							<input type="text" id="userName" name="userName" class="search-text" style="width:120px;">
			   						</li>
			   					</ul>
								<div class="type-choice">
									<a class="button-search" type="button" onclick="javascript:searchOptLogLists();"><i class="fa fa-search"></i><span><fmt:message key="tiles.institution.policy.policy.search" /></span></a>
									<a class="button-search" type="button" onclick="javascript:cleanOptLogLists();"><i class="fa fa-trash-o"></i><span><fmt:message key="tiles.institution.policy.policy.clean" /></span></a>
								</div>
			   			</div>
                          <div class="table-responsive">
                            <table class="table table-striped b-t b-light" id="sysManagerLog">
                              <thead>
                                <tr>
                                  <th><fmt:message key="web.institution.exportandimportexcelcontroller.exceltip2" /></th>
                                  <th><fmt:message key="jsp.manager.login.j_username.placeholder" /></th>
                                  <th><fmt:message key="tiles.views.netbehaviormanager.logmanager.usertype" /></th>
                                  <th><fmt:message key="tiles.institution.policy.policy.operate" /></th>
                                  <th><fmt:message key="tiles.views.netbehaviormanager.logmanager.content" /></th>
                                  <th><fmt:message key="tiles.institution.log.device.log.operate.time" /></th> 
                           		  <th><fmt:message key="tiles.views.netbehaviormanager.logmanager.organization.name" /></th>
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
              </section>
            </div>
            </div>