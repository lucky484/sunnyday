<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<section class="row">
	<div class="blog-post">
		<div class="post-item">
			<section class="wrapper-md ">
				<div class="row">
					<div class="col-sm-12">
						<div class="panel panel-default">
							<div class="panel-body">
							     <div class="search-toggle">
								     <a hidfocus="true"><fmt:message key="tiles.institution.comm.expand.search.tip" /></a>
								   </div>
								   <div class="search-mod" style="display: none;">
					   					<ul class="search-list">
					   						<li class="search-item ">
					   							<label class="search-label"><fmt:message key="tiles.views.netbehaviormanager.logmanager.username.condition"/></label>
					   							<input type="text" id="behaviorName" name="behaviorName" class="search-text" style="width:120px;">
					   						</li>
					   					</ul>
					   					<ul class="search-list">
					   						<li class="search-item ">
					   							<label class="search-label"><fmt:message key="tiles.views.netbehaviormanager.logmanager.log.content.condition"/></label>
					   							<input type="text" id="behaviorContent" name="behaviorContent" class="search-text" style="width:120px;">
					   						</li>
					   					</ul>
										<div class="type-choice">
											<a class="button-search" type="button" onclick="javascript:searchBwLog();"><i class="fa fa-search"></i><span><fmt:message key="tiles.institution.policy.policy.search" /></span></a>
											<a class="button-search" type="button" onclick="javascript:cleanBwLog();"><i class="fa fa-trash-o"></i><span><fmt:message key="tiles.institution.policy.policy.clean" /></span></a>
										</div>
					   			</div>
								<div class="table-responsive">
										<table class="table table-striped b-t b-light" id="bWLogTb">
											<thead>
												<tr>
												   <th><fmt:message key="tiles.views.netbehaviormanager.logmanager.login.account"/></th>
												   <th><fmt:message key="tiles.views.netbehaviormanager.logmanager.username"/></th>
												   <th><fmt:message key="tiles.views.netbehaviormanager.logmanager.device.name"/></th>
												   <th><fmt:message key="tiles.views.netbehaviormanager.logmanager.sn"/></th>
												   <th><fmt:message key="tiles.views.netbehaviormanager.logmanager.net.address"/></th>
												   <th><fmt:message key="tiles.views.netbehaviormanager.logmanager.content"/></th>
												   <th><fmt:message key="tiles.views.netbehaviormanager.logmanager.surfnet.time"/></th>
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
			</section>
		</div>
	</div>
</section>
