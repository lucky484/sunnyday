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
				<spring:url value="/farm/order" var="orderlistUrl" />
				<a class="btn btn-info" href="${orderlistUrl}" style="">返回订单列表</a>
				<div class="ibox-content">
					<div class="ibox-title">
						<h5>订单详情</h5>
					</div>
					<form method="post" class="form-horizontal">
						<div class="form-group">
							<div class="col-md-6">
								<div class="form-group">
									<label class="col-sm-3 control-label">订单编号：</label>
									<div class="col-sm-9">
										<input type="text" name="" class="form-control"
											value="${bToCOrderModel.orderNo}">

									</div>
								</div>
								<div class="form-group ui-sortable-helper">
									<label class="col-sm-3 control-label">创建时间：</label>
									<div class="col-sm-9">
										<input type="text" name="" class="form-control"
											value="${bToCOrderModel.createdTime}">

									</div>
								</div>
								<div class="form-group ui-sortable-helper">
									<label class="col-sm-3 control-label">支付方式：</label>
									<div class="col-sm-9">
										<input type="text" name="" class="form-control"
											<c:if test="${bToCOrderModel.payType ==0}">
																						        		value="担保货到付款"
																							        </c:if>
											<c:if test="${bToCOrderModel.payType ==1}">
																						        		value="微信支付"
																							        </c:if>
											<c:if test="${bToCOrderModel.payType ==2}">
																						        		value="支付宝支付"
																							        </c:if>>
									</div>
								</div>
								<div class="form-group ui-sortable-helper">
									<label class="col-sm-3 control-label">收货地址：</label>
									<div class="col-sm-9">
										<input type="text" name="" class="form-control"
											value="${bToCOrderModel.receiverAddress}">

									</div>
								</div>
								<div class="form-group ui-sortable-helper">
									<label class="col-sm-3 control-label">收货时间：</label>
									<div class="col-sm-9">
										<input type="text" name="" class="form-control"
											<c:if test="${bToCOrderModel.receiveTimeType ==1}">
																						        		value="工作日"
																							        </c:if>
											<c:if test="${bToCOrderModel.receiveTimeType ==2}">
																						        		value="节假日"
																							        </c:if>
											<c:if test="${bToCOrderModel.receiveTimeType ==3}">
																						        		value="任何时间"
																							        </c:if>>

									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group ui-sortable-helper">
									<label class="col-sm-3 control-label">店铺：</label>
									<div class="col-sm-9">
										<input type="text" name="" class="form-control"
											value="${bToCOrderModel.shopName}">

									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">订单状态：</label>
									<div class="col-sm-9">
										<input type="text" name="" class="form-control"
											<c:if test="${bToCOrderModel.status ==1}">
																						        	value="已完成"
																							        </c:if>
											<c:if test="${bToCOrderModel.status ==2}">
																					        		value="待支付"
																							        </c:if>
											<c:if test="${bToCOrderModel.status ==3}">
																						        	value="待发货"
																							        </c:if>
											<c:if test="${bToCOrderModel.status ==4}">
																						        	value="待收货" 
																							        </c:if>
											<c:if test="${bToCOrderModel.status ==5}">
																						        	value="待评价" 
																							        </c:if>>
									</div>
								</div>
								<div class="form-group ui-sortable-helper">
									<label class="col-sm-3 control-label">收货人：</label>
									<div class="col-sm-9">
										<input type="text" name="" class="form-control"
											value="${bToCOrderModel.receiverName}">
									</div>
								</div>
								<div class="form-group ui-sortable-helper">
									<label class="col-sm-3 control-label">收货人联系方式：</label>
									<div class="col-sm-9">
										<input type="text" name="" class="form-control"
											value="${bToCOrderModel.receiverTelephone}">

									</div>
								</div>
								<div class="form-group ui-sortable-helper">
									<label class="col-sm-3 control-label">买家留言：</label>
									<div class="col-sm-9">
										<input type="text" name="" class="form-control"
											value="${bToCOrderModel.remark}">

									</div>
								</div>
							</div>
						</div>
						<div class="ibox-title">
							<h5>商品详情</h5>
						</div>
						<div class="form-group">
							<table class="table table-striped">
								<thead>
									<tr>
										<th>序号</th>
										<th>商品名称</th>
										<th>商品图片</th>
										<th>单价</th>
										<th>数量</th>
										<th>商品总价(元)</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${bToCOrderModel.list}" var="list"
										varStatus="i">
										<tr>
											<td width="10%">${i.index+1}</td>
											<td width="30%">${list.name}</td>
											<td width="15%"><img src="${list.logoUrl}" width="23px"
												height="23px" /></td>
											<td width="15%">${list.price / 100}</td>
											<td width="15%">${list.quantity}</td>
											<td width="15%">${list.price * list.quantity /100}</td>
										</tr>
									</c:forEach>

								</tbody>
							</table>
						</div>
						<c:choose>
							<c:when test="${bToCOrderModel.diffenceAmount == null}">
								<div class="form-group">
									<div class="col-md-10"></div>
									<div class="col-md-2">
										<label class="control-label">订单总金额：${bToCOrderModel.total / 100}
											元</label>
									</div>
								</div>
							</c:when>
							<c:otherwise>
								<div class="ibox-title">
									<h5>差价详情</h5>
								</div>
								<div class="form-group">
									<table class="table table-striped">
										<thead>
											<tr>
												<th>差价类型</th>
												<th>差价原因</th>
												<th>差价备注</th>
												<th>差价金额</th>
												<th>审批状态</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td><c:if test="${bToCOrderModel.diffenceType == 0}">补</c:if>
													<c:if test="${bToCOrderModel.diffenceType == 1}">退</c:if></td>
												<td><c:if test="${bToCOrderModel.diffenceCause == 1}">产品重量</c:if>
													<c:if test="${bToCOrderModel.diffenceCause == 2}">我要换货</c:if>
													<c:if test="${bToCOrderModel.diffenceCause == 3}">我要退货</c:if>
													<c:if test="${bToCOrderModel.diffenceCause == 4}">其他</c:if></td>
												<td>${bToCOrderModel.diffenceRemark}</td>
												<td>${bToCOrderModel.diffenceAmount / 100}</td>
												<td>
													<c:if test="${bToCOrderModel.diffenceStatus == 1}">待审核</c:if>
													<c:if test="${bToCOrderModel.diffenceStatus == 2}">审核通过</c:if>
													<c:if test="${bToCOrderModel.diffenceStatus == 3}">审核未通过</c:if>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="form-group">
									<div class="col-md-10"></div>
									<div class="col-md-2">
										<label class="control-label">订单总金额：${bToCOrderModel.total / 100}元</label>
									</div>
									<div class="col-md-10"></div>
									<div class="col-md-2">
										<label class="control-label">差价金额<c:if
												test="${bToCOrderModel.diffenceType == 0}">(补)</c:if> <c:if
												test="${bToCOrderModel.diffenceType == 1}">(退)</c:if>：${bToCOrderModel.diffenceAmount / 100}元
										</label>
									</div>
									<div class="col-md-10"></div>
									<div class="col-md-2">
										<label class="control-label">实际金额：${bToCOrderModel.realTotalPrice / 100}元
										</label>
									</div>
								</div>
							</c:otherwise>
						</c:choose>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>



