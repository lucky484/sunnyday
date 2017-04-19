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
.nav-tabs.nav-justified>.active>a, .nav-tabs.nav-justified>.active>a:hover,
	.nav-tabs.nav-justified>.active>a:focus {
	border: 1px solid #3494D2;
}

.nav-tabs>li.active>a, .nav-tabs>li.active>a:hover, .nav-tabs>li.active>a:focus
	{
	color: #fff;
	cursor: default;
	background-color: #3494D2;
	border: 1px solid #ddd;
	border-bottom-color: transparent;
}

.chosen-w {
	width: 100% !important;
}

.tab-badge-info {
	background-color: #1ccacc !important;
}

.tab-badge {
	background-color: #b0bcd4 !important;
}
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

#tree {
	width: 100%;
	margin: 0px;
	padding: 0px;
}

#tree .list-group {
	height: 175px;
	width: 100%;
	padding: 0px;
	margin: 0px;
}

.info_des, .icon.node-icon.fa-users {
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
	margin-bottom: 5px;
}

#view-a>div:nth-child(2) .btn.btn-rounded.btn-sm {
	border-radius: 5px;
	margin-left: 20px;
	color: #181a1c !important;
	background-color: #f7f8fb;
	border-color: #ddd;
	margin-bottom: 5px;
}

#view-a>div:nth-child(1) .btn.btn-rounded.btn-sm {
	border-radius: 5px;
	margin-left: 20px;
	color: #181a1c !important;
	background-color: #f7f8fb;
	border-color: #ddd;
	margin-bottom: 5px;
}

#view-a .btn.btn-rounded.btn-sm.btn-default {
	border-radius: 5px;
	margin-left: 20px;
	color: #181a1c !important;
	background-color: #f7f8fb;
	border-color: #ddd;
	margin-bottom: 5px;
}

.datatb-max-width-device-rule {
	max-width: 150px !important;
}

.datatb-max-width-device-rule-name {
	max-width: 150px !important;
}

#view-o .table {
	width: 92%;
	margin-left: 4%;
}
/* #btn-confirm.hidden{    display: inline!important;visibility: visible!important;} */
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

#table-rule-legal th，
#table-rule-legal-detail th {
	border-right: 1px solid #eaeef1;
}

#table-rule-legal_length {
	width: 170px;
	float: left;
	margin-top: -20px;
}

#table-rule-legal-detail_length {
	width: 170px;
	float: left;
	margin-top: -30px;
}

#table-rule-legal-detail_info {
	margin-top: -35px;
	margin-left: 150px;
}
ol, ul, li {
	list-style: none;
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
