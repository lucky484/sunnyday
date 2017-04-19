<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/institution/dptmanager/pages" var="pagesUrl" />
<spring:url value="/institution/dptmanager/activeuser" var="activeUrl" />
<spring:url value="/institution/dptmanager/delete" var="deleteUrl" />
<spring:url value="/institution/dptmanager/promotion" var="promotionUrl" />
<spring:url value="/institution/user/roles" var="rolesUrl" />
<spring:url value="/institution/user/newly" var="queryMemberInfo" />
<spring:url value="/resources/js/jquery.tmpl.js" var="tmplJs" />
<spring:url value="/resources/js/jquery.cookie.js" var="cookieJs" />
<script src="${tmplJs}"></script>
<script src="${cookieJs}"></script>
<spring:url value="/resources/js/datatables-1.10.11/lang/language.lang" var="dtLangUrl" />
<script id="troggleTmpl" type="text/x-jquery-tmpl">

</script>
<script type="text/javascript">
var defaultData = ${tree};
$('#promoteTree').treeview({
	color : "#428bca",
	expandIcon : 'glyphicon glyphicon-chevron-right',
	collapseIcon : 'glyphicon glyphicon-chevron-down',
	showCheckbox : true,
	showBorder : false,
	multiSelect : true,
	highlightSelected : false,
	data : defaultData
	
	
	
});
//=============================== datatables国际化
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
	//禁用//启用管理员
	$(function() {
		loadDT();
    });
	$(document).ready(function(){
		$("div[class='radio i-checks'] > label").focus(function(){
			$("#promoteTreeRole").removeClass("parsley-error");
			$("#promote-tree-error-role").addClass("hidden");
		});
	});
	function enableDisable(uid, flag) {
		$(function() {
			var csrf="${_csrf.token}";
			$.post("${activeUrl}", {
				id : uid,
				_csrf:csrf
			}, function(result) {
				result = eval('[' + result + ']');
				$(".notify").notify({
					type : result[0].type,
					message : {
						html : false,
						text : result[0].message
					}
				}).show();
				loadDT();
			});
		});
	};
	
	
	//删除管理员
	function delemanager() {
		var uid=$("#deluid").val();
		var urdid=$("#delurdid").val();
		$(function() {
			var csrf="${_csrf.token}";
			$.post("${deleteUrl}", {
				uid : uid,
				urd : urdid,
				_csrf:csrf
			}, function(result) {
				result = eval('[' + result + ']');
				$(".notify").notify({
					type : result[0].type,
					message : {
						html : false,
						text : result[0].message
					}
				}).show();
				$('#delModalTree').modal('hide');
				loadDT();
			});
		});
	};
	function opendelmodal(uid, urdid){
		$("#deluid").val(uid);
		$("#delurdid").val(urdid);
		$('#delModalTree').modal(open);
	}
	
	//修改管理员modal
	function modify(uid,username,realname) {
		$(".ck-tmpl").html('');    
		$('#promoteTree').treeview('expandAll', { levels: 1, silent: true });
		$("#promoteTreeRole").removeClass("parsley-error");
		$("#promote-tree-error-role").addClass("hidden");
		$("#username").text(username);
		$("#realname").text(realname);

 		$("#managerModal").find("textarea").val('');
		$("#managerModal").find(".promote-uid").val(uid);
		
		 $('#promoteTree').on('nodeChecked', function(event, data) {
			selectNodeLoop(data,1);
			$("#promoteTree").removeClass("parsley-error");
			$("#promote-tree-error").addClass("hidden");
		}); 
		$('#promoteTree').on('nodeUnchecked', function(event, data) {
			selectNodeLoop(data,0);
		}); 
		var csrf="${_csrf.token}";
		$.post("dptmanager/queryRoleByUid",{id:uid,_csrf:csrf},function(data){
			 if(data.userRoleDepartment.departIds != null && data.userRoleDepartment.departIds != ""){
			    	var departId = data.userRoleDepartment.departIds.split(",");
			    	nodes = $('#promoteTree').treeview('getUnchecked');
			    	for (var j = 0; j < departId.length; j++) {
						for (var i = 0; i < nodes.length; i++) {
							if (nodes[i].tags.id == departId[j]) {
								$('#promoteTree').treeview('checkNode',
										[ nodes[i].nodeId, {
											silent : true
										} ]);
							}
						}
					}
			    }
			 var f=0;  
	    	 var mark=data.userRoleDepartment.mark;
	    	 $("textarea[name='mark']").val(mark);
	    	 if(data.auth==0){
	    		 $("input[name=auth][value=1]").removeAttr("checked","checked");
	    		 $("input[name=auth][value=0]").attr("checked","checked");
	    	 }else{
	    		 $("input[name=auth][value=0]").removeAttr("checked","checked");
	    		 $("input[name=auth][value=1]").attr("checked","checked");
	    	 }
			 for(var i=0;i<data.list.length;i++){
				 console.log(data.userRoleDepartment.role.id+"====="+data.list[i].id);
			    	if(data.userRoleDepartment.role.id == data.list[i].id){
			    		var rval=data.list[i].id;
			    		var rtxt=data.list[i].name;
			    		$(".ck-tmpl").append('<div class="radio i-checks"><label><input type=radio name=role.id checked value="'+rval+'"><i></i>'+rtxt+'</div>');
			    		var id=data.userRoleDepartment.role.id;
			    		f++;
			    	}else{
			    		var rval=data.list[i].id;
			    		var rtxt=data.list[i].name;
			    		$(".ck-tmpl").append('<div class="radio i-checks"><label><input type=radio name=role.id  value="'+rval+'"><i></i>'+rtxt+'</div>');
			    	}
			    }
			if(f==0){
		  		  var rval=$("input[type='radio']:eq(0)").val();
			      var rtxt=$("input[type='radio']:eq(0)").parent().text();
			      $("input[type='radio']:eq(0)").parent().parent().html('<label><input type="radio" name="role.id" checked value="'+rval+'"><i></i>'+rtxt+'</label>');
			    } 
		},'json');
		
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
	var validator = $('#mark').parsley();
	//提交提升部门管理员信息到服务器
	function subPromote(){
		validator.validate();
		 if(validator.isValid()){
			var depart="";
			var selectedNodes=$('#promoteTree').treeview('getChecked');
			for(var i=0;i<selectedNodes.length;i++){
				depart=selectedNodes[i].tags.id+separator+depart;
			}
			if(depart.length>0){
				depart=depart.substr(0,depart.length-1);
				$("#promoteTree").removeClass("parsley-error");
				$("#promote-tree-error").addClass("hidden");
			} 
			else
			{
				$("#promoteTree").addClass("parsley-error");
				$("#promote-tree-error").removeClass("hidden");
				return false;
			}
			
			$("#managerModal").find(".department_ids").val(depart);
			$('#uRDFrm').find("input[type='button']").attr("disabled","disabled");
			//发送只服务器
			var options = {
                    url: '${promotionUrl}',
                    type: 'post',
                    data: $("#uRDFrm").serialize(),
                    success: function (result) {
                    	$("#managerModal").modal('hide');
                    	result=eval('['+result+']');
                    	$('#uRDFrm').find("input[type='button']").removeAttr("disabled");
            			$(".notify").notify({type:result[0].type,message: { html: false, text: result[0].message}}).show();
            			autoLoadDTByste();
                    }
                };
				   $.ajax(options);
                }
	}

	
	//显示个人的信息
	function queryMemberInfo(uid) {
		$.get("${queryMemberInfo}", {uid : uid}, function(result) { 
			$("#infoModal").find(".modal-body").html(
					$('#userInfo').tmpl(result));
			$("#infoModal").modal(open);
		},"json");
	}
	function autoLoadDTByste(){
		loadDT();
	}
	
	function searchDeptLists()
	{
		loadDT();
		$('#dptmanager').dataTable().fnDraw();
	}
	 
	function cleanDeptLists()
	{
		$("#searchaccountname").val('');
		$("#searchrealname").val('');
		loadDT();
		$('#dptmanager').dataTable().fnDraw();
	}
	
	//自动加载datatable
	function loadDT() {
		var searchaccountname = $("#searchaccountname").val();
		var searchrealname = $("#searchrealname").val();
		$('#dptmanager').DataTable({
							"dom":"<'m-r m-t-lg pull-right'f>t<'row '<'col-lg-2'l>r<'col-lg-3'i><'pull-right'p>>",
							"ordering" : false,
							"searching" : false,
							"stateSave" : true,
							"bSort" : false,
							"pageLength" : 10,
							"pagingType" : "full_numbers",
							"serverSide" : true,
							"bDestroy" : true,
							"autoWidth": false,
							"oLanguage":{
								"sUrl":languageUrl
							},
							"ajax" : {
								"url" : "${pagesUrl}",
								"type" : "get",
								"dataSrc" : "data",
								"data" : 
									{
										"searchaccountname" : searchaccountname,
										"searchrealname" : searchrealname
									}
							},
							"columns" : [ {
								data : "user.username"
							}, {
								data : "user.realname"
							}, {
								data : "role.name"
							}, {
								data : "user.phone"
							}, {
								data : "islock"
							}, {
								data : "departNames" 
							}, {
								data : ""
							} ],
							columnDefs : [
									{
										className: "col-sm-1",
										targets : [ 0 ],
										render : function(data, type, full,
												meta) {
											return '<a href="javascript:void(0)" class="text-primary" onclick="queryMemberInfo('
													+ full.user.id
													+ ')" data-toggle="modal" data-target="#queryRoleModal">'
													+ full.user.username
													+ '</a>';
										}
									},
									{
										className: "col-lg-1",										
										targets : [ 2 ],
										render : function(data, type, full,
												meta) {
											
											return full.user.phone;
										}
									},
									{
										targets : [ 3 ],
										render : function(data, type, full,
												meta) {
											if(full.role){
												rolename=full.role.name;
											}else{
												rolename="<fmt:message key='web.institution.dpt.roletags.label'/>";
											}
											
											return rolename;
										}
									},
									
									
									
									{
										className: "col-lg-2 datatb-max-width",
										targets : [ 4 ],
										render : function(data, type, full,
												meta) {
											
											return '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+full.departNames+'"><span class="text-ellipsis">'+full.departNames+'</span><div>';
										}

									},
{
										
										targets : [ 5 ],
										render : function(data, type, full,
												meta) {
											if (full.islock == "0") {
												islock ="<fmt:message key='web.institution.dpt.islock.false.label'/>";  
											} else {
												islock = "<fmt:message key='web.institution.dpt.islock.true.label'/>";
											}
											return islock;
										}

									},
								
									{
										
										targets : [ 6 ],
										render : function(data, type, full,
												meta) {
											if (full.islock == "0") {
												retunlist = '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">'
														+ '<i class="i  i-settings"></i>'
														+ '</a>'
														+ '<ul class="dropdown-menu" style="margin-top:-70px;">'
														+ '<li><a href="javascript:void(0)" onclick="enableDisable('
														+ full.user.id
														+ ',1)" data-toggle="modal" data-target="#queryRoleModal"><i class="fa fa-eye"></i>&nbsp;<fmt:message key="web.institution.dpt.islock.true.label"/></a></li>'
														+ ' <li><a href="javascript:void(0);" onclick="modify('
														+ full.user.id
														+ ','+"'"+full.user.username+"'"+','+"'"+full.user.realname+"'"+')" data-toggle="modal" data-target="#modifyRoleModal"><i class="fa fa-pencil"></i>&nbsp;<fmt:message key="web.institution.dpt.modify.label"/></a></li>'
														+ '<li><a href="javascript:void(0);" onclick="opendelmodal('
														+ full.user.id
														+ ','
														+ full.id
														+ ')"><i class="i i-trashcan text-danger"></i><span class="text-danger">&nbsp;<fmt:message key="web.institution.dpt.delete.label"/></span></a></li>'
														+ '</ul>'
											} else {
												retunlist = '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">'
														+ '<i class="i  i-settings"></i>'
														+ '</a>'
														+ '<ul class="dropdown-menu" style="margin-left:-100px;margin-top:-70px;">'
														+ '<li><a href="javascript:void(0)" onclick="enableDisable('
														+ full.user.id
														+ ',0)" ><i class="fa fa-eye"></i>&nbsp;<fmt:message key="web.institution.dpt.active.label"/></a></li>'
														+ ' <li><a href="javascript:void(0);" onclick="modify('
														+ full.user.id
														+ ')" data-toggle="modal" data-target="#modifyRoleModal"><i class="fa fa-pencil"></i>&nbsp;<fmt:message key="web.institution.dpt.modify.label"/></a></li>'
														+ '<li><a href="javascript:void(0);" onclick="opendelmodal('
														+ full.user.id
														+ ','
														+ full.id
														+ ')"><i class="i i-trashcan text-danger"></i><span class="text-danger">&nbsp;<fmt:message key="web.institution.dpt.delete.label"/></span></a></li>'
														+ '</ul>'

											}
											return retunlist;
										}
									}, ]

						});
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
//========================jquery=================================
<script type="text/javascript">
	var defaultData = ${tree};
	var separator = ",";
	</script>