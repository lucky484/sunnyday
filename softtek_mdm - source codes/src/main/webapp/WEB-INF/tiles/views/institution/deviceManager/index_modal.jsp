<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- 锁定设备 -->
<div class="modal fade" id="lockDevice" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.device.manager.lock.device" /></h4>
			</div>
			<div class="modal-body">
				<table class="edit-table">
				    <tbody><tr>
					    <td class="td-label" style="width:20%"><fmt:message key="tiles.institution.device.manager.device.lock" /></td>
					   	<td style="width:80%">
					   		<div style="float: left;"><input type="radio" name="lockStatus" checked value="2" ></div>
					   		<div style="float: left;margin-top: 6px;"><fmt:message key="tiles.institution.device.manager.user.visit.limit" /></div> 
					   		
<%-- 					   		<div><label><input type="radio" name="lockStatus" value="3"><fmt:message key="tiles.institution.device.manager.device.destruction.client.data" /></label></div> --%>
					   		<input type="hidden" id="typeLock" name="typeLock" />
					   		<input type="hidden" id="userIdLock" name="userIdLock" />
					   		<input type="hidden" id="idLock" name="idLock">
					   		<input type="hidden" id="udidLock" name="udidLock">
					   	</td>
					</tr>
				</tbody></table>
			</div>
			 <div class="modal-footer">
			 	<button type="button" class="btn btn-default confirm_button" id="sendLockDevice"><fmt:message key="tiles.institution.org.department.confirm" /></button>
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.close" /></button>
		      </div>
		</div>
	</div>
</div>
<!-- 锁定设备的提示信息 -->
<div class="modal fade" id="lockDeviceMsg" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.comment.msg" /></h4>
			</div>
			<div class="modal-body">
			    <h3 class="text-danger"><fmt:message key='tiles.institution.device.manager.operate.success' /></h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default confirm_button" id="message"><fmt:message key="tiles.institution.org.department.confirm" /></button>
		      </div>
		</div>
	</div>
</div>
<!-- 解锁终端设备 -->
<div class="modal fade" id="unLockDevice" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.comment.msg" /></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.institution.device.manager.unlock.device.msg" /></h3>
			    <input type="hidden" id="typeBatch" name="typeBatch" />
			</div>
			 <div class="modal-footer">
			 	<button type="button" class="btn btn-default confirm_button" id="unLockDeviceSure"><fmt:message key="tiles.institution.org.department.confirm" /></button>
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.close" /></button>
		      </div>
		</div>
	</div>
</div>
<!-- 解锁设备的消息提示 -->
<div class="modal fade" id="unlockDeviceMsg" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.comment.msg" /></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.institution.device.manager.unlock.success" /></h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default confirm_button" id="unlockMessage"><fmt:message key="tiles.institution.org.department.confirm" /></button>
		      </div>
		</div>
	</div>
</div>
<!-- 发送消息 -->
<div class="modal fade" id="sendInfoMessage" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.device.manager.send.msg.device" /></h4>
			</div>
			<div class="modal-body">
			  <form id="messageForm" data-parsley-validate>
				<table class="edit-table">
				    <tbody>
				    <tr>
					    <td class="td-label" style="width:20%"><fmt:message key="tiles.institution.device.manager.message.title" /><span class="red">*</span></td>
					    
					   	<td style="width:80%">
					   		<div>
						   		<input type="text" id="messageTitle" name="messageTitle" value="" data-parsley-trigger="focusout" data-parsley-required="true" data-parsley-maxlength="10">
					   		</div>
					   		<input type="hidden" id="userIdMessage" name="userIdMessage"/>
					   		<input type="hidden" id="udidMessage" name="udidMessage" />
					   	</td>
					</tr>
					<tr>
					    <td class="td-label" style="width:20%"><fmt:message key="tiles.institution.device.manager.message.content" /><span class="red">*</span></td>
					   	<td style="width:80%">
					   		<div>
						   		<textarea style="width: 303px;height: 177px;" id="msg" name="msg" maxlength="500" data-parsley-trigger="focusout" data-parsley-required="true"></textarea>
					   		</div>
					   		<div id="notifylength" class="receinfo"><fmt:message key="tiles.institution.device.manager.word.limit" /></div>
					   	</td>
					</tr>
				</tbody>
				</table>
			 </form>
			</div>
			 <div class="modal-footer">
			 	<button type="button" class="btn btn-default" id="sendToClient"><fmt:message key="tiles.institution.device.manager.send" /></button>
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.close" /></button>
		      </div>
		</div>
	</div>
