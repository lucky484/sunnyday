<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<spring:url value="/resources/js/jquery.tmpl.js" var="jqueryTmplJs" />
<script src="${jqueryTmplJs}"></script>

<jsp:include page="../common/common.jsp"></jsp:include>
<jsp:include page="../common/common_style.jsp"></jsp:include>

<script type="text/javascript">

	var intervalTime;
	var intervalResult;
	var deviceBasicInfo;
	//比较精确的除法运算
	function accDiv(arg1,arg2){
		var t1 = 0;
		var t2 = 0;
		var r1 = 0;
		var r2 = 0;
		if(arg1!=null && arg1!='' && arg2!=null && arg2!= ''){
			try {
				t1 = arg1.toString().split(".")[1].length;
			} catch (e) {
			}
			try {
				t2 = arg2.toString().split(".")[1].length;
			} catch (e) {
			}
			with (Math) {
				r1 = Number(arg1.toString().replace(".", ""));
				r2 = Number(arg2.toString().replace(".", ""));
				return (r1 / r2) * pow(10, t2 - t1);
			}
		}
	}

	function getData(id) {
		$.ajax({
			"dataType" : 'json',
			"data" : {
				"id" : id
			},
			"type" : "GET",
			"async": false,
			"url" : ctx + "/customer/getDeviceInfo",
			"success" : function(result) {
				deviceBasicInfo = result;
				//从后台去加载基本信息，设备信息，网络信息的状态
				if (result.device_status == null) {
					$('#device_status').text('');
				} else {
					if (result.device_status == 1) {
						$('#device_status').text('<fmt:message key="tiles.views.customer.index.index.devicestatus.logoff"/>');
					} else if (result.device_status == 2) {
						$('#device_status').text('<fmt:message key="tiles.views.customer.index.index.devicestatus.falloff"/>');
					} else if (result.device_status == 3) {
						$('#device_status').text('<fmt:message key="tiles.views.customer.index.index.devicestatus.waitmonitor"/>');
					} else if (result.device_status == 4){
						$('#device_status').text('<fmt:message key="tiles.views.customer.index.index.devicestatus.monitoring"/>');
					}else if (result.device_status == 5){
						$('#device_status').text('<fmt:message key="tiles.views.customer.index.index.devicestatus.lost"/>');
					}else if (result.device_status == 6){
						$('#device_status').text('<fmt:message key="tiles.views.customer.index.index.devicestatus.find"/>');
					}
				}
				
				if(result.deviceLegalListModel == null){
					$('#device_compliant').text('<fmt:message key="tiles.views.customer.index.index.devicecompliant.legal"/>');
				}else{
					if(result.deviceLegalListModel.status == 1){
						$('#device_compliant').text('<fmt:message key="tiles.views.customer.index.index.devicecompliant.illegal"/>');
					}else{
						$('#device_compliant').text('<fmt:message key="tiles.views.customer.index.index.devicecompliant.legal"/>');
					}
				}
				
				$('#device_type').text(
						result.device_type == null ? ''
								: result.device_type);
				$('#power').text(
						result.power == null ? '' : result.power);
				$('#os_version').text(
						result.os_version == null ? ''
								: result.os_version);
				$('#phone_number').text(
						result.phone_number == null ? ''
								: result.phone_number);

				$('#vendor').text(
						result.deviceNetworkStatus.vendor == null ? ''
								: result.deviceNetworkStatus.vendor);
				$('#net_type_id')
						.text(
								result.deviceNetworkStatus.net_type_id == null ? ''
										: result.deviceNetworkStatus.net_type_id);
				if (result.deviceNetworkStatus.data_roam == null) {
					$('#data_roam').text('');
				} else {
					//$('#data_roam').text(result.deviceNetworkStatus.data_roam==null?'':result.deviceNetworkStatus.data_roam);
					if (result.deviceNetworkStatus.data_roam == 0) {
						$('#data_roam').html(
								'<i class="fa fa-toggle-off fa-2x"></i>');
					} else if (result.deviceNetworkStatus.data_roam == 1) {
						//var content = '<a href="javascript:void(0);"><i class="fa fa-toggle-on">::before</i>toggle-on</a>';
						$('#data_roam').html(
								'<i class="fa fa-toggle-on fa-2x"></i>');
					} else {
						$('#data_roam').text('<fmt:message key="tiles.views.customer.index.index.device.datarome"/>');
					}
				}
				if (result.deviceNetworkStatus.voice_roam == null) {
					$('#voice_roam').text('');
				} else {
					//$('#data_roam').text(result.deviceNetworkStatus.data_roam==null?'':result.deviceNetworkStatus.data_roam);
					if (result.deviceNetworkStatus.voice_roam == 0) {
						$('#voice_roam').html(
								'<i class="fa fa-toggle-off fa-2x"></i>');
					} else if (result.deviceNetworkStatus.voice_roam == 1) {
						//var content = '<a href="javascript:void(0);"><i class="fa fa-toggle-on">::before</i>toggle-on</a>';
						$('#voice_roam').html(
								'<i class="fa fa-toggle-on fa-2x"></i>');
					} else {
						$('#voice_roam').text('<fmt:message key="tiles.views.customer.index.index.device.datarome"/>');
					}
				}
				//加一个判断，如果当前的设备是android，则把当前的设备中的语音漫游的html代码删除掉
				if(result.device_type == 'android'){
					$("#voice_roam").parent().remove();
				}
				
				$('#wifi_mac')
						.text(
								result.deviceNetworkStatus.wifi_mac == null ? ''
										: result.deviceNetworkStatus.wifi_mac);
				$('#blue_tooth_mac')
						.text(
								result.deviceNetworkStatus.blue_tooth_mac == null ? ''
										: result.deviceNetworkStatus.blue_tooth_mac);

				$('#ip').text(result.ip == null ? '' : result.ip);
				$('#device_name').text(
						result.device_name == null ? ''
								: result.device_name);
				$('#resolution').text(
						result.resolution == null ? ''
								: result.resolution);
				$('#app_version').text(
						result.app_versoin == null ? ''
								: result.app_versoin);
				$('#sn').text(result.sn == null ? '' : result.sn);
				$('#udid').text(result.udid == null ? '' : result.udid);
				$('#esnorimei').text(
						result.esnorimei == null ? ''
								: result.esnorimei);
				$('#last_collection_time')
				.text(
						result.last_collection_time == null ? ''
								: new Date(
										parseInt(result.last_collection_time))
										.Format("yyyy-MM-dd hh:mm:ss"));
				$('#last_login_time').text(
				result.last_login_time == null ? '' : new Date(
						parseInt(result.last_login_time))
						.Format("yyyy-MM-dd hh:mm:ss"));
				
				if (result.capacity == null) {
					return '';
				} else {
					var useMemory = result.capacity.split(",")[0];
					var totalMemory = result.capacity.split(",")[1];
					var  accResult = accDiv(useMemory, totalMemory);
					var length = '';
					if(accResult != null && accResult != '' && accResult != 'undefined'){
						length = accDiv(useMemory, totalMemory)
						.toFixed(2) * 100;
						result.capacity = '&nbsp;<fmt:message key="tiles.views.customer.index.index.device.capacity.remain"/>'
							+ (totalMemory - useMemory).toFixed(2)
							+ 'G&nbsp;|&nbsp;' + '<fmt:message key="tiles.views.customer.index.index.device.capacity.total"/>' + totalMemory
							+ 'G';
						var content = '<div class="data-box"><div class="data-inner" style="width:'
							+ length
							+ '%;background:#77b94e"></div></div><div class="data-text" style="display:inline-block;">'
							+ result.capacity + '</div>';
						$('#capacity').html(content);
					}
				}
				if (result.sdCard == null) {
					return '';
				} else {
					var useMemory = result.sdCard.split(",")[0];
					var totalMemory = result.sdCard.split(",")[1];
					var  accResult = accDiv(useMemory, totalMemory);
					var length = '';
					if(accResult != null && accResult != '' && accResult != 'undefined'){
						length = accDiv(useMemory, totalMemory)
						.toFixed(2) * 100;
						result.sdCard = '&nbsp;<fmt:message key="tiles.views.customer.index.index.device.capacity.remain"/>'
							+ (totalMemory - useMemory).toFixed(2)
							+ 'G&nbsp;|&nbsp;' + '<fmt:message key="tiles.views.customer.index.index.device.capacity.total"/>' + totalMemory
							+ 'G';
						var content = '<div class="data-box"><div class="data-inner" style="width:'
							+ length
							+ '%;background:#77b94e"></div></div><div class="data-text" style="display:inline-block;">'
							+ result.sdCard + '</div>';
						$('#sd_card').html(content);
					}
				}
			}
		});
	}
	var intDiff = parseInt(300);//倒计时总秒数量
	var timeStatus = false;
	
	//添加cookie
	function addCookie(name,value,expiresHours){
		var cookieString=name+"="+escape(value); 
		//判断是否设置过期时间,0代表关闭浏览器时失效
		if(expiresHours>0){
		var date=new Date(); 
		date.setTime(date.getTime()+expiresHours*1000);
		cookieString=cookieString+";expires="+ date.toUTCString();
		}
		window.sessionStorage.timeOut=cookieString;
		document.cookie=cookieString;
	}
	
	//修改cookie中的值
	function editCookie(name,value,expiresHours){ 
		var cookieString=name+"="+escape(value); 
		if(expiresHours>0){
		 var date=new Date(); 
		 date.setTime(date.getTime()+expiresHours*1000); //单位是毫秒
		 cookieString=cookieString+";expires="+ date.toGMTString(); 
		}
		document.cookie=cookieString;
	}
	
	//根据name从cookie中获取值
	function getCookieValue(name){ 
		 var strCookie=document.cookie; 
		 var arrCookie=strCookie.split(";"); 
		 for(var i=0;i<arrCookie.length;i++){ 
		 var arr=arrCookie[i].split("="); 
		 if(arr[0]==name){
		 	return unescape(arr[1]);
			break;
		}else{
		 	return""; 
			break;
		}
	  }
	}
	
	//倒计时的时候处理按钮的显示
	function setTime() {
		var intDiff = getCookieValue("secondsremained");  
		//var intDiff =  localStorage.getItem("secondsremained");
		intDiff = (intDiff - 1);
		editCookie("secondsremained",intDiff,intDiff);
		//localStorage["secondsremained"] = intDiff;
		//localStorage.setItem("secondsremained", intDiff);
		if (intDiff < 0) {
			clearInterval(intervalTime);
			$('#updateBtn').html('<fmt:message key="tiles.views.customer.index.index.updatebtn"/>');
			timeStatus = false;
		} else {
			//当出现倒计时时,点击应该不触发任何事件
			$('#updateBtn').html('(' + intDiff + '<fmt:message key="tiles.views.customer.index.index.device.update.time"/>)');
			$('#updateBtn').attr('onclick', "javascript:void(0);");
		}

	}
	
	//每隔5秒更新设备信息
	function updateDetail() {
		var did = $('#leftSelect').children('option:selected').val();
		getData(did);
	}
	
	//设置timer的函数
	function timer(intDiff, did, udid, type) {
		//发送请求信息到客户端，请求更新设备信息
		var uid = $('#uid').val();
		var csrf="${_csrf.token}";
		$.post("customer/sendCommandToClient", {
			flag : type,
			udid : udid,
			uid : uid,
			_csrf:csrf  
		}, function(data) {
			var result = $.parseJSON(data);
			var status = result.status;
			if (!status) {
				$("#failMsg").modal();
				$('#failMsgEnsure').click(function() {
					$("#failMsg").modal('hide');
				});
			} else {
				if (!timeStatus) {
					addCookie("secondsremained",60,60);
					//localStorage.setItem("secondsremained", "60");
					intervalTime = setInterval("setTime()", 1000);
					//intervalResult = setInterval("updateDetail()", 5000);
					updateDetail();
					timeStatus = true;
				}
			}
		});
	}

	//这个方法是index页面的
	function updateDeviceInfo(type) {
		//获取当前选中的设备的id
		var did = $('#leftSelect').children('option:selected').val();
		var udid = $('#leftSelect').children('option:selected').attr('id');
		//点击更新按钮之后做倒计时的处理
		var intDiff = 300;
		timer(intDiff, did, udid, type);
	}
	$(function(){
		var id = '';
		$('#leftSelect').change(function(){
			id = $(this).children('option:selected').val();  
			getData(id);
		});
		if(id == ''){
			id = $('#leftSelect option:first').val();
			getData(id);
		}
		var intDiff = getCookieValue("secondsremained");  
		//var intDiff = localStorage.getItem("secondsremained");
		if(intDiff > 0){
			//获取当前选中的设备的id
			var did = $('#leftSelect').children('option:selected').val();
			var udid = $('#leftSelect').children('option:selected').attr('id');
			//点击更新按钮之后做倒计时的处理
			timer(intDiff, did, udid, 3);
		}
		if(deviceBasicInfo.device_type == "android"){
			  $("#aside-nav > li").html('<li><a href="javascript:void(0);" onclick="sengMessageBatch();">'+
										'<i class="glyphicon glyphicon-envelope"></i>&nbsp;<fmt:message key="tiles.fragments.consumer.nav.sendmessage"/></a></li>'+
										'<li><a href="javascript:void(0);"  onclick="refrashDeviceBatch(3);">'+
										'<i class="glyphicon glyphicon-refresh"></i>&nbsp;<fmt:message key="tiles.fragments.consumer.nav.updatedevice"/></a></li>'+
										'<li><a href="javascript:void(0);"  onclick="lockTerminBatch(6);">'+
										'<i class="fa fa-unlock"></i>&nbsp;<fmt:message key="tiles.fragments.consumer.nav.lockterm"/></a></li>'+
										'<li><a href="javascript:void(0);"  onclick="deviceBellBatch(9);">'+
										'<i class="fa fa-bell"></i>&nbsp;<fmt:message key="tiles.fragments.consumer.nav.devicebell"/></a></li>'+
										'<li><a href="javascript:void(0);"  onclick="cleanPasswordBatch(8);">'+
										'<i class="i i-trashcan"></i>&nbsp;<fmt:message key="tiles.fragments.consumer.nav.cleanpassword"/></a></li>'+
										'<li><a href="javascript:void(0);"  onclick="defaultSetBatch(7);">'+
										'<i class="fa fa-reply"></i>&nbsp;<fmt:message key="tiles.fragments.consumer.nav.defaultsetting"/></a></li>');
		}else{
			if(deviceBasicInfo.device_status == 4){
				 $("#aside-nav > li").html('<li><a href="javascript:void(0);" onclick="sengMessageBatch();">'+
							'<i class="glyphicon glyphicon-envelope"></i>&nbsp;<fmt:message key="tiles.fragments.consumer.nav.sendmessage"/></a></li>'+
							'<li><a href="javascript:void(0);"  onclick="refrashDeviceBatch(3);">'+
							'<i class="glyphicon glyphicon-refresh"></i>&nbsp;<fmt:message key="tiles.fragments.consumer.nav.updatedevice"/></a></li>'+
							'<li><a href="javascript:void(0);"  onclick="lockTerminBatch(6);">'+
							'<i class="fa fa-unlock"></i>&nbsp;<fmt:message key="tiles.fragments.consumer.nav.lockterm"/></a></li>'+
							'<li><a href="javascript:void(0);"  onclick="deviceBellBatch(9);">'+
							'<i class="fa fa-bell"></i>&nbsp;<fmt:message key="tiles.fragments.consumer.nav.devicebell"/></a></li>'+
							'<li><a href="javascript:void(0);"  onclick="cleanPasswordBatch(8);">'+
							'<i class="i i-trashcan"></i>&nbsp;<fmt:message key="tiles.fragments.consumer.nav.cleanpassword"/></a></li>');
			}else{
				 $("#aside-nav > li").html('<li><a href="javascript:void(0);" onclick="sengMessageBatch();">'+
							'<i class="glyphicon glyphicon-envelope"></i>&nbsp;<fmt:message key="tiles.fragments.consumer.nav.sendmessage"/></a></li>');
			}
		}
	    if(deviceBasicInfo.lost_status == null){
	    	$("#aside-nav > li").append('<li><a href="javascript:void(0);"  onclick="remarkDeviceBatch(1);">'+
										'<i class="fa fa-pencil"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.remark.lost"/></a></li>'+
										'<li><a href="javascript:void(0);"  onclick="logOffDeviceBatch(10);">'+
										'<i class="glyphicon glyphicon-remove"></i>&nbsp;<fmt:message key="tiles.fragments.consumer.nav.logoffdevice"/></a></li>');
	    }else{
	    	$("#aside-nav > li").append('<li><a href="javascript:void(0);"  onclick="remarkDeviceBatch(2);">'+
					'<i class="fa fa-pencil"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.remark.find"/></a></li>'+
					'<li><a href="javascript:void(0);"  onclick="logOffDeviceBatch(10);">'+
					'<i class="glyphicon glyphicon-remove"></i>&nbsp;<fmt:message key="tiles.fragments.consumer.nav.logoffdevice"/></a></li>');
	    }
  });
</script>