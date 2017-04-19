<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- BEGIN SIDEBAR -->
<div class="page-sidebar nav-collapse collapse">
	<c:if test="${user != null }">
		<c:set var="roleName" value="${sessionScope.user.role.roleName }"
			scope="page"></c:set>
		<c:import var="menus" charEncoding="utf-8" url="../../xml/menu.xml" />
		<c:if test="${url == null }">
			<c:set var="url" value="index.do"></c:set>
		</c:if>
		<c:if test="${url != null }">
			<c:set var="url" value="${fn:substring(url,1,fn:length(url)) }"
				scope="page"></c:set>
		</c:if>
		<x:parse var="doc" xml="${menus}" />
		<!-- BEGIN SIDEBAR MENU -->
		<ul class="page-sidebar-menu">
			<li>
				<div class="sidebar-toggler hidden-phone"></div>
			</li>
			<x:forEach select="$doc/menus/menu" var="menu" varStatus="i">
				<x:if
					select="$menu/authority='all' or contains($menu/authority,$pageScope:roleName)">
					<li <c:if test="${i.first }">class="start"</c:if>
						<x:if select="starts-with($url,$menu/@prefix)">class="active open"</x:if>>
						<a href=<x:out select="$menu/href" />> <i
							class=<x:out select="$menu/icon" />></i> <span class="title">
								<x:out select="$menu/name" />
						</span> <x:if select="$menu/menu">
								<span
									class="arrow <x:if select="starts-with($url,$menu/@prefix)">open </x:if>"></span>
							</x:if>
					</a> <x:if select="$menu/menu">
							<ul class="sub-menu">
								<x:forEach select="$menu/menu" var="second-menu">
									<x:if
										select="$second-menu/authority='all' or contains($second-menu/authority,$pageScope:roleName)">
										<x:set var="url" select="$pageScope:url" />
										<li
											class="<x:if select="starts-with($url,concat($menu/@prefix,$second-menu/@prefix))">active </x:if>">
											<a
											href=<x:out select="concat($menu/@prefix,concat($second-menu/@prefix,$second-menu/href))" />>
												<x:out select="$second-menu/name" />
										</a>
										</li>
									</x:if>
								</x:forEach>
							</ul>
						</x:if>
					</li>
				</x:if>
			</x:forEach>
		</ul>
		<!-- END SIDEBAR MENU -->
	</c:if>
</div>
<!-- END SIDEBAR -->