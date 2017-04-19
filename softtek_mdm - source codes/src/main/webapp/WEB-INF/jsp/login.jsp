<%@page import="org.springframework.context.MessageSource"%>
<%@page
	import="org.springframework.security.core.AuthenticationException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.softtek.mdm.util.CommUtil"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title><fmt:message key="jsp.login.title" /></title>
<meta name="description"
	content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<meta name="_type" content="${type}"/>
<meta name="_message" content="${message}"/>
<spring:url value="/getCode" var="validCode" />
<meta name="_url" content="${validCode}"/>
<spring:url value="/resources/favicon.ico" var="icoUrl" />
<link rel="shortcut icon" href="${icoUrl}" mce_href="${icoUrl}"
	type="image/x-icon">
<spring:url value="/resources/css/bootstrap.css" var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<spring:url value="/resources/css/animate.css" var="animateCss" />
<link rel="stylesheet" href="${animateCss}" />
<spring:url value="/resources/css/font-awesome.min.css"
	var="fontAwesomeCss" />
<link rel="stylesheet" href="${fontAwesomeCss}" />
<spring:url value="/resources/css/icon.css" var="iconCss" />
<link rel="stylesheet" href="${iconCss}" />
<spring:url value="/resources/css/font.css" var="fontCss" />
<link rel="stylesheet" href="${fontCss}" />
<spring:url value="/resources/css/app.css" var="appCss" />
<link rel="stylesheet" href="${appCss}" />
<spring:url
	value="/resources/js/bootstrap-notify/css/bootstrap-notify.css"
	var="notifyCss" />
<link rel="stylesheet" href="${notifyCss}" />
<spring:url value="/resources/js/parsley-2.3.7/parsley.css"
	var="parsleyCss" />
<link rel="stylesheet" href="${parsleyCss}" />
<spring:url value="/resources/css/loginCss/app.css" var="loginCss" />
<link rel="stylesheet" href="${loginCss}" />
<spring:url value="/queryImgByOrgId" var="queryImgByOrgId" />
<%-- <spring:url value="/resources/images/qrcode.png" var="qrcode" /> --%>
<spring:url value="/resources/images/default.png" var="qrcode" />
<!--[if lt IE 9]>
	<spring:url value="/resources/js/ie/html5shiv.js" var="html5shivJs"/>    
	<script src="${html5shivJs}"></script>
	<spring:url value="/resources/js/ie/respond.min.js" var="respondJs"/>   
    <script src="${respondJs}"></script>
    <spring:url value="/resources/js/ie/excanvas.js" var="excanvasJs"/>
    <script src="${excanvasJs}"></script>
  <![endif]-->
