<!DOCTYPE html>
<html>
	<head> 
	    <title>SMC Home</title>
	    <%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp" %>
	    
	 
	
		<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
		<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
		
		<link href="${pageContext.request.contextPath}/css/admin-console/components/visibility-by-category.css" rel="stylesheet" type="text/css"/> 
	</head>
	<body>
		<%@ include file="../../../jsp/jsp-fragment/global/header.jsp" %>
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/components/left-nav.jsp" %>
			
			<div class="leftNavAdjacentContainer">
				
				<!-- table -->
					<div class="table_div" >
					
						<span class="floatRight addRow">
							<a  class="add-visibility">Add Visibility</a>
							<img src="${commonStaticUrl}/images/add.png" class="centerImage handCursor" alt="Add Row"/>
						</span>
						
						<table class="component_table" id="component-table" >
							<thead>
								<tr>
									<th></th>
									<th>Component Name</th>
									<th>PO Category</th>
									<th>Sub Category</th>
									<th>Visible</th>
								</tr>
							</thead>
							<tbody>
							
								<c:forEach items="${componentList}" var="component" varStatus="count">
								<tr class="component-row">
									<td class="editable centerAlign">
										<a>
											<img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin delete-visibility" data-component-row='${count.index}'/>
										</a>
										 <input type ="hidden" class="component-id" value="${component.componentId}" />
										 <input type="hidden"  class="is-component-vehicle" value="${component.isComponentVehicle}"/>
										 <input type="hidden"  class="component-category" value="${component.category.categoryId}"/>
										 <input type="hidden"  class="component-sub-category" value="${component.subCategory.subCategoryId}"/>
									</td>
									 
									
								
									
									<td>${component.componentName}</td>
									 
									 <td class="component-category-name">
										<%-- <c:forEach items="${component.category}" var="cat"> --%>
										${component.category.categoryName}
										<%-- </c:forEach> --%>
									</td>
									
									
									 <td class="component-sub-category-name">${component.subCategory.subCategoryName}</td> 
									 <td>
										<c:choose>
											<c:when test="${component.visibility eq '1'}">YES
											</c:when>
									   </c:choose>
								   </td> 
								</tr>
								</c:forEach>
								
								
								
							</tbody>
						</table>
						
					</div>
					
				
 				<!-- table ends -->
					
				
 				<!-- table ends -->
 				
 				<!-- visibility Modal -->
	 				<div class="modal" id="visibility-modal">
	 					
					</div>
					
				<!-- delete Modal -->	
				
					<div id="delete-component-modal" class="delete-confirm modal">	
							
					
					</div>
			</div>
		</div> 
		<input type="hidden" id="common-static-url" value="${commonStaticUrl}"/>
	</body>
	<!-- Scripts -->
	<script src="${context}/js/admin-console/components/visibility-by-category.js" type="text/javascript"></script>
</html>