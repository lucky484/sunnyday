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
	color: #788288;
	font-size: 13px !important;
	min-width: 180px;
	margin-left: -40px;
	margin-bottom: -10px;
	border: 1px solid #eaeef1;
	border-top: 0px;
}

#tree {
	margin-bottom: 20px;
	border-top: 1px solid #eaeef1;
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
	margin-bottom: 0px;
}

#tree-gourp-list ul:last-child {
	margin-bottom: -10px;
}

#tree-gourp-list li:hover {
	background-color: #F5F5F5;
}

#tree-gourp-list li.active-tag {
	color: #FFFFFF;
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
	margin-bottom: 5px;
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
	height: 63%;
}

.col-lg-9 .text-right {
	width: 240px;
	text-align: left;
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
}

.glyphicon-plus:before {
	content: "\e080";
}

.glyphicon-minus:before {
	content: "\e114";
}
.right{

}
</style>

