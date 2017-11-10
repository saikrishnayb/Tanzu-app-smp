<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}" scope="page" />
	<%@ include file="../../../jsp-fragment/admin-console/components/edit-sub-category-content.jsp" %>
	
	


<div  class="save-cancel" >
				<a href="#" class="secondaryLink cancel" tabIndex="4">No, Cancel</a>
				<a href="#" class="buttonPrimary save-category-edited" tabIndex="5">Yes, save</a>
				
</div>