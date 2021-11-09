<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="${baseUrl}/css/app-container/modal/vendor-filter.css" rel="stylesheet" type="text/css"/>    

<div class="modal-content" data-modal-title="Vendor Filter" data-modal-width="500">

  <div class="modal-body">
  	<a id="vendor-filter-fuzzy-search-clear" class="secondaryLink">Clear</a><input id="vendor-filter-fuzzy-search" type="text">
    <c:forEach items="${allOrgFilters}" var="orgFilter">
    
      <c:set value="${empty orgFilter.vendorFilters}" var="noVendors"/>
  
      <div class="common-tree-container" data-organization-id="${orgFilter.orgId}">
        <div class="common-tree-header <c:if test="${noVendors}">closed</c:if>">
          <span class="caret cursor-pointer <c:if test="${noVendors}">flip-right</c:if>"></span>
          
          <label>
            <input type="checkbox" class="org-input" value="${orgFilter.orgId}"
              <c:if test="${orgFilter.orgSelected}">checked</c:if>
            /> <span class="org-name">${orgFilter.orgName}</span>
          </label>
          
        </div>
        
        <div class="common-tree-content <c:if test="${not noVendors}">loaded</c:if>">
        
          <c:forEach items="${orgFilter.vendorFilters}" var="vendorFilter">
            
            <label>
              <input type="checkbox" class="vendor-input" value="${vendorFilter.vendorId}"
                <c:if test="${vendorFilter.selected}">checked</c:if>
              /> ${vendorFilter.vendorNumber} - ${vendorFilter.vendorCorp} ${vendorFilter.formattedAddress}
            </label>
          
          </c:forEach>
        </div>
        
      </div>
     
    </c:forEach>
  </div>
  
  <div class="modal-footer">
    <a class="buttonSecondary btn btn-clear-vendors">Clear All</a>
    <a class="buttonPrimary btn btn-save-vendor-filter">Save</a>
  </div>
  
</div>

<script src="${baseUrl}/js/app-container/modal/vendor-filter.js" type="text/javascript"></script>