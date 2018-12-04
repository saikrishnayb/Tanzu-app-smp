<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
		<c:forEach items="${vendorTemplates}" var="location">
							<div class="width-half initial-accordion templates-accordion accordion-headers">
								<h3>
									<span></span> 
									<a href="#" class="initial-accordion">${location.manufacturer} - ${location.city}, ${location.state} - ${location.vendorNumber} Templates</a>
								</h3>
								<div>
						<c:forEach items="${location.templatePoCategorySubCategory}" var="template">
							<div class="width-half initial-accordion templates-accordion component-accordions">
								<h3>
									<span></span> 
									<a href="#" class="initial-accordion">${template.poCategory.categoryName} - ${template.subCategory.subCategoryName}</a>
								</h3>
								<div>
									<div class="full-width">
										<table class="po-cat-table" >
											<thead>
												<tr>
													<th>Component Name</th>
													<th>Editable</th>
													<th>Visible</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${template.templateComponents}" var="component">
													<tr>
														<td>${component.componentName}</td>
														<td><input type="checkbox" 
																<c:if test="${component.visible eq '1'}">
																		checked
																</c:if> onclick="return false"/>
														</td>
														<td><input type="checkbox"
																<c:if test="${component.editable eq '1'}">
																	checked
																</c:if> onclick="return false"/>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</c:forEach>
						</div>
						</div>
					</c:forEach>
<script src="${baseUrl}/js/admin-console/security/templates-accordion.js" type="text/javascript"></script>