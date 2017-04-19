<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>订单列表</title>
<style type="text/css">
.hidden {
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
	/* 	text-overflow:ellipsis; */
}

.hidden:hover {
	text-overflow: inherit;
	overflow: visible;
}
</style>
<jsp:include page="/include/common.jsp" />
    <script type="text/javascript" src="<c:url value="/resources/lib/jquery/plugin/jquery.form.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/verify.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/lib/My97DatePicker/WdatePicker.js" />"></script>
    <script type="text/javascript">

        $(document).ready(function(){
            $('#dgRecord').datagrid({
                url:'<c:url value="/web/farm/getFarmOrderListJSON.json" />',
                title:'订单列表',
                singleSelect:true,
                fit:true,
                fitColumns:true,
                toolbar:'#tbRecord',
                rownumbers:true,
                pagination:true,
                pageSize: 20,
                columns:[[
                    {field:'orderId',title:'编号',align:'center'},
                    {field:'produceName',title:'商品名',width:60,align:'center'},
                    {field:'sku',title:'订单号',width:120,align:'center'},
                    {field:'merchant',title:'姓名',width:50,align:'center'},
                    {field:'phone',title:'联系电话',width:80,align:'center'},
                    {field:'total',title:'总价',width:50,align:'center'},
                    {field:'weight',title:'数量',width:20,align:'center'},
                    {field:'address',title:'地址',width:200,halign:'center',nowrap:false},
                    {field:'freight',title:'运费',width:50,align:'center'},
                    {field:'unitPrice',title:'单价',width:50,align:'center'},
                    {field:'createDate',title:'下单时间',width:100,align:'center'},
                    {field:'status',title:'订单状态',width:50,align:'center'},
                    {field:'nickname',title:'下单人昵称',width:50,align:'center'},
                    {field:'shareNickname',title:'链接来源',width:50,align:'center',formatter:function(val,row){
                    	if (val == '') {
                    		if (row.shareOpenId == '') {
	                    		return '公众号';
                    		} else {
                    			return row.shareOpenId + "(店铺)";
                    		}
                    	} else {
                    		return val;
                    	}
                    }},
                    {field:'comments',title:'买家留言',width:100,align:'center',nowrap:false,
	   /*                 	formatter: function (val, row){
	   			    		  if (row.comments < 0) {
	   			    			  return '';
	   			    		  }
	   			    		  if (row.comments.length <= 10){		    			  
	   			    		  	return '<div title='+ row.comments +'>' + row.comments + '</div>';
	   			    		  }
	   			    		  if (row.comments.length > 10){
	   			    			return '<div class="hidden" title='+ row.comments +'>' + row.comments + '</div>'; 
	   			    		  }
	   			    	  } */
                    },
                    /* {field:'openId',title:'加密微信号',width:80,align:'center'} */
//                     {field:'edit',title:'Edit',align:'center',formatter:function(val,row){
//                         if(row.recordId < 0) { return ''; }
//                         var editUrl = "<c:url value="/web/record/getRecordEditPage.action?recordId=" />" + row.recordId;
//                         return '&nbsp;' +
//                                 '<a href="javascript:void(0)" onclick="openWindow(\'#wRecord\', \'' + editUrl + '\')">编辑</a>' +
//                                 '&nbsp;';
//                     }}
                ]]
            });

            formatPagination("dgRecord");

            vEasyUIUtil.createWindow("wRecord", {
                title: '新增/编辑',
                width:640,
                height:vSugar.getMaxWinHeight("mainPanel", 600),
                modal:true,
                closed:true,
                collapsible:false,
                minimizable:false,
                maximizable:true,
                iconCls:'icon-save'
            });
        });

        /******************************* 数据操作 *******************************/

        function submitRemove(recordId, isFakeDelete){
            if (!confirm("确认删除")){
                return;
            }
            // isFakeDelete=1表示假删除
            $.post("logicRemoveRecord.action", {recordId: recordId, isFakeDelete: isFakeDelete},
                    function(result){
                        if(result == 1){
                            $('#dgRecord').datagrid('reload');
                        }
                    }
            );
        }

        function submitSearch(){
            var dgRecord = $('#dgRecord');

            dgRecord.datagrid('options').pageNumber = 1;
            dgRecord.datagrid('getPager').pagination("options").pageNumber = 1;
            var param = {
            };
            $("#dgRecord").datagrid({queryParams: param}, 'reload');
        }

        function clearSearch(){
        }
    </script>
</head>
<body class="easyui-layout">

<div data-options="region:'center',border:false" class="mainPanel">
    <div id="tbRecord" class="toolBarPadding">
        <div style="float:left;">
            <a href="<c:url value="/web/farm/FarmOrderExcl1.action"/> "
              class="easyui-linkbutton v-align-middle" data-options="iconCls:'icon-edit',plain:true">导出 <span style="color:#e80a0a;">未发货</span> 订单列表&nbsp;</a>
          <a href="<c:url value="/web/farm/FarmOrderExcl.action"/> "
          class="easyui-linkbutton v-align-middle" data-options="iconCls:'icon-edit',plain:true">导出 全部 订单列表&nbsp;</a>
        </div>
        <div style="float:right;"></div>
        <div style="clear:both;"></div>
    </div>
    <table id="dgRecord"></table>
</div>

</body>
</html>
