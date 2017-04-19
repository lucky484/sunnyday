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
					<div class="panel-body">
						<form class="form-horizontal m-t">
							<div class="form-group">
								<div class="col-md-6">
									<label>账户名称(*)</label> <input type="text" name="accountName" class="form-control" value="${fUserModel.accountName}" disabled/>
								</div>
								<div class="col-md-6">
									<label>真实姓名(*)</label> <input type="text" name="realName" class="form-control" value="${fUserModel.realName}" disabled/>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-6">
									<label>昵称</label> <input type="text" name="nickName" class="form-control" value="${fUserModel.nickName}" disabled/>
								</div>
								<div class="col-md-6">
									<label>手机号码(*)</label> <input type="text" name="phone" class="form-control" value="${fUserModel.phone}" disabled/>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-6">
									<label>用户角色</label> 
									<input type="text" name="roleName" class="form-control" value="${roleName}" disabled>
								</div>
								<div class="col-md-6 <c:if test='${roleId ne 2 and roleId ne 3 and roleId ne 4 and roleId ne 5 }'>hide</c:if>">
									<label>省份</label>
									<input type="text" class="form-control" value="${selectedProvinceName }" disabled/>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-6 <c:if test='${roleId ne 3 and roleId ne 4 and roleId ne 5 }'>hide</c:if>">
									<label>城市</label>
									<input type="text" class="form-control" value="${selectedCityName }" disabled/>
								</div>
								<div class="col-md-6 <c:if test='${roleId ne 4 and roleId ne 5 }'>hide</c:if>">
									<label>辖区</label>
									<input type="text" class="form-control" value="${selectAreadName }" disabled/>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-6">
									<label>邮箱</label> <input type="text" name="email" class="form-control" value="${fUserModel.email}" disabled>
								</div>
								<div class="col-md-6">
									<label>详细地址</label> 
									<input type="text" name="address" class="form-control" value="${fUserModel.address}" disabled>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-6 <c:if test='${roleId ne 3 and roleId ne 4 and roleId ne 2 }'>hide</c:if>">
									<label>分润比例(%)</label> <input type="text" name="shareProfitPer" class="form-control" value="${fUserModel.shareProfitPer}" disabled>
								</div>
							</div>
							</form>
					</div>
				</section>
			</div>
		</div>
      </div>