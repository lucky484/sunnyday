var dataTable;
$(function() {
	dataTable = $('#projectsTable').dataTable({
		"autoWidth" : true,
	    "language": {
	       url: basePath + 'plugins/datatables/lang/chinese.json'
	    },
		"ordering" : true,
		"processing" : false,
		"serverSide" : true,
		"bLengthChange" : false,
		"bPaginate" : true,
		"pageLength" : 20,
		"pagingType" : "full_numbers",
		"order": [[ 0, "desc" ]],
		"searching" : false,
		"ajax" : {
			"url" : "getProjects.do",
			"dataSrc" : "data",
			"type" : "post",
			"data" : function(d) {
				d.sch = d.search.value;
				if (d.order[0].column == 0) {
					d.column = "create_time";
				}
				if (d.order[0].column == 1) {
					d.column = "project_code";
				}
				if (d.order[0].column == 2) {
					d.column = "project_name";
				}
				if (d.order[0].column == 3) {
					d.column = "project_manager";
				}
				if (d.order[0].column == 4) {
					d.column = "implement_manager";
				}
				if (d.order[0].column == 5) {
					d.column = "start_time";
				}
				if (d.order[0].column == 6) {
					d.column = "medial_time";
				}
				if (d.order[0].column == 7) {
					d.column = "lead_time";
				}
				if (d.order[0].column == 8) {
					d.column = "project_status";
				}
				if (d.order[0].column == 9) {
					d.column = "project_type";
				}
				if (d.order[0].column == 10) {
					d.column = "customer_name_short";
				}
				d.dir = d.order[0].dir;
			}
		},
		columns : [ {
			data : "projectId"
		}, {
			data : "projectCode"
		}, {
			data : "projectName"
		}, {
			data : "projectManager"
		}, {
			data : "implementManager"
		},{
			data : "startTime"
		}, {
			data : "medialTime"
		}, {
			data : "leadTime"
		}, {
			data : "projectStatus"
		}, {
			data : "customerName"
		}, {
			data : "crTotal"
		}, {
			data : "action"
		} ],
		"columnDefs" : [ {
			"targets" : 0,
			"bSortable" : false,
			"bVisible" : false
		},{
			"targets" : 5,
			"bSortable" : true,
			"render" : function(data, type, row) {
				return "<div class='text-c'>" + new Date(data).Format("MM-dd") + "</div>";
			}
		}, {
			"targets" : 6,
			"bSortable" : true,
			"render" : function(data, type, row) {
				return "<div class='text-c'>" + new Date(data).Format("MM-dd") + "</div>";
			}
		}, {
			"targets" : 7,
			"bSortable" : true,
			"render" : function(data, type, row) {
				return "<div class='text-c'>" + new Date(data).Format("MM-dd") + "</div>";
			}
		}, {
			"targets" : 8,
			"bSortable" : true,
			"render" : function(data, type, row) {
				return "<div class='text-c'>" + data + "</div>";
			}
		},{
			"targets" : 10,
			"bSortable" : true,
			"render" : function(data, type, row) {
				return "<div class='text-c'>" + data + "</div>";
			}
		}, {
			"targets" : 11,
			"bSortable" : false,
			"render" : function(data, type, row) {
				var action = "<div class='text-c'>";
				if ($("#sessionUserRole",window.parent.document).val() =='general_manager' || $("#sessionUserRole",window.parent.document).val() =='admin' || $("#sessionUserId",window.parent.document).val() == row.creatorId) {
					action = action + "<a href='edit.do?projectId=" + row.projectId + "'class='btn-primary' style='padding:4px 4px;text-decoration:none'><i class='Hui-iconfont' style='font-size:15px;'>&#xe60c;</i> 编辑 </a>&nbsp;<a href='javascript:deleteProject(" + row.projectId + ")' class='btn-danger' style='padding:4px 4px;text-decoration:none' ><i class='Hui-iconfont' style='font-size:15px;'></i> 删除 </a>";
				}
				action = action + "&nbsp;<a href='viewProject.do?projectId=" + row.projectId + "' class='btn-secondary' style='padding:4px 4px;text-decoration:none'><i class='Hui-iconfont' style='font-size:15px;'>&#xe725;</i> 详情</a>&nbsp;" + "<a href='Requirement/crList.do?projectId=" + row.projectId + "' class='btn-success' style='padding:4px 4px;text-decoration:none'><i class='Hui-iconfont' style='font-size:15px;'>&#xe667;</i> CRs </a></div>";
				return action;
			}
		} ]
	});
});

function deleteProject(id) {
	layer.confirm('确认要删除吗？',function(index){
		$.post("deleteProject.do", {
			projectId : id
		}, function(data) {
			if (data == 1) {
				layer.msg('删除成功!',{icon:1,time:1000});
				dataTable.fnDraw();
			}
		}, 'json');
	});
}

$('#projectTypeNumber').change(function() {
	dataTable.DataTable().column(10).search(
			$(this).find("option:selected").val()
	    ).draw();
});

$('#projectManager').change(function() {
	dataTable.DataTable().column(3).search(
	        $(this).find("option:selected").val()
	    ).draw();
});

$("#search").click(function(){
	dataTable.fnDraw();
});

$("#clean").click(function(){
	$("#projectNumber").val('');
	$("#projectName").val('');
	$("#projectManager").val('');
	$("#startTime").val('');
	$("#medialTime").val('');
	$("#leadTime").val('');
});
$(function(){
	if (jQuery().datepicker) {
		$('.date-picker').datepicker().on('changeDate', function(ev) {
			$(this).valid();
			$(this).datepicker('hide');
		});
	}
});
