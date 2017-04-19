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
                        <header class="panel-heading font-bold">
                            <spring:url value="/institution/clientConfig" var="clientConfigURL" />
                            <spring:url value="/institution/clientManager" var="clientManager" />
                        	<a href="${clientConfigURL}" class="btn btn-s-sm btn-primary"><fmt:message key="tiles.institution.client.config" /></a> 
							<a href="${clientManager}" class="btn btn-s-sm"><fmt:message key="tiles.institution.client.manager" /></a>
                        </header>
                        <div class="panel-body">
                          <div class="main-cons">
                             <input type="hidden" id="id" value="${clientConfig.id}"/>
                             <form id="Js-setOrgForm" method="post" action="">
                                <table class="edit-table" id="policy">
                                <tr>
									   <td colspan="2">
											<div class="detail-tb-hd">
												<span class="tb-hd"><fmt:message key="tiles.institution.client.function.config" /></span>
											</div>
										</td>
							    </tr>
                                <tr>
                                   <td class="td-label" style="line-height:80px;width:275px;padding-left: 133px;"><fmt:message key="tiles.institution.client.business.switch.config" /></td>
                                   <td>
                                      <ul class="checkbox-list">
									<%--     	<li class="checkbox-item">
									    		<label><input type="checkbox" id="handsetCancel" name="handsetCancel" <c:if test="${clientConfig.terminalUnbundEnable == 1}">checked</c:if> />启用终端解绑</label>
									    	</li> --%>
									    	<li class="checkbox-item">
									    		<label><input type="checkbox" id="hiddenServerConfig" name="hiddenServerConfig" <c:if test="${clientConfig.serviceSettingHide == 1}">checked</c:if> /><fmt:message key="tiles.institution.client.config.hide.service.config" /></label>
									    	</li>
									    	<li class="checkbox-item">
									    		<label><input type="checkbox" id="hiddenDeviceInfo" name="hiddenDeviceInfo" <c:if test="${clientConfig.deviceInfoHide == 1}">checked</c:if> /><fmt:message key="tiles.institution.client.config.hide.device.info" /></label>
									    	</li>
									   </ul>
                                   </td>
                                </tr>
                              <tbody>
                              </tbody>
                            </table>
                            <table class="edit-table">
								<tbody>
									<tr>
										<th colspan="2" class="edit-btns">
											<div class="edit-btns outside">
												<a href="javascript:void(0);" class="btn btn-primary btn-sm" id="Js-setOrgSubmit"><fmt:message key="tiles.institution.client.config.button.save" /></a>
							    				<a href="javascript:void(0);" class="btn btn-default btn-sm" id="Js-setOrgReSet"><fmt:message key="tiles.institution.client.config.button.reset" /></a>
										    </div>
										 </th>
									</tr>				
								</tbody>
							</table>
							</form>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>