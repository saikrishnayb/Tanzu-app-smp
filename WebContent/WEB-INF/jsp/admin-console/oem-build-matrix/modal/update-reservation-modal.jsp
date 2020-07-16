<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="modalName" value="Update Reservation" />
<div class="modal-content col-xs-12" data-modal-title="${modalName}" data-modal-width="373">
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
								<input type="text" class="update-res-input" id="unit-number" value="${productionSlotResult.unitNumber}">	
							</td>
						</tr>
					</c:if>
					<tr class="row" >
						<td class="col-xs-4"><span class="formLbl">Plant</span></td>
						<td class="col-xs-8">
						<c:if test="${productionSlotResult.reservationStatus eq 'P'}">
							<select id="plant-dropdown"  class="update-res-input">
								<c:forEach items="${plantList}" var="plants">
  	       						 <option value="${plants}">${plants}</option>
  	      						</c:forEach>
							</select>	
						</c:if>
						<c:if test="${productionSlotResult.reservationStatus eq 'E'}">
							<select id="plant-dropdown"  class="update-res-input">
								<c:forEach items="${productionSlotList}" var="plants">
  	       						 <option value="${plants}">${plants}</option>
  	      						</c:forEach>
							</select>	
						</c:if>
						</td>
					</tr>
					<tr class="row" >
						<td class="col-xs-4"><span class="formLbl">Date</span></td>
						<td class="col-xs-8">
							<input class="production-date date-picker numeric numeric-jquery-date advanced-date update-res-input"
										required value="" />	
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<div class="modal-footer">
		<div id="save-reservation-div">
			<a class="buttonPrimary floatRight" id="save-reservation">Save</a>
		</div>
	</div>
</div>
<script
	src="${baseUrl}/js/admin-console/oem-build-matrix/modals/edit-dimension.js"
	type="text/javascript"></script>