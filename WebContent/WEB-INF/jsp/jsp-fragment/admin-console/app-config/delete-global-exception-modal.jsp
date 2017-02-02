<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<div class="line">
		<label>Component Name:</label>${exception.componentName}
	</div>
	<div class="blank"></div>
	<div class="line">
		<div id="list-div">
		<label>PO &#38; Vendor Associations:</label>
    
		<ul id="po-group-items">
    
           <c:forEach items="${exception.poCategoryGroups}" var="poCategoryGroup">
             <li>
              <label>${poCategoryGroup.poCategorySubcategory} -</label>
              <span>${poCategoryGroup.vendor.vendorName}</span>
              <span>${poCategoryGroup.vendor.city},</span>
              <span>${poCategoryGroup.vendor.state}</span>
              <span>${poCategoryGroup.vendor.zipCode}</span>
              </li>
            </c:forEach>
			
		</ul>
		</div>
	</div>
	<div class="blank"></div>
	<div class="line">
		<label>To be Provided By:</label>${exception.providerVendor.vendorName}
	</div>
	<!-- <div class="blank"></div>
	<div class="line">
		<label class="wide-label">Provider Sub Group:</label>
		<label>${exception.providerPoSub}</label>
	</div> -->
	<input type="hidden" id="exception-id-modal" value="${exception.exceptionId}"/>
<div class="blank"></div>
<div class="buttonLine">
	<div class="right-buttons">
		<a class="cancel">Cancel</a>
		<a class="buttonPrimary delete-global-exception-confirm">Yes, Delete</a>
	</div>
</div>