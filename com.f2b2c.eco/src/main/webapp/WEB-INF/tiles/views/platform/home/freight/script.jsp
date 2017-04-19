<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/farm/home/insert-freight" var="submitUrl" />
<script type="text/javascript">
	/* 添加运费 */
	function submit(){
		if ($("#freemoney").val()== null || $("#freemoney").val() == "") {
			$.notify("error", "请问消费满多少免运费？");
			$("#publish").attr("disabled", false)
			return;
		}
		if ($("#money").val()== null || $("#money").val() == "") {
			$.notify("error", "请输入标准费用");
			$("#publish").attr("disabled", false)
			return;
		}
		$.ajax({
			"type" : "post",
			"url" : "${submitUrl}", 
		    "data":{
		    	"id":$("#id").val(),
		    	"freemoney":$("#freemoney").val(),
		        "money":$("#money").val()
			},
			"dataType" : 'text',
	        "success":function(data){  
	        	alert("提交成功");  	
	        	console.log(data);
			},
			"error":function(data){
				alert("已提交");
			}
		});
	}
</script>