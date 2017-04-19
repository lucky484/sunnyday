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
					<h3 class="text-danger del-text" id="NameListMessage"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.del.sure.tips"/></h3>
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
		<input type="hidden" id="netUrlStr" />
       <input type="hidden" id="netUrlNames" />
       <input type="hidden" id="blackWhiteListId" />
         <div class="modal-header b-b-l-none">
           <button type="button" class="close" data-dismiss="modal" aria-label="Close">
             <span aria-hidden="true">&times;</span>
           </button>
           <h4 id="modelTitle"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.add.bwlist"/></h4>
         </div>
         <div class="modal-body">
           <div class="row" id="bwListValidatePart">
              <div class="panel-body">
                    <div class="form-group">
                    <div class="col-lg-12">
                     <label class="col-lg-2 paddingWidth"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.kind"/></label>
	                  <div class="col-lg-8 paddingWidth">
	                    <select id="blackWhiteListType" class="form-control m-b">
	                      <option value="0"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.black"/></option>
	                      <option value="1"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.white"/></option>
	                    </select>
	                  </div>
                    </div>
                   </div>
                   <div class="form-group">
                   <div class="col-lg-12">
                     <div class="col-lg-2 paddingWidth"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.name"/><span class="text-danger">*</span></div>
                     <div class="col-lg-8 paddingWidth">
                       <input type="text" id="blackWhiteListName" name="blackWhiteListName" class="form-control m-b add-user-name"
                       		  data-parsley-required="true" data-parsley-maxlength="20"
								data-parsley-trigger="blur"
								data-parsley-remote data-parsley-remote-validator="checkNameIsExist" 
								data-parsley-trigger="blur" data-parsley-remote-message="<fmt:message key="parsley.account.exists"/>"/>
                     </div>
                    </div>
                   </div>
                   <div class="form-group">
                   <div class="col-lg-12">
	                  <div class="col-lg-2 paddingWidth"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.desc"/>&nbsp;</div>
	                   <div class="col-lg-8 paddingWidth">
	                      <textarea class="form-control m-b" id="blackWhiteRemark" data-parsley-maxlength="500" data-parsley-trigger="blur" onblur="checkValue()"></textarea>
	                   </div>
	               </div>
	               </div>
                   <div class="form-group app_list">
                   <div class="col-lg-12">
                     <div class="col-lg-2 paddingWidth"></div>
	                  <div class="col-lg-10 paddingWidth">
	                  <div class="col-lg-3 appIdMargin">
	                  <div class="col-lg-11 appIdMargin">
	                    <input type="text" id="netUrlName" placeholder="<fmt:message key='tiles.views.netbehaviormanager.blackwhitelist.url.name.need'/>" class="form-control m-b inputNoWidth" data-parsley-required="true"/>
	                  </div>
	                  <div class="col-lg-1 comma">、</div>
	                  </div>
	                     <div class="col-lg-3 appNameMargin">
	                      <div class="col-lg-11 appIdMargin">
	                     <input type="text" id="netUrlAddress" placeholder="<fmt:message key='tiles.views.netbehaviormanager.blackwhitelist.url.address.need'/>" class="form-control m-b inputNoWidth" data-parsley-required="true"/>
	                     </div>
	                     <div class="col-lg-1 comma">、</div>
	                     </div>
	                     <div class="col-lg-4 btnPaddingLeft">
	                     <button class="btn btn-xm btn-success btn-rounded"  onclick="addNewBwListUrl()"><i class="fa fa-plus"></i><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.add"/></button>
	                     <a href="javascript:void(0);" class="btn btn-sm btn-default btn-rounded "onclick="openExcelModal()" id="importUser"> <i
									class="fa fa-download"></i> &nbsp;<fmt:message key="tiles.views.user.index.import"/>
								</a>
	                     </div>
	                  </div>
	                  </div>
                   </div>
	               <div class="form-group app_table">
	                   <div class="col-lg-12">
	                     <div class="col-lg-2">
	                     	<div><label><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.net.url.list"/></label></div>
	                     	<!-- <font  id="netListValue" style="color:red">必填</font> -->
	                     </div>
	                     <div class="col-lg-8 paddingWidth">
	                       <div class="col-lg-12 appTitle">
	                         <div class="col-lg-4 appIdTitle"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.url.name"/></div>
	                         <div class="col-lg-4 appIdTitle"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.url.address"/></div>
	                         <div class="col-lg-1">&nbsp;</div>
	                       </div>
	                       <div class="col-lg-12 paddingWidth appHeight" id="bWListsDiv">
	                       </div>
	                   </div>
	                  </div> 
                  </div>
                 </div>
              </div>
              <div class="modal-footer" id="saveBtn">
			     <a href="#" onclick="saveNetBehaviorBlackWhitelList()" title="addHref" class="btn btn-success"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.save"/></a>
			     <a href="#" onclick="updateNetBehaviorBlackWhitelList()" title="updateHref" class="btn btn-success"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.update"/></a>
			     <a href="#" class="btn" data-dismiss="modal"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.cancle"/></a>
		      </div>
           </div>
		</div>
	</div>
