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
		<div class="row">
			<div class="col-sm-12">
				<div class="div-title" style="background-color: White">
					<font class="divTitleName"><fmt:message key="tiles.views.sysmanager.lisence.update.title"/></font>
					<%-- <form> --%>
					<form id="licenseInfoUpdate" class="bs-example form-horizontal">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<table class="updateLicense">
							<tbody>
								<tr>
									<td class="td-label"><fmt:message key="tiles.views.sysmanager.lisence.update.yours.title"/></td>
									<td>
										<div class="file-box">
											<input type='text' name='licensefileName' id='licensefileName' class='licenseFiletTxt'/>
											<input type="button" class="btn btn-md btn-primary" value='<fmt:message key="tiles.views.sysmanager.lisence.watch"/>' /> 
											<input type="button" name="uploadlicenseFile" class="btn btn-md btn-primary" onclick="uploadLicenseFile()" value='<fmt:message key="tiles.views.sysmanager.lisence.upload"/>' />
											<!-- <input type="file" name="licenseKeyfile" class="fileupload" id="licenseKeyfile" size="28"
												onchange="document.getElementById('licensefileName').value=this.value" /> -->
											<input type="file" name="licenseKeyfile" class="fileupload" id="licenseKeyfile" size="28"
												onchange="uploadClientOnchange(this,'licensefileName')" />
											<span>&nbsp;<fmt:message key="tiles.views.sysmanager.lisence.lisence.prifx"/></span>
										</div>
										<ul class="parsley-errors-list filled" id="licenseFilePart" style="display:none"><li class="parsley-required" id="licenseFileErrorDesc">
											<fmt:message key="tiles.views.sysmanager.lisence.must.fields"/>
										</li></ul>
									</td>
								</tr>
								
								<tr>
									<td class="td-label"><fmt:message key="tiles.views.sysmanager.lisence.active.code"/></td>
									<td>
										<div>
											<input type="text" id="activeCode" onblur="isShowDwBtn()" class="right-text"/>
										</div>
									</td>
								</tr>
								<tr id="machineTr" style="display :none">
									<td class="td-label"><fmt:message key="tiles.views.sysmanager.lisence.machine.file"/></td>
									<td>
										<div>
											<input type="text" id="machineCode" class="right-text" disabled>
											<input type="button" id="dwBtn" onclick="downloadMachineCode()" class="btn btn-md btn-primary" value="<fmt:message key="web.admin.system.license.manager.download.failed"/>" style="display :none"/>
										</div>
									</td>
								</tr>
								<tr>
									<td class="td-label"><fmt:message key="tiles.views.sysmanager.lisence.advice.days.before.beyond.date"/><span class="red">*</span></td>
									<td>
										<div>
											<input type="text" id="adviseBeforeDays" class="right-text" data-parsley-required="true"
												data-parsley-type="integer">
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2" style="text-align: center;"><input
										type="button" class="btn btn-md btn-primary" onclick="updateLicenseInfo()" value='<fmt:message key="tiles.views.sysmanager.lisence.update.button"/>'></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
				&nbsp;
				<div class="div-title" style="background-color: White">
					<font class="divTitleName"><fmt:message key="tiles.views.sysmanager.lisence.info"/></font>
					<table class="info-table">
						<tbody id="licenseInfoPart">
							<tr>
								<td class="td-label" style="width: 20%"><fmt:message key="tiles.views.sysmanager.lisence.version.info"/></td>
								<td><span id="versionType" class="pop-gray">NA</span></td>
								<td class="td-label"><fmt:message key="tiles.views.sysmanager.lisence.product.type"/></td>
								<td><span id=productType class="pop-gray"><fmt:message key="tiles.views.sysmanager.lisence.enterprise.edition"/></span></td>
							</tr>
							<tr>
								<td class="td-label"><fmt:message key="tiles.views.sysmanager.lisence.auth.days"/></td>
								<td><span id="authDays" class="pop-gray">NA</span></td>
								<td class="td-label"><fmt:message key="tiles.views.sysmanager.lisence.remain.days"/></td>
								<td><span id="remainDays" class="pop-gray">NA</span></td>
							</tr>
							<tr>
								<td class="td-label"><fmt:message key="tiles.views.sysmanager.lisence.auth.amount"/></td>
								<td><span id="authTotalMembers" class="pop-gray">NA</span></td>
								<td class="td-label"><fmt:message key="tiles.views.sysmanager.lisence.auth.has.used"/></td>
								<td><span id="authUsedMembers" class="pop-gray">NA</span></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</section>
</div>

