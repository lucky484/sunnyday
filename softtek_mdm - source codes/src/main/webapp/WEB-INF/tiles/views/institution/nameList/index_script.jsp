<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
  <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
  <spring:url value="/resources/js/datatables-1.10.11/js/jquery.dataTables.js" var="dataTableJs" />
  <script src="${dataTableJs}"></script>
  <spring:url value="/resources/js/datatables-1.10.11/js/dataTables.bootstrap.js" var="dataTableBootstrapJs" />
  <script src="${dataTableBootstrapJs}"></script>
  <spring:url value="/resources/js/jquery.tmpl.js" var="jqueryTmplJs" />
  <script src="${jqueryTmplJs}"></script>
  <spring:url value="/institution/nameList/exists" var="existsUrl" />
  <spring:url value="/resources/js/datatables-1.10.11/lang/language.lang" var="dtLangUrl" />
  <script type="text/javascript">
      // 应用列表数组
	  var appArray = [];
 	  var languageUrl;
	  var lang = "${dtLangUrl}";
	  var str = lang.substring(lang.lastIndexOf("/")+1,lang.lastIndexOf("."));
	  var str1 = lang.substring(0,lang.lastIndexOf("/"));
	  var str2 = lang.substring(lang.lastIndexOf("."),lang.length);
	  languageUrl = str1 + "/" + str + "_" + navigator.language + str2;
	  $(function(){
		   loadNameList();
	  });
  function loadNameList(){
	  var name = $("#name").val();
	  var type = $("#type").val();
   //加载策略列表数据
   var dataTable = $('#appNameList').dataTable({
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
  					"url" : ctx+"/institution/nameList/queryAll?now="+ new Date().getTime(),
  					"data" : {"name":name,"type":type}
  				},
  				"columnDefs" : [
   			   {
					"targets" : [0],
					"render" : function(data, type, row) {
						if(row.listType==1){
							return '<fmt:message key="tiles.views.institution.namelist.indexscript.blackname"/>';
						} else {
							return '<fmt:message key="tiles.views.institution.namelist.indexscript.whitename"/>';
						}
					}
				},
				{
					className: "col-lg-2 datatb-max-width-namelist",
					"targets" : [1],
					"render" : function(data, type, row) {
						var rowname = "";
						rowname = '<a href="javascript:void(0)"  title="'+row.listName+'" class="text-primary" onclick="findApplication('+row.id+')">';
						rowname+= row.listName+'</a>';
						return '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+row.listName+'"><span class="text-ellipsis">'+rowname+'</span><div>';
					}
				},
				{
					className: "col-lg-2 datatb-max-width-namelistcount",
					"targets" : [2],
					"render" : function(data, type, row) {
						return row.appCount;
					}
				},
				{
					className: "col-lg-2 datatb-max-width-namelist",
					"targets" : [3],
					"render" : function(data, type, row) {
						var str = "";
						var remark = row.remark;
						if(remark!=null&&remark!=""){
							if(remark.length>35){
								remark = remark.substring(0,35)+"...";
							}
							str = "<a title='"+row.remark+"'>"+remark+"</a>";
						}
						if(remark==null){
							str = "";
						}
						return '<div title="'+row.remark+'"><span class="text-ellipsis">'+str+'</span><div>';
					}
				},
				{
					"targets" : [4],
					"render" : function(data, type, row) {
						var isenable=0;
						if("${softtek_manager.user}"!=""&&"${softtek_manager.id}"!=row.createUser){
							isenable=1;
						} 
						if("${softtek_manager.auth}"=="0"&&"${softtek_manager.user}"!=""){
							var btns = '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">';
							btns += '<i class="i  i-settings"></i>';
							btns += '</a>';
							btns += '<ul class="dropdown-menu" style="margin-left:-100px;margin-top:-70px;">';
							btns += ' <li><a href="javascript:void(0);" onclick="findApplication('+row.id+')"><i class="fa fa-eye"></i>&nbsp;<fmt:message key="general.jsp.find.label"/></a></li></ul>';
							
							return btns;
						}
						var btns = '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">';
						btns += '<i class="i  i-settings"></i>';
						btns += '</a>';
						btns += '<ul class="dropdown-menu" style="margin-left:-100px;margin-top:-70px;">';
						btns += ' <li><a href="javascript:void(0);" onclick="findApplication('+row.id+')"><i class="fa fa-eye"></i>&nbsp;<fmt:message key="general.jsp.find.label"/></a></li>';
						btns += ' <li><a href="javascript:void(0);" onclick="editApplication('+row.id+','+isenable+')"><i class="fa fa-pencil"></i>&nbsp;<fmt:message key="general.jsp.modify.label"/></a></li>';
						btns += '<li><a href="javascript:void(0);" onclick="deleteNameList('+row.id+','+isenable+')"><i class="i i-trashcan text-danger"></i><span class="text-danger">&nbsp;<fmt:message key="general.jsp.delete.label"/></span></a></li>'
						btns += '</ul>';
						return btns;
					}
				}]
  			});
	    }
  
  //查询名单列表
  function searchPolicy(){
	  loadNameList();
	  $('#appNameList').dataTable().fnDraw();
  }
  
  //清除名单列表
  function cleanPolicy(){
	   $("#name").val("");
	   $("#type").val("");
	   $(".Js_curVal").find("input").val('<fmt:message key="tiles.views.institution.namelist.index.alltype"/>');
	   loadNameList();
	   $('#appNameList').dataTable().fnDraw();
  }
  
  // 名单删除提示功能
  function _deleteNameList(id){
	  $("#delId").val(id);
	  $("#NameListMessage").html('<fmt:message key="tiles.views.institution.namelist.indexscript.delmessage"/>');
	  $("#nameListClick").attr("onclick","delNameList()");
	 $("#delNameListModal").modal(open);	
   }
  
  
  // 删除名单
  function deleteNameList(id,isenable){
	 if(isenable==1){
		 Modal.confirm().on(function(e){if(e==true){_deleteNameList(id);}});
	 } else {
		 _deleteNameList(id);
	 }
  }
  
   //删除名单
   function delNameList(){
	 var id = $("#delId").val();
	 var csrf="${_csrf.token}";
	 $.ajax({
	    "dataType" : 'json',
        "type": "POST",
        "url":ctx+"/institution/nameList/delNameList?now="+ new Date().getTime(),
        "data": {"id":id,_csrf:csrf},
        "success": function(data){
         	$(".notify").notify({type:data.type,message: { html: false, text: data.message}}).show();
      	    $("#delNameListModal").modal("hide");
        	  loadNameList();
         }	
	  });
	}
	
   // 新增应用名单
   function addAppNameList(){
	   	$.ajax({
			"dataType" : 'json',
	        "type": "GET",
	        "url":ctx+"/institution/device/policy/addToken?now="+ new Date().getTime(),
	        "success": function(data){
	     	   initData();
	           exists();
	           $("#tokenId").val(data);
		   	   	var btn = '';
		   		btn += '<a href="#" onclick="saveApplication()" class="btn btn-success"><fmt:message key="general.jsp.save.label"/></a>';
		   	    btn += '<a href="#" class="btn" data-dismiss="modal"><fmt:message key="general.jsp.cancel.label"/></a>';
		   	    $("#nameTitle").html();
		   		$("#saveBtn").html(btn);
		    	$("#addModal").modal("show");
	        }	
	   });

   }
   
   // 选择应用
   function chooseApp(){
	  var type = $("#appType").val();
	  $("#addModal").modal("hide");
	  $("#chooseAppList").modal("show");
	  var dataTable = $('#loadAppList').dataTable({
		  		    "dom":"<'m-r m-t-lg pull-right'f>t<'row '<'col-lg-2'l>r<'col-lg-3'i><'pull-right'p>>",
	  				"autoWidth": true,
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
	  					"url" : ctx+"/institution/nameList/loadAppList?now="+ new Date().getTime(),
	  					"data" : {"type":type}
	  				},
	  				"columnDefs" : [
	   			    {
						"targets" : [0],
						"render" : function(data, type, row) {
							var html = "";
							html += '<input type="checkbox" id="chooseAppId" name="chooseAppId" value='+row.id+' />';
							return html;
						}
					},
	   			    {
						"targets" : [1],
						"render" : function(data, type, row) {
							return '<input type="hidden" id="chooseAppIds'+row.id+'" value=\''+row.appId+'\'>'+row.appId;
						}
					},
					{
						"targets" : [2],
						"render" : function(data, type, row) {
							return '<input type="hidden" id="chooseAppNames'+row.id+'" value=\''+row.appName+'\'>'+row.appName
						}
					}]
	  	   });
    }
   
   // 关闭选择应用
   function closeApp(){
	   $("#chooseAppList").modal("hide");
	   $("#addModal").modal("show");
   }
   
   // 确认选择应用
   function confirmChooseApp(){
	   $("input[name='chooseAppId']:checked").each(function(){
		   var id = $(this).val();
		   var appId = $("#chooseAppIds"+id).val();
		   var appName = $("#chooseAppNames"+id).val();
		   var appType = $("#appType").val();
		   if (checkIsExist(appArray, appId)) {
		       $(".notify").notify({type:'warning',message: { html: false, text: '<fmt:message key="tiles.views.institution.namelist.indexscript.addappmessage"/>'}}).show(); 
			   return false;
		   }
		   appArray.push({appId : appId, appName : appName, appType : appType});
		   displayApplication(appArray);
	   });
	   $("#chooseAppList").modal("hide");
	   $("#addModal").modal("show");
   }
   
   // 新增应用
   function addApp() {
	   var validator = $("#saveAppListForm").parsley();
	   validator.validate();
       if(validator.isValid()!=null&&!validator.isValid()) {
          return false; 
       }
	   var appId = $("#appId").val();
	   var appName = $("#appName").val();
	   var appType = $("#appType").val();
	   if (checkIsExist(appArray, appId)) {
	       $(".notify").notify({type:'warning',message: { html: false, text: '<fmt:message key="tiles.views.institution.namelist.indexscript.addappmessage"/>'}}).show(); 
		   return false;
	   }
	   appArray.push({appId : appId, appName : appName, appType : appType});
	   displayApplication(appArray);
    }
    
    // 判断是否在数组里存在
 	function checkIsExist(array, temp) {
	    if (null != array && array.length > 0) {
			for (var i = 0; i < array.length; i++) {
				var obj = array[i];
				if (obj.appId == temp) {
					return true;
				}
			}
		}
		return false;
	}
   
   // 编辑应用
   function findApplication(id){
	   	$.ajax({
			"dataType" : 'json',
	        "type": "GET",
	        "url":ctx+"/institution/nameList/editApplication?now="+ new Date().getTime(),
	        "data": {"id":id},
	        "success": function(data){
	        	initData();
	        	$("#id").val(data.result.id);
	            exists();
	        	$("#listName").val(data.result.listName);
	        	$("#listName").prop("disabled", true); 
	    	    $("#nameTitle").html('<fmt:message key="tiles.views.institution.namelist.indexscript.find.title"/>');
	        	$("#remark").val(data.result.remark);
	        	$("#remark").prop("disabled", true); 
	        	$("#nameType option[value='"+data.result.listType+"']").prop("selected",true);
	        	$("#nameType").prop("disabled", true); 
	        	displayDisableApplication(data.result.appList);
	        	getAppListToArray(data.result.appList);
	            $("#addModal").modal("show");
	            $("#addAppAndroid").html("");
	    	   	var btn = '';
	    	    btn += '<a href="#" class="btn" data-dismiss="modal"><fmt:message key="general.jsp.cancel.label"/></a>';
	    		$("#saveBtn").html(btn);
	        }
	    });
   }
   
   // 添加andorid应用显示
   function addAppAndroid(){
       var html='<div class="col-lg-12">';
        html +='<div class="col-lg-2 paddingWidth"></div>';
        html+='<div class="col-lg-10 paddingWidth">';
        html+='<div class="col-lg-3 appIdMargin">';
        html+='<div class="col-lg-11 appIdMargin">';
        html+='<input type="text" id="appId" placeholder="<fmt:message key="tiles.views.institution.namelist.indexscript.appid"/>" data-parsley-required="true" data-parsley-trigger="blur" class="form-control m-b inputNoWidth" data-parsley-appidnumber/>';
        html+='</div>';
        html+='<div class="col-lg-1 comma">、</div>';
        html +='</div>';
        html +='<div class="col-lg-3 appNameMargin">';
        html +='<div class="col-lg-11 appIdMargin">';
        html +='<input type="text" id="appName" placeholder="<fmt:message key="tiles.views.institution.namelist.indexscript.appname"/>" data-parsley-required="true" data-parsley-trigger="blur" class="form-control m-b inputNoWidth"/>';
        html +='</div>';
        html +='<div class="col-lg-1 comma">、</div>';
        html +='</div>'; 
        html +='<div class="col-lg-3 appNameMargin"><select id="appType" class="form-control m-b">';  
        html +='<option value="1"><fmt:message key="tiles.views.institution.namelist.indexscript.androidapplication"/></option>';
        html +='<option value="0"><fmt:message key="tiles.views.institution.namelist.indexscript.iosapplication"/></option>';
        html += '</select></div>';
        html +='<div class="col-lg-3 btnPaddingLeft">';
        html +='<div class="btn btn-sm btn-success" onclick="addApp()"><fmt:message key="general.jsp.insert.label"/></div>&nbsp;';      
        html +='<div class="btn btn-sm btn-success" onclick="chooseApp()"  style="margin-left: -3px;"><fmt:message key="general.jsp.choose.label"/></div>&nbsp;';
        html +='<div class="btn btn-sm btn-success" onclick="openExcelModal()"  style="margin-left: -2px;"  ><fmt:message key="jsp.import.title"/></div></div>';
        
/*         html += '<a href="javascript:void(0);" class="btn btn-sm btn-default btn-rounded "onclick="openExcelModal()" id="importUser"> <i class="fa fa-download"></i><fmt:message key="jsp.import.title"/></a>';
  */       html += '</div>';
        html += '</div>'; 
        $("#addAppAndroid").html(html);
   }
   
   function editApplication(id,isenable){
	 if(isenable==1){
			Modal.confirm().on(function(e){if(e==true){_editApplication(id);}});
		 } else {
			_editApplication(id);
		 }
   }
   
   // 编辑应用
   function _editApplication(id){
	   	$.ajax({
			"dataType" : 'json',
	        "type": "GET",
	        "url":ctx+"/institution/nameList/editApplication?now="+ new Date().getTime(),
	        "data": {"id":id},
	        "success": function(data){
	        	initData();
	        	$("#id").val(data.result.id);
	            exists();
	        	$("#listName").val(data.result.listName);
	        	$("#nameType").prop("disabled", true);
	    	    $("#nameTitle").html('<fmt:message key="tiles.views.institution.namelist.indexscript.editnamelist"/>');
	        	$("#remark").val(data.result.remark);
	        	$("#tokenId").val(data.token);
	        	$("#nameType option[value='"+data.result.listType+"']").prop("selected",true);
	        	// 展示应用
	        	displayApplication(data.result.appList);
	        	getAppListToArray(data.result.appList);
	            $("#addModal").modal("show");
	    	   	var btn = '';
	    		btn += '<a href="#" onclick="saveApplication()" class="btn btn-success"><fmt:message key="general.jsp.save.label"/></a>';
	    	    btn += '<a href="#" class="btn" data-dismiss="modal"><fmt:message key="general.jsp.cancel.label"/></a>';
	    		$("#saveBtn").html(btn);
	          	$("#listName").prop("autofocus","autofocus");
	        }
	    });
   }
   
   // 将修改应用或者展示应用时返回的appList数据push到appArray中
   function getAppListToArray(appList) {
		if (null != appList && appList.length > 0) {
			for (var i = 0; i < appList.length; i++) {
				var obj = appList[i]; 
				appArray.push({appId : obj.appId, appName : obj.appName, appType : obj.appType});
			}
		}
   }
   
   // 初始化参数
   function initData(){
    $("#id").val("");
	$("#listName").val("");
	$("#remark").val("");
	$("#appstr").val("");
	$("#appIds").val("");
	$("#appId").val("");
	$("#appName").val("");
	addAppAndroid();
	$("#passwordLength nameType").prop("selected", 'selected');
    $("#appDiv").html("");
 	// 重置名称验证信息
    $(".parsley-remote").html("");
    $(".parsley-required").html("");
    $("#listName").removeClass("parsley-error");
    $("#listName").removeClass("parsley-success");
	$("#remark").removeAttr("disabled"); 
	$("#listName").removeAttr("disabled"); 
	$("#nameType").removeAttr("disabled"); 
  	$("#listName").removeAttr("autofocus");
  	appArray = [];
  	$("#nameTitle").html('<fmt:message key="tiles.views.institution.namelist.indexscript.title"/>');
   }
   
    // 将数组转换为json格式
	function parseToJsonStr(arr) {
		if (null != arr && arr.length > 0) {
			return JSON.stringify(arr).toString();
		}
		return "";
	}
   
   // 删除应用
   function deleteApplication(id){
	   var arr = [];
	   if (null != appArray && appArray.length > 0) {
		   for (var i = 0; i < appArray.length; i++){
				var obj = appArray[i];
				if (obj.appId != id) {
					arr.push(obj);
				}
			}
		}
		appArray = arr;
		displayApplication(appArray);
   }   
   // 展示应用
   function displayApplication(appArray){
	   var html ='';
	   if(appArray != null && appArray.length > 0) { 
		   for (var i = 0; i < appArray.length; i++) {
			   var obj = appArray[i];
			   var appId = obj.appId;
			   var name = obj.appName;
			   html +='<div class="col-lg-12 paddingWidth marginWidth appBody">';
			   html +='<div class="col-lg-4" paddingWidth marginWidth">';
 			   if(appId.length>12) {
				   html += appId.substring(0,12)+"...";
			   } else {
				   html += appId;
			   }
			   html += '</div>';
			   html +='<div class="col-lg-4 paddingWidth marginWidth" >';
 			   if(name!=null&&name.length>6) {
				   html += name.substring(0,6)+"...";
			   } else {
				   html += name;
			   } 
			   html += '</div>';
			   html +='<div class="col-lg-3 paddingWidth marginWidth">';
			   if(obj.appType != null && obj.appType !='' && obj.appType == 1){
				   html += '<fmt:message key="tiles.views.institution.namelist.indexscript.androidapplication"/>';
			   } else {
				   html += '<fmt:message key="tiles.views.institution.namelist.indexscript.iosapplication"/>';
			   }
			   html += '</div>';
			   html +='<div class="col-lg-1 paddingWidth marginWidth" onclick="deleteApplication(\''+obj.appId+'\')">';
			   html += '<i class="glyphicon glyphicon-remove limargin"></i>';
			   html += '</div>';
			   html += '</div>';
		   } 
	   }
	   $("#appDiv").html(html);
	   $("#appType option:first").prop("selected", 'selected');
	   $("#appId").val("");
	   $("#appName").val("");
	   $("#appId").removeClass("parsley-error");
       $("#appId").removeClass("parsley-success");
	   $("#appName").removeClass("parsley-error");
       $("#appName").removeClass("parsley-success");
   }
   
   // 展示应用
   function displayDisableApplication(appArray){
	   var html ='';
	   if(appArray != null && appArray.length > 0) { 
		   for (var i = 0; i < appArray.length; i++) {
			   var obj = appArray[i];
			   html +='<div class="col-lg-12 paddingWidth appBody">';
			   html +='<div class="col-lg-4 paddingWidth">';
			   html += obj.appId;
			   html += '</div>';
			   html +='<div class="col-lg-4 paddingWidth">';
			   html += obj.appName;
			   html += '</div>';
			   html +='<div class="col-lg-3">';
			   if(obj.appType != null && obj.appType !='' && obj.appType == 1){
				   html += '<fmt:message key="tiles.views.institution.namelist.indexscript.androidapplication"/>';
			   } else {
				   html += '<fmt:message key="tiles.views.institution.namelist.indexscript.iosapplication"/>';
			   }
			   html += '</div>';
			   html +='<div class="col-lg-1" onclick="deleteApplication(\''+obj.appId+'\')">';
			   html += '</div>';
			   html += '</div>';
		   } 
	   }
	   $("#appDiv").html(html);
   }
   
	//导入导出操作 start
	function openExcelModal() {
		$("#excelModal").modal();
		$("#file").val("");
		$(".showFileName").val("");
		$(".fileerrorTip").html("");
	}
	
	function exportUserModel() {
		$("#getusermodel").submit();
	}
	
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
	
	//导入用户
	function importApps() {
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
					url : ctx + "/institution/nameList/importApps",
					cache : false,
					headers : {
						"${_csrf.headerName}" : "${_csrf.token}",
					},
					contentType : false,
					processData : false,
					success : function(result) {
						$('#loadingModal').modal("hide");
					    var data = $.parseJSON(result);
                       		if(data.type=="success"){
								var list = $.parseJSON(data.result);
					    		$.each(list, function(i,val){    
					    			if(appArray.length > 1000) {
				    			        $(".notify").notify({type:'warning',message: { html: false, text: '<fmt:message key="tiles.views.institution.namelist.indexscript.import.limit"/>'}}).show(); 
				    				    return false;
					    			}
	 			    			    if (!checkIsExist(appArray, appId)) { 
	 			    			    	appArray.push({appId : val.appId, appName : val.appName, appType : val.appType});;
				    			    } 
					        	}); 
								displayApplication(appArray);
								$('#successModal').modal(open);
		                        $("#successModal").find("h1").html('<fmt:message key="tiles.views.user.index.script.user.excel.import.success"/>');
		                        $('#rownumbers').addClass('hidden');
		                        $('#rownumbers').html(data.success);
	                        } else{
	    						$('#erroModal').modal(open);
	                            $('#rownumbers2').removeClass('hidden');
	                            $("#erroModal").find("h1").html('<fmt:message key="tiles.views.user.index.script.user.excel.import.failed"/>');
	                            $('#rownumbers2').html(data.message);
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
   
   // 保存黑白名单
   function saveApplication(){
	 var validator = $("#saveNameListForm").parsley();
	 validator.validate();
	 if(validator.isValid()!=null&&!validator.isValid()){
	     return false;
	 }
	   var id = $("#id").val();
	   var appstr = $("#appstr").val();
	   var name = $("#listName").val();
	   var remark = $("#remark").val();
	   var nameType = $("#nameType").val();
	   var postData = {
		 params : {}
	   };
	   postData.params.id = id;
	   postData.params.appstr = parseToJsonStr(appArray);
	   postData.params.listName = name;
	   postData.params.nameType = nameType;
	   postData.params.remark = remark;
	   var token = $("#tokenId").val();
	   postData.token = token;
	   // 判断id是否为空?为空新增 不为空修改
	   var url = "";
	   var csrf = "${_csrf.token}";
	   if(id!=null&&id!=""){
		   url = ctx+"/institution/nameList/updateNameList?_csrf="+csrf;
	   } else {
		   url = ctx+"/institution/nameList/saveNameList?_csrf="+csrf;
	   }
	   $.ajax({
			"dataType" : 'json',
	        "data": postData,
	        "type": "POST",
	         "url":url,
	        "success": function(data){
	        	$(".notify").notify({type:data.type,message: { html: false, text: data.message}}).show();
	        	if(data.type=="success"){
	        		$("#addModal").modal("hide");
	        		window.location.href = ctx+"/institution/nameList";
	    	     }
	           	return false;
	         } 
	     }); 
	   
   }

   exists();
   // 判断策略名称是否存在
   function exists(){
  	var id = $("#id").val();
	$("#listName").parsley().addAsyncValidator('existsValidate',function(xhr){
		return (xhr.responseText.indexOf('success') >= 0); 
	},"${existsUrl}", { "type": "GET", "dataType": "json", "contentType": "application/json; charset=utf-8", "data": {"id":id} } );
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