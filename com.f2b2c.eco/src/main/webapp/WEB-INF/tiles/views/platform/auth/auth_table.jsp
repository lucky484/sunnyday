<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="wrapper wrapper-content animated fadeInRight">
	 <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                	<c:if test="${token eq 1 }">
                		<div class="ibox-title">
	                        <a data-toggle="modal" data-target="#myModal" class="btn btn-primary ">授权码生成工具</a>
	                    </div>
                	</c:if>
                    <div class="ibox-content">
                        <table class="table table-striped table-bordered table-hover " id="editable">
                            <thead>
                                <tr>
                                    <th>id</th>
                                    <th width="25%">授权码</th>
                                    <th width="25%">状态</th>
                                    <th width="25%">创建人</th>
                                    <th width="25%">创建时间</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                    <!-- modal start -->
                    <div class="modal inmodal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
	                    <div class="modal-dialog">
	                        <div class="modal-content animated bounceInRight">
	                            <div class="modal-header">
	                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
	                                </button>
	                                <h4 class="modal-title">批量生成授权码</h4>
	                                </div>
	                                <div class="modal-body">
										<div class="form-group">
											<input type="hidden" name="userId" value="${user.id}" id="userId"/>
											<label class="col-sm-2 control-label ptop">数量:</label> 
											<div class="col-sm-5">
												<input id="auth-num" type="number" step="1" min="1" placeholder="请输入授权码的数量" class="form-control">
											</div>
											<div class="col-sm-5">
												<span id="email-error" class="help-block m-b-none ptop hidden"><i class="fa fa-times-circle"></i> 请输入正确的数</span>
											</div>
										</div>
	                                </div>
	                                <div class="modal-footer">
	                                    <button type="button" class="btn btn-primary" onclick="generateKeys();">生成</button>
	                                </div>
	                            </div>
	                        </div>
	                    </div>
                     <!-- modal end -->        
                </div>
            </div>
        </div>
    </div>
