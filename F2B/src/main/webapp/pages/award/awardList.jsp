<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/24
  Time: 13:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>周期列表</title>
    <jsp:include page="/include/common.jsp" />
    <script type="text/javascript" src="<c:url value="/resources/lib/jquery/plugin/jquery.form.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/verify.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/lib/My97DatePicker/WdatePicker.js" />"></script>
    <script type="text/javascript">

        $(document).ready(function(){
            $('#dgAward').datagrid({
                url:'<c:url value="/web/award/getAwardListJSON.json" />',
                title:'周期列表',
                singleSelect:true,
                fit:true,
                fitColumns:true,
                toolbar:'#tbAward',
                rownumbers:true,
                pagination:true,
                pageSize: 20,
                columns:[[
                    {field:'awardId',title:'编号',align:'center'},
                    {field:'awards',title:'抽中奖品',width:160,align:'center'},
                    {field:'number',title:'奖品的数量',width:160,align:'center'},
                    {field:'createDate',title:'创建时间（注册时间）',width:80,align:'center'},
                    {field:'updateDate',title:'更新时间',width:80,align:'center'},
                    {field:'edit',title:'Edit',align:'center',formatter:function(val,row){
                        if(row.awardId < 0) { return ''; }
                        var editUrl = "<c:url value="/web/award/getAwardEditPage.action?awardId=" />" + row.awardId;
                        return '&nbsp;' +
                                '<a href="javascript:void(0)" onclick="openWindow(\'#wAward\', \'' + editUrl + '\')">编辑</a>' +
                                '&nbsp;';
                    }}
                ]]
            });

            formatPagination("dgAward");

            vEasyUIUtil.createWindow("wAward", {
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

        function submitRemove(awardId, isFakeDelete){
            if (!confirm("确认删除")){
                return;
            }
            // isFakeDelete=1表示假删除
            $.post("logicRemoveAward.action", {awardId: awardId, isFakeDelete: isFakeDelete},
                    function(result){
                        if(result == 1){
                            $('#dgAward').datagrid('reload');
                        }
                    }
            );
        }

        function submitSearch(){
            var dgAward = $('#dgAward');

            dgAward.datagrid('options').pageNumber = 1;
            dgAward.datagrid('getPager').pagination("options").pageNumber = 1;
            var param = {
            };
            $("#dgAward").datagrid({queryParams: param}, 'reload');
        }

        function clearSearch(){
        }
    </script>
</head>
<body class="easyui-layout">

<div data-options="region:'center',border:false" class="mainPanel">
    <div id="tbAward" class="toolBarPadding">
        <div style="float:left;">
            <%--<a href="<c:url value="/web/cycle/exportExcelFile.action"/> "--%>
            <%--class="easyui-linkbutton v-align-middle" data-options="iconCls:'icon-edit',plain:true">导出EXCEL&nbsp;</a>--%>
        </div>
        <div style="float:right;"></div>
        <div style="clear:both;"></div>
    </div>
    <table id="dgAward"></table>
</div>

</body>
</html>
