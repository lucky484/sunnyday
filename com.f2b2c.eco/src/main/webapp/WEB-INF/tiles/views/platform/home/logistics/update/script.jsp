<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/resources/platform/js/plugins/jeditable/jquery.jeditable.js" var="jeditableJsUrl"/>
<script src="${jeditableJsUrl}"></script>
<spring:url value="/resources/platform/js/plugins/dataTables/jquery.dataTables.js" var="dataTablesJsUrl"/>
<script src="${dataTablesJsUrl}"></script>
<spring:url value="/resources/platform/js/plugins/dataTables/dataTables.bootstrap.js" var="dataTablesBootstrapJsUrl"/>
<script src="${dataTablesBootstrapJsUrl}"></script>
<spring:url value="/resources/platform/js/content.min.js" var="contentJsUrl"/>
<script src="${contentJsUrl}"></script>
<spring:url value="/resources/platform/js/common/date.js" var="commonJsUrl"/>
<script src="${commonJsUrl}"></script>

<spring:url value="/farm/home/update" var="updateUrl" />
<spring:url value="/farm/home/logistics" var="returnUrl" />
<script type="text/javascript">
	/* 添加物流方式 */
	function update(){
		$.ajax({
			type : "POST",
			url : "${updateUrl}", 
		    data:{
		    	id:$("#id").val(),
		       name:$("#name").val()
			},
			dataType : 'json',
	        success:function(data){  
	        	alert("修改成功");
	        	window.location.href = "${returnUrl}";	        	 	
			}
		})
	}
</script>