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
                           		 <select class="form-control" id="catalog">
								 		<option class="form-control" value="">请选择订单类型</option>
								 		<option class="form-control" value="0">水果订单</option>
								  		<option class="form-control" value="1">非水果订单</option>
								   </select>
                            </div>
                             <div class="col-sm-2">
                           		 <select class="form-control" id="payType">
								 		<option class="form-control" value="">请选择付款类型</option>
								 		<option class="form-control" value="0">已付款</option>
								  		<option class="form-control" value="1">未付款</option>
								   </select>
                            </div>
                            <div class="col-sm-2 search"> 
		                           <select class="form-control" id="status">
								 		<option class="form-control" value="">请选择订单状态</option>
								 		<option class="form-control" value="1">已完成</option>
								  		<option class="form-control" value="2">待支付</option>
								  		<option class="form-control" value="3">待发货</option>
								  		<option class="form-control" value="4">待收货</option>
								  		<option class="form-control" value="6">已取消</option>
								   </select>
	                        </div>
                            <div class="col-sm-2">
                                <button class="btn-sm btn btn-primary" type="button" onclick="search();">查询</button>
                                <button class="btn-sm btn" type="button" onclick="reset();">清空</button>
                            </div>
                        </div>
                        <table id="orderList" class="table table-striped  table-bordered">
                            <thead>
                              <tr>
                                <th>订单编号</th>
                                <th>收货人姓名</th>
                                <th>收货人电话</th>
                                <th>收货人地址</th>
                                <th>订单状态</th>
                                <th>订单类型</th>
                                <th>总金额（元）</th>
                                <th>运费（元）</th>
                                <th>实际付款（元）</th>
                                <th>创建时间</th>
                                <th>付款时间</th>
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