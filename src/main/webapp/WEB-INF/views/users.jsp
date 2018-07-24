<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<%--<c:if test="$(users != null)" >--%>
<table border="1">
    <thead>
    <tr>
        <th>Id</th>
        <th>Login</th>
        <th>EMail</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="user">
        <tr>
            <td><a href="/user/${user.id}"><c:out value="${user.id}"/></a></td>
            <td><a href="/user?login=${user.login}"><c:out value="${user.login}"/></a></td>
            <td><c:out value="${user.email}"/></td>
            <td><a href="/users/${user.id}"/>delete</td>
            <td><a href="/update/${user.id}"/>update</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%--</c:if>--%>
<br>
<a href="/home">HOME</a>
</body>
</html>
