<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/farm/terminal/goods/page" var="goodsListUrl" />
<spring:url value="/farm/terminal/goods/index" var="goodsIndex" />
<spring:url value="/farm/order" var="orderlistUrl" />

<script type="text/javascript">
	 $("input").each(function(){
		$(this).attr("disabled",true);
	 });
       
	function orderlistUrl(){
		window.location.href="${orderlistUrl}";
	}   
    </script>