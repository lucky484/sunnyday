<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String appId = request.getParameter("appid");
String timeStamp = request.getParameter("timeStamp");
String nonceStr = request.getParameter("nonceStr");
String packageValue = request.getParameter("package");
String paySign = request.getParameter("sign");
String orderNo = request.getParameter("orderNo");
%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>微信支付</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is test wx pay">
  </head>
  
  <body>
  		appId:<%=appId%><br>
  		timeStamp:<%=timeStamp%><br>
  		nonceStr:<%=nonceStr%><br>
  		packageValue:<%=packageValue%><br>
  		signType:MD5
  		paySign:<%=paySign%><br>
  		orderNo:<%=orderNo%><br>
		<br><br><br><br><br><br><br>
  			<div style="text-align:center;size:30px;"><input type="button" style="width:200px;height:80px;" value="微信支付" onclick="callpay()"></div>
  </body>
  <script type="text/javascript">
  	function callpay(){
		 WeixinJSBridge.invoke('getBrandWCPayRequest',{
  		 "appId" : "<%=appId%>",
  		 "timeStamp" : "<%=timeStamp%>", 
  		 "nonceStr" : "<%=nonceStr%>", 
  		 "package" : "<%=packageValue%>",
  		 "signType" : "MD5", 
  		 "paySign" : "<%=paySign%>" 
   			},function(res){
				WeixinJSBridge.log(res.err_msg);
	            if(res.err_msg == "get_brand_wcpay_request:ok"){// 支付成功回调  
	                alert("微信支付成功!");  
	            }else if(res.err_msg == "get_brand_wcpay_request:cancel"){// 用户取消操作  
	                alert("用户取消支付!");
	            }else{  // 支付失败
	               alert("当前支付失败!");  
	            }  
			})
		}
  </script>
</html>
