<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/farm/goods/fgoodsPage" var="fGoodsPageUrl" />
<spring:url value="/farm/goods/fgoodsdetail" var="fgoodsDetailUrl" />
<spring:url value="/farm/goods/modifyfgoodsurl" var="modifyfgoodsurl" />
<spring:url value="/farm/goods/down-or-release" var="downOrReleaseFgoodsUrl" />
<spring:url value="/farm/goods/update-fgoods-weight" var="modiftFgoodsWeightUrl" />
<spring:url value="/farm/admin/kind/get-kinds-for-jstree" var="getKindsUrl" />
<spring:url
	value="/resources/platform/js/plugins/easyUI/jquery.easyui.min.js"
	var="easyUIUrl" />
<script type="text/javascript" src="${easyUIUrl}"></script>
<script>
	var params = {};
	var dataTable;
	
	$("#kindTree").combotree({
		url : '${getKindsUrl}',
		height : 35,
		width : '95%',
		prompt : '请选择商品类型',
	});
	
	function getShopList(){
		//var name = $('#name').val();
		dataTable = $("#shop2list").dataTable({
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
				"url" : "${fGoodsPageUrl}?now="+new Date().getTime(),
				"type" : "post",
				"dataType":'json',
				"dataSrc" : "content",
				"data" : function (d) {
					d.name = params.name;
					d.kind = params.kind;
					d.productPlace = params.location;
					return d;
				}
			},
			"columns" : [{
				data : "goodsNo",
				data : "logoUrl",
				data : "type",
				data : "kind",
				data : "price",
				data : "unit",
				data : "remain",
				data : "producePlace",
				data : "cityId",
				data : "spec",
				data : "updatedTime",
				data : "status",
				data : ""
			}  ],
			"columnDefs" : [
							{
								targets : [0],
									"render" : function(data, type,full, meta) {
										return full.goodsNo;
									}
							},
							{
								targets : [1],
									"render" : function(data, type,full, meta) {
										return full.name;
									}
							},
							{
								targets : [2],
									"render" : function(data, type,full, meta) {
										if(full.logoUrl != null && full.logoUrl != ""){
											return  "<img src="+full.logoUrl+"  style='height: 50px; weight: 50px; width: 70px;'>";  
										}else{
											return 	"无图片";
										}
									}
							},
							{
								targets : [3],
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
										targets : [ 4 ],
										"render" : function(data, type, full,
												meta) {
											if(full.kind != null){
												if(full.kind.isEnable == 1){
													return full.kind.kindName;
												}else{
													return full.kind.kindName+"<div style='color: red;'>(分类已下架)</div>";	
												}
											}
											
										}
									},
									{
										targets : [ 5 ],
										"render" : function(data, type, full,
												meta) {
											return full.price / 100;
										}
									},
									{
										targets : [ 6 ],
										"render" : function(data, type, full,
												meta) {

											if (full.unit == "0") {
												return "箱";
											} else if (full.unit == "1") {
												return "斤";
											} else if (full.unit == "2") {
												return "公斤";
											} else {
												return "单位参数异常";
											}
										}
									},
									{
										targets : [ 7 ],
										"render" : function(data, type, full,
												meta) {
											return full.remain;
										}
									},
									{
										targets : [ 8 ],
										"render" : function(data, type, full,
												meta) {
											return full.producePlace;
										}
									},
									{
										targets : [ 9 ],
										"render" : function(data, type, full,
												meta) {
											return full.provinceName + "-"
													+ full.cityName;
										}
									},
									{
										targets : [ 10 ],
										"render" : function(data, type, full,
												meta) {
											if (full.updatedTime != null) {
												return formatCurrentTimeMillis(full.updatedTime);
											} else {
												return "";
											}

										}
									},
									{
										targets : [ 11 ],
										"render" : function(data, type, full,
												meta) {
											if (full.status == 1) {
												return "上架中";
											} else if (full.status == 2) {
												return "已下架";
											} else {
												return "状态异常";
											}

										}
									},
									{
										targets : [ 12 ],
										"render" : function(data, type, full,
												meta) {
											var str = '<a href="${fgoodsDetailUrl}?id='
												+ full.id
												+ '"></i>&nbsp;查看</a><a href="${modifyfgoodsurl}?id='
												+ full.id
												+ '"></i></a>';
											if(full.kind.isEnable == 1){
												if(full.status == 1){
													str +='<a href="${modifyfgoodsurl}?id='
														+ full.id
														+ '"></i>&nbsp;修改</a><a onclick=downOrReleaseFgoods('
														+ full.id
														+ ','
														+ full.status
														+ ')></i>&nbsp;下架</a>';
												}else if (full.status == 2) {
													str += '<a href="${modifyfgoodsurl}?id='
														+ full.id
														+ '"></i>&nbsp;修改</a><a onclick=downOrReleaseFgoods('
														+ full.id
														+ ','
														+ full.status
														+ ')></i>&nbsp;上架</a>';
													} 
											}
											str += '<a onclick=goods_Weight(' 
												+ full.id
												+ ','
												+ full.weight
												+ ',\''
												+ full.kind.kindName
												+ '\')></i>&nbsp;设置权重</a>';
										return str;
										}
									},

							]
						});
	}

	function downOrReleaseFgoods(id, status) {
		$.ajax({
			"dataType" : 'json',
			"type" : "POST",
			"async" : false,
			"url" : "${downOrReleaseFgoodsUrl}",
			"data" : {
				"id" : id,
				"status" : status
			},
			"success" : function(data) {
				getShopList();
			}
		});
	}
	//jquery初始化的时候加载商品列表
	$(function() {
		getShopList();
	});

	function formatCurrentTimeMillis(dateTime) {
		return new Date(dateTime).Format("yyyy-MM-dd hh:mm:ss");
	}

	function search() {
		params.name = $("#name").val();
		params.location = $("#location").val();
		params.kind = $("#kindTree").combotree('getValue');
		dataTable.fnDraw();
	}

	function goods_Weight(id,weight,kindName){
		$("#modifyWeight").attr("disabled",false);
		$("#goodsId_modal").val(id);
		$("#kindName_modal").val(kindName);
		if(weight != null){
			$("#weight_modal").val(weight);
		}else{
			$("#weight_modal").val("");
		}
		 $("#modifyGoodsWeightModal").modal({backdrop: 'static', keyboard: false});
	}
	// 设置F端商品权重
	function modifyWeight(){
		// 防止重复提交
		$("#modifyWeight").attr("disabled",true);
		var weight = $("#weight_modal").val();
		var id = $("#goodsId_modal").val();
		// 校验参数有效性
		if(weight == null || weight == ""){
			$.notify("error", "输入权值");
			$("#modifyWeight").attr("disabled",false);
			return;
		}
		if(weight <= 0 || weight >=10000){
			$.notify("error", "请输入1-9999的有效值");
			$("#modifyWeight").attr("disabled",false);
			return;
			}
		if(id != null){
			$.ajax({
				"dataType" : 'json',
				"type" : "POST",
				"async" : false,
				"url" : "${modiftFgoodsWeightUrl}",
				"data" : {
					"id" : id,
					"weight" : weight
				},
				"success" : function(data) {
					if(data.status == 200){
						$.notify("info", "设置权值成功");
					 	$("#modifyGoodsWeightModal").modal("hide");
					 	dataTable.fnDraw();
				 	}else{
				 		$.notify("erro", "设置权值失败");
				 	}
				}
			});
		}else{
			$.notify("erro", "设置权值失败");
			return
		}
	}
	function reset() {
		$("#name").val("");
		$("#location").val("");
		$("#kindTree").combotree('setValue', '');
		search();
	}
</script>