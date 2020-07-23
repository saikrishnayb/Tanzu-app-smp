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
					<input type="hidden" id="reservation-status" value="${productionSlotResult.reservationStatus}">
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
								<option value="">Please Select</option>
								<c:forEach items="${plantList}" var="plants">
								 <c:set var="isPlantSelected">${productionSlotResult.productionSlot eq plants.productionSlot}</c:set>
		          					<option value="${plants.plantId}" ${isPlantSelected?'selected="selected"':'' } >${plants.productionSlot}</option>
  	      						</c:forEach>
							</select>	
						</c:if>
						<c:if test="${productionSlotResult.reservationStatus eq 'E'}">
							<select id="plant-dropdown"  class="update-res-input">
								<option value="">Please Select</option>
								<c:forEach items="${productionSlotList}" var="plants">
									<c:set var="isPlantSelected">${productionSlotResult.productionSlot eq plants.productionSlot}</c:set>
		          					<option value="${plants.plantId}" ${isPlantSelected?'selected="selected"':'' } >${plants.productionSlot}</option>
  	      						</c:forEach>
							</select>	
						</c:if>
						</td>
					</tr>
					<tr id="production-date-div" class="row <c:if test="${productionSlotResult.productionSlot==''}">hideOption</c:if>" >
						<td class="col-xs-4"><span class="formLbl">Date</span></td>
						<td class="col-xs-8">
							<input class="production-date date-picker numeric numeric-jquery-date advanced-date update-res-input"
										required value="${productionSlotResult.productionSlotDate}" readonly="readonly" />	
							<input type="hidden" id="slot-dates" value='${slotDates}'>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<div class="modal-footer">
		<div id="save-reservation-div">
			<a class="buttonPrimary floatRight buttonDisabled" id="save-reservation">Save</a>
		</div>
	</div>
</div>
