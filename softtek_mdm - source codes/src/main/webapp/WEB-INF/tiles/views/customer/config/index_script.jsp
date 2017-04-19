<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<spring:url value="/resources/js/jquery.tmpl.js" var="jqueryTmplJs" />
<script src="${jqueryTmplJs}"></script>


<script type="text/javascript">
	
	function loadConfigDT(){
		//自动加载datatable
			$('#config')
				.DataTable(
					{
						//编写应用列表的方法
				});
		}
</script>

<jsp:include page="../common/common.jsp"></jsp:include>
<jsp:include page="../common/common_style.jsp"></jsp:include>


<script type="text/javascript">
	$(function(){
		//从后台去加载设备描述文件列表
		
		
	});
</script>