<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.softtek.mdm.util.CommUtil"%>
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
<spring:url value="/resources/js/autocomplete/jquery-ui.min.js" var="jqueryUiJs"/>
<script src="${jqueryUiJs}"></script>
<spring:url value="/system/param/manager/modifystyle" var="modifystyle" />
<script type="text/javascript">
var imgCtx = '<%=CommUtil.DISPLAY_LOGO_PATH%>';
$(function(){
	queryMdmParams();
});

$(".help-btn").each(function(){
	//$(this).hide();
	var $this=$(this)
	$this.click(function(){
		var top=20;
		var left=$this.offset().left+$this.outerWidth(true);
		$this.next(".help-cons").show();
	})
});

$(".help-cons").each(function(){
	$(this).hide();
});

$(".help-close").each(function(){
	$(this).click(function(){
		$(this).parent().hide();
	})
});

$(".help-close").each(function(){
	$(this).click(function(){
		$(this).parent().hide();
	})
});

$(function(){
	//点击
	$(".a-upload").on("change","input[type='file']",function(){
	    var filePath=$(this).val();
	    if(filePath.indexOf("png")!=-1){
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
	
	$("#sysToolBar li").click(function(){
		 changeStyle();
		 $(this).addClass('current');
		 var checkItem =$(this).attr("data-val");
		 if (checkItem == '0')
		 {
			 queryMdmParams();
		 }
		 else if (checkItem == '1')
		 {
			 queryEmailParams();
		 }
		 else if (checkItem == '2')
		 {
			 queryEmailModels();
		 }
	});
});
	
	
function changeStyle(){
	$("#sysToolBar li").each(function(){
		$(this).removeClass('current');
	});
}

function queryMdmParams(){
	var _csrf = "${_csrf.token}";
	$.ajax({
		"dataType" : 'json',
		"type" : "get",
		"async" : false,
		"url" : ctx + "/system/param/manager/queryMdmParam?now="
				+ new Date().getTime(),
		"data" : {
			"_csrf" : _csrf,
		},
		"success" : function(data) {
			if (data.result == 'success')
			{
				$("#mdmAddress").val(data.sysParamModel.mdmAddress);
				$("#outControlTimeLimit").val(data.sysParamModel.outManagerTime);
				$("#deviceInfoCollectPeriod").val(data.sysParamModel.deviceInfoCollectTime);
				$("#deviceIllegalCollectPeriod").val(data.sysParamModel.illegalInfoCollectTime);
			}
		},
		"error" : function(data) {
		}
	});
}

function updateMdmParams() {
	var validator = $('#formOne').parsley();
	validator.validate();
    if(!validator.isValid())
    {
    	return;
    }
	var _csrf = "${_csrf.token}";
	var address = $("#mdmAddress").val();
	var outControlTimeLimit = $("#outControlTimeLimit").val();
	var deviceInfoCollPeriod = $("#deviceInfoCollectPeriod").val();
	var deviceIllegalCollPeriod = $("#deviceIllegalCollectPeriod").val();

	$.ajax({
		"dataType" : 'json',
		"type" : "get",
		"async" : false,
		"url" : ctx + "/system/param/manager/updateMdmParam",
		"data" : {
			"_csrf" : _csrf,
			"address" : address,
			"outControlTimeLimit" : outControlTimeLimit,
			"deviceInfoCollPeriod" : deviceInfoCollPeriod,
			"deviceIllegalCollPeriod" : deviceIllegalCollPeriod
		},
		"success" : function(data) {
			if(data.type != "success")
			{
				$(".notify").notify({
					type : data.type,
					message : {
						html : false,
						text : data.message
					}
				}).show();
			}
			else
			{
				$(".notify").notify({
					type : data.type,
					message : {
						html : false,
						text : data.message
					}
				}).show();
			}
		}
	});
}
function openlogomodal(){
	$('#file').val("");
	$(".fileerrorTip").html("");
	$('#logoModal').modal(open); 
}
function changeLogo(){
	var filePath = $("#file").val();
	var url; 
	if(filePath!=""&&filePath!=null){
	if (navigator.userAgent.indexOf("MSIE")>=1) {   // IE 
	url = document.getElementById("file").value; 
	} else if(navigator.userAgent.indexOf("Firefox")>0) { // Firefox 
	url = window.URL.createObjectURL(document.getElementById("file").files.item(0)); 
	} else if(navigator.userAgent.indexOf("Chrome")>0) { // Chrome 
	url = window.URL.createObjectURL(document.getElementById("file").files.item(0)); 
	} 
	if (filePath.indexOf("png") != -1) {
		var formData = new FormData();
		formData.append("files", $("#file")[0].files[0]);
		 $.ajax({
			 data : formData,
			type : "POST",
			url : ctx + "/system/param/manager/updatelogo",
			cache : false,
			headers : {
				"${_csrf.headerName}" : "${_csrf.token}",
			},
			contentType : false,
			processData : false,
			success : function(result) {
				if($.parseJSON(result).result=="success"){
					$('#logoModal').modal("hide");
					$("#ischangelgo").val("0");
					$("#logopath").val($.parseJSON(result).path);
					if($("#file").val()!=null){
						$(".notify").notify({type:"success",message: { html: false, text:'<fmt:message key="web.institution.systemset.script.style.file.upload.success.messages"/>'}}).show();
						$("#logoUrl1").attr("src",url);
					}else{   
						$(".fileerrorTip").html('<fmt:message key="web.institution.systemset.script.style.file.erro1.messages"/>').show();
					}
				}else if($.parseJSON(result).result=="false"){
					$(".fileerrorTip").html('<fmt:message key="web.institution.systemset.script.style.file.erro2.messages"/>').show();
					$(".notify").notify({type:"warning",message: { html: false, text:'<fmt:message key="web.institution.systemset.script.style.file.upload.erro.messages"/>'}}).show();
				}else{
					$(".notify").notify({type:"warning",message: { html: false, text:'<fmt:message key="web.institution.systemset.script.style.file.upload.erro.messages"/>'}}).show();
					$('#logoModal').modal("hide");
				}
			},
			error:function(data){
				$(".notify").notify({type:"warning",message: { html: false, text:'<fmt:message key="tiles.views.user.index.script.user.excel.import.failed"/>'}}).show();
			}
		});	
	}else{
		$(".showFileName").val("");
		$(".fileerrorTip").html("<fmt:message key='tiles.views.user.index.script.user.excel.upload.tip'/>").show();
	}
	}else{
		$(".fileerrorTip").html("<fmt:message key='tiles.views.user.index.script.user.excel.upload.tip'/>").show();
	}
}
function refresh() {
	window.location.reload(true);  
}	
	function submitStyle() {
		var ischangelogo = $("#ischangelgo").val();
		if ($("#logopath").val() != null) {
			var logopath = $("#logopath").val();
		} else {
			var logopath = "0";
		}
		//var copyright=$("#copyright").val();
		if(ischangelogo==0){
		var formData = new FormData();
		formData.append("files", $("#file")[0].files[0]);
		formData.append("ischangelogo",ischangelogo);
		formData.append("logopath",logopath);
		//formData.append("copyright",copyright);
		 $.ajax({
			 data : formData,
			type : "POST",
			url : ctx + "/system/param/manager/modifystyle",
			cache : false,
			headers : {
				"${_csrf.headerName}" : "${_csrf.token}",
			},
			contentType : false,
			processData : false,
			success : function(result) {
				refresh();
			},
			error:function(data){
				
			}
		});	}else{
			$(".notify").notify({type:"danger",message: { html: false, text:"<fmt:message key='web.institution.systemset.script.style.file.erro3.messages'/>"}}).show();
		}
	}
	function resetstyle() {
		 $.ajax({
			type : "POST",
			url : ctx + "/system/param/manager/resetstyle",
			cache : false,
			headers : {
				"${_csrf.headerName}" : "${_csrf.token}",
			},
			contentType : false,
			processData : false,
			success : function() {
				$(".notify").notify({type:"success",message: { html: false, text:"<fmt:message key='web.institution.systemset.script.style.reset.success.tip'/>"}}).show();
			},
			error:function(data){
				$(".notify").notify({type:"danger",message: { html: false, text:"<fmt:message key='web.institution.systemset.script.style.reset.erro.tip'/>"}}).show();
			}
		});	
		 window.setTimeout(function() {
				refresh();
			}, 5000);
	}
	function updateMessageAdviceParam() {
		var validator = $('#formEmail').parsley();
		validator.validate();
	    if(!validator.isValid())
	    {
	    	return;
	    }
	    
		var _csrf = "${_csrf.token}";
		var smtpAddress = $("#smtpServiceAddress").val();
		var isSll = $("#isSsl").is(':checked');
		var smtpPort = $("#smtpPort").val();
		var mailUsername = $("#username").val();
		var mailPassword = $("#password").val();
		var sendUserMailAddress = $("#sendToUserAddress").val();

		$.ajax({
			"dataType" : 'json',
			"type" : "get",
			"async" : false,
			"url" : ctx + "/system/param/manager/updateMessageAdviceParam?now="
					+ new Date().getTime(),
			"data" : {
				"_csrf" : _csrf,
				"smtpAddress" : smtpAddress,
				"isSll" : isSll,
				"smtpPort" : smtpPort,
				"mailUsername" : mailUsername,
				"mailPassword" : mailPassword,
				"sendUserMailAddress" : sendUserMailAddress
			},
			"success" : function(data) {
				if (data.type != "success") {
					$(".notify").notify({
						type : data.type,
						message : {
							html : false,
							text : data.message
						}
					}).show();
				} else {
					$(".notify").notify({
						type : data.type,
						message : {
							html : false,
							text : data.message
						}
					}).show();
				}
			}
		});

	}

	function updateEmailModels() {
		var _csrf = "${_csrf.token}";
		var emailModels = [];

		var illegalEmailAdviceTheme = $("#illegalEmailAdviceTheme").val();
		var illegalMailAdviceContent = $("#illegalMailAdviceContent").html();
		// 将adviceType为0表示违规邮件通知类型的数据放入数组
		emailModels.push({
			adviceType : 0,
			theme : illegalEmailAdviceTheme,
			content : illegalMailAdviceContent
		});

		/* var importEmailAdviceTheme = $("#importEmailAdviceTheme").val();
		var importEmailAdviceContent = $("#importEmailAdviceContent").html();
		// 将adviceType为1表示用户批量导入通知邮件的数据放入数组
		emailModels.push({
			adviceType : 1,
			theme : importEmailAdviceTheme,
			content : importEmailAdviceContent
		});

		var pwBeyondDataTheme = $("#pwBeyondDataTheme").val();
		var pwBeyondDateAdviceContent = $("#pwBeyondDateAdviceContent").html();
		// 将adviceType为2表示密码过期提醒邮件的数据放入数组
		emailModels.push({
			adviceType : 2,
			theme : pwBeyondDataTheme,
			content : pwBeyondDateAdviceContent
		}); */

		var testEmailTheme = $("#testEmailTheme").val();
		var testEmailAdviceContent = $("#testEmailAdviceContent").html();
		// 将adviceType为3表示测试邮件的数据放入数组
		emailModels.push({
			adviceType : 3,
			theme : testEmailTheme,
			content : testEmailAdviceContent
		});

		var postData = {
			params : {}
		};

		postData.params.emailModels = parseToJsonStr(emailModels);
		postData._csrf = _csrf;

		$.ajax({
			"dataType" : 'json',
			"data" : postData,
			"type" : "get",
			"url" : ctx + "/system/param/manager/updateEmailModels",
			"success" : function(data) {
				if (data.type == 'success')
				{
					$(".notify").notify({
						type : data.type,
						message : {
							html : false,
							text : "<fmt:message key='web.admin.system.param.manager.resultdto.message.update.success'/>"
						}
					}).show();
				}
				else
				{
					$(".notify").notify({
						type : data.type,
						message : {
							html : false,
							text : "<fmt:message key='web.admin.system.param.manager.resultdto.message.update.failed'/>"
						}
					}).show();
				}
			}
		});
	}

	function queryEmailModels() {
		var _csrf = "${_csrf.token}";
		$.ajax({
			"dataType" : 'json',
			"data" : {
				"_csrf" : _csrf
			},
			"type" : "get",
			"url" : ctx + "/system/param/manager/queryEmailModels?now="
					+ new Date().getTime(),
			"success" : function(data) {
				var cotentList = data.cotentList;
				if (null != cotentList && cotentList.length > 0) {
					for (var i = 0; i < cotentList.length; i++) {
						if (cotentList[i].adviceType == 0) {
							$("#illegalEmailAdviceTheme").val(
									cotentList[i].theme);
							$("#illegalMailAdviceContent").html(
									cotentList[i].content);
						} else if (cotentList[i].adviceType == 1) {
							$("#importEmailAdviceTheme").val(
									cotentList[i].theme);
							$("#importEmailAdviceContent").html(
									cotentList[i].content);
						} else if (cotentList[i].adviceType == 2) {

							$("#pwBeyondDataTheme").val(cotentList[i].theme);
							$("#pwBeyondDateAdviceContent").html(
									cotentList[i].content);
						} else {
							$("#testEmailTheme").val(cotentList[i].theme);
							$("#testEmailAdviceContent").html(
									cotentList[i].content);
						}
					}
				}
			}
		});
	}

	function queryEmailParams() {
		var _csrf = "${_csrf.token}";
		$.ajax({
			"dataType" : 'json',
			"data" : {
				"_csrf" : _csrf
			},
			"type" : "get",
			"url" : ctx + "/system/param/manager/queryEmailParams?now="
					+ new Date().getTime(),
			"success" : function(data) {
				if (data.result == 'success' && data.emailParamModel != null) {
					$("#smtpServiceAddress").val(data.emailParamModel.host);
					if (data.emailParamModel.isSSL == 0) {
						$("#isSsl").attr("checked", true);
					}

					$("#smtpPort").val(data.emailParamModel.port);
					$("#username").val(data.emailParamModel.username);
					$("#password").val(data.emailParamModel.password);
					$("#sendToUserAddress").val(data.emailParamModel.sender);
				} else {
				}
			}
		});
	}

	function queryMdmSystemParam() {
		$.ajax({
			"dataType" : 'json',
			"data" : postData,
			"type" : "get",
			"url" : ctx + "/system/param/manager/queryMdmSystemParam?now="
					+ new Date().getTime(),
			"success" : function(data) {
				if (data.result == 'success') {
					$("#mdmAddress").val(data.mdmAddress);

					$("#username").val(data.username);
					$("#outControlTimeLimit").val(data.outControlTimeLimit);
					$("#deviceInfoCollectPeriod").val(
							data.deviceInfoCollectPeriod);
					$("#deviceIllegalCollectPeriod").val(
							data.deviceIllegalCollectPeriod);
				} else {
				}
			}
		});
	}
	function parseToJsonStr(arr) {
		if (null != arr && arr.length > 0) {
			return JSON.stringify(arr).toString();
		}

		return "";
	}

	function sendTestMail() {
		
		var validator = $('#formEmail').parsley();
		validator.validate();
	    if(!validator.isValid())
	    {
	    	return;
	    }
	    
	    var smtpAddress = $("#smtpServiceAddress").val();
		var isSll = $("#isSsl").is(':checked');
		var smtpPort = $("#smtpPort").val();
		var mailUsername = $("#username").val();
		var mailPassword = $("#password").val();
		var sendUserMailAddress = $("#sendToUserAddress").val();

		$.ajax({
			"dataType" : 'json',
			"type" : "get",
			"async" : false,
			"url" : ctx + "/system/param/manager/sendTestMailByEmailParam?now="
					+ new Date().getTime(),
			"data" : {
				"smtpAddress" : smtpAddress,
				"isSll" : isSll,
				"smtpPort" : smtpPort,
				"mailUsername" : mailUsername,
				"mailPassword" : mailPassword,
				"sendUserMailAddress" : sendUserMailAddress
			},
			"success" : function(data) {
				if (data.type == "success") {
					$(".notify").notify({
						type : data.type,
						message : {
							html : false,
							text : "<fmt:message key='tiles.views.sysmanager.email.test.mail.send.success'/>"
						}
					}).show();
				} else {
					$(".notify").notify({
						type : data.type,
						message : {
							html : false,
							text : "<fmt:message key='tiles.views.sysmanager.email.test.mail.send.failed'/>"
						}
					}).show();
				}
			}
		});
	}
</script>