<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html class="no-js" lang="zh-cn">
    <head>
        <base href="<%=basePath%>">
        <meta charset="utf-8">
        <meta http-equiv="pragma" CONTENT="no-cache"> 
		<meta http-equiv="Cache-Control" CONTENT="no-cache, must-revalidate"> 
		<meta http-equiv="expires" CONTENT="0"> 
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <meta name="apple-mobile-web-app-capable" content="yes"/>
        <title>多模态高通量人员特征信息识别卡口系统</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
		<meta charset="utf-8">
		<meta name="renderer" content="webkit|ie-comp|ie-stand">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta http-equiv="Cache-Control" content="no-siteapp" />
<!-- 		[if lt IE 9]> -->
		<script type="text/javascript" src="lib/html5.js"></script>
		<script type="text/javascript" src="lib/respond.min.js"></script>
		<script type="text/javascript" src="lib/respond.min.js"></script>
		<script src="./js/index.js"></script>
<!--  		<script type="text/javascript" src="lib/PIE_IE678.js"></script> -->
<!-- 		<![endif] -->
		<link href="css/H-ui.min.css" rel="stylesheet" type="text/css" />
		<link href="css/H-ui.admin.css" rel="stylesheet" type="text/css" />
		<link href="skin/default/skin.css" rel="stylesheet" type="text/css" id="skin" />
		<link href="lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
		<link href="css/style.css" rel="stylesheet" type="text/css" />		
        <link rel="stylesheet" href="css/normalize.css">
        <link rel="stylesheet" href="css/main.css">
    </head>
<body onload="refreshMenus()">
<input type="hidden" name="warningTime" id="warningTime" value="">
        <!-- 头部 -->
        <div class="header">
            <div class="container">
                <div class="brand">
                    <img src="img/slogan.png" alt="多模态高通量人员特征信息识别卡口系统">
                </div>
                <div class="time">
                    当前时间 : <span id="curr-time">2015-12-01  12: 00: 00</span>
                </div>
            </div>
        </div>	
<div class="dislpayArrowtop"><a class="pngfixtop open" href="javascript:void(0);" onClick="displaytopnavbar(this)"></a></div>
        <!-- 头部 end -->	
