<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<aside
	class="bg-black aside-md hidden-print hidden-xs ${aside=='0'?'':'nav-xs'}"
	id="nav">
	<section class="vbox">
		<section class="w-f scrollable">
			<div class="slim-scroll" data-height="auto"
				data-disable-fade-out="true" data-distance="0" data-size="10px"
				data-railOpacity="0.2">
				<div class="clearfix wrapper dk nav-user hidden-xs">
					<div class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"> <span
							class="thumb avatar pull-left m-r"> <spring:url
									value="/resources/images/a0.png" var="thumb" /> <img
								src="${thumb}" class="dker" alt="..."> <i
								class="on md b-black"></i>
						</span> <span class="hidden-nav-xs clear"> <span
								class="block m-t-xs"> <strong class="font-bold text-lt">${softtek_manager.name}</strong>
									<b class="caret"></b>
							</span> <span class="text-muted text-xs block"> <fmt:message key="jsp.fragments.admin.aside.welcome.label"/> </span>
						</span>
						</a>
						<ul class="dropdown-menu animated fadeInRight m-t-xs">
							<li><span class="arrow top hidden-nav-xs"></span> <spring:url value="/admin/personal"
								var="personalUrl" /> <a href="${personalUrl}"><fmt:message key="jsp.fragments.admin.aside.personal.label"/></a></li>
							<li class="divider"></li>
							<li><spring:url value="/j_spring_security_logout"
									var="logout" /> <a href="${logout}"><fmt:message key="jsp.fragments.admin.aside.logout.label"/></a></li>
						</ul>
					</div>
				</div>
				<!-- nav -->
				<nav class="nav-primary hidden-xs" id="aside-nav">
					<ul class="nav nav-main" data-ride="collapse">
					<c:if test="${islicense==true}">
						<li class="" data-menu="1" onclick="activeMenu(1);">
							<a href="javascript:void(0);" class=""> 
								<span class="pull-right text-muted"> <i
										class="i i-circle-sm-o text"></i> <i
										class="i i-circle-sm text-active"></i>
								</span> <i class="fa fa-sitemap ls fa-2x"></i> 
								<span class="font-bold"><fmt:message key="jsp.fragments.admin.aside.org.span"/></span>
							</a>
							<ul class="nav dk">
								<li data-sub="2" onclick="activeSub(2);">
								<spring:url value="/admin" var="organizationUrl"/>
								<a href="${organizationUrl}" class="auto"> 
									<i class="i i-dot"></i>
									<span><fmt:message key="jsp.fragments.admin.aside.org.span"/></span>
								</a></li>
								<li data-sub="3" onclick="activeSub(3);">
								<spring:url value="/admin/index/manager" var="managerUrl"/>
								<a href="${managerUrl}" class="auto"> 
									<i class="i i-dot"></i> 
									<span><fmt:message key="jsp.fragments.admin.aside.manager.span"/></span>
								</a></li>
							</ul></li>
						</c:if>
						<li class="" data-menu="4" onclick="activeMenu(4);">
							<a href="javascript:void(0);"> 
							<span class="pull-right text-muted"> <i
									class="i i-circle-sm-o text"></i> <i
									class="i i-circle-sm text-active"></i>
							</span> <i class="fa fa-cog fa-fw fa-2x"></i> 
							<span class="font-bold"><fmt:message key="jsp.fragments.admin.aside.sysmangager.span"/></span>
						</a>
							<ul class="nav dk">
							<c:if test="${islicense==true}">
								<li data-sub="5" onclick="activeSub(5);">
								<spring:url value="/admin/sysmanageLog" var="sysmanagerLog1" />
									<a href="${sysmanagerLog1}" class="auto"> 
										<i class="i i-dot"></i>
										<span><fmt:message key="jsp.fragments.admin.aside.logmangager.span"/></span>
									</a></li>
									</c:if>
								<li data-sub="6" onclick="activeSub(6);">
									<spring:url value="/system/license/manager" var="licenseManagerUrl"/>
									<a href="${licenseManagerUrl}" class="auto"> 
										<i class="i i-dot"></i> 
										<span><fmt:message key="jsp.fragments.admin.aside.licensemangager.span"/></span>
									</a></li>
									<c:if test="${islicense==true}">
								 <li data-sub="7" onclick="activeSub(7);">
								    <spring:url value="/admin/clientManager" var="clientManager"/>
									<a href="${clientManager}" class="auto"> 
										<i class="i i-dot"></i> 
										<span><fmt:message key="jsp.fragments.admin.aside.clientmangager.span"/></span>
									</a>
								</li> 
							<!--	<li data-sub="8" onclick="activeSub(8);">
									<a href="javascript:void(0);" class="auto"> 
										<i class="i i-dot"></i> 
										<span>数据参数设置</span>
									</a></li>-->
								<%-- <li data-sub="9" onclick="activeSub(9);">
								<spring:url value="/admin/backup/setting" var="backupsettingUrl"/>
								<a href="${backupsettingUrl}" class="auto"> 
									<i class="i i-dot"></i> 
									<span>数据备份</span>
								</a></li> --%>
								
								<li data-sub="10" onclick="activeSub(10);">
									<spring:url value="/system/param/manager" var="paramManagerUrl"/>
									<a href="${paramManagerUrl}" class="auto"> 
										<i class="i i-dot"></i> 
										<span><fmt:message key="jsp.fragments.admin.aside.parammangager.span"/></span>
								</a></li>
								</c:if>
							</ul></li>
					</ul>
				</nav>
				<!-- / nav -->
			</div>
		</section>
	</section>
</aside>
