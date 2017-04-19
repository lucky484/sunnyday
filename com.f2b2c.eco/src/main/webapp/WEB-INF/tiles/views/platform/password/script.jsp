<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<spring:url value="/farm/personal/password" var="updatePass" /> 
<script type="text/javascript">
	
	//点击修改按钮，把修改之后的个人信息从前台序列化到后台之后，提交服务器端保存
	function modifySubmit(){
		
		var password = $('#old_password').val();
		var password_confirm = $("#password_confirm").val();
		var password_reconfirm = $("#password_reconfirm").val();
		if(password == null || password == ''){
			alert("请输入原密码");
			return;
		}
		if(password_confirm == null || password_confirm == ''){
			alert("请输入新密码");
			return;
		}
		if(password_reconfirm == null || password_reconfirm == ''){
			alert("请输入确认密码");
			return;
		}
		if(password_confirm != password_reconfirm){
			alert("两次密码输入不一致");
			return ;
		}
       	//当验证通过的时候需要把form表单中的字段数据传递到后台做更新的操作
       	var postData = $("form").serialize();
       	$.ajax({
			"dataType": 'json',
	        "data": postData,
	        "type": "POST",
            "url": "${updatePass}",
	        "success": function(result){
				 if(result.type == 'error'){
					 alert("修改密码失败");
					 return;
				 }else{
					 window.location.reload();				 
				} 			
	        } 
	   }); 
	}
</script>
