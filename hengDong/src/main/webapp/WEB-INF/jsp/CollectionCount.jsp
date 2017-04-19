<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!doctype html>
<html class="no-js" lang="">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="pragma" CONTENT="no-cache"> 
		<meta http-equiv="Cache-Control" CONTENT="no-cache, must-revalidate"> 
		<meta http-equiv="expires" CONTENT="0"> 
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>采集次数统计</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="../css/normalize.css">
        <link rel="stylesheet" href="../css/main.css">
        <style type="text/css">
        	.ysh{
        		width:30px;
        		margin: 0px;
          		border: 0px;
        		
        	}
        </style>
    </head>
    <body>
    	<input type="hidden" name="hiddenIdCardNo" id="hiddenIdCardNo" />
        <input type="hidden" name="hiddenCollectSite" id="hiddenCollectSite"/>
        <input type="hidden" name="hiddenCountPage" id="hiddenCountPage"/>
        <input type="hidden" name="hiddenStartDate" id="hiddenStartDate"/>
        <input type="hidden" name="hiddenEndDate" id="hiddenEndDate"/>
        <input type="hidden" id="hiddenfys" name="hiddenfys" value="10">
        <input type="hidden" id="flag" name="flag" value="0">
       
                <div class="page-con">
                    <div class="match-hd">
                        <div class="match-title-box">
                            研判分析
                            <span>采集次数统计</span>
                        </div>
                        <div class="match-title-box">
                            <div class="check-more" style="width:90%;">
                                <div id="check-more">
                                   <div class="form-group clearfix">
                                        <div class="pull-left w1" style="width:34%;">
                                            <label style="width:20%;">采集时间</label>
                                           <div class="date" style="width:33%;">
                                               <input type="text" id="date_timepicker_start" class="form-control"  name="startDate" >
                                              
                                           </div>
                                           <hr style="width:1%;margin-left: 2px;margin-right: 2px;">
                                           <div class="date" style="width:33%;">
                                               <input type="text" id="date_timepicker_end" class="form-control" name="endDate" >
                                               
                                           </div>
                                        </div>
                                       <div class="pull-left w2" style="width:30%;">
                                            <label style="width:30%;">采集地点</label>
                                            <input type="text" class="form-control" id="collectSite" name="collectSite" style="width:60%;"> 
                                       </div>
									   <div class="pull-left w3" style="width:35%;">
                                            <label style="width:40%;">采集人员身份证</label>
                                            <input type="text" class="form-control" id="idCardNo" name="idCardNo" style="width:60%;">
                                       </div>
                                   </div>
                                </div>
                            </div>
                            <button class="btn search-more" id="query_btn" style="bottom:20px;">查询</button>
                        </div>
                    </div>
                    <div class="match-data">
                        <table class="table">
                            <thead>
                                <tr>
                                	<th width="20%">采集地点</th>
                                    <th width="35%">采集身份证信息</th>
                                    <th width="16%">身份信息比对</th>
                                    <th width="16%">采集次数</th>
                                    <th width="13%">操作</th>
                                </tr>
                            </thead>
                            <tbody id="pageContent">  
                            </tbody>
                        </table>
                        <div class="clearfix">
                            <div class="pull-left">
                                <span class="page-info" >共<span id="counts">0</span>条 每页<input type="text" id="fys" value="10" class="ysh">条 页次：<span id="currentPage">1</span>/<span id="pages">1</span></span>
                            </div>
                            <div class="pull-right">
                                 <ul class="pagination">
                                    <li>
                                      <button onclick="getPage(1)">首页</button>
                                    </li>
                                    <li><button onclick="getPrePage()">上一页</button></li>
                                    <li>
                                        <input type="text" value="1" id ="page" >
                                    </li>
                                    <li><button onclick="getNextPage()">下一页</button></li>
                                    
                                    <li><button onclick="getEndPage()">尾页</button></li>
                                  </ul>
                            </div>
                        </div>
                    </div>
                </div>
        <script src="../js/vendor/jquery-1.11.3.min.js"></script>
        <script src="../js/plugins.js"></script>
        <script src="../js/jquery.datetimepicker.full.min.js"></script>
        <script src="../js/CollectionCount.js"></script>
    </body>
</html>
