<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/farm/goods/fshoplist" var="fgoodslistUrl" />	
<script>
function fgoodslistUrl(){
	window.location.href="${fgoodslistUrl}";
}   
</script>