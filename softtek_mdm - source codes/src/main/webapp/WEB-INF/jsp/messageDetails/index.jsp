<%@page import="org.springframework.context.MessageSource"%>
<%@page import="org.springframework.security.core.AuthenticationException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<spring:url value="/resources/js/jquery.min.js" var="jqueryJs" />
<script src="${jqueryJs}"></script>
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
	margin: 20px;
	padding: 20px;
	background-color: white;
	border-image: none;
	border-color: #e7eaec;
    border-image-source:  none;
    border-image-slice: initial;
    border-image-width: initial;
    border-image-outset: initial;
    border-image-repeat: initial;
    border-style: solid solid none;
    border-top-style: solid;
    border-right-style: solid;
    border-bottom-style: none;
    border-left-style: solid;
    border-width: 1px 0;
    border-top-width: 1px;
    border-right-width: 0px;
    border-bottom-width: 1px;
    border-left-width: 0px;
}
 </style>
</head>
<div class="" id="picAndWord" style="word-wrap:break-word;word-break:break-all;overflow: hidden;">
<h1 style="text-align: center;"><b>${data.msgTheme}</b></h1>
<div id="time"style="text-align: right;"></div>
 <div>
 	 <input type="hidden" id="type" name="type" value="1" />
 </div>
 <c:out value="${data.content}" escapeXml="false"/>
</div>
<!-- <body class="bg-signin" >
  <div>
  
  </div>
</body> -->

<script type="text/javascript">
   
//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function (fmt) { //author: meizz 
   var o = {
       "M+": this.getMonth() + 1, //月份 
       "d+": this.getDate(), //日 
       "h+": this.getHours(), //小时 
       "m+": this.getMinutes(), //分 
       "s+": this.getSeconds(), //秒 
       "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
       "S": this.getMilliseconds() //毫秒 
   };
   if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
   for (var k in o)
   if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
   return fmt;
}

$(function(){
	  var d = "${data.createTime}"; 
	  d=d.replace("CST","");
	  $("#time").text(formatCurrentTimeMillis(d));
	  function formatCurrentTimeMillis(dateTime) {
			return new Date(dateTime).Format("yyyy-MM-dd hh:mm:ss");
		}
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
			 var img_h = $(this).height();
				var img_w = $(this).width();
				if($(this).width()>width){
					$(this).attr("width",width);
					var height = img_h * width / img_w;
					$(this).attr("height",height);
				}else{
					$(this).attr("width",img_w);
				}
			 });
	 });
	//$("img")
});  

if (/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
	$("#picAndWord").removeClass("picAndWord");
}else{
	$("#picAndWord").addClass("picAndWord");
}
</script>
</html>

