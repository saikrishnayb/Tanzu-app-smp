<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../../../global/v2/modal-error-container.jsp"%>
<div class="modal-content col-xs-12" data-modal-title="Create Slots" data-modal-width="300">
	<div class="row modal-body">
		<form id="create-slots-form" class="create-slots-form form-horizontal">
			<div class="error col-xs-12 hidden" id="errorMessage">
				<img src="${commonStaticUrl}/images/warning.png" /> 
				<span class="errorMsg"></span>
			</div>
			<div class="col-xs-12">
				<div class="form-group">
					<div class="col-xs-6 text-right">
						<label for="build-weeks-before" class="control-label">Slot Type:</label>
					</div>
					<div class="col-xs-6">
						<select id="slot-types" name="createSlotType">
							<option value=""></option>
							<c:forEach items="${buildMatrixSlotTypes}" var="type">
          						<option value="${type.slotTypeId}" ${vehicleselected?'selected="selected"':'' } >${type.slotTypeDesc }</option>
          					</c:forEach>
          				</select>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-6 text-right">
						<label for="build-weeks-after" class="control-label">Year:</label>
					</div>
					<div class="col-xs-6">
						<select id="years" name="year">
							<option value=""></option>
							<option value="${currentYear - 2}">${currentYear - 2}</option>
							<option value="${currentYear - 1}">${currentYear - 1}</option>
							<option value="${currentYear}">${currentYear}</option>
							<option value="${currentYear + 1}">${currentYear + 1}</option>
							<option value="${currentYear + 2}">${currentYear + 2}</option>
          				</select>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div class="modal-footer">
		<div class="button-div">
			<a class="buttonPrimary buttonDisabled floatRight"
				id="generate-slots"
				onclick="">Generate</a>
		</div>
	</div>
</div>
<script src="${baseUrl}/js/admin-console/oem-build-matrix/modals/create-slots-modal.js" type="text/javascript"></script>