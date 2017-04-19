<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="ibox-content">
	<div style="width: 910px; margin-left: auto; margin-right: auto;">
		<div class="ibox-title">
			<h5>订单详情</h5>
		</div>
		<div class="ibox-content">
			<div class="flow-steps">
				<ol class="num4" style="margin-left:-58px;">
				    <c:if test="${btc.status == 2}">
						<li class="done last-current-2"><span class="first-1">1.确认订单信息</span></li>
						<li class="done"><span>2.已付款</span></li>
						<li class="done"><span>3.确认收货</span></li>
						<li class="last-current-1"><span>4.已完成</span></li>
					</c:if>
					<c:if test="${btc.status == 3}">
						<li class="done current-prev"><span class="first">1.确认订单信息</span></li>
						<li class="done last-current-2"><span>2.已付款</span></li>
						<li class="done"><span>3.确认收货</span></li>
						<li class="last-current-1"><span>4.已完成</span></li>
					</c:if>
					<c:if test="${btc.status == 4}">
						<li class="done"><span class="first">1.确认订单信息</span></li>
						<li class="done current-prev"><span>2.已付款</span></li>
						<li class="done last-current-2"><span>3.确认收货</span></li>
						<li class="last-current-1"><span>4.已完成</span></li>
					</c:if>
					
					<c:if test="${btc.status == 1}">
						<li class="done"><span class="first">1.确认订单信息</span></li>
						<li class="done"><span>2.已付款</span></li>
						<li class="done current-prev"><span>3.确认收货</span></li>
						<li class="last-current"><span>4.已完成</span></li>
					</c:if>
					
					<c:if test="${btc.status == 6}">
						<li class="done"><span class="first">1.确认订单信息</span></li>
						<li class="done"><span>2.已付款</span></li>
						<li class="done current-prev"><span>3.确认收货</span></li>
						<li class="last-current"><span>4.已完成</span></li>
					</c:if>
				</ol>
			</div>
			<div class="tabs-container">
				<ul class="tabs-nav">
					<li class="current"><a>订单信息</a></li>
				</ul>
				<div class="tabs-panels" style="background-color: #fff;">
					<div class="info-box order-info" style="display: block;">
						<div class="bd">
							<table>
								<colgroup>
									<col width="280">
									<col width="110">
									<!-- 商品名称-->
									<col class="col1">
									<!-- 交易状态 -->
									<col class="col2">
									<!-- 单价（元） -->
									<col class="col3">
									<!-- 数量 -->
									<col class="col4">
									<!-- 总价 -->
									<col class="col5">
									<!-- 合计（元） -->
									<col class="col6">
									<!-- 运费（元） -->
									<!-- 买/卖家信息 -->
								</colgroup>
								<tbody class="contact-info">
									<tr>
										<th colspan="8">
											<h4>卖家信息</h4>
										</th>
									</tr>
									<tr>
										<td>昵称：<span class="nickname">${btc.shopName}</span>
										</td>
										<td colspan="3">店长姓名：<span class="name">${bShopInfo.user.realName}</span>
										</td>
										<td colspan="4">城市：<span class="city">${bShopInfo.areaName}</span>
										</td>
									</tr>
									<tr>
										<td>联系电话：<span class="tel">${bShopInfo.user.phone}</span>
										</td>
										<td colspan="3">
										</td>
										<td colspan="4">
										</td>
									</tr>
								</tbody>
								<tbody class="misc-info">
									<tr class="sep-row">
										<td colspan="8"></td>
									</tr>
									<tr>
										<th colspan="8">
											<h4>订单信息</h4>
										</th>
									</tr>
									<tr>
										<td><span class="span">订单编号：</span> <span
											class="order-num">${btc.orderNo}</span></td>
										<td colspan="3"><span class="span">交易方式：</span>
										<c:if test="${btc.payType == 0}">
											<span class="pay-type">支付宝支付</span>
										</c:if>
										<c:if test="${btc.payType == 1}">
											<span class="pay-type">微信支付</span>
										</c:if>
										<c:if test="${btc.payType == 2}">
											<span class="pay-type">余额支付</span>
										</c:if>
										 </td>
									</tr>
									<tr>
										<td><span class="span">成交时间：</span> <span
											class="trad-time">${btc.createdTime}</span></td>
										<td colspan="3"><span class="span">付款时间：</span> <span
											class="pay-time"><fmt:formatDate value="${btc.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span></td>
										<td colspan="4"><span class="span">确认时间：</span> <span
											class="confirm-time"><fmt:formatDate value="${btc.receiveTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span></td>
									</tr>
								</tbody>
								<tbody>
									<tr>
										<td colspan="6"></td>
									</tr>
									<tr class="order-hd">
										<th class="item">商品名称</th>
										<th class="status">状态</th>
										<th class="price">单价（元）</th>
										<th class="num">数量</th>
										<th class="order-price">商品总价（元）</th>
										<th class="post-fee">运费（元）</th>
									</tr>
									<c:forEach var="list" varStatus="i" items="${btc.list}">
									<tr class="order-item">
										<td class="item">
											<div class="pic-info">
												<div class="pic s50">
													<a hidefocus="true" href="javascript:;" title="商品图片">
														<img src="${list.logoUrl}" />
													</a>
												</div>
											</div>
											<div class="text-info">
												<div class="desc">
													<span style="color:#676a6c">${list.name}</span>
												</div>
											</div>
										</td>
										<c:if test="${btc.status == 1}">
										    <c:if test="${list.goodsStatus != null}">
										 	   <c:if test="${list.goodsStatus == 3}">
										  	       <td>退款完成</td>
										 	   </c:if>
										 	   <c:if test="${list.goodsStatus == 4}">
										  	       <td>退货完成</td>
										 	   </c:if>
										 	   <c:if test="${list.goodsStatus == 5}">
										  	       <td>拒绝退款</td>
										 	   </c:if>
										 	   <c:if test="${list.goodsStatus == 6}">
										  	       <td>拒绝退货</td>
										 	   </c:if>
										  </c:if>
										  <c:if test="${list.goodsStatus == null}">
										   	<td>已确认收货</td>
										  </c:if>
										</c:if>
										<c:if test="${btc.status == 2}">
										   <td>待付款</td>
										</c:if>
										<c:if test="${btc.status == 3}">
										   <td>等待商家发货</td>
										</c:if>
										<c:if test="${btc.status == 4}">
										  <c:if test="${list.goodsStatus != null}">
										       <c:if test="${list.goodsStatus == 1}">
										           <td>退款中</td>
										  	   </c:if>
										  	   <c:if test="${list.goodsStatus == 2}">
										  	       <td>退货中</td>
										 	   </c:if>
										 	   <c:if test="${list.goodsStatus == 3}">
										  	       <td>退款完成</td>
										 	   </c:if>
										 	   <c:if test="${list.goodsStatus == 4}">
										  	       <td>退货完成</td>
										 	   </c:if>
										 	   <c:if test="${list.goodsStatus == 5}">
										  	       <td>拒绝退款</td>
										 	   </c:if>
										 	   <c:if test="${list.goodsStatus == 6}">
										  	       <td>拒绝退货</td>
										 	   </c:if>
										  </c:if>
										  <c:if test="${list.goodsStatus == null}">
										   	<td>待收货</td>
										  </c:if>
										</c:if>
										<c:if test="${btc.status == 6}">
										    <td>用户已取消订单</td>
										</c:if>
										<td class="price">${list.price / 100}</td>
										<td class="num">${list.quantity}</td>
										<td class="order-price" rowspan="1">${list.price * list.quantity / 100}</td>
										<td class="post-fee" rowspan="1"><span>${btc.freight / 100}</span> <br>(快递 )</td>
									</tr>
									</c:forEach>
									<tr class="order-ft">
										<td colspan="6">
											<div class="get-money" colspan="6">
										        <c:if test="${btc.payTime != null }">
													<br>实付款： <strong>${btc.realPay / 100}</strong>元<br>
												</c:if>
												 <c:if test="${btc.payTime == null }">
													<br>实付款： <strong>0.00</strong>元<br>
												</c:if>
											</div>
										</td>
									</tr>
								</tbody>
								<table class="simple-list logistics-list">
									<tbody>
										<tr class="sep-row">
											<td colspan="8"></td>
										</tr>
										<tr>
											<th colspan="3"><h4>物流信息</h4></th>
										</tr>
										<tr>
											<td class="span">收货地址：</td>
											<td colspan="7">${btc.receiverName} ，${btc.reveiverMobile} ，${btc.receiverAddress}</td>
										</tr>
										<c:if test="${btc.catalog eq 0}">
										    <tr>
												<td class="span">运送方式：</td>
												<td colspan="7">商家配送</td>
											</tr>
										</c:if>
										<c:if test="${btc.catalog eq 1}">
											<tr>
												<td class="span">运送方式：</td>
												<td colspan="7">快递</td>
											</tr>
											<tr>
												<td class="span">物流公司：</td>
												<td colspan="7">${btc.logisticsCode}</td>
											</tr>
											<tr>
												<td class="span">运单号：</td>
												<td colspan="7">${btc.waybillNumber}</td>
											</tr>
										</c:if>
										<tr>
											<td class="span">买家留言：</td>
											<td colspan="7"></td>
										</tr>
									</tbody>
								</table>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
