<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spring:url value="/farm/admin/kind/new" var="newKindUrl"/>
<div class="wrapper wrapper-content animated fadeInRight">
	 <div class="row" style="min-height: 600px;">
                <div class="col-sm-4">
		            <div class="ibox float-e-margins">
		                <div class="ibox-content">
		                    <div id="treeview1" class="test"></div>
		                </div>
		            </div>
		        </div>
            
            <div class="col-sm-8">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <a data-toggle="modal" onclick="newKind()" id="newKindBtn" class="btn btn-primary ">新增分类</a>
                    </div>
                    <div class="ibox-content">
                        <table class="table table-striped table-bordered table-hover " id="editable" style="text-align: center;">
                            <thead>
                                <tr>
                                    <th>分类名称</th>
                                    <th>icon图</th>
                                    <th>备注</th>
                                    <th>状态</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                    <!-- modal start -->
                    <div class="modal inmodal" id="newModal" tabindex="-1" role="dialog"  aria-hidden="true">
	                    <div class="modal-dialog">
	                        <div class="modal-content animated bounceInRight">
								<form role="form" action="${newKindUrl}" method="post" enctype="multipart/form-data" id="kindSubmit">
	                            	<div class="modal-header">
		                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
		                                </button>
		                                <h4 class="modal-title"><span class="modal-oper">新增</span>分类</h4>
	                                </div>
	                                <div class="modal-body cleanfix">
											<div class="form-group">
												<div class="col-md-6">
													<label>分类名称(*)</label> <input type="text" name="kindName" class="form-control"
														placeholder="请输入分类名..." required maxlength="40">
												</div>
												<div class="col-md-6">
													<label>种类(*)</label> 
													<select name="catalog" class="form-control">
														<option value="0">水果</option>
														<option value="1">非水果</option>
													</select>
													<input type="hidden" name="catalogVal" value="" id="catalogVal"/>
												</div>
											</div>
											<div class="form-group">
												<div class="col-md-12">
													<label>备注</label> <input type="text" name="remark" class="form-control"
													placeholder="添加点备注吧。。。" required maxlength="40">
												</div>
											</div>
											<div class="form-group">
												<div class="col-md-6">
													<label>图标 </label> <input type="file" id="file_upload" name="file_upload2" onchange="change_photo()" class="form-control">
												</div>
												<div class="col-md-6">
													<label>预览</label><div id="Imgdiv">
												        <img id="Img" width="34px" height="34px"/>
												    </div>
												</div>
											</div>
											<input id="kind-parent-id" name="parent.id" type="hidden"/>
											<input id="kind-id" name="id" type="hidden" />
	                                </div>
	                                <div class="modal-footer">
	                                    <button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
	                                    <button type="submit" class="btn btn-primary"><span class="modal-oper">新增</span></button>
	                                </div>
									</form>
	                            </div>
	                        </div>
	                    </div>
                     <!-- modal end -->        
                </div>
            </div>
            
        </div>
    </div>

</div>