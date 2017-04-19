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
							<header class="panel-heading font-bold">
							 <c:if test="${softtek_manager.user==null || softtek_manager.auth > 0 }">
							<span onclick="addPicAndTxtMsg();" class="btn btn-sm btn-success btn-rounded"  style="cursor:pointer"><i class="fa fa-plus"></i>&nbsp;
								<fmt:message key="tiles.views.institution.message.headname"/></span>
								</c:if>
							</header>
							<div class="panel-body">
							     <div class="search-toggle">
						     <a hidfocus="true"><fmt:message key="tiles.institution.comm.expand.search.tip" /></a>
						   </div>
						   <div class="search-mod" style="display: none;">
			   					<ul class="search-list">
			   						<li class="search-item ">
			   							<label class="search-label"><fmt:message key="tiles.views.institution.message.searchname"/></label>
			   							<input type="text" id="name" name="name" class="search-text" style="width:120px;">
			   						</li>
			   					</ul>
								<div class="type-choice">
									<a class="button-search" type="button" onclick="javascript:searchMsgLists();"><i class="fa fa-search"></i><span><fmt:message key="tiles.institution.policy.policy.search" /></span></a>
									<a class="button-search" type="button" onclick="javascript:cleanMsgLists();"><i class="fa fa-trash-o"></i><span><fmt:message key="tiles.institution.policy.policy.clean" /></span></a>
								</div>
			   			</div>
								<div class="table-responsive">
										<table class="table table-striped b-t b-light" id="PicAndTxtMsgList">
											<thead>
												<tr>
												   <th><fmt:message key="tiles.views.institution.message.table.msgtitle"/></th>
												   <th><fmt:message key="tiles.views.institution.message.usercount"/></th>
											       <th><fmt:message key="tiles.views.institution.message.table.createtime"/></th>
												   <th><fmt:message key="tiles.views.institution.message.pushtime"/></th>
												   <th><fmt:message key="tiles.views.institution.message.table.operation"/></th>
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
	`
</section>
