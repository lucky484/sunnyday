<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/farm/admin/auth/datatable/json" var="datatableUrl"/>
<script>
		// jquary dataTables
		$(function() {
			$("#editable").dataTable({
				"dom":"<'m-r m-t-lg pull-right'f>t<'row col-sm-12'<'col-sm-4'l>r<'col-sm-4'i><'pull-right'p>>",
			    "autoWidth": false,
			    "searching" : false,
				"stateSave" : true,
				"ordering" : false,
				"bSort":false,
				"pageLength" : 10,
				"pagingType" : "full_numbers",
				"serverSide" : true,
				"bDestroy" : true,
				"ajax" : {
					"url" : "${datatableUrl}?time="+new Date().getTime(),
					"type" : "get",
					"dataSrc" : "content",
					"dataType":'json',
				},
				"columns" : [ {
					data : "id"
				}, {
					data : "code"
				}, {
					data : "isUsed"
				}, {
					data : "createdUser"
				}, {
					data : "createdTime"
				}],
				"columnDefs" : [
					{
						targets : [0],
						"visible": false
					},
					{
						targets : [2],
							"render" : function(data, type,full, meta) {
								if (data == 0)
								{
									return "未被使用";
								}
								else if(data == 1)
								{
									return "已使用";
								}else{
									return "-";
								}
							}
					},
					{
						targets : [3],
							"render" : function(data, type,full, meta) {
								if(!data.realName){
									return '-'
								}else{
									return data.realName;
								}
							}
					},
					{
						targets : [4],
							"render" : function(data, type,full, meta) {
								return new Date(parseInt(full.createdTime)).Format("yyyy-MM-dd hh:mm:ss");
							}
					},             
				]
		});	
	 });
	 
	 function generateKeys(){
		 var authNum = $("#auth-num").val();
		 var userId = $("#userId").val();
		 $.post('generate',{userId: userId,num : authNum}, function(data){
	 	 	 window.location.reload()// 当前页面  
		 })
	 }
	 
</script>