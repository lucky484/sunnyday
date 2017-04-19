<%--
  User: Mozzie.chu
  Date: 2016/8/18
  Time: 10:20
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport"/>
<link href="<c:url value="/css/farmOrder.css"/>" type="text/css" rel="stylesheet" />
<link href="<c:url value="/css/style.css"/>" type="text/css" rel="stylesheet" />
<link href="<c:url value="/resources/lib/area-select/css/common.css"/>" rel="stylesheet"/>
<link href="<c:url value="/resources/lib/area-select/css/select2.css"/>" rel="stylesheet"/>
<link href="<c:url value="/css/weui.css"/>" type="text/css" rel="stylesheet" />
<link href="<c:url value="/css/mui.picker.css"/>" type="text/css" rel="stylesheet" />
<link href="<c:url value="/css/mui.poppicker.css"/>" type="text/css" rel="stylesheet" />
<title>订单页</title>
<style type="text/css">
body {
	font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
    font-size: 14px;
    line-height: 1.42857143;
    color: #333;
    overflow-x: hidden !important;
}

.error{
	color:red;
}
.comments {
	width: 90%;
    border: 1px solid #ccc !important;
    padding: 10px;
    margin: 10px;
}

.font_14 {
	font-size: 14px;
	font-family: 'Microsoft YaHei',Arial, Helvetica, sans-serif;
}
.font_16 {
	font-size: 16px;
	font-family: 'Microsoft YaHei',Arial, Helvetica, sans-serif;
}
.font_30 {
	font-size: 30px;
}
.line2{
	border: 0;
/*     border-bottom: 1px solid #ccc; */
}
.padding_l1{
	padding-left:10px;
}
.margin_l1{
	margin-left: 10px;
}
.bottom_order2{
	width: 100%; 
	bottom: 0; 
	position: fixed;
}
.bottom_order2 > input{
	background-color: #620d0a;
    width: 100%;
    height: 40px;
    color: #e8d586;
    font-size: 20px;
    font-weight: 700;
	text-align: -webkit-center;
}
input[type="submit"], input[type="reset"], input[type="button"], button { -webkit-appearance: none;}

