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
					   <input type="text" id="startTime" data-date-format="yyyy-mm-dd" size="16"/>
				  </div>
				  <div class="time_icon">~</div>
				  <div class="timeends">
					   <input type="text" id="endTime" data-date-format="yyyy-mm-dd" size="16"/>
				  </div>
			    </div>
			   <div class="search_button head-right">
					<button class="btn btn-sm btn-default search_icon " type="button" onclick="search();"><span class="glyphicon glyphicon-search"></span>&nbsp;<fmt:message key="tiles.views.institution.operationalStatistics.fluxStatistics.index.query"/></button>&nbsp;&nbsp;
					<button class="btn btn-sm btn-default reset_icon button_left" type="button" onclick="reset();"><i class="fa fa-reply"></i>&nbsp;<fmt:message key="tiles.views.institution.operationalStatistics.fluxStatistics.index.defalut"/></button>
				</div>
			</div>
		</header>
				<section class="wrapper-md">
				<div class="row head-section">
					<div class="col-sm-12">
						<div id="userTimeChart" class="userTimeChart chart-mod">
							
						</div>
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="table-responsive">
									<div class="col-sm-12">
										<table class="table table-striped b-t b-light" id="usertime">
											<thead>
												<tr>
												   <th><fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.table.createTime"/></th>
												   <th><fmt:message key="tiles.views.institution.operationalStatistics.userTimeStatistics.user.best"/></th>
												   <th><fmt:message key="tiles.views.institution.operationalStatistics.userTimeStatistics.user.times"/></th>
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
