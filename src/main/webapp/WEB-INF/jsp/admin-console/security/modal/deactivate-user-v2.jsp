<div class="modal-content col-xs-12" data-modal-title="Deactivate User" data-modal-width="370">
	<%@ include file="../../../global/v2/modal-error-container.jsp"%>
	<div class="row modal-body">
		<div class="col-xs-12">
			<div class="row">
				<div class="col-xs-12">
					<h3><b>This operation cannot be undone!</b></h3>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12">
					Are you sure you want to delete the profile for
					<ul>
						<li>${email}</li>
					</ul>
					<input id="user-id" type="hidden" value="${userId}"/>
					<input id="is-vendor-user" type="hidden" value="${isVendorUser}"/>
				</div>
			</div>
		</div>
	</div>

	<div class="row modal-footer" style="margin-top:20px;">
		<div class="col-xs-12">
			<a id="cancelButton" class="secondaryLink cancel" tabIndex="-1">No, Cancel</a> 
			<a class="buttonPrimary deactivate-confirm" tabIndex="-1">Yes, Deactivate</a>
		</div>
	</div>
</div>