</div>

<!-- 发送图文消息给色被 -->
    <div class="modal fade" id="loadBwNameList" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
     <!-- 选择黑白名单列表的id值 -->
     <input type="hidden" id="chooseEnableIds"/>
     <input type="hidden" id="chooseDepartIds"/>
	    <div class="modal-dialog modal-lg" role="document">
	      <div class="modal-content">
	        <div class="modal-header b-b-l-none">
	          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	            <span aria-hidden="true">&times;</span>
	          </button>
	        </div>
	        <div class="modal-body" style="overflow: scroll;">
	          <div class="row" id="insert">
	            <div class="col-sm-12">
	              <section class="panel panel-default">
	                <div class="panel-body">
	                    <div class="table-responsive">
	                       <table class="table table-striped b-t b-light" id="pushPicAndInfoTb" width="100%">
	                         <thead>
	                           <tr>
	                             <th></th>
	                             <th><fmt:message key="tiles.institution.device.manager.name" /></th>
	                             <th><fmt:message key="tiles.institution.device.manager.create.by" /></th>
	                             <th><fmt:message key="tiles.institution.device.manager.create.time" /></th>
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
	          </div>
	          <div class="modal-footer">
				<a href="javascript:void(0)" onclick="sendPicInfo()" class="btn btn-success confirm_button"><fmt:message key="tiles.institution.org.department.confirm" /></a>
				<a href="javascript:void(0)" class="btn" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.close" /></a>
             </div>
	        </div>
	   </div>
    </div>

<!-- 消息发送成功 -->
<div class="modal fade" id="sendSuccess" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.comment.msg" /></h4>
			</div>
			<div class="modal-body">
			    <h3 class="text-danger"><fmt:message key="tiles.institution.device.manager.send.success" /></h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default confirm_button" id="success"><fmt:message key="tiles.institution.org.department.confirm" /></button>
		      </div> 
		</div>
	</div>
</div>

<!-- 消息发送成功 -->
<div class="modal fade" id="sendPicInfoSuccess" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.comment.msg" /></h4>
			</div>
			<div class="modal-body">
			    <h3 class="text-danger"><fmt:message key="tiles.institution.device.manager.send.success" /></h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default" id="sendSuccess"><fmt:message key="tiles.institution.org.department.confirm" /></button>
		      </div> 
		</div>
	</div>
</div>

<!-- 更新设备信息 -->
<div class="modal fade" id="refrashBatch" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.comment.msg" /></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.institution.device.manager.update.device.msg" /></h3>
			    <input type="hidden" id="typeRefrash" name="typeRefrash" />
			    <input type="hidden" id="userIdRefrash" name="userIdRefrash" />
			    <input type="hidden" id="udidRefrash" name="udidRefrash" />
			</div>
			 <div class="modal-footer">
			 	<button type="button" class="btn btn-default confirm_button" id="refrashSure"><fmt:message key="tiles.institution.org.department.confirm" /></button>
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.close" /></button>
		      </div>
		</div>
	</div>
</div>
<!-- 更新设备信息 -->
<div class="modal fade" id="refrash" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.comment.msg" /></h4>
			</div>
			<div class="modal-body">
			    <h3 class="text-danger"><fmt:message key="tiles.institution.device.manager.update.device.success" /></h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default confirm_button" id="refrashMsg"><fmt:message key="tiles.institution.org.department.confirm" /></button>
		      </div> 
		</div>
	</div>
</div>
<!-- 修改锁屏密码 -->
<div class="modal fade" id="updateLockPassword" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.device.manager.update.terminal.password" /></h4>
			</div>
			<div class="modal-body">
			  <form id="cleanPasswordForm">
				<table class="edit-table">
				    <tbody>
					   <tr>
					    <td class="td-label" style="width:20%"><fmt:message key="tiles.institution.device.manager.lock.screen.password" /><span class="red">*</span></td>
					   	<td style="width:80%">
					   		<div>
						   		<input type="text" id="lockPassword" name="lockPassword" value="">
						   		<input type="hidden" id="userIdPassword" name="userIdPassword" />
						   		<input type="hidden" id="typePassword" name="typePassword" />
						   		<input type="hidden" id="udidPassword" name="udidPassword" />
					   		</div>
					   	</td>
					</tr>
				</tbody>
			</table>
			</form>
			</div>
			 <div class="modal-footer">
			 	<button type="button" class="btn btn-default" id="lock"><fmt:message key="tiles.institution.device.manager.lock" /></button>
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.close" /></button>
		      </div>
		</div>
	</div>
