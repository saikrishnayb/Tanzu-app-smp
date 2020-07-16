<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="modalName" value="Update Reservation" />
<div class="modal-content col-xs-12" data-modal-title="${modalName}" data-modal-width="420">
	<div class="row modal-body">
		<form id="edit-reservation-form" class="edit-reservation-form form-horizontal" data-save-attr-id="">
			<input type="hidden" autofocus="autofocus" class="display-none">
			<div class="error col-xs-12" id="errorMessage" style="display: none">
				<img src="${commonStaticUrl}/images/warning.png" /> 
				<span class="errorMsg"></span>
			</div>
			<table id="update-reservation-table">
				<tbody>
					<c:if test="${productionSlotResult.reservationStatus eq 'P'}">
						<tr class="row" >
							<td class="col-xs-4"><span class="formLbl">Unit</span></td>
							<td class="col-xs-8">
								<input type="text" id="unit-number" value="${productionSlotResult.unitNumber}">	
							</td>
						</tr>
					</c:if>
					<tr class="row" >
						<td class="col-xs-4"><span class="formLbl">Plant</span></td>
						<td class="col-xs-8">
							<select id="plant-dropdown">
								<option value="">PLANT 1</option>
								<option value="">PLANT 2</option>
								<option value="">PLANT 3</option>
							</select>	
						</td>
					</tr>
					<tr class="row" >
						<td class="col-xs-4"><span class="formLbl">Date</span></td>
						<td class="col-xs-8">
							<input class="production-date  date-picker numeric numeric-jquery-date advanced-date width-75"
										required value="" />	
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<div class="modal-footer">
		<div class="save-reservation">
			<a class="buttonPrimary floatRight"
				id="update-reservation" data-save-attr-id="" onclick="">Save</a>
		</div>
	</div>
</div>
<script
	src="${baseUrl}/js/admin-console/oem-build-matrix/modals/edit-dimension.js"
	type="text/javascript"></script>
