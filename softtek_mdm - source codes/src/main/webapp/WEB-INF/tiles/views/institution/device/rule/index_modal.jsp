<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Modal start-->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.views.institution.device.rule.add" /></h4>
			</div>
			<div class="modal-body" style="margin-bottom: -20px;">
				<section class="">
					<header class="panel-heading bg-light">
						<ul id="rule-tab-justified" class="nav nav-tabs nav-justified">
							<li class="active">
							<a href="#detail" data-toggle="tab">
							<span class="text-info"><b class="badge tab-badge-info text-lg">1</b><fmt:message key="tiles.views.institution.device.rule.add.detail"/></span></a></li>
							<li class="">
							<a href="#_arranage" data-toggle="tab">
							<span class="text-muted"><b class="badge  text-lg">2</b><fmt:message key="tiles.views.institution.device.rule.add.arrange"/></span></a></li>
							<li class="">
							<a href="#_rule" data-toggle="tab">
							<span class="text-muted"><b class="badge  text-lg">3</b><fmt:message key="tiles.views.institution.device.rule.heander.rule"/></span></a></li>
							<li class="">
							<a href="#_opera" data-toggle="tab">
							<span class="text-muted"><b class="badge  text-lg">4</b><fmt:message key="tiles.views.institution.device.rule.add.opera"/></span></a></li>
						</ul>
					</header>
					<div class="panel-body">
						<div class="tab-content" id="tab-pane-body">
							<div class="tab-pane active" id="detail">
			                    <form id="nextDetailFrm" class="bs-example form-horizontal">
			                   		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			                    	<input id="detail-rule-id" type="hidden" value="">
			                        <div class="form-group">
			                          <label class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.device.rule.name"/><span class="text-danger">*</span></label>
			                          <div class="col-lg-8">
			                            <input id="detail-rule-name" autocomplete="off" type="text" class="form-control" data-parsley-required="true" maxlength="30">
			                          </div>
			                        </div>
			                        <div class="form-group">
			                          <label class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.device.rule.add.describe"/><span class="text-danger">*</span></label>
			                          <div class="col-lg-8">
			                            <textarea id="detail-rule-describe" class="form-control" rows="5" cols="20" style="resize:none;" data-parsley-required="true" maxlength="100"></textarea>
			                          </div>
			                        </div>
			                      </form>
			                      <div class="modal-footer">
										<button type="button" class="btn btn-primary" onclick="next_detail(1)">
											<fmt:message key="tiles.views.institution.device.rule.add.next"/>
										</button>
									</div>
							</div>
							<div class="tab-pane" id="arranage">
							  <form id="" class="form-horizontal">
			                        <div class="form-group">
			                          <label class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.device.rule.platform"/></label>
			                          <div class="col-lg-8">
			                           	<select id="arranage-rule-select" class="form-control" >
			                           		<option value="1"><fmt:message key="tiles.views.institution.device.rule.add.common"/></option>
			                           		<option value="2"><fmt:message key="tiles.views.institution.device.rule.add.android"/></option>
			                           		<option value="3"><fmt:message key="tiles.views.institution.device.rule.add.ios"/></option>
			                           	</select>
			                          </div>
			                        </div>
			                        <div class="form-group">
			                         <label class="col-lg-offset-1 col-lg-8 control-label info_des" style="text-align:left"><fmt:message key="tiles.views.institution.device.rule.add.tip"/></label>
			                        </div>
			                        <div class="form-group">
			                        <label class="col-lg-2"></label>
			                           <div class="col-lg-8">
			                            <div style="min-height:100px;max-height:200px;overflow: scroll;width:100%;">
			                        	<ul id="tree" class="col-lg-offset-1 col-lg-8" ></ul>
			                        	</div>
			                           </div>
			                        </div>
			                        <div class="form-group vir_user">
			                        	<div class="col-lg-4 only_vir">
			                        		<label class="col-lg-offset-2 col-lg-8 control-label m-t-xs " style="text-align:left;margin-bottom: 5px;"><fmt:message key="tiles.views.institution.device.rule.add.vtl"/></label>
			                        		<div id="vtls" class="col-lg-offset-2 col-sm-12" style="max-height:150px;overflow-y:scroll;">
			                        		<c:forEach items="${listGroups}" var="group">
			                        			<div class="alerts alert-infos lter" style="padding-left: 5px;">
							                    <div class="checkbox i-checks" style="padding-top: 4px;">
					                              <label>
					                                <input type="checkbox" value="${group.id}"><i></i>${group.name}
					                              </label>
					                            </div>
							                  </div>
			                        		</c:forEach>
							                </div>
			                        	</div>
			                        	<div class="col-lg-4 only_user">
			                        		<div class="col-lg-offset-2 col-lg-12">
			                        			<div class="form-group">
			                        			<label class="col-lg-12 control-label m-t-xs " style="text-align:left;margin-bottom: 5px;"><fmt:message key="tiles.views.institution.device.rule.add.user"/></label>
							                      <div class="col-sm-12">
							                        <select style="width:260px" multiple class="chosen-select" data-placeholder="<fmt:message key='tiles.views.institution.device.rule.add.user.choose'/>">
							                          <c:forEach items="${users}" var="user">
							                          	<option value="${user.id}">${user.realname}(${user.username})</option>
							                          </c:forEach>
							                        </select>
							                      </div>
							                    </div>
			                        		</div>
			                        	</div>
			                        </div>
			                   </form>
			                        
								 <div class="modal-footer">
										<button type="button" class="btn btn-default"  onclick="pre_detail(1)">
											<fmt:message key="tiles.views.institution.device.rule.add.pre"/>
										</button>
										<button type="button" class="btn btn-primary" onclick="next_detail(2)">
											<fmt:message key="tiles.views.institution.device.rule.add.next"/>
										</button>
								</div>
							</div>
							
							<div class="tab-pane" id="rule" style="max-height: 500px; overflow-y: scroll;">
								<div class="row">
									<div class="form-inline m-l" role="form">
										<div class="form-group">
											<label><fmt:message key="tiles.views.institution.device.rule.add.diff.type"/></label> <select class="form-control">
												<option value="1"><fmt:message key="tiles.views.institution.device.rule.add.match.full"/></option>
											</select>
										</div>
										<div class="form-group">
											<label><fmt:message key="tiles.views.institution.device.rule.add.same.type"/></label> <select id="rule-match-select" class="form-control">
												<option value="1"><fmt:message key="tiles.views.institution.device.rule.add.match.full"/></option>
												<option value="2"><fmt:message key="tiles.views.institution.device.rule.add.match.depart"/></option>
											</select>
										</div>
									</div>
								</div>
								<div class="line line-dashed b-b line-lg pull-in"></div>
								<div class="row">
									<div id="ruleFrm" class="form-inline m-l" role="form">
										<div class="form-group">
										<span><fmt:message key="tiles.views.institution.device.rule.add.type.choose"/>:</span>
											<select id="firstSelect" class="form-control"
												onchange="selectOnChange(this)">
												<option value="0"><fmt:message key="tiles.views.institution.device.rule.add.type.choose"/></option>
												<c:if test="${menu!=null&&menu[21].isshow==-1}">
												<option value="1"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.blackandwhite"/></option>
												</c:if>
												<option value="2"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.root"/></option>
												<option value="3"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.no"/></option>
												<option value="4"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.osversion"/></option>
												<option value="5"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.lock"/></option>
											<%-- 	<option value="6"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.belong"/></option> --%>
												<option value="7"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.timerange"/></option>
												<option value="8"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.imsi"/></option>
												<option value="9"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.status"/></option>
												<option value="10"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.sim"/></option>
												<option value="11"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.sd"/></option>
												<option value="12"><fmt:message key="tiles.views.institution.device.rule.add.type.choose.gps"/></option>
											</select>
										</div>
										<div id="tmplContent" class="form-group"></div>
										<div id="tmplRemoteContent" class="form-group"></div>
										<div id="tmpladdIcon" class="form-group"></div>
									</div>
								</div>
								<div class="line line-dashed b-b line-lg pull-in"></div>
								<table id="ruleTb" class="table table-striped b-t b-light">
									<thead>
										<tr class="rule_th">
											<th><fmt:message key="tiles.views.institution.device.rule.add.type.rule"/></th>
											<th><fmt:message key="tiles.views.institution.device.rule.add.type.rule.expr"/></th>
											<th><fmt:message key="tiles.views.institution.device.rule.add.type.rule.value"/></th>
											<th><fmt:message key="tiles.views.institution.device.rule.add.type.rule.delete"/></th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
								 <div class="modal-footer">
										<button type="button" class="btn btn-default"  onclick="pre_detail(2)">
											<fmt:message key="tiles.views.institution.device.rule.add.pre"/>
										</button>
										<button id="btn-next" type="button" class="btn btn-primary hidden" onclick="next_detail(3)">
											<fmt:message key="tiles.views.institution.device.rule.add.next"/>
										</button>
								</div>
							</div>
							<div class="tab-pane" id="opera">
								<div class="row">
									<div class="form-group">
										<label class="col-lg-12"><fmt:message key="tiles.views.institution.device.rule.add.type.rule.tip"/></label>
									</div>
								</div>
								<div class="row">
									<div id="operaFrm" class="form-inline m-l" role="form">
										<div class="form-group">
										<span><fmt:message key="tiles.views.institution.device.rule.please.choose"/></span>
											<select id="operafirstSelect" class="form-control"
												onchange="operaSelectOnChange(this)">
												<option value="0"><fmt:message key="tiles.views.institution.device.rule.add.type.choose"/></option>
												<option value="1"><fmt:message key="tiles.views.institution.device.rule.add.type.opera.inform"/></option>
												<option value="2"><fmt:message key="tiles.views.institution.device.rule.add.type.opera.order"/></option>
												<option value="3"><fmt:message key="tiles.views.institution.device.rule.add.type.opera.termial"/></option>
												<c:if test="${menu!=null&&menu[14].isshow==-1}">
												<option value="4"><fmt:message key="tiles.views.institution.device.rule.add.type.opera.changestragy"/></option>
												</c:if>
												<option value="7"><fmt:message key="tiles.views.institution.device.rule.add.type.opera.changelock"/></option>
											<!-- <option value="5">安全邮件</option>
												<option value="6">应用限制</option> -->
											</select>
										</div>
										<div id="tmplOperaContent" class="form-group"></div>
										<div id="tmplOperaRemoteContent" class="form-group"></div>
										<div id="tmplOperaAddIcon" class="form-group"></div>
									</div>
								</div>
								<div class="line line-dashed b-b line-lg pull-in"></div>
								<table id="operaTb" class="table table-striped b-t b-light">
									<thead>
										<tr class="rule_th">
											<th class=""><fmt:message key="tiles.views.institution.device.rule.add.opera.type"/></th>
											<th><fmt:message key="tiles.views.institution.device.rule.add.opera.expr"/></th>
											<th><fmt:message key="tiles.views.institution.device.rule.add.opera.value"/></th>
											<th><fmt:message key="tiles.views.institution.device.rule.add.type.rule.delete"/></th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
								 <div class="modal-footer">
										<button type="button" class="btn btn-default" onclick="pre_detail(3)">
											<fmt:message key="tiles.views.institution.device.rule.add.pre"/>
										</button>
										<button type="button" onclick="btnConfim();" id="btn-confirm" class="btn btn-primary hidden" >
											<fmt:message key="tiles.views.institution.device.rule.add.done"/>
										</button>
								</div>
							</div>
						</div>
					</div>
				</section>
				
			</div>
		</div>
	</div>
