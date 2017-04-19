<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:url
	value="/resources/js/datatables-1.10.11/css/dataTables.bootstrap.css"
	var="dataTableCss" />
<link href="${dataTableCss}" rel="stylesheet" />
<style>
/********libra modify the user management part css 3/29 ***************************/
.fa-group:before, .fa-users:before {
	content: "";
}

.glyphicon-chevron-down:before {
	content: "\2212";
}

.glyphicon-chevron-right:before {
	content: "\2b";
}

.icon.node-icon.fa.fa-users {
	display: none;
}

.wrapper-md {
	width: 100%;
}

.doc-buttons .btn {
	border-radius: 5px;
	margin-right: 10px;
}

.scrollable {
	overflow-x: hidden;
}

@media ( min-width : 768px) {
	.wrapper-md  .aside-md {
		width: 15%;
	}
}

@media ( min-width : 768px) {
	.col-sm-9 {
		width: 85%;
	}
}

#myTable th .checkbox {
	margin-left: 25px;
}

#myTable th .checkbox input {
	display: none;
}

#department, #modify_depart {
	border: 1px solid #cbd5dd;
	border-radius: 2px;
}

.form-group .col-lg-10 {
	margin-left: 0px;
}

.treeview .list-group {
	min-width: 180px;
}

.wrapper-md .aside-md {
	/* overflow-x: scroll; */
	
}

#tree {
	overflow-x: scroll;
}

#tree-list .list-group {
	border: 1px solid #eaeef1;
	border-top: 0px;
}

.wrapper-md .scrollable.aside-md.col-sm-3 {
	width: 20%;
	border: 1px solid #eaeef1;
	border-radius: 3px;
}

.wrapper-md .col-sm-9 {
	width: 80%;
}

.panel-body {
	padding: 5px;
}

#myTable_wrapper {
	width: 100%;
}

.wrapper-md  .list-group .node-tree {
	color: #788288;
	border: 1px solid #eaeef1;
}

.node-tree.node-selected {
	color: #ffffff;
}
/*****libra add new css for font icon   4/8*********/
.fa-unlock:before {
	font-size: 16px;
	margin-right: 5px;
}

.i-arrow2:before {
	font-size: 16px;
	margin-right: 5px;
}

.i-arrow:before {
	font-size: 16px;
	margin-right: 5px;
}

.glyphicon-user:before {
	font-size: 16px;
	margin-right: 5px;
}

.btn.btn-rounded.btn-sm.btn-info {
	border-radius: 5px;
}

.panel-default.hbox.stretch .scrollable {
	height: 450px;
}

#deviceModal .modal-dialog {
	height: 600px;
}

.glyphicon-chevron-down:before {
	content: "\e114";
}

.glyphicon-chevron-right:before {
	content: "\e080";
}

#myTable_filter {
	margin-top: -35px;
}

#myTable_length {
	margin-top: 10px;
}

.panel-body .col-sm-10.ck-tmpl {
	margin-bottom: 15px;
	border: 1px solid #cbd5dd;
	border-radius: 3px;
}

.panel-body #promoteTree.col-sm-10.treeview {
	margin-bottom: 15px;
	border: 1px solid #cbd5dd;
	border-radius: 3px;
}

.treeview .list-group-item {
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

#department .list-group-item.node-department {
	color: #788288 !important;
	border: 1px solid #eaeef1;
}

#department .list-group-item.node-department.node-selected {
	background-color: #eaeef1 !important;
}

#modify_depart .list-group-item.node-modify_depart {
	color: #788288 !important;
	border: 1px solid #eaeef1;
}

#modify_depart .list-group-item.node-modify_depart.node-selected {
	background-color: #eaeef1 !important;
}

#uRDFrm>div>div:nth-child(5)>div {
	padding: 0px;
}

.wrapper-md #tree {
	overflow-y: hidden;
}

.aside-md .treeview .list-group-item {
	cursor: pointer !important;
}
/******4/21************/
.import, .export, .notice {
	display: none;
}

.add_user {
	background: #3494d2 !important
}

#myTable_wrapper .col-lg-2 {
	margin-top: -5px;
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

.searchName_val, .searchAccount_val, .searchStatus_val {
	width: 16%;
	padding: 0px;
	margin-top: -5px;
}

.search_button {
	margin-top: -5px;
}

.datatb-max-width-user-policy {
	max-width: 100px !important;
}

.datatb-max-width-user-username {
	width: 100px !important;
	max-width: 100px !important;
}

.datatb-max-width-user-appInfo-name {
	max-width: 160px !important;
}

.datatb-max-width-user-appversion {
	max-width: 160px !important;
}

.datatb-max-width-user-logsInfo {
	max-width: 200px !important;
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
/********user status********/
.fa-lock:before {
	font-size: 16px;
	margin-right: 5px;
}

.users_status {
	text-align: left !important;
	position: relative;
}

.users_status i:nth-child(1) {
	width: 33% !important;
}

.users_status i:nth-child(2) {
	width: 33% !important;
}

.users_status i:nth-child(3) {
	width: 33% !important;
}

/*a  upload */
.a-upload {
	padding: 4px 10px;
	height: 30px;
	width: 155px;
	line-height: 20px;
	position: relative;
	cursor: pointer;
	color: #888;
	background: #fafafa;
	border: 1px solid #ddd;
	border-radius: 4px;
	overflow: hidden;
	display: inline-block;
	*display: inline;
	*zoom: 1;
	text-align: center;
}

.a-upload  input {
	position: absolute;
	font-size: 100px;
	right: 0;
	top: 0;
	opacity: 0;
	filter: alpha(opacity = 0);
	cursor: pointer
}

.a-upload:hover {
	color: #444;
	background: #eee;
	border-color: #ccc;
	text-decoration: none
}

.uploadvirmodel {
	margin-top: 20px;
}

.showFileName {
	width: 100%;
	margin-top: 2px;
	margin-left: -13px;
}
</style>

<style type="text/css">
html {
	height: 100%
}

body {
	height: 100%;
	margin: 0px;
	padding: 0px
}

#deviceInfoContent {
	height: 100%;
	width: 100%
}
</style>
<style>
.devicePlace {
	width: 100%;
	height: 408px;
	overflow: hidden;
	margin: 0;
	font-family: "microsoft yahei";
}

