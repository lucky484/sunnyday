<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

  <spring:url value="/resources/js/datatables-1.10.11/css/dataTables.bootstrap.css" var="dataTableCss" />
  <link href="${dataTableCss}" rel="stylesheet" />
  

  <style >
  .doc-buttons .btn.btn-success{border-radius: 5px;}
	#queryRoleModal textarea{margin-top: 10px;}
	#roleTable{
    width: 100%!important;
	}
	.checkbox i{
    	border-radius: 4px;
	}
      .user li:nth-child(1) a i{color: #177bbb;}
.user li:nth-child(1) > a
{
    min-width: 120px;
    text-align: center;
}
.dropdown.open > ul > li:nth-child(1) > a{ text-align: left;}
#appList {width:100%!important;}
#appList_length{margin-top:5px;}
.table-responsive .row{margin-top:30px!important;}
#container{height:100%;} 
.blog-post{height:100%} 
.devicePlace {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"microsoft yahei";} 
</style>
