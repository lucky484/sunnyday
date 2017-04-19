<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spring:url value="/resources/market/js/plugins/ueditor1_4_3_3/ueditor.config.js" var="ueditorconfigJsUrl"/>
<script type="text/javascript" src="${ueditorconfigJsUrl}"></script> 
<spring:url value="/resources/market/js/plugins/ueditor1_4_3_3/ueditor.all.js" var="ueditorAllJsUrl"/>
<script type="text/javascript" src="${ueditorAllJsUrl}"></script> 
 <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                	<button class="btn btn-info" onclick="bgoodslistUrl()" style="">返回商品列表</button> 
                      <div class="ibox-content">
                      <div class="ibox-title">
                                    <h5>商品详情</h5>
                                </div>
                        <form method="post" class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">名称：</label>
                                <div class="col-sm-6">
                                    <input type="text" id="name" class="form-control" value="${data.name}">
                                </div>
                            </div>
                           <div class="form-group">
                                <label class="col-sm-3 control-label">商品类型：</label>
                                <div class="col-sm-3">
                                   <select class="form-control m-b" name="type" id="type">
                                        <option value="">
                                        				 <c:if test="${data.type == 1}">国产商品</c:if>
                                        				 <c:if test="${data.type == 2}">进口商品</c:if>
                                        				 <c:if test="${data.type == 3}">活动商品</c:if>
                                        				 <c:if test="${data.type == 4}">其他商品</c:if>
                                        				 </option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">分类：</label>
                                <input type="hidden" id="hiddenKindId" value="2">
                                <div class="col-sm-3">
                                   <select class="form-control m-b" name="account">
                                        <option value="1">${data.kind.kindName}</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">国产/进口：</label>
                                <div class="col-sm-3">
                                   <select class="form-control m-b" name="account" id="madeInChina">
                                       <c:if test="${data.madeInChina == 0}">														
											<option value="0">国产</option>
										</c:if>
										 <c:if test="${data.madeInChina == 1}">														
											<option value="1">进口</option>
										</c:if>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">图片：</label>
                                <div class="col-sm-6 logoUrlDiv">
									 <c:forEach items="${urllist}" var="list">
								      	<div class="picDiv" id="picDiv2">
										<img class='goodsImg' src='${list}' />
									</div>
								 	 </c:forEach>
								</div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">单价：</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="price" value="${data.price / 100}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">单位：</label>
                                <div class="col-sm-3">
                                  <select class="form-control m-b" name="account" id="unit">
                                        <option value="0" <c:if test="${data.unit == '0'}"> selected=true</c:if>>箱</option>
                                        <option value="1" <c:if test="${data.unit == '1'}"> selected=true</c:if> >斤</option>
                                        <option value="2"<c:if test="${data.unit == '2'}"> selected=true</c:if>>公斤</option>
                                    </select> 
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="col-sm-3 control-label">库存：</label>
                                <div class="col-sm-6">
                                    <input type="number" class="form-control" id="remain" value="${data.remain}">
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="col-sm-3 control-label">产地：</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" id="producePlace" value="${data.producePlace}">
                                </div>
                            </div>
                             <%--  <div class="form-group">
                                <label class="col-sm-3 control-label">重量：</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" id="unitOfWeight" value="${data.unitOfWeight}">
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="col-sm-3 control-label">规格：</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" id="spec" value="${data.spec}">
                                </div>
                            </div>  --%>
                            <c:if test="${data.sharePercent != '' &&  data.sharePercent != null}">
	                             <div class="form-group" id="yongjin" >
	                                <label class="col-sm-3 control-label">佣金比例<span class="flower">*</span>：</label>
	                                <div class="col-sm-3" >
	                                	<div class="input-group">
		                                    <input type="number" class="form-control" value="" size="16" id="sharePercent" value="${data.sharePercent}">
		                                    <span class="input-group-addon">%</span>
	                                    </div>
	                                </div>
	                            </div> 
                            </c:if>
                          <div class="form-group"> 
                               <label class="col-sm-3 control-label">详情：</label> 
                                <div class="col-sm-6"> 
                                   <div class="row"> 
								    <script id="container" name="content" type="text/plain"> 
											 ${data.detail} 
										 </script> 

								  		<script type="text/javascript"> 
   									    </script> 
									   </div> 
									   <input type="hidden" id="details" value=""> 
                                </div> 
                             </div> 
                             <div class="form-group">
                                <label class="col-sm-3 control-label">产品参数<span class="flower">*</span>：</label>
                                <div class="col-sm-6">
                                   <div class="row">
									    <script id="container2" name="content2" type="text/plain">${data.parameter}</script>
								  		<script type="text/javascript">
   									    </script>
									   </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label"></label>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
      </div>
      
  
    
  