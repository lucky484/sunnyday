<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/farm/order/querybypages" var="queryByPageUrl"></spring:url>
<spring:url value="/farm/order/vieworder" var="viewOrderUrl"></spring:url>
<spring:url value="/farm/order/modifyprepare" var="modifyPrepareUrl"></spring:url>
<spring:url value="/farm/order/deleteorder" var="delOrderUrl"></spring:url>
<spring:url value="/farm/order/sendorder" var="sendOrderUrl"></spring:url>
<spring:url value="/farm/order/makesurepay" var="makeSurePayUrl"></spring:url>
<spring:url value="/farm/order/checkOrderMoney" var="checkOrderMoney"></spring:url>
<spring:url value="/farm/order/showDiffOrder" var="showDiffOrder"></spring:url>
<spring:url value="/farm/order/detail" var="orderDetailUrl"></spring:url>
<spring:url value="/farm/order/deleteOrder" var="deleteOrderUrl"></spring:url>
<spring:url value="/resources/platform/plugins/My97DatePicker/WdatePicker.js" var="timeUrl"></spring:url>
<script type="text/javascript" src="${timeUrl}"> </script>
<script>
var datatable;
var selectedOption = 0;
var dtUrl = "${queryByPageUrl}";
$(".selectpicker").on("change",function(){
    selectedOption = $(".selectpicker option:selected").val();
    window.sessionStorage.selectedOption=selectedOption;
    loadDT();
});

$(document).ready(function(){
    
    if (undefined != window.sessionStorage.selectedOption)
    {
         $(".selectpicker option").each(function(){
             if($(this).val()==window.sessionStorage.selectedOption){
                 $(this).attr("selected","selected");
             }else{
                 $(this).removeAttr("selected");
             }
         });
         selectedOption = window.sessionStorage.selectedOption;
    }
    loadDT();
});

