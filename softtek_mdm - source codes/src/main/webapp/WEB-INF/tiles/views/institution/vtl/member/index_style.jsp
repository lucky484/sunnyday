<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<style>
/*****libra add css for 虚拟组部分 3/23******/
/***虚拟组CSS修改*****/
.scrollable {
	overflow-y: auto;
}

#tree li {
	border: none;
	cursor: pointer;
	position: relative;
	display: block;
	padding: 10px 15px;
	color: #788288 !important;
	font-size: 13px !important;
	min-width: 180px;
	margin-bottom: -10px;
	margin-left: -40px;
}

#tree-list li span, #tree-list li strong {
	font-weight: normal;
}

#tree-list li span {
	width: 12px;
	margin-right: 5px;
}

#tree ul li.list-group:hover {
	background-color: #F5F5F5;
}

#tree-list:first-child li.active-tag, #tree-list li.active-tag {
	background-color: #428bca;
	color: #ffffff;
}

#tree-list:first-child li.active-tag:hover, #tree ul#tree-list li.active-tag:hover
	{
	background-color: #428bca;
}

#tree-gourp-list li {
	min-width: 180px;
	font-size: 12px !important;
	padding-left: 55px;
	margin-left: -80px;
	height: 38px;
	margin-bottom: 0px;
	border: 1px solid #eaeef1;
	border-top: 0px;
}

#tree-list .list-group {
	border: 1px solid #eaeef1;
	border-top: 0px;
}

#tree {
	margin-bottom: 20px;
	border-top: 1px solid #eaeef1;
}

#tree-gourp-list ul:last-child {
	margin-bottom: -10px;
}

#tree-gourp-list li:hover {
	background-color: #F5F5F5;
}

#tree-gourp-list li.active-tag {
	color: #FFFFFF !important;
	background-color: #428bca;
}

.form-horizontal .form-group {
	margin-left: 0px !important;
}

.col-lg-8 .padder {
	padding-left: 0px;
	padding-right: 0px;
}

.form-horizontal .form-group {
	margin-bottom: 15px;
}

.col-lg-offset-2.col-lg-10 {
	margin-top: 10px;
	text-align: center;
	padding-left: 0px !important;
	margin-left: 0px !important;
}

.scrollable li.tree-list-style.active-tag {
	color: #ffffff !important;
}

.note-name.text-ellipsis strong {
	font-weight: normal;
	font-size: 12px;
}

.col-lg-9 .vbox.flex {
	height: 45%;
}

.col-lg-9 .text-right {
	width: 240px;
	text-align: left;
	margin-left: 20px;
}
/**********delete the X from the virtual group list***********/
#note-items-1 .close.hover-action {
	display: none;
}

.wrapper-md .scrollable.aside-md.col-sm-3 {
	width: 20%;
	border: 1px solid #eaeef1;
	border-radius: 3px;
	overflow-y: hidden;
}

.wrapper-md .col-sm-9 {
	width: 80%;
	height: 547px;
}

.glyphicon-plus:before {
	content: "\e080";
}

.glyphicon-minus:before {
	content: "\e114";
}

.text-right .pagination>li>a {
	outline: 0;
	border: 1px solid #d9d9d9 !important;
	border-right-width: 0;
	background: #fff;
	padding: 4px 8px;
	color: #3c4144;
	text-decoration: none;
}

.select-sort {
	border: 1px solid #b9c6d0;
}

.fa-chevron-left:before, .fa-chevron-right:before {
	content: "";
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

.exportvirtual {
	margin-top: 40px;
}

.uploadvirmodel {
	margin-top: 20px;
}

.showFileName {
	width: 100%;
	margin-top: 2px;
	margin-left: -13px;
}

.modal-body {
	
}
</style>