<aside class="Hui-aside">
	<input runat="server" id="divScrollValue" type="hidden" value="" />
	<div class="menu_dropdown bk_2">
		<dl id="menu-picture">
			<dt><i class="Hui-iconfont">&#xe620;</i> 实时监控<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a data-url="<c:url value="/AnalyzingController/EquipmentMonitoring.do?title=2"/>" class="link_url">设备一览</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-product" style="display:none">
			<dt><i class="Hui-iconfont">&#xe620;</i> 信息查询<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li id="syndromeInfoQuery" style="display:none"><a data-url="<c:url value="/complexQuery/getComplexQueryList.do"/>" class="link_url">综合信息查询</a></li>
					<li id="idCardInfoQuery" style="display:none"><a data-url="<c:url value="/InquiryIdCardInfo/InquiryIdCardInfo.do"/>" class="link_url">身份证信息查询</a></li>
					<li id="faceInfoQuery" style="display:none"><a data-url="<c:url value="/SearchFaceInfo/getSearchFaceInfo.do"/>" class="link_url">人脸信息查询</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-comments" style="display:none">
			<dt><i class="Hui-iconfont">&#xe622;</i> 研判分析<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li id="picCompare" style="display:none"><a data-url="<c:url value="/AnalyzingController/AnalyzingFaceinfo.do"/>" class="link_url">照片对比</a></li>
					<li id="idCardCompare" style="display:none"><a data-url="<c:url value="/AnalyzingController/AnalyzingIDCard.do"/>" class="link_url">身份证号对比</a></li>
					<li id="collectTimesStatistics" style="display:none"><a data-url="<c:url value="/AnalyzingController/CollectionCount.do"/>" class="link_url">采集次数统计</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-member" style="display:none">
			<dt><i class="Hui-iconfont">&#xe60d;</i> 设备管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li id="subdeviceManage" style="display:none"><a data-url="<c:url value="/ipc/equips.do"/>" class="link_url">设备管理</a></li>
					<li id="deviceStatusMonitor" style="display:none"><a data-url="<c:url value="/AnalyzingController/EquipmentMonitoring.do?title=1"/>" class="link_url">设备运行状态监控</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-admin" style="display:none">
			<dt><i class="Hui-iconfont">&#xe62d;</i> 系统管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li id="areaManage" style="display:none"><a data-url="<c:url value="/CollectParamManage/AreaManagement.do"/>" class="link_url">辖区管理</a></li>
					<li id="userManage" style="display:none"><a data-url="<c:url value="/CollectParamManage/UserManage.do"/>" class="link_url">用户管理</a></li>
					<li id="roleManage" style="display:none"><a data-url="<c:url value="/CollectParamManage/chmod.do"/>" class="link_url">角色权限管理</a></li>
					<li id="logManage" style="display:none"><a data-url="<c:url value="/CollectParamManage/LogManagement.do"/>" class="link_url">日志管理</a></li>
					<li id="collectParamsManage" style="display:none"><a data-url="<c:url value="/CollectParamManage/collectParamManage.do"/>" class="link_url">采集参数管理</a></li>
					<li id="tempControl" style="display:none"><a data-url="<c:url value="/TempCtrlController/getTempCtrl.do"/>" class="link_url">临时测试人员管理</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="logOut">
			<dt><i class="Hui-iconfont">&#xe61a;</i> <a onclick="logout()">退出登录</a><i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
		</dl>
	</div>
	<!-- 预警 -->
	<div id="face" style="float:left;width:95%;display:none;border: 1px solid gray;margin-top: 10px;background-color: yellow;margin-left: 3px;">
		
	</div>
	<div id="card" style="float:left;width:95%;display:none;border: 1px solid gray;margin-top: 10px;background-color: yellow;margin-left: 3px;">
		
	</div>
	<div id="faceCard" style="float:left;width:95%;display:none;border: 1px solid gray;margin-top: 10px;background-color: yellow;margin-left: 3px;">
		
	</div>
</aside>
<div class="dislpayArrow"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>

<section class="Hui-article-box">
	<div id="Hui-tabNav" class="Hui-tabNav">
		<div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
	</div>
	<div id="iframe_box" class="Hui-article">
		<div class="show_iframe">
			 <iframe src="<c:url value="/AnalyzingController/EquipmentMonitoring.do"/> " class="span12" style="height:100%; width:100%; border:none;" name="mainframe" id="mainframe"></iframe>
		</div>
	</div>
