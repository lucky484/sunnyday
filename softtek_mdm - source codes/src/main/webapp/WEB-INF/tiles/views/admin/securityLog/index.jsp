<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spring:url value="/admin/securityEventLog/export" var="export" />
<div class="blog-post">
	<div class="post-item">
		<header
			class="panel-heading b-l-l-none b-t-l-none b-b-l-1 b-r-l-none b-s-s">
		    <spring:url value="/admin/sysmanageLog" var="sysmanageLog" />
			<a href="${sysmanageLog}" class="btn btn-s-sm" id="sysmanageLog"><fmt:message key="tiles.institution.log.operate.logger" /></a> 
			<spring:url value="/admin/securityEventLog" var="securityEventLog" />
			<a href="${securityEventLog}" class="btn btn-s-sm btn-primary"><fmt:message key="tiles.institution.log.security.event.logger" /></a>
			<spring:url value="/admin/deviceLog" var="deviceLog" />
			<a href="${deviceLog}" class="btn btn-s-sm "><fmt:message key="tiles.institution.log.deivce.logger" /></a>
		</header>
			<section class="wrapper-md ">
			<div class="row">		
              <div class="hbox stretch">
                <div>
                  <div class="vbox">
                    <div class="scrollable padder">
                      <div class="panel panel-default">
                        <div class="panel-body">
                          <div class="role-btns">
                              <a href="${export}" onclick=""><img src="../resources/images/out.png" style="margin-top:-4px;" /><em style="font-style:normal;color:#6888B9"><fmt:message key="tiles.views.admin.index.organization.export" /></em></a> 
                          </div>
                          <div class="search-toggle">
						     <a hidfocus="true"><fmt:message key="tiles.institution.comm.expand.search.tip" /></a>
						   </div>
						    <div class="search-mod" style="display: none;height:46px;">
								   <ul class="search-list">
					   						<li class="search-item ">
					   							<label class="search-label" style="width:76px;margin-top:3px;"><fmt:message key="tiles.institution.log.operate.event.type" />:</label>
					   							<div class="Js_dropMod select-box inline-select select-200" style="width:195px;margin-left:9px;">
													<input type="hidden" class="Js_hiddenVal" id="eventType" name="eventType" value="" />
													<span class="Js_curVal"><input type="text" value="<fmt:message key="tiles.institution.log.security.log.all.type" />"></span>
													<ul class="select-list" id="event_type" style="width:195px;">
														<li class="select-item"><a href="javascript:;" rel=""><fmt:message key="tiles.institution.log.security.log.all.type" /></a></li>
														<li class="select-item"><a href="javascript:;" rel="2"><fmt:message key="tiles.institution.log.security.password.error" /></a></li>
														<li class="select-item"><a href="javascript:;" rel="3"><fmt:message key="tiles.institution.log.security.url.error" /></a></li>
													</ul>
												</div>
					   						</li>
					   					</ul>
					   			<div class="type-choice">
									<a class="button-search" type="button" onclick="javascript:searchDeviceLists();"><i class="fa fa-search"></i><span><fmt:message key="tiles.institution.policy.policy.search" /></span></a>
									<a class="button-search" type="button" onclick="javascript:cleanDeviceLists();"><i class="fa fa-trash-o"></i><span><fmt:message key="tiles.institution.policy.policy.clean" /></span></a>
								</div>
			   				</div>
                          <div class="table-responsive">
<!--                            <div class="col-sm-12 search_part"> -->
<!--                            <div class="col-sm-1 searchName">事件类型:</div> -->
<!-- 							<div class="col-sm-2 searchName_val"> -->
<!-- 								<select id="eventType" class="input-sm form-control"> -->
<!-- 									<option value="">所有类型</option> -->
<!-- 									<option value="2">用户连续输错密码</option> -->
<!-- 									<option value="1">用户设备被标记为引爆锁定</option> -->
<!-- 									<option value="3">用户访问了禁止访问的请求地址</option> -->
<!-- 								</select> -->
<!-- 							</div> -->
<!-- 							<div class="col-sm-2 search_button"> -->
<!-- 								<button class="btn btn-sm btn-default search_icon" type="button" id="search">查询</button> -->
<!-- 								<button class="btn btn-sm btn-default reset_icon" type="button" id="clear">清空</button> -->
<!-- 							</div> -->
<%-- 							 <a href="${export}" class="btn btn-primary btn-md pull-right m-r"><i class="fa fa-pencil-square-o"></i>导出</a> --%>
<!-- 							</div> -->
                            <table class="table table-striped b-t b-light" id="securityLog">
                              <thead>
                                <tr>
                                  <th><fmt:message key="tiles.institution.log.operate.event.type" /></th>
                                  <th><fmt:message key="tiles.institution.log.security.level" /></th>
                                  <th><fmt:message key="tiles.institution.log.security.log.create.time" /></th>
                                  <th><fmt:message key="tiles.institution.log.operate.event.content" /></th>
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