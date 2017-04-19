/**
 * 初始化
 */
$(function(){
	init();
	$('.fancybox').fancybox();
});

function checkAddRoleName()
{
	var roleName = $("#addRoleName").val();
	if (null == roleName || '' == roleName || undefined == roleName)
	{
		$("#addRoleNameEmpty").css("display","");
		$("#addUserNameExist").css("display","none");
		return false;
	}
	else
	{
		$("#addRoleNameEmpty").css("display","none");
		$.post("checkRoleName.do",{
			roleName : roleName
		},function(data)
		{
			var isExist = data.isExist;
			if (isExist)
			{
				$("#addRoleNameExist").css("display","");
				return false;
			}
			$("#addRoleNameExist").css("display","none");
			return true;
		});
		return true;
	}
	
	
	
}

function checkAddRoleDesc()
{
	var roleDesc = $("#addRoleDesc").val();
	if (null == roleDesc || '' == roleDesc || undefined == roleDesc)
	{
		$("#addRoleDescEmpty").css("display","");
		return false;
	}
	$("#addRoleDescEmpty").css("display","none");
	return true;
}

function checkModifyRoleDesc()
{
	var roleDesc = $("#modifyRoleDesc").val();
	if (null == roleDesc || '' == roleDesc || undefined == roleDesc)
	{
		$("#modifyRoleDescEmpty").css("display","");
		return false;
	}
	$("#modifyRoleDescEmpty").css("display","none");
	return true;
}

function checkModifyRoleName()
{
	var roleName = $("#modifyRoleName").val();
	var roleId = $("#modifyRoleID").val();
	if (null == roleName || '' == roleName || undefined == roleName)
	{
		$("#modifyRoleNameEmpty").css("display","");
		return false;
	}
	else
	{
		$("#modifyRoleNameEmpty").css("display","none");
		$.post("checkModifyRoleName.do",{
			roleId : roleId,
			roleName : roleName
		},function(data){
			
			var isExist = data.isExist;
			if (isExist)
			{
				$("#modifyRoleNameExist").css("display","");
				return false;
			}
			else
			{
				$("#modifyRoleNameExist").css("display","none");
				return true
			}
		});
		
		return true;
	}
}

/**
 * 获取第一页角色内容
 */
function init(){
	getAjax(1);
	$("#page").val("1");	
}
var roleName = "";
var page;
function getRole(){
	page = 1;
	$("#page").val("1");
	roleName = $("#roleName").val();
	getAjax(page,roleName);
}

$(function(){
	var maxpage;
    $("#page").blur(function(){
    	var page = $("#page").val();
    	if(page > maxpage){
    		page = maxpage;
    		$("#page").val(page);
    	}
    	getAjax(page,roleName);
    });
});

function getPrePage(){
	var page = $("#page").val();
	if( page > 1){
		$("#page").val(eval(page+"-1"));
		getAjax($("#page").val(), roleName);
	}
}

function getNextPage(){
	var page = $("#page").val();
	if( page < maxpage){
		$("#page").val(eval(page+"+1"));
		getAjax($("#page").val(), roleName);
	}
}

function getPage(page,roleName){
	$("#page").val(page);
	getAjax(page, roleName);
}

function getLastPage(){
	$("#page").val(maxpage);
	getAjax(maxpage, roleName);
}

$(function() {
	$('.fancybox').fancybox();
})

function closeview(){
	$('[data-remodal-id="viewRoleInfo"]').remodal().close();
}

function closeModify(){
	$('[data-remodal-id="modifyRoleInfo"]').remodal().close();
}

function updateRole()
{
	var roleName = $("#modifyRoleName").val();
	var roleID = $("#modifyRoleID").val();
	var desc = $("#modifyRoleDesc").val();
	var remark = $("#modifyRoleRemark").val();
	var menuIdList = [];
	$('input:checkbox[name=modifyAuthChecked]:checked').each(function(i){
		
		menuIdList[i]= $(this).context.id;
	});
	var menuIds ="";
	for (var ii=0; ii<menuIdList.length;ii++)
	{
		if (ii == menuIdList.length -1)
		{
			menuIds = menuIds + menuIdList[ii];
		}
		else
		{
			menuIds = menuIds + menuIdList[ii] +",";
		}
		
	}
	
	$.post("modifyRole.do",{
		menuIds : menuIds,
		roleName : roleName,
		desc: desc,
		remark : remark,
		roleID : roleID
	},function(data)
	{
		$.fancybox.close();
		getAjax(1);
		$("#page").val("1");
	});
}

