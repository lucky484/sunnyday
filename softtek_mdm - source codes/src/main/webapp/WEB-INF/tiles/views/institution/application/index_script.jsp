<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.softtek.mdm.util.CommUtil"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
  <spring:url value="/resources/js/datatables-1.10.11/js/jquery.dataTables.js" var="dataTableJs" />
  <script src="${dataTableJs}"></script>
  <spring:url value="/resources/js/datatables-1.10.11/js/dataTables.bootstrap.js" var="dataTableBootstrapJs" />
  <spring:url value="/resources/js/datatables-1.10.11/lang/language.lang" var="dtLangUrl" />
  <script src="${dataTableBootstrapJs}"></script>
  <spring:url value="/institution/application/searchAppList" var="searchApp" />
  <script type="text/javascript">
	  //=============================== datatables国际化
	  var languageUrl;
		  var lang = "${dtLangUrl}";
		  var str = lang.substring(lang.lastIndexOf("/")+1,lang.lastIndexOf("."));
		  var str1 = lang.substring(0,lang.lastIndexOf("/"));
		  var str2 = lang.substring(lang.lastIndexOf("."),lang.length);
		  var nlang=navigator.language;
		  if(nlang.toLowerCase().indexOf("zh")>=0){
			 languageUrl = str1 + "/" + str + "_zh-CN" + str2;
		  } else {
			 languageUrl = str1 + "/" + str + "_en-US" + str2;
		  }
	var imgCtx = '<%=CommUtil.DISPLAY_ICON_Path%>';
	if("${softtek_manager.auth}"=="0"&&"${softtek_manager.user}"!=""){
	    	$("#fabu").html("<button disabled class='btn-sm btn-green'><fmt:message key='general.jsp.publish.label'/></button>")
	} 
    loadApplicationManager();
    loadApplicationDistribute();
    
    // 保存ios app应用
    function saveIosApp(){
 	   var validator = $("#saveIosApp").parsley();
	   validator.validate();
       if(validator.isValid()){
    	   var releaseType = $("#releaseType1").val();
    	   var iosAppId = $("#iosAppId").val();
    	   var iosIconPath = $("#iosIconPath").attr("src");
    	   var iosAppName = $("#iosAppName").val();
    	   var iosAppVersion = $("#iosAppVersion").val();
    	   var iosAuthorName = $("#iosAuthorName").val();
    	   var iosAppDescription = $("#iosAppDescription").val();
    	   var iosFileSizeBytes = $("#iosFileSizeBytes").val();
     	   var iosTrackId = $("#iosTrackId").val();
    	   var iosFormattedPrice = $("#iosFormattedPrice").val(); 
		   var postData = {
		       params : {}
		   };
		   postData.params.releaseType = releaseType;
		   postData.params.iosAppId = iosAppId;
		   postData.params.iosIconPath = iosIconPath;
		   postData.params.iosAppName = iosAppName;
		   postData.params.iosAppVersion = iosAppVersion;
		   postData.params.iosAuthorName = iosAuthorName;
		   postData.params.iosAppDescription = iosAppDescription;
		   postData.params.iosFileSizeBytes = iosFileSizeBytes;
 		   postData.params.iosTrackId = iosTrackId;
		   postData.params.iosFormattedPrice = iosFormattedPrice; 
		   var token = $("#tokenIosId").val();
		   postData.token = token;
		   var csrf = "${_csrf.token}";
 		   var url = ctx+"/institution/application/saveIosApp?_csrf="+csrf;
			$.ajax({
				"dataType" : 'json',
			    "data": postData,
			    "type": "POST",
			    "url":url,
		        "success": function(data){
			       	 $(".notify").notify({type:data.type,message: { html: false, text: data.message}}).show(); 
			         if(data.type=="success"){
			   	        window.location.href = ctx +"/institution/application";
			   	     }
			         return false;
		        } 
			});
			return true;
        } else {
        	return false;
        }
    }
    
    // 保存android策略
	function save(){
	   var validator = $("#saveApp").parsley();
	   validator.validate();
       if(validator.isValid()){
			var releaseType = $("#releaseType1").val();
			var apkFile = $("#apkFile").val();
			var appId = $("#appId1").val();
			var appName = $("#appName1").val();
			var signatureCertificate = $("#signatureCertificate1").val();
			var appVersion = $("#appVersion1").val();
			var author = $("#authorName").val();
		    var appDescription = $("#appDescription").val();
		    var minimumVersion = $("#minimumVersion").val();
		    var icon = $("#icon").val();
			var postData = {
			    params : {}
			};
			postData.params.releaseType = releaseType;
			postData.params.appName = appName;
			postData.params.signatureCertificate = signatureCertificate;
			postData.params.appVersion = appVersion;
			postData.params.author = author;
			postData.params.appDescription = appDescription;
			postData.params.minimumVersion = minimumVersion;
			postData.params.minimumVersion = minimumVersion;
			postData.params.apkFile = apkFile;
			postData.params.appId = appId;
			postData.params.icon = icon;
			var token = $("#tokenId").val();
			postData.token = token;
			var csrf = "${_csrf.token}";
			var url = ctx+"/institution/application/save?_csrf="+csrf;
			$.ajax({
				"dataType" : 'json',
			       "data": postData,
			       "type": "POST",
			         "url":url,
			       "success": function(data){
			       	  $(".notify").notify({type:data.type,message: { html: false, text: data.message}}).show(); 
			          if(data.type=="success"){
			   	        window.location.href = ctx +"/institution/application";
			   	      }
			          return false;
			       } 
			});
        } else {
        	return false;
        }
	} 

	exists();
	// 判断策略名称是否存在
	function exists(){
		var id = $("#id").val();
		$("#appId1").parsley().addAsyncValidator('existsValidate',function(xhr){
			return (xhr.responseText.indexOf('success') >= 0); 
		},"${existsUrl}", { "type": "GET", "dataType": "json", "contentType": "application/json; charset=utf-8", "data": {"id":id} } );
    }

 // 上传图片
 $("input[type=file][name=file]").change(function(){
	var that=this;
	var id = $("#pulishVersionId").val();
	var fileuploadUrl = "";
	var tag = that.id;
	if("file2"==tag){
		fileuploadUrl = ctx+"/institution/application/upload?_csrf=${_csrf.token}&id="+id;
	} else {
		fileuploadUrl = ctx+"/institution/application/upload?_csrf=${_csrf.token}";
	}
	var files=this.files;
	$.each(files, function(index, file) {
		var name = file.name;
		//存放大于100M上传失败的
		if(file.size > 100*1024*1024){
			alert('<fmt:message key="tiles.views.institution.sapplication.indexscript.fileupload.failed"/>');
			return false;
		}
		ext = name.substring(name.lastIndexOf("."));
		if((ext.toLowerCase() != ".apk") && (ext.toLowerCase() != ".ipa")){
	        $(".notify").notify({type:"warning",message: { html: false, text: '<fmt:message key="tiles.views.institution.application.indexscript.upload.file"/>'}}).show(); 
	        return false;
		}
		var data = new FormData();
		data.append("file", file);
		  $.ajax({
			  "dataType":"json",
		      "data":data,
		      "type": "POST",
		      "url":fileuploadUrl,
		      "cache": false,
		      "contentType": false,
		      "processData": false,
		      "success": function(data) {
 		        	$(".notify").notify({type:data.type,message: { html: false, text: data.message}}).show(); 
 		        	if("file2"==tag){
 		        		 if(data.type="success"){
 		        			$("#pulishVersionId").val(data.result.id);
 		        			$("#apkFile2").val(data.result.apkFile);
 		        			$("#icon2").val(data.result.icon);
 		        			$("#iconPath2").attr("src",data.result.iconPath);
 		        			$("#appVersion2").val(data.result.appVersion);
 		        			$("#appName2").val(data.result.appName);
 		        		 }
 		        	} else {
	 		        	if(data.type=="success"){
			        		$("#appId1").val(data.result.appId);
			        		$("#appName1").val(data.result.appName);
			        		$("#appVersion1").val(data.result.appVersion);
			        		$("#apkFile").val(data.result.apkFile);
			        		$("#icon").val(data.result.icon);
	 		        		$("#iconPath").attr("src",data.result.iconPath);
			        		$("#signatureCertificate1").val(data.result.signatureCertificate);
			        		var html = '<select id="minimumVersion" class="form-control m-b" style="background-color:white;" required>';
			        		html += '<option value=""><fmt:message key="tiles.views.institution.application.indexmodal.chooseversion"/></option>';
			        		html += '<option value="0">Android 1.5</option>';
			        		html += '<option value="1">Android 1.6</option>';
			        		html += '<option value="2">Android 2.0</option>';
			        		html += '<option value="3">Android 2.1</option>';
			        		html += '<option value="4">Android 2.2</option>';
			        		html += '<option value="5">Android 2.3</option>';
			        		html += '<option value="6">Android 3.0</option>';
			        		html += '<option value="7">Android 3.1</option>';
			        		html += '<option value="8">Android 3.2</option>';
			        		html += '<option value="9">Android 4.0</option>';
			        		html += '<option value="10">Android 4.0.3</option>';
			        		html += '<option value="11">Android 4.1</option>';
			        		html += '<option value="12">Android 4.2</option>';
			        		html += '</select>';
			        		$("#minimumVersionDesc").html(html);
			        	} else {
			        		return false;
			        	}
 		        	}
		        },
		        error: function(XMLHttpRequest, textStatus, errorThrown) {
		        	debugger;
		        },
		    });
	});
});
 
 // 初始化tab2的的内容
 function initAppPublish(){
	 $("#releaseType1 option:first").prop("selected", 'selected');
	 $("#apkFile").val("");
	 $("#file1").val("");
	 $("#appId1").val("");
	 $("#icon").val("");
	 $("#iconPath").attr("src","");
	 $("#appName1").val("");
	 $("#signatureCertificate1").val("");
	 $("#appVersion1").val("");
	 $("#authorName").val("");
	 $("#appDescription").val("");
	 var html = "";
	 html += '<select id="minimumVersion" class="form-control m-b" readonly="readonly" style="background-color:white;" required>';
	 html += '<option value=""><fmt:message key="tiles.views.institution.application.indexmodal.chooseversion"/></option>';
	 html += '</select>';
	 $("#minimumVersionDesc").html(html);
	 
 }
 
