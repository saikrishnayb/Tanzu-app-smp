<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../../../global/v2/modal-error-container.jsp"%>
<div class="modal-content col-xs-12" data-modal-title="Import Slot Maintenance" data-modal-width="400">
	<div class="row modal-body">
		<form id="import-form" class="import-form form-horizontal">
			<div class="error col-xs-12" id="errorMessage" style="display: none">
				<img src="${commonStaticUrl}/images/warning.png" /> 
				<span class="errorMsg"></span>
			</div>
			<div class="col-xs-12">
				
				<!-- modal content -->
				
			</div>
		</form>
	</div>
	<div class="modal-footer">
		<div class="button-div">
			<a class="buttonPrimary buttonDisabled floatRight"
				id="import-slots-btn" >Import</a>
		</div>
	</div>
</div>
<script src="${baseUrl}/js/admin-console/oem-build-matrix/modals/import-slot-maintenance.js" type="text/javascript"></script>