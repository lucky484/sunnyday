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
        <title>研判分析-照片对比</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="../css/normalize.css">
        <link rel="stylesheet" href="../css/main.css">
        <style type="text/css">
        .type-file-file{height:24px; filter:alpha(opacity:0);opacity: 0;width:125px }
        

		.input{border:0px;color:white;background:white;font:normal 12px Tahoma;}

        </style>
        
    </head>
    <script>
		function fclick(obj){
		   with(obj){
		     style.posTop=event.srcElement.offsetTop;
		     var x=event.x-offsetWidth/2;
		     if(x<event.srcElement.offsetLeft)x=event.srcElement.offsetLeft;
		     if(x>event.srcElement.offsetLeft+event.srcElement.offsetWidth-offsetWidth)x=event.srcElement.offsetLeft+event.srcElement.offsetWidth-offsetWidth
		     style.posLeft=x;
		   }
		}
		
		
	</script>
    <body>
    	<input type="hidden" name="hiddenSimilarity" id="hiddenSimilarity" value="${idCardNo}"/>
        <input type="hidden" name="hiddenCollectSite" id="hiddenCollectSite" value="${collectSite1}"/>
        <input type="hidden" name="hiddenCountPage" id="hiddenCountPage" value="${pages}"/>
        <input type="hidden" name="hiddenPrimary" id="hiddenPrimary" value="${primary}"/>
        <input type="hidden" id="nullData" value="${empty nullDataFlag?'1':nullDataFlag}">
       
                <div class="page-con">
                    <div class="match-hd">
                        <div class="match-title-box">
                           	 研判分析 <span>照片对比</span>
                        </div>
                        <div class="match-title-box">
                        <form action="getFaceInfoCompareMessage.do" method="post" id="AnalyzingFaceinfo"  enctype="multipart/form-data">
                            <input type="hidden" name="fys" id="fys" value="5">
                            
                            <div class="upload-photo" id="upload" >
                            	<div id="localImag" style="border: 0px;"  style="diplay:none"><img id="preview" alt="" src=""  ></div>   
								<input type="button" class='upload-box'  id="upload_a" onmouseover="fclick(upLoadFile)" value="选择上传文件">
								<input name="upLoadFileName" type="file"   class="type-file-file" id="upLoadFile" onchange="javascript:setImagePreview();">	
                  			</div>
                            <div  class="photo-form" >
                                <div class="form-block">
                                    <label>比对相似度 ≥</label>
                                    <div class="per">
                                       <input type="text" name="similarity" class="form-control" maxlength="6">
                                       <i>%</i> 
                                    </div>
                                </div>
                                <div class="form-block">
                                    <label>采集地点</label>
                                    <input type="text" name="collectSite" class="form-control">
                                </div>
                                <div class="form-block">
                                    <label>显示条数</label>
                                    <input type="text" id="count" name="count" class="form-control" value="10">
                                </div>
                                <div class="form-block">
                                    <label>选择对比库</label>
                                    <select name="ctrlBaseId" id="ctrlBaseId">
                                    	<option value="1">采集库</option>
                                    	<option value="101">临时辑控库</option>
                                    </select>
                                </div>
                                <div class="form-block">
                                    <label></label>
                                    <input type="button" id="compare_btn" class="btn" onclick="getsubmit()" value="开始对比">
                                    <input  type="button" class="btn" id="reset_btn"  onclick="resetBtn()" value="重置">
                                </div>
                            </div>
                            </form>
                        </div>
                    </div>
                    <div class="match-data">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>采集时间</th>
                                    <th>采集地点</th>
                                    <th>采集照片</th>
                                    <th>相似度</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody id="message">
                            	<c:forEach items="${list}" var="faceInfoList" varStatus="status">
                                <tr>
                                    <td>${faceInfoList.collectTimeStr}</td>
                                    <td>${faceInfoList.collectSite}</td>
                                    <td>
                                        <img src="data:image/jpg;base64,${faceInfoList.collectPicStr}" class="people-img" alt="">
                                    </td>
                                    <td>
                                        <span class="pre-num"  style="font-size: 14px;">
                                            ${faceInfoList.similarity}%
                                        </span>
                                    </td>
                                    <td><a href="/hengdong/complexQuery/queryComplexById.do?compareBaseId=${faceInfoList.compareBaseID}&comeFrom=照片对比&faceId=${faceInfoList.faceId}" class="more">查看 </a></td>
                                </tr>
                                </c:forEach>
                                
                            </tbody>
                        </table>
                    </div>
                </div>

        <script src="../js/vendor/jquery-1.11.3.min.js"></script>
        <script src="../js/plugins.js"></script>
        <script src="../js/main.js"></script>
        <script src="../js/AnalyzingFaceinfo.js"></script>
    </body>
</html>
