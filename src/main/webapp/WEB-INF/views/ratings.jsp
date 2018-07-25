<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Ratings</title>

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

<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <label class="navbar-brand" style="color: white"><span class="glyphicon glyphicon-music"
                                                                   aria-hidden="true"></span></label>
        </div>
        <div>
            <ul class="nav navbar-nav">
                <li class="divider-vertical"></li>
                <li><a href="/"><spring:message code="menu.main"/></a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="menu.category"/> <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <c:forEach items="${categoryMap}" var="cat">
                            <li class="dropdown-submenu">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">${cat.getKey()}</a>
                                <ul class="dropdown-menu">
                                    <c:forEach items="${cat.getValue()}" var="catSub">
                                        <li><a href="/titles/subcat/${catSub.id}">${catSub.name}</a></li>
                                    </c:forEach>
                                </ul>
                            </li>
                        </c:forEach>
                    </ul>
                </li>
                <li><a href="/ratings"><spring:message code="menu.ratings"/></a></li>
                <li><a href="#"><spring:message code="menu.contact"/></a></li>
                <li><a href="#"><spring:message code="menu.sign_in"/></a></li>
                <li><a href="#"><spring:message code="menu.messages"/> <span class="badge badge-success">9</span></a></li>
            </ul>

            <form class="navbar-form navbar-right" role="search">
                <a href="${requestScope['javax.servlet.forward.request_uri']}?lang=en" style="color:white;text-decoration: none;">EN&nbsp;&nbsp;&nbsp;&nbsp;</a>
                <label style="color:white;">|</label>
                <a href="${requestScope['javax.servlet.forward.request_uri']}?lang=ua" style="color:white;text-decoration: none;">&nbsp;&nbsp;&nbsp;&nbsp;UA&nbsp;&nbsp;&nbsp;&nbsp;</a>
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Search">
                </div>
                <button type="submit" class="btn btn-success" style="height: 35px"><span
                        class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
            </form>

        </div>
    </div>
</div>

<br><br><br><br><br><br><br>
<div class="container">
    BAD RATING:<br><br>
    <div class="row">
        <c:forEach items="${ratings.get(0)}" var="rating">
            <p><b>${rating.getKey()}: ${rating.getValue()}</b></p>
        </c:forEach>
    </div>

</div>

<br><br><br><br><br><br><br>
<div class="container">
    GOOD RATING:<br><br>
    <div class="row">
        <c:forEach items="${ratings.get(1)}" var="rating">
            <p><b>${rating.getKey()}: ${rating.getValue()}</b></p>
        </c:forEach>
    </div>

</div>

</body>
</html>
