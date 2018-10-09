<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Add Review</title>
</head>
<body>

<jsp:include page="menu.jsp"/>

<c:if test="${userMessageList.size() != 0}">
    <div class="container text-center" style="color: white">
        <c:forEach items="${userMessageList}" var="userMessage">
            <h3>${userMessage.adminMessage}</h3>
            <c:if test="${userMessage.read == false}">
                <form action="/read" method="post">
                    <input type="hidden" name="id" value="${userMessage.id}">
                    <button name="read" value="read${userMessage.id}" style="color: black">READ</button>
                </form>
            </c:if>
        </c:forEach>
    </div>
</c:if>

</body>
</html>