<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Success</title>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="../css/footer.css">
    <link rel="stylesheet" href="../css/content.css">
    <link rel="stylesheet" href="../css/success.css">
    <script src="../js/jquery-3.3.1.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="content">
    <div class="innerContent">
        <h2>Congratulations !!!</h2>
        <div class="textInfo">
            <div>User:${user.name} ${user.surname}</div>
            <div>Card number: ${order.creditCard}</div>
            <div>Shipping: ${order.shipping}</div>
            <div>Billing: ${order.billing}</div>
            <div>Order date: ${order.date}</div>
            <div>Total price: ${order.totalPrice}$</div>
        </div>
        <div class="basketInfo">
            <c:forEach var="ord" items="${order.listOfOrderedProducts}">
                <div class="gadget">
                    <div>
                        <img class="imgMini" src="/img/products/${ord.productId}.png" alt="">
                    </div>
                    <h3>Count of products: ${ord.productCount}</h3>
                </div>
            </c:forEach>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
