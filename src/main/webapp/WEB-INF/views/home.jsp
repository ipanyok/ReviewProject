<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Main</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <link rel="stylesheet" href="/resources/css/stylebootstrap.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    ﻿

    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
</head>
<style>
    [class*="sticker"] {
        position: relative;
        margin: 15px 0;
        padding: 33px 15px 15px;

        border-radius: 20px;
        box-shadow: 10px 10px 50px 15px rgba(0, 0, 0, .64)
    }

    [class*="sticker"]:after {
        content: attr(data-sticker);
        position: absolute;
        top: 0;
        padding: 3px 0px;
        font-size: 25px;
        font-weight: bold;
        /*background: #3b3b3b;*/
        color: white;
        border-radius: 5px;
    }

    .title {
        font-size: 14px;
        font-weight: 700;
        color: #e91e63;
        text-transform: capitalize;
        margin: 0 0 5px 0;
    }
</style>
<body>

<jsp:include page="menu.jsp"/>

<security:authorize access="isAuthenticated() and principal.username != 'Admin'">
    <div class="container text-right">
        <a href="/addallnew">ADD ALL NEW</a><br><br>
    </div>
</security:authorize>

<%--<div class="container">--%>
<%--<c:if test="${lastAddedReviews.size() == 0}">--%>
<%--<label>NO REVIEWS</label>--%>
<%--</c:if>--%>

<%--<c:if test="${lastAddedReviews.size() != 0}">--%>
<%--<div class="row">--%>
<%--<c:forEach items="${lastAddedReviews}" var="review">--%>
<%--<div class="col-md-4">--%>
<%--<p><b>${review.getValue()}</b></p>--%>
<%--<p>${review.getKey().reviewName} (mark: ${review.getKey().mark})</p>--%>
<%--<p>${review.getKey().text}</p>--%>
<%--<p>${review.getKey().date}</p>--%>
<%--</div>--%>
<%--</c:forEach>--%>
<%--</div>--%>
<%--</c:if>--%>
<%--</div>--%>

<%--<c:if test="${userMessage != null}">--%>
<%--<div class="container text-center">--%>
<%--<p><h3 style="color: green">${userMessage}</h3></p><br><br>--%>
<%--</div>--%>
<%--</c:if>--%>

<div class="container">
    <c:if test="${lastAddedReviews.size() == 0}">
        <label>NO REVIEWS</label>
    </c:if>

    <c:if test="${lastAddedReviews.size() != 0}">
        <div class="row">
            <c:forEach items="${lastAddedReviews}" var="review">
                <div class="col-md-4 col-sm-6">
                    <div class="sticker" data-sticker="${review.getValue()}">
                        <div class="serviceBox">
                            <div class="service-content">
                                <h3><label
                                        style="color: #111111; font-size: 20px;">${review.getKey().reviewName}</label>
                                </h3>
                                <p class="title">${review.getKey().mark}&#9734&#9734&#9734&#9734&#9734</p>
                                <c:if test="${review.getKey().text.length() > 170}">
                                    <p style="font-size: 14px">${review.getKey().text.substring(0, 170)}<a href="#">...read
                                        more...</a></p>
                                    <p style="font-size: 14px">${review.getKey().date}</p>
                                </c:if>
                                <c:if test="${review.getKey().text.length() <= 170}">
                                    <p style="font-size: 14px">${review.getKey().text}</p>
                                    <p style="font-size: 14px">${review.getKey().date}</p>
                                    <br><br><br>
                                </c:if>
                            </div>
                            <div>
                                <a href="#" class="btn btn-default">Read more</a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
</div>

<c:if test="${userMessage != null}">
    <div class="container text-center">
        <p>
        <h3 style="color: green">${userMessage}</h3></p><br><br>
    </div>
</c:if>

</body>
</html>
