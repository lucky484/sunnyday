<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


 <!-- Modal start-->
  <div class="modal" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
     <div class="modal-dialog modal-lg" role="document">
       <div class="modal-content">
         <div class="modal-header b-b-l-none">
           <button type="button" class="close" data-dismiss="modal" aria-label="Close">
             <span aria-hidden="true">&times;</span>
           </button>
           <h3><fmt:message key="tiles.aside.menu.device.flux.abnormal.flux.detail"/></h3>
         </div>
         <div class="modal-body">
           <div id="fluxChart" class="deviceChart chart-mod">
							
			</div>
           </div>
           <div class="table-responsive">
           <input type="hidden" value="" id="hidenuserId">
           <input type="hidden" value="" id="hidenSn">
									<div class="col-sm-12">
										<table class="table table-striped b-t b-light" id="appFluxListTable">
											<thead>
												<tr>
												   <th><fmt:message key="tiles.views.customer.appList.index.table.name"/></th>
												   <th><fmt:message key="tiles.aside.menu.device.flux.abnormal.flux.work"/></th>
												   <th><fmt:message key="tiles.aside.menu.device.flux.abnormal.flux.time"/></th>
												</tr>
											</thead>
											<tbody>
											</tbody>
										</table>
									</div>									
								</div>
								
								
           <div class="modal-footer" id="saveBtn">
			 <!--  <a href="javascript:void(0)" onclick="appfluxList()" class="btn btn-success">查看当日流量明细</a> -->
			  <a href="javascript:void(0)" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.fragments.consumer.nav.sendmessage.closebtn"/></a>
          </div>
         </div>
       </div>
     </div>
<!--Modal end-->