<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="type" required="false" rtexprvalue="false"
	description="Type of the message; success,info,error"%>
<%@ attribute name="msg" required="false" rtexprvalue="true" %>
<%@ attribute name="center" required="false" rtexprvalue="true" %>

<c:if test="${!empty msg}">
	<script type="text/javascript">
		$(document).ready(function(){
			$.notify("${type}", "${msg}", "${center}");
		}).show();
	</script>
</c:if>