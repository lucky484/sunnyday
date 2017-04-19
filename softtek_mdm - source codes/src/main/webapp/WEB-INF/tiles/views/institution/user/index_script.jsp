<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=vAXGBRGMlqQdmpmO1G8LlVuMMf2R0leh&s=1">
//v2.0版本的引用方式：src="http://api.map.baidu.com/api?v=2.0&ak=您的密钥"
//v1.4版本及以前版本的引用方式：src="http://api.map.baidu.com/api?v=1.4&key=您的密钥&callback=initialize"
</script> 
<spring:url
	value="/resources/js/datatables-1.10.11/js/jquery.dataTables.js"
	var="dataTableJs" />
<script src="${dataTableJs}"></script>
<spring:url
	value="/resources/js/datatables-1.10.11/js/dataTables.bootstrap.js"
	var="dataTableBootstrapJs" />
<script src="${dataTableBootstrapJs}"></script>
<spring:url value="/resources/js/jquery.tmpl.js" var="jqueryTmplJs" />
<script src="${jqueryTmplJs}"></script>

<spring:url value="/institution/user/delete" var="deleteUrl" />
<spring:url value="/institution/user/locktoggle" var="lockUrl" />
<spring:url value="/institution/user/activetoggle" var="activeUrl" />
<spring:url value="/institution/user/pages" var="pagesUrl" />
<spring:url value="/institution/user/newly" var="newlyUrl" />
<spring:url value="/institution/user/modify" var="modifyUrl" />
<spring:url value="/institution/user/activebatch" var="activebatchUrl" />
<spring:url value="/institution/user/deletebatch" var="deletebatchUrl" />
<spring:url value="/institution/user/strategy" var="strategyUrl" />
<spring:url value="/institution/user/updateStrategy" var="updateStrategyUrl" />
<spring:url value="/institution/user/promotion" var="promotionUrl" />
<spring:url value="/institution/user/device" var="deviceUrl" />
<spring:url value="/institution/user/apps" var="appsUrl" />
<spring:url value="/institution/user/device-base" var="deviceBaseUrl" />
<spring:url value="/institution/user/roles" var="rolesUrl" />
<spring:url value="/institution/user/checkname" var="ckNameUrl" />
<spring:url value="/institution/user/save" var="saveUrl" />
<spring:url value="/institution/user/update" var="updateUrl" />
<spring:url value="/institution/user/devicestrategy" var="devicestrategyUrl" />
<spring:url value="/institution/user/updateds" var="updatedsUrl" />
<spring:url value="/institution/user/netinfo" var="netInfoUrl" />
<spring:url value="/institution/user/autotree" var="autotreeUrl" />
<spring:url value="/institution/user/userinfo" var="userinfoUrl" />
<spring:url value="/institution/user/illegal" var="illegalUrl" />
<spring:url value="/institution/user/safety" var="safetyUrl" />
<spring:url value="/institution/user/record" var="recordUrl" />
<spring:url value="/institution/user/dlogs" var="dlogsUrl" />
<spring:url value="/institution/user/devicegps" var="deviceGpsUrl" />
<spring:url value="/institution/user/getdeviceposition" var="getdeviceposition" />
<spring:url value="/resources/js/datatables-1.10.11/lang/language.lang" var="dtLangUrl" />
<script type="text/javascript">
// =============================== datatables国际化
	  var languageUrl;
   	  var lang = "${dtLangUrl}";
  	  var str = lang.substring(lang.lastIndexOf("/")+1,lang.lastIndexOf("."));
  	  var str1 = lang.substring(0,lang.lastIndexOf("/"));
  	  var str2 = lang.substring(lang.lastIndexOf("."),lang.length);
  	var nlang=navigator.language;
	if(nlang.toLowerCase().indexOf("zh")>=0){
		languageUrl = str1 + "/" + str + "_zh-CN" + str2;
	}else{
		languageUrl = str1 + "/" + str + "_en-US" + str2;
	}
