<!DOCTYPE html>
<html>
	<head> 
	    <title>SMC Home</title>
	    <%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp" %>
	    
	 
	
		<script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
		<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
		
		<link href="${pageContext.request.contextPath}/css/admin-console/components/category-association.css" rel="stylesheet" type="text/css"/> 
	</head>
	<body>
		<%@ include file="../../../jsp/jsp-fragment/global/header.jsp" %>
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/components/left-nav.jsp" %>

			<div class="leftNavAdjacentContainer">
<c:choose>
    <c:when test="${access eq true}">				
				<!-- table -->
					<div class="table-div" >
						
						<span class="floatRight addRow">
							<a  class="add-category-association">Add Association</a>
							<img src="${commonStaticUrl}/images/add.png" class="centerImage handCursor" alt="Add Row"/>
						</span>
						<table id="category-association-table" >
							<thead>
								<tr>
									<th></th>
									<th>Po Category</th>
									<th>Sub Category</th>
									<th>Status</th>
								</tr>
							</thead>
							<tbody>
							
								<c:forEach items="${categoryAssociation}" var="association" varStatus="count">
								<tr class="category-association-row">
									<td class="editable leftAlign">
									
										<c:choose>
												<c:when test="${association.assocStatus=='1'}">
													<a><img src="${commonStaticUrl}/images/delete.png" class="leftMargin delete-association" /></a>
												</c:when>
												<c:otherwise>
													<a class="activat-association margin-left:30px"><img alt="activate" src="${commonStaticUrl}/images/add.png" class="leftMargin"/>activate</a>
												</c:otherwise>
										</c:choose>
										<!--<c:choose>
												<c:when test="${association.poCatStatus=='A' && association.subCatStatus=='A'}">
													 <input type ="hidden" class="can-activate" id="canActivate" value="1" />
												</c:when>
												<c:otherwise>
													 <input type ="hidden" class="can-activate" id="canActivate" value="0" />
												</c:otherwise>
										</c:choose>-->
										 <input type ="hidden" class="association-id" value="${association.associationId}" />
										
									</td>
									
									<td class="po-cat-name">${association.poCategoryName}
										<input type ="hidden" class="poCategory-id" value="${association.poCategoryId}" /> 
									 </td>
									<td class="sub-cat-name">${association.subCategoryName}
										<input type ="hidden" class="subCategory-id" value="${association.subCategoryId}" />
									 </td>
									<td class="cat-assoc-status">
									<c:choose>
										<c:when test="${association.assocStatus=='1'}">Active</c:when>
										<c:otherwise>Inactive</c:otherwise>
									</c:choose>
									</td>
								</tr>
								</c:forEach>
								
								
								
							</tbody>
						</table>
						
					</div>
					
				
 				<!-- table ends -->
 				
				
 				<!-- table ends -->
 				
 				<!--  Modals -->
	 				<div class="modal" id="add-association-modal">
	 					
					</div>
					
				
					<div id="deactivate-category-association-modal" class="delete-association modal"></div>
					<div id="activate-category-association-modal" class="acivate-association modal"></div>
					<div id="edit-category-modal" class="edit-category modal">	
							
					
					</div>
					<div id="error-modal" class="acivate-association-error modal"></div>
					
	</c:when>
	<c:otherwise>
	       <span style="float: center;color: #CC0000" >
				You are not authorized to see the category association page. Please Contact Support for further assistance.
			</span> 
	 </c:otherwise>
</c:choose>					
					
			</div>
		</div> 
		<input type="hidden" id="common-static-url" value="${commonStaticUrl}"/>
	</body>

	<!-- Scripts -->
	<script src="${context}/js/admin-console/components/category-association.js" type="text/javascript"></script>
</html>