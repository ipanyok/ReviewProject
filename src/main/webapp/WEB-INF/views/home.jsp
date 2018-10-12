<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<title>Home</title>
<body>

<jsp:include page="menu.jsp"/>

<security:authorize access="isAuthenticated() and principal.username != 'Admin'">
    <div class="container text-right">
        <div class="card-body">
            <a href="/addallnew" class="btn btn-success">Add new category</a>
        </div>
    </div>
</security:authorize>


<div class="container">
    <c:if test="${lastAddedReviews.size() == 0}">
    <label>NO REVIEWS</label>
    <c:if test="${userMessage != null}">
    <div class="container text-center">
        <p>
        <h3 style="color: green">${userMessage}</h3></p><br><br>
    </div>
    </c:if>
    </c:if>

    <c:if test="${lastAddedReviews.size() != 0}">
    <p class="text-center" style="font-size: 40px; color: white; font-family: ''; margin-top: 19px; margin-bottom: auto;">LAST REVIEWS</p>
    <div class="container p-4">
        <div class="card-deck" style="width: 100%">
            <c:forEach items="${lastAddedReviews}" var="review">
                <c:if test="${review.getKey().mark > 3}">
                    <div class="card border-success" style="background-color: #3a3a3a">
                </c:if>
                <c:if test="${review.getKey().mark <= 3}">
                    <div class="card border-danger" style="background-color: #3a3a3a">
                </c:if>
                    <img class="card-img-top" src="/getphoto/${review.getKey().idTitle}" alt="NO IMAGE" style="position: relative">
                    <div class="card-header text-center" style="background-color: #2d2d2d; color: white; font-family: 'Segoe Script'">
                        <h4>${review.getValue().get(0)}, &nbsp;<small>${review.getValue().get(1)}</small>
                        </h4>
                    </div>
                    <c:if test="${review.getKey().mark > 3}">
                    <div class="card-body text-success" style="background-color: #3a3a3a">
                        </c:if>
                        <c:if test="${review.getKey().mark <= 3}">
                        <div class="card-body text-danger" style="background-color: #3a3a3a;">
                            </c:if>
                            <h5 class="card-title" style="font-family: 'Segoe Print'">${review.getKey().reviewName},&nbsp; <small>mark: ${review.getKey().mark}</small></h5>

                            <c:if test="${review.getKey().text.length() > 25}">
                                <p class="card-text" style="font-family: 'Segoe Print'">${review.getKey().text.substring(0, 25)}<a href="/titles/${review.getValue().get(3)}" style="color: #11a3fc">&nbsp;...read more...</a></p>
                            </c:if>
                            <c:if test="${review.getKey().text.length() <= 25}">
                                <p class="card-text" style="font-family: 'Segoe Print'">${review.getKey().text}</p>
                            </c:if>

                            <p class="card-text">
                                <small class="text-muted">${review.getValue().get(2)}&nbsp;${review.getKey().date}</small>
                            </p>
                            <p><a href="/titles/${review.getValue().get(3)}" class="btn btn-primary" role="button">Read more</a></p>
                        </div>
                    </div>
                    </c:forEach>
                </div>
            </div>
            </c:if>

            <c:if test="${userMessage != null}">
                <div class="container text-center">
                    <p>
                    <h3 style="color: green">${userMessage}</h3></p><br><br>
                </div>
            </c:if>



        </div>
    </div>
</div>
<div class="static-bottom">
    <div class="container">
        <p class="text-center" style="font-size: 40px; color: white; font-family: ''">ABOUT US</p>
        <div>
            <hr style="border: none; background-color: grey; color: grey; height: 2px;">
        </div>
        <div class="text-center" style="color:white;">
            U.S. Highway 25 in the state of Michigan (U.S. 25) was a highway that ran northeasterly from
            the
            Ohio state line near Toledo through Monroe and Detroit to Port Huron.
            Created with the initial U.S. Highway System in 1926, U.S. 25 followed some roadways dating
            from
            the 19th and early 20th centuries, and replaced several state highway designations.
            The highway was extended to Port Austin in 1933.
            Starting in the early 1960s, segments of Interstate 75 and Interstate 94 were built, and
            U.S. 25
            was shifted to follow them concurrently south of Detroit to Port Huron.
            On September 26, 1973, the entire designation was removed from the state. The final routing
            of
            the highway is still maintained by the state under eight different designations, some
            unsigned.
            <br><br><br>
        </div>
    </div>
</div>

</body>
</html>
