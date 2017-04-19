<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html class="no-js" lang="">
    <head>
        <meta charset="utf-8">
        <!-- 无缓存模式 -->
		<meta http-equiv="pragma" CONTENT="no-cache"> 
		<meta http-equiv="Cache-Control" CONTENT="no-cache, must-revalidate"> 
		<meta http-equiv="expires" CONTENT="0"> 
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>设备管理</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="../css/normalize.css">
        <link rel="stylesheet" href="../css/main.css">
        <link rel="stylesheet" href="../css/remodal.css">
    </head>
    <body>
            <div class="page-wrapper">
                <div class="page-con">
                    <div class="match-hd">
                        <div class="match-title-box">
                            设备管理
                        </div>
                        <div class="match-title-box">
                            <div class="form-inline">
                                <label for="">设备编号</label>
                                <input type="text" id="deviceCode" class="form-control">
                                <label for="">设备地点</label>
                                <input type="text" id="collectSite" class="form-control">
                                <button class="btn" onclick="drawPage()">查询</button>
                                <a href="#add-device" class="btn fancybox" onclick="cleanInsertFrom();">增加</a>
                            </div>
                        </div>
                    </div>
                    <div class="match-data">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>设备编号</th>
                                    <th>设备类型</th>
                                    <th>启用状态</th>
                                    <th>部署地点</th>
                                    <th>部署日期</th>
                                    <th>人脸设备的设备型号</th>
                                    <th>人脸设备的镜头型号</th>
                                    <th>人脸设备的IP</th>
                                    <th>身份证设备的序列号</th>
                                    <th>操作</th>
								</tr>
                            </thead>
                            <tbody id='e_tbody'>
                            </tbody>
                        </table>
                       <div class="clearfix">
							<div class="pull-left">
								<span class="page-info">共<span id="e_total">0</span>条 每页<span
									id="e_pageSize">10</span>条 页次：<span id="e_page">0</span>/<span
									id="e_pages">0</span></span>
							</div>
							<div class="pull-right">
								<ul class="pagination">
									<li><a onclick="homePage()">首页</a></li>
									<li><a onclick="prePage()">上一页</a></li>
									<li><input type="text" value="1"></li>
									<li><a onclick="nextPage()">下一页</a></li>
									<li><a onclick="lastPage()">尾页</a></li>
								</ul>
							</div>
						</div>
                    </div>
                </div>
            </div>
            <!-- 弹出样式 -->
            <div class="pop-box" id="add-device">
            <div class="pop-hd">
                <h3>增加设备</h3>
                <a href="javascript:$.fancybox.close();" class="pop-close" onclick="cleanClass();">关闭</a>
            </div>
            <div class="pop-bd">
            	<form id="e_insert_form" >
	                <div class="pop-inner">
	                    <table class="edit-device-li">
	                        <tr>
	                         	<th width="25%"><font color="red" style="font-size: 15px">*</font>设备编号：</th>
	                            <td width="25%">
	                                <input id="i_deviceCode" name="i_deviceCode" type="text" class="form-control" maxlength="32" value="" required>
	                            </td>
	                            <th width="25%">设备名称：</th>
	                            <td width="25%">
	                                <input id="i_deviceName" name="i_deviceName" type="text" maxlength="22" class="form-control" value="">
	                            </td>
	                        </tr>
	                        <tr>
	                         	<th>设备类型：</th>
	                            <td>
	                                <select id="i_deviceType" name="i_deviceType" class="form-control">
	                                    <option value="移动">移动</option>
	                                    <option value="固定">固定</option>
	                                </select>
	                            </td>
	                            <th>设备地点：</th>
	                            <td>
	                                <input id="i_deviceSite" name="i_deviceSite" type="text" maxlength="22" class="form-control" value="">
	                            </td>
	                        </tr>
	                        
	                        <tr>
	                        	<th>启用状态：</th>
	                            <td>
	                                <select id="i_enableStatus" name="i_enableStatus" class="form-control" >
	                                    <option value="0">启用</option>
	                                    <option value="1">停用</option>
	                                </select>
	                            </td>
	                            <th>部署日期：</th>
	                            <td>
	                                <input id="i_installDate" name="i_installDate" type="text" class="form-control" >
	                            </td>
	                        </tr>
	                        <tr>
	                        	<th>人脸设备型号：</th>
	                            <td>
	                                <input id="i_faceDvModel" name="i_faceDvModel" maxlength="22" type="text" class="form-control" >
	                            </td>
	                            <th>人脸设备镜头型号：</th>
	                            <td>
	                                <input id="i_faceDvLensModel" name="i_faceDvLensModel" maxlength="22" type="text" class="form-control" >
	                            </td>
	                        </tr>
	                        <tr>
	                        	<th>人脸设备序列号：</th>
	                            <td>
	                                <input id="i_faceDvNo" name="i_faceDvNo" maxlength="22" type="text" class="form-control" >
	                            </td>
	                            <th>人脸采集间隔时间：</th>
	                            <td>
	                                <input id="i_faceDvCollInterval" maxlength="4" name="i_faceDvCollInterval" type="text" class="form-control" >
	                            </td>
	                        </tr>
	                        <tr>
	                        	<th>人脸设备IP：</th>
	                            <td>
	                                <input id="i_faceDvIp" name="i_faceDvIp" type="text" maxlength="22" class="form-control" >
	                            </td>
	                            <th>身份证设备序列号：</th>
	                            <td>
	                                <input id="i_idCardDvNo" name="i_idCardDvNo" type="text" maxlength="22" class="form-control" >
	                            </td>
	                        </tr>
	                        <tr>
	                        	<th>部署辖区ID：</th>
	                            <td>
	                                <select name="i_regionCode" id="i_regionCode"  class="form-control" >
	                                    <option value="0">崇安区</option>
	                                    <option value="1">滨湖区</option>
	                                </select>
	                            </td>
	                            <th>运行状态：</th>
	                            <td>
	                                <select name="i_runningState" id="i_runningState" class="form-control" >
	                                    <option value="0">正常</option>
	                                    <option value="1">异常</option>
	                                </select>
	                            </td>
	                        </tr>
	                        <tr>
	                        	<th>视频设备ID：</th>
	                            <td>
	                                <input id="i_videoId" name="i_videoId" type="text" maxlength="22" class="form-control" >
	                            </td>
	                            <th>视频设备URL：</th>
	                            <td>
	                                <input id="i_videoIp" name="i_videoIp" type="text" class="form-control" maxlength="22">
	                            </td>
	                        </tr>
	                        
	                        <tr  class="edit_comments">
	                        	<th>备注：</th>	
	                            <td colspan="3">
	                               <textarea id="i_remarkDesc" name="i_remarkDesc" maxlength="320" style="margin:0;width:100%;height:50px;outline:1px;border-radius:4px;outline-color: #222;"></textarea>
	                            </td>
	                        </tr>
	                    </table>
	                </div>
	                <div class="pop-btn" >
	                	<a href="#" class="btn" onclick="e_insert();">确定</a>
	                   <!--  <button class="btn" type="submit">确定</button> -->
	                    <a href="javascript:;" class="btn btn-gray" onclick="e_close();cleanClass();">取消</a>
	                </div>
                </form>
            </div>
        </div>
        <div class="pop-box" id="edit-device">
            <div class="pop-hd">
                <h3>编辑设备</h3>
                <a href="javascript:$.fancybox.close();" class="pop-close">关闭</a>
            </div>
            <div class="pop-bd">
            	<form id="e_update_form">
	                <div class="pop-inner">
	                    <table class="edit-device-li">
	                        <tr>
	                        	<th width="25%"><font color="red" style="font-size: 15px">*</font>设备编号：</th>
	                            <td width="25%">
	                                <input id="u_deviceCode" name="u_deviceCode" type="text" class="form-control" value="IPC001" maxlength="32">
	                            </td>
	                            <th width="25%">设备名称：</th>
	                            <td width="25%">
	                                <input id="u_deviceName" name="u_deviceName" type="text" class="form-control" value="IPC001" maxlength="22">
	                            </td>
	                        </tr>
	                        <tr>
	                        	<th>设备类型：</th>
	                            <td>
	                                <select name="" id="u_deviceType" name="u_deviceType" class="form-control">
	                                    <option value="移动">移动</option>
	                                    <option value="固定">固定</option>
	                                </select>
	                            </td>
	                            <th>设备地点：</th>
	                            <td>
	                                <input id="u_deviceSite" name="u_deviceSite" type="text" class="form-control" value="无锡市滨湖区火车站" maxlength="22">
	                            </td>
	                        </tr>
	                        
	                        <tr>
	                        	<th>启用状态：</th>
	                            <td>
	                                <select name="u_enableStatus" id="u_enableStatus" class="form-control" >
	                                    <option value="0">启用</option>
	                                    <option value="1">停用</option>
	                                </select>
	                            </td>
	                            <th>部署日期：</th>
	                            <td>
	                                <input id="u_installDate" name="u_installDate" type="text" class="form-control" maxlength="22">
	                            </td>
	                        </tr>
	                         <tr>
	                        	<th>人脸设备型号：</th>
	                            <td>
	                                <input id="u_faceDvModel" name="u_faceDvModel" type="text" class="form-control" maxlength="22">
	                            </td>
	                            <th>人脸设备镜头型号：</th>
	                            <td>
	                                <input id="u_faceDvLensModel" name="u_faceDvLensModel" type="text" class="form-control" maxlength="22">
	                            </td>
	                        </tr>
	                        <tr>
	                        	<th>人脸设备序列号：</th>
	                            <td>
	                                <input id="u_faceDvNo" name="u_faceDvNo" type="text" class="form-control" maxlength="22">
	                            </td>
	                            <th>人脸采集间隔时间：</th>
	                            <td>
	                                <input id="u_faceDvCollInterval" name="u_faceDvCollInterval" type="text" class="form-control" maxlength="4">
	                            </td>
	                        </tr>
	                        <tr>
	                        	<th>人脸设备IP：</th>
	                            <td>
	                                <input id="u_faceDvIp" name="u_faceDvIp" type="text" class="form-control" maxlength="22" >
	                            </td>
	                            <th>身份证设备序列号：</th>
	                            <td>
	                                <input id="u_idCardDvNo" name="u_idCardDvNo" type="text" class="form-control" maxlength="22">
	                            </td>
	                        </tr>
	                        <tr>
	                        	<th>部署辖区ID：</th>
	                            <td>
	                                <select name="u_regionCode" id="u_regionCode" class="form-control" >
	                                    <option value="0">崇安区</option>
	                                    <option value="1">滨湖区</option>
	                                </select>
	                            </td>
	                            <th>运行状态：</th>
	                            <td>
	                                <select name="u_runningState" id="u_runningState" class="form-control" >
	                                    <option value="0">正常</option>
	                                    <option value="1">异常</option>
	                                </select>
	                            </td>
	                        </tr>
	                        <tr>
	                        	<th>视频设备ID：</th>
	                            <td>
	                                <input id="u_videoId" name="u_videoId" type="text" class="form-control" maxlength="22">
	                            </td>
	                            <th>视频设备URL：</th>
	                            <td>
	                                <input id="u_videoIp" name="u_videoIp" type="text" class="form-control" maxlength="22">
	                            </td>
	                        </tr>
	                        
	                        <tr  class="edit_comments">
	                        	<th>备注：</th>	
	                            <td colspan="3">
	                               <textarea id="u_remarkDesc" name="u_remarkDesc" maxlength="320" style="margin:0;width:100%;height:50px;outline:1px;border-radius:4px;outline-color: #222;"></textarea>
	                            </td>
	                        </tr>
	                    </table>
	                </div>
	                <div class="pop-btn" >
	                   <!--  <button class="btn" type="submit">确定</button> -->
	                    <a href="#" class="btn" onclick="e_update()">确定</a>
	                    <a href="#" class="btn btn-gray" onclick="e_close()">取消</a>
	                    <input id="dev_hid_id" type="hidden" value="" />
	                </div>
                </form>
            </div>
        </div>
        
        <div class="pop-box" id="look-device">
            <div class="pop-hd">
                <h3>查看设备</h3>
                <a href="javascript:$.fancybox.close();" class="pop-close">关闭</a>
            </div>
            <div class="pop-bd">
                <div class="pop-inner">
                    <table class="edit-device-li">
                        <tr>
                        	<th width="25%"><font color="red" style="font-size: 15px">*</font>设备编号：</th>
                            <td width="25%">
                                <input id="l_deviceCode" type="text" class="form-control" value="IPC001" disabled="disabled">
                            </td>
                            <th width="25%">设备名称：</th>
                            <td width="25%">
                                <input id="l_deviceName" type="text" class="form-control" value="IPC001" disabled="disabled">
                            </td>
                        </tr>
                        <tr>
                        	<th>设备类型：</th>
                            <td>
                                <select name="" id="l_deviceType" class="form-control" disabled="disabled">
                                    <option value="移动">移动</option>
                                    <option value="固定">固定</option>
                                </select>
                            </td>
                            <th>设备地点：</th>
                            <td>
                               <input id="l_deviceSite" type="text" class="form-control" value="无锡市滨湖区火车站" disabled="disabled">
                            </td>
                        </tr>
                        <tr>
                        	<th>启用状态：</th>
                            <td>
                                <select name="" id="l_enableStatus" class="form-control" disabled="disabled">
                                    <option value="0">启用</option>
                                    <option value="1">停用</option>
                                </select>
                            </td>
                            <th>部署日期：</th>
                            <td>
                                <input id="l_installDate" type="text" class="form-control" disabled="disabled">
                            </td>
                        </tr>
                         <tr>
                        	<th>人脸设备型号：</th>
                            <td>
                                <input id="l_faceDvModel" type="text" class="form-control" disabled="disabled">
                            </td>
                            <th>人脸设备镜头型号：</th>
                            <td>
                                <input id="l_faceDvLensModel" type="text" class="form-control" disabled="disabled">
                            </td>
                        </tr>
                        <tr>
                        	<th>人脸设备序列号：</th>
                            <td>
                                <input id="l_faceDvNo" type="text" class="form-control" disabled="disabled">
                            </td>
                            <th>人脸采集间隔时间：</th>
                            <td>
                                <input id="l_faceDvCollInterval" type="text" class="form-control" disabled="disabled">
                            </td>
                        </tr>
                        <tr>
                        	<th>人脸设备IP：</th>
                            <td>
                                <input id="l_faceDvIp" type="text" class="form-control" disabled="disabled">
                            </td>
                            <th>身份证设备序列号：</th>
                            <td>
                                <input id="l_idCardDvNo" type="text" class="form-control" disabled="disabled">
                            </td>
                        </tr>
                        <tr>
                        	<th>部署辖区ID：</th>
                            <td>
                                <select name="" id="l_regionCode" class="form-control" disabled="disabled">
                                    <option value="0">崇安区</option>
                                    <option value="1">滨湖区</option>
                                </select>
                            </td>
                            <th>运行状态：</th>
                            <td>
                                <select name="" id="l_runningState" class="form-control" disabled="disabled">
                                    <option value="0">正常</option>
                                    <option value="1">异常</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                        	<th>视频设备ID：</th>
                            <td>
                                <input id="l_videoId" type="text" class="form-control" disabled="disabled">
                            </td>
                            <th>视频设备URL：</th>
                            <td>
                                <input id="l_videoIp" type="text" class="form-control" disabled="disabled">
                            </td>
                        </tr>
                        <tr  class="edit_comments">
                        	<th>备注：</th>	
                            <td colspan="3">
                               <textarea id="l_remarkDesc" style="margin:0;width:100%;height:50px;outline:1px;border-radius:4px;outline-color: #222;" disabled="disabled"></textarea>
                            </td>
	                    </tr>
                    </table>
                </div>
                <div class="pop-btn" >
                    <a href="#" class="btn btn-gray" onclick="e_close()">取消</a>
                </div>
            </div>
        </div>
        
        <!-- 删除弹框 -->
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
		
        
        <!-- 删除tips -->
        <div class="remodal delete-box" data-remodal-id="deleteConfirm">
			<div class="modal-bd">
				<p>您真的要永久删除该设备？</p>
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
        <script src="../js/eutil.js"></script>
        <script src="../js/remodal.min.js"></script>
        <script src="../js/jquery.validate.min.js"></script>
        <script src="../js/jquery.datetimepicker.full.min.js"></script>
        <script>
	        $(function(){
		        // 模态框的初始化
	            $('.fancybox').fancybox();
				// 时间控件初始化
	            $('#i_installDate').datetimepicker({
	                  format:'Y/m/d',
	                  timepicker:false
	            });
	            $('#u_installDate').datetimepicker({
	                  format:'Y/m/d',
	                  timepicker:false
	            });

				// 辖区的初始化
	            initRegionList();

	        });

	        // 查询所有辖区
	        function initRegionList(){
	        	$.post("../ipc/queryRegions.do?", function(data) {
		        	// {REGIONCODE: "132", REGIONID: 11, REGIONNAME: "新区"}
		        	regions = data.regions;
		        	var i_region ='';
		        	for(var item in data.regions){
		        		var region = regions[item];
		        		i_region+='<option value="'+ region.REGIONCODE +'">'+ region.REGIONNAME +'</option>';
			        }
		        	$('#i_regionCode').html(i_region);
		        	$('#u_regionCode').html(i_region);
		        	$('#l_regionCode').html(i_region);
				});
		    }

	        function e_close(){
	        	$.fancybox.close();
		    }
            function getdate(id) {
                  var today = new Date();
                  var y = today.toLocaleDateString();
                  var h = today.getHours();
                  var m = today.getMinutes();
                  var s = today.getSeconds();
                  if (s < 10) {
                        s = "0" + s;
                  }
                  if (m < 10) {
                        m = "0" + m;
                  }
                  $(id).text(y + " " +h + " : " + m + " : " + s);
                  setTimeout(function() {
                        getdate(id)
                  }, 500);
            }
            $(function() {
    			getdate('#curr-time');

    			//左侧导航
    			$("#site-menu").metisMenu();
    			drawPage();
    		});

            // 弹出框赋值
    		function e_edit(id){
    			 console.log(temp); 
    			var device ;
    			for(var item in temp) {
    				if(id == temp[item].deviceId){
        				device = temp[item];
        				break;
        			}
    			}
    			/* debugger; */
    			$("#u_deviceCode").val(device.deviceCode);
				$("#u_deviceName").val(device.deviceName);
				$("#u_deviceType").val(device.deviceType);
				// 部署地点
				$("#u_deviceSite").val(device.installSite);
				$("#u_enableStatus").val(device.enableStatus);
				$("#u_installDate").val(device.installDateStr);
				$("#u_faceDvNo").val(device.faceDvNo);
				$("#u_faceDvModel").val(device.faceDvModel);
				$("#u_faceDvLensModel").val(device.faceDvLensModel);
				$("#u_faceDvCollInterval").val(device.faceDvCollInterval);
				$("#u_faceDvIp").val(device.faceDvIp);
				$("#u_faceDvURL").val(device.faceDvURL);
				$("#u_idCardDvNo").val(device.idCardDvNo);
				$("#u_videoId").val(device.videoId);
				$("#u_videoIp").val(device.videoIp);
				$("#u_regionCode").val(device.regionCode);
				$("#u_runningState").val(device.runningState);
				$("#u_remarkDesc").val(device.remarkDesc);
				$("#dev_hid_id").val(id);
        	}

    		 // 弹出框赋值
    		function e_look(id){
    			var device ;
    			for(var item in temp) {
    				if(id == temp[item].deviceId){
        				device = temp[item];
        				break;
        			}
    			}
    			$("#l_deviceCode").val(device.deviceCode);
				$("#l_deviceName").val(device.deviceCode);
				$("#l_deviceType").val(device.deviceType);
				// 部署地点
				$("#l_deviceSite").val(device.installSite);
				$("#l_enableStatus").val(device.enableStatus);
				$("#l_installDate").val(device.installDateStr);
				$("#l_faceDvNo").val(device.faceDvNo);
				$("#l_faceDvModel").val(device.faceDvModel);
				$("#l_faceDvLensModel").val(device.faceDvLensModel);
				$("#l_faceDvCollInterval").val(device.faceDvCollInterval);
				$("#l_faceDvIp").val(device.faceDvIp);
				$("#l_faceDvURL").val(device.faceDvURL);
				$("#l_idCardDvNo").val(device.idCardDvNo);
				$("#l_videoId").val(device.videoId);
				$("#l_videoIp").val(device.videoIp);
				$("#l_regionCode").val(device.regionCode);
				$("#l_runningState").val(device.runningState);
				$("#l_remarkDesc").val(device.remarkDesc);
        	}

            // 插入一条设备信息
    		function e_insert(){
        		
        		if($("#e_insert_form").valid()){
	        		var device = {
	        				deviceCode  :$("#i_deviceCode").val(),
	        				deviceName  :$("#i_deviceName").val(),
	        				deviceType  :$("#i_deviceType").val(),
	        				installSite :$("#i_deviceSite").val(),
	        				enableStatus:$("#i_enableStatus").val(),
	        				installDateStr :$("#i_installDate").val(),
	        				faceDvNo    :$("#i_faceDvNo").val(),
	        				faceDvModel :$("#i_faceDvModel").val(),
	        				faceDvLensModel:$("#i_faceDvLensModel").val(),
	        				faceDvCollInterval:$("#i_faceDvCollInterval").val(),
	        				faceDvIp    :$("#i_faceDvIp").val(),
	        				faceDvIp    :$("#i_faceDvIp").val(),
	        				faceDvURL   :$("#i_faceDvURL").val(),
	        				idCardDvNo  :$("#i_idCardDvNo").val(),
	        				videoId     :$("#i_videoId").val(),
	        				videoIp     :$("#i_videoIp").val(),
	        				regionCode  :$("#i_regionCode").val(),
	        				runningState:$("#i_runningState").val(),
	        				remarkDesc  :$("#i_remarkDesc").val()
	                };
	    			$.ajax({  
	                    url : '../ipc/insertEquip.do',  
	                    type : "POST",  
	                    datatype:"json", 
	                    contentType: "application/json; charset=utf-8",  
	                    data : JSON.stringify(device),  
	                    success : function(data, stats) {
	                        if(data.status == '1'){
		                        drawPage();
		                       /*  $("#model_info").html('<p>'+'新增成功!'+'</p>');
		                        $('[data-remodal-id="tip_info"]').remodal().open();
								$.fancybox.close(); */
	                        }
	                        $.fancybox.close();
	                        /* else{
	                        	$("#model_info").html('<p>'+data.msg+'</p>');
	                        } */
	                        
	                    }
	                }); 
        		}
        	}

        	function cleanInsertFrom (){
        		$("#i_deviceCode").val('');
				$("#i_deviceName").val('');
				$("#i_deviceType").val('');
				$("#i_deviceSite").val('');
				$("#i_enableStatus").val('');
				$("#i_installDate").val('');
				$("#i_faceDvNo").val('');
				$("#i_faceDvModel").val('');
				$("#i_faceDvLensModel").val('');
				$("#i_faceDvCollInterval").val('');
				$("#i_faceDvIp").val('');
				$("#i_faceDvIp").val('');
				$("#i_faceDvURL").val('');
				$("#i_idCardDvNo").val('');
				$("#i_videoId").val('');
				$("#i_videoIp").val('');
				$("#i_regionCode").val('');
				$("#i_runningState").val('');
				$("#i_remarkDesc").val('');
            }
        	
    		// 更新一条设备信息
    		function e_update(){
        		
        		if($("#e_update_form").valid()){
	    			var deviceId = $("#dev_hid_id").val();
					var u_device = {
							deviceId   :deviceId,
							deviceCode  :$("#u_deviceCode").val(),
	        				deviceName  :$("#u_deviceName").val(),
	        				deviceType  :$("#u_deviceType").val(),
	        				installSite :$("#u_deviceSite").val(),
	        				enableStatus:$("#u_enableStatus").val(),
	        				installDateStr :$("#u_installDate").val(),
	        				faceDvNo    :$("#u_faceDvNo").val(),
	        				faceDvModel :$("#u_faceDvModel").val(),
	        				faceDvLensModel:$("#u_faceDvLensModel").val(),
	        				faceDvCollInterval:$("#u_faceDvCollInterval").val(),
	        				faceDvIp    :$("#u_faceDvIp").val(),
	        				faceDvIp    :$("#u_faceDvIp").val(),
	        				faceDvURL   :$("#u_faceDvURL").val(),
	        				idCardDvNo  :$("#u_idCardDvNo").val(),
	        				videoId     :$("#u_videoId").val(),
	        				videoIp     :$("#u_videoIp").val(),
	        				regionCode  :$("#u_regionCode").val(),
	        				runningState:$("#u_runningState").val(),
	        				remarkDesc  :$("#u_remarkDesc").val()
	                };
					$.ajax({  
	                    url : '../ipc/updateEquip.do',  
	                    type : "POST",  
	                    datatype:"json", 
	                    contentType: "application/json; charset=utf-8",  
	                    data : JSON.stringify(u_device),  
	                    success : function(data, stats) {
	                        if(data.status == '1'){
								drawPage();
								/* $("#model_info").html('<p>'+'修改成功!'+' ！</p>'); */
	                        }
	                        $.fancybox.close();
	                    }
	                }); 
        		}
        	}

        	 var temp;

        	 // 弹框
	   		 function e_delete(deviceId) {
	   			$('[data-remodal-id="deleteConfirm"]').remodal().open();
            	$('[data-remodal-action="confirm"]').click(function() {
            		$.post("deleteEquip.do?deviceId=" + deviceId, function(data) {
            			drawPage();
            			/* $("#model_info").html('<p>删除成功 ！</p>');
		        		$('[data-remodal-id="tip_info"]').remodal().open(); */
            		});
            	})
					
	   	     }

        	function formatEnableStatus(data){
            	if(data == '0'){
                	return '启用';
                }else if(data == '1'){
                    return '停用';
                }else{
                    return '—';
                }
            }
    		function drawPage(page) {
    			if (!page) {
    				page = 1;
    			}
    			var pageSize = $("#e_pageSize").html();
    			var deviceCode = $("#deviceCode").val();
    			var collectSite = $("#collectSite").val();
    			/* %u5730%u94C1 */
    			/* %E5%9C%B0%E9%93%81 */
    			$.post("../ipc/queryEquipsByPage.do?page=" + page + "&pageSize=" + pageSize
    					+ "&deviceCode=" + deviceCode + "&collectSite="
    					+ encodeURI(collectSite), function(data) {
    				var list = data.devices;
    				temp = list;
    				var tr = '';
    				if(!list.length){
    					tr = "<tr><td colspan='10'>暂无数据！</td></tr>";
        			}else{
	    				for (var i = 0; i < list.length; i++) {
	        				var deviceId = list[i].deviceId;
	    					// 设备编号（唯一的）
	    					var deviceCode = list[i].deviceCode;
	    					// 设备类型（移动；固定）
	    					var deviceType = list[i].deviceType;
	    					// 启用状态(0-启用；1-停用)
	    					var enableStatus = list[i].enableStatus;
	    					// 部署地点
	    					var collectSite = list[i].installSite;
	    					// 人脸设备的镜头型号
							var faceDvCollInterval = list[i].faceDvCollInterval;
	    					// 人脸设备的IP
	    					var faceDvIp = list[i].faceDvIp;
	    					// 人脸设备的视频URL
	    					var faceDvUrl = list[i].faceDvUrl;
	    					// 人脸设备的设备型号
	    					var faceDvModel = list[i].faceDvModel;
	    					// 人脸设备的设备型号
	    					var faceDvLensModel = list[i].faceDvLensModel;
	    					// 人脸设备的序列号（唯一的）
	    					var faceDvNo = list[i].faceDvNo;
	    					// 身份证设备的序列号（唯一的）
	    					var idCardDvNo = list[i].idCardDvNo;
	    					// 部署日期
	    					var installDateStr = list[i].installDateStr;
	    					// 部署辖区ID
	    					var regionCode = list[i].regionCode;
	    					// 备注
	    					var remarkDesc = list[i].remarkDesc;
	    					// 运行状态（0-正常；1-异常）
	    					var runningState = list[i].runningState;
	    					// 视频设备（球机）的唯一标识ID
	    					var videoId = list[i].videoId;
	    					// 视频设备（球机）的URL
	    					var videoIp = list[i].videoIp;
	    					tr += '<tr>'+
				                    '<td id="'+deviceId+'_code">'+
				                        checkData(deviceCode)+
				                    '</td>'+
				                    '<td id="'+deviceId+'_type">'+  checkData(deviceType)+'</td>'+
				                    '<td>'+ formatEnableStatus(enableStatus) +'</td>'+
				                    '<td id="'+deviceId+'_site">'+ checkData(collectSite) +'</td>'+
				                    '<td>'+ checkData(installDateStr) +'</td>'+
				                    '<td>'+ checkData(faceDvModel) +'</td>'+
				                    '<td>'+ checkData(faceDvLensModel) +'</td>'+
				                    '<td>'+ checkData(faceDvIp) +'</td>'+
				                    '<td>'+ checkData(idCardDvNo) +'</td> '+
				                    '<td>'+ 
				                    '<a href="#edit-device" class="table-link fancybox" onclick="e_edit('+ deviceId +')">编辑</a>' +
	                                '<a href="#look-device" class="table-link fancybox" onclick="e_look('+ deviceId +')">查看</a>' +
	                                '<a href="#" class="table-link c-danger" onclick="e_delete('+ deviceId +')">删除</a>' +
				                    '</td>'+ 
				                '</tr>';
	    				}
        			}
    				$("#e_tbody").html(tr);
    				$("#e_page").html(data.pageInfo.currentPage);
    				$("#e_pages").html(data.pageInfo.totalPage);
    				$("#e_total").html(data.pageInfo.totalCount);
    			});
    		};

    		//下面开始做校验
    		    $(function() {
            	//判断中文
				$.validator.methods.checkname = function (value,element,param){
					var reg = "^[\u4e00-\u9fa5]+$";
					var r = value.match(reg);
					if(r){
						return true;
					}else{
						return false;
					}
				};
				//判断数字
				$.validator.methods.checknumber = function (value,element,param){
					var reg = "^[0-9]+$";
					var r = value.match(reg);
					if(r){
						return true;
					}else{
						return false;
					}
				};
				//判断英文字母和数字
				$.validator.methods.checkengandnum = function (value,element,param){
					var reg = "^[A-Za-z0-9]+$";
					var r = value.match(reg);
					if(r){
						return true;
					}else{
						return false;
					}
				};

				//判断英文字母和数字
				$.validator.methods.checkip = function (value,element,param){
					var reg = "^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$";
					if(value === 0){
						return false;
					}
					if(!value){
						return true;
					}
					var r = value.match(reg);
					if(r){
						return true;
					}else{
						return false;
					}
				};
			});

    		// 清除错误信息
   			function cleanClass(){
   				$(".error").html('');
       		}
			
			var validator;
			$(function() {
				validator = $("#e_insert_form").validate({
					//出错时添加的标签
			        errorElement: "span",
					onfocusout : function(element) {
						$(element).valid();
					},
					rules : {
							i_deviceCode : {
								required : true,
								maxlength : 30,
								remote: {
					                   type: "post",
					                   url: "queryEquipByCode.do",
					                   data:{
						                   deviceCode :  function() {
							                   return $("#i_deviceCode").val();
							               }
						               },
					                   dataType: "json",
					                   dataFilter: function(data, type) {
					                	   var jMsg = eval('('+data+')');
						                   if( jMsg.msg == '1'){
							                   return true;
							               }else{
								               return false;
								           }
					                   }
								}
							},
							i_deviceName : {
								maxlength : 20
							},
							i_deviceSite : {
								maxlength : 20
							},
							i_faceDvModel : {
								maxlength : 20
							},
							i_faceDvLensModel : {
								maxlength : 20
							},
							i_faceDvNo : {
								maxlength : 20
							},
							i_faceDvCollInterval : {
								digits    :true,
								range     :[0,100] //输入值必须介于5 和10 之间
							},
							i_faceDvIp : {
								maxlength : 30,
								checkip   :true
							},
							i_idCardDvNo : {
								maxlength : 20
							},
							i_videoId : {
								maxlength : 20
							},
							i_videoIp : {
								maxlength : 50
								/* url       :true */
							},
							i_remarkDesc : {
								maxlength : 300
							},
					},
					success: function(label) {
			            //正确时的样式
			            label.text(" ").addClass("success");
			        },
					messages : {
						i_deviceCode : {
							required : "请输入设备号",
							maxlength : "设备号不能超过30个字",
							remote: "设备号不能重复"
						},
						i_deviceName : {
							maxlength : "设备名称不能超过20个字"
						},
						i_deviceSite : {
							maxlength : "设备地点不能超过20个字"
						},
						i_faceDvModel : {
							maxlength : "人脸设备型号不能超过20个字"
						},
						i_faceDvLensModel : {
							maxlength : "人脸设备镜头型号不能超过20个字"
						},
						i_faceDvNo : {
							maxlength : "人脸设备序号不能超过20个字"
						},
						i_faceDvCollInterval : {
							digits: "输入的值必须是正整数",
							range : "人脸采集间隔时间必须介于0和100之间"
						},
						i_faceDvIp : {
							maxlength : "人脸设备IP不能超过20个字",
							checkip   : "必须是合法的ip"
						},
						i_idCardDvNo : {
							maxlength : "身份证序列号不能超过20个字"
						},
						i_videoId : {
							maxlength : "视频设备ID不能超过20个字"
						},
						i_videoIp : {
							maxlength : "视频设备IP不能超过20个字",
							url       : "必须输入正确格式的网址"
						},
						i_remarkDesc : {
							maxlength : "备注不能超过300个字"
						}
					}
            	});
			});
			
			$(function() {
				$("#e_update_form").validate({
					onfocusout : function(element) {
						$(element).valid();
					},
					rules : {
						u_deviceCode : {
							required : true,
							maxlength : 30,
							remote: {
				                   type: "post",
				                   url: "queryEquipByCode2u.do",
				                   data:{
					                   deviceCode :  function() {
						                   return $("#u_deviceCode").val();
						               },
					                   deviceId :  function() {
						                   return $("#dev_hid_id").val();
						               }
					               },
				                   dataType: "json",
				                   dataFilter: function(data, type) {
				                	   var jMsg = eval('('+data+')');
					                   if( jMsg.msg == '1'){
						                   return true;
						               }else{
							               return false;
							           }
				                   }
							}
						},
						u_deviceName : {
							maxlength : 20
						},
						u_deviceSite : {
							maxlength : 20
						},
						u_faceDvModel : {
							maxlength : 20
						},
						u_faceDvLensModel : {
							maxlength : 20
						},
						u_faceDvNo : {
							maxlength : 20
						},
						u_faceDvCollInterval : {
							digits    :true,
							range     :[0,100] //输入值必须介于5 和10 之间
						},
						u_faceDvIp : {
							maxlength : 30,
							checkip   :true
						},
						u_idCardDvNo : {
							maxlength : 20
						},
						u_videoId : {
							maxlength : 20
						},
						u_videoIp : {
							maxlength : 50
							/* url       :true */
						},
						u_remarkDesc : {
							maxlength : 300
						}
					},
					messages : {
						u_deviceCode : {
							required : "请输入设备号",
							maxlength : "设备号不能超过30个字",
							remote: "设备号不能重复"
						},
						u_deviceName : {
							maxlength : "设备名称不能超过20个字"
						},
						u_deviceSite : {
							maxlength : "设备地点不能超过20个字"
						},
						u_faceDvModel : {
							maxlength : "人脸设备型号不能超过20个字"
						},
						u_faceDvLensModel : {
							maxlength : "人脸设备镜头型号不能超过20个字"
						},
						u_faceDvNo : {
							maxlength : "人脸设备序号不能超过20个字"
						},
						u_faceDvCollInterval : {
							digits: "输入的值必须是正整数",
							range : "人脸采集间隔时间必须介于0和100之间"
						},
						u_faceDvIp : {
							maxlength : "人脸设备IP不能超过20个字",
							checkip   : "必须是合法的ip"
						},
						u_idCardDvNo : {
							maxlength : "身份证序列号不能超过20个字"
						},
						u_videoId : {
							maxlength : "视频设备ID不能超过20个字"
						},
						u_videoIp : {
							maxlength : "视频设备IP不能超过20个字",
							url       : "必须输入正确格式的网址"
						},
						u_remarkDesc : {
							maxlength : "备注不能超过300个字"
						}
					}
				});
            });
        </script>
    </body>
</html>
