<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Reviews</title>
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

</head>
<body>

<jsp:include page="menu.jsp"/>

<c:set var="mark"></c:set>

<div class="container" style="color: white; margin-top: 15px;">
    <p class="text-center"><b>${title.title} (${city.name})</b></p>

    <security:authorize url="/**/addreview">
        <%--<div class="container">--%>
        <%--&lt;%&ndash;<a href=${requestScope['javax.servlet.forward.request_uri']}/addreview>ADD REVIEW</a>&ndash;%&gt;--%>
        <%--<a href="/titles/${title.id}/addreview">ADD REVIEW</a>--%>
        <%--</div>--%>
        <div>
            <form class="form-horizontal" action="/titles/${title.id}/addreview" method="post" modelAttribute="review">
                <div class="form-group">
                    <div class="col-sm-offset-5 col-sm-12">
                        <input type="text" name="reviewName" class="form-control col-xs-3 replyAuteur" id="reviewName" formControlName="reviewName" maxlength="50" required
                               placeholder="Введіть назву відгуку ..." autocomplete="off">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-3 col-sm-12">
                        <textarea class="form-control replyTextArea" name="text" rows="5" id="text" formControlName="text" required placeholder="Ваш відгук ..."></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-3 col-sm-12">
                        <span class="fa fa-star" id="star1" onclick="add(this,1)"></span>
                        <span class="fa fa-star" id="star2" onclick="add(this,2)"></span>
                        <span class="fa fa-star" id="star3" onclick="add(this,3)"></span>
                        <span class="fa fa-star" id="star4" onclick="add(this,4)"></span>
                        <span class="fa fa-star" id="star5" onclick="add(this,5)"></span>
                        <span class="fa fa-star" id="star6" onclick="add(this,6)"></span>
                        <span class="fa fa-star" id="star7" onclick="add(this,7)"></span>
                        <span class="fa fa-star" id="star8" onclick="add(this,8)"></span>
                        <span class="fa fa-star" id="star9" onclick="add(this,9)"></span>
                        <span class="fa fa-star" id="star10" onclick="add(this,10)"></span>
                        <input type="hidden" name="mark" id="mark" value="0">
                        <script>
                            function add(ths,sno){

                                for (var i=1;i<=10;i++){
                                    var cur=document.getElementById("star"+i)
                                    cur.className="fa fa-star"
                                }

                                for (var i=1;i<=sno;i++){
                                    var cur=document.getElementById("star"+i)
                                    if(cur.className=="fa fa-star")
                                    {
                                        cur.className="fa fa-star checked"
                                    }
                                }
                                document.getElementById('mark').value = sno;
                            }
                        </script>
                        <button type="submit" class="btn btn-success replySubmit green float-right" name="addreview" [disabled]="replyForm.invalid"><i class="fa fa-share"></i> Коментувати</button>
                    </div>
                </div>
            </form>
        </div>

        <c:if test="${message != null}">
            <div class="container">
                <p style="color: red">${message}</p>
            </div>
        </c:if>

        <br><br><br>
    </security:authorize>

    <c:if test="${reviews.size() == 0}">
        <label>NO REVIEWS</label>
    </c:if>
    <div class="reviews col-sm-12">
        <c:if test="${reviews.size() != 0}">
        <c:forEach items="${reviews}" var="review">
        <div class="row blockquote review-item">
            <div class="col-md-3 text-center">
                <img class="rounded-circle reviewer" src="/resources/img/avatar.png">
                <div class="caption" style="color: black">
                    <small>by ${review.userLogin}</small>
                </div>

            </div>
            <div class="col-md-9" style="color: black">
                <h4>${review.reviewName}</h4>
                <div class="ratebox text-center" data-id="${review.mark}" data-rating="${review.mark}"></div>
                <p class="review-text">${review.text}</p>

                <small class="review-date">${review.date}</small>
            </div>
        </div>
    </div>

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
    </c:forEach>
    </c:if>

</div>


</body>
</html>