function loadDT()
{
	datatable =  $(".dataTables-orderlist").dataTable({
    	"dom":"<'m-r m-t-lg pull-right'f><'tb-fix't><'row col-sm-12'<'col-sm-4'l>r<'col-sm-4'i><'pull-right'p>>",
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
            "url" : dtUrl + "?orderStatus="+selectedOption ,
            "type" : "POST",
            "dataSrc" : "content",
            "dataType":'json',
            "data" :function(d){
            	d.orderNo = $("#orderNo").val();
            	d.status = $("#status").val();
            	d.startTime = $("#startTime").val();
            	d.endTime = $("#endTime").val();
            	d.shopName = $("#shopName").val();
            }
        },
        "columns" : [  {
            // 订单ID
            data : "orderId"
        }, {
            // 订单ID
            data : "orderId"
        }, {
            // 总价
            data : "user.nickName"
        }, {
            // 店铺名称
            data : "user.shopName"
        }, {
            // 总价
            data : "total"
        }, {
            // 实际支付
            data : "realPay"
        }, {
            // 配送地区
            data : "distributionArea"
        },{
            // 收货地址
            data : "receiverAddress"
        },{
            // 支付类型
            data : "payType"
        },{
            // 支付方式
            data : "createdTime"
        } ,{
            // 支付方式
            data : "createdTime"
        }, {
            // 补差价金额
            data : "diffenceAmount"
        } , {
            // 订单编号
            data : "id"
        }  ],
        "columnDefs" : [
            {
                targets : [0],
                    "render" : function(data, type,full, meta) {
                        if(full.status==1){
                            return "<span class='text-success'>已完成</span>";
                        }else if(full.status==2){
                            return "<span class='text-danger'>待支付</span>";
                        }else if(full.status==3){
                            return "<span class='text-warning'>待发货</span>";
                        }else if(full.status==4){
                            return "<span class='text-warning'>待收货</span>";
                        }else{
                            return "<span class='text-info'>待评价</span>";
                        }
                        
                    }
            } ,
            {
                targets : [1],
                    "render" : function(data, type,full, meta) {
                    	var realPayMoney;
                    	if (full.realPay == null)
                        {
                    		realPayMoney= 0;
                        }
                        else
                        {
                            if(full.diffenceId!=null&&full.diffenceId!=""){
                                if(full.diffenceStatus==0||full.diffenceStatus==1){
                                    if(full.diffenceType==1){
                                    	realPayMoney = (full.realPay-full.diffenceAmount);
                                    } else {
                                    	realPayMoney =  (full.diffenceAmount+full.realPay);
                                    }
                                } else if(full.diffenceStatus==2){
                                	if(full.diffenceType==0){
                                		realPayMoney = (full.diffenceAmount+full.realPay);
                                	} else {
                                		realPayMoney =  full.realPay;
                                	}
                                }else if(full.diffenceStatus==3){
                                	realPayMoney = full.realPay;
                                }
                                
                            } else {
                            	realPayMoney = full.realPay;
                            }
                        }
						return "<a href='${orderDetailUrl}?orderId="+full.id+"&realPay="+realPayMoney+"'>"+full.orderId+"</a>";                        
                    }
            } ,
            {
                targets : [4],
                    "render" : function(data, type,full, meta) {
                        return (full.total/100)
                    }
            } ,
            {
                targets : [5],
                    "render" : function(data, type,full, meta) {
                        if (full.realPay == null)
                        {
                            return "——";
                        }
                        else
                        {
                            return full.realPay/100;
                        }
                    }
            } ,
            {
                targets : [8],
                    "render" : function(data, type,full, meta) {
                        var type = full.payType;
                        if (type == 0)
                        {
                            return "<span class='text-warning'>担保货到付款</span>";
                        }
                        if (type == 1)
                        {
                            return "<span class='text-primary'>微信</span>";
                        }
                        if (type == 2)
                        {
                            return "<span class='text-primary'>支付宝</span>";
                        }
                    }
            },
            {
                targets : [9],
                    "render" : function(data, type,full, meta) {
                        return formatCurrentTimeMillis(full.createdTime);
                    }
            } ,
            {
                targets : [10],
                    "render" : function(data, type,full, meta) {
                    	if(full.payTime != null){
                       	 return formatCurrentTimeMillis(full.payTime);
                    	}else{
                    		return '';
                    	}
                    }
            } ,
            {
                targets : [11],
                    "render" : function(data, type,full, meta) {
                        if(full.diffenceId!=null&&full.diffenceId!=""){
                            var status = '';
                            status += '<a href="javascript:showDiffOrder(\''+full.diffenceId+'\')" >';
                            if(full.diffenceStatus==0||full.diffenceStatus==1){
                                if(full.diffenceType==0){
                                    status += "补(" + full.diffenceAmount/100 + ")";
                                } else {
                                    status += "退(" + full.diffenceAmount/100 + ")";
                                }
                            }else if(full.diffenceType==0 && full.diffenceStatus==2){
                            	 status += "补款已完成";
                            } else if(full.diffenceType==1 && full.diffenceStatus==2){
                                status += "退款已完成";
                            } else if(full.diffenceStatus==3){
                                status += "审核未通过";
                            } 
                            status += "</a>";
                        } else {
                            return "";
                        }
                            
                        return status;
                    }
            } ,
            {
                targets : [12],
                    "render" : function(data, type,full, meta) {
                    	 var value = "";
                         if (full.payType == 0 && (full.status == 3 || full.status == 2))
                         {
                        	 value = '<div class="div-center"><a href="javascript:void(0)" onclick="sendOrder('+"'"+ full.orderId +"'"+')"></i>&nbsp;发货</a></div>';
                         }else if (full.payType != 0 && full.status == 2)
                         {
                        	 value = '<div class="div-center"><span>未付款</span></div>';
                         }else if (full.status == 3)
                         {
                        	 value = '<div class="div-center"><a href="javascript:void(0)" onclick="sendOrder('+"'"+ full.orderId +"'"+')"></i>&nbsp;发货</a></div>';
                         }else if (full.status == 4)
                         {
                        	 value = '<div class="div-center"><a href="javascript:void(0)" onclick="makeSurePay('+"'"+ full.orderId +"'"+')"></i>&nbsp;确认收款</a></div>';
                         }else if (full.status == 5)
                        	 value = "<div class='div-center'><span>待评价</span></div>";
                         else{
                        	 value = "<div class='div-center'><span>已完成</span></div>";
                         }
                         if("${USER_INSESSION.roleId}" == 1){
	                         value +='<div class="div-center"><a href="javascript:void(0)" onclick="deleteOrder(\''+ full.id +'\')"></i>&nbsp;删除</a></div>';
                         }
                         return value;
                    }
            } 
        ]
});
}

