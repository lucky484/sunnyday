 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
  <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
     <div class="modal-dialog modal-lg" role="document">
       <div class="modal-content">
         <div class="modal-header b-b-l-none">
           <button type="button" class="close" data-dismiss="modal" aria-label="Close">
             <span aria-hidden="true">&times;</span>
           </button>
           <h4><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.title"/></h4>
         </div>
         <div class="modal-body">
           <input type="hidden" id="userType" value="${userType}"/>
           <input type="hidden" name="token" id="tokenId"/>
           <form class="bs-example form-horizontal" id="savePolicyForm" data-parsley-validate>
           <div class="row">
              <section class="panel-default" style="min-height:300px;border:0px solid silver;">
                    <header class="panel-heading bg-light" id="deviceTab">
                      <ul class="nav nav-tabs nav-justified">
                        <li class="active"><a href="#tab1" data-toggle="tab"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.currency"/></a></li>
                        <li><a href="#tab2" data-toggle="tab"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.password"/></a></li>
                        <li><a href="#tab3" data-toggle="tab"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.security"/></a></li>
                        <li><a href="#tab4" data-toggle="tab"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.limit"/></a></li>
                        <c:if test="${menu[42].isshow==-1}">
                        <li id="tab5auth"><a href="#tab5" data-toggle="tab"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.netlimit"/></a></li>
                        </c:if>
                        <li><a href="#tab6" data-toggle="tab"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.applimit"/></a></li>
                        <li><a href="#tab7" data-toggle="tab"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.wifisetting"/></a></li>
                    </ul>
                    </header>
                    <div class="panel-body">
                      <div class="tab-content">
                        <div class="tab-pane active" id="tab1">	
							<input type="hidden" id="id" name="id" />
	                        <div class="form-group">
	                          <div class="col-lg-3 control-label"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.name"/><span class="text-danger">*</span></div>
	                          <div class="col-lg-8">
	                            <input type="text" id="name" onblur="exists()" class="form-control m-b"  data-parsley-required="true"  data-parsley-maxlength="30" 
											 data-parsley-remote data-parsley-remote-validator="existsValidate" 
											 data-parsley-trigger="blur"  
											 autofocus="autofocus"  
											 data-parsley-remote-message="<fmt:message key="parsley.divice.account.exists"/>" autocomplete="off"/>
	                            <span class="help-block m-b-none"></span>
	                          </div>
	                        </div>
	                        <div class="form-group">
	                          <div class="col-lg-3 control-label"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.description"/>&nbsp;</div>
	                          <div class="col-lg-8">
	                          <textarea class="form-control m-b" id="description" style="width:100%;resize:none;"></textarea>
	                          </div>
	                        </div>
	                        <div class="form-group">
	                          <div class="col-lg-3 control-label"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.department"/></div>
                          <div class="col-lg-8">
	                          <div class="treeHeight">
	                             <ul id="tree"></ul>
	                          </div>
	                          </div>
	                        </div>
	                        <div class="form-group virtualAuth">
	                          <label class="col-lg-3 control-label"></label>
	                          <div class="col-lg-8">
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
                        </div>
                        <div class="tab-pane" id="tab2">
                          <div class="form-group">
	                        <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.passwordlength"/></label>
		                      <div class="col-sm-8">
		                        <select id="passwordLength" class="form-control m-b" onclick="resetPasswordLength()">
		                          <option value=""><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.choose"/></option>
		                          <option value="0">4</option>
<!-- 		                          <option value="1">5</option>
		                          <option value="2">6</option>
		                          <option value="3">7</option>
		                          <option value="4">8</option>
		                          <option value="5">9</option>
		                          <option value="6">10</option> -->
		                        </select>
		                        <div id="passwordLength-modify-error" style="height:38px;line-height:38px;" class="col-sm-12">
								 <ul class="parsley-errors-list filled"><li class=""><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.passwordlengthdesc"/></li></ul>
						     </div>
		                      </div>
	                      </div>
                          <div class="form-group">
	                        <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.passwordcomplex"/></label>
		                      <div class="col-sm-8">
		                       <select id="passwordComplexity" class="form-control m-b" onclick="resetPasswordComplexity()">
		                          <option value=""><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.choose"/></option>
		                          <option value="0"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.digitpassword"/></option>
