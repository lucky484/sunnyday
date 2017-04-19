 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <!-- 删除提示功能 -->
    <!-- Modal start-->
	<div class="modal fade" id="delNameListModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog" role="document">
		<input type="hidden" id="delId" />
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
					<h4 class="text-danger del-text" id="NameListMessage"><fmt:message key="tiles.views.institution.namelist.indexmodasl.delmessage"/></h4>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<fmt:message key="tip.del.cancel" />
					</button>
					<input type="button" id="nameListClick" class="btn btn-danger btn-delete-users"	data-dismiss="modal" onclick="delNameList()" value="<fmt:message key='tip.del.confirm'/>">
				</div>
			</div>
		</div>
	</div>
	<!--Modal end-->
	
	<!-- 新增或编辑黑白名单 start -->
	<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
     <div class="modal-dialog modal-lg" role="document">
       <div class="modal-content">
       <input type="hidden" id="appstr" />
       <input type="hidden" id="appIds" />
       <input type="hidden" id="id" />
       <input type="hidden" name="token" id="tokenId"/>
         <div class="modal-header b-b-l-none">
           <button type="button" class="close" data-dismiss="modal" aria-label="Close">
             <span aria-hidden="true">&times;</span>
           </button>
           <h4 id="nameTitle"><fmt:message key="tiles.views.institution.namelist.indexmodal.titlename"/></h4>
         </div>
         <div class="modal-body" style="width:900px;">
           <div class="row">
              <div class="panel-body">
                  <form id="saveNameListForm" data-parsley-validate>
                    <div class="form-group">
                    <div class="col-lg-12">
                     <label class="col-lg-2 paddingWidth control-label"><fmt:message key="tiles.views.institution.namelist.indexmodal.type"/></label>
	                  <div class="col-lg-8 paddingWidth">
	                    <select id="nameType" class="form-control m-b">
	                      <option value="1"><fmt:message key="tiles.views.institution.namelist.indexmodal.blacknamelist"/></option>
	                      <option value="0"><fmt:message key="tiles.views.institution.namelist.indexmodal.whitenamelist"/></option>
	                    </select>
	                  </div>
                    </div>
                   </div>
                   <div class="form-group">
                   <div class="col-lg-12">
                     <div class="col-lg-2 paddingWidth control-label"><fmt:message key="tiles.views.institution.namelist.indexmodal.name"/><span class="text-danger">*</span></div>
                     <div class="col-lg-8 paddingWidth">
                       <input type="text" id="listName" class="form-control m-b" onblur="exists()" class="form-control m-b"  data-parsley-required="true"  data-parsley-maxlength="30" 
											 data-parsley-remote data-parsley-remote-validator="existsValidate" 
											 data-parsley-trigger="blur"  
											 autofocus="autofocus"  
											 data-parsley-remote-message="<fmt:message key="parsley.account.exists"/>"/>
                       <span class="help-block m-b-none"></span>
                     </div>
                    </div>
                   </div>
                   </form>
                   <div class="form-group">
                   <div class="col-lg-12">
	                  <div class="col-lg-2 paddingWidth"><fmt:message key="tiles.views.institution.namelist.indexmodal.description"/>&nbsp;</div>
	                   <div class="col-lg-8 paddingWidth">
	                      <textarea class="form-control m-b" id="remark"></textarea>
	                   </div>
	               </div>
	               </div>
	               <form id="saveAppListForm" data-parsley-validate>
                      <div class="form-group app_list" id="addAppAndroid"></div>
	               </form>
	               <div class="form-group app_table">
	                   <div class="col-lg-12">
	                     <div class="col-lg-2"><fmt:message key="tiles.views.institution.namelist.indexmodal.applist"/></div>
	                     <div class="col-lg-8 paddingWidth">
	                       <div class="col-lg-12 appTitle">
	                         <div class="col-lg-4 appIdTitle"><fmt:message key="tiles.views.institution.namelist.indexmodal.appid"/></div>
	                         <div class="col-lg-4 appIdTitle"><fmt:message key="tiles.views.institution.namelist.indexmodal.appname"/></div>
	                         <div class="col-lg-3"><fmt:message key="tiles.views.institution.namelist.indexmodal.apptype"/></div>
	                         <div class="col-lg-1">&nbsp;</div>
	                       </div>
	                       <div class="col-lg-12 paddingWidth appHeight" id="appDiv">
	                       </div>
	                   </div>
	                  </div> 
                  </div>
           
                 </div>
              </div>
              <div class="modal-footer" id="saveBtn">
			     
		      </div>
		      
		     </div>
           </div>
		</div>
	</div>
    <!-- 新增或编辑黑白名单 end -->
    <!-- 选择应用 start -->
    <div class="modal fade" id="chooseAppList" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
	    <div class="modal-dialog modal-lg" role="document">
	      <div class="modal-content">
	        <div class="modal-header b-b-l-none">
	          <button type="button" class="close" data-dismiss="modal" onclick="closeApp()">
	            <span aria-hidden="true">&times;</span>
	          </button><h4><fmt:message key="tiles.views.institution.application.indexscript.chooseapps"/></h4>
	        </div>
	        <div class="modal-body" style="min-height: 300px;overflow: scroll;">
	          <div class="row" id="insert">
	            <div class="col-sm-12">
	              <section class="panel panel-default">
	                <div class="panel-body">
	                    <div class="table-responsive">
	                       <table class="table table-striped b-t b-light" id="loadAppList" style="width:100%">
	                         <thead>
	                           <tr>
	                             <th></th>
	                             <th><fmt:message key="tiles.views.institution.namelist.indexmodal.appid"/></th>
	                             <th><fmt:message key="tiles.views.institution.namelist.indexmodal.appname"/></th>
	                           </tr>
	                         </thead>
	                         <tbody>
	                         </tbody>
	                       </table>
	                     </div> 
	                 </div>
	               </section>
	              </div>
	            </div>
	          </div>
	          <div class="modal-footer">
	          	 <button type="button" class="btn btn-success" data-dismiss="modal" onclick="confirmChooseApp()">
					<fmt:message key="general.jsp.select.label" />
				</button>
				<a href="javascript:closeApp()" class="btn"><fmt:message key="general.jsp.close.label"/></a>
             </div>
	        </div>
	   </div>
    </div>
    
    <!-- 加载已分配设备 end  -->
    <div class="modal fade" id="warningModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
				    <fmt:message key="tiles.views.user.index.modal.warning.title"/>
				</h4>
			</div>
			<div class="modal-body">
				<h3 class="text-warning"><fmt:message key="tiles.views.user.index.modal.warning.content"/></h3>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal" onclick="">
					<fmt:message key="tiles.views.institution.device.rule.delete.tip.cancel" />
				</button>
				<button type="button" class="btn btn-warning" data-dismiss="modal" onclick="">
					<fmt:message key="tiles.views.institution.device.rule.delete.tip.confirm" />
				</button>
			</div>
		</div>
	</div>