</div>
<!-- 修改锁屏密码提示信息 -->
<div class="modal fade" id="lockMsg" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.comment.msg" /></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.institution.device.manager.update.password.msg" /></h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default confirm_button" id="updateLock"><fmt:message key="tiles.institution.org.department.confirm" /></button>
		      </div> 
		</div>
	</div>
</div>
<!-- 修改锁屏密码提示信息 -->
<div class="modal fade" id="lockMessage" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.comment.msg" /></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.institution.device.manager.send.request.success" /></h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default confirm_button" id="updateLockMsg"><fmt:message key="tiles.institution.org.department.confirm" /></button>
		      </div> 
		</div>
	</div>
</div>
<!-- 锁定终端屏幕 -->
<div class="modal fade" id="lockTermin" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.comment.msg" /></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.institution.device.manager.take.off.screen.info" /></h3>
			    <input type="hidden" id="userIdTermin" name="userIdTermin" />
			    <input type="hidden" id="typeTermin" name="typeTermin" />
			    <input type="hidden" id="udidTermin" name="udidTermin" />
			    <input type="hidden" id="idTermin" name="idTermin" />
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default confirm_button" id="lockTerminSure"><fmt:message key="tiles.institution.org.department.confirm" /></button>
		      </div> 
		</div>
	</div>
</div>
<!-- 恢复出厂设置  -->
<div class="modal fade" id="defaultSet" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.comment.msg" /></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.institution.device.manager.init.device.msg" /></h3>
			    <input type="hidden" id="userIdDefault" name="userIdDefault" />
			    <input type="hidden" id="typeDefault" name="typeDefault" />
			    <input type="hidden" id="udidDefault" name="udidDefault"/>
			    <input type="hidden" id="idDefault" name="idDefault"/>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default confirm_button" id="defaultSetSure"><fmt:message key="tiles.institution.org.department.confirm" /></button>
		      </div> 
		</div>
	</div>
</div>
<!-- 清楚锁屏密码  -->
<div class="modal fade" id="cleanPassword" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.comment.msg" /></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.institution.device.manager.clean.password.msg" /></h3>
			    <input type="hidden" id="userIdClean" name="userIdClean" />
			    <input type="hidden" id="typeClean" name="typeClean" />
			    <input type="hidden" id="udidClean" name="udidClean" />
			</div> 
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default confirm_button"  id="cleanPasswordSure"><fmt:message key="tiles.institution.org.department.confirm" /></button>
		      </div> 
		</div>
	</div>
</div>
<!-- 清楚锁屏密码  -->
<div class="modal fade" id="deviceBell" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.comment.msg" /></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.institution.device.manager.device.bell.msg" /></h3>
			    <input type="hidden" id="userIdDeviceBell" name="userIdDeviceBell" />
			    <input type="hidden" id="typeDeviceBell" name="typeDeviceBell" />
			    <input type="hidden" id="udidDeviceBell" name="udidDeviceBell" />
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default confirm_button" id="deviceBellSure"><fmt:message key="tiles.institution.org.department.confirm" /></button>
		      </div> 
		</div>
	</div>
</div>
<!-- 标记设备丢失 -->
<div class="modal fade" id="remarkDeviceLost" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.comment.msg" /></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.institution.device.manager.remark.device.lost" /></h3>
			    <input type="hidden" id="typeLost" name="typeLost" />
			    <input type="hidden" id="idLost" name="idLost" />
			    <input type="hidden" id="userIdLost" name="userIdLost" />
			    <input type="hidden" id="udidLost" name="udidLost" />
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default confirm_button" id="remarkDeviceLostMsg"><fmt:message key="tiles.institution.org.department.confirm" /></button>
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="general.jsp.cancel.label" /></button>
		      </div> 
		</div>
	</div>
