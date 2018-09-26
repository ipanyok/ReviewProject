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

<div class="container text-center">
    <form method="post" action="/addcategories">
        <label>Category</label>
        <input type="text" name="categories" list="categories"/>
        <datalist id="categories">
            <c:forEach items="${categories}" var="category">
                <option value="${category.name}"/>
            </c:forEach>
        </datalist>

        <label>Subcategory</label>
        <input type="text" name="subCategories"/>

        <input style="color: black" type="submit" name="ADD" value="ADD"/>
    </form>
</div>

<c:if test="${error != null}">
    <div class="container text-center" style="color: red">
        <spring:message code="error.nullcategories"/>
    </div>
</c:if>
<c:if test="${errorexist != null}">
    <div class="container text-center" style="color: red">
        <spring:message code="error.categoryexist"/>
    </div>
</c:if>
<c:if test="${success != null}">
    <div class="container text-center" style="color: green">
        <spring:message code="category.success"/>
    </div>
</c:if>
<c:if test="${nocategory != null}">
    <div class="container text-center" style="color: red">
        <spring:message code="category.null"/>
    </div>
</c:if>
<c:if test="${subcategoryexist != null}">
    <div class="container text-center" style="color: red">
        <spring:message code="subcategory.exist"/>
    </div>
</c:if>

</body>
</html>