<%-- 		                          <option value="1"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.digitletter"/></option>
		                          <option value="2"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.complexpassword"/></option>
 --%>		                       </select>
		                       <div id="passwordComplexity-modify-error" style="height:38px;line-height:38px;" class="col-sm-12" >
								  <ul class="parsley-errors-list filled"><li class=""><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.passwordcomplexdesc"/></li></ul>
							  </div>
		                      </div>
	                        </div>
                           <div class="form-group">
	                        <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.locklongesttime"/></label>
		                      <div class="col-sm-8">
		                        <select id="lockLongestTime" class="form-control m-b">
		                          <option value=""><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.choose"/></option>
		                          <option value="0"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.never"/></option>
		                          <option value="1">15<fmt:message key="tiles.views.institution.devicepolicy.indexmodal.second"/></option>
		                          <option value="2">30<fmt:message key="tiles.views.institution.devicepolicy.indexmodal.second"/></option>
		                          <option value="3">1<fmt:message key="tiles.views.institution.devicepolicy.indexmodal.minute"/></option>
		                          <option value="4">2<fmt:message key="tiles.views.institution.devicepolicy.indexmodal.minute"/></option>
		                          <option value="5">5<fmt:message key="tiles.views.institution.devicepolicy.indexmodal.minute"/></option>
		                          <option value="6">10<fmt:message key="tiles.views.institution.devicepolicy.indexmodal.minute"/></option>
		                          <option value="7">30<fmt:message key="tiles.views.institution.devicepolicy.indexmodal.minute"/></option>
		                        </select>
		                      </div>
	                       </div>
	                       <div class="form-group">
	                        <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.passwordvalidity"/></label>
		                      <div class="col-sm-8">
		                        <select id="passwordValidity" class="form-control m-b">
		                          <option value=""><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.choose"/></option>
		                          <option value="0"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.forever"/></option>
		                          <option value="1"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.oneweek"/></option>
		                          <option value="2"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.twoweek"/></option>
		                          <option value="3"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.onemonth"/></option>
		                          <option value="4"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.twomonth"/></option>
		                          <option value="5"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.threemonth"/></option>
		                        </select>
		                      </div>
	                       </div>
	                       <div class="form-group">
	                        <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.attempttimes"/></label>
		                      <div class="col-sm-8">
		                        <select id="attemptTimes" class="form-control m-b">
		                          <option value=""><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.choose"/></option>
		                          <option value="0"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.unlimited"/></option>
		                          <option value="1">4</option>
		                          <option value="2">5</option>
		                          <option value="3">6</option>
		                          <option value="4">7</option>
		                          <option value="5">8</option>
		                          <option value="6">9</option>
		                          <option value="7">10</option>
		                          <option value="8">11</option>
		                          <option value="9">12</option>
		                        </select>
		                      </div>
		                      <div class="col-sm-12 attention">
		                      <label class="col-sm-3 control-label"></label>
		                        <div class="col-sm-8"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.attempttimesdesc"/>
		                         </div>
		                      </div>
	                      </div>
                        </div>
                        <div class="tab-pane" id="tab3">
                           <div class="form-group">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks ">
                                  <label class="col-lg-12">
                                    <input type="checkbox" id="deviceEncryption" name="deviceEncryption" />
                                    <i></i> <fmt:message key="tiles.views.institution.devicepolicy.indexmodal.deviceencryption"/>
                                  </label>
                                </div>
                              </div>
                            </div>
                            <div class="form-group">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks ">
                                  <label class="col-lg-12">
                                    <input type="checkbox" id="sdEncryption" name="sdEncryption" />
                                    <i></i> <fmt:message key="tiles.views.institution.devicepolicy.indexmodal.sdencryption"/>
                                  </label>
                                </div>
                              </div>
                            </div>
                        </div>
                        <div class="tab-pane" id="tab4">
                            <div class="form-group">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks ">
                                  <label class="col-lg-12">
                                    <input type="checkbox" id="allowUseCamera" name="allowUseCamera" />
                                    <i></i> <fmt:message key="tiles.views.institution.devicepolicy.indexmodal.allowusecamera"/>
                                  </label>
                                </div>
                              </div>
                            </div>
                            <div class="form-group">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks ">
                                  <label class="col-lg-12">
                                    <input type="checkbox" id="allowUseWifi" name="allowUseWifi" />
                                    <i></i> <fmt:message key="tiles.views.institution.devicepolicy.indexmodal.allowusewifi"/>
                                  </label>
                                </div>
                              </div>
                            </div>
                            <div class="form-group">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks ">
                                  <label class="col-lg-12">
                                    <input type="checkbox" id="allowUseBluetooth" name="allowUseBluetooth" />
                                    <i></i> <fmt:message key="tiles.views.institution.devicepolicy.indexmodal.allowusebluetooth"/>
                                  </label>
                                </div>
                              </div>
                            </div>
                            <div class="form-group">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks ">
                                  <label class="col-lg-12">
                                    <input type="checkbox" id="allowUseGps" name="allowUseGps" />
                                    <i></i><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.allowusegps"/>
                                  </label>
                                </div>
                              </div>
                            </div>
                          </div> 
                        <c:if test="${menu[42].isshow==-1}">
                        <div class="tab-pane" id="tab5">
                     	 <div class="form-group" class="blackRadio">
							<div class="radio i-checks col-lg-8">
								<label class="col-lg-5"><input type="radio" name="enableNameList" onclick="enableNameIds(0)" id="name1" value="0">
									<i></i> <fmt:message key="tiles.views.institution.devicepolicy.indexmodal.enableblacknamelist"/>
								</label>&nbsp;&nbsp;&nbsp;
								<label class="col-lg-5"> <input type="radio" name="enableNameList" onclick="enableNameIds(1)" id="name2" value="1">
									<i></i> <fmt:message key="tiles.views.institution.devicepolicy.indexmodal.enablewhitenamelist"/>
								</label>
							</div>
						</div>
						<div class="form-group">
							<div class="col-lg-12" class="notion">
							   <span id="enableWords"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.enablewords"/></span>
							</div>
						</div>
	                    <div class="form-group" class="currentNameList">
	                      <label class="col-sm-10 control-label" style="text-align:left;"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.currentnamelist"/></label>
	                      <div class="col-sm-10">
	                        <div class="input-group">
	                          <input type="text" class="form-control" disabled="disabled" id="nameListText" />
	                          <span class="input-group-btn">
	                            <button class="btn btn-default" type="button" onclick="loadNameList(0)"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.choosenamelist"/></button>
	                          </span>
	                        </div>
	                      </div>
	                    </div>
	                    <div class="form-group" class="currentNameList">
	                      <label class="col-sm-10" style="text-algin:left;align:left;"><input class="col-sm-1" type="checkbox" id="visitLimit" name="visitLimit"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.visitlimit"/></label>
	                      <div class="col-sm-12 marginTop">
	                        <div class="col-sm-3"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.monday"/>：</div>
	                        <div class="col-sm-4">
	                        <input type="text" class="form-control" id="startTime1" placeholder="00:00-23:59"  data-parsley-startdatestr />
	                        </div>
	                        <div class="col-sm-1">、</div> 
	                        <div class="col-sm-4">
	                        <input type="text" class="form-control" id="endTime1" placeholder="00:00-23:59" data-parsley-enddatestr  data-parsley-comparedate="#startTime1" />
	                        </div>
	                      </div>		
	                      <div class="col-sm-12 marginTop">
	                      	<div class="col-sm-3"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.tuesday"/>：</div>
	                        <div class="col-sm-4">
	                        <input type="text" class="form-control" id="startTime2" placeholder="00:00-23:59" data-parsley-startdatestr />
	                        </div>
	                        <div class="col-sm-1">、</div>
	                        <div class="col-sm-4">
	                        <input type="text" class="form-control" id="endTime2" placeholder="00:00-23:59"  data-parsley-enddatestr data-parsley-comparedate="#startTime2" />
	                        </div>
	                      </div>	
	                      <div class="col-sm-12 marginTop">
	                        <div class="col-sm-3"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.wednesday"/>：</div>
	                        <div class="col-sm-4">
	                        <input type="text" class="form-control" id="startTime3" placeholder="00:00-23:59" data-parsley-startdatestr/>
	                        </div>
	                        <div class="col-sm-1">、</div>
	                        <div class="col-sm-4">
	                        <input type="text" class="form-control" id="endTime3" placeholder="00:00-23:59" data-parsley-enddatestr data-parsley-comparedate="#startTime3" />
	                        </div>
	                      </div>	
	                      <div class="col-sm-12 marginTop">
	                        <div class="col-sm-3"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.thursday"/>：</div>
	                        <div class="col-sm-4">
	                        <input type="text" class="form-control" id="startTime4" placeholder="00:00-23:59"  data-parsley-startdatestr/>
	                        </div>
	                        <div class="col-sm-1">、</div>
	                        <div class="col-sm-4">
	                        <input type="text" class="form-control" id="endTime4" placeholder="00:00-23:59" data-parsley-enddatestr data-parsley-comparedate="#startTime4" />
	                        </div>
	                      </div>	
	                      <div class="col-sm-12 marginTop">
	                        <div class="col-sm-3"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.friday"/>：</div>
	                        <div class="col-sm-4">
	                        <input type="text" class="form-control" id="startTime5" placeholder="00:00-23:59" data-parsley-startdatestr/>
	                        </div>
	                        <div class="col-sm-1">、</div>
	                        <div class="col-sm-4">
	                        <input type="text" class="form-control" id="endTime5" placeholder="00:00-23:59" data-parsley-enddatestr data-parsley-comparedate="#startTime5"  />
	                        </div>
	                      </div>	
	                      <div class="col-sm-12 marginTop">
	                        <div class="col-sm-3"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.saturday"/>：</div>
	                        <div class="col-sm-4">
	                        <input type="text" class="form-control" id="startTime6" placeholder="00:00-23:59" data-parsley-startdatestr/>
	                        </div>
	                        <div class="col-sm-1">、</div>
	                        <div class="col-sm-4"> 
	                        <input type="text" class="form-control" id="endTime6" placeholder="00:00-23:59" data-parsley-enddatestr data-parsley-comparedate="#startTime6"  />
	                        </div>
	                      </div>	
	                      <div class="col-sm-12 marginTop">
	                        <div class="col-sm-3"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.sunday"/>：</div>
	                        <div class="col-sm-4">
	                        <input type="text" class="form-control" id="startTime7" placeholder="00:00-23:59"  data-parsley-startdatestr />
	                        </div>
	                        <div class="col-sm-1">、</div>
	                        <div class="col-sm-4">
	                        <input type="text" class="form-control" id="endTime7" placeholder="00:00-23:59"  data-parsley-enddatestr data-parsley-comparedate="#startTime7"  />
	                        </div>
	                      </div>		
						</div>
                        </div>
                        </c:if>
                        <div class="tab-pane" id="tab6">
                           <div class="form-group blackRadio">
							<div class="radio i-checks col-lg-8">
							   <!-- 黑名单 -->
								<label class="col-lg-5"><input type="radio" name="enableAppNameList" onclick="enableAppNameIds(0)" id="appName1" value="1">
									<i></i> <fmt:message key="tiles.views.institution.devicepolicy.indexmodal.enableblacknamelist"/>
								</label>&nbsp;&nbsp;&nbsp;
								<!-- 0.白名单 -->
 								<label class="col-lg-5"> <input type="radio" name="enableAppNameList" onclick="enableAppNameIds(1)" id="appName2" value="0">
									<i></i> <fmt:message key="tiles.views.institution.devicepolicy.indexmodal.enablewhitenamelist"/>
								</label> 
							</div>
						   </div>
						   <div class="form-group notion">
							  <div class="col-lg-12">
							     <span id="appEnableWords"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.enablewords"/></span>
							  </div>
						      <label class="col-sm-10 control-label" style="text-align:left;"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.currentnamelist"/></label>
		                      <div class="col-sm-10">
		                        <div class="input-group">
		                          <input type="text" class="form-control" disabled="disabled" id="appNameListText" />
		                          <span class="input-group-btn">
		                            <button class="btn btn-default" type="button" onclick="loadAppNameList(0)"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.choosenamelist"/></button>
		                          </span>
		                        </div>
		                      </div>
						  </div>
                        </div>
                        <div class="tab-pane" id="tab7">   
                        </div> 
                      </div>
                    </div>
                  </section>
              </div>
              </form>
           </div>
           <div class="modal-footer" id="saveBtn">
			  <a href="javascript:void(0)" onclick="saveAndroidPolicy()" class="btn btn-success"><fmt:message key="general.jsp.comfirm.label"/></a>
			  <a href="javascript:void(0)" class="btn" data-dismiss="modal"><fmt:message key="general.jsp.cancel.label"/></a>
           </div>
         </div>
       </div>
     </div>
     <!-- 加载黑名单或者白名单列表数据 -->
    <div class="modal fade" id="loadNameList" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
     <!-- 选择黑白名单列表的id值 -->
     <input type="hidden" id="chooseEnableIds"/>
     <input type="hidden" id="chooseDepartIds"/>
     <input type="hidden" id="nameTag"/>
	    <div class="modal-dialog modal-lg" role="document">
	      <div class="modal-content">
	        <div class="modal-header b-b-l-none">
	          <button type="button" class="close"  onclick="openNameList()">
	            <span aria-hidden="true">&times;</span>
	          </button>
	        </div>
	        <div class="modal-body" style="min-height: 300px;overflow: scroll;">
	          <div class="row" id="insert">
	            <div class="col-sm-12">
	              <section class="panel panel-default">
	                <div class="panel-body">
	                    <div class="table-responsive">
	                       <table class="table table-striped b-t b-light" id="whiteBlackNamelist">
	                         <thead>
	                           <tr>
	                             <th></th>
	                             <th><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.loadname"/></th>
	                             <th><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.loadtype"/></th>
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
				<a href="javascript:void(0)" onclick="chooseNameList()" class="btn btn-success"><fmt:message key="general.jsp.comfirm.label"/></a>
				<a href="javascript:openNameList()" class="btn"><fmt:message key="general.jsp.cancel.label"/></a>
             </div>
	        </div>
	   </div>
    </div>
    
    <!-- 加载黑名单或者白名单列表数据 -->
    <div class="modal fade" id="loadAppNameList" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
     <!-- 选择应用黑白名单列表的id值 -->
     <input type="hidden" id="chooseAppEnableIds"/>
     <input type="hidden" id="chooseIosAppEnableIds"/>
     <input type="hidden" id="appTag"/>
	    <div class="modal-dialog modal-lg" role="document">
	      <div class="modal-content">
	        <div class="modal-header b-b-l-none">
	          <button type="button" class="close" onclick="openAppNameList()">
	            <span aria-hidden="true">&times;</span>
	          </button>
	        </div>
	        <div class="modal-body" style="min-height: 300px;overflow: scroll;">
	          <div class="row" id="insert">
	            <div class="col-sm-12">
	              <section class="panel panel-default">
	                <div class="panel-body">
	                    <div class="table-responsive">
	                       <table class="table table-striped b-t b-light" id="whiteAppBlackNamelist">
	                         <thead>
	                           <tr>
	                             <th></th>
	                             <th><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.loadname"/></th>
	                             <th><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.loadtype"/></th>
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
				<a href="javascript:void(0)" onclick="chooseAppNameList()" class="btn btn-success"><fmt:message key="general.jsp.comfirm.label"/></a>
				<a href="javascript:openAppNameList()" class="btn"><fmt:message key="general.jsp.cancel.label"/></a>
             </div>
	        </div>
	   </div>
    </div>
    
    <!-- 加载已分配设备  -->
    <div class="modal fade" id="loadAssignedDevice" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
	    <div class="modal-dialog modal-lg" role="document">
	      <div class="modal-content">
	        <div class="modal-header b-b-l-none">
	          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	            <span aria-hidden="true">&times;</span>
	          </button><h4><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.assigneddevice"/></h4>
	        </div>
	        <div class="modal-body" style="min-height: 300px;overflow: scroll;">
	          <div class="row" id="insert">
	            <div class="col-sm-12">
	              <section class="panel panel-default">
	                <div class="panel-body">
	                    <div class="table-responsive">
	                       <table class="table table-striped b-t b-light" id="loadAssignedList" style="width:100%">
	                         <thead>
	                           <tr>
	                             <th><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.devicestate"/></th>
	                             <th><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.devicename"/></th>
	                             <th><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.account"/></th>
	                             <th><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.devicemodel"/></th>
	                             <th><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.clientversion"/></th>
	                             <th><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.deviceownership"/></th>
	                             <th><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.deviceupdatetime"/></th>
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
	        </div>
	   </div>
    </div>
    
    <!-- 删除Ios设备策略功能 -->
	<div class="modal fade" id="delPolicyModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog" role="document">
		<input type="hidden" id="delId" />
		<input type="hidden" id="tag" />
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
					<h3 class="text-danger del-text" id="policyMessage"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.delmessage"/></h3>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<fmt:message key="tip.del.cancel" />
					</button>
					<input type="button" id="policyClick" class="btn btn-danger btn-delete-users"
						data-dismiss="modal" onclick="delDevicePolicy()" value="<fmt:message key='tip.del.confirm'/>">
				</div>
			</div>
		</div>
	</div>
	<!--Modal end-->
	
   <!-- Modal start-->
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

 <!-- 新增IOS策略 start -->
 <div class="modal fade" id="myIosModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
    <div class="modal-dialog modal-lg" role="document">
      <div class="modal-content">
        <div class="modal-header b-b-l-none">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
          <h4><fmt:message key="tiles.views.institution.devicepolicy.index.iostitle"/></h4>
        </div>
        <div class="modal-body">
          <form class="bs-example form-horizontal" id="saveIosPolicyForm" data-parsley-validate>
          <div class="row">
             <section class="panel-default" style="min-height:300px;border:0px solid silver;">
                   <header class="panel-heading bg-light" id="deviceIosTab">
                     <ul class="nav nav-tabs nav-justified">
                       <li class="active"><a href="#tab10" data-toggle="tab"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.currency"/></a></li>
                       <li><a href="#tab11" data-toggle="tab"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.password"/></a></li>
                       <li><a href="#tab12" data-toggle="tab"><fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.limit"/></a></li>
                       <li><a href="#tab13" data-toggle="tab"><fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.configure"/></a></li>
                       <c:if test="${menu[42].isshow==-1}">
                        <li id="tab14auth"><a href="#tab14" data-toggle="tab"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.netlimit"/></a></li>
                        </c:if>
