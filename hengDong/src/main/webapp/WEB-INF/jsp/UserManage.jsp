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
        <title>用户管理</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="../css/normalize.css">
        <link rel="stylesheet" href="../css/main.css">
        <link rel="stylesheet" href="../css/remodal.css">
    </head>
        <body>
        <div class="container wrapper face_info">
            <div class="page-wrapper">
                <div class="page-con">
                    <div class="match-hd">
                        <div class="match-title-box">
                            用户管理
                            <span>用户管理</span>
                        </div>
                       <div class="match-title-box">
                            <div action="" class="form-inline">
                                <label for="">用户名</label>
                                <input type="text" id="username" class="form-control" style="width:100px">
								<label for="">姓名</label>
                                <input type="text" id="name" class="form-control" style="width:100px">
								<label for="">所属机构</label>
                                <input type="text" id="department" class="form-control" style="width:100px">
								<button id="search" class="btn">查询</button>
								<a href="#add-UserManageMent" class="btn fancybox" onclick="cleanadd()">增加</a>
                            </div>
                        </div>
                    </div>
                    <div class="match-data">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>用户名</th>
                                    <th>姓名</th>
                                    <th>性别</th>
                                    <th>所属角色名称</th>
                                    <th>所属机构</th>
									<th>最后登录时间</th>
									<th>状态</th>
                                </tr>
                            </thead>
                            <tbody id="userMessage">
                            </tbody>
                        </table>
                        <div class="clearfix">
                            <div class="pull-left" id="faceInfoPages">
                                <span class="page-info" id="faceInfoCount"></span>
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
		 <div class="pop-boxx" id="add-UserManageMent" style="display: none;">
            <div class="pop-hdd">
                <h3>增加用户</h3>
                <a href="javascript:$.fancybox.close();" class="pop-close" onclick="refresh()">关闭</a>
            </div>
            <form  id="addUserForm" onsubmit="addNewUser()">
            <div class="pop-bdd">
                <div class="pop-innerr">
                    <table class="add_UserManage" id="addusers">
                        <tr>
                            <th align="right"><font color="red" style="font-size: 15px">*</font>用户名：</th>
                            <td>
                                <input type="text" id="addUserName" name="addUserName" class="form-control" required>
                                <font id="addUserNameExist"style="color:red;display:none">用户名不能重复</font>
                            </td>
							<th align="right"><font color="red" style="font-size: 15px">*</font>密码：</th>
                            <td>
                                <input type="password" id="addPassword" name="addPassword" class="form-control" required>
                            </td>
                        </tr>
                        <tr>
                            <th align="right"><font color="red" style="font-size: 15px">*</font>身份证号：</th>
                            <td>
                                <input type="text" id="addIdCardNm" name="addIdCardNm" class="form-control" required>
                            </td>
							<th align="right"><font color="red" style="font-size: 15px">*</font>警员编号：</th>
                            <td>
                                <input type="text" id="addPoliceNm" name="addPoliceNm" class="form-control" required>
                            </td>
                        </tr>
                        <tr>
							<th align="right"><font color="red" style="font-size: 15px">*</font>姓名：</th>
                            <td>
                                <input type="text" id="addName" name="addName" class="form-control" required>
                            </td>
                            
                            <th align="right"><font color="red" style="font-size: 15px">*</font>性别：</th>
							 	<td>
                             		<select name="addSex" id="addSex" style="color:black">
										<option value="">请选择</option>
										<option value="男">男</option>
										<option value="女">女</option>
									</select>
						    </td>
                            
                        </tr>
                        <tr>
							<th align="right">备注：</th>
                            <td>
                                <input type="text" id="addRemarkDesc" name="addRemarkDesc" class="form-control">
                            </td>
                            
							 <th align="right"><font color="red" style="font-size: 15px">*</font>所属机构：</th>
                            <td>
                                <select name="addDept" id="addDept" style="color:black">
										<option value="">请选择</option>
								</select>
                            </td>
                            
						</tr>
						
						<tr>
							<th align="right"><font color="red" style="font-size: 15px">*</font>角色名称</th>
                            <td>
                                <select name="addUserRole" id="addUserRole" style="color:black">
										<option value="">请选择</option>
								</select>
                            </td>
						</tr>
                    </table>
                </div>
                <div class="pop-btn">
                    <button class="btn" type="submit">确定</button>
                    <a href="javascript:$.fancybox.close();" class="btn btn-gray" onclick="refresh()">取消</a>
                </div>
            </div>
            </form>
        </div>
		
		<!--查询用户start-->
        <div class="pop-boxxx" data-remodal-id="viewuser">
            <div class="pop-hdd">
                <h3>查看用户</h3>
               <a onclick="closeview()" class="pop-close">关闭</a>
            </div>
            <div class="pop-bdd">
                <div class="pop-innerr">
		<table class="add_UserManage">
					 <tr>
                            <th align="right"><font color="red" style="font-size: 15px">*</font>用户名：</th>
                            <td>
                                <input type="text" id="viewUserName" name="viewUserName" class="form-control" disabled>
                            </td>
							<th align="right"><font color="red" style="font-size: 15px">*</font>密码：</th>
                            <td>
                                <input type="password" id="viewPassword" name="viewPassword" class="form-control" disabled>
                            </td>
                        </tr>
                        <tr>
                            <th align="right"><font color="red" style="font-size: 15px">*</font>身份证号：</th>
                            <td>
                                <input type="text" id="viewIdCardNm" name="viewIdCardNm" class="form-control" disabled>
                            </td>
							<th align="right"><font color="red" style="font-size: 15px">*</font>警员编号：</th>
                            <td>
                                <input type="text" id="viewPoliceNm" name="viewPoliceNm" class="form-control" disabled>
                            </td>
                        </tr>
                        <tr>
							<th align="right"><font color="red" style="font-size: 15px">*</font>姓名：</th>
                            <td>
                                <input type="text" id="viewName" name="viewName" class="form-control" disabled>
                            </td>
                            
                            <th align="right"><font color="red" style="font-size: 15px">*</font>性别：</th>
							 <td>
								<input type="text" id="viewSex" name="viewSex" class="form-control" disabled>
						    </td>
                            
                        </tr>
                        <tr>
							<th align="right">备注：</th>
                            <td>
                                <input type="text" id="viewRemarkDesc" name="viewRemarkDesc" class="form-control" disabled>
                            </td>
                            
							 <th align="right"><font color="red" style="font-size: 15px">*</font>所属机构：</th>
                            <td>
								<input type="text" id="viewDept" name="viewDept" class="form-control" disabled>
                            </td>
                            
						</tr>
						
						<tr>
							<th align="right">角色名称：</th>
                            <td>
								<input type="text" id="viewUserRole" name="viewUserRole" class="form-control" disabled>
                            </td>
                            <th align="right">最后登录时间：</th>
                            <td>
                                <input type="text" id="viewLastLoginTime" name="viewLastLoginTime" class="form-control" disabled>
                            </td>
						</tr>
						
						<tr>
							<th align="right">状态：</th>
                            <td>
                                 <input type="text" id="viewStatus" name="viewStatus" class="form-control" disabled>
                            </td>
                            <th align="right">删除状态：</th>
                            <td>
                                <input type="text" id="viewDeleteStatus" name="viewDeleteStatus" class="form-control" disabled>
                            </td>
						</tr>
						
						<tr>
							<th align="right">创建人：</th>
                            <td>
                                 <input type="text" id="viewCreater" name="viewCreater" class="form-control" disabled>
                            </td>
                            <th align="right">更新人：</th>
                            <td>
                                <input type="text" id="viewUpdater" name="viewUpdater" class="form-control" disabled>
                            </td>
						</tr>
						
						<tr>
							<th align="right">创建时间：</th>
                            <td>
                                 <input type="text" id="viewCreateTime" name="viewCreateTime" class="form-control" disabled>
                            </td>
                            <th align="right">更新时间：</th>
                            <td>
                                <input type="text" id="viewUpdateTime" name="viewUpdateTime" class="form-control" disabled>
                            </td>
						</tr>
			  </table>
			  </div>
			  <div class="pop-btn"><a onclick="closeview()" class="btn">取消</a></div>
            </div>  
        </div>
		<!--查询用户end-->
		
		<div class="pop-boxx" id="edit-UserManageMent"  style="display: none">
            <div class="pop-hdd">
                <h3>编辑用户</h3>
                <a href="javascript:$.fancybox.close();" class="pop-close" onclick="ref()">关闭</a>
            </div>
            <!-- <form action="updateUserManage.do" id="updateformId" method="post"> -->
            <form id="updateformId" onsubmit="updateUserSubmit()">
            <div class="pop-bdd">
                <div class="pop-innerr">
                    <table class="add_UserManage">
                        <tr>
                            <th align="right"><font color="red" style="font-size: 15px">*</font>用户名：</th>
                            <td>
                                <input type="text" id="modifyUserName" name="modifyUserName" class="form-control" disabled required>
                            </td>
							<th align="right"><font color="red" style="font-size: 15px">*</font>密码：</th>
                            <td>
                                <input type="password" id="modifyPassword" name="modifyPassword" class="form-control" required>
                            </td>
                        </tr>
                        <tr>
                            <th align="right"><font color="red" style="font-size: 15px">*</font>身份证号：</th>
                            <td>
                                <input type="text" id="modifyIdCardNm" name="modifyIdCardNm" class="form-control" required>
                            </td>
							<th align="right"><font color="red" style="font-size: 15px">*</font>警员编号：</th>
                            <td>
                                <input type="text" id="modifyPoliceNm" name="modifyPoliceNm" class="form-control" required>
                            </td>
                        </tr>
                        <tr>
							<th align="right"><font color="red" style="font-size: 15px">*</font>姓名：</th>
                            <td>
                                <input type="text" id="modifyName" name="modifyName" class="form-control" required>
                            </td>
                            
                            <th align="right"><font color="red" style="font-size: 15px">*</font>性别：</th>
							 	<td>
                             		<select name="modifySex" id="modifySex">
										<option value="">请选择</option>
										<option value="男">男</option>
										<option value="女">女</option>
									</select>
						    </td>
                            
                        </tr>
                        <tr>
							<th align="right">备注：</th>
                            <td>
                                <input type="text" id="modifyRemarkDesc" name="modifyRemarkDesc" class="form-control">
                            </td>
                            
							 <th align="right"><font color="red" style="font-size: 15px">*</font>所属机构：</th>
                            <td>
                                <select name="modifyDept" id="modifyDept">
										<option value="">请选择</option>
								</select>
                            </td>
                            
						</tr>
						
						<tr>
							<th align="right">角色名称</th>
                            <td>
                                <select name="modifyUserRole" id="modifyUserRole">
										<option value="">请选择</option>
								</select>
                            </td>
                            <th align="right" style="display:none">用户ID</th>
                            <td>
                                <input type="text" id="modifyUserId" name="modifyUserId" class="form-control" style="display:none">
                            </td>
						</tr>
                    </table>
                </div>
                <div class="pop-btn">
                    <button class="btn" type="submit">确定</button>
                    <a href="javascript:$.fancybox.close();" class="btn btn-gray" onclick="ref()">取消</a>
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
		<script src="../js/UserManager.js"></script>
		<script src="../js/jquery.validate.min.js"></script>
    </body>
</html>
