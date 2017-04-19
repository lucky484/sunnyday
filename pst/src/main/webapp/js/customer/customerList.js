var dataTable;
$(function() {
	dataTable = $('#projectsTable').dataTable(
			{
				"autoWidth" : true,
				"ordering" : true,
				"language": {
			       url: basePath + 'plugins/datatables/lang/chinese.json'
			    },
				"processing" : false,
				"serverSide" : true,
				"bSort" : true,
				"bLengthChange" : false,
				"bPaginate" : true,
				"pageLength" : 20,
				"order" : [ [ 5, "desc" ] ],
				"pagingType" : "full_numbers",
				"searching" : true,
				"ajax" : {
					"url" : "getCustomer.do",
					"dataSrc" : "data",
					"type" : "post",
					"data" : function(d) {
						d.sch = d.search.value;
						if (d.order[0].column == 0) {
							d.column = "company_name";
						}
						if (d.order[0].column == 1) {
							d.column = "customer_name";
						}
						if (d.order[0].column == 2) {
							d.column = "customer_name_short";
						}
						if (d.order[0].column == 3) {
							d.column = "customer_title";
						}
						if (d.order[0].column == 4) {
							d.column = "customer_phone";
						}
						if (d.order[0].column == 5) {
							d.column = "add_time";
						}
						d.dir = d.order[0].dir;
					}
				},
				columns : [ {
					data : "companyName"
				},{
					data : "customerName"
				}, {
					data : "customerNameShort"
				}, {
					data : "customerTitle"
				}, {
					data : "customerPhone"
				}, {
					data : "addTime"
				}, {
					data : "action"
				} ],
				"columnDefs" : [
						{
							"targets" : 1,
							"bSortable" : true,
							"render" : function(data, type, row) {
								return "<div class='text-c'>" + data + "</div>";
							}
						},
						{
							"targets" : 2,
							"bSortable" : true,
							"render" : function(data, type, row) {
								return "<div class='text-c'>" + data + "</div>";
							}
						},
						{
							"targets" : 3,
							"bSortable" : true,
							"render" : function(data, type, row) {
								return "<div class='text-c'>" + data + "</div>";
							}
						},
						{
							"targets" : 4,
							"bSortable" : true,
							"render" : function(data, type, row) {
								return "<div class='text-c'>" + data + "</div>";
							}
						},
						{
							"targets" : 6,
							"bSortable" : false,
							"render" : function(data, type, row) {
								var editBtn =   '<a title="编辑" href="editCustomer.do?customerId=' + row.customerId + '" class="btn-primary" style="padding:4px 4px;text-decoration: none;"><i class="Hui-iconfont" style="font-size:15px;">&#xe60c;</i> 编辑</a>&nbsp;&nbsp;';
								var deleteBtn = '<a title="删除" href="javascript:deleteCustomer(' + row.customerId + ')" class="btn-danger" style="padding:4px 4px;text-decoration:none"><i class="Hui-iconfont" style="font-size:15px;"></i> 删除</a>&nbsp;&nbsp;';
								var detailBtn = '<a title="详情" href="viewCustomer.do?customerId=' + row.customerId + '" class="btn-secondary" style="padding:4px 4px;text-decoration:none"><i class="Hui-iconfont" style="font-size:15px;">&#xe725;</i> 详情</a>';
								var action="<div class='text-c'>";
								if ($("#sessionUserRole",window.parent.document).val() == 'general_manager') {
									action += editBtn;
									action += deleteBtn;
								} else if ($("#sessionUserRole",window.parent.document).val() == 'admin') {
									action += editBtn;
								}
								action += detailBtn;
								action += "</div>";
								return action;
							}
						}, {
							"targets" : 5,
							"bSortable" : true,
							"render" : function(data, type, row) {
								return "<div class='text-c'>" + new Date(data).Format("yyyy-MM-dd hh:mm:ss") + "</div>";
							}
						}, ]
			});
});

function deleteCustomer(id) {
	$("#deleteConfirm").modal();
	$("#deleteCustomer").unbind('click').bind('click',function() {
		$.post("projectsManagement/customers/deleteCustomer.do", {
			customerId : id
		}, function(data) {
			if (data == 1) {
				dataTable.fnDraw();
			}
		}, 'json');
	});
}
