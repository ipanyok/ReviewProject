<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Add Review</title>
<style>

    a, a:hover, a:focus{outline:none; text-decoration:none;}

    body{
        font-family: 'Open Sans', sans-serif;
    }

    h2{float:left; width:100%; color:#fff; margin-bottom:30px; font-size: 14px;}
    h2 span{font-family: 'Libre Baskerville', serif; display:block; font-size:45px; text-transform:none; margin-bottom:20px; margin-top:30px; font-weight:700}
    h2 a{color:#fff; font-weight:bold;}


    section{

        float:left;
        width:100%;
        padding:30px 0;
    }

    .card {
        -moz-box-direction: normal;
        -moz-box-orient: vertical;
        background-color: #fff;
        border-radius: 0.25rem;
        display: flex;
        flex-direction: column;
        position: relative;
        margin-bottom:1px;
        border:none;
    }
    .card-header:first-child {
        border-radius: 0;
    }
    .card-header {
        background-color: #f7f7f9;
        margin-bottom: 0;
        padding: 20px 1.25rem;
        border:none;

    }
    .card-header a i{
        float:left;
        font-size:25px;
        padding:5px 0;
        margin:0 25px 0 0px;
        color:#195C9D;
    }
    .card-header i{
        float:right;
        font-size:30px;
        width:1%;
        margin-top:8px;
        margin-right:10px;
    }
    .card-header a{
        width:97%;
        float:left;
        color:#565656;
    }
    .card-header p{
        margin:0;
    }

    .card-header h3{
        margin:0 0 0px;
        font-size:20px;
        font-family: 'Slabo 27px', serif;
        color: black;
    }
    .card-block {
        -moz-box-flex: 1;
        flex: 1 1 auto;
        padding: 20px;
        color:#232323;
        box-shadow:inset 0px 4px 5px rgba(0,0,0,0.1);
        border-top:1px soild #000;
        border-radius:0;
    }
</style>
</head>
<body>

<jsp:include page="menu.jsp"/>

<c:if test="${userMessageList.size() != 0}">
    <%--<div class="container text-center" style="color: white">--%>
        <%--<c:forEach items="${userMessageList}" var="userMessage">--%>
            <%--<h3>${userMessage.adminMessage}</h3>--%>
            <%--<c:if test="${userMessage.read == false}">--%>
                <%--<form action="/read" method="post">--%>
                    <%--<input type="hidden" name="id" value="${userMessage.id}">--%>
                    <%--<button name="read" value="read${userMessage.id}" style="color: black">READ</button>--%>
                <%--</form>--%>
            <%--</c:if>--%>
        <%--</c:forEach>--%>
    <%--</div>--%>



<section>
    <div class="container">
        <div class="row">
            <c:forEach items="${userMessageList}" var="userMessage">
                <input type="hidden" name="id" value="${userMessage.id}">
                <div class="col-md-8 offset-md-2">
                <div class="bd-example" data-example-id="">
                    <div id="accordion" role="tablist" aria-multiselectable="true">
                        <div class="card">
                            <div class="card-header" role="tab" id="heading${userMessage.id}" style="background-color: wheat">
                                <div class="mb-0">
                                    <a href="#" style="text-decoration: none">
                                        <c:if test="${userMessage.read == false}">
                                            <i class="fa fa-envelope-o" aria-hidden="true" style="font-weight:bold;"></i>
                                            <h3 style="font-weight:bold;" data-toggle="modal" data-target="#messageModal${userMessage.id}">Read message</h3>
                                        </c:if>
                                        <c:if test="${userMessage.read == true}">
                                            <i class="fa fa-envelope-o" aria-hidden="true" style="font-weight:normal;"></i>
                                            <h3 style="font-weight:normal;" data-toggle="modal" data-target="#messageModal${userMessage.id}">Read message</h3>
                                        </c:if>
                                    </a>
                                    <!-- Modal -->
                                    <div class="modal fade" id="messageModal${userMessage.id}" tabindex="-1" role="dialog" aria-labelledby="feedbackModalLabel" aria-hidden="true">
                                        <div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                                <c:if test="${userMessage.adminMessage.contains('cancel')}">
                                                <div class="modal-header bg-danger text-white">
                                                </c:if>
                                                <c:if test="${!userMessage.adminMessage.contains('cancel')}">
                                                <div class="modal-header bg-success text-white">
                                                </c:if>
                                                    <h5 class="modal-title" id="feedbackModalLabel">Ваше повідомлення</h5>
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">×</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body">
                                                    <form action="/read" method="post">
                                                        <input type="hidden" name="id" value="${userMessage.id}">
                                                        <div class="form-row">
                                                            <div class="form-group col-md-12">
                                                                <textarea id="comment" name="comment" cols="40" rows="5" class="form-control">${userMessage.adminMessage}</textarea>
                                                            </div>
                                                        </div>
                                                        <div class="form-row">
                                                            <c:if test="${!userMessage.adminMessage.contains('cancel')}">
                                                                <button class="btn btn-success" name="read" value="read${userMessage.id}">OK</button>
                                                            </c:if>
                                                            <c:if test="${userMessage.adminMessage.contains('cancel')}">
                                                                <button class="btn btn-danger" name="read" value="read${userMessage.id}">OK</button>
                                                            </c:if>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Modal -->
                                </div>
                            </div>
                        </div>
                    </div>
                    <br>
                </div>
            </div>
            </c:forEach>
        </div>
    </div>

</c:if>

</body>
</html>