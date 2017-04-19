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
			<spring:url value="/admin/backup/setting" var="setting" />
			<a href="${setting}" class="btn btn-s-sm">数据备份设置</a>
			<spring:url value="/admin/backup/manage" var="manage" />
			<a href="${manage}" class="btn btn-s-sm btn-primary">数据备份管理</a>
		</header>
		<section class="wrapper-md ">
				<div class="row">
					<div class="col-sm-12">
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="table-responsive">
								 <div class="col-sm-12">
										<div class="form-group">
					                      <div class="col-sm-1 col-md-1 searchName">产生时间：</div>
											  <div class="timestarts">
												   <input class="" type="text" id="timeStart" data-date-format="yyyy-mm-dd" size="16"/>
											  </div>
											  <div class="time_icon">~</div>
											  <div class="timeends">
												   <input class="" type="text" id="timeEnd" data-date-format="yyyy-mm-dd" size="16"/>
											  </div>
										</div>
										<div class="col-sm-2 search_button">
											<button class="btn btn-sm btn-default search_icon" type="button" onclick="searchBackupLists();">查询</button>&nbsp;&nbsp;
											<button class="btn btn-sm btn-default reset_icon" type="button" onclick="cleanBackupLists();">清空</button>
										</div>
									</div>
									<div class="col-sm-12">
										<table class="table table-striped b-t b-light" id="backupList">
											<thead>
												<tr>
												   <th>id</th>
												   <th>备份状态</th>
												   <th>备份路径</th>
												   <th>文件大小</th>
												   <th>备份时间</th>
												   <th>备份类型</th>
												   <th>操作</th>
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
