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
		<meta charset="utf-8">
		<meta name="renderer" content="webkit|ie-comp|ie-stand">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta http-equiv="Cache-Control" content="no-siteapp" />
		<LINK rel="Bookmark" href="/favicon.ico" >
		<LINK rel="Shortcut Icon" href="/favicon.ico" />
		<link href="../css/H-ui.min.css" rel="stylesheet" type="text/css" />
		<link href="../css/H-ui.admin.css" rel="stylesheet" type="text/css" />
		<link href="../skin/default/skin.css" rel="stylesheet" type="text/css" id="skin" />
		<link href="../lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
		<link href="../css/style.css" rel="stylesheet" type="text/css" />		
        <link rel="stylesheet" href="../css/normalize.css">
        <link rel="stylesheet" href="../css/main.css">
        <style type="text/css">
			.real-title.FcWhiteBackGroundColor{
				 /* border: 2px solid #1e92d5; */ 
				 border-bottom: 0;		
				 background-color: white;
			}
			.real-con.clearfix.FcWhiteBackGroundColor{
				 /* border: 2px solid #1e92d5;  */		
				 background-color: white;
			}
			/*长定时*/
			.real-title.FcLongBackGroundColor{
				 /* border: 2px solid #1e92d5; */ 
				 border-bottom: 0;		
				 background-color: #DAA520;
			}
			.real-con.clearfix.FcLongBackGroundColor{
				 /* border: 2px solid #1e92d5;  */		
				 background-color: #DAA520;
			}
			
			.real-title.FcRedBackGroundColor{
   				 /* border: 2px solid red; */
   				 border-bottom: 0;
   				 background-color: red;
			}
			.real-con.clearfix.FcRedBackGroundColor{
   				 /* border: 2px solid red; */
   				 background-color: red;
			}
			
			
			.contrast-con.paddingDiv{
				padding-left:10%;
			}
			
		</style>
    </head>
    <body>
	    <input type="hidden" id="faceCompareResult">
		<input type="hidden" id="faceCode">
		<input type="hidden" id="idCardCompareResult">
		<input type="hidden" id="hiddenCardId">
		<input type="hidden" id="hiddenFaceId">
		
		<!-- 设备号 -->
		<input type="hidden" id="e_deviceCode" value="${e_deviceCode }">
		
		<!-- 人脸起停 -->
		<input type="hidden" id="m_p_faceCompareFlag" value="${m_params.faceCompareFlag }">
		<!-- 身份证起停 -->
		<input type="hidden" id="m_p_cardCompareFlag" value="${m_params.cardCompareFlag }">
		<!-- 人证合一起停 -->
		<input type="hidden" id="m_p_faceCardCompFlag" value="${m_params.faceCardCompFlag }">
		<!-- 预警查询控制时间 -->
		<input type="hidden" id="warningTime" value="">
		<!-- 实时监控身份证全信息消失时间 -->
		<input type="hidden" id="push_IdCardMissTime" value="${push_IdCardMissTime} ">
		<!-- 预警闪烁时间 -->
		<input type="hidden" id="push_warmingTime" value="${push_warmingTime}">
		<!-- 预警定时取数时间-->
		<input type="hidden" id="push_timeOutTime" value="${push_timeOutTime}">
		<!-- 预警闪烁之后长显示时间 -->
		<input type="hidden" id="push_displayTime" value="${push_displayTime}">
		
        <div class="container wrapper realtime">
            <!-- 主内容区域 -->
            <div class="page-wrapper">
                <div class="page-con">
                    <div class="clearfix">
                        <div class="page-hd-nofloat">
                            <h2 class="dib">实时监控</h2>
                            <div class="address dib">
                               <%-- ${e_collectSite } --%><span  id="deviceCode">${e_collectSite }</span>
                               <!--  <span class="icon i-arrow-down"></span> -->
                            </div>
                        </div>
                       <dl class="page-hd-data" style="font-weight: bold;width:100%;">
						<dt>当日身份证采集数</dt>
						<dd id="id_card_count">0</dd>
						<dt>当日人脸采集数</dt>
						<dd id="face_count">0</dd>
						<dd id="fc_warns" style="float:right;">0</dd>
						<dt style="float:right;">当日人证不一致预警数</dt>
						<dd id="face_warns" style="float:right;">0</dd>
						<dt style="float:right;">当日人脸信息对比预警数</dt>
						
						<dd id="idcard_warns" style="float:right;">0</dd>
						<dt style="float:right;">当日身份证对比预警数</dt>
						
						
						
						
						
					</dl>
                    </div>
                    <div class="pure-g">
                        <div class="pure-u-10-24">
                            <div class="real-time-info">
                                <div class="real-box">
                                    <div class="real-title">实时采集信息</div>
                                    <div class="real-con">
                                        <div class="pure-g">
                                            <div class="pure-u-7-12">
                                                <div class="line">
                                                  <div class="real-time-photo" id="realPhoto">
	                                                  <img src="../img/collect-fail.png" alt="">
	                                                  <img src="../img/default.png" class="full" alt="">
	                                                  <div class='get-time'></div>
                                                  </div>
                                                </div>
                                            </div>
                                             <div class="pure-u-5-12">
                                                <div class="his-photo" id="rightPhoto">
                                                </div>
                                            </div>
                                    </div>
                                </div>
                                </div>
                                <div class="id-check-box">
                                    <div class="id-info">
                                        <div class="id-img" id="imgIdCard">
                                        	<img src="../img/collect-fail.png" width="98" height="124" alt=""><span>证件照</span>
                                            <span>证件照</span>
                                        </div>
                                        <div class="id-spec">
                                            <div class="id-item">
                                                <label>身份号码&nbsp;:&nbsp;</label>
                                                <span ></span>
                                            </div>
                                            <div class="id-item">
                                                <label>身份证ID&nbsp;:&nbsp;</label>
                                                <span ></span>
                                            </div>
                                            <div class="id-item">
                                                <label>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名&nbsp;:&nbsp;</label>
                                                <span ></span>
                                            </div>
                                            <div class="id-item">
                                                <label>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别&nbsp;:&nbsp;</label>
                                                <span ></span>
                                            </div>
                                            <div class="id-item">
                                                <label>民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族&nbsp;:&nbsp;</label>
                                                <span></span>
                                            </div>
                                            <div class="id-item">
                                                <label>出&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;生&nbsp;:&nbsp;</label>
                                                <span></span>
                                            </div>
                                            <div class="id-item">
                                                <label>住&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址&nbsp;:&nbsp;</label>
                                                <span ></span>
                                            </div>
                                            <div class="id-item">
                                                <label>签发机关&nbsp;:&nbsp;</label>
                                                <span ></span>
                                            </div>
                                            <div class="id-item">
                                                <label>采集时间&nbsp;：&nbsp;</label>
                                                <span ></span>
                                            </div>
                                           
                                        </div>
                                    </div>
                                    <div class="id-list">
                                        <table>
                                            <tbody id="idcardInfo">
                                               
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="pure-u-14-24">
                            <div class="real-contrast">
                                <div class="real-box">
                                    <a href="/hengdong/InquiryIdCardInfo/InquiryIdCardInfo.do" class="h-more">历史结果 <i class="fa fa-angle-double-right"></i></a>
                                    <div class="real-title" id="sfz">身份证比对预警</div>
                                    <div class="real-con clearfix" id="IdCardteam">
                                        <div class="contrast contrast-lg">
                                            <div class="contrast-item g-1">
                                                <h3><b>采集身份证信息</b></h3>
                                                <div class="id-img" id="cardinfo"></div>
                                                <div class="c-info" id="idCardNo"></div>
                                                <div class="c-info" id="idCollectTimeStr"></div>
                                            </div>
                                            <div class="contrast-item g-2">
                                                <div class="contrast-con paddingDiv">
                                                    <h3 class="ml"><b>身份证缉控比对信息</b></h3>
                                                    <div class="clearfix">
                                                       <div class="m-left">
                                                            <div class="id-img" id="cardInfoComp"></div>
                                                        </div>
                                                        <div class="m-txt id_class" id="cardInfoPhoto"></div>
                                                    </div>
                                                    
                                                </div>
                                            </div>
                                            <div class="contrast-item g-1">
                                                <h3><b>采集人像匹配</b></h3>
                                                <div class="id-img" id="faceCardInfo"></div>
										        <div class="c-info" id="cardInfoFTime"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="real-box">
                                    <a href="/hengdong/SearchFaceInfo/getSearchFaceInfo.do" class="h-more">历史结果 <i class="fa fa-angle-double-right"></i></a>
                                    <div class="real-title" id="face">人脸信息比对预警</div>
                                    <div class="real-con clearfix" id="faceDiv">
                                        <div class="contrast">
                                            <div class="contrast-item g-1">
                                                <h3><b>采集人像信息</b></h3>
                                                <div class="id-img" id="faceInfo"></div>
                                                <div class="c-info" id="faceCollectTime"></div>
                                            </div>
                                            <div class="contrast-item g-2">
                                                <div class="contrast-con paddingDiv">
                                                    <h3 class="ml"><b>人像缉控比对信息</b></h3>
                                                    <div class="clearfix">
                                                       <div class="m-left">
                                                            <div class="id-img" id="faceComp"></div>
                                                        </div>
                                                        <div class="m-txt face_class" id="faceInfoComp"></div>
                                                    </div>
                                                    
                                                </div>
                                            </div>
                                            <div class="contrast-item g-1">
                                                <h3><b>采集身份证匹配</b></h3>
                                                <div class="id-img" id="faceInfoC"></div>
                                                <div class="c-info" id="faceInfoCTime"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="real-box">
                                    <a href="/hengdong/InquiryIdCardInfo/InquiryIdCardInfo.do" class="h-more">历史结果 <i class="fa fa-angle-double-right"></i></a>
                                    <div class="real-title" id="rz">人证信息不一致预警</div>
                                    <div class="real-con clearfix" id="fc">
                                        <div class="contrast" id="FCteam">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 主内容区域 end -->
        </div>
        <script src="../js/vendor/jquery-1.11.3.min.js"></script>
		<script src="../js/plugins.js"></script>
		<script src="../js/main.js"></script>
		<script src="../js/RealTimeMonitor.js"></script>
    </body>
</html>
