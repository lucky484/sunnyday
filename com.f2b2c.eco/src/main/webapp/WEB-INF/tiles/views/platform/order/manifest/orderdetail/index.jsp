<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<spring:url value="/resources/platform/img/hxc_logo.png" var="logo"></spring:url>
<div class="wrapper wrapper-content animated fadeInRight">
	<div class="row">
		<!-- 头部打印按钮 -->
		<div class="ibox-title">
			<h5>发货清单</h5>
			<button class="btn btn-sm btn-warning pull-right m-t-n-sm"
				onclick="print();">导出</button>
		</div>
		<div class="content" id="content">
			<div class="rule">
				<div class="rule-child">
					<input type="hidden" name="id" id="oid" value="${orderInfo.orderId}"/>
					<table class="table-header">
						<tr>
							<td width="32%"><img src="${logo}" /></td>
							<td width="34%" align="center">
								<h2>好享吃商城</h2>
								<p>
									网址：<a href="javascript:;">http://shop.hxcchn.com</a>
								</p>
							</td>
						</tr>
						<tr>
							<td>订单编号：${orderInfo==null?"未知":orderInfo.orderId}</td>
							<td >联系电话：${orderInfo==null?"保密":orderInfo.user.phone}</td>
							<td >订单日期：<fmt:formatDate value="${orderInfo.createdTime}" pattern="yyyy-MM-dd"/></td>
						</tr>
						<tr >
							<td >客户名称：${orderInfo==null?"保密":orderInfo.shopName}</td>
							<td >联 系 人： ${orderInfo==null?"保密":orderInfo.user.realName}</td>
							<td >配送日期：<fmt:formatDate value="${orderInfo.sendTime}" pattern="yyyy-MM-dd"/></td>
						</tr>
						<tr>
							<td colspan="3">客户地址：${orderInfo==null?"未知":orderInfo.distributionArea}&nbsp;&nbsp;${orderInfo==null?"未知":orderInfo.receiverAddress}</td>
						</tr>
					</table>
				</div>
				<div class="rule-child" style="">
					<table class="table table-bordered-i">
						<thead>
							<tr>
								<th width="7%" >序号</th>
								<th width="30%">产品名称</th>
								<th width="11%">规格</th>
								<th width="8%" >数量</th>
								<th width="8%" >单位</th>
								<th width="12%">单价</th>
								<th width="12%">金额</th>
								<th width="15%">备注</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${orderInfo!=null and orderInfo.fSalesOrderDetailsModel!=null}">
								<c:if test="${fn:length(orderInfo.fSalesOrderDetailsModel) lt 12}">
									<c:forEach items="${orderInfo.fSalesOrderDetailsModel}" var="detail" varStatus="index">
										<tr>
	                                          <td  class="column-center">${index.index+1}</td>
	                                          <td  class="">${detail.goodsName}</td>
	                                          <td  class="column-center">${detail.goods==null?"":detail.goods.spec}</td>
	                                          <td  class="column-right">${detail.goodsQty}</td>
	                                          <td  class="column-center">箱</td>
	                                          <td  class="column-right">￥${detail.goods==null?"":detail.goods.price/100}</td>
	                                          <td  class="column-right">￥${(detail.price*detail.goodsQty)/100}</td>
	                                          <td></td>
	                                      </tr>
									</c:forEach>
									<c:forEach var="temp" begin="1" step="1" end="${12-fn:length(orderInfo.fSalesOrderDetailsModel)}"> 
										<tr>
	                                          <td  class="column-center blank"></td>
	                                          <td  class="blank"></td>
	                                          <td  class="column-center blank"></td>
	                                          <td  class="column-right blank"></td>
	                                          <td  class="column-center blank"></td>
	                                          <td  class="column-right blank"></td>
	                                          <td  class="column-right blank"></td>
	                                          <td></td>
	                                      </tr>
									</c:forEach>
								</c:if>
								<c:if test="${fn:length(orderInfo.fSalesOrderDetailsModel) gt 12}">
									<c:forEach items="${orderInfo.fSalesOrderDetailsModel}" var="detail" varStatus="index">
										<tr>
	                                          <td  class="column-center">${index.index+1}</td>
	                                          <td  class="">${detail.goodsName}</td>
	                                          <td  class="column-center">${detail.goods==null?"":detail.goods.spec}</td>
	                                          <td  class="column-right">${detail.goodsQty}</td>
	                                          <td  class="column-center">箱</td>
	                                          <td  class="column-right">￥${detail.goods==null?"":detail.goods.price/100}</td>
	                                          <td  class="column-right">￥${(detail.price*detail.goodsQty)/100}</td>
	                                          <td></td>
	                                      </tr>
									</c:forEach>
								</c:if>
							</c:if>
						</tbody>
						<tfoot>
							<tr>
								<th colspan="3">合计：</th>
								<th class="column-right">${orderInfo.goodsCount}</th>
								<th></th>
								<th></th>
								<th class="column-right">￥${orderInfo.total/100}</th>
								<th></th>
							</tr>
						</tfoot>
					</table>
				</div>
				<div class="rule-child" >
					<table class="table-header" >
						<tr >
							<td width="25" >制单：${producer}</td>
							<td width="25" >配送人员：</td>
							<td width="25" >客户签收：</td>
							<td width="25" >实收金额：</td>
						</tr>
					</table>
				</div>
				<div class="float-r upright">
					①存根︵ 白 ︶②财务︵ 红 ︶③客户︵ 黄 ︶ "
				</div>
			</div>
		</div>
	</div>
</div>
