//=======================
	
	function ckchange(obj){
		if(!$(obj).is(":checked")){
			$("#fck").val(0);
			sessionStorage.setItem("fck.value", 0);
		}
	}
	
	
	$("#fck").on("change",function(){
		sessionStorage.setItem("fck.value", $(this).val());
		ckbx();
		
	});
	
	function ckbx(){
		if($("#myTable").find("tbody :checkbox").length==0)
			return false;
		var fckVal=sessionStorage.getItem("fck.value");
		
		if(fckVal=="0"){
			//未选择,清空所有选项
			$("#myTable").find("tbody tr").each(function(i,e){
				var id=$(e).find("td").eq(0).find(":checkbox").val();
				$(e).find("td").eq(0).html('<label class="checkbox m-l m-t-none m-b-none i-checks"> <input type="checkbox" name="post[]" value="'+id+'" onclick="ckchange(this)"><i></i></label>');
			});
			return true;
		}
		if(fckVal=="1"||fckVal=="2"){
			//选择当页
			$("#myTable").find("tbody tr").each(function(i,e){
				var id=$(e).find("td").eq(0).find(":checkbox").val();
				$(e).find("td").eq(0).html('<label class="checkbox m-l m-t-none m-b-none i-checks"> <input type="checkbox" checked name="post[]" value="'+id+'" onclick="ckchange(this)"><i></i></label>');
			});
			if(fckVal=="1"){
				sessionStorage.setItem("fck.value", 0);
			}
			return true;
		}
	}
	//提交设备策略
	function submitStrategy(){
		var uid=$("#strategyModal").find("#android_uid").val();
		var deviceStrategy="";
		var iosDeviceStrategy = "";
		$(".android-strategy").find(":radio").each(function(){
			if($(this).is(":checked")){
				deviceStrategy=$(this).val();
			}
		});
		$(".ios-strategy").find(":radio").each(function(){
			if($(this).is(":checked")){
				iosDeviceStrategy=$(this).val();
			}
		});
		var csrf="${_csrf.token}";
		$.post("${updatedsUrl}",{uid:uid,deviceStrategy:deviceStrategy,iosDeviceStrategy:iosDeviceStrategy,_csrf:csrf},function(result){
			$(".notify").notify({type:result.type,message: { html: false, text: result.message}}).show();
			$("#strategyModal").modal("hide");
		},"json");
	}
	
	//设备策略
	function showStrategy(uid){
		$("#strategyModal").find("#android_uid").val(uid);
		$("#strategyModal").find("#ios-strategy").val(uid);
		$.get("${devicestrategyUrl}",{uid:uid},function(result){
			$(".android-strategy").html('');
			for(var i=0;i<result.android.length;i++){
				if(result.android[i].id==result.current_android){
					$(".android-strategy").append('<div class="radio i-checks m-l"><label> <input type="radio" checked name="deviceStrategy" value="'+result.android[i].id+'"> <i></i>'+result.android[i].name+'</label></div>');
				}else{
					$(".android-strategy").append('<div class="radio i-checks m-l"><label> <input type="radio" name="deviceStrategy" value="'+result.android[i].id+'"> <i></i>'+result.android[i].name+'</label></div>');
				}
			}
			$(".ios-strategy").html('');
			for(var i=0;i<result.ios.length;i++){
				if(result.ios[i].id==result.current_ios){
					$(".ios-strategy").append('<div class="radio i-checks m-l"><label> <input type="radio" checked name="iosDeviceStrategy" value="'+result.ios[i].id+'"> <i></i>'+result.ios[i].name+'</label></div>');
				} else{
					$(".ios-strategy").append('<div class="radio i-checks m-l"><label> <input type="radio" name="iosDeviceStrategy" value="'+result.ios[i].id+'"> <i></i>'+result.ios[i].name+'</label></div>');
				}
			}
		},"json");
	}
	
	//新增用户modal显示
	function showModel(){
		$('#addFrm').parsley().reset();
		document.forms[0].reset();
		$("#addModal").find(".parsley-success").each(function(){
			$(this).removeClass("parsley-success");
		});
		var node=$('#tree').treeview('getSelected');
		
		if(node.length>0&&node[0].nodeId>0){
			$('#department').treeview('selectNode', [ node[0].nodeId, {silent : true} ]);
			$('.belong_structure').val(node[0].tags.id);
		}else{
			var snode=$('#department').treeview('getSelected');
			if(snode.length>0){
				$('#department').treeview('unselectNode', [ snode[0].nodeId, {silent : true} ]);
			}
			
		}
		$('#addModal').modal(open);
	}
	
	//显示个人的信息
	function newly(uid) {
		$.get("${newlyUrl}", {uid : uid}, function(result) {
			$("#infoModal").find(".modal-body").html(
					$('#userInfo').tmpl(result));
			$("#infoModal").modal(open);
		},"json");
	}

	//========================修改用户start=====================================
	//显示修改用户模态框
	function _modify(uid){
		$.get("${modifyUrl}", {uid : uid}, function(result) {
			result.vtlcols = eval(result.vtlcols);
			result.vtls = eval(result.vtls);
			$("#modifyModal").find(".modal-body").html($('#modifyInfo').tmpl(result));
			$("#modifyFrm").parsley().reset();
			//编辑内容的树结构
			$('#modify_depart').treeview({
				color : "#428bca",
				expandIcon : 'glyphicon glyphicon-chevron-right',
				collapseIcon : 'glyphicon glyphicon-chevron-down',
				nodeIcon : 'fa fa-users',
				showBorder : false,
				data : defaultData
			});
			$('#modify_depart').treeview('expandAll', {
				silent : true
			});

			//当点击树的时候，不允许选择顶级部门
			$('#modify_depart').on(
					'nodeSelected',
					function(event, data) {
						if (data.nodeId == 0) {
							$('.belong_ste').attr('value', "");
							$("#modify_depart").addClass("parsley-error");
							$("#depart-modify-error").removeClass("hidden");
							$('#modify_depart').treeview('unselectNode', [ 0, {silent : true} ]);
						} else {
							$('.belong_ste').attr('value', data.tags.id);
							$("#modify_depart").removeClass("parsley-error");
							$("#depart-modify-error").addClass("hidden");
						}
					});
			nodes = $('#modify_depart').treeview('getUnselected');
			for (var i = 0; i < nodes.length; i++) {
				if (nodes[i].tags.id == result.departmentid) {
					$('#modify_depart').treeview('selectNode',
							[ nodes[i].nodeId, {
								silent : true
							} ]);
				}
			}
			$('#modifyFrm').parsley().reset();
			$("#modifyModal").modal(open);
		},"json");
	}
	
	//修改个人信息模态框
	function modify(uid,isenable) {
		if(isenable==1){
			Modal.confirm().on(function(e){
				if(e==true){
					_modify(uid);
				}
			});
		}else{
			_modify(uid);
		}
	}

	function toggleExclued(){
		if($("#moreless").hasClass("show")){
			$("#pwd_modify_confirm").removeAttr("data-parsley-required");
			$('#password_modify_confirm').removeAttr("data-parsley-required");
			$("#pwd_modify_confirm").removeAttr("data-parsley-alphanumber");
			$('#password_modify_confirm').removeAttr("data-parsley-alphanumber");
			$('#password_modify_confirm').removeAttr("data-parsley-equalto");
		}else{
			$("#pwd_modify_confirm").attr("data-parsley-required","");
			$('#password_modify_confirm').attr("data-parsley-required","");
			$("#pwd_modify_confirm").attr("data-parsley-alphanumber","");
			$('#password_modify_confirm').attr("data-parsley-alphanumber","");
			$('#password_modify_confirm').attr("data-parsley-equalto","#pwd_modify_confirm");
		}
	}
	
	//提交修改
	function modifySubmit(obj) {
		$('#modifyFrm').parsley().reset();
		var vtlStr = "";
		$('.m_ckrd').find('input').each(function() {
			if ($(this).is(":checked")) {
				vtlStr = $(this).val() + separator + vtlStr;
			}
		});
		if(vtlStr.length>0){
			vtlStr = vtlStr.substr(0, vtlStr.length - 1)
		}
		
		$("input[name='vtlids']").attr('value', vtlStr);
		var validator = $('#modifyFrm').parsley();
		validator.validate();
        if(validator.isValid()){
        	if ($('.belong_ste').val().trim().length == 0) {
    			$("#modify_depart").addClass("parsley-error");
    			$("#depart-modify-error").removeClass("hidden");
    			return false;
    		}
        	$(obj).attr("disabled","disabled");
        	var options = {
                    url: '${updateUrl}',
                    type: 'post',
                    //dataType: 'text',
                    data: $("#modifyFrm").serialize(),
                    success: function (result) {
                    	$("#modifyModal").modal('hide');
                    	$(obj).removeAttr("disabled");
                    	result=eval('['+result+']');
            			$(".notify").notify({type:result[0].type,message: { html: false, text: result[0].message}}).show();
            			//autoLoadDTByste();
            			window.location.reload();
                    }
                };
                $.ajax(options);
        }else{
        	if ($('.belong_ste').val().trim().length == 0) {
    			$("#modify_depart").addClass("parsley-error");
    			$("#depart-modify-error").removeClass("hidden");
    		}
        	return false;
        }
	}
	//========================修改用户start=====================================

	//====================锁定/解锁用户start=============================
	function _lockUser(uid,flag){
		var csrf="${_csrf.token}";
		$.post("${lockUrl}", {id : uid,_csrf:csrf}, function(result) {
   			$(".notify").notify({type:result.type,message: { html: false, text: result.message}}).show();
   			autoLoadDTByste();
		},"json");
	}
	//锁定/解锁用户
	function lockUser(uid, flag,isenable) {
		if(isenable==1){
			Modal.confirm().on(function(e){
				if(e==true){
					_lockUser(uid,flag);
				}
			});
		}else{
			_lockUser(uid,flag);
		}
	}
	//====================锁定/解锁用户end=============================

	//=========================激活/取消激活 start========================
	function _activeUser(uid, flag){
		var csrf="${_csrf.token}";
		$.post("${activeUrl}", {id : uid,_csrf:csrf}, function(result) {
   			$(".notify").notify({type:result.type,message: { html: false, text: result.message}}).show();
   			autoLoadDTByste();
		},"json");
	}
	
	//激活/取消激活
	function activeUser(uid, flag,isenable) {
		if(isenable==1){
			Modal.confirm().on(function(e){if(e==true){_activeUser(uid, flag);}});
		}else{
			_activeUser(uid, flag)
		}
	}
	//=========================激活/取消激活 end========================

	
	//删除用户,显示模态框
	function deleteUser(uid,name,createby) {
		//非机构管理员并且创建人不是自己
		 if("${softtek_manager.user}"!='' && "${softtek_manager.id}" != createby ){
			 $("#warningModal").modal(open); 
			 $("#delUserModal").find(".del-ids").val(uid);
			 $('#warningModal').find("button").last().attr("onclick","truncateUser()");
		 }else{
			$("#delUserModal").find(".del-text").text("<fmt:message key='tiles.views.user.index.script.delete.pre'/>"+name+"<fmt:message key='tiles.views.user.index.script.delete.next'/>");
			$("#delUserModal").find(".del-ids").val(uid);
			$("#delUserModal").modal(open); 
		}
	}
	
	//删除单个用户
	function truncateUser(){
		var uid=$("#delUserModal").find(".del-ids").val();
		var node=$('#tree').treeview('getSelected');
		var treeId=0;
    	if(node.length>0){
    		treeId=node[0].tags.id;
    	}
    	var csrf="${_csrf.token}";
		$.post("${deleteUrl}", {uid : uid,treeid:treeId,_csrf:csrf}, function(result) {
			result=eval('['+result+']');
   			$(".notify").notify({type:result[0].type,message: { html: false, text: result[0].message}}).show();
   			autoLoadDTByste();
   			window.location.reload();
		});
	}

	//获取用户策略
	function strategy(uid, sid) {
		$.get("${strategyUrl}", function(result) {
			data = {sid : sid,list : result};
			$('#userStrategyModal').find('.modal-body').html(
					$('#strategyInfo').tmpl(data));
			$("input[name='strategy_uid']").val(uid);
		},"json");
	}

	//修改策略
	function strategy_modify() {
		var uid = $("input[name='strategy_uid']").val().trim();
		var pid = $('#userStrategyModal').find("select option:selected").val();
		var csrf="${_csrf.token}";
		$.post("${updateStrategyUrl}", {uid : uid,pid : pid,_csrf:csrf}, function(result) {
			result=eval('['+result+']');
			$(".notify").notify({type:result[0].type,message: { html: false, text: result[0].message}}).show();
			autoLoadDTByste();
		})
	}

	//显示提升部门管理员modal
	function promotion(uid) {
		$("#managerModal").find(".promote-uid").val(uid);
		$.get("${rolesUrl}",{uid:uid},function(result){
			datas={data:result.roles};
			if(result.roles==null||result.roles.length==0){
				$("#managerModal .modal-body").find(".ck-tmpl").html("<fmt:message key='tiles.views.user.index.script.no.rols'/>");
				$("#managerModal .modal-body").find(".btn").addClass("hidden");
			}else{
				$("#managerModal .modal-body").find(".btn").removeClass("hidden");
			}
			$("#managerModal .modal-body").find(".ck-tmpl").html($('#checkboxTmpl').tmpl(datas));
			var treeData=result.treeData;
			$('#promoteTree').treeview({
				color : "#428bca",
				expandIcon : 'glyphicon glyphicon-chevron-right',
				collapseIcon : 'glyphicon glyphicon-chevron-down',
				nodeIcon : 'fa fa-users',
				showBorder : false,
				showCheckbox : true,
				multiSelect :true,
				highlightSelected : false,
				data : treeData
			});
			$('#promoteTree').on('nodeChecked', function(event, data) {
				selectNodeLoop(data,1);
				$("#promoteTree").removeClass("parsley-error");
				$("#promote-tree-error").addClass("hidden");
			});
			
			$('#promoteTree').on('nodeUnchecked', function(event, data) {
				selectNodeLoop(data,0);
			});
		},"json");
		
		$("#managerModal").find("textarea").val('');
		$("#managerModal").modal(open);
	}
	
	//递归选择部门节点
	function selectNodeLoop(data,flag){
		var tempNodes=data.nodes;
		if(tempNodes!=undefined){
			for(var i=0;i<tempNodes.length;i++){
				if(flag==1){
					$('#promoteTree').treeview('checkNode', [ tempNodes[i].nodeId, { silent: true } ]);
				}else{
					$('#promoteTree').treeview('uncheckNode', [ tempNodes[i].nodeId, { silent: true } ]);
				}
				 selectNodeLoop(tempNodes[i],flag);
			}
		}
		return ;
	}
	
	//提交提升部门管理员信息到服务器
	function subPromote(usrid,ismanager){
		var depart="";
		if(ismanager=="0"){
			//从普通用户升为管理员
			var selectedNodes=$('#promoteTree').treeview('getChecked');
			for(var i=0;i<selectedNodes.length;i++){
				depart=selectedNodes[i].tags.id+separator+depart;
			}
			if(depart.length>0){
				depart=depart.substr(0,depart.length-1);
				$("#promoteTree").removeClass("parsley-error");
				$("#promote-tree-error").addClass("hidden");
			}else{
				$("#promoteTree").addClass("parsley-error");
				$("#promote-tree-error").removeClass("hidden");
				return false;
			}
			$("#managerModal").find(".department_ids").val(depart);
			$('#uRDFrm').find("input[type='button']").attr("disabled","disabled");
			//发送到服务器
			var options = {
                    url: '${promotionUrl}',
                    type: 'post',
                    data: $("#uRDFrm").serialize(),
                    success: function (result) {
                    	$("#managerModal").modal('hide');
                    	result=eval('('+result+')');
            			$(".notify").notify({type:result.type,message: { html: false, text: result.message}}).show();
            			autoLoadDTByste();
            			$('#uRDFrm').find("input[type='button']").removeAttr("disabled");
                    }
                };
                $.ajax(options);
		}else{
			//从管理员降为普通用户
			var csrf="${_csrf.token}";
			$.post("${promotionUrl}",{uid:usrid,_csrf:csrf},function(result){
				result=eval('('+result+')');
    			$(".notify").notify({type:result.type,message: { html: false, text: result.message}}).show();
    			autoLoadDTByste();
			});
		}
	}

	//-------------设备信息start-----------
	function deviceInfo(userId){
			$("#deviceInfo").modal();
			$("#handsetUuidUL").html('');
			  var csrf = "${_csrf.token}";
			  var deviceId;
			  $.post("deviceManager/deviceBasicInfo",{userId:userId,_csrf:csrf},function(data){
				  if(data.list != null){
					  for(var i=0;i<data.list.length;i++){
						  $("#handsetUuidUL").append('<li class="select-item">'+
						  '<a href="javascript:void(0);" style="width:100%;" onclick="reLoadUserDevice('+data.list[i].id+')">'+data.list[i].device_name+'</a>'+
						  '</li>');
					  }
					  $(".Js_curVal").find("input").val(data.list[0].device_name);
					  $("#handsetUuid").val(data.list[0].id);
					  deviceId = data.list[0].id;
					  //$("#handsetUuid").attr("normal",data.list[0].device_name);
			 		 baseInfo(deviceId);
				  }
			  },'json');
			  $("#userIdDeviceInfo").val(userId);
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
								'<span class="tb-hd">基本信息</span>'+
							'</div>'+
							'<div class="detail-table-box">'+
								'<table class="edit-table">'+
									'<tbody><tr>'+
										'<td class="td-label">设备状态</td>'+
										'<td><span id="handsetStatusSpan" class="pop-gray"></span></td>'+
									'</tr>'+
									'<tr>'+
										'<td class="td-label">合规性</td>'+
										'<td><span id="regularSpan" class="pop-gray"></span></td>'+
									'</tr>'+
									'<tr>'+
										'<td class="td-label">设备平台</td>'+
										'<td><span id="handsetPlatformSpan" class="pop-gray"></span></td>'+
									'</tr>'+
									'<tr>'+
										'<td class="td-label">操作系统</td>'+
										'<td><span id="osTypeSpan" class="pop-gray"></span>	</td>'+
									'</tr>'+
									'<tr>'+
										'<td class="td-label">设备归属</td>'+
										'<td><span id="handsetOwnerSpan" class="pop-gray"></span></td>'+
									'</tr>'+
									'<tr>'+
										'<td class="td-label">电话号码</td>'+
										'<td><span id="msisdnSpan" class="pop-gray"></span></td>'+
									'</tr>'+
									'<tr>'+
										'<td class="td-label">客户端版本</td>'+
										'<td><span id="clientVersionSpan" class="pop-gray"></span></td>'+
									'</tr>'+
									'<tr>'+
										'<td class="td-label">最后登录IP</td>'+
										'<td><span id="lastIpAddressSpan" class="pop-gray"></span></td>'+
									'</tr>'+
									'<tr>'+
										'<td class="td-label">电源状态</td>'+
										'<td><span class="pop-gray" id="batteryLevelSpan"></span></td>'+
									'</tr>'+
								'</tbody></table>'+
							'</div>'+
						'</td>'+
						'<td class="span-60per">'+
							'<div class="detail-tb-hd">'+
								'<span class="tb-hd">详细信息</span>'+
							'</div>'+
							'<div class="detail-table-box">'+
								'<table class="edit-table">'+
									'<tbody><tr>'+
										'<td class="td-label">设备型号</td>'+
										'<td><span id="handsetModelSpan" class="pop-gray"></span></td>'+
									'</tr>'+
									'<tr>'+
										'<td class="td-label">分辨率</td>'+	
										'<td><span id="terminalResolutionSpan" class="pop-gray"></span></td>'+
									'</tr>'+
									'<tr>'+
										'<td class="td-label">序列号</td>'+
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
									'<tr style="display:none">'+
										
										'<td class="td-label">设备策略</td>'+
										'<td><span id="handsetPolicyNameSpan" class="pop-gray"></span></td>'+
									'</tr>'+
									'<tr>'+
										
										'<td class="td-label">存储容量</td>'+
										'<td><span class="pop-gray" id="availableCapacitySpan"></span></td>'+
									'</tr>'+
									'<tr id="sdTR1">'+
										'<td class="td-label">SD卡状态</td>'+
										'<td><span id="availableSDSpan" class="pop-gray"></span></td>'+
									'</tr>'+
									'<tr>'+
										'<td class="td-label">最后采集时间</td>'+
										'<td><span id="collectTimeSpan" class="pop-gray"></span></td>'+
									'</tr>'+
									'<tr>'+
										'<td class="td-label">最后登录时间</td>'+
										'<td><span id="lastUsingTimeSpan" class="pop-gray"></span></td>'+
									'</tr>'+
								'</tbody></table>'+
								
							'</div>'+
						'</td>'+
					'</tr>'+
				'</tbody></table>'+
			'</form>');
			if(userId == undefined){
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
				                 '<div class="data-text" style="display:inline-block;">&nbsp;&nbsp;已用'+arr[0]+'G|&nbsp;共'+arr[1]+'G</div>');
					  }else{
						  $("#availableCapacitySpan").html('未知');
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
					        								return "合规";
					        							}else{
					        								return "违规";
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
			$("#Js-optAsideList").find("li").eq(3).find("a").addClass("current clicked");
			$("#deviceInfoContent").html('<table width="100%">'+
	    		'<tbody><tr>'+
					'<td align="left" width="100%" colspan="2" style="border:0">&nbsp;&nbsp;'+
							'<a href="javascript:doLocation();" class="button" id="locationB"><i class="fa fa-map-marker" style="color:#6888B9;"></i>&nbsp;&nbsp;<span style="color:#6888B9;">立即定位</span></a>'+
					'</td>'+
	    		'</tr>'+
	    		'<tr>'+
	    		  '<td align="left" style="width:19%;border:0">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;定位结果：</td>'+
	    		  '<td align="left" style="width:87%;border:0" id="locationRequest">无</td>'+
	    		'</tr>'+
	    	'</tbody></table>'+
   		'<div id="descDiv" class="devicePlace" ></div>');
			initMap();
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
						    '<td class="td-label" style="width: 30%">运营商</td>'+
						    '<td style="width: 70%"><span id="mobileOperatorSpan" class="pop-label"></span></td>'+
						'</tr>'+
						'<tr>'+
						    '<td class="td-label" style="width: 30%">手机号码</td>'+
						    '<td style="width: 70%"><span id="msisdnSpan" class="pop-label"></span></td>'+
						'</tr>'+
						'<tr> '+
						    '<td class="td-label" style="width: 30%">支持网络类型</td>'+
						    '<td style="width: 70%"><span id="cellularTechnologySpan" class="pop-label"></span></td>'+
						'</tr>'+
						'<tr style="display:none">'+
						    '<td class="td-label" style="width: 30%">phoneMac</td>'+
						    '<td style="width: 70%"><span id="phoneMacSpan" class="pop-label"></span></td>'+
						'</tr>'+
						'<tr style="display:none">'+
						    '<td class="td-label" style="width: 30%">ESN/IMEI</td>'+
						    '<td style="width: 70%"><span id="imeiSpan" class="pop-label"></span></td>'+
						'</tr>'+
						'<tr>'+
							'<td class="td-label" style="width: 30%">SIM卡号1</td>'+
							'<td style="width: 70%">'+
								'<span id="imsi1" class="pop-label"></span>'+
							'</td>'+
						'</tr>'+
						'<tr>'+
							'<td class="td-label" style="width: 30%">SIM卡号2</td>'+
							'<td style="width: 70%">'+
								'<span id="imsi2" class="pop-label"></span>'+
							'</td>'+
						'</tr>'+
						'<tr style="display: none;">'+
							'<td class="td-label" style="width: 30%">授权SIM卡号2</td>'+
							'<td style="width: 70%">'+
							'<span id="imsi2" class="pop-label"></span>'+
						'</tr>'+
						'<tr style="display: none;">'+
						    '<td class="td-label" style="width: 30%">语音漫游</td>'+
						    '<td style="width: 70%"><span id="soundRoamingSpan" class="pop-label"></span></td>'+
						'</tr>'+
						'<tr>'+
						    '<td class="td-label" style="width: 30%">数据漫游</td>'+
						    '<td style="width: 70%"><span id="dataRoamingSpan" class="pop-label"></span></td>'+
						'</tr>'+
						'<tr>'+
						    '<td class="td-label" style="width: 30%">WIFI地址</td>'+
						    '<td style="width: 70%"><span id="wifiMacSpan" class="pop-label"></span></td>'+
						'</tr>'+
						'<tr>'+
						    '<td class="td-label" style="width: 30%">蓝牙地址</td>'+
						    '<td style="width: 70%"><span id="bluetoothMacSpan" class="pop-label"></span></td>'+
						'</tr>'+
						'<tr style="td-label">'+
						    '<td class="td-label" style="width: 30%">个人热点</td>'+
						    '<td style="width: 70%"><span id="personalHotspotSpan" class="pop-label"></span></td>'+
						'</tr>'+
						'<tr style="td-label">'+
						    '<td class="td-label" style="width: 30%">语音漫游</td>'+
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
								'<span class="tb-hd">硬件</span>'+
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
								'<span class="tb-hd">加密</span>'+
							'</div>'+
						'</td>'+
			   			'<td colspan="2">'+
							'<div class="detail-tb-hd">'+
								'<span class="tb-hd">密码</span>'+
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
								'<span class="tb-hd">网络</span>'+
							'</div>'+
						'</td>'+
			   			'<td colspan="2">'+
							'<div class="detail-tb-hd">'+
								'<span class="tb-hd">违规</span>'+
							'</div>'+
						'</td>'+
			   		'</tr>'+
					'<tr>'+
						'<td class="td-label" style="width: 25%" id="dataRoamingOn"></td>'+
					    '<td style="width: 25%" id="dataRoamingOnSpan" align="center"></td>'+
					    '<td class="td-label" style="width: 25%" id="isRegular"></td>'+
					    '<td style="width: 25%" id="isRegularSpan" align="center"></td>'+
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
					 $("#mdmStatus").html('<fmt:message key="tiles.views.user.index.table.safty.mirror.nol"/>');
					 $("#mdmStatusSpan").html('<img style="width:16px;height:16px;" src="../resources/images/warnning.png">');
				 }else if(data.deviceSafty.mdm == 6){
					 $("#mdmStatus").html('<fmt:message key="tiles.views.user.index.table.safty.mirror.yes"/>');
					 $("#mdmStatusSpan").html('<img style="width:16px;height:16px;" src="../resources/images/warnning.png">');
				 }else{
					 $("#mdmStatus").html('<fmt:message key="tiles.views.user.index.table.safty.mirror.out"/>');
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
						    '<td class="td-label">登录帐号</td>'+
						    '<td><span id="loginId" class="pop-label pop-gray"></span></td>'+
						'</tr>'+
						'<tr>'+
						    '<td class="td-label">姓名</td>'+
						    '<td><span id="userName" class="pop-label pop-gray"></span></td>'+
						'</tr>'+
						'<tr>'+
						    '<td class="td-label">电话</td>'+
						    '<td><span id="phone" class="pop-label pop-gray"></span></td>'+
						'</tr>'+
						'<tr>'+
						    '<td class="td-label">Email地址</td>'+
						    '<td><span id="emailAddress" class="pop-label pop-gray"></span></td>'+
						'</tr>'+
						'<tr>'+
						    '<td class="td-label">所属部门</td>'+
						    '<td><span id="depName" class="pop-label pop-gray"></span></td>'+
						'</tr>'+
						'<tr>'+
						    '<td class="td-label">所属机构</td>'+
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
		$(".Js_dropMod").hover(function(){
			var _this = $(this);
			if(_this.find(".select-list").find("li").length<=0) return;
			_this.find(".select-list").show().find("li").show();
		},function(){
			var _this = $(this);
			_this.find(".select-list").hide().find("li").removeClass("hover");
		});
		function reLoadUserDevice(deviceId){
			$("#handsetUuid").val(deviceId);
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
				$("#locationRequest").html("设备于"+data.locationTime+"发起定位请求。定位中...");
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
					$("#locationRequest").html("设备于"+data.locationRequestDate+"发起定位请求。定位中···");
				}else if(data.locationStatus == "success"){
					$("#locationRequest").html("设备于"+data.locationRequestDate+"发起定位请求。位置:"+data.deviceLocation.locDec);
					$("#locationB").find("span").html("立即定位");
					$("#locationB").attr("href","javascript:doLocation();");
					clearInterval(intervalTime);
	                clearInterval(intervalResult);
	                times = 300;
	                //定位成功，重新绘制地图
	                panToMap(data.deviceLocation.longitude,data.deviceLocation.latitude);
				}else if(data.locationStatus == "fail"){
					 $("#locationRequest").html("设备于"+data.locationRequestDate+"发起定位请求。定位失败");
					   $("#locationB").find("span").html("立即定位");
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
	//-------------设备信息end-------------
	
	//加载datatable
	function autoLoadDTByste(){
		var node=$('#tree').treeview('getSelected');
		loadDT(node[0].tags.id);
	}
	
	var tmpSteId;
	//自动加载datatable
	function loadDT(steId){
		tmpSteId = steId;
		var searchusername = $("#searchusername").val();
		var searchaccountname =$("#searchaccountname").val();
		var searchactivestatus = $("#searchactivestatus").val();
		$('#myTable').DataTable({
			"dom":"<'m-r m-t-lg pull-right'f>t<'row '<'col-lg-2'l>r<'col-lg-3'i><'pull-right'p>>",
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
				"url" : "${pagesUrl}?id="+steId,
				"type" : "get",
				"dataSrc" : "data",
				"data" : {
					"searchusername" : searchusername,
					"searchaccountname" : searchaccountname,
					"searchactivestatus" : searchactivestatus,
				}
			},
			"columns" : [ {data : "id"}, 
			              {data : "realname"}, 
			              {data : "username"}, 
			              {data : "phone"}, 
			              {data : "mark"}, 
			              {data : "phone"}, 
			              {data : "phone"},
			              {data : "phone"}
			            ],
			"columnDefs" : [
					{
						targets : [ 0 ],
						render : function(
								data, type,
								full, meta) {
							$("#fck").find(":checkbox").each(function(){
								$(this).removeAttr("checked");
							});
							/* if("${softtek_manager.user}"!=""&&"${softtek_manager.id}"!=full.createBy){
								return "<fmt:message key='tiles.views.user.index.script.choose.disable'/>";
							} */
							if(sessionStorage.getItem("fck.value")=="2"){
								return '<label class="checkbox m-l m-t-none m-b-none i-checks"> <input type="checkbox" checked name="post[]" value="'+full.id+'" onclick="ckchange(this)"><i></i></label>';
							}else{
								return '<label class="checkbox m-l m-t-none m-b-none i-checks"> <input type="checkbox" name="post[]" value="'+full.id+'" onclick="ckchange(this)"><i></i></label>';
							}
						}
					},
					{
						targets : [ 1 ],
						render : function(
								data, type,
								full, meta) {
							return '<a href="javascript:void(0);" class="text-primary" style="color:#6787B8;"  onclick="newly('
									+ full.id
									+ ')">'
									+ full.realname
									+ '</a>';
						}
					},
					{
						targets : [ 4 ],
						render : function(
								data, type,
								full, meta) {
							if(full.mark=="0"){
								return '<span style="color:#6787B8;">'+full.mark+'</span>';
							}
							return '<a href="javascript:void(0)" onclick="deviceInfo('+full.id+')" class="text-primary" style="color:#6787B8;">'
									+ full.mark
									+ '</a>';
						}
					},
					{
						className: "col-lg-2 datatb-max-width-user-policy",
						targets : [ 5 ],
						render : function(
								data, type,
								full, meta) {
							var str="";
							if("${menu[9].isshow}"=="-1"){
								
								if("${softtek_manager.auth}"=="0"&&"${softtek_manager.user}"!=""){
									return (full.policy==null?'<fmt:message key="tiles.views.user.index.script.strategy.none"/>':full.policy.name);
								}
						
								str='<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+(full.policy==null?'<fmt:message key="tiles.views.user.index.script.strategy.none"/>':full.policy.name)+'"><span class="text-ellipsis"><a href="#" class="text-primary" onclick="strategy('
								+ full.id
								+ ','
								+ (full.policy==null?0:full.policy.id)
								+ ')" data-toggle="modal" style="color:#6787B8;" data-target="#userStrategyModal">'
								+ (full.policy==null?'<fmt:message key="tiles.views.user.index.script.strategy.none"/>':full.policy.name)
								+ '</a></span><div>';
							}else{
								str=(full.policy==null?'<fmt:message key="tiles.views.user.index.script.strategy.none"/>':full.policy.name);
							}
							return str;
						}
					},
					{
						className: "users_status",
						targets : [ 6 ],
						render : function(
								data, type,
								full, meta) {
							var islock = '';
							var isactive = '';
							var ismanager = '';
							if (full.is_lock == 1) {
								islock = '<i class="fa fa-lock text-warning" title="<fmt:message key='tiles.views.user.index.script.lock.enable'/>"></i>';
							} else {
								islock = '<i class="fa fa-unlock" title="<fmt:message key='tiles.views.user.index.script.lock.disable'/>"></i>';
							}
							if (full.is_active == 0) {
								isactive = '<i class="i i-arrow2 " title="<fmt:message key='tiles.views.user.index.script.active.no'/>"></i>';
							} else {
								isactive = '<i class="i i-arrow text-warning" title="<fmt:message key='tiles.views.user.index.script.active.yes'/>"></i>';
							}
							if (full.gender == 1) {
								ismanager = '<i class="glyphicon glyphicon-user text-warning" title="<fmt:message key='tiles.views.user.index.script.depart'/>"></i>'
							}
							return islock
									+ isactive
									+ ismanager;
						}
					},
					{
						targets : [ 7 ],
						render : function(
								data, type,
								full, meta) {
							if("${softtek_manager.auth}"=="0"&&"${softtek_manager.user}"!=""){
								return '<i class="fa fa-shield"></i>';
							}
							
							var islock = '';
							var ismanager = '';
							var isactive = '';
							var isdevice='';
							var isenable=0;
							if("${softtek_manager.user}"!=""&&"${softtek_manager.id}"!=full.createBy){
								isenable=1;
							} 

							if (full.is_lock == 0) {
								islock = '<li><a href="javascript:void(0);" onclick="lockUser('
										+ full.id
										+ ',0,'+isenable+')"><i class="fa fa-lock"></i>&nbsp;&nbsp;<fmt:message key="tiles.views.user.index.script.lock.no"/></a></li>';
							} else {
								islock = '<li><a href="javascript:void(0);" onclick="lockUser('
										+ full.id
										+ ',1,'+isenable+')"><i class="fa fa-unlock"></i>&nbsp;&nbsp;<fmt:message key="tiles.views.user.index.script.lock.yes"/></a></li>';
							}
							if (full.gender == 0) {
								if("${softtek_manager.user}"==""){
									ismanager = '<li><a href="javascript:void(0);" onclick="promotion('
										+ full.id+')"><i class="fa fa-graduation-cap"></i>&nbsp;&nbsp;<fmt:message key="tiles.views.user.index.script.promote"/></a></li>';
								}
								
							} else {
								if("${softtek_manager.user}"==""){
								ismanager = '<li><a href="javascript:void(0);" onclick="subPromote('
										+ full.id
										+ ',1)"><i class="fa fa-user"></i>&nbsp;&nbsp;<fmt:message key="tiles.views.user.index.script.promote.no"/></a></li>';
								}
							}
							if (full.is_active == 0) {
								isactive = '<li><a href="javascript:void(0);" onclick="activeUser('
										+ full.id
										+ ',0,'+isenable+')"><i class="i i-arrow"></i>&nbsp;&nbsp;<fmt:message key="tiles.views.user.index.script.active.enable"/></a></li>';
							} else {
								if(parseInt(full.mark)==0){
									isactive = '<li><a href="javascript:void(0);" onclick="activeUser('
										+ full.id
										+ ',1,'+isenable+')"><i class="i i-arrow2"></i>&nbsp;&nbsp;<fmt:message key="tiles.views.user.index.script.active.disable"/></a></li>';
								}
								
							}
							if("${menu[14].isshow}"=="-1"){
								isdevice='<li><a href="javascript:void(0);" data-toggle="modal" data-target="#strategyModal" onclick="showStrategy('+full.id+')"><i class="fa fa-cubes"></i>&nbsp;<fmt:message key="defes.device.policy.title"/></a></li>';
							}

							return '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">'
									+ '<i class="i  i-settings"></i>'
									+ '</a>'
									+ '<ul class="dropdown-menu" style="margin-left:-100px;margin-top:-120px;">'
									+ ' <li><a href="javascript:void(0);" onclick="modify('
									+ full.id
									+ ','+isenable+')"><i class="fa fa-pencil"></i>&nbsp;&nbsp;<fmt:message key="tiles.views.user.index.table.modifyinfo.title"/></a></li>'
									+ islock
									+ ismanager
									+ '<li style="display:none;"><a href="#"><i class="fa fa-gamepad"></i>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.application.indexmodal.count"/></a></li>'
									+ '<li style="display:none;"><a href="#"><i class="fa fa-eye-slash"></i> &nbsp;&nbsp;<fmt:message key="tiles.views.institution.devicepolicy.indexmodal.insertblacknamelist"/></a></li>'
									+ isdevice
									+ '<li style="display:none;"><a href="#"><i class="fa fa-envelope-o"></i>&nbsp;&nbsp;<fmt:message key="tiles.views.sysmanager.message.email.setting"/></a></li>'
									+ isactive
									+ '<li class="divider"></li>'
									+ '<li><a href="javascript:void(0);" onclick="deleteUser('
									+ full.id
									+ ','+"'"+full.username+"'"+','+"'"+full.createBy+"'"+')"><i class="i i-trashcan text-danger"></i><span class="text-danger">&nbsp;&nbsp;<fmt:message key="tiles.views.user.index.script.user.delete"/></span></a></li>'
									+ '</ul>';
						}
					}]
		});
	}
	
	function searchUserLists()
	{
		loadDT(tmpSteId);
		$('#myTable').dataTable().fnDraw();
	}
	
	function cleanUserLists()
	{
		$("#searchusername").val('');
		$("#searchaccountname").val('');
		$("#searchactivestatus").val('');
		$(".Js_curVal1").find("input").val('<fmt:message key="tiles.views.user.index.search.active.all"/>');
		loadDT(tmpSteId);
		$('#myTable').dataTable().fnDraw();
	}
	//导入导出操作 start
	function openExcelModal()
	{
		$("#excelModal").modal();
		$("#file").val("");
		$(".showFileName").val("");
		$(".fileerrorTip").html("");
	}
	function exportUserModel()
	{
		$("#getusermodel").submit();
	}
	//导出用户
	function exportUsers(){
	var node=$('#tree').treeview('getSelected');
	var id=node[0].tags.id;
	$("#groupid").val(id);
	$("#exportuserFrm").submit();
	}
	//导入用户
	function importUsers() {
		var filePath = $("#file").val();
		if (filePath.indexOf("xls") != -1 || filePath.indexOf("xlsx") != -1) {
			$(".fileerrorTip").html("").hide();
			var arr = filePath.split('\\');
			var fileName = arr[arr.length - 1];
			$(".showFileName").val(fileName);
			$('#rownumbers').html("");
			$('#excelModal').modal("hide");
			$('#loadingModal').modal(open);
			var csrf = "${_csrf.token}";
			var formData = new FormData();
			formData.append("files", $("#file")[0].files[0]);
			 $.ajax({
					 data : formData,
					type : "POST",
					url : ctx + "/institution/user/importusers",
					cache : false,
					headers : {
						"${_csrf.headerName}" : "${_csrf.token}",
					},
					contentType : false,
					processData : false,
					success : function(result) {
						$('#loadingModal').modal("hide");
                        if($.parseJSON(result).success!=null){
						$('#successModal').modal(open);
                            $("#successModal").find("h1").html('<fmt:message key="tiles.views.user.index.script.user.excel.import.success"/>');
                            $('#rownumbers').addClass('hidden');
                            $('#rownumbers').html($.parseJSON(result).success);
                            $(".notify").notify({type:"success",message: { html: false, text:'<fmt:message key="tiles.views.user.index.script.user.excel.import.success"/>'}}).show();
                            
                        }else{
    						$('#erroModal').modal(open);
                            $('#rownumbers2').removeClass('hidden');
                            $("#erroModal").find("h1").html('<fmt:message key="tiles.views.user.index.script.user.excel.import.failed"/>');
                            $('#rownumbers2').html($.parseJSON(result).messages);
                            $(".notify").notify({type:"warning",message: { html: false, text:'<fmt:message key="tiles.views.user.index.script.user.excel.import.failed"/>'}}).show();
                        }
					},
					error:function(data){
						$(".notify").notify({type:"warning",message: { html: false, text:'<fmt:message key="tiles.views.user.index.script.user.excel.import.failed"/>'}}).show();
					}
				});	 
		} else {
			$(".showFileName").val("");
			$(".fileerrorTip").html("<fmt:message key='tiles.views.user.index.script.user.excel.upload.tip'/>").show();
		}
	}
	
	function refresh(){
		window.location.reload();
	}
</script>

<script type="text/javascript">
	var defaultData = ${tree};
	var separator = ",";

	$(function() {
		var msg="${msg}";
		if(msg.trim().length>0){
			$(".notify").notify({type:"${type}",message: { html: false, text: '${msg}'}}).show();
		}
		
		//检查用户名称是否重复
		$(".add-user-name").parsley().addAsyncValidator(
			'existsValidate',function(xhr){
				return !(xhr.responseText.indexOf('true') >= 0); 
			},"${ckNameUrl}",
			 { "type": "GET", "dataType": "json", "contentType": "application/json; charset=utf-8", "data": {} } );
		
		//初次加载
		if(window.sessionStorage.userTreeselected!=undefined){
			loadDT(window.sessionStorage.userTreeselected);
		}else{
			loadDT("${treeId}");
		}
		
		var id;
		//生成树形结构
		$('#tree').treeview({
			color : "#428bca",
			expandIcon : 'glyphicon glyphicon-chevron-right',
			collapseIcon : 'glyphicon glyphicon-chevron-down',
			nodeIcon : 'fa fa-users',
			showBorder : false,
			data : defaultData
		});
		//默认选择第一个
		var defaultSelected=0;
		var defaultParent=-1;
		var myNode=$('#tree').treeview('getUnselected');
		for(var i=0;i<myNode.length;i++){
			if(myNode[i].tags.id==window.sessionStorage.userTreeselected){
				defaultSelected=myNode[i].nodeId;
				break;
			}
		}
		if(window.sessionStorage.userTreeselected!=undefined&&defaultSelected==0){
			window.sessionStorage.userTreeselected=myNode[0].tags.id;
		}
		$('#tree').treeview('selectNode', [ defaultSelected, {silent : true} ]);
		defaultParent=defaultSelected;
		while(defaultParent!=undefined&&defaultParent!=0){
			var node=$('#tree').treeview('getParent', [ defaultParent, {silent : true} ]);
			$('#tree').treeview('expandNode', [ node.nodeId, {silent : true} ]);
			defaultParent=node.nodeId;
		}
		sessionStorage.setItem("fck.value", 0);
		
		//批量激活用户 
		$(".btn-active-users").on("click",function() {
			var uids = "";
			if($("#myTable tbody").find(":checked").length==0){
				$(".notify").notify({type:"warning",message: { html: false, text:'<fmt:message key="tiles.views.user.index.script.active.userchoose"/>'}}).show();
				return false;
			}
			$("#myTable>tbody").find(":checked").each(function() {
				if($(this).val().length>0)
					uids = $(this).val() + separator + uids;
			});
			if(uids.length>0)
				uids = uids.substr(0, uids.length - 1);
			else
				uids=-1;//expired data
			
			var isall=sessionStorage.getItem("fck.value");
			if(isall==null){
				isall=0;
			}
			var node=$('#tree').treeview('getSelected');
			var treeId=0;
			if(node.length>0){
				treeId=node[0].tags.id;
			}
			var param={};
			param.uids=uids;
			param.treeid=treeId,
			param.isall=isall;
			param.searchusername=$("#searchusername").val().trim();
			param.searchaccountname=$("#searchaccountname").val().trim();
			param.searchactivestatus=$("#searchactivestatus").val()
			
			var csrf="${_csrf.token}";
			$.post("${activebatchUrl}", {param : param,_csrf:csrf}, function(result) {
					$(".notify").notify({type:result.type,message: { html: false, text: result.message}}).show();
					autoLoadDTByste();
			},"json");
		});
		
		//批量上传用户
		$(".btn-del-users").on("click",function(){
			if($("#myTable").find(":checked").length>0){
				$("#delModal").modal(open);
			}else{
				$(".notify").notify({type:"warning",message: { html: false, text:'<fmt:message key="tiles.views.user.index.script.delete.userchoose"/>'}}).show();
			}
			
		});

		
		//批量删除用户
		$(".btn-delete-users").on("click",function() {
			var uids = "";
			$("#myTable").find("tr").each(function() {
				if ($(this).find(":checkbox").is(":checked") && $(this).find(":checkbox").val().length > 5) {
					uids = $(this).find(":checkbox").val()+ separator + uids;
				}
			});
			uids = uids.substr(0, uids.length - 1);
			var node=$('#tree').treeview('getSelected');
			var treeId=0;
	    	if(node.length>0){
	    		treeId=node[0].tags.id;
	    	}
	    	var csrf="${_csrf.token}";
	    	var isall=sessionStorage.getItem("fck.value");
	    	if(isall==null){
	    		isall=0;
	    	}
	    	var param={};
	    	param.uids=uids;
	    	param.treeid=treeId,
	    	param.isall=isall;
	    	param.searchusername=$("#searchusername").val().trim();
	    	param.searchaccountname=$("#searchaccountname").val().trim();
	    	param.searchactivestatus=$("#searchactivestatus").val()
			$.post("${deletebatchUrl}", {_csrf:csrf,param:param}, function(result) {
	   			$(".notify").notify({type:result.type,message: { html: false, text: result.message}}).show();
	   			autoLoadDTByste();
			},"json");
		});
		
		//全部展开节点或者收缩节点
		$(".node-states").on('click', function() {
			if ($(".node-states").hasClass('active')) {
				$('#tree').treeview('collapseAll', {silent : true});
			} else {
				$('#tree').treeview('expandAll', {silent : true});
			}
		});

		//点击左侧树的时候，加载datatable
		$('#tree').on('nodeSelected',function(event, data) {
			var nodes=$("#tree").treeview('getSelected');
			for(var i=0;i<nodes.length;i++){
				if(nodes[i].nodeId!=data.nodeId){
					$('#tree').treeview('unselectNode', [ nodes[i].nodeId, {silent : true} ]);
				}
			}
			sessionStorage.setItem("fck.value", 0);
			$("#fck").val(0);
			loadDT(data.tags.id);
			window.sessionStorage.userTreeselected=data.tags.id;
		});
		
		$('#tree').on('nodeUnselected ',function(event, data) {
			$('#tree').treeview('selectNode', [ data.nodeId, {silent : true} ]);
		});

		//新增使用的树结构
		$('#department').treeview({
			color : "#428bca",
			expandIcon : 'glyphicon glyphicon-chevron-right',
			collapseIcon : 'glyphicon glyphicon-chevron-down',
			nodeIcon : 'fa fa-users',
			showBorder : false,
			data : defaultData
		});
		
		$('#department').on('nodeSelected', function(event, data) {
			if(data.nodeId==0){
				$('#department').treeview('unselectNode', [ 0, {silent : true} ]);
				$('.belong_structure').attr('value',"");
				$("#department").addClass("parsley-error");
				$("#depart-error").removeClass("hidden");
			}else{
				$('.belong_structure').attr('value', data.tags.id);
				$("#depart-error").addClass("hidden");
				$("#department").removeClass("parsley-error");
			}
		});
		
		//新增用户提交
		var validator = $('#addFrm').parsley();
		$('#addFrm').find("input[type='button']").click(function() {
			
			var vtlStr = "";
			$('.vtls').each(function() {
				if ($(this).is(":checked")) {
					vtlStr = $(this).val() + separator + vtlStr;
				}
			});
			vtlStr = vtlStr.substr(0, vtlStr.length - 1)
			$("input[name='vtls']").attr('value', vtlStr);
			
			
			validator.validate();
	        if(validator.isValid()){
	        	if ($('.belong_structure').val().trim().length == 0) {
					$("#department").addClass("parsley-error");
					$("#depart-error").removeClass("hidden");
					return false;
				}
	        	var node=$('#tree').treeview('getSelected');
	        	if(node.length>0){
	        		$('#addFrm').find("#defaultSelectNode").val(node[0].tags.id);
	        	}
	        	
	        	$('#addFrm').find("input[type='button']").attr("disabled","disabled");
	        	var options = {
	                    url: '${saveUrl}',
	                    type: 'post',
	                    data: $("#addFrm").serialize(),
	                    success: function (result) {
	                    	result=eval('('+result+')');
	                    	if(result.type!="danger"){
	                    		$("#addModal").modal('hide');
	                    	}
	            			$(".notify").notify({type:result.type,message: { html: false, text: result.message}}).show();
	            			autoLoadDTByste();
	            			$('#addFrm').find("input[type='button']").removeAttr("disabled");
	            			window.location.reload();
	                    }
	                };
	                $.ajax(options);
	        }else{
	        	if ($('.belong_structure').val().trim().length == 0) {
					$("#department").addClass("parsley-error");
					$("#depart-error").removeClass("hidden");
					return false;
				}
	        	return false;
	        }
		});
		//点击上传文件
		$(".a-upload").on("change","input[type='file']",function(){
		    var filePath=$(this).val();
		    if(filePath.indexOf("xls")!=-1 || filePath.indexOf("xlsx")!=-1){
		        $(".fileerrorTip").html("").hide();
		        var arr=filePath.split('\\');
		        var fileName=arr[arr.length-1];
		        $(".showFileName").val(fileName);
		    }else{
		        $(".showFileName").val("");
		        $(".fileerrorTip").html("<fmt:message key='tiles.views.user.index.script.user.excel.upload.tip'/>").show();
		        return false 
		    }
		});
	});
	 //查询模块的公共样式的设置和一些公共事件
    //查询框显隐
		$(".search-toggle a").click(function(){
					if($(this).hasClass("hide1")){
						$(this).removeClass("hide1");
						$(this).removeAttr("style");
						$(this).text("");
						$(this).text('<fmt:message key="tiles.institution.comm.expand.search.tip" />');
						$(".search-mod").hide();
					}else{
						$(this).addClass("hide1");
						$(this).removeAttr("style");
						$(this).text("");
						$(this).text('<fmt:message key="tiles.institution.comm.close.search.tip" />');
						$(".search-mod").show();
					}
				});
		$(".Js_dropMod").hover(function(){
			var _this = $(this);
			if(_this.find(".select-list").find("li").length<=0) return;
			_this.find(".select-list").show().find("li").show();
		},function(){
			var _this = $(this);
			_this.find(".select-list").hide().find("li").removeClass("hover");
		});
		$(".Js_dropMod").find("a").on("click",function(e){
			if($(this).parents("ul").siblings(".Js_curVal1").find("input:text").length<=0){
				$(this).parents("ul").siblings(".Js_curVal1").text($(this).text()).css("color","#5A5A5A")
			}else{
				$(this).parents("ul").siblings(".Js_curVal1").find("input:text").val($(this).text().replace("&lt;","<").replace("&gt;",">")).css("color","#5A5A5A")
			}
			$(this).parents("ul").siblings(".Js_hiddenVal").attr("normal",$(this).text())
			$(this).parents("ul").siblings(".Js_hiddenVal").val($(this).attr("rel"))
			$(this).parents("ul.select-list").hide()
		})
</script>
