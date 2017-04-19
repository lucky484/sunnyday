<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:url
	value="/resources/js/datatables-1.10.11/css/dataTables.bootstrap.css"
	var="dataTableCss" />
<link href="${dataTableCss}" rel="stylesheet" />
<style>
/********libra modify the role management part css 3/29 ***************************/
.doc-buttons .btn.btn-success {
	border-radius: 5px;
}

#queryRoleModal textarea {
	margin-top: 10px;
}

#roleTable {
	width: 100% !important;
}

.checkbox i {
	border-radius: 4px;
}

/***search*************/
.search_part {
	margin-top: 15px;
	margin-bottom: -15px;
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

.datatb-max-width-rolename {
	width: 200px !important;
	max-width: 200px !important;
}

.datatb-max-width-rolemark {
	width: 600px !important;
	max-width: 600px !important;
}

#roleTable_wrapper .row {
	margin-top: 30px;
}

.dropdown-menu {
	margin-left: 0px !important;
	margin-top: -30px !important;
}
</style>