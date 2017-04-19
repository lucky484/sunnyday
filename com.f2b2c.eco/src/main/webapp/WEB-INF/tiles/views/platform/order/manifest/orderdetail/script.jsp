<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<spring:url value="/farm/manifest/print" var="printUrl"></spring:url>
<script>
	function print(){
		
		var form = $("<form></form>");
        form.attr('action',"${printUrl}");
        var oid = $("#oid").val();
        var input3 = $('<input type="hidden" name="oid" value="'+oid+'"/>');
        form.append(input3);
        form.attr('method','post');
        //form.attr('enctype','multipart/form-data');
        form.submit();
        
	}
</script>