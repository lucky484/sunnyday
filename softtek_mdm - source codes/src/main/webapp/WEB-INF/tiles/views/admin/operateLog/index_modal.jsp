<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	<div class="modal fade" id="userOperateLogDetail" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.log.operate.log.session.detail" /></h4>
			</div>
			<div class="modal-body">
			   <table class="table table-striped b-t b-light" id="userOperateDetail">
                              <thead>
                                <tr>
                                  <th><fmt:message key="tiles.institution.log.device.log.operate.time" /></th>
                                  <th><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.operate" /></th>
                                  <th><fmt:message key="tiles.views.netbehaviormanager.logmanager.content" /></th>
                                </tr>
                              </thead>
                              <tbody>
                              </tbody>
                            </table>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.cancle" /></button>
		      </div> 
		</div>
	</div>
</div>