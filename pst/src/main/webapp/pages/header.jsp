<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- BEGIN HEADER -->
<div class="header navbar navbar-inverse navbar-fixed-top">
	<!-- BEGIN TOP NAVIGATION BAR -->
	<div class="navbar-inner">
		<div class="container-fluid">
			<!-- BEGIN LOGO -->
			<a class="brand" href="index.do">项目状态跟踪系统 </a>
			<!-- END LOGO -->
			<!-- BEGIN RESPONSIVE MENU TOGGLER -->
			<a href="javascript:;" class="btn-navbar collapsed"
				data-toggle="collapse" data-target=".nav-collapse"> <img
				src="plugins/framework/image/menu-toggler.png" alt="" />
			</a>
			<!-- END RESPONSIVE MENU TOGGLER -->
			<!-- BEGIN TOP NAVIGATION MENU -->
			<ul class="nav pull-right">
				<!-- BEGIN USER LOGIN DROPDOWN -->
				<li class="dropdown user"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"> <span class="username">${sessionScope.user.showInfo }<input id="sessionUserId" type="hidden" value="${sessionScope.user.userId }" /><input id="sessionUserRole" type="hidden" value="${sessionScope.user.role.roleName }" /></span> <i class="icon-angle-down"></i>
				</a>
					<ul class="dropdown-menu">
						<li><a href="usersManagement/users/logout.do"><i class="icon-key"></i>&nbsp;&nbsp;注&nbsp;销</a></li>
						<li><a href="usersManagement/users/editPsd.do"><i class="icon-lock"></i>&nbsp;&nbsp;修改密码</a></li>
					</ul></li>
				<!-- END USER LOGIN DROPDOWN -->
			</ul>
			<!-- END TOP NAVIGATION MENU -->
		</div>
	</div>
	<!-- END TOP NAVIGATION BAR -->
</div>
<!-- END HEADER -->