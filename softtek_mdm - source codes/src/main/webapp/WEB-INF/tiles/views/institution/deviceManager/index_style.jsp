<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
.datatb-max-width-user-logsInfo {
	max-width: 200px !important;
}

.tb-opt-main {
	white-space: pre-line;
	position: absolute;
	z-index: 2;
	right: 22px;
	top: -8px;
	display: none;
	width: 105px;
	padding: 1px;
	background: #fff;
	border: 1px solid #C9C9C9;
	line-heigth: 14px;
	text-align: left;
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
	cursor: pointer;
	outline: none;
}

.gird-btns {
	line-height: 25px;
	text-align: left;
	padding: 2px 9px 0 0;
	overflow: hidden;
}

table {
	border-collapse: collapse;
	border-spacing: 0;
}

.edit-table {
	margin-bottom: 10px;
	width: 100%;
}

.td-label {
	width: 15%;
	text-align: center;
	vertical-align: center;
}
/*****libra 4 18*******/
.wrapper-md .scrollable.aside-md.col-sm-3 {
	width: 20%;
	border: 1px solid #eaeef1;
	border-radius: 3px;
}

.wrapper-md .col-sm-9 {
	width: 80%;
}

.treeview .list-group {
	min-width: 180px;
}

.aside-md .treeview .list-group-item {
	cursor: pointer !important;
}

.wrapper-md .list-group .node-tree {
	color: #788288;
	border: 1px solid #eaeef1;
}

.glyphicon-minus:before {
	content: "\e114" !important;
}

.glyphicon-plus:before {
	content: "\e080" !important;
}

.wrapper-md {
	width: 100%;
}

.panel-default .gird-btns {
	position: relative;
}

.showul.dropdown-menu {
	margin-top: -30px !important;
	margin-left: 560px !important;
}

#deviceManager  table>tbody>tr>td {
	border: 0px;
}
/* .icon.node-icon{display:none;}
.modal-dialog .edit-table .td-label{padding:10px;}
.modal-dialog .edit-table td div{padding:10px;}
.modal-dialog .edit-table td div #messageTitle{    height: 30px;    width: 400px;}
.modal-dialog .edit-table td div #msg{    height: 100px;    width: 400px!important;} */
#sendToClient {
	background-color: #3494d2 !important;
	color: #ffffff !important;
}
/***search*************/
.search_part {
	margin-top: 15px;
	margin-bottom: -15px;
}

.searchName {
	width: 12%;
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

.datatb-max-width-devicestatus {
	width: 150px !important;
	max-width: 150px !important;
}

.search_icon {
	float: left;
	margin-right: 2px;
}

.dataTables_length {
	margin-top: 4px;
}

.dataTables_info {
	margin-left: -2px;
	margin-top: 6px;
}

.datatb-max-width-deviceManager-status {
	max-width: 160px !important;
}

.paging_full_numbers {
	margin-right: 15px;
}

#deviceManager_filter {
	display: none;
}
/****device status************/
.device_status {
	text-align: left !important;
	position: relative;
}

.device_status td:nth-child(1) {
	width: 10% !important;
	margin-right: 10px;
}

.device_status td:nth-child(2) {
	width: 10% !important;
	margin-right: 10px;
}

.device_status td:nth-child(3) {
	width: 10% !important;
	margin-right: 10px;
}

.device_status td:nth-child(4) {
	width: 10% !important;
	margin-right: 10px;
}

.device_status td:nth-child(5) {
	width: 10% !important;
	margin-right: 10px;
}

.confirm_button {
	background-color: #3494d2 !important;
	border-color: #158b6c;
	border-radius: 5px;
	color: #fff !important;
}

.confirm_button:hover {
	color: #fff !important;
}

.confirm_button:click {
	color: #fff !important;
}

#pushPicAndInfoTb_wrapper>div.row>div.col-lg-2 {
	width: 25% !important;
}

body, html, #allmap {
	width: 100%;
	height: 100%;
	overflow: hidden;
	margin: 0;
	font-family: "微软雅黑";
}

<!--
add by jing.liu -->.col-sm-1.user_name {
	width: 12%;
	margin-right: -15px;
}

.col-sm-1.device_type {
	width: 12%;
	margin-right: -15px;
}

.col-sm-1.esn_imei {
	width: 12%;
	margin-right: -15px;
}

.col-sm-1.sequence_number {
	width: 12%;
	margin-right: -15px;
}

.search_part1 {
	margin-top: 15px;
	margin-bottom: 0px;
}

.search_part2 {
	margin-top: 15px;
	margin-bottom: 0px;
}

<!--
add by jing.liu -->.check-select-box {
	position: relative;
	width: 50%;
	padding: 0 26px 0 0;
	border: 1px solid #BFBFC0;
	height: 18px;
	line-height: 18px;
	background: #fff;
	margin-left: 4%;
}

.check-select-list {
	display: none;
	background: #F0F0F0;
	position: absolute;
	z-index: 5;
	left: 47px;
	top: 208px;
	width: 88px;
	white-space: nowrap;
	overflow: auto;
	max-height: 50px;
}

.check-select-list .check-select-item {
	*zoom: 1;
	padding: 2px 0px 0px 2px;
	text-align: left;
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

.pagination {
	display: inline-block;
	border-radius: 4px;
	margin-top: 5px;
}

.devicePlace {
	width: 100%;
	height: 408px;
	overflow: hidden;
	margin: 0;
	font-family: Microsoft YaHei;
}
</style>
<style>
ol, ul, li {
	list-style: none;
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
	/*background:url(../images/icon/icon_arrow_r_dis.png) no-repeat 145px center #DAE6F4;*/
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

.select-box .Js_curVal,Js_curVal1, input, .Js_curVal input[type=text] {
	height: 23px;
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

.search-toggle {
	margin: 0px 0px 16px;
	background: #fff;
	height: 1px;
	position: relative;
	cursor: pointer;
	padding-top: 8px;
	border-bottom: 1px solid #aaa;
}

.search-toggle a {
	line-height: 13px;
	padding: 0 0 0px 23px;
	background: url("../resources/images/btn_sh.jpg") 5px -17px no-repeat
		#fff;
	height: 18px;
	*line-height: 18px;
	position: absolute;
	right: 0;
	top: 2px;
	display: inline;
}

.search-toggle a.hide1 {
	background-position: 5px 1px
}

.search-mod {
	position: relative;
	background: #FAFDFE;
	border: 1px solid #ccc;
	margin: 10px 0 5px;
	display: none
}

.search-list {
	margin: 5px 5px 5px -29px;
	float: left
}

.search-item {
	margin: 0 1px;
	padding: 2px 0px;
	display: inline-block;
	*display: inline;
	*zoom: 1;
	vertical-align: top;
}

.search-label, .search-text {
	display: inline-block;
	*display: inline;
	*zoom: 1;
	vertical-align: middle;
	height: 22px;
	line-height: 22px;
}

.search-label {
	width: 80px;
	text-align: right;
}

.button-search {
	height: 27px;
	line-height: 24px;
	line-height: 22px\9;
	padding: 11px 5px 0 5px;
	margin: 0 12px 0 5px;
	vertical-align: middle;
	font-weight: normal;
	display: inline-block;
	*display: inline;
	*zoom: 1;
	cursor: pointer;
	outline: none
}

 .search-mod:after {
	content: "\0020";
	display: block;
	clear: both;
	height: 0;
	font-size: 0;
}
</style>