var dataTable;
$(function() {
	dataTable = $('#projectsTable').dataTable(
			{
				"autoWidth" : true,
				"ordering" : true,
				"processing" : true,
				"serverSide" : true,
				"language": {
				       url: basePath + 'plugins/datatables/lang/chinese.json'
			     },
			    "searching":false,
				"bLengthChange" : false,
				"bPaginate" : false,
				"pageLength" : 20,
				"order": [[ 0, "desc" ]],
				"pagingType" : "full_numbers",
				"ajax" : {
					"url" : "projectsManagement/projects/Requirement/getAllCR.do",
					"dataSrc" : "data",
					"type" : "post",
					"data" : function(d) {
						d.projectId = $("#projectId").val();
						d.sch = d.search.value;
						if (d.order[0].column == 0) {
							d.column = "create_time";
						}
						if (d.order[0].column == 1) {
							d.column = "cr_describe";
						}
						if (d.order[0].column == 2) {
							d.column = "estimated_workload";
						}
						if (d.order[0].column == 3) {
							d.column = "presenter";
						}
						if (d.order[0].column == 4) {
							d.column = "confirmor";
						}
						if (d.order[0].column == 5) {
							d.column = "confirm_quotation";
						}
						d.dir = d.order[0].dir;
					}
				},
				columns : [ {
					data : "createTime"
				}, {
					data : "crDescribe"
				}, {
					data : "estimatedWorkload"
				}, {
					data : "presenter"
				}, {
					data : "confirmor"
				}, {
					data : "confirmQuotation"
				}, {
					data : "crId"
				} ],
				"columnDefs" : [
						{
							"targets" : 0,
							"bSortable" : false,
							"bVisible" : false
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
							"targets" : 5,
							"bSortable" : true,
							"render" : function(data, type, row) {
								return "<span style='float:right;'>￥" + (row.confirmQuotation || 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,') + "</span>";
							}
						},
						{
							"targets" : 6,
							"bSortable" : false,
							"render" : function(data, type, row) {
								var action = "<div class='text-c'>";
								if ($("#sessionUserRole",window.parent.document).val() =='general_manager' || $("#sessionUserRole",window.parent.document).val() == 'admin' || $("#sessionUserId",window.parent.document).val() == row.creatorId) {
									action = action + "<a href='projectsManagement/projects/Requirement/editCRList.do?crId=" + row.crId
											+ "'class='btn-primary' style='padding:4px 4px;text-decoration:none'><i class='Hui-iconfont' style='font-size:15px;'>&#xe60c;</i> 编辑 </a>&nbsp;<a href='javascript:deleteCR(" + row.crId
											+ ")' class='btn-danger' style='padding:4px 4px;text-decoration:none'><i class='Hui-iconfont' style='font-size:15px;'></i> 删除 </a>"
								}
								action = action + "&nbsp;<a href='projectsManagement/projects/Requirement/viewCRList.do?crId=" + row.crId
										+ "' class='btn-secondary' style='padding:4px 4px;text-decoration:none'><i class='Hui-iconfont' style='font-size:15px;'>&#xe725;</i> 详情 </a></div>"
								return action;
							}
						} ],
				"footerCallback" : function(row, data, start, end, display) {
					var api = this.api(), data;
					timeTotal = api.column(2, {
						page : 'current'
					}).data().reduce(function(a, b) {
						return parseInt(a) + parseInt(b);
					}, 0);
	/*				estimatequotationTotal = api.column(4, {
						page : 'current'
					}).data().reduce(function(a, b) {
						return parseInt(a) + parseInt(b);
					}, 0);*/
					confirmquotationTotal = api.column(5, {
						page : 'current'
					}).data().reduce(function(a, b) {
						return (parseInt(a.toString().replace(/\,/g,"","")) + parseInt(b.toString().replace(/\,/g,"",""))).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,');
					}, 0);
					$(api.column(2).footer()).html("<div class='text-c'>" + timeTotal + "</div>");
				//	$(api.column(5).footer()).html("<span style='float:right;'>￥" + estimatequotationTotal + "</span>");
					$(api.column(5).footer()).html("<span style='float:right;'>￥" + confirmquotationTotal + "</span>");
				}
			});
			
});

function deleteCR(id) {
	layer.confirm('确认要删除吗？',function(index){
		$.post("projectsManagement/projects/Requirement/deleteCR.do", {
			crId : id
		}, function(data) {
			if (data == 1) {
				layer.msg('删除成功!',{icon:1,time:1000});
				dataTable.fnDraw();
			}
		}, 'json');
	});
	/*$("#deleteConfirm").modal();
	$("#deleteCR").unbind('click').bind('click',function() {
		$.post("projectsManagement/projects/Requirement/deleteCR.do", {
			crId : id
		}, function(data) {
			if (data == 1) {
				dataTable.fnDraw();
			}
		}, 'json');
	});*/
}
$(function(){
	$("#projectQuotation").html("￥" + (projectQuotation != 0 ? projectQuotation.toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,') : 0));
});
function show_table(obj){
	if($(obj).hasClass("collapse")){
		$(obj).removeClass("collapse");
		$(obj).addClass("expand");
		$(".portlet-body").hide();
	}else{
		$(obj).removeClass("expand");
		$(obj).addClass("collapse");
		$(".portlet-body").show();
	}
}