</div>
<!-- 加载已分配设备 end  --> 

<!-- Modal start-->
<div class="modal fade" id="excelModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header b-b-l-none">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-sm-12">
						<section class="panel panel-default">
							<header class="panel-heading font-bold">
								<fmt:message key="tiles.views.name.list.index.table.excel.import" />
							</header>
							<div class="panel-body">
								<spring:url value="/institution/vtl/save" var="modal_saveUrl" />
								<spring:url value="/resources/images/virtualMemberexcelmode.png"
									var="virtualMemberexcelmode" />

								<label class="col-lg-12"><fmt:message
										key="tiles.views.name.list.index.table.excel.tip" /> </label>
								<spring:url value="/institution/nameList/getAppExcelTemplate" var="namelistTemplate" />
								<form action="${namelistTemplate}" method="get" id="getusermodel">
									<div class="col-sm-12">
										<div class="col-sm-5 exportvirtual">
											<a href="javascript:void(0)" onclick="exportUserModel();"
												class="clear text-ellipsis"><strong class="block">
													<i class="fa fa-download fa-2x"></i> <fmt:message
														key="tiles.views.user.index.table.excel.download" />
											</strong> </a>
										</div>
									</div>
								</form>
								<spring:url value="/institution/nameList/importApps" var="importApps" />
								<form action="${importApps}" method="post" id="importApps"
									enctype="multipart/form-data">
									<div class="col-sm-12 uploadvirmodel">
										<div class="col-sm-4">
											<a href="javascript:void();" class="a-upload"> <input
												type="file" name="file" id="file">
											<fmt:message key="tiles.views.namelist.index.table.excel.upload" />
											</a> <input type="hidden" name="${_csrf.parameterName}"
												value="${_csrf.token}" />
										</div>
										<div class="col-sm-8">
											<input type="text" class="showFileName" disabled value="">
										</div>
									</div>
								</form>
								<!--   导出用户表单临时存放 -->
								<spring:url value="/institution/user/exportuser"
									var="exportUserUrl" />
								<form action="${exportUserUrl}" method="get" id="exportuserFrm">
									<input type="hidden" value="" id="groupid" name="groupid">
								</form>
								<div class="col-sm-12">
									<a class="fileerrorTip" style="color: red"></a>
								</div>
								<div class="col-lg-offset-2 col-lg-10"></div>
							</div>
						</section>
						<div align="center">
							<input type="button" class="btn btn-md btn-primary"
								onclick="importApps()"
								value="<fmt:message key='jsp.import.title'/>">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- Modal start-->
<div class="modal fade" id="successModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close" onclick="refresh()">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
					<fmt:message key="tiles.views.user.index.modal.success.title" />
				</h4>
			</div>

			<div class="col-sm-12">
				<h1 class="text-danger"></h1>
				<div id="rownumbers"
					style="width: 500px; height: 100px; overflow: auto; border: 2px solid #E5E5E5;"></div>
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-danger" data-dismiss="modal"
					onclick="refresh()">
					<fmt:message
						key="tiles.views.institution.device.rule.delete.tip.confirm" />
				</button>
			</div>
		</div>
	</div>
</div>
<!--Modal end-->

<!-- Modal start-->
<div class="modal fade" id="erroModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
					<fmt:message key="tiles.views.user.index.modal.success.title" />
				</h4>
			</div>

			<div class="col-sm-12">
				<h1 class="text-danger"></h1>
				<div id="rownumbers2"
					style="width: 500px; height: 100px; overflow: auto; border: 2px solid #E5E5E5;"></div>
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-danger" data-dismiss="modal">
					<fmt:message
						key="tiles.views.institution.device.rule.delete.tip.confirm" />
				</button>
			</div>
		</div>
	</div>
</div>
<!--Modal end-->

<!--Modal end-->
    