function delOrder(id){
    $.ajax({
        "dataType" : 'json',
        "type" : "post",
        "async" : false,
        "url" : '${delOrderUrl}',
        "data" : {
            "orderId" : id
        },
        "success" : function(data) {
            if (data)
            {
                alert("修改成功");
            }
            else
            {
                alert("修改成功");
            }
        },
        "error": function(data){
            alert("修改成功");
        }
    });
}

function sendOrder(id){
    $.ajax({
        "dataType" : 'json',
        "type" : "post",
        "async" : false,
        "url" : '${sendOrderUrl}',
        "data" : {
            "orderId" : id
        },
        "success" : function(data) {
            if (data)
            {
                swal({
                    title: "成功!",
                    type: "info", 
                } , function(){window.location.reload()});
            }
            else
            {
                alert("修改成功");
            }
        },
        "error": function(data){
            alert("修改成功");
        }
    });
}

function makeSurePay(id){
    
    swal({
        title: "确认收货",
        text: "请输入真实付款金额:",
        type: "input",
        showCancelButton: true,
        closeOnConfirm: false,
        animation: "slide-from-top",
        inputPlaceholder: "金额(人民币:元)",
        confirmButtonText: "确认收货",
        cancelButtonText: "取消"
    },
    function(inputValue) {
        if (inputValue === false) return false;
        if (inputValue === "") {
            swal.showInputError("请输入真实付款金额!");
            return false
        }
        $.ajax({
            "dataType" : 'json',
            "type" : "post",
            "async" : false,
            "url" : '${makeSurePayUrl}',
            "data" : {
                "orderId" : id,
                "realPay" : inputValue
            },
            "success" : function(data) {
                if (data)
                {
                    swal({
                        title: "成功!",   
                        text: "确认收货: " + inputValue+"元成功！",   
                        type: "info", 
                    } , function(){window.location.reload()});
                }
            },
            "error": function(data){
                swal({
                    title: "成功!",   
                    text: "",   
                    type: "info",   
                } , function(){window.location.reload()});
            }
        });
        
    });
}

function formatCurrentTimeMillis (dateTime) {
    return  new Date(dateTime).Format("yyyy-MM-dd hh:mm:ss");
}

// 显示补差价订单信息
function showDiffOrder(id){
    $.ajax({
        "dataType" : 'json',
        "type" : "post",
        "url" : '${showDiffOrder}',
        "data" : {"id" : id},
        "success" : function(data) {
            $("#checkOrderMoney").modal({backdrop: 'static', keyboard: false});
            $("#findOrderNo").html(data.orderNo);
            $("#differenceUser").html(data.realName);
            var differenceType = data.diffenceType;
             if(differenceType==0){
                $("#differenceType").html("补");
            } else {
                $("#differenceType").html("退");
            }
            var money = data.diffenceAmount;
            if(money!=null&&money!=""){
                $("#differenceMoney").html(parseInt(money)/100);
            }
            var html = '';
            $.each(data.imgList,function(index,obj){
                html += '<img src=\''+obj+ '\' onclick="imgOnclick(\''+obj+'\')" width="100px" height="100px"/>&nbsp;&nbsp;';
            });
            $("#differenceImgs").html(html);
            var differenceCause = data.diffenceCause;
            var cause = "";
            if(differenceCause==1){
                cause = "产品重量";
            } else if(differenceCause==2){
                cause = "我要换货";
            } else if(differenceCause==3){
                cause = "产品重量";
            } else if(differenceCause==4){
                cause = "其他";
            }
            $("#differenceCause").html(cause);
            var statusHtml = "";
            var status = data.status;
            if(status=="1"){
                statusHtml = "待审核";
            } else if(status=="2"){
                statusHtml = "审核通过";
            } else if(status=="3"){
                statusHtml = "审核未通过";
            }
             $("#differenceStatus").html(statusHtml);
             $("#findRemark").html(data.remark);
             if(differenceType==0){
            	 loadCancelBtn();
                $("#differentHtml").css('display','none'); 
            } else {
	           	 if(status=="1"||status=="0"){
	                 loadDiffOrder(id,data.orderId,data.realPay,money);
	        	 } else {
	        		 loadCancelBtn();
	        	 }
                $("#differentHtml").css("display","block");
            }
         },
    });
}

