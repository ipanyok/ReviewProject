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

<div class="container" style="color: white">
    <sf:form action="/titles/${idTitle}/addreview" method="post" modelAttribute="review">
        <div>
            <sf:label path="reviewName">Review Name</sf:label>
            <sf:input path="reviewName" cssStyle="color: black"/>
            <sf:errors path="reviewName" cssStyle="color: red"/>
        </div>
        <div>
            <sf:label path="text">Text</sf:label>
            <sf:textarea path="text" cssStyle="color: black"/>
            <sf:errors path="text" cssStyle="color: red"/>
        </div>
        <div>
            <sf:label path="mark">Mark</sf:label>
            <sf:input path="mark" cssStyle="color: black"/>
            <sf:errors path="mark" cssStyle="color: red"/>
        </div>
        <input style="color: black" type="submit" name="addreview" value="COMMENT">
    </sf:form>
</div>


</body>
</html>