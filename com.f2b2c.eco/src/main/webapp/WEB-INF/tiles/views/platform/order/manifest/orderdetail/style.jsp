<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
 <style type="text/css">
	body {
		font-family: "宋体",arial;
		font-size: 14px;
	}
 	img{ 
 		width:43mm; 
 		height:15mm; 
 	} 
	.title{
		padding-bottom:5px;
	}
 	.rule { 
 		width: 230mm; 
 		height: 150mm; 
 		border:1px solid transparent; 
 		padding-right: 18px; 
 		line-height: 20px; 
 	} 
 	.rule-child { 
 		width: 207mm; 	
 		border:1px solid transparent; 
 		margin-right: 18px; 
 		margin-left: 40px;
 		line-height: 20px; 
 		float: right ; 
 	} 
	.table-header{
		border:0px;
		width: 100%;
	}
	.table-header tr,td {
		border: 0px !important;
	}
	
	.table {
		border-collapse: collapse !important;
		width:100%;
	}
	.table a{
		text-decoration:none;
	}
	.table td,
	.table th {
		background-color: #fff !important;
		height: 3mm;
	}
	.blank{
		height: 6mm !important;
	}
	th {
		text-align: center;
	}
	.table-bordered-i th,
	.table-bordered-i td {
		border: 1px solid #000 !important;
		padding: 2px !important;
	}
	.table-bordered-i td {
		padding-left: 5px;
	}
	.column-center {
		text-align: center;
	}
	.column-right {
		text-align: right;
	}
		
	.float-l {
		float: left;
	}
	.float-r {
		float: right;
	}
	.upright {
		width: 1em;
	    font-size: 14px;
	    letter-spacing: 1px;
	    position: absolute;
	    top: 39mm;
	    left: 223mm;
	    line-height: 1.1em;
}
.scale {
	-moz-transform:rotate(90deg); 
	-webkit-transform:rotate(90deg); 
	-o-transform:rotate(90deg); 
	transform:rotate(90deg); 
	filter:progid:DXImageTransform.Microsoft.BasicImage(rotation=1);
    line-height: 0.5em;
    margin: 1px;
}
	
 </style>