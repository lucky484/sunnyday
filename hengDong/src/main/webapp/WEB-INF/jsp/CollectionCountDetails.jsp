<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!doctype html>
<html class="no-js" lang="">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="pragma" CONTENT="no-cache"> 
		<meta http-equiv="Cache-Control" CONTENT="no-cache, must-revalidate"> 
		<meta http-equiv="expires" CONTENT="0"> 
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>采集次数统计</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="../css/normalize.css">
        <link rel="stylesheet" href="../css/main_detail.css">
    </head>
    <body>
    	<input type="hidden" name="hiddenIdCardNo" id="hiddenIdCardNo" value="${idCardNo}"/>
        <input type="hidden" name="hiddenCollectSite" id="hiddenCollectSite" value="${collectSite1}"/>
        <input type="hidden" name="hiddenCountPage" id="hiddenCountPage" value="${pages}"/>
        <input type="hidden" name="hiddenStartDate" id="hiddenStartDate" value="${startDate1}"/>
        <input type="hidden" name="hiddenEndDate" id="hiddenEndDate" value="${endDate1}"/>
       
                <div class="page-con">
                    <div class="match-hd">
                        <div class="match-title-box">
                            研判分析
                            <span>采集次数统计详情</span>
                        </div>
					</div>
					<div class="warp-go-back clearfix"><div class="go-back" id="back">返回</div></div>
					<div class="real-contrast">
						<div class="real-box">
							<div class="real-title">身份证信息</div>
							<div class="real-con clearfix">
								<div class="contrast">
									<div class="contrast-item g-5 border-right" style="height: 182px;">
										<h3 class="t-left"></h3>
											<div class="clearfix">
											   <div class="m-left mr5">
													<div class="id-img">
														<img src="data:image/jpg;base64,${CollectCountModel.idCardPicStr }" width="98" height="124" alt="">
													</div>
												</div>
												<div class="m-txt">
													<ul class="contrast-sepc">
														<li><h3 style="margin:0">身份证缉控信息</h3></li>
														<li>姓名:      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${CollectCountModel.idCardName }</li>
														<li>性别:      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${CollectCountModel.idCardSex }</li>
														<li>身份证号码:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${CollectCountModel.idCardNo }</li>
														<li>签发机关:  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${CollectCountModel.cardUnit }</li>
														<li>有效期限： &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${CollectCountModel.cardIssueDate }-${CollectCountModel.cardExpiryDate }</li>
													</ul>
												</div>
											</div>
									</div> 
									<c:if test="${flag == 1}" >
                                       	<div class="warp-contrast-result">
											<div class="result-box">
												<div class="result-title">
													身份证比中
												</div>
												<c:forEach items="${typeListjk}" var="type" varStatus="status">
												<%-- <c:forEach items="${typeListT_QB_RY_ZTRYJBXX}" var="type" varStatus="status"> --%>
												<c:if test="${type.tableName == 'T_QB_RY_ZTRYJBXX' }">
													<c:if test="${status.index==0}">
														<span class="contrast-result">
															比中在逃人员
														</span>
													</c:if>
													<div class="result-reason">
														案件:
													</div>
													<span class="sfzbd_right5">${ type.content}</span>
												</c:if>
												<%-- </c:forEach>
												<c:forEach items="${typeListT_QB_LK_LKBK}" var="type" varStatus="status"> --%>
												<c:if test="${type.tableName == 'T_QB_LK_LKBK' }">
													<c:if test="${status.index==0}">
														<span class="contrast-result">
															比中临控人员
														</span>
													</c:if>
													<div class="result-reason">
														临控等级:
														
													</div>
													<span class="sfzbd_right5">${ type.content}</span>
													</c:if>
												<%-- </c:forEach>
												<c:forEach items="${typeListT_QB_RY_CKRYJBXX}" var="type" varStatus="status"> --%>
												<c:if test="${type.tableName == 'T_QB_RY_CKRYJBXX' }">
													<c:if test="${status.index==0}">
														<span class="contrast-result">
															比中常控人员
														</span>
													</c:if>
													<div class="result-reason">
														重点人员标记:<br/>
														
													</div>
													<span class="sfzbd_right5">${ type.content}</span>
													</c:if>
												<%-- </c:forEach> --%>
												</c:forEach>
											</div>
										</div>
                                     </c:if>			
								</div>
							</div>
							<div class="warp-collect-info">
								<div class="collect-info">
									<table border="1">
									  <tr>
										<th>采集时间</th>
										<th>采集地点</th>
										<th>操作</th>
									  </tr>
									  <c:forEach items="${list}" var="idCardList" varStatus="status">
										  <tr>
											<td>${idCardList.collectTimeStr}</td>
											<td>${idCardList.collectsite}</td>
											<td><a
												href="getIntegratedQueryMessage.do?compareBaseID=${compareBaseID} &comeFrom=采集次数统计详情&flag=${flag}&idCardNo=${idCardList.idCardNo}&cardCode=${idCardList.cardCode}&collectTimeStr=${idCardList.collectTimeStr}&collectSite=${idCardList.collectsite}"
												class="more">查看 </a></td>
											<%-- <td style="display:none">${idCardList.cardCode}</td> --%>
										  </tr>
									  </c:forEach>
									</table>
								</div>
							</div>
						</div>
					</div>
                </div>

        <script src="../js/vendor/jquery-1.11.3.min.js"></script>
        <script src="../js/plugins.js"></script>
        <script src="../js/jquery.datetimepicker.full.min.js"></script>
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

                

                //时间
                 $('#date_timepicker_start').datetimepicker({
                  format:'Y/m/d',
                  onShow:function( ct ){
                   this.setOptions({
                    maxDate:$('#date_timepicker_end').val()?$('#date_timepicker_end').val():false
                   })
                  },
                  timepicker:false
                 });
                 $('#date_timepicker_end').datetimepicker({
                  format:'Y/m/d',
                  onShow:function( ct ){
                   this.setOptions({
                    minDate:$('#date_timepicker_start').val()?$('#date_timepicker_start').val():false
                   })
                  },
                  timepicker:false
                 });
				
                 $("#back").click(function(){
     				//window.history.go(-1);
     				window.location.href=document.referrer
     			});

            });
            
        </script>
    </body>
</html>
