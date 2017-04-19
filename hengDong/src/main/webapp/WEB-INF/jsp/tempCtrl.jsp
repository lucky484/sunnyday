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
        <meta http-equiv="x-ua-compatible" content="ie=8">
        <title>用户管理</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="../css/normalize.css">
        <link rel="stylesheet" href="../css/main.css">
        <link rel="stylesheet" href="../css/remodal.css">
    </head>
        <body>
         <input type="hidden" name="hiddenqueryIdCardName" id="hiddenqueryIdCardName">
         <input type="hidden" name="hiddenquerySex" id="hiddenquerySex">
         <input type="hidden" name="hiddenqueryIdCardNo" id="hiddenqueryIdCardNo" >
         <input type="hidden" name="message" id="message" value="${message }">
        <div class="container wrapper face_info">
            <div class="page-wrapper">
                <div class="page-con">
                    <div class="match-hd">
                        <div class="match-title-box">
                            临时测试人员管理
                            </div>
                       <div class="match-title-box">
                            <div class="form-inline">
                                <input type="hidden" name="fys" id="fys" value="10">
                                <label for="">姓名</label>
                                <input type="text"  id="queryIdCardName" name="queryIdCardName" class="form-control" style="width:100px" value="${queryIdCardName }">
								<label for="">性别</label>
                                <input type="text"  id="querySex" name="querySex" class="form-control" style="width:100px" value="${querySex }">
                                <label for="">身份证号码</label>
                                <input type="text" id="queryIdCardNo" name="queryIdCardNo" class="form-control" style="width:175px" value="${queryIdCardNo }">
								
								<button id="query" class="btn">查询</button>
								<a href="#add-UserManageMent" class="btn fancybox" >增加</a>
                            </div>
                        </div>
                    </div>
                    <div class="match-data">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>姓名</th>
                                    <th>性别</th>
                                    <th>身份证号码</th>
                                    <th>图片</th>
                                    <th>创建时间</th>
                                    <th>状态</th>
                                    <th>备注</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody id="TempCtrldata">
                            </tbody>
                        </table>
                        <div class="clearfix">
                            <div class="pull-left" id="faceInfoPages">
                                <span class="page-info" id="faceInfoCount">共<span id="count"></span>   条  每页10条             页次    <span id="currentpage">1</span>/<span id="pages"></span>    </span>
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
       <div class="remodal delete-box" data-remodal-id="deleteConfirm">
		<div class="modal-hd">
			<a href="javascript" data-remodal-action="close" class="modal-close"></a>
		</div>
		<div class="modal-bd">
			<p>是否删除该数据？</p>
			<div class="btn-group">
				<button data-remodal-action="confirm" class="btn">是</button>
				<button data-remodal-action="cancel" class="btn btn-gray">否</button>
			</div>
		</div>
	</div>
		
		<!--添加用户start-->
		 <div class="pop-boxx" id="add-UserManageMent" style="display:none;">
             <div class="pop-hdd">
                <h3>增加辑控人员</h3>
                <a href="javascript:$.fancybox.close();" class="pop-close">关闭</a>
            </div>
           <form action="addTempCtrl.do" method="post" id="addTempCtrl"  enctype="multipart/form-data">
           
           <input type="hidden" name="hiddenIdCardName" id="hiddenIdCardName">
           <input type="hidden" name="hiddenSex" id="hiddenSex">
           <input type="hidden" name="hiddenIdCardNo" id="hiddenIdCardNo">
            <div class="pop-bdd">
                <div class="pop-innerr">
                    <table class="add_UserManage">
                        <tr>
                            <th width="15%" align="right"><font color="red" style="font-size: 15px"></font>姓名：</th>
                            <td width="35%">
                                <input type="text" id="idCardName" name="idCardName" required>
                            </td>
							<th align="right"><font color="red" style="font-size: 15px"></font>性别：</th>
                            <td>
                                
                                <select id="sex" name="sex" required>
                                	<option value="1" checked>男</option>
                                	<option value="2">女</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
							<th align="right"><font color="red" style="font-size: 15px" >*</font>身份证号码：</th>
                            <td>
                                <input type="text" id="idCardNo" name="idCardNo" required maxlength="18">
                            </td>
                            <th align="right"><font color="red" style="font-size: 15px">*</font>备注：</th>
                            <td>
                                <input type="text" id="remark" name="remark" required>
                            </td>
                        </tr>
                        <tr>
							<th align="right"><font color="red" style="font-size: 15px">*</font>上传图片：</th>
                            <td>
                                <input type="file" id="pic" name="pic" required>
                            </td>
                            
                        </tr>
                    </table>
                </div>
                <div class="pop-btn">
                    <button class="btn" type="submit">确定</button>
                    <a href="javascript:void(0)" class="btn btn-gray" onclick="$.fancybox.close()">取消</a>
                </div>
            </div>
            </form> 
        </div>
		
		
		
		<!--判断开始-->
		<div class="remodal delete-box" data-remodal-id="panduan">
            <div class="modal-hd">
               <a href="javascript" data-remodal-action="close" class="modal-close"></a> 
            </div>
            <div class="modal-bd" id="judge">
            </div>  
        </div>
		<!--判断结束-->
        <script src="../js/vendor/jquery-1.11.3.min.js"></script>
        <script src="../js/plugins.js"></script>
        <script src="../js/main.js"></script>
		<script type="text/javascript" src="../js/remodal.min.js"></script>
		<script src="../js/tempCtrl.js"></script>
		<script src="../js/jquery.validate.min.js"></script>
    </body>
</html>
