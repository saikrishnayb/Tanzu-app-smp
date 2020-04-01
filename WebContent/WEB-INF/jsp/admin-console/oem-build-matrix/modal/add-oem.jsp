<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="${baseUrl}/css/admin-console/oem-build-matrix/modal/add-oem.css" rel="stylesheet" type="text/css" />

<div class="modal-content col-xs-12" data-modal-title="Add OEM" data-modal-width="540">
	<div class="modal-body row">
	  	<div class="col-xs-12">
	      <form id="add-oem-form">   
		    <div class="row">
		      <label for="po-category-drpdown" class="col-xs-3 control-label">PO Category: </label>
		      <div class="col-xs-9">
		        <select id="po-category-drpdown" name="po-category-drpdown" class="common-form-control">
					<option value=""></option>
					<c:forEach items="${poCategoryList}" var="poCategory">
						<option value="${poCategory.poCategoryName}" >${poCategory.poCategoryName}</option>
					</c:forEach>
				</select>        
		      </div>
		    </div>
		    <div class="row">
		      <label for="oem-name-drpdown" class="col-xs-3 control-label">OEM Name: </label>
		      <div class="col-xs-9">
		  	    <select id="oem-name-drpdown" name="oem-name-drpdown" class="common-form-control" multiple>
				</select>
		      </div>
		    </div>
	      </form>
	    </div>
	</div> 
	
	<div class="modal-footer">
	  <a class="buttonPrimary btn btn-spec-option-form-save disabled">Save</a>
	</div>
</div>

<!-- Scripts -->
<script src="${baseUrl}/js/admin-console/oem-build-matrix/modals/add-oem.js" type="text/javascript"></script>