<%--                         <li><a href="#tab15" data-toggle="tab"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.applimit"/></a></li>
 --%>                     </ul>
                   </header>
                   <div class="panel-body">
                     <div class="tab-content">
                       <div class="tab-pane active" id="tab10">	
                       <input type="hidden" id="iosId" name="iosId" />
                       <input type="hidden" id="tokenIosId" name="tokenIosId"/>
                       <input type="hidden" id="isEnablePassword" name="isEnablePassword" value="0"/>
                        <div class="form-group">
                          <div class="col-lg-3 control-label"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.name"/><span class="text-danger">*</span></div>
                          <div class="col-lg-8">
                            <!-- 设备策略id -->
                            <input type="text" id="iosName" onblur="iosExists()" class="form-control m-b"  data-parsley-required="true"  data-parsley-maxlength="30" 
										 data-parsley-remote data-parsley-remote-validator="existsValidate" 
										 data-parsley-trigger="blur"  
										 autofocus="autofocus"  
										 data-parsley-remote-message="<fmt:message key="parsley.divice.account.exists"/>" autocomplete="off"/>
                            <span class="help-block m-b-none"></span>
                          </div>
                        </div>
                        <div class="form-group">
                          <div class="col-lg-3 control-label"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.description"/>&nbsp;</div>
                          <div class="col-lg-8">
                          <textarea class="form-control m-b" id="iosDescription" style="width:100%;resize:none;"></textarea>
                          </div>
                        </div>
                        <div class="form-group">
                          <div class="col-lg-3 control-label"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.department"/></div>
                             <div class="col-lg-8">
                          <div class="treeHeight">
                             <ul id="iosTree"></ul>
                          </div>
                          </div>
                        </div>
                        <div class="form-group virtualAuth">
                          <label class="col-lg-3 control-label"></label>
                          <div class="col-lg-8">
                          <div class="col-lg-12 paddingZero">
                          <table class="policyVirtPadding">
                           <tr height="30px;">
                             <td width="50%" class="borderNoLine"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.virtual"/></td>
                             <td class="borderNoLine" class="borderNoLine">
                             <div class="col-lg-12 paddingZero">
                                <div class="col-lg-11 paddingZero">
                                   <input id="policyIosName" type="text" class="input-sm form-control" placeholder="<fmt:message key="tiles.views.institution.devicepolicy.indexmodal.user"/>" style="margin-left:2px;"/>
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
                                        <li class="virtualLi"><input id="virIosTu${virtual.id}" type="checkbox" name="virtualIosIds" value="${virtual.id}"/>&nbsp;&nbsp;${virtual.name}(${virtual.collectionName})</li>
                                     </c:forEach>
	                            </ul>
                             </td>
                             <td class="borderNoLine">
	                           <ul class="policyUserRight" id="policyIosVirtualRight"> 
                              </ul>
                             </td>
                           </tr>
                          </table>
                          </div>
                          </div>
                        </div>
                       </div>
                       <div class="tab-pane" id="tab11">
                       </div>
                      <div class="tab-pane" id="tab12">
                         <div class="form-group col-lg-12 iosHeight">&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.devicefunction"/>
                         </div>
                         <!-- 允许安装应用程序 -->
                         <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowInstallApp" name="allowInstallApp" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowinstallapp"/>
                            </div>
                          </div>
                          <!-- 允许受管控的应用打开不受管控的应用的文档 -->
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowOpenFromManagedToUnmanaged" name="allowOpenFromManagedToUnmanaged" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowOpenFromManagedToUnmanaged"/>
                            </div>
                          </div>
                          <!-- 允许不受管控的应用打开受管控的应用的文档 -->
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowOpenFromUnmanagedToManaged" name="allowOpenFromUnmanagedToManaged" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowOpenFromUnmanagedToManaged"/>
                            </div>
                          </div>
                          <!-- 允许使用相机 -->
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowIosUseCamera" name="allowIosUseCamera" checked="checked" onclick="checkIosCamera()"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowusecamera"/>
                            </div>
                             <div class="col-lg-12 m-l">
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="allowFaceTime" name="allowFaceTime" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allow"/>FaceTime
                            </div> 
                          </div>
                          <!-- 允许屏幕捕捉 -->
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowScreenCatch" name="allowScreenCatch" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowscreencapture"/>
                            </div>
                          </div>
                          <!-- 允许在漫游时自动同步 -->
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowGlobalBackgroundFetchWhenRoaming" name="allowGlobalBackgroundFetchWhenRoaming" checked="checked"/>&nbsp;&nbsp;允许在漫游时自动同步
                            </div>
                          </div>
                          <!-- 允许Siri -->
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowSiri" name="allowSiri" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allow"/> Siri
                            </div>
                          </div>
                          <!-- 设备锁定时允许使用Siri -->
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowAssistantWhileLocked" name="allowAssistantWhileLocked" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowAssistantWhileLocked"/>
                            </div>
                          </div>
                          <!-- 允许语音拨号 -->
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowVoiceDialing" name="allowVoiceDialing" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowvoicedial"/>
                            </div>
                          </div>
                          <!-- 强制用户为所有购买项目输入iTunes Store密码 -->
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="forceiTunesStore" name="forceiTunesStore"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.limitinputitunspassword"/>
                            </div>
                          </div>
                          <!-- 限制广告追踪 -->
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="limitAdvertTracking" name="limitAdvertTracking"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.limitadverttracking"/>
                            </div>
                          </div> 
                          <!-- 允许锁屏时显示TodayView的消息 -->
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowLockScreenTodayView" name="allowLockScreenTodayView" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowLockScreenTodayView"/>
                            </div>
                          </div>
                          <!-- icloud同步钥匙串 -->
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowCloudKeychainSync" name="allowCloudKeychainSync" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowCloudKeychainSync"/>
                            </div>
                          </div>
                          <!-- 允许锁屏时显示控制中心的消息 -->
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowLockScreenControlCenter" name="allowLockScreenControlCenter" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowLockScreenControlCenter"/>
                            </div>
                          </div>
                          <!-- 允许指纹解锁 -->
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowFingerprintForUnlock" name="allowFingerprintForUnlock" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowFingerprintForUnlock"/>
                            </div>
                          </div>
                          <!-- 允许锁屏时显示通知消息 -->
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowLockScreenNotificationsView" name="allowLockScreenNotificationsView" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowLockScreenNotificationsView"/>
                            </div>
                          </div>
                          <!-- 允许锁屏时显示Passbook消息 -->
