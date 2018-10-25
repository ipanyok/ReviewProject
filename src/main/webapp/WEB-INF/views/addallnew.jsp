<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Add All</title>
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


<div class="content py-5">
    <div class="container">
        <div class="row">
            <div class="col-md-8 offset-md-2">
                <span class="anchor" id="formUserEdit"></span>
                <!-- form user info -->
                <div class="card card-outline-secondary">
                    <div class="card-header">
                        <h3 class="mb-0">Категорії</h3>
                    </div>
                    <div class="card-body">
                        <form action="/addallnew" method="post" enctype="multipart/form-data">
                            <div class="form-group row">
                                <label class="col-lg-3 col-form-label form-control-label">Категорія*</label>
                                <div class="col-lg-9">
                                    <input class="form-control" type="text" placeholder="Введіть категорію" name="categoryName" autocomplete="off">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-lg-3 col-form-label form-control-label">Підкатегоія*</label>
                                <div class="col-lg-9">
                                    <input class="form-control" type="text" placeholder="Введіть підкатегорію" name="subCategoryName" autocomplete="off">
                                </div>
                            </div>
                            <div class="card-header">
                                <h3>Товар</h3>
                            </div>
                            <br>
                            <div class="form-group row">
                                <label class="col-lg-3 col-form-label form-control-label">Назва товару*</label>
                                <div class="col-lg-9">
                                    <input class="form-control" type="text" name="titleName" autocomplete="off">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-lg-3 col-form-label form-control-label">Опис</label>
                                <div class="col-lg-9">
                                    <textarea class="form-control" type="text" value="" name="titleDescription" autocomplete="off"></textarea>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-lg-3 col-form-label form-control-label">Місто*</label>
                                <div class="col-lg-9">
                                    <input type="text" class="form-control" name="titleCity" list="cities" autocomplete="off"/>
                                    <datalist id="cities">
                                        <c:forEach items="${cities}" var="city">
                                            <option value="${city}"/>
                                        </c:forEach>
                                    </datalist>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-lg-3 col-form-label form-control-label">Фото</label>
                                <div class="col-lg-9">
                                    <input name="file" type="file" value="Додайте фото"/>
                                </div>
                            </div>
                            <div class="card-header">
                                <h3>Ваш відгук</h3>
                            </div>
                            <br>
                            <div class="form-group row">
                                <label class="col-lg-3 col-form-label form-control-label">Введіть назву*</label>
                                <div class="col-lg-9">
                                    <input class="form-control" type="text" name="reviewName" autocomplete="off">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-lg-3 col-form-label form-control-label">Ваш відгук*</label>
                                <div class="col-lg-9">
                                    <textarea type="text" name="text" autocomplete="off" class="form-control"></textarea>
                                </div>
                            </div>
                            <input type="hidden" name="mark" id="mark" value="0">
                            <div class="form-group row">
                                <input type="hidden" name="mark"/>
                                <label class="col-lg-3 col-form-label form-control-label">Оцінка*</label>
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
                            </div>
                            <c:if test="${errors != null}">
                                <div class="form-group row">
                                    <div class="col-lg-9" style="color: red">
                                        <p><spring:message code="review.empty"/></p>
                                    </div>
                                </div>
                            </c:if>
                            <div class="form-group row">
                                <label class="col-lg-3 col-form-label form-control-label"></label>
                                <div class="col-lg-9">
                                    <input type="submit" class="btn btn-primary" value="Коментувати" name="addreviewtonewtitle">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <!-- /form user info -->

            </div>
        </div>
    </div>
</div>


</body>
</html>