<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../../../global/v2/modal-error-container.jsp"%>
<div class="modal-content col-xs-12" data-modal-title="Release Unit" data-modal-width="400">
	<div class="row modal-body">
		<form id="release-units-form" class="release-units-form form-horizontal">
			<div class="error col-xs-12" id="errorMessage" style="display: none">
				<img src="${commonStaticUrl}/images/warning.png" /> 
				<span class="errorMsg"></span>
			</div>
			<div class="col-xs-12">
				<div class="form-group">
					<div class="col-xs-5 text-right">
						<label class="control-label">Region</label>
					</div>
					<div class="col-xs-7">
						${region} - ${regionDesc}
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-5 text-right">
						<label class="control-label">Plant</label>
					</div>
					<div class="col-xs-7">
						${bodyPlant.plantManufacturer} - ${bodyPlant.city}, ${bodyPlant.state}
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-5 text-right">
						<label class="control-label">Slot Date</label>
					</div>
					<div class="col-xs-7">
						${slotDate.formattedSlotDate}
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-5 text-right">
						<label class="control-label">Select Unit</label>
					</div>
					<div class="col-xs-7">
						<select id="release-units" name="releaseUnits" multiple onchange="enableUpdate()" class="common-form-control">
							<c:forEach items="${slotReservations}" var="slotReservation">
								<option value="${slotReservation.slotReservationId}">${slotReservation.unitNumber} - (Build ${slotReservation.runId})</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div class="modal-footer">
		<div class="button-div">
			<a class="buttonPrimary buttonDisabled floatRight"
				id="release-units-btn"
				data-region="${region}" 
				data-plant-id="${bodyPlant.plantId}" 
				data-slot-date-id="${slotDate.slotDateId}"
				>Release</a>
		</div>
	</div>
</div>
<script src="${baseUrl}/js/admin-console/oem-build-matrix/modals/release-units-modal.js" type="text/javascript"></script>