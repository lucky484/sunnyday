<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="wrapper wrapper-content animated fadeInRight">
	<div class="row">
		<div class="col-sm-3">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<span class="label label-info pull-right">全部</span>
					<h5>F端商品数</h5>
				</div>
				<div class="ibox-content">
					<h1 class="no-margins">${goodscount } 件</h1>
<!-- 					<div class="stat-percent font-bold text-info"> -->
<!-- 						0% <i class="fa fa-level-up"></i> -->
<!-- 					</div> -->
<!-- 					<small>没有新添加</small> -->
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-3">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<span class="label label-info pull-right">全部</span>
					<h5>C端用户数</h5>
				</div>
				<div class="ibox-content">
					<h1 class="no-margins">${cusercount } 位</h1>
					<!-- <div class="stat-percent font-bold text-info">
						0% <i class="fa fa-level-up"></i>
					</div>
					<small>没有新添加</small> -->
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-3">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<span class="label label-info pull-right">全部</span>
					<h5>B端用户数</h5>
				</div>
				<div class="ibox-content">
					<h1 class="no-margins">${busercount } 位</h1>
					<!-- <div class="stat-percent font-bold text-info">
						0% <i class="fa fa-level-up"></i>
					</div>
					<small>没有新添加</small> -->
				</div>
			</div>
		</div>
	</div>
</div>