</div>
<!-- 标记设备找回 -->
<div class="modal fade" id="remarkDeviceFind" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.comment.msg" /></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.institution.device.manager.remark.device.find" /></h3>
			    <input type="hidden" id="typeFind" name="typeLost" />
			    <input type="hidden" id="idFind" name="idLost" />
			    <input type="hidden" id="userIdFind" name="userIdFind" />
			    <input type="hidden" id="udidFind" name="udidFind" />
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default confirm_button" id="remarkDeviceFindMsg"><fmt:message key="tiles.institution.org.department.confirm" /></button>
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="general.jsp.cancel.label" /></button>
		      </div> 
		</div>
	</div>
</div>
<!-- 注销终端设备 -->
<div class="modal fade" id="logOffDevice" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.comment.msg" /></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.institution.device.manager.take.off.sure" /></h3>
			    <input type="hidden" id="userIdLogOff" name="userIdLogOff" />
			    <input type="hidden" id="typeLogOff" name="typeLogOff" />
			    <input type="hidden" id="idLogOff" name="idLogOff" />
			    <input type="hidden" id="udidLogOff" name="udidLogOff" />
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default confirm_button" id="logOffDeviceMsg"><fmt:message key="tiles.institution.org.department.confirm" /></button>
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="general.jsp.cancel.label" /></button>
		      </div> 
		</div>
	</div>
</div>
<!-- 彻底删除设备 -->
<div class="modal fade" id="delteDevice" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.comment.msg" /></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.institution.device.manager.delete.data.doing" /></h3>
			    <input type="hidden" id="userIdDelete" name="userIdDelete" />
			    <input type="hidden" id="typeDelete" name="typeDelete" />
			    <input type="hidden" id="idDelete" name="idDelete" />
			    <input type="hidden" id="udidDelete" name="udidDelete" />
			    <input type="hidden" id="deviceName" name="deviceName" />
			    <input type="hidden" id="imeino" name="imeino" />
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default confirm_button" id="deleteDeviceSure"><fmt:message key="tiles.institution.org.department.confirm" /></button>
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="general.jsp.cancel.label" /></button>
		      </div> 
		</div>
	</div>
</div>
<!-- 没有选择设备时的消息提示 -->
<div class="modal fade" id="showMsg" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.comment.msg" /></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.institution.device.manager.select.operate.device" /></h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default confirm_button" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.confirm" /></button>
		      </div>
		</div>
	</div>
</div>

<!-- 没有选择图文消息提示 -->
<div class="modal fade" id="showPicInfoMsg" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.comment.msg" /></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.institution.device.manager.select.content.info" /></h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default confirm_button" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.confirm" /></button>
		      </div>
		</div>
	</div>
</div>

<!-- 启用终端解绑  -->
<div class="modal fade" id="enableunbundle" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.comment.msg" /></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.institution.device.manager.enable.unbundle.msg" /></h3>
			    <input type="hidden" id="userIdUnbundle" name="userIdUnbindle" />
			    <input type="hidden" id="idUnbundle" name="idUnbundle" />
			    <input type="hidden" id="typeUnbundle" name="typeUnbundle" />
			    <input type="hidden" id="udidUnbundle" name="udidUnbundle" />
			</div> 
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default confirm_button"  id="unbundle"><fmt:message key="tiles.institution.org.department.confirm" /></button>
		      </div> 
		</div>
	</div>
</div>

<!-- 禁用终端解绑  -->
<div class="modal fade" id="disableunbundle" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.comment.msg" /></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.institution.device.manager.enable.disunbundle.msg" /></h3>
			    <input type="hidden" id="userIdunbundle" name="userIdunbundle" />
			    <input type="hidden" id="idunbundle" name="idunbundle" />
			    <input type="hidden" id="typeunbundle" name="typeunbundle" />
			    <input type="hidden" id="udidunbundle" name="udidunbundle" />
			</div> 
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default confirm_button"  id="disbundle"><fmt:message key="tiles.institution.org.department.confirm" /></button>
		      </div> 
		</div>
	</div>
</div>

<div class="modal fade" id="enableunbundleMsg" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.comment.msg" /></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.fragments.consumer.nav.sendmessage.success.info" /></h3>
			</div> 
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default confirm_button"  id="unbundleMsg"><fmt:message key="tiles.institution.org.department.confirm" /></button>
		      </div> 
		</div>
	</div>
</div>
<!-- 解锁设备时的设备丢失状态 -->
<div class="modal fade" id="lostDevice" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.comment.msg" /></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.institution.device.manager.has.lost" /></h3>
			</div> 
			 <div class="modal-footer">
			     <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.confirm" /></button>
		      </div> 
		</div>
	</div>
