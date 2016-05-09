<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
	<form id="editSubCategory" class="categoryForm">
		
		<div class="error" id="errorMessage">
			<img src="${commonStaticUrl}/images/warning.png" /> <span
			class="errorMsg"></span>
		</div>
		
		<div class="category">
			<label>Category Name<span class="requiredField">*</span></label>
			<input type="text" value="${category.subCategoryName}" class="category-name input alpha alpha-name" name="subCategoryName"/>
		</div>
		
		<input type="hidden" value="${category.subCategoryId}" class="category-id" name="subCategoryId"/>
		
		<div class="category">
			<label>Description<span class="requiredField">*</span></label>
			<input type="text" value="${category.description}" class="category-description input alpha alpha-name" name="description"/>
		</div>
		
		<div class="category">
			<label>Status</label>
			<select id="status" name="status">
					<c:choose>
						<c:when test="${category.status eq 'A'}">
							<option value="A" selected>Active</option>
							<option value="I">Inactive</option>
						</c:when>
						<c:when test="${category.status eq 'I'}">
							<option value="A">Active</option>
							<option value="I" selected>Inactive</option>
						</c:when>
						<c:otherwise>
							<option value="A">Active</option>
						</c:otherwise>
					</c:choose>
			
				</select>
			</div>
	</form>
	
		
	 		 