function saveModify()
{
	var modifyRoleNameValidator = checkModifyRoleName();
	var modifyRoleDescValidator = checkModifyRoleDesc();
	var menuIdList = [];
	$('input:checkbox[name=modifyAuthChecked]:checked').each(function(i){
		
		menuIdList[i]= $(this).context.id;
	});
	
//	if (menuIdList.length == 0)
//	{
//		$("#modifyRoleEmpty").css("display","");
//	}
//	else
//	{
//		$("#modifyRoleEmpty").css("display","none");
//	}
//	if (menuIdList.length == 0 || !modifyRoleDescValidator || !modifyRoleNameValidator)
	if (!modifyRoleDescValidator || !modifyRoleNameValidator)
	{
		return false;
	}
//	$("#modifyRoleEmpty").css("display","none");
	var nextUrl = "modifyRole.do?";  
	var theForm = $("#modifyRoleform");
	theForm.attr("action", nextUrl);  
	theForm.attr("onsubmit", '');  
	theForm.submit();
}

function addNewRole()
{
	var addRoleNameValidator = checkAddRoleName();
	var addRoleDescValidator = checkAddRoleDesc();
	
	
	var roleName = $("#addRoleName").val();
	var desc = $("#addRoleDesc").val();
	var remark = $("#addRoleRemark").val();
	var menuIdList = [];
	$('input:checkbox[name=addAuthChecked]:checked').each(function(i){
		
		menuIdList[i]= $(this).context.id;
	});
	
	var menuIds ="";
	
//	if (menuIdList.length == 0)
//	{
//		$("#addRoleEmpty").css("display","");
//	}
//	else
//	{
//		$("#addRoleEmpty").css("display","none");
//	}
	
//	if (menuIdList.length == 0 || !addRoleNameValidator || !addRoleDescValidator)
	if (!addRoleNameValidator || !addRoleDescValidator)
	{
		return false;
	}
	
//	$("#addRoleEmpty").css("display","none");
	for (var ii=0; ii<menuIdList.length;ii++)
	{
		if (ii == menuIdList.length -1)
		{
			menuIds = menuIds + menuIdList[ii];
		}
		else
		{
			menuIds = menuIds + menuIdList[ii] +",";
		}
		
	}
	$.post("addNewRole.do",{
		menuIds : menuIds,
		roleName : roleName,
		desc: desc,
		remark : remark
	},function(data)
	{
		$.fancybox.close();
		getAjax(1);
		$("#page").val("1");
	});
	 
}


// 查看权限
var menus;
//查看
function viewRoleInfo(roleID){
	$.post("getRoleInfo.do",{
		roleID : roleID
	},function(data){
		roleMenus = data.list;
		$("#roleTreeView").children().each(function(index) {
			$(this).remove();
		});
		
		$("#roleTreeView").append("<details class='menu' open=''><summary><input type='checkbox' name='defaultViewAuthChecked' checked='checked' disabled=''>实时监控(默认权限)</summary></details>"
        		+ "<li><input type='checkbox' name='defaultViewAuthChecked' checked='checked' disabled=''><a href='#'>(设备一览(默认权限))</a></li>");
		
		for (var i = 0; i < roleMenus.length; i++)
		{	
			var checked ='';
			if (roleMenus[i].menuDictModel.isAuth == true)
			{
				checked = "checked='checked'";
			}
			$("#roleTreeView").append("<details class='menu' open><summary><input type='checkbox' name='viewAuthChecked' disabled='true' value=" + roleMenus[i].menuDictModel.menuID + " id="+ roleMenus[i].menuDictModel.menuID + " " + checked + "></input>"+ roleMenus[i].menuDictModel.menuName +"</summary>");
			var subs = roleMenus[i].subMenuModels;
			if (subs.length!=0)
 			{
				$("#roleTreeView").append("<ul>")
			}
			for (var j = 0; j < subs.length; j++)
			{
				checked ='';
				if (subs[j].menuDictModel.isAuth == true)
				{
					checked = "checked='checked'";
				}
				$("#roleTreeView").append("<li><input type='checkbox' name='viewAuthChecked' disabled='true' value=" + subs[j].menuDictModel.menuID + " id="+ subs[j].menuDictModel.menuID + " " + checked +"></input><a href='#'>("+ subs[j].menuDictModel.menuName + ")</a></li>");
			}
			
			if (subs.length!=0)
 			{
				$("#roleTreeView").append("</ul>")
			}
			$("#roleTreeView").append("</details>");
		}
		$("#viewRoleDesc").val(data.desc);
		$("#viewRoleRemarkDesc").val(data.remarkDesc);
		$("#viewRoleName").val(data.roleName);
		
		$('[data-remodal-id="viewRoleInfo"]').remodal().open();
  		});
}

