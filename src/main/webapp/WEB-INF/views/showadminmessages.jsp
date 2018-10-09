<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Add Review</title>
</head>
<body>

<jsp:include page="menu.jsp"/>

<c:if test="${adminBufferList.size() != 0}">
    <div class="container text-center" style="color: white">
        <c:forEach items="${adminBufferList}" var="adminBuffer">
            <form action="/newcategory" method="post">
                <input type="hidden" name="idAdminBuffer" value="${adminBuffer.adminBuffer.id}">
                <input type="hidden" name="titleName" value="${adminBuffer.adminBuffer.titleName}">
                <input type="hidden" name="titleCity" value="${adminBuffer.adminBuffer.titleCity}">
                <input type="hidden" name="titleDescription" value="${adminBuffer.adminBuffer.titleDescription}">
                <input type="hidden" name="reviewName" value="${adminBuffer.adminBuffer.reviewName}">
                <input type="hidden" name="reviewText" value="${adminBuffer.adminBuffer.reviewText}">
                <input type="hidden" name="mark" value="${adminBuffer.adminBuffer.mark}">
                <input type="hidden" name="userName" value="${adminBuffer.adminBuffer.userName}">

                <p>
                    <h4>${adminBuffer.adminBuffer.userName}:
                        <select name="categoryName" style="color: black">
                            <option style="color: black">${adminBuffer.adminBuffer.categoryName}</option>
                            <c:forEach items="${categoriesList}" var="category">
                                <option style="color: black">${category.name}</option>
                            </c:forEach>
                        </select> ->
                        <select name="subCategoryName" style="color: black">
                            <option style="color: black">${adminBuffer.adminBuffer.subCategoryName}</option>
                            <c:forEach items="${subCategoriesList}" var="subCategory">
                                <option style="color: black">${subCategory.name}</option>
                            </c:forEach>
                        </select>
                        -> ${adminBuffer.adminBuffer.titleName} (${adminBuffer.adminBuffer.titleCity})
                        -> ${adminBuffer.adminBuffer.titleDescription}
                        -> ${adminBuffer.adminBuffer.reviewName}
                        -> ${adminBuffer.adminBuffer.reviewText}
                        -> ${adminBuffer.adminBuffer.mark}
                        -> <img class="card-img-top img-circle" src="/getphoto/admin/${adminBuffer.adminBuffer.id}" style="width: 15vw; height: 10vw; object-fit: cover;">

                        <c:if test="${adminBuffer.status == 'ADDED'}">
                            <p style="color: green">${adminBuffer.status}</p>
                        </c:if>
                        <c:if test="${adminBuffer.status == 'IN PROGRESS'}">
                            <p style="color: blue">${adminBuffer.status}</p>
                        </c:if>
                        <c:if test="${adminBuffer.status == 'CANCEL'}">
                            <p style="color: red">${adminBuffer.status}</p>
                        </c:if>

                </h3>
                <c:if test="${adminBuffer.status == 'IN PROGRESS'}">
                    <button name="add" value="add${adminBuffer.adminBuffer.id}" style="color: black">Add</button>
                    <button name="cancel" value="cancel${adminBuffer.adminBuffer.id}" style="color: black">Cancel</button>
                </c:if>
                </p>
            </form>
        </c:forEach>

    </div>
</c:if>

</body>
</html>