<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<spring:url value="/resources/js/datatables-1.10.11/js/jquery.dataTables.js" var="dataTableJs" />
<script src="${dataTableJs}"></script>
<spring:url value="/resources/js/datatables-1.10.11/js/dataTables.bootstrap.js" var="dataTableBootstrapJs" />
<script src="${dataTableBootstrapJs}"></script>
<spring:url value="/resources/js/jquery.tmpl.js" var="jqueryTmplJs" />
<script src="${jqueryTmplJs}"></script>
<spring:url value="/resources/js/clockpicker/clockpicker.js" var="clockpickerJs" />
<script src="${clockpickerJs}"></script>
<spring:url value="/resources/js/chosen/chosen.jquery.min.js" var="chosenJs" />
<script src="${chosenJs}"></script>
<spring:url value="/resources/js/datatables-1.10.11/lang/language.lang" var="dtLangUrl" />
<spring:url value="/institution/device/rules/namelist" var="nameListUrl"/>
<spring:url value="/institution/device/rules/devicepolicy" var="devicePolicyUrl"/>
<spring:url value="/institution/device/rules/saveorupdate" var="saveUrl"/>
<spring:url value="/institution/device/rules/page" var="pageUrl"/>
<spring:url value="/institution/device/rules/newly" var="newlyUrl"/>
<spring:url value="/institution/device/rules/toggle" var="toggleUrl"/>
<spring:url value="/institution/device/rules/users" var="usersUrl"/>
<spring:url value="/institution/device/rules/delete" var="deleteUrl"/>
<spring:url value="/institution/device/rules/copy" var="copyUrl"/>
<spring:url value="/institution/device/rules/testing" var="testingUrl"/>
<spring:url value="/institution/device/rules/legalpage" var="legalpageUrl"/>
<spring:url value="/institution/device/rules/legaldetailpage" var="legaldetailpageUrl"/>
<spring:url value="/institution/device/rules/dellegal" var="dellegalUrl"/>


<script type="text/javascript">
// datatables 国际化
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
// ================================================================================
	$(function(){
		autoloadDt();
		$(".chosen-select").chosen();
		$(".chosen-container-multi").addClass("chosen-w");
		
		var defaultData=${tree};
		//编辑内容的树结构
		$('#tree').treeview({
			color : "#428bca",
			expandIcon : 'glyphicon glyphicon-chevron-right',
			collapseIcon : 'glyphicon glyphicon-chevron-down',
			nodeIcon : 'fa fa-users',
			showBorder : false,
			showCheckbox : true,
			multiSelect :false,
			highlightSelected : false,
			data : defaultData
		});
		$('#tree').on('nodeChecked', function(event, data) {
			selectNodeLoop(data,1);
		});
		$('#tree').on('nodeUnchecked', function(event, data) {
			selectNodeLoop(data,0);
		});
	});
	
function searchRole()
{
	autoloadDt();
}

function cleanRole()
{
	$("#roletype").val('');
	$("#e_platform").val('');
	$("#deviceRuleName").val('');
	$(".Js_curVal").find("input").val('<fmt:message key="tiles.views.institution.device.rule.status.all"/>');
	autoloadDt();
	$('#table-rule').dataTable().fnDraw();
}

