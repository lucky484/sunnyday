<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="blog-post">
	<div class="post-item">
		<header
			class="panel-heading b-l-l-none b-t-l-none b-b-l-1 b-r-l-none b-s-s">
			<c:if test="${menu[6].isshow==-1}">
			<spring:url value="/institution/vtl" var="vtl" />
			<a href="${vtl}" class="btn btn-s-sm "><fmt:message key="tiles.institution.vtl.group.action.group.label"/></a>
			</c:if>
			<c:if test="${menu[7].isshow==-1}">
			<spring:url value="/institution/vtl/member" var="member" />
			<a href="${member}" class="btn btn-s-sm btn-primary"><fmt:message key="tiles.institution.vtl.group.action.member.label"/></a>
			</c:if>
		</header>
		<section class="wrapper-md ">
			<div class="row">
				<div class="scrollable aside-md col-sm-3">
					<div class="btn-toolbar">
						<c:if test="${softtek_manager.user==null || softtek_manager.auth > 0 }">
						<button class="btn btn-default btn-sm btn-bg b-none text-info-ipt"
							onclick="openmyModal()">
							<i class="glyphicon glyphicon-import"></i> <span><fmt:message key="tiles.institution.vtl.member.import.label"/></span>
						</button>
						</c:if>
					</div>
					<div id="tree">
						<c:forEach items="${vtlcollist}" var="vtlcol" varStatus="i">
							<ul onclick="liClick(this);" id="tree-list">
							<li data-type="parent" data-id="${vtlcol.id}"
									vtlcolName="${vtlcol.name}" id="select${i.index}"
									class="list-group text-ellipsis"
									style="font-size: 18px; color: #428bca"><span
									class="icon expand-icon glyphicon glyphicon-plus "
									plusid="${vtlcol.id}"></span> <span
									class="icon expand-icon glyphicon glyphicon-minus hidden"
									minusid="${vtlcol.id}"></span> <label>${vtlcol.name}</label>(${vtlcol.mark}<fmt:message key="tiles.institution.vtl.member.information7.member.label"/>)</li>
							</ul>
							<ul id="tree-gourp-list" class="hidden">
								<c:forEach items="${vtllist}" var="vtl">
									<c:if test="${vtlcol.id eq vtl.virtualCollection.id}">
										<ul>
											<li onclick="querymembers(this);" data-type="children"
												parentId="${vtlcol.id}" data-id="${vtl.id}" data-selected="${vtlcol.multiple}"
												data-name="${vtl.name}" id="selectChild${vtlcol.id}"
												class="tree-list-style"
												style="font-size: 15px; color: #428bca"><label>${vtl.name}</label><span id="">(${vtl.quantity}<fmt:message key="tiles.institution.vtl.member.information7.member.label"/>)</span></li>
										</ul>
									</c:if>
								</c:forEach>
							</ul>

						</c:forEach>
					</div>
				</div>
				<div class="col-sm-9">
					<section class="panel panel-default">
						<header class="panel-heading font-bold"><fmt:message key="tiles.institution.vtl.member.information.label"/></header>
						<div class="panel-body">
							<form class="bs-example form-horizontal">
								<div class="col-lg-5">
									<div class="form-group">
										<label class="control-label m-l-lg"><fmt:message key="tiles.institution.vtl.member.information.member.label"/><span
											class="text-danger"><fmt:message key="tiles.institution.vtl.member.information1.member.label"/></span><fmt:message key="tiles.institution.vtl.member.information2.member.label"/> <span
											class="font-bold text-info">【<span id="virtual_name"></span>】</span><fmt:message key="tiles.institution.vtl.member.information3.member.label"/>
										</label>
									</div>
									<div class="form-group">
										<label class="col-lg-3 control-label"><fmt:message key="tiles.institution.vtl.member.information4.member.label"/></label>
										<div class="col-lg-9">
											<div class="input-group">
												<input type="text" class="form-control text-add-filter"id="non-existence-name">
												<span class="input-group-btn">
													<button class="btn btn-default btn-add-filter"
														type="button" onclick="queryMember()">
														<i class="fa fa-filter"></i><fmt:message key="tiles.institution.vtl.member.information5.member.label"/>
													</button>
													<input type="hidden" id="non-existence-name-submit">
												</span>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-3 control-label"></label>
										<div class="col-lg-9">
											<section class="vbox flex">
												<div class="padder"
													style="min-height: 100px; max-height: 340px;">
													<!-- note list -->
													<ul id="note-items" class="list-group select-sort">
													</ul>
												</div>
											</section>
											<div class="text-right">
												<ul class="pagination pagination-sm m-t-none m-r">
													<li onclick="prevPageNoexist(this)" class="prevPageNoexist"
														pageNumber="" group-id=""><a id=""> <i
															class="fa fa-chevron-left"></i><fmt:message key="tiles.institution.vtl.member.previouspage.label"/>
													</a></li>
													<li><a onclick="nextPageNoexist(this)"
														class="nextPageNoexist" pageNumber="" group-id="" max-page=""><fmt:message key="tiles.institution.vtl.member.nextpage.label"/>
															<i class="fa fa-chevron-right"></i>
													</a></li>
													<li><a href="javascript:void(0);"
														style="cursor: default;"><fmt:message key="tiles.institution.vtl.member.information6.member.label"/> (<span id="totalityleft"></span>) <fmt:message key="tiles.institution.vtl.member.information7.member.label"/></a></li>
												</ul>
											</div>

										</div>
									</div>
								</div>
								<div class="col-lg-2">
									<div class="form-group">
										<label class="col-lg-12 control-label">&nbsp;</label>
									</div>
									<div class="form-group">
										<label class="col-lg-12 control-label">&nbsp;</label>
									</div>
									<div class="form-group">
										<label class="col-lg-12 control-label">&nbsp;</label>
									</div>
									<div class="btn-group-vertical m-b-sm">
											<c:if test="${softtek_manager.auth > 0 || softtek_manager.user==null}">
										<button type="button" onclick="insertmember()"
											class="btn btn-default btn-s-lg btn-arrow-right">
											<fmt:message key="tiles.institution.vtl.member.information8.member.label"/> <i class="fa fa-arrow-right"></i>
										</button>
										<button type="button" onclick="deletemember()"
											class="btn btn-default btn-s-lg btn-arrow-left">
											<i class="fa fa-arrow-left"></i><fmt:message key="tiles.institution.vtl.member.information9.member.label"/>
										</button>
										</c:if>
									</div>
								</div>
								<div class="col-lg-5">
									<div class="form-group">
										<label class="control-label m-l-lg"><fmt:message key="tiles.institution.vtl.member.information.member.label"/><span
											class="text-danger"><fmt:message key="tiles.institution.vtl.member.information10.member.label"/></span><fmt:message key="tiles.institution.vtl.member.information2.member.label"/> <span
											class="font-bold text-info">【<span id="virtual_name_1"></span>】</span><fmt:message key="tiles.institution.vtl.member.information3.member.label"/>
										</label>
									</div>
									<div class="form-group">
										<label class="col-lg-3 control-label"><fmt:message key="tiles.institution.vtl.member.information4.member.label"/></label>
										<div class="col-lg-9">
											<div class="input-group">
												<input type="text" class="form-control text-add-filter-1" id="existent-member">
												<span class="input-group-btn">
													<button class="btn btn-default btn-add-filter-1"
														type="button" onclick="queryexistMember()">
														<i class="fa fa-filter"></i> <fmt:message key="tiles.institution.vtl.member.information5.member.label"/>
													</button>
													<input type="hidden" id="existence-name-submit">
												</span>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-3 control-label"></label>
										<div class="col-lg-9">
											<section class="vbox flex">
												<div class="padder"
													style="min-height: 100px; max-height: 300px;">
													<!-- note list -->
													<ul id="note-items-1" class="list-group select-sort">
													</ul>
												</div>
											</section>
											<div class="text-right">
												<ul class="pagination pagination-sm m-t-none m-r">
													<li onclick="prevPageExist(this)" class="prevPageexist"
														pageNumber="" group-id=""><a href="#"> <i
															class="fa fa-chevron-left"></i><fmt:message key="tiles.institution.vtl.member.previouspage.label"/>
													</a></li>
													<li onclick="nextPageExist(this)" class="nextPageexist"
														pageNumber="" group-id="" ><a href="#"><fmt:message key="tiles.institution.vtl.member.nextpage.label"/><i
															class="fa fa-chevron-right"></i>
													</a></li>
													<li><a href="javascript:void(0);"
														style="cursor: default;"><fmt:message key="tiles.institution.vtl.member.information6.member.label"/> (<span id="totalityright"></span>) <fmt:message key="tiles.institution.vtl.member.information7.member.label"/></a></li>
												</ul>
											</div>
										</div>
									</div>
								</div>
							</form>
						</div>
					</section>
				</div>
			</div>
		</section>
	</div>
</div>

