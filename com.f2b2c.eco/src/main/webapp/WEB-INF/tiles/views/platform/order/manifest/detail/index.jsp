<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
 <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox">
                    <div class="ibox-title">
                        <h5>出货清单</h5>
                        <button class="btn btn-sm btn-warning pull-right m-t-n-sm" onclick="print();">打印</button>
                    </div>
                    <div class="ibox-content">
                        <div class="content clearfix">
                           <div class="ibox-content col-sm-12">
                       <table class="table table-striped table-bordered table-hover dataTables-example" id="report">
                                        <thead>
                                        <tr>
                                            <th colspan="3" rowspan="3" class="text-center h3 font-bold">好享吃商城配送单</th>
                                            <th colspan="3">订单编号</th>
                                            <th colspan="4">${orderInfo==null?"未知":orderInfo.orderId}</th>
                                        </tr>
                                        <tr>
                                            <th colspan="3">订单日期</th>
                                            <th colspan="4"><fmt:formatDate value="${orderInfo.createdTime}" pattern="yyyy年MM月dd日"/></th>
                                        </tr>
                                        <tr>
                                            <th colspan="3">配送日期</th>
                                            <th colspan="4">${orderInfo==null?"未知":orderInfo.receiveTimeType==1?"工作日":orderInfo.receiveTimeType==2?"节假日":""}</th>
                                        </tr>
                                        <tr>
                                            <th class="th-sm">客户姓名</th>
                                            <th colspan="2">${orderInfo==null?"保密":orderInfo.user.realName}</th>
                                            <th colspan="2">客户地址</th>
                                            <th colspan="6">${orderInfo==null?"未知":orderInfo.distributionArea}&nbsp;&nbsp;${orderInfo==null?"未知":orderInfo.receiverAddress}</th>
                                        </tr>
                                        <tr>
                                            <th>店铺名称</th>
                                            <th colspan="2">${orderInfo==null?"保密":orderInfo.shopName}</th>
                                            <th colspan="2">联系电话</th>
                                            <th colspan="6">${orderInfo==null?"保密":orderInfo.user.phone}</th>
                                        </tr>
                                        
                                        <tr>
                                            <th colspan="10" class="text-center">订购货品</th>
                                        </tr>
                                        <tr class="gradeX">
                                            <th width="5%">序号</th>
                                            <th width="25%">商品名称</th>
                                            <!-- <th width="5%">规格</th> -->
                                            <th width="5%">单位</th>
  <!--                                           <th width="5%">单位重量</th> -->
                                            <th width="12%">单价</th>
                                            <th width="10%">订购数量</th>
                                            <th width="12%">订购价格</th>
                                            <th width="11%">实收金额</th>
                                            <th width="10%">差异处理</th>
                                        </tr>
                                        </thead>
                                        <tbody>
											<c:if test="${orderInfo!=null and orderInfo.fSalesOrderDetailsModel!=null}">
												<c:forEach items="${orderInfo.fSalesOrderDetailsModel}" var="detail" varStatus="index">
													<tr class="gradeX">
		                                            <td>${index.index+1}</td>
		                                            <td>${detail.goodsName}</td>
		                                            <!-- <td>暂无</td> -->
		                                            <td>${detail.goods==null?"":detail.goods.unit==0?"箱":detail.goods.unit==1?"斤":"公斤"}</td>
		                                            <!-- <td>0</td> -->
		                                            <td>￥${detail.goods==null?"":detail.goods.price/100}</td>
		                                            <td>${detail.goodsQty}</td>
		                                            <td>￥${(detail.price*detail.goodsQty)/100}</td>
		                                            <td></td>
		                                            <td></td>
		                                        </tr>
												</c:forEach>
											</c:if>
                                        <tr class="gradeX">
                                            <td>合计</td>
                                            <td colspan="7">￥${orderInfo.total/100}
                                            </td>
                                        </tr>
                                        <tr class="font-bold">
                                            <td colspan="3">取货员:</td>
                                            <td colspan="3">客户签收:</td>
                                            <td colspan="4">配送员:</td>
                                        </tr>
                                        </tbody>
                                    </table>

                    </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>