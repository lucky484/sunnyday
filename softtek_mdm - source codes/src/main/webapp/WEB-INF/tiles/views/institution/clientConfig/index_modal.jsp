<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="modal fade" id="insertMsg" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.fragments.consumer.nav.sendmessage.success.tip" /></h4>
			</div>
			<div class="modal-body">
				<h3 class="text-danger"><fmt:message key="tiles.institution.client.config.setting.success" /></h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.del.cancel"/></button>
		        <button type="button" class="btn btn-danger" id="confirm"><fmt:message key="tiles.institution.org.department.confirm"/></button>
		      </div>
		</div>
	</div>
</div>