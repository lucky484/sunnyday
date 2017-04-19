<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 <spring:url value="/market/border/queryReturnOrder" var="queryReturnOrder" />
 <spring:url value="/market/border/agreeReturn" var="agreeReturn" />
 <spring:url value="/market/border/refuseReturn" var="refuseReturn" />
 <style>
    .div-center {
	width: 50%;
	margin-right: auto;
	margin-left: auto;
	margin-top: 5px;
}
 </style>
 <script type="text/javascript">
    var datatables;
    datatables = $("#returnOrderList").dataTable({
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
          "url" : "${queryReturnOrder}",
          "type" : "POST",
          "dataSrc" : "content",
          "dataType":'json',
          "data" :function(d){
        	  d.orderNo = $("#orderNo").val();
        	  d.receiverName = $("#receiverName").val();
         	  d.goodsName = $("#goodsName").val();
         	  d.goodsStatus = $("#goodsStatus").val();
          }
      },
      "columns" : [{
          data : "order.orderNo",
          data : "order.receiverName",
          data : "order.reveiverMobile",
          data : "order.receiverAddress",
          data : "goods.name",
          data : "goodsQty",
          data : "price",
          data : "goodsStatus",
          data : "returnPay.returnType",
          data : "returnPay.receiveType",
          data : "returnPay.returnReason",
          data : "order.total",
          data : "returnPay.returnAmount",
          data : "order.createdTime",
          data : "order.id",
      }  ],
      "columnDefs" : [
                      {
                          targets : [0],
                              "render" : function(data, type,full, meta) {
                                  return full.order.orderNo;
                              }
                      },
                      {
                          targets : [1],
                              "render" : function(data, type,full, meta) {
                                  return full.order.receiverName;
                              }
                      },
                      {
                          targets : [2],
                              "render" : function(data, type,full, meta) {
                                  return full.order.reveiverMobile;
                              }
                      },
                      {
                          targets : [3],
                              "render" : function(data, type,full, meta) {
                                  return full.order.receiverAddress;
                              }
                      },
                      {
                          targets : [4],
                              "render" : function(data, type,full, meta) {
                                 return full.goods.name;
                              }
                      },
                      {
                          targets : [5],
                          "render" : function(data, type,full, meta) {
                        	  return full.goodsQty;
                          }
                      },
                      {
                          targets : [6],
                              "render" : function(data, type,full, meta) {
                                  return full.price / 100;
                              }
                      },
                      {
                          targets : [7],
                              "render" : function(data, type,full, meta) {
                                  if(full.goodsStatus == 1){
                                	  return "退款中";
                                  }else if(full.goodsStatus == 2){
                                	  return "退货中";
                                  }else if(full.goodsStatus == 3){
                                	  return "退款完成";
                                  }else if(full.goodsStatus == 4){
                                	  return "退货完成";
                                  }else if(full.goodsStatus == 5){
                                	  return "店家拒绝退款";
                                  }else if(full.goodsStatus == 6){
                                	  return "店家拒绝退货";
                                  }
                              }
                      },
                      {
                          targets : [8],
                              "render" : function(data, type,full, meta) {
                                  if(full.returnPay.returnType == 1){
                                      return "退款";
                                  }else{
                                      return "退货";
                                  }
                              }
                      },{
                          targets : [9],
                          "render" : function(data, type,full, meta) {
                               if(full.returnPay.receiveType == 1){
                            	   return "未收到货";
                               }else if(full.returnPay.receiveType == 2){
                            	   return "已收到货";
                               }else{
                            	   return '';
                               }
                          }
                      },
                      {
                          targets : [10],
                          "render" : function(data, type,full, meta) {
                              return full.returnPay.returnReason;
                          }
                      },
                      {
                          targets : [11],
                          "render" : function(data, type,full, meta) {
                             return full.order.total/100;
                          }
                      },
                      {
                          targets : [12],
                          "render" : function(data, type,full, meta) {
                             return full.returnPay.returnAmount/100;
                          }
                      },
                      {
                          targets : [13],
                          "render" : function(data, type,full, meta) {
                             return new Date(full.order.createdTime).Format("yyyy-MM-dd hh:mm:ss") ;
                          }
                      },
                      {
                          targets : [14],
                          "render" : function(data, type,full, meta) {
                        	  if(full.goodsStatus == 1 || full.goodsStatus == 2){
                        		  return '<div class="div-center"><a id="'+full.order.id+'" href="javascript:agree(\''+full.order.id+'\',\''+full.order.orderNo+'\',\''+full.goods.name+'\',\''+full.id+'\','+full.returnPay.returnType+','+full.order.userId+','+full.returnPay.returnAmount+');">&nbsp;同意</a></div><div class="div-center"><a id="'+full.order.id+'" href="javascript:refuseOrder(\''+full.order.id+'\',\''+full.order.orderNo+'\',\''+full.goods.name+'\','+full.order.userId+',\''+full.id+'\','+full.returnPay.returnType+');" style="color:red;">&nbsp;拒绝</a></div>';
                        	  }else if(full.goodsStatus == 3){
                        		  return "<div class='div-center'><span>退款完成</span></div>";
                        	  }else if(full.goodsStatus == 4){
                        		  return "<div class='div-center'><span>退货完成</span></div>";
                        	  }else if(full.goodsStatus == 5){
                        		  return "<div class='div-center'><span>店家拒绝退款</span></div>";
                        	  }else if(full.goodsStatus == 6){
                        		  return "<div class='div-center'><span>店家拒绝退货</span></div>";
                        	  }
                             return '';
                          }
                      }
              ]
    });
    
    function agree(orderId,orderNo,goodsName,orderDetailId,returnType,userId,returnAmount){
    	$("#"+orderId).attr("href","javascript:;");
    	$.post("${agreeReturn}",{orderId:orderId,orderNo:orderNo,goodsName:goodsName,orderDetailId:orderDetailId,returnType:returnType,userId:userId,returnAmount:returnAmount},function(data){
    		if(data.result == 1){
    			window.location.reload();
    		}
    	},'json');
    }
    
    function refuseOrder(orderId,orderNo,goodsName,userId,orderDetailId,returnType){
    	$("#"+orderId).attr("href","javascript:;");
    	$.post("${refuseReturn}",{orderId:orderId,orderNo:orderNo,goodsName:goodsName,userId:userId,orderDetailId:orderDetailId,returnType:returnType},function(data){
    		if(data.result == 1){
    			window.location.reload();
    		}
    	},'json');
    }
    function search(){
    	datatables.fnDraw();
    }
    function reset(){
    	 $("#orderNo").val('');
   	     $("#receiverName").val('');
   	     $("#goodsName").val('');
   	     $("#goodsStatus").val('');
      	 datatables.fnDraw();
    }
 </script>