//添加
function addRoleInfo(){
	
	$("#addRoleName").val('');
	$("#addRoleDesc").val('');
	$("#addRoleRemark").val('');
//	$("#addRoleEmpty").css("display","none");
	$("#addRoleDescEmpty").css("display","none");
	$("#addRoleNameEmpty").css("display","none");
	$("#addUserNameExist").css("display","none");
	
	$.post("addRoleInfo.do",{
	},function(data){
		roleMenus = data.list;
		$("#addRoleTree").children().each(function(index) {
			$(this).remove();
		});
		
		$("#addRoleTree").append("<details class='menu' open=''><summary><input type='checkbox' name='defaultAddAuthChecked' checked='checked' disabled=''>实时监控(默认权限)</summary></details>"
        		+ "<li><input type='checkbox' name='defaultAddAuthChecked' checked='checked' disabled=''><a href='#'>(设备一览(默认权限))</a></li>");
		for (var i = 0; i < roleMenus.length; i++)
		{	
			$("#addRoleTree").append("<details class='menu' open><summary><input type='checkbox' name='addAuthChecked' onclick='checkAddSelectNum(" + roleMenus[i].menuDictModel.menuID + ")' value=" + roleMenus[i].menuDictModel.menuID + " id="+ roleMenus[i].menuDictModel.menuID + ">"+ roleMenus[i].menuDictModel.menuName +"</summary>");
			var subs = roleMenus[i].subMenuModels;
			if (subs.length!=0)
 			{
				$("#addRoleTree").append("<ul>")
			}
			for (var j = 0; j < subs.length; j++)
			{
				$("#addRoleTree").append("<li><input type='checkbox' name='addAuthChecked' onclick='checkAddSelectNum(" +subs[j].menuDictModel.menuID +")' value=" + subs[j].menuDictModel.menuID + " id="+ subs[j].menuDictModel.menuID + "><a href='#'>("+ subs[j].menuDictModel.menuName + ")</a></li>");
			}
			
			if (subs.length!=0)
 			{
				$("#addRoleTree").append("</ul>")
			}
			$("#addRoleTree").append("</details>");
		}
  		});
}