</div>

	<!-- 新增或编辑黑白名单 start -->
	<div class="modal fade" id="viewModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
     <div class="modal-dialog modal-lg" role="document">
       <div class="modal-content">
       <input type="hidden" id="viewNetUrlStr" />
       <input type="hidden" id="viewNetUrlNames" />
       <input type="hidden" id="viewBlackWhiteListId" />
         <div class="modal-header b-b-l-none">
           <button type="button" class="close" data-dismiss="modal" aria-label="Close">
             <span aria-hidden="true">&times;</span>
           </button>
           <h5><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.view.bwlist"/></h5>
         </div>
         <div class="modal-body">
           <div class="row">
              <div class="panel-body">
                    <div class="form-group">
                    <div class="col-lg-12">
                     <label class="col-lg-2 paddingWidth"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.kind"/></label>
	                  <div class="col-lg-8 paddingWidth">
	                    <select id="viewBlackWhiteListType" class="form-control m-b" disabled="disabled">
	                      <option value="0"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.black"/></option>
	                      <option value="1"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.white"/></option>
	                    </select>
	                  </div>
                    </div>
                   </div>
                   <div class="form-group">
                   <div class="col-lg-12">
                     <div class="col-lg-2 paddingWidth"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.name"/><span class="text-danger">*</span></div>
                     <div class="col-lg-8 paddingWidth">
                       <input type="text" id="viewBlackWhiteListName" class="form-control m-b" readonly/>
                     </div>
                    </div>
                   </div>
                   <div class="form-group">
                   <div class="col-lg-12">
	                  <div class="col-lg-2 paddingWidth"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.desc"/>&nbsp;</div>
	                   <div class="col-lg-8 paddingWidth">
	                      <textarea class="form-control m-b" id="viewBlackWhiteRemark" readonly></textarea>
	                   </div>
	               </div>
	               </div>
                   </div>
	               <div class="form-group app_table">
	                   <div class="col-lg-12">
	                     <div class="col-lg-2"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.net.url.list"/></div>
	                     <div class="col-lg-8 paddingWidth">
	                       <div class="col-lg-12 appTitle">
	                         <div class="col-lg-4 appIdTitle"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.url.name"/></div>
	                         <div class="col-lg-4 appIdTitle"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.url.address"/></div>
	                       </div>
	                       <div class="col-lg-12 paddingWidth appHeight" id="bWListsDivView">
	                       </div>
	                   </div>
	                  </div> 
                  </div>
                 </div>
              </div>
              <div class="modal-footer" id="saveBtn">
			     <!-- <a href="#" class="btn" data-dismiss="modal">取消</a> -->
			     <a href="#" class="btn" data-dismiss="modal"><fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.cancle"/></a>
			     
		      </div>
           </div>
		</div>
	</div>
     <!-- 新增或编辑黑白名单 end -->
     
     
     
     
     
<!--      导入功能 -->

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
								<fmt:message key="tiles.views.blacklist.export.title" />
							</header>
							<div class="panel-body">
								<spring:url value="/institution/vtl/save" var="modal_saveUrl" />
								<spring:url value="/resources/images/virtualMemberexcelmode.png"
									var="virtualMemberexcelmode" />

								<label class="col-lg-12"><fmt:message
										key="tiles.views.blacklist.export.title2" /> </label>
								<spring:url value="/institution/netbehavior/blackwitelist/getexportmodel"
									var="getexportModel" />
								<form action="${getexportModel}" method="get" id="getexportModel">
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
								<spring:url value="/institution/user/importusers"
									var="importUsers" />
								<form action="${importUsers}" method="post" id="importUsers"
									enctype="multipart/form-data">
									<div class="col-sm-12 uploadvirmodel">
										<div class="col-sm-4">
											<a href="javascript:void();" class="a-upload"> <input
												type="file" name="file" id="file">
											<fmt:message key="tiles.views.user.index.table.excel.upload" />
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
								onclick="importUsers()"
								value="<fmt:message key='tiles.views.user.index.import'/>">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--Modal end-->
<!-- Modal start-->
<div class="modal fade" id="loadingModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<spring:url value="/resources/images/loading.gif" var="loadingGif" />
			<img class="col-sm-7" src="${loadingGif}"
				style="width: 200px; margin-left: 30%; margin-top: 30%;" />

		</div>
	</div>
</div>
     
     
     
     
<!-- Modal start-->
<div class="modal fade" id="successModal" tabindex="-1" role="dialog"
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
			
			<div class="col-sm-12"><h1 class="text-danger"></h1>
			<div  id="rownumbers"style="width:500px; height:100px; overflow:auto; border:2px solid #E5E5E5;"></div></div>
			
			<div class="modal-footer">
				<button type="button" class="btn btn-danger" data-dismiss="modal" >
					<fmt:message key="tip.del.confirm" />
				</button>
			</div>
		</div>
	</div>
</div>
<!--Modal end-->