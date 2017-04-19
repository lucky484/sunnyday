<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/farm/shop/index" var="shopIndex"></spring:url>
<script type="text/javascript">
	
	function confirm(){
		window.location.href = "${shopIndex}";
	}

	$(function(){
		var toolbar = [ 'title', 'bold', 'italic', 'underline', 'strikethrough',  
		            'color', '|', 'ol', 'ul', 'blockquote', 'code', 'table', '|',  
		            'link', 'image', 'hr', '|', 'indent', 'outdent' ];  
        var editor=new Simditor({
    		textarea:$("#editor"),
    		placeholder:'请在这里输入内容...',
    		toolbar : toolbar,  //工具栏  
    		imageButton:['upload'],
    		upload:{
    			url:'',
    			params:null,
    			fileKey:'files',
    			connectionCount:3,
    			leaveConfirm:'正在上传文件'
    		}
        });
	});
</script>




