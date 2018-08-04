<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${pageContext.request.locale}"/>
<fmt:setBundle basename="resources"/>
<html>
<head>
    <title>Sign up</title>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="../css/footer.css">
    <link rel="stylesheet" href="../css/content.css">
    <link rel="stylesheet" href="../css/signup.css">
    <script src="../js/jquery-3.3.1.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="content">
    <div class="reg">
        <h2>Fill the form to register</h2>
        <form action="/reg" id="regForm" method="post" onsubmit="return validate(this)">
            <label for="name">Type your name</label>
            <input type="text" id="name" name="name" value="${bean.name}"
                   class="${(not empty errors.name)?"redOutline":""}">
            <div id="nameMessage" class="messages">Name shouldn't contains numbers or forbidden symbols and be less than
                15 symbols!
            </div>
            <div class="messages serv">${errors.name}</div>
            <label for="surname">Type your surname</label>
            <input type="text" id="surname" name="surname" value="${bean.surname}"
                   class="${(not empty errors.surname)?"redOutline":""}">
            <div id="surnameMessage" class="messages">Surname shouldn't contains numbers or forbidden symbols and be
                less than 15 symbols!
            </div>
            <div class="messages serv">${errors.surname}</div>
            <label for="email">Type Email</label><br>
            <input type="text" id="email" name="email" value="${bean.email}"
                   class="${(not empty errors.email)?"redOutline":""}"><br>
            <div id="emailM" class="messages">Email should be written like example.email@gmail.com!</div>
            <div class="messages serv">${errors.email}</div>
            <label for="password">Type password</label><br>
            <input type="password" id="password" name="password"
                   class="${(not empty errors.password)?"redOutline":""}"><br>
            <div id="passM" class="messages">Password should contains more than 8 symbols!</div>
            <div class="messages serv">${errors.password}</div>
            <label for="password2">Type password again</label><br>
            <input type="password" id="password2" name="password2"
                   class="${(not empty errors.password2)?"redOutline":""}"><br>
            <div id="pass2" class="messages">Passwords don't match</div>
            <div class="messages serv">${errors.password2}</div>
            <label>Choose your gender</label>
            <div class="gender">
                <label for="male">male</label>
                <input type="radio" value="male" id="male" name="gender"
                ${(bean.gender eq "male")?"checked":(bean.gender eq "female")?"":"checked"}>
                <label for="female">female</label>
                <input type="radio" value="female" id="female" name="gender"
                ${(bean.gender eq "female")?"checked":""}>
            </div>
            <label for="photo">Upload your photo</label><br>
            <div class="fileUpload">
            <input type="file" id="photo" name="photo"><br>
            </div>
            <div class="messages serv">${errors.photo}</div>
            <tag:captcha/>
            <div class="messages serv">${errors.duplicate}</div>
            <input type="submit" value="sign up" id="bt">
            <input type="text" hidden id="hiddenId" name="hiddenId" value="${hiddenId}">
        </form>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
<c:remove var="bean" scope="session"/>
<c:remove var="errors" scope="session"/>
<script src="../js/script.js"></script>
<script src="../js/validation/signup_jquery.js"></script>
</body>
</html>
