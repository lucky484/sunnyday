<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/resources/js/datatables/datatables.css"
	var="dataTableCss" />
<link href="${dataTableCss}" rel="stylesheet" />
<style>
/********libra modify the department manager management part css 4/5 ***************************/
#promoteTree {
	border: 1px solid #cbd5dd;
	border-radius: 2px;
	margin-bottom: 20px;
}

.form-group .col-sm-10.ck-tmpl {
	border: 1px solid #cbd5dd;
	border-radius: 2px;
	margin-bottom: 20px;
}

.promoteTree .list-group-item {
	cursor: auto !important;
}

.glyphicon {
	cursor: pointer;
}

.glyphicon-check:before, .glyphicon-uncheck:before {
	font-size: 16px;
}

.list-group .node-promoteTree {
	color: #788288 !important;
	border: 1px solid #eaeef1;
}

#uRDFrm>div>div:nth-child(5)>div {
	padding: 0px;
}

.dataTable th {
	border-right: 1px solid #eaeef1;
}

.blog-post .post-item {
	margin-right: 15px;
	margin-left: 15px;
}
/***search*************/
.search_part {
	margin-bottom: -30px;
}

.searchName {
	width: 10%;
	margin-right: -15px;
}

.searchAccount {
	width: 12%;
}

.searchStatus {
	width: 12%;
}

.searchName_val, .searchAccount_val, .searchStatus_val {
	width: 16%;
	padding: 0px;
	margin-top: -5px;
}

.search_button {
	margin-top: -5px;
}

#searchpolicyname, #searchpolicytype {
	height: 30px !important;
}

.search_part {
	z-index: 99;
}

#policy_length {
	margin-top: 10px;
	padding: 0px;
}

.dataTables_info {
	margin-left: -30px;
}

.dataTables_length {
	margin-top: 5px;
}

#roleTable_wrapper .row {
	margin-top: 30px;
}

.dataTables_length {
	margin-top: 10px;
	padding: 0px !important;
}

.login_name {
	text-align: center;
}

.comments {
	padding-right: 0px !important;
	padding-left: 0px !important;
}
</style>
