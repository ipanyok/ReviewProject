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

<div class="container-fluid text-center" style="color: white; margin-top: 20px;">
    <form method="post" action="/addtitle" enctype="multipart/form-data">
        <label>Category</label>
        <input type="text" name="categories" list="categories" style="color: black" value="${currentCategory}" onchange="window.location='/addtitle/category/' + this.value;"/>
        <datalist id="categories" style="color: black">
            <c:forEach items="${categories}" var="category">
                <option value="${category.name}"/>
            </c:forEach>
        </datalist>

        <label>Subcategory</label>
        <input type="text" name="subCategories" list="subCategories" style="color: black"/>
        <datalist id="subCategories" style="color: black">
            <c:forEach items="${subCategories}" var="subCategory">
                <option value="${subCategory.name}"/>
            </c:forEach>
        </datalist>

        <label>Title</label>
        <input type="text" name="title" style="color: black"/>

        <label>Title City</label>
        <input type="text" name="cities" list="cities" style="color: black"/>
        <datalist id="cities">
            <c:forEach items="${cities}" var="city">
                <option value="${city}"/>
            </c:forEach>
        </datalist>

        <label>Title Description</label>
        <input type="text" name="titleDescription" style="color: black"/>

        <br><br>
        <input name="file" type="file" value="Download Photo"/>

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