<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form id="set-offline-date-form">
	<span class="floatLeft clear-left errorMsg"></span>

	<input type="hidden" value="${plantData.plantId}" class="plantId" name="plantId"/>
	<div class="row offline-row padding-8">
		<div class="col-xs-6 left-padding">
			<label class="floatLeft clear-left ">Offline Start Date :</label>
		</div>
		<div class="col-xs-6 left-padding">
			<input name="startDate" id="start-date" class="common-form-control date-picker numeric numeric-jquery-date advanced-date" type="text" value="${plantData.formattedOfflineStartDate}" />
			<input  name="offlineStartDate" type="hidden" id="datepickerStartHidden" value="${plantData.formattedOfflineStartDate}"  />
		</div>
	</div>
	
	<div class="row offline-row">
		<div class="col-xs-6 left-padding">
			<label class="floatLeft clear-left ">Offline End Date :</label>
		</div>
		<div class="col-xs-6 left-padding">
			<input name="endDate" id="end-date" class="common-form-control date-picker numeric numeric-jquery-date advanced-date" type="text" value="${plantData.formattedOfflineEndDate}" />
			<input  name="offlineEndDate" type="hidden" id="datepickerEndHidden" value="${plantData.formattedOfflineStartDate}" />
		</div>
	</div>
	
	<div class="row floatRight offline-row right-padding">
		<a class="buttonPrimary  clear-left " id="save-offline-dates" tabindex=8>Save</a>
     </div>
	
</form>

<script src="${baseUrl}/js/admin-console/oem-build-matrix/modals/set-offline-dates-modal.js" type="text/javascript"></script>