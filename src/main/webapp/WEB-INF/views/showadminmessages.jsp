<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Add Review</title>

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

<c:if test="${adminBufferList.size() != 0}">
    <div class="container text-center">
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
                        <select name="categoryName">
                            <option>${adminBuffer.adminBuffer.categoryName}</option>
                            <c:forEach items="${categoriesList}" var="category">
                                <option>${category.name}</option>
                            </c:forEach>
                        </select> ->
                        <select name="subCategoryName">
                            <option>${adminBuffer.adminBuffer.subCategoryName}</option>
                            <c:forEach items="${subCategoriesList}" var="subCategory">
                                <option>${subCategory.name}</option>
                            </c:forEach>
                        </select>
                        -> ${adminBuffer.adminBuffer.titleName} (${adminBuffer.adminBuffer.titleCity})
                        -> ${adminBuffer.adminBuffer.titleDescription}
                        -> ${adminBuffer.adminBuffer.reviewName}
                        -> ${adminBuffer.adminBuffer.reviewText}
                        -> ${adminBuffer.adminBuffer.mark}

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
                    <button name="add" value="add${adminBuffer.adminBuffer.id}">Add</button>
                    <button name="cancel" value="cancel${adminBuffer.adminBuffer.id}">Cancel</button>
                </c:if>
                </p>
            </form>
        </c:forEach>

    </div>
</c:if>

</body>
</html>