</div>
<!--Modal end-->


<!--Modal Start-->

<!-- Modal start-->
<div class="modal fade" id="viewModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"><fmt:message key="tiles.views.institution.device.rule.detail" /></h4>
			</div>
			<div class="modal-body" style="margin-bottom: -20px;">
				
			</div>
		</div>
	</div>
</div>
<!--Modal end-->


<!-- Modal start-->
<div class="modal fade" id="delModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
					<fmt:message key="tiles.views.institution.device.rule.delete.tip.header" />
				</h4>
			</div>
			<div class="modal-body">
				<input id="del-rule-id" type="hidden" value="">
				<h3 class="text-danger"><fmt:message key="tiles.views.institution.device.rule.delete.tip.content"/></h3>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<fmt:message key="tiles.views.institution.device.rule.delete.tip.cancel" />
				</button>
				<input type="button" class="btn btn-danger" onclick="truncateRule()"
					data-dismiss="modal" value="<fmt:message key='tiles.views.institution.device.rule.delete.tip.confirm'/>">
			</div>
		</div>
	</div>
</div>
<!--Modal end-->


<!-- Modal start-->
<div class="modal fade" id="copyModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
					<i class="fa fa-warning text-primary m-r"></i><fmt:message key="tiles.views.institution.device.rule.copy.tip.header"/>
				</h4>
			</div>
			<div class="modal-body">
				<input id="copy-rule-id" type="hidden" value="">
				<h3 class=""><fmt:message key="tiles.views.institution.device.rule.copy.tip.content"/></h3>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<fmt:message key="tiles.views.institution.device.rule.delete.tip.cancel" />
				</button>
				<input type="button" class="btn btn-primary" onclick="copyRuleConfirm()"
					data-dismiss="modal" value="<fmt:message key='tiles.views.institution.device.rule.delete.tip.confirm'/>">
			</div>
		</div>
	</div>
