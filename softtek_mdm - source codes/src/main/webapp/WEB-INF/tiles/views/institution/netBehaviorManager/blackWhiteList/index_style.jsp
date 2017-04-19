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
	text-align: right;
    margin-right: 14px;
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
/*********4/14 libra add***********/
.blog-post .post-item {
	margin-right: 15px;
	margin-left: 15px;
}

.btn-success.btn-rounded {
	border-radius: 5px;
}


/* .col-lg-2.paddingWidth {
	margin-left: 100px;
}

.col-lg-8.paddingWidth {
	margin-left: -90px;
}
 */
.form-group {
	margin-bottom: 0px;
}

.app_list .col-lg-10.paddingWidth {
	margin-left: 155px;
}

.app_table .col-lg-12 {
	margin-left: 100px;
}

.app_table .col-lg-2 {
	margin-left: -40px;
}

.app_table .col-lg-8 {
	margin-left: -150px;
}

#parsley-id-2 {
	margin-top: -10px;
}

.table-striped.dataTable {
	width: 100% !important;
}

.app_table .col-lg-12.paddingWidth.appBody {
	margin-left: 0px;
	background: #fff;
	border-bottom: 1px solid #eee;
}

.app_table .col-lg-12.paddingWidth.appBody:hover {
	background: #eee;
}

.datatb-max-width-blackwitelist {
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
	margin-right: -30px;
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

#bWListTb_wrapper .row {
	margin-top: 30px;
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

/* 导入功能样式 */

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