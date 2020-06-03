<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../../../global/v2/modal-error-container.jsp"%>
<div class="modal-content col-xs-12" data-modal-title="Update Build Parameters" data-modal-width="400">
	<div class="row modal-body">
		<form id="update-build-params-form" class="update-build-params-form form-horizontal" data-build-id="${summary.buildId}">
			<div class="error col-xs-12" id="errorMessage" style="display: none">
				<img src="${commonStaticUrl}/images/warning.png" /> 
				<span class="errorMsg"></span>
			</div>
			<div class="col-xs-12">
				<div class="form-group">
					<div class="col-xs-6">
						<label for="build-weeks-before" class="control-label">Build Weeks Before:</label>
					</div>
					<div class="col-xs-6">
						<input id="build-weeks-before" class="common-form-control numbers-only numeric numeric-whole" value="${summary.maxWeeksBefore}"/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-6">
						<label for="build-weeks-after" class="control-label">Build Weeks After:</label>
					</div>
					<div class="col-xs-6">
						<input id="build-weeks-after" class="common-form-control numbers-only numeric numeric-whole" value="${summary.maxWeeksAfter}"/>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div class="modal-footer">
		<div class="button-div">
			<a class="buttonPrimary buttonDisabled floatRight"
				id="update-build-params-btn" data-build-id="${summary.buildId}"
				onclick="">Save</a>
		</div>
	</div>
</div>
<script src="${baseUrl}/js/admin-console/oem-build-matrix/modals/update-build-params.js" type="text/javascript"></script>