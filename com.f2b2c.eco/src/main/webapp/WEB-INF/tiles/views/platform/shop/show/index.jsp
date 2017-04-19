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
					<header class="panel-heading font-bold">查看门店详情</header>
					<div class="panel-body">
						<form role="form" action="" method="post" id="showForm" class="form-horizontal m-t">
							<div class="form-group">
								<div class="col-md-1 control-label">
									<label>门店名称：</label> 
								</div> 
								<div class="col-md-11">
									<input type="text" name="shopName" class="form-control" value="${shopInfo.shopName}"
										placeholder="请输入门店名称..." required maxlength="40" disabled>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-1 control-label">
									<label>店主姓名：</label> 
								</div> 
								<div class="col-md-11">
									<input type="text" name="userName" class="form-control" value="${shopInfo.userName}"
										placeholder="请输入门店名称..." required maxlength="40" disabled>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-1 control-label">
									<label>身份证号：</label> 
								</div> 
								<div class="col-md-11">
									<input type="text" name="identityId" class="form-control" value="${shopInfo.identityId}"
										placeholder="请输入店主身份证号..." required maxlength="40" disabled>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-1 control-label">
									<label>门店地址：</label> 
								</div> 
								<div class="col-md-11">
									<input type="text" name="address" class="form-control" value="${shopInfo.address}"
										placeholder="请输入门店地址..." required maxlength="40" disabled>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-1 control-label">
									<label>门店经纬度_x：</label> 
								</div> 
								<div class="col-md-11">
									<input type="text" name="locationX" class="form-control" value="${shopInfo.locationX}"
										placeholder="请输入门店经纬度_x..." required maxlength="40" disabled>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-1 control-label">
									<label>门店经纬度_y：</label> 
								</div> 
								<div class="col-md-11">
									<input type="text" name="locationY" class="form-control" value="${shopInfo.locationY}"
										placeholder="请输入门店经纬度_y..." required maxlength="40" disabled>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-1 control-label">
									<label>授权码：</label> 
								</div> 
								<div class="col-md-11">
									<input type="text" name="authCode" class="form-control" value="${shopInfo.authCode}"
										placeholder="请输入门店授权码..." required maxlength="40" disabled>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-1 control-label">
									<label>门店详情：</label> 
								</div>
								<div class="col-md-11">
									<textarea id="editor" name="details" placeholder="这里输入门店详情" autofocus disabled>${shopInfo.details}</textarea>
								</div> 
							</div>
							<footer class="row">
								<div class="col-sm-4 col-sm-offset-4 col-md-4 col-md-offset-4">
									<input type="button" class="btn btn-s-md btn-primary " value="确定" onclick="confirm();"/>&nbsp;&nbsp;
									<!-- <input type="button" class="btn btn-s-md btn-primary " value="取消" onclick="reset();"/> -->
								</div>
							</footer>
						</form>
					</div>
				</section>
			</div>
		</div>
      </div>