function checkAddSelectNum(value)
{
	
	var menuIdList = [];
	$('input:checkbox[name=addAuthChecked]:checked').each(function(i){
		
		menuIdList[i]= $(this).context.id;
	});
	
	var menuIds = "";
	for (var ii=0; ii<menuIdList.length;ii++)
	{
		if (ii == menuIdList.length -1)
		{
			menuIds = menuIds + menuIdList[ii];
		}
		else
		{
			menuIds = menuIds + menuIdList[ii] +",";
		}
	}
	
	var roleMenus;
	$.ajax({  
        url : 'getCheckedMenus.do',  
        async : false, // 注意此处需要同步，因为返回完数据后，下面才能让结果的第一条selected  
        type : "POST",  
        data : {
        	checkId : value,
        	menuIds : menuIds,
        },
        success : function(data) {  
        	roleMenus = data.menuModels;
        	
        	$("#addRoleTree").children().each(function(index) {
    			$(this).remove();
    		});
        	
    		$("#addRoleTree").append("<details class='menu' open=''><summary><input type='checkbox' name='defaultAddAuthChecked' checked='checked' disabled=''>实时监控(默认权限)</summary></details>"
            		+ "<li><input type='checkbox' name='defaultAddAuthChecked' checked='checked' disabled=''><a href='#'>(设备一览(默认权限))</a></li>");
    		
    		for (var i = 0; i < roleMenus.length; i++)
    		{	
    			
    			var checked ='';
    			if (roleMenus[i].menuDictModel.isAuth == true)
    			{
    				checked = "checked='checked'";
    			}
    			
    			$("#addRoleTree").append("<details class='menu' open><summary><input type='checkbox' name='addAuthChecked' onclick='checkAddSelectNum(" + roleMenus[i].menuDictModel.menuID + ")' value=" + roleMenus[i].menuDictModel.menuID + "  id="+ roleMenus[i].menuDictModel.menuID + " " + checked +">"+ roleMenus[i].menuDictModel.menuName +"</summary>");
    			var subs = roleMenus[i].subMenuModels;
    			if (subs.length!=0)
     			{
    				$("#addRoleTree").append("<ul>")
    			}
    			for (var j = 0; j < subs.length; j++)
    			{
    				checked ='';
    				if (subs[j].menuDictModel.isAuth == true)
    				{
    					checked = "checked='checked'";
    				}
    				
    				$("#addRoleTree").append("<li><input type='checkbox' name='addAuthChecked' onclick='checkAddSelectNum(" + subs[j].menuDictModel.menuID + ")' value=" + subs[j].menuDictModel.menuID + " id="+ subs[j].menuDictModel.menuID + " " + checked +"><a href='#'>("+ subs[j].menuDictModel.menuName + ")</a></li>");
    			}
    			
    			if (subs.length!=0)
     			{
    				$("#addRoleTree").append("</ul>")
    			}
    			$("#addRoleTree").append("</details>");
    		}
        }  
    });  
	
//	if (null == roleMenus || undefined== roleMenus || roleMenus.length == 0)
//	{
//		$("#addRoleEmpty").css("display","");
//	}
//	else
//	{
//		$("#addRoleEmpty").css("display","none");
//	}
}

function checkModifySelectNum(value)
{
	var menuIdList = [];
	$('input:checkbox[name=modifyAuthChecked]:checked').each(function(i){
		
		menuIdList[i]= $(this).context.id;
	});
	
	var menuIds = "";
	for (var ii=0; ii<menuIdList.length;ii++)
	{
		if (ii == menuIdList.length -1)
		{
			menuIds = menuIds + menuIdList[ii];
		}
		else
		{
			menuIds = menuIds + menuIdList[ii] +",";
		}
	}
	var roleMenus;
	$.ajax({  
        url : 'getCheckedMenus.do',  
        async : false, // 注意此处需要同步，因为返回完数据后，下面才能让结果的第一条selected  
        type : "POST",  
        data : {
        	checkId : value,
        	menuIds : menuIds,
        },
        success : function(data) {  
        	roleMenus = data.menuModels;
        	
        	$("#modifyRoleTreeView").children().each(function(index) {
    			$(this).remove();
    		});
        	
        	$("#modifyRoleTreeView").append("<details class='menu' open=''><summary><input type='checkbox' name='defaultModifyAuthChecked' checked='checked' disabled=''>实时监控(默认权限)</summary></details>"
        		+ "<li><input type='checkbox' name='defaultModifyAuthChecked' checked='checked' disabled=''><a href='#'>(设备一览(默认权限))</a></li>");
    		for (var i = 0; i < roleMenus.length; i++)
    		{	
    			var checked ='';
    			if (roleMenus[i].menuDictModel.isAuth == true)
    			{
    				checked = "checked='checked'";
    			}
    			
    			$("#modifyRoleTreeView").append("<details class='menu' open><summary><input type='checkbox' name='modifyAuthChecked' onclick='checkModifySelectNum(" + roleMenus[i].menuDictModel.menuID + ")' value=" + roleMenus[i].menuDictModel.menuID + " id="+ roleMenus[i].menuDictModel.menuID + " "+ checked +"></input>"+ roleMenus[i].menuDictModel.menuName +"</summary>");
    			var subs = roleMenus[i].subMenuModels;
    			if (subs.length!=0)
     			{
    				$("#modifyRoleTreeView").append("<ul>")
    			}
    			for (var j = 0; j < subs.length; j++)
    			{
    				checked ='';
    				if (subs[j].menuDictModel.isAuth == true)
    				{
    					checked = "checked='checked'";
    				}
    				
    				$("#modifyRoleTreeView").append("<li><input type='checkbox' name='modifyAuthChecked' onclick='checkModifySelectNum(" + subs[j].menuDictModel.menuID + ")' value=" + subs[j].menuDictModel.menuID + " id="+ subs[j].menuDictModel.menuID + " "+ checked +"></input><a href='#'>("+ subs[j].menuDictModel.menuName + ")</a></li>");
    			}
    			
    			if (subs.length!=0)
     			{
    				$("#modifyRoleTreeView").append("</ul>")
    			}
    			$("#modifyRoleTreeView").append("</details>");
    		}
    		
//    		$('[data-remodal-id="modifyRoleInfo"]').remodal().open();
      		}});
	
//	if (null == roleMenus || undefined== roleMenus || roleMenus.length == 0)
//	{
//		$("#modifyRoleEmpty").css("display","");
//	}
//	
//	if (menuIdList.length == 0)
//	{
//		$("#modifyRoleEmpty").css("display","");
//	}
//	else
//	{
//		$("#modifyRoleEmpty").css("display","none");
//	}
}


