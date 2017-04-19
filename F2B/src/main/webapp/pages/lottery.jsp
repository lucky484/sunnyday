<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/25
  Time: 10:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta
	content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"
	name="viewport" />
<title>好享吃抽奖</title>
<link href="<c:url value="/turnplate/style.css"/>" type="text/css"
	rel="stylesheet" />
<style>
.box {
	position: absolute;
	text-align: center; width : 100%;
	top: 75%;
	width: 100%;
}
.banner {
	margin-top:30%;
}
.list_lh{ max-height:70px; overflow:hidden; z-index:20;min-height: 10px;}
.list_lh li{ padding:0px; }
.list_lh li p{ height:10%; line-height:25px;}
</style>
<%-- <link href="<c:url value="/css/lottery.css"/>" type="text/css" rel="stylesheet" /> --%>
<script>
	var _hmt = _hmt || [];
	(function() {
		var hm = document.createElement("script");
		hm.src = "//hm.baidu.com/hm.js?bb3d9f7871f6122cd0cd7b8e3cc5d534";
		var s = document.getElementsByTagName("script")[0];
		s.parentNode.insertBefore(hm, s);
	})();
</script>
<script type="text/javascript">
	window.onload = function() {
		if (!isWeiXin()) {
			$("body").html("");
			alert("请用微信打开！");
		}
	}
	function isWeiXin() {
		var ua = window.navigator.userAgent.toLowerCase();
		if (ua.match(/MicroMessenger/i) == 'micromessenger') {
			return true;
		} else {
			return false;
		}
	}
</script>
</head>

