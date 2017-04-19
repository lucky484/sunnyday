     <!--   页面未在项目中使用 暂不做国际化 -->
          <!--   页面未在项目中使用 暂不做国际化 -->
               <!--   页面未在项目中使用 暂不做国际化 -->
                    <!--   页面未在项目中使用 暂不做国际化 -->
                         <!--   页面未在项目中使用 暂不做国际化 -->
                              <!--   页面未在项目中使用 暂不做国际化 -->
                                   <!--   页面未在项目中使用 暂不做国际化 -->
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
				<a href="javascript:void(0);" class="btn btn-s-sm" onclick="javascript:loadCompliant();"><fmt:message key="tiles.views.customer.index.head.compliant"/></a>
				<!-- <a href="javascript:void(0);" class="btn btn-s-sm btn-primary" onclick="javascript:loadConfig();">配置描述文件</a> -->
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
                       <table class="table table-striped b-t b-light" id="config">
                         <thead>
                           <tr>
                             <th>策略名称</th>
                             <th>用户/部门/虚拟组/规则</th>
                             <th>安装状态</th>
                             <th>安装时间</th>
                           </tr>
                         </thead>
                         <tbody>
                         	<tr>
                         		<td>默认策略</td>
                         		<td>softtek</td>
                         		<td>待更新</td>
                         		<td>2015-12-19 00:43:11</td>
                         	</tr>
                         	<tr>
                         		<td>默认策略</td>
                         		<td>softtek</td>
                         		<td>待更新</td>
                         		<td>2015-12-19 00:43:11</td>
                         	</tr>
                         	<tr>
                         		<td>默认策略</td>
                         		<td>softtek</td>
                         		<td>待更新</td>
                         		<td>2015-12-19 00:43:11</td>
                         	</tr>
                         	<tr>
                         		<td>默认策略</td>
                         		<td>softtek</td>
                         		<td>待更新</td>
                         		<td>2015-12-19 00:43:11</td>
                         	</tr>
                         	<tr>
                         		<td>默认策略</td>
                         		<td>softtek</td>
                         		<td>待更新</td>
                         		<td>2015-12-19 00:43:11</td>
                         	</tr>
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

