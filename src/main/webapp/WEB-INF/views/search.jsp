<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Search View</title>
</head>
<body>

<jsp:include page="menu.jsp"/>

<div class="container" style="color: white; margin-top: 15px;">

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
