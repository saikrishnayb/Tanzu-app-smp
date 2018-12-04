<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div id="edit-org-html">
<%@ include file="org-form.jsp"%>
<div class="edit-buttons">
	<a class="secondaryLink cancel" tabIndex="-1">Cancel</a> 
	<a id="save-org-edit" class="buttonPrimary save" tabIndex="-1">Save</a>
	<div class="error-messages-container displayNone">
		<img src="${commonStaticUrl}/images/warning.png"></img>
		<span class=errorMsg>One or more required fields not filled in correctly.</span>
	</div>
</div>
</div>