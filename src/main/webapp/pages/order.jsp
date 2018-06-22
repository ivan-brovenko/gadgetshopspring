
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${pageContext.request.locale}"/>
<fmt:setBundle basename="resources"/>

<html>
<head>
    <title>Order</title>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="../css/footer.css">
    <link rel="stylesheet" href="../css/content.css">
    <link rel="stylesheet" href="../css/order.css">
    <script src="../js/jquery-3.3.1.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="content">
    <div class="innerContent">
        <h2>Make an order</h2>
        <form action="/order" method="post" name="orderForm">
            <label>Shipping</label><br>
            <select name="ship">
                <option hidden>...</option>
                <option>self-delivery</option>
                <option>home-delivery</option>
            </select><br>
            <label>Billing</label><br>
            <select name="bill">
                <option hidden>...</option>
                <option>card</option>
                <option>cash</option>
            </select><br>
            <div class="cardPay">
                <label>Cart Number</label><br>
                <input pattern="[0-9]*" name="cardNumber" type="number" class="cartNumber" min="100000000000" max="999999999999"><br>
                <label>CVV</label><br>
                <input pattern="[0-9]*" name="cvv" type="number" class="cvv" min="1000" max="9999"><br>
            </div>
            <div class="cashPay">
                <label>Home adress</label><br>
                <textarea name="address"></textarea><br>
            </div>
            <input type="submit" value="submit an order" class="submitOrder"><br>
        </form>
        <div class="finalPrice"> Total price: ${empty (cart.getTotalPrice())?"0":cart.getTotalPrice()}$</div>
        <div class="errorMessage">Fill all the fields!!!</div>
        <div class="errorMessage displayBlock">${errors.errorLogInMessage}</div>
    </div>
</div>
<c:remove var="errors"/>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
<script src="../js/order.js"></script>
</body>
</html>
