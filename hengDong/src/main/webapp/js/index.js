function refreshMenus() {
	$.post("./LoginController/getAuthMenus.do", function(data) {
		var menuRoles = data.menuRoles;
		var onelevels = [];

		for (var i = 0; i < menuRoles.length; i++) {
			var elements;
			if (menuRoles[i].menuName == '信息查询') {
				$("#menu-product").css("display", "");
			} else if (menuRoles[i].menuName == '综合信息查询') {
				$("#syndromeInfoQuery").css("display", "");
			} else if (menuRoles[i].menuName == '身份证信息查询') {
				$("#idCardInfoQuery").css("display", "");
			} else if (menuRoles[i].menuName == '人脸信息查询') {
				$("#faceInfoQuery").css("display", "");
			} else if (menuRoles[i].menuName == '研判分析') {
				$("#menu-comments").css("display", "");
			} else if (menuRoles[i].menuName == '照片对比') {
				$("#picCompare").css("display", "");
			} else if (menuRoles[i].menuName == '身份证号对比') {
				$("#idCardCompare").css("display", "");
			} else if (menuRoles[i].menuName == '采集次数统计') {
				$("#collectTimesStatistics").css("display", "");
			} else if (menuRoles[i].menuName == '设备管理'
					&& null == menuRoles[i].fatherMenuID) {
				$("#menu-member").css("display", "");
			} else if (menuRoles[i].menuName == '设备管理'
					&& null != menuRoles[i].fatherMenuID) {
				$("#subdeviceManage").css("display", "");
			} else if (menuRoles[i].menuName == '设备运行状态监控') {
				$("#deviceStatusMonitor").css("display", "");
			} else if (menuRoles[i].menuName == '系统管理') {
				$("#menu-admin").css("display", "");
			} else if (menuRoles[i].menuName == '辖区管理') {
				$("#areaManage").css("display", "");
			} else if (menuRoles[i].menuName == '用户管理') {
				$("#userManage").css("display", "");
			} else if (menuRoles[i].menuName == '角色权限管理') {
				$("#roleManage").css("display", "");
			} else if (menuRoles[i].menuName == '日志管理') {
				$("#logManage").css("display", "");
			} else if (menuRoles[i].menuName == '采集参数管理') {
				$("#collectParamsManage").css("display", "");
			} else if (menuRoles[i].menuName == '临时辑控管理') {
				$("#tempControl").css("display", "");
			}
		}
	});

}

function logout()
{
	$.post("./LoginController/logOut.do", function(data) {
		var status = data.status;
		if ("success" == status)
		{
			var isChrome = navigator.userAgent.toLowerCase().match(/chrome/) != null;//判断是否是谷歌浏览器
			if (isChrome)
			{
				window.location="./login.html";
			}
			else
			{
				window.location="../login.html";
			}
			
		}});
}