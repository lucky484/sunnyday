<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spring:url value="/customer" var="getIndex" />  
<spring:url value="/customer/appList" var="getAllAppList" /> 
<spring:url value="/customer/position" var="getDevicePosition" /> 
<spring:url value="/customer/compliant" var="getAllCompliant" /> 
<spring:url value="/customer/config" var="getAllConfig" /> 
<spring:url value="/customer/personal" var="getPersonalInfo" /> 
<spring:url value="/customer/password" var="getChangePass" /> 
<spring:url value="/customer/device" var="getDeviceInfo" /> 

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
				<h4 class="modal-title"><fmt:message key="tiles.fragments.consumer.nav.sendmessage.head"/></h4>
			</div>
			<div class="modal-body">
				 <div class="row">
	              <div class="panel-body">
	              	<form id="sendMessageFrm" class="bs-example form-horizontal">
	              		<input type="hidden" id="udidMessage" name="udidMessage" />
	              		<input type="hidden" id="userIdMessage" name="userIdMessage" />
	              		<div class="form-group">
							<label class="col-lg-2 control-label"><fmt:message key="tiles.fragments.consumer.nav.sendmessage.title"/><span class="text-danger">*</span></label>
							<div class="col-lg-10">
								 <input type="text" class="form-control" id="messageTitle" name="messageTitle" data-parsley-required="true" 
									 data-parsley-trigger="blur" data-parsley-maxlength="20">
							</div>
						</div>
						<div style="margin-top:20px;"></div>
						<div class="form-group">
							<label class="col-lg-2 control-label"><fmt:message key="tiles.fragments.consumer.nav.sendmessage.content"/><span class="text-danger">*</span></label>
							<div class="col-lg-10">
								<textarea class="form-control" id="messageContent" name="messageContent" style="min-height: 200px;"
									data-parsley-required="true" data-parsley-trigger="blur" data-parsley-maxlength="500"></textarea>
							</div>
						</div>
	              	</form>
	              </div>
            </div>
			</div>
			 <div class="modal-footer">
			 	<button type="button" class="btn btn-default btn-sm  btn-success" id="sendToClient"><fmt:message key="tiles.fragments.consumer.nav.sendmessage.sendbtn"/></button>
		        <button type="button" class="btn btn-default" id="closeSendMessage"><fmt:message key="tiles.fragments.consumer.nav.sendmessage.closebtn"/></button>
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
				<h4 class="modal-title"><fmt:message key="tiles.fragments.consumer.nav.sendmessage.success.tip"/></h4>
			</div>
			<div class="modal-body">
			    <h3 class="text-danger"><fmt:message key="tiles.fragments.consumer.nav.sendmessage.success.info"/></h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default btn-sm  btn-success" id="success"><fmt:message key="tiles.fragments.consumer.nav.sendmessage.success.confirm"/></button>
		      </div> 
		</div>
	</div>
</div>
<!-- 更新设备信息弹出框 -->
<div class="modal fade" id="refrashBatch" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.fragments.consumer.nav.updatedevice.head"/></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.fragments.consumer.nav.updatedevice.title"/></h3>
			    <input type="hidden" id="typeRefrash" name="typeRefrash" />
			    <input type="hidden" id="udidRefrash" name="udideRefrash" />
			    <input type="hidden" id="userIdRefrash" name="userIdRefrash" />
			</div>
			 <div class="modal-footer">
			 	<button type="button" class="btn btn-default btn-sm  btn-success" id="refrashSure"><fmt:message key="tiles.fragments.consumer.nav.updatedevice.confirm"/></button>
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.fragments.consumer.nav.updatedevice.close"/></button>
		      </div>
		</div>
	</div>
</div>
<!-- 更新设备信息消息提示 -->
<div class="modal fade" id="refrash" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.fragments.consumer.nav.updatedevice.success.tip"/></h4>
			</div>
			<div class="modal-body">
			    <h3 class="text-danger"><fmt:message key="tiles.fragments.consumer.nav.updatedevice.success.info"/></h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default btn-sm  btn-success" id="refreshMsg"><fmt:message key="tiles.fragments.consumer.nav.updatedevice.success.confirm"/></button>
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
				<h4 class="modal-title"><fmt:message key="tiles.fragments.consumer.nav.lockterm.head"/></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.fragments.consumer.nav.lockterm.title"/></h3>
			    <input type="hidden" id="userIdTermin" name="userIdTermin" />
			    <input type="hidden" id="typeTermin" name="typeTermin" />
			    <input type="hidden" id="udidTermin" name="udidTermin" />
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default btn-sm  btn-success" id="lockTerminSure"><fmt:message key="tiles.fragments.consumer.nav.lockterm.confirm"/></button>
		      </div> 
		</div>
	</div>