.data-box {
	border: 0px;
	background: #C9C9C9;
	height: 12px;
	vertical-align: middle;
	width: 135px;
	display: inline-block;
}

.data-inner {
	background: #77B94E;
	height: 12px;
	text-align: center;
	color: #000;
	line-height: 12px;
	white-space: pre;
	word-wrap: normal;
	word-break: normal;
	overflow: visible;
}

.data-text {
	font-size: 8pt;
	display: inline-block;
}

ol, ul, li {
	list-style: none;
}

a:link, a:visited {
	text-decoration: none;
	color: #333;
}

.aside-item a:link, .aside-item a:visited {
	display: block;
	padding: 5px 0px 5px 25px;
	color: #fff;
}

.aside-item a:hover, .aside-item a:active, .aside-item a.current {
	color: #949494;
}

.aside-list {
	padding-top: 35px;
	width: 160px;
}

.pop-aside {
	overflow: auto;
	background-color: #F0F0F0;
}

.pop-asidelist .aside-item {
	position: relative;
	*zoom: 1;
}

.pop-asidelist .aside-item a:link, .pop-asidelist .aside-item a:visited
	{
	height: 30px;
	line-height: 24px;
	padding: 0px 0px 12px 20px;
	border-bottom: 1px solid #ddd;
	color: #A9A9A9;
	border-left: 5px solid #F0F0F0;
	font-size: 14px;
}

.pop-asidelist .aside-item a:active, .pop-asidelist .aside-item a:hover,
	.pop-asidelist .aside-item a.current:link, .pop-asidelist .aside-item a.current:visited
	{
	color: #1E5E8F;
	border-left: 5px solid #1E5E8F;
}

.pop-aside {
	width: 160px;
	float: left;
}

.pop-aside .aside-list {
	width: auto;
}

.pop-main {
	position: relative;
	overflow: auto;
	height: 450px;
}

.main-item {
	position: relative;
	overflow: auto;
	background: #fff;
}

.detail-aside {
	width: 195px;
}

.detail-aside .pop-asidelist .aside-item a:link, .detail-aside .pop-asidelist .aside-item a:visited
	{
	padding: 0 0 0 20px;
}

.pop-main {
	position: relative;
	overflow: auto;
	height: 450px;
}

.pop-cons {
	overflow: auto;
	padding: 0px 0px 0px;
	border: 1px solid #BBBBBB;
}

.main-item {
	position: relative;
	overflow: auto;
	background: #fff;
}

.main-cons {
	padding: 4px 7px;
	padding: 4px 7px 0px 7px;
	margin: 10px;
	border: 1px solid #ccc;
	background-color: #fff;
}

.edit-table {
	margin-bottom: 10px;
	width: 100%;
}

.temp-scroll-box {
	overflow-x: hidden;
}

table {
	border-collapse: collapse;
	border-spacing: 0;
}

.edit-table td {
	border: 0;
	padding: 4px 10px 4px 4px;
	word-wrap: no-wrap;
	white-space: nowrap;
	vertical-align: top;
	line-height: 25px;
}

.span-40per {
	width: 40%;
}

.span-60per {
	width: 60%;
}

.edit-table td div {
	line-height: 18px;
}

.detail-tb-hd {
	position: relative;
	border-bottom: 1px solid #aaa;
	height: 1px;
	margin: 12px 0px;
	line-height: normal;
}

.tb-hd {
	position: absolute;
	padding: 6px 15px;
	left: 0px;
	top: -12px;
	background: #fff;
	font-size: 16px;
	color: #424954;
}

.detail-table-box {
	margin: 0px 20px;
}

.detail-table-box .edit-table td {
	border-bottom: 1px solid #efefef;
	color: #3b3b3b;
	border: 0
}

.detail-table-box .edit-table td {
	border-bottom: 1px solid #efefef;
	color: #3b3b3b;
}

.edit-table .td-label {
	width: 15%; 
	text-align: right;
	vertical-align: top;
}

.edit-table td { 
	padding: 4px 10px 4px 4px;
	word-wrap: no-wrap;
	white-space: nowrap;
	vertical-align: top;
	line-height: 25px;
}

.tempbd-item {
	float: left;
	overflow: auto;
}

.inline-select {
	display: inline-block;
	*display: inline;
	*zoom: 1;
	vertical-align: middle;
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

.select-box .Js_curVal input,.Js_curVal1 input, .Js_curVal input[type=text],.Js_curVal1 input[type=text] {
	width: 85%;
	height: 23px;
	border: none;
}

.select-list {
	display: none;
	position: absolute;
	z-index: 5;
	left: -1px;
	top: 24px;
	width: 218px;
	border: 1px solid #ddd;
	white-space: nowrap;
	overflow: auto;
	max-height: 300px;
	background: #F0F0F0;
}

.th-border {
	border: 1px solid #eaeef1;
}

.table>thead>tr>th {
	vertical-align: bottom;
	border-bottom: -1px;
}
</style>