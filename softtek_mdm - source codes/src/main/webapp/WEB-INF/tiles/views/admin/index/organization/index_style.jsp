<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url
	value="/resources/js/datatables-1.10.11/css/dataTables.bootstrap.css"
	var="dataTableCss" />
<link href="${dataTableCss}" rel="stylesheet" />
<style>
/********libra modify the department manager management part css 4/5 ***************************/
.div_sep {
	margin-top: 25px;
}

.div_top {
	margin-top: 5px;
	margin-left: 5px;
}

.mark {
	min-height: 100px;
}

textarea {
	resize: none;
}

.panel-left {
	margin-left: 16px;
}

.view {
	margin-top: 20px;
}

.span_sep {
	margin-top: 6px;
}

.datatables-max-length {
	width: 300px !important;
	max-width: 300px !important;
}

.open {
	position: relative !important;
}
</style>