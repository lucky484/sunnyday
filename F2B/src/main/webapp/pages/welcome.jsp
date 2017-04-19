<%--
  Created by IntelliJ IDEA.
  User: val
  Date: 14-3-4
  Time: 下午4:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>

<body style="background:#F9F9F7;">
欢迎您，${loginAgent.loginName}！<a href="<c:url value="/auth/logout.action"/> " target="_parent">退出</a>
<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>

</body>
</html>