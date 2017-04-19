<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<spring:url value="/farm/shop/index" var="shopUrl" />
<spring:url value="/farm/logout" var="logoutUrl"/>
<spring:url value="/resources/platform/img/shop_logo.png" var="profilePic"/>
<spring:url value="/farm/personal/index" var="personalIndex"/>
<!--左侧导航开始-->
<nav class="navbar-default navbar-static-side" role="navigation">
     <div class="nav-close">
     	<i class="fa fa-times-circle"></i>
     </div>
     <div class="sidebar-collapse">
         <ul class="nav" id="side-menu">
             <li class="nav-header">
                 <div class="dropdown profile-element">
                     <span><img alt="image" class="img-circle" src="${profilePic}" width="64px" height="64px"/></span>
                     <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                         <span class="clear">
                         <span class="block m-t-xs"><strong class="font-bold">${USER_INSESSION.accountName}</strong></span>
                         <span class="text-muted text-xs block">${USER_INSESSION.realName}<!-- <b class="caret"></b> --></span>
                         </span>
                     </a>
                     <ul class="dropdown-menu animated fadeInRight m-t-xs">
                         <!-- <li><a class="J_menuItem" href="form_avatar.html">修改头像</a>
                         </li> -->
                        <%--  <li><a class="J_menuItem" ref="${personalIndex}">个人资料</a>
                         </li>
                         <li class="divider"></li>
                         <li><a href="${logoutUrl}">安全退出</a>
                         </li> --%>
                     </ul>
                 </div>
             </li>
             <li></li>
            <%--  <c:if test="${AUTHCATION_ROLE!=null&&AUTHCATION_ROLE.id==1}">
             <li>
                 <a href="#">
                     <i class="fa fa-home"></i>
                     <span class="nav-label">${menus[0].menuName}</span>
                     <span class="fa arrow"></span>
                 </a>
                 <ul class="nav nav-second-level">
                     <li>
                     	 <spring:url value="${menus[1].href}" var="warrantorUrl" />
                         <a class="J_menuItem" ref="${warrantorUrl}">${menus[1].menuName}</a>
                     </li>
                     <li>
                     	 <spring:url value="${menus[2].href }" var="registerprotocolUrl" />
                         <a class="J_menuItem" ref="${registerprotocolUrl}">${menus[2].menuName}</a>
                     </li>
                     <li>
                     	 <spring:url value="${menus[3].href }" var="freightUrl" />
                         <a class="J_menuItem" ref="${freightUrl}">${menus[3].menuName}</a>
                     </li>
                     <li>
                     	 <spring:url value="${menus[4].href }" var="logisticsUrl" />
                         <a class="J_menuItem" ref="${logisticsUrl}">${menus[4].menuName}</a>
                     </li>
                      <li>
                     	 <spring:url value="${menus[25].href }" var="wapUrl" />
                         <a class="J_menuItem" ref="${wapUrl}">${menus[25].menuName}</a>
                     </li>
                 </ul>

             </li>
             <li>
                 <a href="#">
                     <i class="fa fa fa-bar-chart-o"></i>
                     <span class="nav-label">${menus[5].menuName}</span>
                     <span class="fa arrow"></span>
                 </a>
                 <ul class="nav nav-second-level">
                 	<spring:url value="${menus[6].href}" var="kindUrl" />
                     <li>
                         <a class="J_menuItem" ref="${kindUrl}">${menus[6].menuName}</a>
                     </li>
                     <li>
                     	<spring:url value="${menus[7].href}" var="fshoplistUrl" />
                         <a class="J_menuItem" ref="${fshoplistUrl }">${menus[7].menuName}</a>
                     </li>
                     <li>
                     	 <spring:url value="${menus[8].href}" var="shopUrl" />
                         <a class="J_menuItem" ref="${shopUrl}">${menus[8].menuName}</a>
                     </li>
                 </ul>
             </li>
             </c:if>
                    <li>
                        <a href="mailbox.html"><i class="fa fa-envelope"></i> <span class="nav-label">${menus[9].menuName}</span> <span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                        <spring:url value="${menus[10].href}" var="authUrl" />
                            <li><a class="J_menuItem" ref="${authUrl }">${menus[10].menuName}</a>
                            </li>
                        </ul>
                    </li>
                    <c:if test="${AUTHCATION_ROLE!=null&&AUTHCATION_ROLE.id==1}">
                    <li>
                        <a href="#"><i class="fa fa-edit"></i> <span class="nav-label">${menus[12].menuName}</span><span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                         <spring:url value="${menus[13].href}" var="userlistUrl"></spring:url>
                     		<li><a class="J_menuItem" ref="${userlistUrl}">${menus[13].menuName}</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-desktop"></i> <span class="nav-label">${menus[14].menuName}</span><span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                        	<spring:url value="${menus[15].href}" var="roleUrl" />
                            <li><a class="J_menuItem" ref="${roleUrl}">${menus[15].menuName}</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-flask"></i> <span class="nav-label">${menus[16].menuName}</span><span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                        	<spring:url value="${menus[17].href}" var="shopUrl" />
                            <li><a class="J_menuItem" ref="${shopUrl}">${menus[17].menuName}</a>
                            </li>
                            <li><a class="J_menuItem" href="${menus[18].href}">${menus[18].menuName}</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-table"></i> <span class="nav-label">${menus[19].menuName}</span><span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li><a class="J_menuItem" ref="${menus[20].href}">${menus[20].menuName}</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-table"></i> <span class="nav-label">${menus[21].menuName}</span><span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                        	<spring:url value="${menus[22].href}" var="fOrderUrl" />
                            <li><a class="J_menuItem" ref="${fOrderUrl}">${menus[22].menuName}</a>
                            </li>
                            <spring:url value="${menus[27].href}" var="flistUrl" />
                            <li><a class="J_menuItem" ref="${flistUrl}">${menus[27].menuName}</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-flask"></i> <span class="nav-label">${menus[23].menuName}</span><span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                        	<spring:url value="${menus[24].href}" var="bGoodsUrl" />
                            <li><a class="J_menuItem" ref="${bGoodsUrl}">${menus[24].menuName}</a>
                            </li>
                        </ul>
                    </li>
                     <li>
                        <a href="#"><i class="fa fa-file-code-o"></i> <span class="nav-label">${menus[26].menuName}</span><span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <spring:url value="${menus[28].href}" var="paramUrl" />
                            <li><a class="J_menuItem" ref="${paramUrl}">${menus[28].menuName}</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-flask"></i> <span class="nav-label">${menus[29].menuName}</span><span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                        	<spring:url value="${menus[30].href}" var="settlementUrl" />
                            <li><a class="J_menuItem" ref="${settlementUrl}">${menus[30].menuName}</a>
                            </li>
                        </ul>
                    </li>
                	</c:if> --%>
                </ul>
            </div>
        </nav>
        <!--左侧导航结束-->
