<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/farm/home/querylogisticsmodel" var="querylogisticsmodelUrl" />
<spring:url value="/farm/home/update-logistics" var="updateUrl" />
<spring:url value="/farm/home/add-logistics" var="addUrl" />
<spring:url value="/farm/home/del" var="delUrl" />
<script>

	$(function() {
		$("#logistics").dataTable({
			"dom":"<'m-r m-t-lg pull-right'f>t<'row col-sm-12'<'col-sm-4'l>r<'col-sm-4'i><'pull-right'p>>",
			"autoWidth": false,
			"searching" : false,
			"stateSave" : true,
			"ordering" : false,
			"bSort":false,
			"pageLength" : 10,
			"pagingType" : "full_numbers",
			"lengthChange": true,
			"serverSide" : true,
			"bDestroy" : true,
			"ajax" : {
				"url" : "${querylogisticsmodelUrl}",
				"type" : "get",
				"dataType" :"json",
				"dataSrc":"content",
				"data" :{"name":name},
			}, 
			"columns" : [ 
			/* {
				"data" : "id"
			}, */ 
			{
				"data" : "name"
			}, {
				"data" : ""
			}],
			"columnDefs" : [ 
			{
			"targets" : 1,
			"bSortable" : false,
			"render" : function(data, type, full, row) {
				return '<a href="${updateUrl}?id='+ full.id +'" class="btn mini light-blue">修改 </a>'
						+'<a href="javascript:void(0)" onclick="del('+full.id+')" class="btn mini light-blue">删除 </a>';
				}
			} ]
		});
	});
	
	//添加物流
	function add(){
		window.location.href = "${addUrl}";
	}
	
	/* 删除功能*/
	function del(id) {
// 		$("#deleteConfirm").modal();
// 		$("#deleteApplyBtn").unbind("click").bind("click", function() {
/* 			$.post('del.do', { */
// 				id : id
// 			}, function(data) {
// 				alert("删除成功")
// 				document.location.reload()// 当前页面  
// 			});
// 		});
		 $.ajax({
			"dataType" : 'json',
			"type" : 'post',
			"data" : {
				"id" : id
			},
			"url" : '${delUrl}',
			"success" : function(data) {
				debugger
				if (data) {
					swal({
						title: "成功!",   
						text: "删除成功",   
						type: "info", 
					} , function(){window.location.reload()});
				}else{
					swal({
						title: "失败!",   
						text: "删除失败",   
						type: "info", 
					} , function(){window.location.reload()});
				}
			},
			"error" : function(data) {
				debugger
				alert("修改成功");
			}
		});
	}
</script>