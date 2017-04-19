<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<section class="hbox stretch">
	<aside class="aside-lg bg-light lter b-r">
		<section class="vbox">
			<section class="scrollable">
				<div class="wrapper">
					<section class="panel no-border bg-primary lt">
						<div class="panel-body">
							<div class="row m-t-xl">
								<div class="col-xs-6 col-xs-offset-3 text-center">
									<div class="inline">
											<div class="thumb-lg avatar">
											 <spring:url value="/resources/images/a0.png"  var="thumbUrl"/>   
												<img src="${thumbUrl}" class="dker">
											</div>
										<div class="h4 m-t m-b-xs font-bold text-lt">${manager.name}</div>
										<small class="text-muted m-b">${manager.username}</small>
									</div>
								</div>
							</div>
							<div class="wrapper m-t-xl m-b">
								<div class="row m-b">
									<div class="col-xs-6 text-right">
										<small><fmt:message key="jsp.views.institution.personal.index.label6"/></small>
										<div class="text-lt font-bold">${manager.phone}</div>
									</div>
									<div class="col-xs-6">
										<small><fmt:message key="jsp.views.institution.personal.index.label5"/></small>
										<div class="text-lt font-bold">${manager.email}</div>
									</div>
								</div>
							</div>
						</div>
						<footer class="panel-footer dk text-center no-border">
							<div class="row pull-out">
								<div class="col-xs-4">
									<div class="padder-v">
										<span class="m-b-xs h3 block text-white">${departNum}</span> <small
											class="text-muted"><fmt:message key="jsp.views.institution.personal.index.label1"/></small>
									</div>
								</div>
								<div class="col-xs-4 dker">
									<div class="padder-v">
										<span class="m-b-xs h3 block text-white">${userNum}</span> <small
											class="text-muted"><fmt:message key="jsp.views.institution.personal.index.label2"/></small>
									</div>
								</div>
								<div class="col-xs-4">
									<div class="padder-v">
										<span class="m-b-xs h3 block text-white">${deviceNum}</span> <small
											class="text-muted"><fmt:message key="jsp.views.institution.personal.index.label3"/></small>
									</div>
								</div>
							</div>
						</footer>
					</section>
				</div>
			</section>
		</section>
	</aside>
	<aside class="col-lg-4 b-l no-padder">
		<section class="vbox">
			<section class="scrollable">
				<div class="wrapper">
						  <section class="panel panel-default m-t-n-md">
                    <header class="panel-heading font-bold"><fmt:message key="jsp.views.institution.personal.index.label4"/></header>
                    <div class="panel-body">
                     <spring:url value="/institution/personal/update" var="updateUrl"/>  
                      <form id="personForm" role="form" action="${updateUrl}" method="post">
                       <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                      <div id="personFrm">
                        <div class="form-group">
                          <label><fmt:message key="jsp.views.institution.personal.index.label5"/><span class="text-danger">*</span></label>
                          <input type="email" class="form-control" name="email" value="${manager.email}" data-parsley-required data-parsley-type="email">
                        </div>
                        <div class="form-group">
                          <label><fmt:message key="jsp.views.institution.personal.index.label6"/></label>
                          <input type="text" class="form-control" name="phone" value="${manager.phone}" data-parsley-cellphone>
                        </div>
                         <div class="form-group">
                          <label><fmt:message key="jsp.views.institution.personal.index.label7"/><span class="text-danger">*</span></label>
                          <input type="text" class="form-control" name="name" value="${manager.name}" data-parsley-required data-parsley-maxlength="20">
                        </div>
                        <div class="form-group">
                          <label><fmt:message key="jsp.views.institution.personal.index.label8"/></label>
                          <input type="text" class="form-control" name="mark" value="${manager.mark}" data-parsley-maxlength="100">
                        </div>
                        </div>
                        <div id="moreless" class="hide">
	                        <div class="form-group">
	                          <label><fmt:message key="jsp.views.institution.personal.index.label9"/><span class="text-danger">*</span></label>
	                          <input type="password" class="form-control" id="source_password"  name="source_password" value="" data-parsley-required>
	                        </div>
	                        <div class="form-group">
	                          <label><fmt:message key="jsp.views.institution.personal.index.label10"/><span class="text-danger">*</span></label>
	                          <input type="password" class="form-control" id="password" name="password" value="" data-parsley-required data-parsley-alphanumber>
	                        </div>
	                        <div class="form-group">
	                          <label><fmt:message key="jsp.views.institution.personal.index.label10"/><span class="text-danger">*</span></label>
	                          <input type="password" class="form-control" id="ckpassword" data-parsley-equalto="#password" value="" data-parsley-required data-parsley-alphanumber>
	                        </div>
                        </div>
                         <button href="#moreless" class="btn btn-sm btn-default pull-right" data-toggle="class:show">
		                  <i class="fa fa-plus text"></i>
		                  <span class="text"><fmt:message key="jsp.views.institution.personal.index.label11"/></span>
		                  <i class="fa fa-minus text-active"></i>
		                  <span class="text-active"><fmt:message key="jsp.views.institution.personal.index.label12"/></span>
		                </button>
                        <button type="submit" class="btn btn-md btn-primary"><fmt:message key="jsp.views.institution.personal.index.label13"/></button>
                      </form>
                    </div>
                  </section>
					<section class="panel clearfix bg-info dk">
						<div class="panel-body">
							<a href="#" class="thumb pull-left m-r"> <img
								src="${thumbUrl}" class="img-circle b-a b-3x b-white">
							</a>
							<div class="clear">
								<a href="javascript:void(0);" class="text-info"><fmt:message key="jsp.views.institution.personal.index.label14"/> </a><small class="block text-muted">${manager.mark}</small>
							</div>
						</div>
					</section>
				</div>
			</section>
		</section>
	</aside>
</section>