</head>
<body class="bg-signin">
	<div class="login_content">
		<div class="part1">
			<section id="content" class="wrapper-md animated fadeInUp">
				<div class="row">
					<div class="container aside-xl aside-xl-top col-sm-7 login_part">
						<a class="navbar-brand block" href="javascript:void(0)"><fmt:message
								key="jsp.manager.login.header" /></a>
						<section class="m-b-lg">
							<header class="wrapper text-center" style='display: none;'>
								<strong class=""><fmt:message
										key="jsp.login.header.sub" /></strong>
							</header>
							<spring:url value="/j_spring_security_check"
								var="springSecurityCheck"></spring:url>
							<form id="signin" action="${springSecurityCheck}" method="post">
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
								<div class="list-group">
									<div class="list-group-item">
										<select name="j_organization" class="form-control no-border j_org" onchange="switch_org();">
											<c:forEach items="${orgs}" var="org">
												<option value="${org.id}" >${org.name}</option>
											</c:forEach>
										</select>
									</div>
									<div class="list-group-item">
										<input type="text" name="j_username"
											placeholder="<fmt:message key='jsp.manager.login.j_username.placeholder' />"
											class="form-control no-border user_name" autofocus value=""
											data-parsley-required="true" maxlength="30"
											data-parsley-error-message="<fmt:message key='jsp.manager.login.j_username.parsley-error-message' />">
									</div>
									<div class="list-group-item">
										<input type="password" name="j_password"
											placeholder="<fmt:message key='jsp.manager.login.j_password.placeholder'/>"
											class="form-control no-border user_password" value=""
											data-parsley-required="true" data-parsley-alphanumber
											data-parsley-error-message="<fmt:message key='jsp.manager.login.j_password.parsley-error-message'/>"
											maxlength="30">
									</div>
									<div class="list-group-item">
										<div class="form-group form-inline code_inline">
											<input id="index_code" name="validatecode"
												class="form-control no-border" type="text"
												placeholder="<fmt:message key='jsp.manager.login.validatecode.placeholder'/>"
												data-parsley-required="true" value=""
												data-parsley-error-message="<fmt:message key='jsp.manager.login.validatecode.parsley-error-message' />"
												maxlength="4" autocomplete="off" />
											<img id="imgObj" alt="validCode" src="${validCode}"
												width="100px" />
										</div>
									</div>
								</div>
								<input type="submit"
									class="login_button btn btn-lg btn-primary btn-block"
									value="<fmt:message key='jsp.manager.login.submit.txt'/>">
							</form>
						</section>
					</div>
					<div class="qr_code col-sm-5 qrcode_part">
						<spring:url value="/resources/images/softtek_logo.png"
							var="logoUrl" />
						<img id="logoUrl" src="${logoUrl}"><br />
						<img id="codeimg" src="" width="140px" height="140px" style="margin-left:42px;"><br/>
						<span class="code_des" style="margin-left:39px;"><fmt:message
								key="jsp.manager.login.qrcode.codeimg" /></span>
					</div>
				</div>
			</section>
		</div>
		<div class="part2">
			<!-- footer -->
			<footer id="footer">
				<div class="text-center padder">
					<p>
						<small><fmt:message
								key="jsp.manager.login.footer.copyright" /></small>
					</p>
				</div>
			</footer>
			<!-- / footer -->
		</div>
	</div>
	<%
		AuthenticationException error = (AuthenticationException) request.getSession()
				.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		String errorMsg = error == null ? "" : error.getMessage();
		request.getSession().removeAttribute("SPRING_SECURITY_LAST_EXCEPTION");
	%>
	<span class="hidden" id="message_error"><%=errorMsg%></span>
	<div class='notifications bottom-right notify'></div>
	<spring:url value="/resources/js/jquery.min.js" var="jqueryJs" />
	<script src="${jqueryJs}"></script>
	<!-- Bootstrap -->
	<spring:url value="/resources/js/bootstrap.js" var="bootstrapJs" />
	<script src="${bootstrapJs}"></script>
	<!-- App -->
	<spring:url value="/resources/js/app.js" var="appJs" />
	<script src="${appJs}"></script>
	<spring:url value="/resources/js/slimscroll/jquery.slimscroll.min.js"
		var="jquerySlimscrollJs" />
	<script src="${jquerySlimscrollJs}"></script>
	<spring:url value="/resources/js/app.plugin.js" var="appPluginJs" />
	<script src="${appPluginJs}"></script>
	<spring:url
		value="/resources/js/bootstrap-notify/js/bootstrap-notify.js"
		var="notifyJs" />
	<script src="${notifyJs}"></script>
	<spring:url value="/resources/js/parsley-2.3.7/parsley.js"
		var="parsleyUrl" />
	<script src="${parsleyUrl}"></script>
	<spring:url value="/resources/js/parsley-2.3.7/parsley.extend.js"
		var="parsleyExtUrl" />
	<script src="${parsleyExtUrl}"></script>
	<spring:url value="/resources/js/parsley-2.3.7/i18n/zh_cn.js"
		var="parsleyZhUrl" />
	<script src="${parsleyZhUrl}"></script>
	<spring:url value="/resources/js/parsley-2.3.7/i18n/en.js" var="parsleyEnUrl" />
	<script src="${parsleyEnUrl}"></script>
	<spring:url value="/resources/js/apps/jsp/manager/login-compress.js"
		var="loginCompressJs" />
	<script src="${loginCompressJs}"></script>
</body>
<script type="text/javascript">
var ctx = '<%=request.getContextPath() %>';
</script>

<script type="text/javascript">
var imgCtx = '<%=CommUtil.DISPLAY_LOGO_PATH%>';

$(function(){
	switch_org();
	});
	function switch_org(){
        var csrf = "${_csrf.token}";
        var qrcode = "${qrcode}";
      	var orgId = $("select[name='j_organization']").find("option:selected").val();
      	$.post("${queryImgByOrgId}",{orgId:orgId,_csrf:csrf},function(data){
      		$("#codeimg").attr("src",data.clientManager != null?data.clientManager.imageUrl:qrcode);
      	},'json');
	}
</script>
</html>