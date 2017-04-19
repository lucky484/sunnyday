<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spring:url value="/institution/deviceManager" var="deviceManagerUrl" />
<div  class="row allrow <c:choose> <c:when test="${idlist==null}">org_manager</c:when><c:otherwise>dep_manager</c:otherwise></c:choose>">
	<div class="col-lg-7 user_list">
	<c:if test="${deadtime!=null}">
		<c:choose>
			<c:when test="${softtek_manager.user==null}">
			<div class="alert alert-danger m-b-none m-t-n-lg">
			</c:when>
			<c:otherwise>
			<div class="alert alert-danger m-b-n-md">
			</c:otherwise>
		</c:choose>
      <fmt:message key="tiles.views.index.license.warning">
      	<fmt:param value="${deadtime}" />
      </fmt:message>
    </div>
	</c:if>
		<div class="col-lg-12 part1">
			<section class="row text-center box-shadow m-t-n-md">
				<div class="col-xs-3 bg-default dk lter r-l">
					<div class="wrapper">
						<spring:url value="/institution/user" var="userUrl" />
						<a href="${userUrl}" data-toggle="tooltip" data-placement="bottom" title=""
							data-original-title='<fmt:message key="web.institution.index.jsp.usercount.label"/>' onclick="clickUrl(1,8)"> <i
							class="fa fa-users fa fa-3x m-t m-b-sm text-primary"></i></a>
						<p class="text-muted font-bold"><fmt:message key="web.institution.index.jsp.usercount.label"/></p>
						<div class="h4 font-bold">
							<a href="${userUrl}" onclick="clickUrl(1,8)">${map.userCount}<fmt:message key="web.institution.index.jsp.unit1"/></a>
						</div>
					</div>
				</div>
				<div class="col-xs-3 bg-default lt r-r">
					<div class="wrapper">
						<a href="${deviceManagerUrl}" data-toggle="tooltip" data-placement="bottom" title=""
							data-original-title='<fmt:message key="tiles.excel.export.devicestatistics.sheet.title.deivetotal"/>' onclick="clickUrl(1,9)">
							<i class="fa fa-mobile fa fa-3x m-t m-b-sm text-primary" ></i>
							 
						</a>
						<p class="text-muted font-bold"><fmt:message key="tiles.excel.export.devicestatistics.sheet.title.deivetotal"/></p>
						<div class="h4 font-bold">
							<a href="${deviceManagerUrl}" onclick="clickUrl(13,16)">${map.deviceCount}<fmt:message key="web.institution.index.script.unit"/></a>
						</div>
					</div>
				</div>
				<div class="col-xs-3 bg-default lt">
					<div class="wrapper">
					<spring:url value="/institution/device/policy" var="devicePolicyUrl" />
						<a href="${devicePolicyUrl}" data-toggle="tooltip" data-placement="bottom" title=""
							data-original-title='<fmt:message key="web.institution.index.jsp.devicepolicy.label"/>' onclick="clickUrl(13,14)"> <i
							class="fa fa-file-text fa fa-3x m-t m-b-sm text-primary"></i></a>
						<p class="text-muted font-bold"><fmt:message key="web.institution.index.jsp.devicepolicy.label"/></p>
						<div class="h4 font-bold">
							<a href="${devicePolicyUrl}" onclick="clickUrl(13,14)">${map.devicePolicyCount}<fmt:message key="web.institution.index.jsp.unit2"/></a>
						</div>
					</div>
				</div>
				<div class="col-xs-3 bg-default lt r-r">
					<div class="wrapper">
					<spring:url value="/institution/device/rules" var="deviceRulesUrl" />
						<a href="${deviceRulesUrl}" data-toggle="tooltip" data-placement="bottom" title=""
							data-original-title='<fmt:message key="web.institution.index.jsp.devicerole.label"/>' onclick="clickUrl(13,15)"> <i
							class="glyphicon glyphicon-th-large i-3x m-t m-b-sm text-primary"></i>
						</a>
						<p class="text-muted font-bold"><fmt:message key="web.institution.index.jsp.devicerole.label"/></p>
						<div class="h4 font-bold">
							<a href="${deviceRulesUrl}" onclick="clickUrl(13,15)">${map.deviceRoleCount}<fmt:message key="web.institution.index.jsp.unit2"/></a>
						</div>
					</div>
				</div>
				
			</section>
		</div>
		
		
		
		
		<div class="col-lg-12 part2">
			<section class="panel panel-info col-lg-4 wrapper m-t">
				<div class="panel-body">
					<a href="${deviceManagerUrl}" onclick="clickUrl(13,16,0)" class="thumb pull-right m-l m-t-xs"> <i
						class="fa fa-mobile-phone fa-5x text-info"></i>
					</a>
					<div class="clear">
						<a href="${deviceManagerUrl}" onclick="clickUrl(13,16,0)" class="h2  desc_size  text-info">${map.monitoredCount}</a> <small
							class="block text-info m-t text-ellipsis"
							data-toggle="tooltip" data-placement="bottom" title=""
							data-original-title='<fmt:message key="web.institution.index.jsp.count.monitored.label"/>'><fmt:message key="web.institution.index.jsp.count.monitored.label"/></small>
					</div>
				</div>
			</section>
			
			<section class="panel  <c:choose> <c:when test="${map.trusteeshipCount eq 0}">panel-info</c:when> 
            											<c:otherwise>panel-danger</c:otherwise> 
            											</c:choose> col-lg-4 wrapper m-t">
				<div class="panel-body">
					<a href="${deviceManagerUrl}" onclick="clickUrl(13,16,2)" class="thumb pull-right m-l m-t-xs"> <i
						class="fa fa-mobile-phone fa-5x <c:choose> 
														<c:when test="${map.trusteeshipCount eq 0}">text-info</c:when> 
            											<c:otherwise>text-danger</c:otherwise> 
            											</c:choose>"></i>
					</a>
					<div class="clear">
						<a href="${deviceManagerUrl}" onclick="clickUrl(13,16,2)" class="h2 desc_size <c:choose> 
														<c:when test="${map.trusteeshipCount eq 0}">text-info</c:when> 
            											<c:otherwise>text-danger</c:otherwise> 
            											</c:choose>">${map.trusteeshipCount}</a> <small
							class="block <c:choose> 
														<c:when test="${map.trusteeshipCount eq 0}">text-info</c:when> 
            											<c:otherwise>text-danger</c:otherwise> 
            											</c:choose> m-t text-ellipsis"
            											data-toggle="tooltip" data-placement="bottom" title=""
							data-original-title='<fmt:message key="web.institution.index.jsp.count.trusteeship.label"/>'><fmt:message key="web.institution.index.jsp.count.trusteeship.label"/></small>
					</div>
				</div>
			</section>
			
			<%-- <!-- 违反系统规则的设备数 -->
			<section class="panel <c:choose><c:when test="${map.irrCount eq 0}">panel-info</c:when><c:otherwise>panel-danger</c:otherwise></c:choose> col-lg-4 wrapper m-t">
				<div class="panel-body">
					<a href="${deviceManagerUrl}" onclick="clickUrl(13,16)" class="thumb pull-right m-l m-t-xs"> <i
						class="fa fa-mobile-phone fa-5x <c:choose> 
														<c:when test="${map.irrCount eq 0}">text-info</c:when> 
            											<c:otherwise>text-danger</c:otherwise> 
            											</c:choose>"></i>
					</a>
					<div class="clear">
						<a href="${deviceManagerUrl}" onclick="clickUrl(13,16)" class="h2 desc_size <c:choose> 
														<c:when test="${map.irrCount eq 0}">text-info</c:when> 
            											<c:otherwise>text-danger</c:otherwise> 
            											</c:choose>">${map.irrCount}</a> <small
							class="block <c:choose> 
														<c:when test="${map.irrCount eq 0}">text-info</c:when> 
            											<c:otherwise>text-danger</c:otherwise> 
            											</c:choose> m-t text-ellipsis" 
            											data-toggle="tooltip" data-placement="bottom" title=""
							data-original-title='<fmt:message key="web.institution.index.jsp.count.irr.label"/>' ><fmt:message key="web.institution.index.jsp.count.irr.label"/></small>
					</div>
				</div>
			</section> --%>
			
		<!-- 待监控 -->
			<section class="panel <c:choose> 
														<c:when test="${map.notMonitoredCount eq 0}">panel-info</c:when> 
            											<c:otherwise>panel-danger</c:otherwise> 
            											</c:choose> col-lg-4 wrapper m-t">
				<div class="panel-body">
					<a href="${deviceManagerUrl}" onclick="clickUrl(13,16)" class="thumb pull-right m-l m-t-xs"> <i
						class="fa fa-mobile-phone fa-5x <c:choose> 
														<c:when test="${map.notMonitoredCount eq 0}">text-info</c:when> 
            											<c:otherwise>text-danger</c:otherwise> 
            											</c:choose>"></i>
					</a>
					<div class="clear">
						<a href="${deviceManagerUrl}" onclick="clickUrl(13,16,1)" class="h2 desc_size <c:choose> 
														<c:when test="${map.notMonitoredCount eq 0}">text-info</c:when> 
            											<c:otherwise>text-danger</c:otherwise> 
            											</c:choose>">${map.notMonitoredCount}</a> <small
							class="block <c:choose> 
														<c:when test="${map.notMonitoredCount eq 0}">text-info</c:when> 
            											<c:otherwise>text-danger</c:otherwise> 
            											</c:choose> m-t text-ellipsis"
            											data-toggle="tooltip" data-placement="bottom" title=""
							data-original-title='<fmt:message key="web.institution.index.jsp.count.notMonitored.label"/>'><fmt:message key="web.institution.index.jsp.count.notMonitored.label"/></small>
					</div>
				</div>
			</section>
			
			  <!-- root设备 -->
	   
			<section class="panel <c:choose><c:when test="${map.breakStatusCount eq 0}">panel-info</c:when><c:otherwise>panel-danger</c:otherwise></c:choose> col-lg-4 wrapper m-t-none">
				<div class="panel-body">
					<a href="${deviceManagerUrl}" onclick="clickUrl(13,16)" class="thumb pull-right m-l m-t-xs"> <i
						class="fa fa-mobile-phone fa-5x <c:choose> 
														<c:when test="${map.breakStatusCount eq 0}">text-info</c:when> 
            											<c:otherwise>text-danger</c:otherwise> 
            											</c:choose>"></i>
					</a>
					<div class="clear">
						<a href="${deviceManagerUrl}" onclick="clickUrl(13,16)" class="h2 desc_size <c:choose> 
            					<c:when test="${map.breakStatusCount eq 0}">text-info</c:when> 
            					 <c:otherwise>text-danger</c:otherwise> 
            					</c:choose>">${map.breakStatusCount}</a> <small
							class="block<c:choose> 
            					<c:when test="${map.breakStatusCount eq 0}">text-info</c:when> 
            					 <c:otherwise>text-danger</c:otherwise> 
            					</c:choose> m-t text-ellipsis" data-toggle="tooltip" data-placement="bottom" title=""
							data-original-title='<fmt:message key="web.institution.index.jsp.count.break.label"/>'><fmt:message key="web.institution.index.jsp.count.break.label"/></small>
					</div>
				</div>
			</section> 
			
			
			<section class="panel <c:choose><c:when test="${map.irrCount eq 0}">panel-info</c:when><c:otherwise>panel-danger</c:otherwise></c:choose> col-lg-4 wrapper m-t-none">
				<div class="panel-body">
					<a href="${deviceManagerUrl}" onclick="clickUrl(13,16)" class="thumb pull-right m-l m-t-xs"> <i
						class="fa fa-mobile-phone fa-5x <c:choose> 
														<c:when test="${map.irrCount eq 0}">text-info</c:when> 
            											<c:otherwise>text-danger</c:otherwise> 
            											</c:choose>"></i>
					</a>
					<div class="clear">
						<a href="${deviceManagerUrl}" onclick="clickUrl(13,16)" class="h2 desc_size <c:choose> 
														<c:when test="${map.irrCount eq 0}">text-info</c:when> 
            											<c:otherwise>text-danger</c:otherwise> 
            											</c:choose>">${map.irrCount}</a> <small
							class="block <c:choose> 
														<c:when test="${map.irrCount eq 0}">text-info</c:when> 
            											<c:otherwise>text-danger</c:otherwise> 
            											</c:choose> m-t text-ellipsis" 
            											data-toggle="tooltip" data-placement="bottom" title=""
							data-original-title='<fmt:message key="web.institution.index.jsp.count.irr.label"/>' ><fmt:message key="web.institution.index.jsp.count.irr.label"/></small>
					</div>
				</div>
			</section> 
			<%-- <section class="panel  <c:choose> 
														<c:when test="${map.trusteeshipCount eq 0}">panel-info</c:when> 
            											<c:otherwise>panel-danger</c:otherwise> 
            											</c:choose> col-lg-4 wrapper m-t-none">
				<div class="panel-body">
					<a href="${deviceManagerUrl}" onclick="clickUrl(13,16,2)" class="thumb pull-right m-l m-t-xs"> <i
						class="fa fa-mobile-phone fa-5x <c:choose> 
														<c:when test="${map.trusteeshipCount eq 0}">text-info</c:when> 
            											<c:otherwise>text-danger</c:otherwise> 
            											</c:choose>"></i>
					</a>
					<div class="clear">
						<a href="${deviceManagerUrl}" onclick="clickUrl(13,16,2)" class="h2 desc_size <c:choose> 
														<c:when test="${map.trusteeshipCount eq 0}">text-info</c:when> 
            											<c:otherwise>text-danger</c:otherwise> 
            											</c:choose>">${map.trusteeshipCount}</a> <small
							class="block <c:choose> 
														<c:when test="${map.trusteeshipCount eq 0}">text-info</c:when> 
            											<c:otherwise>text-danger</c:otherwise> 
            											</c:choose> m-t text-ellipsis"
            											data-toggle="tooltip" data-placement="bottom" title=""
							data-original-title='<fmt:message key="web.institution.index.jsp.count.trusteeship.label"/>'><fmt:message key="web.institution.index.jsp.count.trusteeship.label"/></small>
					</div>
				</div>
			</section> --%>
			<%-- <section class="panel panel-info col-lg-4 wrapper m-t-none">
				<div class="panel-body">
					<a href="${deviceManagerUrl}" onclick="clickUrl(13,16,0)" class="thumb pull-right m-l m-t-xs"> <i
						class="fa fa-mobile-phone fa-5x text-info"></i>
					</a>
					<div class="clear">
						<a href="${deviceManagerUrl}" onclick="clickUrl(13,16,0)" class="h2  desc_size  text-info">${map.monitoredCount}</a> <small
							class="block text-info m-t text-ellipsis"
							data-toggle="tooltip" data-placement="bottom" title=""
							data-original-title='<fmt:message key="web.institution.index.jsp.count.monitored.label"/>'><fmt:message key="web.institution.index.jsp.count.monitored.label"/></small>
					</div>
				</div>
			</section> --%>
			<section class="panel <c:choose> 
														<c:when test="${map.deviceLostCount eq 0}">panel-info</c:when> 
            											<c:otherwise>panel-danger</c:otherwise> 
            											</c:choose> col-lg-4 wrapper m-t-none">
				<div class="panel-body">
					<a href="${deviceManagerUrl}" onclick="clickUrl(13,16,3)" class="thumb pull-right m-l m-t-xs"> <i
						class="fa fa-mobile-phone fa-5x <c:choose> 
														<c:when test="${map.deviceLostCount eq 0}">text-info</c:when> 
            											<c:otherwise>text-danger</c:otherwise> 
            											</c:choose>"></i>
					</a>
					<div class="clear">
						<a href="${deviceManagerUrl}" onclick="clickUrl(13,16,3)" class="h2 desc_size <c:choose> 
														<c:when test="${map.deviceLostCount eq 0}">text-info</c:when> 
            											<c:otherwise>text-danger</c:otherwise> 
            											</c:choose>">${map.deviceLostCount}</a> <small
							class="block <c:choose> 
														<c:when test="${map.deviceLostCount eq 0}">text-info</c:when> 
            											<c:otherwise>text-danger</c:otherwise> 
            											</c:choose> m-t text-ellipsis"
            											data-toggle="tooltip" data-placement="bottom" title=""
							data-original-title='<fmt:message key="web.institution.index.jsp.count.deviceLost.label"/>'><fmt:message key="web.institution.index.jsp.count.deviceLost.label"/></small>
					</div>
				</div>
			</section>
		</div>
		<div class="col-md-12 hidden part3" >
                  <section class="panel panel-default">
                    <header class="panel-heading font-bold">Multiple</header>
                    <div class="panel-body">
                      <div id="flot-chart"  style="height:100%;width:100%"></div>
                    </div>                  
                  </section>
                </div>
	</div>
	<div class="col-lg-5 row">
		<c:if test="${idlist==null}">
		<div class="col-lg-offset-8 col-lg-12">
			<select name="account" class="form-control input-md" onchange="onExchange(this)">
				<c:forEach items="${orgs}" var="org">
					<option value="${org.id}" ${softtek_organization.id==org.id?'selected':''}>${org.name}</option>
				</c:forEach>
			</select>
		</div>
		</c:if>
		<div class="col-lg-12 part4">
			<section class="panel panel-warning col-lg-6 wrapper m-t">
				<div class="panel-body">
					<a href="#" class="thumb pull-right m-l m-t-xs"> <i
						class="fa fa-apple fa-3x text-warning"></i>
					</a>
					<div class="clear">
						<a href="${deviceManagerUrl}" onclick="clickUrlIos(13,16,1)" class="text-warning text-lg">${map.iosCount}</a> <small
							class="block text-muted text-ellipsis"data-toggle="tooltip" data-placement="bottom" title=""
							data-original-title='<fmt:message key="web.institution.index.jsp.count.ios.label"/>'><fmt:message key="web.institution.index.jsp.count.ios.label"/></small> <a href="${deviceManagerUrl}" onclick="clickUrlIos(13,16,1)"
							class="btn btn-xs btn-warning m-t-xs"><fmt:message key="web.institution.index.jsp.count.search.label"/></a>
					</div>
				</div>
			</section>
			<section class="panel panel-success col-lg-6 wrapper m-t">
				<div class="panel-body">
					<a href="#" class="thumb pull-right m-l m-t-xs"> <i
						class="fa fa-android fa-3x text-success"></i>
					</a>
					<div class="clear">
						<a href="${deviceManagerUrl}" onclick="clickUrlIos(13,16,2)" class="text-info text-lg">${map.androidCount}</a> <small
							class="block text-muted text-ellipsis"data-toggle="tooltip" data-placement="bottom" title=""
							data-original-title='<fmt:message key="web.institution.index.jsp.count.android.label"/>'><fmt:message key="web.institution.index.jsp.count.android.label"/></small> <a href="${deviceManagerUrl}" onclick="clickUrlIos(13,16,2)"
							class="btn btn-xs btn-success m-t-xs"><fmt:message key="web.institution.index.jsp.count.search.label"/></a>
					</div>
				</div>
			</section>
		</div>
		
		 <div class="col-md-12 part5">
             <section class="panel panel-default">
               <header class="panel-heading font-bold"><fmt:message key="web.institution.index.jsp.count.status.label"/></header>
               <div class="panel-body ">
                 <div id="flot-pie-donut" style="height:230px"></div>
               </div>
             </section>
           </div>
           <div class="col-md-12 part5">
             <section class="panel panel-default">
               <header class="panel-heading font-bold"><fmt:message key="web.institution.index.jsp.count.status.label"/></header>
             <div class="panel-body ">
                 <div id="flot-pie-donut2" style="height:230px"></div>
               </div>
             </section>
           </div>
           
	</div>
</div>
<style>  
    div.graph  
            {  
                width: 400px;  
                height: 300px;  
                border: 1px dashed gainsboro;  
            }  
      
    </style>  
      
