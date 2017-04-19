<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!-- Modal start-->
<div class="modal fade" id="infoModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header b-b-l-none">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body" style="max-height: 500px; overflow: scroll;">

			</div>
		</div>
	</div>
</div>
<!--Modal end-->
<!-- Modal start-->
<div class="modal fade" id="managerModal" tabindex="-1" role="dialog"
	data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<span class="font-bold"><fmt:message key="web.institution.dpt.modal.modify.title.label"/></span>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<section class="panel panel-default">
				<form id="uRDFrm">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<header class="panel-heading font-bold"><span class="text-warning pull-right"><fmt:message key="web.institution.dpt.modal.modify.title.tags1.label"/><span class="text-danger">*</span><fmt:message key="web.institution.dpt.modal.modify.title.tags2.label"/></span></header>
				<%-- <form> --%>
				<input type="hidden" value="" name="user.id" class="promote-uid">
				<input type="hidden" value="" name="departmentIds" class="department_ids"><%-- </form> --%>
				<div class="form-group">
											<label class="col-lg-2 control-label login_account"><fmt:message key="web.institution.dpt.modal.modify.username.label"/></label>
				<div class="col-lg-10">
												<label class="control-label" id="username"></label>
											</div> <div >
										</div>
				<div class="form-group">
											<label class="col-lg-2 control-label login_name"><fmt:message key="web.institution.dpt.modal.modify.realname.label"/></label>
					<div class="col-lg-10">
						<label class="control-label" id="realname"></label>
						</div>
				</div>
				<div class="panel-body">
				   <div class="form-group">
				    <label class="col-sm-2 control-label"><fmt:message key="web.institution.dpt.modal.modify.role.label"/><span class="text-danger">*</span></label>
			      	<div class="col-sm-10 ck-tmpl" style="max-height:200px;overflow-y: scroll;" id="promoteTreeRole">
	      			</div>
	      			 <div id="promote-tree-error-role" class="hidden col-sm-offset-2">
						 <ul class="parsley-errors-list filled"><li><fmt:message key="web.institution.dpt.modal.modify.title.tags3.label"/></li></ul>
						</div>
				    </div>
				    <div class="line line-dashed b-b line-lg pull-in"></div>
				     <div class="form-group">
				      <label class="col-sm-2 control-label"><fmt:message key="web.institution.dpt.modal.modify.departselected.label"/><span class="text-danger">*</span></label>
				      <div id="promoteTree" class="col-sm-10" style="max-height:200px;overflow-y: scroll;">
		      		  </div>
		      		   <div id="promote-tree-error" class="hidden col-sm-offset-2">
						 <ul class="parsley-errors-list filled"><li><fmt:message key="web.institution.dpt.modal.modify.title.tags3.label"/></li></ul>
						</div>
				     </div>
				     <div class="line line-dashed b-b line-lg pull-in"></div>
				     <div class="form-group">
				      <label class="col-sm-2 control-label"><fmt:message key="web.institution.dpt.modal.modify.mark.label"/></label>
				      	<div class="col-sm-10 comments">
		                   <textarea name="mark" id="mark"  data-parsley-maxlength="100" data-parsley-trigger="blur"  rows="5" cols="20" class="form-control" style="resize:none;" value="12321321312aaa"></textarea>
		      			</div>
		      			</div>
		      			 <div class="form-group">
				      <label class="col-sm-2 control-label"><fmt:message key="web.institution.dpt.modal.modify.author.label"/></label>
				      	<div class="col-sm-10 comments">
								<div class="radio i-checks">
								<label>
			                            <input type="radio" name="auth" value="0" >
			                            <i></i>
			                            <fmt:message key="tiles.views.user.index.modal.manager.auth.readonly.title"/>
			                          </label>
								</div>
								<div class="radio i-checks">
								<label>
			                            <input type="radio" name="auth" value="1" checked>
			                            <i></i>
			                            <fmt:message key="tiles.views.user.index.modal.manager.auth.all.title"/>
			                          </label>
								</div>
									
			                          
								</div>
		      			</div>
		      			<input type="button" onclick="subPromote()" class="btn btn-md btn-primary m-t pull-right"value="<fmt:message key='web.institution.dpt.modify.label'/>" id="modiftbtn"> 
				</div>
				</form>
				</section>
				
			</div>
		</div>
	</div>
