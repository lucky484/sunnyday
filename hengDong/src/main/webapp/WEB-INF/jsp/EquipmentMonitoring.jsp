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
<title>设备一览</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="../css/normalize.css">
<link rel="stylesheet" href="../css/main.css">
</head>
<body>
	<div class="page-wrapper">
		<div class="page-con">
			<div class="match-hd">
				<div class="match-title-box">${e_title }</div>
				<div class="match-title-box">
<!-- 					<form class="form-inline" id="form-submit" action=""> -->
                      <div class = "form-inline">
						<label for="">设备编号</label> <input type="text" id="deviceCode"
							class="form-control"> <label for="">设备地点</label> <input
							type="text" id="collectSite" class="form-control">
						<button id="btn-submit" class="btn" onclick="drawPage()">查询</button>
					</div>
<!-- 					</form> -->
				</div>
			</div>
			<div class="match-data">
				<table class="table">
					<thead>
						<tr>
							<th>设备编号</th>
							<th>设备类型</th>
							<th>启用状态</th>
							<th>设备地点</th>
							<th>人脸设备连接状态</th>
							<th>身份证设备连接状态</th>
							<th>内存使用率（%）</th>
							<th>硬盘空间</th>
							<th>数据库大小（M）</th>
						</tr>
					</thead>
					<tbody id="e_tbody">
						<%-- <c:forEach items="${devices}" var="devices">
							<tr>
								<td><a href="#" class="more">${devices.DEVICECODE}</a></td>
								<td>移动</td>
								<td>启用</td>
								<td>无锡滨湖区火车站</td>
								<td>${devices.FACECONNSTATE }</td>
								<td>${devices.IDCARDCONNSTATE }</td>
								<td>${devices.MEMORYUSAGE }%</td>
								<td>${devices.DISKSPACE }</td>
								<td>${devices.DBSIZE }M</td>
							</tr>
						</c:forEach> --%>
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
	<script src="../js/vendor/jquery-1.11.3.min.js"></script>
	<script src="../js/plugins.js"></script>
	<script src="../js/main.js"></script>
	<script src="../js/eutil.js"></script>
	<script>
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
			$(id).text(y + " " + h + " : " + m + " : " + s);
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
			$.post("../ipc/getPage.do?page=" + page + "&pageSize=" + pageSize
					+ "&deviceCode=" + deviceCode + "&collectSite="
					+ encodeURI(collectSite), function(data) {
				var list = data.devices;
				var tr = '';
				if(!list.length){
					tr = "<tr><td colspan='10'>暂无数据！</td></tr>";
				}else{
					for (var i = 0; i < list.length; i++) {
						var deviceCode = list[i].deviceCode;
						var deviceType = list[i].deviceType;
						var enableStatus = list[i].enableStatus;
						var collectSite = list[i].collectSite;
						var faceConnState = list[i].faceConnState;
						var idcardConnState = list[i].idcardConnState;
						var memoryUsage = list[i].memoryUsage;
						var diskSpace = list[i].diskSpace;
						var dbsize = list[i].dbsize;

						
						tr += '<tr>' + '<td><a href="../FaceInfo/getRealTimeMonitor.do?collectSite='+checkData(collectSite)+'&deviceCode='+ checkData(deviceCode) +'" class="more">'
								+ checkData(deviceCode) + '</a></td>' + '<td>'+checkData(deviceType) +'</td>'
								+ '<td>'+ formatEnableStatus(enableStatus) +'</td>' + '<td>'+ checkData(collectSite) +'</td>' + '<td>'
								+ checkData(faceConnState) + '</td>' + '<td>'
								+ checkData(idcardConnState) + '</td>' + '<td>'
								+ formatData(checkData(memoryUsage),'%') + '</td>' + '<td>'
								+ checkData(diskSpace) + '</td>' + '<td>'
								+ formatData(checkData(dbsize), 'M') + '</td>' + '</tr>';
					}
				}
				$("#e_tbody").html(tr);
				$("#e_page").html(data.pageInfo.currentPage);
				$("#e_pages").html(data.pageInfo.totalPage);
				$("#e_total").html(data.pageInfo.totalCount);
			});

		}

		function formatData(data, symbol){
			if(data=='—'){
				return data;
			}else{
				return data+symbol;
			}
		}
	</script>
</body>
</html>
