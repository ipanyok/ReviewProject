<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<title>Home</title>
<style>
    body {
        background-color: silver;
    }

    .reviews {
        padding: 15px;
        max-width: 768px;
        margin: 0 auto;
    }

    .review-item {
        background-color: white;
        padding: 15px;
        margin-bottom: 5px;
        box-shadow: 1px 1px 5px #343a40;
    }

    .review-item .review-date {
        color: #cecece;
    }

    .review-item .review-text {
        font-size: 16px;
        font-weight: normal;
        margin-top: 5px;
        color: #343a40;
    }

    .review-item .reviewer {
        width: 100px;
        height: 100px;
        border: 1px solid #cecece;
    }

    /****Rating Stars***/
    .raterater-bg-layer {
        color: rgba(0, 0, 0, 0.25);
    }

    .raterater-hover-layer {
        color: rgba(255, 255, 0, 0.75);
    }

    .raterater-hover-layer.rated { /* after the user selects a rating */
        color: rgba(255, 255, 0, 1);
    }

    .raterater-rating-layer {
        color: rgba(255, 155, 0, 0.75);
    }

    .raterater-outline-layer {
        color: rgba(0, 0, 0, 0.25);
    }

    /* don't change these - you might break something.. */
    .raterater-wrapper {
        overflow: visible;
    }

    .software .raterater-wrapper {
        margin-top: 4px;
    }

    .raterater-layer,
    .raterater-layer i {
        display: block;
        position: absolute;
        overflow: visible;
        top: 0px;
        left: 0px;
    }

    .raterater-hover-layer {
        display: none;
    }

    .raterater-hover-layer i,
    .raterater-rating-layer i {
        width: 0px;
        overflow: hidden;
    }
    .checked {
        color: orange;
    }
</style>
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
        <div class="alert alert-success" role="alert">
            <h4 class="alert-heading">${userMessage}</h4>
        </div>
    </c:if>
    </c:if>

    <c:if test="${lastAddedReviews.size() != 0}">
    <p class="text-center" style="font-size: 40px; color: white; font-family: 'Segoe Print'; margin-top: 19px; margin-bottom: auto;">Останні Відгуки</p>
    <div class="container p-4">
        <div class="card-deck" style="width: 100%">
            <c:forEach items="${lastAddedReviews}" var="review">
                <c:if test="${review.getKey().mark > 3}">
                    <div class="card border-success" style="background-color: grey">
                </c:if>
                <c:if test="${review.getKey().mark <= 3}">
                    <div class="card border-danger" style="background-color: grey">
                </c:if>
                    <img class="card-img-top" src="/getphoto/${review.getKey().idTitle}" alt="NO IMAGE" style="height: 250px; width: 100%; display: block;" data-holder-rendered="true">
                    <div class="card-header text-left" style="background-color: #2d2d2d; color: white; font-family: 'Segoe Script'">
                        <h4>${review.getValue().get(0)}, &nbsp;<small>${review.getValue().get(1)}</small>
                        </h4>
                    </div>
                    <c:if test="${review.getKey().mark > 3}">
                    <div class="card-body text-success" style="background-color: #3a3a3a;">
                    </c:if>
                    <c:if test="${review.getKey().mark <= 3}">
                    <div class="card-body text-danger" style="background-color: #3a3a3a;">
                    </c:if>
                            <h5 class="card-title" style="font-family: 'Segoe Print'">${review.getKey().reviewName}</h5>
                                <p class="ratebox text-center" data-id="${review.getKey().mark}" data-rating="${review.getKey().mark}"></p>

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
                <div class="alert alert-success" role="alert">
                    <h4 class="alert-heading">${userMessage}</h4>
                </div>
            </c:if>
        </div>
    </div>
</div>
<footer class="bg-dark text-white">
    <div class="container">
        <p class="text-center" style="font-size: 40px; color: white; font-family: 'Segoe Print'">Про Нас</p>
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
            <br><br>
        </div>
    </div>
</footer>