</div>
<!--Modal end-->
<script id="userInfo" type="text/x-jquery-tmpl">
					<div class="row">
						<div class="col-sm-12">
							<section class="panel panel-default">
								<header class="panel-heading font-bold"><fmt:message key="web.institution.dpt.modal.user.userinfomation.label"/></header>
								<div class="panel-body">
									<div class="bs-example form-horizontal">
										<h5>
											<span class="badge bg-primary"><fmt:message key="web.institution.dpt.modal.user.userinfomation1.label"/></span>
										</h5>
										<div class="form-group">
											<label class="col-lg-3 control-label font-bold"><fmt:message key="web.institution.dpt.modal.user.userinfomation2.label"/></label>
											<div class="col-lg-9">
												<label class="control-label">{{= username}}</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label  font-bold"><fmt:message key="web.institution.dpt.modal.user.userinfomation3.label"/></label>
											<div class="col-lg-9">
												<label class="control-label">{{= realname}}</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label font-bold"><fmt:message key="web.institution.dpt.modal.user.userinfomation4.label"/></label>
											<div class="col-lg-9">
												<label class="control-label">{{= department}}</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label  font-bold"><fmt:message key="web.institution.dpt.modal.user.userinfomation5.label"/></label>
											<div class="col-lg-9">
												<label class="control-label">{{= phone}}</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label  font-bold">email</label>
											<div class="col-lg-9">
												<label class="control-label">{{= email}}</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label  font-bold"><fmt:message key="web.institution.dpt.modal.user.userinfomation6.label"/></label>
											<div class="col-lg-9">
												<label class="control-label">{{= createtime}}</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label font-bold"><fmt:message key="web.institution.dpt.modal.user.userinfomation7.label"/></label>
											<div class="col-lg-9">
												<label class="control-label">{{= mark}}</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label  font-bold"><fmt:message key="web.institution.dpt.modal.user.userinfomation8.label"/></label>
											<div class="col-lg-9">
												<label class="control-label"><fmt:message key="web.institution.dpt.modal.user.userinfomation9.label"/></label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label"><fmt:message key="web.institution.dpt.modal.user.userinfomation10.label"/></label>
											<div class="col-lg-9">
												<label class="control-label">{{= vtl}}</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label  font-bold"><fmt:message key="web.institution.dpt.modal.user.userinfomation11.label"/></label>
											<div class="col-lg-9">
												<label class="control-label">{{= weight}}</label>
											</div>
										</div>
										<hr>
										<h5>
											<span class="badge bg-primary"><fmt:message key="web.institution.dpt.modal.user.userinfomation12.label"/></span>
										</h5>

										<div class="form-group">
											<label class="col-lg-3 control-label  font-bold"><fmt:message key="web.institution.dpt.modal.user.userinfomation13.label"/></label>
											<div class="col-lg-9">
												<label class="control-label">{{if gender==1}}<fmt:message key="web.institution.dpt.modal.user.userinfomation14.label"/>{{else}}<fmt:message key="web.institution.dpt.modal.user.userinfomation15.label"/>{{/if}}</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label font-bold"><fmt:message key="web.institution.dpt.modal.user.userinfomation16.label"/></label>
											<div class="col-lg-9">
												<label class="control-label">{{= sign}}</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label font-bold"><fmt:message key="web.institution.dpt.modal.user.userinfomation17.label"/></label>
											<div class="col-lg-9">
												<label class="control-label">{{= address}}</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label font-bold"><fmt:message key="web.institution.dpt.modal.user.userinfomation18.label"/></label>
											<div class="col-lg-9">
												<label class="control-label">{{= office}}</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label font-bold"><fmt:message key="web.institution.dpt.modal.user.userinfomation19.label"/></label>
											<div class="col-lg-9">
												<label class="control-label">{{= position}}</label>
											</div>
										</div>
									</div>
								</div>
							</section>
						</div>
					</div>
</script>
<!-- Modal start-->
<div class="modal fade" id="delModalTree" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
					<fmt:message key="tip.del.title" />
				</h4>
			</div>
			<div class="modal-body">
				<h3 class="text-danger"><fmt:message key="web.institution.dpt.modal.user.delete.label"/></h3>
				<input type ="hidden" id ="deluid" value="">
				<input type ="hidden" id ="delurdid" value="">
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<fmt:message key="tip.del.cancel" />
				</button>
				<button type="button" class="btn btn-danger" id="delcol" onclick="delemanager()">
					<fmt:message key="tip.del.confirm" />
				</button>
			</div>
		</div>
	</div>
</div>
<!--Modal end-->

<!-- Jquery Tmplates  start-->
<script id="checkboxTmpl" type="text/x-jquery-tmpl">
	{{each(i,e) data}}
		 <div class="radio i-checks">
		 	<label>
		 	<input type="radio" name="role.id" value="{{= e.id}}">
		 	<i></i>
		 	{{= e.name}}
		 	</label>
		 </div> 
	{{/each}}
</script>
