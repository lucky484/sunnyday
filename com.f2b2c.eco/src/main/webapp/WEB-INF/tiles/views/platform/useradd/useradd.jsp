<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spring:url value="/farm/user/queryuserpages" var="otherUrl" />
<div class="wrapper wrapper-content animated fadeInRight">
	<div class="row">
		<a href="${otherUrl}" class="btn btn-default m-l">返回</a>
	</div>
	<div class="row">
		<div class="col-sm-12">
			<section class="panel panel-default">
				<header class="panel-heading font-bold">
					<span class="text-danger">请完善以下信息( * 号必填)</span>
				</header>
				<div class="panel-body">
					<spring:url value="/farm/user/save" var="signupUrl" />
					<form role="form" action="${signupUrl}" method="post"
						class="form-horizontal m-t">
						<div class="form-group">
							<div class="col-md-6">
								<label>账户名称(*)</label> <input type="text" name="accountName"
									class="form-control" placeholder="请输入账户名称..." required
									maxlength="40" onblur="checkName()"> <span
									class="help-block m-b-none text-danger hide" id="nameHint">该账户名称已存在</span>
							</div>
							<div class="col-md-6">
								<label>真实姓名(*)</label> <input type="text" name="realName"
									class="form-control" placeholder="请输入真实姓名..." required
									maxlength="40">
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6">
								<label>昵称</label> <input type="text" name="nickName"
									class="form-control" maxlength="40">
							</div>
							<div class="col-md-6">
								<label>手机号码(*)</label> <input type="text" name="phone"
									class="form-control" placeholder="请输入手机号码..." required
									maxlength="40">
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6">
								<label>登录密码(*)</label> <input type="password" name="password"
									class="form-control" placeholder="请输入登录密码..." required
									minlength=6 maxlength="40">
							</div>
							<div class="col-md-6">
								<label>确认密码(*)</label> <input type="password"
									name="confirm_password" class="form-control"
									placeholder="请再次确认密码..." required minlength=6 maxlength="40"
									onblur="checkIsSame()">
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6">
								<label>请选择角色(*)</label> <select class="form-control"
									name="roleId" id="roleId">
									<option value="-1" class="form-control" selected>请选择用户角色</option>
									<c:forEach items="${roles}" var="r" varStatus="stat">
										<!-- 不是最后一个元素 -->
										<c:if test="${!stat.last}">
										  <c:if test="${USER_INSESSION.roleId != 1}">
										    <c:if test="${r.id == 5}">
										     <option value="${r.id}" class="form-control">${r.roleName}</option>
										    </c:if>
										  </c:if>
										  <c:if test="${USER_INSESSION.roleId == 1}">
											<option value="${r.id}" class="form-control">${r.roleName}</option>
									      </c:if>
										</c:if>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-6 hide" id="provinceDiv">
								<label>选择省份(*)</label> <select
									class="form-control provinceSelect" name="provinceid">
										<option value="-1">请选择省份</option>
									<c:forEach items="${provinces}" var="p">
										<option value="${p.id}"
											ref="<c:forEach items="${p.cityModelList}" var="c" varStatus="index">${c.id}-${c.name}&<c:forEach items="${c.areaModelList}" var="a" varStatus="areaindex">${a.id}-${a.name}<c:if test="${areaindex.count!=c.areaModelList.size()}">,</c:if></c:forEach><c:if test="${index.count!=p.cityModelList.size()}">|</c:if></c:forEach>">${p.name}</option>
									</c:forEach>
								</select> <span class="help-block m-b-none text-danger hide"
									id="provinceHint">该省已存在合伙人</span>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6 hide" id="cityDiv">
								<label>选择城市(*)</label> <select class="form-control citySelect"
									id="cityselected" name="cityid">
									<option value="-1">请选择城市</option>
								</select> <span class="help-block m-b-none text-danger hide"
									id="cityHint">该市已存在合伙人</span>
							</div>
							<div class="col-md-6 hide" id="areaDiv">
								<label>选择地区(*)</label> <select class="form-control areaSelect"
									name="areaId">
									<option value="-1">请选择区域</option>
								</select> <span class="help-block m-b-none text-danger hide"
									id="areaHint">该区已存在合伙人</span>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6">
								<label>详细地址</label> <input type="text" name="address"
									class="form-control" placeholder="请输入详细地址..." maxlength="100">
							</div>
							<div class="col-md-6">
								<label>邮箱</label> <input type="text" name="email"
									class="form-control" maxlength="50">
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6 hide" id="shareProfitPerDiv">
								<label>分润比例(%)</label> <input type="text" name="shareProfitPer"
									class="form-control" placeholder="请输入分润比例..." maxlength="100">
							</div>
						</div>
						<div class="col-md-12">
							<input type="submit" class="btn btn-md btn-primary text-center" id="submit"
								value="立即注册">
						</div>
					</form>
				</div>
			</section>
		</div>
	</div>
</div>