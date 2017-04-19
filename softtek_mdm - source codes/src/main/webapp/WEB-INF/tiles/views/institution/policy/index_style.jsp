<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:url value="/resources/js/datatables/datatables.css"
	var="dataTableCss" />
<link href="${dataTableCss}" rel="stylesheet" />
<spring:url value="/resources/js/clockpicker/standalone.css"
	var="standaloneCss" />
<link href="${standaloneCss}" rel="stylesheet" />
<spring:url value="/resources/js/clockpicker/clockpicker.css"
	var="clockpickerCss" />
<link href="${clockpickerCss}" rel="stylesheet" />
<style>
/********libra modify the user policy management part css 3/29 ***************************/
.form-group #tree {
	border: 1px solid #cbd5dd;
	border-radius: 2px;
}

.btn-success.btn-rounded {
	border-radius: 5px;
}

#addStrategyModal .modal-body, #queryDetail .modal-body, #updatePolicy .modal-body
	{
	overflow-x: hidden !important;
}

#addStrategyModal #insertTree .node-insertTree {
	width: 100%;
	cursor: auto;
}

#addStrategyModal #insertTree {
	margin-left: -40px;
}

.datepicker-input1:hover, .datepicker-input2:hover {
	cursor: pointer;
}

#policy {
	width: 100% !important;
}

#content div.scrollable.padder {
	background: #FAFAFA;
	padding: 20px;
	border-radius: 5px;
}

#myTable_length {
	margin-top: -10px;
}

.badge.bg-primary.limits {
	margin-top: 20px;
	margin-left: 20px;
}

.worktime, .wifi, .limit_login, .auto_login, .allow_errow, .login_count,
	.fig_pass {
	margin-left: 90px !important;
}

.open .dropdown-menu {
	margin-left: -45px !important;
}

#parsley-id-31, #parsley-id-72 {
	float: right;
	margin-top: 40px;
	margin-left: -38px;
	position: relative;
	left: -627px;
}

#parsley-id-33, #parsley-id-74 {
	float: right;
	margin-top: 40px;
	margin-left: -38px;
	position: relative;
	left: -642px;
}

#parsley-id-38, #parsley-id-79 {
	float: right;
	margin-top: 40px;
	margin-left: -38px;
	position: relative;
	left: -632px;
}

#parsley-id-28 {
	float: right;
	margin-top: 30px;
	margin-left: -38px;
	position: relative;
	left: -539px;
	margin-bottom: -20px;
}

#parsley-id-30 {
	float: right;
	position: relative;
	margin-right: 300px;
	margin-bottom: -20px;
}

#parsley-id-35 {
	float: right;
	margin-top: 30px;
	margin-left: -38px;
	position: relative;
	left: -540px;
	margin-bottom: -20px;
}

#parsley-id-66 {
	float: right;
	margin-top: 30px;
	margin-left: -38px;
	position: relative;
	left: -548px;
	margin-bottom: -20px;
}

#parsley-id-68 {
	float: right;
	position: relative;
	margin-right: 300px;
	margin-bottom: -20px;
}

#parsley-id-73 {
	float: right;
	margin-top: 30px;
	margin-left: -38px;
	position: relative;
	left: -548px;
	margin-bottom: -20px;
}

.parsley-errors-list.filled {
	font-size: 10px !important;
}

.glyphicon-minus:before {
	content: "\e114" !important;
}

.glyphicon-plus:before {
	content: "\e080" !important;
}

.glyphicon {
	cursor: pointer;
}

.glyphicon-check:before, .glyphicon-uncheck:before {
	font-size: 16px;
}

#insertTree .node-insertTree {
	color: #788288 !important;
	border: 1px solid #eaeef1;
	ss
}

#modifyPolicy .list-group {
	margin-left: -40px;
}

#modifyPolicy .node-updateTree {
	color: #788288 !important;
	border: 1px solid #eaeef1;
	cursor: auto !important;
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
	height: 25px !important;
}

.search_part {
	z-index: 99;
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

.deleted a, .deleted a .i.text-dange {
	color: #e33244 !important;
	font-weight: bold !important;
}

.dataTables_info {
	margin-left: -30px;
}
/* .parsley-errors-list.filled#parsley-id-12{
	float: right;
    position: relative;
    margin-right: 30px;
}
.parsley-errors-list.filled#parsley-id-14{
	 float: right;
    position: relative;
    margin-top: 7px;
    margin-right: 338px;
    margin-bottom: -30px;

} */
.parsley-errors-list.filled#parsley-id-12 {
	float: right;
	margin-top: 30px;
	position: relative;
	left: -456px;
	margin-bottom: -20px;
}

.parsley-errors-list.filled#parsley-id-14 {
	float: right;
	margin-top: 30px;
	position: relative;
	left: -255px;
	margin-bottom: -20px;
}

#parsley-id-50 {
	float: right;
	margin-top: 30px;
	position: relative;
	left: -426px;
	margin-bottom: -20px;
}

#parsley-id-52 {
	float: right;
	position: relative;
	margin-right: 233px;
	margin-bottom: -30px;
	margin-top: 6px;
}
</style>
