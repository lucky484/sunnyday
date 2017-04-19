<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="modal fade" id="releaseClient" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content" style="width: 800px;height: 719px;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.client.manager.upload" /></h4>
			</div>
			<div class="modal-body" style="height:600px;">
			<form id="clientform" enctype="multipart/form-data">
			    <table class="edit-table">
				    <tbody>
						    <tr>
								<td colspan="4">
									<div class="detail-tb-hd">
										<span class="tb-hd"><fmt:message key="tiles.views.customer.index.index.basicinfo" /></span>
									</div>
								</td>
							</tr>
							<tr> 
								<td class="td-label"><fmt:message key="tiles.institution.client.manager.client.file" /></td> 
								<td colspan="3"><div class="pop-conlists">
									<input type="file" id="clientfile" name="clientfile" class="file-upload" onchange="javascript:uploadClientOnchange(this,'fileShade');">
								    <input class="pop-text" id="fileShade" type="text">
									<a class="button fa fa-eye"><fmt:message key="tiles.institution.client.manager.browser" /></a>
									<a href="javascript:;" id="clientinport" class="button fa fa-arrow-circle-o-up"><fmt:message key="tiles.views.sysmanager.lisence.upload" /></a>
									<br><span class="line-blcok"><fmt:message key="tiles.institution.client.manager.upload.file.msg" /></span>
									</div>
								</td>
							</tr>
					    
							<tr> 
								<td class="td-label"><fmt:message key="tiles.institution.client.manager.client.id" /><span class="red">*</span></td> 
								<td colspan="3">
								<input type="text" id="clientId" name="clientId" class="pop-text" readonly="readonly">
								</td> 
							</tr>
							<tr> 
								<td class="td-label"><fmt:message key="tiles.institution.client.manager.client.name" /><span class="red">*</span></td> 
								<td colspan="3">
									<input type="text" id="clientName" name="clientName" class="pop-text" readonly="readonly">
								</td> 
							</tr>
							<tr> 
								<td class="td-label"><fmt:message key="tiles.institution.client.manager.client.version" /><span class="red">*</span></td> 
								<td colspan="3">
									<input type="text" id="clientVersion" name="clientVersion" class="pop-text" readonly="readonly">
								</td> 
							</tr>
							
								<tr> 
								<td class="td-label"><fmt:message key="tiles.institution.client.manager.client.sign" /></td> 
								<td colspan="3">
									<input type="text" id="certOwner" name="certOwner" readonly="readonly" class="pop-text">
								</td> 
							</tr>
							<tr class="pop-item" style="display: none;">
									<td class="td-label"><fmt:message key="tiles.institution.client.manager.select.org" /></td>
									<td colspan="3">
									<div class="pop-conlists">
										<label><input type="radio" name="loginTime" id="orgNoLimit" value="" checked onclick="hideOrgList()">适用所有机构</label><br>
										<label><input type="radio" name="loginTime" id="orgLimit" value="" onclick="showOrgList()">指定机构</label>
									</div>
								</td>
							</tr>
							<tr id="orgListTr" style="display: table-row;">
								<td class="td-label"><fmt:message key="tiles.views.admin.index.manager.org" /><span class="red" id="orgRed" style="">*</span></td>
								<td colspan="3">
									<div class="rece-treebox pad-race-treebox">
										<ul class="chk-list" id="orgList">
											<c:forEach items="${list}" var="list">
											  <li class="chk-item">
											     <label><input type="checkbox" id="${list.id}" value = "${list.id}"/>${list.name}</label>
											  </li>
										</c:forEach>
										</ul>
									</div>
									<span id="depart-error" style="display:none;">
												<ul class="parsley-errors-list filled">
													<li class=""><fmt:message
															key="tiles.views.user.index.table.required" /></li>
												</ul>
									</span>
								</td>
							</tr>
							<tr> 
								<td class="td-label"><fmt:message key="tiles.institution.client.manager.remark" /></td> 
								<td colspan="3">
									<div class="equal-area" style="padding-bottom: 11px;">
										<div class="area-model">
											<textarea id="clientDescription" name="clientDescription" class="pop-area"></textarea>
										</div>
									</div>
									
								</td> 
							</tr>
							<tr>
								<td class="td-label"><fmt:message key="tiles.institution.client.manager.forceupdate" /></td> 
								<td>
									<input type="checkbox" id="forceupdateAll" value="1">
								</td> 
								<td class="td-label"></td> 
								<td>
									
								</td>
							</tr>
							<!-- <tr>
								<td class="td-label">支持设备</td> 
								<td>
									<input id="phone" type="checkbox" disabled>手机<input id="pad" type="checkbox" disabled>平板
								</td> 
								<td class="td-label"></td> 
								<td>
									
								</td>
							</tr> -->
						</tbody>
			    </table>
			   </form>
			</div>
			<div class="pop-btns">
				<a href="javascript:;" class="btn btn-primary btn-md" id="insert"><fmt:message key="tiles.views.institution.message.modal.view.confirm" /></a>
				<a href="javascript:;" class="btn btn-default btn-md" style="margin-left:35px;" onclick="closePopWin();"><fmt:message key="tiles.views.institution.message.modal.update.cancel" /></a>
			</div>
		</div>
	</div>
</div>


<div class="modal fade" id="pleaseSelect" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.views.institution.message.modal.delete.tip" /></h4>
			</div>
			<div class="modal-body">
			    <h3 class="text-danger"><fmt:message key="tiles.institution.client.manager.select.file" /></h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default confirm_button" id="message"><fmt:message key="tiles.views.institution.message.modal.view.confirm" /></button>
		      </div>
		</div>
	</div>
