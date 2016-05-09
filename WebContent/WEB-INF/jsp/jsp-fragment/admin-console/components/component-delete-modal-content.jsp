		<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		
		<p>Are you sure you want to delete the visibility</p>
							<div>
								<span>
									<ul>
									
										<li id="component" >
									
										<input id="delete-componentId" type="hidden" value=${componentVisibility.componentId} />
										<input id="delete-categoryId" type="hidden" value=${componentVisibility.category.categoryId} />
										<input id="delete-subCategoryId" type="hidden" value=${componentVisibility.subCategory.subCategoryId} />
										<input id="delete-isComponentVehicle" type="hidden" value=${componentVisibility.isComponentVehicle} />
											${componentVisibility.componentName} category=${componentVisibility.category.categoryName} subcategory=${componentVisibility.subCategory.subCategoryName}
										</li>
										
									</ul>
								</span>
							</div>
							<div style="position:absolute;bottom:3px;right:5px;">
								<a href="#" class="secondaryLink cancel" tabIndex="-1">No, Cancel</a>
								<a href="#" class="buttonPrimary delete" tabIndex="-1">Yes, Delete</a>
							</div>