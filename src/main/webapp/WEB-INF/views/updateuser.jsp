<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add USer</title>
</head>
<body>
<form method="post" action="/update/${user.id}">
    First Name
    <input type="text" name="firstName" value="${user.firstName}">
    <br>
    Last Name
    <input type="text" name="lastName" value="${user.lastName}">
    <br>
    Login
    <input type="text" name="login" value="${user.login}">
    <br>
    EMail
    <input type="text" name="email" value="${user.email}">
    <br>
    Password
    <input type="text" name="password" value="${user.password}">
    <br>
    City
    <input type="text" name="city" value="${user.city}">
    <br>
    <input type="submit" name="UPDATE" value="UPDATE"/>
</form>
</body>
</html>
