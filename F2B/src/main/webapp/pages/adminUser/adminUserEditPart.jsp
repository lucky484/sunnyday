<%--
  User: val.jzp
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<form id="myFormAdminUser" name="" action="<c:url value="/web/admin/adminUser/executeAdminUserEdit.action" />" method="post">
    <table class="table-container">
        <tr>
            <td class="item-name">&nbsp;</td>
            <td class="item-value">
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitEdit()">提交</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWindow('#wAdminUser')">取消</a>
            </td>
        </tr>
        <c:if test="${adminUser != null}">
        <tr>
            <td class="item-name">编号：</td>
            <td class="item-value">
                <p>${adminUser.adminUserId}</p>
                <input type="hidden" name="adminUserId" value="${adminUser.adminUserId}" />
            </td>
        </tr>
        </c:if>
        <tr>
            <td class="item-name"><label for="f_loginName">用户名：</label></td>
            <td class="item-value">
                <input type="text" id="f_loginName" name="loginName" value="${adminUser.loginName}" />
            </td>
        </tr>
        <tr>
            <td class="item-name"><label for="f_loginPassword">密码：</label></td>
            <td class="item-value">
                <input type="text" id="f_loginPassword" name="loginPassword" value="${adminUser.loginPassword}" />
            </td>
        </tr>
        <tr>
            <td class="item-name"><label for="f_realName">姓名：</label></td>
            <td class="item-value">
                <input type="text" id="f_realName" name="realName" value="${adminUser.realName}" />
            </td>
        </tr>
        <tr>
            <td class="item-name"><label for="f_email">邮箱：</label></td>
            <td class="item-value">
                <input type="text" id="f_email" name="email" value="${adminUser.email}" />
            </td>
        </tr>
    </table>
</form>

<script type="text/javascript">
    function submitEdit(){
        var flag = true;
        $(".validateNeed").each(function(){
            if(!$(this).validatebox("isValid")){
                flag = false;
            }
        });

        if(!flag){
            return false;
        }

        $("#wAdminUser").window('close');
        $("#myFormAdminUser").ajaxSubmit({
            success : function(html, status) {
                $('#dgAdminUser').datagrid('reload');
            }
        });
    }
</script>