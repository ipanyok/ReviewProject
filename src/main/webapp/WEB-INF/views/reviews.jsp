<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Reviews</title>
</head>
<body>

<jsp:include page="menu.jsp"/>

<div class="container" style="color: white; margin-top: 15px;">
    <p class="text-center"><b>${title.title} (${city.name})</b></p>

    <c:if test="${reviews.size() == 0}">
        <label>NO REVIEWS</label>
    </c:if>

    <c:if test="${reviews.size() != 0}">
        <div class="row">
            <c:forEach items="${reviews}" var="review">
                <div class="col-md">
                    <p><b>${review.reviewName} (mark: ${review.mark})</b></p>
                    <p>${review.text}</p>
                    <p>${review.userLogin}</p>
                    <p>${review.date}</p>
                </div>
            </c:forEach>
        </div>
    </c:if>
</div>


<c:if test="${message != null}">
    <div class="container">
        <p style="color: red">${message}</p>
    </div>
</c:if>

<security:authorize url="/**/addreview">
    <div class="container">
            <%--<a href=${requestScope['javax.servlet.forward.request_uri']}/addreview>ADD REVIEW</a>--%>
        <a href="/titles/${title.id}/addreview">ADD REVIEW</a>
    </div>
</security:authorize>

</body>
</html>
