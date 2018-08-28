<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
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
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="menu.category"/> <b
                            class="caret"></b></a>
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

                <security:authorize access="(isAuthenticated() and principal.username != 'Admin') or isAnonymous()">
                    <li><a href="#"><spring:message code="menu.contact"/></a></li>
                </security:authorize>


                <security:authorize url="/**/addtitle">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="menu.add"/> <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="/addtitle">Add Title</a>
                                <a href="/addcategories">Add Categories</a>
                            </li>
                        </ul>
                    </li>
                </security:authorize>


                <security:authorize access="isAuthenticated()">
                    <li><a href="/showmessages"><spring:message code="menu.messages"/>
                        <c:if test="${messagesmenu != 0}"><span
                                class="badge badge-success">${messagesmenu}</span></c:if>
                    </a></li>
                </security:authorize>
                <security:authorize access="isAnonymous()">
                    <li><a href="/login"><spring:message code="menu.sign_in"/></a></li>
                </security:authorize>
                <security:authorize access="isAuthenticated()">
                    <li><a href="/logout"><spring:message code="menu.sign_out"/></a></li>
                </security:authorize>
            </ul>

            <form class="navbar-form navbar-right" role="search">
                <security:authorize access="isAuthenticated()">
                    <label>
                        <h7 style="color: white">Hello, <security:authentication property="principal.username"/>&nbsp;&nbsp;&nbsp;&nbsp;</h7>
                    </label>
                </security:authorize>
                <security:authorize access="isAuthenticated()">
                    <select>
                        <option value="${currentCity}">${currentCity}</option>
                        <c:forEach items="${cities}" var="city">
                            <option>${city}</option>
                        </c:forEach>
                    </select>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                </security:authorize>

                <a href="${requestScope['javax.servlet.forward.request_uri']}?lang=en"
                   style="color:white;text-decoration: none;">EN&nbsp;&nbsp;&nbsp;&nbsp;</a>
                <label style="color:white;">|</label>
                <a href="${requestScope['javax.servlet.forward.request_uri']}?lang=ua"
                   style="color:white;text-decoration: none;">&nbsp;&nbsp;&nbsp;&nbsp;UA&nbsp;&nbsp;&nbsp;&nbsp;</a>
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Search">
                </div>
                <button type="submit" class="btn btn-success" style="height: 35px"><span
                        class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
            </form>

        </div>
    </div>
</div>
<br><br><br><br><br>