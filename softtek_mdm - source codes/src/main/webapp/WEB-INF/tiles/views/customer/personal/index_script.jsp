<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<spring:url value="/resources/js/jquery.tmpl.js" var="jqueryTmplJs" />
<script src="${jqueryTmplJs}"></script>

<script type="text/javascript">
	//点击修改按钮，把修改之后的个人信息从前台序列化到后台之后，提交服务器端保存
	function modifySubmit(){
		var validator = $('#modifyFrm').parsley();
		validator.validate();
        if(validator.isValid()){
        	//当验证通过的时候需要把form表单中的字段数据传递到后台做更新的操作
        	var postData = $("form").serialize();
        	$.ajax({
				"dataType": 'json',
		        "data": postData,
		        "type": "POST",
	            "url":ctx+"/customer/personal/update",
		        "success": function(result){
        			$(".notify").notify({type:result.type,message: { html: false, text: result.message}}).show();
        			window.location.reload();
		        } 
		   }); 
           return true;
        }else{
           return false;
        }
	}
	
</script>

<jsp:include page="../common/common.jsp"></jsp:include>
<jsp:include page="../common/common_style.jsp"></jsp:include>


<script type="text/javascript">
	
</script>