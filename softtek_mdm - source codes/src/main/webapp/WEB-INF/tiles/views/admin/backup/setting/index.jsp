<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="blog-post">
	<div class="post-item">
		<header
			class="panel-heading b-l-l-none b-t-l-none b-b-l-1 b-r-l-none b-s-s">
			<spring:url value="/admin/backup/setting" var="setting" />
			<a href="${setting}" class="btn btn-s-sm btn-primary">数据备份设置</a>
			<spring:url value="/admin/backup/manage" var="manage" />
			<a href="${manage}" class="btn btn-s-sm">数据备份管理</a>
		</header>
		<section class="wrapper-md ">
				<div class="row">
					<div class="col-sm-12">
						<div class="panel panel-default">
							<div class="panel-body">
								<spring:url value="/admin/backup/updateBackupSetting" var="updateSetting" />
								<!-- 采用提交表单 -->
								<form class="bs-example form-horizontal" id="updateSettingfrm">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<input type="hidden" name="flag" value="${flag}" id = "flag"/>
									<!-- 自动备份选项 -->
									<div class="form-group">
										<label class="col-lg-2 control-label">自动备份</label>
										<div class="col-lg-8 center">
											<!-- 添加单选按钮组 -->
											<label><input type="radio" id="close" name="autoBackup" value="0"/>关闭</label>&nbsp;
											<label><input type="radio" id="open" name="autoBackup" value="1" checked="checked"/>开启</label> 
										</div>
									</div>
									<!-- 备份类型选项 -->
									<div class="form-group">
										<label class="col-lg-2 control-label">备份类型<span class="text-danger">*</span></label>
										<div class="col-lg-8 center">
											<!-- 添加单选按钮组 -->
											<div class="col-lg-1 clear">
												<div >
													<label><input type="radio" id="day" name="backupType" value="0"/>按天</label>
												</div>
												<div >
													<label><input type="radio" id="week" name="backupType" checked="checked" value="1"/>按周</label>
												</div>
												<div >
													<label><input type="radio" id="month" name="backupType" value="2"/>按月</label> 
												</div>
											</div>
											<!-- 以下的html代码是隐藏的选择按周、按天、按月备份的显示的内容 -->
											<div class="col-lg-7 float-right" id="hiddenContent">
												<p style="display:none;" id="pDay">每&nbsp;<input type="text" id="backUpDay" name="backUpDay" value="" style="width: 70px;"
												/>&nbsp;天</p>
												<p style="display:none;" id="pWeek">
									            	<label><input type="checkbox" name="backUpWeek" value="1" checked = "checked"/>星期一</label>
									            	<label><input type="checkbox" name="backUpWeek" value="2"/>星期二</label>
									            	<label><input type="checkbox" name="backUpWeek" value="3"/>星期三</label>
									            	<label><input type="checkbox" name="backUpWeek" value="4"/>星期四</label>
									            	<label><input type="checkbox" name="backUpWeek" value="5"/>星期五</label>
									            	<label><input type="checkbox" name="backUpWeek" value="6"/>星期六</label>
									            	<label><input type="checkbox" name="backUpWeek" value="7"/>星期日</label>
		                               			</p>
												 <p style="display:none;" id="pDate">
									                <label><input type="checkbox" name="backUpDate" value="1"/>01号</label>
									            	<label><input type="checkbox" name="backUpDate" value="2"/>02号</label>
									            	<label><input type="checkbox" name="backUpDate" value="3"/>03号</label>
									            	<label><input type="checkbox" name="backUpDate" value="4"/>04号</label>
									            	<label><input type="checkbox" name="backUpDate" value="5"/>05号</label>
									            	<label><input type="checkbox" name="backUpDate" value="6"/>06号</label>
									            	<label><input type="checkbox" name="backUpDate" value="7"/>07号</label><br/>
									            	<label><input type="checkbox" name="backUpDate" value="8"/>08号</label>
									            	<label><input type="checkbox" name="backUpDate" value="9"/>09号</label>
									            	<label><input type="checkbox" name="backUpDate" value="10"/>10号</label>
									            	<label><input type="checkbox" name="backUpDate" value="11"/>11号</label>
									            	<label><input type="checkbox" name="backUpDate" value="12"/>12号</label>
									            	<label><input type="checkbox" name="backUpDate" value="13"/>13号</label>
									            	<label><input type="checkbox" name="backUpDate" value="14"/>14号</label><br/>
									            	<label><input type="checkbox" name="backUpDate" value="15"/>15号</label>
									            	<label><input type="checkbox" name="backUpDate" value="16"/>16号</label>
									            	<label><input type="checkbox" name="backUpDate" value="17"/>17号</label>
									            	<label><input type="checkbox" name="backUpDate" value="18"/>18号</label>
									            	<label><input type="checkbox" name="backUpDate" value="19"/>19号</label>
									            	<label><input type="checkbox" name="backUpDate" value="20"/>20号</label>
									            	<label><input type="checkbox" name="backUpDate" value="21"/>21号</label><br/>
									            	<label><input type="checkbox" name="backUpDate" value="22"/>22号</label>
									            	<label><input type="checkbox" name="backUpDate" value="23"/>23号</label>
									            	<label><input type="checkbox" name="backUpDate" value="24"/>24号</label>
									            	<label><input type="checkbox" name="backUpDate" value="25"/>25号</label>
									            	<label><input type="checkbox" name="backUpDate" value="26"/>26号</label>
									            	<label><input type="checkbox" name="backUpDate" value="27"/>27号</label>
									            	<label><input type="checkbox" name="backUpDate" value="28"/>28号</label><br/>
									            	<label><input type="checkbox" name="backUpDate" value="29"/>29号</label>
									            	<label><input type="checkbox" name="backUpDate" value="30"/>30号</label>
									            	<label><input type="checkbox" name="backUpDate" value="31"/>31号</label>
				                                </p>
											</div>
										</div>
									</div>
									<!-- 备份时间选项 -->
									<div class="form-group">
										<label class="col-lg-2 control-label">备份时间</label>
										<div class="col-lg-8 center">
											<input class="time" type="text" id="backupTime" name="backupTime" size="16" value="03:00"/>
										</div>
									</div>
									<!-- 备份目录选项 -->
									<div class="form-group">
										<label class="col-lg-2 col-md-2 control-label">备份目录</label>
										<div class="col-lg-8 center">
											<label class="col-lg-2 col-md-2 clear left-align center">本地备份目录<span class="text-danger">*</span></label>
											<div class="col-lg-2 clear col-md-2">
												<input class="path " type="text" id="backupPath" name = "backupPath" data-parsley-required="true"/>
											</div>
										</div>
										<label class="col-lg-2 col-md-2 col-md-offset-3 control-label "><span class="text-danger center">注：本目录必须可写操作</span></label>
									</div>
									<!-- 按钮组 -->
									<div class="form-group">
										<div class="col-lg-offset-5 col-lg-10">
											<button type="button" class="btn btn-md btn-primary " id="updateL" onclick="btnSave(0);">保存</button>&nbsp;&nbsp;
											<button type="button" class="btn btn-md btn-primary " id="btn-reset" onclick="btnSave(1);" >保存并立即备份</button>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
		</section>
	</div>
</div>
