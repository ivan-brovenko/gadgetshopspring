<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log in</title>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="../css/footer.css">
    <link rel="stylesheet" href="../css/content.css">
    <link rel="stylesheet" href="../css/signup.css">
    <link rel="stylesheet" href="../css/login.css">
    <script src="../js/jquery-3.3.1.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="content">
    <div class="reg">
        <tag:login/>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
<script src="../js/script.js"></script>
</body>
</html>