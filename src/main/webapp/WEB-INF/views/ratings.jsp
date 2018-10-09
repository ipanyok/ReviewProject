<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Ratings</title>
</head>
<body>

<jsp:include page="menu.jsp"/>

<div class="container" style="color: white">
    BAD RATING:<br><br>
    <div class="row">
        <c:forEach items="${ratings.get(0)}" var="rating">
            <p><b>${rating.getKey()}: ${rating.getValue()}</b></p><br>
        </c:forEach>
    </div>

</div>

<br><br><br><br><br><br><br>
<div class="container" style="color: white">
    GOOD RATING:<br><br>
    <div class="row">
        <c:forEach items="${ratings.get(1)}" var="rating">
            <p><b>${rating.getKey()}: ${rating.getValue()}</b></p><br>
        </c:forEach>
    </div>

</div>

</body>
</html>
