<%@page import="org.springframework.context.MessageSource"%>
<%@page import="org.springframework.security.core.AuthenticationException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <spring:url value="/resources/market/css/bootstrap.min.css" var="bootstrapCssUrl"/>
     <spring:url value="/resources/market/js/bootstrap.min.js" var="bootstrapJsUrl"/>
 
    <link href="${bootstrapCssUrl}" rel="stylesheet">
    <spring:url value="/resources/market/js/jquery.min.js" var="jqueryJsUrl"/>
    <script src="${jqueryJsUrl}"></script>
    <script src="${bootstrapJsUrl}"></script>
    <spring:url value="/resources/market/img/cregist/cregist.png" var="logoUrl"/><!-- 好享吃logo -->
    <spring:url value="/resources/market/img/cregist/password.png" var="passwordUrl"/><!-- 锁img -->
    <spring:url value="/resources/market/img/cregist/phone.png" var="phoneUrl"/><!-- 手机img -->
    <spring:url value="/resources/market/img/cregist/code.png" var="codeUrl"/><!-- 手机img -->
    <spring:url value="/resources/market/img/cregist/true.png" var="trueUrl"/><!-- 手机img -->
    <spring:url value="/resources/market/img/cregist/false.png" var="falseUrl"/><!-- 手机img -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>  
 <meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
 <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
 <style>

.logourl {
	text-align: center;
	margin-top: 50px;
	margin-bottom: 40px;
}

#phone {
	width: 65%;
}

.mui-btn-block {
	width: 100%;
	color: #fff;
    border: 1px solid #ff9000;
    background-color: #ff9000;
    height: 40px;
    font-size: 16px;
    font-weight: 700;
}

.float-l {
	float: left;
}
.clear {
	clear: both;
}
.addon {
    width: 35%;
    height: 34px;
    text-align: center;
}
</style>

</head>

<body>

<form class="form-inline" role="form">
	<div class="col-xs-12" id="top">
	 <div class="logourl"><img alt="" src="${logoUrl}"></div>
		<div class="input-group">
            <label for="" class="input-group-addon">
            	<img alt="" src="${phoneUrl}" width="20px" height="20px">
            </label>
            <input type="number" class="form-control" id="phone"  placeholder="请输入手机号">
			<input type="button" id="codeBtn"  onclick="validateCode(this)" value="获取验证码" class="input-group-addon addon"/>
        </div>
       <a style="color:red;" id="erroPhone" class="hide">请输入正确的手机号</a>
        <br/>
        
        <div class="input-group">
            <label for="" class="input-group-addon">
            	<img alt="" src="${codeUrl}" width="20px" height="20px">
            </label>
            <input type="number"  oninput="if(value.length>6)value=value.slice(0,6)" class="form-control" id="smsCode"  placeholder="请输入验证码">
        </div>
        
        <br/>
        
        <div class="input-group">
            <label for="" class="input-group-addon">
            	<img alt="" src="${passwordUrl}" width="20px" height="20px">
            </label>
            <input type="password" class="form-control" id="password" maxlength="16" placeholder="请输入密码">

        </div>
        
        <br/>
        <input type="hidden"  value="${userId}" id="introducerId">
        <input type="hidden"  value="${userType}" id="introducerType">
       
	    <input type="button" id="regist" class="mui-btn-block" value="注册">
        
	</div>
	 <div class="col-xs-12" id="msg" style="text-align:center">
	    <div class="logourl"><img alt="" id="trueOrFalseImg" src=""></div>
	 	<h1 id="trueOrFalse"></h1>
	 	<div id="FalseMsg"></div>
	 	<input type="button" calss="mui-btn-block"id="returnRegist" onclick="returnRegist()" value="返回注册页面">
 	 	<a href="http://fir.im/wh6p";>下载好享吃APP</a>
	 </div>
</form>

</body>
<spring:url value="/api/smsvalidcode/insert-smsvalid-code" var="smsCodeUrl"/>
<spring:url value="/api/user/regist-user" var="registUrl"/>
<spring:url value="/api/user/regist" var="returnRegistUrl"/>
<spring:url value="/resources/market/js/jquery.tmpl.js" var="tmplJs" />
<script src="${tmplJs}"></script>  
<script type="text/javascript">
$("#returnRegist").addClass("hide");
var wait=60;
function time(o) {
		if (wait == 0) {
			o.removeAttribute("disabled");			
			o.value="获取验证码";
			wait = 60;
		} else {
			o.setAttribute("disabled", true);
			o.value="重新发送(" + wait + ")";
			wait--;
			setTimeout(function() {
				time(o)
			},
			1000)
		}
	}
	
function validateCode(o){
	var phone = $("#phone").val();
	if(phone != "" && /^1[3|4|5|8|7]\d{9}$/.test(phone)){
		$("#erroPhone").addClass("hide");
		time(o);
		//发送验证码接口
		$.ajax({
			"dataType" : 'json',
			"type" : 'post',
			"async" : false,
			"url" : '${smsCodeUrl}',
			"data" : {
				"mobilePhone" : phone
			},
			"success" : function(data) {
				if (data) {
					
				}
			},
			"error" : function(data) {
			}
		})
	}else{
		$("#erroPhone").removeClass("hide");
	}
}
document.getElementById("codeBtn").onclick=function(){validateCode(this);}
document.getElementById("regist").onclick=function(){regist();}
document.getElementById("returnRegist").onclick=function(){returnRegist();}

function returnRegist(){
	var introducerId = $("#introducerId").val();
	var introducerType = $("#introducerType").val();
	if(introducerId != null && introducerId != "" && introducerType != null && introducerType != ""){
		window.location.href="${returnRegistUrl}?userId="+introducerId+"&userType="+introducerType;
	}else{
		window.location.href="${returnRegistUrl}";
	}
}
function regist(){
	var introducerId = $("#introducerId").val();
	var introducerType = $("#introducerType").val();
	var smsCode = $("#smsCode").val();
	var password = $("#password").val();
	var phone = $("#phone").val();
	if(phone  == undefined || phone == ""){
		alert("请输入手机号码");
		return
	}else if(!/^1[3|4|5|8]\d{9}$/.test(phone)){
		alert("请输入正确的手机号码");
		return
	}
	if(smsCode  == undefined || smsCode == ""){
		alert("请输入验证码");
		return
	}
	if(password  == undefined || password == ""){
		alert("请输入密码");
		return
	}
	$.ajax({
		"dataType" : 'json',
		"type" : 'post',
		"async" : false,
		"url" : '${registUrl}',
		"data" : {
			"introducerId" : introducerId,
			"smsCode" : smsCode,
			"password" : password,
			"phone" : phone,
			"introducerType" : introducerType
		},
		"success" : function(data) {
			$("#msg").removeClass("hide");
			$("#top").addClass("hide");
			if (data.status==200) {
				$("#trueOrFalseImg").attr("src","${trueUrl}");
				$("#trueOrFalse").text("注册成功");
			}else{
				$("#trueOrFalseImg").attr("src","${falseUrl}");
				$("#trueOrFalse").text("注册失败");
				$("#returnRegist").removeClass("hide");
				$("#FalseMsg").html(data.msg);
			}
		},
		"error" : function(data) {
		}
	});
}
</script>
<script id="registTmpl" type="text/x-jquery-tmpl">
 	
</script>


</html>