.mui-btn {
    color: #fff;
    border: 1px solid #007aff;
    background-color: #007aff;
}
.border-bottom{
	border-bottom: 1px solid #e7e7e7;
}
.tb-rmb-margin{
    font-family: arial;
    font-weight: 400;
    margin-right: -4px;
    color: #F40;
}
</style>
</head>
<body style="overflow-x: hidden;">
	<div id="window" style="overflow-x:hidden;">
		<div>
		<c:if test="${type==0 }">
		<form id="FarmOrderList" action="<c:url value="/wxpay/pay-1/pre.action"/>" method="post" onsubmit="return checkForm();">
			<br/><input name="openid" value="${openid }" type="hidden" /><input name="status" value="${status }" type="hidden" />
			
			<table width="100%">
				<tr>
					<td style="padding-left: 15px; height: 35px;"> <!-- width:70px; --> <b>收&nbsp;货&nbsp;人：</b> </td>
					<td class="" colspan="2" style="padding-right: 15px;"><input
						type="text" class="line2 border-bottom" name="merchant" id="merchant"
						style="width: 100%;" value="" tabindex="1" placeholder="请输入收货人姓名" /></td>
				</tr>
				<tr>
					<td style="padding-left: 15px; height: 35px;"> <b>电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话：</b></td>
					<td class="" colspan="1" style="padding-right: 15px;"><input
						type="text" class="line2 border-bottom" name="phone" id="phone"
						style="width: 100%;" value="" tabindex="2" placeholder="请输入收货人电话" /></td>
				</tr>
				<tr>
					<td class="" style="vertical-align: center; padding-left: 15px; width: 35%; height: 35px;">
						<b>收货地址：</b>
					</td>
					<td class="" colspan="2" style="padding-right: 15px; height: 35px;">
						<div id='showCityPicker3' class="" onblur="closeCityPicker()" tabindex="3">(点击选择区域)
						</div>
						<div id='cityResult3' class="ui-alert"></div>
						<input id="town" name="town" type="hidden" value="" />
						<input id="city" name="city" type="hidden" value="" />
						<input id="province" name="province" type="hidden" value="" />
					</td>
				</tr>
				<tr>
					<td class="" style="vertical-align: center; padding-left: 15px; width: 35%; height: 35px;"> <b>详细地址：</b></td>
					<td class="" colspan="2" style="padding-right: 15px;">
						<input type="text" class="line2 border-bottom" name="address" id="address" style="width: 100%;"
						placeholder="请输入地址(XX街XX号)" value="" tabindex="4" />
					</td>
				</tr>
			</table>
			<br />			
			<img src="<c:url value="/resources/images/farmOrder/order_4.jpg"/>" style="width: 100%;" />
			<div class="magrin_t3">
				<div class="float_l margin_l1">
					<img src="<c:url value="/resources/images/farmOrder/apple/8.5kg.jpg"/>" style="width:80px;height: 80px;"/>
				</div>
				<div class="float_l" style="width:70%;">
					<c:if test="${type==0 }">
						<input class="font_16 padding_l1" id="produceName" name="produceName" style="font-weight: 700;border: 0;width:600px; " value="阿克苏苹果 8.5公斤/箱" readonly="true" />
						<input type="hidden" id="type" value="0" >
					</c:if>
					<div class="float_l padding_l1">
						<div class="font_16" id="" >
							<div class="">
								<c:if test="${type==0 }">
									<strong class="tb-promo-price">
										<em class="tb-rmb-margin">¥</em>
										<input class="tb-rmb" id="unitPrice" name="unitPrice" readonly="readonly" value="78" style="border: 0; width: 12%;" />
										<font style="font-weight: 100;">8.5公斤/箱</font>
									</strong>
									<span id="" class="tb-promo-type" style="font-size: 12px;">产地直销</span>
								</c:if>
							</div>
							<div class="tb-promo-item-ft"></div>
						</div>
					</div>
					<div class="gw_num float_l" style="margin-left: 10px; margin-top: 7px;">
							<em class="jian">-</em> 
							<input type="number" value="1" class="num" id="weight" name="weight" onkeyup="validate(this); "
								onblur="validate(this);" min="1"/> 
							<em class="add">+</em>
					</div>
					<div class="gw_num_pi float_r" style="margin-top:7px;width: 90px;">
						<span class="clear" style="padding-top: 2px;">运费 :¥</span>
						<input type="number" value="0" class="num" id="logistics" name="freight" placeholder="运费单位：元/箱" readonly="true" style="width: 50%;padding-top: 2px;"/>
					</div>
				</div>
				</div><br class="clear"/><br/>


			<div class="clear ">
				<div>
					<span class="font_16 margin_l1">买家留言</span>
				</div>
			</div>
			<div class="clear">
				<div>
					<textarea rows="10" cols="20" id="comments" class="comments" name="comments" placeholder="选填：对本次交易说明" tabindex="5" onfocus="closeCityPicker()"></textarea>
				</div>
			</div>
