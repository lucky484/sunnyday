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
        <title>身份证信息查询</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="../css/normalize.css">
        <link rel="stylesheet" href="../css/main.css">
    </head>
    <body>
        <div class="container wrapper">

            <div class="page-wrapper">
                <div class="page-con">
                    <div class="match-hd">
                        <div class="match-title-box">
                            信息查询
                            <span>身份证信息查询</span>
                        </div>
                         <div class="match-title-box">
<!--                          <form action="InquiryIdCardInfo.do" id="formId" method="post"> -->
                            <div class="check-more">
                                <div class="form-group clearfix">
                                    <label class="checkbox-inline">
                                      <input type="checkbox" id="isCardInfo" value="" checked> 证件比中
                                    </label>
                                     <label class="checkbox-inline">
                                      <input type="radio" name="isFaceAndCard" value="" checked> 全部
                                    </label>
                                    <label class="checkbox-inline">
                                      <input type="radio" name="isFaceAndCard" value="1"> 人证匹配人员
                                    </label>
                                    <label class="checkbox-inline">
                                      <input type="radio" name="isFaceAndCard" value="2"> 人证不匹配
                                    </label>
                                </div>
                                <div id="check-more">
                                   <div class="form-group clearfix">
                                        <div class="pull-left w1">
                                            <label>采集时间</label>
                                           <div class="date">
                                               <input type="text" id="date_timepicker_start" name="collectTimeStart" class="form-control" value="${collectTimeStart}">
                                              
                                           </div>
                                           <hr>
                                           <div class="date">
                                               <input type="text" id="date_timepicker_end" name="collectTimeEnd" class="form-control" value="${collectTimeEnd}">
                                               
                                           </div>
                                        </div>
                                       <div class="pull-left w2">
                                            <label>采集地点</label>
                                             <input type="text" name="collectSite" class="form-control" value="${collectSite}">
                                             <i class="fa fa-x"></i>
                                       </div>
                                   </div>
                                   <div class="form-group clearfix">
                                        <div class="pull-left w1">
                                            <label>采集人员姓名</label>
                                            <input type="text" name="idCardName"  class="form-control fl-100" id="idCardName" value="${idCardName}" style="margin: 0 48px 0 0;">
                                            <i class="fa fa-x fa-x-pos"></i>
                                            <label>采集人员性别</label>
                                            <select name="sexSelected" id="idCardSex" class="form-control fl-100" value="${sexSelected}">
                                                <option value="">请选择</option>
                                                <option value="男">男</option>
                                                <option value="女">女</option>
                                            </select>
                                        </div>
                                       <div class="pull-left w2">
                                            <label>采集人员身份证</label>
                                            <input type="text" name="idCardNo" class="form-control" value="${idCardNo}">
                                             <i class="fa fa-x"></i>
                                       </div>
                                   </div>
                                </div>
                            </div>
                            <a href="javascript:void(0);" class="btn search-more" id="search" >查询</a>
                             <input type="hidden" id="page" name="page" value="${page.page}"/>
<!--                            </form> -->
                        </div>
                    </div>
                    <div class="match-data">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th width="20%">采集时间</th>
                                    <th width="20%">采集地点</th>
                                    <th width="20%">采集身份证信息</th>
                                    <th width="15%">身份信息比对</th>
                                    <th width="15%">人证匹配</th>
                                    <th width="10%">操作</th>
                                </tr>
                            </thead>
                             <tbody id="idCardInfo">
                            </tbody>	
                        </table>
                          <div class="clearfix">
                            <div class="pull-left" id="pageCount">
                            </div>
                            <div class="pull-right">
                                     <ul class="pagination">
                                  </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="../js/vendor/jquery-1.11.3.min.js"></script>
        <script src="../js/plugins.js"></script>
        <!-- <script src="js/main.js"></script> -->
        <script src="../js/jquery.datetimepicker.full.min.js"></script>
		<script src="../js/InquiryIdCardInfo.js"></script>
    </body>
</html>
