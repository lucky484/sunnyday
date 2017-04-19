<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!-- simditor组件 -->
<spring:url value="/farm/home/insertprotocol" var="insertUrl"/>
 <spring:url value="/resources/platform/css/plugins/simditor/simditor.css" var="simditorCssUrl" />
	<link href="${simditorCssUrl}" rel="stylesheet">
<script>
/* 注册 */
$(function(){
	toolbar = [ 'title', 'bold', 'italic', 'underline', 'strikethrough', 'color', '|',
	            'ol', 'ul', 'code', 'table', '|', 'link',  'hr', 'indent', 'outdent','alignment' ];
	var editor = new Simditor( {
		textarea : $('#editor'),
		toolbar : toolbar,  //工具栏
		upload: true,
		});
   });
   
function save() {
	$.ajax({
		"type" : "post",
		"url" : '${insertUrl}', 
	    "data":{
	    	"id" : $("#id").val(),
	        "content" : $("#editor").val(),
	        "type" : $("#type").val()
		},
		"dataType" : 'text',
        "success" : function(data){  
        	alert("保存成功");
        	document.location.reload();// 当前页面  	
		},
		"error" : function(data) {
			alert("已提交");
			document.location.reload();// 当前页面  
		}
	});
}
</script>