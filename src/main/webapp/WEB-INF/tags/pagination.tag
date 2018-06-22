<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pagination">
    <c:if test="${currentPage != 1}">
        <span class="page-item">
            <a class="page-link"
               href="/products?${urlOfAttributes}&currentPage=${currentPage-1}">Previous</a>
        </span>
    </c:if>
    <c:forEach begin="1" end="${nOfPages}" var="i">
        <c:choose>
            <c:when test="${currentPage eq i}">
                <span class="page-item active"><a class="page-link" id="currentPage">${i}</a>
                </span>
            </c:when>
            <c:otherwise>
                <span class="page-item">
                    <a class="page-link"
                       href="/products?${urlOfAttributes}&currentPage=${i}">${i}</a>
                </span>
            </c:otherwise>
        </c:choose>
    </c:forEach>

    <c:if test="${currentPage lt nOfPages}">
        <span class="page-item">
            <a class="page-link"
               href="/products?=${urlOfAttributes}&currentPage=${currentPage+1}">Next</a>
        </span>
    </c:if>
</div>