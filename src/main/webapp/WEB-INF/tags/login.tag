<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${pageContext.request.locale}"/>
<fmt:setBundle basename="resources"/>
<c:set var="user" scope="page" value="${sessionScope._user}"/>
<div class="loginblock">
    <c:if test="${empty user}">
        <form action="/login" method="post">
            <label for="emailTag"><fmt:message key="header.login"/> </label>
            <input type="text" id="emailTag" name="email">
            <label for="passwordTag"><fmt:message key="header.password"/> </label>
            <input type="password" id="passwordTag" name="password">
            <input type="submit" value="<fmt:message key="header.login.button"/> " class="bt">
        </form>
    </c:if>
    <c:if test="${not empty user}">
        <form action="/logout" method="post">
            <img src="/temp/${user.email}.png" id="pic" alt="pic">
            <label>${user.name}</label>
            <label>${user.surname}</label>
            <input type="submit" value="log out" class="bt">
        </form>
    </c:if>
</div>