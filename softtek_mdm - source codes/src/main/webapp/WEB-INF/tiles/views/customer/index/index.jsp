<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="blog-post">
	<div class="post-item">
		<header class="panel-heading b-l-l-none b-t-l-none b-b-l-1 b-r-l-none b-s-s">
			<c:if test="${fn:length(deviceBasicInfoList) > 0}">
				<a href="javascript:void(0);" class="btn btn-s-sm btn-primary" onclick="javascript:loadIndex();"><fmt:message key="tiles.views.customer.index.head.deviceinfo"/></a>
				<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadAppList();"><fmt:message key="tiles.views.customer.index.head.applist"/></a>
				<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadposition();"><fmt:message key="tiles.views.customer.index.head.position"/></a>
				<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadCompliant();"><fmt:message key="tiles.views.customer.index.head.compliant"/></a>
				<!-- <a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadConfig();">配置描述文件</a> -->
				<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadPersonal();"><fmt:message key="tiles.views.customer.index.head.personalinfo"/></a>
				<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadPassword();"><fmt:message key="tiles.views.customer.index.head.updpassword"/></a>
				<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadDeviceLog();"><fmt:message key="tiles.views.customer.index.head.devicelog"/></a>
			</c:if>
			<c:if test="${fn:length(deviceBasicInfoList) <= 0}">
				<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadPersonal();"><fmt:message key="tiles.views.customer.index.head.personalinfo"/></a>
				<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadPassword();"><fmt:message key="tiles.views.customer.index.head.updpassword"/></a>
			</c:if>
		</header>
		<section class="wrapper-md">
			<div class="btn-toolbar">
				<button class="btn btn-default btn-sm  btn-success" onclick="updateDeviceInfo(3);" id="updateBtn">
					 &nbsp;<fmt:message key="tiles.views.customer.index.index.updatebtn"/>
				</button>
			</div>
			<div class="separate_div"></div>
			<div id="infos">
				<div class="row">
				<div class="col-sm-6">
					<div class="row">
						<div class="basic_info basic_line">
							<span class="span_line"><fmt:message key="tiles.views.customer.index.index.basicinfo"/></span>
						</div>
						<div class="text-md div-content">
							<p><span class="font-bold span-content" ><fmt:message key="tiles.views.customer.index.index.basicinfo.devicestatus"/></span>&nbsp;&nbsp;<span id="device_status"></span></p>
							<p><span class="font-bold span-content"><fmt:message key="tiles.views.customer.index.index.basicinfo.devicecompliant"/></span>&nbsp;&nbsp;<span id="device_compliant"></span></p>
							<p><span class="font-bold span-content"><fmt:message key="tiles.views.customer.index.index.basicinfo.power"/></span>&nbsp;&nbsp;<span id="power"></span></p>
							<p><span class="font-bold span-content"><fmt:message key="tiles.views.customer.index.index.basicinfo.devicetype"/></span>&nbsp;&nbsp;<span id="device_type"></span></p>
							<p><span class="font-bold span-content"><fmt:message key="tiles.views.customer.index.index.basicinfo.osversion"/></span>&nbsp;&nbsp;<span id="os_version"></span></p>
							<p><span class="font-bold span-content"><fmt:message key="tiles.views.customer.index.index.basicinfo.phonenumber"/></span>&nbsp;&nbsp;<span id="phone_number"></span></p>
						</div>
					</div>
				<div class="row">
					<div class="basic_info basic_line">
						<span class="span_line"><fmt:message key="tiles.views.customer.index.index.networkinfo"/></span>
					</div>
					<div class="text-md div-content">
						<p><span class="font-bold span-content"><fmt:message key="tiles.views.customer.index.index.networkinfo.vendor"/></span>&nbsp;&nbsp;<span id="vendor"></span></p>
						<p><span class="font-bold span-content"><fmt:message key="tiles.views.customer.index.index.networkinfo.networktype"/></span>&nbsp;&nbsp;<span id="net_type_id"></span></p>
						<!-- 语音漫游在iphone中有，但是在android中没有，需要做类型判断-->
						<!--<p><span class="font-bold span-content">语音漫游</span>&nbsp;&nbsp;未知</p>-->
						<p><span class="font-bold span-content"><fmt:message key="tiles.views.customer.index.index.networkinfo.voicerome"/></span>&nbsp;&nbsp;<span id="voice_roam"></span></p>
						<p><span class="font-bold span-content"><fmt:message key="tiles.views.customer.index.index.networkinfo.datarome"/></span>&nbsp;&nbsp;<span id="data_roam"></span></p>
						<p><span class="font-bold span-content"><fmt:message key="tiles.views.customer.index.index.networkinfo.wifimac"/></span>&nbsp;&nbsp;<span id="wifi_mac"></span></p>
						<p><span class="font-bold span-content"><fmt:message key="tiles.views.customer.index.index.networkinfo.bluetoothmac"/></span>&nbsp;&nbsp;<span id="blue_tooth_mac"></span></p>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="basic_info basic_line">
					<span class="span_line"><fmt:message key="tiles.views.customer.index.index.detailinfo"/></span>
				</div>
					<div class="text-md div-content">
						<p><span class="font-bold span-content"><fmt:message key="tiles.views.customer.index.index.detailinfo.ip"/></span>&nbsp;&nbsp;<span id="ip"></span></p>
						<p><span class="font-bold span-content"><fmt:message key="tiles.views.customer.index.index.detailinfo.devicename"/></span>&nbsp;&nbsp;<span id="device_name"></span></p>
						<p><span class="font-bold span-content"><fmt:message key="tiles.views.customer.index.index.detailinfo.resolution"/></span>&nbsp;&nbsp;<span id="resolution"></span></p>
						<p><span class="font-bold span-content"><fmt:message key="tiles.views.customer.index.index.detailinfo.appversion"/></span>&nbsp;&nbsp;<span id="app_version"></span></p>
						<p><span class="font-bold span-content"><fmt:message key="tiles.views.customer.index.index.detailinfo.sn"/></span>&nbsp;&nbsp;<span id="sn"></span></p>
						<p><span class="font-bold span-content"><fmt:message key="tiles.views.customer.index.index.detailinfo.udid"/></span>&nbsp;&nbsp;<span id="sn"></span>&nbsp;&nbsp;<span id="udid"></span></p>
						<p><span class="font-bold span-content"><fmt:message key="tiles.views.customer.index.index.detailinfo.esn/imei"/></span>&nbsp;&nbsp;<span id="esnorimei"></span></p>
						<p><span class="font-bold span-content"><fmt:message key="tiles.views.customer.index.index.detailinfo.capacity"/></span>&nbsp;&nbsp;<span id="capacity"></span></p>
						<!-- <p><span class="font-bold span-content">物理内存</span>&nbsp;&nbsp;未知</p> -->
						<p><span class="font-bold span-content"><fmt:message key="tiles.views.customer.index.index.detailinfo.sdcard"/></span>&nbsp;&nbsp;<span id="sd_card"></span></p>
						<!-- <p><span class="font-bold span-content">设备签名</span>&nbsp;&nbsp;默认签名</p>  -->
						<p><span class="font-bold span-content"><fmt:message key="tiles.views.customer.index.index.detailinfo.lastcollectiontime"/></span>&nbsp;&nbsp;<span id="last_collection_time"></span></p>
						<p><span class="font-bold span-content"><fmt:message key="tiles.views.customer.index.index.detailinfo.lastlogintime"/></span>&nbsp;&nbsp;<span id="last_login_time"></span></p>
					</div>
			    </div>
	 		 </div>
		  </div>
		</section>
	</div>
</div>

