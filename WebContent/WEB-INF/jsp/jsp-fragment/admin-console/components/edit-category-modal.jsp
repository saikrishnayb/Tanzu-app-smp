<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}" scope="page" />
	<%@ include file="../../../jsp-fragment/admin-console/components/edit-category-modal-content.jsp" %>
	
	


<div  class="save-cancel" >
				<a href="#" class="secondaryLink cancel" tabIndex="-1">No, Cancel</a>
				<a href="#" class="buttonPrimary save-category-edited" tabIndex="-1">Yes, save</a>
				
</div>