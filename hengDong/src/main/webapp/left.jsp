<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib  uri ="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html class="no-js" lang="">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="pragma" CONTENT="no-cache"> 
		<meta http-equiv="Cache-Control" CONTENT="no-cache, must-revalidate"> 
		<meta http-equiv="expires" CONTENT="0"> 
        <meta http-equiv="x-ua-compatible" content="ie=8">
        <title>多模态高通量人员特征信息识别卡口系统</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="./css/normalize.css">
        <link rel="stylesheet" href="./css/main.css">
        <style type="text/css">
        	/*侧边栏*/
			.wrapper { background: url(./img/main-bg.png)  repeat-x; }
			.navbar { width: 220px; position: absolute; top: 0; bottom: 0; left: 0; overflow-x: hidden; overflow-y: auto; }
			.nav { padding-left: 0; list-style: none; margin-bottom: 0; }
			.nav>li,
			.nav>li>a { position: relative; display: block; }
			.nav>li { border-bottom: 1px solid #c5c5c5; }
			.nav>li>a { color: #1e92d5; font-size: 16px; padding: 10px 20px 10px 57px; }
			.icon-nav { float: right; width: 10px; height: 14px; background-position: 0 0; margin-top: 4px; }
			.nav>.active>a { color: #333; background-color: #fff; }
			.nav>.active .icon-nav { background-position: -26px 0; }
			.logout { width: 220px; position: absolute; bottom: 60px; }
			.logout a { display: block; border-top: 1px solid #c5c5c5; border-bottom: 1px solid #c5c5c5; padding: 10px 20px 10px 57px; font-size: 14px; color: #1e92d5; }
			.logout .i-arrow-left { float: right; margin-top: 6px; }
        </style>
        <script type="text/javascript">
	        var timeFlag = false;
	    	var count = 1;
	    	function onTime(){		
	    		timeFlag = true;
	    		time();
	    	}
	    	function getApage(){
	    		/* var cs = count++;
	    		//$("#pageA").trigger("click");
	    		$.getJSON("/TestFrame/servlet/frame?name=a&cs="+cs,function(data){});
	    		//$("form").submit(); */
	    		alert("aa");
	    		var a_dom = $("<a href='/hengdong/FaceInfo/queryFaceInfo.do' target='right'>");
	    		a_dom.trigger("click");
	    	}
	    	function offTime(){
	    		timeFlag = false;
	    	}
	    	function time(){
	    		if(!timeFlag) return;
	    		getApage();
	    		setTimeout(time,1000);
	    	}
        </script>
    </head>
    <body>
    	<div class="wrapper">
            <div class="navbar">
                <div class="sidebar-collapse">
                    <ul class="nav">
                        <li>
                            <a href="b.jsp" target="right" onclick="offTime()">
                                <span>首页</span>
                                <span class="icon icon-nav"></span>
                            </a>
                        </li>
                        <li class="active">
                            <a href="/hengdong/FaceInfo/getRealTimeMonitor.do" target="right" onclick="onTime()">
                                <span>实时监控</span>
                                <span class="icon icon-nav"></span>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span>信息查询</span>
                                <span class="icon icon-nav"></span>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span>研判分析</span>
                                <span class="icon icon-nav"></span>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span>设备管理</span>
                                <span class="icon icon-nav"></span>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span>系统管理</span>
                                <span class="icon icon-nav"></span>
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="logout">
                    <a href="#">
                        <span class="icon i-logout"></span>
                        退出登录
                        <span class="icon i-arrow-left"></span>
                    </a>
                </div>
            </div>
      </div>     
</body>
</html>