<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html class="no-js" lang="">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="pragma" CONTENT="no-cache"> 
		<meta http-equiv="Cache-Control" CONTENT="no-cache, must-revalidate"> 
		<meta http-equiv="expires" CONTENT="0"> 
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>多模态高通量人员特征信息识别卡口系统</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="../css/normalize.css">
        <link rel="stylesheet" href="../css/main.css">
        <link rel="stylesheet" href="../css/main_view_page.css">
    </head>
    <body>
          <div class="container wrapper">
            <div class="page-wrapper">
                <div class="page-con">
					<%-- <div class="xxcxkuang" style="width:100%;">
						<h1>信息查询|</h1>
						<h2>${comeFrom }</h2>
					</div> --%>
					<div class="match-title-box">
                            信息查询
                            <span id="comfrom">${comeFrom }</span>
                        </div>
					<div class="xxcx_right">
						<a class="xianchangruxiang">播放现场录像</a>
						<a class="xianchangruxiang" id="back">返回</a>
					</div>
                      <div class="clearfix">
                        <div class="page-hd">
                            <div class="address dib" style="font-weight: bold;">
                                采集地点：${collectSite}
                                <!--<span class="icon i-arrow-down"></span>-->
                            </div>
                        </div>
              
                    </div>
                    <div class="clearfix">
                        <div class="real-time-info">
                            <div class="real-box">
                                <div class="real-title">实时采集信息</div>
                                <div class="real-con clearfix">
									<div class="real-photo-top">
										<h2>人像采集信息</h2>
										<h3>采集时间：${collectTime}</h3>
									
									</div>
                                   <c:if test="${imgList != null}" >
										    <div class="real-time-photo pull-left">
		                                    <c:forEach items="${list}" var="list" varStatus="status">
		                                       <c:if test="${status.index == 0}">
		                                        	<img src="data:image/jpg;base64,${imgList[status.index]}" class="full" alt="">
		                                       </c:if>
		                                       <c:if test="${status.index == 1}">
		                                        	<img src="data:image/jpg;base64,${imgList[status.index]}" class="" alt="">
		                                       </c:if>
		                                       <c:if test="${status.index == 2}">
		                                        	<img src="data:image/jpg;base64,${imgList[status.index]}" class="" alt="">
		                                       </c:if>
		                                  	</c:forEach>
		                                    </div>
									</c:if>
									<c:if test="${imgList == null}" >
													<div class="real-time-photo pull-left">
			                                    	<img src="../img/default.png" class="full" alt="">
			                                     	<img src="../img/collect-fail.png" alt="">
													<img src="../img/collect-fail.png" alt=""> 
		                                    </div>
									</c:if>
									<div class="real-pull-bottom">
										<h1>身份证采集信息</h1>
										<h2>采集时间：${complexQueryModel.collectTimeStr}</h2>
									</div>
									<div class="real-pull-bottom-x">
									<c:if test="${complexQueryModel != null}">
									   <c:if test="${complexQueryModel.idCardPicStr != ''}">
										<div class="real-pull-bottom-x-left">
											<img src="data:image/jpg;base64,${complexQueryModel.idCardPicStr}">
										</div>
									   </c:if>
									    <c:if test="${complexQueryModel.idCardPicStr == ''}">
										<div class="real-pull-bottom-x-left">
											<img src="../img/collect-fail.png" alt="">
										</div>
									   </c:if>
									 </c:if>
									 <c:if test="${complexQueryModel == null}">
										<div class="real-pull-bottom-x-left">
											<img src="../img/collect-fail.png" alt="">
										</div>
									 </c:if>
									 <div class="id-spec" style="margin-bottom: 60px;">
	
                                            <div class="id-item" style="color:#fff;font-size: 12px;">
                                                <label>身份号码&nbsp;:&nbsp;</label>
                                               <span>${complexQueryModel.idCardNo }</span>
                                            </div>
                                            <div class="id-item" style="color:#fff;font-size: 12px;">
                                                <label>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名&nbsp;:&nbsp;</label>
                                                <span>${complexQueryModel.idCardName }</span>
                                            </div>
                                            <div class="id-item" style="color:#fff;font-size: 12px;">
                                                <label>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别&nbsp;:&nbsp;</label>
                                                <span>${complexQueryModel.idCardSex }</span>
                                            </div>
                                            <div class="id-item" style="color:#fff;font-size: 12px;">
                                                <label>民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族&nbsp;:&nbsp;</label>
                                               <span>${complexQueryModel.idCardNation }</span>
                                            </div>
                                            <div class="id-item" style="color:#fff;font-size: 12px;">
                                                <label>出&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;生&nbsp;:&nbsp;</label>
                                               <span>${complexQueryModel.idCardBirth }</span>
                                            </div>
                                            <div class="id-item" style="color:#fff;font-size: 12px;">
                                                <label>住&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址&nbsp;:&nbsp;</label>
                                               <span>${complexQueryModel.idCardAddress }</span>
                                            </div>
                                            <div class="id-item" style="color:#fff;font-size: 12px;">
                                                <label>签发机关&nbsp;:&nbsp;</label>
                                                <span>${complexQueryModel.cardUnit }</span>
                                            </div>
                                            <div class="id-item" style="color:#fff;font-size: 12px;">
                                                <label>采集时间：&nbsp;</label>
                                                <span>${complexQueryModel.collectTimeStr }</span>
                                            </div>
                                           
                                        </div>
										<%-- <div class="real-pull-bottom-x-right">
											<div class="real-pull-bottom-x-right_z">
												<span>身份号码</span>
												<span>姓&nbsp;&nbsp;名</span>
												<span>性&nbsp;&nbsp;别</span>
												<span>民&nbsp;&nbsp;族</span>
												<span>出&nbsp;&nbsp;生</span>
												<span>住&nbsp;&nbsp;址</span>
												<span>  </span>
												<span>签发机关</span>
												<span>有效期限</span>
											</div>
											<div class="real-pull-bottom-x-right_y">
												<span>${complexQueryModel.idCardNo }</span>
												<span>${complexQueryModel.idCardName }</span>
												<span>${complexQueryModel.idCardSex }</span>
												<span>${complexQueryModel.idCardNation }</span>
												<span>${complexQueryModel.idCardBirth }</span>
												<span>${complexQueryModel.idCardAddress }</span>
												<span></span>
												<span>${complexQueryModel.cardUnit }</span>
												<c:if test="${complexQueryModel.cardIssueDate != null}">
												<span>${complexQueryModel.cardIssueDate }~${complexQueryModel.cardExpiryDate }</span>
												</c:if>
												<c:if test="${complexQueryModel.cardIssueDate != null}">
												<span></span>
												</c:if>
											</div>
										</div> --%>
									</div>
                                    
                                </div>
                            </div>
                            
                        </div>
                        <div class="real-contrast">
                            <div class="real-boxy">
                              
                                <div class="real-title" style="font-weight: bold;">身份证比对</div>
                                <div class="real-con clearfix">
                                    <div class="contrast">
                                        <div class="sfzbd">
											 <c:if test="${idCardCompResult != null}">
                                        <c:if test="${idCardCompResult.idCardPicStr != ''}">
											<img class="sfzbd_leftimg" style="float: left;" src="data:image/jpg;base64,${idCardCompResult.idCardPicStr}">
										</c:if>
										 <c:if test="${idCardCompResult.idCardPicStr == ''}">
											<img src="../img/collect-fail.png" style="float: left;" alt="">
										</c:if>
										</c:if>
										 <c:if test="${idCardCompResult == null}">
										 <img src="../img/collect-fail.png"  style="float: left;" alt="">
										 </c:if>
											<div class="sfzbd_middle">
											<p class="sfzbd_middleh1" style="font-weight: bold;">身份证缉控信息</p>
											<table style="font-size:12px;">

											<c:if test="${idCardCompResult.compareBaseID == '1'}">
												<tr>
													<td>姓名</td>
													<td>${idCardCompResult.xm }</td>
												</tr>
												<tr>
													<td>性别</td>
													<td>${idCardCompResult.xb}</td>
													
												</tr>
												<tr>
													<td>身份证号码&nbsp;&nbsp;&nbsp;&nbsp;</td>
													<td>${idCardCompResult.ysfzh }</td>
												</tr>
											 </c:if>
											 <c:if test="${idCardCompResult.compareBaseID == '2'}">
												<tr>
													<td>姓名</td>
													<td>${idCardCompResult.T_QB_RY_CKRYJBXXxm }</td>
												</tr>
												<tr>
													<td>性别</td>
													
													<td>${idCardCompResult.T_QB_RY_CKRYJBXXxb}</td>
													
												</tr>
												<tr>
													<td>身份证号码&nbsp;&nbsp;&nbsp;&nbsp;</td>
													<td>${idCardCompResult.T_QB_RY_CKRYJBXXsfzh }</td>
												</tr>
											 </c:if>
											 <c:if test="${idCardCompResult.compareBaseID == '0'}">
												<tr>
													<td>姓名</td>
													<td>${idCardCompResult.bbkrxm }</td>
												</tr>
												<tr>
													<td>性别</td>
													
													<td>${idCardCompResult.bbkrxb}</td>
													
												</tr>
												<tr>
													<td>身份证号码&nbsp;&nbsp;&nbsp;&nbsp;</td>
													<td>${idCardCompResult.bbkrzjhm }</td>
												</tr>
											 </c:if>
											</table>
											</div>
											<div class="sfzbd_right">
												 <c:if test="${idCardCompResult.isControlled == '1'}">
													<span class="sfzbd_right1" style="font-weight: bold;">身份证比中</span>
												  </c:if>
												  <c:if test="${idCardCompResult.isControlled == '0'}">
													<span class="sfzbd_right1" style="font-weight: bold;">身份证未比中</span>
												  </c:if>
												<c:if test="${idCardCompResult.compareBaseID == '1'}">
													<span class="sfzbd_right2" > 比中在逃人员</span>
													<div class="sfzbd_right3">
														<span class="sfzbd_right4" style="font-weight: bold;">案件：</span>
														<span class="sfzbd_right5">${idCardCompResult.context}</span>
													</div>
												  </c:if>
												  <c:if test="${idCardCompResult.compareBaseID == '2'}">
													<span class="sfzbd_right2">比中常控人员</span>
													<div class="sfzbd_right3">
														<span class="sfzbd_right6" style="font-weight: bold;">常控等级：</span>
														<span class="sfzbd_right5">${idCardCompResult.T_QB_RY_CKRYJBXXContext}</span>
													</div>
												  </c:if>
												  <c:if test="${idCardCompResult.compareBaseID == '0'}">
													<span class="sfzbd_right2">比中临控人员</span>
													<div class="sfzbd_right3">
														<span class="sfzbd_right6" style="font-weight: bold;">临控类型：</span>
														<span class="sfzbd_right5">${idCardCompResult.lkzllx}</span>
													</div>
												  </c:if>
											</div>
										</div>
                                                                               
                                    </div>
                                </div>
                            </div>
                            <div class="real-boxy">
                               
                                <div class="real-title" style="font-weight: bold;">人脸信息比对</div>
                                <div class="real-con clearfix">
									
									
									<div class="sliderbox">
										<div id="btn-left" class="arrow-btn dasabled"></div>
										<div class="slider">
												<ul id="faceComePareYj">
										<c:forEach items="${faceInfoCompResultList}" var="list" varStatus="status">
												<li >
												<c:if test="${list.similarity >= paramSet.faceCompAlarmVal}">
													<div class="sfzbd_01">
														<c:if test="${ !empty list.photoStr}">
														<img class="sfzbd_leftimg" src="data:image/jpg;base64,${list.photoStr}">
														</c:if>
														<c:if test="${ empty list.photoStr}">
														<img class="sfzbd_leftimg" src="../img/collect-fail.png">
														</c:if>
														<div class="sfzbd_middle_01">
														<p class="sfzbd_middleh1_01" style="font-weight: bold;">身份证缉控信息</p>
														<table style="font-size:12px;">
														<c:if test="${list.compareBaseID == '1'}">
															<tr>
																<td>姓名</td>
																<td>${list.xm}</td>
															</tr>
															<tr>
																<td>性别</td>
																
																<td>${list.xb}</td>
																
															</tr>
															<tr>
																<td>身份证号码&nbsp;&nbsp;&nbsp;&nbsp;</td>
																<td>${list.ysfzh }</td>
															</tr>
														</c:if>
														<c:if test="${list.compareBaseID == '2'}">
															<tr>
																<td>姓名</td>
																<td>${list.T_QB_RY_CKRYJBXXxm}</td>
															</tr>
															<tr>
																<td>性别</td>
																
																<td>${list.T_QB_RY_CKRYJBXXxb}</td>
																
															</tr>
															<tr>
																<td>身份证号码&nbsp;&nbsp;&nbsp;&nbsp;</td>
																<td>${list.T_QB_RY_CKRYJBXXsfzh }</td>
															</tr>
														</c:if>
														<c:if test="${list.compareBaseID == '0'}">
															<tr>
																<td>姓名</td>
																<td>${list.bbkrxm}</td>
															</tr>
															<tr>
																<td>性别</td>
																
																<td>${list.bbkrxb}</td>
																
															</tr>
															<tr>
																<td>身份证号码&nbsp;&nbsp;&nbsp;&nbsp;</td>
																<td>${list.bbkrzjhm }</td>
															</tr>
														</c:if>
														</table>
														</div>
														<div class="sfzbd_right_01">
															<span class="sfzbd_right01" style="font-weight: bold;">人脸相似度 ${list.similarity }%</span>
															
															<c:if test="${list.compareBaseID == '1'}">
															<span class="sfzbd_right02">比中在逃人员</span>
															<div class="sfzbd_right03">
																<span class="sfzbd_right04" style="font-weight: bold;">案件：</span>
																	<span class="sfzbd_right5">${list.context}</span>
															</div>
															</c:if>
																<c:if test="${list.compareBaseID == '2'}">
															<span class="sfzbd_right02">比中常控人员</span>
															<div class="sfzbd_right03">
																<span class="sfzbd_right6" style="font-weight: bold;">常控等级：</span>
																	<span class="sfzbd_right5">${list.T_QB_RY_CKRYJBXXContext}</span>
															</div>
															</c:if>
															<c:if test="${list.compareBaseID == '0'}">
															<span class="sfzbd_right02">比中临控人员</span>
															<div class="sfzbd_right03">
																<span class="sfzbd_right6" style="font-weight: bold;">临控类型：</span>
																	<span class="sfzbd_right5">${list.lkzllx}</span>
															</div>
															</c:if>
														</div>
													</div>
													</c:if>
												</li>
										</c:forEach>
											</ul>
										</div>
										<div id="btn-right" class="arrow-btn"></div>
									</div>
									<script src="../js/jquery.js"></script>
									<script>
									$(function(){
										var $slider = $('.slider ul');
										var $slider_child_l = $('.slider ul li').length;
										$('.slider ul li').width($('.slider').width());
										var $slider_width = $('.slider ul li').width();
										$slider.width($slider_child_l * $slider_width);

										var slider_count = 0;

										if ($slider_child_l < 1) {
											$('#btn-right').css({cursor: 'auto'});
											$('#btn-right').removeClass("dasabled");
										}
										
										if(!$("#faceComePareYj").has('li').length){ 
											$("#faceComePareYj").html("<div class='sfzbd_01' style='margin-top: 30px;margin-left: 100px;'><h3>采集信息未比对到缉控信息</h3></div>");
										} 
										
										
										$('#btn-right').click(function() {
											if ($slider_child_l < 1 || slider_count >= $slider_child_l - 1) {
												return false;
											}
											slider_count++;
											$slider.animate({left: '-=' + $slider_width + 'px'}, 'slow');
											slider_pic();
										});

										$('#btn-left').click(function() {
											if (slider_count <= 0) {
												return false;
											}
											slider_count--;
											$slider.animate({left: '+=' + $slider_width + 'px'}, 'slow');
											slider_pic();
										});
										function slider_pic() {
											if (slider_count >= $slider_child_l - 1) {
												$('#btn-right').css({cursor: 'auto'});
												$('#btn-right').addClass("dasabled");
											}
											else if (slider_count > 0 && slider_count <= $slider_child_l - 1) {
												$('#btn-left').css({cursor: 'pointer'});
												$('#btn-left').removeClass("dasabled");
												$('#btn-right').css({cursor: 'pointer'});
												$('#btn-right').removeClass("dasabled");
											}
											else if (slider_count <= 0) {
												$('#btn-left').css({cursor: 'auto'});
												$('#btn-left').addClass("dasabled");
											}
										}
										$('.slider a').hover(function() {
											if ($(this).find('img:animated').length) return;
											$(this).animate({marginTop: '0px'}, 1200);
											$(this).find('img').animate({width: '150px'}, 1200);
										}, function() {
											$(this).animate({marginTop: '24px'}, 400);
											$(this).find('img').animate({width: '90px'}, 400);
										});
										$("#back").click(function(){
											if($("#comfrom").html()=="照片对比"){
												window.history.go(-2);
											}else{
												window.history.go(-1);
											}
											
											//window.history.back();
										});
									})
									</script>
                                </div>
                            </div>
                            <div class="real-boxy">
                              
                                <div class="real-title" style="font-weight: bold;">人证信息比对</div>
                                <div class="real-con clearfix">
                                      <div class="kuang3x">
										<div class="kuang3x_left">
											<div class="kuang3x_left_left">
											<c:if test="${faceAndCard != null}">
											 <c:if test="${faceAndCard.collectPicStr != ''}">
												<img class="kuang3x_left_left_img" src="data:image/jpg;base64,${faceAndCard.collectPicStr}" >
											 </c:if>
											  <c:if test="${faceAndCard.collectPicStr == ''}">
												<img src="../img/collect-fail.png" alt="">
											 </c:if>
											 </c:if>
											  <c:if test="${faceAndCard == null}">
											 	<img src="../img/collect-fail.png" alt="">
											 </c:if>
												<span>采集照片</span>
											</div>
											<div class="kuang3x_left_middle">vs</div>
											<div class="kuang3x_left_left">
											<c:if test="${faceAndCard != null}">
											 <c:if test="${faceAndCard.idCardPicStr != ''}">
												<img class="../kuang3x_left_left_img" src="data:image/jpg;base64,${faceAndCard.idCardPicStr }">
											 </c:if>
											 <c:if test="${faceAndCard.idCardPicStr == ''}">
											 <img src="../img/collect-fail.png" alt="">
											 </c:if>
											 </c:if>
											 <c:if test="${faceAndCard == null}">
											 <img src="../img/collect-fail.png" alt="">
											 </c:if>
												<span>采集身份证照片</span>
											</div>
										</div>
										<c:if test="${faceAndCard.similarity >= paramSet.faceCardCompAlarmVal}" >
										<div class="kuang3x_right">
											<h1>相似度</h1>
											<h2>${faceAndCard.similarity}%</h2>
											
												<h3>人证匹配</h3>
											
										</div>
										</c:if>
										<c:if test="${faceAndCard.similarity < paramSet.faceCardCompAlarmVal}" >
										<div class="kuang3x_right">
											<h1>相似度</h1>
											<h2>${faceAndCard.similarity}%</h2>
											
												<h3>人证不匹配</h3>
											
										</div>
										</c:if>
									</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
              </div>
            </div>
        <script src="../js/vendor/jquery-1.11.3.min.js"></script>
        <script src="../js/plugins.js"></script>
        <script src="../js/main.js"></script>
        <script>
        </script>
    </body>
</html>
