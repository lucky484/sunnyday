<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/farm/terminal/goods/page" var="goodsListUrl"/>
<spring:url value="/farm/terminal/goods/index" var="goodsIndex" />
    <spring:url value="/market/bgoods/bgoodsPage" var="bgoodsPageUrl"/>
    <spring:url value="/market/bgoods/bgoodsdetail" var="bgoodsDetailUrl"/>
      <spring:url value="/market/bgoods/modify-remain" var="modifyRemainUrl"/>
      <spring:url value="/market/bgoods/modifybgoodsurl" var="modifyBgoodsUrl"/>
      <spring:url value="/market/bgoods/next-shelf-bgoods" var="nextshelfbgoodsUrl" />
     <spring:url value="/farm/goods/down-or-release" var="downOrReleaseFgoodsUrl" />
<spring:url value="/api/fkind/get-kinds-for-jstree"
	var="getKindsUrl" />
    <spring:url
	value="/resources/platform/js/plugins/easyUI/jquery.easyui.min.js"
	var="easyUIUrl" />
<script type="text/javascript" src="${easyUIUrl}"></script>
 <script type="text/javascript">
 var params = {};
	var dataTable;
	
	$("#kindTree").combotree({
		url : '${getKindsUrl}',
		height : 35,
		width : '95%',
		prompt : '请选择分类',
	});
	
	function search() {
		params.name = $("#name").val();
		params.location = $("#location").val();
		params.kind = $("#kindTree").combotree('getValue');
		params.goodsStatus = $("#goodsStatus").val();
		dataTable.fnDraw();
	}
	
	function reset() {
		$("#name").val("");
		$("#location").val("");
		$("#kindTree").combotree('setValue','');
		$("#goodsStatus").val("");
		search();
	}
	
 $(document).ready(function(){
	 bgoodslistUrl();
	});
 
 function nextshelfbgoods(id){
 	$.ajax({
			"dataType" : 'json',
			"type" : "POST",
			"async" : false,
			"url" : "${nextshelfbgoodsUrl}",
			"data" : {
				"id" : id
			},
			"success" : function(data) {
				bgoodslistUrl();
			}
			}); 
	}   

 function bgoodslistUrl(){
	 dataTable = $(".dataTables-userlist").dataTable({
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
				"url" : "${bgoodsPageUrl}",
				"type" : "POST",
				"dataSrc" : "content",
				"dataType":'json',
				"data" : function (d) {
					d.name = params.name;
					d.kind = params.kind;
					d.productPlace = params.location;
					d.status = params.goodsStatus;
					return d;
				}
			},
			"columns" : [{
				data : "name",
				data : "logoUrl",
				data : "type",
				data : "kind.id",
				data : "id",
				data : "price",
				data : "remain",
				data : "producePlace",
				data : "sales",
				data : "updatedTime",
				data : "updatedTime",
				data : "id",
				data : "id"
			}  ],
			"columnDefs" : [
					{
						targets : [0],
							"render" : function(data, type,full, meta) {
								return 	full.name;
							}
					},
					{
						targets : [1],
							"render" : function(data, type,full, meta) {
								if(full.logoUrl != null && full.logoUrl != ""){
									
									return 	"<img src="+full.logoUrl+" style='height: 50px; weight: 50px; width: 70px;'>";  
								}else{
									return 	"无图片";
								}
							}
					},
					{
						targets : [2],
							"render" : function(data, type,full, meta) {
								if (full.type == 1) {
									return "国产商品";
								}
								if (full.type == 2) {
									return "进口商品";
								}
								if (full.type == 3) {
									return "活动商品";
								}
								if (full.type == 4) {
									return "其他商品";
								}
							}
					},
					{
						targets : [3],
							"render" : function(data, type,full, meta) {
								if(full.kind != null){
									return 	full.kind.kindName;
								}else{
									return "";
								}
								
							}
					},{
						targets : [4],
						"render" : function(data, type,full, meta) {
							return 	full.price/100;
						}
					},
					{
						targets : [5],
						"render" : function(data, type,full, meta) {
							var str = '<a style="text-decoration:none" class="ml-5" onclick="modifyRemain('+full.remain+','+full.id+');" href="javascript:void(0);">'+full.remain+'</a>';
							if(full.unit == "0"){
								str += "(箱)"
							}
							if(full.unit == "1"){
								str += "(斤)"
							}
							if(full.unit == "2"){
								str += "(公斤)"
							}
							return 	str;
						}
					},
					{
						targets : [6],
						"render" : function(data, type,full, meta) {
							return 	full.producePlace;
						}
					},
					{
						targets : [7],
						"render" : function(data, type,full, meta) {
							return 	full.sales;
						}
					},
					{
						targets : [8],
						"render" : function(data, type,full, meta) {
							if(full.updatedTime != null){
								return 	formatCurrentTimeMillis(full.updatedTime);
							}else{
								return "";
							}
						}
					},
					{
						targets : [9],
							"render" : function(data, type,full, meta) {
								if(full.status != null){
									/* 0-待审核，1-上架，2-下架，3-审核不通过，4-草稿', */
									if(full.status == 0){
										return "待审核";
									}
									if(full.status == 1){
										return "上架";
									}
									if(full.status == 2){
										return "已下架";
									}
									if(full.status == 3){
										return "审核不通过"+'<a style="text-decoration:none" class="ml-5" onclick="no_pass(\''+full.reason+'\');" href="javascript:void(0);" title="查看原因">查看原因</a>';
									}
									if(full.status == 4){
										return "草稿";
									}
								}else{
									return "状态异常";
								}
							}
					},
				{
					targets : [10],
						"render" : function(data, type,full, meta) {
							if(full.status == 0){
								return '<a href="${bgoodsDetailUrl}?id='+full.id +'"></i>&nbsp;查看</a><a href="${modifyUserUrl}?id='+full.id +'"></i></a>';
							}
							if(full.status == 1){
								return '<a href="${bgoodsDetailUrl}?id='+full.id +'"></i>&nbsp;查看</a><a href="${modifyUserUrl}?id='+full.id +'"></i></a><a href="${modifyBgoodsUrl}?id='+full.id +'"></i>&nbsp;修改</a><a onclick=nextshelfbgoods('+full.id +')></i>&nbsp;下架</a>';
							}
							if(full.status == 2){
								return '<a href="${bgoodsDetailUrl}?id='+full.id +'"></i>&nbsp;查看</a> <a href="${modifyBgoodsUrl}?id='+full.id +'"></i>&nbsp;修改</a>';
							}
							if(full.status == 3){
								return '<a href="${bgoodsDetailUrl}?id='+full.id +'"></i>&nbsp;查看</a><a href="${modifyBgoodsUrl}?id='+full.id +'"></i>&nbsp;修改</a>';
							}
								return '<a href="${bgoodsDetailUrl}?id='+full.id +'"></i>&nbsp;查看</a><a href="${modifyBgoodsUrl}?id='+full.id +'"></i>&nbsp;修改</a>';
						}
				}
			]
	});
	}    
 function formatCurrentTimeMillis (dateTime) {
		return  new Date(dateTime).Format("yyyy-MM-dd hh:mm:ss");
	}

 
 function no_pass(data){
	 	$("#reasonStr").html("");
	 	$("#reasonStr").html(data);
		$("#reasonModal").modal({backdrop: 'static', keyboard: false});
	}
 
 
 function modifyRemain(remain,id){
	 	$("#modifyRemainBtn").attr("disabled",false);
	 	$("#modifyRemain").val("");
	 	$("#modifyRemainId").val("");
	 	$("#modifyRemain").val(remain);
	 	$("#modifyRemainId").val(id);
		$("#modifyRemainModal").modal({backdrop: 'static', keyboard: false});
	}

 
 //修改商品
 $("#modifyRemainBtn").on("click",function() {
 	$.pnotify_remove_all();
 	 $("#modifyRemainBtn").attr("disabled",true);
 	//商品名称
 	var id= $("#modifyRemainId").val(); 
 	//商品类型
 	var remain= $("#modifyRemain").val(); 
 	if(remain == undefined || remain == null || remain == ""){
		$.notify("error", "请输入库存");
		$("#modifyRemainBtn").attr("disabled",false)
		return;
	}else if(remain <= 0){
		$.notify("error", "库存不是一个有效值");
		$("#modifyRemainBtn").attr("disabled",false)
		return;
	}
 	if(id == undefined || id == null || id == ""){
		$.notify("error", "商品异常,不能修改库存");
		$("#modifyRemainBtn").attr("disabled",false)
		return;
	}
 	 $.ajax({
			"dataType" : 'json',
			"type" : "POST",
			"async" : false,
			"url" : "${modifyRemainUrl}",
			"data" : {
				"id" : id,
				"remain" : remain
			},
			"success" : function(data) {
				$.notify("info", "修改库存成功");
				bgoodslistUrl();
				$("#modifyRemainModal").modal('hide');
				$("#modifyRemainBtn").attr("disabled",false);
			},
			error:function(data){
				$("#modifyRemainBtn").attr("disabled",false);
				$.notify("error", "修改库存失败");
			}
		}); 
 	 }
 );

 
    </script>