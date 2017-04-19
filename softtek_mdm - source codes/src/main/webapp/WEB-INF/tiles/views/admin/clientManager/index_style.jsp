<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
.dataTables_info {
	white-space: nowrap;
}

.dataTables_paginate.paging_full_numbers {
	margin-right: 13px;
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

html, body, div, span, a, ol, ul, li, label,
	table, tbody, tfoot, thead, tr, th, td, aside {
	margin: 0;
	padding: 0;
	border: 0;
	font-family: "microsoft yahei";
	vertical-align: baseline;
}

.gird-btns {
	line-height: 25px;
	text-align: left;
	padding: 2px 9px 0 0;
	overflow: hidden;
}

.edit-table {
	margin-bottom: 10px;
	width: 100%;
}

.edit-table td div {
	line-height: 18px;
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

.edit-table .td-label {
	width: 15%; 
	text-align: right;
	vertical-align: top;
}

.edit-table td {
	padding: 10px 10px 4px 4px;
	word-wrap: no-wrap;
	white-space: nowrap;
	vertical-align: top;
	line-height: 25px;
}

.edit-table .pop-conlists {
	display: block;
}

.edit-table td div {
	line-height: 18px;
}

.pop-text {
	width: 200px;
	height: 22px;
	line-height: 22px;
	border: 1px solid #bbb; 
	padding: 0 3px
}

.pop-conlists {
	display: inline-block;
	vertical-align: middle;
	color: #333;
	position: relative;
}

.pop-conlists {
	*display: inline;
	*zoom: 1; 
	font-size: 12px;
}

.pop-title, .pop-text, .pop-content, .pop-listtext {
	display: inline-block;
	*display: inline;
	*zoom: 1;
	vertical-align: middle;
}

.edit-table .file-upload {
	width: 71%;
	padding: 0px 45px 0 0;
	width: 71% \9;
}

.file-upload {
	position: absolute;
	opacity: 0;
	filter: alpha(opacity = 0);
	left: 0;
	height: 30px;
	width: 295px;
	cursor: pointer;
	z-index: 1;
}

.edit-table .pop-text {
	width: 60%;
	height: 28px;
	line-height: 28px;
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

.pop-item {
	line-height: 25px;
}

.edit-table .pop-conlists {
	display: block;
}

.pop-conlists label {
	display: inline-block;
	*display: inline;
	vertical-align: middle;
}

.edit-table .pad-race-treebox {
	width: 60%;
	display: inline-block;
	*display: inline;
	*zoom: 1;
	padding: 0 0px 0 6px;
	vertical-align: middle;
}

.edit-table .rece-treebox {
	width: 60%;
	display: inline-block;
	*display: inline;
	*zoom: 1;
	vertical-align: middle;
}

ol, ul, li {
	list-style: none;
}

.rece-treebox {
	width: 300px;
	border: 1px solid #ccc;
	max-height: 150px;
	overflow: auto;
}

.equal-area {
	overflow: hidden;
}

.edit-table .pop-area, .equal-area {
	width: 80%;
}

.area-model {
	border: 1px solid #bbb;
}

.equal-area .pop-area {
	width: 99.9%;
	padding: 25px;
	border: none;
}

.red {
	display: inline-block;
	*display: inline;
	*zoom: 1;
	position: relative;
	width: 6px;
	margin-right: -6px;
	vertical-align: -3px;
	color: #f00;
}

.pop-btns {
	position: relative;
	z-index: 3;
	padding: 10px 0px;
	text-align: center;
	border-top: 1px solid #bbb
}

.pop-btns .button {
	padding: 0 20px 0 20px;
	margin-right: 22px;
	margin-left: 0;
	background: #88B442;
	border: 1px solid #366915
}

a:link, a:visited {
	text-decoration: none;
	color: #333;
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

.detail-table-box .edit-table td {
	border-bottom: 1px solid #efefef;
	color: #3b3b3b;
}

.tb-hd1 {
	position: absolute;
	padding: 6px 15px;
	left: 0px;
	top: 58px;
	background: #fff;
	font-size: 16px;
	color: #424954;
}

.equal-area .pop-area {
	width: 99.9%;
	padding: 11px;
	border: none;
}

.tb-w-max {
	min-widht: 200px;
	max-width: 200px;
}

.tb-w {
	min-width: 80px !important
}

.edit-table .pad-race-treebox {
	width: 60%;
	display: inline-block;
	*display: inline;
	*zoom: 1;
	padding: 0 0px 0 6px;
	vertical-align: middle;
}

.edit-table .rece-treebox {
	width: 60%;
	display: inline-block;
	*display: inline;
	*zoom: 1;
	vertical-align: middle;
}

.rece-treebox {
	width: 300px;
	border: 1px solid #ccc;
	max-height: 150px;
	overflow: auto;
}
</style>