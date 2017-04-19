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
                	  <div class="ibox-title">
                           <h5>商品列表</h5>
                      </div>
                	
                      <div class="ibox-content">
                      <div class="row">
                    		<div class="col-sm-2">
                    		<input id="name" type="text" class="form-control" placeholder="请输入商品名称" />
                    		</div>
                    		<div class="col-sm-2">
                    		<input id="kindTree" class="form-control">
                    		</div>
                    		<div class="col-sm-2">
                   			<input id="location" class="form-control" placeholder="请输入产地" />
                    		</div>
                    		<div class="col-sm-2">
                    			<select id="goodsStatus" class="form-control">
                   				  <option value="">所有状态</option>
								  <option value="0">待审核</option>
								  <option value="1">上架</option>
								  <option value="2">下架</option>
								  <option value="3">审核不通过</option>
                    			</select>
                    		</div>
                    		<div class="col-sm-2">
                    			<button class="btn-sm btn btn-primary" type="button" onclick="search();">查询</button>
								<button class="btn-sm btn" type="button" onclick="reset();">清空</button>
                    		</div>
                		</div>
                        <table class="table table-striped  table-bordered dataTables-userlist">
                            <thead>
                                <tr>
                                    <th width="10%">名称</th>
                                    <th width="8%">主图</th>
                                    <th width="10%">商品类型</th>
                                    <th width="10%">分类</th>
                                    <th width="8%">单价(元)</th>
                                    <th width="8%">库存</th>
                                    <th width="10%">产地</th>
                                    <th width="7%">销量</th>
                                    <th width="14%">最后更新</th>
									<th width="5%">状态</th>                                    
                                    <th width="10%">操作</th>
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
      
  <!-- modal start -->
                    <div class="modal inmodal" id="reasonModal" tabindex="-1" role="dialog" aria-hidden="true">
	                    <div class="modal-dialog">
	                        <div class="modal-content animated bounceInRight">
								<form role="form" action="${newKindUrl}" method="post" enctype="multipart/form-data">
	                            	<div class="modal-header">
		                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
		                                </button>
		                                <h4 class="modal-title">拒绝原因</h4>
	                                </div>
	                                <div class="modal-body cleanfix">
											<div class="form-group" id="reasonStr">
											</div>
	                                </div>
	                                <div class="modal-footer">
	                                    <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
	                                </div>
									</form>
	                            </div>
	                        </div>
	                    </div>
                     <!-- modal end -->        
    
    
     <!-- modal start -->
                    <div class="modal inmodal" id="modifyRemainModal" tabindex="-1" role="dialog" aria-hidden="true">
	                    <div class="modal-dialog">
	                        <div class="modal-content animated bounceInRight">
								<form role="form" action="${newKindUrl}" method="post" enctype="multipart/form-data">
	                            	<div class="modal-header">
		                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
		                                </button>
		                                <h4 class="modal-title">修改库存</h4>
	                                </div>
	                                <div class="modal-body cleanfix">
										<input type="number" class="form-control" id="modifyRemain"> 
										<input type="hidden" value="" id="modifyRemainId"> 
	                                </div>
	                                <div class="modal-footer">
                               		 	<button class="btn btn-primary" type="button" id="modifyRemainBtn">修改库存</button>
	                                    <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
	                                </div>
									</form>
	                            </div>
	                        </div>
	                    </div>
                     <!-- modal end -->        
    
  