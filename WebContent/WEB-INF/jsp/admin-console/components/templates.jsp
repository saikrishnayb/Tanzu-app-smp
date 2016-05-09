<!DOCTYPE html>
<html>
	<head> 
	    <title>SMC Home</title>
	    <%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp" %>
	    
	    <script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
		<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
	     <link href="${pageContext.request.contextPath}/css/admin-console/components/vendor-templates.css" rel="stylesheet" type="text/css"/> 
	</head>
	<body>
		<%@ include file="../../../jsp/jsp-fragment/global/header.jsp" %>
		<div id="mainContent" class="borderTop">
			<%@ include file="../../../jsp/jsp-fragment/admin-console/components/left-nav.jsp" %>
			<div class="leftNavAdjacentContainer">
			
				<div style="width: 100%;">
					
					<span id="advancedSearch" class="expandableContent handCursor collapsedImage floatRight" 
					         onclick="toggleContent('searchContent','advancedSearch');">Show Search Criteria</span>
					<div id="searchContent" style="width:100%;height:100px;" class="displayNone tableBorder">
							
						<form id="searchForm" action="vendor-template-search" method="post">
						
							<div class="formColumn floatLeft">
							
								<label class="floatLeft"><span class="requiredField">*</span>Manufacture</label>
								<select class="floatLeft" id="manufacture" name="manufacture">
									<option value="select">Select</option>
									<c:forEach items="${manufactures}" var="manufacture">
										<option value="${manufacture}" ${param.manufacture == manufacture ? 'selected' : ''} >${manufacture}</option>
										
									</c:forEach>
								</select>
								
								<label class="floatLeft">VendorNumber</label>
								<select class="floatLeft" id="vendorNumber" name="vendorNumber">
								<option value="0">Select</option>
									<!-- <c:forEach items="${templates}" var="template">
										<option value="${template.vendorNumber}" ${param.vendorNumber == template.vendorNumber ? 'selected' : ''}>${template.state}-${template.vendorNumber}</option>
									</c:forEach> -->
								</select>
								
								<label class="floatLeft">PoCategory</label>
								<select class="floatLeft" id="poCategory" name="poCategoryId">
								<option value="0">Select</option>
									<c:forEach items="${categories}" var="category">
										<option value="${category.categoryId}" ${param.poCategoryId == category.categoryId ? 'selected' : ''}>${category.categoryName}</option>
									</c:forEach>
								</select>
								
								<label class="floatLeft">SubCategory</label>
								<select class="floatLeft" id="subCategory" name="subCategoryId">
									<option value="0" >Select</option>
								
								</select>
							</div>
						</form>
						<div class="clear">
						
						</div>
						<div class="floatRight search-btn">
							<a class="buttonSecondary floatRight search">Search</a>
							<a class="secondaryLink floatRight reset">Reset</a>
						</div>
						<div class="requiredFieldsCheck" id="errorMessage">
								<img src="${commonStaticUrl}/images/warning.png" /> <span
								class="errorMsg">please enter required fields</span>
						</div>
					</div>
				</div>
			
			
			
				
				<div class="table_div" >
						<span class="clear-both floatRight addRow">
							<!--  <a  class="add-template">Create Template</a>-->
							<a href="./create-template.htm" class="add-template">Create Template</a>
							<img src="${commonStaticUrl}/images/add.png" class="centerImage handCursor" alt="Add Row"/>
						</span>
						<table  id="vendor-table" >
							<thead>
								<tr>
									<th></th>
									<th>Vendor</th>
									<th>Vendor Location</th>
									<th>Defined PO Categories</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${vendorTemplate}" var="vendor">
								<tr class="template-row">
									<td class="editable centerAlign">
										<a class="rightMargin edit-template">Edit</a>
										<a ><img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin delete-template"/></a>
										<input type="hidden" value="${vendor.templateId}" class ="template-id" />
										<input type="hidden" value="${vendor.vendorNumber}" class="vendor-number"/>
										<input type="hidden" value="${vendor.corpCode}" class="corp-code" />
									</td>
						
									<td>${vendor.manufacture}</td>
									
									<td>${vendor.state}Area-${vendor.vendorNumber}</td>
									<td>	
										<c:forEach items="${vendor.templatePoCategorySubCategory}" var="category" varStatus="loop">
										${category.poCategory.categoryName}-${category.subCategory.subCategoryName}
										<c:if test="${!loop.last}">,</c:if>
										</c:forEach>
									</td>
								</tr>
								</c:forEach>
								
							</tbody>
						</table>
					</div>
					<div id="delete-template-modal" class="delete-template modal"></div>
					<div id="edit-template-modal" class="edit-template modal"></div>
					<div id="delete-template-category" class="delete-template-category modal">	
							
					
					 </div>
					
					
			</div>
		</div> 
		
	</body>
	<!-- Scripts -->
	<script src="${context}/js/admin-console/components/templates.js" type="text/javascript"></script>
	
</html>