<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Modal start-->
<div class="modal fade" id="delModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
					<fmt:message key="tiles.views.institution.device.rule.delete.tip.header" />
				</h4>
			</div>
			<div class="modal-body">
				<input id="del-rule-id" type="hidden" value="">
				<h3 class="text-danger"><fmt:message key="tiles.views.institution.device.rule.deleteall.tip.content"/></h3>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<fmt:message key="tiles.views.institution.device.rule.delete.tip.cancel" />
				</button>
				<input type="button" class="btn btn-danger" onclick="delIllegeRecords()"
					data-dismiss="modal" value="<fmt:message key='tiles.views.institution.device.rule.delete.tip.confirm'/>">
			</div>
		</div>
	</div>
</div>
<!--Modal end-->

