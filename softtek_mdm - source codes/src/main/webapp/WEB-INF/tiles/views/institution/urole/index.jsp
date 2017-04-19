<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="blog-post">
	<div class="post-item">
		<section class="wrapper-md ">
			<div class="row">
				<div class="col-sm-12">
					<section class="panel panel-default">
						<header class="panel-heading font-bold">
							<div class="doc-buttons">
								<button class="btn btn-sm btn-success btn-rounded"
									onclick="add()">
									<i class="fa fa-plus"></i> &nbsp;
									<fmt:message key="tiles.views.institution.urule.name" />
								</button>
							</div>
						</header>
						<div class="panel-body ">
						 <div class="search-toggle">
						     <a hidfocus="true"><fmt:message key="tiles.institution.comm.expand.search.tip" /></a>
						   </div>
						   <div class="search-mod" style="display: none;">
			   					<ul class="search-list">
			   						<li class="search-item ">
			   							<label class="search-label" style="width:76px"><fmt:message key="tiles.views.institution.urule.name" />:</label>
			   							<input type="text" id="searchrolename" name="searchrolename" class="search-text" style="width:120px;">
			   						</li>
			   					</ul>
								<div class="type-choice">
									<a class="button-search" type="button" onclick="javascript:searchRoleLists();"><i class="fa fa-search"></i><span><fmt:message key="tiles.institution.policy.policy.search" /></span></a>
									<a class="button-search" type="button" onclick="javascript:cleanRoleLists();"><i class="fa fa-trash-o"></i><span><fmt:message key="tiles.institution.policy.policy.clean" /></span></a>
								</div>
			   			</div>
						<%-- 	<div class="col-sm-12 search_part">
								<div class="col-sm-1 searchName">
									<fmt:message key="tiles.views.institution.urule.name" />
									:
								</div>
								<div class="col-sm-2 searchName_val">
									<input id="searchrolename" type="text"
										class="input-sm form-control" />
								</div>
								<div class="col-sm-2 search_button">
									<button class="btn btn-sm btn-default search_icon"
										type="button" onclick="searchRoleLists()">
										<fmt:message key="tiles.views.institution.device.rule.query" />
									</button>
									<button class="btn btn-sm btn-default reset_icon" type="button"
										onclick="cleanRoleLists()">
										<fmt:message key="tiles.views.institution.device.rule.reset" />
									</button>
								</div>
							</div> --%>
							<div class="table-responsive">
								<table id="roleTable" class="table table-striped b-t b-light">
									<thead>
										<tr>
											<th><fmt:message
													key="tiles.views.institution.urule.rulename" /></th>
											<th><fmt:message
													key="tiles.views.institution.urule.describe" /></th>
											<th><fmt:message
													key="tiles.views.institution.device.rule.opera" /></th>
										</tr>
									</thead>
									<tbody>

									</tbody>
								</table>
							</div>
						</div>
					</section>
				</div>
			</div>
		</section>
	</div>
</div>