</div>
<!--终端设备响铃-->
<div class="modal fade" id="deviceBell" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.fragments.consumer.nav.devicebell.head"/></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.fragments.consumer.nav.devicebell.title"/></h3>
			    <input type="hidden" id="userIdDeviceBell" name="userIdDeviceBell" />
			    <input type="hidden" id="typeDeviceBell" name="typeDeviceBell" />
			    <input type="hidden" id="udidDeviceBell" name="udidDeviceBell" />
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default btn-sm  btn-success" id="deviceBellSure"><fmt:message key="tiles.fragments.consumer.nav.devicebell.confirm"/></button>
		      </div> 
		</div>
	</div>
</div>
<!-- 清除锁屏密码  -->
<div class="modal fade" id="cleanPassword" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.fragments.consumer.nav.cleanpassword.head"/></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.fragments.consumer.nav.cleanpassword.title"/></h3>
			    <input type="hidden" id="userIdClean" name="userIdClean" />
			    <input type="hidden" id="typeClean" name="typeClean" />
			    <input type="hidden" id="udidClean" name="udidClean" />
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default btn-sm  btn-success" id="cleanPasswordSure"><fmt:message key="tiles.fragments.consumer.nav.cleanpassword.confirm"/></button>
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
				<h4 class="modal-title"><fmt:message key="tiles.fragments.consumer.nav.defaultsetting.head"/></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.fragments.consumer.nav.defaultsetting.title"/></h3>
			    <input type="hidden" id="userIdDefault" name="userIdDefault" />
			    <input type="hidden" id="typeDefault" name="typeDefault" />
			    <input type="hidden" id="udidDefault" name="udidDefault"/>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default btn-sm  btn-success" id="defaultSetSure"><fmt:message key="tiles.fragments.consumer.nav.defaultsetting.confirm"/></button>
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
				<h4 class="modal-title"><fmt:message key="tiles.fragments.consumer.nav.remarkdevice.head"/></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.fragments.consumer.nav.remarkdevice.title"/></h3>
			    <input type="hidden" id="typeLost" name="typeLost" />
			    <input type="hidden" id="idLost" name="idLost" />
			    <input type="hidden" id="userIdLost" name="userIdLost" />
			    <input type="hidden" id="udidLost" name="udidLost" />
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default btn-sm  btn-success" id="remarkDeviceLostMsg"><fmt:message key="tiles.fragments.consumer.nav.remarkdevice.confirm"/></button>
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.fragments.consumer.nav.remarkdevice.cancel"/></button>
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
				<h4 class="modal-title"><fmt:message key="tiles.fragments.consumer.nav.logoffdevice.head"/></h4>
			</div>
			<div class="modal-body">
			    <h3><fmt:message key="tiles.fragments.consumer.nav.logoffdevice.title"/></h3>
			    <input type="hidden" id="userIdLogOff" name="userIdLogOff" />
			    <input type="hidden" id="typeLogOff" name="typeLogOff" />
			    <input type="hidden" id="idLogOff" name="idLogOff" />
			    <input type="hidden" id="udidLogOff" name="udidLogOff" />
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default btn-sm  btn-success" id="logOffDeviceMsg"><fmt:message key="tiles.fragments.consumer.nav.logoffdevice.confirm"/></button>
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.fragments.consumer.nav.logoffdevice.cancel"/></button>
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
			    <input type="hidden" id="typeUnLock" name="typeUnLock" />
			    <input type="hidden" id="idUnLock" name="idUnLock" />
			    <input type="hidden" id="udidUnLock" name="udidUnLock" />
			    <input type="hidden" id="userIdUnLock" name="userIdUnLock" />
			</div>
			 <div class="modal-footer">
			 	<button type="button" class="btn btn-default confirm_button" id="unLockDeviceSure"><fmt:message key="tiles.institution.org.department.confirm" /></button>
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.close" /></button>
		      </div>
		</div>
	</div>
