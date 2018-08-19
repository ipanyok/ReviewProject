<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Reviews</title>

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
