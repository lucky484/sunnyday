<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<style>

.div-title {
	border: solid 1px grey;
	width: 100%;
	background-color: White font-size: 16px;
}

.divTitleName{
	line-height: 30px; 
	background-color: #FFFFFF; 
	color: #000000; 
	position:relative; 
	top: -15px; 
	left: 30px;
}

input{
	height:30px;
}
.file-box {
	position: relative;
	width: 100%
}

.licenseFiletTxt{
	width:60%
}
.right-text{
	width:60%
}

.fileupload {
	position: absolute;
	top: 0;
	left: 0px;
	height: 30px;
	/* filter: alpha(opacity : 0); */
	opacity: 0;
	width: 65%;
}


.updateLicense{
	width:100%;
}

.info-table{
	width:100%;
}

.updateLicense td {
	white-space: nowrap;
	vertical-align: top;
	line-height: 25px;
	padding: 4px 10px 4px 4px;
}

.info-table td {
	white-space: nowrap;
	vertical-align: top;
	line-height: 25px;
	padding: 4px 10px 4px 4px;
}

.td-label{
	width: 15%;
	text-align: right;
	vertical-align: top;
}

table {
	border-collapse: collapse;
	border-spacing: 0px;
	cellspacing: 0px;
	cellpadding: 0px;
}

.red {
	display: inline-block;
	position: relative;
	width: 6px;
	margin-right: -6px;
	vertical-align: -3px;
	color: #f00;
}

thead, td {
	border: 0px;
}
</style>