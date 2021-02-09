<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="col-xs-12">
	<table id="optionTable">
		<thead>
			<tr>
				<th class="viewCol"></th>
				<th>Order Code</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="option" items="${adjustmentOptions}">
			<tr class="option-row">
				<td class="editable centerAlign">
					<a class="rightMargin edit-option">Edit</a>
					<a href="javascript:void(0)" >
						<img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin delete-button"/>
					</a>
					<input class="option-id" type="hidden" value="${option.optionId}"/>
				</td>
				<td>${option.orderCode}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
