<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/farm/home/addlist-logistics" var="addUrl" />
<spring:url value="/farm/home/logistics" var="returnUrl" />
<script type="text/javascript">
	/* 添加物流方式 */
	function add(){
		if ($("#name").val()== null || $("#name").val() == "") {
			$.notify("error", "请输入物流方式");
			$("#publish").attr("disabled", false)
			return;
		}
		$.ajax({
			type : "POST",
			url : "${addUrl}", 
		    data:{
		       name:$("#name").val()
			},
			dataType : 'json',
	        success:function(data){  
	        	alert("保存成功");
	        	console.log(data);
	        	if(data == 1){
	        		window.location.href = "${returnUrl}";
	        	}
			}
		})
	}
</script>