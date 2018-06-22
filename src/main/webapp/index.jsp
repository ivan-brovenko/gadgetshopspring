<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${pageContext.request.locale}"/>
<fmt:setBundle basename="resources"/>

<html>
<head>
    <meta charset="UTF-8" name="viewport"
          content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>iStore</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="css/footer.css">
    <script src="js/jquery-3.3.1.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="carusel">
    <input type="radio" name="images" id="i1" checked>
    <input type="radio" name="images" id="i2">
    <input type="radio" name="images" id="i3">
    <div class="slide_img" id="one">
        <img src="img/mp11.jpg" alt="fck">
        <div class="text" id="headline1">iStore shop</div>
        <label for="i3" class="pre"><span><i class="fa fa-chevron-left" aria-hidden="true"></i></span></label>
        <label for="i2" class="nxt"><span><i class="fa fa-chevron-right" aria-hidden="true"></i></span></label>
    </div>
    <div class="slide_img" id="two">
        <img src="img/mp12.jpg" alt="fck">
        <div class="text" id="headline2">Gadgets of the best quality</div>
        <label for="i1" class="pre"><span><i class="fa fa-chevron-left" aria-hidden="true"></i></span></label>
        <label for="i3" class="nxt"><span><i class="fa fa-chevron-right" aria-hidden="true"></i></span></label>
    </div>
    <div class="slide_img" id="three">
        <img src="img/mp3.jpg" alt="fck">
        <div class="text" id="headline3">New products</div>
        <label for="i2" class="pre"><span><i class="fa fa-chevron-left" aria-hidden="true"></i></span></label>
        <label for="i1" class="nxt"><span><i class="fa fa-chevron-right" aria-hidden="true"></i></span></label>
    </div>
    <div class="nav">
        <label class="dots" id="dot1" for="i1"></label>
        <label class="dots" id="dot2" for="i2"></label>
        <label class="dots" id="dot3" for="i3"></label>
    </div>
</div>
<div>
    <a href=""><img class="tab" src="img/ix.jpg" alt="java"></a>
    <img class="tab" src="img/watch.jpg" alt="quiz">
    <a href="#"><img class="tab" src="img/mac1.jpg" alt="photo"></a>
    <img class="tab" src="img/ipad.jpg" alt="web">
</div>
<section class="content-text">
    <p>
        Are you looking for the latest electronics?
    </p>
    <p>
        If you are as passionate about technology as we are, you’ll love what the iStore has to offer.
        In a quest to offer South Africans the latest and greatest gadgets, we founded the first store in 2004.
    </p>
    <p>
        With our commitment to sourcing the most innovative products just for you, we have expanded our range to over
        4000 product lines.
        From computer accessories to business and office toys, our extraordinary gizmos have got you covered whatever
        your needs are.
        Whether you are a tech geek or you are looking for the best gift ever, we are here to inspire you.
    </p>
    <p>
        Amp up the fun by grabbing some of our boy’s toys and taking them for a spin.
        With our user-friendly online store, you can be the first to get your hands on the latest electronics and
        super-toys without leaving the comfort of your home.
        Now you can find everything electronic, intelligent, technological and FUN in one place.
    </p>
</section>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
<script src="js/script.js"></script>
</body>
</html>