<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${pageContext.request.locale}"/>
<fmt:setBundle basename="resources"/>
<html>
<head>
    <title>Ading page</title>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="../css/footer.css">
    <link rel="stylesheet" href="../css/content.css">
    <link rel="stylesheet" href="../css/basket.css">
    <script src="../js/jquery-3.3.1.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="content">
    <div class="reg">
        <div class="emptyBasket">Admin Page</div>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
<script src="../js/script.js"></script>
</body>
</html>
