<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url
	value="/resources/platform/css/plugins/easyUI/themes/metro/easyui.css"
	var="easyUICssUrl" />
<link rel="stylesheet" type="text/css" href="${easyUICssUrl}" />
<style>
	th{
		text-align:center;
	}
	td{
		text-align:center;
	}
	
.picDiv {
	width: 100px;
	height: 100px;
	border: 1px solid #ccc;
	text-align: center;
	color: #23527c;
	position: relative;
	float: left;
}

.picHint {
	vertical-align: middle;
	position: absolute;
	left: 0;
	width: 100%;
	height: 100%;
}

.picInput {
	width: 100px;
	height: 100px;
	position: absolute;
	opacity: 0;
	cursor: pointer;
}

.goodsImg {
	width:100%;
	height:100%;
}
#sortable {
	list-style-type: none;
	margin: 0;
	padding: 0;
}

#sortable li {
	float: left;
	width: 100px;
	height: 100px;
	margin: 0 10px 0 0;
}

.close-modal {
	top: -8px;
	right: -8px;
	width: 18px;
	height: 18px;
	font-size: 14px;
	line-height: 16px;
	border-radius: 9px;
	color: #fff;
	text-align: center;
	cursor: pointer;
	position: absolute;
	background: rgba(153, 153, 153, 0.6);
}

</style>
