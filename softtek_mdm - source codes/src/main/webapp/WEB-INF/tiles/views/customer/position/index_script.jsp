<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=vAXGBRGMlqQdmpmO1G8LlVuMMf2R0leh&s=1"></script>
<spring:url value="/customer/appListPages" var="pagesUrl" />
<spring:url value="/customer/getLocation" var="getdeviceposition" />
<spring:url value="/customer/deviceLocation" var="deviceGpsUrl" />
<spring:url value="/resources/js/datatables-1.10.11/js/jquery.dataTables.js" var="dataTableJs" />
<script src="${dataTableJs}"></script>
<spring:url
	value="/resources/js/datatables-1.10.11/js/dataTables.bootstrap.js"
	var="dataTableBootstrapJs" />
<script src="${dataTableBootstrapJs}"></script>
<spring:url value="/resources/js/jquery.tmpl.js" var="jqueryTmplJs" />
	var="dtLangUrl" />
<script src="${jqueryTmplJs}"></script>

<jsp:include page="../common/common.jsp"></jsp:include>
<jsp:include page="../common/common_style.jsp"></jsp:include>

<script type="text/javascript">
	//加载默认地图
	function loadposition() {
		$("#container").html('<table width="100%">'+
	    		'<tbody><tr>'+
					'<td align="left" width="100%" colspan="2" style="border:0">&nbsp;&nbsp;'+
							'<a href="javascript:deviceGps();" class="button" id="locationB"><i class="fa fa-map-marker" style="color:#6888B9;"></i>&nbsp;&nbsp;<span style="color:#6888B9;"><fmt:message key="tiles.views.user.index.table.location" /></span></a>'+
					'</td>'+
	    		'</tr>'+
	    		'<tr>'+
	    		  '<td align="left" style="width:10%;border:0">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="tiles.institution.device.manager.location.result" /></td>'+
	    		  '<td align="left" style="width:87%;border:0" id="locationRequest"><fmt:message key="tiles.institution.device.manager.location.no" /></td>'+
	    		'</tr>'+
	    	'</tbody></table>'+
   		'<div id="descDiv" class="devicePlace" style="width: 100%;margin-left: 0px;height: 100%;"></div>');
		initMap();
	}
	var point;
	var zoom;
	function initMap(){
		  zoom = 11;
		  point = new BMap.Point("116.405437","39.912786");  // 创建点坐标  
		  createMap();//创建地图
	      setMapEvent();//设置地图事件
	      addMapControl();//向地图添加控件
	}
	//创建地图函数
	function createMap(){
		 var map = new BMap.Map("descDiv");//在百度地图容器中创建一个地图
		 map.centerAndZoom(point,zoom);//设定地图的中心点和坐标并将地图显示在地图容器中
		 window.map = map;//将map变量存储在全局
	}
	//地图事件设置函数
	function setMapEvent(){
		  map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
          map.enableScrollWheelZoom();//启用地图滚轮放大缩小
	      map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
	}
	   //地图控件添加函数：
	   function addMapControl(){
		   //向地图中添加缩放控件
		   var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
		   map.addControl(ctrl_nav);
		   //向地图中添加缩略图控件
		   var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:true});
		   map.addControl(ctrl_ove);
		   //向地图中添加比例尺控件
		   var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
		   map.addControl(ctrl_sca);
		   var ctrl_type = new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP,BMAP_HYBRID_MAP]});
		   map.addControl(ctrl_type);
	   }
	//立即定位
	var times;
	var intervalTime;
	var intervalResult;
	function deviceGps(){
		times = 300;
		var did = $('#leftSelect').children('option:selected').val();  
		var userId = $('#uid').val();
		var csrf = "${_csrf.token}";
		$.post("${deviceGpsUrl}",{did:did,userId:userId,_csrf:csrf},function(data){
			$("#locationRequest").html("设备于"+data.locationTime+"发起定位请求。定位中...");
			intervalTime = setInterval("setTime()",1000);
			times = times - 5;
			intervalResult = setInterval("loadLocationResult(times)", 5000);
		},'json');
	}
	//发送定位请求，定位倒计时
	function setTime(){
		times = (times - 1);
		if(times < 0){
			clearInterval(intervalTime);
		}else{
			$("#locationB").find("span").html('('+times+'秒)');
			$("#locationB").attr("href","javascript:void(0)");
		}
	}
	//获取定位信息
	function loadLocationResult(times){
		var did = $('#leftSelect').children('option:selected').val();  
		var csrf = "${_csrf.token}";
		$.post("${getdeviceposition}",{did:did,times:times,_csrf:csrf},function(data){
			if(data.locationStatus == "locationing"){
				$("#locationRequest").html("设备于"+data.locationRequestDate+"发起定位请求。定位中···");
			}else if(data.locationStatus == "success"){
				$("#locationRequest").html("设备于"+data.locationRequestDate+"发起定位请求。位置:"+data.deviceLocation.locDec);
				$("#locationB").find("span").html("立即定位");
				$("#locationB").attr("href","javascript:doLocation();");
				clearInterval(intervalTime);
                clearInterval(intervalResult);
                times = 300;
                //定位成功，重新绘制地图
                panToMap(data.deviceLocation.longitude,data.deviceLocation.latitude);
			}else if(data.locationStatus == "fail"){
				 $("#locationRequest").html("设备于"+data.locationRequestDate+"发起定位请求。定位失败");
				   $("#locationB").find("span").html("立即定位");
                   $("#locationB").attr("href","javascript:doLocation();");
                   clearInterval(intervalTime);
                   clearInterval(intervalResult);
                   times = 300;
			}
		},'json');
	}
	//重绘地图的函数
    function panToMap(longitude,latitude){   
    	initMap();   //定位成功后新重新初始化地图
	    map.panTo(new BMap.Point(longitude,latitude));    
	    map.clearOverlays(); 
	    var new_point = new BMap.Point(longitude,latitude);
	    var marker = new BMap.Marker(new_point);  // 创建标注
	    map.addOverlay(marker);              // 将标注添加到地图中
	    map.panTo(new_point);  
    }
	/* //设备定位
	function deviceGps() {
		var result = "";
		//获取当前用户的id
		var uid = $('#uid').val();
		//获取当前设备的id
		var did = $('#leftSelect').children('option:selected').val();  
		var csrf = "${_csrf.token}";
		var map = new BMap.Map("container"); // 创建地图实例  
		var point = new BMap.Point(116.404, 39.915); // 创建点坐标  
		map.centerAndZoom(point, 15);
		map.enableScrollWheelZoom();// 初始化地图，设置中心点坐标和地图级别  
		map.enableKeyboard();
		map.addControl(new BMap.NavigationControl());
		map.addControl(new BMap.ScaleControl());
		map.addControl(new BMap.OverviewMapControl());
		map.addControl(new BMap.MapTypeControl());
		$.post("${deviceGpsUrl}", {
			did : did,
			uid : uid,
			_csrf : csrf
		});
		window.setTimeout(function() {
			$.post("${getdeviceposition}", {
				did : did,
				_csrf : csrf
			}, function(data) {
				var longitude = data.deviceBasicInfo.longitude;
				var latitude = data.deviceBasicInfo.latitude;
				if (data.deviceBasicInfo.longitude != null
						&& data.deviceBasicInfo.longitude != "") {
					map.panTo(new BMap.Point(longitude, latitude));
					map.clearOverlays();
					var new_point = new BMap.Point(longitude, latitude);
					var marker = new BMap.Marker(new_point); // 创建标注
					map.addOverlay(marker); // 将标注添加到地图中
					map.panTo(new_point);
				}
			}, 'json');
		}, 5000);
	} */
	$(function() {
		loadposition();
	})
</script>