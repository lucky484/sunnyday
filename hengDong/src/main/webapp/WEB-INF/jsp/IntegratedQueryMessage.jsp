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
        <link rel="stylesheet" href="../css/main_xxxx.css">
    </head>
    <body>
      
                <div class="page-con">
					<div class="match-title-box">
                            信息查询
                            <span>${comeFrom }</span>
                        </div>
					<div class="xxcx_right">
						<a class="xianchangruxiang">播放现场录像</a>
						<a class="xianchangruxiang" id="back">返回</a>
					</div>
                    <div class="clearfix">
                        <div class="page-hd">
                            <div class="address dib" style="font-weight: bold;">
                                采集地点：${collectCountModel.collectsite }
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
											<h3>采集时间：${ FaceCollectTimeStr }</h3>
										
										</div>
										
										<c:if test="${isHaveFace == 1}" >
														<div class="real-time-photo pull-left">
			                                    <c:forEach items="${faceList}" var="faceListvar" varStatus="status">
			                                    	<c:if test="${status.index==0}">
			                                        	<img src="data:image/jpg;base64,${faceListvar.collectFacePicStr}" class="full" alt="">
			                                     	</c:if>
			                                     	<c:if test="${status.index==1}">
													<img src="data:image/jpg;base64,${faceListvar.collectFacePicStr}" alt="">
													</c:if>
													<c:if test="${status.index==2}">
													<img src="data:image/jpg;base64,${faceListvar.collectFacePicStr}" alt=""> 
													</c:if>
			                                  	</c:forEach>
			                                    </div>
										</c:if>
										<c:if test="${isHaveFace == 0}" >
											<div class="real-time-photo pull-left">
			                                	<img src="../img/default.png" class="full" alt="">
				                                <img src="../img/collect-fail.png" alt="">
												<img src="../img/collect-fail.png" alt=""> 
											</div>
										</c:if>
										
										
									<div class="real-pull-bottom">
										<h1>身份证采集信息</h1>
										<h2>采集时间：${collectCountModel.collectTimeStr }</h2>
									</div>
									<div class="real-pull-bottom-x">
										<div class="real-pull-bottom-x-left">
											<img src="data:image/jpg;base64,${collectCountModel.idCardPicStr }">
										</div>
										<div class="id-spec"  style="margin-bottom: 60px;">
	
                                            <div class="id-item" style="color:#fff;font-size: 12px;">
                                                <label>身份号码&nbsp;:&nbsp;</label>
                                                <span>${collectCountModel.idCardNo }</span>
                                            </div>
                                            <div class="id-item" style="color:#fff;font-size: 12px;">
                                                <label>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名&nbsp;:&nbsp;</label>
                                                <span>${collectCountModel.idCardName }</span>
                                            </div>
                                            <div class="id-item" style="color:#fff;font-size: 12px;">
                                                <label>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别&nbsp;:&nbsp;</label>
                                                <span>${collectCountModel.idCardSex }</span>
                                            </div>
                                            <div class="id-item" style="color:#fff;font-size: 12px;">
                                                <label>民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族&nbsp;:&nbsp;</label>
                                                <span>${collectCountModel.idCardNation }</span>
                                            </div>
                                            <div class="id-item" style="color:#fff;font-size: 12px;">
                                                <label>出&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;生&nbsp;:&nbsp;</label>
                                                <span>${collectCountModel.idCardBirth }</span>
                                            </div>
                                            <div class="id-item" style="color:#fff;font-size: 12px;">
                                                <label>住&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址&nbsp;:&nbsp;</label>
                                               <span>${collectCountModel.idCardAddress }</span>
                                            </div>
                                            <div class="id-item" style="color:#fff;font-size: 12px;">
                                                <label>签发机关&nbsp;:&nbsp;</label>
                                                <span>${collectCountModel.cardUnit }</span>
                                            </div>
                                            <div class="id-item" style="color:#fff;font-size: 12px;">
                                                <label>采集时间：&nbsp;</label>
                                                <span>${collectCountModel.collectTimeStr }</span>
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
												<span>${collectCountModel.idCardNo }</span>
												<span>${collectCountModel.idCardName }</span>
												<span>${collectCountModel.idCardSex }</span>
												<span>${collectCountModel.idCardNation }</span>
												<span>${collectCountModel.idCardBirth }</span>
												<span>${collectCountModel.idCardAddress }</span>
												<span></span>
												<span>${collectCountModel.cardUnit }</span>
												<span>${collectCountModel.cardIssueDate }~${collectCountModel.cardExpiryDate }</span>
											</div>
										</div> --%>
									</div>
                                    
                                </div>
                            </div>
                            
                        </div>
                        <div class="real-contrast">
                            <div class="real-boxy">
                              
                                <div class="real-title" style="font-weight: bold;">身份证比对</div>
                                <!-- 显示辑控库信息 -->
                                <div class="real-con clearfix">
                                    <div class="contrast">
                                        <div class="sfzbd">
                                        	<c:if test="${empty collectCountModeljk.idCardPicStr }">
                                        		<img class="sfzbd_leftimg" src="../img/collect-fail.png">
                                        	</c:if>
                                        	<c:if test="${!empty collectCountModeljk.idCardPicStr }">
												<img class="sfzbd_leftimg" src="data:image/jpg;base64,${collectCountModeljk.idCardPicStr }">
											</c:if>
											<div class="sfzbd_middle">
											<p class="sfzbd_middleh1" style="font-weight: bold;">身份证缉控信息</p>
											<table style="font-size:12px;">

												<tr>
													<td>姓名</td>
													<td>${collectCountModeljk.idCardName }</td>
												</tr>
												<tr>
													<td>性别</td>
													<td>${collectCountModeljk.idCardSex }</td>
												</tr>
												<tr>
													<td>身份证号码&nbsp;&nbsp;&nbsp;&nbsp;</td>
													<td>${collectCountModeljk.idCardNo }</td>
												</tr>
												<%-- <tr>
													<td>签发机关 </td>
													<td>${collectCountModel.cardUnit }</td>
												</tr>
												<tr>
													<td>有效期限</td>
													<td>${collectCountModel.cardIssueDate }~${collectCountModel.cardExpiryDate }</td>
												</tr> --%>
											</table>
											</div>
											
											<div class="sfzbd_right">
												<c:if test="${flag == 1}" >
													<span class="sfzbd_right1" style="font-weight: bold;">身份证比中</span>
													
													<%-- <c:forEach items="${typeListjk}" var="type" varStatus="status"> --%>
													<%-- <c:forEach items="${typeListT_QB_RY_ZTRYJBXX}" var="type" varStatus="status"> --%>
													<c:if test="${type.tableName == 'T_QB_RY_ZTRYJBXX' }">
														<%-- <c:if test="${status.index==0}"> --%>
															<span class="sfzbd_right2">
																比中在逃人员
															</span>
														<%-- </c:if> --%>
														<div class="sfzbd_right3">
																<span class="sfzbd_right4" style="font-weight: bold;">案件:</span>
																<span class="sfzbd_right5">${ type.content}</span>
														</div>
														</c:if>
													 <%-- </c:forEach>
													 <c:forEach items="${typeListT_QB_LK_LKBK}" var="type" varStatus="status"> --%>
													 <c:if test="${type.tableName == 'T_QB_LK_LKBK' }">
														<%-- <c:if test="${status.index==0}"> --%>
															<span class="sfzbd_right2" style="font-weight: bold;">
																比中临控人员
															</span>
														<%-- </c:if> --%>
														<div class="sfzbd_right3">
																<span class="sfzbd_right4" style="font-weight: bold;">临控等级:</span>
																<span class="sfzbd_right5">${ type.content}</span>
														</div>
														</c:if>
													 <%-- </c:forEach>
													 <c:forEach items="${typeListT_QB_RY_CKRYJBXX}" var="type" varStatus="status"> --%>
													 <c:if test="${type.tableName == 'T_QB_RY_CKRYJBXX' }">
														<%-- <c:if test="${status.index==0}"> --%>
															<span class="sfzbd_right2" style="font-weight: bold;">
																比中常控人员
															</span>
														<%-- </c:if> --%>
														<div class="sfzbd_right3">
																<span class="sfzbd_right4" style="font-weight: bold;">重点人员标记:</span>
																<span class="sfzbd_right5">${ type.content}</span>
														</div>
														</c:if>
													<%--  </c:forEach> --%>
													 <%-- </c:forEach> --%>
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
											<ul>
												<c:forEach items="${compareList}" var="list" varStatus="status">
												<c:if test="${list.tableName == 'T_QB_RY_ZTRYJBXX' }">
												<%-- <c:forEach items="${compareListT_QB_RY_ZTRYJBXX}" var="list" varStatus="status"> --%>
													<li>
													
													<!-- 显示辑控库信息 -->
														<div class="sfzbd_01">
															<c:choose>
																<c:when test="${ empty list.collectIdCardPicStr }">
																	<img class="sfzbd_leftimg" src="../img/collect-fail.png">
																</c:when>
																
																<c:otherwise>
																	<img class="sfzbd_leftimg" src="data:image/jpg;base64,${list.collectIdCardPicStr }">
																</c:otherwise>
															</c:choose>
															
															<%-- <img class="sfzbd_leftimg" src="data:image/jpg;base64,${list.collectIdCardPicStr}"> --%>
															<div class="sfzbd_middle_01">
															<p class="sfzbd_middleh1_01" style="font-weight: bold;">身份证缉控信息</p>
															<table style="font-size:12px;">
	
																<tr>
																	<td>姓名</td>
																	<td>${list.idCardName }</td>
																</tr>
																<tr>
																	<td>性别</td>
																	<td>${list.idCardSex }</td>
																</tr>
																<tr>
																	<td>身份证号码&nbsp;&nbsp;&nbsp;&nbsp;</td>
																	<td>${list.idCardNo }</td>
																</tr>
																<%-- <tr>
																	<td>签发机关 </td>
																	<td>${list.cardUnit }</td>
																</tr>
																<tr>
																	<td>有效期限</td>
																	<td>${list.cardIssueDate }~${list.cardExpiryDate }</td>
																</tr> --%>
															</table>
															</div>
															<div class="sfzbd_right_01">
																<span class="sfzbd_right01" style="font-weight: bold;">人脸相似度&nbsp;&nbsp;${list.similarity }%</span>
																<span class="sfzbd_right02">比中在逃人员</span>
																<div class="sfzbd_right03">
																	<span class="sfzbd_right04" style="font-weight: bold;">案件：</span>
																	<span class="sfzbd_right5">${ list.content}</span>
																</div>
															</div>
														</div>
													</li>
													</c:if>
													<%-- </c:forEach>
													<c:forEach items="${compareListT_QB_LK_LKBK}" var="list" varStatus="status"> --%>
													<c:if test="${list.tableName == 'T_QB_LK_LKBK' }">
													<li>
														<div class="sfzbd_01">
															<c:if test="${empty list.collectIdCardPicStr }">
				                                        		<img class="sfzbd_leftimg" src="../img/collect-fail.png">
				                                        	</c:if>
				                                        	<c:if test="${!empty list.collectIdCardPicStr} }">
																<img class="sfzbd_leftimg" src="data:image/jpg;base64,${list.collectIdCardPicStr }">
															</c:if>
															<div class="sfzbd_middle_01">
															<p class="sfzbd_middleh1_01" style="font-weight: bold;">身份证缉控信息</p>
															<table style="font-size:12px;">
																<tr>
																	<td>姓名</td>
																	<td>${list.idCardName }</td>
																</tr>
																<tr>
																	<td>性别</td>
																	<td>${list.idCardSex }</td>
																</tr>
																<tr>
																	<td>身份证号码&nbsp;&nbsp;&nbsp;&nbsp;</td>
																	<td>${list.idCardNo }</td>
																</tr>
																<%-- <tr>
																	<td>签发机关 </td>
																	<td>${list.cardUnit }</td>
																</tr>
																<tr>
																	<td>有效期限</td>
																	<td>${list.cardIssueDate }~${list.cardExpiryDate }</td>
																</tr> --%>
															</table>
															</div>
															<div class="sfzbd_right_01">
																<span class="sfzbd_right01" style="font-weight: bold;">人脸相似度&nbsp;&nbsp;${list.similarity }%</span>
																<span class="sfzbd_right02">比中临控人员</span>
																<div class="sfzbd_right03">
																	<span class="sfzbd_right04" style="font-weight: bold;">临控等级:</span>
																	<span class="sfzbd_right5">${ list.content}</span>
																</div>
															</div>
														</div>
													</li>
													</c:if>
													<%-- </c:forEach>
													
													<c:forEach items="${compareListT_QB_RY_CKRYJBXX}" var="list" varStatus="status"> --%>
													<c:if test="${list.tableName == 'T_QB_RY_CKRYJBXX' }">
													<li>
														<div class="sfzbd_01">
															<c:if test="${empty list.collectIdCardPicStr }">
				                                        		<img class="sfzbd_leftimg" src="../img/collect-fail.png">
				                                        	</c:if>
				                                        	<c:if test="${!empty list.collectIdCardPicStr} }">
																<img class="sfzbd_leftimg" src="data:image/jpg;base64,${list.collectIdCardPicStr }">
															</c:if>
															<div class="sfzbd_middle_01">
															<p class="sfzbd_middleh1_01" style="font-weight: bold;">身份证缉控信息</p>
															<table style="font-size:12px;">
																<tr>
																	<td>姓名</td>
																	<td>${list.idCardName }</td>
																</tr>
																<tr>
																	<td>性别</td>
																	<td>${list.idCardSex }</td>
																</tr>
																<tr>
																	<td>身份证号码&nbsp;&nbsp;&nbsp;&nbsp;</td>
																	<td>${list.idCardNo }</td>
																</tr>
																<%-- <tr>
																	<td>签发机关 </td>
																	<td>${list.cardUnit }</td>
																</tr>
																<tr>
																	<td>有效期限</td>
																	<td>${list.cardIssueDate }~${list.cardExpiryDate }</td>
																</tr> --%>
															</table>
															</div>
															<div class="sfzbd_right_01">
																<span class="sfzbd_right01" style="font-weight: bold;">人脸相似度&nbsp;&nbsp;${list.similarity }%</span>
																<span class="sfzbd_right02" style="font-weight: bold;">比中常控人员</span>
																<div class="sfzbd_right03">
																	<span class="sfzbd_right04" style="font-weight: bold;">重点人员标记:</span>
																	<span class="sfzbd_right5">${ list.content}</span>
																</div>
															</div>
														</div>
													</li>
													<%-- </c:forEach> --%>
													</c:if>
												</c:forEach>
												<c:if test="${empty compareList }">
													<div class="sfzbd_01" style="margin-top: 30px;margin-left: 100px;">
														<h3>采集信息未比对到缉控信息</h3>
													</div>
												</c:if>
											</ul>
										</div>
										<div id="btn-right" class="arrow-btn"></div>
									</div>
									<script src="../js/jquery.js"></script>
									<script>
									$(function(){
										var $slider = $('.slider ul');
										var $slider_child_l = $('.slider ul li').length;
										var $slider_width = $('.slider ul li').width();
										$slider.width($slider_child_l * $slider_width);

										var slider_count = 0;

										if ($slider_child_l < 1) {
											$('#btn-right').css({cursor: 'auto'});
											$('#btn-right').removeClass("dasabled");
										}

										$('#btn-right').click(function() {
											if (($slider_child_l < 1) || (slider_count >= $slider_child_l - 1)) {
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
									})
									</script>
                                    
                                </div>
                            </div>
                            <div class="real-boxy">
                              
                                <div class="real-title" style="font-weight: bold;">人证信息比对</div>
                                <div class="real-con clearfix">
                                    <div class="kuang3x">
										<div class="kuang3x_left">
										
										
										<c:if test="${isHaveFace == 0}" >
											<div class="kuang3x_left_left">
												<img class="kuang3x_left_left_img" src="../img/collect-fail.png" >
												<span>采集照片</span>
											</div>
										</c:if>
										<c:if test="${isHaveFace == 1}" >
											<div class="kuang3x_left_left">
												<img class="kuang3x_left_left_img" src="data:image/jpg;base64,${ collectFacePicStr10}" >
												<span>采集照片</span>
											</div>
										</c:if>
										

											<div class="kuang3x_left_middle">vs</div>
											<div class="kuang3x_left_left">
												<img class="../kuang3x_left_left_img" src="data:image/jpg;base64,${collectCountModel.idCardPicStr }">
												<span>采集身份证照片</span>
											</div>
										</div>
										<div class="kuang3x_right">
										<c:if test="${!empty IntegratedQueryMessage}">
											<c:if test="${IntegratedQueryMessage.faceAndCodeCompareFlag == '1'}" >
												<h1>相似度</h1>
												<h2>${IntegratedQueryMessage.similarity}%</h2>
												<h3>人证匹配</h3>
											</c:if>
											<c:if test="${IntegratedQueryMessage.faceAndCodeCompareFlag == '0'}" >
												<h1>相似度</h1>
												<h2>${IntegratedQueryMessage.similarity}%</h2>
												<h3>人证不匹配</h3>
											</c:if>
										</c:if>
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
            function getdate(id) {
                  var today = new Date();
                  var y = today.toLocaleDateString();
                  var h = today.getHours();
                  var m = today.getMinutes();
                  var s = today.getSeconds();
                  if (s < 10) {
                        s = "0" + s;
                  }
                  if (m < 10) {
                        m = "0" + m;
                  }
                  $(id).text(y + " " +h + " : " + m + " : " + s);
                  setTimeout(function() {
                        getdate(id)
                  }, 500);
            }
            $(function(){
                getdate('#curr-time');
                //左侧导航
                $("#site-menu").metisMenu();
                //对比区域设置等高
                $('.contrast').each(function(){  
            
                        var highestBox = 0;
                        $('.contrast-item', this).each(function(){
                        
                            if($(this).height() > highestBox) 
                               highestBox = $(this).height(); 
                        });  
                        
                        $('.contrast-item',this).height(highestBox);                                           
                });    

			$("#back").click(function(){
				window.history.go(-1);
			});
			
            });
        </script>
    </body>
</html>
