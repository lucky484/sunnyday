<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url
	value="/resources/platform/js/plugins/easyUI/jquery.easyui.min.js"
	var="easyUIUrl" />
<script type="text/javascript" src="${easyUIUrl}"></script>
<script>
function formatCurrentTimeMillis(dateTime) {
		return new Date(dateTime).Format("yyyy-MM-dd hh:mm:ss");
	}
</script>