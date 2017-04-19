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
				<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadIndex();"><fmt:message key="tiles.views.customer.index.head.deviceinfo"/></a>
				<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadAppList();"><fmt:message key="tiles.views.customer.index.head.applist"/></a>
				<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadposition();"><fmt:message key="tiles.views.customer.index.head.position"/></a>
				<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadCompliant();"><fmt:message key="tiles.views.customer.index.head.compliant"/></a>
				<!-- <a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadConfig();">配置描述文件</a> -->
				<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadPersonal();"><fmt:message key="tiles.views.customer.index.head.personalinfo"/></a>
				<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadPassword();"><fmt:message key="tiles.views.customer.index.head.updpassword"/></a>
				<a href="javascript:void(0);" class="btn btn-s-sm  btn-primary" onclick="javascript:loadDeviceLog();"><fmt:message key="tiles.views.customer.index.head.devicelog"/></a>
			</c:if>
			<c:if test="${fn:length(deviceBasicInfoList) <= 0}">
				<a href="javascript:void(0);" class="btn btn-s-sm btn-primary" onclick="javascript:loadPersonal();"><fmt:message key="tiles.views.customer.index.head.personalinfo"/></a>
				<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadPassword();"><fmt:message key="tiles.views.customer.index.head.updpassword"/></a>
			</c:if>
		</header>
	</div>
<div id="content">
         <div class="hbox stretch">
           <div>
             <div class="vbox">
               <div class="scrollable padder">
                 <div class="panel panel-default">
                   <div class="panel-body">
                       <div class="search-toggle">
						     <a hidfocus="true"><fmt:message key="jsp.views.customer.device.js.openquery"/></a>
						   </div>
				   		   <div class="search-mod" style="display: none;height:46px;">
			   				<form method="" action="">
			   				    <ul class="search-list">
			   						<li class="search-item ">
			   							<label class="search-label" style="width:76px"><fmt:message key="tiles.institution.log.operate.event.type" />:</label>
			   							<div class="Js_dropMod select-box inline-select select-200" style="width:130px;">
											<input type="hidden" class="Js_hiddenVal" id="eventType" name="eventType" value="" />
											<span class="Js_curVal"><input type="text" value="<fmt:message key='tiles.institution.log.operate.all.type'/>"></span>
											<ul class="select-list" style="width:130px;">
												<li class="select-item"><a href="javascript:;" rel=""><fmt:message key='tiles.institution.log.operate.all.type'/></a></li>
												<li class="select-item"><a href="javascript:;" rel="0"><fmt:message key="tiles.views.customer.device.index.eventtype.0" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="1"><fmt:message key="tiles.views.customer.device.index.eventtype.1" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="2"><fmt:message key="tiles.views.customer.device.index.eventtype.2" /></a></li>
												<li class="select-item"><a href="javascript:;" rel="3"><fmt:message key="tiles.views.customer.device.index.eventtype.3" /> </a></li>
												<li class="select-item"><a href="javascript:;" rel="4"><fmt:message key="tiles.views.customer.device.index.eventtype.4" /> </a></li>
												<li class="select-item"><a href="javascript:;" rel="5"><fmt:message key="tiles.views.customer.device.index.eventtype.5" /> </a></li>
												<li class="select-item"><a href="javascript:;" rel="6"><fmt:message key="tiles.views.customer.device.index.eventtype.6" /> </a></li>
												<li class="select-item"><a href="javascript:;" rel="7"><fmt:message key="tiles.views.customer.device.index.eventtype.7" /> </a></li>
												<li class="select-item"><a href="javascript:;" rel="8"><fmt:message key="tiles.views.customer.device.index.eventtype.8" /> </a></li>
												<li class="select-item"><a href="javascript:;" rel="9"><fmt:message key="tiles.views.customer.device.index.eventtype.9" /> </a></li>
												<li class="select-item"><a href="javascript:;" rel="10"><fmt:message key="tiles.views.customer.device.index.eventtype.10" /> </a></li>
												<li class="select-item"><a href="javascript:;" rel="11"><fmt:message key="tiles.views.customer.device.index.eventtype.11" /> </a></li>
												<li class="select-item"><a href="javascript:;" rel="12"><fmt:message key="tiles.views.customer.device.index.eventtype.12" /> </a></li>
												<li class="select-item"><a href="javascript:;" rel="13"><fmt:message key="tiles.views.customer.device.index.eventtype.13" /> </a></li>
												<li class="select-item"><a href="javascript:;" rel="14"><fmt:message key="tiles.views.customer.device.index.eventtype.14" /> </a></li>
											</ul>
										</div>
			   						</li>
			   					</ul>
			   					<ul class="search-list">
			   						<li class="search-item ">
			   							<label class="search-label" style="width:76px"><fmt:message key="tiles.views.customer.device.index.createtime" /></label>
			   								<input type="text" id="timeStart" name="timeStart" class="search-text user-disabled" data-date-format="yyyy-mm-dd" size="16">
   											~
   											<input type="text" id="timeEnd" name="timeEnd" class="search-text user-disabled" data-date-format="yyyy-mm-dd" size="16">
			   						</li>
			   					</ul>
								<div class="type-choice">
									<a class="button-search" type="button" onclick="javascript:searchDeviceLog();"><i class="fa fa-search"></i><span><fmt:message key="tiles.institution.policy.policy.search" /></span></a>
									<a class="button-search" type="button" onclick="javascript:cleanDeviceLog();"><i class="fa fa-trash-o"></i><span><fmt:message key="tiles.institution.policy.policy.clean" /></span></a>
								</div>
			   				</form>
			   			</div>
                     
                     <div class="table-responsive">
					    <div class="col-sm-12 device_table">
	                       <table class="table table-striped b-t b-light" id="deviceLog">
	                         <thead>
	                           <tr>
	                             <th><fmt:message key="tiles.views.customer.device.index.table.eventtype" /></th>
	                             <th><fmt:message key="tiles.views.customer.device.index.table.realname" /></th>
	                             <th><fmt:message key="tiles.views.customer.device.index.table.operatedate" /></th>
	                             <th><fmt:message key="tiles.views.customer.device.index.table.operatecontent" /></th>
	                           </tr>
	                         </thead>
	                         <tbody>
	                         	 
	                         </tbody>
	                       </table>
                       </div>
                     </div>
                   </div>
                 </div>
               </div>
             </div>
           </div>
         </div>
     </div>
</div>

