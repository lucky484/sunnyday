var dataTable;
$(function() {
	dataTable = $('#projectsForFinshTable').dataTable({
		"autoWidth" : true,
		"ordering" : true,
		"processing" : true,
		"serverSide" : true,
		"bLengthChange" : false,
		"bPaginate" : true,
		"pageLength" : 20,
		"pagingType" : "full_numbers",
		"order": [[ 0, "desc" ]],
		"searching" : false,
		"ajax" : {
			"url" : "projectsManagement/projects/getProjectsForFinsh.do",
			"dataSrc" : "data",
			"type" : "post",
			"data" : function(d) {
				d.sch = d.search.value;
				d.projectNumber = $("#projectNumber").val();
				d.projectName = $("#projectName").val();
				d.projectManager = $("#projectManager").find("option:selected").text();
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
				return "<div class='text-center'>" + new Date(data).Format("MM-dd") + "</div>";
			}
		}, {
			"targets" : 6,
			"bSortable" : true,
			"render" : function(data, type, row) {
				return "<div class='text-center'>" + new Date(data).Format("MM-dd") + "</div>";
			}
		}, {
			"targets" : 7,
			"bSortable" : true,
			"render" : function(data, type, row) {
				return "<div class='text-center'>" + new Date(data).Format("MM-dd") + "</div>";
			}
		}, {
			"targets" : 8,
			"bSortable" : true,
			"render" : function(data, type, row) {
				return "<div class='text-center'>" + data + "</div>";
			}
		},{
			"targets" : 10,
			"bSortable" : true,
			"render" : function(data, type, row) {
				return "<div class='text-center'>" + data + "</div>";
			}
		}, {
			"targets" : 11,
			"bSortable" : false,
			"render" : function(data, type, row) {
				var action = "<div class='text-center'>";
				if ($("#sessionUserRole").val() =='general_manager' || $("#sessionUserRole").val() =='admin' || $("#sessionUserId").val() == row.creatorId) {
					action = action + "<a href='javascript:deleteProject(" + row.projectId + ")' class='btn mini black'><i class='icon-trash'></i> 删除 </a>";
				}
				action = action + "&nbsp;<a href='projectsManagement/projects/viewProject.do?projectId=" + row.projectId + "' class='btn mini blue'><i class='icon-eye-open'></i> 详情</a>&nbsp;" + "<a href='projectsManagement/projects/Requirement/crList.do?projectId=" + row.projectId + "' class='btn mini green'><i class='icon-list'></i> CRs </a></div>";
				return action;
			}
		} ]
	});
});

function deleteProject(id) {
	$("#deleteConfirm").modal();
	$("#deleteProject").unbind('click').bind('click',function() {
		$.post("projectsManagement/projects/deleteProject.do", {
			projectId : id
		}, function(data) {
			if (data == 1) {
				dataTable.fnDraw();
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
