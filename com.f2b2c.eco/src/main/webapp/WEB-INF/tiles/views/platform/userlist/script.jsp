<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/resources/platform/js/content.min.js" var="contentJsUrl"/>
<spring:url value="/farm/user/querybypages" var="userPagesUrl"></spring:url>
<spring:url value="/farm/user/modifyuser" var="modifyUserUrl" />
<spring:url value="/farm/user/viewuser" var="viewUserUrl" />
<spring:url value="/farm/user/deluser" var="delUserUrl" />
<script>

function dataTables(){
	$(".dataTables-userlist").dataTable({
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
			"url" : "${userPagesUrl}",
			"type" : "GET",
			"dataSrc" : "content",
			"dataType":'json',
			"data" :{}
		},
		"columns" : [ 
		{
			data: "accountName"	
		}, {
			data : "realName"
		},{
			data : "fRoleModel.roleName"
		}, {
			data : "phone"
		}, {
			data : "address"
		}, {
			data : "isActive"
		}, {
			data : "createdTime"
		}, {
			data : "id"
		}  ],
		"columnDefs" : [
			{
				targets : [5],
					"render" : function(data, type,full, meta) {
						if (full.sex == undefined || full.sex == 0)
						{
							return "激活";
						}
						else
						{
							return "锁定";
						}
					}
			},
			{
				targets : [6],
					"render" : function(data, type,full, meta) {
						return formatCurrentTimeMillis(full.createdTime);
					}
			},
			{
				targets : [7],
					"render" : function(data, type,full, meta) {
						if(full.accountName == 'admin'){
							return   '<a href="${viewUserUrl}?id='+full.id +'"></i>&nbsp;查看</a>'
									 +'<a href="${modifyUserUrl}?id='+full.id +'"></i>&nbsp;修改</a>';
						}else{
							return   '<a href="${viewUserUrl}?id='+full.id +'"></i>&nbsp;查看</a>'
									 +'<a href="${modifyUserUrl}?id='+full.id +'"></i>&nbsp;修改</a>'
									 +'<a href="javascript:;" onclick="delUser('+full.id +')"></i>&nbsp;删除</a>';
						}
					}
			}
		]
	});
}
$(document).ready(function(){
	dataTables();
});

function delUser(id){
	 swal({
		    title: "删除提示",
         type: "info", 
         showCancelButton: true,
         closeOnConfirm: false,
	        confirmButtonText: "确认删除",
	        cancelButtonText: "取消"
	    },function(){
	    	$.ajax({
	    		"dataType" : 'json',
	    		"type" : "get",
	    		"async" : false,
	    		"url" : '${delUserUrl}',
	    		"data" : {
	    			"userId" : id
	    		},
	    		"success" : function(data) {
	    			if (data)
	    			{
	    				swal({
	    					title: "成功!",   
	    					text: "删除成功",   
	    					type: "info", 
	    				} , function(){
	    						dataTables();
	    					});
	    			}
	    			else
	    			{
	    				swal({
	    					title: "失败!",   
	    					text: "删除失败",   
	    					type: "info", 
	    				} , function(){
	    					dataTables();
	    				});
	    			}
	    		},
	    		"error": function(data){
	    			alert("修改成功");
	    		}
	    	});
	    }
	);
}

function formatCurrentTimeMillis (dateTime) {
	return  new Date(dateTime).Format("yyyy-MM-dd hh:mm:ss");
	
}
</script>