// 加载应用管理数据
function loadApplicationManager(){
	var releaseType = $("#releaseType").val();
	var appName = $("#appName").val();
	var appState = $("#appState").val();
    $('#applicationManager').dataTable().fnDestroy();
//加载策略列表数据
var dataTable = $('#applicationManager').dataTable({
	   			"dom":"<'m-r m-t-lg pull-right'f>t<'row '<'col-lg-2'l>r<'col-lg-3'i><'pull-right'p>>",
	   		    "autoWidth": false,
	    		"searching" : false,
				"stateSave" : true,
				"ordering" : false,
				"bSort" : false,
				"serverSide" : true,
				"pageLength" : 10,
				"pagingType" : "full_numbers",
				"bDestroy" : true,
				"oLanguage": {
					"sUrl":languageUrl
			    },
				"ajax" : {
					"dataType" : 'json',
					"type" : "get",
					"url" : ctx+"/institution/application/queryAll?now="+ new Date().getTime(),
					"data" : {"releaseType":releaseType,"appName":appName,"appState":appState}
				},
				"columnDefs" : [
			   {
					"targets" : [0],
					"render" : function(data, type, row) {
						var path = '';
						if(row.releaseType=="0"){
							path = imgCtx + "application/" + row.iconPath;
						} else if(row.releaseType=="1"){
							path = row.iconPath;
						}
						var appHtml = '<div class="left_align"><img src="'+path+'" width="40px" height="40px"/>&nbsp;&nbsp;';
						    appHtml += '<a style="color:#6787B8" href="javascript:findApplication(\''+row.id+'\')">'+row.appName+'</a></div>';
						return appHtml;
					}
				},
				{
					"targets" : [1],
					"render" : function(data, type, row) {
						if(row.releaseType=="0"){
							return "Android"+'<fmt:message key="tiles.views.institution.application.indexscript.appid"/>';
						} else if(row.releaseType=="1"){
							return "iOS"+'<fmt:message key="tiles.views.institution.application.indexscript.appid"/>';
						}
						return "";
					}
				},
				{
					"targets" : [2],
					"render" : function(data, type, row) {
						return row.appId;
					}
				},
				{
					"targets" : [3],
					"render" : function(data, type, row) {
						return row.appVersion;
					}
				},
				{
					"targets" : [4],
					"render" : function(data, type, row) {
						var state = row.state;
						if(state==1){
						    return '<fmt:message key="tiles.views.institution.application.indexscript.onshelves"/>';
						} else {
							return '<fmt:message key="tiles.views.institution.application.indexscript.offshelves"/>';
						}

					}
				},{
					"targets" : [5],
					"render" : function(data, type, row) {
						if("${softtek_manager.auth}"=="0"){
							var btns = '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">';
							btns += '<i class="i  i-settings"></i>';
							btns += '</a>';
							btns += '<ul class="dropdown-menu" style="margin-left:-100px;margin-top:-70px;">';
							btns += '<li><a href="javascript:void(0)" onclick="findApplication('+row.id+')"><i class="fa fa-eye"></i>&nbsp;<fmt:message key="general.jsp.find.label"/></a></li></ul>';
							return btns;
						}
						
						if("${softtek_manager.user}"!=""&&"${softtek_manager.id}"!=row.createBy){
							return "无操作权限";
						}
						var btns = '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">';
						btns += '<i class="i  i-settings"></i>';
						btns += '</a>';
						btns += '<ul class="dropdown-menu" style="margin-left:-100px;margin-top:-70px;">';
						if(row.state!=1){
						    btns += '<li><a href="javascript:void(0)" onclick="openNewVersion('+row.id+')"><i class="fa fa-plus"></i>&nbsp;<fmt:message key="general.jsp.push.version.label"/></a></li>';
						}
						btns += '<li><a href="javascript:void(0)" onclick="findApplication('+row.id+')"><i class="fa fa-eye"></i>&nbsp;<fmt:message key="general.jsp.find.label"/></a></li>';
						btns += ' <li><a href="javascript:void(0);" onclick="editApplication('+row.id+')"><i class="fa fa-pencil"></i>&nbsp;<fmt:message key="general.jsp.modify.label"/></a></li>';
						if(row.state==1){
							btns += ' <li><a href="javascript:void(0);" onclick="onApplication('+row.id+',0,\''+row.appName+'\')"><i class="fa fa-lock"></i>&nbsp;<fmt:message key="general.jsp.offself.label"/></a></li>';
						} else {
							btns += ' <li><a href="javascript:void(0);" onclick="onApplication('+row.id+',1,\''+row.appName+'\')"><i class="fa fa-unlock"></i>&nbsp;<fmt:message key="general.jsp.onself.label"/></a></li>';
							btns += '<li><a href="javascript:void(0);" onclick="deleteApplication('+row.id+',\''+row.appName+'\','+row.appCount+')"><i class="i i-trashcan text-danger"></i><span class="text-danger">&nbsp;<fmt:message key="general.jsp.delete.label"/></span></a></li>'
						}
						btns += '</ul>';
						return btns;

					}
				}]
			});
	    }

