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
<spring:url value="/resources/js/clockpicker/standalone.css"
	var="standaloneCss" />
<link href="${standaloneCss}" rel="stylesheet" />
<spring:url value="/resources/js/clockpicker/clockpicker.css"
	var="clockpickerCss" />
<link href="${clockpickerCss}" rel="stylesheet" />
<spring:url value="/resources/css/carousel/lanrenzhijia.css"
	var="lanrenzhijia" />
<link href="${lanrenzhijia}" rel="stylesheet" />
<style>
/********libra modify the device policy management part css 3/29 ***************************/
/*device policy */
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

.userStyle {
	overflow-x: hidden;
	height: 155px;
	list-style: none;
}

.section {
	height: 650px;
}

.asterisk {
	color: red;
	font-size: 16px;
}

.attention {
	color: red;
	margin-top: 5px;
	padding: 0px;
}

.description {
	height: 80px;
	margin-bottom: -15px;
}

.department {
	height: 185px;
}

.treeHeight {
	min-height: 100px;
	max-height: 175px;
	overflow: scroll;
	width: 100%;
	border: 1px solid #aaafbc;
}

.modal-footer {
	border-top: 1px solid #aaafbc;
}

.virtualAuth {
	height: 155px;
}

.blackRadio {
	height: 38px;
}

.notion {
	height: 55px;
}

.currentNameList {
	height: 38px;
}

.virtualLi {
	height: 28px;
	cursor: pointer;
	font-size: 13px;
	margin-bottom: 5px;
}

.userLi {
	height: 28px;
	line-height: 28px;
	margin-top: 6px;
	cursor: pointer;
	font-size: 13px;
	background: none repeat scroll 0 0 #eee;
}

.paddingZero {
	padding: 0px;
}

.datemargin {
	margin-left: 7px;
}

.limargin {
	margin-top: 4px;
}

.chooseNameList {
	height: 30px;
	line-height: 30px;
}

.btn-success.btn-rounded {
	border-radius: 5px;
}

.description {
	height: 80px;
	margin-bottom: -15px;
}

.panel-heading .nav-tabs>li>a {
	border: 1px solid #eee;
}

.nav-justified .active a {
	font-weight: bold;
}

.form-horizontal .form-group {
	margin-right: -15px;
	margin-left: 0px;
}

#tab2 .form-group {
	margin-right: -15px;
	margin-left: 0px;
	width: 100%;
	float: left;
}

#tab2 .col-sm-2 {
	text-align: right;
	padding-top: 7px;
}

#tab2 .col-sm-8 {
	margin-bottom: -20px;
}

#tab3 .m-l, #tab4 .m-l {
	margin-left: 145px;
}

.glyphicon-chevron-down:before {
	content: "\2212";
}

.glyphicon-chevron-right:before {
	content: "\2b";
}

.icon.fa-users {
	display: none;
}

#devicePolicy {
	width: 100% !important;
}

.blog-post .post-item {
	margin-right: 15px;
	margin-left: 15px;
}

.paddingZero {
	padding: 0px;
}

.searchIcon {
	margin-top: 7px;
	margin-left: 5px;
}

table.dataTable thead .sorting_asc:after {
	content: "";
}

.glyphicon-chevron-down:before {
	content: "\e114" !important;
}

.glyphicon-chevron-right:before {
	content: "\e080" !important;
}

.glyphicon {
	cursor: pointer;
}

.glyphicon-check:before, .glyphicon-uncheck:before {
	font-size: 16px;
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

.worktime {
	margin-left: 90px !important;
}

.open .dropdown-menu {
	margin-left: -45px !important;
}

#deviceTab>ul>li:first-child {
	padding-left: 1px;
}

#deviceTab>ul>li:last-child {
	padding-right: 1px;
}

.tab-pane#tab5 {
	margin-left: 145px;
}

.tab-pane#tab6 {
	margin-left: 145px;
}

#visitLimit {
	width: 38px;
	height: 20px;
}

