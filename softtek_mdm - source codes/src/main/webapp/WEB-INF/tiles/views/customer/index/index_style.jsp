<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

  <spring:url value="/resources/js/datatables-1.10.11/css/dataTables.bootstrap.css" var="dataTableCss" />
  <link href="${dataTableCss}" rel="stylesheet" />
  

  <style>
  
  .data-box{
  	border: 0px;
    background: #C9C9C9;
    height: 12px;
    vertical-align: middle;
    width: 135px;
    display: inline-block;
  }
  
  .data-inner{
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
  
  .data-text{
  	font-size: 8pt;
    display: inline-block;
  }
  
  .separate_div{
  	margin-top:20px;
  }
  .separate_div_basic{
  	margin-top:50px;
  }
  .basic_info{
  	font-size: 16px;
    color: #424954;
  }
  .basic_line{
  	position: relative;
    border-bottom: 1px solid #aaa;
    height: 1px;
    margin: 12px 0px;
    line-height: normal;
  }
  .span_line{
  	position: absolute;
    padding: 6px 15px;
    left: 0px;
    top: -16px;
    background: #fff;
    font-size: 16px;
    color: #424954;
  }
  .div-content{
  	margin-left:100px;
  }
  .span-content{
  	margin-top:20px;
  }
  
      .user li:nth-child(1) a i{color: #177bbb;}
.user li:nth-child(1) > a
{
    min-width: 120px;
    text-align: center;
}
.dropdown.open > ul > li:nth-child(1) > a{ text-align: left;}
</style>
