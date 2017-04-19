<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 <spring:url value="/market/border/getOrderList" var="getOrderList" />
 <spring:url value="/market/border/getLogisticsInfo" var="getLogisticsInfo" />
 <spring:url value="/market/border/saveLogisticsInfo" var="saveLogisticsInfo" />
 <spring:url value="/market/border/receiveOrder" var="updateOrder" />
 <spring:url value="/market/border/deliverOrder" var="deliverOrder" />
 <spring:url value="/market/border/getOrderDetail" var="getOrderDetail" />
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
    datatables = $("#orderList").dataTable({
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
          "url" : "${getOrderList}",
          "type" : "POST",
          "dataSrc" : "content",
          "dataType":'json',
          "data" :function(d){
               d.orderNo = $("#orderNo").val();
               d.receiverName = $("#receiverName").val();
               d.catalog = $("#catalog").val();
               d.payType = $("#payType").val();
              d.status = $("#status").val();
          }
      },
      "columns" : [{
          data : "orderNo",
          data : "receiverName",
          data : "reveiverMobile",
          data : "receiverAddress",
          data : "status",
          data : "total",
          data : "freight",
          data : "total",
          data : "createdTime",
          data : "payTime",
          data : "id"
      }  ],
      "columnDefs" : [
                      {
                          targets : [0],
                              "render" : function(data, type,full, meta) {
                                  return "<a href='${getOrderDetail}?orderId="+full.id+"'>"+full.orderNo+"</a>";
                              }
                      },
                      {
                          targets : [1],
                              "render" : function(data, type,full, meta) {
                                  return full.receiverName;
                              }
                      },
                      {
                          targets : [2],
                              "render" : function(data, type,full, meta) {
                                  return full.reveiverMobile;
                              }
                      },
                      {
                          targets : [3],
                              "render" : function(data, type,full, meta) {
                                  return full.receiverAddress;
                              }
                      },
                      {
                          targets : [4],
                              "render" : function(data, type,full, meta) {
                                  if(full.status == 1){
                                      if(full.receiveOrder == 2){
                                          return "订单已完成(店家拒绝接单)";
                                      }
                                      if(full.returnStatus == 2){
                                    	  return "已完成(退款完成)";
                                      }
                                      return "已完成";
                                  }else if(full.status == 2){
                                      return "待支付";
                                  }else if(full.status == 3){
                                      return "待发货";
                                  }else if(full.status == 4){
                                	  if(full.returnStatus == 1){
                                    	  return "待收货(退款中)";
                                      }
                                       return "待收货";
                                  }else if(full.status == 6){
                                      return "用户已取消订单";
                                  }else{
                                      return "";
                                  }
                              }
                      },
                      {
                          targets : [5],
                          "render" : function(data, type,full, meta) {
                              if(full.catalog == 0){
                                  return "水果订单";
                              }else if(full.catalog == 1){
                                  return "非水果订单";
                              }else{
                                  return "";
                              }
                          }
                      },
                      {
                          targets : [6],
                              "render" : function(data, type,full, meta) {
                                  return full.total / 100;
                              }
                      },
                      {
                          targets : [7],
                              "render" : function(data, type,full, meta) {
                                  return full.freight / 100;
                              }
                      },
                      {
                          targets : [8],
                              "render" : function(data, type,full, meta) {
                                  if(full.payTime != null){
                                      return full.realPay / 100;
                                  }else{
                                      return "";
                                  }
                              }
                      },{
                          targets : [9],
                          "render" : function(data, type,full, meta) {
                              return new Date(full.createdTime).Format("yyyy-MM-dd hh:mm:ss") ;
                          }
                      },
                      {
                          targets : [10],
                          "render" : function(data, type,full, meta) {
                              if(full.payTime != null){
                                  return new Date(full.payTime).Format("yyyy-MM-dd hh:mm:ss") ;
                              }else{
                                  return '';
                              }
                          }
                      },
                      {
                          targets : [11],
                          "render" : function(data, type,full, meta) {
                              if(full.status == 3){
                                  if((full.catalog == "0" && full.receiveOrder == null) || (full.catalog == "0" && full.receiveOrder == 0)){
                                      return '<div class="div-center"><a href="javascript:receiveOrder(\''+full.id+'\','+0+','+full.userId+',\''+full.orderNo+'\')">&nbsp;接单</a></div><div class="div-center"><a href="javascript:receiveOrder(\''+full.id+'\','+1+','+full.userId+',\''+full.orderNo+'\','+full.realPay+')" style="color:red">&nbsp;拒绝</a></div>';
                                  }else if((full.catalog == "0" && full.receiveOrder != null) || (full.catalog == "0" && full.receiveOrder != 0)){
                                      return '<div class="div-center"><a href="javascript:deliverOrder(\''+full.id+'\',\''+full.orderNo+'\','+full.userId+')">&nbsp;发货</a></div>'; 
                                  }
                                  if(full.catalog == "1"){
                                      return '<div class="div-center"><a href="javascript:DeliverGoods(\''+full.orderNo+'\','+full.userId+')">&nbsp;发货</a></div>';
                                  }
                              }else{
                                  return '';
                              }
                          }
                      }
              ]
    });
    
    var compayTable;
   // 选择发货地址
       compayTable = $("#CompanyTable").dataTable({
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
             "url" : "${getLogisticsInfo}",
             "type" : "POST",
             "dataSrc" : "content",
             "dataType":'json',
             "data" :function(d){
                 d.name = $("#name").val();
            }
         },
         "columns" : [{
             data : "code",
             data : "name" 
         }  ],
         "columnDefs" : [
                         {
                             targets : [0],
                             "render" : function(data, type,full, meta) {
                                 var codeHtml = '';
                                 codeHtml += '<input type="radio" name="companyRd" value=\''+full.code+','+full.name+'\'/>&nbsp;&nbsp;';
                                 codeHtml += full.code;
                                 return codeHtml;
                             }
                         },
                         {
                             targets : [1],
                                 "render" : function(data, type,full, meta) {
                                     return full.name;
                                 }
                         }
                 ]
    });
   
    // 发货功能
    function DeliverGoods(orderNo,userId){
        $("#showDelivyGoodsModel").modal({backdrop: 'static', keyboard: false});
        $("#logisticName").val("");
        $("#logisticCode").val("");
        $("#logisticNumber").val("");
        
        var html = '<button type="button" class="btn btn-white" data-dismiss="modal">取消</button>';
        html += '<button type="button" class="btn btn-primary" onclick="saveLogisticsInfo(\''+orderNo+'\','+userId+')"><span class="modal-oper">确认发货</span></button>';
        $("#saveLogisticHtml").html(html);
   
    }
    
    function getLogisticsInfo(){
        $("#name").val('');
        compayTable.fnDraw();
        $("#chooseLogicCompay").modal({backdrop: 'static', keyboard: false});
    }
    // 选择物流公司
    function chooseCompayVal(){
        var code = $('input:radio:checked').val();
        if(code==null||code==""){
            alert("请选择物流公司");
            return false;
        }
        var arr = code.split(",");
        $("#logisticName").val(arr[1]);
        $("#logisticCode").val(arr[0]);
        $("#chooseLogicCompay").modal("hide");
    }
    
    // 保存物流信息    
    function saveLogisticsInfo(orderNo,userId){
        var code = $("#logisticCode").val();
        var number = $("#logisticNumber").val();
        if(code == null||code==""){
            alert("请选择物流公司");
            return false;
        }
        if(number==null||number==""){
            alert("请输入运单号");
            return false;
        }
        $.ajax({
            "dataType" : 'json',
            "type" : "GET",
            "url" : "${saveLogisticsInfo}",
            "data" :{"orderNo":orderNo,"code":code,"number":number,"userId":userId},
            "success" : function(data) {
                if(data!=null&&data!=0){
                    alert("发货成功");
                    $("#showDelivyGoodsModel").modal("hide");
                    window.location.href = ctx + "/market/border/orderlist";
                }
                return false;
            }
        });
    }
        function receiveOrder(orderId,type,userId,orderNo,realPay){
            var status = "";
            var receiveOrder = "";
            if(type == 0){
                status = "3";
                receiveOrder = "1";
            }else{
                status = "1";
                receiveOrder = "2";
            }    
            $.post("${updateOrder}",{orderId:orderId,status:status,receiveOrder:receiveOrder,userId:userId,orderNo:orderNo,realPay:realPay},function(data){
                if(data == 1){
                    window.location.reload();
                }
            },'json');
        }
        function deliverOrder(orderId,orderNo,userId){
            $.post("${deliverOrder}",{orderId : orderId,orderNo:orderNo,userId:userId},function(data){
                if(data.result == 1){
                    window.location.reload();
                }
            },'json');
        }
        
        // 物流搜索查询
        function compaySearch(){
        	compayTable.fnDraw();
        }
        
        // 物流清空查询
        function compayReset(){
            $("#name").val('');
            compayTable.fnDraw();
        }
        
        function search(){
            datatables.fnDraw();
        }
        function reset(){
             $("#orderNo").val('');
                $("#receiverName").val('');
                $("#catalog").val('');
                $("#payType").val('');
               $("#status").val('');
              datatables.fnDraw();
        }
 </script>