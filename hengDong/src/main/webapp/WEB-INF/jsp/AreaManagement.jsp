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
        <title>辖区管理</title>
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
                            辖区管理
                            <span>辖区管理</span>
                        </div>
                        <div class="match-title-box">
                            <div action="" class="form-inline">
                                <label for="">辖区编号</label>
                                <input type="text" id="regionCode" class="form-control">
                                <label for="">辖区名称</label>
                                <input type="text" id="regionName" class="form-control">
                                <button class="btn" href="javascript:void(0)" onclick="getRegion()">查询</button>
                                <a href="#add-AreaManageMent" class="btn fancybox" onclick="cleanadd()">增加</a>
                            </div>
                        </div>
                    </div>
                    <div class="match-data">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>辖区编号</th>
                                    <th>辖区名称</th>
                                    <th>级别</th>
                                    <th>上级辖区编号</th>
                                    <th>设备数量</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody id="areaManagement">
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
        <div class="pop-box" id="add-AreaManageMent">
            <div class="pop-hd">
                <h3>增加辖区</h3>
                <a href="javascript:$.fancybox.close();" class="pop-close" onclick="refresh()">关闭</a>
            </div>
            
            <form id="addNewArea" onsubmit="saveAreaManage()">
            <div class="pop-bd">
                <div class="pop-inner">
                    <table class="add_UserManage" id="addNewArea">
                        <tr>
                            <th align="right"><font color="red" style="font-size: 15px">*</font>辖区名称:</th>
                            <td>
                                <input type="text" id="regionName1" name="regionName1" class="form-control">
                            </td>
							<th align="right"><font color="red" style="font-size: 15px">*</font>辖区编号:</th>
                            <td>
                                <input type="text"  id="regionCode1" name="regionCode1" class="form-control">
                            </td>
                        </tr>
                        <tr>
                            <th align="right">上级辖区编号:</th>
                            <td>
                                <input type="text"  id="preRegionCode1" name="preRegionCode1" class="form-control">
                                </br>
                                <font id="preRegionCodeCheck" style="color:red;display:none"></font>
                            </td>
                            <th align="right"></font>级别:</th>
                            <td>
                                <input type="text" id="regionLevel1" name="regionLevel1" class="form-control">
                                </br>
                                <font id="regionLevelCheck" style="color:red;display:none"></font>
                            </td>
                        </tr>
						<tr>
                            <th align="right">辖区描述:</th>
                            <td>
                                <input type="text" id="description" name="description" class="form-control">
                            </td>
                            <th align="right">备注:</th>
                            <td>
                                <input type="text" id="remarkDesc" name="remarkDesc" class="form-control">
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="pop-btn">
                    <button class="btn" type="submit">确定</button>
                    <a href="javascript:$.fancybox.close();" onclick="refresh()" class="btn btn-gray">取消</a>
                </div>
               
            </div>
            </form>
        </div>
        <div class="pop-boxxx" data-remodal-id="viewRegionInfo">
		<div class="pop-hdd">
                <h3>查看辖区</h3>
                <a onclick="closeview()" class="pop-close">关闭</a>
            </div>
		<div class="pop-bdd">
		<div class="pop-innerr">
			<table class="add_UserManage">
				<tbody>
					<tr>
						<th align="right">辖区编号：</th>
						<td><input type="text" id="rCode" class="form-control" disabled></td>
						<th align="right">辖区名称：</th>
						<td><input type="text" id="rName" class="form-control" disabled></td>
					</tr>
					<tr>
						<th align="right">级别：</th>
						<td><input type="text" id="rLevel" class="form-control" disabled></td>
						<th align="right">上级辖区编号：</th>
						<td><input type="text" id="preRcode" class="form-control" disabled></td>
					</tr>
					<tr>
						<th align="right">辖区描述：</th>
						<td><input type="text" id="desc" class="form-control" disabled></td>
						<th align="right">备注：</th>
						<td><input type="text" id="remk" class="form-control" disabled></td>
					</tr>
					<tr>
						<th align="right">创建人：</th>
						<td><input type="text" id="cn" class="form-control" disabled></td>
						<th align="right">创建时间：</th>
						<td><input type="text" id="ct" class="form-control" disabled></td>
					</tr>
					<tr>
						<th align="right">更新时间：</th>
						<td><input type="text" id="ut" class="form-control" disabled></td>
						<th align="right">更新人：</th>
						<td><input type="text" id="un" class="form-control" disabled></td>
					</tr>
					<tr>
						<th align="right">删除状态：</th>
						<td><input type="text" id="del" class="form-control" disabled></td>
					</tr>
					<tr>
						<td colspan="6" align="center" style="padding-top: 30px;"><input
							onclick="closeviewRegion()" class="btn" type="button" value="取消"></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	</div>
        <div class="pop-box" id="edit-AreaManageMent">
            <div class="pop-hd">
                <h3>编辑辖区</h3>
                <a href="javascript:$.fancybox.close();" class="pop-close" onclick="ref()">关闭</a>
            </div>
            <form id="editformReg" onsubmit="modifyRegionSubmit()">
            <div class="pop-bd">
                <div class="pop-inner">
                    <table class="add_UserManage">
                        <tr>
                            <th align="right"><font color="red" style="font-size: 15px" disabled>*</font>辖区编号:</th>
                            <td>
                                <input type="text"  id="rCode1" name="rCode1" class="form-control" required>
                            </td>
							<th align="right"><font color="red" style="font-size: 15px">*</font>辖区名称:</th>
                            <td>
                                <input type="text" id="rName1" name="rName1" class="form-control">
                            </td>
                        </tr>
                        <tr>
                        	<th align="right"></font>上级辖区编号:</th>
                            <td>
                                <input type="text" id="preRcode1" name="preRcode1" class="form-control">
                            </td>
                            <th align="right"></font>级别:</th>
                            <td>
                                <input type="text" id="rLevel1" name="rLevel1" class="form-control">
                            </td>
                        </tr>
						<tr>
							<th align="right">辖区描述:</th>
                            <td>
                                <input type="text" id="desc1" name="desc1" class="form-control">
                            </td>
                            <th align="right">备注:</th>
                            <td>
                                <input type="text" id="remk1" name="remk1" class="form-control">
                            </td>
						</tr>
						<tr>
