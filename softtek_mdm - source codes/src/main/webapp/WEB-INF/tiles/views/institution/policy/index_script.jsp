<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<spring:url value="/resources/js/clockpicker/clockpicker.js" var="clockpickerJs" />
<script src="${clockpickerJs}"></script>
<spring:url value="/resources/js/datatables-1.10.11/lang/language.lang" var="dtLangUrl" />
<script type="text/javascript">
	var nodeIds;
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
	$(function(){
		loadDT();
	});
	//进入新增页面
	$("#insert").click(
			function() {
			    $(".parsley-errors-list").remove();
			    $(".parsley-error").removeClass("parsley-error");
				$("#insertPolicy").parsley().reset();
				$("#addStrategyModal").modal();
				$("#insertPolicy")[0].reset();
	/* 		    $(".parsley-errors-list").remove();
				$(".parsley-error").removeClass("parsley-error"); */
				var defaultData = ${tree};
				$('#insertTree').treeview({
					color : "#428bca",
					showBorder : false,
					showCheckbox : true,
					multiSelect : true,
					collapseAll : true,
					highlightSelected : false,
					data : defaultData,
				});
				var parentPolicyId;
				$('#insertTree').on('nodeChecked', function(event, data) {
			    	var nodeId = data.tags.id;
			    	parentPolicyId = data.tags.policy.id
					selectNodeLoop(data,1);
			    });
				  $('#insertTree').on('nodeUnchecked', function(event, data) {
				    	var nodeId = data.tags.id;
						selectNodeLoop(data,0);
				    });
				//递归选择部门节点
				function selectNodeLoop(data,flag){
					var tempNodes=data.nodes;
					if(tempNodes!=undefined){
						for(var i=0;i<tempNodes.length;i++){
							if(flag == 1){
								 if(parentPolicyId == tempNodes[i].tags.policy.id){
									 $('#insertTree').treeview('checkNode', [ tempNodes[i].nodeId, { silent: true } ]);
								} 
							}else{
								if(parentPolicyId == tempNodes[i].tags.policy.id){
									 $('#insertTree').treeview('uncheckNode', [ tempNodes[i].nodeId, { silent: true } ]);
								} 
							}
						}
					}
					return ;
				}
					
				$('#insertTree').treeview('collapseAll', {
					silent : true
				}); 
				if ($("#visitLimit").prop("checked") == false) {
					$("#startTime").attr("disabled", true);
					$("#endTime").attr("disabled", true);
				}
				if ($("#errorLimit").prop("checked") == false) {
					$("#isLock").attr("disabled", true);
					$("#lockTime").attr("disabled", true);
				}
				if ($("#device").prop("checked") == false) {
					$("#deviceCount").attr("disabled", true);
				}
				//异步校验策略名称
				$("#policyName").parsley().addAsyncValidator('checkname',
						function(xhr) {
							return !(xhr.responseJSON == true);
						}, 'policy/queryIsExitsName', {
							"type" : "GET",
							"dataType" : "json",
							"contentType" : "application/json; charset=utf-8",
							"data" : {}
						},'json');
			});
	$("#visitLimit").click(function(){
		if ($("#visitLimit").prop("checked") == false) {
			$("#startTime").attr("disabled", true);
			$("#endTime").attr("disabled", true);
			$("#startTime").removeAttr("data-parsley-required");
			$("#endTime").removeAttr("data-parsley-required");
			$(".parsley-errors-list").remove();
			$(".parsley-error").removeClass("parsley-error");
			$("#insertPolicy").parsley().reset();
		}else{
			$("#startTime").removeAttr("disabled");
			$("#endTime").removeAttr("disabled");
			$("#startTime").attr("data-parsley-required", true);
			$("#endTime").attr("data-parsley-required", true);
		}
	});
	$("#errorLimit").click(function() {
		if ($("#errorLimit").prop("checked") == false) {
			$("#isLock").attr("disabled", true);
			$("#lockTime").attr("disabled", true);
			$("#isLock").removeAttr("data-parsley-required");
			$("#lockTime").removeAttr("data-parsley-required");
		    $(".parsley-errors-list").remove();
			$(".parsley-error").removeClass("parsley-error"); 
			$("#insertPolicy").parsley().reset();
		} else {
			$("#isLock").removeAttr("disabled");
			$("#lockTime").removeAttr("disabled");
			$("#isLock").attr("data-parsley-required", true);
			$("#lockTime").attr("data-parsley-required", true);
		}
	});
	$("#device").click(function() {
		if ($("#device").prop("checked") == false) {
			$("#deviceCount").attr("disabled", true);
			$("#deviceCount").removeAttr("data-parsley-required");
			$(".parsley-errors-list").remove();
			$(".parsley-error").removeClass("parsley-error"); 
			$("#insertPolicy").parsley().reset();
		} else {
			$("#deviceCount").removeAttr("disabled");
			$("#deviceCount").attr("data-parsley-required", true);
		}
	});
	$(function() {
		$('#startTime').clockpicker({
		    placement: 'bottom',
		    align: 'left',
		    autoclose: true,
		    'default': 'now'
		});
		$('#endTime').clockpicker({
		    placement: 'bottom',
		    align: 'left',
		    autoclose: true,
		    'default': 'now'
		});
		$('#startTimeUpdate').clockpicker({
		    placement: 'bottom',
		    align: 'left',
		    autoclose: true,
		    'default': 'now'
		});
		$('#endTimeUpdate').clockpicker({
		    placement: 'bottom',
		    align: 'left',
		    autoclose: true,
		    'default': 'now'
		});
	});
	function searchPolicyLists()
	{
		loadDT();
	}
	
	function cleanPolicyLists()
	{
		$("#searchpolicyname").val('');
		$("#searchpolicytype").val('');
		$(".Js_curVal").find("input").val('<fmt:message key="tiles.institution.policy.all.policy" />');
		loadDT();
	}
	
	//列表展示
	function loadDT(){
		$('#policy').dataTable({
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
						"language":{
								"url":languageUrl
						},
						"ajax" : {
							"url" : "policy/queryAll",
							"dataSrc" : "data",
							"data" : {
								"searchpolicyname" : $("#searchpolicyname").val(),
								"searchpolicytype" : $("#searchpolicytype").val()
							}
						},
						columns : [ {
							data : "userName"
						}, {
							data : "department"
						}, {
							data : "isDefault"
						}, {
							data : "createTimeStr"
						}, {
							data : "count"
						} ],
						"columnDefs" : [
								{
									className: "col-lg-2 datatb-max-width-policy",
									"targets" : 0,
									"render" : function(data, type, row) {
										return '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+row.name+'"><span class="text-ellipsis"> <a href="javascript:void(0);" class="text-primary" onclick="queryDetail('
												+ row.id
												+ ')">'
												+ row.name
												+ '</a></span><div>';
									}
								},
								{
									className: "col-lg-2 datatb-max-width-policy-depart",
									"targets" : 1,
									render : function(data, type,row) {
										if(row.department != null){
											return '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+row.department+'"><span class="text-ellipsis">'+row.department+'</span><div>';
										}else{
											return '';
										}
									}

								},
								{
									"targets" : 2,
									"render" : function(data, type, row) {
										if (row.isDefault == 1) {
											return '<fmt:message key="tiles.institution.policy.yes" />';
										} else {
											return '<fmt:message key="tiles.institution.policy.no" />';
										}
									}
								},
								{
									"targets" : 5,
									"render" : function(data, type, row) {
										if("${softtek_manager.auth}"=="0"&&"${softtek_manager.user}"!=""){
											return '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">'
											+ '<i class="i  i-settings"></i>'
											+ '</a>'
											+ '<ul class="dropdown-menu" style="margin-left:-100px;margin-top:-60px;">'
											+ '<li><a href="javascript:void(0);" onclick="queryDetail('
											+ row.id
											+ ')">'
											+ '<i class="glyphicon glyphicon-eye-open"></i>&nbsp;<fmt:message key="tiles.institution.policy.query" /></a></li></ul>';
										}else{
											if (row.isDefault == 1) {
												return '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">'
														+ '<i class="i  i-settings"></i>'
														+ '</a>'
														+ '<ul class="dropdown-menu" style="margin-left:-100px;margin-top:-60px;">'
														+ '<li><a href="javascript:void(0);" onclick="queryDetail('+ row.id+ ')">'
														+ '<i class="glyphicon glyphicon-eye-open"></i>&nbsp;<fmt:message key="tiles.institution.policy.query" /></a></li>'
														+ '<li><a href="javascript:void(0);" onclick="updatePolicy('+ row.id+ ','+row.createBy+')">'
														+ '<i class="fa fa-pencil"></i>&nbsp;<fmt:message key="tiles.institution.policy.update" /></a></li>'
														+ '</ul>';
											} else {
												return '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">'
														+ '<i class="i  i-settings"></i>'
														+ '</a>'
														+ '<ul class="dropdown-menu" style="margin-left:-100px;margin-top:-80px;">'
														+ '<li><a href="javascript:void(0);" onclick="queryDetail('+ row.id+ ')">'
														+ '<i class="glyphicon glyphicon-eye-open"></i>&nbsp;<fmt:message key="tiles.institution.policy.query" /></a></li>'
														+ '<li><a href="javascript:void(0);" onclick="updatePolicy('+ row.id+ ','+row.createBy+')">'
														+ '<i class="fa fa-pencil"></i>&nbsp;<fmt:message key="tiles.institution.policy.update" /></a></li>'
														+ '<li class="deleted"><a href="javascript:void(0);" onclick="deletePolicy('+ row.id+ ','+row.createBy+')">'
														+ '<i class="i i-trashcan text-dange"></i>&nbsp;<fmt:message key="tiles.institution.policy.delete" /></a></li>'
														+ '</ul>';
											}
										}
									}
								} ]
					});
	}
	function deletePolicy(policyId,createBy) {
		if("${softtek_manager.user}" != ""){
			var id = "${softtek_manager.user.id}";
			if(createBy != parseInt(id)){
				Modal.confirm().on(function(e){
					if(e==true){
						$("#deleteMsg").modal();
						$("#delPolicyId").val(policyId);
					}
				});
			}else{
				$("#deleteMsg").modal(open);
				$("#policyId").val(policyId);
			}
		}else{
			$("#deleteMsg").modal(open);
			$("#policyId").val(policyId);
		}
	}
	$("#deletePolicy").click(function() {
		var _this = $(this);
		_this.attr("disabled",true);
		var csrf = "${_csrf.token}";
		var id = $("#policyId").val();
		$.post("policy/deletePolicy", {id : id,_csrf:csrf}, function(data) {
			window.location.reload();
		}); 
	});
	$("#delPolicyModal").click(function(){
		var csrf = "${_csrf.token}";
		var _this = $(this);
		_this.attr("disabled",true);
		var id = $("#delPolicyId").val();
		$.post("policy/deletePolicy", {id : id,_csrf:csrf}, function(data) {
			window.location.reload();
		}); 
	});
	//新增策略提交
	$("#addPolicy").click(function(){
				var validator = $("#insertPolicy").parsley();
				var isDefault = "0";
				var visitLimit = "0";
				var wifi = "0";
				var companyWifi = "0";
				var loginLimit = "0";
				var autoLoginLimit = "0";
				var errorLimit = "0";
				var device = "0";
				var monitorVisitApp = "0";
				var password = "";
				if ($("#isDefault").is(':checked') == true) {
					isDefault = "1";
				}
				if ($("#visitLimit").is(':checked') == true) {
					visitLimit = "1";
				}
				if ($("input[name='wifi']").is(':checked') == true) {
					wifi = "1";
				}
				if ($("input[name='companyWifi']").is(':checked') == true) {
					companyWifi = "1";
				}
				if ($("#loginLimit").is(':checked') == true) {
					loginLimit = "1";
				}
				if ($("#autoLoginLimit").is(':checked') == true) {
					autoLoginLimit = "1";
				}
				if ($("#errorLimit").is(':checked') == true) {
					errorLimit = "1";
				}
				if ($("#device").is(':checked') == true) {
					device = "1";
				}
				if ($("#password").is(':checked') == true) {
					password = "1";
				}
				var nodes=$('#insertTree').treeview('getChecked');
				var nodeId = "";
				for (var i = 0; i < nodes.length; i++) {
					 nodeId += nodes[i].tags.id + ","
					 nodeIds = nodeId.substring(0, nodeId.length - 1);
				}
				var csrf = "${_csrf.token}"; 
				var policy = {
					nodeIds : nodeIds,
					name : $("#policyName").val(),
					isDefault : isDefault,
					visit_on_worktime : visitLimit,
					visit_time_start : $("#startTime").val() == "" ? null : $("#startTime").val(),
					visit_time_end : $("#endTime").val() == "" ? null : $("#endTime").val(),
					allow_wifi : wifi,
					only_comp_wifi : companyWifi,
					login_limit : loginLimit,
					auto_login_limit : autoLoginLimit,
					login_error_limit_times : errorLimit,
					login_error_limit : $("#isLock").val() == null?3:$("#isLock").val(),
					login_error_limit_lock : $("#lockTime").val() == null?10:$("#lockTime").val(),
					device_limit : device,
					device_limit_count : $("#deviceCount").val() == null?2:$("#deviceCount").val(),
					force_password : password
				};
				validator.validate();
				if(validator.isValid() == null){
					 var _this = $(this);
				        _this.attr("disabled",true);
					$.post("policy/save?_csrf="+csrf, policy, function(data) {
						window.location.reload();
					});
				}else if(validator.isValid()){
					 var _this = $(this);
				        _this.attr("disabled",true);
					$.post("policy/save?_csrf="+csrf, policy, function(data) {
						window.location.reload();
					});
				}
			});
	//详情页面
	function queryDetail(id) {
		$("#queryDetail").modal();
		var csrf = "${_csrf.token}";
		$.post("policy/queryInfoById",{id : id,_csrf:csrf},
						function(data) {
							if (data.list.length > 0) {
								$("#departmentInfo").find("tbody").html('');
								for (var i = 0; i < data.list.length; i++) {
									$("#departmentInfo").find("tbody").append(
											'<tr><td>' + data.listArry[i]
													+ '</td><td>'
													+ data.list[i]
													+ '</td></tr>');
								}
							} else {
								$("#departmentInfo").find("tbody").html('');
							}
							if (data != null) {
								$("#policyNameDetail").val(data.policy.name);
								if (data.policy.isDefault == 1) {
									$("#isDefaultDetail").attr("checked", true);
								}
								if (data.policy.visit_on_worktime == 1) {
									$("#visitLimitDetail").attr("checked", true);
								}
								if (data.policy.allow_wifi == 1) {
									$("#wifiDetail").attr("checked", true);
								}
								if (data.policy.only_comp_wifi == 1) {
									$("#companyWifiDetail").attr("checked",
											true);
								}
								if (data.policy.login_limit == 1) {
									$("#loginLimitDetail")
											.attr("checked", true);
								}
								if (data.policy.auto_login_limit == 1) {
									$("#autoLoginLimitDetail").attr("checked",
											true);
								}
								if (data.policy.login_error_limit_times == 1) {
									$("#errorLimitDetail").attr("checked", true);
								}
								if (data.policy.device_limit == 1) {
									$("#deviceDetail").attr("checked", true);
								
								}
								if (data.policy.force_password == 1) {
									$("#passwordDetail").attr("checked", true);
								}
								$("#startTimeDetail").val(data.policy.visit_time_start);
								$("#endTimeDetail").val(data.policy.visit_time_end);
								$("#isLockDetail").val(data.policy.login_error_limit);
								$("#lockTimeDetail").val(data.policy.login_error_limit_lock);
								$("#deviceCountDetail").val(data.policy.device_limit_count);
							}
						}, 'json');
	}
	function updatePolicy(policyId,createBy) {
		if("${softtek_manager.user}" != ""){
			var id = "${softtek_manager.user.id}";
			if(createBy != parseInt(id)){
				Modal.confirm().on(function(e){
					if(e==true){
						updatePolicy1(policyId);
					}
				});
			}else{
				updatePolicy1(policyId);
			}
		}else{
			updatePolicy1(policyId);
		}
	}
	/* $("#updatePolicyModal").click(function(){
		var id = $("#updatePolicyId").val();
		updatePolicy1(id);
	}) */
	var treeId;
	var firstTreeId = "";
	//进入策略修改页面
	function updatePolicy1(id) {
		$("#modifyPolicy").parsley().reset();
		$("#updatePolicy").modal();
		var defaultData = ${tree};
		$('#updateTree').treeview({
			color : "#428bca",
			showBorder : false,
			showCheckbox : true,
			highlightSelected : false,
			multiSelect : true,
			data : defaultData,
		});
		$('#updateTree').treeview('collapseAll', {
			silent : true
		});
		var csrf = "${_csrf.token}";
		$.post("policy/queryInfoById", {id : id,_csrf:csrf}, function(data) {
			if (data != null) {
				//data = data.policy;
				$("#id").val(data.policy.id);
				$("#policyNameUpdate").val(data.policy.name);
				$("#name_hiden").val(data.policy.name);
				//异步校验策略名称
				$("#policyNameUpdate").parsley().addAsyncValidator('checknameUpdate',
						function(xhr) {
							return !(xhr.responseJSON == true);
						}, 'policy/checkName', {
							"type" : "GET",
							"dataType" : "json",
							"contentType" : "application/json; charset=utf-8",
							"data" : {
								firstName :data.policy.name
							}
						},'json');
				if (data.policy.isDefault == 1) {
					$("#isDefaultUpdate").attr("checked", true);
					$("#isDefaultUpdate").attr("disabled", true);
				} else {
					$("#isDefaultUpdate").removeAttr("checked", true);
					$("#isDefaultUpdate").removeAttr("disabled", true);
				}
				if (data.policy.visit_on_worktime == 1) {
					$("#visitLimitUpdate").attr("checked", true);
					$("#startTimeUpdate").removeAttr("disabled");
					$("#endTimeUpdate").removeAttr("disabled");
				} else {
					$("#visitLimitUpdate").removeAttr("checked", true);
					$("#startTimeUpdate").attr("disabled",true);
					$("#endTimeUpdate").attr("disabled",true);
				}
				if (data.policy.allow_wifi == 1) {
					$("#wifiUpdate").attr("checked", true);
				} else {
					$("#wifiUpdate").attr("checked", false);
				}
				if (data.policy.only_comp_wifi == 1) {
					$("#companyWifiUpdate").attr("checked", true);
				} else {
					$("#companyWifiUpdate").attr("checked", false);
				}
				if (data.policy.login_limit == 1) {
					$("#loginLimitUpdate").attr("checked", true);
				} else {
					$("#loginLimitUpdate").attr("checked", false);
				}
				if (data.policy.auto_login_limit == 1) {
					$("#autoLoginLimitUpdate").attr("checked", true);
				} else {
					$("#autoLoginLimitUpdate").attr("checked", false);
				}
				if (data.policy.login_error_limit_times == 1) {
					$("#errorLimitUpdate").attr("checked", true);
					$("#isLockUpdate").removeAttr("disabled");
					$("#lockTimeUpdate").removeAttr("disabled");
				} else {
					$("#errorLimitUpdate").attr("checked", false);
					$("#isLockUpdate").attr("disabled",true);
					$("#lockTimeUpdate").attr("disabled",true);
				}
				if (data.policy.device_limit == 1) {
					$("#deviceUpdate").attr("checked", true);
					$("#deviceCountUpdate").removeAttr("disabled");
				} else {
					$("#deviceUpdate").attr("checked", false);
					$("#deviceCountUpdate").attr("disabled",true);
				}
				if (data.policy.force_password == 1) {
					$("#passwordUpdate").attr("checked", true);
				} else {
					$("#passwordUpdate").attr("checked", false);
				}
				$("#startTimeUpdate").val(data.policy.visit_time_start);
				$("#endTimeUpdate").val(data.policy.visit_time_end);
				$("#isLockUpdate").val(data.policy.login_error_limit);
				$("#lockTimeUpdate").val(data.policy.login_error_limit_lock);
				$("#deviceCountUpdate").val(data.policy.device_limit_count);
				var departmentId = data.policy.deparmentId;
				if (departmentId != null) {
					firstTreeId = departmentId;
				}
				nodes = $('#updateTree').treeview('getUnselected');
				var department;
				if(departmentId != null && departmentId != ""){
					department = departmentId.split(",");
				for (var j = 0; j < department.length; j++) {
					for (var i = 0; i < nodes.length; i++) {
						if (nodes[i].tags.id == department[j]) {
							$('#updateTree').treeview('checkNode',[nodes[i].nodeId, {silent : true}]);
						}
					}
				}
				}
				var parentPolicyId;
				$('#updateTree').on('nodeChecked', function(event, data) {
			    	var nodeId = data.tags.id;
			    	parentPolicyId = data.tags.policy.id;
					selectNodeLoop(data,1);
			    });
				  $('#updateTree').on('nodeUnchecked', function(event, data) {
				    	var nodeId = data.tags.id;
				    	parentPolicyId = data.tags.policy.id;
						selectNodeLoop(data,0);
				    });
				//递归选择部门节点
				function selectNodeLoop(data,flag){
					var tempNodes=data.nodes;
					if(tempNodes!=undefined){
						for(var i=0;i<tempNodes.length;i++){
							if(flag == 1){
								 if(parentPolicyId == tempNodes[i].tags.policy.id){
									 $('#updateTree').treeview('checkNode', [ tempNodes[i].nodeId, { silent: true } ]);
								} 
							}else{
								if(parentPolicyId == tempNodes[i].tags.policy.id){
									 $('#updateTree').treeview('uncheckNode', [ tempNodes[i].nodeId, { silent: true } ]);
								} 
							}
						}
					}
					return ;
				}
			}
		}, 'json');
	}
	//如果checkbox被选中，怎文本框可用，否则是disabled状态
	$("#visitLimitUpdate").click(function() {
		if ($("#visitLimitUpdate").prop("checked") == false) {
			$("#startTimeUpdate").attr("disabled", true);
			$("#endTimeUpdate").attr("disabled", true);
			$("#startTimeUpdate").removeAttr("data-parsley-required");
			$("#endTimeUpdate").removeAttr("data-parsley-required");
		    $(".parsley-errors-list").remove();
			$(".parsley-error").removeClass("parsley-error"); 
			$("#modifyPolicy").parsley().reset();
		} else {
			$("#startTimeUpdate").removeAttr("disabled");
			$("#endTimeUpdate").removeAttr("disabled");
			$("#startTimeUpdate").attr("data-parsley-required",true);
			$("#endTimeUpdate").attr("data-parsley-required",true);
		}
	});
	$("#errorLimitUpdate").click(function() {
		if ($("#errorLimitUpdate").prop("checked") == false) {
			$("#isLockUpdate").attr("disabled", true);
			$("#lockTimeUpdate").attr("disabled", true);
			$("#isLockUpdate").removeAttr("data-parsley-required");
			$("#lockTimeUpdate").removeAttr("data-parsley-required");
		    $(".parsley-errors-list").remove();
			$(".parsley-error").removeClass("parsley-error"); 
			$("#modifyPolicy").parsley().reset();
		} else {
			$("#isLockUpdate").removeAttr("disabled");
			$("#lockTimeUpdate").removeAttr("disabled");
			$("#isLockUpdate").attr("data-parsley-required",true);
			$("#lockTimeUpdate").attr("data-parsley-required",true);
		}
	});
	$("#deviceUpdate").click(function() {
		if ($("#deviceUpdate").prop("checked") == false) {
			$("#deviceCountUpdate").attr("disabled", true);
			$("#deviceCountUpdate").removeAttr("data-parsley-required");
		    $(".parsley-errors-list").remove();
			$(".parsley-error").removeClass("parsley-error"); 
			$("#modifyPolicy").parsley().reset();
		} else {
			$("#deviceCountUpdate").removeAttr("disabled");
			$("#deviceCountUpdate").attr("data-parsley-required",true);
		}
	});
	var allTreeIds = "";
	//修改策略提交
	$("#updatePolicyL").click(function(){
						var validator = $("#modifyPolicy").parsley();
						var nodes = $('#updateTree').treeview('getChecked');
						if (nodes.length > 0) {
							for (var i = 0; i < nodes.length; i++) {
								allTreeIds += nodes[i].tags.id + ",";
							}
						}
						treeId = allTreeIds.substring(0, allTreeIds.length - 1);
						var isDefault = "0";
						var visitLimit = "0";
						var wifi = "0";
						var companyWifi = "0";
						var loginLimit = "0";
						var autoLoginLimit = "0";
						var errorLimit = "0";
						var device = "0";
						var monitorVisitApp = "0";
						var password = "";
						if ($("#isDefaultUpdate").is(':checked') == true) {
							isDefault = "1";
						}
						if ($("#visitLimitUpdate").is(':checked') == true) {
							visitLimit = "1";
						}
						if ($("input[name='wifiUpdate']").is(':checked') == true) {
							wifi = "1";
						}
						if ($("input[name='companyWifiUpdate']").is(':checked') == true) {
							companyWifi = "1";
						}
						if ($("#loginLimitUpdate").is(':checked') == true) {
							loginLimit = "1";
						}
						if ($("#autoLoginLimitUpdate").is(':checked') == true) {
							autoLoginLimit = "1";
						}
						if ($("#errorLimitUpdate").is(':checked') == true) {
							errorLimit = "1";
						}
						if ($("#deviceUpdate").is(':checked') == true) {
							device = "1";
						}
						if ($("#passwordUpdate").is(':checked') == true) {
							password = "1";
						}
						var csrf = "${_csrf.token}";
						var policy = {
							id : $("#id").val(),
							name : $("#policyNameUpdate").val(),
							isDefault : isDefault,
							visit_on_worktime : visitLimit,
							visit_time_start : $("#startTimeUpdate").val() == "" ? null
									: $("#startTimeUpdate").val(),
							visit_time_end : $("#endTimeUpdate").val() == "" ? null
									: $("#endTimeUpdate").val(),
							allow_wifi : wifi,
							only_comp_wifi : companyWifi,
							login_limit : loginLimit,
							auto_login_limit : autoLoginLimit,
							login_error_limit_times : errorLimit,
							login_error_limit : $("#isLockUpdate").val(),
							login_error_limit_lock : $("#lockTimeUpdate").val(),
							device_limit : device,
							device_limit_count : $("#deviceCountUpdate").val(),
							force_password : password
						};
							validator.validate();
							if(validator.isValid() == null){
								 var _this = $(this)
					                _this.attr("disabled",true);
								$.post("policy/updatePolicyInfo?treeId=" + treeId
										+ "&departmentId=" + firstTreeId + "&_csrf=" + csrf, policy,
										function(data) {
											window.location.reload();
										});
							}else if(validator.isValid()){
								 var _this = $(this)
					                _this.attr("disabled",true);
								$.post("policy/updatePolicyInfo?treeId=" + treeId
										+ "&departmentId=" + firstTreeId + "&_csrf=" + csrf, policy,
										function(data) {
											window.location.reload();
										},'json');
							}
					});
		function compareStartTime(type){
			var result = false;
			var startTime = "";
			if(type == 0){
			   startTime = $("#startTime").parsley();
			}else{
			   startTime = $("#startTimeUpdate").parsley();
			}
			startTime.validate();
			if(startTime.isValid()){
				result = true;
				$("#parsley-id-12").addClass("time");
				$("#parsley-id-14").addClass("time");
				$("#parsley-id-50").addClass("time");
				$("#parsley-id-52").addClass("time");
				compareEndTime1(type);
				return result;
			}else{
				return result;
			}
			
		}
		function compareEndTime(type){
			var result = false;
			var endValidate = "";
			if(type == 0){
			 endValidate = $("#endTime").parsley();
			}else{
				endValidate = $("#endTimeUpdate").parsley();	
			}
			endValidate.validate();
			if(endValidate.isValid()){
			    result = true;
			    $("#parsley-id-12").addClass("time");
				$("#parsley-id-14").addClass("time");
				$("#parsley-id-50").addClass("time");
				$("#parsley-id-52").addClass("time");
   			    compareStartTime(type);
				return result;
			}else{
				return result;
			}
		}
		function compareEndTime1(type){
			var result = false;
			var endValidate = "";
			if(type == 0){
			 endValidate = $("#endTime").parsley();
			}else{
				endValidate = $("#endTimeUpdate").parsley();	
			}
			endValidate.validate();
			if(endValidate.isValid()){
			    result = true;
			    $("#parsley-id-12").addClass("time");
				$("#parsley-id-14").addClass("time");
				$("#parsley-id-50").addClass("time");
				$("#parsley-id-52").addClass("time");
				return result;
			}else{
				return result;
			}
		}
	//比较时间
    function compareDate(startTime,endTime){
		if(startTime&&endTime){
			stimeAry = startTime.split(":");
			etimeAry = endTime.split(":");
			if(parseInt(etimeAry[0]) < parseInt(stimeAry[0])){
			    return false;
			}
			if(parseInt(etimeAry[0]) == parseInt(stimeAry[0])){
			    if(parseInt(etimeAry[1]) <= parseInt(stimeAry[1])){
			        return false;
			    }
			}
		}
		return true;
	}
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
			if($(this).parents("ul").siblings(".Js_curVal").find("input:text").length<=0){
				$(this).parents("ul").siblings(".Js_curVal").text($(this).text()).css("color","#5A5A5A")
			}else{
				$(this).parents("ul").siblings(".Js_curVal").find("input:text").val($(this).text().replace("&lt;","<").replace("&gt;",">")).css("color","#5A5A5A")
			}
			$(this).parents("ul").siblings(".Js_hiddenVal").attr("normal",$(this).text())
			$(this).parents("ul").siblings(".Js_hiddenVal").val($(this).attr("rel"))
			$(this).parents("ul.select-list").hide()
		})
</script>