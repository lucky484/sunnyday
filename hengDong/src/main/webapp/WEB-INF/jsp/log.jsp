<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html class="no-js" lang="">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="pragma" CONTENT="no-cache"> 
		<meta http-equiv="Cache-Control" CONTENT="no-cache, must-revalidate"> 
		<meta http-equiv="expires" CONTENT="0"> 
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>日志管理</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="../css/normalize.css">
        <link rel="stylesheet" href="../css/main.css">
    </head>
    <body>
	<input type="hidden" name="hiddenSimilarity" id="hiddenSimilarity" value="${idCardNo}"/>
        <input type="hidden" name="hiddenCollectSite" id="hiddenCollectSite" value="${collectSite1}"/>
        <input type="hidden" name="hiddenCountPage" id="hiddenCountPage" value="${pages}"/>
        <input type="hidden" name="hiddenPrimary" id="hiddenPrimary" value="${primary}"/>
		<div class="container wrapper ">
            <div class="page-wrapper">
                <div class="page-con">
                    <div class="match-hd">
                        <div class="match-title-box">
                            日志管理
                        </div>
                        <div class="match-title-box">
								<label for="">开始时间</label>
                                <input type="text" id="date_timepicker_start" class="form-control" style="width:150px;" name="logStartDate">
                                <label for="">结束时间</label>
                                <input type="text" id="date_timepicker_end" class="form-control" style="width:150px;" name="logEndDate">
                                <label for="">关键字</label>
                                <input type="text" id="keyWords" class="form-control" value="" style="width:150px;">
								<button class="btn"  onclick="getLogInfo()">查询</button>
								</div>
                    </div>
                    <div class="match-data">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>信息管理系统</th>
                                    <th>人员ID</th>
                                    <th>人员姓名</th>
                                    <th>组织名称</th>
                                    <th>操作时间</th>
                                    <th>操作类型</th>
                                    <th>操作结果</th>
                                    <th>功能模块名称</th>
                                </tr>
                            </thead>
                            <tbody id="logInformations">
                            </tbody>
                        </table>
							<div class="clearfix">
                            <div class="pull-left" id="logInfoPages">
                                <span class="page-info" id="logInfoCount"></span>
                            </div>
                            <div class="pull-right">
                                 <ul class="pagination">
                                    <li>
                                      <a onclick="getPage(1)">首页</a>
                                    </li>
                                    <li><a onclick="getPrePage()">上一页</a></li>
                                    <li>
                                        <input type="text" value="1" id ="page" >
                                    </li>
                                    <li><a onclick="getNextPage()">下一页</a></li>
                                    
                                    <li><a onclick="getLastPage()">尾页</a></li>
                                  </ul>
                            </div>
                        </div>   
                        </div>
                    </div>
                </div>
            </div>
        <script src="../js/vendor/jquery-1.11.3.min.js"></script>
        <script src="../js/plugins.js"></script>
        <script src="../js/main.js"></script>
        <script src="../js/jquery.datetimepicker.full.min.js"></script>
        <script type="text/javascript" src="../js/logManager.js"></script>
    </body>
</html>
