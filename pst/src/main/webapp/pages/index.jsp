<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" href="plugins/framework/css/bootstrap.min.css">
<link rel="stylesheet" href="plugins/framework/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="plugins/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="plugins/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="plugins/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="plugins/static/h-ui.admin/skin/default/skin.css"/>
<link rel="stylesheet" type="text/css" href="plugins/static/h-ui.admin/css/style.css" />
<title>项目管理</title>
<%-- <jsp:include page="_header.jsp"></jsp:include> --%>
</head>
<body>
<header class="navbar-wrapper">
	<div class="navbar navbar-fixed-top">
		<div class="container-fluid cl"> <span class="logo navbar-logo f-l mr-10 hidden-xs" style="font-size:25px;">项目状态跟踪系统</span>
			<nav class="nav navbar-nav">
			</nav>
			<nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
				<ul class="cl" style="float:right;">
					<li class="dropDown dropDown_hover"> <a href="#" class="dropDown_A">${sessionScope.user.showInfo }<input id="sessionUserId" type="hidden" value="${sessionScope.user.userId }" /><input id="sessionUserRole" type="hidden" value="${sessionScope.user.role.roleName }" /> <i class="Hui-iconfont">&#xe6d5;</i></a>
						<ul class="dropDown-menu menu radius box-shadow">
							<li><a href="#">个人信息</a></li>
							<li><a href="#">修改密码</a></li>
						</ul>
					</li>
					<li id="Hui-msg"> <a href="#" title="退出"><i class="icon-signout" style="font-size:18px"></i><span>退出</span></a> </li>
				</ul>
			</nav>
		</div>
	</div>
</header>
<aside class="Hui-aside">
	<div class="menu_dropdown bk_2">
		<dl id="menu-article">
			<dt><i class="Hui-iconfont">&#xe616;</i> 项目管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul id="menuUl">
					<li><a data-href="/projectsManagement/projects/projectsList.do" data-title="项目列表" class="_link">项目列表</a></li>
					<li><a data-href="/projectsManagement/customers/customerList.do" data-title="客户列表" class="_link">客户列表</a></li>
					<li><a data-href="/projectsManagement/projectManager/projectManagerList.do" data-title="项目经理" class="_link">项目经理</a></li>
					<li><a data-href="/projectsManagement/finance/financeList.do" data-title="财务管理" class="_link">财务管理</a></li>
					<li><a data-href="/projectsManagement/implementManager/implementManagerList.do" data-title="客户经理" class="_link">客户经理</a></li>
					<li><a data-href="article-list.html" data-title="外包管理">外包管理</a></li>
					<li><a data-href="article-list.html" data-title="机会管理">机会管理</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-system">
			<dt><i class="Hui-iconfont">&#xe62e;</i> 系统管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a data-title="用户管理">用户管理</a></li>
					<li><a data-title="日志管理">日志管理</a></li>
				</ul>
			</dd>
		</dl>
	</div>
</aside>
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displayMenu(this)"></a></div>
<section class="Hui-article-box">
	<div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
		<div class="Hui-tabNav-wp">
		</div>
		<div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
	</div>
	<div id="iframe_box" class="Hui-article">
		<div class="show_iframe">
			<iframe src="<c:url value="/projectsManagement/projects/projectsList.do"/> " class="span12" style="height:100%; width:100%; border:none;" name="mainframe" id="mainframe"></iframe>
		</div>
	</div>
</section>
<script type="text/javascript" src="plugins/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="plugins/lay/layer.js"></script>
<script type="text/javascript" src="plugins/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="plugins/static/h-ui.admin/js/H-ui.admin.js"></script>
<%-- <jsp:include page="_footer.jsp"></jsp:include> --%>
<!-- 左侧菜单事件隐藏事件 -->
<script type="text/javascript">
var basePath = '<%=basePath%>';
$(function(){
	
});
function displayMenu(obj) {
	if ($(obj).hasClass("open")) {
		$(obj).removeClass("open");
		$("body").removeClass("big-page")
	} else {
		$(obj).addClass("open");
		$("body").addClass("big-page")
	}
}
$("._link").click(function(){
	var url = $(this).attr("data-href");
	$("#menuUl").children().find("a").removeClass("menu-selected");
	$(this).addClass("menu-selected");
	window.top.window.document.getElementById('mainframe').src = basePath+url;
	return false;
});
</script>
</body>
</html>