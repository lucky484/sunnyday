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
        <title>人脸信息查询</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="../css/normalize.css">
        <link rel="stylesheet" href="../css/main.css">
        
    </head>
    <body>
        <div class="container wrapper face_info">
            <div class="page-wrapper">
                <div class="page-con">
                    <div class="match-hd">
                        <div class="match-title-box">
                            信息查询
                            <span>人脸信息查询</span>
                        </div>
                           <div class="match-title-box">
                            <div class="check-more">
								<!--搜索start-->
								<div class="form-group clearfix">
                                        <div class="pull-left w1">
                                            <label>采集时间</label>
                                           <div class="date">
                                               <input type="text" id="date_timepicker_start" class="form-control" value="">
                                              
                                           </div>
                                           <hr>
                                           <div class="date">
                                               <input type="text" id="date_timepicker_end" class="form-control" value="">
                                              
                                           </div>
                                        </div>
										
                                       <div class="pull-left w2">
                                            <label>采集地点</label>
                                            <input type="text" class="form-control" id="location">
                                        </div>
										
										<div class="pull-left w3">
                                            <label>相似度≥</label>
                                            <input type="text" class="form-control" id="similarity" maxlength="4">
											%
                                        </div>
										
										<div class="pull-right w4">
										<label class="checkbox-inline" style="padding-top : 10%;">
											<input onclick="getFace()" type="checkbox" id="GET" value="option2" > 可疑人员
										</label>
                                        </div>	
                                   </div>
								<!--搜索end-->
                            </div>
                            <a href="javascript:void(0)" onclick="getface()" class="btn search-more">查询</a>
                        </div>
                    </div>
                    <div class="match-data">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>采集时间</th>
                                    <th>采集地点</th>
                                    <th>采集照片</th>
                                    <th width="300">缉控人员信息</th>
                                    <th>人脸相似度</th>
                                    <th>人证匹配</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody id="faceInfoC">
                              
                            </tbody>
                        </table>
                        <div class="clearfix">
                             <div class="pull-left" id="faceInfoPages">
                                <span class="page-info" id="faceInfoCount"></span>
                            </div>
                            <div class="pull-right">
                                 <ul class="pagination">
                                    <li>
                                      <a onclick="getPage(1)" style="cursor: pointer;">首页</a>
                                    </li>
                                    <li><a onclick="getPrePage()" style="cursor: pointer;">上一页</a></li>
                                    <li>
                                        <input type="text" value="1" id ="page" >
                                    </li>
                                    <li><a onclick="getNextPage()" style="cursor: pointer;">下一页</a></li>
                                    
                                    <li><a onclick="getLastPage()" style="cursor: pointer;">尾页</a></li>
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
		<script src="../js/SearchFaceInfo.js"></script>
    </body>
</html>
