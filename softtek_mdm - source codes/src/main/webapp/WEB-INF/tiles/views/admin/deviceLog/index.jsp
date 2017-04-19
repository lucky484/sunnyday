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
		    <spring:url value="/admin/sysmanageLog" var="sysmanageLog" />
			<a href="${sysmanageLog}" class="btn btn-s-sm"><fmt:message key="tiles.institution.log.operate.logger" /></a> 
			<spring:url value="/admin/securityEventLog" var="securityEventLog" />
			<a href="${securityEventLog}" class="btn btn-s-sm "><fmt:message key="tiles.institution.log.security.event.logger" /></a>
			<spring:url value="/admin/deviceLog" var="deviceLog" />
			<a href="${deviceLog}" class="btn btn-s-sm btn-primary"><fmt:message key="tiles.institution.log.deivce.logger" /></a>
		</header>
			<section class="wrapper-md ">
			<div class="row">		
              <div class="hbox stretch">
                <div>
                  <div class="vbox">
                    <div class="scrollable padder">
                      <div class="panel panel-default">
                        <div class="panel-body">
                          <div class="search-toggle">
						     <a hidfocus="true"><fmt:message key="tiles.institution.comm.expand.search.tip" /></a>
						   </div>
						   <div class="search-mod" style="display: none;">
						    <ul class="search-list">
			   						<li class="search-item ">
			   							<label class="search-label" style="width:90px"><fmt:message key="tiles.views.netbehaviormanager.logmanager.usertype" />:</label>
			   							<div class="Js_dropMod select-box inline-select select-200" style="width:130px;">
											<input type="hidden" class="Js_hiddenVal" id="eventType" name="eventType" value="" />
											<span class="Js_curVal"><input type="text" value="<fmt:message key="tiles.institution.log.operate.all.type" />"></span>
											<ul class="select-list" style="width:130px;">
												<li class="select-item"><a href="javascript:;" rel=""><fmt:message key="tiles.institution.log.operate.all.type" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="0"><fmt:message key="tiles.views.customer.device.index.eventtype.0" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="1"><fmt:message key="tiles.views.customer.device.index.eventtype.1" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="2"><fmt:message key="tiles.views.customer.device.index.eventtype.2" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="3"><fmt:message key="tiles.views.customer.device.index.eventtype.3" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="4"><fmt:message key="tiles.views.customer.device.index.eventtype.4" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="5"><fmt:message key="tiles.views.customer.device.index.eventtype.5" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="6"><fmt:message key="tiles.views.customer.device.index.eventtype.6" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="7"><fmt:message key="tiles.views.customer.device.index.eventtype.7" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="8"><fmt:message key="tiles.views.customer.device.index.eventtype.8" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="9"><fmt:message key="tiles.views.customer.device.index.eventtype.9" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="10"><fmt:message key="tiles.views.customer.device.index.eventtype.10" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="11"><fmt:message key="tiles.views.customer.device.index.eventtype.11" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="12"><fmt:message key="tiles.views.customer.device.index.eventtype.12" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="13"><fmt:message key="tiles.views.customer.device.index.eventtype.13" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="14"><fmt:message key="tiles.views.customer.device.index.eventtype.14" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="15"><fmt:message key="tiles.views.customer.device.index.eventtype.15" /></a></li>
											</ul>
										</div>
			   						</li>
			   					</ul>
			   					<ul class="search-list" style="margin-left:98px;">
			   						<li class="search-item ">
			   							<label class="search-label" style="width:90px"><fmt:message key="tiles.institution.log.operate.device.name" />:</label>
			   							<input type="text" id="devicename" name="devicename" class="search-text" style="width:120px;">
			   						</li>
			   					</ul>
			   					<ul class="search-list" style="margin-left:98px;">
			   						<li class="search-item ">
			   							<label class="search-label" style="width:76px"><fmt:message key="tiles.views.user.index.search.username" />:</label>
			   							<input type="text" id="username" name="username" class="search-text" style="width:120px;">
			   						</li>
			   					</ul>
								<div class="type-choice">
									<a class="button-search" type="button" onclick="javascript:searchDeviceLogs();"><i class="fa fa-search"></i><span><fmt:message key="tiles.institution.policy.policy.search" /></span></a>
									<a class="button-search" type="button" onclick="javascript:cleanDeviceLogs();"><i class="fa fa-trash-o"></i><span><fmt:message key="tiles.institution.policy.policy.clean" /></span></a>
								</div>
			   			</div>
                          <div class="table-responsive">
                   <%--         <div class="col-sm-12 search_part">
                          	<div class="col-sm-1 searchName"><fmt:message key="tiles.institution.log.operate.event.type" />:</div>
							<div class="col-sm-2 searchName_val">
								<select id="eventType" class="input-sm form-control">
									<option value=""><fmt:message key="tiles.institution.log.operate.all.type" /></option>
									<option value="0"><fmt:message key="tiles.views.customer.device.index.eventtype.0" /></option>
									<option value="1"><fmt:message key="tiles.views.customer.device.index.eventtype.1" /></option>
									<option value="2"><fmt:message key="tiles.views.customer.device.index.eventtype.2" /></option>
									<option value="3"><fmt:message key="tiles.views.customer.device.index.eventtype.3" /></option>
									<option value="4"><fmt:message key="tiles.views.customer.device.index.eventtype.4" /></option>
									<option value="5"><fmt:message key="tiles.views.customer.device.index.eventtype.5" /></option>
									<option value="6"><fmt:message key="tiles.views.customer.device.index.eventtype.6" /></option>
									<option value="7"><fmt:message key="tiles.views.customer.device.index.eventtype.7" /></option>
									<option value="8"><fmt:message key="tiles.views.customer.device.index.eventtype.8" /></option>
									<option value="9"><fmt:message key="tiles.views.customer.device.index.eventtype.9" /></option>
									<option value="10"><fmt:message key="tiles.views.customer.device.index.eventtype.10" /></option>
									<option value="11"><fmt:message key="tiles.views.customer.device.index.eventtype.11" /></option>
									<option value="12"><fmt:message key="tiles.views.customer.device.index.eventtype.12" /></option>
									<option value="13"><fmt:message key="tiles.views.customer.device.index.eventtype.13" /></option>
									<option value="14"><fmt:message key="tiles.views.customer.device.index.eventtype.14" /></option>
									<option value="15"><fmt:message key="tiles.views.customer.device.index.eventtype.15" /></option>
								</select>
							</div>
                          	<div class="col-sm-1 searchName"><fmt:message key="tiles.institution.log.operate.device.name" />:</div>
							<div class="col-sm-2 searchName_val">
								<input id="devicename" type="text" class="input-sm form-control"/>
							</div>
							<div class="col-sm-1 searchName"><fmt:message key="tiles.views.user.index.search.username" />:</div>
							<div class="col-sm-2 searchName_val">
								<input id="username" type="text" class="input-sm form-control"  />
							</div>
							<div class="col-sm-2 search_button">
								<button class="btn btn-sm btn-default search_icon" type="button" onclick="searchDeviceLogs()"><fmt:message key="tiles.views.institution.device.rule.query" /></button>
								<button class="btn btn-sm btn-default reset_icon" type="button" onclick="cleanDeviceLogs()"><fmt:message key="tiles.views.institution.device.rule.reset" /></button>
							</div>
							</div> --%>
                            <table class="table table-striped b-t b-light" id="deviceLog">
                              <thead>
                                <tr>
                                  <th><fmt:message key="tiles.institution.log.operate.event.type" /></th>
                                  <th><fmt:message key="tiles.institution.log.operate.device.name" /></th>
                                  <th><fmt:message key="tiles.views.user.index.search.username" /></th>
                                  <th><fmt:message key="tiles.institution.log.operate.event.create.by" /></th>
                                  <th><fmt:message key="tiles.institution.log.operate.event.create.time" /></th>
                                  <th><fmt:message key="tiles.institution.log.operate.event.content" /></th>
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