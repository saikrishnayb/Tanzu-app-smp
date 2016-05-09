<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}"  scope="page" />
<div>

	<div class="add-categories">
		<label  class="leftAlign">PoCategory</label>
		<select id="po-category">
			<option value=''>Select</option>
			<c:forEach items="${categories}" var="category">
				<option value="${category.categoryId}">${category.categoryName}</option>
			</c:forEach>
		</select>
	</div>
	<div class="add-categories">
	
		<fieldset class="sub-category" id="sub-category">
			<legend>SubCategory</legend>
			
		</fieldset>
	</div>
	<div class="error" id="errorMessage">
			<img src="${commonStaticUrl}/images/warning.png" /> <span
			class="errorMsg"></span>
	</div>
<div class="save">
	<a href="#" class="secondaryLink cancel" tabIndex="-1">Cancel</a>
	<a href="#" class="buttonPrimary save-category" tabIndex="-1">Add</a>
</div>
</div>
<script src="${context}\js\admin-console\components\po-cat-modal.js" type="text/javascript"></script>

