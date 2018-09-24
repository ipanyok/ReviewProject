<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Search View</title>

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

    <c:if test="${searchResult.size() == 0}">
        <label>NO TITLES</label>
    </c:if>

    <c:if test="${searchResult.size() != 0}">
        <div class="row">
            <c:forEach items="${searchResult}" var="title">
                <div class="col-md-2">
                    <p><b>${title.title}</b></p>
                    <p>${title.description}</p>
                    <p><a href="/titles/${title.id}" style="text-decoration: none;color:black"><spring:message code="show_reviews"/></a></p>
                </div>
            </c:forEach>
        </div>
    </c:if>

    <!-- Pagination -->

    <c:if test="${countPagesSearch.size() != 1}">
        <c:forEach items="${countPagesSearch}" var="elem">
            <a href="/search/page/${elem.number}" class="text-center">${elem.number}</a>
        </c:forEach>
    </c:if>
</div>

</body>
</html>
