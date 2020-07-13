<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../../../global/v2/modal-error-container.jsp"%>
<div class="modal-content col-xs-12" data-modal-title="Import Slot Maintenance" data-modal-width="400">
	<div class="row modal-body">
		<form id="import-form" class="import-form form-horizontal" action="./import-slot-maintenance.htm" method="POST" enctype="multipart/form-data">
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
						<label for="sample-input">
							<input id="file-name" class="file-input-textbox" readonly="readonly" tabindex="-1"/>
						</label>
					</div>
					<div class="col-xs-8 text-right">			
						<span class="file-input-span">
							<a href="#" class="buttonSecondary file-input-button">Browse</a>
							<input type="file" class="fileInputHidden" name="file" id="sample-input"
							          onchange="javascript: document.getElementById('file-name').value = this.value" tabindex="-1"/>		
						</span>	
					</div>
				</div>
			</div>
			<input type ="hidden" id="slotTypeId" name="slotTypeId" value="${slotType.slotTypeId}"/>
			<input type ="hidden" id="year" name="year" value="${year}"/>
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