// 加载应用分发数据
function loadApplicationDistribute(){
	var releaseType = $("#authReleaseType").val();
	var appName = $("#authAppName").val();
	$('#applicationDistribute').dataTable().fnDestroy();
	//加载策略列表数据
	var dataTable = $('#applicationDistribute').dataTable({
		   			"dom":"<'m-r m-t-lg pull-right'f>t<'row '<'col-lg-2'l>r<'col-lg-3'i><'pull-right'p>>",
		   		    "autoWidth": false,
		    		"searching" : false,
					"stateSave" : true,
					"ordering" : false,
					"bSort" : false,
					"serverSide" : true,
					"pageLength" : 10,
					"pagingType" : "full_numbers",
					"bDestroy" : true,
					"oLanguage": {
						"sUrl":languageUrl
				    },
					"ajax" : {
						"dataType" : 'json',
						"type" : "get",
						"url" : ctx+"/institution/application/queryApplicationAll?now="+ new Date().getTime(),
						"data" : {"releaseType":releaseType,"appName":appName,"appState":""}
					},
					"columnDefs" : [
				   {
						"targets" : [0],
						"render" : function(data, type, row) {
							return row.appName;
						}
					},
					{
						"targets" : [1],
						"render" : function(data, type, row) {
							if(row.releaseType=="0"){
								return "Android"+'<fmt:message key="tiles.views.institution.application.indexscript.appid"/>';
							} else if(row.releaseType=="1"){
								return "iOS"+'<fmt:message key="tiles.views.institution.application.indexscript.appid"/>';
							}
							return "";
						}
					},
					{
						"targets" : [2],
						"render" : function(data, type, row) {
							return row.appVersion;
						}
					},
					{
						"targets" : [3],
						"render" : function(data, type, row) {
						    return row.appCount;
						}
					},
					{
						"targets" : [4],
						"render" : function(data, type, row) {
							if(row.notAuthCount==null||row.notAuthCount==''){
								return 0;
							}
							return row.notAuthCount;
						}
					},{
						"targets" : [5],
						"render" : function(data, type, row) {
							var btns = '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">';
							btns += '<i class="i  i-settings"></i>';
							btns += '</a>';
							btns += '<ul class="dropdown-menu" style="margin-left:-100px;margin-top:-70px;">';
							btns += '<li><a href="javascript:void(0)" onclick="findDevice('+row.id+','+row.appCount+')"><i class="fa fa-eye"></i>&nbsp;<fmt:message key="tiles.views.institution.application.indexscript.find.device"/></a></li>';
							btns += '</ul>';
							return btns;
						}
					}]
				});
		    }
	    
	    // 删除应用
	    function deleteApplication(id,name,appCount){
	       if(appCount != 0){
	    	   $(".notify").notify({type:data.type,message: { html: false, text: '<fmt:message key="tiles.views.institution.application.index.delete.app"/>'}}).show();
	           return false;
	       }
	       $.ajax({
				"dataType" : 'json',
	            "type": "GET",
	            "url":ctx+"/institution/application/delete?now="+ new Date().getTime(),
	            "data": {"id":id,"appName":"name"},
	            "success": function(data){
	            	$(".notify").notify({type:data.type,message: { html: false, text: data.message}}).show();
	            	if(data.type="success"){
		            	window.location.href = ctx + "/institution/application?now="+ new Date().getTime();
	            	} else {
	            		return false;
	            	}
	            }	
	       });
	    }
	    
	    // 启用禁用应用
	   function onApplication(id,tag,name){
	       var url = "";
	       if(tag=="0"){
	    	   url = ctx+"/institution/application/changeOffState?now="+ new Date().getTime();
	       } else {
	    	   url = ctx+"/institution/application/changeOnState?now="+ new Date().getTime();
	       }
	       $.ajax({
				"dataType" : 'json',
	            "type": "GET",
	            "url":url,
	            "data": {"id":id,"tag":tag,"appName":name},
	            "success": function(data){
	            	$(".notify").notify({type:data.type,message: { html: false, text: data.message}}).show();
	            	if(data.type="success"){
		            	/* loadApplicationManager(); */
		            	window.location.href = ctx + "/institution/application";
	            	} else {
		            	return false;
	            	}
	            }	
	       });
	   }
	    
	   // 查看应用页面
	   function findApplication(id){
	       $.ajax({
				"dataType" : 'json',
	            "type": "GET",
	            "url":ctx+"/institution/application/edit?now="+ new Date().getTime(),
	            "data": {"id":id},
	            "success": function(data){
	            	$("#findAppName").html(data.appName);
	            	$("#findIcon").attr("src",data.iconPath);
                    $("#findApkFile").attr("href",data.apkFile);
                    $("#findAppVersion").html(data.appVersion);
                    $("#findAppId").html(data.appId);
                    var releaseType = data.releaseType;
                    if(releaseType=="0"){
                    	$("#findType").html("Android<fmt:message key='tiles.views.institution.application.indexscript.app'/>");
	                    var version = data.minimumVersion;
	                    var minimunVersion = "";
	                    if(version=="0"){
	                    	minimunVersion = 'Android 1.5';
	                    } else if(version=="1"){
	                    	minimunVersion = 'Android 1.6';
	                    }  else if(version=="2"){
	                    	minimunVersion = 'Android 2.0';
	                    }  else if(version=="3"){
	                    	minimunVersion = 'Android 2.1';
	                    }  else if(version=="4"){
	                    	minimunVersion = 'Android 2.2';
	                    }  else if(version=="5"){
	                    	minimunVersion = 'Android 2.3';
	                    }  else if(version=="6"){
	                    	minimunVersion = 'Android 3.0';
	                    }  else if(version=="7"){
	                    	minimunVersion = 'Android 3.1';
	                    }  else if(version=="8"){
	                    	minimunVersion = 'Android 3.2';
	                    }  else if(version=="9"){
	                    	minimunVersion = 'Android 4.0';
	                    }  else if(version=="10"){
	                    	minimunVersion = 'Android 4.0.3';
	                    }  else if(version=="11"){
	                    	minimunVersion = 'Android 4.1';
	                    }  else if(version=="12"){
	                    	minimunVersion = 'Android 4.2';
	                    } 
                    } else if(releaseType=="1"){
                    	$("#findType").html("iOS<fmt:message key='tiles.views.institution.application.indexscript.app'/>");
                    	minimunVersion = "<fmt:message key='tiles.views.institution.application.index.none'/>";
                    }
                    $("#findMinimumVersion").html(minimunVersion);
                    $("#findAppDescription").html(data.appDescription);
	            	$("#findApplication").modal("show");
	            }	
	       });
	   }
	    
	   // 展示编辑应用页面
	   function editApplication(id){
	       $.ajax({
				"dataType" : 'json',
	            "type": "GET",
	            "url":ctx+"/institution/application/edit?now="+ new Date().getTime(),
	            "data": {"id":id},
	            "success": function(data){
	            	$("#editAppName").html(data.appName);
	            	$("#id").val(data.id);
	            	$("#editIcon").attr("src",data.iconPath);
	            	$("#editAppDescription").val(data.appDescription);
	            	$("#editApplication").modal("show");
	            }	
	       });
	   }
	   
	   // 编辑保存功能
	   function editSaveApplication(){
		   var validator = $("#editAppForm").parsley();
		   validator.validate();
	       if(validator.isValid()){
			var id = $("#id").val();
	        var appName = $("#editAppName").html();
	           var appDescription = $("#editAppDescription").val();
			var postData = {
			    params : {}
			};
			postData.params.id = id;
			postData.params.appName=appName;
			postData.params.appDescription = appDescription;
			var csrf = "${_csrf.token}";
			var url = ctx+"/institution/application/update?_csrf="+csrf;
			$.ajax({
				"dataType" : 'json',
			    "data": postData,
			    "type": "POST",
			    "url":url,
			    "success": function(data){
			        $(".notify").notify({type:data.type,message: { html: false, text: data.message}}).show(); 
			       	if(data.type=="success"){
			       		loadApplicationManager();
			       		/* $("#editApplication").modal("hide"); */
			       		window.location.href = ctx + "/institution/application";
			       		
			   	    } 
			        return false;
			     } 
			});
	       }
	   }
	   
   // 关闭弹出框
   function closeApplication(){
	  $("#editApplication").modal("hide");
   }
   // 关闭查看应用弹出框
   function closeFindApp(){
	   $("#findApplication").modal("hide");
   }
	
   // 当点击app取出部门以及虚拟组
   function onclickApp(id,name){
	   var url = ctx+"/institution/application/loadDepart";
		$.ajax({
			"dataType" : 'json',
		    "data": {"id":id},
		    "type": "GET",
		    "url":url,
		    "success": function(data){
		    	$("#aId").val(id);
		  	    $("#aName").val(name);
		    	initAppData(data);
		     } 
		});
    }

    // 初始加载数据
    function initAppData(data){
    	var departHtml = "";
    	// 未授权部门
		$.each(data.departList, function(i,val){       
			departHtml += '<div class="col-sm-12 padding">';
			departHtml += '<div class="col-sm-4 border_top1 divHeight" title="'+val.name+'"><span class="text-ellipsis">'+val.name+'</span></div>';
			if(val.isInstall==1){
				departHtml += '<div class="col-sm-4 border_top divHeight"><fmt:message key="tiles.views.institution.application.indexscript.allowinstall"/></div>';
			} else {
				departHtml += '<div class="col-sm-4 border_top divHeight"><fmt:message key="tiles.views.institution.application.indexscript.disableinstall"/></div>';
			}
			departHtml += '<div class="col-sm-4 border_top divHeight cursor button_color" onclick="cancelGrantAuth('+val.id+',0,\''+val.name+'\')"><fmt:message key="tiles.views.institution.application.indexscript.cancelgrandauth"/></div>';
			departHtml += '</div>';
    	}); 
    	$("#departAuth").html(departHtml);
    	
		var virtualHtml = "";
    	// 未授权虚拟组
		$.each(data.virtualList, function(i,val){       
			virtualHtml += '<div class="col-sm-12 padding">';
			virtualHtml += '<div class="col-sm-4 border_top1 divHeight" title="'+val.name+'"><span class="text-ellipsis">'+val.name+'</span></div>';
			if(val.isInstall==1){
				virtualHtml += '<div class="col-sm-4 border_top divHeight"><fmt:message key="tiles.views.institution.application.indexscript.allowinstall"/></div>';
			} else {
				virtualHtml += '<div class="col-sm-4 border_top divHeight"><fmt:message key="tiles.views.institution.application.indexscript.disableinstall"/></div>';
			}
			virtualHtml += '<div class="col-sm-4 border_top divHeight cursor button_color" onclick="cancelGrantAuth('+val.id+',1,\''+val.name+'\')"><fmt:message key="tiles.views.institution.application.indexscript.cancelgrandauth"/></div>';
			virtualHtml += '</div>';
    	}); 
		$("#virtualAuth").html(virtualHtml);
		
    	var authVirtualHtml = '';
		// 已授权虚拟组
		// 虚拟集合
		$.each(data.firstVirtualList, function(i,val){
			authVirtualHtml += '<ul class="list_type" style="width:100%;">';
			authVirtualHtml += '<span id="arrow'+val.fatherId+'" class="fa fa-chevron-right cursor" onclick="ulClick('+val.fatherId+')">&nbsp;&nbsp;'+val.fatherName+'</span>';
			authVirtualHtml += '<div id="virUl'+val.fatherId+'" class="diplay_off">';
			// 虚拟组
    		$.each(val.list, function(i,second){  
    			authVirtualHtml += '<li style="width:100%;">&nbsp;&nbsp;<input type="checkbox" name="virtualIds" value="'+second.id+'" />&nbsp;&nbsp;';
    			authVirtualHtml += second.name;
    			authVirtualHtml += '</li>';
        	}); 
    		authVirtualHtml += '</div>';
    		authVirtualHtml += '</ul>';
    	}); 
		$("#notAuthVirtualGourp").html(authVirtualHtml);
		//生成树形结构
		$('#tree1').treeview({
			color : "#428bca",
			showBorder : false,
			showCheckbox : true,
			multiSelect : true,
			highlightSelected:false,
		    state: {
			    checked: true,
			    disabled: true,
			    expanded: true,
			    selected: true
			},
			data : data.jsonStr
		});
		
		$('#tree1').treeview('collapseAll', { silent: true }); 
		// 选中部门节点
		$('#tree1').on('nodeChecked', function(event, data) {
		  var nodeId = data.tags.id;
			selectNodeLoop(data,1);
		});
		// 不选中部门节点
		$('#tree1').on('nodeUnchecked', function(event, data) {
			var nodeId = data.tags.id;
			selectNodeLoop(data,0);
		});
    }
   
	//递归选择部门节点
	function selectNodeLoop(data,flag){
		var tempNodes=data.nodes;
		if(tempNodes!=undefined){
			for(var i=0;i < tempNodes.length;i++){
				if(flag==1){
					if(tempNodes[i].state.disabled != true) {
						$('#tree1').treeview('checkNode', [ tempNodes[i].nodeId, { silent: true } ]);
					}
				} else{
					if(tempNodes[i].state.disabled != true) {
						$('#tree1').treeview('uncheckNode', [ tempNodes[i].nodeId, { silent: true } ]);
					}
				}
				selectNodeLoop(tempNodes[i],flag);
			}
		}
		return ;
	}
   
   // 展开或收起
   function ulClick(id){
	 var className = $("#virUl"+id).attr("class");
	 if(className=="diplay_off"){
		 $("#virUl"+id).attr("class","diplay_on");
		 $("#arrow"+id).attr("class","list_type fa fa-chevron-down cursor");
	 } else {
		 $("#virUl"+id).attr("class","diplay_off");
		 $("#arrow"+id).attr("class","list_type fa fa-chevron-right cursor");
	 }
   }
   // 弹出应用级授权弹出框
   // 0:部门  1.虚拟组
   function grandAppAuth(tag){
	  var aId = $("#aId").val();
	  $("#aTag").val(tag);
	  if(aId==null||aId==""){
 			  $(".notify").notify({type:"warning",message: { html: false, text:'<fmt:message key="tiles.views.institution.application.indexscript.chooseapp"/>'}}).show(); 
		  return false;
	  }
	  $("#grandAppAuth").modal("show");
   }
   // 关闭应用级授权弹出框
   function closeGrantAuth(){
	  $("#grandAppAuth").modal("hide");
   }

   // 保存应用授权
   // 0.保存部门  1.保存虚拟组
   function saveGrantAppAuth(){
	   var aTag = $("#aTag").val();
	   var aId = $("#aId").val();
	   var aName = $("#aName").val();
	   var type = "";
	   $('input[name="install"]:checked').each(function(){ 
		   var tempVal = $(this).val();
		   if(tempVal=="on"){
			   type = "1";
		   } else {
			   type = "0";
		   }
	   }); 
       var url = "";
	   if("0"==aTag){
			var departIds="";
			var nodeChecked=$('#tree1').treeview('getChecked');
			if(nodeChecked.length>0){
				for(var i=0;i<nodeChecked.length;i++){
					departIds=nodeChecked[i].tags.id+","+departIds;
				}
				departIds=departIds.substr(0,departIds.length-1);
			}

	   } else {
		   var virIds = "";
		   $('input[name="virtualIds"]:checked').each(function(){ 
			   var tempVal = $(this).val();
			   virIds += tempVal+",";
		   }); 
		   if(virIds!=""){
			   virIds = virIds.substring(0,virIds.length-1);
		   }
	   }
	   var postData = {
			    params : {}
			};
			postData.params.id = aId;
			postData.params.appName = aName;
			postData.params.tag=aTag;
			postData.params.type = type;
			postData.params.departIds=departIds;
			postData.params.virIds=virIds;
			var csrf = "${_csrf.token}";
		    url = ctx+"/institution/application/grantAppAuth?_csrf="+csrf;
			$.ajax({
				"dataType" : 'json',
			    "data": postData,
			    "type": "POST",
			    "url":url,
			    "success": function(data){
			        $(".notify").notify({type:data.type,message: { html: false, text: data.message}}).show(); 
			       	if(data.type=="success"){
			       		$("#grandAppAuth").modal("hide");
			       		initAppData(data);
			   	    } 
			        return false;
			     } 
			});
       }
   
   // 取消授权
   function cancelGrantAuth(id,tag,name){
	   var aId = $("#aId").val();
	    var url = ctx+"/institution/application/cancelGrantAuth";
		$.ajax({
			"dataType" : 'json',
		    "data": {"id":aId,"secondId":id,"tag":tag,"appName":name},
		    "type": "GET",
		    "url":url,
		    "success": function(data){
		    	$(".notify").notify({type:data.type,message: { html: false, text: data.message}}).show(); 
		       	if(data.type=="success"){
		       		initAppData(data);
		   	    } 
		        return false;
		     } 
		});
   }
   
   // 检索应用
   function searchAppliction(){
	  var value = $("#searchApp").val();
	    var url = ctx+"/institution/application/searchAppliction";
		$.ajax({
			"dataType" : 'json',
		    "data": {"value":value},
		    "type": "GET",
		    "url":url,
		    "success": function(data){
	    		var appHtml = "";
		    	$.each(data, function(index, app) {
		    		appHtml += '<li class="cursor" onclick="onclickApp('+app.id+',\''+app.appName+'\')"><img src="'+ctx+iconPath+'"/>&nbsp;'+app.appName;
		    		appHtml += '(';
		    		if(app.releaseType==0){
		    			appHtml += 'Android <fmt:message key="tiles.views.institution.application.indexmodal.application"/>';
		    		}
		    		appHtml += ')';
		    		appHtml += '</li>';
		    	});
	    		$("#appUl").html(appHtml);
		     } 
		});
   }
   // 查看设备
   function findDevice(id,count){
	 if(count==0){
		 $(".notify").notify({type:"warning",message: { html: false, text: '<fmt:message key="tiles.views.institution.application.indexscript.nodevice"/>'}}).show(); 
		 return false;
	 }
	 $('#loadDeviceTable').dataTable().fnDestroy();
	   //加载策略列表数据
	   var dataTable = $('#loadDeviceTable').dataTable({
	  	   			"dom":"<'m-r m-t-lg pull-right'f>t<'row '<'col-lg-3'l>r<'col-lg-3'i><'pull-right'p>>",
	  	   		    "autoWidth": false,
	  	    		"searching" : false,
	  				"stateSave" : true,
	  				"ordering" : false,
	  				"bSort" : false,
	  				"serverSide" : true,
	  				"pageLength" : 10,
	  				"pagingType" : "full_numbers",
	  				"bDestroy" : true,
	  				"oLanguage": {
	  					"sUrl":languageUrl
	  			    },
	  				"ajax" : {
	  					"dataType" : 'json',
	  					"type" : "get",
	  					"url" : ctx+"/institution/application/queryDevice?now="+ new Date().getTime(),
	  					"data" : {"id":id}
	  				},
	  				"columnDefs" : [
	  			   {
	  					"targets" : [0],
	  					"render" : function(data, type, row) {
	  						return row.real_name;
	  					}
	  				},
	  			   {
	  					"targets" : [1],
	  					"render" : function(data, type, row) {
	  						return row.device_name;
	  					}
	  				},
	  				{
	  					"targets" : [2],
	  					"render" : function(data, type, row) {
	  						return row.device_type;
	  					}
	  				},
	  				{
	  					"targets" : [3],
	  					"render" : function(data, type, row) {
	  						return row.sn;
	  					}
	  				},
	  				{
	  					"targets" : [4],
	  					"render" : function(data, type, row) {
	  						return row.esnorimei;
	  					}
	  				}]
	  		  });  
	  $("#loadDeviceInfo").modal("show");  
      loadTableTd("#loadDeviceInfo",5);
   }
   // 关闭设备信息弹出框
   function closeDevice(){
	   $("#loadDeviceInfo").modal("hide");
   }
   
   // 查看应用列表数据 	
   function searchApplication(){
	   $('#applicationManager').dataTable().fnDestroy();
	   loadApplicationManager();
	   $('#applicationManager').dataTable().fnDraw();
   }
   
   // 查看应用列表数据 	
   function searchAuthApplication(){
	   $('#applicationDistribute').dataTable().fnDestroy();
	   loadApplicationDistribute();
	   $('#applicationDistribute').dataTable().fnDraw();
   }
   
   // 清楚查询应用列表数据	
   function cleanApplication(){
	   $('#applicationManager').dataTable().fnDestroy();
	   $("#appName").val("");
	   $("#releaseType").val('');
	   $("#appState").val('');
	   $("#Js_curVal").find("input").val('<fmt:message key="tiles.views.institution.namelist.index.alltype"/>');
	   $("#Js_curVal1").find("input").val('<fmt:message key="tiles.views.institution.application.index.allstate"/>');
	   loadApplicationManager();
	   $('#applicationManager').dataTable().fnDraw();
   }
   
   // 清楚查询应用列表数据	
   function cleanAuthApplication(){
	   $('#applicationDistribute').dataTable().fnDestroy();
	   $("#authAppName ").val("");
	   $("#authReleaseType").val('');
	   $("#Js_curVal2").find("input").val('<fmt:message key="tiles.views.institution.namelist.index.alltype"/>');
	   loadApplicationDistribute();
	   $('#applicationDistribute').dataTable().fnDraw();
   }
   
   // 打开新版本
   function openNewVersion(id){
	   $("#pulishVersionId").val(id);
	   $("#publishNewVersion").modal("show");
	   $("#file2").val("");
	   $("#iconPath2").attr("src",ctx+'/resources/images/app/upload.png');
	   $("#appVersion2").val("");
   }
   
   // 关闭新版本
   function closeNewVersion(){
	   $("#publishNewVersion").modal("hide");
   }
   
   // 发布版本
   function saveNewVersion(){
	   var validator = $("#publishVersionForm").parsley();
	   validator.validate();
       if(validator.isValid()){
		    var id = $("#pulishVersionId").val();
			var apkFile = $("#apkFile2").val();
			var appVersion = $("#appVersion2").val();
		    var icon = $("#icon2").val();
		    var appName = $("#appName2").val();
			var postData = {
			    params : {}
			};
			postData.params.id = id;
			postData.params.appName = appName;
			postData.params.apkFile = apkFile;
			postData.params.appVersion = appVersion;
			postData.params.icon = icon;
			var csrf = "${_csrf.token}";
			var url = ctx+"/institution/application/publish?_csrf="+csrf;
			$.ajax({
				"dataType" : 'json',
			       "data": postData,
			       "type": "POST",
			         "url":url,
			       "success": function(data){
			       	  $(".notify").notify({type:data.type,message: { html: false, text: data.message}}).show(); 
			       	if(data.type=="success"){
			   	        window.location.href = ctx +"/institution/application";
			   	     }
			         return false;
			      } 
			});
        }
	} 
   
   // 发布类型点击事件
   function onSelectClick(){
	   var releaseType1 = $("#releaseType1").val();
	   if(releaseType1==0){
		   $("#app1").attr("class","display_on");
		   $("#app2").attr("class","display_off");
	   } else {
		   $("#app1").attr("class","display_off");
		   $("#app2").attr("class","display_on")
	   }
   }
   
   // 检索appstore应用列表
   function searchAppList(){
	   var value = $("#appKey").val();
	   if(value==null||value==""){
		   return false;
	   }
	   $.ajax({
			"dataType" : 'json',
			"data":{"keywords":value},
		    "type": "Get",
		    "url":'${searchApp}',
		    "success": function(data){
		    	var html = '';
		    	if(data!=null&&data!=""){
	 	    		$.each(data, function(i,val){
	 	    			html += '<div id="appElement'+i+'" name="appstoreApp" class="col-sm-6 appstore" onclick="appClick('+i+',\''+val.bundleId+'\',\''+val.trackName+'\',\''+val.artworkUrl100+'\',\''+val.primaryGenreName+'\',\''+val.price+'\',\''+val.artistName+'\',\''+val.fileSizeBytes+'\',\''+val.version+'\',\''+val.trackId+'\',\''+val.formattedPrice+'\')">';
	 	    			html += '<div class="col-sm-4">';
		    			html += '<img src="'+val.artworkUrl100+'">';
		    			html += '</div>';
		    			html += '<div class="col-sm-8">';
		    			html += '<span>'+val.displayTrackName+'</span><br/>';
		    			html += '<fmt:message key="tiles.views.institution.application.indexmodal.appstore.supportedDevices"/>:iPhone,iPad<br/>'
		    			html += '<fmt:message key="tiles.views.institution.application.indexmodal.appstore.primaryGenreName"/>:'+val.primaryGenreName+'<br/>';
		    			html += '<fmt:message key="tiles.views.institution.application.indexmodal.appstore.price"/>:'+val.formattedPrice;
		    			html += '<input type="hidden" id="hiddenDesc'+i+'" value="'+val.description+'"/>';
		    			html += '</div>'; 
		    			html += '</div>';
		        	});
		    		$("#showAppKeywords").html(html);
		    		$("#appListInAppstore").modal("show");
		    	} else {
			    	$(".notify").notify({type:'warning',message: { html: false, text: '<fmt:message key="web.institution.application.data.failed"/>'}}).show(); 
		    	}
		    } 
	   });
   }
   
   // 关闭新版本
   function closeAppListInAppstore(){
	   $("#appListInAppstore").modal("hide");
   }
   
   // 选择app
   function chooseApp(){
	   var bundleId = $("#hiddenBundleId").val();
	   var trackName = $("#hiddenTrackName").val();
	   var artworkUrl100 = $("#hiddenArtworkUrl100").val();
	   var primaryGenreName = $("#hiddenPrimaryGenreName").val();
	   var price = $("#hiddenPrice").val();
	   var artistName = $("#hiddenArtistName").val();
	   var fileSizeBytes = $("#hiddenFileSizeBytes").val();
	   var version = $("#hiddenVersion").val();
	   var description = $("#hiddenDescription").val();
	   var trackId = $("#hiddenTrackId").val();
	   var formattedPrice = $("#hiddenFormattedPrice").val();
	   $("#hiddenFormattedPrice").val(formattedPrice);
	   $("#iosAppId").val(bundleId);
	   $("#iosIconPath").attr("src",artworkUrl100);
	   $("#iosAppName").val(trackName);
	   $("#iosAuthorName").val(artistName);
	   $("#iosFileSizeBytes").val(fileSizeBytes);
	   $("#iosAppVersion").val(version);
	   $("#iosAppDescription").val(description);
	   $("#iosTrackId").val(trackId);
	   $("#iosFormattedPrice").val(formattedPrice);
	   $("#appListInAppstore").modal("hide");
   }
   
   // APP点击事件
   function appClick(i,bundleId,trackName,artworkUrl100,primaryGenreName,price,artistName,fileSizeBytes,version,trackId,formattedPrice){
	   $("#hiddenBundleId").val(bundleId);
	   $("#hiddenTrackName").val(trackName);
	   $("#hiddenArtworkUrl100").val(artworkUrl100);
	   $("#hiddenPrimaryGenreName").val(primaryGenreName);
	   $("#hiddenPrice").val(price);
	   $("#hiddenArtistName").val(artistName);
	   $("#hiddenFileSizeBytes").val(fileSizeBytes);
	   $("#hiddenVersion").val(version);
	   $("#hiddenTrackId").val(trackId);
	   $("#hiddenFormattedPrice").val(formattedPrice);
	   $("#hiddenDescription").val($("#hiddenDesc"+i).val());
	   $("div[name=appstoreApp]").attr("class","col-sm-6 appstore");
	   $("#appElement"+i).attr("class","col-sm-6 appstore chooose_app_on");
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
			} else {
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
		function updateCss(){
		   if($("#applicationDistribute").find("tbody").find("tr:eq(0)").find("td").hasClass("dataTables_empty")){
		    	$("#applicationDistribute").find("tbody").find("tr:eq(0)").find("td").attr("colspan",6);
		    }
		}
</script>