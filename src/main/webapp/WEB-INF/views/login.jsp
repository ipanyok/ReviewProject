<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Login</title>

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
<style>
    /* Demo Background */
    body {
        background-color: #3a3a3a
    }

    /* Form Style */
    .form-horizontal {
        background: #fff;
        padding-bottom: 40px;
        border-radius: 15px;
        text-align: center;
    }

    .form-horizontal .heading {
        display: block;
        font-size: 35px;
        font-weight: 700;
        padding: 35px 0;
        border-bottom: 1px solid #f0f0f0;
        margin-bottom: 30px;
    }

    .form-horizontal .form-group {
        padding: 0 40px;
        margin: 0 0 25px 0;
        position: relative;
    }

    .form-horizontal .form-control {
        background: #f0f0f0;
        border: none;
        border-radius: 20px;
        box-shadow: none;
        padding: 0 20px 0 45px;
        height: 40px;
        transition: all 0.3s ease 0s;
    }

    .form-horizontal .form-control:focus {
        background: #e0e0e0;
        box-shadow: none;
        outline: 0 none;
    }

    .form-horizontal .form-group i {
        position: absolute;
        top: 12px;
        left: 60px;
        font-size: 17px;
        color: #c8c8c8;
        transition: all 0.5s ease 0s;
    }

    .form-horizontal .form-control:focus + i {
        color: #00b4ef;
    }

    .form-horizontal .fa-question-circle {
        display: inline-block;
        position: absolute;
        top: 12px;
        right: 60px;
        font-size: 20px;
        color: #808080;
        transition: all 0.5s ease 0s;
    }

    .form-horizontal .fa-question-circle:hover {
        color: #000;
    }

    .form-horizontal .main-checkbox {
        float: left;
        width: 20px;
        height: 20px;
        background: #11a3fc;
        border-radius: 50%;
        position: relative;
        margin: 5px 0 0 5px;
        border: 1px solid #11a3fc;
    }

    .form-horizontal .main-checkbox label {
        width: 20px;
        height: 20px;
        position: absolute;
        top: 0;
        left: 0;
        cursor: pointer;
    }

    .form-horizontal .main-checkbox label:after {
        content: "";
        width: 10px;
        height: 5px;
        position: absolute;
        top: 5px;
        left: 4px;
        border: 3px solid #fff;
        border-top: none;
        border-right: none;
        background: transparent;
        opacity: 0;
        -webkit-transform: rotate(-45deg);
        transform: rotate(-45deg);
    }

    .form-horizontal .main-checkbox input[type=checkbox] {
        visibility: hidden;
    }

    .form-horizontal .main-checkbox input[type=checkbox]:checked + label:after {
        opacity: 1;
    }

    .form-horizontal .text {
        float: left;
        margin-left: 7px;
        line-height: 20px;
        padding-top: 5px;
        text-transform: capitalize;
    }

    .form-horizontal .btn {
        float: right;
        font-size: 14px;
        color: #fff;
        background: #00b4ef;
        border-radius: 30px;
        padding: 10px 25px;
        border: none;
        text-transform: capitalize;
        transition: all 0.5s ease 0s;
    }

    @media only screen and (max-width: 479px) {
        .form-horizontal .form-group {
            padding: 0 25px;
        }

        .form-horizontal .form-group i {
            left: 45px;
        }

        .form-horizontal .btn {
            padding: 10px 20px;
        }
    }
</style>
<body>

<jsp:include page="menu.jsp"/>


<div class="container">
    <div class="row">

        <div class="col-md-offset-3 col-md-6">
            <form class="form-horizontal" action="/login" method="post">
                <span class="heading" style="color: black"><spring:message code="login.autorize"/></span>
                <div class="form-group">
                    <input type="text" class="form-control" id="inputLogin" name="login" placeholder=<spring:message code="login.login"/>>
                    <i class="fa fa-user"></i>
                </div>
                <div class="form-group help">
                    <input type="password" class="form-control" id="inputPassword" name="password" placeholder=<spring:message code="login.password"/>>
                    <i class="fa fa-lock"></i>
                </div>
                <c:if test="${error != null}">
                    <div style="color: red">
                        <spring:message code="error.login"/>
                            <%--${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}--%>
                    </div>
                </c:if>
                <div class="form-group">
                    <h4><a class="text" style="color: black; text-decoration: none" href="/register"><spring:message code="login.reg"/></a></h4>
                    <button type="submit" class="btn btn-default"><spring:message code="login.sign"/></button>
                </div>
            </form>
        </div>

    </div><!-- /.row -->
</div><!-- /.container -->


</body>
</html>