</div>
<!--Modal end-->


<!-- Modal start-->
<div class="modal fade" id="legalModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
					<fmt:message key="tiles.views.institution.device.rule.history.header"/>
				</h4>
			</div>
			<div class="modal-body">
				<div class="table-responsive">
		<table id="table-rule-legal" class="table table-striped b-t b-light" style="width:100%!important">
			<thead>
				<tr>
					<th><fmt:message key="tiles.views.institution.device.rule.history.table.username"/></th>
					<th><fmt:message key="tiles.views.institution.device.rule.history.table.account"/></th>
					<th><fmt:message key="tiles.views.institution.device.rule.history.table.deviceno"/></th>
					<th><fmt:message key="tiles.views.institution.device.rule.history.table.status"/></th>
					<th><fmt:message key="tiles.views.institution.device.rule.history.table.testingtime"/></th>
					<th><fmt:message key="tiles.views.institution.device.rule.history.table.deal"/></th>
					<th><fmt:message key="tiles.views.institution.device.rule.opera"/></th>
				</tr>
			</thead>
			<tbody>

			</tbody>
		</table>
	</div>
			</div>
		</div>
	</div>
</div>
<!--Modal end-->

<!-- Modal start-->
<div class="modal fade" id="legalDetailModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
					<fmt:message key="tiles.views.institution.device.rule.illeage.header"/>
				</h4>
			</div>
			<div class="modal-body">
				<div class="table-responsive">
		<table id="table-rule-legal-detail" class="table table-striped b-t b-light" style="width:100%!important">
			<thead>
				<tr class="">
					<th><fmt:message key="tiles.views.institution.device.rule.name"/></th>
					<th><fmt:message key="tiles.views.institution.device.rule.illeage.time"/></th>
					<th><fmt:message key="tiles.views.institution.device.rule.illeage.detail"/></th>
					<th><fmt:message key="tiles.views.institution.device.rule.opera"/></th>
				</tr>
			</thead>
			<tbody>

			</tbody>
		</table>
	</div>
			</div>
		</div>
	</div>
