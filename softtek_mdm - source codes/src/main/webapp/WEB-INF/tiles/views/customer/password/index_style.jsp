<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

  <spring:url value="/resources/js/datatables-1.10.11/css/dataTables.bootstrap.css" var="dataTableCss" />
  <link href="${dataTableCss}" rel="stylesheet" />
  

  <style>
    .user li:nth-child(1) a i{color: #177bbb;}
.user li:nth-child(1) > a
{
  min-width: 120px;
    text-align: center;
}
.dropdown.open > ul > li:nth-child(1) > a{ text-align: left;}
  
</style>
