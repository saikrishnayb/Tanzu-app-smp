<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="col-xs-12">
	<table id="toleranceTable">
		<thead>
			<tr>
				<th class="viewCol"></th>
				<th>PO Category</th>
				<th>Make</th>
				<th>PO Vendor #</th>
				<th>Cost Tolerance</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="tolerance" items="${tolerances}">
			<tr class="tolerance-row">
				<td class="editable centerAlign">
					<a class="rightMargin edit-tolerance">Edit</a>
					<a href="javascript:void(0)" >
						<img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin delete-button"/>
					</a>
					<input class="tolerance-id" type="hidden" value="${tolerance.toleranceId}"/>
				</td>
				<td><c:out value="${tolerance.poCategory.poCategoryName}" /></td>
				<td><c:out value="${tolerance.mfrCode}" /></td>
				<td><c:out value="${tolerance.poVendorNumber}" /></td>
				<td>$<c:out value="${tolerance.tolerance}" /></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
