<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<section class="row">
	<ul class="breadcrumb">
		<c:forEach var="entry" items="${sessionScope.currentBreadCrumb}" varStatus="index">
			<c:choose>
				<c:when test="${entry.currentPage == true}">
					<c:if test="${index.index!=0}">
						<c:if test="${entry.belong!=''}">
						<li><fmt:message key="${entry.belong}" /></a></li>
						</c:if>
					
					</c:if>
					<c:if test="${index.index==0}"><i class="fa fa-home"></i></c:if>
					<li class="active"><fmt:message key="${entry.label}" /></li>

				</c:when>
				<c:otherwise>
					<li><a href="${entry.url}" onclick="activeSub(0);">
						<c:if test="${index.index==0}"><i class="fa fa-home"></i></c:if>
						<fmt:message key="${entry.label}" /></a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</ul>
</section>