.online_time {
	float: right;
	margin-right: 145px;
	margin-top: -5px;
}

#findDevice a {
	color: #6787B8;
}

#findDevice a:hover {
	color: #D67632;
	text-decoration: underline;
}

#findDevice a:visited {
	color: #6787B8;
}

.datatb-max-width-devicepolicyname {
	width: 150px !important;
	max-width: 150px !important;
}

.datatb-max-width-devicepolicymark {
	width: 150px !important;
	max-width: 150px !important;
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

.datatb-max-width-devicepolicy-status {
	width: 105px !important;
	max-width: 105px !important;
}

label {
	display: inline-block;
	width: 5em;
}

.dataTables_length {
	margin-top: 4px;
}
</style>
<style>
.ui-tooltip, .arrow:after {
	background: #3494D2;
	border: 0px;
}

.ui-tooltip {
	color: #fff;
	font: 5px "Helvetica Neue", Sans-Serif;
	text-transform: uppercase;
	box-shadow: 0 0 7px black;
}

.arrow {
	width: 70px;
	height: 16px;
	overflow: hidden;
	position: absolute;
	left: 50%;
	margin-left: -35px;
	bottom: -16px;
}

.arrow.top {
	top: -16px;
	bottom: auto;
}

.arrow.left {
	left: 20%;
}

.arrow:after {
	content: "";
	position: absolute;
	left: 20px;
	top: -20px;
	width: 25px;
	height: 25px;
	box-shadow: 6px 5px 9px -9px black;
	-webkit-transform: rotate(45deg);
	-moz-transform: rotate(45deg);
	-ms-transform: rotate(45deg);
	-o-transform: rotate(45deg);
	tranform: rotate(45deg);
}

.arrow.top:after {
	bottom: -20px;
	top: auto;
}

li.node-tree.node-selected {
	background-color: #fff !important;
}

.glyphicon-minus:before {
	content: "\e114" !important;
}

.glyphicon-plus:before {
	content: "\e080" !important;
}

#loadAssignedList_length, #loadAssignedList_info {
	width: 150px;
	float: left;
	margin-top: -20px;
}

.wifiindex {
	margin-left: 300px;
	margin-top: 50px;
}

.wifitop {
	margin-top: 10px
}

.wifileft {
	margin-left: 40px
}

.saftyType {
	width: 150px;
}

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

.iosHeight {
	height: 40px;
	line-height: 40px;
	font-size: 18px;
	background-color: #ddd;
	color: white;
}

.filterStyle {
	border: 1px solid silver;
	width: 100%;
	height: 130px;
	overflow: scroll;
}

.notFilterStyle {
	border: 1px solid silver;
	width: 100%;
	height: 130px;
	overflow: scroll;
}

.blue {
	color: #6888B9
}

.webcontent_outer {
	border: 1px solid silver;
	height: 100px;
	overflow: scroll;
}

.bookmark {
	text-align: center;
	height: 30px;
	line-height: 30px;
}

.bookName {
	border-right: 1px solid silver;
	height: 30px;
	line-height: 30px;
	text-align: center;
}

.bookUrl {
	border-right: 1px solid silver;
	height: 30px;
	line-height: 30px;
	text-align: center;
}

.bookTitle {
	border: 1px solid silver;
	height: 30px;
	border-bottom: 0px;
}

.cusorPoint {
	cursor: pointer;
}

.liStyle {
	background-color: #eaeef1;
	height: 25px;
	line-height: 25px;
	cursor: pointer;
	margin-top: 5px;
}

.blue {
	background-color: #369BD7;
	color: white;
	height: 25px;
	line-height: 25px;
	cursor: pointer;
	margin-top: 5px
}

.marginBottom {
	margin-bottom: 0px;
}

.borderBottom {
	border-bottom: 1px solid silver;
}

.borderLR {
	border-right: 0px solid silver;
}

.paddingLR {
	padding-left: 0px;
	padding-right: 0
}

.cursor {
	cursor: pointer;
}

.marginTop {
	margin-top: 5px;
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
