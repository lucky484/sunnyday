 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
  <div class="modal fade" id="addStrategyModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
          <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
              <div class="modal-header b-b-l-none">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body" style="max-height: 600px;overflow: scroll;">
                <div class="row" id="insert">
                  <div class="col-sm-12">
                    <section class="panel panel-default">
                      <header class="panel-heading font-bold"><fmt:message key="tiles.institution.policy.add.policy" /></header>
                      <div class="panel-body">
                        <form class="bs-example form-horizontal" id="insertPolicy" data-parsley-validate>
                          <h5>
                            <span class="badge bg-primary"><fmt:message key="tiles.institution.policy.base.info" /></span>
                          </h5>
                          <div class="form-group">
                            <label class="col-lg-2 control-label"><fmt:message key="tiles.institution.policy.policy.name" /><span style="color:red">*</span></label>
                            <div class="col-lg-10">
                              <input type="text" class="form-control" id="policyName" data-parsley-required="true" data-parsley-trigger="focusout" data-parsley-maxlength="50" data-parsley-maxlength-message="<fmt:message key="parsley.department.name.length"/>" data-parsley-remote-validator="checkname"
                            data-parsley-remote  data-parsley-remote-message="<fmt:message key="parsley.policy.name.exists"/>" autocomplete="off" /></div>
                          </div>
                          <div class="form-group">
                            <label class="col-lg-2 control-label"><fmt:message key="tiles.institution.policy.default.policy" /></label>
                            <div class="col-lg-10">
                              <div class="checkbox i-checks">
                                <label>
                                  <input type="checkbox" id="isDefault">
                                  <i></i>
                                </label>
                              </div>
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-lg-2 control-label"><fmt:message key="tiles.institution.policy.set.policy" /></label>
                            <div class="col-lg-10">
                              <div id="tree" style="min-height: 100px;max-height: 200px;overflow: scroll;">
                              <ul id="insertTree"></ul>
                              </div>
                            </div>
                            <h5>
                              <span class="badge bg-primary limits"><fmt:message key="tiles.institution.policy.terminal.limit" /></span>
                            </h5>
                            <div class="form-group worktime">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks pull-left">
                                  <label> 
                                    <input type="checkbox" id="visitLimit">
                                    <i></i>
                                   <fmt:message key="tiles.institution.policy.visit.time" />
                                  </label>
                             </div>
                              	<input type="text" id="startTime" name="startTime" data-parsley-trigger="change" data-parsley-required="true" data-parsley-checkstartdate="#endTime" autocomplete="off" value="08:30" onblur="compareStartTime(0);" style="width: 100px;display:inline;">
                                ~
                                  <input type="text" id="endTime" name="endTime"  data-parsley-trigger="change" data-parsley-required="true" value="17:30" data-parsley-checkdate="#startTime" autocomplete="off" onblur="compareEndTime(0);" style="width: 100px;display:inline;">
                                </div>
                            </div>
                            <div class="form-group wifi">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks pull-left m-r">
                                  <label>
                                    <input type="checkbox" id="wifi" name="wifi" />
                                    <i></i>
                                  <fmt:message key="tiles.institution.policy.visit.wifi" />
                                  </label>
                                </div>
                              </div>
                            </div>
                            <div class="form-group limit_login">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks ">
                                  <label>
                                    <input type="checkbox" id="loginLimit" name="loginMit" />
                                    <i></i>
                                  <fmt:message key="tiles.institution.policy.forbid.login" />
                                  </label>
                                </div>
                              </div>
                            </div>
                            <div class="form-group auto_login">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks ">
                                  <label>
                                    <input type="checkbox" id="autoLoginLimit" name="autoLoginLimit" />
                                    <i></i>
                                    <fmt:message key="tiles.institution.policy.forbid.auto.login" />
                                  </label>
                                </div>
                              </div>
                            </div>
                            <div class="form-group allow_errow">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks pull-left">
                                  <label>
                                    <input type="checkbox" id="errorLimit" name="errorLimit" checked />
                                    <i></i>
                                    <fmt:message key="tiles.institution.policy.allow.error" />
                                  </label>
                                </div>
                                <input type="text" id="isLock" name="isLock" data-parsley-trigger="focusout" data-parsley-required="true" value="3" data-parsley-type="digits" data-parsley-type-message="<fmt:message key="parsley.policy.password.digits"/>" style="width: 60px;display:inline;">
                             <fmt:message key="tiles.institution.policy.error.lock" />
                                <input type="text" id="lockTime" name="lockTime" data-parsley-trigger="focusout" data-parsley-required="true" value="10" data-parsley-type="digits" data-parsley-type-message="<fmt:message key="parsley.policy.password.digits"/>" style="width: 60px;display:inline;"><fmt:message key="tiles.institution.policy.error.minutes" /></div>
                            </div>
                            <div class="form-group login_count">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks pull-left">
                                  <label>
                                    <input type="checkbox" id="device" name="device" checked />
                                    <i></i>
                                   <fmt:message key="tiles.institution.policy.user.registration" />
                                  </label>
                                </div>
                                <input type="text" id="deviceCount" name="deviceCount" value="2" data-parsley-required="true" data-parsley-trigger="focusout" data-parsley-type="digits" data-parsley-type-message="<fmt:message key="parsley.policy.password.digits"/>" style="width: 60px;display:inline;"><fmt:message key="tiles.institution.policy.device.count" /></div>
                            </div>
                            <div class="form-group fig_pass">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks ">
                                  <label>
                                    <input type="checkbox" id="password" name="password" />
                                    <i></i>
                                   <fmt:message key="tiles.institution.policy.set.password" />
                                  </label>
                                </div>
                              </div>
                            </div>
                            </div>
                            <input type="button" class="btn btn-md btn-primary pull-right" id="addPolicy" value="<fmt:message key="tiles.institution.policy.insert" />" ></form>
                        </div>
                      </section>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!-- 查询页面的模态框 -->
           <div class="modal fade" id="queryDetail" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
          <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
              <div class="modal-header b-b-l-none">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body" style="max-height: 600px;overflow: scroll;">
                <div class="row" id="insert">
                  <div class="col-sm-12">
                    <section class="panel panel-default">
                      <header class="panel-heading font-bold"><fmt:message key="tiles.institution.policy.policy.detail" /></header>
                      <div class="panel-body">
                        <form class="bs-example form-horizontal" >
                          <h5>
                            <span class="badge bg-primary"><fmt:message key="tiles.institution.policy.base.info" /></span>
                          </h5>
                          <div class="form-group">
                            <label class="col-lg-2 control-label"><fmt:message key="tiles.institution.policy.policy.name" /></label>
                            <div class="col-lg-10">
                              <input type="text" class="form-control" id="policyNameDetail" readonly></div>
                          </div>
                          <div class="form-group">
                            <label class="col-lg-2 control-label"><fmt:message key="tiles.institution.policy.default.policy" /></label>
                            <div class="col-lg-10">
                              <div class="checkbox i-checks">
                                <label>
                                  <input type="checkbox" id="isDefaultDetail" disabled="disabled">
                                  <i></i>
                                </label>
                              </div>
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-lg-2 control-label"><fmt:message key="tiles.institution.policy.set.policy" /></label>
                            <div class="col-lg-10">
                             <div class="table-responsive">
                            <table class="table table-striped b-t b-light" id="departmentInfo">
                              <thead>
                                <tr>
                                  <th><fmt:message key="tiles.institution.policy.detail.department.name" /></th>
                                  <th><fmt:message key="tiles.institution.policy.attribution.department" /></th>
                                </tr>
                              </thead>
                              <tbody>
                              </tbody>
                            </table>
                          </div>
                            </div>
                            <h5>
                              <span class="badge bg-primary limits"><fmt:message key="tiles.institution.policy.terminal.limit" /></span>
                            </h5>
                            <div class="form-group worktime">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks pull-left">
                                  <label> 
                                    <input type="checkbox" id="visitLimitDetail" disabled="disabled">
                                    <i></i>
                                   <fmt:message key="tiles.institution.policy.visit.time" />
                                  </label>
                                </div>
                             <input type="text" id="startTimeDetail" name="startTimeDetail" disabled="disabled" readonly data-date-format="hh:ii" style="width: 100px;display:inline;">
                              ~
                                <input type="text" id="endTimeDetail" name="endTimeDetail" disabled="disabled" readonly data-date-format="hh:ii" style="width: 100px;display:inline;"></div>
                            </div>
                            <div class="form-group wifi">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks pull-left m-r">
                                  <label>
                                    <input type="checkbox" id="wifiDetail" name="wifiDetail" disabled="disabled"/>
                                    <i></i>
                                    <fmt:message key="tiles.institution.policy.visit.wifi" />
                                  </label>
                                </div>
                              </div>
                            </div>
                            <div class="form-group limit_login">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks ">
                                  <label>
                                    <input type="checkbox" id="loginLimitDetail" name="loginLimitDetail" disabled="disabled" />
                                    <i></i>
                                 <fmt:message key="tiles.institution.policy.forbid.login" />
                                  </label>
                                </div>
                              </div>
                            </div>
                            <div class="form-group auto_login">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks ">
                                  <label>
                                    <input type="checkbox" id="autoLoginLimitDetail" name="autoLoginLimitDetail" disabled="disabled"/>
                                    <i></i>
                                     <fmt:message key="tiles.institution.policy.forbid.auto.login" />
                                  </label>
                                </div>
                              </div>
                            </div>
                            <div class="form-group allow_errow">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks pull-left">
                                  <label>
                                    <input type="checkbox" id="errorLimitDetail" name="errorLimitDetail" disabled="disabled"/>
                                    <i></i>
                                     <fmt:message key="tiles.institution.policy.allow.error" />
                                  </label>
                                </div>
                                <input type="text" id="isLockDetail" name="isLockDetail" disabled style="width: 60px;display:inline;">
                              <fmt:message key="tiles.institution.policy.error.lock" />
                                <input type="text" id="lockTimeDetail" name="lockTimeDetail" disabled style="width: 60px;display:inline;"><fmt:message key="tiles.institution.policy.error.minutes" /></div>
                            </div>
                            <div class="form-group login_count">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks pull-left">
                                  <label>
                                    <input type="checkbox" id="deviceDetail" name="deviceDetail" disabled="disabled"/>
                                    <i></i>
                                    <fmt:message key="tiles.institution.policy.user.registration" />
                                  </label>
                                </div>
                                <input type="text" id="deviceCountDetail" name="deviceCountDetail" disabled style="width: 60px;display:inline;"><fmt:message key="tiles.institution.policy.device.count" /></div>
                            </div>
                            <div class="form-group fig_pass">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks ">
                                  <label>
                                    <input type="checkbox" id="passwordDetail" name="passwordDetail" disabled="disabled"/>
                                    <i></i>
                                    <fmt:message key="tiles.institution.policy.set.password" />
                                  </label>
                                </div>
                              </div>
                            </div>
                            </div>
                            </form>
                        </div>
                      </section>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          
          <!-- 修改页面的模态框 -->
             <div class="modal fade" id="updatePolicy" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
          <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
              <div class="modal-header b-b-l-none">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body" style="max-height: 600px;overflow: scroll;">
                <div class="row" id="insert">
                  <div class="col-sm-12">
                    <section class="panel panel-default">
                      <header class="panel-heading font-bold"><fmt:message key="tiles.institution.policy.policy.update" /></header>
                      <div class="panel-body">
                        <form class="bs-example form-horizontal" id="modifyPolicy" data-parsley-validate>
                          <h5>
                            <span class="badge bg-primary"><fmt:message key="tiles.institution.policy.base.info" /></span>
                          </h5>
                          <div class="form-group">
                            <label class="col-lg-2 control-label"><fmt:message key="tiles.institution.policy.policy.name" /><span style="color:red">*</span></label>
                            <div class="col-lg-10">
                              <input type="text" class="form-control" id="policyNameUpdate" name="policyNameUpdate" data-parsley-required="true" data-parsley-maxlength="10" data-parsley-trigger="focusout" data-parsley-maxlength-message="<fmt:message key="parsley.department.name.length"/>" data-parsley-remote data-parsley-remote-validator="checknameUpdate"
                              data-parsley-remote-message="<fmt:message key="parsley.policy.name.exists"/>" /></div>
                              <input type="hidden" id="id" name="id" />
                              <input type="hidden" id="name_hiden" name="name_hiden">
                          </div>
                          <div class="form-group">
                            <label class="col-lg-2 control-label"><fmt:message key="tiles.institution.policy.default.policy" /></label>
                            <div class="col-lg-10">
                              <div class="checkbox i-checks">
                                <label>
                                  <input type="checkbox" id="isDefaultUpdate" name="isDefaultUpdate" />
                                  <i></i>
                                </label>
                              </div>
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-lg-2 control-label"><fmt:message key="tiles.institution.policy.set.policy" /></label>
                            <div class="col-lg-10">
                              <div id="tree" style="min-height: 100px;max-height: 200px;overflow: scroll;">
                              <ul id="updateTree"></ul>
                              </div>
                            </div>
                            <h5>
                              <span class="badge bg-primary limits"><fmt:message key="tiles.institution.policy.terminal.limit" /></span>
                            </h5>
                            <div class="form-group worktime">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks pull-left">
                                  <label> 
                                    <input type="checkbox" id="visitLimitUpdate" name="visitLimitUpdate">
                                    <i></i>
                                   <fmt:message key="tiles.institution.policy.visit.time" />
                                  </label>
                                </div>
                             <input type="text" id="startTimeUpdate" name="startTimeUpdate" data-parsley-required="true" disabled data-parsley-trigger="focusout" onblur="compareStartTime(1)" data-parsley-checkstartdate="#endTimeUpdate" style="width: 100px;display:inline;">
                                ~
                                <input type="text" id="endTimeUpdate" name="endTimeUpdate" data-parsley-required="true" disabled data-parsley-trigger="focusout" onblur="compareEndTime(1);" data-parsley-checkdate="#startTimeUpdate" style="width: 100px;display:inline;"></div>
                            </div>
                            <div class="form-group wifi">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks pull-left m-r">
                                  <label>
                                    <input type="checkbox" id="wifiUpdate" name="wifiUpdate" />
                                    <i></i>
                                    <fmt:message key="tiles.institution.policy.visit.wifi" />
                                  </label>
                                </div>
                              </div>
                            </div>
                            <div class="form-group limit_login">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks ">
                                  <label>
                                    <input type="checkbox" id="loginLimitUpdate" name="loginLimitUpdate" />
                                    <i></i>
                                    <fmt:message key="tiles.institution.policy.forbid.login" />
                                  </label>
                                </div>
                              </div>
                            </div>
                            <div class="form-group auto_login">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks ">
                                  <label>
                                    <input type="checkbox" id="autoLoginLimitUpdate" name="autoLoginLimitUpdate" />
                                    <i></i>
                                    <fmt:message key="tiles.institution.policy.forbid.auto.login" />
                                  </label>
                                </div>
                              </div>
                            </div>
                            <div class="form-group allow_errow">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks pull-left">
                                  <label>
                                    <input type="checkbox" id="errorLimitUpdate" name="errorLimitUpdate" />
                                    <i></i>
                                    <fmt:message key="tiles.institution.policy.allow.error" />
                                  </label>
                                </div>
                                <input type="text" id="isLockUpdate" name="isLockUpdate" data-parsley-required="true" data-parsley-trigger="focusout" data-parsley-type="digits" data-parsley-type-message="<fmt:message key="parsley.policy.password.digits"/>" style="width: 60px;display:inline;">
                                <fmt:message key="tiles.institution.policy.error.lock" />
                                <input type="text" id="lockTimeUpdate" name="lockTimeUpdate" data-parsley-required="true" data-parsley-trigger="focusout" data-parsley-type="digits" data-parsley-type-message="<fmt:message key="parsley.policy.password.digits"/>" style="width: 60px;display:inline;"><fmt:message key="tiles.institution.policy.error.minutes" /></div>
                            </div>
                            <div class="form-group login_count">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks pull-left">
                                  <label>
                                    <input type="checkbox" id="deviceUpdate" name="deviceUpdate"/>
                                    <i></i>
                                   <fmt:message key="tiles.institution.policy.user.registration" />
                                  </label>
                                </div>
                                <input type="text" id="deviceCountUpdate" name="deviceCountUpdate" data-parsley-required="true" data-parsley-trigger="focusout" data-parsley-type="digits" data-parsley-type-message="<fmt:message key="parsley.policy.password.digits"/>" style="width: 60px;display:inline;"><fmt:message key="tiles.institution.policy.device.count" /></div>
                            </div>
                            <div class="form-group fig_pass">
                              <div class="col-lg-12 m-l">
                                <div class="checkbox i-checks ">
                                  <label>
                                    <input type="checkbox" id="passwordUpdate" name="passwordUpdate"/>
                                    <i></i>
                                    <fmt:message key="tiles.institution.policy.set.password" />
                                  </label>
                                </div>
                              </div>
                            </div>
                            </div>
                             <input type="button" class="btn btn-md btn-primary pull-right" id="updatePolicyL" value="<fmt:message key="tiles.institution.policy.update" />" ></form>
                        </div>
                      </section>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
      <!-- Modal start-->
			<div class="modal fade" id="deleteMsg" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" data-backdrop="static">
				<div class="modal-dialog" role="document">
				 <input type="hidden" id="policyId" name="policyId" />
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title"><fmt:message key="tiles.institution.policy.del.title" /></h4>
						</div>
						<div class="modal-body">
							<h3 class="text-danger"><fmt:message key="tiles.institution.policy.del.msg" /></h3>
						</div>
						 <div class="modal-footer">
					        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.close"/></button>
					        <button type="button" class="btn btn-danger" id="deletePolicy"><fmt:message key="tiles.institution.org.department.confirm"/></button>
					      </div>
					</div>
				</div>
			</div>
<!--Modal end-->
<div class="modal fade" id="updatePolicyTip" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.org.department.msg" /></h4>
			</div>
			<div class="modal-body">
				<h3 class="text-danger"><fmt:message key="tiles.institution.user.policy.update.msg" /></h3>
				<input type="hidden" id="updatePolicyId" />
			</div>
			 <div class="modal-footer">
			 	<button type="button" class="btn btn-default" id="updatePolicyModal" data-dismiss="modal"><fmt:message key="tiles.fragments.consumer.nav.sendmessage.success.confirm"/></button>
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.close" /></button>
		      </div>
		</div>
	</div>
</div>
<div class="modal fade" id="delPolicyTip" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.institution.org.department.msg" /></h4>
			</div>
			<div class="modal-body">
				<h3 class="text-danger"><fmt:message key="tiles.institution.user.policy.delete.msg" /></h3>
				<input type="hidden" id="delPolicyId" />
			</div>
			 <div class="modal-footer">
			 	<button type="button" class="btn btn-default" id="delPolicyModal" data-dismiss="modal"><fmt:message key="tiles.fragments.consumer.nav.sendmessage.success.confirm"/></button>
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="tiles.institution.org.department.close" /></button>
		      </div>
		</div>
	</div>
</div>