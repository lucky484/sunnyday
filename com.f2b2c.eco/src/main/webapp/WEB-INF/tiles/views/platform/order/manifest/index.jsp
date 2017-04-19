<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>货单列表</h5>
                        <div class="pull-right title">
                        	<a href="javascript:;" onclick="batchPrint();">导出</a>&nbsp;|
                        	<a href="javascript:;" onclick="setProducer();">设置制单人</a>
                        </div>
                    </div>
                    <div class="ibox-content col-sm-12" >
                    	 <div class="col-sm-2 search">
                            <input type="text" id="condition" name="condition" class="form-control" placeholder="请输入查询条件"/>
                        </div>
                    	<div class="col-sm-2 search"> 
                            <select class="form-control" name="type" id="type">
                            	<option value="-1" class="form-control" selected>请选择</option>
								<option value="1" class="form-control">已完成</option>
								<option value="4" class="form-control">待收货</option>
							</select>
                        </div>
                        <div class="col-sm-2 search">
                            <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="time" name="time" class="form-control" placeholder="请输入查询时间"/>
                        </div>
                        <div class="col-sm-6">
                            <button class="btn-sm btn btn-primary" type="button" onclick="search();">查询</button>
                            <button class="btn-sm btn" type="button" onclick="reset();">清空</button>
                        </div>
                        <table id="orderListTb" class="table">
                            <thead>
                                <tr>
                                	<th><input type="checkbox" onclick="checkAll(this);" id="checkAll"></th>
                                    <th>订单编号</th>
                                    <th>用户姓名</th>
                                    <th>商铺名称</th>
                                    <th>总金额</th>
                                    <th>实际付款</th>
                                    <th>配送地区</th>
                                    <th>收货地址</th>
                                    <th>订单状态</th>
                                    <th>支付方式</th>
                                    <th>创建时间</th>
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