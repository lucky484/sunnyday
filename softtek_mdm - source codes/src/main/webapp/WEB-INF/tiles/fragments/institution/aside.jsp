<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

	<aside class="bg-black aside-md hidden-print hidden-xs ${aside=='0'?'':'nav-xs'}" id="nav">          
      <section class="vbox">
        <section class="w-f scrollable">
          <div class="slim-scroll" data-height="auto" data-disable-fade-out="true" data-distance="0" data-size="10px" data-railOpacity="0.2">
            <div class="clearfix wrapper dk nav-user hidden-xs">
              <div class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                  <span class="thumb avatar pull-left m-r">   
                  <spring:url value="/resources/images/a0.png"  var="thumb"/>                    
                    <img src="${thumb}" class="dker" alt="...">
                    <i class="on md b-black"></i>
                  </span>
                  <span class="hidden-nav-xs clear">
                    <span class="block m-t-xs">
                      <strong class="font-bold text-lt">${softtek_manager.name}</strong> 
                      <b class="caret"></b>
                    </span>
                    <span class="text-muted text-xs block">
                    	<fmt:message key="tiles.fragments.institution.aside.welcome" />
					</span>
                  </span>
                </a>
                <ul class="dropdown-menu animated fadeInRight m-t-xs">                      
                  <li>
                    <span class="arrow top hidden-nav-xs"></span>
                    <spring:url value="/institution/personal"  var="personalUrl"/> 
                    <a href="${personalUrl}"><fmt:message key="tiles.fragments.institution.aside.persioncenter"/></a>
                  </li>
                  <li class="divider"></li>
                  <li>
                   <spring:url value="/j_spring_security_logout" var="logout" />
              		<a href="${logout}"><fmt:message key="tiles.fragments.institution.aside.logout"/></a>
                  </li>
                </ul>
              </div>
            </div>
            <!-- nav -->        
           <nav id="aside-nav" class="nav-primary hidden-xs">
             <ul class="nav nav-main" data-ride="collapse">
              <c:if test="${menu[1].isshow==-1}">
               <li class="" data-menu="1" onclick="activeMenu(1);">
               	 <a href="#" class="">
                   <span class="pull-right text-muted">
                     <i class="i i-circle-sm-o text"></i>
                     <i class="i i-circle-sm text-active"></i>
                   </span>
                   <i class="fa fa-user icon fa-2x">
                   </i>
	               <span class="font-bold">${menu[1].name}</span>
                 </a>
                 <ul class="nav dk">
                  <c:if test="${menu[2].isshow==-1}">
                   <li class="" data-sub="2" onclick="activeSub(2);">
                     <spring:url value="/institution/org" var="orgUrl" />         
                     <a href="${orgUrl}" class="">                                                        
                       <i class="i i-dot"></i>
                       <span>${menu[2].name}</span>
                     </a>
                   </li>
                   </c:if>
                   <c:if test="${menu[5].isshow==-1}">
                   <li class="" data-sub="5" onclick="activeSub(5);">
                    <spring:url value="/institution/vtl" var="vtlUrl" />
                     <a href="${vtlUrl}" class="auto">                                                        
                       <i class="i i-dot"></i>
                       <span>${menu[5].name}</span>
                     </a>
                   </li>
                   </c:if>
                   <c:if test="${menu[8].isshow==-1}">
                   <li class="" data-sub="8" onclick="activeSub(8);">
                   	 <spring:url value="/institution/user" var="userUrl" />
                     <a href="${userUrl}" class="auto">                                                        
                       <i class="i i-dot"></i>
                       <span>${menu[8].name}</span>
                     </a>
                   </li>
                    </c:if>
                    <c:if test="${menu[9].isshow==-1}">
                   <li class="" data-sub="9" onclick="activeSub(9);">
                    <spring:url value="/institution/policy" var="policyUrl" />
                     <a href="${policyUrl}" class="auto">                                                        
                       <i class="i i-dot"></i>
                       <span>${menu[9].name}</span>
                     </a>
                   </li>
                   </c:if>
                   <c:if test="${menu[10].isshow==-1}">
                   <li class="" data-sub="10" onclick="activeSub(10);">
                     <spring:url value="/institution/urole" var="uRoleUrl" />
                     <a href="${uRoleUrl}" class="auto">                                                        
                       <i class="i i-dot"></i>
                       <span>${menu[10].name}</span>
                     </a>
                   </li>
                   </c:if>
                   <c:if test="${menu[11].isshow==-1}">
                   <li class="" data-sub="11" onclick="activeSub(11);">
                   <spring:url value="/institution/dptmanager" var="dptmanUrl" />
                      <a href="${dptmanUrl}" class="auto">                                                         
                       <i class="i i-dot"></i>
                       <span>${menu[11].name}</span>
                     </a>
                   </li>
                   </c:if>
                 </ul>
               </li>
                </c:if>
                <c:if test="${menu[13].isshow==-1}">
               <li class="" data-menu="13" onclick="activeMenu(13);">
                 <a href="#" class="auto">
                   <span class="pull-right text-muted">
                     <i class="i i-circle-sm-o text"></i>
                     <i class="i i-circle-sm text-active"></i>
                   </span>
                   <i class="fa fa-mobile icon fa-2x">
                   </i>
                   <span class="font-bold">${menu[13].name}</span>
                 </a>
                 <ul class="nav dk">
                  <c:if test="${menu[14].isshow==-1}">
                   <li class="" data-sub="14" onclick="activeSub(14);">
                     <spring:url value="/institution/device/policy" var="devicePolicyUrl" />
                     <a href="${devicePolicyUrl}" class="auto" >                                                        
                       <i class="i i-dot"></i>
                       <span>${menu[14].name}</span>
                     </a>
                   </li>
                   </c:if>
                    <c:if test="${menu[15].isshow==-1}">
                    <spring:url value="/institution/device/rules" var="deviceRuleUrl" />
                   <li class="" data-sub="15" onclick="activeSub(15);">
                     <a href="${deviceRuleUrl}" class="auto">                            
                       <i class="i i-dot"></i>
                       <span>${menu[15].name}</span>
                     </a>
                   </li>
                    </c:if>
                    <c:if test="${menu[16].isshow==-1}">
                   <li class="" data-sub="16" onclick="activeSub(16);">
 					<spring:url value="/institution/deviceManager" var="deviceManager" />
                     <a href="${deviceManager}" class="auto">                                                        
                       <i class="i i-dot"></i>
                       <span>${menu[16].name}</span>
                     </a>
                   </li>
                   </c:if>
                 </ul>
               </li>
               </c:if>
               <c:if test="${menu[20].isshow==-1}">
               <li  class="" data-menu="20" onclick="activeMenu(20);">
                 <a href="javascript:void(0);" class="auto">
                   <span class="pull-right text-muted">
                     <i class="i i-circle-sm-o text"></i>
                     <i class="i i-circle-sm text-active"></i>
                   </span>
                   <i class="fa fa-earth icon fa-2x">
                   </i>
                   <span class="font-bold">${menu[20].name}</span>
                 </a>
                 <ul class="nav dk">
                   <c:if test="${menu[51].isshow==-1}">
                   <li class="" data-sub="51" onclick="activeSub(51);">
                    <spring:url value="/institution/application" var="application" />
                     <a href="${application}" class="auto">                                                        
                       <i class="i i-dot"></i>
                       <span>${menu[51].name}</span>
                     </a>
                   </li>
                   </c:if>                 
                  <c:if test="${menu[21].isshow==-1}">
                   <li class="" data-sub="21" onclick="activeSub(21);">
                    <spring:url value="/institution/nameList" var="nameListUrl" />
                     <a href="${nameListUrl}" class="auto">                                                        
                       <i class="i i-dot"></i>
                       <span>${menu[21].name}</span>
                     </a>
                   </li>
                   </c:if>
                 </ul>
               </li>
               </c:if>
               
              <c:if test="${menu[41].isshow==-1}">
               <li class="" data-menu="41" onclick="activeMenu(41);">
                 <a href="#" class="auto">
                   <span class="pull-right text-muted">
                     <i class="i i-circle-sm-o text"></i>
                     <i class="i i-circle-sm text-active"></i>
                   </span>
                   <i class="fa fa-globe icon fa-2x">
                   </i>
                   <span class="font-bold">${menu[41].name}</span>
                 </a>
                 <ul class="nav dk">
                  <c:if test="${menu[42].isshow==-1}">
                   <li class="" data-sub="42" onclick="activeSub(42);">
                     <spring:url value="/institution/netbehavior/blackwitelist" var="blackWhiteListUrl" />
                     <a href="${blackWhiteListUrl}" class="auto" >                                                        
                       <i class="i i-dot"></i>
                       <span>${menu[42].name}</span>
                     </a>
                   </li>
                  </c:if>
                  <c:if test="${menu[43].isshow==-1}">
                    <spring:url value="/institution/netbehavior/log" var="netBehaviorLogUrl" />
                   <li class="" data-sub="43" onclick="activeSub(43);">
                     <a href="${netBehaviorLogUrl}" class="auto">                            
                       <i class="i i-dot"></i>
                       <span>${menu[43].name}</span>
                     </a>
                   </li>
                  </c:if>
                 </ul>
               </li>
               </c:if>
               <c:if test="${menu[47].isshow==-1}">
               <li class="" data-menu="47" onclick="activeMenu(47);">
                 <a href="#" class="auto">
                   <span class="pull-right text-muted">
                     <i class="i i-circle-sm-o text"></i>
                     <i class="i i-circle-sm text-active"></i>
                   </span>
                   <i class="fa fa-commenting-o icon fa-2x">
                   </i>
                   <span class="font-bold">${menu[47].name}</span>
                 </a>
                 <ul class="nav dk">
                    <li class="" data-sub="48" onclick="activeSub(48);">
                	<spring:url value="/institution/picAndTxtMeg" var="picAndTxtMeg" />
                     <a href="${picAndTxtMeg}" class="auto">
                       <i class="i i-dot"></i>
                       <span>${menu[48].name}</span>
                     </a>
                   </li>
                 </ul>
               </li>
               </c:if>
                <c:if test="${menu[30].isshow==-1}">
               <li class="" data-menu="30" onclick="activeMenu(30);">
                 <a href="#" class="auto">
                   <span class="pull-right text-muted">
                     <i class="i i-circle-sm-o text"></i>
                     <i class="i i-circle-sm text-active"></i>
                   </span>
                   <i class="glyphicon glyphicon-stats icon fa-2x">
                   </i>
                   <span class="font-bold">${menu[30].name}</span>
                 </a>
                 <ul class="nav dk">
                    <li class="" data-sub="31" onclick="activeSub(31);">
                	<spring:url value="/institution/systemStatistics/user" var="user" />
                     <a href="${user}" class="auto">
                       <i class="i i-dot"></i>
                       <span>${menu[31].name}</span>
                     </a>
                   </li>
                 </ul>
                  <ul class="nav dk">
                    <li class="" data-sub="32" onclick="activeSub(32);">
                    <spring:url value="/institution/systemStatistics/device" var="device" />
                     <a href="${device}" class="auto">
                       <i class="i i-dot"></i>
                       <span>${menu[32].name}</span>
                     </a>
                   </li>
                 </ul>
                 <ul class="nav dk">
                    <li class="" data-sub="33" onclick="activeSub(33);">
                    <spring:url value="/institution/systemStatistics/flux" var="flux" />
                     <a href="${flux}" class="auto">
                       <i class="i i-dot"></i>
                       <span>${menu[33].name}</span>
                     </a>
                   </li>
                 </ul>
                 <ul class="nav dk">
                    <li class="" data-sub="53" onclick="activeSub(53);">
                	<spring:url value="/institution/systemStatistics/flux/abnormal" var="fluxabnormal" />
                     <a href="${fluxabnormal}" class="auto">
                       <i class="i i-dot"></i>
                       <span>${menu[53].name}</span>
                     </a>
                   </li>
                 </ul>
                  <ul class="nav dk">
                    <li class="" data-sub="52" onclick="activeSub(52);">
                	<spring:url value="/institution/userTimes" var="usertime" />
                     <a href="${usertime}" class="auto">
                       <i class="i i-dot"></i>
                       <span>${menu[52].name}</span>
                     </a>
                   </li>
                 </ul>
                 <ul class="nav dk">
                    <li class="" data-sub="55" onclick="activeSub(55);">
                    <spring:url value="/institution/net" var="net" />
                     <a href="${net}" class="auto">
                       <i class="i i-dot"></i>
                       <span>${menu[55].name}</span>
                     </a>
                   </li>
                 </ul>
               </li>
               </c:if>
               <c:if test="${menu[44].isshow==-1}">
               <li class="" data-menu="44" onclick="activeMenu(44);">
                 <a href="#" class="auto">
                   <span class="pull-right text-muted">
                     <i class="i i-circle-sm-o text"></i>
                     <i class="i i-circle-sm text-active"></i>
                   </span>
                   <i class="fa fa-cogs icon fa-2x">
                   </i>
                   <span class="font-bold">${menu[44].name}</span>
                 </a>
                 <ul class="nav dk">
                    <li class="" data-sub="45" onclick="activeSub(45);">
                	<spring:url value="/institution/sysmanageLog" var="sysmanagerLog" />
                     <a href="${sysmanagerLog}" class="auto">
                       <i class="i i-dot"></i>
                       <span>${menu[45].name}</span>
                     </a>
                   </li>
                 </ul>
                  <ul class="nav dk">
                    <li class="" data-sub="49" onclick="activeSub(49);">
                    <spring:url value="/institution/clientConfig" var="clientConfig" />
                     <a href="${clientConfig}" class="auto">
                       <i class="i i-dot"></i>
                       <span>${menu[49].name}</span>
                     </a>
                   </li>
                 </ul>
                 <ul class="nav dk">
                    <li class="" data-sub="54" onclick="activeSub(54);">
                    <spring:url value="/institution/words" var="words" />
                     <a href="${words}" class="auto">
                       <i class="i i-dot"></i>
                       <span>${menu[54].name}</span>
                     </a>
                   </li>
                 </ul>
               </li>
               </c:if>
             </ul>
           </nav>
           <!-- / nav -->
         </div>
       </section>
         <footer class="footer hidden-xs no-padder text-center-nav-xs">
              <a id="nav-footer-circle" href="#nav" onclick="changeLayout()" data-toggle="class:nav-xs" class="btn btn-icon icon-muted btn-inactive m-l-xs m-r-xs ${aside=='0'?'':'active'}">
                <i class="i i-circleleft text"></i>
                <i class="i i-circleright text-active"></i>
              </a>
            </footer>
     </section>
   </aside>
