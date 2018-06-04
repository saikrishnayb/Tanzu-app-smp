<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach items="${exception}" var="exception">
	<div class="line">
		<label>Component Name:</label><label>${exception.componentName}</label>
	</div>
	<div class="blank"></div>
	<div class="line">
		<div id="list-div">
			<table class='dataTable'>
				<thead>
					<tr>
						<th>PO Category-Sub Category</th>
						<th>Vendor Name</th>
						<th class="addressHeader" colspan="3" style="text-align: center;">Address</th>
					</tr>
					
				</thead>
				<tbody>
					<input type="hidden" id="exception-id-modal" value="${exception.exceptionId}"/>
                     <c:forEach items="${exception.poCategoryGroups}" var="poCategoryGroup" varStatus="rowIndex">
                     <tr class="${rowIndex.index  % 2 == 0 ? 'odd' : 'even'}">
                       	<td>${poCategoryGroup.poCategorySubcategory}</td>
                       	<td>${poCategoryGroup.vendor.vendorName}</td>
                       	<td>${poCategoryGroup.vendor.city}</td>
                    	<td>${poCategoryGroup.vendor.state}</td>
                    	<td>${poCategoryGroup.vendor.zipCode}</td>
                      </tr>
                   
                     </c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div class="blank"></div>
	<div class="line">
		<label>To be Provided By:</label>${exception.providerVendor.vendorName}
	</div>
	<input type="hidden" id="exception-id-modal" value="${exception.exceptionId}"/>
</c:forEach>
<div class="blank"></div>
<div class="buttonLine">
	<div class="right-buttons">
		<a class="cancel">Cancel</a>
		<a class="buttonPrimary delete-global-exception-confirm">Yes, Delete</a>
	</div>
</div>