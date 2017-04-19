<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<style>
.ibox-tools-btn {
	display: inline-block;
	float: right;
	position: relative;
	padding: 0px;
	margin-top: -8px !important;
}
</style>
<div class="wrapper wrapper-content animated fadeInRight">
	<div class="row">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>
						物流方式 <small> / 物流方式列表 </small>
					</h5>
					<span class="pull-right"> <a class="btn btn-primary radius btn-sm m-t-n-xs"
						data-title="添加物流方式" onclick="add();"
						href="javascript:void(0);"> <i class="fa fa-plus"> 添加物流方式
						</i>
					</a>
					</span>
				</div>

				<div class="ibox-content">
					<table
						class="table table-striped table-bordered table-hover dataTables-logistics"
						id="logistics">
						<thead>
							<tr>
								<!-- <th>ID</th> -->
								<th>物流名称</th>
								<th>操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
			
			<!-- 删除提示 -->
			<div id="deleteConfirm" class="modal inmodal" tabindex="-1"
				role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content animated bounceInRight">
						<div class="modal-header">
							<button type="button" class="close"></button>
							<h3 id="myModalLabel">提示</h3>
						</div>
						<div class="modal-body">
							<p>确认删除？</p>
						</div>
						<div class="modal-footer">
							<button class="btn" data-dismiss="modal" aria-hidden="true">
								取消</button>
							<button data-dismiss="modal" class="btn blue" id="deleteApplyBtn">
								删除</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
