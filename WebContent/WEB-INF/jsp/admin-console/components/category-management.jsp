<!DOCTYPE html>
<html>
<head>
<title>SMC Home</title>
<%@ include
	file="../../../jsp/jsp-fragment/global/default-head-block.jsp"%>



<script src="${commonStaticUrl}/js/jquery.dataTables.min.js"
	type="text/javascript"></script>
<link href="${commonStaticUrl}/css/jquery.dataTables.css"
	rel="stylesheet" type="text/css" />

<link
	href="${baseUrl}/css/admin-console/components/category-management.css"
	rel="stylesheet" type="text/css" />
</head>
<body>
	<%@ include file="../../../jsp/jsp-fragment/global/header.jsp"%>
	<div id="mainContent" class="borderTop">
		<%@ include
			file="../../../jsp/jsp-fragment/admin-console/components/left-nav.jsp"%>

		<div class="leftNavAdjacentContainer">

			<!-- table -->
			<div class="row">
				<div class="col-xs-6">
					<h1>Po Category</h1>
					<table class="category-table" id="po-category-table">
						<thead>
							<tr>
								<th style="display: none"></th>
								<th>Category Name</th>
								<th>Description</th>
								<th>Status</th>
							</tr>
						</thead>
						<tbody>

							<c:forEach items="${categoryList}" var="category"
								varStatus="count">
								<tr class="category-row">
									<td class="editable centerAlign po-cat-id" style="display: none">
										<input type="hidden" class="category-id" value="${category.categoryId}" />
									</td>
									<td class="po-cat-name">${category.categoryName}</td>
									<td>${category.description}</td>
									<td><c:choose>
											<c:when test="${category.status eq 'A'}">Active</c:when>
											<c:otherwise>Inactive</c:otherwise>
										</c:choose></td>
								</tr>
							</c:forEach>



						</tbody>
					</table>

				</div>


				<!-- table ends -->
				<div class="col-xs-6">
					<h1>Sub Category</h1>

					<span class="floatRight addRow"> 
						<a class="add-sub-category">Add Sub-Category
							<img src="${commonStaticUrl}/images/add.png" class="centerImage handCursor" alt="Add Row" /> 
						</a> 
					</span>

					<table class="category-table" id="sub-category-table">
						<thead>
							<tr>
								<th></th>
								<th>Sub-Category Name</th>
								<th>Description</th>
								<th>Status</th>
							</tr>
						</thead>
						<tbody>

							<c:forEach items="${subCategoryList}" var="subCategory"
								varStatus="count">
								<tr class="sub-category-row">
									<td class="editable centerAlign sub-cat-id"><a
										class="rightMargin edit-category">Edit</a> <a
										style="display: none"> <img
											src="${commonStaticUrl}/images/delete.png"
											class="centerImage rightMargin delete-category"
											data-component-row='${count.index}' />
									</a> <input type="hidden" class="sub-category-id"
										value="${subCategory.subCategoryId}" /></td>

									<td class="sub-cat-name">${subCategory.subCategoryName}</td>
									<td>${subCategory.description}</td>
									<td><c:choose>
											<c:when test="${subCategory.status eq 'A'}">Active</c:when>
											<c:otherwise>Inactive</c:otherwise>
										</c:choose></td>
								</tr>
							</c:forEach>



						</tbody>
					</table>

				</div>



				<!-- table ends -->

				<!--  Modals -->
				<div class="modal" id="add-category-modal"></div>
				<div class="modal" id="add-sub-category-modal"></div>

				<div id="delete-pocategory-modal" class="delete-category modal"></div>

				<div id="delete-subcategory-modal" class="delete-category modal"></div>

			</div>
			<div id="edit-category-modal" class="edit-category modal"></div>
			<div id="edit-sub-category-modal" class="edit-category modal">


			</div>

		</div>
	</div>
	</div>

</body>
<!-- Scripts -->
<script
	src="${baseUrl}/js/admin-console/components/category-management.js"
	type="text/javascript"></script>
</html>