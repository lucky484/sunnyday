var datatable;
$(function(){
	datatable = $("#outsourcingManage").dataTable({
		"autoWidth" : true,
		"ordering" : true,
		"processing" : true,
		"serverSide" : true,
		"bLengthChange" : false,
		"bPaginate" : true,
		"pageLength" : 20,
		"pagingType" : "full_numbers",
		"order": [[ 0, "desc" ]],
		"searching" : true,
		"ajax" : {
			"url" : "projectsManagement/outsourcingManage/getOutsourcingManageLists.do",
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
				data : "outsourcingManageId"
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
					return "<div class='text-center'>" + data + "</div>";
				}
			}, {
				"targets" : 2,
				"bSortable" : true,
				"render" : function(data, type, row) {
					return "<div class='text-center'>" + data + "</div>";
				}
			},{
				"targets" : 3,
				"bSortable" : true,
				"render" : function(data, type, row) {
					return "<div class='text-center'>" + data + "</div>";
				}
			}, {
				"targets" : 4,
				"bSortable" : true,
				"render" : function(data, type, row) {
					return "<div class='text-center'>" + new Date(data).Format("yyyy-MM-dd") + "</div>";
				}
			},{
				"targets" : 5,
				"bSortable" : false,
				"render" : function(data, type, row) {
					var action = "<div class='text-center'>";
					if ($("#sessionUserRole").val() =='general_manager' || $("#sessionUserRole").val() =='admin' || $("#sessionUserId").val() == row.creatorId) {
						action = action + "<a href='projectsManagement/outsourcingManage/edit.do?outsourcingManageId="+ row.outsourcingManageId +"' class='btn mini purple'><i class='icon-edit'></i> 编辑 </a>&nbsp;<a href='javascript:;' class='btn mini black' onclick='deleteOutsourcingManager("+row.outsourcingManageId+")'><i class='icon-trash'></i> 删除 </a>";
					}
					action = action + "&nbsp;<a href='projectsManagement/outsourcingManage/view.do?outsourcingManageId="+ row.outsourcingManageId +"' class='btn mini blue'><i class='icon-eye-open'></i> 详情</a>&nbsp;</div>";
					return action;
				}
			} 
			]
	});
});

function deleteOutsourcingManager(id) {
	$("#deleteConfirm").modal();
	$("#deleteOutSourcingManage").unbind('click').bind('click',function() {
		$.post("projectsManagement/outsourcingManage/deleteOutsourcingManage.do", {
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