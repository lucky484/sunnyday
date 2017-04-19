<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/farm/shop/index" var="shopIndex"></spring:url>
<spring:url value="/farm/shop/upload" var="shopUploadUrl" />
<spring:url value="/resources/platform/js/plugins/simditor/image/image.png" var="defaultImgUrl" />
<script type="text/javascript">
	
	//添加店铺的方法
	function update(){
   		//提交数据到后台
   		var postData = $("#updateForm").serialize();
   		$.ajax({
   			"dataType": 'json',
   	        "type": "post",
   	        "data": postData,
            "url":"${pageContext.request.contextPath}/farm/shop/update",
   	        "success": function(result){
   	        	if(result.type == "error"){
					swal({
    			        title: "修改店铺失败",
    			        type: "warning"
    			    });
				}else{
					window.location.href = "${shopIndex}";
				}
   	        } 
   	   }); 
	}
	//初始化加载simditor编辑器
	$(function(){
		var toolbar = [ 'title', 'bold', 'italic', 'underline', 'strikethrough',  
		            'color', '|', 'ol', 'ul', 'blockquote', 'code', 'table', '|',  
		            'link', 'image', 'hr', '|', 'indent', 'outdent' ];  
        var editor=new Simditor({
    		textarea:$("#editor"),
    		placeholder:'请在这里输入内容...',
    		toolbar : toolbar,  //工具栏  
    		imageButton:['upload'],
    		defaultImage:"${defaultImgUrl}",
    		upload:{
    			url : '${shopUploadUrl}',
    			params:null,
    			fileKey:'files',
    			connectionCount:3,
    			leaveConfirm:'正在上传文件'
    		}
        });
	});
</script>




