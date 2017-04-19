<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spring:url value="/resources/js/jquery.tmpl.js" var="tmplJs" />
<spring:url value="/resources/js/jquery.cookie.js" var="cookieJs" />
<script src="${tmplJs}"></script>
<script src="${cookieJs}"></script>
<spring:url value="/resources/js/datatables-1.10.11/js/jquery.dataTables.js" var="dataTableJs" />
<script src="${dataTableJs}"></script>
<spring:url value="/resources/js/datatables-1.10.11/js/dataTables.bootstrap.js" var="dataTableBootstrapJs" />
<script src="${dataTableBootstrapJs}"></script>
<spring:url value="/admin/index/validUsername" var="ckUsernameUrl" />
<spring:url value="/admin/index/managerLists" var="pagesUrl" />
<spring:url value="/resources/js/datatables-1.10.11/lang/language.lang" var="dtLangUrl" />
<script type="text/javascript">
	//=============================== datatables国际化
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
	function LoadManagerList(){
		var username = $('#username').val();
		var name = $('#name').val();
		var orgId = $('#orgType').val();
		var csrf="${_csrf.token}"; 
		$('#managerList').DataTable({
			"dom":"<'m-r m-t-lg pull-right'f>t<'row '<'col-lg-2'l>r<'col-lg-3'i><'pull-right'p>>",
			"searching" : false,
			"stateSave" : true,
			"ordering" : false,
			"bSort" : false,
			"pageLength" : 10,
			"pagingType" : "full_numbers",
			"serverSide" : true,
			"bDestroy" : true,
			"oLanguage": {
				"sUrl":languageUrl
		    },
			"ajax" : {
				"dataType":'json',
				"type" : "POST",
				"url" : "${pagesUrl}?now="+ new Date().getTime(),
				"data" : {"username":username,"name":name,"orgId":orgId,"_csrf":csrf}
			},
			"columns":[
			              {data : "username"}, 
			              {data : "name"}, 
			              {data : "user_type"},
			              {data : "phone"},
			              {data : "status"},
			              {data : "updateTime"},
			              {data : "total"},
			              {data : ""}
			          ],
			"columnDefs":[
							
							{
								className:"col-lg-1 datatables-max-length",
								"targets" : [0],
								"render" : function(data, type,full, meta) {
									return '<a href="javascript:void(0);" class="text-primary"  onclick="viewManager('
											+ full.id
											+ ')">'
											+ full.username
											+ '</a>';
								}
							},
							{
								className:"col-lg-1 datatables-max-length",
								"targets" : [1],
								"render" : function(data, type, row) {
											return row.name;
								}
							},
							{
								className:"col-lg-1 datatables-max-length",
								"targets" : [2],
								"render" : function(data, type, row) {
									if(row.user_type == '2'){
										return '<fmt:message key="tiles.views.admin.index.manager.table.user_type"/>';
								}
								}
							},
							{
								className:"col-lg-1 datatables-max-length",
								"targets" : [3],
								"render" : function(data, type, row) {
									return '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+row.phone+'"><span class="text-ellipsis">'+row.phone+'</span><div>';
								}
							},
							{
								className:"col-lg-1 datatables-max-length",
								"targets" : [4],
								"render" : function(data, type, row) {
											if(row.status == 1){
												return '<fmt:message key="tiles.views.admin.index.manager.table.unlock"/>';
										}else{
											return '<fmt:message key="tiles.views.admin.index.manager.table.lock"/>';
										}
								}
							},
							 {
								className:"col-lg-1 datatables-max-length",
								"targets" : [5],
								"render" : function(
										data, type,
										full, meta) {
									if(full.last_login_time == null || full.last_login_time == ''){
										return '';
									}else{
										var dateStr=new Date(parseInt(full.last_login_time)).Format("yyyy-MM-dd hh:mm:ss");
										return '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+dateStr+'"><span class="text-ellipsis">'+dateStr+'</span><div>';
									}
								}
							},
							{
								className:"col-lg-1 datatables-max-length",
								"targets" : [6],
								"render" : function(data, type, row) {
											if(row.total == null){
												return 0;
										}else{
											return row.total;
										}
								}
							},
							{
								className:"col-lg-1 datatables-max-length",
								"targets" : [7],
								"render" : function(data, type, row) {
									if(row.status ==1){
										return '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">'
										+ '<i class="i  i-settings"></i>'
										+ '</a>'
										+ '<ul class="dropdown-menu" style="margin-left:-100px;margin-top:-60px;">'
										+ '<li><a href="javascript:void(0);" onclick="updManager('+row.id+')">'
										+ '<i class="fa fa-pencil"></i>&nbsp;<fmt:message key="tiles.views.admin.index.manager.table.update"/></a></li>'
										+ '<li><a href="javascript:void(0);" onclick="lockManager('+row.id+')">'
										+ '<i class="fa fa-lock"></i>&nbsp;<fmt:message key="tiles.views.admin.index.manager.table.lock"/></a></li>'
										+ '<li><a href="javascript:void(0);" onclick="delManager('+row.id+')">'
										+ '<i class="i i-trashcan text-danger"></i><span class="text-danger">&nbsp;<fmt:message key="tiles.views.admin.index.manager.table.delete"/></span></a></li>'
										+ '</ul>';
									}else{
										return '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">'
										+ '<i class="i  i-settings"></i>'
										+ '</a>'
										+ '<ul class="dropdown-menu" style="margin-left:-100px;margin-top:-60px;">'
										+ '<li><a href="javascript:void(0);" onclick="updManager('+row.id+')">'
										+ '<i class="fa fa-pencil"></i>&nbsp;<fmt:message key="tiles.views.admin.index.manager.table.update"/></a></li>'
										+ '<li><a href="javascript:void(0);" onclick="unLockManager('+row.id+')">'
										+ '<i class="fa fa-unlock"></i>&nbsp;<fmt:message key="tiles.views.admin.index.manager.table.unlock"/></a></li>'
										+ '<li><a href="javascript:void(0);" onclick="delManager('+row.id+')">'
										+ '<i class="i i-trashcan text-danger"></i><span class="text-danger">&nbsp;<fmt:message key="tiles.views.admin.index.manager.table.delete"/></span></a></li>'
										+ '</ul>';
									}
									
								}
							}   
							                          
					  ]	
			});
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
	
	//查看机构管理员信息
	function viewManager(id){
		//获取机构管理员的相关信息显示在modal上
		var csrf="${_csrf.token}";
		var html = '';
		$.ajax({
			"dataType": 'json',
			"data": {"id":id,"_csrf":csrf},
			'async':false,
			"type": "POST",
			"url": ctx + "/admin/index/getManagerById",
			success: function(data) {
				$.each(data.lists,function(index,obj){
					html += '<label>'+obj.name+'</label><br/>';
				});
				$('#viewUsername').text(data.username == null ? '':data.username);
				$('#vieName').text(data.name == null ? '':data.name);
				$('#viewStatus').text(data.status == null ? '':data.status==1?'<fmt:message key="tiles.views.admin.index.manager.table.unlock"/>':'<fmt:message key="tiles.views.admin.index.manager.table.lock"/>');
				$('#viewRole').text(data.user_type == null ? '':data.user_type==2?'<fmt:message key="tiles.views.admin.index.manager.table.user_type"/>':'');
				$('#viewPhone').text(data.phone == null ? '':data.phone);
				$('#viewEmail').text(data.email == null ? '':data.email);
				$('#viewMark').text(data.mark == null ? '':data.mark);
				$('#viewCreateTime').text(data.createTime == null ? '':new Date(parseInt(data.createTime)).Format("yyyy-MM-dd hh:mm:ss"));
				$('#viewCreateBy').text(data.createBy == null ? '':'<fmt:message key="tiles.views.admin.index.manager.table.user_type.admin"/>');
			}
		});
		$('#viewOrgs').html(html);
		$('#viewModal').modal('show');	
	}
	
	//启用机构管理员
	function unLockManager(id){
		$('#unLockManagerId').val(id);
		$('#unLockManagerModal').modal('show');
	}
	
	function unLockManagerBtn(){
		var csrf="${_csrf.token}"; 
		var id = $('#unLockManagerId').val();	
		$.ajax({
			"dataType": 'json',
	        "data": {"id":id,"_csrf":csrf},
	        "type": "POST",
            "url":ctx+"/admin/index/unLockManager",
	        "success": function(result){
    				$('#unLockManagerModal').modal('hide');
    				window.location.reload();
	        	} 
	   }); 
	}
	
	//禁用机构管理员
	function lockManager(id){
		$('#lockManagerId').val(id);
		$('#lockManagerModal').modal('show');
	}
	
	function lockManagerBtn(){
		var csrf="${_csrf.token}"; 
		var id = $('#lockManagerId').val();		
		$.ajax({
			"dataType": 'json',
	        "data": {"id":id,"_csrf":csrf},
	        "type": "POST",
            "url":ctx+"/admin/index/lockManager",
	        "success": function(result){
   				$('#lockManagerModal').modal('hide');
   				window.location.reload();
	        }
	   });  
	}
	
	//修改机构管理员
	function updManager(id){
		var orgId = [];
		var csrf="${_csrf.token}"; 
		$('#updateFrm').parsley().reset();
		document.forms[1].reset();
		$("#updateFrm").find(".parsley-success").each(function(){
			$(this).removeClass("parsley-success");
		});
		$.ajax({
			"dataType": 'json',
			"data": {"id":id,"_csrf":csrf},
			'async':false,
			"type": "POST",
			"url": ctx + "/admin/index/getManagerById",
			success: function(data) {
				$.each(data.lists,function(index,obj){
					orgId.push(obj.id);
				});
				$('#editUsername').val(data.username == null ? '':data.username);
				$('#editName').val(data.name == null ? '':data.name);
				$('#editPhone').val(data.phone == null ? '':data.phone);
				$('#editEmail').val(data.email == null ? '':data.email);
				$('#editMark').val(data.mark == null ? '':data.mark);
			}
		});
		$('#editOrgList').html('');
		$.ajax({
				"dataType": 'json',
		        "type": "get",
		        'async':false,
	            "url":ctx+"/admin/index/getAllOrganizationLists",
		        "success": function(result){
		        	var html= '';
		        	if(result.length == orgId.length){
		        		html='<label> <input type="checkbox" id="editSelectAll" value="" onclick="editCheckAll();" checked="checked"> <i></i><fmt:message key="tiles.views.admin.index.manager.checkAll"/></label>';
		        	}else{
		        		html='<label> <input type="checkbox" id="editSelectAll" value="" onclick="editCheckAll();"> <i></i><fmt:message key="tiles.views.admin.index.manager.checkAll"/></label>';
		        	}
        			$.each(result,function(index,obj){
       					 if(orgId.contains(obj.id)){
       						html += '<label style="display:list-item;"> <input type="checkbox" class="orgs" name="editOrgs"' 
                   				+ 'value= '+obj.id+' onclick="editCheck();" checked="checked"> <i></i> '+obj.name+'</label>';
       					 }else{ 
       						html += '<label style="display:list-item;"> <input type="checkbox" class="orgs" name="editOrgs"' 
                   				+ 'value= '+obj.id+' onclick="editCheck();"> <i></i> '+obj.name+'</label>';
       					 }
        			});
        			$('#editOrgList').html(html);
		        } 
		   });
		$('#btn').removeClass('active');
		$('#moreless').removeClass('show');
		$("#pwd_modify_confirm").removeAttr("data-parsley-required");
		$('#password_modify_confirm').removeAttr("data-parsley-required");
		$("#pwd_modify_confirm").removeAttr("data-parsley-alphanumber");
		$('#password_modify_confirm').removeAttr("data-parsley-alphanumber");
		$('#password_modify_confirm').removeAttr("data-parsley-equalto");
		$('#managerId').val(id);
		$('#updateModal').modal('show');	
	}
	
	//向后台修改机构管理员
	$('#update').on('click',function(){
		var validator = $('#updateFrm').parsley();
		validator.validate();
        if(validator.isValid()){
        	//当机构没有选择的时候需要给出提示
        	var ids = '';
        	$("input[name=editOrgs]").each(function(){
				//判断chekcbox是否选中应该采用这个方法，其他都是有问题的
				if($(this).is(':checked')){
					ids += $(this).val()+",";
				}
			});
			if(ids == null || ids == '' || ids == undefined){
				$(".notify").notify({type:"warning",message: { html: false, text:'<fmt:message key="tiles.views.admin.index.manager.checkOrganization"/>'}}).show();
				/* $('#openTips').modal('show');
				$('#tips').html('<fmt:message key="tiles.views.admin.index.manager.checkOrganization"/>'); */
				return;
			}
        	//序列化表单的内容，并发送到后台
        	var postData = $("#updateFrm").serialize();
        	$.ajax({
				"dataType": 'json',
		        "data": postData,
		        "type": "POST",
	            "url":ctx+"/admin/index/updateManager",
		        "success": function(result){
        			window.location.reload();
		        } 
		   });  
        	return true;
        }else{
        	return false;
        }
	});
	
	
	//删除机构管理员
	function delManager(id){
		$('#managerId').val(id);
		$('#delOrganizationModel').modal('show');
	}
	
	function deleteManager(){
		var csrf="${_csrf.token}"; 
		var id = $('#managerId').val();
		$.ajax({
			"dataType": 'json',
	        "data": {"id":id,"_csrf":csrf},
	        "type": "POST",
            "url":ctx+"/admin/index/deleteManager",
	        "success": function(result){
   				$('#delOrganizationModel').modal('hide');
   				window.location.reload();
	        } 
	   });  
	}
	
	//添加机构管理员的modal
	function addManager(){
		$('#addFrm').parsley().reset();
		document.forms[0].reset();
		$("#addFrm").find(".parsley-success").each(function(){
			$(this).removeClass("parsley-success");
		});
		$("#username").parsley().addAsyncValidator(
				'existsValidate',function(xhr){
					return !(xhr.responseText.indexOf('true') >= 0); 
				},"${ckUsernameUrl}",
				 { "type": "GET", "dataType": "json", "contentType": "application/json; charset=utf-8", "data": {}});
		$('#orgList').html('');
		//获取所有的机构，显示到界面上
		$.ajax({
				"dataType": 'json',
		        "type": "get",
	            "url":ctx+"/admin/index/getAllOrganizationLists",
		        "success": function(result){
		        	var html='<label> <input type="checkbox" id="selectAll" value="" onclick="checkAll();"> <i></i><fmt:message key="tiles.views.admin.index.manager.checkAll"/></label>';
        			$.each(result,function(index,obj){
        				html += '<label style="display:list-item;"> <input type="checkbox" class="orgs" name="orgs"' 
            				+ 'value= '+obj.id+' onclick="check();"> <i></i> '+obj.name+'</label>';
        			});
        			$('#orgList').html(html);
		        } 
		   }); 
		$('#addModal').modal('show');			
	}
	
	//向后台添加机构管理员
	$('#add').on('click',function(){
		var validator = $('#addFrm').parsley();
		validator.validate();
        if(validator.isValid()){
        	//当机构没有选择的时候需要给出提示
        	var ids = '';
        	$("input[name=orgs]").each(function(){
				//判断chekcbox是否选中应该采用这个方法，其他都是有问题的
				if($(this).is(':checked')){
					ids += $(this).val()+",";
				}
			});
			//当选择按星期备份的时候如果星期没有选择的话需要弹出提示框
			if(ids == null || ids == '' || ids == undefined){
				$(".notify").notify({type:"warning",message: { html: false, text:'<fmt:message key="tiles.views.admin.index.manager.checkOrganization"/>'}}).show();
				/* $('#openTips').modal('show');
				$('#tips').html('<fmt:message key="tiles.views.admin.index.manager.checkOrganization"/>'); */
				return;
			}
        	//序列化表单的内容，并发送到后台
        	var postData = $("#addFrm").serialize();
        	$.ajax({
				"dataType": 'json',
		        "data": postData,
		        "type": "POST",
	            "url":ctx+"/admin/index/saveManager",
		        "success": function(result){
        			window.location.reload();
		        } 
		   });  
        	return true;
        }else{
        	return false;
        }
	});
	
	//关闭对话框	
	$('#cancel').on('click',function(){
		$('#addModal').modal('hide');	
	});
	
	$('#updateCancel').on('click',function(){
		$('#updateModal').modal('hide');	
	});
	
	$('#cancelView').on('click',function(){
		$('#viewModal').modal('hide');	
	});
	
	$('#successTitle').on('click',function(){
		$('#openTips').modal('hide');
	});

	$('#falseLock').on('click',function(){
		$('#lockTips').modal('hide');
	});
	
	//查找
	function searchManagerLists(){
		var username = $('#username').val();
		var name = $('#name').val();
		var orgId = $('#orgType').val();
		LoadManagerList();
		$('#managerList').dataTable().fnDraw();
	}
	
	//清空
	function cleanManagerLists(){
		$('#username').val("");
		$('#name').val("");
		$('#orgType').val("");
		$(".Js_curVal").find("input").val('<fmt:message key="tiles.views.admin.index.manager.selectOrg"/>');
		LoadManagerList();
		$('#managerList').dataTable().fnDraw();
	}
	
	//根据搜索条件模糊搜索所有的机构
	function searchOrgList(){
		$("#orgList label:gt(0)").hide();
		var orgSearch = $("#searchOrg").val();
		if("" != orgSearch){
			$("#orgList label:gt(0):contains('"+orgSearch+"')").each(function(){
				$(this).show();
			});
		}else{
			$("#orgList label:gt(0)").show();
		}
		
		if($('input[name = orgs]:checked').length >= $('input[name = orgs]:visible').length && 0 != $('input[name = orgs]:visible').length){
			$("#selectAll").prop("checked",true);
		}else{
			$("#selectAll").prop("checked",false);
		}
	}
	
	//根据搜索条件模糊搜索所有的机构
	function editSearchOrgList(){
		$("#editOrgList label:gt(0)").hide();
		var orgSearch = $("#editSearchOrg").val();
		if("" != orgSearch){
			$("#editOrgList label:gt(0):contains('"+orgSearch+"')").each(function(){
				$(this).show();
			});
		}else{
			$("#editOrgList label:gt(0)").show();
		}
		
		if($('input[name = orgs]:checked').length >= $('input[name = orgs]:visible').length && 0 != $('input[name = orgs]:visible').length){
			$("#editSelectAll").prop("checked",true);
		}else{
			$("#editSelectAll").prop("checked",false);
		}
	}
	
	//checkbox全选功能
	function checkAll(){
		if($("#selectAll").is(':checked')){
			$('input[name = orgs]:checkbox:visible').each(function(){
				$(this).prop("checked",true);
			});
		}else{
			$('input[name = orgs]:checkbox:visible').each(function(){
				//注意 jquery 1.6之后 对boolean类型的值的赋值是采用 prop，而不是attr
				$(this).prop("checked",false);
			});
		}
	    
	}
	
	function editCheckAll(){
		if($("#editSelectAll").is(':checked')){
			$('input[name = editOrgs]:checkbox:visible').each(function(){
				$(this).prop("checked",true);
			});
		}else{
			$('input[name = editOrgs]:checkbox:visible').each(function(){
				//注意 jquery 1.6之后 对boolean类型的值的赋值是采用 prop，而不是attr
				$(this).prop("checked",false);
			});
		}
	    
	}
	
	//checkbox部分选的功能
	function check(){
		if($('input[name = orgs]:visible').length == $('input[name = orgs]:checked:visible').length){
			$("#selectAll").prop("checked",true);
		}else{
			$("#selectAll").prop("checked",false);
		}
	}
	
	function editCheck(){
		if($('input[name = editOrgs]:visible').length == $('input[name = editOrgs]:checked:visible').length){
			$("#editSelectAll").prop("checked",true);
		}else{
			$("#editSelectAll").prop("checked",false);
		}
	}
	
	//初始化向后台发送请求加载机构管理员列表
	$(function(){
		LoadManagerList();
	});
	
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
	
	Array.prototype.contains = function (obj) {  
	    var i = this.length;  
	    while (i--) {  
	        if (this[i] === obj) {  
	            return true;  
	        }  
	    }  
	    return false;  
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