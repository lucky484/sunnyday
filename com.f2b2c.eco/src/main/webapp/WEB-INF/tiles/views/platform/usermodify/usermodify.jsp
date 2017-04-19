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
 			<a href="${otherUrl}" class="btn btn-primary m-l">返回</a>
 		</div>
 		<div class="row">
			<div class="col-sm-12">
				<section class="panel panel-default">
					<header class="panel-heading font-bold"><span class="text-danger">请完善以下信息( * 号必填)</span></header>
					<div class="panel-body">
						<spring:url value="/farm/user/update" var="updateUserUrl" />
						<form action="${updateUserUrl}" method="post" class="form-horizontal m-t">
							<input type="hidden" name="id" class="form-control" value="${fUserModel.id}"/>
							<div class="form-group">
								<div class="col-md-6">
									<label>账户名称(*)</label> <input type="text" name="accountName" class="form-control" onblur="checkName()" value="${fUserModel.accountName}" readonly="readonly"/>
									<!-- <span class="help-block m-b-none text-danger hide" id="nameHint">该账户名称已存在</span> -->
								</div>
								<div class="col-md-6">
									<label>真实姓名(*)</label> <input type="text" name="realName" class="form-control" value="${fUserModel.realName}"/>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-6">
									<label>昵称</label> <input type="text" name="nickName" class="form-control" value="${fUserModel.nickName}"/>
								</div>
								<div class="col-md-6">
									<label>手机号码(*)</label> <input type="text" name="phone" class="form-control" value="${fUserModel.phone}"/>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-6">
									<label>请选择角色(*)</label> 
									<select class="form-control" name="roleId" id="roleId">
										<option value="-1" class="form-control" selected>请选择用户角色</option>
										<c:forEach items="${roles}" var="r">
											<c:choose>
												<c:when test="${roleId eq r.id }">
													<option value="${r.id}" class="form-control" selected>${r.roleName}</option>
												</c:when>
												<c:otherwise>
													<!-- 不是最后一个元素 -->
													<c:if test="${!stat.last}">
														<option value="${r.id}" class="form-control">${r.roleName}</option>
													</c:if>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</div>
								<div class="col-md-6 <c:if test='${roleId ne 2 and roleId ne 3 and roleId ne 4 and roleId ne 5 }'>hide</c:if>" id="provinceDiv">
									<label>选择省份(*)</label> 
									<select class="form-control provinceSelect" name="provinceid">
										<option value="-1">请选择省份</option>
										<c:forEach items="${provinces}" var="p">
											<c:if test="${fUserModel.provinceid eq p.id}">
												<option value="${p.id}" selected="selected" ref="<c:forEach items="${p.cityModelList}" var="c" varStatus="index">${c.id}-${c.name}&<c:forEach items="${c.areaModelList}" var="a" varStatus="areaindex">${a.id}-${a.name}<c:if test="${areaindex.count!=c.areaModelList.size()}">,</c:if></c:forEach><c:if test="${index.count!=p.cityModelList.size()}">|</c:if></c:forEach>">${p.name}</option>
											</c:if>
											<c:if test="${fUserModel.provinceid ne p.id}">
												<option value="${p.id}" ref="<c:forEach items="${p.cityModelList}" var="c" varStatus="index">${c.id}-${c.name}&<c:forEach items="${c.areaModelList}" var="a" varStatus="areaindex">${a.id}-${a.name}<c:if test="${areaindex.count!=c.areaModelList.size()}">,</c:if></c:forEach><c:if test="${index.count!=p.cityModelList.size()}">|</c:if></c:forEach>">${p.name}</option>
											</c:if>
										</c:forEach>
									</select>
									<span class="help-block m-b-none text-danger hide"
									id="provinceHint">该省已存在合伙人</span>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-6 <c:if test='${roleId ne 3 and roleId ne 4 and roleId ne 5 }'>hide</c:if>" id="cityDiv">
									<label>选择城市(*)</label>
									<select class="form-control citySelect" id="cityselected" name="cityid">
										<option value="-1">请选择城市</option>
										<c:forEach items="${cityModels}" var="c">
											<c:if test="${fUserModel.cityid eq c.id}">
												<option value="${fUserModel.cityid}" selected="selected">${c.name}</option>
											</c:if>
											<c:if test="${fUserModel.cityid ne c.id}">
												<option value="${c.id}">${c.name}</option>
											</c:if>
										</c:forEach>
									</select>
									 <span class="help-block m-b-none text-danger hide"
									id="cityHint">该市已存在合伙人</span>
								</div>
								<div class="col-md-6 <c:if test='${roleId ne 4 and roleId ne 5 }'>hide</c:if>" id="areaDiv">
									<label>选择地区(*)</label>
									<select class="form-control areaSelect" name="areaId">
										<option value="-1">请选择区域</option>
										<c:forEach items="${areaModels}" var="a">
											<c:if test="${fUserModel.areaId eq a.id}">
												<option value="${fUserModel.areaId}" selected="selected">${a.name}</option>
											</c:if>
											<c:if test="${fUserModel.areaId ne a.id}">
												<option value="${a.id}">${a.name}</option>
											</c:if>
										</c:forEach>
									</select>
									<span class="help-block m-b-none text-danger hide"
									id="areaHint">该区已存在合伙人</span>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-6">
									<label>密码</label> 
									<input type="password" name="password" class="form-control" value=""  placeholder="请输入登录密码..." 
									minlength=6 maxlength="40">
								</div>
								<div class="col-md-6">
									<label>确认密码</label> 
									<input type="password"
									name="confirm_password" class="form-control" value=""
									placeholder="请再次确认密码..." minlength=6 maxlength="40"
									onblur="checkIsSame()">
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-6">
									<label>详细地址</label> 
									<input type="text" name="address" class="form-control" value="${fUserModel.address}">
								</div>
								<div class="col-md-6">
									<label>邮箱</label> <input type="text" name="email" class="form-control" value="${fUserModel.email}">
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-6 <c:if test='${roleId ne 3 and roleId ne 4 and roleId ne 2 }'>hide</c:if>" id="shareProfitPerDiv">
								<label>分润比例(%)</label> <input type="text" name="shareProfitPer"
									class="form-control" placeholder="请输入分润比例..." maxlength="100" value="${fUserModel.shareProfitPer }">
								</div>
							</div>
							<div class="col-md-12">
								<input type="submit" class="btn btn-md btn-success text-center" value="更新">
							</div>
						</form>
					</div>
				</section>
			</div>
		</div>
      </div>