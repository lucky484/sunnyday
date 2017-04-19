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
        <title>研判分析-身份证对比</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="../css/normalize.css">
        <link rel="stylesheet" href="../css/main.css">
        <style type="text/css">
        	.ysh{
        		width:30px;
        		margin: 0px;
          		border: 0px;
        		
        	}
        </style>
    </head>
    <body>
    	<input type="hidden" name="hiddenIdCardNo" id="hiddenIdCardNo" value="${idCardNo}"/>
        <input type="hidden" name="hiddenCollectSite" id="hiddenCollectSite" value="${collectSite1}"/>
        <input type="hidden" name="hiddenCountPage" id="hiddenCountPage" value="${pages}"/>
        <input type="hidden" name="hiddenflag" id="hiddenflag" value="${flag}"/>
                <div class="page-con">
                    <div class="match-hd">
                        <div class="match-title-box">
                            研判分析
                            <span>身份证号对比</span>
                        </div>
                        <div class="match-title-box">
                            <form action="getPhotoCompareMessage.do" id="getPhotoCompareMessage" class="form-inline" method="post">
                            	<input type="hidden" id="hiddenfys" name="hiddenfys" value="${empty fys?'10':fys}" >
                                <label for="" style="width:15%;">比对的身份证号</label>
                                <input type="text" id="idCardNo" name = "idCardNo" class="form-control" value="${empty idCardNo?'':idCardNo}" style="width:25%;">
                                <label for="" style="width:15%;">采集地点</label>
                                <input type="text" id="collectSite"  name = "collectSite" class="form-control" value="${empty collectSite1 ? '' :collectSite1}" style="width:25%;">
                                <button class="btn" id="compare_btn" style="width:10%;">开始对比</button>
                            </form>
                        </div>
                    </div>
                    <div class="match-data">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th width="15%">采集时间</th>
                                    <th width="25%">采集地点</th>
                                    <th width="30%">身份证信息</th>
                                    <th width="15%">布控人员</th>
                                    <th width="15%">操作</th>
                                </tr>
                            </thead>
                            <tbody id="pageContent">
                            <c:forEach items="${list}" var="listIdCard" varStatus="status">
                                <tr>
                                    <td>${listIdCard.collectTimeStr}</td>
                                    <td>${listIdCard.collectsite}</td>
                                    <td width="380">
                                        <div class="id-info">
                                            <div class="id-img">
                                                <img src="data:image/jpg;base64,${listIdCard.idCardPicStr}" width="68" height="86" alt="">
                                            </div>
                                            <div class="id-spec">
                                                <div class="id-item">
                                                    <label>姓名</label>
                                                    <span>${listIdCard.idCardName}</span>
                                                </div>
                                                <div class="id-item">
                                                    <label>性别</label>
                                                    <span>${listIdCard.idCardSex}</span>
                                                </div>
                                                <div class="id-item">
                                                    <label>民族</label>
                                                    <span>${listIdCard.idCardNation}</span>
                                                </div>
                                                <div class="id-item">
                                                    <label>身份号码</label>
                                                    <span>${listIdCard.idCardNo}</span>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
 
                                    <c:choose>
                                    	<c:when test="${listIdCard.isControlled==1}">
                                        	<span class="icon i-yes"></span>
                                        	<span class="red">是</span>
                                        </c:when>
                                        <c:when test="${listIdCard.isControlled==0}">
                                        	<span class="icon i-no"></span>否
                                        </c:when>
                                     </c:choose>  
                                    </td>
                                    <td>
                                        <a href="getIntegratedQueryMessage.do?comeFrom=身份证对比详情&flag=${listIdCard.isControlled}&idCardNo=${listIdCard.idCardNo}&cardCode=${listIdCard.cardCode}&collectTimeStr=${listIdCard.collectTimeStr}&collectSite=${listIdCard.collectsite}&page=1" class="more">查看 </a>
                                    </td>
                                </tr>
                                </c:forEach>

                            </tbody>
                        </table>
                        <div class="clearfix">
                            <div class="pull-left">
                                <span class="page-info">共<span id="dataCount">${empty count?'0':count}</span>条 每页<input type="text" class="ysh" id="fys" value="${empty fys?'10':fys}">条 页次：<span id="currentPage">1</span>/${pages==0?1:pages}</span>
                            </div>
                            <div class="pull-right">
                                 <ul class="pagination">
                                    <li>
                                      <button onclick="getPage(1)">首页</button>
                                    </li>
                                    <li><button onclick="getPrePage()">上一页</button></li>
                                    <li>
                                        <input type="text" value="1" id ="page" >
                                    </li>
                                    <li><button onclick="getNextPage()">下一页</button></li>
                                    
                                    <li><button onclick="getPage(${pages})">尾页</button></li>
                                  </ul>
                            </div>
                        </div>
                    </div>
                </div>
        <script src="../js/vendor/jquery-1.11.3.min.js"></script>
        <script src="../js/plugins.js"></script>
        <script src="../js/main.js"></script>
        <script src="../js/AnalyzingIDCard.js"></script>

    </body>
</html>
	