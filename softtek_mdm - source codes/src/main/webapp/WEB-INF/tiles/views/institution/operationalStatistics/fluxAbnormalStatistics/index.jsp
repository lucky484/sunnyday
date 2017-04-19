<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="blog-post">
	<div class="post-item">
		<section class="wrapper-md">
		 <a class="font-bold" style="margin-left: 30%;color: #e20606;"><fmt:message key="tiles.views.flux.tips.only.android"/></a>
				<div class="row head-section">
					<div class="col-sm-12">
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="table-responsive">
									<div class="col-sm-12">
										<table class="table table-striped b-t b-light" id="deviceStatisticsLists">
											<thead>
												<tr>
												   <th><fmt:message key="tiles.views.user.index.table.username"/></th>
												   <th><fmt:message key="tiles.views.user.index.table.account"/></th>
												   <th><fmt:message key="tiles.institution.log.operate.device.name"/></th>
												   <th><fmt:message key="tiles.aside.menu.device.flux.abnormal.date"/></th>
												   <th><fmt:message key="tiles.aside.menu.device.flux.abnormal.flux"/></th>
												   <th><fmt:message key="tiles.aside.menu.device.flux.abnormal.flux.detail"/></th>
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