<%--                           <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowDisplayPassbook" name="allowDisplayPassbook" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowdisplaypassbook"/>
                            </div>
                          </div> --%>
                          <!-- 允许被管理的应用将数据存储到iCloud -->
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowManagedAppsCloudSync" name="allowManagedAppsCloudSync" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowManagedAppsCloudSync"/>
                            </div>
                          </div>
                          <!-- 允许iCloud照片图库 -->
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowCloudPhotoLibrary" name="allowCloudPhotoLibrary" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowCloudPhotoLibrary"/>
                            </div>
                          </div>
                          <!-- 允许iCloud照片共享 -->
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowSharedStream" name="allowSharedStream" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowSharedStream"/>
                            </div>
                          </div>
                          <!-- 允许使用handoff -->
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowActivityContinuation" name="allowActivityContinuation" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowActivityContinuation"/>
                            </div>
                          </div>
                          <!-- 允许使用YouTube -->
                          <div class="form-group col-lg-12 iosHeight">&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowaccessprogram"/></div>
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowUseYoutube" name="allowUseYoutube" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowuse"/>YouTube
                            </div>
                          </div>
                          <!-- 允许使用iTunes Store -->
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowUseiTunes" name="allowUseiTunes" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowuse"/>iTunes Store
                            </div>
                          </div>
                          <!-- 允许使用Game Center -->
