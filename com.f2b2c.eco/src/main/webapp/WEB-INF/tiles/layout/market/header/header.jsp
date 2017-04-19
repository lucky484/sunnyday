<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
 <spring:url value="/market/admin" var="homeUrl" />
 <spring:url value="/market/logout" var="logoutUrl" />
 <spring:url value="/market/bgoods/bgoodslist" var="bgoodslistUrl" />
 <spring:url value="/market/bgoods/publishgoods" var="bGoodsUrl" />
 <spring:url value="/market/freight/index" var="bFreightUrl" />
 <spring:url value="/market/border/orderlist" var="borderUrl" />
 <spring:url value="/market/border/getReplyOrder" var="replyOrderUrl" />
<!--左侧导航开始-->
<nav class="navbar navbar-static-top" role="navigation">
    <div class="navbar-header" style="width: 80%;">
        <button aria-controls="navbar" aria-expanded="false" data-target="#navbar" data-toggle="collapse" class="navbar-toggle collapsed" type="button">
            <i class="fa fa-reorder"></i>
        </button>
        <a href="javascript:void(0);" class="navbar-brand">店铺 : ${MARKET_USER.shopName} </a>
        <ul class="nav navbar-nav">
            <li class="active">
                <a aria-expanded="false" role="button" href="${bgoodslistUrl}"><span class="glyphicon glyphicon-th-list"></span>&nbsp;商品列表</a>
            </li>
            <li class="active">
                <a aria-expanded="false" role="button" href="${bGoodsUrl}"> <span class="glyphicon glyphicon-pencil"></span>&nbsp;发布商品</a>
            </li>
            <li class="active">
                <a aria-expanded="false" role="button" href="${borderUrl}"> <span class="glyphicon glyphicon-file"></span>&nbsp;订单管理</a>
            </li>
            <li class="active">
                <a aria-expanded="false" role="button" href="${replyOrderUrl}"> <span class="fa fa-reply"></span>&nbsp;退款/退货商品</a>
            </li>
            <li class="active">
                <a aria-expanded="false" role="button" href="${bFreightUrl}"> <span class="glyphicon glyphicon-wrench"></span>&nbsp;运费设置</a>
            </li>
            <li class="active">
                <a aria-expanded="false" role="button" onclick="qrcode()"> <span class="glyphicon glyphicon-qrcode"></span>&nbsp;我的分享二维码</a>
            </li>
        </ul>
    </div>
    <div class="navbar-collapse collapse" id="navbar">
        <%-- <ul class="nav navbar-nav">
            <li class="active">
                <a aria-expanded="false" role="button" href="${bgoodslistUrl}"> 商品列表</a>
            </li>
             <li class="active">
                <a aria-expanded="false" role="button" href="${bGoodsUrl}"> 发布商品</a>
            </li>
        </ul> --%>
        <ul class="nav navbar-top-links navbar-right">
            <li>
                <a href="${logoutUrl}">
                    <i class="fa fa-sign-out"></i> 退出
                </a>
            </li>
        </ul>
    </div>
</nav>
