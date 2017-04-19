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
                           <h5>订单列表</h5>
                      </div>
                    
                      <div class="ibox-content">
                      <div class="row">
                            <div class="col-sm-2">
                            	 <input id="orderNo" type="text" class="form-control" placeholder="请输入订单号" />
                            </div>
                            <div class="col-sm-2">
                           		 <input id="receiverName" type="text" class="form-control" placeholder="请输入收货人姓名" />
                            </div>
                            <div class="col-sm-2">
                           		 <input id="goodsName" type="text" class="form-control" placeholder="请输入商品名称" />
                            </div>
                            <div class="col-sm-2">
                           		 <select class="form-control" id="goodsStatus">
								 		<option class="form-control" value="">请选择退款\退货状态</option>
								 		<option class="form-control" value="1">退款中</option>
								  		<option class="form-control" value="2">退货中</option>
								  		<option class="form-control" value="3">退款完成</option>
								  		<option class="form-control" value="4">退货完成</option>
								  		<option class="form-control" value="5">拒绝退款</option>
								  		<option class="form-control" value="6">拒绝退货</option>
								  </select>
                            </div>
                            <div class="col-sm-2">
                                <button class="btn-sm btn btn-primary" type="button" onclick="search();">查询</button>
                                <button class="btn-sm btn" type="button" onclick="reset();">清空</button>
                            </div>
                        </div>
                        <table id="returnOrderList" class="table table-striped  table-bordered">
                            <thead>
                              <tr>
                                <th>订单编号</th>
                                <th>收货人姓名</th>
                                <th>收货人电话</th>
                                <th>收货人地址</th>
                                <th>商品名称</th>
                                <th>商品数量</th>
                                <th>商品单价</th>
                                <th>退款/退货状态</th>
                                <th>退款/退货类型</th>
                                <th>收货类型</th>
                                <th>退款原因</th>
                                <th>订单金额</th>
                                <th>退款金额</th>
                                <th>订单创建时间</th>
                                <th width="8%">操作</th>
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