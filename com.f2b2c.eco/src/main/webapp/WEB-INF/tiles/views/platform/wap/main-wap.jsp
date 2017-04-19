<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spring:url value="/farm/home/wap/addorupdate" var="wapUrl"/>

<!-- wap-页面 -->
<div class="wrapper wrapper-content animated fadeInRight modal-open">
	<div class="row">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<a data-toggle="modal" onclick="add()" class="btn btn-primary ">新增轮播图片</a>
				</div>
				<div class="ibox-content">
					<table class="table table-striped table-bordered table-hover "
						id="editable">
						<thead>
							<tr>
								<th>图片</th>
								<th>操作</th>
							</tr>
						</thead>
					</table>
				</div>

				<!-- modal start -->
				<div class="modal inmodal" id="newModal" tabindex="-1" role="dialog"
					aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content animated bounceInRight">
							<form role="form" action="${wapUrl}" enctype="multipart/form-data" method="post" >
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal">
										<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
									</button>
									<h4 class="modal-title">
										<span class="modal-oper">新增</span>wap广告图
									</h4>
								</div>
								<div class="modal-body cleanfix">
									<div class="form-group">
										<div class="col-md-12">
											<label>图片</label> <input type="file" name="file"
												class="form-control" placeholder="" onchange="change_photo()" required />
										</div>
										<div class="col-md-6">
											<label>预览</label><div id="Imgdiv">
										        <img id="Img" width="34px" height="34px"/>
										    </div>
										</div>
									</div>
									<input id="wap-id" name="id" type="hidden" />
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-white"
										data-dismiss="modal">取消</button>
									<button type="submit" class="btn btn-primary">
										<span class="modal-oper">新增</span>
									</button>
								</div>
							</form>
						</div>
					</div>
				</div>
				<!-- modal end -->
							
			<!-- 删除提示 -->
<!-- 			<div id="deleteConfirm" class="modal inmodal" tabindex="-1" -->
<!-- 				role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"> -->
<!-- 				<div class="modal-dialog"> -->
<!-- 					<div class="modal-content animated bounceInRight"> -->
<!-- 						<div class="modal-header"> -->
<!-- 							<button type="button" class="close"></button> -->
<!-- 							<h3 id="myModalLabel">提示</h3> -->
<!-- 						</div> -->
<!-- 						<div class="modal-body"> -->
<!-- 							<p>确认删除？</p> -->
<!-- 						</div> -->
<!-- 						<div class="modal-footer"> -->
<!-- 							<button class="btn" data-dismiss="modal" aria-hidden="true"> -->
<!-- 								取消</button> -->
<!-- 							<button data-dismiss="modal" class="btn blue" id="deleteApplyBtn"> -->
<!-- 								删除</button> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
			</div>
		</div>
	</div>
</div>