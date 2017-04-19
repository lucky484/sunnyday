<%@page import="org.springframework.context.MessageSource"%>
<%@page import="org.springframework.security.core.AuthenticationException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <spring:url value="/resources/market/js/jquery.min.js" var="jqueryJsUrl"/>
      <spring:url value="/resources/market/js/plugins/qrcodejs-master/qrcode.js" var="qrcodeJsUrl"/>
    <script src="${jqueryJsUrl}"></script>
     <script src="${qrcodeJsUrl}"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>  
  <meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
 <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
 <style>
 h3 {
 	text-align: center;
 }
 p {
 	color:#52514C;
 	line-height: 23px;
 }

.picAndWord{
	font-family: Microsoft Yahei;
	text-align: center;
	margin-top: 10%;
	}
#qrcode{
	width: 300px;
	height: 300px;
	text-align: center;
    margin-left: auto;
    margin-right: auto;
	}
 </style>
</head>
<div class="picAndWord" style="word-wrap:break-word;word-break:break-all;overflow: hidden;">
<h1> 我的二维码 </h1>
 <div id="qrcode">
 	 <input type="hidden" id="type" name="type" value="1" />
 </div>
</div>
<!-- <body class="bg-signin" >
  <div>
  
  </div>
</body> -->

<script type="text/javascript">
var url="${url}";
  $(function(){
	 var width=$(window).width();
	 $(".picAndWord").attr("width",width);
	 var imgdefereds=[];
	 $('img').each(function(){
	  var dfd=$.Deferred();
	  $(this).bind('load',function(){
	   dfd.resolve();
	  }).bind('error',function(){
	  //图片加载错误，加入错误处理
	  // dfd.resolve();
	  })
	  if(this.complete) setTimeout(function(){
	   dfd.resolve();
	  },1000);
	  imgdefereds.push(dfd);
	 })
	 $.when.apply(null,imgdefereds).done(function(){
		 $("img").each(function(){
				if($(this).width()>width){
					var img_h = $(this).height();
					var img_w = $(this).width()
					$(this).attr("width",width);
					var height = img_h * width / img_w;
					$(this).attr("height",height);
				};
			 });
		 
	 });
	//$("img")
});  
  
  window.onload =function(){
      var qrcode = new QRCode(document.getElementById("qrcode"), {
          width : 300,//设置宽高
          height : 300
      });
      qrcode.makeCode(url);
  }

</script>
</html>

