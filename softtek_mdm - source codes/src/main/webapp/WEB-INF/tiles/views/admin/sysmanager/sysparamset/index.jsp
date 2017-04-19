<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="blog-post">
	<section class="wrapper-md">
		&nbsp;
		<div class="setpart" style="height: 100%">
			<ul id="sysToolBar" class="tab-list">
				<li class="tab-item current" data-val="0"><a href="#tab1" data-toggle="tab"><fmt:message key="tiles.views.sysmanager.system.param.setting"/></a></li>
				<li class="tab-item" data-val="1" onclick=""><a href="#tab2"
					data-toggle="tab"><fmt:message key="tiles.views.sysmanager.message.advice.setting"/></a></li>
				<li class="tab-item" data-val="2" onclick=""><a href="#tab3"
					data-toggle="tab"><fmt:message key="tiles.views.sysmanager.mail.model.setting"/></a></li>
				<li class="tab-item" data-val="3" onclick=""><a href="#tab4"
					data-toggle="tab"><fmt:message key="tiles.views.sysmanager.system.style.setting"/></a></li>
			</ul>
			<div class="panel-body">
				<div class="tab-content">
					<div class="tab-pane active" id="tab1">
						<div class="mdmdetails">
							<span class="mdmfont"><fmt:message key="tiles.views.sysmanager.mdm.param.setting"/></span>
						</div>
						<div class="contentPart">
							<form id="formOne">
							<table class="mdmparamset">
								<tr>
									<td class="tdleft"><fmt:message key="tiles.views.sysmanager.mdm.manage.platform.address"/><span class="red">*</span></td>
									<td class="tdright">
										<div>
											<input type="text" id="mdmAddress" class="right-text" data-parsley-required="true">
										</div>
									</td>
								</tr>
								<tr>
									<td class="tdleft"><fmt:message key="tiles.views.sysmanager.device.beyond.control.data"/><span class="red">*</span></td>
									<td class="tdright">
										<div>
											<input type="text" id="outControlTimeLimit" class="right-text" data-parsley-required="true">
										</div>
									</td>
								</tr>
								<tr>
									<td class="tdleft"><fmt:message key="tiles.views.sysmanager.device.info.collect.period"/><span class="red">*</span></td>
									<td class="tdright">
										<div>
											<input type="text" id="deviceInfoCollectPeriod" class="right-text" data-parsley-required="true">
										</div>
									</td>
								</tr>
								<tr>
									<td class="tdleft"><fmt:message key="tiles.views.sysmanager.illegal.behavior.collect.period"/><span class="red">*</span></td>
									<td class="tdright">
										<div>
											<input type="text" id="deviceIllegalCollectPeriod" class="right-text" data-parsley-required="true">
										</div>
									</td>
								</tr>
								
								<tr>
									<td colspan="2" style="text-align: center;"><input
										type="button" class="btn btn-md btn-primary" value="<fmt:message key="tiles.views.sysmanager.update"/>" onclick="updateMdmParams()"></td>
								</tr>
							</table>
							</form>
						</div>
					</div>
					<div class="tab-pane" id="tab2">
						<div class="mdmdetails">
							<span class="mdmfont"><fmt:message key="tiles.views.sysmanager.email.service.setting"/></span>
						</div>
						<div class="contentPart">
							<form id="formEmail">
							<table class="mdmparamset">
								<tr>
									<td class="tdleft"><fmt:message key="tiles.views.sysmanager.smtp.service.address"/><span class="red">*</span></td>
									<td class="tdright">
										<div>
											<input type="text" id="smtpServiceAddress" class="right-text" data-parsley-required="true">
										</div>
									</td>
								</tr>
								<tr class="hidden">
									<td class="tdleft">SSL</span></td>
									<td class="tdright">
										<label><input type="checkbox" id="isSsl" style="vertical-align:middle;margin-right: 5px;"/>
									</td>
								</tr>
								<tr>
									<td class="tdleft"><fmt:message key="tiles.views.sysmanager.smtp.service.port"/></td>
									<td class="tdright">
										<div>
											<input type="text" id="smtpPort" class="right-text">
										</div>
									</td>
								</tr>
								<tr>
									<td class="tdleft"><fmt:message key="tiles.views.sysmanager.email.service.username"/></td>
									<td class="tdright">
										<div>
											<input type="text" id="username" class="right-text">
										</div>
									</td>
								</tr>
								<tr>
									<td class="tdleft"><fmt:message key="tiles.views.sysmanager.email.service.auto.code"/></td>
									<td class="tdright">
										<div>
											<input type="text" id="password" class="right-text">
										</div>
									</td>
								</tr>
								<tr>
									<td class="tdleft"><fmt:message key="tiles.views.sysmanager.email.sender.address"/></td>
									<td class="tdright">
										<div>
											<input type="text" id="sendToUserAddress" class="right-text">
										</div>
									</td>
								</tr>
								
								<tr>
									<td class="tdleft"><fmt:message key="tiles.views.sysmanager.email.yours.email.address"/></td>
									<td class="tdright">
										<div id="sendTestEmailWithParams">
											<a href="javascript:void(0)"  onclick="sendTestMail()" class="button" bgcolor="#ffffff"><em><fmt:message key="tiles.views.sysmanager.email.send.test.emial.to.my.email"/></em></a>
										</div>
									</td>
								</tr>
							</table>
							</form>
						</div>
						<div style="text-align:center;"><input type="button" class="btn btn-md btn-primary" value="<fmt:message key="tiles.views.sysmanager.save"/>" onclick="updateMessageAdviceParam()"></div>
						
					</div>
					<div class="tab-pane" id="tab3">
						<div class="mdmdetails">
							<span class="mdmfont"><fmt:message key="tiles.views.sysmanager.email.illeagl.behavior.advice.email"/></span>
						</div>
						<div class="contentPart">
							<table class="mdmparamset">
								<tr>
									<td class="tdleft"><fmt:message key="tiles.views.sysmanager.email.theme"/><span class="red"></span></td>
									<td class="tdright">
										<input type="text" id="illegalEmailAdviceTheme" class="right-text">
										<!-- <a href="" class="button"><em>参数说明</em> </a>
										<div class="help-cons pull-right show">
												<span class="help-close">X</span>
												$deviceName$&nbsp;&nbsp;&nbsp;&nbsp;设备名称<br>$ruleContent$&nbsp;&nbsp;&nbsp;&nbsp;规则内容<br>$ruleName$&nbsp;&nbsp;&nbsp;&nbsp;规则名称<br>$userDefinedContent$&nbsp;&nbsp;&nbsp;&nbsp;用户自定义内容<br>$userName$&nbsp;&nbsp;&nbsp;&nbsp;用户名<br>
										</div> -->
										 <span style="position: relative;"> 
										 <a href="javascript:;" class="button help-btn"> <em><fmt:message key="tiles.views.sysmanager.email.param.instruction"/></em></a>
											<div class="help-cons"
												style="position: absolute; left: 80px; top: -20px; display: block;">
												<span class="help-close">X</span>
												<fmt:message key="tiles.views.sysmanager.email.model.illegal.param.instruction"/><br>
											</div>
										</span>
									</td>
									
								</tr>
								<tr>
									<td class="tdleft"><fmt:message key="tiles.views.sysmanager.email.content"/></td>
									<td class="tdright">
										<div>
											<div id="illegalMailAdviceContent" contenteditable="true" class="contentDiv"></div>
										</div>
										<span class="red"><fmt:message key="tiles.views.sysmanager.email.note.tips"/></span>
									</td>
								</tr>
							</table>
						</div>
						<%-- <div class="mdmdetails">
							<span class="mdmfont"><fmt:message key="tiles.views.sysmanager.email.user.batch.import.advice.email"/></span>
						</div>
						<div class="contentPart">
							<table class="mdmparamset">
								<tr>
									<td class="tdleft"><fmt:message key="tiles.views.sysmanager.email.theme"/><span class="red"></span></td>
									<td class="tdright">
										<div>
											<input type="text" id="importEmailAdviceTheme" class="right-text">
											<span style="position:relative;">
													<a href="javascript:;" class="button help-btn"><em><fmt:message key="tiles.views.sysmanager.email.param.instruction"/></em></a>
													<div class="help-cons" style="position: absolute; left: 80px; top: -20px; display: block;">
													<span class="help-close">X</span>
														<fmt:message key="tiles.views.sysmanager.email.model.user.batch.import.param.instruction"/><br></div>
													</span>
										</div>
									</td>
								</tr>
								<tr>
									<td class="tdleft"><fmt:message key="tiles.views.sysmanager.email.content"/></td>
									<td class="tdright">
										<div>
											<div id="importEmailAdviceContent" contenteditable="true" class="contentDiv"></div>
										</div>
										<span class="red"><fmt:message key="tiles.views.sysmanager.email.note.tips"/></span>
									</td>
								</tr>
							</table>
						</div> --%>
						<%-- <div class="mdmdetails">
							<span class="mdmfont"><fmt:message key="tiles.views.sysmanager.email.password.beyond.data.advice"/></span>
						</div>
						<div class="contentPart">
							<table class="mdmparamset">
								<tr>
									<td class="tdleft"><fmt:message key="tiles.views.sysmanager.email.theme"/><span class="red"></span></td>
									<td class="tdright">
										<div>
											<input type="text" id="pwBeyondDataTheme" class="right-text">
											<span style="position:relative;">
												<a href="javascript:;" class="button help-btn"><em><fmt:message key="tiles.views.sysmanager.email.param.instruction"/></em></a>
												<div class="help-cons" style="position: absolute; left: 80px; top: -20px; display: block;">
													<span class="help-close">X</span>
												<fmt:message key="tiles.views.sysmanager.email.model.password.beyond.data.param.instruction"/><br></div>
											</span>
										</div>
									</td>
								</tr>
								<tr>
									<td class="tdleft"><fmt:message key="tiles.views.sysmanager.email.content"/></td>
									<td class="tdright">
										<div>
											<div id="pwBeyondDateAdviceContent" contenteditable="true" class="contentDiv"></div>
										</div>
										<span class="red"><fmt:message key="tiles.views.sysmanager.email.note.tips"/></span>
									</td>
								</tr>
							</table>
						</div> --%>
						<div class="mdmdetails">
							<span class="mdmfont"><fmt:message key="tiles.views.sysmanager.email.test.email"/></span>
						</div>
						<div class="contentPart">
							<table class="mdmparamset">
								<tr>
									<td class="tdleft"><fmt:message key="tiles.views.sysmanager.email.theme"/><span class="red"></span></td>
									<td class="tdright">
										<div>
											<input type="text" id="testEmailTheme" class="right-text">
											<!-- <a href="" class="button"><em>参数说明</em> </a> -->
										</div>
									</td>
								</tr>
								<tr>
									<td class="tdleft"><fmt:message key="tiles.views.sysmanager.email.content"/></td>
									<td class="tdright">
										<div>
											<div id="testEmailAdviceContent" contenteditable="true" class="contentDiv"></div>
										</div>
										<span class="red"><fmt:message key="tiles.views.sysmanager.email.note.tips"/></span>
									</td>
								</tr>
							</table>
						</div>
						<div style="text-align:center;"><input type="button" class="btn btn-md btn-primary" value="<fmt:message key="tiles.views.sysmanager.save"/>" onclick="updateEmailModels()"></div>
					</div>
					
					<div class="tab-pane" id="tab4">
					<div class="mdmdetails">
							<span class="mdmfont"><fmt:message key="tiles.views.sysmanager.page.logo.setting"/></span>
					</div>
					<table class="mdmparamset">
								<tr>
									<td class="tdleft"><fmt:message key="tiles.views.sysmanager.page.logo.picture"/><span class="red">*</span></td>
									<td class="tdright">
										<div>
										<spring:url value="/resources/logo/logo.png" var="logochangeurl" />
											<a href="#"><img id="logoUrl1" src="${logochangeurl}" onclick="openlogomodal()">
									     	</a>
										</div>
									</td>
								</tr>
								<form id="systemstylefrm" method="post">
								<input type="hidden" value="1" id="ischangelgo">
								<input type="hidden" value="" id="logopath">
								<%-- <tr>
									<td class="tdleft"><fmt:message key="tiles.views.sysmanager.copyright.info"/><span class="red">*</span></td>
									<td class="tdright">
										<div >
											<input type="text" id="copyright" class="right-text"
												 data-parsley-required="true" data-parsley-trigger="blur"  
												data-parsley-maxlength="40" >
										</div>
									</td>
								</tr> --%>
								
								</form>
								<tr>
									<td class="tdleft"></td>
									<td class="tdright"><input
										type="button" class="btn btn-md btn-primary" value='<fmt:message key="tiles.views.sysmanager.update"/>' onclick="submitStyle()">&nbsp;&nbsp;&nbsp;<input
										type="button" class="btn btn-md btn-primary" value='<fmt:message key="tiles.views.sysmanager.recover.default.setting"/>' onclick="resetstyle()"></td>
								</tr>
							</table>
						</div>
				</div>
			</div>
		</div>
	</section>
</div>