<body>
	<div id="formbackground" style="position: absolute; z-index: -1;">
		<img src="<c:url value="/images/background.jpg"/>" height="100%"
			width="100%" />
	</div>
	<div
		style="font: normal bold 20px simhei; position: absolute; left: 27%; top: 22%;">
		您还有<span style="color: red;">${rewardTimes}</span>次抽奖机会！
	</div>
	<img src="<c:url value='/turnplate/images/1.png' />"
		tppabs="http://www.17sucai.com/preview/222076/2015-05-28/turnplate/images/1.png"
		id="shan-img" style="display: none;" />
	<img src="<c:url value='/turnplate/images/2.png' />"
		tppabs="http://www.17sucai.com/preview/222076/2015-05-28/turnplate/images/2.png"
		id="sorry-img" style="display: none;" />
	<div class="banner">
		<div class="turnplate"
			style="background-image: url(images/turnplate-bg.png); background-size: 100% 100%;">
			<canvas class="item" id="wheelcanvas" width="422px" height="422px"></canvas>
			<img class="pointer"
				src="<c:url value='/turnplate/images/turnplate-pointer.png' />"
				tppabs="http://www.17sucai.com/preview/222076/2015-05-28/turnplate/images/turnplate-pointer.png" />
		</div>
	</div>
	<div class="box">
		<!-- 代码开始 -->
		<div class="list_lh">
			<ul></ul>
		</div>
	</div>
	<input type="hidden" id="reward" value="${reward}" />
	<input type="hidden" id="orderNo" value="${orderNo}" />
	<input type="hidden" id="rewardTimes" value="${rewardTimes}" />
	<script type="text/javascript"
		src="<c:url value="/Javascript/jquery-1.9.1.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/turnplate/js/awardRotate.js"/>"></script>
	<script type="text/javascript">
		$('#formbackground').height($(window).height());
		$('#formbackground').width($(window).width());
		var turnplate = {
			restaraunts : [], //大转盘奖品名称
			colors : [], //大转盘奖品区块对应背景颜色
			outsideRadius : 160, //大转盘外圆的半径
			textRadius : 130, //大转盘奖品位置距离圆心的距离
			insideRadius : 70, //大转盘内圆的半径
			startAngle : 0, //开始角度
			bRotate : false
		//false:停止;ture:旋转
		};

		$(document).ready(function() {
			//动态添加大转盘的奖品与奖品区域背景颜色
			turnplate.restaraunts = [ 5, 10, 50, 100, 500, 1000 ];
			turnplate.colors = ["#FFFFFF", "#FFF4D6", "#FFFFFF", "#FFF4D6", "#FFFFFF", "#FFF4D6"];

			function getIndex(val) {
				var i = -1;
				$.each(turnplate.restaraunts, function(index, item) {
					if (item == val) {
						i = index;
						return;
					}
				});
				return i;
			}

			var rotateTimeOut = function() {
				$('#wheelcanvas').rotate({
					angle : 0,
					animateTo : 2160,
					duration : 8000,
					callback : function() {
						alert('网络超时，请检查您的网络设置！');
					}
				});
			};

			//旋转转盘 item:奖品位置; txt：提示语;
			var rotateFn = function(item, txt) {
				var angles = item * (360 / turnplate.restaraunts.length) - (360 / (turnplate.restaraunts.length * 2));
				if (angles < 270) {
					angles = 270 - angles;
				} else {
					angles = 360 - angles + 270;
				}
				$('#wheelcanvas').stopRotate();
				$('#wheelcanvas').rotate({
					angle : 0,
					animateTo : angles + 1800,
					duration : 8000,
					callback : function() {
						//alert(txt);
						$.ajax({
							type : "post",
							url : "<c:url value="/getLotteryResult.action"/>",
							async : false,
							data : {
								reward : $("#reward").val(),
								orderNo : $("#orderNo").val()
							},
							dataType : "json",
							timeout : 1000,
							success : function(data) {
								if (!isNaN(data)) {
									if (parseInt(data) == -1) {
										alert("对不起，您已抽过奖！");
									} else if (parseInt(data) == -2) {
										alert("对不起，只有先购买才有抽奖机会！");
									} else if (parseInt(data) == -4) {
										alert("系统检测到您有异常操作，请确保您已关注本公众号并已成功下单！");
									} else {
										window.location.href = "<c:url value='/hbReceivePrize.action?openid=${openid}&reward=" + $("#reward").val() + "&orderNo=" + $("#orderNo").val() + "'/>";
									}
								}
							},
							error : function() {
								//如果方法异常，直接返回谢谢参与
							}
						});
						turnplate.bRotate = !turnplate.bRotate;
					}
				});
			};

			$('.pointer').click(function() {
				if (turnplate.bRotate)
					return;
				turnplate.bRotate = !turnplate.bRotate;
				$('.pointer').attr("disabled", "disabled");
				//获取随机数(奖品个数范围内)
				//var item = rnd(1,turnplate.restaraunts.length);
				//奖品数量等于10,指针落在对应奖品区域的中心角度[252, 216, 180, 144, 108, 72, 36, 360, 324, 288]
				rotateFn(getIndex($("#reward").val()) + 1, turnplate.restaraunts[getIndex($("#reward").val())]);
				//rotateFn(item, ${reward});
				/* switch (item) {
					case 1:
						rotateFn(252, turnplate.restaraunts[0]);
						break;
					case 2:
						rotateFn(216, turnplate.restaraunts[1]);
						break;
					case 3:
						rotateFn(180, turnplate.restaraunts[2]);
						break;
					case 4:
						rotateFn(144, turnplate.restaraunts[3]);
						break;
					case 5:
						rotateFn(108, turnplate.restaraunts[4]);
						break;
					case 6:
						rotateFn(72, turnplate.restaraunts[5]);
						break;
					case 7:
						rotateFn(36, turnplate.restaraunts[6]);
						break;
					case 8:
						rotateFn(360, turnplate.restaraunts[7]);
						break;
					case 9:
						rotateFn(324, turnplate.restaraunts[8]);
						break;
					case 10:
						rotateFn(288, turnplate.restaraunts[9]);
						break;
				} */
			});
		});

		//页面所有元素加载完毕后执行drawRouletteWheel()方法对转盘进行渲染
		window.onload = function() {
			drawRouletteWheel();
		};

		function drawRouletteWheel() {
			var canvas = document.getElementById("wheelcanvas");
			if (canvas.getContext) {
				//根据奖品个数计算圆周角度
				var arc = Math.PI / (turnplate.restaraunts.length / 2);
				var ctx = canvas.getContext("2d");
				//在给定矩形内清空一个矩形
				ctx.clearRect(0, 0, 422, 422);
				//strokeStyle 属性设置或返回用于笔触的颜色、渐变或模式  
				ctx.strokeStyle = "#FFBE04";
				//font 属性设置或返回画布上文本内容的当前字体属性
				ctx.font = '16px Microsoft YaHei';
				for (var i = 0; i < turnplate.restaraunts.length; i++) {
					var angle = turnplate.startAngle + i * arc;
					ctx.fillStyle = turnplate.colors[i];
					ctx.beginPath();
					//arc(x,y,r,起始角,结束角,绘制方向) 方法创建弧/曲线（用于创建圆或部分圆）    
					ctx.arc(211, 211, turnplate.outsideRadius, angle, angle + arc, false);
					ctx.arc(211, 211, turnplate.insideRadius, angle + arc, angle, true);
					ctx.stroke();
					ctx.fill();
					//锁画布(为了保存之前的画布状态)
					ctx.save();

					//----绘制奖品开始----
					ctx.fillStyle = "#E5302F";
					var text;
					if (turnplate.restaraunts[i] == 0) {
						text = "谢谢参与";
					} else {
						text = turnplate.restaraunts[i] + "元";
					}
					var line_height = 17;
					//translate方法重新映射画布上的 (0,0) 位置
					ctx.translate(211 + Math.cos(angle + arc / 2) * turnplate.textRadius, 211 + Math.sin(angle + arc / 2) * turnplate.textRadius);

					//rotate方法旋转当前的绘图
					ctx.rotate(angle + arc / 2 + Math.PI / 2);

					/** 下面代码根据奖品类型、奖品名称长度渲染不同效果，如字体、颜色、图片效果。(具体根据实际情况改变) **/
					if (text.indexOf("M") > 0) {//流量包
						var texts = text.split("M");
						for (var j = 0; j < texts.length; j++) {
							ctx.font = j == 0 ? 'bold 20px Microsoft YaHei' : '16px Microsoft YaHei';
							if (j == 0) {
								ctx.fillText(texts[j] + "M", -ctx.measureText(texts[j] + "M").width / 2, j * line_height);
							} else {
								ctx.fillText(texts[j], -ctx.measureText(texts[j]).width / 2, j * line_height);
							}
						}
					} else if (text.length > 6) {//奖品名称长度超过一定范围 
						text = text.substring(0, 6) + "||" + text.substring(6);
						var texts = text.split("||");
						for (var j = 0; j < texts.length; j++) {
							ctx.fillText(texts[j], -ctx.measureText(texts[j]).width / 2, j * line_height);
						}
					} else {
						//在画布上绘制填色的文本。文本的默认颜色是黑色
						//measureText()方法返回包含一个对象，该对象包含以像素计的指定字体宽度
						ctx.fillText(text, -ctx.measureText(text).width / 2, 0);
					}

					//添加对应图标
					if (text.indexOf("元") > 0) {
						var img = document.getElementById("shan-img");
						img.onload = function() {
							ctx.drawImage(img, -15, 10);
						};
						ctx.drawImage(img, -15, 10);
					} else if (text.indexOf("谢谢参与") >= 0) {
						var img = document.getElementById("sorry-img");
						img.onload = function() {
							ctx.drawImage(img, -15, 10);
						};
						ctx.drawImage(img, -15, 10);
					}
					//把当前画布返回（调整）到上一个save()状态之前 
					ctx.restore();
					//----绘制奖品结束----
				}
			}
		}
	</script>
	<script type="text/javascript"
		src="<c:url value="/Javascript/scroll.js"/>"></script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script>
		$.ajax({
			type : "post",
			url : "<c:url value="/getScrollList.action"/>",
			data : {},
			dataType : "json",
			timeout : 1000,
			success : function(data) {
				$.each(data.scrollList, function(i, val) {
					$(".list_lh>ul").append($("<li ><p style=\"font-size:3vw;color: red;text-align: center;\">" + val + "</p>  </li>"));
				});
			},
			error : function() {
				$.each(lotteryList, function(i, val) {
					$(".list_lh>ul").append($("<li ><p style=\"font-size:3vw;color: red;text-align: center;\">" + val + "</p>  </li>"));
				});
			}
		});
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
				imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/apple_goods.jpg"/>',
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
				imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/apple_goods.jpg"/>',
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
				imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/apple_goods.jpg"/>',
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
				imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/apple_goods.jpg"/>',
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
				imgUrl : localhostPaht + '<c:url value="/resources/images/farmOrder/apple/apple_goods.jpg"/>',
				success : function() {
					// 用户确认分享后执行的回调函数
				},
				cancel : function() {
					// 用户取消分享后执行的回调函数
				}
			});
		});

		$("div.list_lh").myScroll({
			speed : 120, //数值越大，速度越慢
			rowHeight : 40
		//li的高度
		});
	</script>
</body>
</html>
