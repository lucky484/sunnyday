var dataTable;
$(function() {
	if ($("#sessionUserRole").val() == 'general_manager') {
		dataTable = $('#usersTable').dataTable({
			"autoWidth" : true,
			"ordering" : true,
			"processing" : true,
			"serverSide" : true,
			"bSort" : true,
			"bLengthChange" : false,
			"bPaginate" : true,
			"pageLength" : 20,
			"order" : [ [ 0, "desc" ] ],
			"pagingType" : "full_numbers",
			"searching" : true,
			"ajax" : {
				"url" : "usersManagement/users/getUsers.do",
				"dataSrc" : "data",
				"type" : "post",
				"data" : function(d) {
					d.sch = d.search.value;
					if (d.order[0].column == 0) {
						d.column = "user_id";
					}
					if (d.order[0].column == 1) {
						d.column = "user_name";
					}
					if (d.order[0].column == 2) {
						d.column = "last_login_time";
					}
					if (d.order[0].column == 3) {
						d.column = "create_time";
					}
					if (d.order[0].column == 4) {
						d.column = "role_id";
					}
					if (d.order[0].column == 5) {
						d.column = "english_name";
					}
					if (d.order[0].column == 6) {
						d.column = "chinese_name";
					}
					d.dir = d.order[0].dir;
				}
			},
			columns : [ {
				data : "userName"
			}, {
				data : "lastLoginTime"
			}, {
				data : "createTime"
			}, {
				data : "role.roleDescription"
			}, {
				data : "englishName"
			}, {
				data : "chineseName"
			}, {
				data : "userId"
			} ],
			"columnDefs" : [ {
				"targets" : 0,
				"bSortable" : true,
				"render" : function(data, type, row) {
					return "<div class='text-center'>" + data + "</div>";
				}
			}, {
				"targets" : 1,
				"bSortable" : true,
				"render" : function(data, type, row) {
					if (data == null || data == "") {
						return "";
					} else {
						return "<div class='text-center'>" + new Date(data).Format("yyyy-MM-dd hh:mm:ss") + "</div>";
					}
				}
			}, {
				"targets" : 2,
				"bSortable" : true,
				"render" : function(data, type, row) {
					return "<div class='text-center'>" + new Date(data).Format("yyyy-MM-dd hh:mm:ss") + "</div>";
				}
			}, {
				"targets" : 3,
				"bSortable" : true,
				"render" : function(data, type, row) {
					return "<div class='text-center'>" + data + "</div>";
				}
			}, {
				"targets" : 4,
				"bSortable" : true,
				"render" : function(data, type, row) {
					return "<div class='text-center'>" + data + "</div>";
				}
			}, {
				"targets" : 5,
				"bSortable" : true,
				"render" : function(data, type, row) {
					return "<div class='text-center'>" + data + "</div>";
				}
			}, {
				"targets" : 6,
				"bSortable" : false,
				"render" : function(data, type, row) {
					var editBtn = "<a href='usersManagement/users/editUserList.do?userId=" + row.userId + "' class='btn mini purple'><i class='icon-edit'></i> 编辑 </a>&nbsp;";
					var deleteBtn = "<a href='javascript:deleteUser(" + row.userId + ")' class='btn mini black'><i class='icon-trash'></i> 删除 </a>";
					var action = "<div class='text-center'>";
					action += editBtn;
					action += deleteBtn;
					action += "</div>";
					return action;
				}
			} ]
		});
	} else {
		dataTable = $('#usersTable').dataTable({
			"autoWidth" : true,
			"ordering" : true,
			"processing" : true,
			"serverSide" : true,
			"bSort" : true,
			"bLengthChange" : false,
			"bPaginate" : true,
			"pageLength" : 10,
			"order" : [ [ 0, "desc" ] ],
			"pagingType" : "full_numbers",
			"searching" : true,
			"ajax" : {
				"url" : "usersManagement/users/getUsers.do",
				"dataSrc" : "data",
				"data" : function(d) {
					d.sch = d.search.value;
					if (d.order[0].column == 0) {
						d.column = "user_id";
					}
					if (d.order[0].column == 1) {
						d.column = "user_name";
					}
					if (d.order[0].column == 2) {
						d.column = "last_login_time";
					}
					if (d.order[0].column == 3) {
						d.column = "create_time";
					}
					if (d.order[0].column == 4) {
						d.column = "role_id";
					}
					if (d.order[0].column == 5) {
						d.column = "english_name";
					}
					if (d.order[0].column == 6) {
						d.column = "chinese_name";
					}
					d.dir = d.order[0].dir;
				}
			},
			columns : [ {
				data : "userName"
			}, {
				data : "userPassword"
			}, {
				data : "lastLoginTime"
			}, {
				data : "createTime"
			}, {
				data : "role.roleDescription"
			}, {
				data : "englishName"
			}, {
				data : "chineseName"
			} ],
			"columnDefs" : [ {
				"targets" : 0,
				"bSortable" : true,
				"render" : function(data, type, row) {
					return "<div class='text-center'>" + data + "</div>";
				}
			}, {
				"targets" : 1,
				"bSortable" : true,
				"render" : function(data, type, row) {
					return "<div class='text-center'>" + data + "</div>";
				}
			}, {
				"targets" : 2,
				"bSortable" : true,
				"render" : function(data, type, row) {
					if (data == null || data == "") {
						return "";
					} else {
						return "<div class='text-center'>" + new Date(data).Format("yyyy-MM-dd hh:mm:ss") + "</div>";
					}
				}
			}, {
				"targets" : 3,
				"bSortable" : true,
				"render" : function(data, type, row) {
					return "<div class='text-center'>" + new Date(data).Format("yyyy-MM-dd hh:mm:ss") + "</div>";
				}
			}, {
				"targets" : 4,
				"bSortable" : true,
				"render" : function(data, type, row) {
					return "<div class='text-center'>" + data + "</div>";
				}
			}, {
				"targets" : 5,
				"bSortable" : true,
				"render" : function(data, type, row) {
					return "<div class='text-center'>" + data + "</div>";
				}
			}, {
				"targets" : 6,
				"bSortable" : true,
				"render" : function(data, type, row) {
					return "<div class='text-center'>" + data + "</div>";
				}
			} ]
		});
	}
});

function deleteUser(id) {
	$("#deleteConfirm").modal();
	$("#deleteUser").unbind('click').bind('click',function() {
		$.post("usersManagement/users/deleteUser.do", {
			userId : id
		}, function(data) {
			if (data == 1) {
				dataTable.fnDraw();
			}
		}, 'json');
	});
}