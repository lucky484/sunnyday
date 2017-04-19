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
        <title>角色权限管理</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="../css/normalize.css">
        <link rel="stylesheet" href="../css/main.css">
        <link rel="stylesheet" href="../css/role.css">
        <link rel="stylesheet" href="../css/remodal.css">
    </head>
    <body>
	<div class="page-wrapper">
		<div class="page-con">
			<div class="match-hd">

				<div class="match-title-box">
					系统管理 <span>角色权限管理</span>
				</div>
			</div>


			<div class="match-title-box">
				<div action="" class="form-inline">
					<label for="">角色名称</label> <input type="text" id="roleName"
						class="form-control">
					<button class="btn" href="javascript:void(0)"
						onclick="getRole()">查询</button>
					<a href="#add-RoleManageMent" class="btn fancybox" onclick="addRoleInfo()">增加</a>
				</div>
			</div>


			<div class="match-data">
				<table class="table">
					<thead>
						<tr>
							<th style="text-align:center">角色名称</th>
							<th style="text-align:center">描述</th>
							<th style="text-align:center">创建人</th>
							<th style="text-align:center">创建时间</th>
							<th style="text-align:center">更新人</th>
							<th style="text-align:center">更新时间</th>
							<th style="text-align:center">修改权限</th>
						</tr>
					</thead>
					<tbody id="roleManagement">
					</tbody>
				</table>

				<div class="clearfix">
					<div class="pull-left" id="roleInfoPages">
						<span class="page-info" id="roleInfoCount"></span>
					</div>
					<div class="pull-right">
						<ul class="pagination">
							<li><a onclick="getPage(1)">首页</a></li>
							<li><a onclick="getPrePage()">上一页</a></li>
							<li><input type="text" value="1" id="page"></li>
							<li><a onclick="getNextPage()">下一页</a></li>

							<li><a onclick="getLastPage()">尾页</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>

	 <div class="remodal delete-box" data-remodal-id="deleteConfirm">
		<div class="modal-hd">
			<a href="javascript" data-remodal-action="close" class="modal-close"></a>
		</div>
		<div class="modal-bd" id="changeModalPart">
			<p id="msgTips" style="text-align:center">是否删除该数据？</p>
			<div class="btn-group">
				<button data-remodal-action="confirm" class="btn">是</button>
				<button data-remodal-action="cancel" class="btn btn-gray">否</button>
			</div>
		</div>
	</div>

	<div class="pop-box" id="add-RoleManageMent">
            <div class="pop-hd">
                <h3>增加角色</h3>
                <a href="javascript:$.fancybox.close();" class="pop-close">关闭</a>
            </div>
            <div class="pop-bd">
                <div class="pop-inner">
                	   <table class="add-AreaManageMent" id="addRoleTable">
                        <tr>
                            <th width="15%" align="left">角色名称:</th>
                            <td width="40%">
                                <input type="text" id="addRoleName" name="addRoleName" onblur="checkAddRoleName()"/>
                                </br>
                                <font id="addRoleNameExist" style="color:red;display:none">角色名已经存在</font>
                                <font id="addRoleNameEmpty" style="color:red;display:none">角色名不能为空</font>
                            </td>
							<th width="15%" align="left"> 角色描述:</th>
                            <td>
                                <input type="text"  id="addRoleDesc" name="addRoleDesc" onblur="checkAddRoleDesc()" />
                                </br>
                                <font id="addRoleDescEmpty" style="color:red;display:none">角色描述不能为空</font>
                            </td>
                        </tr>
                        <tr>
                        	<th>&nbsp;</th>
                        </tr>
                        <tr>
                            <th align="left">备注:</th>
                            <td>
                                <input type="text"  id="addRoleRemark" name="addRoleRemark" required>
                            </td>
                            <td>
                            </td>
                        </tr>
                        	<th>&nbsp;</th>
                        <tr>
                            <th width="15%" align="left" >菜单权限:</th>
                            <td width="40%">
								<div id="addRoleTree"></div>
								<font id="addRoleEmpty" style="color:red;display:none">请至少选择一个角色</font>
                            </td>
							<th align="left" style="visible:false"></th>
                            <td>
                            </td>
                           <!--  <th align="left" style="visible:false"> 功能权限:</th>
                            <td>
                                <input type="text"  id="regionCode1" name="regionCode1" required>
                            </td> -->
                        </tr>
                    </table>
                </div>
                <div class="pop-btn">
                    <a href="#" class="btn" onclick="addNewRole()">确定</a>
                    <a href="#" onclick="$.fancybox.close()" class="btn btn-gray" onclick="refresh()">取消</a>
                </div>
            </div>
        </div>


	<div class="pop-boxRole" data-remodal-id="viewRoleInfo">

		<div class="pop-hdd">
			<h3>查看角色</h3>
			<a onclick="closeview()" class="pop-close">关闭</a>
		</div>
		<div class="pop-bdd">
			<div class="pop-innerr">
				<table class="add-UserManageMent">
					<tr>
						<th width="15%" align="left">角色名称:</th>
						<td width="40%"><input style="font-size:14px;" type="text" id="viewRoleName"
							name="viewRoleName" disabled></td>
						<th width="15%" align="left">角色描述:</th>
						<td><input style="font-size:14px;" type="text" id="viewRoleRemarkDesc" name="viewRoleRemarkDesc"
							disabled></td>
					</tr>
					<tr>
						<th>&nbsp;</th>
					</tr>
					<tr>
						<th align="left">备注:</th>
						<td><input style="font-size:14px;" type="text" id="viewRoleDesc"
							name="viewRoleDesc" disabled></td>
						<td></td>
					</tr>
					<th>&nbsp;</th>
					<tr>
						<th width="15%" align="left">菜单权限:</th>
						<td width="40%">
							<div id="roleTreeView"></div>
						</td>
						<th align="left" style="visible: false"></th>
						<td></td>
						<!--  <th align="left" style="visible:false"> 功能权限:</th>
                            <td>
                                <input type="text"  id="regionCode1" name="regionCode1" required>
                            </td> -->
					</tr>
				</table>
			</div>
			<div class="pop-btn">
				<a onclick="closeview()" class="btn">取消</a>
			</div>
		</div>
	</div>

	<div class="pop-box" data-remodal-id="modifyRoleInfo">
		<div class="pop-hdd">
			<h3>修改角色</h3>
			<a onclick="closeModify()" class="pop-close">关闭</a>
		</div>
		<form id="modifyRoleform" method="post">
		<div class="pop-bdd">
			<div class="pop-innerr">
				<table class="add-UserManageMent">
					<tr>
						<th width="15%" align="left">角色名称:</th>
						<td width="40%"><input style="font-size:14px;" type="text" id="modifyRoleName" name="modifyRoleName" onblur="checkModifyRoleName()">
						 </br>
                          <font id="modifyRoleNameExist" style="color:red;display:none">角色名已经存在</font>
                          <font id="modifyRoleNameEmpty" style="color:red;display:none">角色名不能为空</font>
						</td>
						<th width="15%" align="left">角色描述:</th>
						<td><input style="font-size:14px;" type="text" id="modifyRoleDesc" name="modifyRoleDesc" onblur="checkModifyRoleDesc()">
						</br>
						<font id="modifyRoleDescEmpty" style="color:red;display:none">角色描述不能为空</font>
						</td>
					</tr>
					<tr>
						<th>&nbsp;</th>
					</tr>
					<tr>
						<th align="left">备注:</th>
						<td><input style="font-size:14px;" type="text" id="modifyRoleRemark" name="modifyRoleRemark"></td>
						<th align="left" style="">角色ID:</th>
						<td><input style="font-size:14px;" type="text" id="modifyRoleID" name="modifyRoleID" style=""></td>
					</tr>
					<th>&nbsp;</th>
					<tr>
						<th width="15%" align="left">菜单权限:</th>
						<td width="40%">
							<div id="modifyRoleTreeView"></div>
							<!-- <font id="modifyRoleEmpty" style="color:red;display:none">角色名不能为空</font> -->
							<font id="modifyRoleEmpty" style="color:red;display:none">请至少选择一个角色</font>
						</td>
						<th align="left" style="visible: false"></th>
						<td></td>
						<!--  <th align="left" style="visible:false"> 功能权限:</th>
                            <td>
                                <input type="text"  id="regionCode1" name="regionCode1" required>
                            </td> -->
					</tr>
				</table>
			</div>
			
			
			<div class="pop-btn">
				<!-- <a onclick="updateRole()" class="btn">确定</a> -->
				<!-- <a onclick="closeModify()" class="btn">取消</a> -->
				<button class="btn" type="button" onclick="saveModify()">确定</button>
				 <a href="#" onclick="$.fancybox.close()" class="btn btn-gray">取消</a>
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
		<script type="text/javascript" src="../js/remodal.min.js"></script>
		<script src="../js/jquery.validate.min.js"></script>
        <script src="../js/plugins.js"></script>
        <script src="../js/main.js"></script>
        <script type="text/javascript" src="../js/roleManagement.js"></script>
    </body>
</html>