</div>

<script type="text/javascript">
	function loadIndex(){
		window.location.href = "${getIndex}";
	}
	function loadAppList(){
		window.location.href = "${getAllAppList}";
	}
	function loadposition(){
		window.location.href = "${getDevicePosition}";
	}
	function loadCompliant(){
		window.location.href = "${getAllCompliant}";
	}
	function loadConfig(){
		window.location.href = "${getAllConfig}";
	}
	function loadPersonal(){
		window.location.href = "${getPersonalInfo}";
	}
	function loadPassword(){
		window.location.href = "${getChangePass}";
	}
	function loadDeviceLog(){
		window.location.href = "${getDeviceInfo}";
	}
	
	//=============================以下代码是ascde左侧菜单的所有事件
	//获取设备的id
	function getId(){
		return $('#leftSelect').children('option:selected').val(); 
	}
	//获取设备的udid
	function getUdid(){
		return $('#leftSelect').children('option:selected').attr('id');
	}

	//这个是aside左侧的的菜单事件
	//发送消息通知
	function sengMessageBatch(){
		//打开发送消息的提示框
		var udid = getUdid();
		var uid = $('#uid').val();
		$("#sendInfoMessage").modal();
		$("#udidMessage").val(udid);
		$('#userIdMessage').val(uid);
		
	}
	
	//发送消息的post请求
    function sendInfoMessage(messageTitle,message,udid,uid){
		var id = getId();
    	var csrf = "${_csrf.token}";
		$.post(ctx+"/customer/sendInfoMessage",{messageTitle:messageTitle,message:message,udid:udid,uid:uid,id:id,_csrf:csrf},function(data){
 		  $("#sendSuccess").modal();
		   $('#sendInfoMessage').modal('hide');
		   $("#success").click(function(){
			   $("#sendSuccess").modal('hide');
			   window.location.reload();	
		   });
 	   },'json');
    }
	//更新设备信息
	function refrashDeviceBatch(type){
		var udid = getUdid();
		var uid = $('#uid').val();
		$("#refrashBatch").modal();
	    $("#typeRefrash").val(type);
		$("#udidRefrash").val(udid);
		$("#userIdRefrash").val(uid);
	}
	//锁定终端屏幕
	function lockTerminBatch(type){
		var udid = getUdid();
		var uid = $('#uid').val();
		$("#lockTermin").modal();
		$("#typeTermin").val(type);
		$("#udidTermin").val(udid);
		$("#userIdTermin").val(uid);
	}
	//终端设备响铃
	 function deviceBellBatch(type){
		 var udid = getUdid();
		 var uid = $('#uid').val();
		 $("#deviceBell").modal();
		 $("#userIdDeviceBell").val(uid);
  	     $("#typeDeviceBell").val(type);
  	     $("#udidDeviceBell").val(udid);
	}
	//清除锁屏幕密码
	function cleanPasswordBatch(type){
		var udid = getUdid();
		var uid = $('#uid').val();
		 $("#cleanPassword").modal();
		 $("#typeClean").val(type);
		 $("#userIdClean").val(uid);
		 $("#udidClean").val(udid);
	}
	//恢复出厂设置
	function defaultSetBatch(type){
		 var udid = getUdid();
		 var uid = $('#uid').val();
		 $("#defaultSet").modal();
  	   	 $("#userIdDefault").val(uid);
  	     $("#typeDefault").val(type);
  	     $("#udidDefault").val(udid);
		
	}
	//标记设备丢失
	function remarkDeviceBatch(type){
		 var udid = getUdid();
		 var uid = $('#uid').val();
		 var id = getId();
		  if(type == 1){
   		   $("#remarkDeviceLost").modal();
   		   $("#typeLost").val(type);
   		   $("#idLost").val(id);
   		   $("#userIdLost").val(uid);
   		   $("#udidLost").val(udid);
   	   }else{
   		   $("#remarkDeviceFind").modal();
   		   $("#typeFind").val(type);
   		   $("#idFind").val(id);
   		   $("#userIdFind").val(uid);
   		   $("#udidFind").val(udid);
   	   }
	}
	//标记设备丢失并向后台传送数据
	function remarkDeviceStatus(type,userId,id,udid){
		 var csrf = "${_csrf.token}";
  	   $.post(ctx+"/customer/remarkDevice",{type:type,uid:userId,id:id,sn:udid,_csrf:csrf},function(data){
	       $("#remarkDeviceLost").modal('hide');
	   	   $("#remarkDeviceFind").modal('hide');
		   //$("#lockDeviceMsg").modal();
		   $("#message").click(function(){
				  window.location.reload();	
			   });
	   	   },'json');
     }
	//注销终端设备
	function logOffDeviceBatch(type){
		 var udid = getUdid();
		 var uid = $('#uid').val();
		 $("#logOffDevice").modal();
  	     $("#typeLogOff").val(type);
  	     var id = getId();
  	     $("#idLogOff").val(id);
  	     $("#userIdLogOff").val(uid);
  	     $("#udidLogOff").val(udid);
		
	}
	
	$(function(){
		//点击发送消息按钮触发事件
		$("#sendToClient").on('click',function(){
		   //加入空值的验证
		   var validator = $('#sendMessageFrm').parsley();
		   validator.validate();
           if(validator.isValid()){
        	   var udid = $("#udidMessage").val();
        	   var uid = $('#userIdMessage').val();
         	   var messageTitle = $("#messageTitle").val();
         	   var message = $("#messageContent").val();
         	   sendInfoMessage(messageTitle,message,udid,uid);
           }
        });
		//点击关闭发送消息按钮的button
		$("#closeSendMessage").on('click',function(){
			$("#sendInfoMessage").modal('hide');
			$("#messageTitle").val('');
	   	    $("#messageContent").val('');
	     });
		
		//点击更新设备信息按钮触发事件
		$("#refrashSure").on('click',function(){
     	   var type = $("#typeRefrash").val();
     	   var did = $("#udidRefrash").val();
     	   var uid = $("#userIdRefrash").val();
     	   var csrf = "${_csrf.token}";
     	   var id = getId();
     	   $.post(ctx+"/customer/sendCommandToClient",{flag:type,sn:did,uid:uid,id:id,_csrf:csrf},function(data){
     		   if(data.status = "success"){
     			  $("#refrashBatch").modal('hide');
     			  $("#refrash").modal();
	   	   		   $("#refreshMsg").click(function(){
	   	   		 		$("#refrash").modal('hide');
	   	   		 		window.location.reload();
	      		   });
     		   }
   	   },'json');
    });
	//锁定终端屏幕
	$("#lockTerminSure").on('click',function(){
		 var uid = $("#userIdTermin").val();
      	 var type =  $("#typeTermin").val();
      	 var udid = $("#udidTermin").val();
      	 var csrf = "${_csrf.token}";
      	 var id = getId();
       	 $.post(ctx+"/customer/sendCommandToClient",{flag:type,uid:uid,sn:udid,id:id,_csrf:csrf},function(data){
     		   if(data.status = "success"){
     			   $("#lockTermin").modal('hide');
     			  // $("#lockDeviceMsg").modal();
     			   $("#message").click(function(){
     				   window.location.reload();	
     			   });
     		   }
     	   },'json');
	   });
	//终端设备响铃
	$("#deviceBellSure").on('click',function(){
	 	 var uid = $("#userIdDeviceBell").val();
     	 var type = $("#typeDeviceBell").val();
     	 var sn = $("#udidDeviceBell").val();
     	 var id = getId();
     	 var csrf = "${_csrf.token}";
       	 $.post(ctx+"/customer/sendCommandToClient",{flag:type,uid:uid,sn:sn,id:id,_csrf:csrf},function(data){
     		   if(data.status = "success"){
     			  	$("#deviceBell").modal('hide');
    			    //$("#lockDeviceMsg").modal();
    			    $("#message").click(function(){
    				   window.location.reload();	
    			   });
     		   }
     	   },'json');
	   });
	//清除锁屏密码
	 $("#cleanPasswordSure").on('click',function(){
		 var userId = $("#userIdClean").val();
   	   	 var type =  $("#typeClean").val();
   	     var udid = $("#udidClean").val();
   	     var id = getId();
	   	  var csrf = "${_csrf.token}";
	   	     $.post(ctx+"/customer/sendCommandToClient",{flag:type,uid:userId,sn:udid,id:id,_csrf:csrf},function(data){
	   		   if(data.status = "success"){
	   			  $("#cleanPassword").modal('hide');
	   			  //$("#lockDeviceMsg").modal();
	   			   $("#message").click(function(){
	   				   window.location.reload();	
	   			   });
	   		   }
	   	    },'json');
     });
	//恢复出厂设置
	$("#defaultSetSure").on('click',function(){
		var uid = $("#userIdDefault").val();
  	    var type =  $("#typeDefault").val();
  	    var udid = $("#udidDefault").val();
  	    var id = getId();
  	  var csrf = "${_csrf.token}";
  	    $.post(ctx+"/customer/sendCommandToClient",{flag:type,uid:uid,sn:udid,id:id,_csrf:csrf},function(data){
   		   if(data.status = "success"){
   			   $("#defaultSet").modal('hide');
   			   //$("#lockDeviceMsg").modal();
   			   $("#message").click(function(){
   				   window.location.reload();	
   			   });
   		   }
   	   },'json');
	});
	//标记设备丢失
	//设备丢失
    $("#remarkDeviceLostMsg").on('click',function(){
 	   	   var type = $("#typeLost").val();
		   var id = $("#idLost").val();
		   var userId = $("#userIdLost").val();
		   var udid = $("#udidLost").val();
		   remarkDeviceStatus(type,userId,id,udid);
    });
    //设备找回
    $("#remarkDeviceFindMsg").click(function(){
 	   var _this = $(this)
 	   _this.attr("disabled",true);
 	   var type = $("#typeFind").val();
		   var id = $("#idFind").val();
		   var userId = $("#userIdFind").val();
		   var sn = $("#udidFind").val();
		   remarkDeviceStatus(type,userId,id,sn);
    });
	//注销终端设备
    $("#logOffDeviceMsg").on('click',function(){
 	   var userId = $("#userIdLogOff").val();
  	   var type =  $("#typeLogOff").val();
  	   var id =  $("#idLogOff").val();
  	   var udid = $("#udidLogOff").val();
  	   var csrf = "${_csrf.token}";
 	   $.post(ctx+"/customer/sendCommandToClient",{flag:type,uid:userId,id:id,sn:udid,_csrf:csrf},function(data){
  		   if(data.status = "success"){
  			  $("#logOffDevice").modal('hide');
  			   //$("#lockDeviceMsg").modal();
  			   $("#message").click(function(){
  				   window.location.reload();	
  			   });
  		   }
  	   },'json');
    });
})
	/* function unLockTerminBatch(type){
		var udid = getUdid();
		var uid = $('#uid').val();
		var id = getId();
		$("#unLockDevice").modal();
		$("#typeUnLock").val(type);
		$("#udidUnLock").val(udid);
		$("#idUnLock").val(id);
		$("#userIdUnLock").val(uid);
	}
	
	$("#unLockDevice").click(function(){
		var userId = $("#userIdUnLock").val();
		var id = $("#idUnLock").val();
		var udid = $("#udidUnLock").val();
		var type = $("#typeUnLock").val();
		$.post(ctx+"/customer/sendCommandToClient",{flag:type,uid:userId,id:id,sn:udid,_csrf:csrf},function(data){
	  		   if(data.status = "success"){
	  			  $("#unLockDevice").modal('hide');
		  			   $("#message").click(function(){
		  				   window.location.reload();	
		  			   });
	  		   }
	  	   },'json');
	}); */

Date.prototype.Format = function(fmt)   
	{   
	  var o = {   
	    "M+" : this.getMonth()+1,                 //月份   
	    "d+" : this.getDate(),                    //日   
	    "h+" : this.getHours(),                   //小时   
	    "m+" : this.getMinutes(),                 //分   
	    "s+" : this.getSeconds(),                 //秒   
	    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
	    "S"  : this.getMilliseconds()             //毫秒   
	  };   
	  if(/(y+)/.test(fmt))   
	    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
	  for(var k in o)   
	    if(new RegExp("("+ k +")").test(fmt))   
	  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
	  return fmt;   
	}  

</script>
