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
<%-- <spring:url value="/system/license/manager/downLoadMachineCodeFile" var="downloadFile" />
<script src="${downloadFile}"></script> --%>
<script type="text/javascript">
	var formData;
	
	$(function(){
		getLicenseInfo();
	})
	
	$(function(){
		
		$("#fileNameWarning").text('');
	})
	
	function getLicenseInfo()
	{
		$.ajax({
			"dataType" : 'json',
			"type" : "get",
			"async" : false,
			"url" : ctx
					+ "/system/license/manager/getLicenseInfo?now="
					+ new Date().getTime(),
			"success" : function(data) 
			{
				var versionType = data.versionType;
				if (undefined == versionType || null == versionType || '' == versionType)
				{
					$("#versionType").text("<fmt:message key='tiles.views.sysmanager.lisence.unknow'/>");
					$("#authDays").text("<fmt:message key='tiles.views.sysmanager.lisence.unknow'/>");
					$("#remainDays").text("<fmt:message key='tiles.views.sysmanager.lisence.unknow'/>");
					$("#authTotalMembers").text("<fmt:message key='tiles.views.sysmanager.lisence.unknow'/>");
					$("#authUsedMembers").text("<fmt:message key='tiles.views.sysmanager.lisence.unknow'/>");
					$("#productType").text("<fmt:message key='tiles.views.sysmanager.lisence.unknow'/>");
					
				}
				else
				{
					if ('0' == versionType)
					{
						$("#versionType").text("<fmt:message key='tiles.views.sysmanager.lisence.try.used'/>");
					}
					else
					{
						$("#versionType").text("<fmt:message key='tiles.views.sysmanager.lisence.formal.used'/>");
					}
					
					$("#authDays").text(data.totalDay);
					$("#remainDays").text(data.remainDay);
					$("#authTotalMembers").text(data.totalMembers);
					$("#authUsedMembers").text(data.usedMembers);
				}
				$("#activeCode").val(data.activeCodeTop);
				$("#machineCode").val(data.machineFilePathTop);
				$("#adviseBeforeDays").val(data.adviceDaysTop);
			}
		});
	}
	
	// 浏览license文件
	function uploadClientOnchange(obj,fileShade){
		
		var uploadFileName = $(obj).val();
		if (!isEndWithCorrectType(uploadFileName, '.key'))
		{
			$("#licensefileName").val('');
			$("#licenseKeyfile").val('');
			$("#licensefileName").addClass('parsley-error');
			$("#licenseFileErrorDesc").text("<fmt:message key='tiles.views.sysmanager.lisence.lisence.prifx'/>")
			$("#licenseFilePart").css("display","");
			return;
		}
		$("#licensefileName").removeClass('parsley-error');
		$("#licenseFilePart").css("display","none");
		
		var fileName = $("#licensefileName").val();
		var tmp = uploadFileName + fileName;
		if (undefined == uploadFileName || '' == uploadFileName || null == uploadFileName)
		{
			$("#fileNameWarning").text("<fmt:message key='tiles.views.sysmanager.lisence.should.not.null'/>");
			$("#fileNameWarning").css("display", "none");
			return;
		}
		formData = new FormData();
		formData.append("file", $("#licenseKeyfile")[0].files[0]); 
		var index = uploadFileName.lastIndexOf("\\");
		if (index != -1) {
			var tempFileName = uploadFileName.substring(index + 1,
					uploadFileName.length);
			$("#" + fileShade).val(tempFileName);
		} else {
			$("#" + fileShade).val(uploadFileName);
		}
	}

	//上传license文件
	function uploadLicenseFile() {
		licenseUpload();
	}
	
	
	// 上传license文件
	function licenseUpload(){
		
		var fileName = $("#licensefileName").val();
		if (undefined == fileName || '' == fileName || null == fileName)
		{
			$("#licensefileName").addClass('parsley-error');
			$("#licenseFilePart").css("display","");
			$("#licenseFileErrorDesc").text("<fmt:message key='tiles.views.sysmanager.lisence.should.not.null'/>")
			return;
		}
		
		$.ajax({
				dataType : 'json',
				headers: {
					"${_csrf.headerName}":"${_csrf.token}",
	        	},
				url : ctx + "/system/license/manager/uploadLicenseFile?now="
						+ new Date().getTime(),
				type : 'POST',
				data : formData,
				processData : false,
				contentType : false,
				success : function(data) { 
					var ss = 0;
					if (data.type == "success") {
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
	
	// 生成机器码文件
	function generateMachineFile()
	{
		
		$("#machineTr").css("display", "");
		var activeCode = $("#activeCode").val();
		var _csrf = "${_csrf.token}";
		if (undefined == activeCode || '' == activeCode || null == activeCode)
		{
			return;
		}
		else
		{
			$.ajax({
				"dataType" : 'json',
				"type" : "POST",
				"async" : false,
				"url" : ctx
						+ "/system/license/manager/generateMachineCode?now="
						+ new Date().getTime(),
				"data" : {
					"activeCode" : activeCode,
					"_csrf":_csrf
				},
				"success" : function(data) 
				{
					if (data.type = 'success')
					{
						$("#machineCode").val(data.machineFile);
					}
				}
			});
		}
	}
	
	function updateLicenseInfo()
	{
		//新增用户提交
		var validator = $('#licenseInfoUpdate').parsley();
		validator.validate();
        if(!validator.isValid())
        {
        	return;
        }
		
		var licenseAdviceDay = $("#adviseBeforeDays").val();
		var activeCode = $("#activeCode").val();
		var machineCode = $("#machineCode").val();
		var _csrf = "${_csrf.token}";
		$.ajax({
			"dataType" : 'json',
			"type" : "POST",
			"async" : false,
			"url" : ctx
					+ "/system/license/manager/updateLicenseInfo?now="
					+ new Date().getTime(),
			"data" : {
				"licenseAdviceDay" : licenseAdviceDay,
				"activeCode" : activeCode,
				"machineCode" : machineCode,
				"_csrf":_csrf
			},
			"success" : function(data) 
			{
				if (data.type != 'success')
				{
					$(".notify").notify({
						type : data.type,
						message : {
							html : false,
							text : data.message
						}
					}).show();
					$("#versionType").text("<fmt:message key='tiles.views.sysmanager.lisence.unknow'/>");
					$("#authDays").text("<fmt:message key='tiles.views.sysmanager.lisence.unknow'/>");
					$("#remainDays").text("<fmt:message key='tiles.views.sysmanager.lisence.unknow'/>");
					$("#authTotalMembers").text("<fmt:message key='tiles.views.sysmanager.lisence.unknow'/>");
					$("#authUsedMembers").text("<fmt:message key='tiles.views.sysmanager.lisence.unknow'/>");
					$("#productType").text("<fmt:message key='tiles.views.sysmanager.lisence.unknow'/>");
				}
				else
				{
					if ('0' == data.result.versionType)
					{
						$("#versionType").text("<fmt:message key='tiles.views.sysmanager.lisence.try.used'/>");
					}
					else
					{
						$("#versionType").text("<fmt:message key='tiles.views.sysmanager.lisence.formal.used'/>");
					}
					
					$("#authDays").text(data.result.totalDay);
					$("#remainDays").text(data.result.remainDay);
					$("#authTotalMembers").text(data.result.totalMembers);
					$("#authUsedMembers").text(data.result.usedMembers);
					$(".notify").notify({
						type : data.type,
						message : {
							html : false,
							text : "<fmt:message key='tiles.views.sysmanager.lisence.update.success'/>"
						}
					}).show();
					window.location.reload();
				}
			}
		});
	}
	
	function downloadMachineCode()
	{
		var url = ctx + "/system/license/manager/downLoadMachineCodeFile";
		var _csrf = "${_csrf.token}";
		var form=$("<form id='tmpFom'>");//定义一个form表单
		form.attr("style","display:none");
		form.attr("target","");
		form.attr("method","get");
		form.attr("action",url);
		$("body").append(form);//将表单放置在web中

		form.submit();//表单提交 
		
		$('tmpFom').remove();
	}
	
	function isShowDwBtn()
	{
		var activeCode = $("#activeCode").val();
		
		if (undefined == activeCode || '' == activeCode || null == activeCode)
		{
			$("#dwBtn").css("display", "none");
		}
		else
		{
			$.ajax({
				"dataType" : "json",
				"type" : "get",
				"async" : false,
				"url" : ctx + "/system/license/manager/isActiveCodeSame",
				"data" : {
					"activeCode" : activeCode
				},
				"success" : function(data){
					if (data.isSame != true)
					{
						generateMachineFile();
						$("#dwBtn").css("display", "");
					}
				}
			});
		}
	}
	
	function isEndWithCorrectType(file,str)
	{
		if (str == null || str == "" || file.length == 0
				|| str.length > file.length) {
			return false;
		}

		if (file.substring(file.length - str.length) == str) {
			return true;
		}

		else {
			return false;
		}

		return true;
	}
</script>