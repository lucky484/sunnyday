<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<script>
	$(function(){
		var msg="${msg}";
		if(msg.trim().length>0){
			$(".notify").notify({type:"${type}",message: { html: false, text: '${msg}'}}).show();
		}
		
		//提交表单
		$('#personForm').submit(function(){
			var email = $("input[name='email']").parsley();
			var name = $("input[name='name']").parsley();
			var phone=$("input[name='phone']").parsley();
			email.validate();
			name.validate();
			phone.validate();
			if($("#moreless").hasClass("show")){
				
				var source_pwd=$("#source_password").parsley();
				var pwd=$("input[name='password']").parsley();
				var ckpwd=$("#ckpassword").parsley();
				
				source_pwd.validate();
				pwd.validate();
				ckpwd.validate();
				if(phone.isValid()&&email.isValid()&&name.isValid()&&source_pwd.isValid()&&pwd.isValid()&&ckpwd.isValid()){
					return true;
				}else{
					return false;
				}
			}
	        if(phone.isValid()&&email.isValid()&&name.isValid()){
	        	return true;
	        }else{
	        	return false;
	        }
	        return false;
			
		});
	});
</script>