<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="wrapper wrapper-content animated fadeInRight">
	<div class="row">
		<div class="ibox-content col-sm-12"
					style="width: 100%; table-layout: fixed; overflow-x: auto;">
					<table class="table dataTables-orderlist text-center">
						<thead class="text-center">
							<tr>
								<th  class="hidden" style="text-align:center;">订单编号</th>
								<th>详情ID</th>
								<th>商品名称</th>
								<th>商品价格</th>
								<th>商品操作</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${fSalesOrderModel ne null }">
								<c:forEach items="${fSalesOrderModel.fSalesOrderDetailsModel}" var="shopModel">
								 	<tr>
	                     				<td class="hidden">${shopModel.order.id}</td>
	                     				<td>${shopModel.id}</td>
	                     				<td>${shopModel.goodsName}</td>
	                     				<td>${shopModel.price}</td>
	                     				<td>
	                     				<i class="fa fa-minus" onclick="minusAmount(this, ${shopModel.id})">
	                     				</i>&nbsp;<span>${shopModel.goodsQty}</span>&nbsp;
	                     				<i class="fa fa-plus" onclick="plusAmount(this, ${shopModel.id})"></i>&nbsp;
	                     				<i class="fa fa-close" onclick="delGoods(this, ${shopModel.id})"></i></td>
	                     			</tr> 
								 </c:forEach>
							</c:if>
							<input type="button" value="保存" onclick="updateOrderDetails()" >
						</tbody>
					</table>

	</div>
	</div>
</div>

