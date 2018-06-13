<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="edit-global-exception-modal"> 
<form id="edit-global-exception-form" action="edit-global-exception" method="post">
<c:forEach items="${exception}" var="exception">
	<div class="unitPOHeader">
	<div class="popUpPONum"><c:if test="${poNumber != 0}"><h1>PO: ${poNumber}</h1></c:if></div>
	<div class="popUpUnitNum"><c:if test="${not empty unitNumber}"><h1>Unit: ${unitNumber}</h1></c:if></div>
	</div>
	<div class="line">
		<div id="list-div">
		<input type="hidden" id="exception-id-modal" value="${exception.exceptionId}"/>
			<table class='dataTable'>
				<thead>
					<tr>
						<th>PO Category-Sub Category</th>
						<th>Vendor Name</th>
						<th class="addressHeader" colspan="3" style="text-align: center;">Address</th>
					</tr>
					
				</thead>
				<tbody>
                     <c:forEach items="${exception.poCategoryGroups}" var="poCategoryGroup" varStatus="rowIndex">
                     <tr class="${rowIndex.index  % 2 == 0 ? 'odd' : 'even'}">
                       	<td>${poCategoryGroup.poCategorySubcategory}</td>
                       	<td>${poCategoryGroup.vendor.vendorName}</td>
                       	<td>${poCategoryGroup.vendor.city}</td>
                    	<td>${poCategoryGroup.vendor.state}</td>
                    	<td>${poCategoryGroup.vendor.zipCode}</td>
                      </tr>
                   
                     </c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<fieldset>
		<div class="line">
			<label>Component :</label>
			<label>${exception.componentName}</label>
		</div>
		
		<div class="blank"></div>
		<div class="line">
			<label>Responsible Vendor: </label>
			<select id="provider-vendor" class="wide-select input alpha alpha-numeric" >
				<option value=""></option>
				<c:forEach items="${exception.poCategoryGroups}" var="poCategoryGroup">
					<option value="${poCategoryGroup.poCategoryAssociationId}-${poCategoryGroup.vendor.vendorId}"
					<c:if test="${exception.providerVendor.vendorName eq poCategoryGroup.vendor.vendorName}">selected</c:if> name="${poCategoryGroup.vendor.vendorName}">${poCategoryGroup.vendor.vendorNumber} - ${poCategoryGroup.vendor.vendorName}</option>
				</c:forEach>
			</select>
		</div>
	</fieldset>
</c:forEach>
</form>
<div class="blank"></div>
<div class="buttonLine">
	<div class="hidden errorDiv" id="error-message">
		<img src="${commonStaticUrl}/images/warning.png"></img>
		<span id="message-span" class="errorMsg"></span>
	</div>
	<div class="float-right">
		<a class="cancel">Cancel</a>
		<a class="buttonPrimary edit-global-exception-confirm buttonDisabled" id='updateButton'>Update</a>
	</div>
</div>
</div>