// 加载补差价审核按钮
function loadDiffOrder(id,orderId,realPay,money){
    var html = '';
    html += '<button id="checkPass" type="button" class="btn btn-primary" onclick="checkOrderMoney(\''+id+'\',2,\''+orderId+'\','+realPay+','+money+')"><span class="modal-oper">审核通过</span></button>';
    html += '<button id="checkRefuse" type="button" class="btn btn-primary" onclick="checkOrderMoney(\''+id+'\',3,\''+orderId+'\','+realPay+','+money+')"><span class="modal-oper">审核拒绝</span></button>';
    html += '<button type="button" class="btn btn-white" data-dismiss="modal">取消</button>';
    $("#buttonHtml").html(html);
}
// 加载取消按钮
function loadCancelBtn(){
    var html = '<button type="button" class="btn btn-white" data-dismiss="modal">取消</button>';
    $("#buttonHtml").html(html);
}

// 显示放大图片
function imgOnclick(img,alt){
    $("#checkOrderMoney").modal("hide");
    $("#findImgList").modal({backdrop: 'static', keyboard: false});
    $("#imgSrc").attr("src",img);
}

// 关闭图片
function imgClose(){
    $("#findImgList").modal("hide");
    $("#checkOrderMoney").modal({backdrop: 'static', keyboard: false});
}

// 审核补差价金额
function checkOrderMoney(id,type,orderId,realPay,money){
    $.ajax({
        "dataType" : 'json',
        "type" : "post",
        "url" : '${checkOrderMoney}',
        "data" : {
            "id" : id,
            "type":type,
            "orderId":orderId,
            "realPay":realPay,
            "money":money
        },
        "success" : function(result) {
            if(result.type == "error"){
                swal({
                    title: "审核补差价订单信息操作失败！",
                    type: "warning"
                });
            } else {
                $("#checkOrderMoney").modal("hide");
                window.location.reload()
            }
        },
    });
}
function search(){
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	if(startTime == null || startTime == "" && (endTime != null && endTime != "")){
		$.notify("error", "请输入开始时间");
	}else{
		if(new Date(startTime) > new Date(endTime)){
			$.notify("error", "开始时间不能大于结束时间");
			return ;
		}else{
			datatable.fnDraw();
		}
	}
}
function reset(){
	$("#orderNo").val('');
	$("#status").val('');
	$("#startTime").val('');
	$("#endTime").val('');
	$("#shopName").val('');
	datatable.fnDraw();
}

//删除订单
function deleteOrder(orderId){
	 swal({
		    title: "删除提示",
            type: "info", 
            showCancelButton: true,
            closeOnConfirm: false,
	        confirmButtonText: "确认删除",
	        cancelButtonText: "取消"
	    },function(){
	    	$.post("${deleteOrderUrl}",{orderId:orderId},function(data){
	    		if(data.result == 1){
	    		    swal({
	                    title: "删除成功!",
	                    type: "info", 
	                }, function(){window.location.reload()});
	    		}
	    	},'json')
	    }
	);
}
</script>