</div>
<!--Modal end-->


<script id="newlyTmpl" type="text/x-jquery-tmpl">
			<section class="panel panel-default">
                    <header class="panel-heading bg-light">
                      <ul class="nav nav-tabs nav-justified">
                        <li class="active"><a href="#view-i" data-toggle="tab"><fmt:message key="tiles.views.institution.device.rule.add.detail"/></a></li>
                        <li class=""><a href="#view-a" data-toggle="tab"><fmt:message key="tiles.views.institution.device.rule.add.arrange"/></a></li>
                        <li class=""><a href="#view-r" data-toggle="tab"><fmt:message key="tiles.views.institution.device.rule.heander.rule"/></a></li>
                        <li class=""><a href="#view-o" data-toggle="tab"><fmt:message key="tiles.views.institution.device.rule.add.opera"/></a></li>
                      </ul>
                    </header>
                    <div class="panel-body">
                      <div class="tab-content">
                        <div class="tab-pane active" id="view-i">
                         <div id="nextDetailFrm" class="bs-example form-horizontal">
	                        <div class="form-group">
	                          <label class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.device.rule.name"/></label>
	                          <div class="col-lg-9">
	                            <label class="form-control">{{= deviceRule.name}}</label>
	                          </div>
	                        </div>
	                        <div class="form-group">
	                          <label class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.device.rule.add.describe"/></label>
	                          <div class="col-lg-9">
	                           <label class="form-control">{{= deviceRule.describe}}</label>
	                          </div>
	                        </div>
	                         <div class="form-group">
	                          <label class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.device.rule.newly.enable"/></label>
	                          <div class="col-lg-9">
	                           <label class="form-control">{{if deviceRule.isactive==1}}<fmt:message key="tiles.views.institution.device.rule.newly.enable.enable"/>{{else}}<fmt:message key="tiles.views.institution.device.rule.newly.enable.disable"/>{{/if}}</label>
	                          </div>
	                        </div>
	                         <div class="form-group">
	                          <label class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.device.rule.platform"/></label>
	                          <div class="col-lg-9">
	                           <label class="form-control">
									{{if deviceRule.platform==1}}<fmt:message key="tiles.views.institution.device.rule.add.common"/>{{/if}}
									{{if deviceRule.platform==2}}<fmt:message key="tiles.views.institution.device.rule.add.android"/>{{/if}}
									{{if deviceRule.platform==3}}<fmt:message key="tiles.views.institution.device.rule.add.ios"/>{{/if}}
								</label>
	                          </div>
	                        </div>
	                        <div class="form-group">
	                          <label class="col-lg-2 control-label"><fmt:message key="tiles.views.institution.device.rule.nexttest"/></label>
	                          <div class="col-lg-9">
	                           <label class="form-control">{{if deviceRule.updateTime==null}}<fmt:message key="tiles.views.institution.device.rule.newly.none"/>{{else}}
								{{= deviceRule.updateDateStr}}
								{{/if}}</label>
	                          </div>
	                        </div>
	                      </div>
                        </div>
                        <div class="tab-pane" id="view-a">
							<div class="row">
							<label class="col-lg-12 h4 m-b"><fmt:message key="tiles.views.institution.device.rule.newly.arrange"/></label>
							{{if deviceRuleDepartment!=null}}
								{{each(i,e) deviceRuleDepartment}}
									<a href="javascript:void(0);" class="btn btn-rounded btn-sm btn-info">{{= e.structure.name}}</a>
								{{/each}}
							{{else}}
								<fmt:message key="tiles.views.institution.device.rule.newly.arrange.none"/>
							{{/if}}
							</div>
						<div class="row">
							<label class="col-lg-12  h4 m-b m-t"><fmt:message key="tiles.views.institution.device.rule.newly.arrange.vtl"/></label>
							{{if deviceRuleVirtualGroup!=null}}
								{{each(i,e) deviceRuleVirtualGroup}}
									<a href="javascript:void(0);" class="btn btn-rounded btn-sm btn-warning">{{= e.virtualGroup.name}}</a>
								{{/each}}
							{{else}}
								<fmt:message key="tiles.views.institution.device.rule.newly.arrange.vtl.none"/>
							{{/if}}
							</div>
						<div class="row">
							<label class="col-lg-12  h4 m-b m-t"><fmt:message key="tiles.views.institution.device.rule.newly.arrange.user"/></label>
							{{if deviceRuleUser!=null}}
								{{each(i,e) deviceRuleUser}}
									<a href="javascript:void(0);" class="btn btn-rounded btn-sm btn-default">{{= e.user.username}}({{= realname}})</a>
								{{/each}}
							{{else}}
								<fmt:message key="tiles.views.institution.device.rule.newly.arrange.user.none"/>
							{{/if}}
							</div>
						</div>
                        <div class="tab-pane" id="view-r">
						<div class="row">
							<label class="col-lg-12  h4 m-b m-t">
							{{if deviceRule.deviceRuleMatch.id==1}}<fmt:message key="tiles.views.institution.device.rule.newly.rule.tipone"/>{{/if}}
							{{if deviceRule.deviceRuleMatch.id==2}}<fmt:message key="tiles.views.institution.device.rule.newly.rule.tiptwo"/>{{/if}}
							</label>
							<table class="table table-striped b-t b-light">
									<thead>
										<tr class="">
											<th class=""><fmt:message key="tiles.views.institution.device.rule.add.type.rule"/></th>
											<th><fmt:message key="tiles.views.institution.device.rule.add.type.rule.expr"/></th>
											<th><fmt:message key="tiles.views.institution.device.rule.add.type.rule.value"/></th>
										</tr>
									</thead>
									<tbody>
									{{each(i,e) deviceRuleItemRelation}}
										<tr>
										<td>
										{{if e.deviceRuleItemRecord.type==1}}<fmt:message key="tiles.views.institution.device.rule.add.type.choose.blackandwhite"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==2}}<fmt:message key="tiles.views.institution.device.rule.add.type.choose.root"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==3}}<fmt:message key="tiles.views.institution.device.rule.add.type.choose.no"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==4}}<fmt:message key="tiles.views.institution.device.rule.add.type.choose.osversion"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==5}}<fmt:message key="tiles.views.institution.device.rule.add.type.choose.lock"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==6}}<fmt:message key="tiles.views.institution.device.rule.add.type.choose.belong"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==7}}<fmt:message key="tiles.views.institution.device.rule.add.type.choose.timerange"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==8}}<fmt:message key="tiles.views.institution.device.rule.add.type.choose.imsi"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==9}}<fmt:message key="tiles.views.institution.device.rule.add.type.choose.status"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==10}}<fmt:message key="tiles.views.institution.device.rule.add.type.choose.sim"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==11}}<fmt:message key="tiles.views.institution.device.rule.add.type.choose.sd"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==12}}<fmt:message key="tiles.views.institution.device.rule.add.type.choose.gps"/>{{/if}}
										</td>
										<td>
										{{if e.deviceRuleItemRecord.type==1&&e.deviceRuleItemRecord.condition==1}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.black"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==1&&e.deviceRuleItemRecord.condition==0}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.white"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==2&&e.deviceRuleItemRecord.condition==1}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.root.enable"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==2&&e.deviceRuleItemRecord.condition==0}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.root.disable"/>{{/if}}											
										{{if e.deviceRuleItemRecord.type==3}}为{{/if}}
										{{if e.deviceRuleItemRecord.type==4&&e.deviceRuleItemRecord.condition==1}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.comp.eq"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==4&&e.deviceRuleItemRecord.condition==2}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.comp.gt"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==4&&e.deviceRuleItemRecord.condition==3}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.comp.lt"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==4&&e.deviceRuleItemRecord.condition==4}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.comp.gteq"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==4&&e.deviceRuleItemRecord.condition==5}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.comp.lteq"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==5&&e.deviceRuleItemRecord.condition==1}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.config.enable"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==5&&e.deviceRuleItemRecord.condition==0}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.config.disable"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==6&&e.deviceRuleItemRecord.condition==1}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.device.enterp"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==6&&e.deviceRuleItemRecord.condition==0}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.device.person"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==7&&e.deviceRuleItemRecord.condition==1}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.range.in"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==7&&e.deviceRuleItemRecord.condition==0}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.range.out"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==8&&e.deviceRuleItemRecord.condition==1}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.version.eq"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==8&&e.deviceRuleItemRecord.condition==0}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.version.neq"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==9&&e.deviceRuleItemRecord.condition==1}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.mirror.in"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==9&&e.deviceRuleItemRecord.condition==2}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.mirror.wait"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==9&&e.deviceRuleItemRecord.condition==3}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.mirror.outing"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==9&&e.deviceRuleItemRecord.condition==4}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.mirror.inpipe"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==9&&e.deviceRuleItemRecord.condition==5}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.mirror.have"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==9&&e.deviceRuleItemRecord.condition==6}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.mirror.lost"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==9&&e.deviceRuleItemRecord.condition==7}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.mirror.out"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==10&&e.deviceRuleItemRecord.condition==0}}0{{/if}}
										{{if e.deviceRuleItemRecord.type==10&&e.deviceRuleItemRecord.condition==1}}1{{/if}}		
										{{if e.deviceRuleItemRecord.type==10&&e.deviceRuleItemRecord.condition==2}}2{{/if}}			
										{{if e.deviceRuleItemRecord.type==11&&e.deviceRuleItemRecord.condition==1}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.gteqone"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==12&&e.deviceRuleItemRecord.condition==0}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.gps.close"/>{{/if}}
										{{if e.deviceRuleItemRecord.type==12&&e.deviceRuleItemRecord.condition==1}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.gps.open"/>{{/if}}
										</td>

										<td>
										{{if (e.deviceRuleItemRecord.type==2|| 
										e.deviceRuleItemRecord.type==5|| 
										e.deviceRuleItemRecord.type==6||  
										e.deviceRuleItemRecord.type==8|| 
										e.deviceRuleItemRecord.type==9|| 
										e.deviceRuleItemRecord.type==10||  
										e.deviceRuleItemRecord.type==11)}}{{/if}}
										{{if (e.deviceRuleItemRecord.type==1||  
										e.deviceRuleItemRecord.type==3||  
										e.deviceRuleItemRecord.type==4||  
										e.deviceRuleItemRecord.type==7)}}{{= e.deviceRuleItemRecord.value}}{{/if}}
										</td>
										</tr>
									{{/each}}
									</tbody>
								</table>
						</div>

						</div>
                        <div class="tab-pane" id="view-o">
							<div class="row">
							<table class="table table-striped b-t b-light">
									<thead>
										<tr class="">
											<th><fmt:message key="tiles.views.institution.device.rule.add.opera.type"/></th>
											<th><fmt:message key="tiles.views.institution.device.rule.add.opera.expr"/></th>
											<th><fmt:message key="tiles.views.institution.device.rule.add.opera.value"/></th>
										</tr>
									</thead>
									<tbody>
									{{each(i,e) deviceRuleOperationItemRelation}}
										<tr>
										<td>
										{{if e.deviceRuleOperationRecord.type==1}}<fmt:message key="tiles.views.institution.device.rule.add.type.opera.inform"/>{{/if}}
										{{if e.deviceRuleOperationRecord.type==2}}<fmt:message key="tiles.views.institution.device.rule.add.type.opera.order"/>{{/if}}
										{{if e.deviceRuleOperationRecord.type==3}}<fmt:message key="tiles.views.institution.device.rule.add.type.opera.termial"/>{{/if}}
										{{if e.deviceRuleOperationRecord.type==4}}<fmt:message key="tiles.views.institution.device.rule.add.type.opera.changestragy"/>{{/if}}
										{{if e.deviceRuleOperationRecord.type==7}}<fmt:message key="tiles.views.institution.device.rule.add.type.opera.changelock"/>{{/if}}
										</td>
										<td>
										{{if e.deviceRuleOperationRecord.type==1&&e.deviceRuleOperationRecord.condition==1}}<fmt:message key="tiles.views.institution.device.rule.add.type.opera.tmpl.email"/>{{/if}}
										{{if e.deviceRuleOperationRecord.type==1&&e.deviceRuleOperationRecord.condition==2}}<fmt:message key="tiles.views.institution.device.rule.add.type.opera.tmpl.sms"/>{{/if}}
										{{if e.deviceRuleOperationRecord.type==1&&e.deviceRuleOperationRecord.condition==3}}<fmt:message key="tiles.views.institution.device.rule.add.type.opera.tmpl.push"/>{{/if}}
										{{if e.deviceRuleOperationRecord.type==1&&e.deviceRuleOperationRecord.condition==4}}<fmt:message key="tiles.views.institution.device.rule.add.type.opera.tmpl.email.inform"/>{{/if}}
										{{if e.deviceRuleOperationRecord.type==1&&e.deviceRuleOperationRecord.condition==1}}<fmt:message key="tiles.views.institution.device.rule.add.type.opera.tmpl.email"/>{{/if}}
										{{if e.deviceRuleOperationRecord.type==1&&e.deviceRuleOperationRecord.condition==1}}<fmt:message key="tiles.views.institution.device.rule.add.type.opera.tmpl.email"/>{{/if}}
										{{if e.deviceRuleOperationRecord.type==2&&e.deviceRuleOperationRecord.condition==1}}<fmt:message key="tiles.views.institution.device.rule.add.type.opera.tmpl.erase.enterp"/>{{/if}}
										{{if e.deviceRuleOperationRecord.type==2&&e.deviceRuleOperationRecord.condition==0}}<fmt:message key="tiles.views.institution.device.rule.add.type.opera.tmpl.backto"/>{{/if}}
										{{if e.deviceRuleOperationRecord.type==3&&e.deviceRuleOperationRecord.condition==1}}<fmt:message key="tiles.views.institution.device.rule.add.type.opera.tmpl.forbidden.signin"/>{{/if}}
										{{if e.deviceRuleOperationRecord.type==3&&e.deviceRuleOperationRecord.condition==2}}<fmt:message key="tiles.views.institution.device.rule.add.type.opera.tmpl.forbidden.app"/>{{/if}}
										{{if e.deviceRuleOperationRecord.type==3&&e.deviceRuleOperationRecord.condition==3}}<fmt:message key="tiles.views.institution.device.rule.add.type.opera.tmpl.forbidden.file"/>{{/if}}
										{{if e.deviceRuleOperationRecord.type==4&&e.deviceRuleOperationRecord.condition==1}}<fmt:message key="tiles.views.institution.device.rule.add.android"/>{{/if}}
										{{if e.deviceRuleOperationRecord.type==4&&e.deviceRuleOperationRecord.condition==0}}<fmt:message key="tiles.views.institution.device.rule.add.ios"/>{{/if}}
										{{if e.deviceRuleOperationRecord.type==5&&e.deviceRuleOperationRecord.condition==1}}<fmt:message key="tiles.views.institution.device.rule.add.type.opera.tmpl.erase.email"/>{{/if}}
										{{if e.deviceRuleOperationRecord.type==5&&e.deviceRuleOperationRecord.condition==2}}<fmt:message key="tiles.views.institution.device.rule.add.type.opera.tmpl.forbidden.access"/>{{/if}}
										{{if e.deviceRuleOperationRecord.type==6&&e.deviceRuleOperationRecord.condition==1}}<fmt:message key="tiles.views.institution.device.rule.add.type.opera.tmpl.limit"/>{{/if}}
										{{if e.deviceRuleOperationRecord.type==7}}<fmt:message key="tiles.views.institution.device.rule.add.type.opera.tmpl.confirm"/>{{/if}}
										</td>
										
										<td>
										{{if (e.deviceRuleOperationRecord.type==1||
										e.deviceRuleOperationRecord.type==4||
										e.deviceRuleOperationRecord.type==6)}}{{= e.deviceRuleOperationRecord.value}}{{/if}}
										{{if e.deviceRuleOperationRecord.type==2&&
										e.deviceRuleOperationRecord.condition==1&&
										e.deviceRuleOperationRecord.value==1}}<fmt:message key="tiles.views.institution.device.rule.add.type.opera.tmpl.deviceall"/>{{/if}}
										{{if e.deviceRuleOperationRecord.type==2&&
										e.deviceRuleOperationRecord.condition==0&&
										e.deviceRuleOperationRecord.value==2}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.device.person"/>{{/if}}
										{{if e.deviceRuleOperationRecord.type==2&&
										e.deviceRuleOperationRecord.condition==0&&
										e.deviceRuleOperationRecord.value==3}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.device.enterp"/>{{/if}}
										{{if e.deviceRuleOperationRecord.type==2&&
										e.deviceRuleOperationRecord.condition==1}}<fmt:message key="tiles.views.institution.device.rule.add.type.condition.device.enterp"/>{{/if}}
										{{if (e.deviceRuleOperationRecord.type==3||e.deviceRuleOperationRecord.type==5)}}{{/if}}
										</td>
										</tr>
									{{/each}}
									</tbody>
								</table>
						</div>
						</div>
                      </div>
                    </div>
                  </section>
</script>



<script id="selectTmpl" type="text/x-jquery-tmpl">
<div class="form-group">
	<select class="form-control" onchange="selectNameList(this)">
		{{each(i,e) data}}
			<option value="{{= e.id}}">{{= e.name}}</option>
		{{/each}}
	</select>
</div>
</script>

<script id="crackTmpl" type="text/x-jquery-tmpl">
<div class="form-group">
	<select class="form-control">
		{{each(i,e) data}}
			<option value="{{= e.id}}">{{= e.name}}</option>
		{{/each}}
	</select>
</div>
</script>

<script id="txtTmpl" type="text/x-jquery-tmpl">
<div class="form-group">
	<input type="text" class="form-control" autocomplete="off">
</div>
</script>

<script id="selectRemoteTmpl" type="text/x-jquery-tmpl">
<div class="form-group">
	<select class="form-control">
		{{each(i,e) data}}
			<option value="{{= e.id}}">{{= e.listName}}</option>
		{{/each}}
	</select>
</div>
</script>

<script id="tableTrTmpl" type="text/x-jquery-tmpl">
<tr>
<td ref="{{= firstVal}}">{{= firstStr}}</td>
<td ref="{{= secondVal}}">{{= secondStr}}</td>
<td>{{= thirdStr}}</td>
<td><a href="javascript:void(0);" onclick="trash(this)"><i class="fa fa-trash-o text-danger"></i></a></td>
</tr>
</script>

<script id="addIconTmpl" type="text/x-jquery-tmpl">
<div id="addIcon" class="form-group">
	<a href="javascript:void(0)" title="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.add'/>" onclick="addTr()"><i class="fa fa-plus-circle fa-2x text-info"></i></a>
</div>
</script>

<script id="clockpickerTmpl" type="text/x-jquery-tmpl">
<input class="form-control" id="clockpicker-start" style="width:90px;">
<input class="form-control" id="clockpicker-end" style="width:90px;">
</script>

<script id="opAddIconTmpl" type="text/x-jquery-tmpl">
<div id="addIcon" class="form-group">
	<a href="javascript:void(0)" title="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.add'/>" onclick="opAddTr()"><i class="fa fa-plus-circle fa-2x text-info"></i></a>
</div>
</script>

<script id="opOrderTmpl" type="text/x-jquery-tmpl">
<div class="form-group">
	<select class="form-control" onchange="operaOrderSelectOnChange(this)">
		{{each(i,e) data}}
			<option value="{{= e.id}}">{{= e.name}}</option>
		{{/each}}
	</select>
</div>
</script>

<script id="opStrategyTmpl" type="text/x-jquery-tmpl">
<div class="form-group">
	<select class="form-control" onchange="operaStrategySelectOnChange(this)">
		{{each(i,e) data}}
			<option value="{{= e.id}}">{{= e.name}}</option>
		{{/each}}
	</select>
</div>
</script>




