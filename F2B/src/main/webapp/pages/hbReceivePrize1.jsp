<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/25
  Time: 12:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport" />
    <title>好享吃抽奖</title>
    <link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/lottery.css"/>" type="text/css" rel="stylesheet" />
    <style>
        #imgMoney {
            width: 13%;
            height: 6%;
            position: absolute;
            top: 40%;
            margin-left: 44%;
            z-index: 6;
        }

        .img-money {
            position: absolute;
            top: 40%;
        }

        .btn-primary {
            width: 45%;
            height: 35%;
            position: absolute;
            top: 66%;
            left: 29%;
            font-size: 5vw;
        }
    </style>
    <script type="text/javascript" src="<c:url value="/Javascript/jquery-1.9.1.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/Javascript/jquery.base64.js"/>"></script>
    <script type="text/javascript">
        $(function () {
            var prizeId = window.location.href.split('id=')[1];
            $("#imgMoney").attr("src", "<c:url value="/images/moneyValue"/>/" + prizeId + ".png")
            $('#formbackground').height($(window).height());
            $('#formbackground').width($(window).width());
            $("#btnDraw").on("click", function () {
                $.post("<c:url value="/updateState.action"/>", {award:prizeId},
                        function (result) {
                            if (result == 1) {
                                window.location.href = "<c:url value="/pages/hasReceivedhbPrize.jsp?id="/>" + prizeId;
                            }
                            else {
                                alert("不能重复领取");

                            }
                        }
                );
            })
        });
    </script>
</head>
<body>
<div id="formbackground" style="position:absolute;height:100%; width:100%">
    <img id="imgBg" src="<c:url value="/images/bg/hbbg.jpg"/>" height="100%" width="100%" />
</div>
<img id="imgMoney" />
</body>
</html>