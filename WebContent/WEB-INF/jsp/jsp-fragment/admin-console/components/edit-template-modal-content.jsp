<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<span class="floatRight addRow add-category">
		<a >Add Category</a>
		<img src="${commonStaticUrl}/images/add.png" class="centerImage handCursor" alt="Add Row"/>
	</span>
	<%@ include file="../../../jsp-fragment/admin-console/components/categories-components-accordion-content.jsp" %>
	<%@ include file="../../../jsp-fragment/admin-console/components/delete-add-category-modal.jsp" %>
		<div  class="save-cancel-add-category" >
				<a href="#" class="secondaryLink cancel" tabIndex="-1">No, Cancel</a>
				<a href="#" class="buttonPrimary save-edited" tabIndex="-1">Yes, save</a>
		</div>



