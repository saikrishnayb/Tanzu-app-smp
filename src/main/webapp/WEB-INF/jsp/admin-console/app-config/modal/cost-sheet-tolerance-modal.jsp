<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">
	var makesObjectArray = JSON.parse('${makesJson}');
</script>

<c:set var="modalName" value='${tolerance.toleranceId gt 0 ? "Edit Cost Sheet Tolerance" : "Add Cost Sheet Tolerance"}' />

<div class="modal-content col-xs-12" data-modal-title="${modalName}" data-modal-width="400">
	<%@ include file="../../../global/v2/modal-error-container.jsp"%>
	<div class="row modal-body">
		<form id="tolerance-form" class="form-horizontal">
			<input name="toleranceId" type="hidden" value="${tolerance.toleranceId}"/>
			<div class="col-xs-12">

				<div class="form-group">
					<div class="col-xs-4">
						<label for="poCategory">PO Category</label>
					</div>
					<div class="col-xs-6">
						<select id="poCategory" name="poCategory" ${tolerance.toleranceId > 0 ? "disabled" : ""} required >
							<option value="">Select...</option>
							<c:forEach var="category" items ="${poCategoryList}">
								<option value="${category}" ${category == tolerance.poCategory ? "selected" : ""}>
									${category.poCategoryName}
								</option>
							</c:forEach>
						</select>
					</div>
				</div>

				<div class="form-group">
					<div class="col-xs-4">
						<label for="mfrCode">Make</label>
					</div>
					<div class="col-xs-6">
						<select id="mfrCode" name="mfrCode" ${tolerance.toleranceId > 0 ? "disabled" : ""}>
							<option value="">Select...</option>
							<c:forEach var="make" items ="${vehicleMakeList}">
								<option value="<c:out value='{${make.mfrCode}}'/>" ${make.mfrCode == tolerance.mfrCode ? "selected" : ""}>
									<c:out value='${make.mfrCode}'/>
								</option>
							</c:forEach>
						</select>
					</div>
				</div>

				<div class="form-group">
					<div class="col-xs-4">
						<label for="tolerance">PO Vendor Number</label>
					</div>
					<div class="col-xs-6">
						<input id="po-vendor-number" name="poVendorNumber" type="text" autocomplete="off" value="${tolerance.poVendorNumber}" class="numeric numeric-whole" ${tolerance ne null ? "disabled" : ""} />
					</div>
				</div>

				<div class="form-group">
					<div class="col-xs-4">
						<label for="tolerance">Cost Tolerance</label>
					</div>
					<div class="col-xs-6">
						<input id="tolerance" name="tolerance" type="text" autocomplete="off" value="${tolerance.tolerance}" class="numeric numeric-decimal" min="0" required />
					</div>
				</div>

			</div>
		</form>
	</div>

	<div class="modal-footer">
		<a href="javascript:void(0);" id="cancelButton" class="secondaryLink" >Cancel</a>
		<a href="javascript:void(0);" class="buttonPrimary clear-left btn-save-tolerance" >${tolerance eq null ? 'Add' : 'Save'}</a>
	</div>
</div>
