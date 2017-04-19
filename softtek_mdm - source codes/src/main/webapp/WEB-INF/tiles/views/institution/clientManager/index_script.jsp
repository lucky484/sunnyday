<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>	
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<spring:url value="/resources/js/datatables-1.10.11/lang/language.lang" var="dtLangUrl" />
<script>
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
      $("#release").click(function(){
    	  $("#clientform")[0].reset();
    	  $("#releaseClient").modal();
      });
 
      
  	function uploadClientOnchange(obj,fileShade){
	    var uploadFileName = $(obj).val();
	    file = obj.files;
	    var index = uploadFileName.lastIndexOf("\\");
	    if(index != -1){
	        var tempFileName = uploadFileName.substring(index+1,uploadFileName.length);
	        $("#"+fileShade).val(tempFileName);
	    }else{
	        $("#"+fileShade).val(uploadFileName);
	    }
	} 
	function hideOrgList(){
	    $("#orgListTr").hide();
	}
	
	function showOrgList(){
	    $("#orgListTr").show();
	}
	function closePopWin(){
		$("#releaseClient").modal('hide')
	}
	$("#clientinport").click(function(){
		var uri = ctx+"/institution/clientManager/uploadZip?_csrf=${_csrf.token}";
		var clientfile = $("#clientfile").val();
		var formData = new FormData($("#clientform")[0]);
		if("" == clientfile){
			$("#pleaseSelect").modal();
			return ;
		}
		if((clientfile.substr(clientfile.lastIndexOf(".")).toLowerCase() != ".apk") && (clientfile.substr(clientfile.lastIndexOf(".")).toLowerCase() != ".ipa")){
			$("#errormsg").modal();
	   		return;
		}
		 $.ajax({
			  "dataType":"json",
		      "data":formData,
		      "type": "POST",
		      "url":uri,
		      "contentType": false,
		      "processData": false,
		      "success": function(data) {
		    	 $("#clientId").val(data.clientManger.clientIdName);
		    	 $("#clientName").val(data.clientManger.clientAppName);
		    	 $("#clientVersion").val(data.clientManger.clientVersion);
		    	 $("#certOwner").val(data.clientManger.signatureCertificate);
		      }
		});
	});
	$("#orgList").click(function(){
		var _this = $(this);
		_this.find("input").each(function(i,item){
			if(item.checked == true){
				$("#depart-error").hide();
			}
		});
	});
	 $("#insert").click(function(){
		if($("#clientfile").val() == ""){
			$("#selectFile").modal();
			return;
		}
		if($("#clientfile").val() != "" && $("#clientId").val() == ""){
			$("#uploadFile").modal();
			return;
		}
		var str = "";
		var j=0;
		$(".chk-list").find("input").each(function(i,item){
			if(item.checked == true){
				j=i+1;
				str += $(this).val()+","
			}
		});
		if(j==0){
			$("#depart-error").show();
			return;
		}
		var orgId = str.substring(0,str.length-1);
		var url =  ctx+"/institution/clientManager/insertClientManager?_csrf=${_csrf.token}";
		var clientIdName = $("#clientId").val();
		var clientAppName = $("#fileShade").val();
		var clientVersion = $("#clientVersion").val();
		var clientDescription = $("#clientDescription").val();
		var signatureCertificate = $("#certOwner").val();
		var isUpgrade = 0;
    if($("#forceupdateAll").prop("checked") == true){
    	isUpgrade = 1;
    }
     var clientManger = {
    		 clientIdName:clientIdName,
    		 clientAppName:clientAppName,
    		 clientVersion:clientVersion,
    		 remark:clientDescription,
    		 signatureCertificate:signatureCertificate,
    		 isUpgrade:isUpgrade,
    		 belongOrg:orgId
     };
		var _this = $(this)
		_this.attr("disabled",true);
     $.post(url,clientManger,function(data){
    	 if(data.result == 1){
    		 $("#successMsg").modal();
    		 $("#success").click(function(){
    			 window.location.reload();
    		 });
    	 }
     },'json');
	});
	$('#clientManager').dataTable({
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
				"ajax" : {
					"url" : "clientManager/queryAll",
					"dataSrc" : "data",
					"type" : "get",
				},
				"oLanguage":{
					"sUrl":languageUrl
				},
				columns : [ {
					data : "clientIdName"
				}, {
					data : "clientAppName"
				}, {
					data : "belongOrg"
				}, {
					data : "clientVersion"
				}, {
					data : "clientByte"
				} , {
					data : "isUpgrade"
				}, {
					data : "applyPlatform"
				}, {
					data : "supportDevice"
				}],
				"columnDefs" : [
								{
									"className":"tb-w-max",
									"targets" : 2,
									"render" : function(data, type, row) {
										return '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+row.belongOrg+'"><span class="text-ellipsis"><td>'+row.belongOrg+'</td></span><div>'
									}
								},
								{
									"targets" : 5,
									"render" : function(data, type, row) {
										if(row.isUpgrade == 1){
											return '<fmt:message key="tiles.institution.client.manager.upgrade.yes" />';
										}else{
											return '<fmt:message key="tiles.institution.client.manager.upgrade.no" />';
										}
									}
								},
								{
									"targets" : 8,
									"render" : function(data, type, row) {
										if("${softtek_manager.id}" == row.createBy){
											return '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">'
											+ '<i class="i  i-settings"></i>'
											+ '</a>'
											+ '<ul class="dropdown-menu" style="margin-left:-41px;margin-top:-48px;min-width:108px;">'
											+ '<li><a href="javascript:void(0);" style="color:#6787B8;word-wrap:break-word;padding:5px;font-size:12px;margin-left:10px;" onclick="queryDetail('+ row.id + ')">'
											+ '<img src="../resources/images/Book.png" style="margin:-4px;">&nbsp;&nbsp;<fmt:message key="tiles.institution.policy.policy.detail" /></a></li>'
											+ '<li><a href="javascript:void(0);" style="color:#6787B8;word-wrap:break-word;padding:5px;font-size:12px;margin-left:10px;" onclick="deleteClient('+ row.id + ')">'
											+ '<img src="../resources/images/delete.png" style="margin:-4px;">&nbsp;&nbsp;<fmt:message key="tiles.views.institution.message.table.operation.delete" /></a></li>'
											+ '</ul>';
										}else{
											return '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">'
											+ '<i class="i  i-settings"></i>'
											+ '</a>'
											+ '<ul class="dropdown-menu" style="margin-left:-41px;margin-top:-48px;min-width:108px;">'
											+ '<li><a href="javascript:void(0);" style="color:#6787B8;word-wrap:break-word;padding:5px;font-size:12px;margin-left:10px;" onclick="queryDetail('+ row.id + ')">'
											+ '<img src="../resources/images/Book.png" style="margin:-4px;">&nbsp;&nbsp;<fmt:message key="tiles.institution.policy.policy.detail" /></a></li>'
											+ '</ul>';
										}
									}
								}]
			});
	 function queryDetail(id){
		 $("#queryDetail").modal();
		var csrf="${_csrf.token}";
	     $.post("clientManager/queryClientInfoById",{id:id,_csrf:csrf},function(data){
			 $("#QRcode").html('<img src="'+data.clientManager.imageUrl+'" />');
			 $("#clientVersionDetail").html(data.clientManager.clientVersion);
			 $("#certOwnerDetail").html(data.clientManager.signatureCertificate);
			 $("#clientUrl").find("a").html(data.clientManager.fileName + "(" + data.clientManager.clientVersion +")");
			 $("#thirdHttpUrl").html(data.clientManager.downloadUrl);
			 $("#clientUrl").find("a").attr("href",data.clientManager.downloadUrl);
			 $("#ostypeNameDetail").html(data.clientManager.applyPlatform);
			 $("#uploadTime").html(data.clientManager.createDateStr);
			 $("#clientIdDetail").html(data.clientManager.clientIdName);
			 $("#clientNameDetail").html(data.clientManager.clientAppName);
			 $("#clientDescriptionDetail").html(data.clientManager.remark);
			 if(data.clientManager.isUpgrade == 1){
				$("#forceupdateAllDetail").html('<fmt:message key="tiles.institution.client.manager.upgrade.yes" />'); 
			 }else{
				 $("#forceupdateAllDetail").html('<fmt:message key="tiles.institution.client.manager.upgrade.no" />'); 
			 }
			 $("#orgName").html(data.clientManager.belongOrg);
			 $("#isTablet").html(data.clientManager.supportDevice);
		 },'json'); 
	 }
/* 	 function select_org(obj){
		 var _this = $(obj)
		 if(_this.is(":checked")){
			 $(".chk-list").find("input").prop("checked",false);
		 }
		 _this.prop("checked",true);
	 } */
	 function deleteClient(id){
		 $("#deleteMsg").modal();
		 $("#clientId").val(id);
	 }
	 $("#delete").click(function(){
		 var clientId = $("#clientId").val();
		 var csrf="${_csrf.token}";
		 $.post("clientManager/deleteClient",{id:clientId,_csrf:csrf},function(data){
			 if(data.result == 1){
				 window.location.reload();
			 }
		 },'json');
	 });
	 $("#message").click(function(){
		 $("#pleaseSelect").modal('hide');
	 });
</script>