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
				<a href="javascript:void(0);" class="btn btn-s-sm " onclick="javascript:loadIndex();"><fmt:message key="tiles.views.customer.index.head.deviceinfo"/></a>
				<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadAppList();"><fmt:message key="tiles.views.customer.index.head.applist"/></a>
				<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadposition();"><fmt:message key="tiles.views.customer.index.head.position"/></a>
				<a href="javascript:void(0);" class="btn btn-s-sm btn-primary" onclick="javascript:loadCompliant();"><fmt:message key="tiles.views.customer.index.head.compliant"/></a>
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
	</div>
	<div id="content">
         <div class="hbox stretch">
           <div>
             <div class="vbox">
               <div class="scrollable padder">
                 <div class="panel panel-default">
                   <div class="panel-body">
                     <div class="table-responsive">
                       <table class="table table-striped b-t b-light" id="compliantList">
                         <thead>
                           <tr>
                           	 <th>id</th>
                             <th><fmt:message key="tiles.views.customer.compliant.index.table.status"/></th>
                             <th><fmt:message key="tiles.views.customer.compliant.index.table.name"/></th>
                             <th><fmt:message key="tiles.views.customer.compliant.index.table.violate_time"/></th>
                             <th><fmt:message key="tiles.views.customer.compliant.index.table.next_check_time"/></th>
                             <th><fmt:message key="tiles.views.customer.compliant.index.table.violate_manipulate"/></th>
                            <%--  <th><fmt:message key="tiles.views.customer.compliant.index.table.operation"/></th> --%>
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

