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
<spring:url value="/admin/index/validOrgType" var="ckOrgTypeUrl" />
<spring:url value="/admin/index/validOrgName" var="ckOrgNameUrl" />
<spring:url value="/admin/index/organizationLists" var="pagesUrl" />
<spring:url value="/admin/index/export" var="exportUrl" />
<spring:url value="/resources/js/datatables-1.10.11/lang/language.lang" var="dtLangUrl" />
<script type="text/javascript">
var validatorupdate = $('#updateFrm').parsley();
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
$(function() {
	$('#update').on('click',function(){
		validatorupdate.validate();
		//校验当前用户输入的license数目和系统当前剩余的license的数目
		//var validator = $('#updateFrm').parsley();
		var id=$("#org_id").val();
		var csrf="${_csrf.token}"; 
		var licenseCount;
		$.ajax({
			"dataType": 'json',
			"data": {"id":id,"_csrf":csrf},
			"type": "POST",
			'async':false,
			"url": ctx + "/admin/index/getOrganizationById",
			success: function(data) {
				licenseCount=data.licenseCount == null ? '':data.licenseCount;
			}
		});
        if(validatorupdate.isValid()){
        	var userLicense = $("#editLicenseCount").val();
    		var sysRemainder = $("#editRemainder").text();
    		var useLicense =$("#useLicense").text();
    		if(Number(userLicense)<Number(useLicense)){
    			$('#openTips').modal('show');
    			$('#tips').html('<fmt:message key="tiles.views.admin.index.organization.add.tips2"/>');
    			return $("#editLicenseCount");
    		}
    		if(Number(userLicense) > Number(sysRemainder)+licenseCount){
    			$('#openTips').modal('show');
    			$('#tips').html('<fmt:message key="tiles.views.admin.index.organization.add.tips"/>');
    			return;
    		}
        	//序列化表单的内容，并发送到后台
        	var postData = $("#updateFrm").serialize();
        	$.ajax({
				"dataType": 'json',
		        "data": postData,
		        "type": "POST",
	            "url":ctx+"/admin/index/updateOrganization",
		        "success": function(result){
		        	if(result.type == 'danger'){
		        		$('#openTips').modal('show');
		    			$('#tips').html('<fmt:message key="tiles.views.admin.index.organization.add.tips"/>');
		    			return;
		        	}else{
		        		window.location.reload();
		        	}
		        } 
		   }); 
        	return true;
        }else{
        	return false;
        }
		
	})
	
});
	function LoadOrganizationList(){
		
		var csrf="${_csrf.token}"; 
		var orgType = $('#orgType').val();
		var name = $('#name').val();
		$('#organizationList').DataTable({
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
				"data" : {	"orgType":orgType,
							"name":name,
							"_csrf":csrf
						 }
			},
			"columns":[
			              //{data : "id"}, 
			              {data : "orgType"}, 
			              {data : "name"}, 
			              {data : "createName"},
			              {data : "totalUsers"},
			              {data : "totalDevices"},
			              {data : "licenseCount"},
			              {data : "useUsers"},
			              {data : "status"},
			              {data : ""}
			          ],
			"columnDefs":[
							/* {
								className: "col-lg-2 datatables-max-length",
							    "targets": [0],
							    "visible": false
							}, */
							{
								className: "col-lg-1 datatables-max-length",
								"targets" : [0],
								"render" : function(data, type,full, meta) {
									return '<a href="javascript:void(0);" class="text-primary"  onclick="viewOrganization('
											+ full.id
											+ ')">'
											+ full.orgType
											+ '</a>';
								}
							},
							{
								className: "col-lg-2 datatables-max-length",
								"targets" : [1],
								"render" : function(data, type,full, meta) {
									return full.name;
								}
							},
							{
								
								className: "col-lg-1 datatables-max-length",
								"targets" : [2],
								"render" : function(data, type,full, meta) {
									return full.createName;
								}
							},
							{
								className: "col-lg-1 datatables-max-length",
								"targets" : [3],
								"render" : function(data, type, row) {
											if(row.totalUsers == null){
												return '0';
											}else{
												return row.totalUsers;
											}
										}
							},
							{
								className: "col-lg-1 datatables-max-length",
								"targets" : [4],
								"render" : function(data, type, row) {
											if(row.totalDevices == null){
												return '0';
											}else{
												return row.totalDevices;
											}
										}
							},
							{
								
								className: "col-lg-1 datatables-max-length",
								"targets" : [5],
								"render" : function(data, type,full, meta) {
									return full.licenseCount;
								}
							},
							{
								className: "col-lg-1 datatables-max-length", 
								"targets" : [6],
								"render" : function(data, type, row) {
											if(row.useUsers == null){
												return '0';
										}else{
											return row.useUsers;
										}
									}
							},
							{
								className: "col-lg-2 datatables-max-length",
								"targets" : [7],
								"render" : function(data, type, row) {
											if(row.status == '1'){
												return '<fmt:message key="tiles.views.admin.index.manager.table.unlock"/>';
											}else{
												return '<fmt:message key="tiles.views.admin.index.manager.table.lock"/>';
											}
											return '';
										}
								},
							{
								className: "col-lg-2 datatables-max-length",
								"targets" : [8],
								"render" : function(data, type, row) {
									if(row.status == 1){
										return '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">'
										+ '<i class="i  i-settings"></i>'
										+ '</a>'
										+ '<ul class="dropdown-menu" style="margin-left:-100px;margin-top:-60px;">'
										+ '<li><a href="javascript:void(0);" onclick="updOrganization('+row.id+')">'
										+ '<i class="fa fa-pencil"></i>&nbsp;<fmt:message key="tiles.views.admin.index.manager.table.update"/></a></li>'
										+ '<li><a href="javascript:void(0);" onclick="lockOrganization('+row.id+')">'
										+ '<i class="fa fa-lock"></i>&nbsp;<fmt:message key="tiles.views.admin.index.manager.table.lock"/></a></li>'
										+ '<li><a href="javascript:void(0);" onclick="delOrganization('+row.id+')">'
										+ '<i class="i i-trashcan text-danger"></i><span class="text-danger">&nbsp;<fmt:message key="tiles.views.admin.index.manager.table.delete"/></span></a></li>'
										+ '</ul>';
									}else{
										return '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">'
										+ '<i class="i  i-settings"></i>'
										+ '</a>'
										+ '<ul class="dropdown-menu" style="margin-left:-100px;margin-top:-60px;">'
										+ '<li><a href="javascript:void(0);" onclick="updOrganization('+row.id+')">'
										+ '<i class="fa fa-pencil"></i>&nbsp;<fmt:message key="tiles.views.admin.index.manager.table.update"/></a></li>'
										+ '<li><a href="javascript:void(0);" onclick="unLockOrganization('+row.id+')">'
										+ '<i class="fa fa-unlock"></i>&nbsp;<fmt:message key="tiles.views.admin.index.manager.table.unlock"/></a></li>'
										+ '<li><a href="javascript:void(0);" onclick="delOrganization('+row.id+')">'
										+ '<i class="i i-trashcan text-danger"></i><span class="text-danger">&nbsp;<fmt:message key="tiles.views.admin.index.manager.table.delete"/></span></a></li>'
										+ '</ul>';
									}
								}
							}   
							                          
					  ]	
			});
		
		
	}
	
	//查看机构
	function viewOrganization(id){
		var csrf="${_csrf.token}";
		var html = '';
		//去后台加载数据并显示到modal上
		$.ajax({
			"dataType": 'json',
			"data": {"id":id,"_csrf":csrf},
			"type": "POST",
			'async':false,
			"url": ctx + "/admin/index/getOrganizationById",
			success: function(data) {
				$.each(data.lists,function(index,obj){
					html += '<label>'+obj.name+'</label><br/>';
				});
				$('#viewOrgType').text(data.orgType == null ? '':data.orgType);
				$('#vieName').text(data.name == null ? '':data.name);
				$('#viewRemainLicense').text(data.licenseCount == null ? '':data.licenseCount);
				$('#viewCreateTime').text(data.createTime == null ? '':new Date(parseInt(data.createTime)).Format("yyyy-MM-dd hh:mm:ss"));
				$('#viewStatus').text(data.status == null ? '':data.status == 1?'<fmt:message key="tiles.views.admin.index.manager.table.unlock"/>':'<fmt:message key="tiles.views.admin.index.manager.table.lock"/>');
				$('#viewMark').text(data.mark == null ? '':data.mark);
				$('#totalDevices').text(data.totalDevices == null ? 0:data.totalDevices);
				$('#totalUsers').text(data.totalUsers == null ? 0:data.totalUsers);
				$('#useLicenses').text(data.useUsers == null ? 0:data.useUsers);
			}
		});
		$('#viewManagerLists').html(html);
		$('#viewModal').modal('show');
	}

	//修改机构
	function updOrganization(id){
		$("#org_id").val(id);
		var managerId = [];
		var csrf="${_csrf.token}"; 
		$('#updateFrm').parsley().reset();
		document.forms[1].reset();
		$("#updateFrm").find(".parsley-success").each(function(){
			$(this).removeClass("parsley-success");
		});
		$("#editOrgType").parsley().addAsyncValidator(
				'existsValidate',function(xhr){
					return !(xhr.responseText.indexOf('true') >= 0); 
				},"${ckOrgTypeUrl}",
				 { "type": "GET", "dataType": "json", "contentType": "application/json; charset=utf-8", "data": {"orgId":id}});
		$("#editName").parsley().addAsyncValidator(
				'existsNameValidate',function(xhr){
					return !(xhr.responseText.indexOf('true') >= 0); 
				},"${ckOrgNameUrl}",
				 { "type": "GET", "dataType": "json", "contentType": "application/json; charset=utf-8", "data": {"orgId":id}});
		//去查询系统中当前还剩余多少个license
		$.ajax({
				"dataType": 'json',
		        "type": "get",
	            "url":ctx+"/admin/index/getRemainderLicense",
		        "success": function(result){
        			$('#editRemainder').text(result.content == null ? '':result.content);
		        }
		   });
		
		$.ajax({
			"dataType": 'json',
			"data": {"id":id,"_csrf":csrf},
			"type": "POST",
			'async':false,
			"url": ctx + "/admin/index/getOrganizationById",
			success: function(data) {
				$.each(data.lists,function(index,obj){
					managerId.push(obj.id);
				});
				$('#editOrgType').val(data.orgType == null ? '':data.orgType);
				$('#editName').val(data.name == null ? '':data.name);
				$('#editLicenseCount').val(data.licenseCount == null ? '':data.licenseCount);
				$('#editMark').val(data.mark == null ? '':data.mark);
				$('#useLicense').text(data.useUsers == null ? 0:data.useUsers);
			}
		});
		//查询该机构下的机构管理员信息
		$('#editManagerLists').html('');
		$.ajax({
			"dataType": 'json',
	        "type": "get",
	        'async':false,
            "url":ctx+"/admin/index/getAllManagerLists",
	        "success": function(result){
	        	var html= '';
	        	if(managerId!=null){
	        		$.each(result,function(index,obj){
	   					 if(managerId.contains(obj.id)){
	   						html += '<label><input type="checkbox" class="vtls" name="editManagers" value='+obj.id+' checked="checked"><i></i>'+obj.name+'</label><br/>'
	   					 }else{ 
	   						html += '<label><input type="checkbox" class="vtls" name="editManagers" value='+obj.id+'><i></i>'+obj.name+'</label><br/>'
	   					 }
	    			});
	        	}
    			$('#editManagerLists').html(html);
	        } 
	   }); 
	   $('#orgId').val(id);
	   $('#updateModal').modal('show');	
		
	}
	//禁用机构
	function lockOrganization(id){
		$('#lockOrgId').val(id);
		$('#lockOrganizationModal').modal('show');
	}
	
	function lockOrganizationBtn(){
		var csrf="${_csrf.token}"; 
		var id = $('#lockOrgId').val();		
		$.ajax({
			"dataType": 'json',
			"data": {"id":id,"_csrf":csrf},
			"type": "POST",
			"url": ctx + "/admin/index/lockOrganization",
			success: function(data) {
				$('#lockOrganizationModal').modal('hide');
				window.location.reload();
			}
		});
	}
	
	//启用机构
	function unLockOrganization(id){
		$('#unLockOrgId').val(id);
		$('#unLockOrganizationModal').modal('show');
	}
	
	function unLockOrganizationBtn(){
		var csrf="${_csrf.token}"; 
		var id = $('#unLockOrgId').val();		
		$.ajax({
			"dataType": 'json',
			"data": {"id":id,"_csrf":csrf},
			"type": "POST",
			"url": ctx + "/admin/index/unLockOrganization",
			success: function(data) {
				$('#unLockOrganizationModal').modal('hide');
				window.location.reload();
			}
		});
		
	}
	
	//删除机构
	function delOrganization(id){
		$('#orgId').val(id);
		$('#delOrganization').modal('show');
	}
	
	function deleteOrganization(){
		var csrf="${_csrf.token}"; 
		var id = $('#orgId').val();		
		$.ajax({
			"dataType": 'json',
			"data": {"id":id,"_csrf":csrf},
			"type": "POST",
			"url": ctx + "/admin/index/deleteOrganization",
			success: function(data) {
				$('#delOrganization').modal('hide');
				window.location.reload();
			}
		});
	}
	
	//新增机构
	function addOrganization(){
		$('#addFrm').parsley().reset();
		document.forms[0].reset();
		$("#addFrm").find(".parsley-success").each(function(){
			$(this).removeClass("parsley-success");
		});
		$("#orgType").parsley().addAsyncValidator(
				'existsValidate',function(xhr){
					return !(xhr.responseText.indexOf('true') >= 0); 
				},"${ckOrgTypeUrl}",
				 { "type": "GET", "dataType": "json", "contentType": "application/json; charset=utf-8", "data": {}});
		$("#name").parsley().addAsyncValidator(
				'existsNameValidate',function(xhr){
					return !(xhr.responseText.indexOf('true') >= 0); 
				},"${ckOrgNameUrl}",
				 { "type": "GET", "dataType": "json", "contentType": "application/json; charset=utf-8", "data": {}});
		//去查询系统中当前还剩余多少个license
		$.ajax({
				"dataType": 'json',
		        "type": "get",
	            "url":ctx+"/admin/index/getRemainderLicense",
		        "success": function(result){
        			$('#remainder').text(result.content == null ? '':result.content);
		        } 
		   });
		
				
		$('#managerLists').html('');
		//查询所有的机构管理员,这里有一个问题，当机构管理员被禁用之后是够可以选择？
		$.ajax({
				"dataType": 'json',
		        "type": "get",
	            "url":ctx+"/admin/index/getAllManagerLists",
		        "success": function(result){
		        	var html='';
        			$.each(result,function(index,obj){
        				html += '<label><input type="checkbox" class="vtls" name="managers" value='+obj.id+'><i></i>'+obj.name+'</label><br/>';
        			});
        			$('#managerLists').html(html);
		        } 
		   }); 
		$('#addModal').modal('show');	
	}
	//导出机构，不能使用ajax的方式做导出的操作
	function exportOrganization(){
	    /* ar form = $("<form></form>");
        form.attr('action',ctx + "/admin/index/export");
        var input1 = $('<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>');
        form.append(input1);
        var orgType = $("#orgType").val();
        var name = $("#name").val();
        var input2 = $('<input type="hidden" name="orgType" value="'+orgType+'"/>');
        var input3 = $('<input type="hidden" name="name" value="'+name+'"/>');
        form.append(input2);
        form.append(input3);
        form.attr('method','post');
        //form.attr('enctype','multipart/form-data');
        form.submit(); */
        var orgType = $("#orgType").val();
        var name = $("#name").val();
        $("#export-org").val(orgType);
        $("#export-name").val(name);
        $("#exportFrm").submit();
	}
	//根据查询条件加载机构
	function searchOrganizationLists(){
		var orgType = $('#orgType').val();
		var name = $('#name').val();
		LoadOrganizationList();
		$('#organizationList').dataTable().fnDraw();
	}
	//清空机构查询条件
	function cleanOrganizationLists(){
		$('#orgType').val("");
		$('#name').val("");
		LoadOrganizationList();
		$('#organizationList').dataTable().fnDraw();
	}
	
	//向后台添加机构
	$('#add').on('click',function(){
		//校验当前用户输入的license数目和系统当前剩余的license的数目
		var validator = $('#addFrm').parsley();
		validator.validate();
        if(validator.isValid()){
        	var userLicense = $("#licenseCount").val();
    		var sysRemainder = $("#remainder").text();
    		if(Number(userLicense) > Number(sysRemainder)){
    			$('#openTips').modal('show');
    			$('#tips').html('<fmt:message key="tiles.views.admin.index.organization.add.tips"/>'); 
    			return;
    		}
        	//序列化表单的内容，并发送到后台
        	var postData = $("#addFrm").serialize();
        	$.ajax({
				"dataType": 'json',
		        "data": postData,
		        "type": "POST",
	            "url":ctx+"/admin/index/saveOrganization",
		        "success": function(result){
		        	if(result.type == 'danger'){
		        		$('#openTips').modal('show');
		    			$('#tips').html('<fmt:message key="tiles.views.admin.index.organization.add.tips"/>'); 
		    			return;
		        	}else{
		        		window.location.reload();
		        	}
		        } 
		   }); 
        	return true;
        }else{
        	return false;
        }
	});

	//关闭对话框	
	$('#cancelAdd').on('click',function(){
		$('#addModal').modal('hide');
	});
	
	$('#cancelUpdate').on('click',function(){
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
	
	$(function(){
		LoadOrganizationList();
	})
	
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