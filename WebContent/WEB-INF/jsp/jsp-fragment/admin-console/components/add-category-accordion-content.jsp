<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div>

<c:forEach items="${category}" var="category" >
 <div id="category-accordion" class="po-category-accordion added-categories-accordion">


	<h3 class="category-accordian-header" data-accordian-position ='${category.poCategory.categoryId}-${category.subCategory.subCategoryId}'><a  href="#">${category.poCategory.categoryName}-${category.subCategory.subCategoryName}</a>
		<span class="delete-image-container">
				<img class="delete-icon" src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin delete-category" data-accordian-position='${category.poCategory.categoryId}-${category.subCategory.subCategoryId}' />
		<input type="hidden" class="po-category" value="${category.poCategory.categoryId}" >
		<input type="hidden" class="sub-category" value="${category.subCategory.subCategoryId}" >
		<input type="hidden" class="template-id" value="${category.templateId}">
		</span>
		
	</h3>
											
		<div data-accordian-position='${category.poCategory.categoryId}-${category.subCategory.subCategoryId}' class="table-div" id="component-table">
								
			<table class="category-component-table" id="category-table" >
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
					<tr class="component-category-row">
						<td>${components.componentName} <input class="component-id" type=hidden value="${components.componentId}"/></td>
						<td><input type="checkbox" class="component-checkbox" name="component-visible" data-visible /></td>
						<td><input type="checkbox" class="component-checkbox" name="component-editable" data-editable  /></td>
						<td><input type="checkbox" class="component-checkbox" name="component-required" data-required  />
							<input type="hidden" class="data-type" value="${components.dataType}"/>
						</td>
					</tr>		
					</c:forEach>	
				</table>
			</div>

			
		</div>
	</c:forEach>
				
		
						
						
		
<script>
	$('.po-category-accordion').accordion({
		active: 0,
		clearStyle: true, 
		collapsible: true,
		autoHeight: false
		
	});
	
</script>
<script>
$('.category-component-table').dataTable(
		{ //All of the below are optional
			"aaSorting": [[ 1, "desc" ]], //default sort column
		    "bPaginate": true, //enable pagination
		    "bLengthChange": false, //enable change of records per page, not recommended
		    "bFilter": false, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		    "bSort": true, //Allow sorting by column header
		    "bInfo": false, //Showing 1 to 10 of 11 entries
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