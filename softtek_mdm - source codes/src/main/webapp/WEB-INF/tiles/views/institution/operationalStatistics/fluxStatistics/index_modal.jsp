<!-- 此页面没有modal  国际化未作 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- 流量总数列表 -->
<div class="modal fade" id="viewTotalUserModel" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header b-b-l-none">
				<header class="col-sm-6 font-bold model-left">
					<span>流量总数列表</span>
				</header>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body" style="max-height: 800px; overflow: scroll;">
				<div class="row">
					<div class="col-sm-12">
						<div class="panel panel-default">
							<header class="panel-heading font-bold">
								<span onclick="exportTotalUser();" class="btn btn-sm btn-success btn-rounded"  style="cursor:pointer"><i class="fa fa-external-link"></i>&nbsp;
									导出</span>
							</header>
							<div class="panel-body">
								<div class="table-responsive">
									<input type="hidden" name="searchDate" id="totalUserSearchDate"/>
								 <div class="col-sm-12">
										<div class="col-sm-2 searchName">设备名称：</div>
										<div class="col-sm-2 searchName_val">
											<input id="device_name" type="text" class="input-sm form-control" size="16"/>
										</div>
										<div class="search_button head-right">
											<button class="btn btn-sm btn-default search_icon " type="button" onclick="searchTotalUser();"><span class="glyphicon glyphicon-search"></span>&nbsp;查询</button>&nbsp;&nbsp;
											<button class="btn btn-sm btn-default reset_icon button_left" type="button" onclick="clearTotalUser();"><i class="fa fa-trash"></i>&nbsp;清空</button>
										</div>
									</div>
									<div class="col-sm-12">
										<table class="table table-striped b-t b-light" id="totalUserList">
											<thead>
												<tr>
												   <th>统计日期</th>
												   <th>设备名称</th>
												   <th>使用人</th>
												  <!--  <th>归属部门</th> -->
												   <th>已用流量</th>
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
			</div>
		</div>
	</div>
</div>
