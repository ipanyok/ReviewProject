<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Add All</title>
</head>
<body>

<jsp:include page="menu.jsp"/>

<div class="container" style="color: white">
    <form action="/addallnew" method="post" enctype="multipart/form-data">
        <p class="text-center">
        <h3>Category Params</h3></p><br>
        <div>
            <label name="categoryName">Category Name*</label>
            <input type="text" name="categoryName" style="color: black"/>
        </div>
        <div>
            <label name="subCategoryName">Subcategory Name*</label>
            <input type="text" name="subCategoryName" style="color: black"/>
        </div>
        <h3>Title Params</h3></p><br>
        <div>
            <label name="titleName">Title Name*</label>
            <input type="text" name="titleName" style="color: black"/>
        </div>
        <div>
            <label name="titleDescription">Title Description</label>
            <input type="textarea" name="titleDescription" style="color: black"/>
        </div>
        <div>
            <label name="titleCity">Title City*</label>
            <input type="text" name="titleCity" list="cities" style="color: black"/>
            <datalist id="cities">
                <c:forEach items="${cities}" var="city">
                    <option value="${city}"/>
                </c:forEach>
            </datalist>
        </div>
        <div>
            <input name="file" type="file" value="Download Photo"/>
        </div>
        <p class="text-center">
        <h3>Review Params</h3></p><br>
        <div>
            <label name="reviewName">Review Name*</label>
            <input type="text" name="reviewName" style="color: black"/>
        </div>
        <div>
            <label name="text">Text*</label>
            <input type="textarea" name="text" style="color: black"/>
        </div>
        <div>
            <label name="mark">Mark*</label>
            <input type="text" name="mark" style="color: black"/>
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