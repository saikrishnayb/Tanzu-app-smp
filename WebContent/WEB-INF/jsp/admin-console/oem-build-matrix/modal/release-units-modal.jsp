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
					<div class="col-xs-6">
						<label class="control-label">Plant</label>
					</div>
					<div class="col-xs-6">
						MORGAN - RYDAL, GA
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-6">
						<label class="control-label">Slot</label>
					</div>
					<div class="col-xs-6">
						02/18/2019
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-6">
						<label class="control-label">Select Unit</label>
					</div>
					<div class="col-xs-6">
						<select id="release-unit" class="common-form-control">
							<option value=""></option>
							<option value="">377681</option>
							<option value="">377682</option>
							<option value="">377683</option>
							<option value="">377684</option>
							<option value="">377685</option>
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
				onclick="">Release</a>
		</div>
	</div>
</div>
<script src="${baseUrl}/js/admin-console/oem-build-matrix/modals/update-build-params.js" type="text/javascript"></script>