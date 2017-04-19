<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<spring:url value="/farm/admin" var="homeUrl" />
<spring:url value="/farm/home/homestatistic" var="homestatisticUrl" />
<spring:url value="/resources/platform/img/icon/update_password.png" var="updatePassword" />
<spring:url value="/resources/platform/img/icon/log_out.png" var="logOut" />
<spring:url value="/farm/logout" var="logoutUrl"/>
<spring:url value="/farm/personal/updatePass" var="updatePassUrl"/>
<!--右侧部分开始-->
        <div id="page-wrapper" class="gray-bg dashbard-1">
            <div class="row content-tabs">
                <a href="${homeUrl}" class="roll-nav roll-left J_tabLeft"><i class="fa fa-desktop"></i>
                </a>
                <nav class="page-tabs J_menuTabs">
                    <div class="page-tabs-content">
                        <a href="javascript:void(0);" class="active J_menuTab J_menuTitle">首页</a>
<%--                         <a style="margin-left: 18%;" ref="${updatePassUrl}" onclick="updatePass(this);"><img src="${updatePassword}" width="13px" height="13px" style="margin-top: -2px;"/>修改密码</a> --%>
<%--                         <a href="${logoutUrl}"><img src="${logOut}" width="13px" height="13px" style="margin-top: -2px;"/>退出</a> --%>
                    </div>
                </nav>
                <a class="btn-group roll-nav roll-right" ref="${updatePassUrl}" onclick="updatePass(this);" style="border: 0; background: transparent; ">
                	<img src="${updatePassword}" width="13px" height="13px" style="margin-top: -2px;"/>修改密码</a>
                <a class="roll-nav roll-right J_tabExit" href="${logoutUrl}" style="border: 0; background: transparent; ">
                	<img src="${logOut}" width="13px" height="13px" style="margin-top: -2px;"/>退出</a>
            </div>
            <div class="row J_mainContent" id="content-main" style="min-height: 400px;height:100%">
                <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="${homestatisticUrl}" frameborder="0" seamless></iframe>
            </div>
            <!-- <div class="footer">
                <div class="pull-right">&copy; 2014-2015 <a href="javascript:void(0);" target="_blank">f2b2c</a>
                </div>
            </div> -->
        </div>
        <!--右侧部分结束-->
        <script type="text/javascript">
        	//修改用户密码
			function updatePass(obj){
				 if($(obj).attr("ref")!=undefined && $(obj).attr("ref").trim().length>0){
						$(".J_iframe").attr("src",$(obj).attr("ref").trim());
						$(".J_menuTitle").text($(obj).text().trim());
				 }						
			}
		</script>
        
        