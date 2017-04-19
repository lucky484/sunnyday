<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<spring:url value="/farm/role/page" var="roleListUrl"/>
<script>
	
	function getRoleList(){
		$("#roleList").dataTable({
	    	"dom":"<'m-r m-t-lg pull-right'f>t<'row col-sm-12'<'col-sm-4'l>r<'col-sm-4'i><'pull-right'p>>",
		    "autoWidth": false,
		    "searching" : false,
			"stateSave" : true,
			"ordering" : false,
			"bSort":false,
			"serverSide" : true,
			"pagingType" : "full_numbers",
			"bDestroy" : true,
	    	"ajax" : {
				"url" : "${roleListUrl}?now="+new Date().getTime(),
				"type" : "get",
				"dataType":'json',
				"dataSrc":"content",
				"data" :{},
			},
   	     "columns": [
						{"data" : "id" },
						{"data" : "roleName"},
						{"data" : "createdTime"},
						{"data" : ""}
   	             ],
            "columnDefs" : [
						{
							targets : [0],
							"visible": false
						},
						{
							targets : [2],
							"render" : function(data, type,full, meta) {
								if(null == full.createdTime){
									return '';
								}else{
									return new Date(parseInt(full.createdTime)).Format("yyyy-MM-dd hh:mm:ss");
								}
							}
						},
						{
							targets : [3],
							"render" : function(data, type,full, meta) {
								return '——';
							}
						}
					]
	    	});
		}
	
	//jquery初始化的时候加载商品列表
	$(function(){
		getRoleList();
	});
	
</script>