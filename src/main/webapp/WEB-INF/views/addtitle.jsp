<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Admin Page</title>

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

<div class="container-fluid text-center">
    <form method="post" action="/addtitle">
        <label>Category</label>
        <input type="text" name="categories" list="categories"/>
        <datalist id="categories">
            <c:forEach items="${categories}" var="category">
                <option value="${category.name}"/>
            </c:forEach>
        </datalist>

        <label>Subcategory</label>
        <input type="text" name="subCategories" list="subCategories"/>
        <datalist id="subCategories">
            <c:forEach items="${subCategories}" var="subCategory">
                <option value="${subCategory.name}"/>
            </c:forEach>
        </datalist>

        <label>Title</label>
        <input type="text" name="title"/>

        <label>Title City</label>
        <input type="text" name="cities" list="cities"/>
        <datalist id="cities">
            <c:forEach items="${cities}" var="city">
                <option value="${city}"/>
            </c:forEach>
        </datalist>

        <label>Title Description</label>
        <input type="text" name="titleDescription"/>


        <input style="color: black" type="submit" name="ADD" value="ADD"/>
    </form>
</div>

<c:if test="${titleerror != null}">
    <div class="container text-center" style="color: red">
        <spring:message code="title.null"/>
    </div>
</c:if>
<c:if test="${categoryerror != null}">
    <div class="container text-center" style="color: red">
        <spring:message code="title.nullcategory"/>
    </div>
</c:if>
<c:if test="${success != null}">
    <div class="container text-center" style="color: green">
        <spring:message code="title.success"/>
    </div>
</c:if>


</body>
</html>