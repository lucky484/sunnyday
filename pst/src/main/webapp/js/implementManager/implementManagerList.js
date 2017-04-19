var datatable;
$(function(){
	datatable = $("#implementManager").dataTable({
		"autoWidth" : true,
		"ordering" : true,
		"processing" : false,
		"language": {
	       url: basePath + 'plugins/datatables/lang/chinese.json'
	    },
		"serverSide" : true,
		"bLengthChange" : false,
		"bPaginate" : true,
		"pageLength" : 20,
		"pagingType" : "full_numbers",
		"order": [[ 0, "desc" ]],
		"searching" : true,
		"ajax" : {
			"url" : "getImplementManagerLists.do",
			"dataSrc" : "data",
			"type" : "post",
			"data" : function(d) {
					d.sch = d.search.value;
					if (d.order[0].column == 0) {
						d.column = "create_time";
					}
					if (d.order[0].column == 1) {
						d.column = "name";
					}
					if (d.order[0].column == 2) {
						d.column = "email";
					}
					if (d.order[0].column == 3) {
						d.column = "phone";
					}
					d.dir = d.order[0].dir;
				}
			},
			columns : [ 
			   {
				data : "implementManagerId"
			}, {
				data : "name"
			}, {
				data : "email"
			}, {
				data : "phone"
			}, {
				data : "createTime"
			}],
			"columnDefs" : [ {
				"targets" : 0,
				"bSortable" : false,
				"bVisible" : false
			}, {
				"targets" : 1,
				"bSortable" : true,
				"render" : function(data, type, row) {
					return "<div class='text-c'>" + data + "</div>";
				}
			}, {
				"targets" : 2,
				"bSortable" : true,
				"render" : function(data, type, row) {
					return "<div class='text-c'>" + data + "</div>";
				}
			},{
				"targets" : 3,
				"bSortable" : true,
				"render" : function(data, type, row) {
					return "<div class='text-c'>" + data + "</div>";
				}
			}, {
				"targets" : 4,
				"bSortable" : true,
				"render" : function(data, type, row) {
					return "<div class='text-c'>" + new Date(data).Format("yyyy-MM-dd") + "</div>";
				}
			},{
				"targets" : 5,
				"bSortable" : false,
				"render" : function(data, type, row) {
					var action = "<div class='text-c'>";
					if ($("#sessionUserRole",window.parent.document).val() =='general_manager' || $("#sessionUserRole",window.parent.document).val() =='admin' || $("#sessionUserId",window.parent.document).val() == row.creatorId) {
						action = action + "<a href='edit.do?implementManagerId="+ row.implementManagerId +"' class='btn-primary' style='padding:4px 4px;text-decoration:none'><i class='Hui-iconfont' style='font-size:15px;'>&#xe60c;</i> 编辑 </a>&nbsp;" +
								"<a href='javascript:;' class='btn-danger' style='padding:4px 4px;text-decoration:none' onclick='deleteImplementManager("+row.implementManagerId+")'><i class='Hui-iconfont' style='font-size:15px;'></i> 删除</a>";
					}
					action = action + "&nbsp;<a href='view.do?implementManagerId="+ row.implementManagerId +"' class='btn-secondary' style='padding:4px 4px;text-decoration:none'><i class='Hui-iconfont' style='font-size:15px;'>&#xe725;</i> 详情</a>&nbsp;</div>";
					return action;
				}
			} 
			]
	});
});

function deleteImplementManager(id) {
	$("#deleteConfirm").modal();
	$("#deleteImplementManager").unbind('click').bind('click',function() {
		$.post("projectsManagement/implementManager/deleteImplementManager.do", {
			id : id
		}, function(data) {
			if (data.result == 1) {
				datatable.fnDraw();
			} else {
				$.gritter.removeAll();
				$.gritter.add({
					title : '删除失败',
					text : '项目删除失败，请稍后再试或联系管理员'
				});
			}
		}, 'json');
	});
}