<!--                    <th align="right">更新人:</th>
                            <td>
                                <input type="text" id="un1" name="un1" required>
                            </td>   -->         
							<th align="right" style="display: none">辖区ID:</th>
                            <td>
                                <input type="text" id="rID" name="rID" style="display: none">
                            </td>
						</tr>
                    </table>
                </div>
                <div class="pop-btn">
                    <button class="btn" type="submit">确定</button>
                    <a href="javascript:$.fancybox.close();" onclick="ref()" class="btn btn-gray">取消</a>
                </div>
            </div>
            </form>
        </div>
        <div class="remodal delete-box" data-remodal-id="panduan">
            <div class="modal-hd">
               <a href="javascript" data-remodal-action="close" class="modal-close"></a> 
            </div>
            <div class="modal-bd" id="judge">
            </div>  
        </div>
        
        <!-- 删除tips -->
        <div class="remodal delete-box" data-remodal-id="deleteConfirm">
			<div class="modal-bd" id="showMsgTips">
				<p style="text-align:center" id="deleteTips">是否确定删除信息</p>
				<div class="btn-group">
					<button data-remodal-action="confirm" class="btn">是</button>
					<button data-remodal-action="cancel" class="btn btn-gray">否</button>
				</div>
			</div>
		</div>
		
		<!--提示tips-->
		<div class="remodal delete-box" data-remodal-id="tip_info">
            <div class="modal-bd" id="model_info" style="font-weight: normal;">
            </div>
        </div>
        <script src="../js/vendor/jquery-1.11.3.min.js"></script>
		<script src="../js/plugins.js"></script>
		<script src="../js/main.js"></script>
		<script type="text/javascript" src="../js/AreaManagement.js"></script>
		<script type="text/javascript" src="../js/remodal.min.js"></script>
		<script src="../js/jquery.validate.min.js"></script>
        <!-- <script src="..js/vendor/jquery-1.11.3.min.js"></script>
        <script src="..js/plugins.js"></script>
        <script src="..js/main.js"></script>
        <script src="..js/AreaManagement.js"></script> -->
    </body>
</html>
