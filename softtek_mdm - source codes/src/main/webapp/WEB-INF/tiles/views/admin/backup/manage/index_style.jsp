<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/resources/js/datatables/datatables.css"
	var="dataTableCss" />
<link href="${dataTableCss}" rel="stylesheet" />
<style>
.pos {
	margin-top: -5px;
}

.separate_div_basic {
	margin-top: 50px;
}

.button_left {
	margin-left: 10px;
}

.labelPos {
	margin-top: -24px;
}

.user li:nth-child(1) a i {
	color: #177bbb;
}

.user li:nth-child(1)>a {
	min-width: 120px;
	text-align: center;
}

.dropdown.open>ul>li:nth-child(1)>a {
	text-align: left;
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
	width: 12%;
}

.searchStatus {
	width: 12%;
}

.icon_pos {
	margin-left: 25px;
}

.searchName_val, .searchAccount_val, .searchStatus_val {
	width: 16%;
	padding: 0px;
	margin-top: -5px;
}

.search_button {
	margin-top: -18px;
	margin-left: 10px;
}

.datatb-max-width-user-policy {
	max-width: 100px !important;
}

.datatb-max-width-user-username {
	max-width: 100px !important;
}

.network_info {
	margin-left: 65px;
}

.users_infomation {
	margin: 20px;
}

.btn-info {
	color: #fff !important;
	background-color: #3494d2 !important;
}

#timeStart, #timeEnd {
	width: 80px;
}

.searchName_val {
	width: 20% !important;
}

.timestarts {
	float: left;
	margin-right: 10px;
}

.time_icon {
	float: left;
	margin-right: 5px;
}

.timeends {
	float: left;
}

.device_table .row {
	margin-top: 30px;
}

.user li:nth-child(1)>a {
	min-width: 120px;
	text-align: center;
}

.search_part {
	margin-bottom: 10px;
}
</style>