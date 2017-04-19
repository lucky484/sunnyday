<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
   
<!-- 新增图文消息 start -->
	<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
     <div class="modal-dialog modal-lg" role="document">
       <div class="modal-content">
         <div class="modal-header b-b-l-none">
           <button type="button" class="close" data-dismiss="modal" aria-label="Close">
             <span aria-hidden="true">&times;</span>
           </button>
           <h4 id="modelTitle"><fmt:message key="tiles.views.institution.message.modal.add.head"/></h4>
         </div>
         <div class="modal-body">
           <div class="row" id="addValidatePart">
              <div class="panel-body">
              	<form id="addFrm" class="bs-example form-horizontal">
              		<div class="form-group">
						<label class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.message.modal.add.title"/><span class="text-danger">*</span></label>
						<div class="col-lg-10">
							 <input type="text" class="form-control" id="msgTitle" name="msgTitle" data-parsley-required="true" 
								 data-parsley-remote data-parsley-remote-validator="existsValidate" 
								 data-parsley-trigger="blur" data-parsley-remote-message="<fmt:message key="tiles.views.institution.message.modal.add.parsley"/>">
						</div>
					</div>
					<div style="margin-top:20px;"></div>
					<div class="form-group">
						<label class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.message.modal.add.content"/><span class="text-danger">*</span></label>
						<div class="col-lg-10">
							<textarea id="editor"></textarea>
							<ul class="parsley-errors-list filled m-b-md m-t-n-md hidden" id="showWarningMsg">
								<li class="parsley-required"><fmt:message key="tiles.institution.picmessage.manager.content.null"/></li>
							</ul>
						</div>
					</div>
					<div class="form-group m-t">
                          <div class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.department"/></div>
                        	  <div class="col-lg-10">
                          <div class="treeHeight" style="min-height: 100px;max-height: 200px;overflow: scroll;">
                             <ul id="tree"></ul>
                          </div>
                          </div>
	                </div>
	                <div class="form-group virtualAuth">
                       <label class="col-lg-2 control-label"></label>
                       <div class="col-lg-10">
                          <div class="col-lg-12 paddingZero">
	                          <table class="policyVirtPadding">
		                           <tr height="30px;">
		                             <td width="50%" class="borderNoLine"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.virtual"/></td>
		                             <td class="borderNoLine" class="borderNoLine">
			                             <div class="col-lg-12 paddingZero">
			                                <div class="col-lg-11 paddingZero">
			                                   <input id="policyName" type="text" class="input-sm form-control" placeholder="<fmt:message key="tiles.views.institution.devicepolicy.indexmodal.user"/>" style="margin-left:2px;"/>
			                                </div>
				                             <div class="col-lg-1 paddingZero">
				                                <i class="fa fa-search searchIcon"></i>
				                             </div>
			                             </div>
		                             </td>
		                           </tr>
		                           <tr class="department">
		                             <td class="borderNoLine">
			                            <ul class="policyVirtual">
			                                <c:forEach items="${virtualList}" var="virtual">
		                                        <li class="virtualLi"><input id="virtu${virtual.id}" type="checkbox" name="virtualIds" value="${virtual.id}"/>&nbsp;&nbsp;${virtual.name}(${virtual.collectionName})</li>
		                                     </c:forEach>
			                            </ul>
		                             </td>
		                             <td class="borderNoLine">
			                           <ul class="policyUserRight" id="policyVirtualRight"> 
		                              </ul>
		                             </td>
		                           </tr>
	                          </table>
                          </div>
                         </div>
	                 </div>
              	</form>
              </div>
            </div>
            <div class="modal-footer" id="saveBtn">
		     	<a href="javascript:void(0);" onclick="saveMessage();" class="btn btn-success"><fmt:message key="tiles.views.institution.message.modal.add.button.save"/></a>
		     	<a href="javascript:void(0);" class="btn btn-success" onclick="cancelSaveMessage();" data-dismiss="modal"><fmt:message key="tiles.views.institution.message.modal.add.button.cancel"/></a>
	      	</div>
           </div>
		</div>
	</div>
