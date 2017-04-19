<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script>
     $("#Js-setOrgReSet").click(function(){
    	 $("#Js-setOrgForm")[0].reset();
     });
     
     $("#Js-setOrgSubmit").click(function(){
    	 var _this = $(this)
    	 _this.attr("disabled",true);
    	 var terminalUnbundEnable = 0;
    	 var serviceSettingHide = 0;
    	 var deviceInfoHide = 0;
    	 var csrf = "${_csrf.token}";
    	 if($("#handsetCancel").is(':checked') == true){
    		 terminalUnbundEnable = 1;
    	 }
    	 if($("#hiddenServerConfig").is(':checked') == true){
    		 serviceSettingHide = 1;
    	 }
    	 if($("#hiddenDeviceInfo").is(':checked') == true){
    		 deviceInfoHide = 1;
    	 }
    	 var clientConfig = {
    			 id : $("#id").val(),
    			 terminalUnbundEnable : terminalUnbundEnable,
    			 serviceSettingHide : serviceSettingHide,
    			 deviceInfoHide : deviceInfoHide
    	 };
    	 $.post("clientConfig/insertClientConfig?_csrf="+csrf,clientConfig,function(data){
    		 if(data.result == 1){
    			 $("#insertMsg").modal();
    			 $("#confirm").click(function(){
    				 window.location.reload();
    			 });
    		 }
    	 },'json');
     });
</script>