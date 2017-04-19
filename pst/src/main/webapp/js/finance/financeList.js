var dataTable;
$(function() {
	dataTable = $('#projectFinance').dataTable({
		"autoWidth" : true,
		"ordering" : true,
		"language": {
	        url: basePath + 'plugins/datatables/lang/chinese.json'
	     },
		"processing" : false,
		"serverSide" : true,
		"bLengthChange" : false,
		"bPaginate" : true,
		"pageLength" : 20,
		"pagingType" : "full_numbers",
		"order": [[ 0, "desc" ]],
		"searching" : true,
		"ajax" : {
			"url" : "getFinances.do",
			"dataSrc" : "data",
			"type" : "post",
			"data" : function(d) {
				d.sch = d.search.value;
				if (d.order[0].column == 0) {
					d.column = "update_time";
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
					d.column = "lead_time";
				}
				if (d.order[0].column == 7) {
					d.column = "update_time";
				}
				if (d.order[0].column == 8) {
					d.column = "update_time";
				}
				if (d.order[0].column == 9) {
					d.column = "update_time";
				}
				if (d.order[0].column == 10) {
					d.column = "outsourcing_quotation";
				}
				if (d.order[0].column == 11) {
					d.column = "update_time";
				}
				if (d.order[0].column == 12) {
					d.column = "update_time";
				}
				d.dir = d.order[0].dir;
			}
		},
		columns : [ {
			data : "updateTime"
		}, {
			data : "projectCode"
		}, {
			data : "projectName"
		}, {
			data : "projectManager"
		}, {
			data : "implementManager"
		}, {
			data : "startTime"
		}, {
			data : "leadTime"
		},{
			data : "projectQuotationTotal"
		},{
			data : "receive"
		},{
			data : "unreceive"
		},{
			data : "outsourcingQuotation"
		},{
			data : "payment"
		},{
			data : "unpayment"
		},{
			data : "action"
		} ],
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
			"targets" : 3,
			"bSortable" : true,
			"render" : function(data, type, row) {
				return "<div class='text-center'>" + data + "</div>";
			}
		},{
			"targets" : 4,
			"bSortable" : true,
			"render" : function(data, type, row) {
				return "<div class='text-center'>" + data + "</div>";
			}
		},{
			"targets" : 5,
			"bSortable" : true,
			"render" : function(data, type, row) {
				return "<div class='text-center'>" + new Date(data).Format("yyyy-MM-dd") + "</div>";
			}
		},{
			"targets" : 6,
			"bSortable" : true,
			"render" : function(data, type, row) {
				return "<div class='text-center'>" + new Date(data).Format("yyyy-MM-dd") + "</div>";
			}
		}, {
			"targets" : 7,
			"bSortable" : true,
			"render" : function(data, type, row) {
				return "<div class='text-right'>￥"  + (row.projectQuotationTotal || 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,') + "</div>";
			}
		}, {
			"targets" : 8,
			"bSortable" : true,
			"render" : function(data, type, row) {
				return "<div class='text-right'><a href='projectsManagement/finance/viewFinanceReceive.do?projectId=" + row.projectId + "'>￥" + (row.receive || 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,') + "</a></div>";
			}
		},  {
			"targets" : 9,
			"bSortable" : true,
			"render" : function(data, type, row) {
				return "<div class='text-right'>￥" + (row.unreceive || 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,') + "</div>";
			}
		}, {
			"targets" : 10,
			"bSortable" : true,
			"render" : function(data, type, row) {
				return "<div class='text-right'>￥" + (row.outsourcingQuotation || 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,') + "</div>";
			}
		}, {
			"targets" : 11,
			"bSortable" : true,
			"render" : function(data, type, row) {
				return "<div class='text-right'><a href='projectsManagement/finance/viewFinancePayment.do?projectId=" + row.projectId + "'>￥" + (row.payment || 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,') + "</a></div>";
			}
		},{
			"targets" : 12,
			"bSortable" : true,
			"render" : function(data, type, row) {
				return "<div class='text-right'>￥" + (row.unpayment || 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,') + "</div>";
			}
		},{
			"targets" : 13,
			"bSortable" : false,
			"render" : function(data, type, row) {
				var action = "<div class='text-center'><a href='projectsManagement/finance/addFinanceReceive.do?projectId=" + row.projectId + "' class='btn mini purple'><i class='icon-edit'></i>添加收款 </a>" +
						"<a href='projectsManagement/finance/addFinancePayment.do?projectId=" + row.projectId + "' class='btn mini green'><i class='icon-edit'></i> 添加付款 </a></div>";
				return action;
			}
		} ]
	});
});

$('#projectTypeNumber').change(function() {
	dataTable.DataTable().column(10).search(
			$(this).find("option:selected").val()
	    ).draw();
});

$('#projectStatusNumber').change(function() {
	dataTable.DataTable().column(9).search(
	        $(this).find("option:selected").val()
	    ).draw();
});