<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<%@ page import="com.softtek.mdm.util.CommUtil"%>
  <spring:url value="/resources/js/autocomplete/jquery-ui.min.js" var="jqueryUiJs"/>
  <script src="${jqueryUiJs}"></script>
  <spring:url value="/resources/js/clockpicker/clockpicker.js" var="clockpickerJs" />
  <script src="${clockpickerJs}"></script>
  <spring:url value="/resources/js/datatables-1.10.11/js/jquery.dataTables.js" var="dataTableJs" />
  <script src="${dataTableJs}"></script>
  <spring:url value="/resources/js/datatables-1.10.11/js/dataTables.bootstrap.js" var="dataTableBootstrapJs" />
  <script src="${dataTableBootstrapJs}"></script>
  <spring:url value="/resources/js/jquery.tmpl.js" var="jqueryTmplJs" />
  <script src="${jqueryTmplJs}"></script>
  <spring:url value="/institution/device/policy/exists" var="existsUrl" />
  <script src="${existsUrl}"></script>
  <spring:url value="/institution/device/policy/iosExists" var="iosExistsUrl" />
  <script src="${iosExistsUrl}"></script>
  <spring:url value="/resources/js/carousel/lanrenzhijia.js" var="lanrenzhijia" />
  <script src="${lanrenzhijia}"></script>
  <spring:url value="/resources/js/datatables-1.10.11/lang/language.lang" var="dtLangUrl" />
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
	  
	  var saveAndroidPolicyValidator = $("#savePolicyForm").parsley();
	  var saveIosPolicyValidator = $("#saveIosPolicyForm").parsley();
   	  var idArray = [];
      var nameArray = [];
      var allowUrlArray = [];
      var notAllowUrlArray = [];
      var urlArray = [];
      var languageUrl;
      $(function() {
	  	loadDevicePolicy();
      	$(document).tooltip();
      });
  
  	
   //加载Android策略
   function loadDevicePolicy(){
 	   $('#devicePolicy').dataTable().fnDestroy();
 	  var name = $("#devicePolicyName").val();
 	  var policytype = $("#policytype").val();
    //加载策略列表数据
    var dataTable = $('#devicePolicy').dataTable({
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
   					"url" : ctx+"/institution/device/policy/queryAll?now="+ new Date().getTime(),
   					"data" : {"name":name,
   							  "policytype" : policytype
   							 }
   				},
   				"columnDefs" : [

 				{
 					className: "col-lg-2 datatb-max-width-devicepolicyname",
 					"targets" : [0],
 					"render" : function(data, type, row,meta) {
	 						var rowname = "";
	 						rowname = '<a href="javascript:void(0)" title="'+row.name+'" class="text-primary" onclick="findPolicy('+row.id+',0,'+row.type+')">';
	 						rowname+= row.name+'</a>';
	 						return '<span class="text-ellipsis">'+rowname+'</span>';
 					}
 				},
 				{
 					className: "col-lg-2 datatb-max-width-devicepolicymark",
 					"targets" : [1],
 					"render" : function(data, type, row) {
 						var description = row.description;
 						if(description==null){
 							description = "";
 						}
 						return '<div title="'+description+'"><span class="text-ellipsis">'+description+'</span><div>';
 					}
 				},
 				{
 					"targets" : [2],
 					"render" : function(data, type, row) {
 						var type = row.type;
 						if(type==0){
 							type = "Android";
 						} else {
 							type = "iOS";
 						}
 						return type;
 					}
 				},
 				{
 					"targets" : [3],
 					"render" : function(data, type, row) {
 						if(row.assignedCount<1){
 							 return "<span id='findDevice'>"+row.assignedCount+"</span>";
 						}
 					    return "<span id='findDevice'><a href='javascript:loadAssignedDevice("+row.id+","+row.type+")'>"+row.assignedCount+"</a></span>";
 					}
 				},
 				{
 					"targets" : [4],
 					"render" : function(data, type, row) {
 						var date = row.updateDate;
 						if(date!=null&&date!=""){
 							if(date.indexOf(".")>0){
 								date = date.substring(0,date.length-2);
 							}
 						}
 						return date;
 					}
 			   },
 			   {
 					"targets" : [5],
 					"render" : function(data, type, row) {
 						if(row.isEnable==1){
 							return '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.enablestate"/>';
 						} else {
 							return '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.disablestate"/>';
 						}
 					}
 				},
 				{
 					"targets" : [6],
 					"render" : function(data, type, row) {
 						if("${softtek_manager.auth}"=="0"&&"${softtek_manager.user}"!=""){
	 						var findBtns = '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">';
	 						findBtns += '<i class="i  i-settings"></i>';
	 						findBtns += '</a>';
	 						findBtns += '<ul class="dropdown-menu" style="margin-left:-100px;margin-top:-70px;">';
	 						findBtns += '<li><a href="javascript:void(0)" onclick="findPolicy('+row.id+','+isenable+','+row.type+')"><i class="fa fa-eye"></i>&nbsp;<fmt:message key="general.jsp.find.label"/></a></li>';
	 						findBtns += '';
	 						
	 						findBtns += '</ul>';
	 						return findBtns;
						}
						var isenable=0;
  						if("${softtek_manager.user}"!=""&&"${softtek_manager.id}"!=row.createBy){
  							isenable=1;
 						} 
 						var btns = '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">';
 						btns += '<i class="i  i-settings"></i>';
 						btns += '</a>';
 						btns += '<ul class="dropdown-menu" style="margin-left:-100px;margin-top:-70px;">';
 						btns += '<li><a href="javascript:void(0)" onclick="findPolicy('+row.id+','+isenable+','+row.type+')"><i class="fa fa-eye"></i>&nbsp;<fmt:message key="general.jsp.find.label"/></a></li>';
 						btns += ' <li><a href="javascript:void(0);" onclick="editPolicy('+row.id+','+isenable+','+row.type+')"><i class="fa fa-pencil"></i>&nbsp;<fmt:message key="general.jsp.modify.label"/></a></li>';
 						if(row.isEnable==1){
 							btns += ' <li><a href="javascript:void(0);" onclick="enablePolicy('+row.id+',0,'+isenable+','+row.type+')"><i class="fa fa-lock text-warning"></i>&nbsp;<fmt:message key="general.jsp.disable.label"/></a></li>';
 						} else {
 							btns += ' <li><a href="javascript:void(0);" onclick="enablePolicy('+row.id+',1,'+isenable+','+row.type+')"><i class="fa fa-unlock"></i>&nbsp;<fmt:message key="general.jsp.enable.label"/></a></li>';
 						}
 						btns += '<li><a href="javascript:void(0);" onclick="deletePolicy('+row.id+','+isenable+','+row.type+')"><i class="i i-trashcan text-danger"></i><span class="text-danger">&nbsp;<fmt:message key="general.jsp.delete.label"/></span></a></li>'
 						btns += '</ul>';
 						return btns;
 					}
 				}]
   			}); 
 	    }
    var eventId = "";
    $("#policyName").autocomplete({
        source: function(request,response){
             	var name = $("#policyName").val();
			var userIds = "";
			if(null!=idArray&&idArray.length>0){
				for(var i=0;i<idArray.length;i++){
					userIds += idArray[i]+",";
				}
			}
        	if(name!=null&&name!=""){
        		name = '%'+name+'%';
	        	$.ajax({
	    			"dataType" : 'json',
	                "type": "get",
	                "url":ctx+"/institution/device/policy/findUserByName?now="+ new Date().getTime(),
	                "data": {"name":name,"userids":userIds},
	                "success": function(data){
	                	response($.map(data, function(item) {
                            return { label: item.realname+'('+item.username+')', value: item.id}
                        }));
	                } 
	            });
        	}
         },select:function(event,ui){
           	 eventId = ui.item.value;
        	 addArray(ui.item.value,ui.item.label); 
         },close:function(event,ui){
        	 if(eventId!=null&&eventId!=""){
        		 $("#policyName").val("");
        	 }
        	 eventId="";
         }
     });

    var eventIosId = "";
    $("#policyIosName").autocomplete({
        source: function(request,response){
            var name = $("#policyIosName").val();
			var userIds = "";
			if(null!=idArray&&idArray.length>0){
				for(var i=0;i<idArray.length;i++){
					userIds += idArray[i]+",";
				}
			}
        	if(name!=null&&name!=""){
        		name = '%'+name+'%';
	        	$.ajax({
	    			"dataType" : 'json',
	                "type": "get",
	                "url":ctx+"/institution/device/policy/findUserByName?now="+ new Date().getTime(),
	                "data": {"name":name,"userids":userIds},
	                "success": function(data){
	                	response($.map(data, function(item) {
                            return { label: item.realname+'('+item.username+')', value: item.id}
                        }));
	                } 
	            });
        	}
         },select:function(event,ui){
        	 eventIosId = ui.item.value;
        	 addArray(ui.item.value,ui.item.label); 
         },close:function(event,ui){
        	 if(eventIosId!=null&&eventIosId!=""){
        		 $("#policyIosName").val("");
        	 }
        	 eventIosId="";
         }
     });
    
	var defaultData = ${tree};
	//生成树形结构
	$('#tree').treeview({
		color : "#428bca",
		showBorder : false,
		showCheckbox : true,
		multiSelect : true,
		highlightSelected:false,
		data : defaultData,
	});
	$('#tree').treeview('collapseAll', { silent: true }); 
	// 选中部门节点
    $('#tree').on('nodeChecked', function(event, data) {
      var nodeId = data.tags.id;
     // var nodeId = data.nodeId;
     //	var nodeIds = nodeId + ",";
    //	var chooseDepartIds = $("#chooseDepartIds").val();
    //	$("#chooseDepartIds").val(chooseDepartIds+nodeIds);
		selectNodeLoop(data,1);
    });
	// 不选中部门节点
    $('#tree').on('nodeUnchecked', function(event, data) {
    	var nodeId = data.tags.id;
    	// var nodeId = data.nodeId;
    	//var nodeIds = nodeId + ",";
    	//var chooseDepartIds = $("#chooseDepartIds").val();
    	//chooseDepartIds = chooseDepartIds.replace(nodeIds,'');
    	//$("#chooseDepartIds").val(chooseDepartIds);
		selectNodeLoop(data,0);
    });
	
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

	// 生成IOS树形结构
	$('#iosTree').treeview({
		color : "#428bca",
		showBorder : false,
		showCheckbox : true,
		multiSelect : true,
		highlightSelected:false,
		data : defaultData
	});
	$('#iosTree').treeview('collapseAll', { silent: true });
	
	// 选中部门节点
    $('#iosTree').on('nodeChecked', function(event, data) {
      var nodeId = data.tags.id;
		selectIosNodeLoop(data,1);
    });
	// 不选中部门节点
    $('#iosTree').on('nodeUnchecked', function(event, data) {
    	var nodeId = data.tags.id;
		selectIosNodeLoop(data,0);
    });
	
	//递归选择部门节点
	function selectIosNodeLoop(data,flag){
		var tempNodes=data.nodes;
		if(tempNodes!=undefined){
			for(var i=0;i<tempNodes.length;i++){
				if(flag==1){
					$('#iosTree').treeview('checkNode', [ tempNodes[i].nodeId, { silent: true } ]);
				}else{
					$('#iosTree').treeview('uncheckNode', [ tempNodes[i].nodeId, { silent: true } ]);
				}
				selectIosNodeLoop(tempNodes[i],flag);
			}
		}
		return ;
	}
	
   // Android启用或禁用提示功能
   // 参数1:启用功能
   // 参数0:禁用功能
   function _enablePolicy(id,tag){
	   $("#delId").val(id);
	   $("#tag").val(tag);
	   if(tag=='0'){
		   $("#policyMessage").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.disable" />');
	   } else {
		   $("#policyMessage").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.enable" />');
	   } 
	   $("#policyClick").attr("onclick","enableDevicePolicy()");
	   $("#delPolicyModal").modal(open);	
   }
   
   // Ios启用或禁用提示功能
   // 参数1:启用功能
   // 参数0:禁用功能
   function _enableIosPolicy(id,tag){
	   $("#delId").val(id);
	   $("#tag").val(tag);
	   if(tag=='0'){
		   $("#policyMessage").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.disable" />');
	   } else {
		   $("#policyMessage").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.enable" />');
	   } 
	   $("#policyClick").attr("onclick","enableIosPolicy()");
	   $("#delPolicyModal").modal(open);	
   }
   
   // 启用或禁用提示功能
   function enablePolicy(id,tag,isenable,type){
	  if(isenable==1){
			Modal.confirm().on(function(e){if(e==true){
				if(type=="0"){
					_enablePolicy(id,tag);
				} else {
					_enableIosPolicy(id,tag);
				}
			
			}});
	   } else {
		   if(type=="0"){
		       _enablePolicy(id,tag);
		   } else {
			   _enableIosPolicy(id,tag);
		   }
	   }
   }
   
   // 启用或禁用功能
   // 参数1:启用功能
   // 参数0:禁用功能
   function enableIosPolicy(){
	   var id = $("#delId").val();
	   var tag = $("#tag").val();
	   // 判断id是否为空?为空新增 不为空修改
	   var url = "";
	   if(tag!=null&&tag=='1'){
		   url = ctx+"/institution/device/policy/enableIosPolicy?now="+ new Date().getTime();
	   } else {
		   url = ctx+"/institution/device/policy/disableIosPolicy?now="+ new Date().getTime();
	   }
	   var csrf="${_csrf.token}";
	   	$.ajax({
			"dataType" : 'json',
	        "type": "POST",
	        "url":url,
	        "data": {"id":id,"tag":tag,_csrf:csrf},
	        "success": function(data){
        		$(".notify").notify({type:data.type,message: { html: false, text: data.message}}).show();
        		loadDevicePolicy(); 
	        } 
	    });
   }
   
   // 保存IOS策略
   function saveIOSPolicy(){
	  saveIosPolicyValidator.validate();
      if(saveIosPolicyValidator.isValid()!=null&&!saveIosPolicyValidator.isValid()){
    	  return false;
      }
	   var id = $("#iosId").val();
	   var name = $("#iosName").val();
	   var description = $("#iosDescription").val();
		var departIds="";
		var nodeChecked=$('#iosTree').treeview('getChecked');
		if(nodeChecked.length>0){
			for(var i=0;i<nodeChecked.length;i++){
				departIds=nodeChecked[i].tags.id+","+departIds;
			}
			departIds=departIds.substr(0,departIds.length-1);
		}
	   var virtualIds = "";
	   $('input[name="virtualIosIds"]:checked').each(function(){ 
		   var tempVal = $(this).val();
		   virtualIds+=tempVal+",";
	   }); 
	   if(virtualIds!=""){
		   virtualIds = virtualIds.substring(0,virtualIds.length-1);
	   }
	   // 允许简单值
	   var allowSimpleValue = $("input[name='allowSimpleValue']:checked").val();
	   if(allowSimpleValue=="on"){
		   allowSimpleValue = 1;
	   } else {
		   allowSimpleValue = 0;
	   }
	   
	   // 允许简单值
	   var letterDigitValue = $("input[name='letterDigitValue']:checked").val();
	   if(letterDigitValue=="on"){
		   letterDigitValue = 1;
	   } else {
		   letterDigitValue = 0;
	   }
	   // 密码
	   var passwordLength=$("#passwordIosLength").val();
	   // 密码复杂度要求
	   var passwordComplexity = $("#passwordIosComplexity").val();
	   // 自动锁定前最长时间
	   var lockLongestTime = $("#lockIosLongestTime").val();
	   // 设备锁定前最长宽限时间
	   var graceTime = $("#graceTime").val();
	   // 最多可允许的失败次数
	   var failureTimes = $("#failureTimes").val();
	   // 密码的最长有效期(天)
	   var validityPeriod = $("#failureTimes").val();
	   // 密码历史记录
	   var passwordHistory = $("#passwordIosHistory").val();
	   // 允许安装应用程序
	   var allowInstallApp = $("input[name='allowInstallApp']:checked").val();
	   if(allowInstallApp=="on"){
		   allowInstallApp = 1;
	   } else {
		   allowInstallApp = 0;
	   }
	   
	   // 允许受管控的应用打开不受管控的应用的文档
	   var allowOpenFromManagedToUnmanaged = $("input[name='allowOpenFromManagedToUnmanaged']:checked").val();
	   if(allowOpenFromManagedToUnmanaged=="on"){
		   allowOpenFromManagedToUnmanaged = 1;
	   } else {
		   allowOpenFromManagedToUnmanaged = 0;
	   }
	   
	   // 允许不受管控的应用打开受管控的应用的文档
	   var allowOpenFromUnmanagedToManaged = $("input[name='allowOpenFromUnmanagedToManaged']:checked").val();
	   if(allowOpenFromUnmanagedToManaged=="on"){
		   allowOpenFromUnmanagedToManaged = 1;
	   } else {
		   allowOpenFromUnmanagedToManaged = 0;
	   }
	   
	   // 允许使用相机
	   var allowUseCamera = $("input[name='allowIosUseCamera']:checked").val();
	   if(allowUseCamera=="on"){
		   allowUseCamera = 1;
	   } else {
		   allowUseCamera = 0;
	   }
	   
	   // 允许使用FaceTime
	   var allowFaceTime = $("input[name='allowFaceTime']:checked").val();
	   if(allowFaceTime=="on"){
		   allowFaceTime = 1;
	   } else {
		   allowFaceTime = 0;
	   }
	   
	   // 允许使用allowScreenCatch
	   var allowScreenCatch = $("input[name='allowScreenCatch']:checked").val();
	   if(allowScreenCatch=="on"){
		   allowScreenCatch = 1;
	   } else {
		   allowScreenCatch = 0;
	   }
	   
	   // 允许在漫游时自动同步 
	   var allowGlobalBackgroundFetchWhenRoaming = $("input[name='allowGlobalBackgroundFetchWhenRoaming']:checked").val();
	   if(allowGlobalBackgroundFetchWhenRoaming=="on"){
		   allowGlobalBackgroundFetchWhenRoaming = 1;
	   } else {
		   allowGlobalBackgroundFetchWhenRoaming = 0;
	   }
	   
	   // 允许使用allowSiri
	   var allowSiri = $("input[name='allowSiri']:checked").val();
	   if(allowSiri=="on"){
		   allowSiri = 1;
	   } else {
		   allowSiri = 0;
	   }
	   
	   // 设备锁定时允许使用Siri
	   var allowAssistantWhileLocked = $("input[name='allowAssistantWhileLocked']:checked").val();
	   if(allowAssistantWhileLocked=="on"){
		   allowAssistantWhileLocked = 1;
	   } else {
		   allowAssistantWhileLocked = 0;
	   }
	   // 允许语音拨号
	   var allowVoiceDialing = $("input[name='allowVoiceDialing']:checked").val();
	   if(allowVoiceDialing=="on"){
		   allowVoiceDialing = 1;
	   } else {
		   allowVoiceDialing = 0;
	   }
	   
	   // 强制用户为所有购买项目输入iTunes Store密码
	   var forceiTunesStore = $("input[name='forceiTunesStore']:checked").val();
	   if(forceiTunesStore=="on"){
		   forceiTunesStore = 1;
	   } else {
		   forceiTunesStore = 0;
	   }
	   
	   // 强制用户为所有购买项目输入iTunes Store密码
	   var allowAssistantWhileLocked = $("input[name='allowAssistantWhileLocked']:checked").val();
	   if(allowAssistantWhileLocked=="on"){
		   allowAssistantWhileLocked = 1;
	   } else {
		   allowAssistantWhileLocked = 0;
	   }
	   
	   // 限制广告追踪 
	   var limitAdvertTracking = $("input[name='limitAdvertTracking']:checked").val();
	   if(limitAdvertTracking=="on"){
		   limitAdvertTracking = 1;
	   } else {
		   limitAdvertTracking = 0;
	   }
	   
	   // 允许锁屏时显示TodayView的消息
	   var allowLockScreenTodayView = $("input[name='allowLockScreenTodayView']:checked").val();
	   if(allowLockScreenTodayView=="on"){
		   allowLockScreenTodayView = 1;
	   } else {
		   allowLockScreenTodayView = 0;
	   }
	   
	   // icloud同步钥匙串
	   var allowCloudKeychainSync = $("input[name='allowCloudKeychainSync']:checked").val();
	   if(allowCloudKeychainSync=="on"){
		   allowCloudKeychainSync = 1;
	   } else {
		   allowCloudKeychainSync = 0;
	   }
	   
	   // 允许锁屏时显示控制中心的消息
	   var allowLockScreenControlCenter = $("input[name='allowLockScreenControlCenter']:checked").val();
	   if(allowLockScreenControlCenter=="on"){
		   allowLockScreenControlCenter = 1;
	   } else {
		   allowLockScreenControlCenter = 0;
	   }
	   
	   // 允许指纹解锁
	   var allowFingerprintForUnlock = $("input[name='allowFingerprintForUnlock']:checked").val();
	   if(allowFingerprintForUnlock=="on"){
		   allowFingerprintForUnlock = 1;
	   } else {
		   allowFingerprintForUnlock = 0;
	   }
	   
	   // 允许锁屏时显示通知消息
	   var allowLockScreenNotificationsView = $("input[name='allowLockScreenNotificationsView']:checked").val();
	   if(allowLockScreenNotificationsView=="on"){
		   allowLockScreenNotificationsView = 1;
	   } else {
		   allowLockScreenNotificationsView = 0;
	   }
	   
	   // 允许锁屏时显示Passbook消息
	   var allowDisplayPassbook = $("input[name='allowDisplayPassbook']:checked").val();
	   if(allowDisplayPassbook=="on"){
		   allowDisplayPassbook = 1;
	   } else {
		   allowDisplayPassbook = 0;
	   }
	   
	   // 允许被管理的应用将数据存储到iCloud
	   var allowManagedAppsCloudSync = $("input[name='allowManagedAppsCloudSync']:checked").val();
	   if(allowManagedAppsCloudSync=="on"){
		   allowManagedAppsCloudSync = 1;
	   } else {
		   allowManagedAppsCloudSync = 0;
	   }
	   
	   // 允许iCloud照片图库 
	   var allowCloudPhotoLibrary = $("input[name='allowCloudPhotoLibrary']:checked").val();
	   if(allowCloudPhotoLibrary=="on"){
		   allowCloudPhotoLibrary = 1;
	   } else {
		   allowCloudPhotoLibrary = 0;
	   }
	   
	   // 允许iCloud照片共享
	   var allowSharedStream = $("input[name='allowSharedStream']:checked").val();
	   if(allowSharedStream=="on"){
		   allowSharedStream = 1;
	   } else {
		   allowSharedStream = 0;
	   }
	   
	   // 允许使用handoff
	   var allowActivityContinuation = $("input[name='allowActivityContinuation']:checked").val();
	   if(allowActivityContinuation=="on"){
		   allowActivityContinuation = 1;
	   } else {
		   allowActivityContinuation = 0;
	   }
	   
	   // 允许使用YouTube
	   var allowUseYoutube = $("input[name='allowUseYoutube']:checked").val();
	   if(allowUseYoutube=="on"){
		   allowUseYoutube = 1;
	   } else {
		   allowUseYoutube = 0;
	   }
	   
	   // 允许使用iTunes Store
	   var allowUseiTunes = $("input[name='allowUseiTunes']:checked").val();
	   if(allowUseiTunes=="on"){
		   allowUseiTunes = 1;
	   } else {
		   allowUseiTunes = 0;
	   }
	   
	   // 允许使用Game Center
	   var allowGameCenter = $("input[name='allowGameCenter']:checked").val();
	   if(allowGameCenter=="on"){
		   allowGameCenter = 1;
	   } else {
		   allowGameCenter = 0;
	   }
	   
	   // 允许添加 Game Center好友
	   var allowAddingGameCenterFriends = $("input[name='allowAddingGameCenterFriends']:checked").val();
	   if(allowAddingGameCenterFriends=="on"){
		   allowAddingGameCenterFriends = 1;
	   } else {
		   allowAddingGameCenterFriends = 0;
	   }
	   
	   // 允许多人游戏
	   var allowMultiplayerGaming = $("input[name='allowMultiplayerGaming']:checked").val();
	   if(allowMultiplayerGaming=="on"){
		   allowMultiplayerGaming = 1;
	   } else {
		   allowMultiplayerGaming = 0;
	   }
	   
	   // 允许使用Safari
	   var allowUseSafari = $("input[name='allowUseSafari']:checked").val();
	   if(allowUseSafari=="on"){
		   allowUseSafari = 1;
	   } else {
		   allowUseSafari = 0;
	   }
	   
	   // 允许备份
	   var allowBackup = $("input[name='allowBackup']:checked").val();
	   if(allowBackup=="on"){
		   allowBackup = 1;
	   } else {
		   allowBackup = 0;
	   }
	   
	   // 允许文档同步
	   var allowDocumentSynchronization = $("input[name='allowDocumentSynchronization']:checked").val();
	   if(allowDocumentSynchronization=="on"){
		   allowDocumentSynchronization = 1;
	   } else {
		   allowDocumentSynchronization = 0;
	   }
	   
	   // 允许照片流
	   var allowPhotoStream = $("input[name='allowPhotoStream']:checked").val();
	   if(allowPhotoStream=="on"){
		   allowPhotoStream = 1;
	   } else {
		   allowPhotoStream = 0;
	   }
	   var userIds = "";
	   if(null!=idArray&&idArray.length>0){
			for(var i=0;i<idArray.length;i++){
				userIds += idArray[i]+",";
			}
			if(userIds!=""){
				userIds = userIds.substring(0,userIds.length-1);
			}
	   } 
	   // 密码模块->是否启用密码
	   var isEnablePassword = $("#isEnablePassword").val();
	   
	   var str = "";
	   // 是否上网时间限制
	   // 检测SD卡
	   var visitLimit = $("input[name='iosVisitLimit']:checked").val();
	   if(visitLimit=="on"){
		   visitLimit = 1;
		   for(var i=1;i<8;i++){
			   str += addSeparatorStr($("#iosStartTime"+i).val(),$("#iosEndTime"+i).val());
		   }
		   str += "|";
	   } else {
		   visitLimit = 0;
	   }

	   // 网页限制
	   var enableBlacklist = $("input[name='enableIosNameList']:checked").val();
	   // 选中的黑或白名单list
	   var chooseEnableIds = $("#chooseIosEnableIds").val();
	   
	   // 应用限制
	   var enableAppNameList = $("input[name='enableIosApp']:checked").val();
	   // 选中的网页list
	   var chooseIosAppEnableIds = $("#chooseIosAppEnableIds").val(); 
	   var postData = {
			    token:'',
			    iosList:'',
				params : {},
	   };
	   // WIFI配置
	   var imgs=document.getElementById("iosImgs");
	   if(imgs!=null&&imgs!=""&&imgs!=undefined){
		   var imgObj = imgs.getElementsByTagName("li"); 
		   var count = imgObj.length;
		   var list = "";
		   for(var i=0;i<count;i++){
			   var iosSsid = $("#iosSsid"+i).val();
			   var autojoin = $("input[name='autojoin"+i+"']:checked").val();
			   var isAutoJoin = 0;
			   var saftyIosType = $("#saftyIosType"+i).val();
			   var agent = $("#agent"+i).val();
			   var wifiIoskey = $("#wifiIoskey"+i).val();
			   var agentServer = $("#agentServer"+i).val();
			   var agentPort = $("#agentPort"+i).val();
			   var agentAppraisal = $("#agentAppraisal"+i).val();
			   var agentPassword = $("#agentPassword"+i).val();
			   var agentUrl = $("agentUrl"+i).val();
			   list += '{';
			   if(iosSsid==null||iosSsid==""||iosSsid==undefined){
				   list += '"iosSsid":""';
			   } else {
				   list += '"iosSsid":"'+iosSsid+'"';
			   }
			   if(autojoin=="on"){
				   isAutoJoin = 1;
			   } else {
				   isAutoJoin = 0;
			   }
			   list += ',"isAutoJoin":'
			   list += isAutoJoin;
			   if(saftyIosType==null||saftyIosType==""||saftyIosType==undefined){
				   list += ',"saftyIosType":""';
			   } else {
				   list += ',"saftyIosType":'+saftyIosType;
			   } 
			   if(agent==null||agent==""||agent==undefined){
				   list += ',"agent":""';
			   } else {
				   list += ',"agent":'+agent;
			   }
			   if(wifiIoskey==null||wifiIoskey==""||wifiIoskey==undefined){
				   list += ',"wifiIoskey":""';
			   } else {
				   list += ',"wifiIoskey":"'+wifiIoskey+'"';
			   }
			   if(agentServer==null||agentServer==""||agentServer==undefined){
				   list += ',"agentServer":""';
			   } else {
				   list += ',"agentServer":"'+agentServer+'"';
			   }
			   if(agentPort==null||agentPort==""||agentPort==undefined){
				   list += ',"agentPort":""';
			   } else {
				   list += ',"agentPort":"'+agentPort+'"';
			   }
			   if(agentAppraisal==null||agentAppraisal==""||agentAppraisal==undefined){
				   list += ',"agentAppraisal":""';
			   } else {
				   list += ',"agentAppraisal":"'+agentAppraisal+'"';
			   }
			   if(agentPassword==null||agentPassword==""||agentPassword==undefined){
				   list += ',"agentPassword":""';
			   } else {
				   list += ',"agentPassword":"'+agentPassword+'"';
			   }
			   if(agentUrl==null||agentUrl==""||agentUrl==undefined){
				   list += ',"agentUrl":""';
			   } else {
				   list += ',"agentUrl":"'+agentUrl+'"';
			   }
			   list += '},';
		  }
		  list = list.substring(0,list.length-1);
		  list = "["+list+"]";
		  postData.iosList = list;
	   }
	   var safeType = $("#safeType").val();
	   postData.params.id = id;
	   postData.params.name = name;
	   postData.params.description = description;
	   postData.params.chooseDepartIds = departIds;
	   postData.params.virtualIds = virtualIds;
	   postData.params.isEnablePassword = isEnablePassword;
		   postData.params.userIds = userIds;
	   postData.params.allowSimpleValue = allowSimpleValue;
	   postData.params.letterDigitValue = letterDigitValue;
	   postData.params.passwordLength = passwordLength;
	   postData.params.lockLongestTime = lockLongestTime;
	   postData.params.graceTime = graceTime;
	   postData.params.failureTimes = failureTimes;
	   postData.params.validityPeriod = validityPeriod;
	   postData.params.passwordHistory = passwordHistory;
	   postData.params.allowInstallApp = allowInstallApp;
	   postData.params.allowOpenFromManagedToUnmanaged = allowOpenFromManagedToUnmanaged;
	   postData.params.allowOpenFromUnmanagedToManaged = allowOpenFromUnmanagedToManaged;
	   postData.params.allowUseCamera = allowUseCamera;
	   postData.params.allowFaceTime = allowFaceTime;
	   postData.params.allowScreenCatch = allowScreenCatch;
	   postData.params.allowGlobalBackgroundFetchWhenRoaming = allowGlobalBackgroundFetchWhenRoaming;
	   postData.params.allowSiri = allowSiri;
	   postData.params.allowAssistantWhileLocked = allowAssistantWhileLocked;
	   postData.params.allowVoiceDialing = allowVoiceDialing;
	   postData.params.forceiTunesStore = forceiTunesStore;
	   postData.params.limitAdvertTracking = limitAdvertTracking;
	   postData.params.allowLockScreenTodayView = allowLockScreenTodayView;
	   postData.params.allowCloudKeychainSync = allowCloudKeychainSync;
	   postData.params.allowLockScreenControlCenter = allowLockScreenControlCenter;
	   postData.params.allowFingerprintForUnlock = allowFingerprintForUnlock;
	   postData.params.allowLockScreenNotificationsView = allowLockScreenNotificationsView;
	   postData.params.allowDisplayPassbook = allowDisplayPassbook;
	   postData.params.allowManagedAppsCloudSync = allowManagedAppsCloudSync;
	   postData.params.allowSharedStream = allowSharedStream;
	   postData.params.allowActivityContinuation = allowActivityContinuation;
	   postData.params.allowUseYoutube = allowUseYoutube;
	   postData.params.allowUseiTunes = allowUseiTunes;
	   postData.params.allowGameCenter = allowGameCenter;
	   postData.params.allowAddingGameCenterFriends = allowAddingGameCenterFriends;
	   postData.params.allowMultiplayerGaming = allowMultiplayerGaming;
	   postData.params.allowUseSafari = allowUseSafari;
	   postData.params.allowBackup = allowBackup;
	   postData.params.allowDocumentSynchronization = allowDocumentSynchronization;
	   postData.params.allowPhotoStream = allowPhotoStream;
	   postData.params.safeType = safeType;
	   postData.params.visitLimit = visitLimit;
	   postData.params.visitTimeStr = str;
	   postData.params.allowCloudPhotoLibrary = allowCloudPhotoLibrary;
		   // 网页限制
	   postData.params.enableBlacklist = enableBlacklist;
	   postData.params.chooseEnableIds = chooseEnableIds;
	   // 应用限制
	   postData.params.enableAppNameList = enableAppNameList;
	   postData.params.chooseAppEnableIds = chooseIosAppEnableIds;
		   var token = $("#tokenIosId").val();
	   postData.token = token; 
	   // 判断id是否为空?为空新增 不为空修改
	   var url = "";
	   var csrf = "${_csrf.token}";
	   if(id!=null&&id!=""){
		   url = ctx+"/institution/device/policy/updateIosPolicy?_csrf="+csrf;
	   } else {
		   url = ctx+"/institution/device/policy/saveIosPolicy?_csrf="+csrf;
	   }
	   $.ajax({
			"dataType" : 'json',
	        "data": postData,
	        "type": "POST",
            "url":url,
	        "success": function(data){
	        	$(".notify").notify({type:data.type,message: { html: false, text: data.message}}).show();
	        	if(data.type=="success"){
	        		$("#myIosModal").modal("hide");
	    	        window.location.href = ctx +"/institution/device/policy";
	    	    }
            	return false;
	        } 
	     }); 
         return true;
   }
   
   // 启用或禁用功能
   // 参数1:启用功能
   // 参数0:禁用功能
   function enableDevicePolicy(){
	   var id = $("#delId").val();
	   var tag = $("#tag").val();
	   // 判断id是否为空?为空新增 不为空修改
	   var url = "";
	   if(tag!=null&&tag=='1'){
		   url = ctx+"/institution/device/policy/enablePolicy?now="+ new Date().getTime();
	   } else {
		   url = ctx+"/institution/device/policy/disablePolicy?now="+ new Date().getTime();
	   }
	   var csrf="${_csrf.token}";
	   	$.ajax({
			"dataType" : 'json',
	        "type": "POST",
	        "url":url,
	        "data": {"id":id,"tag":tag,_csrf:csrf},
	        "success": function(data){
        		$(".notify").notify({type:data.type,message: { html: false, text: data.message}}).show();
        		loadDevicePolicy(); 
	        } 
	    });
   }
   
   // 启用或禁用功能
   // 参数1:启用功能
   // 参数0:禁用功能
   function enableDevicePolicy(){
	   var id = $("#delId").val();
	   var tag = $("#tag").val();
	   // 判断id是否为空?为空新增 不为空修改
	   var url = "";
	   if(tag!=null&&tag=='1'){
		   url = ctx+"/institution/device/policy/enablePolicy?now="+ new Date().getTime();
	   } else {
		   url = ctx+"/institution/device/policy/disablePolicy?now="+ new Date().getTime();
	   }
	   var csrf="${_csrf.token}";
	   	$.ajax({
			"dataType" : 'json',
	        "type": "POST",
	        "url":url,
	        "data": {"id":id,"tag":tag,_csrf:csrf},
	        "success": function(data){
        		$(".notify").notify({type:data.type,message: { html: false, text: data.message}}).show();
        /* 	 	window.location.href = ctx +"/institution/device/policy";  */
        		loadDevicePolicy(); 
	        } 
	    });
   }
   
   // 设备策略删除提示功能
   function _deleteIosPolicy(id){
	  $("#delId").val(id);
	  $("#policyMessage").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.delmessage"/>');
	  $("#policyClick").attr("onclick","delIosDevicePolicy()");
  	  $("#delPolicyModal").modal(open);	
    }

   // 设备策略删除提示功能s
   function _deletePolicy(id){
	  $("#delId").val(id);
	  $("#policyMessage").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.delmessage"/>');
	  $("#policyClick").attr("onclick","delDevicePolicy()");
  	  $("#delPolicyModal").modal(open);	
    }
   
   // 显示删除android设备策略弹出框
   function deletePolicy(id,isenable,type){
	   if(isenable==1){
			Modal.confirm().on(function(e){if(e==true){
				if(type==0){
					_deletePolicy(id);
				} else {
					_deleteIosPolicy(id);
				}
			}
		  });
	   } else {
			 if(type==0){
				 _deletePolicy(id);
			 } else {
				 _deleteIosPolicy(id);
			 } 
		 }
   }
   
   // 显示删除IOS设备策略弹出框
   function _deleteIosPolicy(id){
	  $("#delIosId").val(id);
	  $("#policyIosMessage").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.delmessage"/>');
	  $("#policyIosClick").attr("onclick","delIosDevicePolicy()");
  	  $("#delIosPolicyModal").modal(open);	
   }
   
   // 删除IOS设备策略
   function delIosDevicePolicy(){
		var id = $("#delIosId").val();
		var csrf="${_csrf.token}";
	 	$.ajax({
			 "dataType" : 'json',
	         "type": "POST",
	         "url":ctx+"/institution/device/policy/delIosPolicy?now="+ new Date().getTime(),
	         "data": {"id":id,_csrf:csrf},
	         "success": function(data){
	         	$(".notify").notify({type:data.type,message: { html: false, text: data.message}}).show();
	     		$("#myIosModal").modal("hide");
	     		loadDevicePolicy();
	         	return false;
	         }	
	    });
   }
   
	//删除策略
	function delDevicePolicy(){
		var id = $("#delId").val();
		   var csrf="${_csrf.token}";
    	$.ajax({
			"dataType" : 'json',
            "type": "POST",
            "url":ctx+"/institution/device/policy/delPolicy?now="+ new Date().getTime(),
            "data": {"id":id,_csrf:csrf},
            "success": function(data){
            	$(".notify").notify({type:data.type,message: { html: false, text: data.message}}).show();
        		$("#myModal").modal("hide");
        		loadDevicePolicy();
            	return false;
            }	
       });
	}
   
   // 初始化Tab默认第一个显示
   function initTab(){
	  for(var i=0;i<7;i++){
		 var j = 0;
	    if(i==0){
		   $("#deviceTab ul li:eq(0)").prop("class","active");
		   j = i+1;
		   $("#tab"+j).prop("class","tab-pane active"); 
		} else {
		   j = i+1;
		   $("#deviceTab ul li:eq("+i+")").removeAttr("class");
		   $("#tab"+j).attr("class","tab-pane");  
	    }
    }
   	$("#id").val("");
	$("#name").val("");
	$("#description").val("");
	$("#policyName").val("");	
	// 重置策略名称验证信息
    $(".parsley-remote").html("");
    $(".parsley-required").html("");
    $("#name").removeClass("parsley-error");
    $("#name").removeClass("parsley-success");
 	$("#name").removeAttr("disabled");
	$("#name").removeAttr("autofocus");
 	$("#description").removeAttr("disabled");
 	$('#tree').treeview('enableAll', { silent: true });
 	$('#tree').treeview('uncheckAll', { silent: true });
	$("#chooseDepartIds").val("");	
	$('input[name="virtualIds"]').removeAttr("disabled"); 
 	$("input[name='virtualIds']").removeAttr("checked");  
	$("input[name='userIds']").removeAttr("checked"); 
	$("#passwordLength").removeAttr("disabled");
	$("#passwordComplexity").prop("disabled", true);
	$("#lockLongestTime").prop("disabled", true);
	$("#passwordValidity").prop("disabled", true);
	$("#attemptTimes").prop("disabled", true);
	$("#tab3 input[type]").removeAttr("disabled"); 
	$("#tab4 input[type]").removeAttr("disabled"); 
	$("#tab5 input[type]").removeAttr("checked"); 
	$("#name1").removeAttr("disabled");
	$("#name2").removeAttr("disabled");
	$("#policyVirtualRight").html("");
	idArray = [];
    nameArray = [];
	 $("#userIds").val("");
     $("#userNames").val("");
 	$("#policyName").removeAttr("disabled");
	// 密码
	$("#passwordLength option:first").prop("selected", 'selected');
	$("#passwordComplexity option:first").prop("selected", 'selected');
	$("#lockLongestTime option:first").prop("selected", 'selected');
	$("#passwordValidity option:first").prop("selected", 'selected');
	$("#attemptTimes option:first").prop("selected", 'selected');
	$("#tab3 input[type]").removeAttr("checked"); 
	$("#tab4 input[type]").prop("checked",true); 
	$("#name1").prop("checked",true);
	$("#name2").removeAttr("checked");
	$("#appName1").prop("checked",true);
	$("#appName2").removeAttr("checked");
	$("#nameListText").val("");
	$("#nameListIds").val("");
	$("#chooseEnableIds").val("");
	$("#appNameListText").val("");
	$("#enableWords").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.enabledesc"/>');
	$("#appEnableWords").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.enableAppdesc"/>');
    $("#passwordLength").removeClass("parsley-error");
    $("#passwordLength-modify-error").addClass("hidden");
	$("#passwordComplexity").removeClass("parsley-error");
	$("#passwordComplexity-modify-error").addClass("hidden");
    $("#visitLimit").removeAttr("checked"); 
    $("#visitLimit").removeAttr("disabled"); 
    saveAndroidPolicyValidator.reset();
    idArray = [];
    nameArray = [];
    var allowUrlArray = [];
    var notAllowUrlArray = [];
    var urlArray = [];
	for(var i=1;i<8;i++){
		$("#startTime"+i).val("");
		$("#endTime"+i).val("");
	}
	enableVisitLimit();
	wifiNoSetting();
   }

   // 初始化IOS设备策略Tab
   function initIosTab(){
/*     wifiNoIosWebClipSetting(0); */
       wifiNoIosWebClipSetting(1);
	   $("#iosId").val("");
	   $("#iosDescription").val("");
	   $("#iosName").val("");
	   $("#iosName").removeAttr("disabled");
	   $("#iosName").removeAttr("autofocus");
	   $("#iosName").removeClass("parsley-error");
	   $("#iosName").removeClass("parsley-success");
	   
	   $("#iosDescription").removeAttr("disabled");
	   for(var i=0;i<6;i++){
		   var j = 10;
		   if(i==0){
			   $("#deviceIosTab ul li:eq(0)").prop("class","active");
			   $("#tab"+j).prop("class","tab-pane active");
			   j += i;
		   } else {
			   $("#deviceIosTab ul li:eq("+i+")").removeAttr("class");
			   j += i;
			   $("#tab"+j).attr("class","tab-pane"); 
		   }
	    }
	 	$('#iosTree').treeview('enableAll', { silent: true });
	 	$('#iosTree').treeview('uncheckAll', { silent: true });
	    $("#policyIosName").val("");
	    $(".parsley-remote").html("");
	    $(".parsley-required").html("");
		$('input[name="virtualIosIds"]').removeAttr("disabled"); 
	 	$("input[name='virtualIosIds']").removeAttr("checked");  
		$("#policyIosVirtualRight").html("");
		$("#allowSimpleValue").prop("checked",true);
		$("#letterDigitValue").removeAttr("checked");
		$("#allowSimpleValue").removeAttr("disabled");
		$("#letterDigitValue").removeAttr("disabled");
		$("#passwordIosLength option:first").prop("selected", 'selected');
		$("#passwordIosLength").removeAttr("disabled");
		$("#failureTimes option:first").prop("selected", 'selected');
		$("#failureTimes").removeAttr("disabled");
		$("#passwordIosHistory option:first").prop("selected", 'selected');
		$("#passwordIosHistory").removeAttr("disabled");		
		$("#allowInstallApp").removeAttr("disabled");
		$("#allowInstallApp").prop("checked",true);
		$("#allowIosUseCamera").removeAttr("disabled");
		$("#allowIosUseCamera").prop("checked",true);
		$("#allowFaceTime").removeAttr("disabled");
		$("#allowFaceTime").prop("checked",true);
		$("#allowScreenCatch").removeAttr("disabled");
		$("#allowScreenCatch").prop("checked",true);
		$("#allowUseYoutube").removeAttr("disabled");
		$("#allowUseYoutube").prop("checked",true);
		$("#allowUseiTunes").removeAttr("disabled");
		$("#allowUseiTunes").prop("checked",true);
		$("#allowUseSafari").removeAttr("disabled");
		$("#allowUseSafari").prop("checked",true);
		$("#allowBackup").removeAttr("disabled");
		$("#allowBackup").prop("checked",true);
		$("#allowDocumentSynchronization").removeAttr("disabled");
		$("#allowDocumentSynchronization").prop("checked",true);
		$("#allowPhotoStream").removeAttr("disabled");
		$("#allowPhotoStream").prop("checked",true);
		$("#passwordIosHistory").val("");
		$("#allowBackup").removeAttr("disabled");
		$("#allowOpenFromManagedToUnmanaged").removeAttr("disabled");
		$("#allowOpenFromUnmanagedToManaged").removeAttr("disabled");
		$("#allowGlobalBackgroundFetchWhenRoaming").removeAttr("disabled");
		$("#allowAssistantWhileLocked").removeAttr("disabled");
		$("#allowLockScreenTodayView").removeAttr("disabled");
		$("#allowCloudKeychainSync").removeAttr("disabled");
		$("#allowLockScreenControlCenter").removeAttr("disabled");
		$("#allowFingerprintForUnlock").removeAttr("disabled");
		$("#allowLockScreenNotificationsView").removeAttr("disabled");
		$("#allowManagedAppsCloudSync").removeAttr("disabled");
		$("#allowCloudPhotoLibrary").removeAttr("disabled");
		$("#allowSharedStream").removeAttr("disabled");
		$("#allowActivityContinuation").removeAttr("disabled");
		$("#allowAddingGameCenterFriends").removeAttr("disabled");
		$("#allowMultiplayerGaming").removeAttr("disabled");
		$("#forceiTunesStore").removeAttr("disabled");
		$("#limitAdvertTracking").removeAttr("disabled");
		$("#isNetLimit").removeAttr("disabled");
		$("#enableBlacklist").removeAttr("disabled");
		$("#enableAppNameList").removeAttr("disabled");
		$("#allowSiri").removeAttr("disabled");
		$("#allowVoiceDialing").removeAttr("disabled");
		$("#allowDisplayPassbook").removeAttr("disabled");
		$("#allowGameCenter").removeAttr("disabled");
		$("#iosName1").removeAttr("disabled");
		$("#iosName2").removeAttr("disabled");
		$("#iosName2").prop("checked",true);
		$("#iosName1").removeAttr("checked");
		$("#iosNameListText").val("");
	    $("#iosVisitLimit").removeAttr("checked"); 
	    $("#iosVisitLimit").removeAttr("disabled"); 
		for(var i=1;i<8;i++){
			$("#iosStartTime"+i).val("");
			$("#iosEndTime"+i).val("");
		}
	    enableIosVisitLimit();
	    idArray = [];
	    nameArray = [];
	    allowUrlArray = [];
	    notAllowUrlArray = [];
	    urlArray = [];
		var html = '<a href="javascript:void(0)" onclick="saveIOSPolicy()" class="btn btn-success"><fmt:message key="general.jsp.comfirm.label"/></a>';
		html += '<a href="javascript:void(0)" class="btn" data-dismiss="modal"><fmt:message key="general.jsp.cancel.label"/></a>';
		$("#saveIosButton").html(html); 
		passwordIosSetting();
   }
   
   // 当点击初始化WIFI配置页面的时候(点击编辑按钮)
   function  wifiNoSetting(){
	   	var wifihtml = "";
	   	wifihtml += '<div class="col-lg-6 wifiindex">';
	   	wifihtml += '<div class="col-lg-12" style="margin-left:-14%;">';
	   	wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.noConfiguration"/>';
	   	wifihtml += '</div>';
		wifihtml += '<div class="col-lg-12 wifitop">';
		wifihtml += '<button class="btn btn-primary wifileft" onclick="wifiSetting()"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.immediateConfiguration"/></button>';
		wifihtml += '</div>';
	   	wifihtml += '</div>';
	   	$("#tab7").html(wifihtml);
   }
   
   // IOS设备策略(tag:0(WebClip),tag:1(WIFI配置))
   function  wifiNoIosWebClipSetting(tag){
	   	var wifihtml = "";
	   	wifihtml += '<div class="col-lg-6 wifiindex">';
	   	wifihtml += '<div class="col-lg-12 style="margin-left:-14%;"">';
	   	if(tag==0){
	   	   wifihtml += '<fmt:message key="tiles.views.institution.Webclip.indexscript.noConfiguration"/>';
	   	} else {
	   	   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.noConfiguration"/>';
	   	}
	   	wifihtml += '</div>';
		wifihtml += '<div class="col-lg-12 wifitop">';
		wifihtml += '<button id="iosBtn" class="btn btn-primary wifileft"';
		if(tag==0){
			wifihtml += 'onclick="webClipSetting(0)"';
		} else {
			wifihtml += 'onclick="wifiIosSetting(0)"';
		}
		wifihtml += '><fmt:message key="tiles.views.institution.devicepolicy.indexscript.immediateConfiguration"/></button>';
		wifihtml += '</div>';
	   	wifihtml += '</div>';
	   	if(tag==0){
	   		$("#tab14").html(wifihtml);
	   	} else {
	   		$("#tab13").html(wifihtml);
	   	}
   }
   
   // ios密码设置
   function  passwordIosSetting(){
	   	var wifihtml = "";
	   	wifihtml += '<div class="col-lg-6 wifiindex">';
	   	wifihtml += '<div class="col-lg-12" style="margin-left:-14%;">';
	    wifihtml += '<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.nosetting.password"/>';
	   	wifihtml += '</div>';
		wifihtml += '<div class="col-lg-12 wifitop">';
		wifihtml += '<button id="iosBtn" class="btn btn-primary wifileft" onclick="initPasscodeSetting()">';
		wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.immediateConfiguration"/></button>';
		wifihtml += '</div>';
	   	wifihtml += '</div>';
	   	$("#tab11").html(wifihtml);
   }
   
   // 新增配置passwordIosSetting配置
   function initPasscodeSetting(){
	   $("#isEnablePassword").val(1);
	   var wifihtml = "";
	   wifihtml += '<div class="form-group">';
	   wifihtml += '<label class="col-sm-3 control-label"><input type="checkbox" id="allowSimpleValue" name="allowSimpleValue" checked="checked"/></label>';
	   wifihtml += '<div class="col-sm-8 control-label" style="text-align:left;"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.allowsimplevalue"/></div>';
	   wifihtml += '</div>';
	   wifihtml += '<div class="form-group">';
	   wifihtml += '<label class="col-sm-3 control-label"><input type="checkbox" id="letterDigitValue" name="letterDigitValue"/></label>';
	   wifihtml += '<div class="col-sm-8 control-label" style="text-align:left;"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.letterdigitvalue"/></div>';
	   wifihtml += '</div>';
	   wifihtml += '<div class="col-sm-12">';
	   wifihtml += '<label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.passwordlength"/></label>';
	   wifihtml += '<div class="col-sm-8">';
	   wifihtml += '<select id="passwordIosLength" class="form-control m-b" onclick="resetPasswordLength()">';
	   wifihtml += '<option value=""><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.choose"/></option>';
	   wifihtml += '<option value="1">1</option>';
	   wifihtml += '<option value="2">2</option>';
	   wifihtml += '<option value="3">3</option>';
	   wifihtml += '<option value="4">4</option>';
	   wifihtml += '<option value="5">5</option>';
	   wifihtml += '<option value="6">6</option>';
	   wifihtml += '<option value="7">7</option>';
	   wifihtml += '<option value="8">8</option>';
	   wifihtml += '<option value="9">9</option>';
	   wifihtml += '<option value="10">10</option>'; 
	   wifihtml += '<option value="11">11</option>';
	   wifihtml += '<option value="12">12</option>'; 
	   wifihtml += '<option value="13">13</option>'; 
	   wifihtml += '<option value="14">14</option>'; 
	   wifihtml += '<option value="15">15</option>'; 
	   wifihtml += '<option value="16">16</option>'; 
	   wifihtml += '</select>'; 
	   wifihtml += '</div>';
	   wifihtml += '</div>';
	   wifihtml += '<div class="col-sm-12">';
	   wifihtml += '<label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.failuretimes"/></label>';
	   wifihtml += '<div class="col-sm-8">';
	   wifihtml += '<select id="failureTimes" class="form-control m-b">';
	   wifihtml += '<option value=""><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.choose"/></option>';
	   wifihtml += '<option value="0">4</option>';
	   wifihtml += '<option value="1">5</option>';
	   wifihtml += '<option value="2">6</option>';
	   wifihtml += '<option value="3">7</option>';
	   wifihtml += '<option value="4">8</option>';
	   wifihtml += '<option value="5">9</option>';
	   wifihtml += '<option value="6">10</option>';
	   wifihtml += '</select>';
	   wifihtml += '</div>';
	   wifihtml += '</div>';
	   wifihtml += '<div class="col-sm-12">';
	   wifihtml += '<label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.passwordhistoryrecord"/></label>';
	   wifihtml += '<div class="col-sm-8">';
	   wifihtml += '<input type="text" id="passwordIosHistory" class="form-control m-b" data-parsley-maxlength="2" onkeyup="this.value=this.value.replace(/\D/g,"")" ';
	   wifihtml += 'onafterpaste="this.value=this.value.replace(/\D/g,"")" value="0"/>';
	   wifihtml += '</div>';
	   wifihtml += '</div>';
	   $("#tab11").html(wifihtml);
   }
   
   // 当点击初始化WIFI配置页面的时候(点击查看按钮)
   function  wifiFindSetting(){
	   	var wifihtml = "";
	   	wifihtml += '<div class="col-lg-6 wifiindex">';
	   	wifihtml += '<div class="col-lg-12" style="margin-left:-14%;">';
	   	wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.noConfiguration"/>';
	   	wifihtml += '</div>';
		wifihtml += '<div class="col-lg-12 wifitop">';
		wifihtml += '<button class="btn btn-gray wifileft"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.immediateConfiguration"/></button>';
		wifihtml += '</div>';
	   	wifihtml += '</div>';
	   	$("#tab7").html(wifihtml);
   }
   
   // 新增配置WebClip配置
   function webClipSetting(i){
	   var wifihtml = "";
	   wifihtml += '<div id="webLuanpo" style="height:430px">';
	   wifihtml += '<ul id="webClipImgs">';    
	   wifihtml += '<li id="ssidli'+i+'">';
	   // 标签
	   wifihtml += '<div class="form-group marginBottom">';
	   wifihtml += '<div class="col-lg-12">';
	   wifihtml += '<div class="col-lg-3 control-label">';
	   wifihtml += '标签<span class="asterisk">*</span>';
	   wifihtml += '</div>';    
	   wifihtml += '<div class="col-lg-7 control-label">';
	   wifihtml += '<input type="text" id="webClipName'+i+'" class="form-control m-b" data-parsley-required="true"/>';
	   wifihtml += '</div>';  
	   wifihtml += '<div class="col-lg-2 control-label">';
	   wifihtml += '<i class="fa fa-plus-circle fa-2x cursor" onclick="plusWebClipSSID()"></i>&nbsp;&nbsp;<i class="fa fa-minus-circle fa-2x cursor" onclick="minusWebClipSSID('+i+')"></i>';
	   wifihtml += '</div>';  
	   wifihtml += '</div>'; 
	   wifihtml += '</div>'; 
	   // url
	   wifihtml += '<div class="form-group marginBottom">';
	   wifihtml += '<div class="col-lg-12">';
	   wifihtml += '<div class="col-lg-3 control-label">';
	   wifihtml += 'URL<span class="asterisk">*</span>';
	   wifihtml += '</div>';    
	   wifihtml += '<div class="col-lg-7 control-label">';
	   wifihtml += '<input type="text" id="webClipUrl'+i+'" class="form-control m-b" data-parsley-required="true"/>';
	   wifihtml += '</div>';  
	   wifihtml += '</div>';
	   wifihtml += '</div>';
	   // 图标
	   wifihtml += '<div class="form-group marginBottom">';
	   wifihtml += '<div class="col-lg-12">';
	   wifihtml += '<div class="col-lg-3 control-label">';
	   wifihtml += '图标';
	   wifihtml += '</div>';    
	   wifihtml += '<div class="col-lg-7">';
	   wifihtml += '<input type="hidden" id="iconPath'+i+'"/>';
	   wifihtml += '<img src="'+ctx+'/resources/images/upload.png" id="imgPath'+i+'" onclick="uploadIconModal('+i+')" width="80px" height="80" />';
	   wifihtml += '</div>';  
	   wifihtml += '</div>';
	   // 可删除
	   wifihtml += '<div class="col-lg-12">';
	   wifihtml += '<div class="col-lg-3 control-label">';
	   wifihtml += '</div>';
	   wifihtml += '&nbsp;&nbsp;&nbsp;<input type="checkbox" name="isRemove'+i+'" id="is_remove'+i+'"/>&nbsp;<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.candelete"/>';
	   wifihtml += '</div>';
	   // 预作
	   wifihtml += '<div class="col-lg-12">';
	   wifihtml += '<div class="col-lg-3 control-label">';
	   wifihtml += '</div>';
	   wifihtml += '&nbsp;&nbsp;&nbsp;<input type="checkbox" name="precomposeIcon'+i+'" id="precompose_icon'+i+'"/>&nbsp;<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.precomposeicon"/>';
	   wifihtml += '</div>';
	   // 全屏
	   wifihtml += '<div class="col-lg-12">';
	   wifihtml += '<div class="col-lg-3 control-label">';
	   wifihtml += '</div>';
	   wifihtml += '&nbsp;&nbsp;&nbsp;<input type="checkbox" name="fullScreen'+i+'" id="full_screen'+i+'"/>&nbsp;<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.fullscreen"/>';
	   wifihtml += '</div>';
	   wifihtml += '</li>';
	   wifihtml += '</ul>';    
	   wifihtml += '<ul id="webClipnum">'; 
	   wifihtml += '<li class="current"></li>';   
	   wifihtml += '</ul>';   
	   wifihtml += '</div>';   
	   $("#tab14").html(wifihtml);
	   webClipTab();
   }
   
   // wifi的IOS设置
   function wifiIosSetting(i){
	   var wifihtml = "";
	   wifihtml += '<div id="iosLuanPo" style="height:430px">';
	   wifihtml += '<ul id="iosImgs">';    
	   wifihtml += '<li id="ssidli'+i+'">';
	   // 服务级标识符
	   wifihtml += '<div class="form-group">';
	   wifihtml += '<div class="col-lg-3 control-label">';
	   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.serviceLevelIdentifier"/>(SSID)<span class="asterisk">*</span>';
	   wifihtml += '</div>';                                                  
	   wifihtml += '<div class="col-lg-7 control-label">';
	   wifihtml += '<input id="iosSsid'+i+'" name="iosSsid'+i+'" data-parsley-required="true"  data-parsley-maxlength="40" type="text" class="input-sm form-control"/>';
	   wifihtml += '</div>';  
	   wifihtml += '<div class="col-lg-2 control-label">';
	   wifihtml += '<i class="fa fa-plus-circle fa-2x cursor" onclick="plusIosSSID()"></i>&nbsp;&nbsp;<i class="fa fa-minus-circle fa-2x cursor" onclick="minusIosSSID('+i+')"></i>';
	   wifihtml += '</div>';  
	   wifihtml += '</div>'; 
	   // 自动加入
	 //  wifihtml += '<div class="form-group">';
	 //  wifihtml += '<div class="col-lg-3 control-label">';
	 //  wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.autojoin"/>';
	 //  wifihtml += '</div>';    
	 //  wifihtml += '<div class=" col-lg-1 control-label" style="text-align:left">';
	 //  wifihtml += '<input type="checkbox" id="autojoin'+i+'" name="autojoin'+i+'" checked="checked"/>';
	 //  wifihtml += '</div>';   
	 //  wifihtml += '</div>';
	   // 安全类型
	   wifihtml += '<div class="form-group">';
	   wifihtml += '<div class="col-lg-3 control-label">';
	   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.safetype"/>';
	   wifihtml += '</div>';    
	   wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
	   wifihtml += '<select id="saftyIosType'+i+'" class="form-control saftyType" onChange="onIosSaftyType('+i+')">';
	   wifihtml += '<option value="0"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.none"/></option>';
	   wifihtml += '<option value="1">WEP</option>';
	   wifihtml += '<option value="2">WPA/WPA2</option>';
	   wifihtml += '</select>';
	   wifihtml += '</div>'; ''
	   wifihtml += '<div class="col-lg-12" id="saftyIosTypeValue'+i+'" style="padding-left:5px;">'; 
	   wifihtml += '</div>';
	   wifihtml += '</div>';
	   // 代理
	   // wifihtml += '<div class="form-group">';
	   // wifihtml += '<div class="col-lg-3 control-label">';
	   // wifihtml += '<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.agent"/>';
	   // wifihtml += '</div>';    
	   // wifihtml += '<div class=" col-lg-1 control-label" style="text-align:left">';
	   // wifihtml += '<select id="agent'+i+'" class="form-control saftyType" onChange="onAgent('+i+')">';
	   // wifihtml += '<option value="0"><fmt:message key="tiles.views.institution.ios.device.policy.indexscript.none"/></option>';
	   // wifihtml += '<option value="1"><fmt:message key="tiles.views.institution.ios.device.policy.indexscript.manual"/></option>';
	   // wifihtml += '<option value="2"><fmt:message key="tiles.views.institution.ios.device.policy.indexscript.auto"/></option>';
	   // wifihtml += '</select>';
	   // wifihtml += '</div>';  
	   // wifihtml += '<div class="col-lg-12" id="agentValue'+i+'" style="padding-left:5px;">'; 	   
	   // wifihtml += '</div>';
	   // wifihtml += '</div>';   
	   wifihtml += '</li>';
	   wifihtml += '</ul>';    
	   wifihtml += '<ul id="iosNum">'; 
	   wifihtml += '<li class="current"></li>';   
	   wifihtml += '</ul>';   
	   wifihtml += '</div>';   
	   $("#tab13").html(wifihtml);
	   iosTab();
   }
   
   // 新增配置WIFI页面
   function wifiSetting(){
	   var wifihtml = "";
	   wifihtml += '<div id="luanpo" style="height:430px">';
	   wifihtml += '<ul id="imgs">';    
	   wifihtml += '<li id="ssidli">';
	   // 服务级标识符
	   wifihtml += '<div class="form-group">';
	   wifihtml += '<div class="col-lg-3 control-label">';
	   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.serviceLevelIdentifier"/>(SSID)<span class="asterisk">*</span>';
	   wifihtml += '</div>';    
	   wifihtml += '<div class="col-lg-7 control-label">';
	   wifihtml += '<input id="ssid" name="ssid" data-parsley-required="true" data-parsley-maxlength="40" type="text" class="input-sm form-control"/>';
	   wifihtml += '</div>';  
	   wifihtml += '<div class="col-lg-2 control-label">';
	   wifihtml += '<i class="fa fa-plus-circle fa-2x cursor" onclick="plusSSID()"></i>&nbsp;&nbsp;<i class="fa fa-minus-circle fa-2x cursor" onclick="minusSSID(0)"></i>';
	   wifihtml += '</div>';  
	   wifihtml += '</div>'; 
	   // 自动加入
	   wifihtml += '<div class="form-group">';
	   wifihtml += '<div class="col-lg-3 control-label">';
	   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.autojoin"/>';
	   wifihtml += '</div>';    
	   wifihtml += '<div class="col-lg-1 control-label" style="text-align:left">';
	   wifihtml += '<input type="checkbox" id="autojoin" name="autojoin" checked="checked"/>';
	   wifihtml += '</div>';   
	   wifihtml += '</div>';
	   // 安全类型
	   wifihtml += '<div class="form-group">';
	   wifihtml += '<div class="col-lg-3 control-label">';
	   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.safetype"/>';
	   wifihtml += '</div>';    
	   wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
	   wifihtml += '<select id="saftyType" class="form-control saftyType" onChange="onSaftyType(0)">';
	   wifihtml += '<option value="0"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.none"/></option>';
	   wifihtml += '<option value="1">WEP</option>';
	   wifihtml += '<option value="2">WPA/WPA2</option>';
	   wifihtml += '<option value="3"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.whatever"/></option>';
	   wifihtml += '<option value="4">802.1xEAP</option>';
	   wifihtml += '</select>';
	   wifihtml += '</div>'; 
	   wifihtml += '<div class="col-lg-12" id="saftyTypeValue" style="padding-left:5px;">'; 
	   wifihtml += '</div>';
	   wifihtml += '</div>';
	   wifihtml += '</li>';
	   wifihtml += '</ul>';    
	   wifihtml += '<ul id="num">'; 
	   wifihtml += '<li class="current"></li>';   
	   wifihtml += '</ul>';   
	   wifihtml += '</div>';   
	   $("#tab7").html(wifihtml);
	   tab();
   }

   //配置WIFI，增加一个SSID配置
   function plusSSID(){
	   var count=document.getElementById("imgs").getElementsByTagName("li").length;
	   var wifihtml = "";
	   var numhtml = "";
	   for(var i=0;i<=count;i++){
		   var ssidval = "";
		   var saftyType = "";
		   var wifikey = "";
		   var eap = "";
		   var stageAuthen = "";
		   var identity = "";
		   var anonymousIdentity = "";
		   if(i==0){
		       wifihtml += '<li id="ssidli">';
			   ssidval = $("#ssid").val();
			   saftyType = $("#saftyType").val();
			   wifikey = $("#wifikey").val();
			   eap = $("#eap").val();
			   stageAuthen = $("#stageAuthen").val();
			   identity = $("#identity").val();
			   anonymousIdentity = $("#anonymousIdentity").val();
		   } else if(i==count){
			   wifihtml += '<li id="ssidli'+i+'">';
			   saftyType = $("#saftyType"+i).val()
			   wifikey = $("#wifikey"+i).val();
			   eap = $("#eap"+i).val();
			   stageAuthen = $("#stageAuthen"+i).val();
			   identity = $("#identity").val();
			   anonymousIdentity = $("#anonymousIdentity").val();
		   } else {
			   wifihtml += '<li id="ssidli'+i+'">'; 
			   ssidval = $("#ssid"+i).val();
			   saftyType = $("#saftyType"+i).val()
			   wifikey = $("#wifikey"+i).val();
			   eap = $("#eap"+i).val();
			   stageAuthen = $("#stageAuthen"+i).val();
			   identity = $("#identity"+i).val();
			   anonymousIdentity = $("#anonymousIdentity"+i).val();
		   }
		   wifihtml += '<div class="form-group">';
		   wifihtml += '<div class="col-lg-3 control-label">';
		   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.serviceLevelIdentifier"/>(SSID)<span class="asterisk">*</span>';
		   wifihtml += '</div>';    
		   wifihtml += '<div class="col-lg-7 control-label">';
		   if(i==0){
			   wifihtml += '<input id="ssid" name="ssid" data-parsley-required="true"  data-parsley-maxlength="40" type="text" class="input-sm form-control" value="'+ssidval+'"/>'; 
		   } else {
			   wifihtml += '<input id="ssid'+i+'" name="ssid'+i+'" data-parsley-required="true"  data-parsley-maxlength="40" type="text" class="input-sm form-control" value="'+ssidval+'"/>';
		   }
		   wifihtml += '</div>';  
		   wifihtml += '<div class="col-lg-2 control-label">';
		   wifihtml += '<i class="fa fa-plus-circle fa-2x cursor" onclick="plusSSID()"></i>&nbsp;&nbsp;<i class="fa fa-minus-circle fa-2x cursor" onclick="minusSSID('+i+')"></i>';
		   wifihtml += '</div>';  
		   wifihtml += '</div>';   
		   wifihtml += '<div class="form-group">';
		   wifihtml += '<div class="col-lg-3 control-label">';
		   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.autojoin"/>';
		   wifihtml += '</div>';    
		   wifihtml += '<div class="col-lg-1 control-label" style="text-align:left">';
		   if(i==0){
			   if($('#autojoin').is(':checked')){
				   wifihtml += '<input type="checkbox" id="autojoin" name="autojoin" checked="checked"/>';  
			   } else {
				   wifihtml += '<input type="checkbox" id="autojoin" name="autojoin"/>';
			   }
		   } else if(i==count){
			   wifihtml += '<input type="checkbox" id="autojoin'+i+'" name="autojoin'+i+'" checked="checked"/>'; 
		   } else {
			   if($('#autojoin'+i).is(':checked')){
		          wifihtml += '<input type="checkbox" id="autojoin'+i+'" name="autojoin'+i+'" checked="checked"/>'; 
			   } else {
		          wifihtml += '<input type="checkbox" id="autojoin'+i+'" name="autojoin'+i+'"/>'
			   }
		   }
		   wifihtml += '</div>';   
		   wifihtml += '</div>';  
		   // 安全类型
		   wifihtml += '<div class="form-group">';
		   wifihtml += '<div class="col-lg-3 control-label">';
		   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.safetype"/>';
		   wifihtml += '</div>';    
		   wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
		   if(i==0){
			   wifihtml += '<select id="saftyType" class="form-control saftyType" onChange="onSaftyType('+i+')">';
		   } else {
			   wifihtml += '<select id="saftyType'+i+'" class="form-control saftyType" onChange="onSaftyType('+i+')">';
		   }
		   if(saftyType=="0"){
			   wifihtml += '<option value="0" selected="selected"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.none"/></option>';
		   } else {
			   wifihtml += '<option value="0"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.none"/></option>';
		   }
		   if(saftyType=="1"){
			   wifihtml += '<option value="1" selected="selected">WEP</option>';
		   } else {
			   wifihtml += '<option value="1">WEP</option>';
		   }
		   if(saftyType=="2"){
			   wifihtml += '<option value="2" selected="selected">WPA/WPA2</option>'; 
		   } else {
			   wifihtml += '<option value="2">WPA/WPA2</option>';  
		   }
		   if(saftyType=="3"){
			   wifihtml += '<option value="3" selected="selected"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.whatever"/></option>';
		   } else {
			   wifihtml += '<option value="3"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.whatever"/></option>';
		   }
		   if(saftyType=="4"){
			   wifihtml += '<option value="4" selected="selected">802.1xEAP</option>';
		   } else {
			   wifihtml += '<option value="4">802.1xEAP</option>';
		   }
		   wifihtml += '</select>';
		   wifihtml += '</div>';  
		   if(i==0){
			   wifihtml += '<div id="saftyTypeValue" style="padding-left:5px;">'; 
		   } else {
			   wifihtml += '<div id="saftyTypeValue'+i+'" style="padding-left:5px;">';
		   } 
           if(saftyType=="1"||saftyType=="2"||saftyType=="3"){
        	   wifihtml += '<div class="col-lg-3 control-label">';
        	   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.password"/>';
        	   wifihtml += '</div>';    
        	   wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
       		  if(i==0){
       			wifihtml += '<input id="wifikey" type="password" class="input-sm form-control" value="'+wifikey+'"/>';
       		  } else {
       			wifihtml += '<input id="wifikey'+i+'" type="password" class="input-sm form-control" value="'+wifikey+'"/>';
       		  }
       		  wifihtml += '</div>';
           }  else if(saftyType=="4"){
        	  wifihtml += '<div class="col-lg-3 control-label">';
        	  wifihtml += 'EAP<fmt:message key="tiles.views.institution.devicepolicy.indexscript.method"/>';
        	  wifihtml += '</div>';    
        	  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
    		  if(i==0){
    			  wifihtml += '<select id="eap" class="form-control saftyType">'; 
    		  } else {
    			  wifihtml += '<select id="eap'+i+'" class="form-control saftyType">';
    		  }
    		  if(eap=="0"){
    			  wifihtml += '<option value="0" selected="selected">PEAP</option>';
    			  wifihtml += '<option value="1">TLS</option>';
    			  wifihtml += '<option value="2">TTLS</option>';
    		  } else if(eap=="1"){
    			  wifihtml += '<option value="0">PEAP</option>';
    			  wifihtml += '<option value="1" selected="selected">TLS</option>';
    			  wifihtml += '<option value="2">TTLS</option>';
    		  } else if(eap=="2"){
    			  wifihtml += '<option value="0">PEAP</option>';
    			  wifihtml += '<option value="1">TLS</option>';
    			  wifihtml += '<option value="2" selected="selected">TTLS</option>';
    		  }
    		  wifihtml += '</select>';
    		  wifihtml += '</div>';
    		  wifihtml += '<div class="col-lg-3 control-label">';
    		  wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.stage2authentication"/>';
    		  wifihtml += '</div>';    
    		  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
    		  if(i==0){
    			  wifihtml += '<select id="stageAuthen" class="form-control saftyType">';
    		  } else {
    			  wifihtml += '<select id="stageAuthen'+i+'" class="form-control saftyType">';
    		  }
    		  wifihtml += '<option value="0"';
    		  if(stageAuthen=="0"){
    			  wifihtml += ' selected="selected"';
    		  } 
    		  wifihtml +=' >NONE</option>';
    		  wifihtml += '<option value="1"';
    		  if(stageAuthen=="1"){
    			  wifihtml += ' selected="selected"';
    		  } 
    		  wifihtml +=' >PAP</option>';
    		  wifihtml += '<option value="2"';
    		  if(stageAuthen=="2"){
    			  wifihtml += ' selected="selected"';
    		  }
    		  wifihtml +=' >MSCHAP</option>';
    		  wifihtml += '<option value="3"';
    		  if(stageAuthen=="3"){
    			  wifihtml += ' selected="selected"';
    		  } 
    		  wifihtml +=' >MSCHAPv2</option>';
    		  wifihtml += '<option value="4"';
    		  if(stageAuthen=="4"){
    			  wifihtml += ' selected="selected"';
    		  }
    		  wifihtml +=' >GTC</option>';
    		  wifihtml += '</select>'; 
    		  wifihtml += '</div>';  		  
    		  wifihtml += '<div class="col-lg-3 control-label">';
    		  wifihtml += 'CA<fmt:message key="tiles.views.institution.devicepolicy.indexscript.certificate"/>';
    		  wifihtml += '</div>';    
    		  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
    		  if(i==0){
    			  wifihtml += '<select id="ca" class="form-control saftyType">';
    		  } else {
    			  wifihtml += '<select id="ca'+i+'" class="form-control saftyType">';
    		  }
    		  
    		  wifihtml += '<option value="0"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.nospecify"/></option>';
    		  wifihtml += '</select>';	
    		  wifihtml += '</div>';
    		  
    		  wifihtml += '<div class="col-lg-3 control-label">';
    		  wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.userCertificate"/>';
    		  wifihtml += '</div>';    
    		  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
    		  if(i==0){
    			  wifihtml += '<select id="user" class="form-control saftyType">';
    		  } else {
    			  wifihtml += '<select id="user'+i+'" class="form-control saftyType">';
    		  }
    		  wifihtml += '<option value="0"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.nospecify"/></option>';
    		  wifihtml += '</select>';   
    		  wifihtml += '</div>';
    		  wifihtml += '<div class="col-lg-3 control-label">';
    		  wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.identity"/>';
    		  wifihtml += '</div>';    
    		  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
    		  if(i==0){
    			  wifihtml += '<input id="identity" type="text" class="input-sm form-control" value="'+identity+'"/>'; 
    		  } else {
    			  wifihtml += '<input id="identity'+i+'" type="text" class="input-sm form-control" value="'+identity+'"/>';
    		  }
    		  wifihtml += '</div>';  
    		  wifihtml += '<div class="col-lg-3 control-label">';
    		  wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.anonymousIdentity"/>';
    		  wifihtml += '</div>';    
    		  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
    		  if(i==0){
    			  wifihtml += '<input id="anonymousIdentity" type="text" class="input-sm form-control" value="'+anonymousIdentity+'"/>'; 
    		  } else {
    			  wifihtml += '<input id="anonymousIdentity'+i+'" type="text" class="input-sm form-control" value="'+anonymousIdentity+'"/>';
    		  }
    		  wifihtml += '</div>';
    		  wifihtml += '<div class="col-lg-3 control-label">';
    		  wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.password"/>';
    		  wifihtml += '</div>';    
    		  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
    		  if(i==0){
    			  wifihtml += '<input id="wifikey" type="password" class="input-sm form-control" value="'+wifikey+'"/>';
    		  } else {
    			  wifihtml += '<input id="wifikey'+i+'" type="password" class="input-sm form-control" value="'+wifikey+'"/>'; 
    		  }
    		  wifihtml += '</div>';
           }
		  
		   wifihtml += '</div>';
		   wifihtml += '</div>';
		   wifihtml += '</li>';
		   if(i==count){
			   numhtml += '<li class="current"></li>';
		   } else {
			   numhtml += '<li class="old"></li>';
		   }
	   }
	   $("#num").html(numhtml);
	   $("#imgs").html(wifihtml);
	   tab();
	   var imgs=document.getElementById("imgs").getElementsByTagName("li");
	   var oimg=document.getElementById("imgs");
	   var left = -(imgs[0].offsetWidth*(imgs.length-1))+"px";
	   oimg.style.left=left;   
   }
   
   // IOS策略 WIFI配置
   function plusIosSSID(){
	   var count=document.getElementById("iosImgs").getElementsByTagName("li").length;
	   var wifihtml = "";
	   var numhtml = "";
	   var iosSsid = "";
	   var autojoin = "";
	   var saftyIosType = "";
	   var agent = "";
	   var wifiIoskey = "";
	   var agentServer = "";
	   var agentPort = "";
	   var agentAppraisal  = "";
	   var agentPassword = "";
	   var agentUrl = "";	
	   for(var i=0;i<=count;i++){
		   iosSsid = $("#iosSsid"+i).val();
		   if(iosSsid==undefined){
			   iosSsid = "";
		   }
		   autojoin = $("input[name='autojoin"+i+"']:checked").val();
		   if(autojoin==undefined){
			   autojoin = 1;
		   } else {
			   if(autojoin=="on"){
				   autojoin = 1;
			   } else {
				   autojoin = 0;
			   }
		   }
		   saftyIosType = $("#saftyIosType"+i).val();
		   if(saftyIosType==undefined){
			   saftyIosType = 0;
		   }
		   wifiIoskey = $("#wifiIoskey"+i).val();
		   if(wifiIoskey==undefined){
			   wifiIoskey = "";
		   }
		   agent = $("#agent"+i).val();
		   if(agent==undefined){
			   agent = "";
		   }
		   agentServer = $("#agentServer"+i).val();
		   if(agentServer==undefined){
			   agentServer = "";
		   }
		   agentPort = $("#agentPort"+i).val();
		   if(agentPort==undefined){
			   agentPort = "";
		   }
		   agentAppraisal = $("#agentAppraisal"+i).val();
		   if(agentAppraisal==undefined){
			   agentAppraisal = "";
		   }
		   agentPassword = $("#agentPassword"+i).val();
		   if(agentPassword==undefined){
			   agentPassword = "";
		   }
		   agentUrl = $("#agentUrl"+i).val();
		   if(agentUrl==undefined){
			   agentUrl = "";
		   }
		   wifihtml += '<li id="ssidli'+i+'">';
		   // 服务级标识符
		   wifihtml += '<div class="form-group">';
		   wifihtml += '<div class="col-lg-3 control-label">';
		   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.serviceLevelIdentifier"/>(SSID)<span class="asterisk">*</span>';
		   wifihtml += '</div>';    
		   wifihtml += '<div class="col-lg-7 control-label">';
		   wifihtml += '<input id="iosSsid'+i+'" name="iosSsid'+i+'" data-parsley-required="true"  type="text" class="input-sm form-control" value=\''+iosSsid+'\' />';
		   wifihtml += '</div>';  
		   wifihtml += '<div class="col-lg-2 control-label">';
		   wifihtml += '<i class="fa fa-plus-circle fa-2x cursor" onclick="plusIosSSID()"></i>&nbsp;&nbsp;<i class="fa fa-minus-circle fa-2x cursor" onclick="minusIosSSID('+i+')"></i>';
		   wifihtml += '</div>';  
		   wifihtml += '</div>'; 
		   // 自动加入
/* 		   wifihtml += '<div class="form-group">';
		   wifihtml += '<div class="col-lg-3 control-label">';
		   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.autojoin"/>';
		   wifihtml += '</div>';    
		   wifihtml += '<div class="col-lg-1 control-label" style="text-align:left">';
		   wifihtml += '<input type="checkbox" id="autojoin'+i+'" name="autojoin'+i+'"';
		   if(autojoin==1){
		       wifihtml += 'checked="checked"';
		   }
		   wifihtml += ' />';
		   wifihtml += '</div>';   
		   wifihtml += '</div>'; */

	       // 安全类型
		   wifihtml += '<div class="form-group">';
		   wifihtml += '<div class="col-lg-3 control-label">';
		   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.safetype"/>';
		   wifihtml += '</div>';    
		   wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
		   wifihtml += '<select id="saftyIosType'+i+'" class="form-control saftyType" onChange="onIosSaftyType('+i+')">';
		   wifihtml += '<option value="0"';
		   if(saftyIosType==0){
			   wifihtml += ' selected="selected"'; 
		   }
		   wifihtml += ' ><fmt:message key="tiles.views.institution.devicepolicy.indexscript.none"/></option>';
		   wifihtml += '<option value="1" ';
		   if(saftyIosType==1){
		      wifihtml += ' selected="selected"';
		   }
		   wifihtml += ' >WEP</option>';
		   wifihtml += '<option value="2" ';
		   if(saftyIosType==2){
			   wifihtml += ' selected="selected"';
		   }
		   wifihtml += '>WPA/WPA2</option>';
		   wifihtml += '</select>';
		   wifihtml += '</div>';
		   wifihtml += '<div class="col-lg-12" id="saftyIosTypeValue'+i+'" style="padding-left:5px;">'; 
		   if(saftyIosType==1||saftyIosType==2){
			   wifihtml += '<div class="col-lg-3 control-label">';
			   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.password"/>';
			   wifihtml += '</div>';    
			   wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
			   wifihtml += '<input id="wifiIoskey'+i+'" type="password" class="input-sm form-control" value=\''+wifiIoskey+'\' />';
			   wifihtml += '</div>'; 
		   } 
		   wifihtml += '</div>';
		   wifihtml += '</div>';
		   // 代理
/* 		   wifihtml += '<div class="form-group">';
		   wifihtml += '<div class="col-lg-3 control-label">';
		   wifihtml += '<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.agent"/>';
		   wifihtml += '</div>';    
		   wifihtml += '<div class=" col-lg-1 control-label" style="text-align:left">';
		   wifihtml += '<select id="agent'+i+'" class="form-control saftyType" onChange="onAgent('+i+')">';
		   wifihtml += '<option value="0"';
		   if(agent==0){
			   wifihtml += ' selected="selected"'; 
		   }
		   wifihtml += ' ><fmt:message key="tiles.views.institution.ios.device.policy.indexscript.none"/></option>';
		   wifihtml += '<option value="1"';
		   if(agent==1){
			   wifihtml += ' selected="selected"'; 
		   }
		   wifihtml += '><fmt:message key="tiles.views.institution.ios.device.policy.indexscript.manual"/></option>';
		   wifihtml += '<option value="2"';
		   if(agent==2){
			   wifihtml += ' selected="selected"'; 
		   }
		   wifihtml += '><fmt:message key="tiles.views.institution.ios.device.policy.indexscript.auto"/></option>';
		   wifihtml += '</select>';
		   wifihtml += '</div>';  
		   wifihtml += '<div class="col-lg-12" id="agentValue'+i+'" style="padding-left:5px;">'; 
		   if(agent==1){
			  wifihtml += '<div class="col-lg-3 control-label">';
			  wifihtml += '<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.serverandport"/>';
			  wifihtml += '</div>';    
			  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
			  wifihtml += '<input id="agentServer'+i+'" type="text" class="input-sm form-control" value=\''+agentServer+'\' />:';
			  wifihtml += '<input id="agentPort'+i+'" type="text" class="input-sm form-control" value=\''+agentPort+'\'/>';
			  wifihtml += '</div>'; 
			   // 鉴定
			  wifihtml += '<div class="col-lg-3 control-label">';
			  wifihtml += '<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.identification"/>';
			  wifihtml += '</div>';    
			  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
			  wifihtml += '<input id="agentAppraisal'+i+'" type="text" class="input-sm form-control" value=\''+agentAppraisal+'\' />';
			  wifihtml += '</div>'; 
			  // 密码
			  wifihtml += '<div class="col-lg-3 control-label">';
			  wifihtml += '<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.password"/>';
			  wifihtml += '</div>';    
			  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
			  wifihtml += '<input id="agentPassword'+i+'" type="password" class="input-sm form-control" value=\''+agentPassword+'\' />';
			  wifihtml += '</div>'; 
		   } else if(agent==2){
			   wifihtml += '<div class="col-lg-3 control-label">';
			   wifihtml += '<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.agentserver"/>URL';
			   wifihtml += '</div>';    
			   wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
			   wifihtml += '<input id="agentUrl'+i+'" type="text" class="input-sm form-control" value=\''+agentUrl+'\' />';
			   wifihtml += '</div>';
		   }
		   wifihtml += '</div>'; */
		   wifihtml += '</div>';
		   wifihtml += '</li>';
		   if(i==count){
			   numhtml += '<li class="current"></li>';
		   } else {
			   numhtml += '<li class="old"></li>';
		   }
	   }
	   $("#iosNum").html(numhtml);
	   $("#iosImgs").html(wifihtml);
	   iosTab();
	   var imgs=document.getElementById("iosImgs").getElementsByTagName("li");
	   var oimg=document.getElementById("iosImgs");
	   var left = -(imgs[0].offsetWidth*(imgs.length-1))+"px";
	   oimg.style.left=left; 
   }

   // 配置WIFI，减少其中一个SSID配置
   function minusIosSSID(tag){
	   $("#ssidli"+tag).remove();
	   var count=document.getElementById("iosImgs").getElementsByTagName("li").length;
	   var wifihtml = "";
	   var numhtml = "";
	   if(count!=0){
		   var iosSsid = "";
		   var autojoin = "";
		   var saftyIosType = "";
		   var agent = "";
		   var wifiIoskey = "";
		   var agentServer = "";
		   var agentPort = "";
		   var agentAppraisal  = "";
		   var agentPassword = "";
		   var agentUrl = "";	

		   var val = 0;
		   for(var i=0;i<count;i++){
               if(i>=tag){
            	   val = i+1;
               } else {
            	   val = i;
               }
    		   iosSsid = $("#iosSsid"+val).val();
    		   if(iosSsid==undefined){
    			   iosSsid = "";
    		   }
    		   autojoin = $("input[name='autojoin"+val+"']:checked").val();
    		   if(autojoin=="on"){
    			   autojoin = 1;
    		   } else {
    			   autojoin = 0;
    		   }
    		   saftyIosType = $("#saftyIosType"+val).val();
    		   if(saftyIosType==undefined){
    			   saftyIosType = 0;
    		   }
    		   wifiIoskey = $("#wifiIoskey"+val).val();
    		   if(wifiIoskey==undefined){
    			   wifiIoskey = "";
    		   }
    		   agent = $("#agent"+val).val();
    		   if(agent==undefined){
    			   agent = "";
    		   }
    		   agentServer = $("#agentServer"+val).val();
    		   if(agentServer==undefined){
    			   agentServer = "";
    		   }
    		   agentPort = $("#agentPort"+val).val();
    		   if(agentPort==undefined){
    			   agentPort = "";
    		   }
    		   agentAppraisal = $("#agentAppraisal"+val).val();
    		   if(agentAppraisal==undefined){
    			   agentAppraisal = "";
    		   }
    		   agentPassword = $("#agentPassword"+val).val();
    		   if(agentPassword==undefined){
    			   agentPassword = "";
    		   }
    		   agentUrl = $("#agentUrl"+val).val();
    		   if(agentUrl==undefined){
    			   agentUrl = "";
    		   }
    		   wifihtml += '<li id="ssidli'+i+'">';
    		   // 服务级标识符
    		   wifihtml += '<div class="form-group">';
    		   wifihtml += '<div class="col-lg-3 control-label">';
    		   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.serviceLevelIdentifier"/>(SSID)<span class="asterisk">*</span>';
    		   wifihtml += '</div>';    
    		   wifihtml += '<div class="col-lg-7 control-label">';
    		   wifihtml += '<input id="iosSsid'+i+'" name="iosSsid'+i+'" data-parsley-required="true"  type="text" class="input-sm form-control" value=\''+iosSsid+'\' />';
    		   wifihtml += '</div>';  
    		   wifihtml += '<div class="col-lg-2 control-label">';
    		   wifihtml += '<i class="fa fa-plus-circle fa-2x cursor" onclick="plusIosSSID()"></i>&nbsp;&nbsp;<i class="fa fa-minus-circle fa-2x cursor" onclick="minusIosSSID('+i+')"></i>';
    		   wifihtml += '</div>';  
    		   wifihtml += '</div>'; 
    		   // 自动加入
/*     		   wifihtml += '<div class="form-group">';
    		   wifihtml += '<div class="col-lg-3 control-label">';
    		   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.autojoin"/>';
    		   wifihtml += '</div>';    
    		   wifihtml += '<div class="col-lg-1 control-label" style="text-align:left">';
    		   wifihtml += '<input type="checkbox" id="autojoin'+i+'" name="autojoin'+i+'"';
    		   if(autojoin==1){
    		       wifihtml += 'checked="checked"';
    		   }
    		   wifihtml += ' />';
    		   wifihtml += '</div>';   
    		   wifihtml += '</div>'; */

    	       // 安全类型
    		   wifihtml += '<div class="form-group">';
    		   wifihtml += '<div class="col-lg-3 control-label">';
    		   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.safetype"/>';
    		   wifihtml += '</div>';    
    		   wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
    		   wifihtml += '<select id="saftyIosType'+i+'" class="form-control saftyType" onChange="onIosSaftyType('+i+')">';
    		   wifihtml += '<option value="0"';
    		   if(saftyIosType==0){
    			   wifihtml += ' selected="selected"'; 
    		   }
    		   wifihtml += ' ><fmt:message key="tiles.views.institution.devicepolicy.indexscript.none"/></option>';
    		   wifihtml += '<option value="1" ';
    		   if(saftyIosType==1){
    		      wifihtml += ' selected="selected"';
    		   }
    		   wifihtml += ' >WEP</option>';
    		   wifihtml += '<option value="2" ';
    		   if(saftyIosType==2){
    			   wifihtml += ' selected="selected"';
    		   }
    		   wifihtml += '>WPA/WPA2</option>';
    		   wifihtml += '</select>';
    		   wifihtml += '</div>';
    		   wifihtml += '<div class="col-lg-12" id="saftyIosTypeValue'+i+'" style="padding-left:5px;">'; 
    		   if(saftyIosType==1||saftyIosType==2){
    			   wifihtml += '<div class="col-lg-3 control-label">';
    			   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.password"/>';
    			   wifihtml += '</div>';    
    			   wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
    			   wifihtml += '<input id="wifiIoskey'+i+'" type="password" class="input-sm form-control" value=\''+wifiIoskey+'\' />';
    			   wifihtml += '</div>'; 
    		   } 
    		   wifihtml += '</div>';
    		   wifihtml += '</div>';
    		   // 代理
/*     		   wifihtml += '<div class="form-group">';
    		   wifihtml += '<div class="col-lg-3 control-label">';
    		   wifihtml += '<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.agent"/>';
    		   wifihtml += '</div>';    
    		   wifihtml += '<div class=" col-lg-1 control-label" style="text-align:left">';
    		   wifihtml += '<select id="agent'+i+'" class="form-control saftyType" onChange="onAgent('+i+')">';
    		   wifihtml += '<option value="0"';
    		   if(agent==0){
    			   wifihtml += ' selected="selected"'; 
    		   }
    		   wifihtml += ' ><fmt:message key="tiles.views.institution.ios.device.policy.indexscript.none"/></option>';
    		   wifihtml += '<option value="1"';
    		   if(agent==1){
    			   wifihtml += ' selected="selected"'; 
    		   }
    		   wifihtml += '><fmt:message key="tiles.views.institution.ios.device.policy.indexscript.manual"/></option>';
    		   wifihtml += '<option value="2"';
    		   if(agent==2){
    			   wifihtml += ' selected="selected"'; 
    		   }
    		   wifihtml += '><fmt:message key="tiles.views.institution.ios.device.policy.indexscript.auto"/></option>';
    		   wifihtml += '</select>';
    		   wifihtml += '</div>';  
    		   wifihtml += '<div class="col-lg-12" id="agentValue'+i+'" style="padding-left:5px;">'; 
    		   if(agent==1){
    			  wifihtml += '<div class="col-lg-3 control-label">';
    			  wifihtml += '<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.serverandport"/>';
    			  wifihtml += '</div>';    
    			  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
    			  wifihtml += '<input id="agentServer'+i+'" type="text" class="input-sm form-control" value=\''+agentServer+'\' />:';
    			  wifihtml += '<input id="agentPort'+i+'" type="text" class="input-sm form-control" value=\''+agentPort+'\'/>';
    			  wifihtml += '</div>'; 
    			   // 鉴定
    			  wifihtml += '<div class="col-lg-3 control-label">';
    			  wifihtml += '<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.identification"/>';
    			  wifihtml += '</div>';    
    			  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
    			  wifihtml += '<input id="agentAppraisal'+i+'" type="text" class="input-sm form-control" value=\''+agentAppraisal+'\' />';
    			  wifihtml += '</div>'; 
    			  // 密码
    			  wifihtml += '<div class="col-lg-3 control-label">';
    			  wifihtml += '<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.password"/>';
    			  wifihtml += '</div>';    
    			  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
    			  wifihtml += '<input id="agentPassword'+i+'" type="password" class="input-sm form-control" value=\''+agentPassword+'\' />';
    			  wifihtml += '</div>'; 
    		   } else if(agent==2){
    			   wifihtml += '<div class="col-lg-3 control-label">';
    			   wifihtml += '<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.agentserver"/>URL';
    			   wifihtml += '</div>';    
    			   wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
    			   wifihtml += '<input id="agentUrl'+i+'" type="text" class="input-sm form-control" value=\''+agentUrl+'\' />';
    			   wifihtml += '</div>';
    		   }
    		   wifihtml += '</div>';
    		   wifihtml += '</div>'; */
    		   wifihtml += '</li>';
    		   if(i==count){
    			   numhtml += '<li class="current"></li>';
    		   } else {
    			   numhtml += '<li class="old"></li>';
    		   }
		   }
		   $("#iosNum").html(numhtml);
	   } else {
		   $("#iosNum").html("");
		   $("#iosImgs").html("");
		   $("#tab13").html("");
		   wifiNoIosWebClipSetting(1);
	   }
	   iosTab();
	   var imgs=document.getElementById("iosImgs").getElementsByTagName("li");
	   var oimg=document.getElementById("iosImgs");
	   var left = -(imgs[0].offsetWidth*(imgs.length-1))+"px";
	   oimg.style.left=left;
   }
   
   // 配置WIFI，减少其中一个SSID配置
   function minusSSID(tag){
	   $("#ssidli"+tag).remove();
	   var count=document.getElementById("imgs").getElementsByTagName("li").length;
	   var wifihtml = "";
	   var numhtml = "";
	   if(count!=0){
		   var wifihtml = "";
		   var numhtml = "";
		   var webClipName = "";
		   var webClipUrl = "";
		   var iconPath = "";
		   var isRemove = "";
		   var precomposeIcon = "";
		   var fullScreen = "";
		   var val = 0;
		   for(var i=0;i<count;i++){
               if(i>=tag){
            	   val = i+1;
               } else {
            	   val = i;
               }
    		   webClipName = $("#webClipName"+val).val();
    		   if(webClipName==undefined){
    			   webClipName = "";
    		   }
    		   webClipUrl = $("#webClipUrl"+val).val();
    		   if(webClipUrl==undefined){
    			   webClipUrl = "";
    		   }
    		   iconPath = $("#iconPath"+val).val();
    		   if(iconPath==undefined){
    			   iconPath = "";
    		   }
    		   isRemove = $("input[name='isRemove"+val+"']:checked").val();
    		   if(isRemove=="on"){
    			   isRemove = 1;
    		   } else {
    			   isRemove = 0;
    		   }
    		   precomposeIcon = $("input[name='precomposeIcon"+val+"']:checked").val();
    		   if(precomposeIcon=="on"){
    			   precomposeIcon = 1;
    		   } else {
    			   precomposeIcon = 0;
    		   }
    		   fullScreen = $("input[name='fullScreen"+val+"']:checked").val();
    		   if(fullScreen=="on"){
    			   fullScreen = 1;
    		   } else {
    			   fullScreen = 0;
    		   }
    		   wifihtml += '<li id="ssidli'+i+'">';
    		   wifihtml += '<div class="form-group marginBottom">';
    		   wifihtml += '<div class="col-lg-12">';
    		   wifihtml += '<div class="col-lg-3 control-label">';
    		   wifihtml += '<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.lable"/><span class="asterisk">*</span>';
    		   wifihtml += '</div>';    
    		   wifihtml += '<div class="col-lg-7 control-label">';
    		   wifihtml += '<input type="text" id="webClipName'+i+'" value="'+webClipName+'" class="form-control m-b" data-parsley-required="true"/>';
    		   wifihtml += '</div>';  
    		   wifihtml += '<div class="col-lg-2 control-label">';
    		   wifihtml += '<i class="fa fa-plus-circle fa-2x cursor" onclick="plusWebClipSSID()"></i>&nbsp;&nbsp;<i class="fa fa-minus-circle fa-2x cursor" onclick="minusSSID('+i+')"></i>';
    		   wifihtml += '</div>';  
    		   wifihtml += '</div>'; 
    		   wifihtml += '</div>'; 
    		   
    		   // url
    		   wifihtml += '<div class="form-group marginBottom">';
    		   wifihtml += '<div class="col-lg-12">';
    		   wifihtml += '<div class="col-lg-3 control-label">';
    		   wifihtml += 'URL<span class="asterisk">*</span>';
    		   wifihtml += '</div>';    
    		   wifihtml += '<div class="col-lg-7 control-label">';
    		   wifihtml += '<input type="text" id="webClipUrl'+i+'" value="'+webClipUrl+'" class="form-control m-b" data-parsley-required="true"/>';
    		   wifihtml += '</div>';  
    		   wifihtml += '</div>';
    		   wifihtml += '</div>';
    		   
    		   // 图标
    		   wifihtml += '<div class="form-group marginBottom">';
    		   wifihtml += '<div class="col-lg-12">';
    		   wifihtml += '<div class="col-lg-3 control-label">';
    		   wifihtml += '<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.icon"/>';
    		   wifihtml += '</div>';    
    		   wifihtml += '<div class="col-lg-7">';
    		   wifihtml += '<input type="hidden" id="iconPath'+i+'" value="'+iconPath+'"/>';
    		   wifihtml += '<img src="'+ctx+'/resources/images/upload.png" width="80px" height="80" />';
    		   wifihtml += '</div>';  
    		   wifihtml += '</div>';
    		   
    		   // 可删除
    		   wifihtml += '<div class="col-lg-12">';
    		   wifihtml += '<div class="col-lg-3 control-label">';
    		   wifihtml += '</div>';
    		   wifihtml += '&nbsp;&nbsp;&nbsp;<input type="checkbox" name="isRemove'+i+'" id="isRemove'+i+'"';
    		   if(isRemove==1){
    			   wifihtml += ' checked="checked"';
    		   }
    		   wifihtml += ' />&nbsp;<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.candelete"/>';
    		   wifihtml += '</div>';
    		   // 预作
    		   wifihtml += '<div class="col-lg-12">';
    		   wifihtml += '<div class="col-lg-3 control-label">';
    		   wifihtml += '</div>';
    		   wifihtml += '&nbsp;&nbsp;&nbsp;<input type="checkbox" name="precomposeIcon'+i+'" id="precomposeIcon'+i+'"';
    		   if(precomposeIcon==1){
    			   wifihtml += ' checked="checked"';
    		   }
    		   wifihtml += ' />&nbsp;<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.precomposeicon"/>';
    		   wifihtml += '</div>';
    		   // 全屏
    		   wifihtml += '<div class="col-lg-12">';
    		   wifihtml += '<div class="col-lg-3 control-label">';
    		   wifihtml += '</div>';
    		   wifihtml += '&nbsp;&nbsp;&nbsp;<input type="checkbox" name="fullScreen'+i+'" id="fullScreen'+i+'"';
    		   if(fullScreen==1){
    			   wifihtml += ' checked="checked"';
    		   }
    		   wifihtml += '/>&nbsp;<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.fullscreen"/>';
    		   wifihtml += '</div>';
    		   wifihtml += '</li>';
			   if(i==(count-1)){
				   numhtml += '<li class="current"></lis>';
			   } else {
				   numhtml += '<li class="old"></li>';
			   }
		   }
		   $("#num").html(numhtml);
	   } else {
		   $("#num").html("");
		   $("#imgs").html("");
		   $("#tab14").html("");
		   webClipSetting(0);
	   }
	   tab();
	   var imgs=document.getElementById("imgs").getElementsByTagName("li");
	   var oimg=document.getElementById("imgs");
	   var left = -(imgs[0].offsetWidth*(imgs.length-1))+"px";
	   oimg.style.left=left;
   }
   
   //配置WIFI，增加一个SSID配置
   function plusWebClipSSID(){
	   var count=document.getElementById("webClipImgs").getElementsByTagName("li").length;
	   var wifihtml = "";
	   var numhtml = "";
	   var webClipName = "";
	   var webClipUrl = "";
	   var iconPath = "";
	   var isRemove = "";
	   var precomposeIcon = "";
	   var fullScreen = "";
	   for(var i=0;i<=count;i++){
		   webClipName = $("#webClipName"+i).val();
		   if(webClipName==undefined){
			   webClipName = "";
		   }
		   webClipUrl = $("#webClipUrl"+i).val();
		   if(webClipUrl==undefined){
			   webClipUrl = "";
		   }
		   iconPath = $("#iconPath"+i).val();
		   if(iconPath==undefined){
			   iconPath = "";
		   }
		   isRemove = $("input[name='isRemove"+i+"']:checked").val();
		   if(isRemove=="on"){
			   isRemove = 1;
		   } else {
			   isRemove = 0;
		   }
		   precomposeIcon = $("input[name='precomposeIcon"+i+"']:checked").val();
		   if(precomposeIcon=="on"){
			   precomposeIcon = 1;
		   } else {
			   precomposeIcon = 0;
		   }
		   fullScreen = $("input[name='fullScreen"+i+"']:checked").val();
		   if(fullScreen=="on"){
			   fullScreen = 1;
		   } else {
			   fullScreen = 0;
		   }
		   
		   wifihtml += '<li id="ssidli'+i+'">';
		   wifihtml += '<div class="form-group marginBottom">';
		   wifihtml += '<div class="col-lg-12">';
		   wifihtml += '<div class="col-lg-3 control-label">';
		   wifihtml += '<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.lable"/><span class="asterisk">*</span>';
		   wifihtml += '</div>';    
		   wifihtml += '<div class="col-lg-7 control-label">';
		   wifihtml += '<input type="text" id="webClipName'+i+'" value="'+webClipName+'" class="form-control m-b" data-parsley-required="true"/>';
		   wifihtml += '</div>';  
		   wifihtml += '<div class="col-lg-2 control-label">';
		   wifihtml += '<i class="fa fa-plus-circle fa-2x cursor" onclick="plusWebClipSSID()"></i>&nbsp;&nbsp;<i class="fa fa-minus-circle fa-2x cursor" onclick="minusWebClipSSID('+i+')"></i>';
		   wifihtml += '</div>';  
		   wifihtml += '</div>'; 
		   wifihtml += '</div>'; 
		   
		   // url
		   wifihtml += '<div class="form-group marginBottom">';
		   wifihtml += '<div class="col-lg-12">';
		   wifihtml += '<div class="col-lg-3 control-label">';
		   wifihtml += 'URL<span class="asterisk">*</span>';
		   wifihtml += '</div>';    
		   wifihtml += '<div class="col-lg-7 control-label">';
		   wifihtml += '<input type="text" id="webClipUrl'+i+'" value="'+webClipUrl+'" class="form-control m-b" data-parsley-required="true"/>';
		   wifihtml += '</div>';  
		   wifihtml += '</div>';
		   wifihtml += '</div>';
		   
		   // 图标
		   wifihtml += '<div class="form-group marginBottom">';
		   wifihtml += '<div class="col-lg-12">';
		   wifihtml += '<div class="col-lg-3 control-label">';
		   wifihtml += '<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.icon"/>';
		   wifihtml += '</div>';    
		   wifihtml += '<div class="col-lg-7">';
		   wifihtml += '<input type="hidden" id="iconPath'+i+'" value="'+iconPath+'"/>';
		   wifihtml += '<img src="'+ctx+'/resources/images/upload.png" id="imgPath'+i+'" onclick="uploadIconModal('+i+')" width="80px" height="80" />';
		   wifihtml += '</div>';  
		   wifihtml += '</div>';
		   
		   // 可删除
		   wifihtml += '<div class="col-lg-12">';
		   wifihtml += '<div class="col-lg-3 control-label">';
		   wifihtml += '</div>';
		   
		   wifihtml += '&nbsp;&nbsp;&nbsp;<input type="checkbox" name="isRemove'+i+'" id="isRemove'+i+'"';
		   if(isRemove==1){
			   wifihtml += ' checked="checked"';
		   } 
		   wifihtml += ' />&nbsp;<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.candelete"/>'; 
		   wifihtml += '</div>';
		   // 预作
		   wifihtml += '<div class="col-lg-12">';
		   wifihtml += '<div class="col-lg-3 control-label">';
		   wifihtml += '</div>';
		   wifihtml += '&nbsp;&nbsp;&nbsp;<input type="checkbox" name="precomposeIcon'+i+'" id="precomposeIcon'+i+'"';
		   if(precomposeIcon==1){
			   wifihtml += ' checked="checked"';
		   }
		   wifihtml += ' />&nbsp;<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.precomposeicon"/>';
		   wifihtml += '</div>';
		   // 全屏
		   wifihtml += '<div class="col-lg-12">';
		   wifihtml += '<div class="col-lg-3 control-label">';
		   wifihtml += '</div>';
		   wifihtml += '&nbsp;&nbsp;&nbsp;<input type="checkbox" name="fullScreen'+i+'" id="fullScreen'+i+'"';
		   if(fullScreen==1){
			   wifihtml += ' checked="checked"';
		   }
		   wifihtml += ' />&nbsp;<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.fullscreen"/>';
		   wifihtml += '</div>';
		   wifihtml += '</li>';
		   if(i==count){
			   numhtml += '<li class="current"></li>';
		   } else {
			   numhtml += '<li class="old"></li>';
		   }
	   }

	   $("#webClipnum").html(numhtml);
	   $("#webClipImgs").html(wifihtml);
	   webClipTab();
	   var webImgs=document.getElementById("webClipImgs").getElementsByTagName("li");
	   var oWebClipImg=document.getElementById("webClipImgs");
	   var left = -(webImgs[0].offsetWidth*(webImgs.length-1))+"px";
	   oWebClipImg.style.left=left; 
   }
   
   // 配置WIFI，减少其中一个SSID配置
   function minusWebClipSSID(tag){
	   $("#ssidli"+tag).remove();
	   var count=document.getElementById("webClipImgs").getElementsByTagName("li").length;
	   var wifihtml = "";
	   var numhtml = "";
	   if(count!=0){
		   var wifihtml = "";
		   var numhtml = "";
		   var webClipName = "";
		   var webClipUrl = "";
		   var iconPath = "";
		   var isRemove = "";
		   var precomposeIcon = "";
		   var fullScreen = "";
		   var val = 0;
		   for(var i=0;i<count;i++){
               if(i>=tag){
            	   val = i+1;
               } else {
            	   val = i;
               }
    		   webClipName = $("#webClipName"+val).val();
    		   if(webClipName==undefined){
    			   webClipName = "";
    		   }
    		   webClipUrl = $("#webClipUrl"+val).val();
    		   if(webClipUrl==undefined){
    			   webClipUrl = "";
    		   }
    		   iconPath = $("#iconPath"+val).val();
    		   if(iconPath==undefined){
    			   iconPath = "";
    		   }
    		   isRemove = $("input[name='isRemove"+val+"']:checked").val();
    		   if(isRemove=="on"){
    			   isRemove = 1;
    		   } else {
    			   isRemove = 0;
    		   }
    		   precomposeIcon = $("input[name='precomposeIcon"+val+"']:checked").val();
    		   if(precomposeIcon=="on"){
    			   precomposeIcon = 1;
    		   } else {
    			   precomposeIcon = 0;
    		   }
    		   fullScreen = $("input[name='fullScreen"+val+"']:checked").val();
    		   if(fullScreen=="on"){
    			   fullScreen = 1;
    		   } else {
    			   fullScreen = 0;
    		   }
    		   wifihtml += '<li id="ssidli'+i+'">';
    		   wifihtml += '<div class="form-group marginBottom">';
    		   wifihtml += '<div class="col-lg-12">';
    		   wifihtml += '<div class="col-lg-3 control-label">';
    		   wifihtml += '<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.lable"/><span class="asterisk">*</span>';
    		   wifihtml += '</div>';    
    		   wifihtml += '<div class="col-lg-7 control-label">';
    		   wifihtml += '<input type="text" id="webClipName'+i+'" value="'+webClipName+'" class="form-control m-b" data-parsley-required="true"/>';
    		   wifihtml += '</div>';  
    		   wifihtml += '<div class="col-lg-2 control-label">';
    		   wifihtml += '<i class="fa fa-plus-circle fa-2x cursor" onclick="plusWebClipSSID()"></i>&nbsp;&nbsp;<i class="fa fa-minus-circle fa-2x cursor" onclick="minusWebClipSSID('+i+')"></i>';
    		   wifihtml += '</div>';  
    		   wifihtml += '</div>'; 
    		   wifihtml += '</div>'; 
    		   // url
    		   wifihtml += '<div class="form-group marginBottom">';
    		   wifihtml += '<div class="col-lg-12">';
    		   wifihtml += '<div class="col-lg-3 control-label">';
    		   wifihtml += 'URL<span class="asterisk">*</span>';
    		   wifihtml += '</div>';    
    		   wifihtml += '<div class="col-lg-7 control-label">';
    		   wifihtml += '<input type="text" id="webClipUrl'+i+'" value="'+webClipUrl+'" class="form-control m-b" data-parsley-required="true"/>';
    		   wifihtml += '</div>';  
    		   wifihtml += '</div>';
    		   wifihtml += '</div>';
    		   
    		   // 图标
    		   wifihtml += '<div class="form-group marginBottom">';
    		   wifihtml += '<div class="col-lg-12">';
    		   wifihtml += '<div class="col-lg-3 control-label">';
    		   wifihtml += '<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.icon"/>';
    		   wifihtml += '</div>';    
    		   wifihtml += '<div class="col-lg-7">';
    		   wifihtml += '<input type="hidden" id="iconPath'+i+'" value="'+iconPath+'"/>';
    		   wifihtml += '<img src="'+ctx+'/resources/images/upload.png"  id="imgPath'+i+'" onclick="uploadIconModal('+i+')" width="80px" height="80" />';
    		   wifihtml += '</div>';  
    		   wifihtml += '</div>';
    		   
    		   // 可删除
    		   wifihtml += '<div class="col-lg-12">';
    		   wifihtml += '<div class="col-lg-3 control-label">';
    		   wifihtml += '</div>';
    		   wifihtml += '&nbsp;&nbsp;&nbsp;<input type="checkbox" name="isRemove'+i+'" id="isRemove'+i+'"';
    		   if(isRemove==1){
    			   wifihtml += ' checked="checked"';
    		   }
    		   wifihtml += ' />&nbsp;<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.candelete"/>';
    		   wifihtml += '</div>';
    		   // 预作
    		   wifihtml += '<div class="col-lg-12">';
    		   wifihtml += '<div class="col-lg-3 control-label">';
    		   wifihtml += '</div>';
    		   wifihtml += '&nbsp;&nbsp;&nbsp;<input type="checkbox" name="precomposeIcon'+i+'" id="precomposeIcon'+i+'"';
    		   if(precomposeIcon==1){
    			   wifihtml += ' checked="checked"';
    		   }
    		   wifihtml += ' />&nbsp;<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.precomposeicon"/>';
    		   wifihtml += '</div>';
    		   // 全屏
    		   wifihtml += '<div class="col-lg-12">';
    		   wifihtml += '<div class="col-lg-3 control-label">';
    		   wifihtml += '</div>';
    		   wifihtml += '&nbsp;&nbsp;&nbsp;<input type="checkbox" name="fullScreen'+i+'" id="fullScreen'+i+'"';
    		   if(fullScreen==1){
    			   wifihtml += ' checked="checked"';
    		   }
    		   wifihtml += '/>&nbsp;<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.fullscreen"/>';
    		   wifihtml += '</div>';
    		   wifihtml += '</li>';
			   if(i==(count-1)){
				   numhtml += '<li class="current"></lis>';
			   } else {
				   numhtml += '<li class="old"></li>';
			   }
		   }
		   $("#webClipImgs").html(wifihtml);
		   $("#webClipnum").html(numhtml);
	   } else {
		   $("#webClipnum").html("");
		   $("#webClipImgs").html("");
		   $("#tab14").html("");
		   wifiNoIosWebClipSetting(0);
	   }
	   webClipTab();
	   var imgs=document.getElementById("webClipImgs").getElementsByTagName("li");
	   var oimg=document.getElementById("webClipImgs");
	   var left = -(imgs[0].offsetWidth*(imgs.length-1))+"px";
	   oimg.style.left=left;
   }
   
   // 配置WIFI，减少其中一个SSID配置
   function minusSSID(tag){
	   if(tag==0){
		   $("#ssidli").remove();
	   } else {
		   $("#ssidli"+tag).remove();
	   }
	   var count=document.getElementById("imgs").getElementsByTagName("li").length;
	   var wifihtml = "";
	   var numhtml = "";
	   if(count!=0){
		   for(var i=0;i<count;i++){
			   var val = 0;
			   var ssidval = "";
			   var wifikey = "";
			   var identity = "";
			   var anonymousIdentity = "";
			   // 下拉框
			   var saftyType = "";
			   var eap = "";
			   var stageAuthen = "";
			   // 如果当前删除的tab大于当前点击的tab,则获取tab+1的ssid值
			   if(i!=0&&i>=tag){   
				   val = i+1;
				   ssidval = $("#ssid"+val).val();
				   wifikey = $("#wifikey"+val).val();
				   identity = $("#identity"+val).val();
				   anonymousIdentity = $("#anonymousIdentity"+val).val();
				   // 下拉框
				   saftyType = $("#saftyType"+val).val();
				   eap = $("#eap"+val).val();
				   stageAuthen = $("#stageAuthen"+val).val();
			   } else if(tag==0&&i==0){
				   val = i+1;
				   ssidval = $("#ssid"+val).val();
				   wifikey = $("#wifikey"+val).val();
				   identity = $("#identity"+val).val();
				   anonymousIdentity = $("#anonymousIdentity"+val).val();
				   // 下拉框
				   saftyType = $("#saftyType"+val).val();
				   eap = $("#eap"+val).val();
				   stageAuthen = $("#stageAuthen"+val).val();
			   } else if(tag!=0&&i==0){   
				   ssidval = $("#ssid").val();
				   wifikey = $("#wifikey").val();
				   identity = $("#identity").val();
				   anonymousIdentity = $("#anonymousIdentity").val();
				   // 下拉框
				   saftyType = $("#saftyType").val();
				   eap = $("#eap").val();
				   stageAuthen = $("#stageAuthen").val();
			   } else {
				   val = i;
				   ssidval = $("#ssid"+val).val();
				   wifikey = $("#wifikey"+val).val();
				   identity = $("#identity"+val).val();
				   anonymousIdentity = $("#anonymousIdentity"+val).val();
				   // 下拉框
				   saftyType = $("#saftyType"+val).val();
				   eap = $("#eap"+val).val();
				   stageAuthen = $("#stageAuthen"+val).val();
			   }
			   if(i==0&&tag!=0){
			       wifihtml += '<li id="ssidli">';
			   } else {
				   if(i==0){
					   wifihtml += '<li id="ssidli">';
				   } else {
					   wifihtml += '<li id="ssidli'+i+'">'; 
				   }
			   }
			   wifihtml += '<div class="form-group">';
			   wifihtml += '<div class="col-lg-3 control-label">';
			   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.serviceLevelIdentifier"/>(SSID)<span class="asterisk">*</span>';
			   wifihtml += '</div>';    
			   wifihtml += '<div class="col-lg-7 control-label">';
			   if(i==0&&tag!=0){
				   wifihtml += '<input id="ssid" name="ssid" data-parsley-required="true"  data-parsley-maxlength="40" type="text" class="input-sm form-control" value="'+ssidval+'"/>'; 
			   } else {
				   if(i==0){
				       wifihtml += '<input id="ssid" data-parsley-required="true"  data-parsley-maxlength="40" name="ssid" type="text" class="input-sm form-control" value="'+ssidval+'"/>';
				   } else {
					   wifihtml += '<input id="ssid'+i+'" name="ssid'+i+'" data-parsley-required="true"  data-parsley-maxlength="40" type="text" class="input-sm form-control" value="'+ssidval+'"/>';
				   }
			   }
			   wifihtml += '</div>';  
			   wifihtml += '<div class="col-lg-2 control-label">';
			   wifihtml += '<i class="fa fa-plus-circle fa-2x cursor" onclick="plusSSID()"></i>&nbsp;&nbsp;<i class="fa fa-minus-circle fa-2x cursor" onclick="minusSSID('+i+')"></i>';
			   wifihtml += '</div>';  
			   wifihtml += '</div>';   
			   wifihtml += '<div class="form-group">';
			   wifihtml += '<div class="col-lg-3 control-label">';
			   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.autojoin"/>';
			   wifihtml += '</div>';    
			   wifihtml += '<div class="col-lg-1 control-label" style="text-align:left">';
			   if(i==0&&tag!=0){
				   if($('#autojoin').is(':checked')){
					    wifihtml += '<input type="checkbox" id="autojoin" name="autojoin" checked="checked"/>';  
				   } else {
					    wifihtml += '<input type="checkbox" id="autojoin" name="autojoin"/>';
				   }
			   } else {
				   if($('#autojoin'+val).is(':checked')){
					  if(i==0&&tag==0){
						  wifihtml += '<input type="checkbox" id="autojoin" name="autojoin" checked="checked"/>';
					  } else {
						  wifihtml += '<input type="checkbox" id="autojoin'+i+'" name="autojoin'+i+'" checked="checked"/>';
					  }
				   } else {
					   if(i==0&&tag==0){
						   wifihtml += '<input type="checkbox" id="autojoin" name="autojoin"/>'
					   } else {
						   wifihtml += '<input type="checkbox" id="autojoin'+i+'" name="autojoin'+i+'"/>'
					   }
				   }
			   }
			   wifihtml += '</div>';   
			   wifihtml += '</div>';
			   // 安全类型
			   wifihtml += '<div class="form-group">';
			   wifihtml += '<div class="col-lg-3 control-label">';
			   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.safetype"/>';
			   wifihtml += '</div>';    
			   wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
			   if(i==0){
			      wifihtml += '<select id="saftyType" class="form-control saftyType" onChange="onSaftyType('+i+')">';
			   } else {
				   wifihtml += '<select id="saftyType'+i+'" class="form-control saftyType" onChange="onSaftyType('+i+')">';
			   }
			   if(saftyType==0){
				   wifihtml += '<option value="0" selected="selected"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.none"/></option>'; 
			   } else {
				   wifihtml += '<option value="0"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.none"/></option>'; 
			   }
			   if(saftyType==1){
				   wifihtml += '<option value="1" selected="selected">WEP</option>';
			   } else {
				   wifihtml += '<option value="1">WEP</option>';
			   }
			   if(saftyType==2){
				   wifihtml += '<option value="2" selected="selected">WPA/WPA2</option>';
			   } else {
				   wifihtml += '<option value="2">WPA/WPA2</option>';
			   }
			   if(saftyType==3){
				   wifihtml += '<option value="3" selected="selected"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.whatever"/></option>';
			   } else {
				   wifihtml += '<option value="3"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.whatever"/></option>';
			   }
			   if(saftyType==4){
				   wifihtml += '<option value="4" selected="selected">802.1xEAP</option>';
			   } else {
				   wifihtml += '<option value="4">802.1xEAP</option>';
			   }
			   wifihtml += '</select>'; 
			   wifihtml += '</div>';   
			   if(i==0){
				   wifihtml += '<div class="col-lg-12" id="saftyTypeValue" style="padding-left:5px;">'; 
			   } else {
				   wifihtml += '<div class="col-lg-12" id="saftyTypeValue'+i+'" style="padding-left:5px;">'; 
			   }

               // 安全类型 start
			   if(saftyType=="1"||saftyType=="2"||saftyType=="3"){
	        	   wifihtml += '<div class="col-lg-3 control-label">';
	        	   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.password"/>';
	        	   wifihtml += '</div>';    
	        	   wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
	       		  if(i==0){
	       			wifihtml += '<input id="wifikey" type="password" class="input-sm form-control" value="'+wifikey+'"/>';
	       		  } else {
	       			wifihtml += '<input id="wifikey'+i+'" type="password" class="input-sm form-control" value="'+wifikey+'"/>';
	       		  }
	       		  wifihtml += '</div>';
	           }  else if(saftyType=="4"){
	        	  wifihtml += '<div class="col-lg-3 control-label">';
	        	  wifihtml += 'EAP<fmt:message key="tiles.views.institution.devicepolicy.indexscript.method"/>';
	        	  wifihtml += '</div>';    
	        	  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
	    		  if(i==0){
	    			  wifihtml += '<select id="eap" class="form-control saftyType">'; 
	    		  } else {
	    			  wifihtml += '<select id="eap'+i+'" class="form-control saftyType">';
	    		  }
	    		  if(eap=="0"){
	    			  wifihtml += '<option value="0" selected="selected">PEAP</option>';
	    			  wifihtml += '<option value="1">TLS</option>';
	    			  wifihtml += '<option value="2">TTLS</option>';
	    		  } else if(eap=="1"){
	    			  wifihtml += '<option value="0">PEAP</option>';
	    			  wifihtml += '<option value="1" selected="selected">TLS</option>';
	    			  wifihtml += '<option value="2">TTLS</option>';
	    		  } else if(eap=="2"){
	    			  wifihtml += '<option value="0">PEAP</option>';
	    			  wifihtml += '<option value="1">TLS</option>';
	    			  wifihtml += '<option value="2" selected="selected">TTLS</option>';
	    		  }
	    		  wifihtml += '</select>';
	    		  wifihtml += '</div>';
	    		  wifihtml += '<div class="col-lg-3 control-label">';
	    		  wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.stage2authentication"/>';
	    		  wifihtml += '</div>';    
	    		  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
	    		  if(i==0){
	    			  wifihtml += '<select id="stageAuthen" class="form-control saftyType">';
	    		  } else {
	    			  wifihtml += '<select id="stageAuthen'+i+'" class="form-control saftyType">';
	    		  }
	    		  wifihtml += '<option value="0"';
	    		  if(stageAuthen=="0"){
	    			  wifihtml += ' selected="selected"';
	    		  } 
	    		  wifihtml +=' >NONE</option>';
	    		  wifihtml += '<option value="1"';
	    		  if(stageAuthen=="1"){
	    			  wifihtml += ' selected="selected"';
	    		  } 
	    		  wifihtml +=' >PAP</option>';
	    		  wifihtml += '<option value="2"';
	    		  if(stageAuthen=="2"){
	    			  wifihtml += ' selected="selected"';
	    		  }
	    		  wifihtml +=' >MSCHAP</option>';
	    		  wifihtml += '<option value="3"';
	    		  if(stageAuthen=="3"){
	    			  wifihtml += ' selected="selected"';
	    		  } 
	    		  wifihtml +=' >MSCHAPv2</option>';
	    		  wifihtml += '<option value="4"';
	    		  if(stageAuthen=="4"){
	    			  wifihtml += ' selected="selected"';
	    		  }
	    		  wifihtml +=' >GTC</option>';
	    		  wifihtml += '</select>'; 
	    		  wifihtml += '</div>';  		  
	    		  wifihtml += '<div class="col-lg-3 control-label">';
	    		  wifihtml += 'CA<fmt:message key="tiles.views.institution.devicepolicy.indexscript.certificate"/>';
	    		  wifihtml += '</div>';    
	    		  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
	    		  if(i==0){
	    			  wifihtml += '<select id="ca" class="form-control saftyType">';
	    		  } else {
	    			  wifihtml += '<select id="ca'+i+'" class="form-control saftyType">';
	    		  }
	    		  
	    		  wifihtml += '<option value="0"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.nospecify"/></option>';
	    		  wifihtml += '</select>';	
	    		  wifihtml += '</div>';
	    		  
	    		  wifihtml += '<div class="col-lg-3 control-label">';
	    		  wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.userCertificate"/>';
	    		  wifihtml += '</div>';    
	    		  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
	    		  if(i==0){
	    			  wifihtml += '<select id="user" class="form-control saftyType">';
	    		  } else {
	    			  wifihtml += '<select id="user'+i+'" class="form-control saftyType">';
	    		  }
	    		  wifihtml += '<option value="0"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.nospecify"/></option>';
	    		  wifihtml += '</select>';   
	    		  wifihtml += '</div>';
	    		  
	    		  wifihtml += '<div class="col-lg-3 control-label">';
	    		  wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.identity"/>';
	    		  wifihtml += '</div>';    
	    		  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
	    		  if(i==0){
	    			  wifihtml += '<input id="identity" type="text" class="input-sm form-control" value="'+identity+'"/>'; 
	    		  } else {
	    			  wifihtml += '<input id="identity'+i+'" type="text" class="input-sm form-control" value="'+identity+'"/>';
	    		  }
	    		  wifihtml += '</div>';  
	    		  wifihtml += '<div class="col-lg-3 control-label">';
	    		  wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.anonymousIdentity"/>';
	    		  wifihtml += '</div>';    
	    		  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
	    		  if(i==0){
	    			  wifihtml += '<input id="anonymousIdentity" type="text" class="input-sm form-control" value="'+anonymousIdentity+'"/>'; 
	    		  } else {
	    			  wifihtml += '<input id="anonymousIdentity'+i+'" type="text" class="input-sm form-control" value="'+anonymousIdentity+'"/>';
	    		  }
	    		  wifihtml += '</div>';
	    		  wifihtml += '<div class="col-lg-3 control-label">';
	    		  wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.password"/>';
	    		  wifihtml += '</div>';    
	    		  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
	    		  if(i==0){
	    			  wifihtml += '<input id="wifikey" type="password" class="input-sm form-control" value="'+wifikey+'"/>';
	    		  } else {
	    			  wifihtml += '<input id="wifikey'+i+'" type="password" class="input-sm form-control" value="'+wifikey+'"/>'; 
	    		  }
	    		  wifihtml += '</div>';
	          }
			  // 安全类型 end
			   wifihtml += '</div>';
			   wifihtml += '</div>';
			   wifihtml += '</li>';
			   if(i==(count-1)){
				   numhtml += '<li class="current"></lis>';
			   } else {
				   numhtml += '<li class="old"></li>';
			   }
		   }
		   $("#num").html(numhtml);
		   $("#imgs").html(wifihtml);
	   } else {
		   $("#num").html("");
		   $("#imgs").html("");
		   $("#tab7").html("");
		   wifiNoSetting();
	   }
	   tab();
	   var imgs=document.getElementById("imgs").getElementsByTagName("li");
	   var oimg=document.getElementById("imgs");
	   var left = -(imgs[0].offsetWidth*(imgs.length-1))+"px";
	   oimg.style.left=left;
   }
   
   // 安全类型选择事件
   function onSaftyType(i){
	   var tag = "";
	   var html = "";
	   if(i==0){
		   tag = $("#saftyType").val();
	   } else {
		   tag = $("#saftyType"+i).val();
	   }
	   if(tag=="0"){
		   $("#saftyTypeValue").html("");
	   } else if(tag=="1"){
		  html += '<div class="col-lg-3 control-label">';
		  html += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.password"/>';
		  html += '</div>';    
		  html += '<div class="col-lg-7 control-label" style="text-align:left">';
		  if(i==0){
			  html += '<input id="wifikey" type="password" class="input-sm form-control"/>';
		  } else {
			  html += '<input id="wifikey'+i+'" type="password" class="input-sm form-control"/>';
		  }
		  html += '</div>'; 
	   } else if(tag=="2"){
		  html += '<div class="col-lg-3 control-label">';
		  html += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.password"/>';
		  html += '</div>';    
		  html += '<div class="col-lg-7 control-label" style="text-align:left">';
		  if(i==0){
			  html += '<input id="wifikey" type="password" class="input-sm form-control"/>';
		  } else {
			  html += '<input id="wifikey'+i+'" type="password" class="input-sm form-control"/>'; 
		  }
		  html += '</div>'; 
	   } else if(tag=="3"){
		  html += '<div class="col-lg-3 control-label">';
		  html += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.password"/>';
		  html += '</div>';    
		  html += '<div class="col-lg-7 control-label" style="text-align:left">';
		  if(i==0){
			  html += '<input id="wifikey" type="password" class="input-sm form-control"/>';
		  } else {
			  html += '<input id="wifikey'+i+'" type="password" class="input-sm form-control"/>';
		  }
		  html += '</div>'; 
	   } else if(tag=="4"){
		  html += '<div class="col-lg-3 control-label">';
		  html += 'EAP<fmt:message key="tiles.views.institution.devicepolicy.indexscript.method"/>';
		  html += '</div>';    
		  html += '<div class="col-lg-7 control-label" style="text-align:left">';
		  if(i==0){
			  html += '<select id="eap" class="form-control saftyType">'; 
		  } else {
			  html += '<select id="eap'+i+'" class="form-control saftyType">';
		  }
		  
		  html += '<option value="0">PEAP</option>';
		  html += '<option value="1">TLS</option>';
		  html += '<option value="2">TTLS</option>';
		  html += '</select>';
		  html += '</div>';
		  html += '<div class="col-lg-3 control-label">';
		  html += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.stage2authentication"/>';
		  html += '</div>';    
		  html += '<div class="col-lg-7 control-label" style="text-align:left">';
		  if(i==0){
			  html += '<select id="stageAuthen" class="form-control saftyType">';
		  } else {
			  html += '<select id="stageAuthen'+i+'" class="form-control saftyType">';
		  }
		  html += '<option value="0">NONE</option>';
		  html += '<option value="1">PAP</option>';
		  html += '<option value="2">MSCHAP</option>';
		  html += '<option value="3">MSCHAPv2</option>';	
		  html += '<option value="4">GTC</option>';	
		  html += '</select>'; 
		  html += '</div>';
		  
		  html += '<div class="col-lg-3 control-label">';
		  html += 'CA<fmt:message key="tiles.views.institution.devicepolicy.indexscript.certificate"/>';
		  html += '</div>';    
		  html += '<div class="col-lg-7 control-label" style="text-align:left">';
		  if(i==0){
			  html += '<select id="ca" class="form-control saftyType">';
		  } else {
			  html += '<select id="ca'+i+'" class="form-control saftyType">';
		  }
		  s
		  html += '<option value="0"><fmt:message key="tiles.views.institution.dsevicepolicy.indexscript.nospecify"/></option>';
		  html += '</select>';	
		  html += '</div>';
		  
		  html += '<div class="col-lg-3 control-label">';
		  html += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.userCertificate"/>';
		  html += '</div>';    
		  html += '<div class="col-lg-7 control-label" style="text-align:left">';
		  if(i==0){
			  html += '<select id="user" class="form-control saftyType">';
		  } else {
			  html += '<select id="user'+i+'" class="form-control saftyType">';
		  }
		  html += '<option value="0"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.nospecify"/></option>';
		  html += '</select>';   
		  html += '</div>';
		  
		  html += '<div class="col-lg-3 control-label">';
		  html += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.identity"/>';
		  html += '</div>';    
		  html += '<div class="col-lg-7 control-label" style="text-align:left">';
		  if(i==0){
			  html += '<input id="identity" type="text" class="input-sm form-control"/>'; 
		  } else {
			  html += '<input id="identity'+i+'" type="text" class="input-sm form-control"/>';
		  }
		  html += '</div>';  
		  html += '<div class="col-lg-3 control-label">';
		  html += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.anonymousIdentity"/>';
		  html += '</div>';    
		  html += '<div class="col-lg-7 control-label" style="text-align:left">';
		  if(i==0){
			  html += '<input id="anonymousIdentity" type="text" class="input-sm form-control"/>'; 
		  } else {
			  html += '<input id="anonymousIdentity'+i+'" type="text" class="input-sm form-control"/>';
		  }
		  html += '</div>';
		  html += '<div class="col-lg-3 control-label">';
		  html += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.password"/>';
		  html += '</div>';    
		  html += '<div class="col-lg-7 control-label" style="text-align:left">';
		  if(i==0){
			  html += '<input id="wifikey" type="password" class="input-sm form-control"/>';
		  } else {
			  html += '<input id="wifikey'+i+'" type="password" class="input-sm form-control"/>'; 
		  }
		  html += '</div>';
	   }
	   if(i==0){
		   $("#saftyTypeValue").html(html);
	   } else {
		   $("#saftyTypeValue"+i).html(html); 
	   }
   }
   
   // 安全类型选择事件
   function onIosSaftyType(i){
	   var tag = "";
	   var html = "";
	   tag = $("#saftyIosType"+i).val();
	   if(tag=="0"){
		   $("#saftyIosTypeValue"+i).html("");
	   } else {
		  html += '<div class="col-lg-3 control-label">';
		  html += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.password"/>';
		  html += '</div>';    
		  html += '<div class="col-lg-7 control-label" style="text-align:left">';
			  html += '<input id="wifiIoskey'+i+'" type="password" class="input-sm form-control"/>';
		  html += '</div>'; 
	   }
	   $("#saftyIosTypeValue"+i).html(html); 
   }
   
   // 代理
   function onAgent(i){
	   var tag = "";
	   var html = "";
	   tag = $("#agent"+i).val();
	   if(tag=="0"){
		   $("#agentValue"+i).html("");
	   } else if(tag=="1"){
		   // 服务器和端口
		  html += '<div class="col-lg-3 control-label">';
		  html += '<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.serverandport"/>';
		  html += '</div>';    
		  html += '<div class="col-lg-7 control-label" style="text-align:left">';
			  html += '<input id="agentServer'+i+'" type="text" class="input-sm form-control" style="width:100px"/>:';
			  html += '<input id="agentPort'+i+'" type="text" class="input-sm form-control" style="width:60px"/>';
		  html += '</div>'; 
		  // 鉴定
		  html += '<div class="col-lg-3 control-label">';
		  html += '<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.identification"/>';
		  html += '</div>';    
		  html += '<div class="col-lg-7 control-label" style="text-align:left">';
			  html += '<input id="agentAppraisal'+i+'" type="text" class="input-sm form-control"/>';
		  html += '</div>'; 
		  // 密码
		  html += '<div class="col-lg-3 control-label">';
		  html += '<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.password"/>';
		  html += '</div>';    
		  html += '<div class="col-lg-7 control-label" style="text-align:left">';
			  html += '<input id="agentPassword'+i+'" type="password" class="input-sm form-control"/>';
		  html += '</div>'; 
	   } else {
		  html += '<div class="col-lg-3 control-label">';
		  html += '<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.agentserver"/>URL';
		  html += '</div>';    
		  html += '<div class="col-lg-7 control-label" style="text-align:left">';
		  html += '<input id="agentUrl'+i+'" type="text" class="input-sm form-control"/>';
		  html += '</div>';
	   }
	   $("#agentValue"+i).html(html); 
   }
   
   // 新增Android策略配置
   function addPolicy(){
	   	$.ajax({
			"dataType" : 'json',
	        "type": "GET",
	        "url":ctx+"/institution/device/policy/addToken?now="+ new Date().getTime(),
	        "success": function(data){
	           	// 初始化tab 默认第一个显示
	        	initTab();
	            exists();
	    	   	var btn = '';
	    	   	disableVisitLimit();
	    		btn += '<a href="javascript:void(0)" onclick="saveAndroidPolicy()" class="btn btn-success"><fmt:message key="general.jsp.add.label"/></a>';
	    	    btn += '<a href="javascript:void(0)" class="btn" data-dismiss="modal"><fmt:message key="general.jsp.cancel.label"/></a>';
	    	    $("#tokenId").val(data);
	    		$("#saveBtn").html(btn);
	    		$("#myModal").modal("show");
	        }	
	   });
   }
   
   //查询策略列表 	
   function searchPolicy(){
	   $('#devicePolicy').dataTable().fnDestroy();
	   loadDevicePolicy();
	   $('#devicePolicy').dataTable().fnDraw();
   }
  
   $("#visitLimit").click(function(){
		if ($("#visitLimit").prop("checked") == false) {
			disableVisitLimit();
		} else {
			enableVisitLimit();
		}
   });
   
   // 选择是否上网限制
   $("#iosVisitLimit").click(function(){
		if ($("#iosVisitLimit").prop("checked") == false) {
			disableIosVisitLimit();
		} else {
			enableIosVisitLimit();
		}
  });
   
   // 启用上网时间端设置
   function enableVisitLimit(){
		for(var i=1;i<8;i++){
			$("#startTime"+i).removeAttr("disabled");
			$("#endTime"+i).removeAttr("disabled");
		}
   }
   
   // 禁用上网时间端设置
   function disableVisitLimit(){
		for(var i=1;i<8;i++){
			$("#startTime"+i).prop("disabled",true);
			$("#endTime"+i).prop("disabled",true);
		}
   }
   
   // 启用上网时间端设置
   function enableIosVisitLimit(){
		for(var i=1;i<8;i++){
			$("#iosStartTime"+i).removeAttr("disabled");
			$("#iosEndTime"+i).removeAttr("disabled");
		}
   }
   
   // 禁用上网时间端设置
   function disableIosVisitLimit(){
		for(var i=1;i<8;i++){
			$("#iosStartTime"+i).prop("disabled",true);
			$("#iosEndTime"+i).prop("disabled",true);
		}
   }
   
   //查询策略列表 	
   function cleanPolicy(){
	   $('#devicePolicy').dataTable().fnDestroy();
	   $("#devicePolicyName").val("");
	   $("#policytype").val('');
	   $(".Js_curVal").find("input").val('<fmt:message key="tiles.views.institution.devicepolicy.index.allstate"/>');
	   loadDevicePolicy();
	   $('#devicePolicy').dataTable().fnDraw();
   }
   
   // 删除临时用户
   function deleteUser(id,name){
	   removeArray(id,name);
   }
   
   // 设备策略编辑功能
   function _editPolicy(id){
	   	$.ajax({
			"dataType" : 'json',
	        "type": "GET",
	        "url":ctx+"/institution/device/policy/editPolicy?now="+ new Date().getTime(),
	        "data": {"id":id},
	        "success": function(data){
	        	$("#tokenId").val(data.token);
	        	initTab();
	        	var btn = '';
	        	btn += '<div onclick="saveAndroidPolicy()" class="btn btn-success"><fmt:message key="general.jsp.save.label"/></div>';
	            btn += '<a href="javascript:void(0)" class="btn" data-dismiss="modal"><fmt:message key="general.jsp.cancel.label"/></a>';
				$("#saveBtn").html(btn);
	        	$("#myModal").modal("show");
	        	$("#id").val(data.result.id);
	        	$("#name").val(data.result.name);
	        	$("#description").val(data.result.description);
	            exists();
	        	// 初始化设置未选中
	        	// 部门        	
	        	var departIds = "";
	    	   	var nodes = $('#tree').treeview('getUnselected');
	    		$.each(data.result.list, function(i,val){       
					for (var i = 0; i < nodes.length; i++) {
						if (nodes[i].tags.id == val.orgStructureId) {
							departIds += nodes[i].tags.id + ",";
							$('#tree').treeview('checkNode',[ nodes[i].nodeId, {silent : true} ]);
						}
					}
	        	}); 
	        	
	        	$("#chooseDepartIds").val(departIds);
	        	$.each(data.result.virtualList, function(i,val){    
	        		var virtualId = $("#virtu"+val.virtualGroupId);
	        		virtualId.prop("checked",true);
		        }); 
	        	$.each(data.result.userList, function(i,val){ 
	        		addArray(val.usersId,val.realname+"("+val.username+")");
		        }); 
	        	
	        	// wifi配置循环
	        	var wifiArray = data.result.wifiList;
	        	editWifiSetting(wifiArray,1);
	        	var passwordLength = data.result.passwordLength;
	        	if(passwordLength==null||passwordLength==""){
	        		$("#passwordLength option:first").prop("selected",true);
		        	$("#passwordComplexity option:first").prop("selected",true);
		        	$("#lockLongestTime option:first").prop("selected",true);
		        	$("#passwordValidity option:first").prop("selected",true);
		        	$("#attemptTimes option:first").prop("selected",true);
		        	$("#passwordLength").removeAttr("disabled");
		        	$("#passwordComplexity").prop("disabled", true);
		        	$("#lockLongestTime").prop("disabled", true);
		        	$("#passwordValidity").prop("disabled", true);
		        	$("#attemptTimes").prop("disabled", true);
	        	} else {
	        		$("#passwordLength option[value='"+data.result.passwordLength+"']").prop("selected",true);
		        	$("#passwordComplexity option[value='"+data.result.passwordComplexity+"']").prop("selected",true);
		        	$("#lockLongestTime option[value='"+data.result.lockLongestTime+"']").prop("selected",true);
		        	$("#passwordValidity option[value='"+data.result.passwordValidity+"']").prop("selected",true);
		        	$("#attemptTimes option[value='"+data.result.attemptTimes+"']").prop("selected",true);
		        	$("#passwordLength").removeAttr("disabled");
		        	$("#passwordComplexity").removeAttr("disabled");
		        	$("#lockLongestTime").removeAttr("disabled");
		        	$("#passwordValidity").removeAttr("disabled");
		        	$("#attemptTimes").removeAttr("disabled");
	        	}
	        	var deviceEncryption = data.result.deviceEncryption;
	        	// 设备加密
	        	if(deviceEncryption=='1'){
	        	   $("input[name='deviceEncryption']").prop("checked",true);
	            } else {
	            	$("input[name='deviceEncryption']").removeAttr("checked");
	            }
	        	// 检测SD卡并报警
	            var sdEncryption = data.result.sdEncryption;
	            if(sdEncryption=='1'){
	            	$("input[name='sdEncryption']").prop("checked",true);
	            } else {
	            	$("input[name='sdEncryption']").removeAttr("checked");
	            }
	            
	        	// 允许使用相机
	            var allowUseCamera = data.result.allowUseCamera;
	            if(allowUseCamera=='1'){
	            	$("input[name='allowUseCamera']").prop("checked",true);
	            } else {
	            	$("input[name='allowUseCamera']").removeAttr("checked");
	            }
	            
	            // 允许使用WIFI
	            var allowUseWifi = data.result.allowUseWifi;
	            if(allowUseWifi=='1'){
	            	$("input[name='allowUseWifi']").prop("checked",true);
	            } else {
	            	$("input[name='allowUseWifi']").removeAttr("checked");
	            }
	            // 允许使用蓝牙
	            var allowUseBluetooth = data.result.allowUseBluetooth;
	            if(allowUseBluetooth=='1'){
	            	$("input[name='allowUseBluetooth']").prop("checked",true);
	            } else {
	            	$("input[name='allowUseBluetooth']").removeAttr("checked");
	            }
	            // 允许使用麦克风
	            var allowMicrophone = data.result.allowMicrophone;
	            if(allowMicrophone=='1'){
	            	$("input[name='allowMicrophone']").prop("checked",true);
	            } else {
	            	$("input[name='allowMicrophone']").removeAttr("checked");
	            }
	            
	            // 允许使用GPS
	            var allowUseGps = data.result.allowUseGps;
	            if(allowUseGps=='1'){
	            	$("input[name='allowUseGps']").prop("checked",true);
	            } else {
	            	$("input[name='allowUseGps']").removeAttr("checked");
	            }
	            
	            // 是否上网限制
	            var isNetLimit = data.result.isNetLimit;
	            if(isNetLimit=='1'){
	            	$("input[name='visitLimit']").prop("checked",true);
		          	var visitTimeStr = data.result.visitTimeStr;
		          	if(visitTimeStr&&visitTimeStr!=""){
		          		visitTimeStr = visitTimeStr.substring(1,visitTimeStr.length);
		          		visitTimeStr = visitTimeStr.substring(0,visitTimeStr.length-1);
		          		var visitArray = visitTimeStr.split("|");
		          		for(var i=0;i<visitArray.length;i++){
		          			if(visitArray[i]!="00:00-00:00"){
		          				var visitStr = visitArray[i];
 		          				var arr = visitStr.split(",");
 		          				var j = i+1;
		          				$("#startTime"+j).val(arr[0]);
		          				$("#endTime"+j).val(arr[1]); 
		          			}
		          		}
		          	}
	            } else {
	            	$("input[name='visitLimit']").removeAttr("checked");
	            	disableVisitLimit();
	            }
	            // 是否启用白名单或者黑名单
	            var enableBlacklist = data.result.enableBlacklist;
	            // 是否启用应用白名单或者应用黑名单
	            var enableAppNameList = data.result.enableAppNameList;
	            var ids = data.result.ids;
	            $("#chooseEnableIds").val(ids)
	            var names = data.result.names;
	            $("#nameListText").val(names);
	            // 1.白名单 0.黑名单
	            if(enableBlacklist=="1"){
	            	$("#name1").removeAttr("checked");
	            	$("#name2").prop("checked",true);
                    $("#enableWords").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.disabledesc"/>');
	            } else {
	            	$("#name1").prop("checked",true);
	            	$("#name2").removeAttr("checked");
                    $("#enableWords").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.enabledesc"/>');
	            }
	            var appIds = data.result.appIds;
	            $("#chooseAppEnableIds").val(appIds);
	            var appNames = data.result.appNames;
	            $("#appNameListText").val(appNames);
	            if(enableAppNameList=="0"){
	            	$("#appName1").removeAttr("checked");
	            	$("#appName2").prop("checked",true);
                    $("#appEnableWords").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.disableAppdesc"/>');
	            } else {
	            	$("#appName1").prop("checked",true);
	            	$("#appName2").removeAttr("checked");
                    $("#appEnableWords").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.enableAppdesc"/>');
	            }
	          	$("#name").prop("autofocus","autofocus");
	          	$("#iosName").prop("autofocus","autofocus");
	         }	
	    });
    }
   
   // 设备策略编辑功能type(0:android  1:ios)
   function editPolicy(id,isenable,type){
	 if(isenable==1){
		Modal.confirm().on(function(e){
			if(e==true){
				if(type==0){
					_editPolicy(id);
				}else{
					_findIosPolicy(id,1);
				}
			} 
		});
	 } else {
		 if(type==0){
		    _editPolicy(id);
		 } else {
			 _findIosPolicy(id,1);
		 }
	 }
	 if(type==1){
		 var html = '<a href="javascript:void(0)" onclick="saveIOSPolicy()" class="btn btn-success"><fmt:message key="general.jsp.comfirm.label"/></a>';
		 html += '<a href="javascript:void(0)" class="btn" data-dismiss="modal"><fmt:message key="general.jsp.cancel.label"/></a>';
         $("#iosName1").removeAttr("disabled");
     	 $("#iosName2").removeAttr("disabled");
         $("#iosName").prop("autofocus","autofocus");
		 $("#saveIosButton").html(html); 
	 }
	 saveAndroidPolicyValidator.reset();
   }
   
   // 编辑或查看WIFI配置(tag=1表示编辑 0表示查看)
   function editWifiSetting(wifiArray,tag){
      var i = 0;
		   var wifihtml = "";
		   wifihtml += '<div id="luanpo" style="height:430px">';
		   wifihtml += '<ul id="imgs">';
		   if(wifiArray!=null&&wifiArray!=""&&wifiArray.length>0){
		   var count = wifiArray.length;
		   for(var i=0;i<count;i++){
			   var obj = wifiArray[i];
			   var ssidval = obj.ssid;
			   var isAutoJoin = obj.isAutojoin;
			   var saftyType = obj.securityType;
			   var wifikey = obj.password;
			   var eap = obj.eapMethod;
			   var stageAuthen = obj.stageAuthentication;
			   var identity = obj.identity;
			   var anonymousIdentity = obj.anonymousIdentity;
			   if(i==0){
			       wifihtml += '<li id="ssidli">';
			   } else if(i==count){
				   wifihtml += '<li id="ssidli'+i+'">';
			   } else {
				   wifihtml += '<li id="ssidli'+i+'">'; 
			   }
			   wifihtml += '<div class="form-group">';
			   wifihtml += '<div class="col-lg-3 control-label">';
			   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.serviceLevelIdentifier"/>(SSID)<span class="asterisk">*</span>';
			   wifihtml += '</div>';    
			   wifihtml += '<div class="col-lg-7 control-label">';
			   if(i==0){
				   wifihtml += '<input id="ssid" name="ssid" data-parsley-required="true"  data-parsley-maxlength="40" type="text" class="input-sm form-control" value="'+ssidval+'"/>'; 
			   } else {
				   wifihtml += '<input id="ssid'+i+'" name="ssid'+i+'" data-parsley-required="true"  data-parsley-maxlength="40" type="text" class="input-sm form-control" value="'+ssidval+'"/>';
			   }
			   wifihtml += '</div>';  
			   if(tag==1){
			   wifihtml += '<div class="col-lg-2 control-label">';
			   wifihtml += '<i class="fa fa-plus-circle fa-2x cursor" onclick="plusSSID()"></i>&nbsp;&nbsp;<i class="fa fa-minus-circle fa-2x cursor" onclick="minusSSID('+i+')"></i>';
			   wifihtml += '</div>'; 
			   }
			   wifihtml += '</div>';   
			   wifihtml += '<div class="form-group">';
			   wifihtml += '<div class="col-lg-3 control-label">';
			   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.autojoin"/>';
			   wifihtml += '</div>';    
			   wifihtml += '<div class="col-lg-1 control-label" style="text-align:left">';
			   if(i==0){
				   if(isAutoJoin==1){
					    wifihtml += '<input type="checkbox" id="autojoin" name="autojoin" checked="checked"/>';  
				   } else {
					    wifihtml += '<input type="checkbox" id="autojoin" name="autojoin"/>';
				   }
			   } else if(i==count){
				   wifihtml += '<input type="checkbox" id="autojoin'+i+'" name="autojoin'+i+'" checked="checked"/>'; 
			   } else {
				   if(isAutoJoin==1){
			              wifihtml += '<input type="checkbox" id="autojoin'+i+'" name="autojoin'+i+'" checked="checked"/>'; 
				   } else {
			               wifihtml += '<input type="checkbox" id="autojoin'+i+'" name="autojoin'+i+'"/>'
				   }
			    }
			   wifihtml += '</div>';   
			   wifihtml += '</div>';  
			   // 安全类型
			   wifihtml += '<div class="form-group">';
			   wifihtml += '<div class="col-lg-3 control-label">';
			   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.safetype"/>';
			   wifihtml += '</div>';    
			   wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
			   if(i==0){
				   wifihtml += '<select id="saftyType" class="form-control saftyType" onChange="onSaftyType(0)">';
			   } else {
				   wifihtml += '<select id="saftyType'+i+'" class="form-control saftyType" onChange="onSaftyType('+i+')">';
			   }
			   if(saftyType=="0"){
				   wifihtml += '<option value="0" selected="selected"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.none"/></option>';
				   wifihtml += '<option value="1">WEP</option>';
				   wifihtml += '<option value="2">WPA/WPA2</option>';
				   wifihtml += '<option value="3"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.whatever"/></option>';
				   wifihtml += '<option value="4">802.1xEAP</option>';
			   } if(saftyType=="1"){
				   wifihtml += '<option value="0"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.none"/></option>';
				   wifihtml += '<option value="1" selected="selected">WEP</option>';
				   wifihtml += '<option value="2">WPA/WPA2</option>';
				   wifihtml += '<option value="3"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.whatever"/></option>';
				   wifihtml += '<option value="4">802.1xEAP</option>';
			   } if(saftyType=="2"){
				   wifihtml += '<option value="0"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.none"/></option>';
				   wifihtml += '<option value="1">WEP</option>';
				   wifihtml += '<option value="2" selected="selected">WPA/WPA2</option>';
				   wifihtml += '<option value="3"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.whatever"/></option>';
				   wifihtml += '<option value="4">802.1xEAP</option>';
			   } if(saftyType=="3"){
				   wifihtml += '<option value="0"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.none"/></option>';
				   wifihtml += '<option value="1">WEP</option>';
				   wifihtml += '<option value="2">WPA/WPA2</option>';
				   wifihtml += '<option value="3" selected="selected"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.whatever"/></option>';
				   wifihtml += '<option value="4">802.1xEAP</option>';
			   } if(saftyType=="4"){
				   wifihtml += '<option value="0"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.none"/></option>';
				   wifihtml += '<option value="1">WEP</option>';
				   wifihtml += '<option value="2">WPA/WPA2</option>';
				   wifihtml += '<option value="3"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.whatever"/></option>';
				   wifihtml += '<option value="4" selected="selected">802.1xEAP</option>';
			   }
			   wifihtml += '</select>';
			   wifihtml += '</div>'; 
			   if(i==0){
				   wifihtml += '<div class="col-lg-12" id="saftyTypeValue">'; 
			   } else {
				   wifihtml += '<div class="col-lg-12" id="saftyTypeValue'+i+'">'; 
			   }
			   if(saftyType=="1"||saftyType=="2"||saftyType=="3"){
	        	   wifihtml += '<div class="col-lg-3 control-label">';
	        	   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.password"/>';
	        	   wifihtml += '</div>';    
	        	   wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
	       		  if(i==0){
	       			wifihtml += '<input id="wifikey" type="password" class="input-sm form-control" value="'+wifikey+'"/>';
	       		  } else {
	       			wifihtml += '<input id="wifikey'+i+'" type="password" class="input-sm form-control" value="'+wifikey+'"/>';
	       		  }
	       		  wifihtml += '</div>';
	           }  else if(saftyType=="4"){
	        	  wifihtml += '<div class="col-lg-3 control-label">';
	        	  wifihtml += 'EAP<fmt:message key="tiles.views.institution.devicepolicy.indexscript.method"/>';
	        	  wifihtml += '</div>';    
	        	  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
	    		  if(i==0){
	    			  wifihtml += '<select id="eap" class="form-control saftyType">'; 
	    		  } else {
	    			  wifihtml += '<select id="eap'+i+'" class="form-control saftyType">';
	    		  }
	    		  if(eap=="0"){
	    			  wifihtml += '<option value="0" selected="selected">PEAP</option>';
	    			  wifihtml += '<option value="1">TLS</option>';
	    			  wifihtml += '<option value="2">TTLS</option>';
	    		  } else if(eap=="1"){
	    			  wifihtml += '<option value="0">PEAP</option>';
	    			  wifihtml += '<option value="1" selected="selected">TLS</option>';
	    			  wifihtml += '<option value="2">TTLS</option>';
	    		  } else if(eap=="2"){
	    			  wifihtml += '<option value="0">PEAP</option>';
	    			  wifihtml += '<option value="1">TLS</option>';
	    			  wifihtml += '<option value="2" selected="selected">TTLS</option>';
	    		  }
	    		  wifihtml += '</select>';
	    		  wifihtml += '</div>';
	    		  wifihtml += '<div class="col-lg-3 control-label">';
	    		  wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.stage2authentication"/>';
	    		  wifihtml += '</div>';    
	    		  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
	    		  if(i==0){
	    			  wifihtml += '<select id="stageAuthen" class="form-control saftyType">';
	    		  } else {
	    			  wifihtml += '<select id="stageAuthen'+i+'" class="form-control saftyType">';
	    		  }
	    		  wifihtml += '<option value="0"';
	    		  if(stageAuthen=="0"){
	    			  wifihtml += ' selected="selected"';
	    		  } 
	    		  wifihtml +=' >NONE</option>';
	    		  wifihtml += '<option value="1"';
	    		  if(stageAuthen=="1"){
	    			  wifihtml += ' selected="selected"';
	    		  } 
	    		  wifihtml +=' >PAP</option>';
	    		  wifihtml += '<option value="2"';
	    		  if(stageAuthen=="2"){
	    			  wifihtml += ' selected="selected"';
	    		  }
	    		  wifihtml +=' >MSCHAP</option>';
	    		  wifihtml += '<option value="3"';
	    		  if(stageAuthen=="3"){
	    			  wifihtml += ' selected="selected"';
	    		  } 
	    		  wifihtml +=' >MSCHAPv2</option>';
	    		  wifihtml += '<option value="4"';
	    		  if(stageAuthen=="4"){
	    			  wifihtml += ' selected="selected"';
	    		  }
	    		  wifihtml +=' >GTC</option>';
	    		  wifihtml += '</select>'; 
	    		  wifihtml += '</div>';  		  
	    		  wifihtml += '<div class="col-lg-3 control-label">';
	    		  wifihtml += 'CA<fmt:message key="tiles.views.institution.devicepolicy.indexscript.certificate"/>';
	    		  wifihtml += '</div>';    
	    		  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
	    		  if(i==0){
	    			  wifihtml += '<select id="ca" class="form-control saftyType">';
	    		  } else {
	    			  wifihtml += '<select id="ca'+i+'" class="form-control saftyType">';
	    		  }
	    		  
	    		  wifihtml += '<option value="0"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.nospecify"/></option>';
	    		  wifihtml += '</select>';	
	    		  wifihtml += '</div>';
	    		  
	    		  wifihtml += '<div class="col-lg-3 control-label">';
	    		  wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.userCertificate"/>';
	    		  wifihtml += '</div>';    
	    		  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
	    		  if(i==0){
	    			  wifihtml += '<select id="user" class="form-control saftyType">';
	    		  } else {
	    			  wifihtml += '<select id="user'+i+'" class="form-control saftyType">';
	    		  }
	    		  wifihtml += '<option value="0"><fmt:message key="tiles.views.institution.devicepolicy.indexscript.nospecify"/></option>';
	    		  wifihtml += '</select>';   
	    		  wifihtml += '</div>';
	    		  
	    		  wifihtml += '<div class="col-lg-3 control-label">';
	    		  wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.identity"/>';
	    		  wifihtml += '</div>';    
	    		  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
	    		  if(i==0){
	    			  wifihtml += '<input id="identity" type="text" class="input-sm form-control" value="'+identity+'"/>'; 
	    		  } else {
	    			  wifihtml += '<input id="identity'+i+'" type="text" class="input-sm form-control" value="'+identity+'"/>';
	    		  }
	    		  wifihtml += '</div>';  
	    		  wifihtml += '<div class="col-lg-3 control-label">';
	    		  wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.anonymousIdentity"/>';
	    		  wifihtml += '</div>';    
	    		  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
	    		  if(i==0){
	    			  wifihtml += '<input id="anonymousIdentity" type="text" class="input-sm form-control" value="'+anonymousIdentity+'"/>'; 
	    		  } else {
	    			  wifihtml += '<input id="anonymousIdentity'+i+'" type="text" class="input-sm form-control" value="'+anonymousIdentity+'"/>';
	    		  }
	    		  wifihtml += '</div>';
	    		  wifihtml += '<div class="col-lg-3 control-label">';
	    		  wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.password"/>';
	    		  wifihtml += '</div>';    
	    		  wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
	    		  if(i==0){
	    			  wifihtml += '<input id="wifikey" type="password" class="input-sm form-control" value="'+wifikey+'"/>';
	    		  } else {
	    			  wifihtml += '<input id="wifikey'+i+'" type="password" class="input-sm form-control" value="'+wifikey+'"/>'; 
	    		  }
	    		  wifihtml += '</div>';
	           }
			   wifihtml += '</div>';
			   wifihtml += '</div>';
			   wifihtml += '</li>';
		   }
		   wifihtml += '</ul>';  
		   wifihtml += '<ul id="num">'; 
		   for(var i=0;i<count;i++){
			   if(i==0){
				   wifihtml += '<li class="current"></li>';
			   } else {
				   wifihtml += '<li class="old"></li>';
			   }
		   }
		   wifihtml += '</ul>';   
		   wifihtml += '</div>';   
		   $("#tab7").html(wifihtml);
		   tab();
	   } else {
		   if(tag==1){
			   wifiNoSetting(); 
		   } else {
			   wifiFindSetting();
		   }
	   }   
   }
   
   // 设备策略查看功能
   function _findPolicy(id){
	   initTab();
	   	$.ajax({
			"dataType" : 'json',
	        "type": "GET",
	        "url":ctx+"/institution/device/policy/findPolicy?now="+ new Date().getTime(),
	        "data": {"id":id},
	        "success": function(data){
	         	$('#tree').treeview('disableAll', { silent: true }); 
				$("#saveBtn").html(""); 
	        	$("#myModal").modal("show");
	        	$("#id").val(data.id);
	        	$("#name").val(data.name);
	        	$("#name").prop("disabled",true);
	        	$("#description").val(data.description);
	        	$("#description").prop("disabled",true);
	        	$("#policyName").prop("disabled",true);
	        	$('input[name="virtualIds"]').prop("disabled",true); 
	        	$("#passwordLength").prop("disabled", true);
	        	$("#passwordComplexity").prop("disabled", true);
	        	$("#lockLongestTime").prop("disabled", true);
	        	$("#passwordValidity").prop("disabled", true);
	        	$("#attemptTimes").prop("disabled", true);
	        	$("#tab3 input[type]").prop("disabled", true); 
	        	$("#tab4 input[type]").prop("disabled", true); 
	        	$("#tab5 input[type]").prop("disabled", true); 
	        	$("#name1").prop("disabled", true); 
	        	$("#name2").prop("disabled", true); 
	        	
	        	// 初始化设置未选中
	        	// 部门	        		
	        	var departIds = "";
	    	   	var nodes = $('#tree').treeview('getUnselected');
	    		$.each(data.list, function(i,val){       
					for (var i = 0; i < nodes.length; i++) {
						if (nodes[i].tags.id == val.orgStructureId) {
							departIds += nodes[i].tags.id + ",";
							$('#tree').treeview('checkNode',[ nodes[i].nodeId, {silent : true} ]);
						}
					}
	        	}); 
	        	$.each(data.virtualList, function(i,val){    
	        		var virtualId = $("#virtu"+val.virtualGroupId);
	        		virtualId.prop("checked",true);
		        }); 
	        	$.each(data.userList, function(i,val){ 
	        		displayArray(val.usersId,val.realname+"("+val.username+")");
		        }); 
	        	// wifi配置循环
	        	var wifiArray = data.wifiList;
	        	editWifiSetting(wifiArray,0);
	        	
	        	$("#passwordLength option[value='"+data.passwordLength+"']").prop("selected",true);
	        	$("#passwordComplexity option[value='"+data.passwordComplexity+"']").prop("selected",true);
	        	$("#lockLongestTime option[value='"+data.lockLongestTime+"']").prop("selected",true);
	        	$("#passwordValidity option[value='"+data.passwordValidity+"']").prop("selected",true);
	        	$("#attemptTimes option[value='"+data.attemptTimes+"']").prop("selected",true);
	        	var deviceEncryption = data.deviceEncryption;
	        	// 设备加密
	        	if(deviceEncryption=='1'){
	        	   $("input[name='deviceEncryption']").prop("checked",true);
	            } else {
	            	$("input[name='deviceEncryption']").removeAttr("checked");
	            }
	        	// 检测SD卡并报警
	            var sdEncryption = data.sdEncryption;
	            if(sdEncryption=='1'){
	            	$("input[name='sdEncryption']").prop("checked",true);
	            } else {
	            	$("input[name='sdEncryption']").removeAttr("checked");
	            }
	            
	        	// 允许使用相机
	            var allowUseCamera = data.allowUseCamera;
	            if(allowUseCamera=='1'){
	            	$("input[name='allowUseCamera']").prop("checked",true);
	            } else {
	            	$("input[name='allowUseCamera']").removeAttr("checked");
	            }
	            
	            // 允许使用GPS
	            var allowUseGps = data.allowUseGps;
	            if(allowUseGps=='1'){
	            	$("input[name='allowUseGps']").prop("checked",true);
	            } else {
	            	$("input[name='allowUseGps']").removeAttr("checked");
	            }
	            
	            
	            // 允许使用WIFI
	            var allowUseWifi = data.allowUseWifi;
	            if(allowUseWifi=='1'){
	            	$("input[name='allowUseWifi']").prop("checked",true);
	            } else {
	            	$("input[name='allowUseWifi']").removeAttr("checked");
	            }
	            // 允许使用蓝牙
	            var allowUseBluetooth = data.allowUseBluetooth;
	            if(allowUseBluetooth=='1'){
	            	$("input[name='allowUseBluetooth']").prop("checked",true);
	            } else {
	            	$("input[name='allowUseBluetooth']").removeAttr("checked");
	            }
	            // 允许使用麦克风
	            var allowMicrophone = data.allowMicrophone;
	            if(allowMicrophone=='1'){
	            	$("input[name='allowMicrophone']").prop("checked",true);
	            } else {
	            	$("input[name='allowMicrophone']").removeAttr("checked");
	            }
	            
	            // 允许上网
	            var isNetLimit = data.isNetLimit;
	            if(isNetLimit=='1'){
	            	$("input[name='visitLimit']").prop("checked",true);
/* 	            	$("#startTime").val(data.visitTimeStart);
	            	$("#endTime").val(data.visitTimeEnd);
		        	$("#startTime").prop("disabled", true);
		        	$("#endTime").prop("disabled", true); */
		          	var visitTimeStr = data.visitTimeStr;
		          	if(visitTimeStr&&visitTimeStr!=""){
		          		visitTimeStr = visitTimeStr.substring(1,visitTimeStr.length);
		          		visitTimeStr = visitTimeStr.substring(0,visitTimeStr.length-1);
		          		var visitArray = visitTimeStr.split("|");
		          		for(var i=0;i<visitArray.length;i++){
		          			if(visitArray[i]!="00:00-00:00"){
		          				var visitStr = visitArray[i];
 		          				var arr = visitStr.split(",");
 		          				var j = i+1;
		          				$("#startTime"+j).val(arr[0]);
		          				$("#endTime"+j).val(arr[1]); 
		          			}
		          		}
		          	}
	            } else {
	            	$("input[name='visitLimit']").removeAttr("checked");
	            	disableVisitLimit();
	            }
	            
	            // 是否启用白名单或者黑名单
	            var ids = data.ids;
	            $("#chooseEnableIds").val(ids);
	            var names = data.names;
	            $("#nameListText").val(names);
	            var enableBlacklist = data.enableBlacklist;
	            if(enableBlacklist=="1"){
	            	$("#name1").removeAttr("checked");
	            	$("#name2").prop("checked",true);
                    $("#enableWords").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.disabledesc"/>');
	            } else {
	            	$("#name1").prop("checked",true);
	            	$("#name2").removeAttr("checked");
                    $("#enableWords").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.enabledesc"/>');
	            }
	            // 是否启用应用白名单或者应用黑名单
	            var enableAppNameList = data.enableAppNameList;
	            var appIds = data.appIds;
	            $("#chooseAppEnableIds").val(appIds);
	            var appNames = data.appNames;
	            $("#appNameListText").val(appNames);
	            if(enableAppNameList=="0"){
	            	$("#appName1").removeAttr("checked");
	            	$("#appName2").prop("checked",true);
                    $("#appEnableWords").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.disableAppdesc"/>');
	            } else {
	            	$("#appName1").prop("checked",true);
	            	$("#appName2").removeAttr("checked");
                    $("#appEnableWords").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.enableAppdesc"/>');
	            }
	        }   
	   });
   }
   
   // 禁用IOS设备策略
   function disableIosAttr(){
	  $("#iosName").prop("disabled",true);
	  $("#iosDescription").prop("disabled",true);
	  $("#allowSimpleValue").prop("disabled",true);
	  $("#letterDigitValue").prop("disabled",true);
	  $("#passwordIosLength").prop("disabled",true);
      $("#failureTimes").prop("disabled",true);
	  $("#passwordIosHistory").prop("disabled",true);		
	  $("#allowInstallApp").prop("disabled",true);
	  $("#allowIosUseCamera").prop("disabled",true);
	  $("#allowFaceTime").prop("disabled",true);
	  $("#allowScreenCatch").prop("disabled",true);
	  $("#allowUseYoutube").prop("disabled",true);
	  $("#allowUseiTunes").prop("disabled",true);
	  $("#allowUseSafari").prop("disabled",true);
	  $("#allowBackup").prop("disabled",true);
	  $("#allowDocumentSynchronization").prop("disabled",true);
      $("#allowPhotoStream").prop("disabled",true);
      $("#allowOpenFromManagedToUnmanaged").prop("disabled",true);
      $("#allowOpenFromUnmanagedToManaged").prop("disabled",true);
      $("#allowGlobalBackgroundFetchWhenRoaming").prop("disabled",true);
      $("#allowAssistantWhileLocked").prop("disabled",true);
      $("#allowLockScreenTodayView").prop("disabled",true);
      $("#allowCloudKeychainSync").prop("disabled",true);
      $("#allowLockScreenControlCenter").prop("disabled",true);
      $("#allowFingerprintForUnlock").prop("disabled",true);
      $("#allowLockScreenNotificationsView").prop("disabled",true);
      $("#allowManagedAppsCloudSync").prop("disabled",true);
      $("#allowCloudPhotoLibrary").prop("disabled",true);
      $("#allowSharedStream").prop("disabled",true);
      $("#allowActivityContinuation").prop("disabled",true);
      $("#allowAddingGameCenterFriends").prop("disabled",true);
      $("#allowMultiplayerGaming").prop("disabled",true);
      $("#forceiTunesStore").prop("disabled",true);
      $("#limitAdvertTracking").prop("disabled",true);
      $("#isNetLimit").prop("disabled",true);
      $("#enableBlacklist").prop("disabled",true);
      $("#enableAppNameList").prop("disabled",true);
      $("#allowSiri").prop("disabled",true);
      $("#allowVoiceDialing").prop("disabled",true);
      $("#allowDisplayPassbook").prop("disabled",true);
      $("#allowGameCenter").prop("disabled",true);
   }
   
   // 查看IOS设备策略功能(tag 0:查看  1:编辑)
   function _findIosPolicy(id,tag){
	   initIosTab();
	   	$.ajax({
			"dataType" : 'json',
	        "type": "GET",
	        "url":ctx+"/institution/device/policy/findIosPolicy?now="+ new Date().getTime(),
	        "data": {"id":id},
	        "success": function(data){
	        	$("#myIosModal").modal("show");
	        	$("#iosId").val(id);
	        	$("#iosName").val(data.name);
	        	$("#iosDescription").val(data.description);
	        	var nodes = $('#iosTree').treeview('getUnselected');
	        	var departIds = "";
	    		$.each(data.iosDepartmentList, function(i,val){       
					for (var i = 0; i < nodes.length; i++) {
						if (nodes[i].tags.id == val.orgStructureId) {
							departIds += nodes[i].tags.id + ",";
							$('#iosTree').treeview('checkNode',[ nodes[i].nodeId, {silent : true} ]);
						}
					}
	        	}); 
	        	$.each(data.iosVirtualList, function(i,val){    
	        		var virtualId = $("#virIosTu"+val.virtualGroupId);
	        		virtualId.prop("checked",true);
		        }); 
	        	$.each(data.iosUserList, function(i,val){ 
	        		displayArray(val.userId,val.realname+"("+val.username+")",tag);
		        }); 
	        	if(data.isEnablePassword=="1"){
		        	initPasscodeSetting();
		        	$("#passwordIosLength option[value='"+data.passwordLength+"']").prop("selected",true);
		        	$("#failureTimes option[value='"+data.failureTimes+"']").prop("selected",true);
		        	$("#passwordIosHistory").val(data.passwordHistory);
	            } else {
	            	passwordIosSetting();
	            }
	        	// 允许简单值
	            var allowSimpleValue = data.allowSimpleValue;
	            if(allowSimpleValue=='1'){
	            	$("input[name='allowSimpleValue']").prop("checked",true);
	            } else {
	            	$("input[name='allowSimpleValue']").removeAttr("checked");
	            }
	            var letterDigitValue = data.letterDigitValue;
	            if(letterDigitValue=='1'){
	            	$("input[name='letterDigitValue']").prop("checked",true);
	            } else {
	            	$("input[name='letterDigitValue']").removeAttr("checked");
	            }
	            var allowInstallApp = data.allowInstallApp;
	            if(allowInstallApp=='1'){
	            	$("input[name='allowInstallApp']").prop("checked",true);
	            } else {
	            	$("input[name='allowInstallApp']").removeAttr("checked");
	            }
	            var allowIosUseCamera = data.allowUseCamera;
	            if(allowIosUseCamera=='1'){
	            	$("input[name='allowIosUseCamera']").prop("checked",true);
	            } else {
	            	$("input[name='allowIosUseCamera']").removeAttr("checked");
	            }  
	            var allowFaceTime = data.allowFaceTime;
	            if(allowFaceTime=='1'){
	            	$("input[name='allowFaceTime']").prop("checked",true);
	            } else {
	            	$("input[name='allowFaceTime']").removeAttr("checked");
	            } 
	            var allowScreenCatch = data.allowScreenCatch;
	            if(allowScreenCatch=='1'){
	            	$("input[name='allowScreenCatch']").prop("checked",true);
	            } else {
	            	$("input[name='allowScreenCatch']").removeAttr("checked");
	            }
	            var allowUseYoutube = data.allowUseYoutube;
	            if(allowScreenCatch=='1'){
	            	$("input[name='allowUseYoutube']").prop("checked",true);
	            } else {
	            	$("input[name='allowUseYoutube']").removeAttr("checked");
	            }
	            var allowUseiTunes = data.allowUseItunes;
	            if(allowUseiTunes=='1'){
	            	$("input[name='allowUseiTunes']").prop("checked",true);
	            } else {
	            	$("input[name='allowUseiTunes']").removeAttr("checked");
	            }
	            var allowUseSafari = data.allowUseSafari;
	            if(allowUseSafari=='1'){
	            	$("input[name='allowUseSafari']").prop("checked",true);
	            } else {
	            	$("input[name='allowUseSafari']").removeAttr("checked");
	            }
	            var allowBackup = data.allowBackup;
	            if(allowBackup=='1'){
	            	$("input[name='allowBackup']").prop("checked",true);
	            } else {
	            	$("input[name='allowBackup']").removeAttr("checked");
	            }
	            var allowDocumentSynchronization = data.allowDocumentSynchronization;
	            if(allowDocumentSynchronization=='1'){
	            	$("input[name='allowDocumentSynchronization']").prop("checked",true);
	            } else {
	            	$("input[name='allowDocumentSynchronization']").removeAttr("checked");
	            }
	            var allowPhotoStream = data.allowPhotoStream;
	            if(allowPhotoStream=='1'){
	            	$("input[name='allowPhotoStream']").prop("checked",true);
	            } else {
	            	$("input[name='allowPhotoStream']").removeAttr("checked");
	            }
	            var allowOpenFromManagedToUnmanaged = data.allowOpenFromManagedToUnmanaged;
	            if(allowOpenFromManagedToUnmanaged=='1'){
	            	$("input[name='allowOpenFromManagedToUnmanaged']").prop("checked",true);
	            } else {
	            	$("input[name='allowOpenFromManagedToUnmanaged']").removeAttr("checked");
	            }
	            var allowOpenFromUnmanagedToManaged = data.allowOpenFromUnmanagedToManaged;
	            if(allowOpenFromUnmanagedToManaged=='1'){
	            	$("input[name='allowOpenFromUnmanagedToManaged']").prop("checked",true);
	            } else {
	            	$("input[name='allowOpenFromUnmanagedToManaged']").removeAttr("checked");
	            }
	            var allowGlobalBackgroundFetchWhenRoaming = data.allowGlobalBackgroundFetchWhenRoaming;
	            if(allowGlobalBackgroundFetchWhenRoaming=='1'){
	            	$("input[name='allowGlobalBackgroundFetchWhenRoaming']").prop("checked",true);
	            } else {
	            	$("input[name='allowGlobalBackgroundFetchWhenRoaming']").removeAttr("checked");
	            }
	            var allowAssistantWhileLocked = data.allowAssistantWhileLocked;
	            if(allowAssistantWhileLocked=='1'){
	            	$("input[name='allowAssistantWhileLocked']").prop("checked",true);
	            } else {
	            	$("input[name='allowAssistantWhileLocked']").removeAttr("checked");
	            }
	            var allowAssistantWhileLocked = data.allowAssistantWhileLocked;
	            if(allowAssistantWhileLocked=='1'){
	            	$("input[name='allowAssistantWhileLocked']").prop("checked",true);
	            } else {
	            	$("input[name='allowAssistantWhileLocked']").removeAttr("checked");
	            }
	            var allowLockScreenTodayView = data.allowLockScreenTodayView;
	            if(allowLockScreenTodayView=='1'){
	            	$("input[name='allowLockScreenTodayView']").prop("checked",true);
	            } else {
	            	$("input[name='allowLockScreenTodayView']").removeAttr("checked");
	            }
	            var allowCloudKeychainSync = data.allowCloudKeychainSync;
	            if(allowCloudKeychainSync=='1'){
	            	$("input[name='allowCloudKeychainSync']").prop("checked",true);
	            } else {
	            	$("input[name='allowCloudKeychainSync']").removeAttr("checked");
	            }
	            var allowLockScreenControlCenter = data.allowLockScreenControlCenter;
	            if(allowLockScreenControlCenter=='1'){
	            	$("input[name='allowLockScreenControlCenter']").prop("checked",true);
	            } else {
	            	$("input[name='allowLockScreenControlCenter']").removeAttr("checked");
	            }
	            var allowFingerprintForUnlock = data.allowFingerprintForUnlock;
	            if(allowFingerprintForUnlock=='1'){
	            	$("input[name='allowFingerprintForUnlock']").prop("checked",true);
	            } else {
	            	$("input[name='allowFingerprintForUnlock']").removeAttr("checked");
	            }
	            var allowLockScreenNotificationsView = data.allowLockScreenNotificationsView;
	            if(allowLockScreenNotificationsView=='1'){
	            	$("input[name='allowLockScreenNotificationsView']").prop("checked",true);
	            } else {
	            	$("input[name='allowLockScreenNotificationsView']").removeAttr("checked");
	            }
	            var allowManagedAppsCloudSync = data.allowManagedAppsCloudSync;
	            if(allowManagedAppsCloudSync=='1'){
	            	$("input[name='allowManagedAppsCloudSync']").prop("checked",true);
	            } else {
	            	$("input[name='allowManagedAppsCloudSync']").removeAttr("checked");
	            }
	            var allowCloudPhotoLibrary = data.allowCloudPhotoLibrary;
	            if(allowCloudPhotoLibrary=='1'){
	            	$("input[name='allowCloudPhotoLibrary']").prop("checked",true);
	            } else {
	            	$("input[name='allowCloudPhotoLibrary']").removeAttr("checked");
	            }
	            var allowSharedStream = data.allowSharedStream;
	            if(allowSharedStream=='1'){
	            	$("input[name='allowSharedStream']").prop("checked",true);
	            } else {
	            	$("input[name='allowSharedStream']").removeAttr("checked");
	            }
	            var allowActivityContinuation = data.allowActivityContinuation;
	            if(allowActivityContinuation=='1'){
	            	$("input[name='allowActivityContinuation']").prop("checked",true);
	            } else {
	            	$("input[name='allowActivityContinuation']").removeAttr("checked");
	            }
	            var allowAddingGameCenterFriends = data.allowAddingGameCenterFriends;
	            if(allowAddingGameCenterFriends=='1'){
	            	$("input[name='allowAddingGameCenterFriends']").prop("checked",true);
	            } else {
	            	$("input[name='allowAddingGameCenterFriends']").removeAttr("checked");
	            }
	            var allowMultiplayerGaming = data.allowMultiplayerGaming;
	            if(allowMultiplayerGaming=='1'){
	            	$("input[name='allowMultiplayerGaming']").prop("checked",true);
	            } else {
	            	$("input[name='allowMultiplayerGaming']").removeAttr("checked");
	            }	            
	            var forceiTunesStore = data.forceItunesStore;
	            if(forceiTunesStore=='1'){
	            	$("input[name='forceiTunesStore']").prop("checked",true);
	            } else {
	            	$("input[name='forceiTunesStore']").removeAttr("checked");
	            }
	            var limitAdvertTracking = data.limitAdvertTracking;
	            if(limitAdvertTracking=='1'){
	            	$("input[name='limitAdvertTracking']").prop("checked",true);
	            } else {
	            	$("input[name='limitAdvertTracking']").removeAttr("checked");
	            }
	            var isNetLimit = data.isNetLimit;
	            if(isNetLimit=='1'){
	            	$("input[name='iosVisitLimit']").prop("checked",true);
		          	var visitTimeStr = data.visitTimeStr;
		          	if(visitTimeStr&&visitTimeStr!=""){
		          		visitTimeStr = visitTimeStr.substring(1,visitTimeStr.length);
		          		visitTimeStr = visitTimeStr.substring(0,visitTimeStr.length-1);
		          		var visitArray = visitTimeStr.split("|");
		          		for(var i=0;i<visitArray.length;i++){
		          			if(visitArray[i]!="00:00-00:00"){
		          				var visitStr = visitArray[i];
 		          				var arr = visitStr.split(",");
 		          				var j = i+1;
		          				$("#iosStartTime"+j).val(arr[0]);
		          				$("#iosEndTime"+j).val(arr[1]); 
		          			}
		          		}
		          	}
		          	enableIosVisitLimit();
	            } else {
	            	$("input[name='iosVisitLimit']").removeAttr("checked");
	            	disableIosVisitLimit();
	            }
	            var enableBlacklist = data.enableBlacklist;
	            if(enableBlacklist=='1'){
	            	$("#iosName2").prop("checked",true);
	            } else {
	            	$("#iosName1").prop("checked",true);
	            }
	            var enableAppNameList = data.enableAppNameList;
	            if(enableAppNameList=='1'){
	            	$("#appIosName1").prop("checked",true);
	            } else {
	            	$("#appIosName2").prop("checked",true);
	            }
	            $("#chooseIosEnableIds").val(data.ids);
	            $("#iosNameListText").val(data.names);
	            var allowSiri = data.allowSiri;
	            if(allowSiri=='1'){
	            	$("input[name='allowSiri']").prop("checked",true);
	            } else {
	            	$("input[name='allowSiri']").removeAttr("checked");
	            }
	            var allowVoiceDialing = data.allowVoiceDialing;
	            if(allowVoiceDialing=='1'){
	            	$("input[name='allowVoiceDialing']").prop("checked",true);
	            } else {
	            	$("input[name='allowVoiceDialing']").removeAttr("checked");
	            }
	            var allowDisplayPassbook = data.allowDisplayPassbook;
	            if(allowDisplayPassbook=='1'){
	            	$("input[name='allowDisplayPassbook']").prop("checked",true);
	            } else {
	            	$("input[name='allowDisplayPassbook']").removeAttr("checked");
	            }
	            var allowGameCenter = data.allowGameCenter;
	            if(allowGameCenter=='1'){
	            	$("input[name='allowGameCenter']").prop("checked",true);
	            } else {
	            	$("input[name='allowGameCenter']").removeAttr("checked");
	            }
	            if(tag==0){
	        		$("#iosName1").prop("disabled", true); 
	        		$("#iosName2").prop("disabled", true); 
	            } else {
	        		$("#iosName1").removeAttr("disabled"); 
	        		$("#iosName2").removeAttr("disabled"); 
	            }
	        	var wifiArray = data.iosWifiList;
	        	editIosWifiSetting(wifiArray,0);
	        	iosExists();
	        }
	   	});
   }
   
   // 设备策略查看功能type(0:android  1:ios)
   function findPolicy(id,isenable,type){
	   // android
	   if(type==0){
		   _findPolicy(id);
	   } else { // ios
		   _findIosPolicy(id,0);
		   $("#saveIosButton").html('<a href="javascript:void(0)" class="btn" data-dismiss="modal"><fmt:message key="general.jsp.cancel.label"/></a>'); 
		   disableIosAttr();
	   }
   }
   
   // 打开黑名单或白名单列表
   // tag:标识  0.Android策略  1.ios策略
   function loadNameList(tag){
	   var ids = "";
	   var type = "";
	   if(tag==0) {
	       $("#myModal").modal("hide");
	       ids = $("#chooseEnableIds").val();
	       type = $("input[name='enableNameList']:checked").val();
	   }  else {
		   $("#myIosModal").modal("hide");
		   ids = $("#chooseIosEnableIds").val();
		   type = $("input[name='enableIosNameList']:checked").val();
	   }
	   $("#nameTag").val(tag);
	   $("#loadNameList").modal("show");
	   $('#whiteBlackNamelist').dataTable().fnDestroy();
	   var dataTable = $('#whiteBlackNamelist').dataTable({"dom":"<'m-r m-t-lg pull-right'f>t<'row '<'col-lg-2'l>r<'col-lg-3'i><'pull-right'p>>",
		        "searching" : false,
				"autoWidth" : false,
				"ordering" : false,
				"bSort" : false,
				"serverSide" : true,
				"bLengthChange" : true,
				"bPaginate" : true,
				"pageLength" : 10,
				"pagingType" : "full_numbers",
				"oLanguage": {
					"sUrl":languageUrl
			    },
				"ajax" : {
  					"dataType" : 'json',
					"type" : "get",
					"url" : ctx+"/institution/device/policy/queryNetNameList?now="+ new Date().getTime(),
					"data" : {"type":type}
				},
				"columnDefs" : [
			   {
				"targets" : [0],
				"render" : function(data, type, row) {
					 ids = ids +",";
					 var str = '<input type="checkbox" id="nameList'+row.id+'" name="nameList"'; 
					 if(ids.indexOf(row.id)>=0){
						 str += ' checked="checked" ';
					 }
					 str += 'value="'+row.id+'" />';
					 str += '<input type="hidden" id="nameId'+row.id+'" value="'+row.blackWhiteName+'" />';
					 return str;
				}
				},
				{
					"targets" : [1],
					"render" : function(data, type, row) {
						return row.blackWhiteName;
					}
				},
				{
					"targets" : [2],
					"render" : function(data, type, row) {
						if(row.type=='0'){
							return '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.netblist"/>';
						} else {
						    return '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.netwlist"/>';
						}
					}
				}]	
	  });	
	  loadTableTd("#loadNameList",3);
   }
   
   // 打开黑名单或白名单列表
   // tag:标识  0.Android策略  1.ios策略
   function loadAppNameList(tag){
	   $("#loadAppNameList").modal("show");
	   var ids = "";
       var type = "";
       if(tag == 0){
    	   $("#myModal").modal("hide"); 
    	   ids = $("#chooseAppEnableIds").val();
    	   type = $("input[name='enableAppNameList']:checked").val();
       } else {
    	   $("#myIosModal").modal("hide"); 
    	   ids = $("#chooseIosAppEnableIds").val();
    	   type = $("input[name='enableIosApp']:checked").val();
       }
	   $("#appTag").val(tag);
	   $('#whiteAppBlackNamelist').dataTable().fnDestroy();
	   var dataTable = $('#whiteAppBlackNamelist').dataTable({
		   "dom":"<'m-r m-t-lg pull-right'f>t<'row '<'col-lg-2'l>r<'col-lg-3'i><'pull-right'p>>",
		        "searching" : false,
				"autoWidth" : false,
				"ordering" : false,
				"bSort" : false,
				"serverSide" : true,
				"bLengthChange" : true,
				"bPaginate" : true,
				"pageLength" : 10,
				"pagingType" : "full_numbers",
				"oLanguage": {
					"sUrl":languageUrl
			    },
				"ajax" : {
  					"dataType" : 'json',
					"type" : "get",
					"url" : ctx+"/institution/device/policy/queryNameList?now="+ new Date().getTime(),
					"data" : {"type":type}
				},
				"columnDefs" : [
			   {
				"targets" : [0],
				"render" : function(data, type, row) {
					 ids = ids +",";
					 var str = '<input type="checkbox" id="appNameList'+row.id+'" name="appNameList"'; 
					 if(ids.indexOf(row.id)>=0){
						 str += ' checked="checked" ';
					 }
					 str += 'value="'+row.id+'" />';
					 str += '<input type="hidden" id="appNameId'+row.id+'" value="'+row.listName+'" />';
					 return str;
				}
				},
				{
					"targets" : [1],
					"render" : function(data, type, row) {
						return row.listName;
					}
				},
				{
					"targets" : [2],
					"render" : function(data, type, row) {
						if(row.listType=='0'){
							return '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.appWhiteName"/>';
						} else {
							return '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.appBlackName"/>';
						}
					}
				}]	
	  });
	   loadTableTd("#loadAppNameList",3);
   }
     
   // 关闭选择黑白名单弹出框
   function openNameList(){
	   var tag = $("#nameTag").val();
	   $("#loadNameList").modal("hide");
	   if(tag==0){
		   $("#myModal").modal("show");
	   } else {
		   $("#myIosModal").modal("show");
	   }
	   
   }
   
   // 关闭选择应用黑白名单弹出框
   function openAppNameList(){
	   var tag = $("#appTag").val();
	   $("#loadAppNameList").modal("hide");
	   if(tag==0){
		   $("#myModal").modal("show");
	   } else {
		   $("#myIosModal").modal("show");
	   }
   }
   
   // 启用黑名单/白名单
   function enableNameIds(tag){
	  $("#chooseEnableIds").val("");
	  $("#nameListText").val("");
	  if(tag=="0"){
		  $("#enableWords").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.enabledesc"/>');
	  } else {
		  $("#enableWords").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.disabledesc"/>');
	  }
	  $("#visitLimit").removeAttr("checked"); 
   }
   
   // 启用黑名单/白名单
   function enableNameIds(tag){
	  $("#chooseEnableIds").val("");
	  $("#nameListText").val("");
	  if(tag=="0"){
		  $("#enableWords").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.enabledesc"/>');
	  } else {
		  $("#enableWords").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.disabledesc"/>');
	  }
	  $("#visitLimit").removeAttr("checked"); 
   }
   
   // 启用黑名单/白名单
   function enableIosName(tag){
	  $("#chooseIosEnableIds").val("");
	  $("#iosNameListText").val("");
	  if(tag=="0"){
		  $("#iosEnableWords").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.enabledesc"/>');
	  } else {
		  $("#iosEnableWords").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.disabledesc"/>');
	  }
	  $("#iosVisitLimit").removeAttr("checked"); 
   }
   
   // 启用应用黑名单/白名单
   function enableAppNameIds(tag){
	  $("#chooseAppEnableIds").val("");
	  $("#appNameListText").val("");
	  if(tag=="0"){
		  $("#appEnableWords").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.enableAppdesc"/>');
	  } else {
		  $("#appEnableWords").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.disableAppdesc"/>');
	  }
   }
   
   // 启用应用黑名单/白名单
   function enableIosAppName(tag){
	  $("#chooseAppEnableIds").val("");
	  $("#appNameListText").val("");
	  if(tag=="0"){
		  $("#appIosEnableWords").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.enableAppdesc"/>');
	  } else {
		  $("#appIosEnableWords").html('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.disableAppdesc"/>');
	  }
   }
   
   //选择名单(nameTag标识:0.android  1.ios) 
   function chooseNameList(){
	   var tag = $("#nameTag").val();
	   var nameListIds = "";
	   var nameListText = "";
	   $('input[name="nameList"]').each(function(){ 
		   var tempVal = $(this).val();
		   var tempText = $("#nameId"+tempVal).val();
		   if($(this).prop("checked")){
			   nameListIds += tempVal +",";
			   nameListText += tempText +",";
		   } 
	   }); 
	   if(nameListIds==null||nameListIds==""){
		   alert('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.choosename"/>');
		   return false;
	   }
	   nameListIds = nameListIds.substring(0,nameListIds.length-1);
	   nameListText = nameListText.substring(0,nameListText.length-1);
	   if(tag==0){
		  $("#chooseEnableIds").val(nameListIds);
		  $("#nameListText").val(nameListText);
		  $("#loadNameList").modal("hide");
		  $("#myModal").modal("show");
	   } else {
		  $("#chooseIosEnableIds").val(nameListIds);
		  $("#iosNameListText").val(nameListText);
		  $("#loadNameList").modal("hide");
		  $("#myIosModal").modal("show");
	   }
   }

   //选择名单(appTag:0.android  1.ios) 
   function chooseAppNameList(){
	   var appTag = $("#appTag").val();
	   var nameListIds = "";
	   var nameListText = "";
	   $('input[name="appNameList"]').each(function(){ 
		   var tempVal = $(this).val();
		   var tempText = $("#appNameId"+tempVal).val();
		   if($(this).prop("checked")){
			   nameListIds += tempVal +",";
			   nameListText += tempText +",";
		   } 
	   }); 
	   if(nameListIds==null||nameListIds==""){
		   alert('<fmt:message key="tiles.views.institution.devicepolicy.indexscript.choosename"/>');
		   return false;
	   }
	   nameListIds = nameListIds.substring(0,nameListIds.length-1);
	   nameListText = nameListText.substring(0,nameListText.length-1);
	   if(appTag==0){
		  $("#chooseAppEnableIds").val(nameListIds);
		  $("#appNameListText").val(nameListText);
		  $("#loadAppNameList").modal("hide");
		  $("#myModal").modal("show");
	   } else {
		   $("#chooseIosAppEnableIds").val(nameListIds);
		   $("#appIosNameListText").val(nameListText);
		   $("#loadAppNameList").modal("hide");
		   $("#myIosModal").modal("show");
	   }
   } 
   
   // 将上网时间段组成字符串
   var separator = "|";
   var str = "";
   function addSeparatorStr(startTime,endTime){
	   str = separator;
	   if((startTime==null||startTime=="")&&(endTime==null||endTime=="")){
		   str += "00:00-00:00"
	   } else if((startTime!=null&&startTime!="")&&(endTime==null||endTime=="")){
		   str += startTime;
	   } else if((endTime!=null&&endTime !="")&&(startTime==null||startTime=="")){
		   str += endTime;
	   } else {
		   str += startTime+","+endTime;
	   }
	   return str;
   }
   
   // 保存Android策略配置
   function saveAndroidPolicy(){
	   saveAndroidPolicyValidator.validate();
       if(saveAndroidPolicyValidator.isValid()!=null&&!saveAndroidPolicyValidator.isValid()){
    	   return false;
       }
	   var id = $("#id").val();
	   var name = $("#name").val();
	   var description = $("#description").val();
	   var departIds="";
	   var nodeChecked=$('#tree').treeview('getChecked');
	   if(nodeChecked.length>0){
		   for(var i=0;i<nodeChecked.length;i++){
				departIds=nodeChecked[i].tags.id+","+departIds;
		   }
		   departIds=departIds.substr(0,departIds.length-1);
	   }
	   var virtualIds = "";
	   $('input[name="virtualIds"]:checked').each(function(){ 
		   var tempVal = $(this).val();
		   virtualIds+=tempVal+",";
	   }); 
	   if(virtualIds!=""){
		   virtualIds = virtualIds.substring(0,virtualIds.length-1);
	   }
	   // 密码
	   var passwordLength=$("#passwordLength").val();
	   // 密码复杂度要求
	   var passwordComplexity = $("#passwordComplexity").val();
	   // 自动锁定前最长时间
	   var lockLongestTime = $("#lockLongestTime").val();
	   // 密码有效期
	   var passwordValidity = $("#passwordValidity").val();
	   // 最大尝试次数
	   var attemptTimes = $("#attemptTimes").val();
	   // 安全
	   // 设备加密
	   var deviceEncryption = $("input[name='deviceEncryption']:checked").val();
	   if(deviceEncryption=="on"){
		   if(passwordLength==null||passwordLength==""){
				   for(var i=0;i<5;i++){
				   var j = 0;
				   if(i==1){
					   j = i+1;
					   $("#deviceTab ul li:eq("+i+")").prop("class","active");
					   $("#tab"+j).prop("class","tab-pane active"); 
				   } else {
					   j = i+1;
					   $("#deviceTab ul li:eq("+i+")").removeAttr("class");
					     $("#tab"+j).prop("class","tab-pane");  
				   }
			   }
			   $("#passwordLength").addClass("parsley-error");
			   $("#passwordLength-modify-error").removeClass("hidden"); 
		      return false;
		   } else if(passwordComplexity==null||passwordComplexity==""){
				   for(var i=0;i<5;i++){
				   var j = 0;
				   if(i==1){
					   j = i+1;
					   $("#deviceTab ul li:eq("+i+")").prop("class","active");
					   $("#tab"+j).prop("class","tab-pane active"); 
				   } else {
					   j = i+1;
					   $("#deviceTab ul li:eq("+i+")").removeAttr("class");
					     $("#tab"+j).prop("class","tab-pane");  
				   }
			   }
		    	$("#passwordComplexity").addClass("parsley-error");
			    $("#passwordComplexity-modify-error").removeClass("hidden");
			    return false;
		   }
		   deviceEncryption = 1;
	   } else {
	    	$("#passwordComplexity").removeClass("parsley-error");
		    $("#passwordComplexity-modify-error").addClass("hidden");
		   deviceEncryption = 0;
	   }
	   // 检测SD卡
	   var sdEncryption = $("input[name='sdEncryption']:checked").val();
	   if(sdEncryption=="on"){
		   sdEncryption = 1;
	   } else {
		   sdEncryption = 0;
	   }
	   // 限制
	   var allowUseCamera = $("input[name='allowUseCamera']:checked").val();
	   if(allowUseCamera=="on"){
		   allowUseCamera = 1;
	   } else {
		   allowUseCamera = 0;
	   }
	   // 允许使用WIFI
	   var allowUseWifi = $("input[name='allowUseWifi']:checked").val();
	   if(allowUseWifi=="on"){
		   allowUseWifi = 1;
	   } else {
		   allowUseWifi = 0;
	   }
	   // 允许使用蓝牙
	   var allowUseBluetooth = $("input[name='allowUseBluetooth']:checked").val();
	   if(allowUseBluetooth=="on") {
		   allowUseBluetooth = 1;
	   } else {
		   allowUseBluetooth = 0;
	   }
	   
	   var allowUseGps = $("input[name='allowUseGps']:checked").val();
	   if(allowUseGps=="on") {
		   allowUseGps = 1;
	   } else {
		   allowUseGps = 0;
	   }
	   // 允许使用麦克风
	   var allowMicrophone = $("input[name='allowMicrophone']:checked").val();
	   if(allowMicrophone=="on"){
		   allowMicrophone = 1;
	   } else {
		   allowMicrophone = 0;
	   }
	   var str = "";
	   // 是否上网时间限制
	   // 检测SD卡
	   var visitLimit = $("input[name='visitLimit']:checked").val();
	   if(visitLimit=="on"){
		   visitLimit = 1;
		   for(var i=1;i<8;i++){
			   str += addSeparatorStr($("#startTime"+i).val(),$("#endTime"+i).val());
		   }
		   str += "|";
	   } else {
		   visitLimit = 0;
	   }

	   // 网页限制
	   var enableBlacklist = $("input[name='enableNameList']:checked").val();
	   var chooseEnableIds = $("#chooseEnableIds").val();
	   // 应用限制
	   var enableAppNameList = $("input[name='enableAppNameList']:checked").val();
	   var chooseAppEnableIds = $("#chooseAppEnableIds").val();
	   $("#myModal #saveBtn a").eq(0).attr("disabled",true); 
		var postData = {
		    token:'',
		    list:'',
			params : {}
		};
		var userIds = "";
		if(null!=idArray&&idArray.length>0){
			for(var i=0;i<idArray.length;i++){
				userIds += idArray[i]+",";
			}
			if(userIds!=""){
				userIds = userIds.substring(0,userIds.length-1);
			}
		}
		var imgs=document.getElementById("imgs");
	   if(imgs!=null&&imgs!=""){
		     var imgObj = imgs.getElementsByTagName("li");
			var count = imgObj.length;
			var list = "";
			for(var i=0;i<count;i++){
				var saftyType = '';
				var wifikey = '';
				var eap = '';
				var stageAuthen = '';
				var identity = '';
				var anonymousIdentity = '';
				var ca = '';
				var user = '';
				list += '{"ssid":"';

				if(i==0){
					list += $("#ssid").val();
					list += '","isAutojoin":';
					// 自动加入
					var autojoin = $("input[name='autojoin']:checked").val();
					var isAutojoin = 0;
				    if(autojoin=="on"){
				    	isAutojoin = 1; 
				    } else {
				    	isAutojoin = 0;
				    }
					list += isAutojoin;
				    saftyType = $("#saftyType").val();				    	
				    if(saftyType!=null&&saftyType!=""&&saftyType!=undefined){
				    	list += ',"saftyType":'+saftyType;
				    } else {
				    	list += ',"saftyType":""';
				    }
				    wifikey = $("#wifikey").val();
				    if(wifikey!=null&&wifikey!=""&&wifikey!=undefined){
				       list += ',"wifikey":"'+wifikey+'"';
				    } else {
				    	list += ',"wifikey":""';
				    }
				    eap = $("#eap").val();
				    if(eap!=null&&eap!=""&&eap!=undefined){
					    list += ',"eap":'+eap;
					} else {
						list += ',"eap":""';
					}
				    stageAuthen = $("#stageAuthen").val();
				    if(stageAuthen!=null&&stageAuthen!=""&&stageAuthen!=undefined){
					    list += ',"stageAuthen":'+stageAuthen;
					} else {
						list += ',"stageAuthen":""';
					}
				    identity = $("#identity").val();
				    if(identity!=null&&identity!=""&&identity!=undefined){
					    list += ',"identity":"'+identity+'"';
					} else {
						list += ',"identity":""';
					}
				    anonymousIdentity = $("#anonymousIdentity").val();	
				    if(anonymousIdentity!=null&&anonymousIdentity!=""&&anonymousIdentity!=undefined){
					    list += ',"anonymousIdentity":"'+anonymousIdentity+'"';
					} else {
						list += ',"anonymousIdentity":""';
					}
				    ca = $("#ca").val();
				    if(ca!=null&&ca!=""&&ca!=undefined){
					    list +=',"ca":'+ca;
					} else {
						list +=',"ca":""';
					}
					user = $("#user").val();
				    if(user!=null&&user!=""&&user!=undefined){
					    list += ',"user":'+user;
					} else {
						list += ',"user":""';
					}
				    list +='},'; 
				} else {
					list += $("#ssid"+i).val();
					list += '","isAutojoin":';
					// 自动加入
					var autojoin = $("input[name='autojoin"+i+"']:checked").val();
					var isAutojoin = 0;
				    if(autojoin=="on"){
				    	isAutojoin = 1;
				    } else {
				    	isAutojoin = 0;
				    }
				    list += isAutojoin;
				    saftyType = $("#saftyType"+i).val();
				    if(saftyType!=null&&saftyType!=""&&saftyType!=undefined){
				    	list += ',"saftyType":'+saftyType;
				    } else {
				    	list += ',"saftyType":""';
				    }
				    wifikey = $("#wifikey"+i).val();
				    if(wifikey!=null&&wifikey!=""&&wifikey!=undefined){
					    list += ',"wifikey":"'+wifikey+'"';
				     } else {
					    list += ',"wifikey":""';
					 }
				    eap = $("#eap"+i).val();
				    if(eap!=null&&eap!=""&&eap!=undefined){
					    list += '","eap":'+eap;
					} else {
						list += ',"eap":""';
					}
				    stageAuthen = $("#stageAuthen"+i).val();
				    if(stageAuthen!=null&&stageAuthen!=""&&stageAuthen!=undefined){
					    list += ',"stageAuthen":'+stageAuthen;
					} else {
						list += ',"stageAuthen":""';
					}
				    identity = $("#identity"+i).val();
				    if(identity!=null&&identity!=""&&identity!=undefined){
					    list += ',"identity":"'+identity+'"';
					} else {
						list += ',"identity":""';
					}
				    anonymousIdentity = $("#anonymousIdentity"+i).val();
				    if(anonymousIdentity!=null&&anonymousIdentity!=""&&anonymousIdentity!=undefined){
					    list += ',"anonymousIdentity":"'+anonymousIdentity+'"';
					} else {
						list += ',"anonymousIdentity":""';
					}
					ca = $("#ca"+i).val();
				    if(ca!=null&&ca!=""&&ca!=undefined){
					    list +=',"ca":'+ca;
					} else {
						list +=',"ca":""';
					}
					user = $("#user"+i).val();
				    if(user!=null&&user!=""&&user!=undefined){
					    list += ',"user":'+user;
					} else {
						list += ',"user":""';
					}
				    list +='},'; 
				}
			}
			list = list.substring(0,list.length-1);
			list = "["+list+"]";
			postData.list = list;
		}			
	   postData.params.id = id;
	   postData.params.name = name;
	   postData.params.description = description;
	   postData.params.chooseDepartIds = departIds;
	   postData.params.virtualIds = virtualIds;
	   postData.params.userIds = userIds;
	   postData.params.passwordLength = passwordLength;
	   postData.params.passwordComplexity = passwordComplexity;
	   postData.params.lockLongestTime = lockLongestTime;
	   postData.params.passwordValidity = passwordValidity;
	   postData.params.attemptTimes = attemptTimes;
	   postData.params.deviceEncryption = deviceEncryption;
	   postData.params.sdEncryption = sdEncryption;
	   postData.params.allowUseCamera = allowUseCamera;
	   postData.params.allowUseWifi = allowUseWifi;
	   postData.params.allowUseBluetooth = allowUseBluetooth;
	   postData.params.allowMicrophone = allowMicrophone;
	   postData.params.enableBlacklist = enableBlacklist;
	   postData.params.chooseEnableIds = chooseEnableIds;
	   postData.params.enableAppNameList = enableAppNameList;
	   postData.params.chooseAppEnableIds = chooseAppEnableIds;		
	   postData.params.visitLimit = visitLimit;
	   postData.params.visitTimeStr = str;
	   postData.params.allowUseGps = allowUseGps;
		   /* 		   postData.params.startTime = startTime;
		   postData.params.endTime = endTime; */
	   var token = $("#tokenId").val();
	   postData.token = token;
	   // 判断id是否为空?为空新增 不为空修改
	   var url = "";
	   var csrf = "${_csrf.token}";
	   if(id!=null&&id!=""){
		   url = ctx+"/institution/device/policy/updateAndroidPolicy?_csrf="+csrf;
	   } else {
		   url = ctx+"/institution/device/policy/saveAndroidPolicy?_csrf="+csrf;
	   }
	    $.ajax({
		   "dataType" : 'json',
           "data": postData,
           "type": "POST",
           "url":url,
           "success": function(data){
        	    $(".notify").notify({type:data.type,message: { html: false, text: data.message}}).show();
        	    if(data.type=="success"){
        		    $("#myModal").modal("hide");
    	            //loadDevicePolicy();
    	            window.location.href = ctx +"/institution/device/policy";
    	        }
           	    return false;
           }
	   });
   }
   exists();
   // 判断策略名称是否存在
   function exists(){
     var id = $("#id").val();
	 $("#name").parsley().addAsyncValidator('existsValidate',function(xhr){
		return (xhr.responseText.indexOf('success') >= 0); 
	 },"${existsUrl}", { "type": "GET", "dataType": "json", "contentType": "application/json; charset=utf-8", "data": {"id":id} } );
   }
    
    iosExists(); 
   // 判断Ios策略名称是否存在
   function iosExists(){
	   var id = $("#iosId").val();
	   $("#iosName").parsley().addAsyncValidator('existsValidate',function(xhr){
				return (xhr.responseText.indexOf('success') >= 0); 
			 },"${iosExistsUrl}", { "type": "GET", "dataType": "json", "contentType": "application/json; charset=utf-8", "data": {"iosId":id} } );
   }
   
   // check Ios相机
   function checkIosCamera(){
	   var checkStatus = $("input[name='allowIosUseCamera']:checked").val();
	   if(checkStatus=="on"){
		   $("#allowFaceTime").removeAttr("disabled");
	   } else {
		   $("#allowFaceTime").prop("disabled", true);
		   $("#allowFaceTime").removeAttr("checked");
	   }
   }
   
   // 重置密码长度
   function resetPasswordLength(){
	   // 密码
	   var passwordLength=$("#passwordLength").val();
	   if(passwordLength==null||passwordLength==""){
			$("#passwordLength").removeAttr("disabled");
			$("#passwordComplexity").prop("disabled", true);
			$("#lockLongestTime").prop("disabled", true);
			$("#passwordValidity").prop("disabled", true);
			$("#attemptTimes").prop("disabled", true);
    		$("#passwordLength option:first").prop("selected",true);
        	$("#passwordComplexity option:first").prop("selected",true);
        	$("#lockLongestTime option:first").prop("selected",true);
        	$("#passwordValidity option:first").prop("selected",true);
        	$("#attemptTimes option:first").prop("selected",true);
	   } else {
			$("#passwordLength").removeAttr("disabled");
			$("#passwordComplexity").removeAttr("disabled");
			$("#lockLongestTime").removeAttr("disabled");
			$("#passwordValidity").removeAttr("disabled");
			$("#attemptTimes").removeAttr("disabled");
		   var deviceEncryption = $("input[name='deviceEncryption']:checked").val();
		   if(deviceEncryption=="on"){
			   if(passwordLength!=null&&passwordLength!=""){
			       $("#passwordLength").removeClass("parsley-error");
				   $("#passwordLength-modify-error").addClass("hidden");
			   }
		   }
	   }
   }		   
   
   // 重置复杂密码
   function resetPasswordComplexity(){
	  // 密码复杂度要求
	  var passwordComplexity = $("#passwordComplexity").val();
	  var deviceEncryption = $("input[name='deviceEncryption']:checked").val();
	  if(deviceEncryption=="on"){
	    if(passwordComplexity!=null&&passwordComplexity!=""){
	      $("#passwordComplexity").removeClass("parsley-error");
	      $("#passwordComplexity-modify-error").addClass("hidden");
	    }
	  }
   }
	 
	// 加载已分配设备数据
	function loadAssignedDevice(id,type){
	   $("#loadAssignedDevice").modal("show");
	   $('#loadAssignedList').dataTable().fnDestroy();
	   //加载策略列表数据 
	   var dataTable = $('#loadAssignedList').dataTable({
 		    "dom": 'rt<"bottom"pli><"clear">',
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
					"url" : ctx+"/institution/device/policy/queryDeviceAll?now="+ new Date().getTime(),
					"data" : {"policyId":id,"type":type}
				},
				"columnDefs" : [
 			   {
			"targets" : [0],
			"render" : function(data, type, row) {
				return '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.assigned"/>';
				
			}
		},
		{
			"targets" : [1],
			"render" : function(data, type, row) {
				return row.user.realname;
			}
		},
		{
			"targets" : [2],
			"render" : function(data, type, row) {
				   return row.user.username;;
			}
		},
		{
			"targets" : [3],
			"render" : function(data, type, row) {
				return row.device_name;
			}
		},
		{
			"targets" : [4],
			"render" : function(data, type, row) {
				return row.os_version;
			}
		},
		{
			"targets" : [5],
			"render" : function(data, type, row) {
				return row.belong;
			}
		},{
			"targets" : [6],
			"render" : function(data, type, row) {
				var updateTime = row.update_time;
				if(updateTime!=null&&updateTime!=""){
					updateTime = new Date(parseInt(updateTime)).Format("yyyy-MM-dd hh:mm:ss");
				}
				return updateTime;
			}
		}]});
	}

	function onChangeEndTime(){
		var endValidate=$("#endTime").parsley();
		endValidate.validate();
		if(endValidate.isValid()){
			return true;
		} else {
			return false;
		}
	}
	
	function parseToJsonStr(arr)
	{
		if (null!= arr && arr.length > 0)
		{
			return JSON.stringify(arr).toString();
		}
		
		return "";
	}
	
	// 显示
	function addArray(id,name){
		var user = new Object();
		user.id = id;
		user.name = name;
		nameArray.push(user);
		idArray.push(id);
		displayUsers(nameArray,null);
	}
	
	// 显示
	function displayArray(id,name,tag){
		var user = new Object();
		user.id = id;
		user.name = name;
		nameArray.push(user);
		idArray.push(id);
		displayUsers(nameArray,tag);
	}
	
	// 编辑显示
	function modifyArray(id,name){
		var newIdArr = [];
		var newNameArr = [];
		if(null!=idArray&&idArray.length>0){
			for(var i=0;i<idArray.length;i++){
				var obj = idArray[i];
				if(obj!=id){
					newIdArr.push(obj);
				}
			}
		}
		idArray = newIdArr;
		if(null!=nameArray&&nameArray.length>0){
			for(var j=0;j<nameArray.length;j++){
				var obj = nameArray[j];
				if(obj.id!=id){
					var user = new Object();
					user.id = obj.id;
					user.name = obj.name;
					newNameArr.push(user);
				}
			}
		}
		nameArray = newNameArr;
		displayUsers(newNameArr,null);
	}
	
	// 显示
	function removeArray(id,name){
		var newIdArr = [];
		var newNameArr = [];
		if(null!=idArray&&idArray.length>0){
			for(var i=0;i<idArray.length;i++){
				var obj = idArray[i];
				if(obj!=id){
					newIdArr.push(obj);
				}
			}
		}
		idArray = newIdArr;
		if(null!=nameArray&&nameArray.length>0){
			for(var j=0;j<nameArray.length;j++){
				var obj = nameArray[j];
				if(obj.id!=id){
					var user = new Object();
					user.id = obj.id;
					user.name = obj.name;
					newNameArr.push(user);
				}
			}
		}
		nameArray = newNameArr;
		displayUsers(newNameArr,null);
	}
	
	function displayUsers(nameArray,tag){
		if(null!=nameArray&&nameArray.length>0){
			var listr  = "";
			for(var j=0;j<nameArray.length;j++){
				var user = nameArray[j];
				if(tag==0){
       			   listr += '<li class="userLi"><div class="col-lg-10">'+user.name+'</div><div class="col-lg-1"><a onclick="removeArray('+user.id+',\''+user.name+'\')"></div></li>';
				} else {
	       		   listr += '<li class="userLi"><div class="col-lg-10">'+user.name+'</div><div class="col-lg-1"><a onclick="removeArray('+user.id+',\''+user.name+'\')"><i class="glyphicon glyphicon-remove limargin"></i></div></li>';
				}
			}
			$("#policyIosVirtualRight").html(listr);
			$("#policyVirtualRight").html(listr);
		} else {
			$("#policyVirtualRight").html("");
			$("#policyIosVirtualRight").html("");
		}
	}
 
	// 新增ios策略
	function addIosPolicy(){
	   	$.ajax({
			"dataType" : 'json',
	        "type": "GET",
	        "url":ctx+"/institution/device/policy/addToken?now="+ new Date().getTime(),
	        "success": function(data){
		         initIosTab();
	        	 iosExists();
		         $("#tokenIosId").val(data);
		         $("#myIosModal").modal("show");
	         }
	    });
	}
	
    // 选择安全类型
    function chooseSafeType(){
    	var safetype = $("#safeType").val();
    	var html = "";
    	if(safetype==0){
    		html = '<div class="form-group">';
    		html += '<div class="col-sm-12">';
            html += '<label class="col-sm-2 control-label"><fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowed"/>URL</label>';
            html += '<div class="col-sm-7 paddingLR">';
            html += '<input type="text" id="allowUrl" name="allowUrl" class="input-sm form-control"/>';
            html += '</div>';
            html += '<div class="col-sm-2 cusorPoint" onclick="addAllowUrl()"><i class="fa fa-plus"></i>&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.add"/></div>';
            html += '</div>';
            html += '</div>';
            html += '<div class="form-group">';
            html += '<div class="col-sm-12">';
            html += '<label class="col-sm-2 control-label"></label>';
            html += '<div class="col-sm-7 paddingLR">';
            html += '<div class="filterStyle" id="allowUrlHtml"></div>';
            html += '</div>';
            html += '<div class="col-sm-2 cusorPoint" onclick="removeAllowUrl()"><i class="glyphicon glyphicon-remove"></i>&nbsp;<fmt:message key="general.jsp.delete.label"/></div>';
            html += '</div>';
            html += '</div>';
            html += '<div class="form-group">'; 
            html += '<div class="col-sm-12">';
            html += '<label class="col-sm-2 control-label"><fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.notallow"/>URL</label>';
            html += '<div class="col-sm-7 paddingLR">';
            html += '<input type="text" id="notAllowUrl" name="notAllowUrl" class="input-sm form-control"/>';
            html +='</div>';
            html += '<div class="col-sm-2 cusorPoint" onclick="allowNotAllowUrl()"><i class="fa fa-plus"></i>&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.add"/></div>';
            html += '</div>';   
            html += '</div>';
            html += '<div class="form-group">';
            html += '<div class="col-sm-12">';
            html += '<label class="col-sm-2 control-label"></label>';
            html += '<div class="col-sm-7 paddingLR">';
            html += '<div class="filterStyle" id="notAllowHtml"></div>';
            html += '</div>';
            html += '<div class="col-sm-2 cusorPoint" onclick="removeNotAllowUrl()"><i class="glyphicon glyphicon-remove"></i>&nbsp;<fmt:message key="general.jsp.delete.label"/></div>';
            html += '</div>'
            html += '</div>'; 
            $("#safeTypeDesc").html(html);
            allowUrlArray = [];
            notAllowUrlArray = [];
    	} else {
    		html = '<div class="form-group">';
    		html += '<div class="col-sm-2">';
    		html += '<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allownet"/>';
    		html += '</div>'; 
            html += '<div class="col-sm-7">';
            html += '<div class="col-sm-12 bookTitle">';
            html += '<div class="col-sm-4 bookUrl">';
            html += 'URL';
            html += '</div>'; 
            html += '<div class="col-sm-4 bookName">';
            html += '<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.name"/>';
            html += '</div>';
            html += '<div class="col-sm-4 bookmark">';
            html += '<fmt:message key="tiles.views.institution.ios.device.policy.indexscript.bookmark"/>';
            html += '</div>';
            html += '</div>';
            html += '<div class="webcontent_outer">';
            html += '<div id="webIosContent" class="col-sm-12" style="padding-left:0px;padding-right:0px;"></div>';
            html += '</div>';
            html += '</div>';
            html += '<div class="col-sm-2">';
            html += '<div class="col-sm-12 cusorPoint" onclick="displayBookmark()">';
            html += '<i class="fa fa-plus"></i>&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.add"/>';
            html += '</div>';
            
            html += '<div class="col-sm-12 cusorPoint" style="margin-top:5px;">';
            html += '<i class="glyphicon glyphicon-remove" onclick="removeIosContent()"></i>&nbsp;<fmt:message key="general.jsp.delete.label"/>';
            html += '</div>';
            
            html += '</div>';
    		html += '</div>';
    		$("#safeTypeDesc").html(html);
    	}
    }
    
    // 添加允许的url
    function addAllowUrl(){
    	var allowUrl = $("#allowUrl").val();
    	if(allowUrl==null||allowUrl==""){
    		$(".notify").notify({type:"warning",message: { html: false, text: '<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.pleaseinput"/>URL'}}).show();
    	}
    	var temp = 0;
    	for(var i=0;i<allowUrlArray.length;i++){
    		if(allowUrl==allowUrlArray[i]){
    			temp = 1;
    			break;
    		}
    	}
    	if(temp==1){
    		$(".notify").notify({type:"warning",message: { html: false, text: 'URL<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.pleaseinput"/>'}}).show();
    		return false;
    	}
    	allowUrlArray.push(allowUrl);
    	$("#allowUrl").val("");
    	displayAllowUrl(allowUrlArray);
    }
    
    // 添加不允许的url
    function allowNotAllowUrl(){
    	var notAllowUrl = $("#notAllowUrl").val();
    	if(notAllowUrl==null||notAllowUrl==""){
    		$(".notify").notify({type:"warning",message: { html: false, text: '<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.pleaseinput"/>URL'}}).show();
    	}
    	var temp = 0;
    	for(var i=0;i<notAllowUrlArray.length;i++){
    		if(notAllowUrl==notAllowUrlArray[i]){
    			temp = 1;
    			break;
    		}
    	}
    	if(temp==1){
    		$(".notify").notify({type:"warning",message: { html: false, text: 'URL<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.alreadyexist"/>'}}).show();
    		return false;
    	}
    	notAllowUrlArray.push(notAllowUrl);
    	$("#notAllowUrl").val("");
    	displayNotAllowUrl(notAllowUrlArray);
    }
    
    // 删除允许的url
    function removeAllowUrl(){
    	var tempArray = [];
    	var allowUrl = $("#allowHiddenUrl").val();
    	if(allowUrlArray.length>0){
    		for(var i=0;i<allowUrlArray.length;i++){
    			if(allowUrl!=allowUrlArray[i]){
    				tempArray.push(allowUrlArray[i]);
    			}
    		}
    	}
    	allowUrlArray = tempArray;
    	displayAllowUrl(allowUrlArray);
    }
    
    // 删除不允许的url
    function removeNotAllowUrl(){
    	var tempArray = [];
    	var notAllowUrl = $("#notAllowHiddenUrl").val();
    	if(notAllowUrlArray.length>0){
    		for(var i=0;i<notAllowUrlArray.length;i++){
    			if(notAllowUrl!=notAllowUrlArray[i]){
    				tempArray.push(notAllowUrlArray[i]);
    			}
    		}
    	}
    	notAllowUrlArray = tempArray;
    	displayNotAllowUrl(notAllowUrlArray);
    } 
    
    // 允许URL
    function displayAllowUrl(allowList){
    	if(null!=allowList&&allowList.length>0){
    		var allowStr = "";
    		for(var i=0;i<allowList.length;i++){
    			var urlObj = allowList[i];
    			allowStr += '<div name="displayAllowName" class="col-sm-12 liStyle" onclick="displayAllowHtml(this,\''+urlObj+'\')">'+urlObj+'</div>';
    		}
    		$("#allowUrlHtml").html(allowStr);
    	} else {
    		$("#allowHiddenUrl").val("");
    		$("#allowUrlHtml").html("");
    	}
    }
    
    // 不允许URL
    function displayNotAllowUrl(notAllowList){
    	if(null!=notAllowList&&notAllowList.length>0){
    		var notAllowStr = "";
    		for(var i=0;i<notAllowList.length;i++){
    			var urlObj = notAllowList[i];
    			notAllowStr += '<div name="displayNotAllowName" class="col-sm-12 liStyle" onclick="displayNotAllowHtml(this,\''+urlObj+'\')">'+urlObj+'</div>';
    		}
    		$("#notAllowHtml").html(notAllowStr);
    	} else {
    		$("#notAllowHiddenUrl").val("");
    		$("#notAllowHtml").html("");
    	}
    }
    
    // 点击允许的url
    function displayAllowHtml(obj,url){
    	var array = $("div[name=displayAllowName]");
    	if(array.length>0){
    		for(var i=0;i<array.length;i++){
    			if(url==array[i].innerText){
    				var attr = $(obj).attr("class")+" blue";
    				$(obj).attr("class",attr);
    			} else {
    				var attr = array[i].className;
    				if(attr.indexOf("blue")>0){
    					attr = attr.replace("blue","");
    					array[i].className = attr;
    				}
    			}
    		}
    	}
    	$("#allowHiddenUrl").val(url);
    }
    
    // 不允许的url
    function displayNotAllowHtml(obj,url){
    	var array = $("div[name=displayNotAllowName]");
    	if(array.length>0){
    		for(var i=0;i<array.length;i++){
    			if(url==array[i].innerText){
    				var attr = $(obj).attr("class")+" blue";
    				$(obj).attr("class",attr);
    			} else {
    				var attr = array[i].className;
    				if(attr.indexOf("blue")>0){
    					attr = attr.replace("blue","");
    					array[i].className = attr;
    				}
    			}
    		}
    	}
    	$("#notAllowHiddenUrl").val(url);
    }
    
    // 显示添加URL书签
    function displayBookmark(){
    	$("#bookmarkIosUrl").val("");
    	$("#bookmarkName").val("");
    	$("#bookmark").val("");
    	$("#addBookmarkModal").modal("show");
    }
    
    // 添加URL书签
    function addBookmark(){
    	var bookmarkUrl = $("#bookmarkIosUrl").val();
    	var bookmarkName = $("#bookmarkName").val();
    	var bookmark = $("#bookmark").val();
        var objUrl = new Object();
        objUrl.bookmarkUrl = bookmarkUrl;
        objUrl.bookmarkName = bookmarkName;
        objUrl.bookmark = bookmark;
        urlArray.push(objUrl);
        $("#addBookmarkModal").modal("hide");
        displayBookUrl(urlArray);
    }
    
    // 遍历展示书签
    function displayBookUrl(tempUrlArray){
    	if(null!=tempUrlArray&&tempUrlArray.length>0){
        	var html = "";
    		for(var i=0;i<tempUrlArray.length;i++){
    			var urlObj = tempUrlArray[i];
    			html += '<div name="limitUrl" class="col-sm-12 paddingLR cursor" onclick="clickBookmarkUrl(\''+urlObj.bookmarkUrl+'\')">';
                html += '<div class="col-sm-4 bookUrl borderLR borderBottom paddingLR">';
                html += urlObj.bookmarkUrl;
                html += '</div>'; 
                html += '<div class="col-sm-4 bookName borderLR borderBottom paddingLR">';
                html += urlObj.bookmarkName;
                html += '</div>';
                html += '<div class="col-sm-4 bookmark borderBottom paddingLR">';
                html += urlObj.bookmark;
                html += '</div>';
                html += '</div>';
    		}
    		$("#webIosContent").html(html);
    	} else {
    		$("#bookmarkUrl").val("");
    		$("#webIosContent").html("");
    	}
    }
    
    // 点击bookmarkurl
    function clickBookmarkUrl(bookmarkUrl){
    	var array = $("div[name=limitUrl]");
    	if(array.length>0){
    		for(var i=0;i<array.length;i++){
    			if(bookmarkUrl==array[i].innerText){
    				var attr = $(obj).attr("class")+" blue";
    				$(obj).attr("class",attr);
    			} else {
    				var attr = array[i].className;
    				if(attr.indexOf("blue")>0){
    					attr = attr.replace("blue","");
    					array[i].className = attr;
    				}
    			}
    		}
    	}
    	$("#bookmarkUrl").val(bookmarkUrl)
    }
    
    // 删除添加的网站
    function removeIosContent(){
    	var tempArray = [];
    	var bookmarkUrl = $("#bookmarkUrl").val();
    	if(urlArray.length>0){
    		for(var i=0;i<urlArray.length;i++){
    			var obj = urlArray[i];
    			if(bookmarkUrl!=obj.bookmarkUrl){
    				tempArray.push(obj);
    			}
    		}
    	}
    	urlArray = tempArray;
    	displayBookUrl(urlArray);
    }
    
    // 加载IOS策略
    function initPolicy(data){
   
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
			} else {
				$(this).parents("ul").siblings(".Js_curVal").find("input:text").val($(this).text().replace("&lt;","<").replace("&gt;",">")).css("color","#5A5A5A")
			}
			$(this).parents("ul").siblings(".Js_hiddenVal").attr("normal",$(this).text())
			$(this).parents("ul").siblings(".Js_hiddenVal").val($(this).attr("rel"))
			$(this).parents("ul.select-list").hide();
		});
		
    // 编辑或查看WIFI配置(tag=1表示编辑 0表示查看)
    function editIosWifiSetting(wifiArray,tag){
        var i = 0;
       var wifihtml = "";
       wifihtml += '<div id="iosLuanPo" style="height:430px">';
       wifihtml += '<ul id="iosImgs">';         
	   if(wifiArray!=null&&wifiArray!=""&&wifiArray.length>0){
		   var count = wifiArray.length;
		   for(var i=0;i<count;i++){
			   var obj = wifiArray[i];
			   var ssidval = obj.ssid;
			   var saftyIosType = obj.securityType;
			   var wifiIoskey = obj.password;
			   wifihtml += '<li id="ssidli'+i+'">';
	    	   // 服务级标识符
	    	   wifihtml += '<div class="form-group">';
	    	   wifihtml += '<div class="col-lg-3 control-label">';
	    	   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.serviceLevelIdentifier"/>(SSID)<span class="asterisk">*</span>';
	    	   wifihtml += '</div>';                                                  
	    	   wifihtml += '<div class="col-lg-7 control-label">';
	    	   wifihtml += '<input id="iosSsid'+i+'" name="iosSsid'+i+'" value='+ssidval+' data-parsley-required="true"  data-parsley-maxlength="40" type="text" class="input-sm form-control"/>';
	    	   wifihtml += '</div>';  
	    	   wifihtml += '<div class="col-lg-2 control-label">';
	    	   wifihtml += '<i class="fa fa-plus-circle fa-2x cursor" onclick="plusIosSSID()"></i>&nbsp;&nbsp;<i class="fa fa-minus-circle fa-2x cursor" onclick="minusIosSSID('+i+')"></i>';
	    	   wifihtml += '</div>';  
	    	   wifihtml += '</div>'; 
	    	   // 安全类型
	    	   wifihtml += '<div class="form-group">';
	    	   wifihtml += '<div class="col-lg-3 control-label">';
	    	   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.safetype"/>';
	    	   wifihtml += '</div>';    
	    	   wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
	    	   wifihtml += '<select id="saftyIosType'+i+'" class="form-control saftyType" onChange="onIosSaftyType('+i+')">';
	    	   wifihtml += '<option value="0" ';
	    	   if(saftyIosType==0){
	    		   wifihtml += 'selected="selected" ';
	    	   }
	    	   wifihtml += '><fmt:message key="tiles.views.institution.devicepolicy.indexscript.none"/></option>';
	    	   wifihtml += '<option value="1" ';
	    	   if(saftyIosType==1){
	    		   wifihtml += 'selected="selected" ';
	    	   }
	    	   wifihtml += '>WEP</option>';
	    	   wifihtml += '<option value="2" ';
	    	   if(saftyIosType==2){
	    		   wifihtml += 'selected="selected" ';
	    	   }
	    	   wifihtml += '>WPA/WPA2</option>';
	    	   wifihtml += '</select>';
	    	   wifihtml += '</div>'; ''
	    	   wifihtml += '<div class="col-lg-12" id="saftyIosTypeValue'+i+'" style="padding-left:5px;">';  
    		   if(saftyIosType==1||saftyIosType==2){
    			   wifihtml += '<div class="col-lg-3 control-label">';
    			   wifihtml += '<fmt:message key="tiles.views.institution.devicepolicy.indexscript.password"/>';
    			   wifihtml += '</div>';
    			   wifihtml += '<div class="col-lg-7 control-label" style="text-align:left">';
    			   wifihtml += '<input id="wifiIoskey'+i+'" type="password" class="input-sm form-control" value=\''+wifiIoskey+'\' />';
    			   wifihtml += '</div>'; 
    		   } 
	    	   wifihtml += '</div>';
	    	   wifihtml += '</div>'; 
	    	   wifihtml += '</li>';
		   }
    	   wifihtml += '</ul>';    
    	   wifihtml += '<ul id="iosNum">'; 
		   for(var i=0;i<count;i++){
			   if(i==0){
				   wifihtml += '<li class="current"></li>';
			   } else {
				   wifihtml += '<li class="old"></li>';
			   }
		   }
    	   wifihtml += '</ul>';   
    	   wifihtml += '</div>';
    	   $("#tab13").html(wifihtml);
    	   iosTab();
	    }
    }
 </script>