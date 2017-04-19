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
<spring:url value="/resources/js/summernote/summernote.css"
	var="summernoteCss" />
<link href="${summernoteCss}" rel="stylesheet" />


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
/*********4/14 libra add***********/
.blog-post .post-item {
	margin-right: 15px;
	margin-left: 15px;
}

.btn-success.btn-rounded {
	border-radius: 5px;
}

.col-lg-2.paddingWidth {
	margin-left: 100px;
}

.col-lg-8.paddingWidth {
	margin-left: -90px;
}

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

#parsley-id-2 {
	margin-top: 5px;
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

li.node-tree.node-selected {
	background-color: #fff !important;
}

.list-group-item:first-child {
	border-top-left-radius: 4px;
	border-top-right-radius: 4px;
}

.list-group {
	padding-left: 0;
	margin-bottom: 20px;
}

.list-group {
	border-radius: 2px;
}

.treeHeight {
	min-height: 100px;
	max-height: 175px;
	overflow: scroll;
	width: 100%;
}

.node-eidtTree {
	
}

#editTree {
	margin: 0px;
	padding: 0px;
	width: 100%;
}

#tree {
	margin: 0px;
	padding: 0px;
	width: 100%;
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

.borderNoLine {
	border: 0px solid silver;
}

.paddingZero {
	padding: 0px;
}

table {
	width: 100%;
}

ul, li, img {
	list-style-type: none;
}

.virtualLi {
	height: 28px;
	cursor: pointer;
	font-size: 13px;
	margin-bottom: 5px;
}

#tree .node-tree {
	color: #788288 !important;
	border: 1px solid #eaeef1;
	ss
}

.userLi {
	height: 28px;
	line-height: 28px;
	margin-top: 6px;
	cursor: pointer;
	font-size: 13px;
	background: none repeat scroll 0 0 #eee;
}

.policyVirtual {
	overflow-x: hidden;
	height: 155px;
	list-style: none;
	border: 1px solid #cccccc;
	border: 1px solid silver;
	padding-left: 5px;
	padding-bottom: 0px;
}

.borderNoLine {
	border: 0px solid silver;
}

.policyVirtualRight {
	overflow-x: hidden;
	height: 155px;
	list-style: none;
	border: 1px solid #cccccc;
	border: 1px solid silver;
	padding-left: 5px;
	margin-left: 10px;
}

.policyUserRight {
	overflow-x: hidden;
	height: 155px;
	list-style: none;
	border: 1px solid #cccccc;
	border: 1px solid silver;
	margin-left: 2px;
	padding-left: 2px;
	padding-right: 2px;
}

.policyVirtHeight {
	height: 185px;
}

.policyVirtPadding {
	padding-left: 8px;
}

.policyVirtPadding {
	width: 100%;
	font-size: 13px;
}

#tree {
	margin: 0px;
	padding: 0px;
	width: 100%;
}

#tree .node-tree {
	color: #788288 !important;
	border: 1px solid #eaeef1;
}

#savePolicyForm .node-tree {
	color: #788288 !important;
	border: 1px solid #eaeef1;
	cursor: auto !important;
}

.node-eidtTree {
	color: #788288 !important;
	border: 1px solid #eaeef1 !important;
}

.node-viewTree {
	color: #788288 !important;
	border: 1px solid #eaeef1 !important;
}

.searchIcon {
	margin-top: 7px;
	margin-left: 5px;
}

.search_part {
	z-index: 99;
}

.search_button {
	margin-top: -5px;
}

.dataTables_length {
	margin-top: 5px;
}

#PicAndTxtMsgList_wrapper .row {
	margin-top: 30px;
}

.note-resizebar{
	display:none;
}
</style>