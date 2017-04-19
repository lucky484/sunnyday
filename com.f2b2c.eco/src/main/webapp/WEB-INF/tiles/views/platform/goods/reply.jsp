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
						<form role="form" method="post" id="replyForm" class="form-horizontal m-t">
							<input type="hidden" value="${goodId}" name="id">
							<div class="form-group">
								<div class="col-md-2 control-label">
									<label>审核不通过原因：<span class="text-danger">*</span></label> 
								</div> 
								<div class="col-md-10">
									<textarea class="textarea" id="reason" name="reason" style="width:99%; height:60px; resize:none"></textarea>
								</div>
							</div>
							<footer class="row">
								<div class="col-sm-4 col-sm-offset-2 col-md-4">
									<input type="button" class="btn btn-s-md btn-primary " value="确定" onclick="confirm();"/>
								</div>
							</footer>
						</form>
					</div>
				</section>
			</div>
		</div>
     </div>