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
				<section class="panel panel-default">
					<header class="panel-heading font-bold"><span class="text-danger">请完善以下信息( * 号必填)</span></header>
					<div class="panel-body">
						<form role="form" action="" method="post" id="updateForm" class="form-horizontal m-t">
							<input type="hidden" name="id" value="${shopInfo.id}" />
							<div class="form-group">
								<div class="col-md-1 control-label">
									<label>门店名称：<span class="text-danger">*</span></label> 
								</div> 
								<div class="col-md-11">
									<input type="text" name="shopName" class="form-control" value="${shopInfo.shopName}"
										placeholder="请输入门店名称..." required maxlength="40">
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-1 control-label">
									<label>店主姓名：<span class="text-danger">*</span></label> 
								</div> 
								<div class="col-md-11">
								<!-- 店主采用下拉框，是该专属顾问所创建的所有店主集合 -->
									<select name="userName" class="form-control" id="userName" value="${shopInfo.userName}">
										<option value="-1">请选择店主...</option>
						             	<c:forEach items="${userList}" var="user">
						             		<c:choose>
						             			<c:when test="${user.realName eq shopInfo.userName}">
						             				<option value="${user.id}" selected>${user.realName}</option>
						             			</c:when>
						             			<c:otherwise>
						             				<option value="${user.id}">${user.realName}</option>
						             			</c:otherwise>
						             		</c:choose>
						             		
						             	</c:forEach>
						            </select>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-1 control-label">
									<label>身份证号：<span class="text-danger">*</span></label> 
								</div> 
								<div class="col-md-11">
									<input type="text" name="identityId" class="form-control" value="${shopInfo.identityId}"
										placeholder="请输入店主身份证号..." required maxlength="40">
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-1 control-label">
									<label>门店地址：<span class="text-danger">*</span></label> 
								</div> 
								<div class="col-md-11">
									<input type="text" name="address" class="form-control" value="${shopInfo.address}"
										placeholder="请输入门店地址..." required maxlength="40">
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-1 control-label">
									<label>门店经纬度_x：<span class="text-danger">*</span></label> 
								</div> 
								<div class="col-md-11">
									<input type="text" name="locationX" class="form-control" value="${shopInfo.locationX}"
										placeholder="请输入门店经纬度_x..." required maxlength="40">
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-1 control-label">
									<label>门店经纬度_y：<span class="text-danger">*</span></label> 
								</div> 
								<div class="col-md-11">
									<input type="text" name="locationY" class="form-control" value="${shopInfo.locationY}"
										placeholder="请输入门店经纬度_y..." required maxlength="40">
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-1 control-label">
									<label>门店详情：<span class="text-danger">*</span></label> 
								</div>
								<div class="col-md-11">
									<textarea id="editor" name="details" placeholder="这里输入门店详情" autofocus >${shopInfo.details}</textarea>
								</div> 
							</div>
							<footer class="row">
								<div class="col-sm-4 col-sm-offset-4 col-md-4 col-md-offset-4">
									<input type="button" class="btn btn-s-md btn-primary " value="修改" onclick="update();"/>&nbsp;&nbsp;
									<!-- <input type="button" class="btn btn-s-md btn-primary " value="取消" onclick="reset();"/> -->
								</div>
							</footer>
						</form>
					</div>
				</section>
			</div>
		</div>
      </div>