<footer class="footer bg-dark text-white">


    <!-- Footer Links -->
    <%--<div class="container pt-5 pb-2">--%>
        <%--<div class="row">--%>

            <%--<div class="col-md-3 col-lg-4 col-xl-3">--%>
                <%--<h4>Company name</h4>--%>
                <%--<hr class="bg-white mb-2 mt-0 d-inline-block mx-auto w-25">--%>
                <%--<p>--%>
                    <%--When an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting--%>
                <%--</p>--%>
            <%--</div>--%>

            <%--<div class="col-md-2 col-lg-2 col-xl-2 mx-auto">--%>
                <%--<h4>Products</h4>--%>
                <%--<hr class="bg-white mb-2 mt-0 d-inline-block mx-auto w-25">--%>
                <%--<p><a href="#" class="text-white">Product-1</a></p>--%>
                <%--<p><a href="#" class="text-white">Product-2</a></p>--%>
                <%--<p><a href="#" class="text-white">Product-3</a></p>--%>
                <%--<p><a href="#" class="text-white">Product-4</a></p>--%>
            <%--</div>--%>

            <%--<div class="col-md-3 col-lg-2 col-xl-2 mx-auto">--%>
                <%--<h4>Useful links</h4>--%>
                <%--<hr class="bg-white mb-2 mt-0 d-inline-block mx-auto w-25">--%>
                <%--<p><a href="#" class="text-white">Home</a></p>--%>
                <%--<p><a href="#" class="text-white">About Us</a></p>--%>
                <%--<p><a href="#" class="text-white">Services</a></p>--%>
                <%--<p><a href="#" class="text-white">Contact</a></p>--%>
            <%--</div>--%>

            <%--<div class="col-md-4 col-lg-3 col-xl-3">--%>
                <%--<h4>Contact</h4>--%>
                <%--<hr class="bg-white mb-2 mt-0 d-inline-block mx-auto w-25">--%>
                <%--<p><i class="fa fa-home mr-3"></i> Company Location</p>--%>
                <%--<p><i class="fa fa-envelope mr-3"></i> info@example.com</p>--%>
                <%--<p><i class="fa fa-phone mr-3"></i> + 98 765 432 11</p>--%>
                <%--<p><i class="fa fa-print mr-3"></i> + 98 765 432 10</p>--%>
            <%--</div>--%>

        <%--</div>--%>
    <%--</div>--%>
    <!-- Footer Links-->

    <hr class="bg-white mx-4 mt-2 mb-1">

    <!-- Copyright-->
    <div class="container-fluid">
        <p class="text-center m-0 py-1">
            © 2018 Copyright <a href="#" class="text-white">Panya</a>
        </p>
    </div>
    <!-- Copyright -->

</footer>



