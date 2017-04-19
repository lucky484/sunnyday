<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ attribute name="page" required="true" rtexprvalue="true" description="page" type="com.jumper.onlinerecruit.helper.PageWrapper" %>

<!-- Pagination Bar -->
<div class='pagination pagination-centered'>
 	<ul>
		<li class='${page.firstPage ? "disabled": "" }'>
    		<c:if test="${page.firstPage}">
    			<span><fmt:message key="tags.pagination.first_page" /></span>
    		</c:if>
    		<c:if test="${not page.firstPage}">
    			<spring:url var="url" value="${page.url}">
    				<spring:param name="page" value="0"></spring:param>
    				<spring:param name="size" value="${page.size}"></spring:param>
    			</spring:url>
    			<a href="${url}"><fmt:message key="tags.pagination.first_page" /></a>
    		</c:if>
    	</li>
		<li class='${page.hasPreviousPage ?  "" : "disabled" }'>
    		<c:if test="${not page.hasPreviousPage}">
    			<span><fmt:message key="tags.pagination.prev_page" /></span>
    		</c:if>
    		<c:if test="${page.hasPreviousPage}">
    			<spring:url var="url" value="${page.url}">
    				<spring:param name="page" value="${page.number-2}"></spring:param>
    				<spring:param name="size" value="${page.size}"></spring:param>
    			</spring:url>
    			<a href="${url}"><fmt:message key="tags.pagination.prev_page" /></a>
    		</c:if>
    	</li>
    
    <c:forEach var="item" items="${page.items}">
    	<li class='${item.current ? "active": "" }'>
    		<c:if test="${item.current}">
    			<span>${item.number}</span>
    		</c:if>
    		<c:if test="${not item.current}">
    			<spring:url var="url" value="${page.url}">
    				<spring:param name="page" value="${item.number-1}"></spring:param>
    				<spring:param name="size" value="${page.size}"></spring:param>
    			</spring:url>
    			<a href="${url}">${item.number}</a>
    		</c:if>
    	</li>
    </c:forEach>
    
    	<li class='${page.hasNextPage ?  "" : "disabled" }'>
    		<c:if test="${not page.hasNextPage}">
    			<span><fmt:message key="tags.pagination.next_page" /></span>
    		</c:if>
    		<c:if test="${page.hasNextPage}">
    			<spring:url var="url" value="${page.url}">
    				<spring:param name="page" value="${page.number}"></spring:param>
    				<spring:param name="size" value="${page.size}"></spring:param>
    			</spring:url>
    			<a href="${url}"><fmt:message key="tags.pagination.next_page" /></a>
    		</c:if>
    	</li>
		<li class='${page.lastPage ? "disabled": "" }'>
    		<c:if test="${page.lastPage}">
    			<span><fmt:message key="tags.pagination.last_page" /></span>
    		</c:if>
    		<c:if test="${not page.lastPage}">
    			<spring:url var="url" value="${page.url}">
    				<spring:param name="page" value="${page.totalPages-1}"></spring:param>
    				<spring:param name="size" value="${page.size}"></spring:param>
    			</spring:url>
    			<a href="${url}"><fmt:message key="tags.pagination.last_page" /></a>
    		</c:if>
    	</li>
	</ul>
</div>
