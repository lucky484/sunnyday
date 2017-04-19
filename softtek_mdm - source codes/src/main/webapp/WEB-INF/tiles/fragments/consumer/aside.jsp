<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
	
	<spring:url value="/resources/js/jquery.min.js" var="jqueryJs"/>   
  	<script src="${jqueryJs}"></script>
	<script type="text/javascript">
		var ctx = '<%=request.getContextPath() %>';
	</script>
	<aside class="bg-black aside-md hidden-print hidden-xs" id="nav">     
	  <input type="hidden" value="${user.id}" id="uid"/>
      <section class="vbox">
        <section class="w-f scrollable">
          <div class="slim-scroll" data-height="auto" data-disable-fade-out="true" data-distance="0" data-size="10px" data-railOpacity="0.2">
			          <div class="clearfix wrapper dk nav-user hidden-xs">
			           	<select name="eventType" class="form-control m-t-sm input-md" id="leftSelect">
				     		<c:if test="${fn:length(deviceBasicInfoList) > 0}">
				     			<c:forEach var="deviceList" items="${deviceBasicInfoList}" >
			           				<option value="${deviceList.id}" id="${deviceList.sn}" >${deviceList.device_name}</option>
			           			</c:forEach>
           					</c:if>  
						</select>
			          </div>                
            			<!-- nav -->    
				         <nav id="aside-nav" class="nav-primary hidden-xs">
					            	<ul class="nav nav-main" data-ride="collapse" >
					             	<li><a href="javascript:void(0);" onclick="sengMessageBatch();">
									<i class="glyphicon glyphicon-envelope"></i>&nbsp;<fmt:message key="tiles.fragments.consumer.nav.sendmessage"/></a></li>
									<c:if test="${deviceBasicInfoList[0].device_type eq 'ios'}">
										<c:if test="${deviceBasicInfoList[0].device_status == 4}">
							             	<li><a href="javascript:void(0);"  onclick="refrashDeviceBatch(3);">
											<i class="glyphicon glyphicon-refresh"></i>&nbsp;<fmt:message key="tiles.fragments.consumer.nav.updatedevice"/></a></li>
											<li><a href="javascript:void(0);"  onclick="lockTerminBatch(6);">
											<i class="fa fa-unlock"></i>&nbsp;<fmt:message key="tiles.fragments.consumer.nav.lockterm"/></a></li>
											<li><a href="javascript:void(0);"  onclick="deviceBellBatch(9);">
											<i class="fa fa-bell"></i>&nbsp;<fmt:message key="tiles.fragments.consumer.nav.devicebell"/></a></li>
											<li><a href="javascript:void(0);"  onclick="cleanPasswordBatch(8);">
											<i class="i i-trashcan"></i>&nbsp;<fmt:message key="tiles.fragments.consumer.nav.cleanpassword"/></a></li>
										</c:if>
									</c:if>
									<!-- <li><a href="javascript:void(0);">
									<i class="glyphicon glyphicon-trash"></i>&nbsp;擦除企业数据</a></li> -->
									<c:if test="${deviceBasicInfoList[0].device_type eq 'android'}">
									    <c:if test="${deviceBasicInfoList[0].device_status eq '4'}">
									        <li><a href="javascript:void(0);"  onclick="refrashDeviceBatch(3);">
											<i class="glyphicon glyphicon-refresh"></i>&nbsp;<fmt:message key="tiles.fragments.consumer.nav.updatedevice"/></a></li>
											<li><a href="javascript:void(0);"  onclick="lockTerminBatch(6);">
											<i class="fa fa-lock"></i>&nbsp;<fmt:message key="tiles.fragments.consumer.nav.lockterm"/></a></li>
											<li><a href="javascript:void(0);"  onclick="defaultSetBatch(7);">
											<i class="fa fa-reply"></i>&nbsp;<fmt:message key="tiles.fragments.consumer.nav.defaultsetting"/></a></li>
											<li><a href="javascript:void(0);"  onclick="deviceBellBatch(9);">
											<i class="fa fa-bell"></i>&nbsp;<fmt:message key="tiles.fragments.consumer.nav.devicebell"/></a></li>
											<li><a href="javascript:void(0);"  onclick="cleanPasswordBatch(8);">
											<i class="i i-trashcan"></i>&nbsp;<fmt:message key="tiles.fragments.consumer.nav.cleanpassword"/></a></li>
										</c:if>
									</c:if>
									<c:if test="${deviceBasicInfoList[0].lost_status == null}">
										<li><a href="javascript:void(0);"  onclick="remarkDeviceBatch(1);">
										<i class="fa fa-pencil"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.remark.lost"/></a></li>
									</c:if>
									<c:if test="${deviceBasicInfoList[0].lost_status != null}">
										<li><a href="javascript:void(0);"  onclick="remarkDeviceBatch(2);">
										<i class="fa fa-pencil"></i>&nbsp;<fmt:message key="tiles.institution.device.manager.remark.find"/></a></li>
									</c:if>
									<li><a href="javascript:void(0);"  onclick="logOffDeviceBatch(10);">
									<i class="glyphicon glyphicon-remove"></i>&nbsp;<fmt:message key="tiles.fragments.consumer.nav.logoffdevice"/></a></li>
								</ul>
				         </nav>
           				<!-- / nav -->
         </div>
       </section>
     </section>
   </aside>
   <!-- jquery部分代码，用于把页面上的设备信息id传递到后台 -->
   <script type="text/javascript">
   		$(function(){
   			
   		});
   </script>
   
   
   
   
   
   
   
   
   
   
   
   
   
   