<!-- 			<div class="clear"> -->
<!-- 				<div> -->
<%-- 					<c:if test="${status == 1}"><!-- 8.5kg苹果,有抵用券  --> --%>
<!-- 						<span class="font_16 margin_l1">抵用券: -->
<!-- 							<input class="" id="quan" readonly="readonly" value="5" style="border: 0; width: 4%;" /> -->
<!-- 						</span>元 -->
<%-- 					</c:if> --%>
<%-- 					<c:if test="${status != 1 }"><!-- 其他苹果,无抵用券  --> --%>
<!-- 						<span class="font_16 margin_l1">无抵用券<input type="hidden" class="" id="quan"readonly="readonly" value="0"/></span> -->
<%-- 					</c:if> --%>
<!-- 				</div> -->
<!-- 			</div> -->
			<br/><br/><br/>
			
			<div class=" bottom_order2" >
				<div class="float_r font_16" style="margin-right:20px;margin-bottom: 45px;">
					共计<span> &nbsp;<span class="font_16" id="total-weight">1</span> &nbsp;</span>件商品
				</div>
				<div class="clear bottom_order2" style="background-color: #fff;border-top: 1px solid #ccc;">
					<div class="float_l font_16" style="margin-left:30px;margin-top:6px;">
						<span class="font_16">合计：¥ </span>
						<c:if test="${type==0 && status != 1}"><!-- 8.5kg苹果 ,无抵用券-->
							<input id="total" name="total" type="hidden" value="78"/>&nbsp;
							<span id="total_read" class="font_16">78</span>
						</c:if>
						<c:if test="${type==0 && status == 1}"><!-- 8.5kg苹果,有抵用券  -->
							<input id="total" name="total" type="hidden" value="78"/>&nbsp;
							<span id="total_read" class="font_16">78</span>
						</c:if>
					</div>
					<input type="submit" class="float_r" id="submit" style="width:50%; border-radius: 0px;" ></input>
				</div>
			</div>	
		</form>
		</c:if>
		<c:if test="${type!=0 }">
		<form id="FarmOrderList" action="<c:url value="/wxpay/pay-1/pre_chris.action"/>" method="post" onsubmit="return checkForm();">
			<br/><input name="openid" value="${openid }" type="hidden" /><input name="status" value="${status }" type="hidden" />
			
			<table width="100%">
				<tr>
					<td style="padding-left: 15px; height: 35px;"> <!-- width:70px; --> <b>收&nbsp;货&nbsp;人：</b> </td>
					<td class="" colspan="2" style="padding-right: 15px;"><input
						type="text" class="line2 border-bottom" name="merchant" id="merchant"
						style="width: 100%;" value="" tabindex="1" placeholder="请输入收货人姓名" /></td>
				</tr>
				<tr>
					<td style="padding-left: 15px; height: 35px;"> <b>电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话：</b></td>
					<td class="" colspan="1" style="padding-right: 15px;"><input
						type="text" class="line2 border-bottom" name="phone" id="phone"
						style="width: 100%;" value="" tabindex="2" placeholder="请输入收货人电话" /></td>
				</tr>
				<tr>
					<td class="" style="vertical-align: center; padding-left: 15px; width: 35%; height: 35px;">
						<b>收货地址：</b>
					</td>
					<td class="" colspan="2" style="padding-right: 15px; height: 35px;">
						<div id='showCityPicker3' class="" onblur="closeCityPicker()" tabindex="3">(点击选择区域)
						</div>
						<div id='cityResult3' class="ui-alert"></div>
						<input id="town" name="town" type="hidden" value="" />
						<input id="city" name="city" type="hidden" value="" />
						<input id="province" name="province" type="hidden" value="" />
					</td>
				</tr>
				<tr>
					<td class="" style="vertical-align: center; padding-left: 15px; width: 35%; height: 35px;"> <b>详细地址：</b></td>
					<td class="" colspan="2" style="padding-right: 15px;">
						<input type="text" class="line2 border-bottom" name="address" id="address" style="width: 100%;"
						placeholder="请输入地址(XX街XX号)" value="" tabindex="4" />
					</td>
				</tr>
			</table>
			<br />			
			<img src="<c:url value="/resources/images/farmOrder/order_4.jpg"/>" style="width: 100%;" />
			<div class="magrin_t3">
				<div class="float_l margin_l1">
					<c:if test="${type==1 }">
						<img src="<c:url value="/resources/images/farmOrder/apple/2.5kg.jpg"/>" style="width:80px;height: 80px;"/>
					</c:if>
					<c:if test="${type==2 }">
						<img src="<c:url value="/resources/images/farmOrder/apple/2.5kg.jpg"/>" style="width:80px;height: 80px;"/>
					</c:if>
					<c:if test="${type==3 }">
						<img src="<c:url value="/resources/images/farmOrder/apple/2.5kg.jpg"/>" style="width:80px;height: 80px;"/>
					</c:if>
				</div>
				<div class="float_l" style="width:70%;">
					<c:if test="${type==0 }">
						<input class="font_16 padding_l1" id="produceName" name="produceName" style="font-weight: 700;border: 0;width:600px; " value="阿克苏苹果 8.5公斤/箱" readonly="true" />
						<input type="hidden" id="type" value="0" >
					</c:if>
					<c:if test="${type==1 }">
						<input class="font_16 padding_l1" id="produceName" name="produceName" style="font-weight: 700;border: 0; width:600px" value="阿克苏苹果 2.5公斤/箱" readonly="true" />
						<input type="hidden" id="type" value="1" >
					</c:if>
					<c:if test="${type==2 }">
						<input class="font_16 padding_l1" id="produceName" name="produceName" style="font-weight: 700;border: 0;width:600px; " value="阿克苏苹果 精装" readonly="true" />
						<input type="hidden" id="type" value="2" >
					</c:if>
					<c:if test="${type==3 }">
						<input class="font_16 padding_l1" id="produceName" name="produceName" style="font-weight: 700;border: 0;width:600px; " value="阿克苏苹果 2.5公斤/箱" readonly="true" />
						<input type="hidden" id="type" value="3" >
					</c:if>
					<div class="float_l padding_l1">
						<div class="font_16" id="" >
							<div class="">
								<c:if test="${type==0 }">
									<strong class="tb-promo-price">
										<em class="tb-rmb text-decoration">¥98</em>
										<em class="tb-rmb-margin">¥</em>
										<input class="tb-rmb" id="unitPrice" name="unitPrice" readonly="readonly" value="88" style="border: 0; width: 12%;" />
										<font style="font-weight: 100;">8.5公斤/箱</font>
									</strong>
									<span id="" class="tb-promo-type" style="font-size: 12px;">产地直销</span>
								</c:if>
								<c:if test="${type==1 }"><!-- 圣诞组合苹果 -->
									<strong class="tb-promo-price">
										<em class="tb-rmb-margin">¥</em>
										<input class="tb-rmb" id="unitPrice" name="unitPrice" readonly="readonly" value="28.8" style="border: 0; width: 18%;" />
									</strong>
								</c:if>
								<c:if test="${type==2 }"><!-- 精装苹果 -->
									<strong class="tb-promo-price">
										<em class="tb-rmb-margin">¥</em>
										<input class="tb-rmb" id="unitPrice" name="unitPrice" readonly="readonly" value="28.8" style="border: 0; width: 18%;" />
									</strong>
								</c:if>
								<c:if test="${type==3 }"><!-- 2.5公斤/箱苹果 -->
									<strong class="tb-promo-price">
										<em class="tb-rmb-margin">¥</em>
										<input class="tb-rmb" id="unitPrice" name="unitPrice" readonly="readonly" value="28.8" style="border: 0; width: 18%;" />
									</strong>
								</c:if>
							</div>
							<div class="tb-promo-item-ft"></div>
						</div>
					</div>
					<div class="gw_num float_l" style="margin-left: 10px; margin-top: 7px;">
							<em class="jian">-</em> 
							<input type="number" value="1" class="num" id="weight" name="weight" onkeyup="validate(this); "
								onblur="validate(this);" min="1"/> 
							<em class="add">+</em>
					</div>
					<div class="gw_num_pi float_r" style="margin-top:7px;width: 90px;">
						<span class="clear" style="padding-top: 2px;">运费 :¥ </span>
						<input type="number" value="0" class="num" id="logistics" name="freight" placeholder="运费单位：元/箱" readonly="true" style="width: 50%;padding-top: 2px;"/>
					</div>
				</div>
				</div><br class="clear"/><br/>


			<div class="clear ">
				<div>
					<span class="font_16 margin_l1">买家留言</span>
				</div>
			</div>
			<div class="clear">
				<div>
					<textarea rows="10" cols="20" id="comments" class="comments" name="comments" placeholder="选填：对本次交易说明" tabindex="5" onfocus="closeCityPicker()"></textarea>
				</div>
			</div>
			<div class="clear">
				<%-- <div>
					<c:if test="${status == 1}"><!-- 8.5kg苹果,有抵用券  -->
						<span class="font_16 margin_l1">抵用券:
							<input class="" id="quan" readonly="readonly" value="5" style="border: 0; width: 4%;" />
						</span>元
					</c:if>
					<c:if test="${status != 1 }"><!-- 其他苹果,无抵用券  -->
						<span class="font_16 margin_l1">无抵用券<input type="hidden" class="" id="quan"readonly="readonly" value="0"/></span>
					</c:if>
				</div> --%>
			</div>
			<br/><br/><br/>
			
			<div class=" bottom_order2" >
				<div class="float_r font_16" style="margin-right:20px;margin-bottom: 45px;">
					共计<span> &nbsp;<span class="font_16" id="total-weight">1</span> &nbsp;</span>件商品
				</div>
				<div class="clear bottom_order2" style="background-color: #fff;border-top: 1px solid #ccc;">
					<div class="float_l font_16" style="margin-left:30px;margin-top:6px;">
						<span class="font_16">合计：¥ </span>
						<c:if test="${type==0}">
							<input id="total" name="total" type="hidden" value="88"/>&nbsp;
							<span id="total_read" class="font_16">78</span>
						</c:if>
						<c:if test="${type==1}"><!-- 精装苹果 -->
							<input id="total" name="total" type="hidden" value="28.8"/>&nbsp;
							<span id="total_read" class="font_16">28.8</span>
						</c:if>
						<c:if test="${type==2}"><!-- 精装苹果 -->
							<input id="total" name="total" type="hidden" value="28.8"/>&nbsp;
							<span id="total_read" class="font_16">28.8</span>
						</c:if>
						<c:if test="${type==3}"><!-- 精装苹果 -->
							<input id="total" name="total" type="hidden" value="28.8"/>&nbsp;
							<span id="total_read" class="font_16">28.8</span>
						</c:if>
					</div>
					<input type="submit" class="float_r" id="submit" style="width:50%; border-radius: 0px;" ></input>
				</div>
			</div>
		</form>
		</c:if>
		</div>
	</div>
	<script type="text/javascript" src="<c:url value="/Javascript/jquery-1.9.1.min.js"/>"></script>
	<script src="<c:url value="/js/mui.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/Javascript/jquery.form.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/Javascript/farmOrder.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/lib/area-select/js/area.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/lib/area-select/js/location.js"/>"></script>
	<script src="<c:url value="/resources/lib/area-select/js/select2.js"/>"></script>
	<script src="<c:url value="/resources/lib/area-select/js/select2_locale_zh-CN.js"/>"></script>
	<script src="<c:url value="/js/mui.picker.js" />"></script>
	<script src="<c:url value="/js/mui.poppicker.js" />"></script>
