<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <!-- 加载黑名单或者白名单列表数据 -->
    <div class="modal fade" id="editApplication" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
     <!-- 选择黑白名单列表的id值 -->
     <input type="hidden" id="chooseEnableIds"/>
     <input type="hidden" id="chooseDepartIds"/>
	    <div class="modal-dialog modal-lg" role="document">
	      <div class="modal-content">
	        <div class="modal-header b-b-l-none"><fmt:message key="tiles.views.institution.application.indexmodal.modifyapp"/>
	          <button type="button" class="close"  onclick="closeApplication()">
	            <span aria-hidden="true">&times;</span>
	          </button>
	        </div>
	        <div class="modal-body" style="min-height: 300px;overflow: scroll;">
	         <form method ="post" style="margin-bottom: 0em;" id="editAppForm">
	          <div class="row" id="insert">
	              <input type="hidden" id="id" name="id"/>
	              <div class="col-sm-12 height">
	                  <div class="col-lg-2 textAlign"><fmt:message key="tiles.views.institution.application.indexmodal.appname"/><span class="red">*</span></div><div class="col-lg-8" id="editAppName"></div>
	              </div>
	              <div class="col-sm-12 height">
	                  <div class="col-lg-2 textAlign"><fmt:message key="tiles.views.institution.application.indexmodal.icon"/>&nbsp;</div><div class="col-lg-8">
	                    <img src="" width="80px" height="80px" id="editIcon">
	                  </div>
	              </div>
	              <div class="col-sm-12 height">
	                  <div class="col-lg-2 textAlign"><fmt:message key="tiles.views.institution.application.indexmodal.appdesc"/><span class="red">*</span></div><div class="col-lg-8">
	                    <textarea rows="7" cols="60" id="editAppDescription" data-parsley-required="true"></textarea>
	                  </div>
	              </div>
	            </div>
	            </form>
	          </div>
	          <div class="modal-footer">
				<a href="javascript:void(0)" onclick="editSaveApplication()" class="btn btn-success"><fmt:message key="general.jsp.comfirm.label"/></a>
				<a href="javascript:closeApplication()" class="btn btn-default"><fmt:message key="general.jsp.cancel.label"/></a>
             </div>
	        </div>
	   </div>
    </div>
    
    <!--查看应用发布-->
    <div class="modal fade" id="findApplication" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
     <!-- 选择黑白名单列表的id值 -->
     <input type="hidden" id="chooseEnableIds"/>
     <input type="hidden" id="chooseDepartIds"/>
	    <div class="modal-dialog modal-lg" role="document">
	      <div class="modal-content">
	        <div class="modal-header b-b-l-none"><fmt:message key="tiles.views.institution.application.indexmodal.appdetail"/>
	          <button type="button" class="close"  onclick="closeFindApp()">
	            <span aria-hidden="true">&times;</span>
	          </button>
	        </div>
	        <div class="modal-body" style="min-height: 300px;overflow: scroll;">
	           <div class="row" id="insert">
	              <!-- 图标 -->
	              <div class="col-sm-12 height">
	                  <div class="col-lg-2 textAlign"><fmt:message key="tiles.views.institution.application.indexmodal.icon"/></div>
	                  <div class="col-lg-8"><img src="" width="80px" height="80px" id="findIcon"></div>
	              </div>
	              <!-- 文件 -->
	              <div class="col-sm-12 height">
	                  <div class="col-lg-2 textAlign"><fmt:message key="tiles.views.institution.application.indexmodal.file"/></div>
	                  <div class="col-lg-8"><a target="_blank" id="findApkFile"><fmt:message key="tiles.views.institution.application.indexmodal.download"/></a></div>
	              </div>
	              <!-- 应用名称 -->
	              <div class="col-sm-12 height">
	                  <div class="col-lg-2 textAlign"><fmt:message key="tiles.views.institution.application.indexmodal.appname"/></div>
	                  <div class="col-lg-8" id="findAppName"></div>
	              </div>
	              <!-- 最新版本 -->
	              <div class="col-sm-12 height">
	                  <div class="col-lg-2 textAlign"><fmt:message key="tiles.views.institution.application.indexmodal.newestversion"/></div>
	                  <div class="col-lg-8" id="findAppVersion"></div>
	              </div>
	              <!-- 应用ID -->
	              <div class="col-sm-12 height">
	                  <div class="col-lg-2 textAlign"><fmt:message key="tiles.views.institution.application.indexmodal.appid"/></div>
	                  <div class="col-lg-8" id="findAppId"></div>
	              </div>
	              <!-- 应用类型 -->
	              <div class="col-sm-12 height">
	                  <div class="col-lg-2 textAlign"><fmt:message key="tiles.views.institution.application.indexmodal.apptype"/></div>
	                  <div class="col-lg-8" id="findType"></div>
	              </div>
	              <div class="col-sm-12 height">
	                  <div class="col-lg-2 textAlign"><fmt:message key="tiles.views.institution.application.indexmodal.minimumversion"/></div>
	                  <div class="col-lg-8" id="findMinimumVersion"></div>
	              </div>
	              <div class="col-sm-12 height">
	                  <div class="col-lg-2 textAlign"><fmt:message key="tiles.views.institution.application.indexmodal.appdesc"/></div>
	                  <div class="col-lg-8" id="findAppDescription"></div>
	              </div>
	            </div>
	          </div>
	          <div class="modal-footer">
				<a href="javascript:closeFindApp()" class="btn btn-default"><fmt:message key="general.jsp.cancel.label"/></a>
             </div>
	        </div>
	   </div>
    </div>
    
    <!-- 应用级授权 -->
    <div class="modal fade" id="grandAppAuth" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
	    <div class="modal-dialog modal-lg" role="document" style="width:400px;">
	      <div class="modal-content">
	        <div class="modal-header b-b-l-none"><fmt:message key="tiles.views.institution.application.indexmodal.grantapplication"/>
	          <button type="button" class="close"  onclick="closeGrantAuth()">
	            <span aria-hidden="true">&times;</span>
	          </button>
	        </div>
	        <div class="modal-body">
	           <div class="row" id="insert">
	              <div class="col-sm-12 height">
	                  <div class="col-lg-12" style="font-size:20px;color:#1C5076;"><fmt:message key="tiles.views.institution.application.indexscript.appinstall"/>
	                  </div>
	              </div>
	            </div>
	            <div class="row" id="insert">
	              <div class="col-sm-12 height">
	                  &nbsp;&nbsp;&nbsp;<input type="radio" name="install" checked="checked">&nbsp;<fmt:message key="tiles.views.institution.application.indexscript.allowinstall"/>&nbsp;&nbsp;
	                  &nbsp;&nbsp;&nbsp;<%-- <input type="radio" name="install">&nbsp;<fmt:message key="tiles.views.institution.application.indexscript.disableinstall"/> --%>
	              </div>
	            </div>
	          </div>
	          <div class="modal-footer">
				<a href="javascript:void(0)" onclick="saveGrantAppAuth()" class="btn btn-success"><fmt:message key="general.jsp.comfirm.label"/></a>
				<a href="javascript:closeGrantAuth()" class="btn btn-default"><fmt:message key="general.jsp.cancel.label"/></a>
             </div>
	        </div>
	   </div>
    </div>
    
    <!-- 加载设备 -->
    <div class="modal fade" id="loadDeviceInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
	    <div class="modal-dialog modal-lg" role="document">
	      <div class="modal-content">
	        <div class="modal-header b-b-l-none"><fmt:message key="tiles.views.institution.application.indexscript.device.info"/>
	          <button type="button" class="close"  onclick="closeDevice()">
	            <span aria-hidden="true">&times;</span>
	          </button>
	        </div>
	        <div class="modal-body" style="height:230px;">
				<div class="table-responsive">
					<div class="col-sm-12">
						<table class="table table-striped b-t b-light" id="loadDeviceTable">
							<thead>
								<tr>
								   <th><fmt:message key="tiles.views.institution.application.indexscript.device.username"/></th>
								   <th><fmt:message key="tiles.views.institution.application.indexscript.device.name"/></th>
								   <th><fmt:message key="tiles.views.institution.application.indexscript.device.type"/></th>
								   <th><fmt:message key="tiles.views.institution.application.indexscript.device.sn"/></th>
								   <th><fmt:message key="tiles.views.institution.application.indexscript.esn.imei"/></th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
			    </div>
	        </div>
	        <div class="modal-footer">
				<a href="javascript:closeDevice()" class="btn btn-default"><fmt:message key="general.jsp.cancel.label"/></a>
            </div>
	      </div>
	   </div>
    </div>
    
    <!-- 发布新版本 -->
    <div class="modal fade" id="publishNewVersion" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
	    <div class="modal-dialog modal-lg" role="document">
	      <div class="modal-content">
	        <div class="modal-header b-b-l-none"><fmt:message key="tiles.views.institution.application.indexscript.device.info"/>
	          <button type="button" class="close"  onclick="closeNewVersion()">
	            <span aria-hidden="true">&times;</span>
	          </button>
	        </div>
	        <div class="modal-body" style="height:230px;">
	         <form enctype="multipart/form-data" method="post" style="margin-bottom: 0em;" id="publishVersionForm">
	           <input type="hidden" id="pulishVersionId"/>
	           <input type="hidden" id="appName2"/>
               <!-- APK文件 -->
               <div class="col-sm-12 height">
                   <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.application.indexmodal.apkfile"/></label>
                    <div class="col-sm-8">
                    <input type="hidden" id="apkFile2" />
                   
					<table style="border:0px;">
					  <tr>
					    <td width="70%" style="border:0px;"><input type="file" name="file" id="file2"/></td>
					    <td width="30%" style="border:0px;"><!-- <input id="uploadFile" type="button" value="&nbsp;&nbsp;上&nbsp;传&nbsp;&nbsp;" class="btn btn-primary btn-xs"/> --></td>
					  </tr>
					</table>
		          
                </div>
              </div>
    	      <div class="col-sm-12 height">
                 <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.application.indexmodal.icon"/>(57*57 png)</label>
                 <div class="col-sm-4">
                  <input id="icon2" name="icon" type="hidden"/>
                  <spring:url value="/resources/images/app/upload.png" var="appicon"/>
                  <img id="iconPath2" src ="${appicon}" width="57px" height="57px"/>
                 </div>
              </div>
   	          <div class="col-sm-12 height">
                 <label class="col-sm-3 control-label"><fmt:message key="tiles.views.institution.application.indexmodal.version"/><span class="red">*</span></label>
	             <div class="col-sm-4">
	                 <input id="appVersion2" type="text" class="form-control" readonly="readonly" style="background-color:white;" data-parsley-required="true"  data-parsley-maxlength="20" />
	             </div>
              </div>
                </form>
	        </div>
	        <div class="modal-footer">
	        	<a href="javascript:void(0)" onclick="saveNewVersion()" class="btn btn-success"><fmt:message key="general.jsp.publish.label"/></a>
				<a href="javascript:closeNewVersion()" class="btn btn-default"><fmt:message key="general.jsp.cancel.label"/></a>
            </div>
	      </div> 
	   </div>
    </div>
    
    <!-- 从appstore拉取的应用列表 -->
    <div class="modal fade" id="appListInAppstore" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
	    <div class="modal-dialog modal-lg" role="document">
	        <div class="modal-content">
	        <input type="hidden" id="hiddenBundleId"/>
	        <input type="hidden" id="hiddenTrackName"/>
	        <input type="hidden" id="hiddenArtworkUrl100"/>
	        <input type="hidden" id="hiddenPrimaryGenreName"/>
	        <input type="hidden" id="hiddenPrice"/>
	        <input type="hidden" id="hiddenArtistName"/>
	        <input type="hidden" id="hiddenFileSizeBytes"/>
	        <input type="hidden" id="hiddenVersion"/>
	        <input type="hidden" id="hiddenDescription"/>
	        <input type="hidden" id="hiddenTrackId"/>
	        <input type="hidden" id="hiddenFormattedPrice"/>
	        <div class="modal-header b-b-l-none"><fmt:message key="tiles.views.institution.application.indexmodal.appstore.info"/>
	          <button type="button" class="close"  onclick="closeAppListInAppstore()">
	            <span aria-hidden="true">&times;</span>
	          </button>
	        </div>
	        <div class="modal-body" style="height:800px;overflow:auto;">
	          <div style="width:800px;" id="showAppKeywords">
	          
	          </div>
	        </div>
	        <div class="modal-footer">
	           <a href="javascript:chooseApp()" class="btn btn-primary"><fmt:message key="general.jsp.choose.label"/></a>
			   <a href="javascript:closeAppListInAppstore()" class="btn btn-default"><fmt:message key="general.jsp.cancel.label"/></a>
            </div>
	      </div> 
	   </div>
	   