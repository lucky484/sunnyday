<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="blog-post">
	<div class="post-item">
		<header class="panel-heading b-l-l-none b-t-l-none b-b-l-none b-r-l-none b-s-s">
			<div class="bordertype">
				<div class="head-left">
					<span class="head-span" ><fmt:message key="tiles.views.institution.operationalStatistics.fluxStatistics.index.date"/></span>
					<sapn class="search" val="1"><fmt:message key="tiles.views.institution.operationalStatistics.fluxStatistics.index.today"/></sapn>
					<sapn class="search" val="2"><fmt:message key="tiles.views.institution.operationalStatistics.fluxStatistics.index.weekly"/></sapn>
					<sapn class="search search-current" val="3"><fmt:message key="tiles.views.institution.operationalStatistics.fluxStatistics.index.month"/></sapn>
				</div>
				<div class="head-middle">
				  <div class="timestarts">
					   <input class="" type="text" id="timeStart" data-date-format="yyyy-mm-dd" size="16"/>
				  </div>
				  <div class="time_icon">~</div>
				  <div class="timeends">
					   <input class="" type="text" id="timeEnd" data-date-format="yyyy-mm-dd" size="16"/>
				  </div>
			    </div>
			    <div class="search_button head-right">
					<button class="btn btn-sm btn-default search_icon " type="button" onclick="search();"><span class="glyphicon glyphicon-search"></span>&nbsp;<fmt:message key="tiles.views.admin.index.manager.search"/></button>&nbsp;&nbsp;
					<button class="btn btn-sm btn-default reset_icon button_left" type="button" onclick="reset();"><i class="fa fa-reply"></i>&nbsp;<fmt:message key="tiles.views.institution.operationalStatistics.fluxStatistics.index.defalut"/></button>
				</div>
			</div>
		</header>
		<section class="wrapper-md">
				<div class="row head-section">
					<div class="col-sm-12">
						<div class="panel panel-default">
							<!-- 头部负责加载char图的部分 -->
							<div id="userChart" class="userChart chart-mod">
								
							</div>
						</div>
						<div class="panel panel-default">
							<header class="panel-heading font-bold">
							<spring:url value="/institution/systemStatistics/flux/export" var="export" />
							<form action="${export}" method="post" id="exportForm">
								<!-- 跨域站信息 -->
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<input type="hidden" name="timeStart" id="timeStartExport" value="1"/>
								<input type="hidden" name="timeEnd" id="timeEndExport" value=""/>
								<input type="hidden" name="searchType"id="searchTypeExport" value=""/>
								<span onclick="exportTable();" class="btn btn-sm btn-success btn-rounded"  style="cursor:pointer"><i class="fa fa-external-link"></i>&nbsp;
									<fmt:message key="tiles.views.institution.operationalStatistics.fluxStatistics.index.export"/></span>
									 <a class="font-bold" style="margin-left: 30%;color: #e20606;"><fmt:message key="tiles.views.flux.tips.only.android"/></a>
								</form>
							</header>
							<div class="panel-body">
								<div class="table-responsive">
									<div class="col-sm-12">
										<table class="table table-striped b-t b-light" id="userStatisticsLists">
											<thead>
												<tr>
												   <th><fmt:message key="tiles.views.institution.operationalStatistics.fluxStatistics.index.datetime"/></th>
												   <th><fmt:message key="tiles.views.institution.operationalStatistics.fluxStatistics.index.fluxsum"/></th>
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
		</section>
	</div>
</div>
