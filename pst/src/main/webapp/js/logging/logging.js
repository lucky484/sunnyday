var dataTable;
$(function() {
	dataTable = $('#projectsTable').dataTable({
		"autoWidth" : true,
		"ordering" : true,
		"processing" : true,
		"serverSide" : true,
		"bSort" : true,
		"bLengthChange" : false,
		"bPaginate" : true,
		"pageLength" : 20,
		"order" : [ [ 2, "desc" ] ],
		"pagingType" : "full_numbers",
		"searching" : false,
		"ajax" : {
			"url" : "logsManagement/logging/getLoggings.do",
			"dataSrc" : "data",
			"type" : "post",
			"data" : function(d) {
				if (d.order[0].column == 0) {
					d.column = "operator";
				}
				if (d.order[0].column == 1) {
					d.column = "event_name";
				}
				if (d.order[0].column == 2) {
					d.column = "create_time";
				}
				if (d.order[0].column == 3) {
					d.column = "action";
				}
				if (d.order[0].column == 4) {
					d.column = "event_type";
				}
				d.dir = d.order[0].dir;
			}
		},
		columns : [ {
			data : "operator"
		}, {
			data : "eventName"
		}, {
			data : "createTime"
		}, {
			data : "action"
		}, ],
		"columnDefs" : [ {
			"targets" : 2,
			"bSortable" : true,
			"render" : function(data, type, row) {
				return "<div class='text-center'>" + new Date(data).Format("yyyy-MM-dd hh:mm:ss") + "</div>";
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
				return "<div class='text-center'>" + data + "</div>";
			}
		}, ]
	});
});
