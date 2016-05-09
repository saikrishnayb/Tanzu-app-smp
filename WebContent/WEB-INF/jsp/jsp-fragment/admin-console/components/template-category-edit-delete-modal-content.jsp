<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		
		<p>Are you sure you want to delete this template category</p>
							<div>
								<span>
									<ul>
									
										<li id="category" >
									${categories.poCategory.categoryName}-${categories.subCategory.subCategoryName}
									
									<input type="hidden" value="${categories.templateId}" name="templateId"/>
									<input type="hidden"  value="${categories.poCategory.categoryId}" name="poCategoryId" />
									<input type="hidden"  value="${categories.subCategory.subCategoryId}" name="subCategoryId"  />
									
									
										</li>
										
									</ul>
								</span>
							</div>
							<div style="position:absolute;bottom:3px;right:5px;">
								<a href="#" class="secondaryLink cancel" tabIndex="-1">No, Cancel</a>
								<a href="#" class="buttonPrimary delete" tabIndex="-1">Yes, Delete</a>
							</div>