<%-- 	<c:if test="${type==0}"> --%>
		<script src="<c:url value="/js/city.data-3.js"/>" type="text/javascript" charset="utf-8"></script>
<%-- 	</c:if> --%>
<%-- 	<c:if test="${type!=0}"> --%>
<%-- 		<script src="<c:url value="/js/city.data-3-0.js"/>" type="text/javascript" charset="utf-8"></script> --%>
<%-- 	</c:if> --%>
		<script type="text/javascript">

		$('body').height($(window).height());
		$('body').width($(window).width());
			
		function checkForm(){
			if ($("#merchant").val() == '') {
				alert("请填写收货人");
				return false;
			}
			if ($("#phone").val() == '') {
				alert("请填写联系电话");
				return false;
			}else if (!$("#phone").val().match(/^(((1[3-8][0-9]{1})|159|153)+\d{8})$/)) {
				alert("请填写正确的手机号码");
				return false;
			}
			if ($("#province").val() == '' || $("#town").val() == '' || $("#city").val() == '') {
				alert("请选择收货地址");
				return false;
			}
			if ($("#address").val() == '') {
				alert("请填写详细地址");
				return false;
			}
			if ($("#weight").val() == '' || $("#weight").val() == 0) {
				alert("请填写您要购买的数量");
				return false;
			}
			$("#submit").attr("disabled", true);//上面的验证通过才会执行到这里禁用按钮。
			$("#submit").val("正在提交···")
			return true;
		}
	</script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
	/*
	 * 注意：
	 * 1. 所有的JS接口只能在公众号绑定的域名下调用，公众号开发者需要先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。
	 * 2. 如果发现在 Android 不能分享自定义内容，请到官网下载最新的包覆盖安装，Android 自定义分享接口需升级至 6.0.2.58 版本及以上。
	 * 3. 常见问题及完整 JS-SDK 文档地址：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html
	 *
	 * 开发中遇到问题详见文档“附录5-常见错误及解决办法”解决，如仍未能解决可通过以下渠道反馈：
	 * 邮箱地址：weixin-open@qq.com
	 * 邮件主题：【微信JS-SDK反馈】具体问题
	 * 邮件内容说明：用简明的语言描述问题所在，并交代清楚遇到该问题的场景，可附上截屏图片，微信团队会尽快处理你的反馈。
	 */
	wx.config({
		debug : false,
		appId : 'wx80bc1ec970681073',
		timestamp : '${timestamp}',
		nonceStr : '${nonceStr}',
		signature : '${signature}',
		jsApiList : [ 'checkJsApi', 'onMenuShareTimeline', 'onMenuShareAppMessage', 'onMenuShareQQ', 'onMenuShareWeibo', 'onMenuShareQZone', ]
	});
