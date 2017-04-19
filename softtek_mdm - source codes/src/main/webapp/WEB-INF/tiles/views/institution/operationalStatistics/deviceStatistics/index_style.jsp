<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/resources/js/datatables-1.10.11/css/dataTables.bootstrap.css" var="dataTableCss" />
<link href="${dataTableCss}" rel="stylesheet" />
<spring:url value="/resources/js/datepicker/bootstrap-datetimepicker.css" var="datetimePickerCss" />
<link href="${datetimePickerCss}" rel="stylesheet" />
<spring:url value="/resources/js/clockpicker/clockpicker.css" var="clockpickerCss" />
<link href="${clockpickerCss}" rel="stylesheet" />
<style>
  /********libra modify the department manager management part css 4/5 ***************************/

.timestarts{
    float: left;
    margin-top:0px;
    margin-right: 10px;
}
.time_icon {
    float: left;
    margin-right:5px;
    margin-top:0px;
}
.timeends {
 	margin-top:0px;
    float: left;
}
.search_icon{
	margin-top:-5px;
}	
.reset_icon{
	margin-top:-5px;
}
.button_left{
	margin-left:5px;
}
.head-middle{
	margin-top:12px;
	margin-left:20px;
	float:left;
}
.head-left{
	margin-top:12px;
	margin-left:10px;
	float:left;
}
.head-right{
	margin-top:14px;
	float:right;
	margin-right:10px;
}
.search{
	margin: 0px 10px 0px 20px;
    cursor: pointer;
    padding: 2px 2px 2px 2px;
}
.search-current{
	background-color: #369bd7;
    border-color: #369bd7;
    color: #fff;
}

.head-section{
	padding-top:10px;
}
.span-dimension{
	margin-top:6px;
}
.bordertype{
	height:50px;
	display:block; 
	border:1px solid #ccc
}
.head-span{
	margin-left:20px;
}
.b-b-l-none{
	height:60px;
}
.deviceChart{
 	height:350px;
 }
</style>
