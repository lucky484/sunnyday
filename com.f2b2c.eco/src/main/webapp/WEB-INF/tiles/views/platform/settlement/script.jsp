<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/farm/admin/settlement/page" var="settlementListUrl"/>
<spring:url value="/farm/order/detail" var="orderDetailUrl"></spring:url>
<script>
	//获取平台分润的列表，根据不同的角色加载
	function getSettlementList(){
		var timeStart = $('#timeStart').val();
		var timeEnd = $('#timeEnd').val();
		$("#settlementList").dataTable({
	    	"dom":"<'m-r m-t-lg pull-right'f>t<'row col-sm-12'<'col-sm-4'l>r<'col-sm-4'i><'pull-right'p>>",
		    "autoWidth": false,
		    "searching" : false,
			"stateSave" : true,
			"ordering" : false,
			"bSort":false,
			"serverSide" : true,
			"bDestroy" : true,
			"pagingType" : "full_numbers",
	    	"ajax" : {
				"url" : "${settlementListUrl}?now="+new Date().getTime(),
				"type" : "get",
				"dataType":'json',
				"dataSrc":"content",
				"data" :{"timeStart":timeStart,"timeEnd":timeEnd},
			},
   	     "columns": [
						{"data" : "id"},
						{"data" : "order.orderId"},
						{"data" : "createdtime"},
						{"data" : "orderAmount"},
						{"data" : "roleName"},
						{"data" : "profitPercent"},
						{"data" : "commissionAmount"}
   	             ],
            "columnDefs" : [
						{
							targets : [0],
							"visible": false
						},
						{
							targets : [1],
							"render" : function(data, type,full, meta) {
								if(null == full.order){
									return '';
								}else{
									if(null == full.order.id){
										return full.order.orderId;  
									}else{
										return "<a href='${orderDetailUrl}?orderId="+full.order.id+"'>"+full.order.orderId+"</a>";  
									}
									                      
								}
							}
						},
						{
							targets : [2],
							"render" : function(data, type,full, meta) {
								if(null == full.createdtime){
									return '';
								}else{
									return new Date(parseInt(full.createdtime)).Format("yyyy-MM-dd hh:mm:ss");
								}
							}
						},
						{
							targets : [3],
							"render" : function(data, type,full, meta) {
								if(null == full.orderAmount){
									return '';
								}else{
									return (full.orderAmount / 100).toFixed(2);
								}
							}
						},
						{
							targets : [5],
							"render" : function(data, type,full, meta) {
								if(null == full.profitPercent){
									return '';
								}else{
									return full.profitPercent +"%";
								}
							}
						},
						{
							targets : [6],
							"render" : function(data, type,full, meta) {
								if(null == full.commissionAmount){
									return '';
								}else{
									return (full.commissionAmount / 100).toFixed(2);
								}
							}
						}
					]
	    	});
		}
	
	//查询table的方法
	function search(){
		var timeStart = $("#timeStart").val();
		var timeEnd = $("#timeEnd").val();
		if(timeEnd!=null && timeEnd!=''){
			if(timeStart==null || timeStart == ''){
				swal({
			        title: "请输入开始日期",
			        type: "warning",
			        confirmButtonColor: "#DD6B55",
			        confirmButtonText: "确定",
			        closeOnConfirm: false
			    });
				return;
			}
		}
		getSettlementList();
		$('#settlementList').dataTable().fnDraw();
	}
	//清空table的方法
	function reset(){
		$("#timeStart").val("");
		$("#timeEnd").val("");
		getSettlementList();
		$('#settlementList').dataTable().fnDraw();
	}
	//jquery初始化的时候加载日期控件
	$(function(){
		$("#timeStart").datetimepicker({
		    format: "yyyy-mm-dd",
		    autoclose: true,
		    language:'cn',
	        todayHighlight: true,
		    minView: "month",
		    maxView: "decade",
		    todayBtn: true,
		    lang:"ch",
		    pickerPosition: "bottom-left"
		}).on("click",function(ev){
		    $("#timeStart").datetimepicker("setEndDate", $("#timeEnd").val());
		});
		$("#timeEnd").datetimepicker({
		    format: "yyyy-mm-dd",
		    autoclose: true,
		    language:'cn',
	        todayHighlight: true,
		    minView: "month",
		    maxView: "decade",
		    todayBtn: true,
		    lang:"ch",
		    pickerPosition: "bottom-left"
		}).on("click", function (ev) {
			$(".search").each(function(){
	    		$(this).removeClass("search-current");
		    });
		    $("#timeEnd").datetimepicker("setStartDate", $("#timeStart").val());
		});
		getSettlementList();
	});
</script>