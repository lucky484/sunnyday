<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=vAXGBRGMlqQdmpmO1G8LlVuMMf2R0leh&s=1"></script>
<spring:url value="/resources/js/jquery.cookie.js" var="cookieJs" />
<spring:url value="/institution/picAndTxtMeg/getAllPicAndTxts" var="pagesUrl" />
<spring:url value="/resources/images/a0.png" var="defaultFaceUrl" />
<script src="${cookieJs}"></script>
<spring:url value="/resources/js/datatables-1.10.11/lang/language.lang" var="dtLangUrl" />
<spring:url
	value="/resources/js/datatables-1.10.11/js/jquery.dataTables.js"
	var="dataTableJs" />
<script src="${dataTableJs}"></script>
<spring:url
	value="/resources/js/datatables-1.10.11/js/dataTables.bootstrap.js"
	var="dataTableBootstrapJs" />
<script src="${dataTableBootstrapJs}"></script>
    <script type="text/javascript">
      var languageUrl;
   	  var lang = "${dtLangUrl}";
  	  var str = lang.substring(lang.lastIndexOf("/")+1,lang.lastIndexOf("."));
  	  var str1 = lang.substring(0,lang.lastIndexOf("/"));
  	  var str2 = lang.substring(lang.lastIndexOf("."),lang.length);
	  if(navigator.language != "undefined" && navigator.language != null){
		  languageUrl = str1 + "/" + str + "_" + navigator.language + str2;
	  }else{
		  languageUrl = str1 + "/" + str + "_en-US" + str2;
	  }
      $(function(){
    	  var defaultData = ${tree};
    	  $("#tree").treeview({
              color: "#428bca",
              showBorder: false,
              data: defaultData,
            });
    	  $("#tree").on('nodeSelected',function(event, data) {
    		  loadData(data.tags.id);
        	 });
   		var myNode=$('#tree').treeview('getUnselected');
   		if(window.sessionStorage.departmentId != undefined && window.sessionStorage.departmentId != "null"){
			for(var i=0;i<myNode.length;i++){
	        	if(parseInt(window.sessionStorage.departmentId) == myNode[i].tags.id){
	        		getParent(myNode[i]);
	        		$('#tree').treeview('selectNode', [ myNode[i].nodeId, {silent : true} ]);
	        		window.sessionStorage.removeItem("departmentId");
	        		 loadData(myNode[i].tags.id);
	        	}
	        }
		}else{
	   		$('#tree').treeview('selectNode', [ 0, {silent : true} ]);
		}
   		var id;
   		loadData(id);
      });
      
  	function getParent(node){
		var obj = node;
		while(obj.parentId != null){
			var parentNode = $('#tree').treeview('getParent',  [obj.nodeId, {silent : true} ]);
			$('#tree').treeview('expandNode', [ parentNode.nodeId,{ silent: true}]);
			obj = parentNode;
		}
	}
      
      function searchDeviceLists(){
    	  var node = $('#tree').treeview('getSelected');
    	  var id;
    	  for(var i=0;i<node.length;i++){
    		  id = node[i].tags.id;
    	  }
    	  loadData(id);
    	  $('#deviceManager').dataTable().fnDraw();
      }
      
      function cleanDeviceLists(){
    	  $("#seletedStatus").val('');
    	  $("#IosStatus").val('');
    	  $("#searchUserName").val('');
    	  $("#deviceType").val('');
    	  $("#esnorimei").val('');
    	  $("#sequenceNumber").val('');
    	  $(".Js_curVal").find("input").val('<fmt:message key="tiles.institution.device.manager.all.status" />');
    	  var node = $('#tree').treeview('getSelected');
    	  var id;
    	  for(var i=0;i<node.length;i++){
    		  id = node[i].tags.id;
    	  }
    	  loadData(id);
    	  $('#deviceManager').dataTable().fnDraw();
      }
      
      function loadData(id){
    	  if(id == undefined){
    		  id = null;
    	  }
    	  if($.cookie("device-tags")=="null"||$.cookie("device-tags")==undefined){ 
  				 var seletedStatus = $("#seletedStatus").val();
  			}else{
  				var seletedStatus =$.cookie("device-tags");
      		   	$("option[value="+seletedStatus+"]").selected();
  				$.cookie('device-tags',null,{path: "/"});
			}
    	  
    	  if($.cookie("device-type-tags")=="null"||$.cookie("device-type-tags")==undefined){ 
    		  var iosStatus =  $("#IosStatus").val();
			}else{
				var iosStatus =$.cookie("device-type-tags");
   		   	   //$("option[value="+iosStatus+"]").selected();
   		   	   	$("#IosStatus").val(iosStatus);
				$.cookie('device-type-tags',null,{path: "/"});
				
			}
    	  var searchUserName =  $("#searchUserName").val();
    	  var deviceType = $("#deviceType").val();
    	  var esnorimei = $("#esnorimei").val();
    	  var sequenceNumber = $("#sequenceNumber").val();
    	  
    	  $('#deviceManager').dataTable({   
    	  				"dom":"<'m-r m-t-lg pull-right'f>t<'row '<'col-lg-3'l>r<'col-lg-3'i><'pull-right'p>>",
    	  			    "autoWidth": false,
    	  			    "searching" : false,
    	 				"stateSave" : true,
    	 				"ordering" : false,
    	 				"bSort" : false,
    	 				"pageLength" : 10,
    	 				"pagingType" : "full_numbers",
    	 				"serverSide" : true,
    	 				"bDestroy" : true,
    	 				"oLanguage":{
    	 					"sUrl":languageUrl
    	 				},
    	  				"ajax" : {
    	  					"url" : "deviceManager/queryDeviceInfo?id="+id,
    	  					"dataSrc" : "data",
    	  					"data" : {
    	  						"seletedStatus" : seletedStatus,
    	  						"searchUserName" : searchUserName,
    	  						"deviceType" : deviceType,
    	  						"sequenceNumber" : sequenceNumber,
    	  						"esnorimei" : esnorimei,
    	  						"iosStatus" : iosStatus
    	  					}
    	  				},
    	  				columns : [{
    	  	    	  					 data: "id"
    	  	    	  				},{
    	  	    	  					data : "userName"
    	  	    	  				},{
    	  	    	  					data : "realName"
    	  	    	  				}, {
    	  	    	  					data : "device_name"
    	  	    	  				}, {
    	  	    	  					data : "sn"
    	  	    	  				}, {
    	  	    	  					data : "esnoOrImei"
    	  	    	  				},  {
    	  	    	  					data : "device_status"
    	  	    	  				}, {
    	  	    	  					data : ""
    	  	    	  				}],
    	  				"columnDefs" : [
    								{
    									"targets" : 0,
    									"render" : function(data, type, row) {
    										if($("#selected").val() == 2){
	    										return '<label class="checkbox m-l m-t-none m-b-none i-checks"> <input type="checkbox" id="'+row.id+'" name="checkList" checked onclick="removeCheckAll(' + row.id + ');"/><i></i></label>'+
	    										'<input type="hidden" id="'+row.userId+'" name="userId" value="'+row.userId+'"/><input type="hidden" name="id" value="'+row.id+'"/>'+
	    										'<input type="hidden" name="sn" value="'+row.sn+'"/><input type="hidden" name="deviceStatus" value="'+row.device_status+'">'+
	    										'<input type="hidden" name="hours" value="'+row.time+'" /><input type="hidden" name="dayToMss" value="'+row.paramSetTime+'" />'+
	    										'<input type="hidden" name="visitStatus" value="'+row.visit_status+'" /><input type="hidden" name="deviceName" value="'+row.device_name+'" />'+
	    										'<input type="hidden" name="imeino" value="'+row.esnoOrImei+'" /><input type="hidden" name="lost_status" value="'+row.lost_status+'" />';
    										}else{
    											return '<label class="checkbox m-l m-t-none m-b-none i-checks"> <input type="checkbox" id="'+row.id+'" name="checkList" onclick="removeCheckAll(' + row.id + ');"/><i></i></label>'+
	    										'<input type="hidden" id="'+row.userId+'" name="userId" value="'+row.userId+'"/><input type="hidden" name="id" value="'+row.id+'"/>'+
	    										'<input type="hidden" name="sn" value="'+row.sn+'"/><input type="hidden" name="deviceStatus" value="'+row.device_status+'">'+
	    										'<input type="hidden" name="hours" value="'+row.time+'" /><input type="hidden" name="dayToMss" value="'+row.paramSetTime+'" />'+
	    										'<input type="hidden" name="visitStatus" value="'+row.visit_status+'" /><input type="hidden" name="deviceName" value="'+row.device_name+'" />'+
	    										'<input type="hidden" name="imeino" value="'+row.esnoOrImei+'" /><input type="hidden" name="lost_status" value="'+row.lost_status+'" />';
    										}
    									}
    								},
    								{
    									"targets" : 1,
    									"render" : function(data, type, row) {
    										return '<a href="javascript:void(0);" class="text-primary" style="color:#6787B8;" onclick="deviceInfo('+row.userId+','+row.id+')">'+row.userName +'</a>';
    									}
    								},
    								 {
    									"targets" : 6,
    									"render" : function(data, type, row) {
    										var deviceStatus = "";
    										var irrStatus = "";
    										var lostStatus = "";
    										var breakStatus = "";
    										var visitStatus = "";
    										var table1 = "<table><tr>";
    										var table2 = "</tr></table>";
    											if(row.deviceStatus == "1"){
    												    deviceStatus = '<td><img style="cursor: pointer;width:16px;height:16px;" src="../resources/images/icons/logout.png" title="<fmt:message key="tiles.institution.device.manager.cance" />"></td>';
    											}else if(row.deviceStatus == "2"){
        												deviceStatus = '<td><img style="cursor: pointer;width:16px;height:16px;" src="../resources/images/icons/borken.png" title="<fmt:message key="tiles.institution.device.manager.take.off" />"></td>';
    											}else if(row.deviceStatus == "3"){
   														deviceStatus = '<td><img style="cursor: pointer;width:16px;height:16px;" src="../resources/images/icons/eye-out.png" title="<fmt:message key="tiles.institution.device.manager.wait.monitoring" />"></td>';
    											}else if(row.deviceStatus == "4"){
   														deviceStatus = '<td><img style="cursor: pointer;width:16px;height:16px;" src="../resources/images/icons/eye.png" title="<fmt:message key="tiles.institution.device.manager.monitoring" />"></td>';
    											}
    										if(row.device_type == "android"){
    											if(row.irr_status == 0){
    												irrStatus = '<td><img style="cursor: pointer;width:16px;height:16px;" src="../resources/images/icons/android-blur.png" title="<fmt:message key="tiles.institution.device.manager.device.not.violation" />"></td>';
    											}else if(row.irr_status == 1){
    												irrStatus = '<td><img style="cursor: pointer;width:16px;height:16px;" src="../resources/images/icons/android-red.png" title="<fmt:message key="tiles.institution.device.manager.device.violation" />"></td>';
    											}
    									    }else if(row.device_type == "ios"){
    									    	if(row.irr_status == 0){
    												irrStatus = '<td><img style="cursor: pointer;width:16px;height:16px;" src="../resources/images/icons/apple_logo_green.png" title="<fmt:message key="tiles.institution.device.manager.ios.device.not.violation" />"></td>';
    											}else if(row.irr_status == 1){
    												irrStatus = '<td><img style="cursor: pointer;width:16px;height:16px;" src="../resources/images/icons/apple_logo_red.png" title="<fmt:message key="tiles.institution.device.manager.ios.device.violation" />"></td>';
    											}
    									    }
    											if(row.lost_status == 0){
    												lostStatus = '<td><img style="cursor: pointer;width:16px;height:16px;" src="../resources/images/icons/lost.gif" title="<fmt:message key="tiles.institution.device.manager.device.has.lost" />"></td>';
    											}else{
    												//lostStatus = '<td style="cursor: pointer;width:26px;">&nbsp;&nbsp;&nbsp;</td>';
    											}
    											if(row.break_status == 1){
    												breakStatus = '<td><img style="cursor: pointer;width:16px;height:16px;" src="../resources/images/icons/wall.png" title="<fmt:message key="tiles.institution.device.manager.device.has.root" />"></td>';
    											}else{
    												//breakStatus = '<td style="cursor: pointer;width:26px;">&nbsp;&nbsp;&nbsp;</td>';
    											}
    											if(row.visit_status == 0){
    												visitStatus = '<td><img style="cursor: pointer;width:16px;height:16px;" src="../resources/images/icons/locked.png" title="<fmt:message key="tiles.institution.device.manager.device.user.visit.lock"/>"></td>';
    											}else if(row.visit_status == 1){
    												visitStatus = '<td><img style="cursor: pointer;width:16px;height:16px;" src="../resources/images/icons/bomb.png" title="<fmt:message key="tiles.institution.device.manager.device.destruction.data"/>"></td>';
    											}else{
    												//visitStatus = '<td style="cursor: pointer;width:26px;">&nbsp;&nbsp;&nbsp;</td>';
    											}
    										     return table1 + deviceStatus + irrStatus + lostStatus + breakStatus + visitStatus +table2;
    										}
    								},
    								{
    									"targets" : 7,
    									"render" : function(data, type, row) {
    										if("${softtek_manager.auth}"=="0"&&"${softtek_manager.user}"!=""){
    											return '<i class="fa fa-shield"></i>';
    										}
    										var toggleDiv1 = '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown"><i class="i  i-settings"></i></a><ul class="dropdown-menu" style="margin-left:-100px;margin-top:-80px;">';
    										var toggleDiv2 = '</ul>';
    										var lockStatus = "";
    										var lostStatus = "";
    										var sendMessage = "";
    										var updateDevice = "";
    										var mapService = "";
    										var updatePassword = "";
    										var lockDevice = "";
    										var defaultSet = "";
    										var cleanPassword = "";
    										var deviceBell = "";
    										var deleteData = "";
    										var lostDevice = "";
    										var updateComDevice = "";
    										var logOffDevice = "";
    										var unbundle = "";
    										var map = "";
    											if(row.device_status == 1){
    												return '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">'
    														+ '<i class="i  i-settings"></i>'
    														+ '</a>'
    														+'<ul class="dropdown-menu" style="margin-left:-100px;margin-top:-80px;">'
    														+ '<li><a href="javascript:void(0);" onclick="logOffDevice(10,'+row.userId+','+row.id+','+"'"+row.sn+"'"+');">'
    														+ '<i class="fa fa-close"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.take.off.device" /></a></li>'
    														+ '<li><a href="javascript:void(0);" onclick="deleteDevice(11,'+row.userId+','+row.id+','+"'"+row.sn+"'"+','+"'"+row.device_name+"'"+','+"'"+row.esnoOrImei+"'"+');">'
    														+ '<i class="fa fa-close"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.delete.device" /></a></li>'
    														+ '</ul>';
    											}else{
    												if(row.visit_status != null){
    													lockStatus = '<li><a href="javascript:void(0);" onclick="sendLockDeviceToClient(0,'+row.userId+','+row.id+','+"'"+row.sn+"'"+','+row.lost_status+','+"'"+row.device_type+"'"+')"><i class="fa fa-unlock"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.unlock.device" /></a></li>';
    												}else{
    													lockStatus = '<li><a href="javascript:void(0);" onclick="lockDevice(1,'+row.userId+','+row.id+','+"'"+row.sn+"'"+')"><i class="fa fa-lock"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.lock.terminal.device" /></a></li>';
    												}
    												if(row.lost_status != null){
    													lostStatus = '<li><a href="javascript:void(0);" onclick="remarkDevice(2,'+row.userId+','+row.id+','+"'"+row.sn+"'"+')"><i class="fa fa-pencil"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.remark.find" /></a></li>';
    												}else{
    													lostStatus = '<li><a href="javascript:void(0);" onclick="remarkDevice(1,'+row.userId+','+row.id+','+"'"+row.sn+"'"+')"><i class="fa fa-pencil"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.remark.lost" /></a></li>';
    												}
    												if(row.device_type == "android"){
	    												defaultSet = '<li><a href="javascript:void(0);" onclick="defaultSet(7,'+row.userId+','+"'"+row.sn+"'"+','+row.id+')"><i class="fa fa-mail-reply"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.initialize.device" /></a></li>';
    												}
    												sendMessage = '<li><a href="javascript:void(0);" onclick="sengMessage('+row.userId+','+"'"+row.sn+"'"+')"><i class="fa fa-envelope"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.send.system.msg" /></a></li>';
    												if(row.device_type == "android"){
    													updatePassword = '<li><a href="javascript:void(0);" onclick="updatePassowrd(5,'+row.userId+','+"'"+row.sn+"'"+')"><i class="fa fa-pencil"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.update.password" /></a></li>';
    												}
    												if(row.enableUnbund == 1){
	    												unbundle = '<li><a href="javascript:void(0);" onclick="unbundleDevice(13,'+row.userId+','+row.id+','+"'"+row.sn+"'"+')"><i class="fa fa-unlink"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.disable.terminal" /></a></li>';
    												}else{
    													unbundle = '<li><a href="javascript:void(0);" onclick="unbundleDevice(12,'+row.userId+','+row.id+','+"'"+row.sn+"'"+')"><i class="fa fa-unlink"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.enable.terminal" /></a></li>';
    												}
    												if(row.deviceStatus == "4"){
	    												updateDevice = '<li><a href="javascript:void(0);" onclick="refrashDevice(3,'+row.userId+','+"'"+row.sn+"'"+')"><i class="fa fa-refresh"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.update.device.info" /></a></li>';
	    												map = '<li><a href="javascript:void(0);" onclick="devicePlace('+row.id+','+row.userId+','+"'"+row.deviceStatus+"'"+')"><i class="fa fa-map-marker"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.map" /></a></li>';
	    												lockDevice = '<li><a href="javascript:void(0);" onclick="lockTermin(6,'+row.userId+','+"'"+row.sn+"'"+','+row.id+')"><i class="fa fa-lock"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.lock.screen" /></a></li>';
	    												cleanPassword = '<li><a href="javascript:void(0);" onclick="cleanPassword(8,'+row.userId+','+"'"+row.sn+"'"+')"><i class="i i-trashcan"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.clean.password" /></a></li>';
	    												deviceBell = '<li><a href="javascript:void(0);" onclick="deviceBell(9,'+row.userId+','+"'"+row.sn+"'"+')"><i class="fa fa-bell"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.device.bell" /></a></li>';
    												}
    												logOffDevice = '<li><a href="javascript:void(0);" onclick="logOffDevice(10,'+row.userId+','+row.id+','+"'"+row.sn+"'"+')"><i class="glyphicon glyphicon-remove"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.take.off.device" /></a></li>';
    												                   
    												/* return toggleDiv1+lockStatus+sendMessage+updateDevice+map+updatePassword+defaultSet+lockDevice+cleanPassword+deviceBell+deleteData+lostStatus+updateComDevice+unbundle+logOffDevice+toggleDiv2;
    												             var    锁定终端      发送信息            更新设备信息      定位     修改锁屏密码        恢复出厂      锁定屏幕          清除锁屏密码                            响铃             ""       丢失状态找回     ""              jiebang   注销 ;       
    												                                            更新设备信息/发送通知消息/定位设备服务/终端设备响铃/锁定终端设备/锁定终端屏幕/修改锁屏密码/清除锁屏密码/标记设备丢失/启用终端解绑/恢复出厂设置/注销终端设备      */                                                  
    												return toggleDiv1+updateDevice+sendMessage+map+deviceBell+lockStatus+lockDevice+updatePassword+cleanPassword+lostStatus+unbundle+defaultSet+logOffDevice+deleteData+updateComDevice+toggleDiv2;
    											}
    										return '';
    									}
    								}]
    	  			});
      }
	    $("#selected").change(function(){
	    	var arr = [];
	    	var number;
       	 if($("#selected").val() == 1){
       		 $("input[type='checkbox']").each(function(i,item){
       			  $(this).prop("checked",true);
       		 });
       	 }else if($("#selected").val() == 2){
       		 $("input[type='checkbox']").each(function(i,item){
       			 $(this).prop("checked",true);
       		 });
       	 }else{
       		 $("input[type='checkbox']").each(function(i,item){
       			  number = i+1;
       			  if(item.checked == true){
       			  	arr.push(item.value);
       			  }
      		 });
       		 if(number == arr.length){
       			$("input[type='checkbox']").each(function(i,item){
         			$(this).prop("checked",false);
        		 });
       		 }
       	 }
        });
	   function removeCheckAll(id){
 		   var arr = [];
		   var number;
		   $("input[name='checkList']").each(function(i,item){
			   number = i+1;
			   if($(this).prop("checked") === true){
				   arr.push(item.value);
			   }
		   }); 
 		   if($("#"+id).prop("checked") == false){
			     $("#selected").val("0");
		   }else{
			   if(arr.length == number){
				   $("#selected").val("1");
			   }
		   } 
	   }
           //点击更多显示div
          function showul(){
    		$('.showul').toggle();
          }
          //鼠标离开时隐藏div
           $(".showul").on("mouseleave",function(){
        	  $('.showul').hide();
          }); 
           function lockDevice(type,userId,id,sn){
        	   $("#lockDevice").modal();
        	   $("#typeLock").val(type);
        	   $("#userIdLock").val(userId);
        	   $("#idLock").val(id);
        	   $("#udidLock").val(sn);
           } 
            //点击发送按钮
           $("#sendLockDevice").click(function(){
        	 var _this = $(this)
        	 _this.attr("disabled",true);
        	 var csrf = "${_csrf.token}";
          	 var lockStatus = $("input[name='lockStatus']:checked").val();
          	 var userId =  $("#userIdLock").val();
          	 var type = $("#typeLock").val();
          	 var id = $("#idLock").val();
          	 var sn = $("#udidLock").val();
          	 var selectVal = $("#selected").val();
   	   		 var node =  $("#tree").treeview('getSelected');
             var departmentId = "";
	       	 var parentDepartmentId;
	       	   for(var i=0;i<node.length;i++){
		       		   departmentId = node[i].tags.id
		       		   if(node[i].tags.parent == null){
		       			   parentDepartmentId = null;
		       		   }else{
		       			   parentDepartmentId = node[i].tags.parent.id;
		       		   }
		       	   }
	          	 $.post("deviceManager/sendCommandToClient",{flag:type,lockStatus:lockStatus,userId:userId,id:id,sn:sn,selectVal:selectVal,departmentId:departmentId,parentDepartmentId:parentDepartmentId,_csrf:csrf},function(data){
	          		if(data.result == 1){
	          			$(".notify").notify({type:"success",message: { html: false, text: "<fmt:message key='tiles.institution.device.manager.operate.success' />"}}).show();
	          			window.sessionStorage.departmentId = departmentId;
	     		    	window.location.reload();	
	          			/* $("#lockDeviceMsg").modal();
		     		    	$("#message").click(function(){
		     		    	    window.sessionStorage.departmentId = departmentId;
		     		    		window.location.reload();	
		     		    	}); */
	          		   } 
	     	   },'json');
           });
           //解锁设备的请求
           function sendLockDeviceToClient(type,userId,id,sn,lostStatus,deviceType){
        	   if(lostStatus == 0){
        		   $("#lostDevice").modal();
        	   }else{
	        	   var selectVal = $("#selected").val();
	        	   var node =  $("#tree").treeview('getSelected');
	        	   var departmentId = "";
	        	   var parentDepartmentId;
	        	   for(var i=0;i<node.length;i++){
	        		   departmentId = node[i].tags.id
	        		   if(node[i].tags.parent == null){
	        			   parentDepartmentId = null;
	        		   }else{
	        			   parentDepartmentId = node[i].tags.parent.id;
	        		   }
	        	   }
	        	   var csrf = "${_csrf.token}";
	        	 	  var url = "deviceManager/sendCommandToClient";
		        	   $.post(url,{flag:type,userId:userId,id:id,sn:sn,selectVal:selectVal,departmentId:departmentId,parentDepartmentId:parentDepartmentId,_csrf:csrf},function(data){
		        		   if(data.result == 1){
		        			   $(".notify").notify({type:"success",message: { html: false, text: "<fmt:message key='tiles.institution.device.manager.operate.success' />"}}).show();
		        				   window.sessionStorage.departmentId = departmentId;
		        		    		window.location.reload();	
		        		    	/* $("#unlockDeviceMsg").modal();
		        		    	$("#unlockMessage").click(function(){
		        		    		window.sessionStorage.departmentId = departmentId;
		        		    		window.location.reload();	
		        		    	}); */
		        		    }
		        	   },'json');
        	   }
           }
           
           function sengMessage(userId,sn){
        	   $("#messageForm")[0].reset();
        	   $("#sendInfoMessage").modal();
				$("#messageForm").parsley().reset();
        	   $("#userIdMessage").val(userId);
        	   $("#udidMessage").val(sn);
           }
           $("#sendToClient").click(function(){
        	   var userId = $("#userIdMessage").val();
        	   var sn = $("#udidMessage").val();
        	   var messageTitle = $("#messageTitle").val();
        	   var message = $("#msg").val();
        	   var selectVal = $("#selected").val();
        	   sendInfoMessage(userId,messageTitle,message,sn,selectVal);
           });
           //发送消息的post请求
           function sendInfoMessage(userId,messageTitle,message,sn,selectVal){
        	   var validator = $("#messageForm").parsley();
        	   var csrf = "${_csrf.token}";
        	   var node =  $("#tree").treeview('getSelected');
        	   var departmentId = "";
        	   var parentDepartmentId;
        	   for(var i=0;i<node.length;i++){
        		   departmentId = node[i].tags.id
        		   if(node[i].tags.parent == null){
        			   parentDepartmentId = null;
        		   }else{
        			   parentDepartmentId = node[i].tags.parent.id;
        		   }
        	   }
        	   validator.validate();
			   if(validator.isValid()){
			   var _this = $("#sendToClient");
		       _this.attr("disabled",true);
        	   $.post("deviceManager/sendInfoMessageBatch",{userId:userId,messageTitle:messageTitle,message:message,sn:sn,selectVal:selectVal,departmentId:departmentId,parentDepartmentId:parentDepartmentId,_csrf:csrf},function(data){
        		   $(".notify").notify({type:"success",message: { html: false, text: "<fmt:message key='tiles.institution.device.manager.operate.success' />"}}).show();
        			   window.sessionStorage.departmentId = departmentId;
    				   history.go(0);	
        		   /* $("#sendSuccess").modal();
        			   $("#success").click(function(){
        				   window.sessionStorage.departmentId = departmentId;
        				   history.go(0);;	
        			   }); */
        	   },'json');
				}
           }
           //更新设备信息
           function refrashDevice(type,userId,sn){
        	   var selectVal = $("#selected").val();
         	   var node =  $("#tree").treeview('getSelected');
               var departmentId = "";
         	   var parentDepartmentId;
         	   for(var i=0;i<node.length;i++){
	       		   departmentId = node[i].tags.id
	       		   if(node[i].tags.parent == null){
	       			   parentDepartmentId = null;
	       		   }else{
	       			   parentDepartmentId = node[i].tags.parent.id;
	       		   }
	       	   }
        	   var csrf = "${_csrf.token}";
        	   $.post("deviceManager/sendCommandToClient",{flag:type,userId:userId,sn:sn,selectVal:selectVal,departmentId:departmentId,parentDepartmentId:parentDepartmentId,_csrf:csrf},function(data){
        		   if(data.status = "success"){
        			   $(".notify").notify({type:"success",message: { html: false, text: "<fmt:message key='tiles.institution.device.manager.operate.success' />"}}).show();
        				   window.sessionStorage.departmentId = departmentId;
         				  history.go(0);	
        			   /* $("#refrash").modal();
        			   $("#refrashMsg").click(function(){
        				  window.sessionStorage.departmentId = departmentId;
        				  history.go(0);	
        			   }); */
        		   }
        	   },'json');
           }
           //修改锁屏密码
           function updatePassowrd(type,userId,sn){
        	   $("#cleanPasswordForm")[0].reset();
        	   $("#updateLockPassword").modal();
        	   $("#userIdPassword").val(userId);
        	   $("#typePassword").val(type);
        	   $("#udidPassword").val(sn);
           }
           $("#lock").click(function(){
        	   $("#lockMsg").modal();
           });
           $("#updateLock").click(function(){
        	   var _this = $(this)
        	   _this.attr("disabled",true);
        	   var userId = $("#userIdPassword").val();
        	   var sn = $("#udidPassword").val();
        	   var type = $("#typePassword").val();
        	   var lockPassword = $("#lockPassword").val();
        	   var selectVal = $("#selected").val();
         	   var node =  $("#tree").treeview('getSelected');
               var departmentId = "";
         	   var parentDepartmentId;
         	   for(var i=0;i<node.length;i++){
	       		   departmentId = node[i].tags.id
	       		   if(node[i].tags.parent == null){
	       			   parentDepartmentId = null;
	       		   }else{
	       			   parentDepartmentId = node[i].tags.parent.id;
	       		   }
	       	   }
        	   var csrf = "${_csrf.token}";
        	   $.post("deviceManager/sendCommandToClient",{flag:type,userId:userId,lockPassword:lockPassword,sn:sn,selectVal:selectVal,departmentId:departmentId,parentDepartmentId:parentDepartmentId,_csrf:csrf},function(data){
        		   if(data.status = "success"){
        			   $(".notify").notify({type:"success",message: { html: false, text: "<fmt:message key='tiles.institution.device.manager.operate.success' />"}}).show();
        				   window.sessionStorage.departmentId = departmentId;
         				  history.go(0);
        			   /* $("#lockMessage").modal();
        			   $("#updateLockMsg").click(function(){
        				   window.sessionStorage.departmentId = departmentId;
        				  history.go(0);	
        			   }); */
        		   }
        	   },'json');
           });
           //锁定终端屏幕
           function lockTermin(type,userId,sn,id){
        	   $("#lockTermin").modal();
        	   $("#userIdTermin").val(userId);
        	   $("#typeTermin").val(type);
        	   $("#udidTermin").val(sn);
        	   $("#idTermin").val(id);
           }
           $("#lockTerminSure").click(function(){
        	  var _this = $(this)
        	  _this.attr("disabled",true);
        	  var userId = $("#userIdTermin").val();
        	  var type =  $("#typeTermin").val();
        	  var sn = $("#udidTermin").val();
        	  var id = $("#idTermin").val();
        	  var selectVal = $("#selected").val();
        	   var node =  $("#tree").treeview('getSelected');
        	   var departmentId = "";
        	   var parentDepartmentId;
        	   for(var i=0;i<node.length;i++){
	       		   departmentId = node[i].tags.id
	       		   if(node[i].tags.parent == null){
	       			   parentDepartmentId = null;
	       		   }else{
	       			   parentDepartmentId = node[i].tags.parent.id;
	       		   }
	       	   }
        	  var csrf = "${_csrf.token}";
        	  $.post("deviceManager/sendCommandToClient",{flag:type,id:id,userId:userId,sn:sn,selectVal:selectVal,departmentId:departmentId,parentDepartmentId:parentDepartmentId,_csrf:csrf},function(data){
       		   if(data.status = "success"){
       			   $("#lockTermin").modal('hide');
       			 $(".notify").notify({type:"success",message: { html: false, text: "<fmt:message key='tiles.institution.device.manager.operate.success' />"}}).show();
       				 window.sessionStorage.departmentId = departmentId;
      				  history.go(0); 
       			 /* $("#lockDeviceMsg").modal();
       			   $("#message").click(function(){
       				  window.sessionStorage.departmentId = departmentId;
       				  history.go(0);	
       			   }); */
       		   }
       	   },'json');
           });
           //恢复出厂设置
           function defaultSet(type,userId,sn,id){
        	   $("#defaultSet").modal();
        	   $("#userIdDefault").val(userId);
        	   $("#typeDefault").val(type);
        	   $("#udidDefault").val(sn);
        	   $("#idDefault").val(id);
           }
           $("#defaultSetSure").click(function(){
        	   var _this = $(this)
        	   _this.attr("disabled",true);
        	   var userId = $("#userIdDefault").val();
        	   var id = $("#idDefault").val();
         	   var type =  $("#typeDefault").val();
         	   var sn = $("#udidDefault").val();
         	   var selectVal = $("#selected").val();
        	   var node =  $("#tree").treeview('getSelected');
        	   var departmentId = "";
        	   var parentDepartmentId;
        	   for(var i=0;i<node.length;i++){
	       		   departmentId = node[i].tags.id
	       		   if(node[i].tags.parent == null){
	       			   parentDepartmentId = null;
	       		   }else{
	       			   parentDepartmentId = node[i].tags.parent.id;
	       		   }
	       	   }
         	   var csrf = "${_csrf.token}";
         	  $.post("deviceManager/sendCommandToClient",{flag:type,userId:userId,sn:sn,selectVal:selectVal,departmentId:departmentId,parentDepartmentId:parentDepartmentId,id:id,_csrf:csrf},function(data){
          		   if(data.status = "success"){
          			   $("#defaultSet").modal('hide');
          			 $(".notify").notify({type:"success",message: { html: false, text: "<fmt:message key='tiles.institution.device.manager.operate.success' />"}}).show();
          				window.sessionStorage.departmentId = departmentId;
        				  history.go(0);
          			 /*  $("#lockDeviceMsg").modal();
          			   $("#message").click(function(){
          				  window.sessionStorage.departmentId = departmentId;
          				  history.go(0);	
          			   }); */
          		   }
          	   },'json');
           });
           //清除锁屏密码
           function cleanPassword(type,userId,sn){
        	   $("#cleanPassword").modal();
        	   $("#userIdClean").val(userId);
        	   $("#typeClean").val(type);
        	   $("#udidClean").val(sn);
           }
           $("#cleanPasswordSure").click(function(){
        	   var _this = $(this)
        	   _this.attr("disabled",true);
        	   var userId = $("#userIdClean").val();
         	   var type =  $("#typeClean").val();
         	   var sn = $("#udidClean").val();
         	   var selectVal = $("#selected").val();
         	   var node =  $("#tree").treeview('getSelected');
         	   var departmentId = "";
         	   var parentDepartmentId;
         	   for(var i=0;i<node.length;i++){
	       		   departmentId = node[i].tags.id
	       		   if(node[i].tags.parent == null){
	       			   parentDepartmentId = null;
	       		   }else{
	       			   parentDepartmentId = node[i].tags.parent.id;
	       		   }
	       	   }
         	   var csrf = "${_csrf.token}";
         	  $.post("deviceManager/sendCommandToClient",{flag:type,userId:userId,sn:sn,selectVal:selectVal,departmentId:departmentId,parentDepartmentId:parentDepartmentId,_csrf:csrf},function(data){
         		   if(data.status = "success"){
         			  $("#cleanPassword").modal('hide');
         			 $(".notify").notify({type:"success",message: { html: false, text: "<fmt:message key='tiles.institution.device.manager.operate.success' />"}}).show();
         				 window.sessionStorage.departmentId = departmentId;
        				  history.go(0);
         			   /* $("#lockDeviceMsg").modal();
         			   $("#message").click(function(){
         				  window.sessionStorage.departmentId = departmentId;
         				  history.go(0);	
         			   }); */
         		   }
         	   },'json');
           });
           //终端设备响铃
           function deviceBell(type,userId,sn){
        	   $("#deviceBell").modal();
        	   $("#userIdDeviceBell").val(userId);
        	   $("#typeDeviceBell").val(type);
        	   $("#udidDeviceBell").val(sn);
           }
           $("#deviceBellSure").click(function(){
        	   var _this = $(this)
        	   _this.attr("disabled",true);
        	   var userId = $("#userIdDeviceBell").val();
         	   var type =  $("#typeDeviceBell").val();
         	   var sn = $("#udidDeviceBell").val();
         	   var selectVal = $("#selected").val();
        	   var node =  $("#tree").treeview('getSelected');
        	   var departmentId = "";
        	   var parentDepartmentId;
        	   for(var i=0;i<node.length;i++){
	       		   departmentId = node[i].tags.id
	       		   if(node[i].tags.parent == null){
	       			   parentDepartmentId = null;
	       		   }else{
	       			   parentDepartmentId = node[i].tags.parent.id;
	       		   }
	       	   }
         	   var csrf = "${_csrf.token}";
         	  $.post("deviceManager/sendCommandToClient",{flag:type,userId:userId,sn:sn,selectVal:selectVal,departmentId:departmentId,parentDepartmentId:parentDepartmentId,_csrf:csrf},function(data){
         		   if(data.status = "success"){
         			   $("#deviceBell").modal('hide');
         			  $(".notify").notify({type:"success",message: { html: false, text: "<fmt:message key='tiles.institution.device.manager.operate.success' />"}}).show();
         				 window.sessionStorage.departmentId = departmentId;
        				  history.go(0);
         			  /*  $("#lockDeviceMsg").modal();
         			   $("#message").click(function(){
         				  window.sessionStorage.departmentId = departmentId;
         				  history.go(0);	
         			   }); */
         		   }
         	   },'json');
           });
           //标记设备找回，丢失
           function remarkDevice(type,userId,id,sn){
        	   if(type == 1){
        		   $("#remarkDeviceLost").modal();
        		   $("#typeLost").val(type);
        		   $("#idLost").val(id);
        		   $("#userIdLost").val(userId);
        		   $("#udidLost").val(sn);
        	   }else{
        		   $("#remarkDeviceFind").modal();
        		   $("#typeFind").val(type);
        		   $("#idFind").val(id);
        		   $("#userIdFind").val(userId);
        		   $("#udidFind").val(sn);
        	   }
           }
            //设备丢失
           $("#remarkDeviceLostMsg").click(function(){
        	   var _this = $(this)
        	   _this.attr("disabled",true);
        	   var type = $("#typeLost").val();
    		   var id = $("#idLost").val();
    		   var userId = $("#userIdLost").val();
    		   var sn = $("#udidLost").val();
    		   remarkDeviceStatus(type,userId,id,sn);
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
           function remarkDeviceStatus(type,userId,id,sn){
        	   var selectVal = $("#selected").val();
         	   var node =  $("#tree").treeview('getSelected');
         	   var departmentId = "";
         	   var parentDepartmentId;
         	   for(var i=0;i<node.length;i++){
	       		   departmentId = node[i].tags.id
	       		   if(node[i].tags.parent == null){
	       			   parentDepartmentId = null;
	       		   }else{
	       			   parentDepartmentId = node[i].tags.parent.id;
	       		   }
	       	   }
        	   var csrf = "${_csrf.token}";
        	   $.post("deviceManager/remarkDevice",{type:type,userId:userId,id:id,sn:sn,selectVal:selectVal,departmentId:departmentId,parentDepartmentId:parentDepartmentId,_csrf:csrf},function(data){
        		       $("#remarkDeviceLost").modal('hide');
        		   	   $("#remarkDeviceFind").modal('hide');
        		   	 $(".notify").notify({type:"success",message: { html: false, text: "<fmt:message key='tiles.institution.device.manager.operate.success' />"}}).show();
        		   		window.sessionStorage.departmentId = departmentId;
       				  history.go(0);
         			  /*  $("#lockDeviceMsg").modal();
         			   $("#message").click(function(){
         				  window.sessionStorage.departmentId = departmentId;
         				  history.go(0);	
         			   }); */
         	   },'json');
           }
           //注销终端设备
           function logOffDevice(type,userId,id,sn){
        	   $("#logOffDevice").modal();
        	   $("#userIdLogOff").val(userId);
        	   $("#typeLogOff").val(type);
        	   $("#idLogOff").val(id);
        	   $("#udidLogOff").val(sn);
           }
           $("#logOffDeviceMsg").click(function(){
        	   var _this = $(this)
        	   _this.attr("disabled",true);
        	   var userId = $("#userIdLogOff").val();
         	   var type =  $("#typeLogOff").val();
         	   var id =  $("#idLogOff").val();
         	   var sn = $("#udidLogOff").val();
         	   var selectVal = $("#selected").val();
        	   var node =  $("#tree").treeview('getSelected');
        	   var departmentId = "";
        	   var parentDepartmentId;
        	   for(var i=0;i<node.length;i++){
	       		   departmentId = node[i].tags.id
	       		   if(node[i].tags.parent == null){
	       			   parentDepartmentId = null;
	       		   }else{
	       			   parentDepartmentId = node[i].tags.parent.id;
	       		   }
	       	   }
         	   var csrf = "${_csrf.token}";
        	   $.post("deviceManager/sendCommandToClient",{flag:type,userId:userId,id:id,sn:sn,selectVal:selectVal,departmentId:departmentId,parentDepartmentId:parentDepartmentId,_csrf:csrf},function(data){
         		   if(data.status = "success"){
         			  $("#logOffDevice").modal('hide');
         			 $(".notify").notify({type:"success",message: { html: false, text: "<fmt:message key='tiles.institution.device.manager.operate.success' />"}}).show();
         				 window.sessionStorage.departmentId = departmentId;
        				  history.go(0);
         			  /*  $("#lockDeviceMsg").modal();
         			   $("#message").click(function(){
         				  window.sessionStorage.departmentId = departmentId;
         				  history.go(0);	
         			   }); */
         		   }
         	   },'json');
           });
           
           //启用终端解绑
           function unbundleDevice(type,userId,id,sn){
        	   if(type == "12"){
	        	   $("#enableunbundle").modal();
	        	   $("#typeUnbundle").val(type);
	        	   $("#idUnbundle").val(id);
	        	   $("#userIdUnbundle").val(userId);
	        	   $("#udidUnbundle").val(sn);
        	   }else{
        		   $("#disableunbundle").modal();
	        	   $("#typeunbundle").val(type);
	        	   $("#idunbundle").val(id);
	        	   $("#userIdunbundle").val(userId);
	        	   $("#udidunbundle").val(sn);
        	   }
           }
           
           $("#unbundle").click(function(){
        	   var _this = $(this)
        	   _this.attr("disabled",true);
        	   var userId = $("#userIdUnbundle").val();
         	   var type =  $("#typeUnbundle").val();
         	   var id = $("#idUnbundle").val();
         	   var sn = $("#udidUnbundle").val();
         	   var selectVal = $("#selected").val();
        	   var node =  $("#tree").treeview('getSelected');
        	   var departmentId = "";
        	   var parentDepartmentId;
        	   for(var i=0;i<node.length;i++){
	       		   departmentId = node[i].tags.id
	       		   if(node[i].tags.parent == null){
	       			   parentDepartmentId = null;
	       		   }else{
	       			   parentDepartmentId = node[i].tags.parent.id;
	       		   }
	       	   }
         	   var csrf = "${_csrf.token}";
         	  $.post("deviceManager/sendCommandToClient",{flag:type,userId:userId,id:id,sn:sn,selectVal:selectVal,departmentId:departmentId,parentDepartmentId:parentDepartmentId,_csrf:csrf},function(data){
        		   if(data.status = "success"){
        			   $("#enableunbindle").modal('hide');
        			   $(".notify").notify({type:"success",message: { html: false, text: "<fmt:message key='tiles.institution.device.manager.operate.success' />"}}).show();
        				   window.sessionStorage.departmentId = departmentId;
         				  history.go(0);
        			  /*  $("#enableunbundleMsg").modal();
        			   $("#unbundleMsg").click(function(){
        				  window.sessionStorage.departmentId = departmentId;
        				  history.go(0);
        			   }); */
        		   }
        	   },'json');
           });
           
           $("#disbundle").click(function(){
        	   var _this = $(this)
        	   _this.attr("disabled",true);
        	   var userId = $("#userIdunbundle").val();
         	   var type =  $("#typeunbundle").val();
         	   var id = $("#idunbundle").val();
         	   var sn = $("#udidunbundle").val();
         	   var selectVal = $("#selected").val();
        	   var node =  $("#tree").treeview('getSelected');
        	   var departmentId = "";
        	   var parentDepartmentId;
        	   for(var i=0;i<node.length;i++){
	       		   departmentId = node[i].tags.id
	       		   if(node[i].tags.parent == null){
	       			   parentDepartmentId = null;
	       		   }else{
	       			   parentDepartmentId = node[i].tags.parent.id;
	       		   }
	       	   }
         	   var csrf = "${_csrf.token}";
         	  $.post("deviceManager/sendCommandToClient",{flag:type,userId:userId,id:id,sn:sn,selectVal:selectVal,departmentId:departmentId,parentDepartmentId:parentDepartmentId,_csrf:csrf},function(data){
        		   if(data.status = "success"){
        			   $("#disableunbundle").modal('hide');
        			   $(".notify").notify({type:"success",message: { html: false, text: "<fmt:message key='tiles.institution.device.manager.operate.success' />"}}).show();
        				   window.sessionStorage.departmentId = departmentId;
         				  history.go(0);
        			  /*  $("#enableunbundleMsg").modal();
        			   $("#unbundleMsg").click(function(){
        				  window.sessionStorage.departmentId = departmentId;
        				  history.go(0);
        			   }); */
        		   }
        	   },'json');
           });
            //彻底删除设备
            function deleteDevice(type,userId,id,sn,deviceName,imeino){
            	$("#delteDevice").modal();
            	$("#userIdDelete").val(userId);
            	$("#typeDelete").val(type);
            	$("#idDelete").val(id);
            	$("#udidDelete").val(sn);
            	$("#deviceName").val(deviceName);
            	$("#imeino").val(imeino);
            }
            $("#deleteDeviceSure").click(function(){
            	var _this = $(this)
            	_this.attr("disabled",true);
            	var userId = $("#userIdDelete").val();
            	var type = $("#typeDelete").val();
            	var id = $("#idDelete").val();
            	var sn = $("#udidDelete").val();
            	var deviceName = $("#deviceName").val();
            	var imeino = $("#imeino").val();
                var selectVal = $("#selected").val();
           	    var node =  $("#tree").treeview('getSelected');
           	    var departmentId = "";
           	    var parentDepartmentId;
           	    for(var i=0;i<node.length;i++){
  	       		   departmentId = node[i].tags.id
  	       		   if(node[i].tags.parent == null){
  	       			   parentDepartmentId = null;
  	       		   }else{
  	       			   parentDepartmentId = node[i].tags.parent.id;
  	       		   }
  	       	   }
            	var csrf = "${_csrf.token}";
            	 $.post("deviceManager/sendCommandToClient",{flag:type,userId:userId,id:id,sn:sn,deviceName:deviceName,imeino:imeino,selectVal:selectVal,departmentId:departmentId,parentDepartmentId:parentDepartmentId,_csrf:csrf},function(data){
           		   if(data.status = "success"){
           			  $("#delteDevice").modal('hide');
           			 $(".notify").notify({type:"success",message: { html: false, text: "<fmt:message key='tiles.institution.device.manager.operate.success' />"}}).show();
      				   window.sessionStorage.departmentId = departmentId;
       				  history.go(0);
           			   /* $("#lockDeviceMsg").modal();
           			   $("#message").click(function(){
           				window.sessionStorage.departmentId = departmentId;
           				  history.go(0);	
           			   }); */
           		   }
           	   },'json');
            });

           //解锁终端设备批量处理
           function unlockDeviceBatch(type){
        	   var arr = [];
        	   $("input[name='checkList']").each(function(i,item){
	        	   if(item.checked == true){
	        		   arr.push(item.checked);
	        	   }
        	   });
        	   if(arr.length > 0){
        		   $("#unLockDevice").modal();
            	   $("#typeBatch").val(type);
        	   }else{
            	   $("#showMsg").modal();
        	   }
           }
           $("#unLockDeviceSure").click(function(){
        	   var _this = $(this);
        	   _this.attr("disabled",true);
        	   $("#unLockDevice").modal('hide');
        	   var type = $("#typeBatch").val();
        	   var userIds = "";
        	   var ids = "";
        	   var sns = "";
        	   $("input[name='checkList']").each(function(i,item){
	        	   if($(this).prop("checked") == true && $(this).parent().parent().find("input").eq(10).val() != 0){
	        		    userIds += $(this).parent().next().val() + ",";
	        		    ids += $(this).parent().next().next().val() + ",";
	        		    sns += $(this).parent().next().next().next().val() + ",";
	        	   }
        	   });
        	   var userId = userIds.substring(0,userIds.length-1);
        	   var id = ids.substring(0,ids.length-1);
        	   var sn = sns.substring(0,sns.length-1);
        	   var selectVal = $("#selected").val();
         	   var node =  $("#tree").treeview('getSelected');
         	   var departmentId = "";
         	   var parentDepartmentId;
         	   for(var i=0;i<node.length;i++){
	       		   departmentId = node[i].tags.id
	       		   if(node[i].tags.parent == null){
	       			   parentDepartmentId = null;
	       		   }else{
	       			   parentDepartmentId = node[i].tags.parent.id;
	       		   }
	       	   }
        	   var csrf = "${_csrf.token}";
        	   $.post("deviceManager/sendCommandToClient",{flag:type,userId:userId,id:id,sn:sn,selectVal:selectVal,departmentId:departmentId,parentDepartmentId:parentDepartmentId,_csrf:csrf},function(data){
        		   $(".notify").notify({type:"success",message: { html: false, text: "<fmt:message key='tiles.institution.device.manager.operate.success' />"}}).show();
    				   window.sessionStorage.departmentId = departmentId;
     				  history.go(0);
        		  /*  $("#lockDeviceMsg").modal();
        		   $("#message").click(function(){
        			  window.sessionStorage.departmentId = departmentId;
        			  history.go(0);	
        		   }); */
        	   });
           });
           //锁定终端设备
           function lockDeviceBatch(type){
        	   var arr = [];
        	   var userIds = "";
        	   var ids = "";
        	   var sns = "";
        	   $("input[name='checkList']").each(function(i,item){
	        	   if(item.checked == true){
	        		   arr.push(item.checked);
	        		   userIds += $(this).parent().next().val() + ",";
	        		   ids += $(this).parent().next().next().val() + ",";
	        		   sns += $(this).parent().next().next().next().val() + ",";
	        	   }
        	   });
        	   var userId = userIds.substring(0,userIds.length-1);
        	   var id = ids.substring(0,ids.length-1);
        	   var sn = sns.substring(0,sns.length-1);
        	   if(arr.length > 0){
        		   $("#lockDevice").modal();
            	   $("#typeLock").val(type);
            	   $("#userIdLock").val(userId);
            	   $("#idLock").val(id);
            	   $("#udidLock").val(sn);
        	   }else{
            	   $("#showMsg").modal();
        	   }
           }
           //批量发送消息通知
           function sengMessageBatch(){
        	   var arr = [];
        	   var userIds = "";
        	   var sns = "";
        	   $("input[name='checkList']").each(function(i,item){
	        	   if(item.checked == true){
	        		   arr.push(item.checked);
	        		   userIds += $(this).parent().next().val() + ",";
	        		   sns += $(this).parent().next().next().next().val() + ",";
	        	   }
        	   });
        	   var userId = userIds.substring(0,userIds.length-1);
        	   var sn = sns.substring(0,sns.length-1);
        	   if(arr.length > 0){
        		   $("#messageForm")[0].reset();
        		   $("#sendToClient").removeAttr("disabled");
        		   $("#sendInfoMessage").modal();
        		   $("#messageForm").parsley().reset();
        		   $("#userIdMessage").val(userId);
        		   $("#udidMessage").val(sn);
        	   }else{
            	   $("#showMsg").modal();
        	   }
           }
           
           function sendPicMsgBatch()
           {
        	   var arr = [];
        	   var userIds = "";
        	   var sns = "";
        	   $("input[name='checkList']").each(function(i,item){
	        	   if(item.checked == true){
	        		   arr.push(item.checked);
	        		   sns += $(this).parent().next().next().next().val() + ",";
	        		   userIds += $(this).parent().next().val() + ",";
	        	   }
        	   });
        	   var userId = userIds.substring(0,userIds.length-1);
        	   var sn = sns.substring(0,sns.length-1);
        	   if(arr.length > 0){
        		   $('#pushPicAndInfoTb').DataTable({
        			    "dom":"<'m-r m-t-lg pull-right'f>t<'row '<'col-lg-2'l>r<'col-lg-3'i><'pull-right'p>>",
						"searching" : false,
						"stateSave" : true,
						"ordering" : false,
						"bSort" : false,
						"serverSide" : true,
						"pageLength" : 10,
						"pagingType" : "full_numbers",
						"bDestroy" : true,
						"oLanguage" : {
							"sUrl":languageUrl
						},
       					"ajax" : {
       	  					"dataType" : 'json',
       						"type" : "GET",
       						"url" : ctx+"/institution/deviceManager/queryPicInfoList?now="+ new Date().getTime(),
       					},
       					
       					"columns" : [ {
							data : "id"
						}, {
							data : "msgTheme"
						}, {
							data : "createUser"
						}],
						
						columnDefs : [
        				   {
        					   
        					   targets : [0],
							   render : function(data, type, full, meta) {
								   var str = '<input type="radio" id="picInfo'+full.id+'" name="picInfo"'; 
	        						 str += 'value="'+full.id+'" />';
	        						/*  str += '<input type="hidden" id="nameId'+row.id+'" value="'+row.blackWhiteName+'" />'; */
	        						return str;
								}
        					},
        					{
        						targets : [1],
   							   render : function(data, type, full, meta) {
   	        						return full.msgTheme;
   								}
        					},
        					{
        						targets : [2],
   							    render : function(data, type, full, meta) {
   	        						return full.createUser;
   								}
        					},
        					{
        						targets : [3],
   							    render : function(data, type, full, meta) {
   	        						return formatCurrentTimeMillis(full.createTime);
   								}
        					}]	
        		  });
        		   
        		   $("#loadBwNameList").modal();
        		   $("#userIdMessage").val(userId);
        		   $("#udidMessage").val(sn);
        	   }else{
            	   $("#showMsg").modal();
        	   }
           }
           
           // 发送图文消息
           function sendPicInfo()
           {
        	   
        	   var length = $("input[name='picInfo']:checked").length;
        	   
        	   if (length < 1)
        	   {
        		   $("#showPicInfoMsg").modal();
        		   return;
        	   }
        	   var picInfoId = $("input[name='picInfo']:checked").val();
        	   
        	   var userIds = $("#userIdMessage").val();
        	   var sns = $("#udidMessage").val();
        	   var csrf = "${_csrf.token}";
        	   $.ajax({
       			"dataType" : 'json',
       			"type" : "POST",
       			"async" : false,
       			"url" : ctx
       					+ "/institution/deviceManager/pushMsgToMobile?now="+ new Date().getTime()+"&_csrf="+csrf,
       			"data" : {
       				"picInfoId" : picInfoId,
       				"sns" : sns,
       				"userIds" : userIds
       			},
       			"success" : function(data) {
					$("#sendSuccess").modal();
     			   	$("#success").click(function(){
     			   	$("#loadBwNameList").modal("hide");	
     			   	$("#sendSuccess").modal("hide");
     			   });
       			}
       			});
           }
           
           //批量更新设备信息
           function refrashDeviceBatch(type){
        	   var arr = [];
        	   var userIds = "";
        	   var sns = "";
        	   $("input[name='checkList']").each(function(i,item){
	        	   if(item.checked == true){
	        		   arr.push(item.checked);
	        		   userIds += $(this).parent().next().val() + ",";
	        		   sns += $(this).parent().next().next().next().val() + ",";
	        	   }
        	   });
        	   var userId = userIds.substring(0,userIds.length-1);
        	   var sn = sns.substring(0,sns.length-1);
        	   if(arr.length > 0){
        		   $("#refrashBatch").modal();
        		   $("#typeRefrash").val(type);
        		   $("#userIdRefrash").val(userId);
        		   $("#udidRefrash").val(sn);
        	   }else{
            	   $("#showMsg").modal();
        	   }
           }
           $("#refrashSure").click(function(){
        	   var _this = $(this)
        	   _this.attr("disabled",true);
        	   var userId = $("#userIdRefrash").val();
        	   var type = $("#typeRefrash").val();
        	   var sn = $("#udidRefrash").val();
        	   var selectVal = $("#selected").val();
        	   var departmentId = "";
        	   var parentDepartmentId; 
        	   var node =  $("#tree").treeview('getSelected');
        	   for(var i=0;i<node.length;i++){
        		   departmentId = node[i].tags.id
        		   if(node[i].tags.parent == null){
        			   parentDepartmentId = null;
        		   }else{
        			   parentDepartmentId = node[i].tags.parent.id;
        		   }
        	   }
        	   var csrf = "${_csrf.token}";
        	   $.post("deviceManager/sendCommandToClient",{flag:type,userId:userId,sn:sn,selectVal:selectVal,departmentId:departmentId,parentDepartmentId:parentDepartmentId,_csrf:csrf},function(data){
        		   $(".notify").notify({type:"success",message: { html: false, text: "<fmt:message key='tiles.institution.device.manager.operate.success' />"}}).show();
    				   window.sessionStorage.departmentId = departmentId;
     				  history.go(0);   
        		   /* $("#refrash").modal();
        			   $("#refrashMsg").click(function(){
        				  window.sessionStorage.departmentId = departmentId;
        				  history.go(0);	
        			   }); */
        	   });
           });
           //清除锁屏密码批量处理
           function cleanPasswordBatch(type){
        	   var arr = [];
        	   var userIds = "";
        	   var sns = "";
        	   $("input[name='checkList']").each(function(i,item){
	        	   if(item.checked == true){
	        		   arr.push(item.checked);
	        		   if($(this).parent().next().eq(4).val() != "0"){
		        		   userIds += $(this).parent().next().val() + ",";
		        		   sns += $(this).parent().next().next().next().val() + ",";
	        		   }
	        	   }
        	   });
        	   var userId = userIds.substring(0,userIds.length-1);
        	   var sn = sns.substring(0,sns.length-1);
        	   if(arr.length > 0){
        		   $("#cleanPassword").modal();
        		   $("#typeClean").val(type);
        		   $("#userIdClean").val(userId);
        		   $("#udidClean").val(sn);
        	   }else{
            	   $("#showMsg").modal();
        	   }
           }
           //修改锁屏密码批量处理
           function updatePasswordBatch(type){
        	   var arr = [];
        	   var userIds = "";
        	   var sns = "";
        	   $("input[name='checkList']").each(function(i,item){
	        	   if(item.checked == true){
	        		   arr.push(item.checked);
	        		   userIds += $(this).parent().next().val() + ",";
	        		   sns += $(this).parent().next().next().next().val() + ",";
	        	   }
        	   });
        	   var userId = userIds.substring(0,userIds.length-1);
        	   var sn = sns.substring(0,sns.length-1);
        	   if(arr.length > 0){
        		   $("#cleanPasswordForm")[0].reset();
        		   $("#updateLockPassword").modal();
            	   $("#userIdPassword").val(userId);
            	   $("#typePassword").val(type);
            	   $("#udidPassword").val(sn);
        	   }else{
            	   $("#showMsg").modal();
        	   }
           }
           //锁定终端屏幕批量处理
           function lockTerminBatch(type){
        	   var arr = [];
        	   var userIds = "";
        	   var sns = "";
        	   $("input[name='checkList']").each(function(i,item){
	        	   if(item.checked == true){
	        		   arr.push(item.checked);
	        		   userIds += $(this).parent().next().val() + ",";
	        		   sns += $(this).parent().next().next().next().val() + ",";
	        	   }
        	   });
        	   var userId = userIds.substring(0,userIds.length-1);
        	   var sn = sns.substring(0,sns.length-1);
        	   if(arr.length > 0){
        		   $("#lockTermin").modal();
            	   $("#userIdTermin").val(userId);
            	   $("#typeTermin").val(type);
            	   $("#udidTermin").val(sn);
        	   }else{
            	   $("#showMsg").modal();
        	   }
           }
           function deviceBellBatch(type){
        	   var arr = [];
        	   var userIds = "";
        	   var sns = "";
        	   $("input[name='checkList']").each(function(i,item){
	        	   if(item.checked == true){
	        		   arr.push(item.checked);
	        		   if($(this).parent().next().eq(4).val() != "0"){
		        		   userIds += $(this).parent().next().val() + ",";
		        		   sns += $(this).parent().next().next().next().val() + ",";
	        		   }
	        	   }
        	   });
        	   var userId = userIds.substring(0,userIds.length-1);
        	   var sn = sns.substring(0,sns.length-1);
        	   if(arr.length > 0){
        		   $("#deviceBell").modal();
            	   $("#userIdDeviceBell").val(userId);
            	   $("#typeDeviceBell").val(type);
            	   $("#udidDeviceBell").val(sn);
        	   }else{
            	   $("#showMsg").modal();
        	   }
           }
           //标记设备丢失批量处理
             function remarkDeviceBatch(type){
        	     var arr = [];
            	 var userIds = "";
            	 var sns = "";
            	 var ids = "";
          	   $("input[name='checkList']").each(function(i,item){
  	        	   if(item.checked == true){
  	        		   arr.push(item.checked);
  	        		   userIds += $(this).parent().next().val() + ",";
  	        		   ids += $(this).parent().next().next().val() + ",";
  	        		   sns += $(this).parent().next().next().next().val() + ",";
  	        	   }
          	   });
          	   var userId = userIds.substring(0,userIds.length-1);
          	   var id = ids.substring(0,ids.length-1);
          	   var sn = sns.substring(0,sns.length-1);
          	   if(arr.length > 0){
          		 if(type == 1){
          		   $("#remarkDeviceLost").modal();
          		   $("#typeLost").val(type);
          		   $("#idLost").val(id);
          		   $("#userIdLost").val(userId);
          		   $("#udidLost").val(sn);
          	   }else{
          		   $("#remarkDeviceFind").modal();
          		   $("#typeFind").val(type);
          		   $("#idFind").val(id);
          		   $("#userIdFind").val(userId);
          		   $("#udidFind").val(sn);
          	   }
          	   }else{
              	   $("#showMsg").modal();
          	   }
        	   
           }
           function unbundleDeviceBatch(type){
        	   var arr = [];
        	   var userIds = "";
        	   var sns = "";
        	   var ids = "";
        	   $("input[name='checkList']").each(function(i,item){
	        	   if(item.checked == true){
	        		   arr.push(item.checked);
	        		   if($(this).parent().next().eq(4).val() != "0"){
		        		   userIds += $(this).parent().next().val() + ",";
		        		   ids += $(this).parent().next().next().val() + ",";
		        		   sns += $(this).parent().next().next().next().val() + ",";
	        		   }
	        	   }
        	   });
        	   var userId = userIds.substring(0,userIds.length-1);
        	   var id = ids.substring(0,ids.length-1);
        	   var sn = sns.substring(0,sns.length-1);
        	   if(arr.length > 0){
        		   $("#enableunbundle").modal();
            	   $("#typeUnbundle").val(type);
            	   $("#idUnbundle").val(id);
            	   $("#userIdUnbundle").val(userId);
            	   $("#udidUnbundle").val(sn);
        	   }else{
            	   $("#showMsg").modal();
        	   }
           }
           //恢复出厂设置批量处理
             function defaultSetBatch(type){
           	   var arr = [];
           	   var userIds = "";
           	   var sns = "";
           	   var ids = "";
           	   $("input[name='checkList']").each(function(i,item){
   	        	   if(item.checked == true){
   	        		   arr.push(item.checked);
   	        		   if($(this).parent().next().eq(4).val() != "0"){
	   	        		   userIds += $(this).parent().next().val() + ",";
	   	        		   sns += $(this).parent().next().next().next().val() + ",";
	   	        		   ids += $(this).parent().next().next().val() + ",";
   	        		   }
   	        	   }
           	   });
           	   var userId = userIds.substring(0,userIds.length-1);
           	   var sn = sns.substring(0,sns.length-1);
           	   if(arr.length > 0){
           		  $("#defaultSet").modal();
           	   	  $("#userIdDefault").val(userId);
           	      $("#typeDefault").val(type);
           	      $("#udidDefault").val(sn);
           	   }else{
               	   $("#showMsg").modal();
           	   }
           }
           //注销终端设备批量操作
             function logOffDeviceBatch(type){
            	 var arr = [];
             	 var userIds = "";
             	 var sns = "";
             	 var ids = "";
             	   $("input[name='checkList']").each(function(i,item){
     	        	   if(item.checked == true){
     	        		   arr.push(item.checked);
     	        		   userIds += $(this).parent().next().val() + ",";
     	        		   ids += $(this).parent().next().next().val() + ",";
     	        		   sns += $(this).parent().next().next().next().val() + ",";
     	        	   }
             	   });
             	   var userId = userIds.substring(0,userIds.length-1);
             	   var id = ids.substring(0,ids.length-1);
             	   var sn = sns.substring(0,sns.length-1);
             	   if(arr.length > 0){
             		  $("#logOffDevice").modal();
                 	   $("#userIdLogOff").val(userId);
                 	   $("#typeLogOff").val(type);
                 	   $("#idLogOff").val(id);
                 	   $("#udidLogOff").val(sn);
             	   }else{
                 	   $("#showMsg").modal();
             	   }
             }
           //彻底删除设备
           function deleteDeviceBatch(type){
        	     var arr = [];
	           	 var userIds = "";
	           	 var sns = "";
	           	 var ids = "";
	           	 var deviceNames = "";
	           	 var imeinos = "";
	           	$("input[name='checkList']").each(function(i,item){
  	        	   if(item.checked == true){
  	        		   arr.push(item.checked);
  	        		  if($(this).parent().next().next().next().next().val() == 1){
  	        		   userIds += $(this).parent().next().val() + ",";
  	        		   ids += $(this).parent().next().next().val() + ",";
  	        		   sns += $(this).parent().next().next().next().val() + ",";
  	        		   deviceNames += $(this).parent().next().eq(8).val() + ",";
  	        		   imeinos += $(this).parent().next().eq(9) + ",";
  	        		  }
  	        	   }
          	   }); 
	           var userId = userIds.substring(0,userIds.length-1);
          	   var id = ids.substring(0,ids.length-1);
          	   var sn = sns.substring(0,sns.length-1);
          	   var deviceName = deviceNames.substring(0,deviceNames.length-1);
          	   var imeino = imeinos.substring(0,imeinos.length-1);
          	   if(arr.length > 0){
          		 $("#delteDevice").modal();
             	 $("#userIdDelete").val(userId);
             	 $("#typeDelete").val(type);
             	 $("#idDelete").val(id);
             	 $("#udidDelete").val(sn);
             	 $("#deviceName").val(deviceName);
            	 $("#imeino").val(imeino);
          	   }else{
              	   $("#showMsg").modal();
          	   }
           }
           
           function formatCurrentTimeMillis (dateTime) {
       		var date = new Date(dateTime);
       		var y = date.getFullYear();
       		var m = (date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
                   	+ (date.getMonth() + 1);
       		var d = date.getDate() < 10 ? "0" + date.getDate() : date
                       .getDate();
       		var h = date.getHours() < 10 ? "0" + date.getHours() : date
                       .getHours();
       		var mi = date.getMinutes() < 10 ? "0" + date.getMinutes() : date
                       .getMinutes();
       		
       		var s = date.getSeconds() < 10 ? "0" + date.getSeconds() : date
                       .getSeconds();

       	    var datetime = y + "-" + m + "-" + d + " " + h + ":" + mi + ":" + s;
       	    return datetime;
       	}
		function devicePlace(id,userId,device_status){
			$("#deviceInfo").modal('show');
			$("#handsetUuidUL").html('');
			 var csrf = "${_csrf.token}";
			$.post("deviceManager/queryAllDevice",{userId:userId,_csrf:csrf},function(data){
				for(var i=0;i<data.list.length;i++){
					$("#handsetUuidUL").append('<li class="select-item">'+
							  '<a href="javascript:void(0);" style="width:100%;" onclick="reLoadUserDevice('+data.list[i].id+','+data.list[i].device_name+')">'+data.list[i].device_name+'</a>'+
							  '</li>');
					if(id == data.list[i].id ){
						$(".Js_curVal1").find("input").val(data.list[i].device_name);
						$("#handsetUuid").val(data.list[i].id);
					}
				}
			},'json');
			  $("#userIdDeviceInfo").val(userId);
			  $("#deviceStatus").val(device_status);
		$('#deviceInfo').on('shown.bs.modal', function () {
			locationInfo();
		})
		}
		function deviceInfo(userId,did){
			$("#deviceInfo").modal();
			$("#handsetUuidUL").html('');
			  var csrf = "${_csrf.token}";
			  $.post("deviceManager/deviceBasicInfo",{userId:userId,_csrf:csrf},function(data){
				  if(data.list != null){
					  for(var i=0;i<data.list.length;i++){
						  $("#handsetUuidUL").append('<li class="select-item">'+
						  '<a href="javascript:void(0);" style="width:100%;" onclick="reLoadUserDevice('+data.list[i].id+','+"'"+data.list[i].device_name+"'"+')">'+data.list[i].device_name+'</a>'+
						  '</li>');
					  }
					  $(".Js_curVal1").find("input").val(data.list[0].device_name);
					  $("#handsetUuid").val(data.list[0].id);
					  //$("#handsetUuid").attr("normal",data.list[0].device_name);
				  }
			  },'json');
			  $("#userIdDeviceInfo").val(userId);
			  baseInfo(did);
		}
		//应用
		function baseInfo(userId){
			$("#Js-optAsideList").each(function(i,item){
				$(this).find("li").find("a").removeClass("current clicked");
			});
			$("#Js-optAsideList").find("li").eq(0).find("a").addClass("current clicked");
			$("#deviceInfoContent").html('<form action="">'+
					'<table class="edit-table mng-edit-table" style="table-layout:fixed">'+
					'<tbody><tr>'+
						'<td class="span-40per">'+
							'<div class="detail-tb-hd">'+
								'<span class="tb-hd"><fmt:message key="tiles.institution.policy.base.info" /></span>'+
							'</div>'+
							'<div class="detail-table-box">'+
								'<table class="edit-table">'+
									'<tbody><tr>'+
										'<td class="td-label"><fmt:message key="tiles.institution.device.manager.device.status" /></td>'+
										'<td><span id="handsetStatusSpan" class="pop-gray"></span></td>'+
									'</tr>'+
									'<tr>'+
										'<td class="td-label"><fmt:message key="tiles.views.user.index.table.deviceInfo.illege" /></td>'+
										'<td><span id="regularSpan" class="pop-gray"></span></td>'+
									'</tr>'+
									'<tr>'+
										'<td class="td-label"><fmt:message key="tiles.views.user.index.table.deviceInfo.platform" /></td>'+
										'<td><span id="handsetPlatformSpan" class="pop-gray"></span></td>'+
									'</tr>'+
									'<tr>'+
										'<td class="td-label"><fmt:message key="tiles.views.user.index.table.deviceInfo.os" /></td>'+
										'<td><span id="osTypeSpan" class="pop-gray"></span>	</td>'+
									'</tr>'+
									'<tr>'+
										'<td class="td-label"><fmt:message key="tiles.views.user.index.table.deviceInfo.belong" /></td>'+
										'<td><span id="handsetOwnerSpan" class="pop-gray"></span></td>'+
									'</tr>'+
									'<tr>'+
										'<td class="td-label"><fmt:message key="tiles.views.user.index.table.phone" /></td>'+
										'<td><span id="msisdnSpan" class="pop-gray"></span></td>'+
									'</tr>'+
									'<tr>'+
										'<td class="td-label"><fmt:message key="tiles.views.user.index.table.deviceInfo.version" /></td>'+
										'<td><span id="clientVersionSpan" class="pop-gray"></span></td>'+
									'</tr>'+
									'<tr>'+
										'<td class="td-label"><fmt:message key="tiles.views.user.index.table.deviceInfo.ip" /></td>'+
										'<td><span id="lastIpAddressSpan" class="pop-gray"></span></td>'+
									'</tr>'+
									'<tr>'+
										'<td class="td-label"><fmt:message key="tiles.views.user.index.table.deviceInfo.power" /></td>'+
										'<td><span class="pop-gray" id="batteryLevelSpan"></span></td>'+
									'</tr>'+
								'</tbody></table>'+
							'</div>'+
						'</td>'+
						'<td class="span-60per">'+
							'<div class="detail-tb-hd">'+
								'<span class="tb-hd"><fmt:message key="tiles.views.user.index.table.deviceInfo.detail" /></span>'+
							'</div>'+
							'<div class="detail-table-box">'+
								'<table class="edit-table">'+
									'<tbody><tr>'+
										'<td class="td-label"><fmt:message key="tiles.views.institution.device.rule.history.table.deviceno" /></td>'+
										'<td><span id="handsetModelSpan" class="pop-gray"></span></td>'+
									'</tr>'+
									'<tr>'+
										'<td class="td-label"><fmt:message key="tiles.views.user.index.table.deviceInfo.px" /></td>'+	
										'<td><span id="terminalResolutionSpan" class="pop-gray"></span></td>'+
									'</tr>'+
									'<tr>'+
										'<td class="td-label"><fmt:message key="tiles.views.user.index.table.deviceInfo.xlh" /></td>'+
										'<td><span id="sequenceNoSpan" class="pop-gray"></span></td>'+
									'</tr>'+
									'<tr>'+
										'<td class="td-label">UDID</td>'+
										'<td><span id="udidSpan" class="pop-gray"></span></td>'+	
									'</tr>'+
									'<tr>'+
										'<td class="td-label">ESN/IMEI</td>'+
										'<td><span id="esnSpan" class="pop-gray"></span></td>'+
									'</tr>'+
									'<tr>'+
										
										'<td class="td-label"><fmt:message key="tiles.views.user.index.table.deviceInfo.cap" /></td>'+
										'<td><span class="pop-gray" id="availableCapacitySpan"></span></td>'+
									'</tr>'+
									'<tr id="sdTR1">'+
										'<td class="td-label"><fmt:message key="tiles.views.customer.index.index.detailinfo.sdcard" /></td>'+
										'<td><span id="availableSDSpan" class="pop-gray"></span></td>'+
									'</tr>'+
									'<tr>'+
										'<td class="td-label"><fmt:message key="tiles.views.customer.index.index.detailinfo.lastcollectiontime" /></td>'+
										'<td><span id="collectTimeSpan" class="pop-gray"></span></td>'+
									'</tr>'+
									'<tr>'+
										'<td class="td-label"><fmt:message key="tiles.views.customer.index.index.detailinfo.lastlogintime" /></td>'+
										'<td><span id="lastUsingTimeSpan" class="pop-gray"></span></td>'+
									'</tr>'+
								'</tbody></table>'+
								
							'</div>'+
						'</td>'+
					'</tr>'+
				'</tbody></table>'+
			'</form>');
			if(userId == null ){
			 userId = $("#handsetUuid").val();
			}
			var csrf = "${_csrf.token}";
			 $.post("deviceManager/selectDevice",{userId:userId,_csrf:csrf},function(data){
				  if(data.basicInfo != null){
					  if(data.basicInfo.device_status == "1"){
						  $("#handsetStatusSpan").html('<fmt:message key="tiles.views.customer.index.index.devicestatus.logoff" />');
					  }else if(data.basicInfo.device_status == "2"){
						  $("#handsetStatusSpan").html('<fmt:message key="tiles.views.customer.index.index.devicestatus.falloff" />');
					  }else if(data.basicInfo.device_status == "3"){
						  $("#handsetStatusSpan").html('<fmt:message key="tiles.views.customer.index.index.devicestatus.waitmonitor" />');
					  }else if(data.basicInfo.device_status == "4"){
						  $("#handsetStatusSpan").html('<fmt:message key="tiles.views.customer.index.index.devicestatus.monitoring" />');
					  }
					  if(data.basicInfo.isActive != 0){
						  $("#regularSpan").html('<fmt:message key="tiles.views.institution.device.rule.table.illegal.disable"/>');
					  }else{
						  $("#regularSpan").html('<fmt:message key="tiles.views.institution.device.rule.table.illegal.enable"/>');
					  }
					  $("#deviceStatus").val(data.basicInfo.device_status);
					  $("#handsetPlatformSpan").html(data.basicInfo.device_type);
					  $("#osTypeSpan").html(data.basicInfo.os_version);
					  $("#handsetOwnerSpan").html('<fmt:message key="tiles.views.institution.device.rule.table。belong.person" />');
					  $("#msisdnSpan").html(data.basicInfo.phone_number);
					  $("#clientVersionSpan").html(data.basicInfo.app_versoin);
					  $("#lastIpAddressSpan").html(data.basicInfo.ip);
					  $("#batteryLevelSpan").html(data.basicInfo.power);
					  $("#handsetModelSpan").html(data.basicInfo.device_name);
					  $("#terminalResolutionSpan").html(data.basicInfo.resolution);
					  $("#sequenceNoSpan").html(data.basicInfo.sn);
					  $("#udidSpan").html(data.basicInfo.udid);
					  $("#esnSpan").html(data.basicInfo.esnorimei);
					  if(data.basicInfo.capacity != null){
						  var capacitySpan = data.basicInfo.capacity;
						  var arr = capacitySpan.split(",");
						  var pre = parseFloat(arr[0])/parseFloat(arr[1]) * 100;
					  $("#availableCapacitySpan").html('<div class="data-box"><div class="data-inner" style="width:'+pre+'%;background:#77B94E"></div></div>'+
				                 '<div class="data-text" style="display:inline-block;">&nbsp;&nbsp;<fmt:message key="tiles.institution.device.manager.has.use" />'+arr[0]+'G|&nbsp;<fmt:message key="tiles.institution.device.manager.total" />'+arr[1]+'G</div>');
					  }else{
						  $("#availableCapacitySpan").html('<fmt:message key="tiles.institution.device.manager.not.know" />');
					  }
					  $("#collectTimeSpan").html(data.basicInfo.last_collection_time_str);
                     $("#lastUsingTimeSpan").html(data.basicInfo.last_login_time_str);					  
				  }
			  },'json');
		}
		//应用 
		function appInfo(){
			$("#Js-optAsideList").each(function(i,item){
				$(this).find("li").find("a").removeClass("current clicked");
			});
			$("#Js-optAsideList").find("li").eq(1).find("a").addClass("current clicked");
			var userId = $("#handsetUuid").val();
			$("#Js-optAsideList").find("li").eq(1).addClass("current clicked");
			$("#deviceInfoContent").html('<div class="table-responsive">'+
					'<table id="device-apps-Table" class="table table-striped b-t b-light" style="width:100%;">'+
					'<thead>'+
						'<tr>'+
							'<th class="th-border"><fmt:message key="tiles.views.user.index.table.app.name"/></th>'+
							'<th class="th-border"><fmt:message key="tiles.views.user.index.table.app.id"/></th>'+
							'<th class="th-border"><fmt:message key="tiles.views.user.index.table.app.version"/></th>'+
							'<th class="th-border"><fmt:message key="tiles.views.user.index.table.app.status"/></th>'+
						'</tr>'+
					'</thead>'+
					'<tbody>'+
						
					'</tbody>'+
				'</table>'+
			'</div>');
			$('#device-apps-Table').DataTable({
				"searching" : false,
				"stateSave" : true,
				"ordering" : false,
				"lengthChange": false,
				"bSort" : false,
				"pageLength" : 10,
				"pagingType" : "simple",
				"serverSide" : true,
				"bDestroy" : true,
				"oLanguage":{
					"sUrl":languageUrl
				},
				"ajax" : {
					"url" : "deviceManager/appInfo?userId="+userId,
					"type" : "get",
					"dataSrc" : "data",
				},
				"columns" : [ 
				              {data : "name"}, 
				              {data : "appid"}, 
				              {data : "app_version"}, 
				              {data : "app_status"}
				            ],"columnDefs" : [
	       					{
	       						className: "col-lg-2 datatb-max-width-user-appInfo-name",
	     						targets : [ 0 ],
	     						render : function(
	     								data, type,
	     								full, meta) {
	     							return '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+full.name+'"><span class="text-ellipsis"><td>'+full.name+'</td></span><div>';
	     						}
	     					},
	     					{
	       						className: "col-lg-2 datatb-max-width-user-appInfo-name",
	     						targets : [ 1 ],
	     						render : function(
	     								data, type,
	     								full, meta) {
	     							return '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+full.appid+'"><span class="text-ellipsis"><td>'+full.appid+'</td></span><div>';
	     						}
	     					},
	     					{
	       						className: "col-lg-2 datatb-max-width-user-appversion",
	     						targets : [ 2 ],
	     						render : function(
	     								data, type,
	     								full, meta) {
	     							return '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+full.app_version+'"><span class="text-ellipsis"><td>'+full.app_version+'</td></span><div>';
	     						}
	     					}]
				
			});
		}
		 //合规性
		  function illegalInfo(){
			  $("#Js-optAsideList").each(function(i,item){
					$(this).find("li").find("a").removeClass("current clicked");
				});
				$("#Js-optAsideList").find("li").eq(2).find("a").addClass("current clicked");
			  var userId=$("#handsetUuid").val();
			  $("#deviceInfoContent").html('<div class="table-responsive">'+
															'<table id="device-illegal-Table" class="table table-striped b-t b-light" style="width:100%;">'+
															'<thead>'+
																'<tr>'+
																	'<th class="th-border"><fmt:message key="tiles.views.user.index.table.app.status"/></th>'+
																	'<th class="th-border"><fmt:message key="tiles.views.user.index.table.rule.name"/></th>'+
																	'<th class="th-border"><fmt:message key="tiles.views.user.index.table.rule.precheck"/></th>'+
																	'<th class="th-border"><fmt:message key="tiles.views.user.index.table.rule.deal"/></th>'+
																'</tr>'+
															'</thead>'+
															'<tbody>'+
																
															'</tbody>'+
														'</table>'+
													'</div>');
				$('#device-illegal-Table').DataTable({
					"searching" : false,
					"stateSave" : true,
					"ordering" : false,
					"lengthChange": false,
					"bSort" : false,
					"pageLength" : 10,
					"pagingType" : "simple",
					"serverSide" : true,
					"bDestroy" : true,
					"oLanguage":{
						"sUrl":languageUrl
					},
					"ajax" : {
						"url" : "deviceManager/illegalInfo?userId="+userId,
						"type" : "get",
						"dataSrc" : "data",
					},
					"columns" : [ 
					              {data : "id"}, 
					              {data : "deviceRule.name"}, 
					              {data : "id"}, 
					              {data : "id"}
					            ],
					            "columnDefs" : [
					        					{
					        						targets : [ 0 ],
					        						render : function(
					        								data, type,
					        								full, meta) {
					        							if(full.status==0){
					        								return '<fmt:message key="tiles.views.customer.index.index.devicecompliant.legal"/>';
					        							}else{
					        								return '<fmt:message key="tiles.views.customer.index.index.devicecompliant.illegal"/>';
					        							}
					        						}
					        					},
					        					{
					        						targets : [ 1 ],
					        						render : function(
					        								data, type,
					        								full, meta) {
					        							if(full.deviceRule==null){
					        								return "-----";
					        							}else{
					        								return 	full.deviceRule.name;
					        							}
					        							
					        						}
					        					},
					        					{
					        						targets : [ 2 ],
					        						render : function(
					        								data, type,
					        								full, meta) {
					        							return new Date(full.violate_time).Format("yyyy-MM-dd hh:mm:ss");
					        						}
					        					},
					        					{
					        						targets : [ 3 ],
					        						render : function(
					        								data, type,
					        								full, meta) {
					        							for(var i=0;i<full.operation.length;i++){
					        								var opera=full.operation[i];
					        								var data=returnData(opera.deviceRuleOperationRecord.type,
					        										opera.deviceRuleOperationRecord.condition,
					        										opera.deviceRuleOperationRecord.value,0);
					        								return data.firstStr+"--"+data.secondStr+"--"+data.thirdStr;
					        							}
					        						}
					        					}
					        					]
				});
		  }
		//位置
		function locationInfo(){
			$("#Js-optAsideList").each(function(i,item){
				$(this).find("li").find("a").removeClass("current clicked");
			});
			deviceId = $("#handsetUuid").val();
			$("#Js-optAsideList").find("li").eq(3).find("a").addClass("current clicked");
			$("#deviceInfoContent").html('<table width="100%">'+
	    		'<tbody><tr>'+
					'<td align="left" width="100%" colspan="2" style="border:0">&nbsp;&nbsp;'+
							'<a href="javascript:doLocation();" class="button" id="locationB"><i class="fa fa-map-marker" style="color:#6888B9;"></i>&nbsp;&nbsp;<span style="color:#6888B9;"><fmt:message key="tiles.institution.device.manager.immediate.location"/></span></a>'+
					'</td>'+
	    		'</tr>'+
	    		'<tr>'+
	    		  '<td align="left" style="width:19%;border:0">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="tiles.institution.device.manager.location.result"/></td>'+
	    		  '<td align="left" style="width:87%;border:0" id="locationRequest"><fmt:message key="tiles.institution.device.manager.location.no"/></td>'+
	    		'</tr>'+
	    	'</tbody></table>'+
   		'<div id="descDiv" class="devicePlace" ></div>');
			initMap();//初始化地图
		}
		var point;
		var zoom;
		function initMap(){
			  zoom = 11;
			  point = new BMap.Point("116.405437","39.912786");  // 创建点坐标  
			  createMap();//创建地图
		      setMapEvent();//设置地图事件
		      addMapControl();//向地图添加控件
		}
		//创建地图函数
		function createMap(){
			 var map = new BMap.Map("descDiv");//在百度地图容器中创建一个地图
			 map.centerAndZoom(point,zoom);//设定地图的中心点和坐标并将地图显示在地图容器中
			 window.map = map;//将map变量存储在全局
		}
		//地图事件设置函数
		function setMapEvent(){
			  map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
	          map.enableScrollWheelZoom();//启用地图滚轮放大缩小
		      map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
		}
		   //地图控件添加函数：
		   function addMapControl(){
			   //向地图中添加缩放控件
			   var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
			   map.addControl(ctrl_nav);
			   //向地图中添加缩略图控件
			   var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:true});
			   map.addControl(ctrl_ove);
			   //向地图中添加比例尺控件
			   var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
			   map.addControl(ctrl_sca);
			   var ctrl_type = new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP,BMAP_HYBRID_MAP]});
			   map.addControl(ctrl_type);
		   }
		//网络
		function net(){
			$("#Js-optAsideList").each(function(i,item){
				$(this).find("li").find("a").removeClass("current clicked");
			});
			$("#Js-optAsideList").find("li").eq(4).find("a").addClass("current clicked");
			 $("#deviceInfoContent").html('<form id="netInfoForm" method="" action="">'+
					'<div class="detail-table-box">'+
					'<table class="edit-table">'+
						'<tbody><tr>'+
						    '<td class="td-label" style="width: 30%"><fmt:message key="tiles.views.user.index.table.net.own"/></td>'+
						    '<td style="width: 70%"><span id="mobileOperatorSpan" class="pop-label"></span></td>'+
						'</tr>'+
						'<tr>'+
						    '<td class="td-label" style="width: 30%"><fmt:message key="tiles.views.user.index.table.net.phone"/></td>'+
						    '<td style="width: 70%"><span id="msisdnSpan" class="pop-label"></span></td>'+
						'</tr>'+
						'<tr> '+
						    '<td class="td-label" style="width: 30%"><fmt:message key="tiles.views.user.index.table.net.type"/></td>'+
						    '<td style="width: 70%"><span id="cellularTechnologySpan" class="pop-label"></span></td>'+
						'</tr>'+
						'<tr>'+
							'<td class="td-label" style="width: 30%"><fmt:message key="tiles.views.user.index.table.net.sim"/>1</td>'+
							'<td style="width: 70%">'+
								'<span id="imsi1" class="pop-label"></span>'+
							'</td>'+
						'</tr>'+
						'<tr>'+
							'<td class="td-label" style="width: 30%"><fmt:message key="tiles.views.user.index.table.net.sim"/>2</td>'+
							'<td style="width: 70%">'+
								'<span id="imsi2" class="pop-label"></span>'+
							'</td>'+
						'</tr>'+
						'<tr>'+
						    '<td class="td-label" style="width: 30%"><fmt:message key="tiles.views.user.index.table.net.data"/></td>'+
						    '<td style="width: 70%"><span id="dataRoamingSpan" class="pop-label"></span></td>'+
						'</tr>'+
						'<tr>'+
						    '<td class="td-label" style="width: 30%"><fmt:message key="tiles.views.user.index.table.net.wifi"/></td>'+
						    '<td style="width: 70%"><span id="wifiMacSpan" class="pop-label"></span></td>'+
						'</tr>'+
						'<tr>'+
						    '<td class="td-label" style="width: 30%"><fmt:message key="tiles.views.user.index.table.net.blue"/></td>'+
						    '<td style="width: 70%"><span id="bluetoothMacSpan" class="pop-label"></span></td>'+
						'</tr>'+
						'<tr style="td-label">'+
						    '<td class="td-label" style="width: 30%"><fmt:message key="tiles.views.user.index.table.net.pwifi"/></td>'+
						    '<td style="width: 70%"><span id="personalHotspotSpan" class="pop-label"></span></td>'+
						'</tr>'+
						'<tr style="td-label">'+
						    '<td class="td-label" style="width: 30%"><fmt:message key="tiles.views.user.index.table.net.voice"/></td>'+
						    '<td style="width: 70%"><span id="voiceRoamingSpan" class="pop-label"></span></td>'+
					'</tr>'+
					'</tbody></table>'+
					'</div></form>');
				var csrf = "${_csrf.token}";
				var userId=$("#handsetUuid").val();
				$.post("deviceManager/netInfo",{userId:userId,_csrf:csrf},function(data){
				        $("#mobileOperatorSpan").html(data.deviceNetworkStatus.vendor);
				        $("#msisdnSpan").html(data.deviceNetworkStatus.phone);
				        $("#cellularTechnologySpan").html(data.deviceNetworkStatus.net_type_id);
				        $("#imsi1").html(data.deviceNetworkStatus.sim_number);
				        $("#dataRoamingSpan").html(data.deviceNetworkStatus.data_roam);
				        $("#wifiMacSpan").html(data.deviceNetworkStatus.wifi_mac);
				        $("#bluetoothMacSpan").html(data.deviceNetworkStatus.blue_tooth_mac);
				        $("#personalHotspotSpan").html(data.deviceNetworkStatus.hot_point);
				        $("#voiceRoamingSpan").html(data.deviceNetworkStatus.voice_roam);
				},'json');
		}
		//安全
		function savety(){
			$("#Js-optAsideList").each(function(i,item){
				$(this).find("li").find("a").removeClass("current clicked");
			});
			$("#Js-optAsideList").find("li").eq(5).find("a").addClass("current clicked");
			$("#deviceInfoContent").html('<form method="" action="">'+
				'<table class="edit-table">'+
					'<tbody><tr>'+
			   			'<td colspan="2">'+
			   				'<div class="detail-tb-hd">'+
								'<span class="tb-hd"><fmt:message key="tiles.views.user.index.table.safty.hardware"/></span>'+
							'</div>'+
						'</td>'+
			   			'<td colspan="2">'+
							'<div class="detail-tb-hd">'+
								'<span class="tb-hd">MDM</span>'+
							'</div>'+
						'</td>'+
			   		'</tr>'+
					'<tr>'+
					    '<td class="td-label" style="width: 25%" id="hardware"></td>'+
					    '<td style="width: 25%" id="rootSpan" align="center"></td>'+
					    '<td class="td-label" style="width: 25%" id="mdmStatus"></td>'+
					    '<td style="width: 25%" id="mdmStatusSpan" align="center"></td>'+
					'</tr>'+
					'<tr>'+
			   			'<td colspan="2">'+
							'<div class="detail-tb-hd">'+
								'<span class="tb-hd"><fmt:message key="tiles.views.user.index.table.safty.encode"/></span>'+
							'</div>'+
						'</td>'+
			   			'<td colspan="2">'+
							'<div class="detail-tb-hd">'+
								'<span class="tb-hd"><fmt:message key="tiles.views.user.index.table.safty.pwd"/></span>'+
							'</div>'+
						'</td>'+
			   		'</tr>'+
					'<tr>'+
					    '<td class="td-label" style="width: 25%" id="sdStatus"></td>'+
					    '<td style="width: 25%" id="sdEncryptSpan" align="center"></td>'+
					    '<td class="td-label" style="width: 25%" id="password"></td>'+
					    '<td style="width: 25%" id="passwordSpan" align="center"></td>'+
					'</tr>'+
					'<tr>'+
					    '<td class="td-label" style="width: 25%" id="dataEncryp"></td>'+
					    '<td style="width: 25%" id="dataEncryptSpan" align="center"></td>'+
					    '<td class="td-label" style="width: 25%"></td>'+
					    '<td style="width: 25%" align="center"></td>'+
					'</tr>'+
					
					'<tr>'+
			   			'<td colspan="2">'+
							'<div class="detail-tb-hd">'+
								'<span class="tb-hd"><fmt:message key="tiles.views.user.index.table.safty.net"/></span>'+
							'</div>'+
						'</td>'+
			   			'<td colspan="2">'+
							'<div class="detail-tb-hd">'+
								'<span class="tb-hd"><fmt:message key="tiles.views.user.index.table.safty.illega"/></span>'+
							'</div>'+
						'</td>'+
			   		'</tr>'+
					'<tr>'+
						'<td class="td-label" style="width: 25%" id="dataRoamingOn"></td>'+
					    '<td style="width: 25%" id="dataRoamingOnSpan" align="center"></td>'+
					    '<td class="td-label" style="width: 25%" id="isRegular"></td>'+
					    '<td style="width: 25%" id="isRegularSpan" align="center"></td>'+
					'</tr>'+
					'<tr>'+
				    '<td class="td-label" style="width: 25%" id="soundRoaming"></td>'+
				    '<td style="width: 25%" id="soundRoamingOffSpan" align="center"></td>'+
				    '<td class="td-label" style="width: 25%"></td>'+
				    '<td style="width: 25%" align="center"></td>'+
				'</tr>'+
				'</tbody></table>'+
				
				'<input type="hidden" id="basePath" name="basePath" value="">'+ 
			'</form>');
			var csrf = "${_csrf.token}";
			var userId=$("#handsetUuid").val();
			$.post("deviceManager/deviceSaftyInfo",{userId:userId,_csrf:csrf},function(data){
				 if(data.deviceSafty.root == 0){
					 $("#hardware").html('<fmt:message key="tiles.views.user.index.table.safty.hardware.root.no"/>');
					 $("#rootSpan").html('<img style="width:16px;height:16px;" src="../resources/images/security.png">');
				 }else{
					 $("#hardware").html('<fmt:message key="tiles.views.user.index.table.safty.hardware.root.yes"/>');
					 $("#rootSpan").html('<img style="width:16px;height:16px;" src="../resources/images/warnning.png">');
				 }
				 if(data.deviceSafty.mdm == 1){
					 $("#mdmStatus").html('<fmt:message key="tiles.views.user.index.table.safty.mirror.in"/>');
					 $("#mdmStatusSpan").html('<img style="width:16px;height:16px;" src="../resources/images/security.png">');
				 }else if(data.deviceSafty.mdm == 2){
					 $("#mdmStatus").html('<fmt:message key="tiles.views.user.index.table.safty.mirror.wait"/>');
					 $("#mdmStatusSpan").html('<img style="width:16px;height:16px;" src="../resources/images/warnning.png">');
				 }else if(data.deviceSafty.mdm == 3){
					 $("#mdmStatus").html('<fmt:message key="tiles.views.user.index.table.safty.mirror.logout"/>');
					 $("#mdmStatusSpan").html('<img style="width:16px;height:16px;" src="../resources/images/warnning.png">');
				 }else if(data.deviceSafty.mdm == 4){
					 $("#mdmStatus").html('<fmt:message key="tiles.views.user.index.table.safty.mirror.inpipe"/>');
					 $("#mdmStatusSpan").html('<img style="width:16px;height:16px;" src="../resources/images/warnning.png">');
				 }else if(data.deviceSafty.mdm == 5){
					 $("#mdmStatus").html('<fmt:message key="tiles.views.user.index.table.safty.mirror.lost.no"/>');
					 $("#mdmStatusSpan").html('<img style="width:16px;height:16px;" src="../resources/images/warnning.png">');
				 }else if(data.deviceSafty.mdm == 6){
					 $("#mdmStatus").html('<fmt:message key="tiles.views.user.index.table.safty.mirror.lost.yes"/>');
					 $("#mdmStatusSpan").html('<img style="width:16px;height:16px;" src="../resources/images/warnning.png">');
				 }
				 if(data.deviceSafty.pass == 0){
					 $("#password").html('<fmt:message key="tiles.views.user.index.table.safty.encode.no"/>');
					 $("#passwordSpan").html('<img style="width:16px;height:16px;" src="../resources/images/warnning.png">');
				 }else{
					 $("#password").html('<fmt:message key="tiles.views.user.index.table.safty.encode.yes"/>');
					 $("#passwordSpan").html('<img style="width:16px;height:16px;" src="../resources/images/security.png">');
				 }
				 if(data.deviceSafty.data_safe == 0){
					 $("#sdStatus").html('<fmt:message key="tiles.views.user.index.table.safty.sd.no" />');
					 $("#sdEncryptSpan").html('<img style="width:16px;height:16px;" src="../resources/images/warnning.png">');
					 $("#dataEncryp").html('<fmt:message key="tiles.views.user.index.table.safty.pwd.no"/>');
					 $("#dataEncryptSpan").html('<img style="width:16px;height:16px;" src="../resources/images/warnning.png">');
				 }else{
					 $("#sdStatus").html('<fmt:message key="tiles.views.user.index.table.safty.sd.yes" />');
					 $("#sdEncryptSpan").html('<img style="width:16px;height:16px;" src="../resources/images/security.png">');
					 $("#dataEncryp").html('<fmt:message key="tiles.views.user.index.table.safty.pwd.yes"/>');
					 $("#dataEncryptSpan").html('<img style="width:16px;height:16px;" src="../resources/images/security.png">');
				 }
				 if(data.deviceSafty.data_roam == 0){
					 $("#dataRoamingOn").html('<fmt:message key="tiles.views.user.index.table.safty.net.no"/>');
					 $("#dataRoamingOnSpan").html('<img style="width:16px;height:16px;" src="../resources/images/security.png">');
				 }else{
					 $("#dataRoamingOn").html('<fmt:message key="tiles.views.user.index.table.safty.net.yes"/>');
					 $("#dataRoamingOnSpan").html('<img style="width:16px;height:16px;" src="../resources/images/warnning.png">');
				 }
				 if(data.deviceSafty.voice_roam == 0){
					 $("#soundRoaming").html('<fmt:message key="tiles.views.user.index.table.safty.voice.no"/>');
					 $("#soundRoamingOffSpan").html('<img style="width:16px;height:16px;" src="../resources/images/security.png">');
				 }else{
					 $("#soundRoaming").html('<fmt:message key="tiles.views.user.index.table.safty.voice.yes"/>');
					 $("#soundRoamingOffSpan").html('<img style="width:16px;height:16px;" src="../resources/images/warnning.png">');
				 }
				 if(data.deviceSafty.violate == 0){
					 $("#isRegular").html('<fmt:message key="tiles.views.user.index.table.safty.illega.no"/>');
					 $("#isRegularSpan").html('<img style="width:16px;height:16px;" src="../resources/images/security.png">');
				 }else{
					 $("#isRegular").html('<fmt:message key="tiles.views.user.index.table.safty.illega.yes"/>');
					 $("#isRegularSpan").html('<img style="width:16px;height:16px;" src="../resources/images/warnning.png">');
				 }
			},'json');
		}
		//用户信息
		function userInfo(){
			$("#Js-optAsideList").each(function(i,item){
				$(this).find("li").find("a").removeClass("current clicked");
			});
			$("#Js-optAsideList").find("li").eq(6).find("a").addClass("current clicked");
			$("#deviceInfoContent").html('<form method="" action="">'+
					'<div class="detail-table-box">'+
					'<table class="edit-table">'+
						'<tbody><tr>'+
							'<td rowspan="6" style="vertical-align: middle;text-align:center;width: 30%"><img style="margin-left:18px;" width="152" height="140" src="../resources/images/user.png"></td>'+
						    '<td class="td-label"><fmt:message key="tiles.views.user.index.table.account"/></td>'+
						    '<td><span id="loginId" class="pop-label pop-gray"></span></td>'+
						'</tr>'+
						'<tr>'+
						    '<td class="td-label"><fmt:message key="tiles.views.user.index.table.realname"/></td>'+
						    '<td><span id="userName" class="pop-label pop-gray"></span></td>'+
						'</tr>'+
						'<tr>'+
						    '<td class="td-label"><fmt:message key="tiles.institution.device.manager.phone"/></td>'+
						    '<td><span id="phone" class="pop-label pop-gray"></span></td>'+
						'</tr>'+
						'<tr>'+
						    '<td class="td-label"><fmt:message key="web.institution.usercontroller.export.excel.label4"/></td>'+
						    '<td><span id="emailAddress" class="pop-label pop-gray"></span></td>'+
						'</tr>'+
						'<tr>'+
						    '<td class="td-label"><fmt:message key="tiles.views.user.index.table.belongdepart"/></td>'+
						    '<td><span id="depName" class="pop-label pop-gray"></span></td>'+
						'</tr>'+
						'<tr>'+
						    '<td class="td-label"><fmt:message key="tiles.institution.client.manager.belong.org"/></td>'+
						    '<td><span id="orgName" class="pop-label pop-gray"></span></td>'+
						'</tr>'+
					'</tbody></table>'+
					'</div>'+
				'</form>');
			var csrf = "${_csrf.token}";
			var userId=$("#handsetUuid").val();
			$.post("deviceManager/userInfo",{userId:userId,_csrf:csrf},function(data){
				$("#loginId").html(data.username);
				$("#userName").html(data.realname);
				$("#depName").html(data.department);
				$("#emailAddress").html(data.email);
				$("#orgName").html(data.orgName);
				$("#phone").html(data.phone);
			},'json');
		}
		
		 //违规历史
		 function recordsInfo(){
			 $("#Js-optAsideList").each(function(i,item){
					$(this).find("li").find("a").removeClass("current clicked");
				});
				$("#Js-optAsideList").find("li").eq(7).find("a").addClass("current clicked");
			$("#deviceInfoContent").html('<div class="table-responsive">'+
					'<table id="device-record-Table" class="table table-striped b-t b-light" style="width:100%;">'+
					'<thead>'+
						'<tr>'+
							'<th class="th-border"><fmt:message key="tiles.views.institution.device.rule.history.table.deviceno"/></th>'+
							'<th class="th-border"><fmt:message key="tiles.views.user.index.table.rule.name"/></th>'+
							'<th class="th-border"><fmt:message key="tiles.views.institution.device.rule.illeage.detail"/></th>'+
							'<th class="th-border"><fmt:message key="tiles.views.institution.device.rule.illeage.time"/></th>'+
						'</tr>'+
					'</thead>'+
					'<tbody>'+
						
					'</tbody>'+
				'</table>'+
			'</div>');
			var csrf = "${_csrf.token}";
			var did=$("#handsetUuid").val();
			$('#device-record-Table').DataTable({
				"searching" : false,
				"stateSave" : true,
				"ordering" : false,
				"lengthChange": false,
				"bSort" : false,
				"pageLength" : 10,
				"pagingType" : "simple",
				"serverSide" : true,
				"bDestroy" : true,
				"oLanguage":{
					"sUrl":languageUrl
				},
				"ajax" : {
					"url" : "deviceManager/illegalRecordInfo?did="+did,
					"type" : "get",
					"dataSrc" : "data",
				},
				"columns" : [ 
				              {data : "deviceBasicInfo.device_name"}, 
				              {data : "deviceRule.name"}, 
				              {data : "id"}, 
				              {data : "id"}
				            ],
				            "columnDefs" : [
											{
												targets : [ 0 ],
												render : function(
														data, type,
														full, meta) {
													if(full.deviceBasicInfo==null){
														return "-----";
													}
													else{
														return full.deviceBasicInfo.device_name;
													}
												}
											},
											{
												targets : [ 2 ],
												render : function(
														data, type,
														full, meta) {
													var data=returnData(full.deviceRuleItemRecord.type,
															full.deviceRuleItemRecord.condition,
															full.deviceRuleItemRecord.value,1);
													return data.firstStr+"--"+data.secondStr+"--"+data.thirdStr;
												}
											},
				        					{
				        						targets : [ 3 ],
				        						render : function(
				        								data, type,
				        								full, meta) {
				        							return new Date(full.violate_time).Format("yyyy-MM-dd hh:mm:ss");
				        						}
				        					}
				        					
				        					]
			});
		}
		//设备日志记录
			function logsInfo(){
				$("#Js-optAsideList").each(function(i,item){
					$(this).find("li").find("a").removeClass("current clicked");
				});
				$("#Js-optAsideList").find("li").eq(8).find("a").addClass("current clicked");
				$("#deviceInfoContent").html('<div class="table-responsive">'+
						'<table id="device-log-Table" class="table table-striped b-t b-light" style="width:100%;">'+
						'<thead>'+
							'<tr>'+
								'<th class="th-border"><fmt:message key="tiles.views.user.index.table.safty.event"/></th>'+
								'<th class="th-border"><fmt:message key="tiles.views.user.index.table.safty.generater"/></th>'+
								'<th class="th-border"><fmt:message key="tiles.views.user.index.table.safty.content"/></th>'+
								'<th class="th-border"><fmt:message key="tiles.views.user.index.table.safty.generate.time"/></th>'+
							'</tr>'+
						'</thead>'+
						'<tbody>'+
							
						'</tbody>'+
					'</table>'+
				   '</div>');
				var csrf = "${_csrf.token}";
				var did=$("#handsetUuid").val();
				$('#device-log-Table').DataTable({
					"searching" : false,
					"stateSave" : true,
					"ordering" : false,
					"lengthChange": false,
					"bSort" : false,
					"pageLength" : 10,
					"pagingType" : "simple",
					"serverSide" : true,
					"bDestroy" : true,
					"oLanguage":{
						"sUrl":languageUrl
					},
					"ajax" : {
						"url" : "deviceManager/deviceLogInfo?did="+did,
						"type" : "get",
						"dataSrc" : "data",
					},
					"columns" : [ 
					              {data : "id"}, 
					              {data : "usermodel.realname"}, 
					              {data : "operateContent"},
					              {data : "operateTime"}
					            ],
					            "columnDefs" : [
												{
													targets : [ 0 ],
													render : function(
															data, type,
															row, meta) {
														if(row.eventType == "0"){
															return '<fmt:message key="tiles.views.customer.device.index.eventtype.0"/>';
														}else if(row.eventType == "1"){
															return '<fmt:message key="tiles.views.customer.device.index.eventtype.1"/>';
														}else if(row.eventType == "2"){
															return '<fmt:message key="tiles.views.customer.device.index.eventtype.2"/>';
														}else if(row.eventType == "3"){
															return '<fmt:message key="tiles.views.customer.device.index.eventtype.3"/>';
														}else if(row.eventType == "4"){
															return '<fmt:message key="tiles.views.customer.device.index.eventtype.4"/>';
														}else if(row.eventType == "5"){
															return '<fmt:message key="tiles.views.customer.device.index.eventtype.5"/>';
														}else if(row.eventType == "6"){
															return '<fmt:message key="tiles.views.customer.device.index.eventtype.6"/>';
														}else if(row.eventType == "7"){
															return '<fmt:message key="tiles.views.customer.device.index.eventtype.7"/>';
														}else if(row.eventType == "8"){
															return '<fmt:message key="tiles.views.customer.device.index.eventtype.8"/>';
														}else if(row.eventType == "9"){
															return '<fmt:message key="tiles.views.customer.device.index.eventtype.9"/>';
														}else if(row.eventType == "10"){
															return '<fmt:message key="tiles.views.customer.device.index.eventtype.10"/>';
														}else if(row.eventType == "11"){
															return '<fmt:message key="tiles.views.customer.device.index.eventtype.11"/>';
														}else if(row.eventType == "12"){
															return '<fmt:message key="tiles.views.customer.device.index.eventtype.12"/>';
														}else if(row.eventType == "13"){
															return '<fmt:message key="tiles.views.customer.device.index.eventtype.13"/>';
														}else if(row.eventType == "14"){
															return '<fmt:message key="tiles.views.customer.device.index.eventtype.14"/>';
														}
													return '';
													}
												},
												{
													targets : [ 0 ],
													render : function(
															data, type,
															row, meta) {
														if(row.usermodel==null){
															return "-----";
														}else{
															return full.usermodel.realname;
														}
													}
												},
												{
						       						className: "col-lg-2 datatb-max-width-user-logsInfo",
						     						targets : [ 2 ],
						     						render : function(
						     								data, type,
						     								full, meta) {
						     							return '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+full.operateContent+'"><span class="text-ellipsis"><td>'+full.operateContent+'</td></span><div>';
						     						}
						     					}
					        					]
				});
			}
		$(".Js_dropMod1").hover(function(){
			var _this = $(this);
			if(_this.find(".select-list").find("li").length<=0) return;
			_this.find(".select-list").show().find("li").show();
		},function(){
			var _this = $(this);
			_this.find(".select-list").hide().find("li").removeClass("hover");
		});
		function reLoadUserDevice(deviceId,deviceName){
			$("#handsetUuid").val(deviceId);
			$(".Js_curVal1").find("input").val(deviceName);
			if($("#Js-optAsideList").find("li").eq(0).find("a").hasClass("current clicked")){
				baseInfo(deviceId);
			}
			if($("#Js-optAsideList").find("li").eq(1).find("a").hasClass("current clicked")){
				appInfo();
			}
			if($("#Js-optAsideList").find("li").eq(2).find("a").hasClass("current clicked")){
				illegalInfo();
			}
			if($("#Js-optAsideList").find("li").eq(3).find("a").hasClass("current clicked")){
				locationInfo();
			}
			if($("#Js-optAsideList").find("li").eq(4).find("a").hasClass("current clicked")){
				net();
			}
			if($("#Js-optAsideList").find("li").eq(5).find("a").hasClass("current clicked")){
				savety();
			}
			if($("#Js-optAsideList").find("li").eq(6).find("a").hasClass("current clicked")){
				userInfo();
			}
			if($("#Js-optAsideList").find("li").eq(7).find("a").hasClass("current clicked")){
				recordsInfo();
			}
			if($("#Js-optAsideList").find("li").eq(8).find("a").hasClass("current clicked")){
				logsInfo();
			}
		}
		//立即定位
		var times;
		var intervalTime;
		var intervalResult;
		function doLocation(){
			var device_status = $("#deviceStatus").val();
			if(device_status == "2"){
				$("#misDoLocation").modal();
				return ;
			}
			times = 300;
			var did = $("#handsetUuid").val();
			var userId = $("#userIdDeviceInfo").val();
			var csrf = "${_csrf.token}";
			$.post("deviceManager/deviceLocation",{did:did,userId:userId,_csrf:csrf},function(data){
				$("#locationRequest").html('<fmt:message key="tiles.institution.device.manager.device.at"><fmt:param value="'+data.locationTime+'" /></fmt:message>');
				intervalTime = setInterval("setTime()",1000);
				times = times - 5;
				intervalResult = setInterval("loadLocationResult(times)", 5000);
			},'json');
		}
		//发送定位请求，定位倒计时
		function setTime(){
			times = (times - 1);
			if(times < 0){
				clearInterval(intervalTime);
			}else{
				$("#locationB").find("span").html('('+times+'秒)');
				$("#locationB").attr("href","javascript:void(0)");
			}
		}
		//获取定位信息
		function loadLocationResult(times){
			var did = $("#handsetUuid").val();
			var csrf = "${_csrf.token}";
			$.post("deviceManager/getLocation",{did:did,times:times,_csrf:csrf},function(data){
				if(data.locationStatus == "locationing"){
					$("#locationRequest").html('<fmt:message key="tiles.institution.device.manager.device.at"><fmt:param value="'+data.locationRequestDate+'" /></fmt:message>');
				}else if(data.locationStatus == "success"){   
					$("#locationRequest").html('<fmt:message key="tiles.institution.device.manager.device.location"><fmt:param value="'+data.locationRequestDate+'" /><fmt:param value="'+data.deviceLocation.locDec+'" /></fmt:message>');
					$("#locationB").find("span").html('<fmt:message key="tiles.institution.device.manager.immediate.location" />');
					$("#locationB").attr("href","javascript:doLocation();");
					clearInterval(intervalTime);
	                clearInterval(intervalResult);
	                times = 300;
	                //定位成功，重新绘制地图
	                panToMap(data.deviceLocation.longitude,data.deviceLocation.latitude);
				}else if(data.locationStatus == "fail"){ 
					 $("#locationRequest").html('<fmt:message key="tiles.institution.device.manager.device.location.fail"><fmt:param value="'+data.locationRequestDate+'" /></fmt:message>');
					   $("#locationB").find("span").html('<fmt:message key="tiles.institution.device.manager.immediate.location" />');
	                   $("#locationB").attr("href","javascript:doLocation();");
	                   clearInterval(intervalTime);
	                   clearInterval(intervalResult);
	                   times = 300;
				}
			},'json');
		}
		//重绘地图的函数
	    function panToMap(longitude,latitude){   
	    	initMap();   //定位成功后新重新初始化地图
		    map.panTo(new BMap.Point(longitude,latitude));    
		    map.clearOverlays(); 
		    var new_point = new BMap.Point(longitude,latitude);
		    var marker = new BMap.Marker(new_point);  // 创建标注
		    map.addOverlay(marker);              // 将标注添加到地图中
		    map.panTo(new_point);  
	    }
	//返回需要的data类型
	function returnData(type,condition,value,isRule){
		var firstStr="";
		var secondStr="<fmt:message key='tiles.views.institution.device.rule.table.is'/>";
		var thirdStr="";
		if(isRule==0){
			switch(type){
				case 1:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.inform'/>";
					thirdStr=value;
					switch(condition){
						case 1:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.email'/>";
						}break;
						case 2:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.sms'/>";
						}break;
						case 3:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.push'/>";
						}break;
						case 4:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.email.inform'/>";
						}break;
					}
				}break;
				case 2:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.order'/>";
					switch(condition){
						case 1:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.erase.enterp'/>";
							switch(value){
								case "1":{
									thirdStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.deviceall'/>";
								}break;
								case "2":{
									thirdStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.device.person'/>";
								}break;
								case "2":{
									thirdStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.device.enterp'/>";
								}break;
							}
						}break;
						case 0:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.backto'/>";
							thirdStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.device.enterp'/>";
						}break;
					}
				}break;
				case 3:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.termial'/>";
					thirdStr="";
					switch(condition){
						case 1:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.forbidden.signin'/>";
						}break;
						case 2:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.forbidden.app'/>";
						}break;
						case 3:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.forbidden.file'/>";
						}break;
					}
				}break;
				case 4:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.changestragy'/>";
					thirdStr=value;
					switch(condition){
						case 1:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.android'/>";
						}break;
						case 0:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.ios'/>";
						}break;
					}
				}break;
				case 5:{
					firstStr="安全邮件";
					thirdStr="";
					switch(condition){
						case 1:{
							secondStr="邮件擦除";
						}break;
						case 2:{
							secondStr="禁止访问";
						}break;
					}
				}break;
				case 6:{
					firstStr="应用限制";
					secondStr="限制使用";
					thirdStr=value;
				}break;
				case 7:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.changelock'/>";
					thirdStr="";
					switch(condition){
						case 1:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.table.yes'/>";
						}break;
					}
				}break;
			}
		}else{
			switch(type){
				case 1:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.choose.blackandwhite'/>";
					thirdStr=value;
					switch(condition){
						case 1:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.black'/>";
						}break;
						case 0:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.white'/>";
						}break;
					}
				}break;
				case 2:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.choose.root'/>";
					thirdStr="";
					switch(condition){
						case 1:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.root.enable'/>";
						}break;
						case 0:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.root.disable'/>";
						}break;
					}
				}break;
				case 3:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.choose.no'/>";
					secondStr="<fmt:message key='tiles.views.institution.device.rule.table.is'/>";
					thirdStr=value;
				}break;
				case 4:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.choose.osversion'/>";
					thirdStr=value;
					switch(condition){
						case 1:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.comp.eq'/>";
						}break;
						case 2:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.comp.gt'/>";
						}break;
						case 3:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.comp.lt'/>";
						}break;
						case 4:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.comp.gteq'/>";
						}break;
						case 5:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.comp.lteq'/>";
						}break;
					}
				}break;
				case 5:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.choose.lock'/>";
					thirdStr="";
					switch(condition){
						case 1:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.config.enable'/>";
						}break;
						case 0:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.config.disable'/>";
						}break;
					}
				}break;
				case 6:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.choose.belong'/>";
					thirdStr="";
					switch(condition){
						case 1:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.device.enterp'/>";
						}break;
						case 0:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.device.person'/>";
						}break;
					}
				}break;
				case 7:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.choose.timerange'/>";
					thirdStr=value;
					switch(condition){
						case 1:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.range.in'/>";
						}break;
						case 0:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.range.out'/>";
						}break;
					}
				}break;
				case 8:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.choose.imsi'/>";
					thirdStr="";
					switch(condition){
						case 1:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.version.eq'/>";
						}break;
						case 0:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.version.neq'/>";
						}break;
					}
				}break;
				case 9:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.choose.status'/>";
					thirdStr="";
					switch(condition){
						case 1:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.in'/>";
						}break;
						case 2:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.wait'/>";
						}break;
						case 3:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.outing'/>";
						}break;
						case 4:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.inpipe'/>";
						}break;
						case 5:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.have'/>";
						}break;
						case 6:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.lost'/>";
						}break;
						case 7:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.out'/>";
						}break;
					}
				}break;
				case 10:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.choose.sim'/>";
					thirdStr="";
					switch(condition){
						case 0:{
							secondStr="0";
						}break;
						case 1:{
							secondStr="1";
						}break;
						case 2:{
							secondStr="2";
						}break;
					}
				}break;
				case 11:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.choose.sd'/>";
					secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.gteqone'/>";
					thirdStr="";
				}break;
				case 12:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.choose.gps'/>";
					secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.gps.close'/>";
					thirdStr="";
				}break;
			}
		}
		var data={firstStr:firstStr,firstVal:type,
			secondStr:secondStr,secondVal:condition,
			thirdStr:thirdStr};
		return data;
	}
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
		//查询框显隐
		$(".search-toggle a").click(function(){
					if($(this).hasClass("hide1")){
						$(this).removeClass("hide1");
						$(this).removeAttr("style");
						$(this).text("");
						$(this).text("<fmt:message key='tiles.institution.comm.expand.search.tip'/>");
						$(".search-mod").hide();
					}else{
						$(this).addClass("hide1");
						$(this).removeAttr("style");
						$(this).text("");
						$(this).text("<fmt:message key='tiles.institution.comm.expand.search.tip'/>");
						$(".search-mod").show();
					}
				});
		$(".Js_dropMod").find("a").on("click",function(e){
			if($(this).parents("ul").siblings(".Js_curVal").find("input:text").length<=0){
				$(this).parents("ul").siblings(".Js_curVal").text($(this).text()).css("color","#5A5A5A")
			}else{
				$(this).parents("ul").siblings(".Js_curVal").find("input:text").val($(this).text().replace("&lt;","<").replace("&gt;",">")).css("color","#5A5A5A")
			}
			$(this).parents("ul").siblings(".Js_hiddenVal").attr("normal",$(this).text())
			$(this).parents("ul").siblings(".Js_hiddenVal").val($(this).attr("rel"))
			$(this).parents("ul.select-list").hide()
		});
		$(".Js_dropMod").hover(function(){
			var _this = $(this);
			if(_this.find(".select-list").find("li").length<=0) return;
			_this.find(".select-list").show().find("li").show();
		},function(){
			var _this = $(this);
			_this.find(".select-list").hide().find("li").removeClass("hover");
		});
</script>
