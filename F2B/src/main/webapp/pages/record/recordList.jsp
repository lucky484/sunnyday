<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/24
  Time: 11:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>中奖纪录</title>
    <jsp:include page="/include/common.jsp" />
    <script type="text/javascript" src="<c:url value="/resources/lib/jquery/plugin/jquery.form.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/verify.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/lib/My97DatePicker/WdatePicker.js" />"></script>
    <script type="text/javascript">

        $(document).ready(function(){
            $('#dgRecord').datagrid({
                url:'<c:url value="/web/record/getRecordListJSON.json" />',
                title:'中奖纪录&nbsp;|&nbsp;共发出奖金<span style="color:red;">${totalReward}</span>元。',
                singleSelect:true,
                fit:true,
                fitColumns:true,
                toolbar:'#tbRecord',
                striped:true,
                rownumbers:true,
                pagination:true,
                pageSize: 20,
                columns:[[
                    {field:'recordId',title:'编号',align:'center'},
                    {field:'lotteryDate',title:'中奖时间',width:100,align:'center'},
                    {field:'orderNo',title:'抽奖订单',width:100,align:'center'},
                    {field:'award',title:'奖品',width:100,align:'center',formatter:function(val,row){
                    	return val + "元";
                    }},
                    {field:'nickname',title:'微信昵称',width:80,align:'center'},
                    {field:'openId',title:'中奖人的openId',width:180,align:'center'},
                    {field:'ip',title:'中奖人的IP地址',width:100,align:'center'},
                    {field:'drawStatus',title:'是否发放奖品',width:100,align:'center',formatter:function(val,row){
                    	if (val == 0) {
                    		return "N";
                    	} else {
                    		return "Y";
                    	}
                    }},
                    {field:'edit',title:'操作',align:'center',formatter:function(val,row) {
                        if(row.recordId < 0) { return ''; }
                        if (row.drawStatus == 0) {
                        return '&nbsp;' +
                                '<a href="javascript:void(0)" onclick="confirm(\'' + row.openId + '\',' + row.award + ',' + row.recordId + ')">发放奖品</a>' +
                                '&nbsp;';
                        } else {
                        	return "-";
                        }
                    }} 
                ]],
               /*  onLoadSuccess:function(data) {
                	setTimeout(function() {
                		$('#dgRecord').datagrid('reload');
                	}, 5000);
                }, */
            });

            formatPagination("dgRecord");
        });

        function confirm(openId, award, recordId) {
        	$.messager.confirm('确认发放', '确认无误发放奖品吗?', function(r){
                if (r){
                    $.post('<c:url value="/web/record/sendRedPack.action" />',{
                    	openId : openId,
                    	award : award,
                    	recordId : recordId
                    },function(data) {
	                   	$('#dgRecord').datagrid('reload');
                          $.messager.show({
                              title:'提示',
                              msg:'奖品已发放',
                              showType:'show'
                          });
                    });
                }
            });
        }
    </script>
</head>
<body class="easyui-layout">
<div data-options="region:'center',border:false" class="mainPanel">
    <div id="tbRecord" class="toolBarPadding">
        <div style="float:left;">
        </div>
        <div style="float:right;"></div>
        <div style="clear:both;"></div>
    </div>
    <table id="dgRecord"></table>
</div>
</body>
</html>
