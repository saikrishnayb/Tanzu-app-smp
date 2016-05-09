<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="template-accordion-container">

<c:forEach items="${template}" var="templates">
<c:forEach items="${templates.templatePoCategorySubCategory}" var="category" varStatus="count">

<div id="po-category-accordion" class="po-category-accordion template-accordion">
	<h3 class="accordian-header" data-accordian-position ='${category.poCategory.categoryId}-${category.subCategory.subCategoryId}'><a href="#">${category.poCategory.categoryName}-${category.subCategory.subCategoryName}</a>
		<span class="delete-image-container">
			<img class="delete-image" src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin delete-category" data-accordian-position='${category.poCategory.categoryId}-${category.subCategory.subCategoryId}'/>
			<input type="hidden" class="po-category" value="${category.poCategory.categoryId}" >
			<input type="hidden" class="sub-category" value="${category.subCategory.subCategoryId}">
			<input type="hidden" class="template-id" value="${templates.templateId}">
		</span>
	</h3>
		<div data-accordian-position='${category.poCategory.categoryId}-${category.subCategory.subCategoryId}'>
			<table class="category-componet-table" id="category-componet-table">
				<thead>
					<tr>
						<th>Component Name</th>
						<th>Visible</th>
						<th>Editable</th>
						<th>Required</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${category.templateComponents}" var="components">
					<tr class="component-row">
						<td>${components.componentName} <input type="hidden" class="tempcomp-id" value="${components.tempCompId}"></td>
						<td><input type="checkbox" class="component-checkbox"  name="component-visible" data-visible <c:if test ="${components.visible eq 1 }" >checked</c:if> /> </td>
						<td><input type="checkbox" class="component-checkbox" name="component-editable" data-editable <c:if test ="${components.editable  eq 1}" >checked</c:if> /></td>
						<td><input type="checkbox" class="component-checkbox" name="component-required" data-required <c:if test ="${components.required eq 1 }" >checked</c:if> /></td>
					</tr>		
					</c:forEach>	
				</tbody>
				</table>
			</div>
		</div>
		</c:forEach>
		</c:forEach>
	</div>		
<script>
$('.po-category-accordion').accordion({
	collapsible: true,
	autoHeight: false
});
</script>
<script>
$('.category-componet-table').dataTable(
		{ //All of the below are optional
			"aaSorting": [[ 1, "desc" ]], //default sort column
		    "bPaginate": true, //enable pagination
		    "bLengthChange": false, //enable change of records per page, not recommended
		    "bFilter": false, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		    "bSort": true, //Allow sorting by column header
		    "bInfo": true, //Showing 1 to 10 of 11 entries
		    "aoColumnDefs": [
		                     {"bSortable": false, "aTargets": [ 0 ]} //stops first column from being sortable
		                     ],
		    "sPaginationType": "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
		    "iDisplayLength": 5 , //number of records per page for pagination
		    "oLanguage": {"sEmptyTable": "&nbsp;"}, //Message displayed when no records are found
		    "fnDrawCallback": function() { //This will hide the pagination menu if we only have 1 page.
			 var paginateRow = $(this).parent().children('div.dataTables_paginate');
			 var pageCount = Math.ceil((this.fnSettings().fnRecordsDisplay()) / this.fnSettings()._iDisplayLength);
			 
			 if (pageCount > 1)  {
			        paginateRow.css("display", "block");
			 } else {
			        paginateRow.css("display", "none");
			 }
			 
			 //This will hide "Showing 1 to 5 of 11 entries" if we have 0 rows.
			 var infoRow = $(this).parent().children('div.dataTables_info');
			 var rowCount = this.fnSettings().fnRecordsDisplay();
			 if (rowCount > 0) {
			        infoRow.css("display", "block");
			 } else {
			        infoRow.css("display", "none");
			 }
			        }
			 } );
</script>
