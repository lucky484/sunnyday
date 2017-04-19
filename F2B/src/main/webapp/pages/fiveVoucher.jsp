<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport"/>
    <title>好享吃抽奖</title>
    <script type="text/javascript" src="<c:url value="/Javascript/bootstrap.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/Javascript/jquery-1.9.1.min.js"/>"></script>
    <link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/bootstrap-theme.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/style.css"/>" type="text/css" rel="stylesheet" />
    <script type="text/javascript">
        $(function () {
            $('#formbackground').height($(window).height());
            $('#formbackground').width($(window).width());
        });
    </script>
</head>
<body>
<div id="formbackground" style="position:absolute; z-index:99;height:100%; width:100%">
    <img
            src="<c:url value="/images/five.png"/>" height="100%" width="100%"/>
</div>
<script>
    var i=3;
    var t;
    $(document).ready(function(){
        voteResult();
    });
    function voteResult() {
        if(i==0)
        {
            clearTimeout(t);
            window.location.href="<c:url value="/pages/share.jsp"/>";
        }
        else
        {
            i--;
            t=setTimeout(voteResult,1000);
        }

    }
</script>
</body>
</html>