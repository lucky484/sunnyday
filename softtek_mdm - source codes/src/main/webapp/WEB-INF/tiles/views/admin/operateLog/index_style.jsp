<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
html, body, div, span, a, ol, ul, li, label,
	table, tbody, tfoot, thead, tr, th, td, aside{
	margin: 0;
	padding: 0;
	border: 0;
	font: 12px/1.5;
	font-family: "microsoft yahei";
	vertical-align: baseline;
}

/*****Libra log add 419*****/
.logdefault {
	border: 0px;
}

.dataTable#sysManagerLog {
	width: 100% !important;
	border: 1px solid #eee !important;
	border-width: 1px 0;
	margin-top: 10px;
}

.dataTable#sysManagerLog td {
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

.datatb-max-width-sysmanagelog {
	width: 300px !important;
	max-width: 300px !important
}

/***search*************/
.search_part {
	margin-top: 15px;
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

#sysManagerLog_wrapper .row {
	margin-top: 30px;
}

.huihua {
	position: relative !important;
	margin-left: -90px;
	margin-top: -40px;
}

#userOperateDetail th {
	border-right: 1px solid #eaeef1;
}

#userOperateDetail {
	width: 100% !important;
	border: 1px solid #eee !important;
	border-width: 1px 0;
	margin-top: 10px;
}
.select-item {
    margin-left: 8px;
}

.select-box {
	position: relative;
	width: 180px;
	border: 1px solid #BFBFC0;
	height: 26px;
	line-height: 22px;
	background: url(../resources/images/down.png) no-repeat right center
		#fff;
	padding-left: 10px;
}
</style>

