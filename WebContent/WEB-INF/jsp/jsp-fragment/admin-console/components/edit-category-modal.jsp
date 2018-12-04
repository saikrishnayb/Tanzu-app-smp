<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ include file="../../../jsp-fragment/admin-console/components/edit-category-modal-content.jsp" %>
	
	


<div  class="save-cancel" >
				<a href="#" class="secondaryLink cancel" tabIndex="-1">No, Cancel</a>
				<a href="#" class="buttonPrimary save-category-edited" tabIndex="-1">Yes, save</a>
<div class="error-messages-container displayNone">
					<img src="${commonStaticUrl}/images/warning.png"></img>
					<span class=errorMsg></span>
				</div>				
</div>