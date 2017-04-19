<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spring:url value="/farm/admin/other" var="otherUrl" />
 <div class="wrapper wrapper-content animated fadeInRight">
 		<div class="row">
 			<a href="${otherUrl}" class="btn btn-primary m-l">新增用户</a>
 		</div>
 		<div class="row">
			<div class="col-sm-12">
				<section class="panel panel-default">
					<header class="panel-heading font-bold">请完善以下信息(*号必填)</header>
					<div class="panel-body">
						<spring:url value="/signup" var="signupUrl" />
						<form role="form" action="${signupUrl}" method="post">
							<div class="form-group">
								<div class="col-md-6">
									<label>账户名称(*)</label> <input type="text" name="accountName" class="form-control"
										placeholder="请输入账户名称..." required maxlength="40">
								</div>
								<div class="col-md-6">
									<label>真实姓名(*)</label> <input type="text" name="realName" class="form-control"
									placeholder="请输入真实姓名..." required maxlength="40">
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-6">
									<label>昵称</label> <input type="text" name="nickName" class="form-control" maxlength="40">
								</div>
								<div class="col-md-6">
									<label>手机号码(*)</label> <input type="text" name="phone" class="form-control"
									placeholder="请输入手机号码..." required maxlength="40">
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-6">
									<label>登录密码(*)</label> <input type="password" name="password"
										class="form-control" placeholder="请输入登录密码..." required minlength=6 maxlength="40">
								</div>
								<div class="col-md-6">
									<label>确认密码(*)</label> <input type="password" name="confirm_password"
										class="form-control" placeholder="请再次确认密码..." required minlength=6 maxlength="40">
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-6">
									<label>选择地区(*)</label> 
									<select class="form-control provinceSelect">
										<c:forEach items="${provinces}" var="p">
											<option value="${p.id}" ref="<c:forEach items="${p.cityModelList}" var="c" varStatus="index">${c.id}-${c.name}<c:if test="${index.count!=p.cityModelList.size()}">,</c:if></c:forEach>">${p.name}</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-md-6">
									<label>选择城市(*)</label>
									<select class="form-control citySelect" name="areaId">
										<option value="1">北京市</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-6">
									<label>邮箱</label> <input type="text" name="email" class="form-control"
										maxlength="50">
								</div>
								<div class="col-md-6">
									<label>详细地址</label> 
									<input type="text" name="address" class="form-control"
										placeholder="请输入详细地址..." maxlength="100">
								</div>
							</div>
								<div class="col-md-12">
									<input type="submit" class="btn btn-md btn-success text-center" value="立即注册">
								</div>
						</form>
					</div>
				</section>
			</div>
		</div>
      </div>