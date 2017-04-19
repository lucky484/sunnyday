<%--
  Created by IntelliJ IDEA.
  User: val
  Date: 14/11/25
  Time: 17:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>好享吃抽奖</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="<c:url value="/resources/lib/bootstrap-3.3.1-dist/css/bootstrap.min.css" />" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
        #container {
            width: 858px;
            height: 400px;
            position: absolute;
            top: 50%;
            left: 50%;
            margin-left: -429px;
            margin-top: -190px;
        }
    </style>
</head>
<body style="background:#2E3E4E;">

<div id="container">
    <div></div>
    <div class="panel panel-default" style="border:none;">
        <div class="panel-body">
            <div class="pull-left">
                <img src="<c:url value="/resources/images/farmOrder/IMG_0289.JPG" />" title="" style="width:480px;height:350px;" />
            </div>
            <div class="pull-right" style="width:320px;">
                <div style="margin:28px 10px;">
                    <img src="<c:url value="/resources/images/farmOrder/logo.jpg"/>" style="width:140px;margin-left:80px;" title="" />
                </div>
                <form role="form">
                    <div class="form-group">
                        <label for="agentName">用户名：</label>
                        <input type="text" class="form-control" id="agentName" placeholder="请输入用户名">
                    </div>
                    <div class="form-group">
                        <label for="password">密&nbsp;&nbsp;&nbsp;&nbsp;码：</label>
                        <input type="password" class="form-control" id="password" placeholder="请输入密码">
                    </div>
                    <%--<div class="checkbox">
                        <label>
                            <input type="checkbox"> Check me out
                        </label>
                    </div>--%>
                    <div class="form-group">
                        <button type="button" id="sub" class="btn btn-primary" style="width:100%;margin-top:10px;">登录</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="<c:url value="/resources/lib/jquery/jquery-1.11.0.min.js" />"></script>
<script src="<c:url value="/resources/lib/bootstrap-3.3.1-dist/js/bootstrap.min.js" />"></script>
<script type="text/javascript">
    $("#sub").on('click', function () {
        $.ajax({
            type: 'post',
            url: "<c:url value="/auth/login.action" />",
            data: {"agentName": $("#agentName").val(), "password": $("#password").val()},
            dataType: 'json',
            async: false,
            success: function (data) {
                if (data) {
                    if (data.success) {
                        window.location.href = "<c:url value="/pages/index.jsp" />";
                    } else {
                        alert("用户名密码输入错误,请重新输入！");
                        $("#password").val("");
                    }
                }
            },
            error: function () {
                alert("出错了，请联系管理员");
            }
        });
    });

    $(document).on("keydown", function(event) {
        if (event.keyCode == 13) {
            $("#sub").click();
        }
    });

    if (self != top){
        window.top.location = window.location;
    }
</script>

</body>
</html>