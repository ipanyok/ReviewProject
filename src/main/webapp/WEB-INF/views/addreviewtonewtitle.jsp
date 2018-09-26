<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Add Review To New Title</title>

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
    <form action="/titles/subcat/${idSubCat}" method="post">
        <p class="text-center">
        <h3>Title Params</h3></p><br>
        <div>
            <label name="titleName">Title Name*</label>
            <input type="text" name="titleName"/>
            <%--<sf:errors path="titleName" cssStyle="color: red"/>--%>
        </div>
        <div>
            <label name="titleDescription">Title Description</label>
            <input type="textarea" name="titleDescription"/>
            <%--<sf:errors path="titleDescription" cssStyle="color: red"/>--%>
        </div>
        <div>
            <label name="titleCity">Title City*</label>
            <input type="text" name="titleCity" list="cities"/>
            <datalist id="cities">
                <c:forEach items="${cities}" var="city">
                    <option value="${city}"/>
                </c:forEach>
            </datalist>
        </div>
        <p class="text-center">
        <h3>Review Params</h3></p><br>
        <div>
            <label name="reviewName">Review Name*</label>
            <input type="text" name="reviewName"/>
            <%--<sf:errors path="reviewName" cssStyle="color: red"/>--%>
        </div>
        <div>
            <label name="text">Text*</label>
            <input type="textarea" name="text"/>
            <%--<sf:errors path="text" cssStyle="color: red"/>--%>
        </div>
        <div>
            <label name="mark">Mark*</label>
            <input type="text" name="mark"/>
            <%--<sf:errors path="mark" cssStyle="color: red"/>--%>
        </div>
        <input style="color: black" type="submit" name="addreviewtonewtitle" value="COMMENT">
    </form>
</div>

<c:if test="${errors != null}">
    <div class="container" style="color: red">
        <spring:message code="review.empty"/>
    </div>
</c:if>


</body>
</html>