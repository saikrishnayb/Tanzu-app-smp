<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="modalName" value="Offline Dates - ${plantData.plantName} - ${plantData.city}, ${plantData.state}" />
<div class="modal-content col-xs-12" data-modal-title="${modalName}" data-modal-width="470">
	<div class="row modal-body">
		<form id="set-offline-date-form">
			<span class="floatLeft clear-left errorMsg"></span>
			
			<input type="hidden" value="${plantData.plantId}" class="plantId" name="plantId"/>
			<table id="offline-dates-table">
				<tbody>
					<c:forEach items="${plantData.offlineDates}" var="offlineDate" varStatus="loop">
						<tr class="row" offlineDateId="${offlineDate.offlineDateId}">
							<td class="col-xs-3"><span class="dateLbl">Date</span></td>
							<td class="col-xs-7">
								<input name="startDate" class="start-date" class="common-form-control date-picker numeric numeric-jquery-date advanced-date" type="text" value="${offlineDate.formattedOfflineStartDate}" disabled />
								<input  name="offlineStartDate" type="hidden" class="datepickerStartHidden" value="${offlineDate.formattedOfflineStartDate}"  /><span class="dateLbl"> - </span> 
								<input name="endDate" class="end-date" class="common-form-control date-picker numeric numeric-jquery-date advanced-date" type="text" value="${offlineDate.formattedOfflineEndDate}" disabled />
								<input  name="offlineEndDate" type="hidden" class="datepickerEndHidden" value="${offlineDate.formattedOfflineEndDate}" />
							</td>
							<td class="col-xs-2">
							<c:if test="${loop.index ne 0 }">
								<a class="deleteRow">
								<img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin delete-button" /> 
								</a>
							</c:if>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${fn:length(plantData.offlineDates) eq 0}">
						<tr class="row">
							<td class="col-xs-3"><span class="dateLbl">Date</span></td>
							<td class="col-xs-7">
								<input name="startDate" class="start-date" class="common-form-control date-picker numeric numeric-jquery-date advanced-date" type="text" disabled />
								<input  name="offlineStartDate" type="hidden" class="datepickerStartHidden"  /><span class="dateLbl"> - </span>
								<input name="endDate" class="end-date" class="common-form-control date-picker numeric numeric-jquery-date advanced-date" type="text" disabled/>
								<input  name="offlineEndDate" type="hidden" class="datepickerEndHidden"  />
							</td>
							<td class="col-xs-2">
							</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			<div class="row floatRight offline-row right-padding">
				<a class="secondaryLink" id="add-new-row">Add Additional Offline Date</a>
				<a class="buttonPrimary  clear-left  buttonDisabled" id="save-offline-dates" tabindex=8>Save</a>
		     </div>
			
		</form>
	</div>
</div>
<script src="${baseUrl}/js/admin-console/oem-build-matrix/modals/set-offline-dates-modal.js" type="text/javascript"></script>