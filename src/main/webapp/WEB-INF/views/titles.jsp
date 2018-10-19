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

    <%--<c:if test="${titles.size() != 0}">--%>
        <%--<h1><p class="text-center"><b>${titles.get(0).category} -> ${titles.get(0).subCategory}</b></p></h1>--%>
        <%--<br><br><br>--%>
        <%--<div class="row">--%>
            <%--<c:forEach items="${titles}" var="title">--%>
                <%--<div class="col-md-2">--%>
                    <%--<p><b>${title.title} (${title.city})</b></p>--%>
                    <%--<p>${title.description}</p>--%>
                    <%--<p><a href="/titles/${title.idTitle}" style="text-decoration: none;color:black"><spring:message code="show_reviews"/></a></p>--%>
                <%--</div>--%>
            <%--</c:forEach>--%>
        <%--</div>--%>
    <%--</c:if>--%>

    <c:if test="${titles.size() != 0}">
        <h1><p class="text-left" style="margin-top: 20px;"><b>${titles.get(0).category}, &nbsp;${titles.get(0).subCategory}</b></p></h1>
        <br>
        <div class="card-deck">
            <c:forEach items="${titles}" var="title">
                <div class="card border-primary" style="background-color: #3a3a3a">
                    <img class="card-img-top" src="/getphoto/${title.idTitle}" alt="NO IMAGE" style="position: relative">
                    <div class="card-body" style="background-color: #3a3a3a">
                        <h5 class="card-title" style="font-family: 'Segoe Print'">${title.title},&nbsp; <small>${title.city}</small></h5>
                        <p class="card-text" style="font-family: 'Segoe Print'">${title.description}</p>
                        <p><a href="/titles/${title.idTitle}" class="btn btn-primary" role="button"><spring:message code="show_reviews"/></a></p>
                    </div>
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
        <%--<div class="container text-center">--%>
            <%--<p><h3 style="color: green">${userMessage}</h3></p><br><br>--%>
        <%--</div>--%>
        <div class="alert alert-success" role="alert">
            <h4 class="alert-heading">${userMessage}</h4>
            <p>Aww yeah, you successfully read this important alert message. This example text is going to run a bit longer so that you can see how spacing within an alert works with this kind of content.</p>
        </div>
    </c:if>

</div>

</body>
</html>