</div>
<!-- 修改图文消息 -->
	<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
     <div class="modal-dialog modal-lg" role="document">
       <div class="modal-content">
         <div class="modal-header b-b-l-none">
          <input type="hidden" value="" id="msgId">
           <button type="button" class="close" data-dismiss="modal" aria-label="Close">
             <span aria-hidden="true">&times;</span>
           </button>
           <h5 id="modelTitle"><fmt:message key="tiles.views.institution.message.modal.update.head"/></h5>
         </div>
         <div class="modal-body">
           <div class="row" id="editValidatePart">
              <div class="panel-body">
              	<form id="updateFrm" class="bs-example form-horizontal">
              		<div class="form-group">
						<label class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.message.modal.update.title"/><span class="text-danger">*</span></label>
						<div class="col-lg-10">
							 <input type="text" class="form-control" id="editMsgTitle" name = "msgTitle" data-parsley-required="true" data-parsley-trigger="blur">
						</div>
					</div>
					<div style="margin-top:20px;"></div>
					<div class="form-group">
						<label class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.message.modal.update.content"/><span class="text-danger">*</span></label>
						<div class="col-lg-10">
							<textarea id="editEditor"></textarea>
							<ul class="parsley-errors-list filled m-b-md m-t-n-md hidden" id="modifyWarningMsg">
								<li class="parsley-required"><fmt:message key="tiles.institution.picmessage.manager.content.null"/></li>
							</ul>
						</div>
					</div>
					<div class="form-group">
                          <div class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.department"/></div>
                        	  <div class="col-lg-10">
                          <div class="treeHeight">
                             <ul id="eidtTree" style="padding:0"></ul>
                          </div>
                          </div>
	                </div>
	                <div class="form-group virtualAuth">
                       <label class="col-lg-2 control-label"></label>
                       <div class="col-lg-10">
                          <div class="col-lg-12 paddingZero">
	                          <table class="policyVirtPadding">
		                           <tr height="30px;">
		                             <td width="50%" class="borderNoLine"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.virtual"/></td>
		                             <td class="borderNoLine" class="borderNoLine">
			                             <div class="col-lg-12 paddingZero">
			                                <div class="col-lg-11 paddingZero">
			                                   <input id="editPolicyName" type="text" class="input-sm form-control" placeholder="<fmt:message key="tiles.views.institution.devicepolicy.indexmodal.user"/>" style="margin-left:2px;"/>
			                                </div>
				                             <div class="col-lg-1 paddingZero">
				                                <i class="fa fa-search searchIcon"></i>
				                             </div>
			                             </div>
		                             </td>
		                           </tr>
		                           <tr class="department">
		                             <td class="borderNoLine">
			                            <ul class="policyVirtual">
			                                <c:forEach items="${virtualList}" var="virtual">
		                                        <li class="virtualLi"><input id="eidtVirtu${virtual.id}" type="checkbox" name="editVirtualIds" value="${virtual.id}"/>&nbsp;&nbsp;${virtual.name}(${virtual.collectionName})</li>
		                                     </c:forEach>
			                            </ul>
		                             </td>
		                             <td class="borderNoLine">
			                           <ul class="policyUserRight" id="eidtPolicyVirtualRight"> 
		                              </ul>
		                             </td>
		                           </tr>
	                          </table>
                          </div>
                         </div>
	                 </div>
              	</form>
              </div>
            </div>
            <div class="modal-footer" id="saveBtn">
		     	<a href="javascript:void(0);" onclick="editMessage();" class="btn btn-success"><fmt:message key="tiles.views.institution.message.modal.update.save"/></a>
		     	<a href="javascript:void(0);" class="btn btn-success" data-dismiss="modal"><fmt:message key="tiles.views.institution.message.modal.update.cancel"/></a>
	      	</div>
           </div>
		</div>
	</div>
</div>
<!-- 图文消息显示modal -->
<div class="modal fade" id="showModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
     <div class="modal-dialog modal-lg" role="document">
       <div class="modal-content">
		<input type="hidden" id="netUrlStr" />
         <div class="modal-header b-b-l-none">
           <button type="button" class="close" data-dismiss="modal" aria-label="Close">
             <span aria-hidden="true">&times;</span>
           </button>
           <h5 id="modelTitle"><fmt:message key="tiles.views.institution.message.modal.view.head"/></h5>
         </div>
         <div class="modal-body">
           <div class="row" id="showValidatePart">
              <div class="panel-body">
                <div class="form-group">
					<label class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.message.modal.view.title"/><span class="text-danger">*</span></label>
					<div class="col-lg-10">
						 <input type="text" class="form-control" id="showMsgTitle" disabled>
					</div>
				</div>
				<div style="margin-top:50px;"></div>
				<div class="form-group">
					<label class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.message.modal.view.content"/><span class="text-danger">*</span></label>
					<div class="col-lg-10">
						<textarea id="showEditor" disabled="disabled" readonly="readonly"></textarea>
					</div>
				</div>
				<div class="form-group">
                          <div class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.department"/></div>
                        	  <div class="col-lg-10">
                          <div class="treeHeight">
                             <ul id="viewTree" style="padding:0"></ul>
                          </div>
                          </div>
	                </div>
	                <div class="form-group virtualAuth">
                       <label class="col-lg-2 control-label"></label>
                       <div class="col-lg-10">
                          <div class="col-lg-12 paddingZero">
	                          <table class="policyVirtPadding">
		                           <tr height="30px;">
		                             <td width="50%" class="borderNoLine"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.virtual"/></td>
		                             <td class="borderNoLine" class="borderNoLine">
			                             <div class="col-lg-12 paddingZero">
			                                <div class="col-lg-11 paddingZero">
			                                   <input id="policyName" type="text" class="input-sm form-control" placeholder="<fmt:message key="tiles.views.institution.devicepolicy.indexmodal.user"/>" style="margin-left:2px;"/>
			                                </div>
				                             <div class="col-lg-1 paddingZero">
				                                <i class="fa fa-search searchIcon"></i>
				                             </div>
			                             </div>
		                             </td>
		                           </tr>
		                           <tr class="department">
		                             <td class="borderNoLine">
			                            <ul class="policyVirtual">
			                                <c:forEach items="${virtualList}" var="virtual">
		                                        <li class="virtualLi"><input id="viewVirtu${virtual.id}" type="checkbox" name="virtualIds" value="${virtual.id}"/>&nbsp;&nbsp;${virtual.name}(${virtual.collectionName})</li>
		                                     </c:forEach>
			                            </ul>
		                             </td>
		                             <td class="borderNoLine">
			                           <ul class="policyUserRight" id="viewPolicyVirtualRight"> 
		                              </ul>
		                             </td>
		                           </tr>
	                          </table>
                          </div>
                         </div>
	                 </div>
              </div>
            </div>
            <div class="modal-footer" id="saveBtn">
		     	<a href="javascript:void(0);" class="btn btn-success" data-dismiss="modal"><fmt:message key="tiles.views.institution.message.modal.view.confirm"/></a>
	      	</div>
           </div>
		</div>
	</div>
