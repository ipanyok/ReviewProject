<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Register</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <link rel="stylesheet" href="/resources/css/stylebootstrap.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
</head>
<body>

<jsp:include page="menu.jsp"/>

<div class="container">
    <sf:form method="post" action="/register" modelAttribute="user">
        <div>
            <sf:label path="firstName">First Name</sf:label>
            <sf:input path="firstName"/>
            <sf:errors path="firstName" cssStyle="color: red"/>
        </div>
        <div>
            <sf:label path="lastName">Last Name</sf:label>
            <sf:input path="lastName"/>
            <sf:errors path="lastName" cssStyle="color: red"/>
        </div>
        <div>
            <sf:label path="login">Login</sf:label>
            <sf:input path="login"/>
            <sf:errors path="login" cssStyle="color: red"/>
        </div>
        <div>
            <sf:label path="email">Email</sf:label>
            <sf:input path="email"/>
            <sf:errors path="email" cssStyle="color: red"/>
        </div>
        <div>
            <sf:label path="password">Password</sf:label>
            <sf:password path="password"/>
        </div>
        <div>
            <label>Confirm Password</label>
            <input type="password" name="confirmPassword"/>
            <sf:errors path="password" cssStyle="color: red"/>
        </div>
        <div>
            <label>City</label>
            <sf:input path="city" list="cities"/>
            <datalist id="cities">
                <c:forEach items="${cities}" var="city">
                    <option value="${city}"/>
                </c:forEach>
            </datalist>
            <sf:errors path="city" cssStyle="color: red"/>
        </div>
        <input type="submit" name="ADD" value="ADD"/>
    </sf:form>
</div>

</body>
</html>