</script>
<script type="text/javascript">
	//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	var curWwwPath = window.document.location.href;
	//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	var pathName = window.document.location.pathname;
	//21
	var pos = curWwwPath.indexOf(pathName);
	//获取主机地址，如： http://localhost:8083
	var localhostPaht = curWwwPath.substring(0, pos);

	wx.ready(function() {
		wx.onMenuShareAppMessage({
			title : '阿克苏苹果冰糖心',
			desc : '阿克苏苹果冰糖心！',
			link : localhostPaht + '<c:url value="/web/farm/getFarmProducePage.action?share_openid=${openid}"/>',
			imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/header.jpg"/>',
			trigger : function(res) {
			},
			success : function(res) {
			},
			cancel : function(res) {
			},
			fail : function(res) {
				alert(JSON.stringify(res));
			}
		});

		wx.onMenuShareTimeline({
			title : '阿克苏苹果冰糖心！',
			link : localhostPaht + '<c:url value="/web/farm/getFarmProducePage.action?share_openid=${openid}"/>',
			imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/header.jpg"/>',
			trigger : function(res) {
			},
			success : function(res) {
			},
			cancel : function(res) {
			},
			fail : function(res) {
				alert(JSON.stringify(res));
			}
		});

		wx.onMenuShareQQ({
			title : '阿克苏苹果冰糖心',
			desc : '阿克苏苹果冰糖心！',
			link : localhostPaht + '<c:url value="/web/farm/getFarmProducePage.action?share_openid=${openid}"/>',
			imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/header.jpg"/>',
			success : function() {
				// 用户确认分享后执行的回调函数
			},
			cancel : function() {
				// 用户取消分享后执行的回调函数
			}
		});

		wx.onMenuShareWeibo({
			title : '阿克苏苹果冰糖心',
			desc : '阿克苏苹果冰糖心！',
			link : localhostPaht + '<c:url value="/web/farm/getFarmProducePage.action?share_openid=${openid}"/>',
			imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/header.jpg"/>',
			success : function() {
				// 用户确认分享后执行的回调函数
			},
			cancel : function() {
				// 用户取消分享后执行的回调函数
			}
		});

		wx.onMenuShareQZone({
			title : '阿克苏苹果冰糖心',
			desc : '阿克苏苹果冰糖心！',
			link : localhostPaht + '<c:url value="/web/farm/getFarmProducePage.action?share_openid=${openid}"/>',
			imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/header.jpg"/>',
			success : function() {
				// 用户确认分享后执行的回调函数
			},
			cancel : function() {
				// 用户取消分享后执行的回调函数
			}
		});
	});
</script>
</body>
</html>
