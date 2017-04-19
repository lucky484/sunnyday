<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url
	value="/resources/js/datatables-1.10.11/css/dataTables.bootstrap.css"
	var="dataTableCss" />
<link href="${dataTableCss}" rel="stylesheet" />
<style>
/********libra modify the department manager management part css 4/5 ***************************/
.mark {
	height: 100px;
}

.panel-left {
	margin-left: 16px;
}

.searchicon {
	background: url(../../resources/images/searchicon.png) no-repeat right
		center;
	border: none;
	padding-right: 20px;
	margin: 0px 3px;
}

.searchtext {
	border: none;
	margin: 0px;
	padding: 0px;
}

.basic_info {
	font-size: 16px;
	color: #424954;
}

.basic_line {
	position: relative;
	border-bottom: 1px solid #aaa;
	height: 1px;
	margin: 1px 0px;
	line-height: normal;
}

.span_line {
	position: absolute;
	padding: 0px 15px;
	left: 0px;
	top: -16px;
	background: #fff;
	font-size: 16px;
	color: #424954;
}

label {
	margin: 0;
	padding: 0;
	list-style: none;
}

.mark {
	min-height: 100px;
}

textarea {
	resize: none;
}

.div_top {
	margin-top: 5px;
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
.select-box {
	position: relative;
	width: 180px;
	border: 1px solid #BFBFC0;
	height: 26px;
	line-height: 22px;
	background: url(../../resources/images/down.png) no-repeat right center
		#fff;
	padding-left: 10px;
}
</style>
