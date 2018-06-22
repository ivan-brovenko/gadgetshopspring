<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="languages">
    <ul>
        <li><a href="${
        (empty pageContext.request.queryString)?"?lang=en":
        (pageContext.request.queryString.contains("lang"))?
        requestScope.servletName.concat(pageContext.request.queryString.replaceAll("lang=..","?lang=en")):
        requestScope.servletName.concat("?lang=en&").concat(pageContext.request.queryString)}">English</a></li>
        <li><a href="${
        (empty pageContext.request.queryString)?"?lang=ru":
        (pageContext.request.queryString.contains("lang"))?
        requestScope.servletName.concat(pageContext.request.queryString.replaceAll("lang=..","?lang=ru")):
        requestScope.servletName.concat("?lang=ru&").concat(pageContext.request.queryString)}">Russian</a></li>
    </ul>
</div>