</div>

<!-- <div class="modal fade" id="sendTitle" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">提示</h4>
			</div>
			<div class="modal-body">
			    <h3 class="text-danger">图文消息标题为空</h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default" id="successTitle">确定</button>
		      </div> 
		</div>
	</div>
</div>-->

<div class="modal fade" id="sendContent" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key='tiles.institution.comment.msg'/></h4>
			</div>
			<div class="modal-body">
			    <h3 class="text-danger"><fmt:message key='tiles.institution.picmessage.manager.content.null'/></h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default" id="successContent"><fmt:message key='tip.del.confirm'/></button>
		      </div> 
		</div>
	</div>
</div>

<!--<div class="modal fade" id="succModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">提示</h4>
			</div>
			<div class="modal-body">
			    <h3 class="text-danger">新建图文消息成功</h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default" id="btnSuccess">确定</button>
		      </div> 
		</div>
	</div>
</div>

<div class="modal fade" id="succEditModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">提示</h4>
			</div>
			<div class="modal-body">
			    <h3 class="text-danger">编辑图文消息成功</h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default" id="btnEditSuccess">确定</button>
		      </div> 
		</div>
	</div>
</div>

<div class="modal fade" id="succDelModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">提示</h4>
			</div>
			<div class="modal-body">
			    <h3 class="text-danger">删除图文消息成功</h3>
			</div>
			 <div class="modal-footer">
		        <button type="button" class="btn btn-default" id="btnDelSuccess">确定</button>
		      </div> 
		</div>
	</div>
</div> -->

<div class="modal fade" id="delPicAndTxt" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog" role="document">
		<input type="hidden" value="" id="msgId">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">
						<fmt:message key="tiles.views.institution.message.modal.delete.tip"/>
					</h4>
				</div>
				<div class="modal-body">
					<h3 class="text-danger del-text" id="picAndTxtMessage"><fmt:message key="tiles.views.institution.message.modal.delete.message"/></h3>
				</div>
				<div class="modal-footer">
					<a href="javascript:void(0);" onclick="deleteMessage();" class="btn btn-success"><fmt:message key="tiles.views.institution.message.modal.delete.confirm"/></a>
		     		<a href="javascript:void(0);" class="btn btn-success" data-dismiss="modal"><fmt:message key="tiles.views.institution.message.modal.delete.cancel"/></a>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
 <!-- Modal start-->
  <div class="modal" id="memberModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
     <div class="modal-dialog modal-lg" role="document">
       <div class="modal-content">
         <div class="modal-header b-b-l-none">
           <button type="button" class="close" data-dismiss="modal" aria-label="Close">
             <span aria-hidden="true">&times;</span>
           </button>
           
           <h5><fmt:message key="tiles.views.institution.message.modal.userlist"/></h5>
         </div>
           <div class="table-responsive">
           <input type="hidden" value="" id="hidenuserId">
           <input type="hidden" value="" id="hidenSn">
									<div class="col-sm-12">
										<table class="table table-striped b-t b-light" id="viewMemberTable">
											<thead>
												<tr>
												   <th><fmt:message key="web.institution.exportandimportexcelcontroller.exceltip2"/></th>
												   <th><fmt:message key="web.institution.exportandimportexcelcontroller.exceltip1"/></th>
												</tr>
											</thead>
											<tbody>
											</tbody>
										</table>
									</div>									
								</div>
								
								
           <div class="modal-footer" id="saveBtn">
			  <a href="javascript:void(0)" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.fragments.consumer.nav.sendmessage.closebtn"/></a>
          </div>
         </div>
       </div>
     </div>