</section>
    	<script src="js/vendor/jquery-1.11.3.min.js"></script>
		<script src="js/plugins.js"></script>
	    <script src="js/main.js"></script>
		<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script> 
		<script type="text/javascript" src="lib/layer/1.9.3/layer.js"></script> 
		<script type="text/javascript" src="js/H-ui.js"></script> 
		<script type="text/javascript" src="js/H-ui.admin.js"></script> 	
        <script>
        var flag = 0;
		/*top菜单-隐藏显示*/
		function displaytopnavbar(obj){
		if($(obj).hasClass("open")){
		$(obj).removeClass("open");
			$(".header").fadeOut("fast");
			$(".Hui-aside").css("margin-top","-44px");
			$(".Hui-article-box").css("margin-top","-78px");
			$(".dislpayArrowtop").css("margin-top","0px");
			$(".dislpayArrowtop .pngfixtop").css("background-position-x", "-61px");
			
			
	}else{
		$(obj).addClass("open");
			$(".header").fadeIn("fast");
			$(".Hui-aside").css("margin-top","18px");
			$(".Hui-article-box").css("margin-top","-15px");
			$(".dislpayArrowtop").css("margin-top","62px");
			$(".dislpayArrowtop .pngfixtop").css("background-position-x", "0px");			
	}		
		}
		
            function getdate(id) {
                  var today = new Date();
                  var y = today.toLocaleDateString();
                  var h = today.getHours();
                  var m = today.getMinutes();
                  var s = today.getSeconds();
                  if (s < 10) {
                        s = "0" + s;
                  }
                  if (m < 10) {
                        m = "0" + m;
                  }
                  $(id).text(y + " " +h + " : " + m + " : " + s);
                  setTimeout(function() {
                        getdate(id);
                      //得到预警值
                        if(flag%10==0){
                        	warning();
                	  	}
                        flag++;
                  }, 500);
            }
            $(function(){
            	getdate('#curr-time');
            	
            });
            /**
             * 预警
             */
            function warning(){
            	//alert($("#warningTime").val());
                $.get("/hengdong/FaceInfo/indexWarningInfo.do?warningTime="+$("#warningTime").val(), function(data) {
                	$("#warningTime").val(data.timeStr);
                	var face = data.faceComResult;
                	var card = data.idCardComResult;
                	var faceCard = data.faceCardComResult;
                	if(face!=null){
                		//if($("#face").style.display =="none"){
                			//$("#face").style.display="inline";
                		//}
                		
                		if(face.length > 0){
                			
                			$("#face").children().each(function(index){
                      			$(this).remove();
                      		});
                			$("#face").show();
                			var title = "<span style='display: inline;'>人像预警信息 <img src='./img/button.png' onclick='closeFace()' style='float:right;'></span>";
                			$("#face").append(title);
                			for(var i=0;i<face.length;i++){
                				var span = "<span style='display: inline-block;'>"+face[i].receiveTimeStr+"&nbsp;&nbsp;"+face[i].deviceCode+"&nbsp;&nbsp;出现人像信息比中</span>";
                				$("#face").append(span);
                			}
                		}
                	}
					if(card!=null){
						//if($("#card").style.display=="none"){
                			//$("#card").style.display="inline";
                		//} 
						
						if(card.length > 0){
							$("#card").show();
                			$("#card").children().each(function(index){
                      			$(this).remove();
                      		});
                			var title = "<span style='display: inline;'>身份证预警信息 <img src='./img/button.png' onclick='closeCard()' style='float:right;'></span>";
                			$("#card").append(title);
                			for(var i=0;i<card.length;i++){
                				var span = "<span style='display: inline-block;'>"+card[i].receiveTimeStr+"&nbsp;&nbsp;"+card[i].deviceCode+"&nbsp;&nbsp;出现身份证信息比中</span>";
                				$("#card").append(span);
                			}
                		}
					}
					if(faceCard!=null){
						//if($("#faceCard").style.display=="none"){
                			//$("#faceCard").style.display="inline";
                		//}
                		
						if(faceCard.length > 0){
							$("#faceCard").show();
                			$("#faceCard").children().each(function(index){
                      			$(this).remove();
                      		});
                			var title = "<span style='display: inline;'>人证不一致预警信息 <img src='./img/button.png' onclick='closeFaceCard()' style='float:right;'></span>";
                			$("#faceCard").append(title);
                			for(var i=0;i<faceCard.length;i++){
                				var span = "<span style='display: inline-block;'>"+faceCard[i].receiveTimeStr+"&nbsp;&nbsp;"+faceCard[i].deviceCode+"&nbsp;&nbsp;人证不一致</span>";
                				$("#faceCard").append(span);
                			}
                		}
					}
					
				},'json');
           }
            
            function closeFace(){
            	$("#face").children().each(function(index){
          			$(this).remove();
          		});
            	$("#face").hide();
            }
            function closeCard(){
            	$("#card").children().each(function(index){
          			$(this).remove();
          		});
            	$("#card").hide();
            }
            function closeFaceCard(){
            	$("#faceCard").children().each(function(index){
          			$(this).remove();
          		});
            	$("#faceCard").hide();
            }
            
                $(".link_url").on("click", function () {
                	$(".link_url").parent("li").removeClass("active");
                	$(".link_url").parent("li").removeClass("background-color");
                    $(this).parent("li").addClass("active");
                    $(this).parent("li").addClass("background-color");
                    window.top.window.document.getElementById('mainframe').src = $(this).attr("data-url");
                    return false;
                });
               
        </script>
    </body>
</html>