function autoloadDt(){
	var roletype = $("#roletype").val();
	var deviceRuleName = $("#deviceRuleName").val();
	var platform=$("#e_platform").val();
	//获取页面数据
	$('#table-rule').DataTable({
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
			"url" : "${pageUrl}",
			"type" : "get",
			"dataSrc" : "data",
			"data" : {
				"roletype" : roletype,
				"deviceRuleName" : deviceRuleName,
				"platform":platform
			}
		},
		"columns" : [ {data : "id"}, 
		              {data : "name"}, 
		              {data : "describe"},
		              {data : "deviceRuleMatch.id"}, 
		              {data : "id"}, 
		              {data : "updateTime"}, 
		              {data : "next_check_time"},
		              {data : "id"}
		            ],
		"columnDefs" : [
				{
					targets : [ 0],
					render : function(
							data, type,
							full, meta) {
						return full.isactive==1?"<fmt:message key='tiles.views.institution.device.rule.status.enable'/>":"<fmt:message key='tiles.views.institution.device.rule.status.disable'/>";
					}
				},
				{
					className: "col-lg-2 datatb-max-width-device-rule",
					targets : [ 1],
					render : function(
							data, type,
							full, meta) {
						return  '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+full.name+'"><span class="text-ellipsis">'+full.name+'</span><div>';
					}
				},
				{
					targets : [ 5],
					render : function(
							data, type,
							full, meta) {
						return new Date(parseInt(full.updateTime)).Format("yyyy-MM-dd hh:mm:ss");
					}
				},
				{
					targets : [ 6 ],
					render : function(
							data, type,
							full, meta) {
						if(full.next_check_time!=null){
							return new Date(parseInt(full.next_check_time)).Format("yyyy-MM-dd hh:mm:ss");
						} else 
							return "<fmt:message key='tiles.views.institution.device.rule.newly.none'/>" ;
					}
				},
				{
					targets : [ 3 ],
					render : function(
							data, type,
							full, meta) {
						if(full.platform==1){
							return "<fmt:message key='tiles.views.institution.device.rule.add.common'/>";
						}else if(full.platform==2){
							return "<fmt:message key='tiles.views.institution.device.rule.add.android'/>";
						}else{
							return "<fmt:message key='tiles.views.institution.device.rule.add.ios'/>";
						}
					}
				},
				{
					targets : [ 4 ],
					render : function(
							data, type,
							full, meta) {
						if(full.organization.name=="0/0/0"){
							return full.organization.name;
						}
						else
						return '<a href="javascript:void(0);"onclick="showLegal('+full.id+')" class="text-primary">'+full.organization.name+'</a>';
					}
				},
				{
					className: "col-lg-2 datatb-max-width-device-rule",
					targets : [ 2 ],
					render : function(
							data, type,
							full, meta) {
						return '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+full.describe+'"><span class="text-ellipsis">'+full.describe+'</span><div>';
					}
				},
				{
					targets : [ 7 ],
					render : function(
							data, type,
							full, meta) {
						if("${softtek_manager.auth}"=="0"&&"${softtek_manager.user}"!=""){
							return '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">'
							+ '<i class="i  i-settings"></i>'
							+ '</a>'
							+ '<ul class="dropdown-menu" style="margin-left:-100px;margin-top:-25px;">'
							+ '<li><a href="javascript:void(0);" onclick="view('+full.id+')"><i class="fa fa-eye"></i>&nbsp;<fmt:message key="tiles.views.institution.device.rule.table.view"/></a></li></ul>';
						}
						var isenable=0;
						if("${softtek_manager.user}"!=""&&"${softtek_manager.id}"!=full.createBy){
							isenable=1;
						} 
						var islock="";
						var istest="";
						if(full.isactive==1){
							islock='<li><a href="javascript:void(0);" onclick="toggle('+full.id+','+isenable+')"><i class="fa fa-lock"></i>&nbsp;<fmt:message key="tiles.views.institution.device.rule.table.disable"/></a></li>'
							istest='<li><a href="javascript:void(0);" onclick="testing('+full.id+')"><i class="fa fa-check"></i>&nbsp;<fmt:message key="tiles.views.institution.device.rule.opera.testing"/></a></li>';
						}else{
							islock='<li><a href="javascript:void(0);" onclick="toggle('+full.id+','+isenable+')"><i class="fa fa-unlock"></i>&nbsp;<fmt:message key="tiles.views.institution.device.rule.table.enable"/></a></li>'
						}
						return '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">'
						+ '<i class="i  i-settings"></i>'
						+ '</a>'
						+ '<ul class="dropdown-menu" style="margin-left:-100px;margin-top:-120px;">'
						+ '<li><a href="javascript:void(0);" onclick="view('+full.id+')"><i class="fa fa-eye"></i>&nbsp;<fmt:message key="tiles.views.institution.device.rule.table.view"/></a></li>'
						+ '<li><a href="javascript:void(0);" onclick="modify('+full.id+','+isenable+')"><i class="fa fa-pencil"></i>&nbsp;<fmt:message key="tiles.views.institution.device.rule.table.modify"/></a></li>'
						+ '<li><a href="javascript:void(0);" onclick="copyRule('+full.id+','+isenable+')"><i class="fa fa-copy"></i>&nbsp;<fmt:message key="tiles.views.institution.device.rule.table.copy"/></a></li>'
						+islock
						+ istest
						+ '<li class="divider"></li>'
						+ '<li><a href="javascript:void(0);" onclick="deleteRule('+full.id+','+isenable+')"><i class="i i-trashcan text-danger"></i><span class="text-danger">&nbsp;<fmt:message key="tiles.views.institution.device.rule.table.delete"/></span></a></li>'
						+ '</ul>';
					}
				}]
	});
}

	function showLegal(rid){
		//获取页面数据
		$('#table-rule-legal').DataTable({
			//"dom":"<'m-r m-t-lg pull-right'f>t<'row '<'col-lg-2'l>r<'col-lg-3'i><'pull-right'p>>",
			//"dom": 'rt<"bottom"pli><"clear">',
		    "autoWidth": false,
		    "searching" : false,
			"stateSave" : true,
			"ordering" : false,
			"bSort" : false,
			"pageLength" : 10,
			"lengthChange": false,
			"pagingType" : "simple",
			"serverSide" : true,
			"bDestroy" : true,
			"oLanguage":{
				"sUrl":languageUrl
			},
			"ajax" : {
				"url" : "${legalpageUrl}?rid="+rid,
				"type" : "get",
				"dataSrc" : "data",
			},
			"columns" : [ {data : "deviceBasicInfo.user.realname"}, 
			              {data : "deviceBasicInfo.user.username"}, 
			              {data : "deviceBasicInfo.device_name"}, 
			              {data : "status"}, 
			              {data : "violate_time"}, 
			              {data : "id"}, 
			              {data : "id"},
			            ],
			            "columnDefs" : [
										{
											targets : [0],
											render : function(
													data, type,
													full, meta) {
												if(full.deviceBasicInfo==null||
												   full.deviceBasicInfo.user==null||
												   full.deviceBasicInfo.user.realname==null||
												   full.deviceBasicInfo.user.realname==""){
													return "-----";
												}else{
													return full.deviceBasicInfo.user.realname
												}
											}
										},
										{
											targets : [1],
											render : function(
													data, type,
													full, meta) {
												if(full.deviceBasicInfo==null||
												   full.deviceBasicInfo.user==null||
												   full.deviceBasicInfo.user.username==null||
												   full.deviceBasicInfo.user.username==""){
													return "-----";
												}else{
													return full.deviceBasicInfo.user.username;
												}
											}
										},
										{
											targets : [2],
											render : function(
													data, type,
													full, meta) {
												if(full.deviceBasicInfo==null||
												   full.deviceBasicInfo.device_name==null||
												   full.deviceBasicInfo.device_name==""){
													return "-----";
												}else{
													return full.deviceBasicInfo.device_name;
												}
											}
										},
			            				{
			            					targets : [3],
			            					render : function(
			            							data, type,
			            							full, meta) {
			            						if(full.status>0)
			            							return "<fmt:message key='tiles.views.institution.device.rule.table.illegal.enable'/>";
			            						else
			            							return "<fmt:message key='tiles.views.institution.device.rule.table.illegal.disable'/>";
			            					}
			            				},
			            				{
			            					targets : [5],
			            					render : function(
			            							data, type,
			            							full, meta) {
			            						var operationList=full.operation;
			            						var dealStr="";
			            						for(var i=0;i<operationList.length;i++){
			            							var data=returnData(operationList[i].deviceRuleOperationRecord.type,
			            									operationList[i].deviceRuleOperationRecord.condition,
			            									operationList[i].deviceRuleOperationRecord.value,
			            									0);
			            							dealStr=dealStr+","+data.firstStr+"--"+data.secondStr;
			            						}
			            						return dealStr.substr(1,dealStr.length-1);
			            					}
			            				},
			            				{
			            					targets : [6],
			            					render : function(
			            							data, type,
			            							full, meta) {
			            						if(null==full.deviceBasicInfo){
			            							return "-----";
			            						}
			            						if(full.status==0){
			            							return '<a class="text-mute"><fmt:message key="tiles.views.institution.device.rule.table.illegal.detail"/></a>';
			            						}
			            						return '<a href="javascript:void(0)" class="text-primary" onclick="illegalDetail('+full.deviceRule.id+','+full.deviceBasicInfo.id+')"><fmt:message key="tiles.views.institution.device.rule.table.illegal.detail"/></a>';
			            					}
			            				}
			            				]
		});
		$("#legalModal").modal(open);
	}
	
	function loadIllegalDt(rid,did){
		$('#table-rule-legal-detail').DataTable({
			"dom": 'rt<"bottom"pli><"clear">',
		    "autoWidth": false,
		    "searching" : false,
			"stateSave" : true,
			"ordering" : false,
			"bSort" : false,
			"pageLength" : 10,
			"pagingType" : "simple",
			"serverSide" : true,
			"bDestroy" : true,
			"oLanguage":{
				"sUrl":languageUrl
			},
			"ajax" : {
				"url" : "${legaldetailpageUrl}?rid="+rid+"&did="+did,
				"type" : "get",
				"dataSrc" : "data",
			},
			"columns" : [ {data : "deviceRule.name"},  
			              {data : "violate_time"}, 
			              {data : "id"}, 
			              {data : "id"},
			            ],
			            "columnDefs" : [
										{
													targets : [0],
													render : function(
															data, type,
															full, meta) {
														if(full.deviceRule.name.length>20){
															var Str=full.deviceRule.name.substr(0,20)+"...";
															return '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+full.deviceRule.name+'"><span class="text-ellipsis"><td>'+Str+'</td></span><div>';
														}
														
														return '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+full.deviceRule.name+'"><span class="text-ellipsis"><td>'+full.deviceRule.name+'</td></span><div>';
													}
												},
												{
													targets : [1],
													render : function(
															data, type,
															full, meta) {
														var dateStr=new Date(parseInt(full.violate_time)).Format("yyyy-MM-dd hh:mm:ss")
														return '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+dateStr+'"><span class="text-ellipsis"><td>'+dateStr+'</td></span><div>';
													}
												},
												{
													targets : [2],
													render : function(
															data, type,
															full, meta) {
														var data=returnData(full.deviceRuleItemRecord.type,
																full.deviceRuleItemRecord.condition,
																full.deviceRuleItemRecord.value,
																1);
														var dealStr="";
														if(data.thirdStr.length>0){
															dealStr=data.firstStr+"--"+data.secondStr+"--"+data.thirdStr;
														}else{
															dealStr=data.firstStr+"--"+data.secondStr;
														}
														return '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+dealStr+'"><span class="text-ellipsis"><td>'+dealStr+'</td></span><div>';
													}
												},
												
												{
													targets : [3],
													render : function(
															data, type,
															full, meta) {
														return '<a href="javascript:void(0)" class="text-primary" onclick="delIllegalDetail('+full.deviceRule.id+','+full.deviceBasicInfo.id+','+full.id+')"><fmt:message key="tiles.views.institution.device.rule.add.type.rule.delete"/></a>';
													}
												}
			            				]
		});
	}
	
	//违规详情
	function illegalDetail(rid,did){
		//获取页面数据
		loadIllegalDt(rid,did);
		$("#legalDetailModal").modal(open);
	}
	
	//删除违规详情
	function delIllegalDetail(rid,did,id){
		var csrf="${_csrf.token}";
		$.post("${dellegalUrl}",{id:id,_csrf:csrf},function(result){
			$(".notify").notify({type:result.type,message: { html: false, text: result.message}}).show();
			loadIllegalDt(rid,did);
		},"json");
	}
	
	//检测规则
	function testing(rid){
		if(rid.length!=0){
			var csrf="${_csrf.token}";
			$.post("${testingUrl}",{rid:rid,_csrf:csrf},function(result){
				$(".notify").notify({type:result.type,message: { html: false, text: result.message}}).show();
				autoloadDt();
			},"json");
		}
	}

	//==================显示确认复制 start========================
	function copyRule(rid,isenable){
		if(isenable==1){
			Modal.confirm().on(function(e){if(e==true){$("#copy-rule-id").val(rid);$("#copyModal").modal(open);}});
		}else{
			$("#copy-rule-id").val(rid);
			$("#copyModal").modal(open);
		}
	}
	
	//拷贝设备规则
	function copyRuleConfirm(){
		var rid=$("#copy-rule-id").val().trim();
		if(rid.length!=0){
			rid=parseInt(rid);
			var csrf="${_csrf.token}";
			$.post("${copyUrl}",{rid:rid,_csrf:csrf},function(result){
				$(".notify").notify({type:result.type,message: { html: false, text: result.message}}).show();
				autoloadDt();
			},"json");
		}
	}
	//==================显示确认复制 end========================

	//显示确认删除
	function deleteRule(rid,isenable){
		$("#del-rule-id").val(rid);
		if(isenable==1){
			Modal.confirm().on(function(e){if(e==true){
				$("#delModal").modal();
			}});
		}else{
			$("#delModal").modal();
		}
	}
	
	//删除设备规则
	function truncateRule(){
		var rid=$("#del-rule-id").val().trim();
		if(rid.length!=0){
			rid=parseInt(rid);
			var csrf="${_csrf.token}";
			$.post("${deleteUrl}",{rid:rid,_csrf:csrf},function(result){
				$(".notify").notify({type:result.type,message: { html: false, text: result.message}}).show();
				autoloadDt();
			},"json");
		}
		
	}


	//修改将值注入-------------------
	//tab恢复默认设置
	function defaultSetting(){
		$("#detail-rule-id").val('');
		$("#detail-rule-name").val('');
		$("#detail-rule-describe").val('');
		$("#rule-tab-justified li").eq(0).find("a").attr("href","#detail");
		$("#rule-tab-justified li").eq(1).find("a").attr("href","#_arranage");
		$("#rule-tab-justified li").eq(2).find("a").attr("href","#_rule");
		$("#rule-tab-justified li").eq(3).find("a").attr("href","#_opera");
    	
		$("#rule-tab-justified li").eq(0).addClass("active");
    	$("#rule-tab-justified li:gt(0)").each(function(){
    		$(this).removeClass("active");
    		$(this).find("span").removeClass("text-info").addClass("text-muted");
    		$(this).find("b").removeClass("tab-badge-info");
    	});
    	$("#tab-pane-body .tab-pane").eq(0).addClass("active");
    	$("#tab-pane-body .tab-pane:gt(0)").each(function(){
    		$(this).removeClass("active");
    	});
    	$("#rule-tab-justified li").eq(0).find("span").removeClass("text-muted").addClass("text-info");
    	$("#rule-tab-justified li").eq(0).find("b").addClass("tab-badge-info");
    	
    	var nodes=$('#tree').treeview('getChecked');
    	for(var i=0;i<nodes.length;i++){
    		$('#tree').treeview('uncheckNode', [ nodes[i].nodeId, { silent: true } ]);
    	}
    	
    	$("#vtls").find(":checkbox").each(function(){
			var value=$(this).val();
			var name=$(this).parent().text().trim();
			$(this).parent().html('<input type="checkbox" value="'+value+'"><i></i>'+name);
		});
    	
    	$(".chosen-select").chosen("destroy");
		
    	$(".chosen-select option").each(function(){
			if($(this).is(":selected")){
				$(this).removeAttr("selected");
			}
		});
    	
		//$(".chosen-select").trigger("chosen:updated");
		$(".chosen-select").chosen({search_contains:true,no_results_text:"<fmt:message key='tiles.views.institution.device.rule.table.norecords'/>"});
		$(".chosen-container-multi").addClass("chosen-w");
    	
		$("#firstSelect").val(0)
		$("#tmplContent").html('');
		$("#tmplRemoteContent").html('');
		$("#tmpladdIcon").html('');
		
		$("#operafirstSelect ").val(0);
		$("#tmplOperaContent").html('');
		$("#tmplOperaRemoteContent").html('');
		$("#tmplOperaAddIcon").html('');
		
		
    	$("#ruleTb tr:gt(0)").remove();
    	$("#operaTb tr:gt(0)").remove();
    	
		$("#btn-next").addClass("hidden");
		$("#btn-confirm").addClass("hidden");
	}
	
	//detail
	function modifyDetail(result){
		$("#detail-rule-id").val(result.deviceRule.id);
		$("#detail-rule-name").val(result.deviceRule.name);
		$("#detail-rule-describe").val(result.deviceRule.describe);
	}
	
	//arrange
	function modifyArrange(result){
		$("#arranage-rule-select option").each(function(){
			if(parseInt($(this).val().trim())==result.deviceRule.platform){
				$(this).attr("selected","selected");
			}
		});
		var nodes=$('#tree').treeview('getUnchecked');
		for(var i=0;i<nodes.length;i++){
			for(var j=0;j<result.deviceRuleDepartment.length;j++){
				if(nodes[i].tags.id==result.deviceRuleDepartment[j].structure.id){
					$('#tree').treeview('checkNode', [ nodes[i].nodeId, { silent: true } ]);
				}
			}
		}
		
		$("#vtls").find(":checkbox").each(function(){
			for(var i=0;i<result.deviceRuleVirtualGroup.length;i++){
				if(parseInt($(this).val().trim())==result.deviceRuleVirtualGroup[i].virtualGroup.id){
					var value=$(this).val();
					var name=$(this).parent().text().trim();
					$(this).parent().html('<input type="checkbox" checked value="'+value+'"><i></i>'+name);
				}
			}
		});
		
		$(".chosen-select").chosen("destroy");
		
		 $.get("${usersUrl}",function(data){
			$(".chosen-select").html('');
			for(var i=0;i<data.length;i++){
				var flag=0;
				for(var k=0;k<result.deviceRuleUser.length;k++){
					if(result.deviceRuleUser[k].user.id==data[i].id){
						flag=1;break;
					}
				}
				if(flag==1){
					$(".chosen-select").append('<option value="'+data[i].id+'" selected="selected">'+data[i].realname+'('+data[i].username+')</option>');
				}else{
					$(".chosen-select").append('<option value="'+data[i].id+'">'+data[i].realname+'('+data[i].username+')</option>');
				}
			}
			$(".chosen-select").trigger("chosen:updated");
			$(".chosen-select").chosen();
			$(".chosen-container-multi").addClass("chosen-w");
		},"json"); 
	}
	
	//rule
	function modifyRule(result){
		$("#rule-match-select option").each(function(){
			if(parseInt($(this).val().trim())==result.deviceRule.deviceRuleMatch.id){
				$(this).attr("selected","selected");
			}
		});
		for(var i=0;i<result.deviceRuleItemRelation.length;i++){
			var data=returnData(result.deviceRuleItemRelation[i].deviceRuleItemRecord.type,
					result.deviceRuleItemRelation[i].deviceRuleItemRecord.condition,
					result.deviceRuleItemRelation[i].deviceRuleItemRecord.value,1);
			$("#ruleTb").append($("#tableTrTmpl").tmpl(data));
		}
		if($("#ruleTb tr").length==1){
			$("#btn-next").addClass("hidden");
		}else{
			$("#btn-next").removeClass("hidden");
		}
	}
	
	//operation
	function modifyOperation(result){
		for(var i=0;i<result.deviceRuleOperationItemRelation.length;i++){
			
			var data=returnData(result.deviceRuleOperationItemRelation[i].deviceRuleOperationRecord.type,
					result.deviceRuleOperationItemRelation[i].deviceRuleOperationRecord.condition,
					result.deviceRuleOperationItemRelation[i].deviceRuleOperationRecord.value,0);
			$("#operaTb").append($("#tableTrTmpl").tmpl(data));
		}
		if($("#operaTb tr").length==1){
			$("#btn-confirm").addClass("hidden");
		}else{
			$("#btn-confirm").removeClass("hidden");
		}
	}
	
	function addNewly(){
		defaultSetting();
		$("#addModal .modal-title").text('<fmt:message key="tiles.views.institution.device.rule.add" />');
		$("#addModal").modal(open);
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
					if(condition>0){
						secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.gps.open'/>";
					}else{
						secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.gps.close'/>";
					}
					
					thirdStr="";
				}break;
			}
		}
		var data={firstStr:firstStr,firstVal:type,
			secondStr:secondStr,secondVal:condition,
			thirdStr:thirdStr};
		return data;
	}

	//=========================显示修改规则 start=========================
	//显示修改规则
	function _modify(rid){
		$.get("${newlyUrl}",{rid:rid},function(result){
			defaultSetting();
			modifyDetail(result);
			modifyArrange(result);
			modifyRule(result);
			modifyOperation(result);
			$("#addModal .modal-title").text('<fmt:message key="tiles.views.institution.device.rule.modify" />');
			$("#addModal").modal(open);
		},"json")
	}
	
	function modify(rid,isenable){
		if(isenable==1){
			Modal.confirm().on(function(e){if(e==true){_modify(rid);}});
		}else{
			_modify(rid);
		}
	}
	
	//===============启用或者禁用规则 start================
	function _toggle(rid){
		var csrf="${_csrf.token}";
		$.post("${toggleUrl}",{rid:rid,_csrf:csrf},function(result){
			$(".notify").notify({type:result.type,message: { html: false, text: result.message}}).show();
			autoloadDt();
		},"json");
	}
	
	function toggle(rid,isenable){
		if(isenable==1){
			Modal.confirm().on(function(e){if(e==true){_toggle(rid);}});
		}else{
			_toggle(rid);
		}
	}
	//===============启用或者禁用规则 end================
	
	//查看规则
	function view(rid){
		$.get("${newlyUrl}",{rid:rid},function(result){
			$("#viewModal .modal-body").html($("#newlyTmpl").tmpl(result));
			$("#viewModal").modal(open);
		},"json");
	}

	//选择类别
	function selectOnChange(obj){
		var selectVal=$(obj).val();
		switch(selectVal){
			case "0":{
				$("#tmplContent").html('');
				$("#tmplRemoteContent").html('');
				$("#tmpladdIcon").html('');
			}break;
			case "1":{
				/* var data={data:[{id:"1",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.black'/>"},{id:"0",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.white'/>"}]}; */
				var data={data:[{id:"1",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.black'/>"}]};
				$("#tmplContent").html($("#selectTmpl").tmpl(data));
				$.get("${nameListUrl}",{listType:1},function(result){
					if(result.length>0){
						var result={data:result};
						$("#tmplRemoteContent").html($("#selectRemoteTmpl").tmpl(result));
						$("#tmpladdIcon").html($("#addIconTmpl").tmpl());
					}else{
						$("#tmplRemoteContent").html('');
						$("#tmpladdIcon").html('');
					}
					
				},"json");
			}break;
			case "2":{
				var data={data:[{id:"1",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.root.enable'/>"},{id:"0",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.root.disable'/>"}]};
				$("#tmplContent").html($("#crackTmpl").tmpl(data));
				$("#tmplRemoteContent").html('');
				$("#tmpladdIcon").html($("#addIconTmpl").tmpl());
			}break;
			case "3":{
				$("#tmplContent").html('');
				$("#tmplRemoteContent").html($("#txtTmpl").tmpl());
				$("#tmpladdIcon").html($("#addIconTmpl").tmpl());
			}break;
			case "4":{
				var data={data:[{id:"1",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.comp.eq'/>"},{id:"2",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.comp.gt'/>"},{id:"3",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.comp.lt'/>"},
				                {id:"4",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.comp.gteq'/>"},{id:"5",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.comp.lteq'/>"}]};
				$("#tmplContent").html($("#crackTmpl").tmpl(data));
				$("#tmplRemoteContent").html($("#txtTmpl").tmpl());
				$("#tmpladdIcon").html($("#addIconTmpl").tmpl());
			}break;
			case "5":{
				var data={data:[{id:"1",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.config.enable'/>"},{id:"0",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.config.disable'/>"}]};
				$("#tmplContent").html($("#crackTmpl").tmpl(data));
				$("#tmplRemoteContent").html('');
				$("#tmpladdIcon").html($("#addIconTmpl").tmpl());
			}break;
			case "6":{
				var data={data:[{id:"1",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.device.enterp'/>"},{id:"0",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.device.person'/>"}]};
				$("#tmplContent").html($("#crackTmpl").tmpl(data));
				$("#tmplRemoteContent").html('');
				$("#tmpladdIcon").html($("#addIconTmpl").tmpl());
			}break;
			case "7":{
				var data={data:[{id:"1",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.range.in'/>"},{id:"0",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.range.out'/>"}]};
				$("#tmplContent").html($("#crackTmpl").tmpl(data));
				$("#tmplRemoteContent").html($("#clockpickerTmpl").tmpl());
				$("#tmpladdIcon").html($("#addIconTmpl").tmpl());
				$('#clockpicker-start').clockpicker({
				    placement: 'bottom',
				    align: 'left',
				    autoclose: true,
				    'default': 'now'
				});
				$('#clockpicker-end').clockpicker({
				    placement: 'bottom',
				    align: 'left',
				    autoclose: true,
				    'default': 'now'
				});
			}break;
			case "8":{
				var data={data:[{id:"1",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.version.eq'/>"},{id:"0",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.version.neq'/>"}]};
				$("#tmplContent").html($("#crackTmpl").tmpl(data));
				$("#tmplRemoteContent").html('');
				$("#tmpladdIcon").html($("#addIconTmpl").tmpl());
			}break;
			case "9":{
				var data={data:[{id:"1",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.in'/>"},{id:"2",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.wait'/>"},{id:"3",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.outing'/>"},{id:"4",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.inpipe'/>"},{id:"5",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.have'/>"},{id:"6",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.lost'/>"}]};
				$("#tmplContent").html($("#crackTmpl").tmpl(data));
				$("#tmplRemoteContent").html('');
				$("#tmpladdIcon").html($("#addIconTmpl").tmpl());
			}break;
			case "10":{
				var data={data:[{id:"0",name:"0"},{id:"1",name:"1"},{id:"2",name:"2"}]};
				$("#tmplContent").html($("#crackTmpl").tmpl(data));
				$("#tmplRemoteContent").html('');
				$("#tmpladdIcon").html($("#addIconTmpl").tmpl());
			}break;
			case "11":{
				var data={data:[{id:"1",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.gteqone'/>"}]};
				$("#tmplContent").html($("#crackTmpl").tmpl(data));
				$("#tmplRemoteContent").html('');
				$("#tmpladdIcon").html($("#addIconTmpl").tmpl());
			}break;
			case "12":{
				var data={data:[{id:"0",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.gps.close'/>"},{id:"1",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.gps.open'/>"}]};
				$("#tmplContent").html($("#crackTmpl").tmpl(data));
				$("#tmplRemoteContent").html('');
				$("#tmpladdIcon").html($("#addIconTmpl").tmpl());
			}break;
		}
	}
	
	//第四步选择类别
	function operaSelectOnChange(obj){
		var selectVal=$(obj).val();
		switch(selectVal){
			case "0":{
				$("#tmplOperaContent").html('');
				$("#tmplOperaRemoteContent").html('');
				$("#tmplOperaAddIcon").html('');
			}break;
			case "1":{
				/* var data={data:[{id:"1",name:"管理员邮件通知"},{id:"2",name:"短信通知"},{id:"3",name:"推送通知"},{id:"4",name:"邮件通知"}]}; */
				var data={data:[{id:"3",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.push'/>"},{id:"4",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.email.inform'/>"}]};
				$("#tmplOperaContent").html($("#crackTmpl").tmpl(data));
				$("#tmplOperaRemoteContent").html($("#txtTmpl").tmpl());
				$("#tmplOperaAddIcon").html($("#opAddIconTmpl").tmpl());
			}break;
			case "2":{
				/* var data={data:[{id:"1",name:"企业数据擦除"},{id:"0",name:"恢复出厂设置"}]}; */
				var data={data:[{id:"0",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.backto'/>"}]};
				$("#tmplOperaContent").html($("#opOrderTmpl").tmpl(data));
				data={data:[{id:"1",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.deviceall'/>"}/* ,{id:"2",name:"个人设备"},{id:"3",name:"企业设备"} */]};
				$("#tmplOperaRemoteContent").html($("#crackTmpl").tmpl(data));
				$("#tmplOperaAddIcon").html($("#opAddIconTmpl").tmpl());
			}break;
			case "3":{
				var data={data:[{id:"1",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.forbidden.signin'/>"}/* ,{id:"2",name:"禁止访问应用资源"},{id:"3",name:"禁止访问文件资源"} */]};
				$("#tmplOperaContent").html($("#crackTmpl").tmpl(data));
				$("#tmplOperaRemoteContent").html('');
				$("#tmplOperaAddIcon").html($("#opAddIconTmpl").tmpl());
			}break;
			case "4":{
				var data={data:[{id:"1",name:"<fmt:message key='tiles.views.institution.device.rule.add.android'/>"},{id:"0",name:"<fmt:message key='tiles.views.institution.device.rule.add.ios'/>"}]};
				$("#tmplOperaContent").html($("#opStrategyTmpl").tmpl(data));
				$.get("${devicePolicyUrl}",{type:"1"},function(result){
					if(result.length>0){
						var result={data:result};
						$("#tmplOperaRemoteContent").html($("#crackTmpl").tmpl(result));
						$("#tmplOperaAddIcon").html($("#opAddIconTmpl").tmpl());
					}else{
						$("#tmplOperaRemoteContent").html('');
						$("#tmplOperaAddIcon").html('');
					}
				},"json");
			}break;
			case "5":{
				var data={data:[{id:"1",name:"邮件擦除"},{id:"2",name:"禁止访问"}]};
				$("#tmplOperaContent").html($("#crackTmpl").tmpl(data));
				$("#tmplOperaRemoteContent").html('');
				$("#tmplOperaAddIcon").html($("#opAddIconTmpl").tmpl());
			}break;
			case "6":{
				var data={data:[{id:"1",name:"限制使用"}]};
				$("#tmplOperaContent").html($("#crackTmpl").tmpl(data));
				//这里查询用户限制使用的应用列表
				/* $.get("",{type:"0"},function(result){
					result={data:result};
					$("#tmplOperaRemoteContent").html($("#crackTmpl").tmpl(result));
				}); */
				data={data:[{id:"1",name:"QQ（iOS Appstore）"},{id:"2",name:"QQ空间（iOS Appstore）"}]};
				$("#tmplOperaRemoteContent").html($("#crackTmpl").tmpl(data));
				$("#tmplOperaAddIcon").html($("#opAddIconTmpl").tmpl());
			}break;
			case "7":{
				var data={data:[{id:"1",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.confirm'/>"}]};
				$("#tmplOperaContent").html($("#crackTmpl").tmpl(data));
				$("#tmplOperaRemoteContent").html('');
				$("#tmplOperaAddIcon").html($("#opAddIconTmpl").tmpl());
			}break;
		}
	}
	
	function operaOrderSelectOnChange(obj){
		var selectVal=$(obj).val();
		switch(selectVal){
			case "0":{
				var data={data:[{id:"1",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.device.enterp'/>"}]};
				$("#tmplOperaRemoteContent").html($("#crackTmpl").tmpl(data));
			}break;
			case "1":{
				var data={data:[{id:"1",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.deviceall'/>"},{id:"2",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.device.person'/>"},{id:"3",name:"<fmt:message key='tiles.views.institution.device.rule.add.type.condition.device.enterp'/>"}]};
				$("#tmplOperaRemoteContent").html($("#crackTmpl").tmpl(data));
			}break;
		}
	}
	
	function operaStrategySelectOnChange(obj){
		var selectVal=$(obj).val();
		switch(selectVal){
			case "0":{
				$.get("${devicePolicyUrl}",{type:"0"},function(result){
					if(result.length>0){
						var result={data:result};
						$("#tmplOperaRemoteContent").html($("#crackTmpl").tmpl(result));
					}else{
						$("#tmplOperaRemoteContent").html('');
					}
					
				},"json");
			}break;
			case "1":{
				$.get("${devicePolicyUrl}",{type:"1"},function(result){
					if(result.length>0){
						var result={data:result};
						$("#tmplOperaRemoteContent").html($("#crackTmpl").tmpl(result));
					}else{
						$("#tmplOperaRemoteContent").html('');
					}
				},"json");
			}break;
		}
	}
	
	
	
	//应用黑白名单之黑名单与白名单切换
	function selectNameList(obj){
		var selectVal=$(obj).val();
		$.get("${nameListUrl}",{listType:selectVal},function(result){
			if(result.length>0){
				var result={data:result};
				$("#tmplRemoteContent").html($("#selectRemoteTmpl").tmpl(result));
				$("#tmpladdIcon").html($("#addIconTmpl").tmpl());
			}else{
				$("#tmplRemoteContent").html('');
				$("#tmpladdIcon").html('');
			}
		},"json");
	}
	
	//添加一行操作
	function opAddTr(){
		var firstSelectVal=$("#operafirstSelect option:selected").val();
		var operaFirstStr=$("#operafirstSelect option:selected").text();
		var operaSecondStr="<fmt:message key='tiles.views.institution.device.rule.is'/>";
		var secondVal=-1;
		if($("#tmplOperaContent").find("select").length>0){
			operaSecondStr=$("#tmplOperaContent").find("select option:selected").text().trim();
			secondVal=$("#tmplOperaContent").find("select option:selected").val();
		}
		var operaThirdStr="";
		if($("#tmplOperaRemoteContent").find("select").length>0){
			operaThirdStr=$("#tmplOperaRemoteContent").find("select option:selected").text().trim();
		}else if($("#tmplOperaRemoteContent").find("input").length>0){
			operaThirdStr=$("#tmplOperaRemoteContent").find("input").val().trim()
		}
		var flag=0;
		$("#operaTb tr").each(function(){
			if($(this).find("td").eq(0).text().trim()==operaFirstStr&&
			$(this).find("td").eq(1).text().trim()==operaSecondStr&&
			$(this).find("td").eq(2).text().trim()==operaThirdStr){
				flag=1;
			}
			if($(this).find("td").eq(0).attr("ref")==4&&$(this).find("td").eq(1).text().trim()==operaSecondStr){
				$(".notify").notify({type:"danger",message: { html: false, text: operaFirstStr+"<fmt:message key='tiles.views.institution.device.rule.table.onlyone'/>"}}).show();
				flag=3;
				return false;
			}
			
			
		});
		
		if(operaThirdStr.trim().length==0&&firstSelectVal=="1"){
			$(".notify").notify({type:"danger",message: { html: false, text: "<fmt:message key='tiles.views.institution.device.rule.table.input'/>"+operaFirstStr+""}}).show();
			return false;
		}
		if(flag==0){
			var data={firstStr:operaFirstStr,firstVal:firstSelectVal,secondStr:operaSecondStr,secondVal:secondVal,thirdStr:operaThirdStr};
			$("#operaTb").append($("#tableTrTmpl").tmpl(data));
			if($("#btn-confirm").hasClass("hidden")){
				$("#btn-confirm").removeClass("hidden")
			}
		}else if(flag==1){
			$(".notify").notify({type:"warning",message: { html: false, text: "<fmt:message key='tiles.views.institution.device.rule.table.exists'/>"}}).show();
			return false;
		}
		
	}
	
	//添加一行规则
	function addTr(){
		var ruleMSVal=$("#rule-match-select option:selected").val();
		var firstSelectVal=$("#firstSelect option:selected").val();
		var firstStr=$("#firstSelect option:selected").text();
		var secondStr="为";
		var secondVal=-1;
		if($("#tmplContent").find("select").length>0){
			secondStr=$("#tmplContent").find("select option:selected").text().trim();
			secondVal=$("#tmplContent").find("select option:selected").val();
		}
		var thirdStr="";
		if($("#tmplRemoteContent").find("select").length>0){
			thirdStr=$("#tmplRemoteContent").find("select option:selected").text().trim();
		}else if($("#tmplRemoteContent").find("input").length>0){
			if($("#firstSelect option:selected").val()!="7"){
				thirdStr=$("#tmplRemoteContent").find("input").val().trim();
			}else{
				var start_time=$("#tmplRemoteContent").find("input").eq(0).val().trim();
				var end_time=$("#tmplRemoteContent").find("input").eq(1).val();
				if(start_time.length==0||end_time.length==0){
					$(".notify").notify({type:"danger",message: { html: false, text: "<fmt:message key='tiles.views.institution.device.rule.table.startend'/>"}}).show();
					return false;
				}else{
					var time_start= new Date().Format("yyyy-MM-dd")+" "+start_time;
					var time_end= new Date().Format("yyyy-MM-dd")+" "+end_time;
					if(new Date(time_end)<new Date(time_start)){
						$(".notify").notify({type:"danger",message: { html: false, text: "<fmt:message key='tiles.views.institution.device.rule.table.startltend'/>"}}).show();
						return false;
					}
					thirdStr=start_time+"~"+end_time;
				}
				
			}
		}
		if(thirdStr.trim().length==0&&(firstSelectVal=="3"||firstSelectVal=="4")){
			$(".notify").notify({type:"danger",message: { html: false, text: "<fmt:message key='tiles.views.institution.device.rule.table.input'/>"+firstStr+""}}).show();
			return false;
		}
		var data={firstStr:firstStr,firstVal:firstSelectVal,secondStr:secondStr,secondVal:secondVal,thirdStr:thirdStr};
		var flag=0;
		$("#ruleTb tr:gt(0)").each(function(){
			if($(this).find("td").eq(0).text().trim()==firstStr&&
			$(this).find("td").eq(1).text().trim()==secondStr&&
			$(this).find("td").eq(2).text().trim()==thirdStr){
				flag=1;
			}
			if(ruleMSVal=="1"&&(firstSelectVal=="1"||firstSelectVal=="2"||
			firstSelectVal=="5"||firstSelectVal=="7"||
			firstSelectVal=="8"||firstSelectVal=="12")){
				if($(this).find("td").eq(0).text().trim()==firstStr&&
						   $(this).find("td").eq(1).text().trim()!=secondStr){
							flag=2;
				}
			}
			
			if(firstSelectVal=="9"&&$(this).find("td").eq(0).attr("ref")=="9"){
						if(($(this).find("td").eq(1).text().trim()=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.in'/>"&&secondStr=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.wait'/>")||
						($(this).find("td").eq(1).text().trim()=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.in'/>"&&secondStr=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.outing'/>")||
						($(this).find("td").eq(1).text().trim()=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.in'/>"&&secondStr=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.inpipe'/>")||
						($(this).find("td").eq(1).text().trim()=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.wait'/>"&&secondStr=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.in'/>")||
						($(this).find("td").eq(1).text().trim()=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.wait'/>"&&secondStr=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.outing'/>")||
						($(this).find("td").eq(1).text().trim()=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.wait'/>"&&secondStr=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.inpipe'/>")||
						($(this).find("td").eq(1).text().trim()=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.outing'/>"&&secondStr=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.in'/>")||
						($(this).find("td").eq(1).text().trim()=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.outing'/>"&&secondStr=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.wait'/>")||
						($(this).find("td").eq(1).text().trim()=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.outing'/>"&&secondStr=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.inpipe'/>")||
						($(this).find("td").eq(1).text().trim()=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.inpipe'/>"&&secondStr=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.in'/>")||
						($(this).find("td").eq(1).text().trim()=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.inpipe'/>"&&secondStr=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.wait'/>")||
						($(this).find("td").eq(1).text().trim()=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.inpipe'/>"&&secondStr=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.outing'/>")||
						($(this).find("td").eq(1).text().trim()=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.have'/>"&&secondStr=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.lost'/>")||
						($(this).find("td").eq(1).text().trim()=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.lost'/>"&&secondStr=="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.have'/>")){
					flag=2;
				}
			}
		});
		
		if(flag==0){
			if($("#btn-next").hasClass("hidden")){
				$("#btn-next").removeClass("hidden")
			}
			$("#ruleTb").append($("#tableTrTmpl").tmpl(data));
		}else if(flag==1){
			$(".notify").notify({type:"warning",message: { html: false, text: "<fmt:message key='tiles.views.institution.device.rule.table.exists.add'/>"}}).show();
			return false;
		}else if(flag==2){
			$(".notify").notify({type:"danger",message: { html: false, text: firstStr+"<fmt:message key='tiles.views.institution.device.rule.table.onetype'/>"}}).show();
		}
		
		if($("#operaTb tr").length>1){
			$("#btn-confirm").removeClass("hidden");
		}else{
			$("#btn-confirm").addClass("hidden");
		}
	}
	
	//删除自身这行
	function trash(obj){
		if($("#ruleTb tr").length==2){
			if($(obj).parent().parent().parent().parent().attr("id")=="ruleTb")
				$("#btn-next").addClass("hidden");
		}
		if($("#operaTb tr").length==2){
			if($(obj).parent().parent().parent().parent().attr("id")=="operaTb")
				$("#btn-confirm").addClass("hidden");
		}
		$(obj).parent().parent().remove()
	}
	
	function pre_detail(index){
		$("#rule-tab-justified li").eq(index).removeClass("active");
    	$("#rule-tab-justified li").eq(index-1).addClass("active");
    	$("#tab-pane-body .tab-pane").eq(index).removeClass("active");
    	$("#tab-pane-body .tab-pane").eq(index-1).addClass("active");
	}
	
	//下一步按钮和上一步按钮
	function next_detail(index){
		if(index==1){
			var next_detail_validator = $('#nextDetailFrm').parsley();
			next_detail_validator.validate();
	        if(next_detail_validator.isValid()){
	        	$("#rule-tab-justified li").eq(1).find("a").attr("href","#arranage");
	        	$("#rule-tab-justified li").eq(0).removeClass("active");
	        	$("#rule-tab-justified li").eq(1).addClass("active");
	        	$("#tab-pane-body .tab-pane").eq(0).removeClass("active");
	        	$("#tab-pane-body .tab-pane").eq(1).addClass("active");
	        	$("#rule-tab-justified li").eq(1).find("span").removeClass("text-muted").addClass("text-info");
	        	$("#rule-tab-justified li").eq(1).find("b").addClass("tab-badge-info");
	        }
		}else{
			if(index==2){
				var nodeChecked=$('#tree').treeview('getChecked').length;
				var vtlChecked=$('#vtls').find(":checked").length;
				var userSelected=$('.chosen-select').find(":selected").length;
				if(nodeChecked==0&&vtlChecked==0&&userSelected==0){
					$(".notify").notify({type:"danger",message: { html: false, text: "<fmt:message key='tiles.views.institution.device.rule.table.inputatleastone'/>"}}).show();
					return false;
				}
			}
			switch(index){
			case 1:$("#rule-tab-justified li").eq(index).find("a").attr("href","#arranage");break;
			case 2:$("#rule-tab-justified li").eq(index).find("a").attr("href","#rule");break;
			case 3:$("#rule-tab-justified li").eq(index).find("a").attr("href","#opera");break;
			}
        	$("#rule-tab-justified li").eq(index-1).removeClass("active");
        	$("#rule-tab-justified li").eq(index).addClass("active");
        	$("#tab-pane-body .tab-pane").eq(index-1).removeClass("active");
        	$("#tab-pane-body .tab-pane").eq(index).addClass("active");
        	$("#rule-tab-justified li").eq(index).find("span").removeClass("text-muted").addClass("text-info");
        	$("#rule-tab-justified li").eq(index).find("b").addClass("tab-badge-info");
        	iosRule();
        	iosOperate();
		}
	}
	

	
	//递归选择部门节点
	function selectNodeLoop(data,flag){
		var tempNodes=data.nodes;
		if(tempNodes!=undefined){
			for(var i=0;i<tempNodes.length;i++){
				if(flag==1){
					$('#tree').treeview('checkNode', [ tempNodes[i].nodeId, { silent: true } ]);
				}else{
					$('#tree').treeview('uncheckNode', [ tempNodes[i].nodeId, { silent: true } ]);
				}
				 selectNodeLoop(tempNodes[i],flag);
			}
		}
		return ;
	}
	
	//返回规则详情的参数
	function ruleDetail(){
		var params={};
		params.id=$("#detail-rule-id").val().trim();
		params.name=$("#detail-rule-name").val().trim();
		params.decribe=$("#detail-rule-describe").val().trim();
		return params;
	}
	
	//返回规则分配的参数
	function ruleArrange(){
		var params={};
		params.platform=$("#arranage-rule-select option:selected").val();
		var departIds="";
		var nodeChecked=$('#tree').treeview('getChecked');
		if(nodeChecked.length>0){
			for(var i=0;i<nodeChecked.length;i++){
				departIds=nodeChecked[i].tags.id+","+departIds;
			}
			departIds=departIds.substr(0,departIds.length-1);
		}
		params.departIds=departIds;
		var vtlIds="";
		if($("#vtls").find(":checked").length>0){
			$("#vtls").find(":checked").each(function(){
				vtlIds=$(this).attr("value")+","+vtlIds;
			});
			vtlIds=vtlIds.substr(0,vtlIds.length-1);
		}
		params.vtlIds=vtlIds
		var userIds="";
		if($('.chosen-select').find(":selected").length>0){
			$('.chosen-select').find(":selected").each(function(){
				userIds=$(this).attr("value")+","+userIds;
			});
			userIds=userIds.substr(0,userIds.length-1);
		}
		params.userIds=userIds;
		return params;
	}

	//返回设备规则的参数
	function ruleRule(){
		var params={};
		params.match=$("#rule-match-select option:selected").val();
		params.items={};
		var ruleTbRows=$("#ruleTb tr");
		if(ruleTbRows.length>1){
			for(var i=1;i<ruleTbRows.length;i++){
				var item={};
				item.type=ruleTbRows.eq(i).find("td").eq(0).attr("ref");
				item.condition=ruleTbRows.eq(i).find("td").eq(1).attr("ref")
				item.value=ruleTbRows.eq(i).find("td").eq(2).text();
				params.items[i-1]=item;
			}
		}
		return params;
	}
	
	//返回操作参数
	function ruleOperation(){
		var params={};
		params.items={};
		var ruleTbRows=$("#operaTb tr");
		if(ruleTbRows.length>1){
			for(var i=1;i<ruleTbRows.length;i++){
				var item={};
				item.type=ruleTbRows.eq(i).find("td").eq(0).attr("ref");
				item.condition=ruleTbRows.eq(i).find("td").eq(1).attr("ref")
				if(ruleTbRows.eq(i).find("td").eq(0).attr("ref")=="2"){
					//item.value=ruleTbRows.eq(i).find("td").eq(2).attr("ref");
					item.value=1;
				}else{
					item.value=ruleTbRows.eq(i).find("td").eq(2).text();
				}
				params.items[i-1]=item;
			}
		}
		return params;
	}
	
	//完成提交
	function btnConfim(){
		var params={};
		params.detail=ruleDetail();
		params.arrange=ruleArrange();
		params.rule=ruleRule();
		params.operation=ruleOperation();
		if($("#detail-rule-id").val().length==0){
			$("#btn-confirm").text("<fmt:message key='tiles.views.institution.device.rule.creating'/>");
		}else{
			$("#btn-confirm").text("<fmt:message key='tiles.views.institution.device.rule.updating'/>");
		}
		$("#btn-confirm").attr("disabled","disabled");
		var csrf="${_csrf.token}";
		$.post("${saveUrl}",{data:params,_csrf:csrf},function(result){
			if(result=="success"){
				$("#addModal").modal("hide");
				$("#btn-confirm").text("<fmt:message key='tiles.views.institution.device.rule.add.done'/>");
				$("#btn-confirm").removeAttr("disabled");
				window.location.reload();
			}
		},"json");
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
		function iosRule(){
			if($("#arranage-rule-select").val() == "3"){
				$("#firstSelect").html('<option value="0"><fmt:message key="tiles.views.institution.device.rule.add.type.choose"/></option>'+
									   '<option value="1"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.blackandwhite"/></option>'+
									   '<option value="2"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.root"/></option>'+
								 	   '<option value="3"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.no"/></option>'+
									   '<option value="4"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.osversion"/></option>'+
									   '<option value="9"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.status"/></option>'+
									   '<option value="12"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.gps"/></option>');
			}else{
				if("${menu!=null&&menu[21].isshow==-1}"){
					$("#firstSelect").html('<option value="0"><fmt:message key="tiles.views.institution.device.rule.add.type.choose"/></option>'+
							'<option value="1"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.blackandwhite"/></option>'+
							'<option value="2"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.root"/></option>'+
							'<option value="3"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.no"/></option>'+
							'<option value="4"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.osversion"/></option>'+
							'<option value="5"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.lock"/></option>'+
							'<option value="7"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.timerange"/></option>'+
							'<option value="8"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.imsi"/></option>'+
							'<option value="9"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.status"/></option>'+
							'<option value="10"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.sim"/></option>'+
							'<option value="11"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.sd"/></option>'+
							'<option value="12"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.gps"/></option>');
				}else{
					$("#firstSelect").html('<option value="0"><fmt:message key="tiles.views.institution.device.rule.add.type.choose"/></option>'+
							'<option value="2"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.root"/></option>'+
							'<option value="3"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.no"/></option>'+
							'<option value="4"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.osversion"/></option>'+
							'<option value="5"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.lock"/></option>'+
							'<option value="7"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.timerange"/></option>'+
							'<option value="8"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.imsi"/></option>'+
							'<option value="9"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.status"/></option>'+
							'<option value="10"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.sim"/></option>'+
							'<option value="11"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.sd"/></option>'+
							'<option value="12"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.gps"/></option>');
				}
			}
		}
		function iosOperate(){
			if($("#arranage-rule-select").val() == "3"){
				if("${menu!=null&&menu[14].isshow==-1}"){
					$("#operafirstSelect").html('<option value="0"><fmt:message key="tiles.views.institution.device.rule.add.type.choose"/></option>'+
												'<option value="1"><fmt:message key="tiles.views.institution.device.rule.add.type.opera.inform"/></option>'+
											    '<option value="3"><fmt:message key="tiles.views.institution.device.rule.add.type.opera.termial"/></option>'+
											    '<option value="4"><fmt:message key="tiles.views.institution.device.rule.add.type.opera.changestragy"/></option>');
				}else{
					$("#operafirstSelect").html('<option value="0"><fmt:message key="tiles.views.institution.device.rule.add.type.choose"/></option>'+
												'<option value="1"><fmt:message key="tiles.views.institution.device.rule.add.type.opera.inform"/></option>'+
											    '<option value="3"><fmt:message key="tiles.views.institution.device.rule.add.type.opera.termial"/></option>');
				}
			}else{
				if("${menu!=null&&menu[14].isshow==-1}"){
					$("#operafirstSelect").html('<option value="0"><fmt:message key="tiles.views.institution.device.rule.add.type.choose"/></option>'+
												'<option value="1"><fmt:message key="tiles.views.institution.device.rule.add.type.opera.inform"/></option>'+
												'<option value="2"><fmt:message key="tiles.views.institution.device.rule.add.type.opera.order"/></option>'+
												'<option value="3"><fmt:message key="tiles.views.institution.device.rule.add.type.opera.termial"/></option>'+
												'<option value="4"><fmt:message key="tiles.views.institution.device.rule.add.type.opera.changestragy"/></option>'+
												'<option value="7"><fmt:message key="tiles.views.institution.device.rule.add.type.opera.changelock"/></option>');
				}else{
					$("#operafirstSelect").html('<option value="0"><fmt:message key="tiles.views.institution.device.rule.add.type.choose"/></option>'+
												'<option value="1"><fmt:message key="tiles.views.institution.device.rule.add.type.opera.inform"/></option>'+
												'<option value="2"><fmt:message key="tiles.views.institution.device.rule.add.type.opera.order"/></option>'+
												'<option value="3"><fmt:message key="tiles.views.institution.device.rule.add.type.opera.termial"/></option>'+
												'<option value="7"><fmt:message key="tiles.views.institution.device.rule.add.type.opera.changelock"/></option>');
				}
			}
		}
</script>