<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${pageContext.request.locale}"/>
<fmt:setBundle basename="resources"/>

<html>
<head>
    <title>Products</title>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="../css/content.css">
    <link rel="stylesheet" href="../css/footer.css">
    <link rel="stylesheet" href="../css/products.css">
    <script src="../js/jquery-3.3.1.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="content">
    <div class="gadgetFilter">
        <h2>Filter products by categories </h2>
        <form action="/products" method="get">
            <table>
                <tr>
                    <td>
                        <div class="price">
                            <h2>Filter by price</h2>
                            <label for="priceFrom">from</label>
                            <input type="number" id="priceFrom" min="0" name="priceFrom" value="${priceFrom}">
                            <label for="priceTo">to</label>
                            <input type="number" id="priceTo" min="0" name="priceTo" value="${priceTo}">
                        </div>
                    </td>
                    <td>
                        <div class="categories">
                            <h2>Filter by category</h2>
                            <div class="categoriesInner">
                                <c:forEach var="category" items="${categories}">
                                    <label for="iphoneCheck">${category.categoryName}</label>
                                    <input type="checkbox" id="iphoneCheck" name="categoriesCheck"
                                           value="${category.categoryName}"
                                    <c:forEach var="catNames" items="${categoriesCheck}">
                                    <c:if test="${catNames eq category.categoryName}">
                                           checked
                                    </c:if>
                                    </c:forEach>><br>
                                </c:forEach>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="firm">
                            <h2>Filter by producer country</h2>
                            <select name="producerCountry">
                                <option value="${producerCountry}" hidden>${(empty producerCountry)?"...":producerCountry}</option>
                                <c:forEach var="country" items="${countries}">
                                    <option value="${country.countryName}">${country.countryName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </td>
                    <td>
                        <div class="name">
                            <h2>Search by name of the product</h2>
                            <input type="text" name="productName" value="${productName}">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="cut">
                            <h2>Cut products on page</h2>
                            <select name="limit">
                                <option value="${limit}" hidden>${(empty limit)?"...":limit}</option>
                                <option>3</option>
                                <option>6</option>
                                <option>9</option>
                                <option>12</option>
                            </select>
                        </div>
                    </td>
                    <td>
                        <div class="sort">
                            <h2>Sort products</h2>
                            <select name="sort">
                                <option value="${sort}" hidden>${(sort eq "nAZ")?
                                "sort by name (a-z)":(sort eq "nZA"?"sort by name (z-a)":(sort eq "p0N")?
                                "sort by price (0-n)":(sort eq "pN0"?"sort by price (n-0)":"..."))}</option>
                                <option value="nAZ">sort by name (a-z)</option>
                                <option value="nZA">sort by name (z-a)</option>
                                <option value="p0N">sort by price (0-n)</option>
                                <option value="pN0">sort by price (n-0)</option>
                            </select>
                        </div>
                    </td>
                </tr>
            </table>
            <input type="submit" value="apply" class="apply">
        </form>
    </div>
    <tag:pagination/>
    <c:if test="${empty gadgets}">
        <div class="noGadget">There's no such a gadget</div>
    </c:if>
    <div class="gadgets">
        <c:forEach var="gadget" items="${gadgets}">
            <div class="gadget">
                <div class="image"><img src="../img/products/${gadget.id}.png"/></div>
                <div class="descr">
                    <div>${gadget.name}</div>
                    <div>${gadget.price}$</div>
                    <div>${gadget.memorySize} GB</div>
                    <input hidden type="number" value="${gadget.id}" class="productId">
                    <input type="number" class="productCount" min="1" value="1">
                    <button class="buyBt">buy</button>
                </div>
            </div>
        </c:forEach>
    </div>
    <tag:pagination/>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
<script src="../js/script.js"></script>
<script src="../js/ajax.js"></script>
</body>
</html>