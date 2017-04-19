<%--
  User: Mozzie.chu
  Date: 2016/8/29
  Time: 10:13
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport"/>
<meta name="screen-orientation" content="portrait">
<meta name="x5-orientation"content="portrait">
<link href="<c:url value="/css/style.css"/>" type="text/css" rel="stylesheet" />
<link href="<c:url value="/css/bootstrap.min.css"/>" type="text/css" rel="stylesheet" />
<link href="<c:url value="/css/produceList.css"/>" type="text/css" rel="stylesheet" />
<title>商品列表</title>

</head>
<body>
	<div>
		<div>
			<h3 class="h3" >商品列表</h3>
			<hr class="hr-small" />
		</div>
		
<%-- <c:forEach items="${list }" var="farmProduce">		 --%>
		<div>
			<div class="margin-1" style="height: 40px;">
				<img class="who-img float-l" alt=""
					src="<c:url value="/resources/images/farmOrder/grape1.jpg"/>" />
				<h5 class="h5 padding-l5" id="wholesaler" name="wholesaler">苹果批发商--ll的小店 </h5>
			</div>
			
			<a href="<c:url value="/web/farm/getFarmProduceApplePage.action" />" style="color:#333;">
				<div>
					<img class="title-img" alt="" src="<c:url value="/resources/images/farmOrder/grape1.jpg"/>" />
				</div>
				<div class="margin-1">
					<h4 class="h4" id="produceName" name="produceName" >卖苹果嘞，新鲜又大的苹果，包甜包甜，不甜不要钱。</h4>
				</div>
				<div class="margin-1">
					<p class="p" id="synopsis" name="synopsis" >今天我们卖苹果嘞，新鲜又大的苹果，包甜包甜，不甜不要钱。走过路过，不要错过。卖苹果嘞，新鲜又大的苹果！快来买快来买，不然又上线不了啦 </p>
				</div>
				<br/>
				<div class="margin-1">
					<button class="btn-price" id="unitPrice" name="unitPrice" >¥ money</button>
				</div>
			</a>
			<hr class="hr" />
		</div>
<%-- </c:forEach>		  --%>
		<div>
			<div class="margin-1" style="height: 40px;">
				<img class="who-img float-l" alt=""
					src="<c:url value="/resources/images/farmOrder/grape1.jpg"/>" />
				<h5 class="h5 padding-l5" id="wholesaler" name="wholesaler">敦煌二墩村大漠葡萄</h5>
			</div>
			
			<a href="<c:url value="/web/farm/getFarmProducePage.action" />" style="color:#333;">
				<div>
					<img class="title-img" alt="" src="<c:url value="/resources/images/farmOrder/grape1.jpg"/>" />
				</div>
				<div class="margin-1">
					<h4 class="h4" id="produceName" name="produceName" >敦煌二墩村大漠葡萄 - 青提</h4>
				</div>
				<div class="margin-1">
					<p class="p" id="synopsis" name="synopsis" >敦煌二墩村大漠葡萄 - 青提</p>
				</div>
				<br/>
				<div class="margin-1">
					<button class="btn-price" id="unitPrice" name="unitPrice" >¥ 210</button>
				</div>
			</a>
			<hr class="hr" />
		</div>
		
		
		
		
	</div>
<script type="text/javascript" src="<c:url value="/Javascript/jquery-1.9.1.min.js"/>"></script>

</body>
</html>