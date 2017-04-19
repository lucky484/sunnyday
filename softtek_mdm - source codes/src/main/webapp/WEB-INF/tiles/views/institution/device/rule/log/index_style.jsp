<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:url
	value="/resources/js/datatables-1.10.11/css/dataTables.bootstrap.css"
	var="dataTableCss" />
<link href="${dataTableCss}" rel="stylesheet" />
<spring:url value="/resources/js/clockpicker/standalone.css"
	var="standaloneCss" />
<link href="${standaloneCss}" rel="stylesheet" />
<spring:url value="/resources/js/clockpicker/clockpicker.css"
	var="clockpickerCss" />
<link href="${clockpickerCss}" rel="stylesheet" />
<spring:url value="/resources/js/chosen/chosen.css" var="chosenCss" />
<link href="${chosenCss}" rel="stylesheet" />


<style>
/*****libra 418*********/
.btn-success.btn-rounded {
	border-radius: 5px;
}

.blog-post .post-item {
	margin-right: 15px;
	margin-left: 15px;
}

#table-rule_wrapper>div:nth-child(3) {
	margin-top: 30px;
}

#table-rule {
	width: 100% !important;
}

#addModal .modal-header {
	height: 50px;
	background: #f9fafc;
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
	border-bottom: 0px;
}

#addModal .modal-header h4 {
	margin-top: 0px;
	font-size: 14px;
	color: #333;
	font-weight: bold;
}

.nav-tabs>li a {
	border-color: #eee !important;
}

#rule-tab-justified li:first-child a {
	border-left: 0px;
}

#rule-tab-justified li:last-child a {
	border-right: 0px;
}

#rule-tab-justified li a {
	background-color: #f7f8fb;
}

#rule-tab-justified li.active a {
	background-color: #ffffff;
	color: #555;
	cursor: default;
}

b.badge {
	margin-right: 5px;
}

#tree .list-group {
	height: 175px;
	overflow-x: hidden;
	border: 1px solid silver;
}

.info_des, #tree {
	margin-left: 145px;
}

.icon.node-icon.fa-users {
	display: none;
}

#tree ul li.node-tree {
	color: #788288 !important;
	border: 1px solid #eaeef1;
	cursor: auto !important;
}

.glyphicon-check:before, .glyphicon-uncheck:before {
	font-size: 16px;
}

.vir_user {
	margin-left: 90px !important;
}

.only_user {
	margin-left: 85px;
}

#vtls {
	max-height: 150px;
	overflow-y: scroll;
	overflow-x: hidden;
	height: 155px;
	list-style: none;
	border: 1px solid #cccccc;
	border: 1px solid silver;
	padding-left: 5px;
}

#rule {
	overflow-x: hidden;
}

.rule_th th {
	background-color: #f9f9f9;
	border: 2px solid #ddd !important;
}

#ruleTb, #operaTb {
	width: 95% !important
}

.vir_user {
	margin-top: -15px;
}

#view-a .h4.m-b, #view-r .h4.m-b {
	font-size: 16px;
}

#view-r .h4.m-b {
	margin-top: -5px;
}

#view-r .table, #view-0 .table {
	width: 92%;
	margin-left: 4%;
}

.dropdown-menu .divider {
	margin: 0px !important;
	background-color: #fff !important;
}

#view-a .btn.btn-rounded.btn-sm.btn-info {
	border-radius: 5px;
	margin-left: 20px;
}

#btn-confirm.hidden {
	display: inline !important;
	visibility: visible !important;
}
/***search*************/
.search_part {
	margin-top: 30px;
	margin-bottom: 0px;
}

.searchName {
	width: 10%;
	margin-right: -15px;
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

#table-rule-log_wrapper .row {
	margin-top: 30px;
}

#table-rule-log {
	width: 100%;
}
</style>

