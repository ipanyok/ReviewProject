<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Admin Page</title>
</head>
<body>

<jsp:include page="menu.jsp"/>

<div class="container text-center" style="margin-top: 20px; color: white">
    <form method="post" action="/addcategories">
        <label>Category</label>
        <input type="text" name="categories" list="categories" style="color: black"/>
        <datalist id="categories">
            <c:forEach items="${categories}" var="category">
                <option value="${category.name}"/>
            </c:forEach>
        </datalist>

        <label>Subcategory</label>
        <input type="text" name="subCategories" style="color: black"/>

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