</div>

<div class="modal fade" id="errormsg" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.views.institution.message.modal.delete.tip" /></h4>
			</div>
			<div class="modal-body">
			    <h3 class="text-danger"><fmt:message key="tiles.institution.client.manager.upload.tip" /></h3>	
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default confirm_button" id="message"><fmt:message key="tiles.views.institution.message.modal.view.confirm" /></button>
		      </div>
		</div>
	</div>
</div>
<!-- 发布成功的消息提示 -->
<div class="modal fade" id="successMsg" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.views.institution.message.modal.delete.tip" /></h4>
			</div>
			<div class="modal-body">
			    <h3 class="text-danger"><fmt:message key="tiles.institution.client.manager.upload.success" /></h3>	
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default confirm_button" id="success"><fmt:message key="tiles.views.institution.message.modal.view.confirm" /></button>
		      </div>
		</div>
	</div>
</div>
<!-- 详情页面 -->
<div class="modal fade" id="queryDetail" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content" style="width: 800px;height: 756px;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.client.manager.query.detail" /></h4>
			</div>
			<div class="detail-table-box" style="height:600px;">
		<form method="post" action="">
		    <table class="edit-table">
			    <tbody>
				    <tr>
						<td colspan="4">
							<div class="detail-tb-hd">
								<span class="tb-hd1"><fmt:message key="tiles.institution.policy.base.info" /></span>
							</div>
						</td>
					</tr>
					<tr> 
						<td class="td-label">&nbsp;</td>
						<td id="QRcode">
							<img src=""  />
						</td>
					</tr>
					
					<tr> 
						<td class="td-label"><fmt:message key="tiles.institution.client.manager.client.version" /></td>
						<td>
							<span id="clientVersionDetail" class="pop-label pop-gray"></span>
							<div id="locked"></div>
						</td>
					</tr>
					<tr> 
						<td class="td-label"><fmt:message key="tiles.views.institution.application.indexmodal.file" /></td>
						<td id="clientUrl" name="clientUrl"><a style="color:#0000FF" href="javascript:download();"></a></td>
					</tr>
					<tr> 
						<td class="td-label"><fmt:message key="tiles.institution.client.manager.client.sign" /></td>
						<td>
							<span id="certOwnerDetail" class="pop-label pop-gray"></span>
						</td>
					</tr>
					<tr> 
						<td class="td-label"><fmt:message key="tiles.institution.client.manager.platform" /></td>
						<td>
							<span id="ostypeNameDetail" class="pop-label pop-gray"></span>
						</td>
					</tr>
					<tr> 
						<td class="td-label"><fmt:message key="tiles.institution.client.manager.upload.time" /></td>
						<td>
							<span id="uploadTime" class="pop-label pop-gray"></span>
						</td>
					</tr>
					<tr> 
						<td class="td-label"><fmt:message key="tiles.institution.client.manager.client.id" /></td>
						<td>
							<span id="clientIdDetail" class="pop-label pop-gray"></span>
						</td>
					</tr>
					<tr> 
						<td class="td-label"><fmt:message key="tiles.institution.client.manager.client.name" /></td>
						<td>
							<span id="clientNameDetail" class="pop-label pop-gray"></span>
						</td>
					</tr>
					<tr> 
						<td class="td-label"><fmt:message key="tiles.institution.client.manager.remark" /></td>
						<td>
							<span id="clientDescriptionDetail" class="pop-label pop-gray"></span>
						</td>
					</tr>
					<tr> 
						<td class="td-label"><fmt:message key="tiles.institution.client.manager.upgrade" /></td>
						<td>
							<span id="forceupdateAllDetail" class="pop-label pop-gray"></span>
						</td>
					</tr>
					<tr> 
						<td class="td-label"><fmt:message key="tiles.institution.client.manager.belong.org" /></td>
						<td>
							<span id="orgName" class="pop-label pop-gray"></span>
						</td>
					</tr>
					<tr>
						<td class="td-label"><fmt:message key="tiles.institution.client.manager.insist.device" /></td>
						<td>
							<span id="isTablet" class="pop-label pop-gray"></span>
						</td>
					</tr>
				</tbody></table>
			</form>
			</div>
		</div>
	</div>
</div>
<!-- 删除消息提示 -->
<div class="modal fade" id="deleteMsg" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.views.institution.message.modal.delete.tip" /></h4>
			</div>
			<div class="modal-body">
			    <input type="hidden" id="clientId" />
			    <h3 class="text-danger"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.delmessage" /></h3>	
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default confirm_button" id="delete"><fmt:message key="tiles.institution.org.department.confirm"/></button>
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.del.cancel"/></button>
		      </div>
		</div>
	</div>
</div>
<!-- 未选择文件 -->
<div class="modal fade" id="selectFile" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.views.institution.message.modal.delete.tip" /></h4>
			</div>
			<div class="modal-body">
			    <h3 class="text-danger"><fmt:message key="tiles.institution.client.manager.select.client.file" /></h3>	
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.confirm"/></button>
		      </div>
		</div>
	</div>
</div>
<!-- 未上传文件文件 -->
<div class="modal fade" id="uploadFile" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.views.institution.message.modal.delete.tip" /></h4>
			</div>
			<div class="modal-body">
			    <h3 class="text-danger"><fmt:message key="tiles.institution.client.manager.not.select.file" /></h3>	
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.confirm"/></button>
		      </div>
		</div>
	</div>
</div>