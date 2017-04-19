<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/farm/terminal/goods/page" var="goodsListUrl" />
<spring:url value="/farm/terminal/goods/index" var="goodsIndex" />
<spring:url value="/market/bgoods/insert-bgoods-pic" var="uploadGoodsPicUrl" />
<script>
	
	function getGoodsList(){
		var goodsNo = $('#goodsNo').val();
		var goodsName = $('#goodsName').val();
		var shopName = $('#shopName').val();
		var goodsStatus = $('#goodsStatus').val(); 
		$("#goodsList").dataTable({
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
				"url" : "${goodsListUrl}?now="+new Date().getTime(),
				"type" : "POST",
				"dataType":'json',
				"dataSrc":"content",
				"data" :{"goodsNo":goodsNo,"goodsName":goodsName,"shopName":shopName,"goodsStatus" : goodsStatus},
			},
   	     "columns": [
						{"data" : "id" },
						{"data" : "goodsNo"},
						{"data" : "name"},
						{"data" : "shop.shopName"},
						{"data" : "sharePercent"},
						{"data" : "status"},
						{"data" : "releaseTime"},
						{"data" : ""}
   	             ],
            "columnDefs" : [
						{
							targets : [0],
							"visible": false
						},
						{
							targets : [1],
							"render" : function(data, type,full, meta) {
								return '<u style="cursor:pointer" class="text-primary clearunderline" onclick="goods_show('+full.id+');" title='+full.goodsNo+'>'+full.goodsNo+'</u>';
							}
						},
						{
							targets : [3],
							"render" : function(data, type,full, meta) {
								if(null == full.shop){
									return '';
								}else{
									return full.shop.shopName;
								}
							}
						},
						{
							targets : [4],
							"render" : function(data, type,full, meta) {
								if(null == full.sharePercent || full.sharePercent == ""	){
									return '——';
								}else{
									return full.sharePercent+"%";
								}
							}
						},
						{
							targets : [5],
							"render" : function(data, type,full, meta) {
								//商品状态码  0-待审核，1-上架，2-下架，3-审核不通过，4-草稿
								if(full.status == 0){
									return '待审核';
								}else if(full.status == 1){
									return '上架';
								}else if(full.status == 2){
									return '下架';
								}else if(full.status == 3){
									return '审核不通过';
								}else{
									return '草稿';
								}
							}
						},
						{
							targets : [6],
							"render" : function(data, type,full, meta) {
								if(null == full.updatedTime){
									return '';
								}else{
									return new Date(parseInt(full.updatedTime)).Format("yyyy-MM-dd hh:mm:ss");
								}
							}
						},
						{
							targets : [7],
							"render" : function(data, type,full, meta) {
								if(full.status == '0'){
									return '<a style="text-decoration:none" class="ml-5" onclick="on_shelf(this,'+full.id+')" href="javascript:void(0);" title="审核通过"><i class="fa fa-arrow-up"></i>审核通过</a>&nbsp;&nbsp;'
									+'<a style="text-decoration:none" class="ml-5" onclick="no_pass(this,'+full.id+');" href="javascript:void(0);" title="拒绝"><i class="fa fa-close"></i>拒绝</a>';
								}else if(full.status == '1'){
									return '<a style="text-decoration:none" class="ml-5" onclick="off_shelf(this,'+full.id+');" href="javascript:void(0);" title="下架"><i class="fa fa-arrow-down"></i>下架</a>';
								}else{
									return '';
								}
							}
						}
					]
	    	});
		}
	
	//根据商品id获取商品详情
	function goods_show(id){
		layer.open({
			  type: 2,
			  title: '商品详情信息',
			  shadeClose: true,
			  area: ['850px', '500px'],
			  content: '${pageContext.request.contextPath}/farm/terminal/goods/show?id='+id
		}); 
	}
	
	
	//上架商品
	function on_shelf(obj,id){
		swal({
	        title: "您确定审核通过该商品吗?",
	        type: "warning",
	        confirmButtonColor: "#DD6B55",
	        confirmButtonText: "确定",
	        cancelButtonText: "取消",
	        showCancelButton: true,
	        closeOnConfirm: false
	    }, function () {
	    	$.ajax({
				"dataType" : 'json',
				"type" : "post",
				"data" : {"id":id,"status":"1"},
				"url" : "${pageContext.request.contextPath}/farm/terminal/goods/updateStatus",
				"success" : function(result) {
					if(result.type == "error"){
						swal({
		      			        title: "修改商品状态失败",
		      			        type: "warning"
		      			    });
					}else{
						window.location.href = "${goodsIndex}";
					}
				}
			});
	    });		
	}
	
	//下架商品
	function off_shelf(obj,id){
		swal({
	        title: "您确定下架该商品吗?",
	        type: "warning",
	        confirmButtonColor: "#DD6B55",
	        confirmButtonText: "确定",
	        cancelButtonText: "取消",
	        showCancelButton: true,
	        closeOnConfirm: false
	    }, function () {
	    	$.ajax({
				"dataType" : 'json',
				"type" : "post",
				"data" : {"id":id,"status":"2"},
				"url" : "${pageContext.request.contextPath}/farm/terminal/goods/updateStatus",
				"success" : function(result) {
					if(result.type == "error"){
						swal({
		      			        title: "修改商品状态失败",
		      			        type: "warning"
		      			    });
					}else{
						window.location.href = "${goodsIndex}";
					}
				}
			});
	    });
	}
	
	//审核不通过
	function no_pass(obj,id){
		swal({
	        title: "您确定拒绝该商品审核通过吗?",
	        type: "warning",
	        confirmButtonColor: "#DD6B55",
	        confirmButtonText: "确定",
	        cancelButtonText: "取消",
	        showCancelButton: true,
	        closeOnConfirm: false
	    }, function () {
	    	//弹出框，必须要输入审核不通过的原因
	    	layer.open({
			  type: 2,
			  title: '填写审核不通过原因',
			  shadeClose: true,
			  area: ['893px', '600px'],
			  content: '${pageContext.request.contextPath}/farm/terminal/goods/reply?id='+id
			}); 
	    });
	}
	
	//审核不通过原因提交后台
	function confirm(){
		//提交数据到后台
		var postData = $("#replyForm").serialize();
		$.ajax({
			"dataType" : 'json',
			"type" : "post",
			"data" : postData,
			"url" : "${pageContext.request.contextPath}/farm/terminal/goods/replyTo",
			"success" : function(result) {
				if(result.type == "error"){
					swal({
	      			        title: "添加审核不通过原因失败",
	      			        type: "warning"
	      			    });
				}else{
					parent.window.location.href = "${goodsIndex}";
				}
			}
		});
	}
	
	//查询table的方法
	function search(){
		getGoodsList();
		$('#goodsList').dataTable().fnDraw();
	}
	
	//清空table的方法
	function reset(){
		$('#goodsNo').val("");
		$('#goodsName').val("");
		$('#shopName').val("");
		$('#goodsStatus').val(""); 
		getGoodsList();
		$('#goodsList').dataTable().fnDraw();
	}
	
	//jquery初始化的时候加载商品列表
	$(function(){
		getGoodsList();
	});
	
</script>