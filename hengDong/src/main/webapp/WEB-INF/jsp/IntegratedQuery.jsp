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
        <title>综合信息查询 - 高级</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="../css/normalize.css">
        <link rel="stylesheet" href="../css/main.css">
    </head>
    <body>
        <div class="container wrapper">
            <!-- 主内容区域 -->
            <div class="page-wrapper">
                <div class="page-con">
                    <div class="match-hd">
                        <div class="match-title-box">
                            信息查询
                            <span>综合信息查询</span>
                        </div>
                       		<div class="match-title-box">
					<!--                         <form action="getComplexQueryList.do" id="formId" method="post"> -->
					<div class="check-more">
						<div class="form-group clearfix">
							<div class="checkbox-inline">比中情况：</div>
							<label class="checkbox-inline"> <input type="checkbox"
								id="" name="faceComp" value="" checked> 人像比中
							</label> <label class="checkbox-inline"> <input type="checkbox"
								id="" name="idCardComp" value="" checked> 证件比中
							</label>
						</div>
						<div class="form-group clearfix">
							<div class="checkbox-inline_redio">身份证采集情况:</div>
							<label class="checkbox-inline_redio"> <input type="radio"
								id="" name="faceAndIdCardComp" value="" checked/> 全部
							</label> <label class="checkbox-inline_redio"> <input
								type="radio" id="" name="faceAndIdCardComp" value="1" /> 人证匹配人员
							</label> <label class="checkbox-inline_redio"> <input
								type="radio" id="" name="faceAndIdCardComp" value="2" />
								人证不匹配
							</label> <label class="checkbox-inline_redio"> <input
								type="radio" id="" name="faceAndIdCardComp" value="3" /> 身份证未采集到
							</label>
						</div>
						<div id="check-more" class="hide">
							<div class="form-group clearfix">
								<div class="pull-left w1">
									<label>采集时间</label>
									<div class="date">
										<input type="text" id="date_timepicker_start"
											name="collectTimeStart" class="form-control">
											
									</div>
									<hr>
									<div class="date">
										<input type="text" id="date_timepicker_end"
											name="collectTimeEnd" class="form-control"> 
											
									</div>
								</div>
								<div class="pull-right w2">
									<label>采集地点</label> <input type="text" name="collectSite"
										class="form-control">
								</div>
							</div>
							<div class="form-group clearfix">
								<div class="pull-left w1">
									<label>采集身份证姓名</label> <input type="text" name="idCardName"
										class="form-control fl-100"> <label>采集身份证性别</label>
									<select name="sexSelected" id="sexSelected"
										class="form-control fl-100">
										<option value="">请选择</option>
										<option value="男">男</option>
										<option value="女">女</option>
									</select>
								</div>
								<div class="pull-right w2">
									<label>采集身份证号</label> <input type="text" name="idCardNo"
										class="form-control">
								</div>
							</div>
							<div class="form-group clearfix" id="faceCompSelect">
								<div class="pull-left w1">
									<label>采集人像比中人员姓名</label> <input type="text"
										class="form-control fl-100" name="faceCompName"> 
										<label>采集人像比中人员性别</label> <select
										name="sexComp" id="sexComp" class="form-control fl-100">
										<option value="">请选择</option>
										<option value="1">男</option>
										<option value="2">女</option>
									</select>
								</div>
								<div class="pull-right w2">
									<label>采集人像比中人员身份证号</label> <input type="text" name="faceCompNo"
										class="form-control">
								</div>
							</div>
							<div class="form-group clearfix" id="cardCompInput">
								<div class="pull-left w1">
									<label>身份证比中人员姓名</label> <input type="text"
										class="form-control fl-100" name="cardCompName"> 
										<label>身份证比中人员性别</label> <select
										name="cardSexComp" id="cardSexComp" class="form-control fl-100">
										<option value="">请选择</option>
										<option value="1">男</option>
										<option value="2">女</option>
									</select>
								</div>
								<div class="pull-right w2">
									<label>身份证比中人员身份证号</label> <input type="text" name="cardCompNo"
										class="form-control">
								</div>
							</div>
						</div>
						<a href="javascript:;" class="open-more"> <i
							class="fa fa-angle-double-down"></i>
						</a>
					</div>
					<a href="javascript:void(0)" class="btn search-more">查询</a> <input
						type="hidden" id="page" name="page"  value="${page.page}"/>
                        </div>
                    </div>
                    <div class="match-data">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th colspan="4">采集信息</th>
                                    <th colspan="4">比对结果</th>
                                  </tr>
                                <tr>
                                    <th width="10%"> 采集时间</th>
                                    <th width="10%">采集地点</th>
                                    <th width="10%">采集照片</th>
                                    <th width="17%">采集身份证信息</th>
                                    <th width="10%">人证相似度</th>
                                    <th width="14%">身份信息比对</th>
                                    <th width="19%">人像信息比对</th>
                                    <th width="19%">操作</th>
                                </tr>
                            </thead>
                             <tbody id="faceAndCardInfo">
					</tbody>
                        </table>
                          <div class="clearfix">
                            <div class="pull-left">
                                <span class="page-info" id="pageCount"></span>
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
            <!-- 主内容区域 end -->
        <script src="../js/vendor/jquery-1.11.3.min.js"></script>
        <script src="../js/plugins.js"></script>
        <script src="../js/main.js"></script>
        <script src="../js/jquery.datetimepicker.full.min.js"></script>
        <script src="../js/IntegratedQuery2.js"></script>
    </body>
</html>
