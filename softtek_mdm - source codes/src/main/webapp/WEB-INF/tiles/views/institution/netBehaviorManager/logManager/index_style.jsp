<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url
	value="/resources/js/datatables-1.10.11/css/dataTables.bootstrap.css"
	var="dataTableCss" />
<link href="${dataTableCss}" rel="stylesheet" />
<spring:url value="/resources/css/autocomplete/jquery-ui.min.css"
	var="jqueryUiCss" />
<link href="${jqueryUiCss}" rel="stylesheet" />
<style>
.appHeight {
	height: 280px;
	border: 1px solid silver;
	overflow-x: hidden;
}

.paddingWidth {
	padding: 0px;
}

.marginWidth {
	margin-left: 0px;
	margin-right: 0px;
}

.appIdMargin {
	padding-left: 0px;
	padding-right: 0px;
}

.appNameMargin {
	padding-left: 2px;
	padding-right: 0px;
}

.btnPaddingLeft {
	padding-left: 8px;
	padding-right: 0px;
}

.inputNoWidth {
	border-left: 0px;
	border-right: 0px;
	border-top: 0px;
}

.comma {
	height: 28px;
	line-height: 38px;
	padding: 0px;
}

.appTitle {
	height: 32px;
	line-height: 32px;
	border-left: 1px solid silver;
	border-right: 1px solid silver;
	border-top: 1px solid silver;
	text-align: center;
	background-color: #eee;
}

.appIdTitle {
	border-right: 1px solid silver;
}

.appBody {
	height: 32px;
	line-height: 32px;
	text-align: center;
	background-color: #eee;
	margin-top: 5px;
}

.limargin {
	margin-top: 5px;
}

.blog-post .post-item {
	margin-right: 15px;
	margin-left: 15px;
}

#bWLogTb {
	width: 100% !important;
}

.datatb-max-width-netbehavior-url {
	width: 200px;
	max-width: 100px !important
}

/***search*************/
.search_part {
	margin-top: 15px;
	margin-bottom: -15px;
}

.searchName {
	width: 10%;
}

.searchAccount {
	width: 10%;
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
	position: relative;
	z-index: 9;
}

#policy_length {
	margin-top: 10px;
	padding: 0px;
}

.datatb-max-width-policy {
	width: 200px !important;
	max-width: 200px !important;
}

.datatb-max-width-policy-depart {
	width: 300px !important;
	max-width: 300px !important;
}

.dataTables_length {
	margin-top: 4px;
}

#bWLogTb_wrapper .row {
	margin-top: 30px;
}
</style>