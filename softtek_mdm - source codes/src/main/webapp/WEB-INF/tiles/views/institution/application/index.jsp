<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.softtek.mdm.util.CommUtil"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="imgCtx" value="<%=CommUtil.DISPLAY_ICON_Path%>"/>
<section class="row">
<div class="blog-post">
	<div class="post-item">
		<section class="wrapper-md" style="padding:0px 0px 0px 0px;padding-left:5px;padding-right:5px;">
			<div class="row">
			  <div class="col-sm-12">
			      <div class="panel panel-default">
                     <div class="panel-body">
	                    <section class="panel-default" style="min-height:300px;border:0px solid silver;">
	                    <header class="panel-heading bg-light" id="deviceTab">
	                      <ul class="nav nav-tabs nav-justified">
	                        <li class="active"><a href="#tab1" data-toggle="tab"><fmt:message key="tiles.views.institution.application.indexmodal.appmanager"/></a></li>
	                        <li><a href="#tab2" data-toggle="tab" onclick="initAppPublish()"><fmt:message key="tiles.views.institution.application.indexmodal.apppublish"/></a></li>
	                        <li><a href="#tab3" data-toggle="tab"><fmt:message key="tiles.views.institution.application.indexmodal.appgrant"/></a></li>
	                        <li><a href="#tab4" data-toggle="tab" onclick="updateCss();"><fmt:message key="tiles.views.institution.application.indexmodal.distribution"/></a></li>
	                      </ul>
	                    </header> 
	                    <div class="panel-body">
	                      <div class="tab-content">
	                        <div class="tab-pane active" id="tab1">	
	                           <div class="table-responsive">
	                           <div class="search-toggle">
						     <a hidfocus="true"><fmt:message key="tiles.institution.comm.expand.search.tip" /></a>
						   </div>
						   <div class="search-mod" style="display: none;">
						   <ul class="search-list">
			   						<li class="search-item">
			   							<label class="search-label" style="width:130px"><fmt:message key="tiles.views.institution.application.index.type"/>：</label>
			   							<div class="Js_dropMod select-box inline-select select-200" style="width:130px;">
											<input type="hidden" class="Js_hiddenVal" id="releaseType" name="releaseType" value="" />
											<span id="Js_curVal" class="Js_curVal"><input type="text" value="<fmt:message key="tiles.views.institution.namelist.index.alltype"/>"></span>
											<ul class="select-list" style="width:130px;">
												<li class="select-item"><a href="javascript:;" rel=""><fmt:message key="tiles.views.institution.namelist.index.alltype"/></a></li>
												<li class="select-item"><a href="javascript:;" rel="0">Android<fmt:message key="tiles.views.institution.application.indexscript.app"/></a></li>
											    <li class="select-item"><a href="javascript:;" rel="1">iOS<fmt:message key="tiles.views.institution.application.indexscript.app"/></a></li>
											</ul>
										</div>
			   						</li>
			   					</ul>
			   					<ul class="search-list">
			   						<li class="search-item ">
			   							<label class="search-label" style="width:130px"><fmt:message key="tiles.views.institution.application.indexmodal.appname"/>：</label>
			   							<input type="text" id="appName" name="appName" class="search-text" style="width:120px;">
			   						</li>
			   					</ul>
			   					<ul class="search-list">
			   						<li class="search-item">
			   							<label class="search-label" style="width:130px"><fmt:message key="tiles.views.institution.application.index.state"/>：</label>
			   							<div class="Js_dropMod select-box inline-select select-200" style="width:130px;">
											<input type="hidden" class="Js_hiddenVal" id="appState" name="appState" value="" />
											<span id="Js_curVal1" class="Js_curVal"><input type="text" value="<fmt:message key="tiles.views.institution.application.index.allstate"/>"></span>
											<ul class="select-list" style="width:130px;">
												<li class="select-item"><a href="javascript:;" rel=""><fmt:message key="tiles.views.institution.application.index.allstate"/></a></li>
												<li class="select-item"><a href="javascript:;" rel="0"><fmt:message key="tiles.views.institution.application.indexscript.offshelves"/></a></li>
												<li class="select-item"><a href="javascript:;" rel="1"><fmt:message key="tiles.views.institution.application.indexscript.onshelves"/></a></li>
											</ul>
										</div>
			   						</li>
			   					</ul>
			   						<div class="type-choice ">
		   								<a class="button-search" type="button" onclick="javascript:searchApplication();"><i class="fa fa-search"></i><span><fmt:message key="tiles.institution.policy.policy.search" /></span></a>
										<a class="button-search" type="button" onclick="javascript:cleanApplication();"><i class="fa fa-trash-o"></i><span><fmt:message key="tiles.institution.policy.policy.clean" /></span></a>
			   						</div>
			   					</ul>
			   			        </div>
								<div class="table-responsive">
								<table id="applicationManager" class="table table-striped b-t b-light">
									<thead>
										<tr>
											<th><fmt:message key="tiles.views.institution.application.index.name"/></th>
											<th><fmt:message key="tiles.views.institution.application.index.type"/></th>
											<th><fmt:message key="tiles.views.institution.application.index.application"/>ID</th>
											<th><fmt:message key="tiles.views.institution.application.index.version"/></th>
											<th><fmt:message key="tiles.views.institution.application.index.state"/></th>
											<th><fmt:message key="general.jsp.operate.lable"/></th>
										</tr>
									</thead>
									<tbody class="">
									</tbody>
								</table>
								</div>
								</div>
	                        </div>
	                        <div class="tab-pane" id="tab2"> 
	                        <form enctype="multipart/form-data" method = "post" style="margin-bottom: 0em;" id="saveApp">
	                        <input type="hidden" id="tokenId" name="token" value="${token}"/>
	                        <!-- 发布类型 -->
		                    <div class="col-sm-12 height">
		                        <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.application.indexmodal.distribtype"/></label>
			                    <div class="col-sm-4">
			                        <select id="releaseType1" class="form-control m-b" onclick="onSelectClick()" >
			                          <option value="0"><fmt:message key="tiles.views.institution.application.indexmodal.androidapp"/></option>
			                          <option value="1">iOS Appstore</option>
			                        </select>
			                    </div>
		                     </div>
	                         <!-- APK文件 -->
	                         <div id="app1" class="display_on">
		                         <div class="col-sm-12 height">
		                           <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.application.indexmodal.apkfile"/></label>
	                               <div class="col-sm-8">
	                               <input type="hidden" id="apkFile" />
										<table style="border:0px;">
										  <tr>
										    <td width="70%" style="border:0px;"><input type="file" name="file" id="file1"/></td>
										    <td width="30%" style="border:0px;"><!-- <input id="uploadFile" type="button" value="&nbsp;&nbsp;上&nbsp;传&nbsp;&nbsp;" class="btn btn-primary btn-xs"/> --></td>
										  </tr>
										</table>
	                               </div>
		                         </div>
		                         <!-- 应用ID-->
		                         <div class="col-sm-12 height">
		                          <spring:url value="/resources/images/app/upload.png" var="appicon"/>
	                               <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.application.indexmodal.appid"/><span class="red">*</span></label>
		                           <div class="col-sm-4">
		                           <input id="appId1" type="text" class="form-control" readonly="readonly" style="background-color:white;"  data-parsley-required="true"  data-parsley-maxlength="50" />
		                           </div>
		                         </div>
		                         <div class="col-sm-12 height">
	                              <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.application.indexmodal.icon"/>(57*57 png)</label>
			                      <div class="col-sm-4">
			                      <input id="icon" name="icon" type="hidden"/>
			                        <img id="iconPath" src ="${appicon}" width="57px" height="57px"/>
			                      </div>
		                         </div>
		                         <div class="col-sm-12 height">
		                         <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.application.indexmodal.appname"/><span class="red">*</span></label>
			                      <div class="col-sm-4">
			            		     <input id="appName1" type="text" class="form-control" readonly="readonly"  style="background-color:white;" data-parsley-required="true"  data-parsley-maxlength="50" />
			                      </div>
		                         </div>
		                         <div class="col-sm-12 height">
		                         <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.application.indexmodal.signaturecertificate"/></label>
			                      <div class="col-sm-4">
			                         <input id="signatureCertificate1" type="text" class="form-control" readonly="readonly"  style="background-color:white;"/>
			                      </div>
		                         </div>
		                         <div class="col-sm-12 height">
		                         <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.application.indexmodal.version"/><span class="red">*</span></label>
			                      <div class="col-sm-4">
			                         <input id="appVersion1" type="text" class="form-control" readonly="readonly" style="background-color:white;" data-parsley-required="true"  data-parsley-maxlength="20" />
			                      </div>
		                         </div>
		                         <div class="col-sm-12 height">
		                         <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.application.indexmodal.author"/></label>
			                      <div class="col-sm-4">
			                         <input id="authorName" type="text" class="form-control"/>
			                      </div>
		                         </div>
		                         <div class="col-sm-12 height">
		                         <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.application.indexmodal.appdesc"/><span class="red">*</span></label>
			                      <div class="col-sm-8">
			                         <textarea rows="7" cols="35" id="appDescription" class="input-sm form-control" data-parsley-required="true"  data-parsley-maxlength="250"></textarea>
			                      </div>
			                      </div>
		                         <div class=" col-sm-12 height">
		                         <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.application.indexmodal.minimumversion"/></label>
			                     <div class="col-sm-4" id="minimumVersionDesc">
			                         <select id="minimumVersion" class="form-control m-b" readonly="readonly" style="background-color:white;" required>
					                      <option value=""><fmt:message key="tiles.views.institution.application.indexmodal.chooseversion"/></option>
					                 </select>
			                     </div>
			                     </div>
			                     <div class="col-sm-12 height">
			                      <label class="col-sm-3"></label>
			                      <div id="fabu"class="col-sm-4"><button onclick="save()" class="btn-sm btn-success"><fmt:message key="general.jsp.publish.label"/></button></div>
			                      </div>
		                      </div>
		                      </form>
		                      <form method="POST" style="margin-bottom: 0em;" id="saveIosApp">
	                          <input type="hidden" id="tokenIosId" name="token" value="${token}"/>
	                          <input type="hidden" id="iosTrackId" name="iosTrackId"/>
	                          <input type="hidden" id="iosFormattedPrice" name="iosFormattedPrice"/>
		                      <div id="app2" class="display_off">
		                         <!-- 应用关键字 -->
		                      	 <div class="col-sm-12 height">
		                             <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.application.indexmodal.appkey"/><span class="red">*</span></label>
				                     <div class="col-sm-4">
				            		    <input id="appKey" type="text" class="form-control"  style="background-color:white;" data-parsley-required="true"  data-parsley-maxlength="50" />
				                     </div>
				                     <div class="col-sm-4">
		                               <span onclick="searchAppList()" class="btn-sm btn-success cursor"><fmt:message key="general.jsp.choose.label"/></span>
		                             </div>
		                         </div>
		                         <!-- 应用ID-->
		                         <div class="col-sm-12 height">
	                               <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.application.indexmodal.appid"/><span class="red">*</span></label>
		                           <div class="col-sm-4">
		                           <input id="iosAppId" type="text" class="form-control" readonly="readonly" style="background-color:white;" data-parsley-required="true"  data-parsley-maxlength="50" />
		                           </div>
		                         </div>
		                         <!-- 图标 -->
		                         <div class="col-sm-12 height">
	                              <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.application.indexmodal.icon"/>(57*57 png)</label>
			                      <div class="col-sm-4">
			                      <input id="icon" name="icon" type="hidden"/>
			                        <img id="iosIconPath" src ="${iconPath}" width="57px" height="57px"/>
			                      </div>
		                         </div>
		                         <!-- 应用名称 -->
		                         <input id="iosFileSizeBytes" type="hidden" class="form-control"/>
		                         <div class="col-sm-12 height">
		                         <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.application.indexmodal.appname"/><span class="red">*</span></label>
			                      <div class="col-sm-4">
			            		     <input id="iosAppName" type="text" class="form-control" readonly="readonly"  style="background-color:white;" data-parsley-required="true"  data-parsley-maxlength="100" />
			                      </div>
		                         </div>
		                         <!-- 版本号 -->
		                         <div class="col-sm-12 height">
		                         <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.application.indexmodal.version"/><span class="red">*</span></label>
			                      <div class="col-sm-4">
			                         <input id="iosAppVersion" type="text" class="form-control" style="background-color:white;" data-parsley-required="true"  data-parsley-maxlength="20" />
			                      </div>
		                         </div>
		                         <!-- 作者 -->
		                         <div class="col-sm-12 height">
		                         <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.application.indexmodal.author"/></label>
			                      <div class="col-sm-4">
			                         <input id="iosAuthorName" type="text" class="form-control"/>
			                      </div>
		                         </div>
		                         <!-- 应用描述 -->
		                         <div class="col-sm-12 height">
		                         <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.application.indexmodal.appdesc"/><span class="red">*</span></label>
			                      <div class="col-sm-8">
			                         <textarea rows="7" cols="35" id="iosAppDescription" class="input-sm form-control" data-parsley-required="true"></textarea>
			                      </div>
			                     </div>
			                     <div class="col-sm-12 height">
			                      <label class="col-sm-3"></label>
			                      <div id="fabu"class="col-sm-4">
			                         <span onclick="saveIosApp()" class="btn-sm btn-success cursor"><fmt:message key="general.jsp.publish.label"/></span></div>
			                      </div>
			                    </div>
                              </form>
	                        </div>
	                        <div class="tab-pane" id="tab3">
	                           <input type="hidden" id="aId" />
	                           <input type="hidden" id="aName" />
	                           <input type="hidden" id="aTag" />
 		                       <div class="col-sm-12 padding">
		                           <div class="col-sm-2 border menuHeight">
			                          <div class="col-sm-12 padding margin_bottom">
	                                      <div class="col-sm-11 padding search_margin_top">
	                                         <input type="text" id="searchApp" onKeyUp="searchAppliction()" class="search_app" style="width:100%;"/>
	                                      </div>
	                                      <div class="col-sm-1 padding search_margin_top">
		                                       <spring:url value="/resources/images/search.png" var="search"></spring:url>
		                                       <img src="${search}" width="22" height="22"/>
	                                      </div>
			                          </div>
		                              <ul class="list_type" id="appUl">
			                              <c:forEach items="${appList}" var="app">
			                                 <li class="cursor" <c:if test="${softtek_manager.auth > 0 || softtek_manager.user==null}"> onclick="onclickApp(${app.id})</c:if>">
			                                 <c:if test="${app.releaseType==0}">
			                       -               <img src="${imgCtx}application/${app.iconPath}" height="40px" width="40px"/>
			                                 </c:if>
			                                 <c:if test="${app.releaseType==1}">
			                                      <img src="${app.iconPath}" height="40px" width="40px"/>
			                                 </c:if>
			                                 &nbsp;${app.appName}(<c:if test="${app.releaseType==0}">Android</c:if><c:if test="${app.releaseType==1}">iOS</c:if> <fmt:message key="tiles.views.institution.application.indexmodal.application"/>)</li>
			                              </c:forEach>
		                              </ul> 
		                           </div>
		                           <div class="col-sm-10 padding not_auth_margin_top">
		                              <div class="col-sm-12">
			                              <div class="col-sm-4 border_title_line"><fmt:message key="tiles.views.institution.application.indexmodal.notauthdepart"/></div>
			                              <div class="col-sm-4"></div>
			                              <div class="col-sm-4 border_title_line"><fmt:message key="tiles.views.institution.application.indexmodal.authdepart"/></div>
		                              </div>
		                              <div class="col-sm-12">
		                                  <!-- 未授权部门 -->
			                              <div class="col-sm-5 border app_height" id="tree1">
			                              </div>
			                              <div class="col-sm-2">
			                                <c:if test="${softtek_manager.auth > 0 || softtek_manager.user==null}">
			                                 <div class="auth_margin cursor text-primary" onclick="grandAppAuth(0)">
		                                     <i class="fa fa-arrow-right"></i>&nbsp;<fmt:message key="tiles.views.institution.application.indexmodal.grantapplication"/>
		                                     </div>
		                                     </c:if>
			                              </div>
			                              <!-- 已授权部门 -->
			                              <div class="col-sm-5 border app_height">
			                                <div class="col-sm-12 padding" style="margin-top:5px;">
			                                  <div class="col-sm-4 border divHeight">
			                                  	<fmt:message key="tiles.views.institution.application.indexmodal.depart"/>
			                                  </div>
			                                  <div class="col-sm-4 border_left divHeight"><fmt:message key="tiles.views.institution.application.indexmodal.auth"/></div>
			                                  <div class="col-sm-4 border_left divHeight"><fmt:message key="tiles.views.institution.application.indexmodal.operate"/></div>
			                                  <div class="col-sm-12 padding" id="departAuth">
			                                  </div>
			                                </div>
			                             </div>
		                              </div> 
		                              <div class="col-sm-12 not_auth_margin_top">
			                              <div class="col-sm-4 border_title_line"><fmt:message key="tiles.views.institution.application.indexmodal.notauthvirtual"/></div>
			                              <div class="col-sm-4"></div>
			                              <div class="col-sm-4 border_title_line"><fmt:message key="tiles.views.institution.application.indexmodal.authvirtual"/></div>
		                                  <div class="col-sm-12 padding" id="departAuth">
		                                    
			                              </div>
		                              </div>
		                              <div class="col-sm-12">
		                                 <!-- 未授权 -->
			                             <div class="col-sm-5 border height app_height">
			                              <div class="col-sm-12" id="notAuthVirtualGourp" style="margin-top:10px;">
			                              
			                              </div>
			                             </div>
			                             <div class="col-sm-2">
			                             <c:if test="${softtek_manager.auth > 0 || softtek_manager.user==null}">
			                                <div class="auth_margin cursor text-primary" onclick="grandAppAuth(1)">
		                                       <i class="fa fa-arrow-right"></i>&nbsp;<fmt:message key="tiles.views.institution.application.indexmodal.grantapplication"/>
		                                    </div>
		                                    </c:if>
			                             </div>
			                             <div class="col-sm-5 border height app_height">
			                             <c:if test="${softtek_manager.auth > 0 || softtek_manager.user==null}">
			                                <div class="col-sm-12 padding" style="margin-top:5px;">
			                                  <div class="col-sm-4 border divHeight"><fmt:message key="tiles.views.institution.application.indexmodal.depart"/></div>
			                                  <div class="col-sm-4 border_left divHeight"><fmt:message key="tiles.views.institution.application.indexmodal.auth"/></div>
			                                  <div class="col-sm-4 border_left divHeight"><fmt:message key="tiles.views.institution.application.indexmodal.operate"/></div>
			                                </div>
			                             </c:if>
			                             <div class="col-sm-12 padding" id="virtualAuth">
			                                
			                             </div>
			                            </div>
		                             </div>
		                          </div>
		                       </div> 
	                        </div>
	                        <div class="tab-pane" id="tab4">
							<div class="table-responsive">
						   <div class="search-toggle">
						     <a hidfocus="true"><fmt:message key="tiles.institution.comm.expand.search.tip" /></a>
						   </div>
						   <div class="search-mod" style="display:none;">
						   <ul class="search-list" >
			   						<li class="search-item">
			   							<label class="search-label" style="width:130px"><fmt:message key="tiles.views.institution.application.index.type"/>：</label>
			   							<div class="Js_dropMod select-box inline-select select-200" style="width:130px;">
											<input type="hidden" class="Js_hiddenVal" id="authReleaseType" name="authReleaseType" value="" />
											<span id="Js_curVal2" class="Js_curVal"><input type="text" value="<fmt:message key="tiles.views.institution.namelist.index.alltype"/>"></span>
											<ul class="select-list">
												<li class="select-item"><a href="javascript:;" rel=""><fmt:message key="tiles.views.institution.namelist.index.alltype"/></a></li>
												<li class="select-item"><a href="javascript:;" rel="0">Android<fmt:message key="tiles.views.institution.application.indexscript.app"/></a></li>
												<li class="select-item"><a href="javascript:;" rel="1">iOS<fmt:message key="tiles.views.institution.application.indexscript.app"/></a></li>
											</ul>
										</div>
			   						</li>
			   					</ul>
			   					<ul class="search-list">
			   						<li class="search-item ">
			   							<label class="search-label" style="width:130px"><fmt:message key="tiles.views.institution.application.indexmodal.appname"/>：</label>
			   							<input type="text" id="authAppName" name="authAppName" class="search-text" style="width:120px;">
			   						</li>
			   					</ul>
								<div class="type-choice">
									<a class="button-search" type="button" onclick="javascript:searchAuthApplication();"><i class="fa fa-search"></i><span><fmt:message key="tiles.institution.policy.policy.search" /></span></a>
									<a class="button-search" type="button" onclick="javascript:cleanAuthApplication();"><i class="fa fa-trash-o"></i><span><fmt:message key="tiles.institution.policy.policy.clean" /></span></a>
								</div>
			   			</div>
									<%-- <div class="col-sm-12 search_part">
										<div class="col-sm-1 searchName"><fmt:message key="tiles.views.institution.application.index.type"/>：</div>
										<div class="col-sm-2 searchName_val">
											<select id="authReleaseType" class="input-sm form-control">
												<option value=""><fmt:message key="tiles.views.institution.namelist.index.alltype"/></option>
												<option value="0">Android<fmt:message key="tiles.views.institution.application.indexscript.app"/></option>
											</select>
										</div>
										<div class="col-sm-1 searchName"><fmt:message key="tiles.views.institution.application.indexmodal.appname"/>：</div>
										<div class="col-sm-2 searchName_val">
											<input id="authAppName" type="text" class="input-sm form-control" />
										</div>
										<div class="col-sm-2 search_button">
											<button class="btn btn-sm btn-default search_icon" type="button" onclick="searchAuthApplication()"><fmt:message key="general.jsp.search.label"/></button>
											<button class="btn btn-sm btn-default reset_icon" type="button" onclick="cleanAuthApplication()"><fmt:message key="general.jsp.clean.label"/></button>
										</div>
									</div> --%>
										<table id="applicationDistribute" class="table table-striped b-t b-light">
											<thead>
												<tr>
													<th><fmt:message key="tiles.views.institution.application.index.name" /></th>
													<th><fmt:message key="tiles.views.institution.application.index.type" /></th>
													<th><fmt:message key="tiles.views.institution.application.index.version" /></th>
													<th><fmt:message key="tiles.views.institution.application.index.count" /></th>
													<th><fmt:message key="tiles.views.institution.application.index.notAuthCount" /></th>
													<th><fmt:message key="general.jsp.operate.lable" /></th>
												</tr>
											</thead>
											<tbody class="">
											</tbody>
										</table>
								</div>
							</div>
	                      </div>
	                      </div>
	                   </section>
					</div>
                 </div>
               </div>
             </div>  
            </section>
            </div>
          </div>
		</section>