<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
.datatb-max-width-security {
	width: 500px !important;
	max-width: 500px !important;
}

.datatb-max-width {
	width: 200px !important;
	max-width: 200px !important;
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

ol, ul, li {
	list-style: none;
}

.role-btns {
	padding: 0 9px 5px 0;
	text-align: left;
}

a:link, a:visited {
	text-decoration: none;
	color: #333;
}

.button {
	height: 27px;
	line-height: 24px;
	line-height: 22px\9;
	padding: 0 5px 0 25px;
	margin: 0 12px 0 5px;
	vertical-align: middle;
	font-weight: normal;
	display: inline-block;
	*display: inline;
	*zoom: 1;
	cursor: pointer;
	outline: none
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

.search-toggle a.hide {
	background-position: 5px 1px
}

.search-toggle a {
	line-height: 13px;
	padding: 0 0 0px 23px;
	height: 18px;
	*line-height: 18px;
	position: absolute;
	right: 0;
	top: 2px;
	display: inline;
}

.role-btns {
	padding: 0 9px 5px 0;
	text-align: left;
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

.select-box .Js_curVal input, .Js_curVal input[type=text] {
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

.select-item {
	margin-left: -30px;
}
</style>