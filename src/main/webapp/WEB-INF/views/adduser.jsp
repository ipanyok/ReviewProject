<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add USer</title>
</head>
<body>
<form method="post" action="/adduser">
    First Name
    <input type="text" name="firstName">
    <br>
    Last Name
    <input type="text" name="lastName">
    <br>
    Login
    <input type="text" name="login">
    <br>
    EMail
    <input type="text" name="email">
    <br>
    Password
    <input type="text" name="password">
    <br>
    City
    <input type="text" name="city">
    <br>
    <input type="submit" name="ADD" value="ADD"/>
</form>
</body>
</html>
