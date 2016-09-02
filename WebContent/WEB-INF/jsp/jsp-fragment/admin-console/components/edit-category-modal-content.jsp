<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div>
	<form id="editCategory" class="categoryForm">
		
		<div class="error" id="errorMessage">
			<img src="${commonStaticUrl}/images/warning.png" /> <span
			class="errorMsg"></span>
		</div>	
		<div class="category">
			<label class="leftAlign">Category Name<span class="requiredField">*</span></label>
			<input type="text" value="${category.categoryName}" class="category-name input alpha alpha-numeric" name="categoryName"/>
		</div>
		
			<input type="hidden" value="${category.categoryId}" class="po-category-id" name="categoryId"/>
		<div class="category">
			<label class="leftAlign">Description<span class="requiredField">*</span></label>
			<input type="text" value="${category.description}" class="category-description input alpha alpha-numeric" name="description"/>
		</div>
		
		<div class="category">
			<label class="leftAlign">Status</label>
						<c:choose>
							<c:when test="${ category.status eq  'A'}">
							<select id="status" name="status">
								<option value="A" selected>Active</option>
								<option value="I">Inactive</option>
							</select>
							</c:when>
							<c:when test="${category.status eq 'I'}">
							<select id="status" name="status">
								<option value="I" selected>Inactive</option>
								<option value="A">Active</option>
							</select>
							</c:when>
							<c:otherwise>
							<select id="status" name="status" disabled>
								<option value="A">Active</option>
							</select>
							</c:otherwise>
						</c:choose>
			</div>
	</form>
</div>