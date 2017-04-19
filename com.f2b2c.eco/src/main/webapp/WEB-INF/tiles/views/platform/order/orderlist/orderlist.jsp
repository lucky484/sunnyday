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
                    <div class="ibox-content col-sm-12" >
                    	 <div class="col-sm-2 search">
                            <input type="text" id="orderNo" name="orderNo" class="form-control" placeholder="请输入订单编号"/>
                        </div>
                        <div class="col-sm-2 search">
                            <input type="text" id="shopName" name="shopNames" class="form-control" placeholder="请输入店铺名称"/>
                        </div>
                    	<div class="col-sm-2 search"> 
                           <select class="form-control" id="status">
					 		<option class="form-control" value="">请选择订单状态</option>
					  		<option class="form-control" value="1">待支付订单</option>
					  		<option class="form-control" value="2">待发货订单</option>
					  		<option class="form-control" value="3">已发货订单</option>
					  		<option class="form-control" value="4">担保付款的订单</option>
					  		<option class="form-control" value="5">已完成订单</option>
					  		<option class="form-control" value="7">补差价订单</option>
						</select>
                        </div>
                        <div class="col-sm-2 search">
                            <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="startTime" name="startTime" readonly class="form-control" placeholder="请输入开始时间"/>
                        </div>
                        <div class="col-sm-2 search">
                            <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="endTime" name="endTime" readonly class="form-control" placeholder="请输入结束时间" />
                        </div>
                        <div class="col-sm-2">
                            <button class="btn-sm btn btn-primary" type="button" onclick="search();">查询</button>
                            <button class="btn-sm btn" type="button" onclick="reset();">清空</button>
                        </div>
				
					<table class="table dataTables-orderlist">
						<thead>
							<tr>
								<th width="6%">订单状态</th>
								<th width="8%">订单编号</th>
								<th width="6%">用户</th>
								<th width="8%">店铺名称</th>
								<th width="5%">总金额（元）</th>
								<th width="5%">实际付款（元）</th>
								<th width="13%">配送地区</th>
								<th width="12%">收货地址</th>
								<th width="8%">支付方式</th>
								<th width="7%">创建时间</th>
								<th width="7%">付款时间</th>
								<th width="7%">补差价金额</th>
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
