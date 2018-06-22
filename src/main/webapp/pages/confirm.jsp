<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${pageContext.request.locale}"/>
<fmt:setBundle basename="resources"/>
<html>
<head>
    <title>Confirm</title>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="../css/footer.css">
    <link rel="stylesheet" href="../css/content.css">
    <link rel="stylesheet" href="../css/confirm.css">
    <script src="../js/jquery-3.3.1.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="content">
    <div class="innerContent">
        <h2>Check your order info</h2>
        <div class="innerInfo">
            <div>User:<span>${user.name} ${user.surname}</span></div>
            <div>Card number:<span>${order.creditCard}</span></div>
            <div>Shipping:<span>${order.shipping}</span></div>
            <div>Billing:<span>${order.billing}</span></div>
            <div>Order date:<span>${order.date}</span></div>
            <div>Address (if present):<span>${order.address}</span></div>
            <div>Total price: <span>${order.totalPrice}</span>$</div>
        </div>
        <h2>Check your products in the basket</h2>
        <div class="innerPics">
            <c:forEach var="gadget" items="${cart.getAllProducts()}">
                <div class="gadget">
                    <img class="miniImg" src="/img/products/${gadget.product.id}.png" alt="">
                    <div>${gadget.product.name} ${gadget.product.price}$(each)</div>
                </div>
            </c:forEach>
        </div>
        <form action="/confirm" method="post">
            <input type="submit" value="confirm order" class="bt orderBt">
            <a href="/order"><input type="button" value="go back to change order" class="bt back"></a>
        </form>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
