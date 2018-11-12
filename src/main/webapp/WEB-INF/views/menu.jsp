<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


<link rel="stylesheet" href="/resources/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="/resources/css/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="/resources/css/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

<link href="/resources/css/bootstrap-4.0.0.min.css" rel="stylesheet" id="bootstrap-css">
<link rel="stylesheet" href="/resources/css/font-awesome.min.css">
<script src="/resources/css/bootstrap-4.0.0.min.js"></script>
<script src="/resources/css/jquery-1.11.1.min.js"></script>
<script src="/resources/css/jquery.min.js"></script>
<link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">


<style>
    body {
        background-image: url('/resources/img/la.jpg');
        background-repeat: no-repeat;
        background-position: center top;
        background-size: cover;
    }

    .dropdown .dropdown-menu-wrapper {
        display: none;
        position: absolute;
        padding-top: 7px;
    }

    .dropdown .dropdown-menu-wrapper > .dropdown-menu {
        display: block;
        position: relative;
    }

    .dropdown:hover > .dropdown-menu-wrapper {
        display: block;
    }

    .dropdown-submenu:hover > .dropdown-menu {
        display: block;
    }

    .dropdown-submenu {
        position: relative;
    }

    .dropdown-submenu > a:after {
        display: block;
        content: " ";
        float: right;
        width: 0;
        height: 0;
        border-color: transparent;
        border-style: solid;
        border-width: 5px 0 5px 5px;
        border-left-color: #000;
        margin-top: 9px;
        margin-right: -15px;
    }

    .dropdown-submenu .dropdown-menu {
        top: 0;
        left: 100%;
        margin-left: 0px;
        margin-top: -9px;
    }
</style>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">DNO.UA</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/" style="font-family: Arial"><spring:message code="menu.main"/></a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true"
                   aria-expanded="false" style="font-family: Arial"><spring:message code="menu.category"/></a>
                <div class="dropdown-menu-wrapper">
                    <div class="dropdown-menu">
                        <c:forEach items="${categoryMap}" var="cat">
                            <c:if test="${cat.getValue().size() != 0}">
                                <div class="dropdown-submenu">
                                    <a class="dropdown-item" href="#" style="font-family: Arial">${cat.getKey()}</a>
                                    <div class="dropdown-menu">
                                        <c:forEach items="${cat.getValue()}" var="catSub">
                                            <a class="dropdown-item"
                                               href="/titles/subcat/${catSub.id}/${currentCity}" style="font-family: Arial">${catSub.name}</a>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${cat.getValue().size() == 0}">
                                <a class="dropdown-item" href="#" style="font-family: Arial">${cat.getKey()}</a>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/ratings" style="font-family: Arial"><spring:message code="menu.ratings"/></a>
            </li>
            <security:authorize access="(isAuthenticated() and principal.username != 'Admin') or isAnonymous()">
                <li class="nav-item"><a href="#" class="nav-link" data-toggle="modal" data-target="#feedbackModal" style="font-family: Arial"><spring:message code="menu.contact"/></a>
                </li>
            </security:authorize>

            <li class="nav-item"><a href="#" class="nav-link disabled" data-toggle="modal" data-target="#feedbackModal" style="text-decoration: none; pointer-events: none;font-family: Arial">Правила розміщення</a>
            </li>

            <security:authorize url="/**/addtitle">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button"
                       aria-haspopup="true" aria-expanded="false" style="font-family: Arial"><spring:message code="menu.add"/></a>
                    <div class="dropdown-menu-wrapper">
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="/addtitle" style="font-family: Arial">Додати товар</a>
                            <a class="dropdown-item" href="/addcategories" style="font-family: Arial">Додати категорії</a>
                        </div>
                    </div>
                </li>
            </security:authorize>

            <security:authorize access="isAnonymous()">
                <li class="nav-item"><a class="nav-link" href="/login" style="font-family: Arial">&nbsp;&nbsp;&nbsp;<spring:message code="menu.sign_in"/></a></li>
            </security:authorize>
            <security:authorize access="isAuthenticated()">
                <li class="nav-item"><a class="nav-link" href="/logout" style="font-family: Arial">&nbsp;&nbsp;&nbsp;<spring:message code="menu.sign_out"/></a></li>
            </security:authorize>
        </ul>


        <form class="form-inline my-2 my-lg-0" action="/search" method="post">
            <security:authorize access="isAuthenticated()">
                <label>
                    <h7 style="color: white; font-family: 'Segoe Print'">Hello, <security:authentication property="principal.username"/>&nbsp;&nbsp;&nbsp;&nbsp;</h7>
                </label>
            </security:authorize>
            <security:authorize access="isAuthenticated()">
                <a href="/showmessages" class="cart-icon" style="font-family: Arial;color: white;text-decoration: none;">
                        <i class="fa fa-envelope-o"></i>
                        <c:if test="${messagesmenu != 0}">
                            <span class="badge badge-light bg-warning">${messagesmenu}</span>
                        </c:if>&nbsp;&nbsp;&nbsp;&nbsp;
                </a>
            </security:authorize>
            <security:authorize access="isAuthenticated()">
                <select style="color: black; font-family: 'Segoe Print'" onchange="window.location='/changecity?city=' + this.value;">
                    <option style="color: black; font-family: 'Segoe Print'" value="${currentCity}">${currentCity}</option>
                    <c:forEach items="${cities}" var="city">
                        <option style="color: black; font-family: 'Segoe Print'">${city}</option>
                    </c:forEach>
                </select>
                &nbsp;&nbsp;&nbsp;&nbsp;
            </security:authorize>
            <a href="${requestScope['javax.servlet.forward.request_uri']}?lang=en"
               style="color:lightslategrey;text-decoration: none; pointer-events: none">EN&nbsp;&nbsp;&nbsp;&nbsp;</a>
            <label style="color:white;">|</label>
            <a href="${requestScope['javax.servlet.forward.request_uri']}?lang=ua"
               style="color:white;text-decoration: none;">&nbsp;&nbsp;&nbsp;&nbsp;UA&nbsp;&nbsp;&nbsp;&nbsp;</a>
            <input class="form-control mr-sm-2" type="search" placeholder="Знайти" aria-label="Search" name="search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Пошук</button>
        </form>
    </div>
</nav>

<!-- Modal -->
<div class="modal fade" id="feedbackModal" tabindex="-1" role="dialog" aria-labelledby="feedbackModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header bg-success text-white">
                <h5 class="modal-title" id="feedbackModalLabel">Зворотній зв'язок</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <input id="Full Name" name="Full Name" placeholder="Ім'я" class="form-control" type="text">
                        </div>
                        <div class="form-group col-md-6">
                            <input type="email" class="form-control" id="inputEmail4" placeholder="Email">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <textarea id="comment" name="comment" cols="40" rows="5" class="form-control"></textarea>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="" id="defaultCheck1">
                                <label class="form-check-label" for="defaultCheck1">
                                    I agree to <a href="#">Terms and Conditions</a>
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-row">
                        <button type="button" class="btn btn-success">Написати</button>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
            </div>
        </div>
    </div>
</div>