//编辑
function modifyRoleInfo(roleID){
	$.post("getRoleInfo.do",{
		roleID : roleID
	},function(data){
		roleMenus = data.list;
		$("#modifyRoleTreeView").children().each(function(index) {
			$(this).remove();
		});
		$("#modifyRoleName").val(data.roleName);
		$("#modifyRoleDesc").val(data.desc);
		$("#modifyRoleRemark").val(data.remarkDesc);
		$("#modifyRoleID").val(data.roleID);
		
		$("#modifyRoleTreeView").append("<details class='menu' open=''><summary><input type='checkbox' name='defaultModifyAuthChecked' checked='checked' disabled=''>实时监控(默认权限)</summary></details>"
        		+ "<li><input type='checkbox' name='defaultModifyAuthChecked' checked='checked' disabled=''><a href='#'>(设备一览(默认权限))</a></li>");
		for (var i = 0; i < roleMenus.length; i++)
		{	
			var checked ='';
			if (roleMenus[i].menuDictModel.isAuth == true)
			{
				checked = "checked='checked'";
			}
			
			$("#modifyRoleTreeView").append("<details class='menu' open><summary><input type='checkbox' name='modifyAuthChecked' onclick='checkModifySelectNum(" + roleMenus[i].menuDictModel.menuID + ")' value=" + roleMenus[i].menuDictModel.menuID + " id="+ roleMenus[i].menuDictModel.menuID + " "+ checked +"></input>"+ roleMenus[i].menuDictModel.menuName +"</summary>");
			var subs = roleMenus[i].subMenuModels;
			if (subs.length!=0)
 			{
				$("#modifyRoleTreeView").append("<ul>")
			}
			for (var j = 0; j < subs.length; j++)
			{
				checked ='';
				if (subs[j].menuDictModel.isAuth == true)
				{
					checked = "checked='checked'";
				}
				
				$("#modifyRoleTreeView").append("<li><input type='checkbox' name='modifyAuthChecked' onclick='checkModifySelectNum(" + subs[j].menuDictModel.menuID + ")' value=" + subs[j].menuDictModel.menuID + " id="+ subs[j].menuDictModel.menuID + " "+ checked +"></input><a href='#'>("+ subs[j].menuDictModel.menuName + ")</a></li>");
			}
			
			if (subs.length!=0)
 			{
				$("#modifyRoleTreeView").append("</ul>")
			}
			$("#modifyRoleTreeView").append("</details>");
		}
		
		$('[data-remodal-id="modifyRoleInfo"]').remodal().open();
  		});
}

