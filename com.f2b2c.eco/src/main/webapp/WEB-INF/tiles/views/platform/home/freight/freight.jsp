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
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>
						运费设置
					</h5>
				</div>

				<div class="ibox-content">
					<form id="freightForm" class="form-horizontal m-t">
						<div class="form-group">
							<div class="col-md-2 control-label">
								<label>消费满多少免运费：<span class="text-danger">*</span></label> 
							</div> 
							<div class="col-md-10">
								<input type="text" name=freemoney class="form-control" id="freemoney"
									placeholder="请输入金额" required maxlength="12" value="${fFreightModel.freemoney }">
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-2 control-label">
								<label>标准费用：<span class="text-danger">*</span></label> 
							</div> 
							<div class="col-md-10">
								<input type="text" name="money" class="form-control" id="money"
									placeholder="请输入标准费用" required maxlength="12" value="${fFreightModel.money }">
							</div>
						</div>
						<footer class="row">
							<div class="col-sm-4 col-sm-offset-2 col-md-4">
								 <a class="btn btn-primary radius" id="publish"
								data-title="提交" onclick="submit();"> 提交
								</a>
							</div>
						</footer>
						<input id="id" type="hidden" value="${fFreightModel.id }">
					</form>
				</div>
			</div>
		</div>
	</div>
</div>