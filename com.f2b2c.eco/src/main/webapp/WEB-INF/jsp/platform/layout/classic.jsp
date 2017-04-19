<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <meta http-equiv="Content-Type" Content="text/html; charset=utf-8">
    <title>系统管理 </title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
    <spring:url value="/resources/platform/favicon.ico" var="faviconUrl"/>
    <link rel="shortcut icon" href="${faviconUrl}">
    <spring:url value="/resources/platform/css/bootstrap.min14ed.css" var="bootstrapCssUrl"/>
    <link href="${bootstrapCssUrl}" rel="stylesheet">
    <spring:url value="/resources/platform/css/font-awesome.min93e3.css" var="fontAwesomeCssUrl"/>
    <link href="${fontAwesomeCssUrl}" rel="stylesheet">
    <spring:url value="/resources/platform/css/animate.min.css" var="animateCssUrl"/>
    <link href="${animateCssUrl}" rel="stylesheet">
    <spring:url value="/resources/platform/css/style.min862f.css" var="styleCssUrl"/>
    <link href="${styleCssUrl}" rel="stylesheet">
    <style>
	.page-tabs a {border-right: solid 0px !important;}
	nav.page-tabs .page-tabs-content {min-width:7680px;background:#2F4050;}
	.fa-angle-down{
		float: right;
		margin-top: 2px;
	}
	a:hover{
	    background: #2F4050
	}
	</style>
</head>
<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
    <div id="wrapper">
        <tiles:insertTemplate template="./aside/aside.jsp" />
        <tiles:insertTemplate template="./header/header.jsp" />
     </div>
    <spring:url value="/resources/platform/js/jquery.min.js" var="jqueryJsUrl"/>
    <script src="${jqueryJsUrl}"></script>
    <spring:url value="/resources/platform/js/bootstrap.min.js" var="bootstrapJsUrl"/>
    <script src="${bootstrapJsUrl}"></script>
    <spring:url value="/resources/platform/js/plugins/metisMenu/jquery.metisMenu.js" var="jsMetisMenuJsUrl"/>
    <script src="${jsMetisMenuJsUrl}"></script>
    <spring:url value="/resources/platform/js/plugins/slimscroll/jquery.slimscroll.min.js" var="slimscrollJsUrl"/>
    <script src="${slimscrollJsUrl}"></script>
    <spring:url value="/resources/platform/js/plugins/layer/layer.min.js" var="layerJsUrl"/>
    <script src="${layerJsUrl}"></script>
    <spring:url value="/resources/platform/js/hplus.min.js" var="hplusJsUrl"/>
    <script src="${hplusJsUrl}"></script>
    <spring:url value="/resources/platform/js/plugins/pace/pace.min.js" var="paceJsUrl"/>
    <script src="${paceJsUrl}"></script>
    <script type="text/javascript">
	    var path = '<%=path%>';
	    $(function(){
	    	loadMenu();
	    });
	    //动态的加载菜单，包括二级菜单，三级菜单等等，无限递归，IE浏览器注意内存溢出
		function loadMenu(){
			var liStr = "";
			//从后台获取菜单
			$.post(path + "/farm/fMenu/getMenuByUserId",function(data){
				if(data.list != null){
					for(var i=0;i<data.list.length;i++){
						if(data.list[i].parentId == null){
						//	var menuImg = data.list[i].icon ?'<img src="'+data.list[i].icon+'" />' : ""; 
							liStr += '<li><a style="color: #a7b1c2;font-weight: 600;padding: 14px 20px 14px 25px;" onclick="showSubNav(this);"><img src="'+path+data.list[i].icon+'" width = "13px" height="12px" style="margin-bottom: 5px;margin-right: 5px;" /><span class="nav-label">'+data.list[i].menuName+'</span><span class="fa arrow"></span></a>';
							liStr += loadSubNav(data.list[i].id,data.list,data.list[i].menuName);
						}
					}
					$("#side-menu li:gt(0)").html(liStr);
				}
			},'json');
		}
		//加载子菜单
		function loadSubNav(parentId,list,parentMenuName){
		     	var liStr="";
				var childMenu=[];
				for(var j=0;j<list.length;j++){
					if(list[j].parentId == parentId){
						childMenu.push(list[j]);
					}
				}
				if(childMenu.length<=0){			
					liStr+='</li>'
					return liStr;	
				}
				liStr='<ul class="nav nav-second-level" style="display:none">';
				for(var a=0;a<childMenu.length;a++){   
						var url = childMenu[a]["href"];
						liStr+='<li><a class="J_menuItem" ref="'+path+childMenu[a]["href"]+'" onclick="link_click(this)">'+childMenu[a]["menuName"]+'</a>';
						//liStr+='<li class="sub-navitem"><a id="'+childMenu[a]["menuId"]+'" href="'+childMenu[a]["href"]+'" rel="'+ parentMenuName + "-" + childMenu[a]["menuName"]+'">'+childMenu[a]["menuName"]+'</a>'
					liStr+=loadSubNav(childMenu[a]["id"],list,childMenu[a]["menuName"]);
				}
				liStr+='</ul>'
				liStr+='</li>'
				return liStr;
		}
		
		//父菜单click事件
		function showSubNav(obj){
			if($(obj).find("span:eq(1)").hasClass("arrow")){
				$(".nav-second-level").hide();    //切换到别的菜单时，先关闭之前打开的菜单
				$(".nav-second-level").prev().find("span:eq(1)").attr("class","fa arrow"); 
				$(obj).next().show();
				$(obj).find("span:eq(1)").attr("class","fa fa-angle-down");
			}else{
				$(obj).next().hide();
				$(obj).find("span:eq(1)").attr("class","fa arrow");
			}
		}
		//iframe嵌套
		function link_click(obj){
			 if($(obj).attr("ref")!=undefined && $(obj).attr("ref").trim().length>0){
				    $(".nav-second-level > li").removeAttr("style");  //移除原先绑定好的鼠标hover事件
				    $(".J_menuItem").removeAttr("style");  //先移除原先菜单的高亮样式
					window.sessionStorage.clear();
					$(".J_iframe").attr("src",$(obj).attr("ref").trim());
					$(".J_menuTitle").text($(obj).text().trim());
					$(obj).parent().attr("style","background-color:#293846;");//在点击这个菜单时，将鼠标的hover事件，绑定到li上
					$(obj).attr("style","color:#fff;");   //给当前菜单添加高亮的样式
			 }
		}
	</script>
</body>
</html>