<%--                           <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowGameCenter" name="allowGameCenter" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowuse"/>Game Center
                            </div>
                          </div> --%>
                          <!-- 允许添加 Game Center好友 -->
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowAddingGameCenterFriends" name="allowAddingGameCenterFriends" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowAddingGameCenterFriends"/>
                            </div>
                          </div>
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowMultiplayerGaming" name="allowMultiplayerGaming" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowMultiplayerGaming"/>
                            </div>
                          </div>
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowUseSafari" name="allowUseSafari" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowusesafari"/>
                            </div>
                          </div>
                          <div class="form-group col-lg-12 iosHeight">&nbsp;&nbsp;iCloud(<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.icloud"/>)</div>
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowBackup" name="allowBackup" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowbackup"/>
                            </div>
                          </div>
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowDocumentSynchronization" name="allowDocumentSynchronization" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowdocumentsynchronization"/>
                            </div>
                          </div>
                          <div class="form-group">
                            <div class="col-lg-12 m-l">
                                <input type="checkbox" id="allowPhotoStream" name="allowPhotoStream" checked="checked"/>&nbsp;&nbsp;<fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.allowphotostream"/>
                            </div>
                          </div>
                      </div>
                      <div class="tab-pane" id="tab13"></div>
					  <c:if test="${menu[42].isshow==-1}">
						<div class="tab-pane" id="tab14">
							<div class="form-group" class="blackRadio">
								<div class="radio i-checks col-lg-8">
								<label class="col-lg-5"> 
									   <input type="radio" name="enableIosNameList" checked="checked" onclick="enableIosName(1)" id="iosName2" value="1"> <i></i>
										<fmt:message key="tiles.views.institution.devicepolicy.indexmodal.enablewhitenamelist" />
									</label>&nbsp;&nbsp;&nbsp;
									<label class="col-lg-5">
									    <input type="radio" name="enableIosNameList" onclick="enableIosName(0)" id="iosName1" value="0"> 
									    <i></i> 
									    <fmt:message key="tiles.views.institution.devicepolicy.indexmodal.enableblacknamelist" />
									</label>
								</div>
							</div>
							<div class="form-group">
								<div class="col-lg-12" class="notion">
									<span id="iosEnableWords">
									<fmt:message key="tiles.views.institution.devicepolicy.indexmodal.enablewords" /></span>
								</div>
							</div>
							<div class="form-group" class="currentNameList">
							    <!-- 选择黑白名单列表的id值 -->
							    <input type="hidden" id="chooseIosEnableIds" name="chooseIosEnableIds" />
								<label class="col-sm-10 control-label" style="text-align: left;">
								<fmt:message key="tiles.views.institution.devicepolicy.indexmodal.currentnamelist" /></label>
								<div class="col-sm-10">
									<div class="input-group">
										<input type="text" class="form-control" disabled="disabled" id="iosNameListText" /> <span
											class="input-group-btn">
											<button class="btn btn-default" type="button" onclick="loadNameList(1)">
												<fmt:message key="tiles.views.institution.devicepolicy.indexmodal.choosenamelist" />
											</button>
										</span>
									</div>
								</div>
							</div>
							<div class="form-group" class="currentNameList">
								<label class="col-sm-10" style="text-algin: left; align: left;">
									<input class="col-sm-1" type="checkbox" id="iosVisitLimit" name="iosVisitLimit" />
								    <fmt:message key="tiles.views.institution.devicepolicy.indexmodal.visitlimit" />
								</label>
								<div class="col-sm-12 marginTop">
									<div class="col-sm-3">
										<fmt:message key="tiles.views.institution.devicepolicy.indexmodal.monday" />：
									</div>
									<div class="col-sm-4">
										<input type="text" class="form-control" id="iosStartTime1" placeholder="00:00-23:59" data-parsley-startdatestr disabled />
									</div>
									<div class="col-sm-1">、</div>
									<div class="col-sm-4">
										<input type="text" class="form-control" id="iosEndTime1" placeholder="00:00-23:59" data-parsley-enddatestr data-parsley-comparedate="#iosStartTime1" disabled />
									</div>
								</div>
								<div class="col-sm-12 marginTop">
									<div class="col-sm-3">
										<fmt:message
											key="tiles.views.institution.devicepolicy.indexmodal.tuesday" />：
									</div>
									<div class="col-sm-4">
										<input type="text" class="form-control" id="iosStartTime2"
											placeholder="00:00-23:59" data-parsley-startdatestr disabled />
									</div>
									<div class="col-sm-1">、</div>
									<div class="col-sm-4">
										<input type="text" class="form-control" id="iosEndTime2"
											placeholder="00:00-23:59" data-parsley-enddatestr
											data-parsley-comparedate="#iosStartTime2" disabled />
									</div>
								</div>
								<div class="col-sm-12 marginTop">
									<div class="col-sm-3">
										<fmt:message
											key="tiles.views.institution.devicepolicy.indexmodal.wednesday" />：
									</div>
									<div class="col-sm-4">
										<input type="text" class="form-control" id="iosStartTime3"
											placeholder="00:00-23:59" data-parsley-startdatestr disabled />
									</div>
									<div class="col-sm-1">、</div>
									<div class="col-sm-4">
										<input type="text" class="form-control" id="iosEndTime3"
											placeholder="00:00-23:59" data-parsley-enddatestr
											data-parsley-comparedate="#iosStartTime3" disabled />
									</div>
								</div>
								<div class="col-sm-12 marginTop">
									<div class="col-sm-3">
										<fmt:message
											key="tiles.views.institution.devicepolicy.indexmodal.thursday" />：
									</div>
									<div class="col-sm-4">
										<input type="text" class="form-control" id="iosStartTime4"
											placeholder="00:00-23:59" data-parsley-startdatestr disabled/>
									</div>
									<div class="col-sm-1">、</div>
									<div class="col-sm-4">
										<input type="text" class="form-control" id="iosEndTime4"
											placeholder="00:00-23:59" data-parsley-enddatestr
											data-parsley-comparedate="#iosStartTime4" disabled/>
									</div>
								</div>
								<div class="col-sm-12 marginTop">
									<div class="col-sm-3">
										<fmt:message
											key="tiles.views.institution.devicepolicy.indexmodal.friday" />：
									</div>
									<div class="col-sm-4">
										<input type="text" class="form-control" id="iosStartTime5"
											placeholder="00:00-23:59" data-parsley-startdatestr disabled />
									</div>
									<div class="col-sm-1">、</div>
									<div class="col-sm-4">
										<input type="text" class="form-control" id="iosEndTime5" placeholder="00:00-23:59" data-parsley-enddatestr data-parsley-comparedate="#iosStartTime5" disabled/>
									</div>
								</div>
								<div class="col-sm-12 marginTop">
									<div class="col-sm-3">
										<fmt:message key="tiles.views.institution.devicepolicy.indexmodal.saturday" />：
									</div>
									<div class="col-sm-4">
										<input type="text" class="form-control" id="iosStartTime6"
											placeholder="00:00-23:59" data-parsley-startdatestr disabled />
									</div>
									<div class="col-sm-1">、</div>
									<div class="col-sm-4">
										<input type="text" class="form-control" id="iosEndTime6"
											placeholder="00:00-23:59" data-parsley-enddatestr
											data-parsley-comparedate="#iosStartTime6" disabled />
									</div>
								</div>
								<div class="col-sm-12 marginTop">
									<div class="col-sm-3">
										<fmt:message key="tiles.views.institution.devicepolicy.indexmodal.sunday" />：
									</div>
									<div class="col-sm-4">
										<input type="text" class="form-control" id="iosStartTime7" placeholder="00:00-23:59" data-parsley-startdatestr disabled/>
									</div>
									<div class="col-sm-1">、</div>
									<div class="col-sm-4">
										<input type="text" class="form-control" id="iosEndTime7" placeholder="00:00-23:59" data-parsley-enddatestr
											data-parsley-comparedate="#iosStartTime7" disabled/>
									</div>
								</div>
							</div>
						 </div>
					     </c:if>
<%-- 						 <div class="tab-pane" id="tab15">
                           <div class="form-group blackRadio">
							  <div class="radio i-checks col-lg-8">
							    <!-- 0.白名单 -->
 								<label class="col-lg-5"><input type="radio" name="enableIosApp" checked="checked" onclick="enableIosAppName(1)" id="appIosName2" value="0">
									<i></i> <fmt:message key="tiles.views.institution.devicepolicy.indexmodal.enablewhitenamelist"/>
								</label> 
							    <!-- 1.黑名单 -->
								<label class="col-lg-5"><input type="radio" name="enableIosApp" onclick="enableIosAppName(0)" id="appIosName1" value="1">
									<i></i> <fmt:message key="tiles.views.institution.devicepolicy.indexmodal.enableblacknamelist"/>
							    </label>&nbsp;&nbsp;&nbsp;
							 </div>
						   </div>
						   <div class="form-group notion">
							  <div class="col-lg-12">
							     <span id="appIosEnableWords"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.enablewords"/></span>
							  </div>
						      <label class="col-sm-10 control-label" style="text-align:left;"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.currentnamelist"/></label>
		                      <div class="col-sm-10">
		                        <div class="input-group">
		                          <input type="text" class="form-control" disabled="disabled" id="appIosNameListText" />
		                          <span class="input-group-btn">
		                          <button class="btn btn-default" type="button" onclick="loadAppNameList(1)"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.choosenamelist"/></button>
		                          </span>
		                        </div>
		                      </div>
						  </div>
                        </div> --%>
                     </div>
                   </div>
                 </section>
             </div>
           </form>
          </div>
          <div class="modal-footer" id="saveIosButton">
		     <a href="javascript:void(0)" onclick="saveIOSPolicy()" class="btn btn-success"><fmt:message key="general.jsp.comfirm.label"/></a>
		     <a href="javascript:void(0)" class="btn" data-dismiss="modal"><fmt:message key="general.jsp.cancel.label"/></a>
         </div>
        </div>
      </div>
    </div>
    
   <!-- Modal start-->
   <div class="modal fade" id="addBookmarkModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
				    <fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.addurlbookmark"/>
				</h4>
			</div>
			<div class="modal-body" style="height:220px;">
			  <!-- URL -->
		      <div class="col-sm-12 form-group">
                 <label class="col-sm-3 control-label">URL&nbsp;<span class="asterisk">*</span></label>
                 <div class="col-sm-8">
                     <input type="text" class="form-control m-b" id="bookmarkIosUrl" name="bookmarkIosUrl" data-parsley-required="true" />
                 </div>
              </div>
              <!-- 名称 -->
              <div class="col-sm-12 form-group">
                 <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.bookmarkname"/>&nbsp;<span class="asterisk">*</span></label>
                   <div class="col-sm-8">
                     <input type="text" class="form-control m-b" id="bookmarkName" name="bookmarkName" data-parsley-required="true" />
                 </div>
              </div>
              <!-- 书签 -->
              <div class="col-sm-12 form-group">
                 <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.bookmark"/>&nbsp;<span class="asterisk">*</span></label>
                   <div class="col-sm-8">
                     <input type="text" class="form-control m-b" id="bookmark" name="bookmark" data-parsley-required="true" />
                   </div>
              </div>
			</div>
			<div class="modal-footer">
			  <a href="javascript:addBookmark()" class="btn btn-success"><fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.add"/></a>
			  <a href="javascript:void(0)" class="btn" data-dismiss="modal"><fmt:message key="general.jsp.cancel.label"/></a>
			</div>
		</div>
	</div>
   </div>  

    <!-- 上传图片-->
	<div class="modal fade" id="uploadIconModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
	     <div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title"><fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.uploadimg"/></h4>
				</div>
				<div class="modal-body" style="height:120px;">
				  <form enctype="multipart/form-data" id="uploadIconForm">
		              <div class="col-sm-12 form-group">
		                  <div class="col-sm-5">
		                    <input type="file" name="imgfile" id="imgfile"/>
		                  </div>
		                  <div class="col-sm-3"><span id="uploadIcon" class="btn btn-xs btn-success">&nbsp;上传&nbsp;</span></div>
		                  <div class="col-sm-5"><fmt:message key="tiles.views.institution.ios.devicepolicy.indexmodal.imgformat"/>:60*60 png jpg bmp</div>
		               </div>
	               </form>
				</div>
			</div>
		</div>
	</div>  
	<!--Modal end-->

    <!-- 删除Ios设备策略功能 -->
	<div class="modal fade" id="delIosPolicyModal" tabindex="-1" role="dialog"
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
				<h3 class="text-danger del-text" id="policyIosMessage"><fmt:message key="tiles.views.institution.devicepolicy.indexmodal.delmessage"/></h3>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<fmt:message key="tip.del.cancel" />
				</button>
				<input type="button" id="policyIosClick" class="btn btn-danger btn-delete-users"
					data-dismiss="modal" onclick="delDevicePolicy()" value="<fmt:message key='tip.del.confirm'/>">
			</div>
			</div>
		</div>
	</div>
	<!--Modal end-->