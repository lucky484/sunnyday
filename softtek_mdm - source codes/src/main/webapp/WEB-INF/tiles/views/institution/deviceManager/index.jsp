<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spring:url value="/institution/deviceManager/exportExcle" var="export" />

<div class="blog-post">
	<div class="post-item">
		<section class="wrapper-md ">
			<div class="row">
				<div class="scrollable aside-md col-sm-3">
					<div id="tree"></div>
				</div>
				<div class="col-sm-9">
					<section class="panel panel-default">
					 <c:if test="${softtek_manager.user==null || softtek_manager.auth > 0 }">
						<header class="panel-heading font-bold">
							<div class="gird-btns" style="overflow: visible;">
								<a href="javascript:void(0);" class="button" onclick="sengMessageBatch();"> <i class="glyphicon glyphicon-envelope"></i> &nbsp;<fmt:message key="tiles.institution.device.manager.send.system.msg" /> </a>
						<%-- 		<c:if test="${menu[48].isshow==-1}">
								<a href="#" class="button" style="display:none" onclick="sendPicMsgBatch();"> <i class="glyphicon glyphicon-envelope"></i> &nbsp;<fmt:message key="tiles.institution.device.manager.send.graphic.msg" /></a>
								</c:if> --%>
								<a href="javascript:;" class="button" onclick="refrashDeviceBatch(3)"> <i class="glyphicon glyphicon-refresh"></i> &nbsp;<fmt:message key="tiles.institution.device.manager.update.device.info" /></a>
								<a href="javascript:;" class="button" onclick="cleanPasswordBatch(8);"> <i class="glyphicon glyphicon-trash"></i> &nbsp;<fmt:message key="tiles.institution.device.manager.clean.password" /></a> 
							    <span style="font-size: 8pt;cursor: pointer" onclick="showul();"><fmt:message key="tiles.institution.device.manager.more" />&gt;&gt;</span>
									<ul class="showul dropdown-menu" style="top:24px;left:-130px;">
										<li><a href="javascript:void(0);" onclick="deviceBellBatch(9);">
										<i class="fa fa-bell"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.device.bell" /></a></li>
										<li><a href="javascript:void(0);" onclick="lockDeviceBatch(1);">
										<i class="fa fa-lock"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.lock.terminal.device" /></a></li>
										<li><a href="javascript:void(0);" onclick="unlockDeviceBatch(0);">
										<i class="fa fa-unlock"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.unlock.device" /></a></li>
										<li><a href="javascript:void(0);" onclick="lockTerminBatch(6);">
										<i class="fa fa-lock"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.lock.screen" /></a></li>
										<li><a href="javascript:void(0);" onclick="updatePasswordBatch(5);">
										<i class="fa fa-pencil"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.update.password" /></a></li>
										<li><a href="javascript:void(0);" onclick="remarkDeviceBatch(1);">
										<i class="fa fa-pencil"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.remark.lost" /></a></li>
										<li><a href="javascript:void(0);" onclick="remarkDeviceBatch(2);">
										<i class="fa fa-pencil"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.remark.find" /></a></li>
										<li><a href="javascript:void(0);" onclick="unbundleDeviceBatch(12);">
										<i class="fa fa-unlink"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.enable.terminal" /></a></li>
										<li><a href="javascript:void(0);" onclick="unbundleDeviceBatch(13);">
										<i class="fa fa-unlink"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.disable.terminal" /></a></li>
										<li><a href="javascript:void(0);" onclick="defaultSetBatch(7)">
										<i class="fa fa-reply"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.initialize.device" /></a></li>
										<li><a href="javascript:void(0);" onclick="logOffDeviceBatch(10);">
										<i class="glyphicon glyphicon-remove"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.take.off.device" /></a></li>
										<li><a href="javascript:void(0);" onclick="deleteDeviceBatch(11)">
										<i class="glyphicon glyphicon-remove"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.delete.device" /></a></li>
										
										
										
									</ul>
							   <a href="${export}" style="float:right;" onclick=""><img src="../resources/images/out.png" style="margin-top:-4px;" /><em style="font-style:normal;color:#6888B9"><fmt:message key="tiles.views.admin.index.organization.export" /></em></a> 
							</div>
						</header>
						</c:if>
						<div class="panel-body">
						   <div class="search-toggle">
						     <a hidfocus="true"><fmt:message key="tiles.institution.comm.expand.search.tip" /></a>
						   </div>
						   <div class="search-mod" style="display: none;">
			   					<ul class="search-list">
			   						<li class="search-item ">
			   							<label class="search-label" style="width:100px"><fmt:message key="tiles.views.customer.personal.index.username" />:</label>
			   							<input type="text" id="searchUserName" name="searchUserName" class="search-text" style="width:120px;">
			   						</li>
			   						<li class="search-item ">
			   							<label class="search-label" style="width:100px">ESN/IMEI：</label>
			   							<input type="text" id="esnorimei" name="esnorimei" class="search-text" style="width:120px;">
			   						</li>
			   						<li class="search-item ">
			   							<label class="search-label" style="width:100px"><fmt:message key="tiles.institution.device.manager.sequence.number" />:</label>
			   							<input type="text" id="sequenceNumber" name="sequenceNumber" class="search-text" style="width:120px;">
			   						</li>
			   						<li class="search-item ">
			   							<label class="search-label" style="width:100px"><fmt:message key="tiles.views.institution.device.rule.history.table.deviceno" />:</label>
			   							<input type="text" id="deviceType" name="deviceType" class="search-text" style="width:120px;">
			   						</li>
			   					</ul>
		   						<ul class="search-list">
			   						<li class="search-item ">
			   							<label class="search-label" style="width:100px"><fmt:message key="tiles.institution.device.manager.device.status" />:</label>
			   							<div class="Js_dropMod select-box inline-select select-200" style="width:130px;">
											<input type="hidden" class="Js_hiddenVal" id="seletedStatus" name="seletedStatus" value="" />
											<span class="Js_curVal"><input type="text" value="<fmt:message key="tiles.institution.device.manager.all.status" />"></span>
											<ul class="select-list" style="width:130px;">
												<li class="select-item"><a href="javascript:;" rel=""><fmt:message key="tiles.institution.device.manager.all.status" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="0"><fmt:message key="tiles.institution.device.manager.monitoring" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="1"><fmt:message key="tiles.institution.device.manager.wait.monitoring" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="2"><fmt:message key="tiles.institution.device.manager.take.off" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="3"><fmt:message key="tiles.institution.device.manager.lost" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="4"><fmt:message key="tiles.institution.device.manager.cance" /></a></li>
											</ul>
										</div>
			   						</li>
			   					</ul>
		   						<ul class="search-list">
			   						<li class="search-item ">
			   							<label class="search-label" style="width:100px"><fmt:message key="tiles.views.institution.application.indexscript.device.type" />:</label>
			   							<div class="Js_dropMod select-box inline-select select-200" style="width:130px;">
											<input type="hidden" class="Js_hiddenVal" id="IosStatus" name="IosStatus" value="" />
											<span class="Js_curVal"><input type="text" value="<fmt:message key="tiles.institution.log.security.log.all.type" />"></span>
											<ul class="select-list" style="width:130px;">
												<li class="select-item"><a href="javascript:;" rel=""><fmt:message key="tiles.institution.log.security.log.all.type" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="1">ios</a></li>
												<li class="select-item"><a href="javascript:;" rel="2">android</a></li>
											</ul>
										</div>
			   						</li>
			   					</ul>
								<div class="type-devicemanager">
									<a class="button-search" type="button" onclick="javascript:searchDeviceLists();"><i class="fa fa-search"></i><span><fmt:message key="tiles.institution.policy.policy.search" /></span></a>
									<a class="button-search" type="button" onclick="javascript:cleanDeviceLists();"><i class="fa fa-trash-o"></i><span><fmt:message key="tiles.institution.policy.policy.clean" /></span></a>
								</div>
			   			</div>
							<div class="table-responsive">
								<table id="deviceManager" class="table table-striped b-t b-light" style="width:100%;">
									<thead>
										<tr>
											<th width="5%">
												<select id="selected" class="text-primary">
												  <option value="0"><fmt:message key="tiles.views.user.index.table.select"/></option>
						                          <option value="1" ><fmt:message key="tiles.views.user.index.table.selectpage"/></option>
						                          <option value="2" ><fmt:message key="tiles.views.user.index.table.allselect"/></option>
						                        </select>
											</th>
											<th width="10%"><fmt:message key="tiles.views.customer.personal.index.username" /></th>
											<th width="10%"><fmt:message key="tiles.institution.device.manager.user.name" /></th>
											<th width="12%"><fmt:message key="tiles.institution.device.manager.device.type.number" /></th>
											<th width="15%" nowrap><fmt:message key="tiles.institution.device.manager.sequence.number" /></th>
											<th width="15%"><fmt:message key="tiles.institution.device.manager.esn.imei" /></th>
											<th width="10%"><fmt:message key="tiles.institution.device.manager.device.status" /></th>
											<th width="5%"><fmt:message key="tiles.institution.device.manager.operate" /></th>
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

