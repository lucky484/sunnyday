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
					<div class="ibox-content">
						<form role="form" method="post" id="addForm" class="form-horizontal m-t">
							<div class="form-group">
								<div class="col-md-2 control-label">
									<label>门店名称：<span class="text-danger">*</span></label> 
								</div> 
								<div class="col-md-10">
									<input type="text" name="shopName" class="form-control"
										placeholder="请输入门店名称..." required maxlength="40">
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-2 control-label">
									<label>店主姓名：<span class="text-danger">*</span></label> 
								</div> 
								<div class="col-md-10">
								<!-- 店主采用下拉框，是该专属顾问所创建的所有店主集合 -->
									<select name="userName" class="form-control" id="userName" >
										<option value="-1">请选择店主...</option>
						             	<c:forEach items="${userList}" var="user">
						             		<option value="${user.id}">${user.realName}</option>
						             	</c:forEach>
						            </select>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-2 control-label">
									<label>身份证号：<span class="text-danger">*</span></label> 
								</div> 
								<div class="col-md-10">
									<input type="text" name="identityId" class="form-control"
										placeholder="请输入店主身份证号..." required maxlength="40">
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-2 control-label">
									<label>门店地址：<span class="text-danger">*</span></label> 
								</div> 
								<div class="col-md-10">
									<input type="text" name="address" class="form-control"
										placeholder="请输入门店地址..." required maxlength="40">
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-2 control-label">
									<label>门店经纬度_x：<span class="text-danger">*</span></label> 
								</div> 
								<div class="col-md-10">
									<input type="text" name="locationX" class="form-control"
										placeholder="请输入门店经纬度_x..." required maxlength="40">
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-2 control-label">
									<label>门店经纬度_y：<span class="text-danger">*</span></label> 
								</div> 
								<div class="col-md-10">
									<input type="text" name="locationY" class="form-control"
										placeholder="请输入门店经纬度_y..." required maxlength="40">
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-2 control-label">
									<label>授权码：<span class="text-danger">*</span></label> 
								</div> 
								<div class="col-md-10">
									<input type="text" name="authCode" class="form-control"
										placeholder="请输入门店授权码..." required maxlength="40">
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-2 control-label">
									<label>门店详情：<span class="text-danger">*</span></label> 
								</div>
								<div class="col-md-10">
									<textarea id="editor" name="details" placeholder="这里输入门店详情" autofocus ></textarea>
								</div> 
							</div>
							<footer class="row">
								<div class="col-sm-4 col-sm-offset-2 col-md-4">
									<input type="button" class="btn btn-s-md btn-primary " value="添加" onclick="add();"/>&nbsp;&nbsp;
								</div>
							</footer>
						</form>
					</div>
				</section>
			</div>
		</div>
      </div>