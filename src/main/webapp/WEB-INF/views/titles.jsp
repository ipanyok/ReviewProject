<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Titles</title>
</head>
<body>

<jsp:include page="menu.jsp"/>


<security:authorize url="/**/addreviewtonewtitle">
<div class="container text-right">
        <%--<a href=${requestScope['javax.servlet.forward.request_uri']}/addreview>ADD REVIEW</a>--%>
            <div class="card-body">
                <a href="${requestScope['javax.servlet.forward.request_uri']}/addreviewtonewtitle" class="btn btn-success">Add new title</a>
            </div>
</div>
</security:authorize>

<div class="container" style="color: white">

    <c:if test="${titles.size() == 0}">
        <label>NO TITLES</label>
    </c:if>

    <c:if test="${titles.size() != 0}">
        <h1><p class="text-center"><b>${titles.get(0).category} -> ${titles.get(0).subCategory}</b></p></h1>
        <br><br><br>
        <div class="row">
            <c:forEach items="${titles}" var="title">
                <div class="col-md-2">
                    <p><b>${title.title} (${title.city})</b></p>
                    <p>${title.description}</p>
                    <p><a href="/titles/${title.idTitle}" style="text-decoration: none;color:black"><spring:message code="show_reviews"/></a></p>
                </div>
            </c:forEach>
        </div>
    </c:if>


    <!-- Pagination -->

    <c:if test="${countPages.size() != 1}">
        <c:forEach items="${countPages}" var="elem">
            <a href="/title/${idSubCategory}/page/${elem.number}" class="text-center">${elem.number}</a>
        </c:forEach>
    </c:if>

    <c:if test="${userMessage != null}">
        <div class="container text-center">
            <p><h3 style="color: green">${userMessage}</h3></p><br><br>
        </div>
    </c:if>

</div>

</body>
</html>