</div>
	<!-- 设备详情 -->
	<div class="modal fade" id="deviceInfo" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog" role="document" style="width:1000px;">
			<div class="modal-content" >
				<div style="min-height: 16.42857143px;padding: 15px;">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title"><fmt:message key="tiles.institution.device.manager.device.detail.info" /></h4>
				</div>
				<div style="background:#F0F0F0">
				   <div class="pop-aside detail-aside" style="height: 470px;">
					<div class="Js_dropMod1 select-box inline-select select-200" style="width: 145px; margin: 10px 0px 10px 20px; z-index: 1;">
						<input type="hidden" class="Js_hiddenVal" id="handsetUuid" name="handsetUuid" value="">
						<span class="Js_curVal1"><input type="text" value="" style="margin-top:1px;border:0px;width:90%"></span>
						<ul class="select-list" id="handsetUuidUL" style="width: 145px; display: none;"></ul>
					</div>
					<div style="margin:0;padding:0;height:1px;width:100%;border-bottom:1px solid #D2DFF4"></div>
					<ul class=" aside-list pop-asidelist" id="Js-optAsideList" style="padding-right: 0px;padding-bottom: 0px;padding-left: 0px;padding-top:35px;">
					
						<li class="aside-item"><a href="javascript:baseInfo();" class="current clicked"><span><fmt:message key="tiles.views.user.index.table.deviceInfo.base" /></span></a></li>
						<li class="aside-item"><a href="javascript:appInfo();" class=""><span><fmt:message key="tiles.views.user.index.table.deviceInfo.app" /></span></a></li>
						
						<li class="aside-item"><a href="javascript:illegalInfo();" class=""><span><fmt:message key="tiles.views.user.index.table.deviceInfo.illege" /></span></a></li>
						<li class="aside-item"><a href="javascript:locationInfo();" class=""><span><fmt:message key="tiles.views.user.index.table.deviceInfo.location" /></span></a></li>
						
						
						<li class="aside-item"><a href="javascript:net();" class=""><span><fmt:message key="tiles.views.user.index.table.deviceInfo.network" /></span></a></li>
						<li class="aside-item"><a href="javascript:savety();" class=""><span><fmt:message key="tiles.views.user.index.table.deviceInfo.safty" /></span></a></li>
						
						<li class="aside-item"><a href="javascript:userInfo();" class=""><span><fmt:message key="tiles.views.user.index.table.userinfo.title" /></span></a></li>
						
						<li class="aside-item"><a href="javascript:recordsInfo();" class=""><span><fmt:message key="tiles.views.user.index.table.deviceInfo.history" /></span></a></li>
						
						<li class="aside-item"><a href="javascript:logsInfo();" class=""><span><fmt:message key="tiles.views.user.index.table.deviceInfo.log" /></span></a></li>
					</ul>
				</div>
				<div class="pop-cons fixed-pop-cons">
				  <div class="pop-main" style="height:468px;overflow-y:hidden">
				     <div class="main-item" style="height:471px;display:block;">
				     <div class="temp-scroll-box">
				       <div class="temp-bd" style="width:799px;">
				        <div class="tempbd-item" style="width: 799px;margin-left: 0px;height: 468px;background:#F0F0F0">
				         <input type="hidden" id="userIdDeviceInfo"/>
				         <input type="hidden" id="deviceStatus" />
				        <div class="main-cons" id="deviceInfoContent">
						</div>
					    </div>
					</div>
				     </div>
				     </div>
				  </div>
				</div>
				</div> 
				 <div class="modal-footer" style="background:#F0F0F0;height:55px;">
				     <button type="button" class="btn btn-default" data-dismiss="modal" style="margin-right:470px;margin-top:-8px"><fmt:message key="tiles.institution.org.department.confirm" /></button>
				     <button type="button" class="btn btn-default" data-dismiss="modal" style="margin-right:385px;margin-top:-56px"><fmt:message key="tiles.fragments.consumer.nav.remarkdevice.cancel" /></button>
			      </div> 
			</div>
		</div>
	</div>
<!-- 解锁设备时的设备丢失状态 -->
<div class="modal fade" id="misDoLocation" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.comment.msg" /></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.institution.device.manager.miss.monitoring" /></h3>
			</div> 
			 <div class="modal-footer">
			     <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.confirm" /></button>
		      </div> 
		</div>
	</div>
</div>
