<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../../../global/v2/modal-error-container.jsp"%>
<div class="modal-content col-xs-12" data-modal-title="Export Slot Maintenance" data-modal-width="400">
	<div class="row modal-body">
		<form id="export-form" class="export-form form-horizontal">
			<div class="error col-xs-12" id="errorMessage" style="display: none">
				<img src="${commonStaticUrl}/images/warning.png" /> 
				<span class="errorMsg"></span>
			</div>
			<div class="col-xs-12">
				<div class="form-group">
					<div class="col-xs-4 text-right">
						<label class="control-label">Slot Type:</label>
					</div>
					<div id="vehicle-desc" class="col-xs-8">
						${slotType.slotTypeDesc}
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-4 text-right">
						<label class="control-label">Year:</label>
					</div>
					<div class="col-xs-8">
						${year}
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-4 text-right">
						<label for="manufacturer" class="control-label">Manufacturer:</label>
					</div>
					<div class="col-xs-8">
						<select id="manufacturer" name="manufacturer">
							<option value=""></option>
							<c:forEach items="${mfrMap}" var="entry">
          						<option value="${entry.key}">${entry.value}</option>
          					</c:forEach>
          				</select>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-4 text-right">
						<label for="plants" class="control-label">Plants:</label>
					</div>
					<div class="col-xs-8">
						<select id="plants" name="plants" style="width:175px;" multiple onchange="enableUpdate()" required disabled>
							<option value=""></option>
          				</select>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div class="modal-footer">
		<div class="button-div">
			<a class="buttonPrimary buttonDisabled floatRight"
				data-slot-type-id="${slotType.slotTypeId}" 
				data-year="${year}"
				id="export-slots-btn" >Export</a>
		</div>
	</div>
</div>
<script src="${baseUrl}/js/admin-console/oem-build-matrix/modals/export-slot-maintenance.js" type="text/javascript"></script>