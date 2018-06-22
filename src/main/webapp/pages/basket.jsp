<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${pageContext.request.locale}"/>
<fmt:setBundle basename="resources"/>
<html>
<head>
    <title>Basket</title>
    <link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="../bootstrap/js/bootstrap.min.js"></script>
    <script src="../js/jquery.min.js"></script>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="../css/content.css">
    <link rel="stylesheet" href="../css/footer.css">
    <script src="../js/jquery-3.3.1.js"></script>
    <link rel="stylesheet" href="../css/basket.css">
    <link rel="stylesheet" href="../css/fix.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="content">
    <div class="container">
        <div class="card">
            <c:if test="${not empty cart.getAllProducts()}">
                <table class="table table-hover shopping-cart-wrap">
                    <thead class="text-muted">
                    <tr>
                        <th scope="col">Product</th>
                        <th scope="col" width="120">Quantity</th>
                        <th scope="col" width="120">Price</th>
                        <th scope="col" width="200" class="text-right">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="product" items="${cart.getAllProducts()}">
                        <tr>
                            <td>
                                <figure class="media">
                                    <div class="img-wrap"><img src="../img/products/${product.product.id}.png"
                                                               class="miniImg"></div>
                                    <figcaption class="media-body">
                                        <h6 class="title text-truncate">${product.product.name}</h6>
                                        <dl class="param param-inline small">
                                            <dt>Memory size:</dt>
                                            <dd>${product.product.memorySize}GB</dd>
                                        </dl>
                                    </figcaption>
                                </figure>
                            </td>
                            <td>
                                <input type="number" min="1" value="${product.countOfProduct}"
                                       class="form-control productCountBasket">
                            </td>
                            <td>
                                <div class="price-wrap">
                                    <var class="price">${product.product.price}</var>
                                    <small class="text-muted">(USD5 each)</small>
                                </div>
                            </td>
                            <td class="text-right">
                                <button class="btn btn-outline-danger deleteButton"> × Remove</button>
                                <input hidden type="number" value="${product.product.id}" class="productId">
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="totalPriceInBasket">Total price: ${basketTotalPrice}$</div>
            </c:if>
            <c:if test="${empty cart.getAllProducts()}">
                <div class="emptyBasket"><fmt:message key="basket.empty"/> </div>
            </c:if>
            <div class="emptyBasket hidden"><fmt:message key="basket.empty"/> </div>
        </div>
        <c:if test="${not empty cart.getAllProducts()}">
            <button class="orderBt makeOrder">make an order</button>
            <button class="orderBt removeBt">clear basket</button>
        </c:if>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
<script src="../js/script.js"></script>
<script src="../js/ajax.js"></script>
</body>
</html>
