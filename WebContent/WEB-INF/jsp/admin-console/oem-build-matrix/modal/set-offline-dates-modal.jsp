<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="modalName" value="Offline Dates - ${plantData.plantManufacturer} - ${plantData.city}, ${plantData.state}" />

<%@ include file="../../../global/v2/modal-error-container.jsp"%>
<div class="modal-content col-xs-12" data-modal-title="${modalName}" data-modal-width="470">
	<div class="row modal-body">
		<form id="set-offline-date-form">
			<input type="text" autofocus="autofocus" class="ui-helper-hidden">
			<input type="hidden" value="${plantData.plantId}" class="plantId" name="plantId"/>
			<table id="offline-dates-table">
				<tbody>
					<c:forEach items="${plantData.offlineDates}" var="offlineDate" varStatus="loop">
						<tr class="row" offlineDateId="${offlineDate.offlineDateId}">
							<td class="col-xs-3"><span class="dateLbl">Date</span></td>
							<td class="col-xs-7">
								<div class="form-group d-inline-block">
									<input name="startDate" class="start-date offline-date common-form-control date-picker numeric numeric-jquery-date advanced-date" type="text" required value="${offlineDate.formattedOfflineStartDate}"/>
								</div>
								<input  name="offlineStartDate" type="hidden" class="datepickerStartHidden" value="${offlineDate.formattedOfflineStartDate}"  /><span class="dateLbl"> - </span>
								<div class="form-group d-inline-block"> 
									<input name="endDate" class="end-date offline-date common-form-control date-picker numeric numeric-jquery-date advanced-date" type="text" required value="${offlineDate.formattedOfflineEndDate}"/>
								</div>
								<input  name="offlineEndDate" type="hidden" class="datepickerEndHidden" value="${offlineDate.formattedOfflineEndDate}" />
							</td>
							<c:if test="${loop.index eq 0 }">
								<td class="col-xs-2">
									<a class="secondaryLink" id="clear-row">Clear</a>
								</td>
							</c:if>
							<c:if test="${loop.index ne 0 }">
								<td class="col-xs-2">
									<a class="deleteRow">
									<img src="${commonStaticUrl}/images/delete.png" class="centerImage rightMargin delete-button" /> 
									</a>
								</td>
							</c:if>
						</tr>
					</c:forEach>
					<c:if test="${fn:length(plantData.offlineDates) eq 0}">
						<tr class="row">
							<td class="col-xs-3"><span class="dateLbl">Date</span></td>
							<td class="col-xs-7">
								<div class="form-group d-inline-block">
									<input name="startDate" class="start-date offline-date common-form-control date-picker numeric numeric-jquery-date advanced-date" required type="text"/>
								</div>
								<input  name="offlineStartDate" type="hidden" class="datepickerStartHidden"  /><span class="dateLbl"> - </span>
								<div class="form-group d-inline-block">
									<input name="endDate" class="end-date offline-date common-form-control date-picker numeric numeric-jquery-date advanced-date" required type="text"/>
								</div>
								<input  name="offlineEndDate" type="hidden" class="datepickerEndHidden"  />
							</td>
							<td class="col-xs-2">
								<a class="secondaryLink" id="clear-row">Clear</a>
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