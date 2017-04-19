<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
html, body, div, span, a, ol, ul, li, label, table, tbody, tfoot, thead,
	tr, th, td, aside {
	margin: 0;
	padding: 0;
	border: 0;
	font: 12px/1.5;
	font-family: "microsoft yahei";
	vertical-align: baseline;
}

html, body {
	background: #F0F0F0;
}

.button {
	height: 27px;
	line-height: 24px;
	line-height: 22px\9;
	padding: 0 5px 0 5px;
	margin: 0 12px 0 5px;
	vertical-align: middle;
	font-weight: normal;
	display: inline-block;
	*display: inline;
	*zoom: 1;
	cursor: pointer;
	outline: none
}

/*****Libra log add 419*****/
.logdefault {
	border: 0px;
}

.dataTable#deviceLog {
	width: 100% !important;
	border: 1px solid #eee !important;
	border-width: 1px 0;
	margin-top: 10px;
}

.dataTable#deviceLog td {
	border: 1px solid #eaeef1;
}

.dataTables_info {
	white-space: nowrap;
}

.dataTables_paginate.paging_full_numbers a.paginate_button {
	cursor: pointer;
	outline: 0;
	border: 1px solid #d9d9d9;
	border-right-width: 0;
	background: #fff;
	padding: 4px 8px;
	color: #3c4144;
}

.dataTables_paginate.paging_full_numbers a.paginate_button.first.disabled,
	.dataTables_paginate.paging_full_numbers a.paginate_button.previous.disabled,
	.dataTables_paginate.paging_full_numbers a.paginate_button.next.disabled,
	.dataTables_paginate.paging_full_numbers a.paginate_button.last.disabled
	{
	cursor: not-allowed;
}

a.paginate_button.current {
	border: 1px solid #d9d9d9 !important;
	background: #eee !important;
}

.dataTables_paginate.paging_full_numbers a.paginate_button.last.disabled,
	.dataTables_paginate.paging_full_numbers a.paginate_button.last {
	border-right: 1px solid #ddd !important;
}

.panel.panel-default {
	border: 0px !important;
}

/***search*************/
.search_part {
	margin-top: 15px;
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

.datatb-max-width-policy {
	width: 100px !important;
	max-width: 100px !important;
}

.datatb-max-width-policy-depart {
	width: 300px !important;
	max-width: 300px !important;
}

#deviceLog_wrapper .row {
	margin-top: 30px;
}

.datatb-max-width-devicelog {
	width: 250px !important;
	max-width: 250px !important;
}

.datatb-max-width-username {
	width: 250px !important;
	max-width: 250px !important;
}

.select-item {
    margin-left: 8px;
}
</style>
