<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport" />
    <title>好享吃抽奖</title>
    <link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/style.css"/>" type="text/css" rel="stylesheet" />
</head>
<body>
<div id="formbackground" style="position:absolute; z-index:-1;"><img src="<c:url value="/images/bg/bgbg.png"/>" height="100%" width="100%" /></div>
<div style="position:absolute;z-index:9;font-size:4.3vw;color:#fff;top:18%;width:100%;height:276px;overflow-y: scroll;">
    <c:forEach var="i" items="${recordList}" varStatus="status">

        <c:if test="${i.award=='1'}">
            <div class="jiang">
                <span>&nbsp;&nbsp;${i.lotteryDate}</span>
                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;iphone6s</span>
                <span style="float:right;padding-right: 13%;" onclick="inn(${i.award},${i.drawStatus})" id="span"${i.award}">进入</span>
            </div>
        </c:if>
        <c:if test="${i.award=='2'}">
            <div class="jiang">
                <span>&nbsp;&nbsp;${i.lotteryDate}</span>
                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2元现金</span>
                <span style="float:right;padding-right: 13%;" onclick="inn(${i.award},${i.drawStatus})" id="span"${i.award}">进入</span>
            </div>
        </c:if>
        <c:if test="${i.award=='3'}">
            <div class="jiang">
                <span>&nbsp;&nbsp;${i.lotteryDate}</span>
                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;10元代金券</span>
                <span style="float:right;padding-right: 13%;" onclick="inn(${i.award},${i.drawStatus})" id="span"${i.award}">进入</span>
            </div>
        </c:if>
        <c:if test="${i.award=='4'}">
            <div class="jiang">
                <span>&nbsp;&nbsp;${i.lotteryDate}</span>
                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5元现金</span>
                <span style="float:right;padding-right: 13%;" onclick="inn(${i.award},${i.drawStatus})" id="span"${i.award}">进入</span>
            </div>
        </c:if>
        <c:if test="${i.award=='6'}">
            <div class="jiang">
                <span>&nbsp;&nbsp;${i.lotteryDate}</span>
                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1元现金</span>
                <span style="float:right;padding-right: 13%;" onclick="inn(${i.award},${i.drawStatus})" id="span"${i.award}">进入</span>
            </div>
        </c:if>
        <c:if test="${i.award=='7'}">
            <div class="jiang">
                <span>&nbsp;&nbsp;${i.lotteryDate}</span>
                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;iphone Watch</span>
                <span style="float:right;padding-right: 13%;" onclick="inn(${i.award},${i.drawStatus})" id="span"${i.award}">进入</span>
            </div>
        </c:if>
        <c:if test="${i.award=='8'}">
            <div class="jiang">
                <span>&nbsp;&nbsp;${i.lotteryDate}</span>
                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5元代金券</span>
                <span style="float:right;padding-right: 13%;" onclick="inn(${i.award},${i.drawStatus},${i.lotteryDate})" id="span"${i.award}">进入</span>
            </div>
        </c:if>
    </c:forEach>
        <%--<div class="jiang">--%>
        <%--<span>&nbsp;&nbsp;2016-3-22</span>--%>
        <%--<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;iphone Watch</span>--%>
        <%--<span style="float:right;padding-right: 13%;color: red;">已领取</span>--%>
        <%--</div>--%>
        <%--<div class="jiang">--%>
        <%--<span>&nbsp;&nbsp;2016-3-22</span>--%>
        <%--<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;10元代金券</span>--%>
        <%--<span onclick="inn('1','0')" style="float:right;padding-right: 13%;">进入</span>--%>
        <%--</div>--%>

</div>
<div style="width: 70%;text-align: center;position: absolute;left: 15%;bottom: 15%;">
    <img id="back" src="<c:url value="/images/back.png"/>" width="30%"/>
</div>
<script type="text/javascript" src="<c:url value="/Javascript/jquery-1.9.1.min.js"/>"></script>
<script>
    function inn(award,drawStatus,lotteryDate)
    {
        if(drawStatus=="0")
        {
            //iphone
            if(award=="1")
            {
                window.location.href="<c:url value="/iphone.action?prizeId="/>"+award+"&lotteryDate="+lotteryDate;
            }
            //1元现金  //5元现金      //2元现金
            if(award=="6"||award=="4"||award=="2")
            {
                window.location.href = "<c:url value="/hbReceivePrize.action?prizeId="/>"+award+"&lotteryDate="+lotteryDate;
            }
            //iWatch
            if(award=="7")
            {
                window.location.href="<c:url value="/iwatch.action?prizeId="/>"+award+"&lotteryDate="+lotteryDate;
            }
            //10元代金券    //5元代金券
            if(award=="3"||award=="8")
            {
                window.location.href = "<c:url value="/jdRecivePrize.action?prizeId="/>"+ award+"&lotteryDate="+lotteryDate;
                <%--$.post("<c:url value="/updateState.action"/>", {award:award},--%>
                        <%--function (result) {--%>
                            <%--if (result == 1) {--%>
                                <%--window.location.href = "<c:url value="/jdRecivePrize.action?prizeId="/>"+award;--%>
                            <%--}--%>
                            <%--else {--%>

                            <%--}--%>
                        <%--}--%>
                <%--);--%>
            }
        }
        if(drawStatus=="1")
        {
            //iphone    //iWatch
            if(award=="1"||award=="7")
            {
                window.location.href="<c:url value="/pages/check.jsp"/>";
            }
            //10元代金券
            if(award=="3")
            {
                window.location.href="<c:url value="/pages/jdRecivePrize2.jsp"/>";
            }
            //5元代金券
            if(award=="8")
            {
                window.location.href="<c:url value="/pages/jdRecivePrize1.jsp"/>";
            }
            //1元现金       //5元现金   //2元现金
            if(award=="6"||award=="4"||award=="2")
            {
                window.location.href = "<c:url value="/hasReceivedhbPrize.action?prizeId="/>"+award;
            }
        }

    }
    $(function () {
        $('#formbackground').height($(window).height());
        $('#formbackground').width($(window).width());

        $("#back").on("click",function()
        {
            window.location.href="<c:url value="/shitWechat.action"/>";
        });

    });
</script>
</body>
</html>