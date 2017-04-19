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
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWindow('#wAdminUser')">关闭</a>
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
            <td class="item-name">用户名：</td>
            <td class="item-value">
                ${adminUser.loginName}
            </td>
        </tr>
        <tr>
            <td class="item-name">密码：</td>
            <td class="item-value">
                ${adminUser.loginPassword}
            </td>
        </tr>
        <tr>
            <td class="item-name">姓名：</td>
            <td class="item-value">
                ${adminUser.realName}
            </td>
        </tr>
        <tr>
            <td class="item-name">邮箱：</td>
            <td class="item-value">
                ${adminUser.email}
            </td>
        </tr>
        <tr>
            <td class="item-name">创建日期：</td>
            <td class="item-value">
                <fmt:formatDate value="${adminUser.createDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
            </td>
        </tr>
        <tr>
            <td class="item-name">更新日期：</td>
            <td class="item-value">
                <fmt:formatDate value="${adminUser.updateDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
            </td>
        </tr>
    </table>
</form>

<%-- <fmt:formatDate value="${record.createDate}" pattern="yyyy-MM-dd" /> --%>