var role;
var count;
function getAjax(page, roleName) {
	$.post("getRole.do",{
				page : page,
				roleName : roleName,
			},
			function(data) {
				role = data.list;
				count = data.count;
				maxpage = data.pages;
				$("#roleInfoCount").html("");
				$("#roleManagement").children().each(function(index) {
					$(this).remove();
				});
				if (count != 0) {
					$("#roleInfoCount").append(
							"共" + count + "条 每页10条" + " 页次" + page
									+ "/" + maxpage);
				} else {
					$("#page").val("0");
					$("#roleManagement").append('<tr><td colspan=10>暂无数据</td></tr>');
					$("#roleInfoCount").append(
							"共" + count + "条 每页10条" + " 页次1/1");
				}
	
				for (var i = 0; i < role.length; i++) {
					var tr1;
	            	var tr2;
	            	tr1 = "<tr>"+	
		            "<td>"+role[i].roleName+"</td>"+
		            "<td>"+role[i].description+"</td>"+
		            "<td>"+role[i].createName+"</td>"+
		            "<td>"+formatterDateTime(role[i].createTime, "yyyy-MM-dd HH:mm:ss")+"</td>"+
		            "<td>"+role[i].updateName+"</td>"+
		            "<td>"+formatterDateTime(role[i].updateTime,"yyyy-MM-dd HH:mm:ss")+"</td>"
					if (role[i].deleteStatus == "1") {
						tr1 = "<td><a href='#edit-RoleManageMent' class='table-link' onclick='modifyRoleInfo("
								+ role[i].roleID
								+ ")'>修改</a>"
								+ "<a href='#view-RoleManageMent' class='table-link' onclick='viewRoleInfo("
								+ role[i].roleID
								+ ")'>查看</a>"
								+ "<a href='#' class='table-link c-danger' onclick='deleteRole("
								+ role[i].roleID + ")'>删除</a></tr>"
					}
					else{
		            	tr2 = "<td><a href='#edit-RoleManageMent' class='table-link' onclick='modifyRoleInfo("+ role[i].roleID +")'>编辑</a>" +
		                      "<a href='#view-RoleManageMent' class='table-link' onclick='viewRoleInfo("+ role[i].roleID +")'>查看</a>" +
		                      "<a href='#' class='table-link c-danger' onclick='deleteRole("+ role[i].roleID +")'>删除</a></tr>"
		            }
					$("#roleManagement").append(tr1+tr2);
				}
			});
}
	
	
	
function deleteRole(roleID) {
	var count = 0;
	$.ajax({  
        url : 'isRoleRelatedUser.do',  
        async : false, // 注意此处需要同步，因为返回完数据后，下面才能让结果的第一条selected  
        type : "POST",  
        data : {
        	roleID : roleID,
        },success : function(data) {
        	count = data.count;
        }
     });
	
	if (count == 0)
	{
		$("#changeModalPart").children().each(function(index) {
			$(this).remove();
		});
		$("#changeModalPart").append("<p id='msgTips' style='text-align:center'>是否删除该数据？</p>" + 
			"<div class='btn-group'>" + 
				"<button data-remodal-action='confirm' class='btn'>是</button>" + 
				"<button data-remodal-action='cancel' class='btn btn-gray'>否</button></div>");
		$('[data-remodal-id="deleteConfirm"]').remodal().open();
		$('[data-remodal-action="confirm"]').click(function() {
			$.post("deleteRole.do?roleID="+roleID, function(data) {
				if (data.result == 1) {
					$('[data-remodal-id="deleteConfirm"]').remodal().close();
				}
			}, 'json');
			window.location.reload(true);
		})
	}
	else
	{
		$("#changeModalPart").children().each(function(index) {
			$(this).remove();
		});
		
		$("#changeModalPart").append("<p id='msgTips' style='text-align:center'>角色已经关联用户，不能被删除!</p>" + 
				"<div class='btn-group'>" + 
					"<button data-remodal-action='cancel' class='btn btn-gray'>确定</button></div>");
		$('[data-remodal-id="deleteConfirm"]').remodal().open();
	}
	
	
};

function getCheckedString(a,b)
{
	a ='';
	if (b == true)
	{
		checked = "checked='checked'";
	}
	return checked;
}


function formatterDateTime (dateTime) {
	var date = new Date(dateTime);
    var datetime = date.getFullYear()
            + "-"// "年"
            + ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
                    + (date.getMonth() + 1))
            + "-"// "月"
            + (date.getDate() < 10 ? "0" + date.getDate() : date
                    .getDate());
//            + " "
//            + (date.getHours() < 10 ? "0" + date.getHours() : date
//                    .getHours())
//            + ":"
//            + (date.getMinutes() < 10 ? "0" + date.getMinutes() : date
//                    .getMinutes())
//            + ":"
//            + (date.getSeconds() < 10 ? "0" + date.getSeconds() : date
//                    .getSeconds());
    return datetime;
}
