<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
  <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
     <div class="modal-dialog modal-lg" role="document">
       <div class="modal-content">
         <div class="modal-header b-b-l-none"> 
           <div class="col-lg-6">
           	   <h4><fmt:message key="tiles.views.institution.system.words.indexmodal.title"/></h4>
           </div>
           <div class="col-lg-6">
	           <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	             <span aria-hidden="true">&times;</span>
	           </button>
           </div>
         </div>
         <div class="modal-body">
           <div class="row">
               <input type="hidden" name="id" id="id"/>
               <form class="bs-example form-horizontal" id="saveWords" data-parsley-validate>
                  <section class="panel-default" style="min-height:200px;border:0px solid silver;">
                        <div class="panel-body">
                          <div class="tab-content">
                              <input type="hidden" id="id" name="id" />
                              <div class="col-lg-12">
                                  <div class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.system.words.indexmodal.name"/><span class="text-danger">*</span></div>
                                  <div class="col-lg-8">
                                    <input type="text" id="name" class="form-control m-b" onblur="exists()"  data-parsley-required="true"  data-parsley-maxlength="30" 
                                             data-parsley-remote data-parsley-remote-validator="existsValidate" 
                                             data-parsley-trigger="blur"  
                                             autofocus="autofocus"  
                                             data-parsley-remote-message="<fmt:message key="parsley.net.account.exists"/>" autocomplete="off"/>
                                  </div>
                               </div>
                               <div class="col-lg-12">
                                  <div class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.system.words.indexmodal.websitetype"/><span class="text-danger">*</span></div>
                                  <div class="col-lg-8" id="websiteHtml">
                                  <select id="website_type" class="form-control website_type" data-parsley-required="true">
                                      <option value=""><fmt:message key="tiles.views.institution.system.words.indexmodal.choose"/></option>
                                      <c:forEach items="${websiteList}" var="web">
                                          <option value="${web.id}">${web.name}</option>
                                      </c:forEach>
                                  </select>
                                 </div>
                                </div>
                            </div> 
                          </div>
                      </section>
                  </form>
              </div>
           </div>
           <div class="modal-footer" id="saveBtn">
              <a href="javascript:save()" class="btn btn-success"><fmt:message key="general.jsp.comfirm.label"/></a>
              <a href="javascript:void(0)" class="btn" data-dismiss="modal"><fmt:message key="general.jsp.cancel.label"/></a>
          </div>
         </div>
       </div>
     </div>
    
    <!-- 查看系统词库 -->
    <div class="modal fade" id="findModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
     <div class="modal-dialog modal-lg" role="document">
       <div class="modal-content">
         <div class="modal-header b-b-l-none"> 
           <button type="button" class="close" data-dismiss="modal" aria-label="Close">
             <span aria-hidden="true">&times;</span>
           </button>
           <h3><fmt:message key="tiles.views.institution.system.words.indexmodal.title"/></h3>
         </div>
         <div class="modal-body">
           <div class="row">
              <section class="panel-default" style="min-height:200px;border:0px solid silver;">
                    <div class="panel-body">
                      <div class="tab-content">
                          <div class="col-lg-12">
                              <div class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.system.words.indexmodal.name"/><span class="text-danger">*</span></div>
                              <div class="col-lg-8">
                                <input type="text" id="findName" class="form-control m-b" disabled="disabled" />
                              </div>
                           </div>
                           <div class="col-lg-12">
                              <div class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.system.words.indexmodal.websitetype"/><span class="text-danger">*</span></div>
                              <div class="col-lg-8" id="findWebsiteHtml"></div>
                            </div>
                        </div> 
                      </div>
                  </section>
              </div>
           </div>
           <div class="modal-footer" id="saveBtn">
              <a href="javascript:void(0)" class="btn" data-dismiss="modal"><fmt:message key="general.jsp.cancel.label"/></a>
          </div>
         </div>
       </div>
     </div>

    <!-- 删除词库提示框-->
	<div class="modal fade" id="delWordsModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog" role="document">
		<input type="hidden" id="delIosId" />
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
				<h3 class="text-danger del-text"><fmt:message key="tiles.views.institution.system.words.indexmodal.delmessage"/></h3>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<fmt:message key="tip.del.cancel" />
				</button>
				<input type="button" id="clickWordsClick" class="btn btn-danger btn-delete-users" data-dismiss="modal" onclick="" value="<fmt:message key='tip.del.confirm'/>"></div>
			</div>
		</div>
	</div>
	<!--Modal end-->