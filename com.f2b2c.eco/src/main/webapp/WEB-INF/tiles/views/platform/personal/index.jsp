<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="blog-post">
	<div class="row">
		<div class="col-sm-12">
			<section class="panel panel-default">
				<div class="panel-body">
					<form id="modifyFrm" class="bs-example form-horizontal">
							<div class="row">
								<div class="form-group">
									<label class="col-lg-2 control-label">登录帐号：</label>
									<div class="col-lg-8">
										<input type="text" name="username"
											class="form-control" value="${personInfo.accountName}" disabled>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group">
									<label class="col-lg-2 control-label">用户姓名：</label>
									<div class="col-lg-8">
										<input type="text" name="name"
											class="form-control" value="${personInfo.realName}" disabled>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group">
									<label class="col-lg-2 control-label">手机号码：</label>
									<div class="col-lg-8">
										<input type="text" name="phone" class="form-control"  value="${personInfo.phone}" disabled>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group">
									<label class="col-lg-2 control-label">电子邮箱：</label>
									<div class="col-lg-8">
										<input type="email" name="email" class="form-control m_email" value="${personInfo.email}" disabled>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group">
									<label class="col-lg-2 control-label">用户备注：</label>
									<div class="col-lg-8">
										<input type="text" class="form-control m_mark" name="mark"
											value="${personInfo.remark}" disabled>
									</div>
								</div>
							</div>
					</form>
				</div>
			</section>
		</div>
	</div>
</div>

