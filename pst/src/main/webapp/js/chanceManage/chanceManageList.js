var datatable;
$(function(){
	datatable = $("#chanceManage").dataTable({
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
			"url" : "projectsManagement/chanceManage/getChanceManageLists.do",
			"dataSrc" : "data",
			"type" : "post",
			"data" : function(d) {
					d.sch = d.search.value;
					if (d.order[0].column == 0) {
						d.column = "create_time";
					}
					if (d.order[0].column == 1) {
						d.column = "project_name";
					}
					if (d.order[0].column == 2) {
						d.column = "chance_project_manager_id";
					}
					if (d.order[0].column == 3) {
						d.column = "start_time";
					}
					if (d.order[0].column == 4) {
						d.column = "lead_time";
					}
					if (d.order[0].column == 5) {
						d.column = "forecast_quotation";
					}
					d.dir = d.order[0].dir;
				}
			},
			columns : [ 
			   {
				data : "chanceManageId"
			}, {
				data : "projectName"
			}, {
				data : "projectManagerName"
			}, {
				data : "startTime"
			}, {
				data : "leadTime"
			}, {
				data : "forecastQuotation"
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
			}, {
				"targets" : 3,
				"bSortable" : true,
				"render" : function(data, type, row) {
					return "<div class='text-center'>" + new Date(data).Format("yyyy-MM-dd") + "</div>";
				}
			},{
				"targets" : 4,
				"bSortable" : true,
				"render" : function(data, type, row) {
					return "<div class='text-center'>" + new Date(data).Format("yyyy-MM-dd") + "</div>";
				}
			},{
				"targets" : 5,
				"bSortable" : true,
				"render" : function(data, type, row) {
					return "<span style='float:right;'>￥" + (row.forecastQuotation || 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,') + "</span>";
				}
			},{
				"targets" : 6,
				"bSortable" : false,
				"render" : function(data, type, row) {
					var action = "<div class='text-center'>";
					if ($("#sessionUserRole").val() =='general_manager' || $("#sessionUserRole").val() =='admin' || $("#sessionUserId").val() == row.creatorId) {
						action = action + "<a href='projectsManagement/chanceManage/edit.do?chanceManageId="+ row.chanceManageId +"' class='btn mini purple'><i class='icon-edit'></i> 编辑 </a>&nbsp;<a href='javascript:;' class='btn mini black' onclick='deleteChanceManage("+row.chanceManageId+")'><i class='icon-trash'></i> 删除 </a>&nbsp;<a href='projectsManagement/projects/addProject.do?chanceManageId=" + row.chanceManageId + "' class='btn mini green'><i class='icon-list'></i> 导入到正式项目</a>";
					}
					action = action + "&nbsp;<a href='projectsManagement/chanceManage/view.do?chanceManageId="+ row.chanceManageId +"' class='btn mini blue'><i class='icon-eye-open'></i> 详情</a>&nbsp;</div>";
					return action;
				}
			} 
			]
	});
});

function deleteChanceManage(id) {
	$("#deleteConfirm").modal();
	$("#deleteChanceManage").unbind('click').bind('click',function() {
		$.post("projectsManagement/chanceManage/deleteChanceManage.do", {
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