<script>
    !function (t) {
        function a(a, r) {
            t('.raterater-input[data-id="' + a + '"]').data("input").val(r).change()
        }

        function r() {
            g.each(function () {
                var a = t(this);
                if (p.mode == u && ("INPUT" == a.prop("tagName") || "SELECT" == a.prop("tagName"))) {
                    var r = "input-" + y++,
                        e = t('<div class="raterater-input"></div>').attr("data-id", r).attr("data-rating", a.val()).data("input", a);
                    a.attr("data-id", r).attr("data-id", r).attr("data-rating", a.val()).data("input", a).after(e).hide(), l = a = e
                }
                l = a;
                var s = c(a);
                if (!s) throw"Error: Each raterater element needs a unique data-id attribute.";
                f[s] = {
                    state: "inactive",
                    stars: null
                }, "static" === a.css("position") && a.css("position", "relative"), a.addClass("raterater-wrapper"), a.html(""), t.each(["bg", "hover", "rating", "outline", "cover"], function () {
                    a.append(' <div class="raterater-layer raterater-' + this + '-layer"></div>')
                });
                for (var n = 0; n < p.numStars; n++) a.children(".raterater-bg-layer").first().append('<i class="fa fa-star"></i>'), a.children(".raterater-outline-layer").first().append('<i class="fa fa-star-o"></i>'), a.children(".raterater-hover-layer").first().append('<i class="fa fa-star"></i>'), a.children(".raterater-rating-layer").first().append('<i class="fa fa-star"></i>');
                p.isStatic || (a.find(".raterater-cover-layer").hover(o, h), a.find(".raterater-cover-layer").mousemove(d), a.find(".raterater-cover-layer").click(i))
            })
        }

        function e() {
            g.each(function () {
                var a;
                a = p.mode == u ? t(this).parent().find('.raterater-input[data-id="' + c(this) + '"]') : t(this);
                var r = (c(a), p.width + "px"), e = Math.floor(p.starWidth / p.starAspect) + "px";
                a.css("width", r).css("height", e), a.find(".raterater-layer").each(function () {
                    t(this).css("width", r).css("height", e)
                });
                for (var i = 0; i < p.numStars; i++) t.each(["bg", "hover", "rating", "outline"], function () {
                    a.children(".raterater-" + this + "-layer").first().children("i").eq(i).css("left", i * (p.starWidth + p.spaceWidth) + "px").css("font-size", Math.floor(p.starWidth / p.starAspect) + "px")
                });
                var s = parseFloat(a.attr("data-rating")), d = Math.floor(s), o = s - d;
                n(a.find(".raterater-rating-layer").first(), d, o)
            })
        }

        function i(r) {
            var e = t(r.target).parent(), i = c(e), s = f[i].whole_stars_hover + f[i].partial_star_hover;
            s = Math.round(100 * s) / 100, f[i].state = "rated", f[i].stars = s, e.find(".raterater-hover-layer").addClass("rated"), "input" != p.mode && void 0 !== window[p.submitFunction] && "function" == typeof window[p.submitFunction] ? window[p.submitFunction](i, s) : a(i, s)
        }

        function s(t, a) {
            var r = Math.floor(t / (p.starWidth + p.spaceWidth)), e = t - r * (p.starWidth + p.spaceWidth);
            if (e > p.starWidth && (e = p.starWidth), e /= p.starWidth, p.step !== !1) {
                var i = 1 / p.step;
                e = Math.round(e * i) / i
            }
            f[a].whole_stars_hover = r, f[a].partial_star_hover = e
        }

        function n(t, a, r) {
            for (var e = (c(t.parent()), 0); a > e; e++) t.find("i").eq(e).css("width", p.starWidth + "px");
            t.find("i").eq(a).css("width", p.starWidth * r + "px");
            for (var e = a + 1; e < p.numStars; e++) t.find("i").eq(e).css("width", "0px")
        }

        function d(a) {
            var r = c(t(a.target).parent());
            if ("hover" === f[r].state) {
                var e = a.offsetX;
                void 0 === e && (e = a.pageX - t(a.target).offset().left), f[r].stars = s(e, r);
                var i = t(a.target).parent().children(".raterater-hover-layer").first();
                n(i, f[r].whole_stars_hover, f[r].partial_star_hover)
            }
        }

        function o(a) {
            var r = c(t(a.target).parent());
            ("rated" !== f[r].state || p.allowChange) && (f[r].state = "hover", t(a.target).parent().children(".raterater-rating-layer").first().css("display", "none"), t(a.target).parent().children(".raterater-hover-layer").first().css("display", "block"))
        }

        function h(a) {
            var r = t(a.target).parent(), e = c(r);
            if (t(a.target).parent().children(".raterater-hover-layer").first().css("display", "none"), t(a.target).parent().children(".raterater-rating-layer").first().css("display", "block"), "rated" === f[e].state) {
                var i = parseFloat(f[e].stars), s = Math.floor(i), d = i - s;
                return void n(r.find(".raterater-rating-layer").first(), s, d)
            }
            f[e].state = "inactive"
        }

        function c(a) {
            return t(a).attr("data-id")
        }

        var l, f = {}, p = {}, u = "input", v = "callback", g = null, y = 0;
        t.fn.raterater = function (a) {
            if (t.fn.raterater.defaults = {
                submitFunction: "",
                allowChange: !1,
                starWidth: 20,
                spaceWidth: 5,
                numStars: 5,
                isStatic: !1,
                mode: v,
                step: !1
            }, p = t.extend({}, t.fn.raterater.defaults, a), p.width = p.numStars * (p.starWidth + p.spaceWidth), p.starAspect = .9226, p.step !== !1 && (p.step = parseFloat(p.step), p.step <= 0 || p.step > 1)) throw"Error: step must be between 0 and 1";
            return g = this, r(), e(), this
        }
    }(jQuery);

    $(document).ready(function () {
        $('.ratebox').raterater({
            submitFunction: 'rateAlert',
            allowChange: false,
            starWidth: 15,
            spaceWidth: 2,
            numStars: 10
        });

    });
</script>

</body>
</html>
