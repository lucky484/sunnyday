<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<spring:url value="/farm/manifest/page" var="pageUrl"/>
<spring:url value="/farm/manifest/detail" var="detailUrl"/>
<spring:url value="/farm/manifest/getproducer" var="getUrl"></spring:url>
<spring:url value="/farm/manifest/setproducer" var="setUrl"></spring:url>
<spring:url value="/farm/manifest/batchprint" var="printUrl"></spring:url>
<spring:url value="/resources/platform/plugins/My97DatePicker/WdatePicker.js" var="timeUrl"></spring:url>
<script type="text/javascript" src="${timeUrl}"> </script>
<script>
	function LoadDt(){
		var condition = $("#condition").val();
		var time = $("#time").val();
		var type = $("#type option:selected").val();
		$("#orderListTb").dataTable({
			"dom":"<'m-r m-t-lg pull-right'f><'tb-fix't><'row col-sm-12'<'col-sm-4'l>r<'col-sm-4'i><'pull-right'p>>",
		    "autoWidth": false,
		    "searching" : false,
			"stateSave" : true,
			"ordering" : false,
			"pageLength" : 10,
		    "pagingType" : "full_numbers",
			"bSort":false,
			"serverSide" : true,
			"bDestroy" : true,
			"ajax" : {
				"url" : "${pageUrl}",
				"type" : "post",
				"dataSrc":"content",
				"dataType":'json',
				"data" :{"condition":condition,"time":time,"type":type},
			},
		    "columns": [
						{"data" : "id"},
						{"data" : "orderId"},
						{"data" : "user.nickName"},
						{"data" : "user.shopName"},
						{"data" : "total"},
						{"data" : "realPay"},
						{"data" : "distributionArea"},
						{"data" : "receiverAddress"},
						{"data" : "status"},
						{"data" : "payType"},
						{"data" : "createdTime"},
						{"data" : ""}
		            ],
		    "columnDefs" : [
						{
							targets : [0],
							"render" : function(data, type,full, meta) {
								return '<input type="checkbox" name="checkObj" value='+full.orderId+' onclick="check(this);">';
							}
						},
						{
							targets : [4],
							"render" : function(data, type,full, meta) {
								return "￥"+(full.total/100);
							}
						},
						{
							targets : [5],
							"render" : function(data, type,full, meta) {
								return "￥"+(full.realPay/100);
							}
						},
						{
							targets : [8],
							"render" : function(data, type,full, meta) {
								var status = "";
								if(full.status==1){
									status = "已完成";
								} else if(full.status==2){
									status = "待支付";
								} else if(full.status==3){
									status = "待发货";
								} else if(full.status==4){
									status = "待收货";
								} else if(full.status==5){
									status = "待评价";
								}
								return status;
							}
						},
						{
							targets : [9],
							"render" : function(data, type,full, meta) {
								return full.payType==0?"担保货到付款":full.payType==1?"微信支付":"支付宝支付";
							}
						},
						{
							targets : [10],
							"render" : function(data, type,full, meta) {
								return new Date(parseInt(full.createdTime)).Format("yyyy-MM-dd hh:mm:ss");
							}
						},
						{
							targets : [11],
							"render" : function(data, type,full, meta) {
								return "<a href='${detailUrl}?oid="+full.orderId+"'>出货单详情</a>";
							}
						}
						
					]
			});
	}
	//设置制单人
	function setProducer(){
		$("#producerModal").modal({backdrop: 'static', keyboard: false});
		//把系统中配置文件中的制单人加载出来
		$.ajax({
			"dataType" : 'json',
			"type" : "get",
			"data" : "",
			"url" : "${getUrl}",
			"success" : function(result) {
				$('#producer').val(result);
			}
		});
	}
	//想后台发送请求
	function confirm(){
		//重新设置制单人
		$.ajax({
			"dataType" : 'json',
			"type" : "post",
			"data" : {"producer":$("#producer").val()},
			"url" : "${setUrl}",
			"success" : function(result) {
				if(result == 'success'){
					$("#producerModal").modal('hide');
				}
			}
		});
	}
	//全选或取消全选的方法
	function checkAll(obj){
		//如果取消全选的话就全部取消
		if(!$(obj).is(":checked")){
			$("input[name='checkObj']").each(function(){
				$(this).prop("checked",false);
			});
		}else{
			$("input[name='checkObj']").each(function(){
				$(this).prop("checked",true);
			});
		}
	}
	//单个checkbox事件
	function check(obj){
		//单个checkbox事件,计算出所有的checkObj选中的长度，如果和checkAll一样长久还是全选，否则就不全选了
		var total = $("#orderListTb").DataTable().rows().data().length;
		var count = 0;
		$("input[name='checkObj']").each(function(){
			if($(this).attr('checked') == 'checked'){
				count++;
			}
		});
		//如果总数相等就把全选选上
		if(total == count){
			$("#checkAll").prop("checked",true);
		//否则取消全选
		}else{
			$("#checkAll").prop("checked",false);
		}
	}
	
	//批量打印的方法
	function batchPrint(){
		//把页面上所有选中的订单去后台打印
		var array = [];
		$("input[name='checkObj']").each(function(){
			if($(this).attr('checked') == 'checked'){
				array.push($(this).val());
			}
		});
		//判断当前有没有选中订单，如果没有选中给出提示，
		if(array == null || array.length == 0){
			 swal({
	                title: "请选择要打印的订单",
	                type: "warning"
	            });
			return;
		}
		//将所有选中的订单号发送到后台去做处理
		var form = $("<form></form>");
        form.attr('action',"${printUrl}");
        var oid = $("#oid").val();
        var input3 = $('<input type="hidden" name="oidArray" value="'+array+'"/>');
        form.append(input3);
        form.attr('method','post');
        //form.attr('enctype','multipart/form-data');
        form.submit();
		
	}
	//默认的重置方法
	function reset(){
		$("#time").val('');
		$("#condition").val('');
		$("#type option:first").prop("selected", 'selected');
		LoadDt();
		$('#orderListTb').dataTable().fnDraw();
	}
	//默认的查询方法
	function search(){
		LoadDt();
		$('#orderListTb').dataTable().fnDraw();
	}
	
	$('input[name=condition]').on('mouseover',function(){
		layer.tips('请输入订单编号或用户姓名或店铺名称或配送区域或收获地址', '#condition', {
			  tips: [1, '#3595CC'],
			  time: 2000
		});
	});
	
	//juqery初始化的时候加载表格
	$(function(){
		LoadDt();
	});
</script>