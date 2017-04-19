<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
        <div class="header">
            <div class="container">
                <div class="brand">
                    <img src="../img/slogan.png" alt="多模态高通量人员特征信息识别卡口系统">
                </div>
                <div class="time">
                    当前时间 : <span id="curr-time">2015-12-01  12: 00: 00</span>
                </div>
            </div>
        </div>
        <div class="container wrapper">
            <div class="navbar">
                <div class="sidebar-collapse">
                    <ul class="nav" id="site-menu">
                        <li>
                            <a href="#">
                                <span>首页</span>
                                <i class="fa"></i>
                            </a>
                        </li>
                        <li>
                            <a href="/hengdong/FaceInfo/getRealTimeMonitor.do">
                                <span>实时监控</span>
                                <i class="fa"></i>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span>信息查询</span>
                                <i class="fa fa-angle-right"></i>
                            </a>
                            <ul>
                                <li><a href="/hengdong/complexQuery/getComplexQueryList.do"><i class="fa fa-angle-right"></i> 综合信息查询</a></li>
                                <li><a href="/hengdong/InquiryIdCardInfo/InquiryIdCardInfo.do"><i class="fa fa-angle-right"></i> 身份证信息查询</a></li>
                                <li><a href="/hengdong/SearchFaceInfo/getSearchFaceInfo.do"><i class="fa fa-angle-right"></i> 人脸信息查询</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="#">
                                <span>研判分析</span>
                                <i class="fa fa-angle-right"></i>
                            </a>
                            <ul>
                                <li><a href="/hengdong/AnalyzingController/AnalyzingFaceinfo.do"><i class="fa fa-angle-right"></i> 照片对比</a></li>
                                
                                <li><a href="/hengdong/AnalyzingController/AnalyzingIDCard.do"><i class="fa fa-angle-right"></i> 身份证号对比</a></li>
                                <li><a href="/hengdong/AnalyzingController/CollectionCount.do"><i class="fa fa-angle-right"></i> 采集次数统计</a></li>
                                
                            </ul>
                        </li>
                        <li>
                            <a href="#">
                                <span>设备管理</span>
                                <i class="fa fa-angle-right"></i>
                            </a>
                            <ul>
                                <li><a href="/hengdong/ipc/equips.do"><i class="fa fa-angle-right"></i> 设备管理</a></li>
                                <li><a href="/hengdong/AnalyzingController/EquipmentMonitoring.do"><i class="fa fa-angle-right"></i> 设备运行状态监控</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="#">
                                <span>系统管理</span>
                                <i class="fa fa-angle-right"></i>
                            </a>
                            <ul>
                                <li><a href="/hengdong/CollectParamManage/AreaManagement.do"><i class="fa fa-angle-right"></i> 辖区管理</a></li>
                                <li><a href="/hengdong/CollectParamManage/UserManage.do"><i class="fa fa-angle-right"></i> 用户管理</a></li>
                                <li><a href="/hengdong/CollectParamManage/chmod.do"><i class="fa fa-angle-right"></i> 角色权限管理</a></li>
                                <li><a href="/hengdong/CollectParamManage/LogManagement.do"><i class="fa fa-angle-right"></i> 日志管理</a></li>
                                <li><a href="/hengdong/CollectParamManage/collectParamManage.do"><i class="fa fa-angle-right"></i> 采集参数管理</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
               
            </div>
            <!-- <script src="../js/vendor/jquery-1.11.3.min.js"></script>
			  <script type="text/javascript">
			  	$("a").click(function(){
			  		$("a").parent().attr("class","active");
			  	});
			  </script> -->