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
						<select id="poCategory" name="poCategory" ${tolerance.toleranceId > 0 ? "disabled" : ""} >
							<option value="">Select...</option>
							<c:forEach var="category" items ="${poCategoryList}">
								<option value="${category.poCategoryName}" ${category.poCategoryName == tolerance.poCategory.poCategoryName ? "selected" : ""}>
									${category.poCategoryName}
								</option>
							</c:forEach>
						</select>
					</div>
					<div id="errCategory" class="error hidden col-xs-12">
						<img src="${commonStaticUrl}/images/warning.png">
						<span class="errorMsg">Please select PO Category</span>
					</div>
				</div>

				<div class="form-group">
					<div class="col-xs-4">
						<label for="mfrCode">Make</label>
					</div>
					<div class="col-xs-6">
						<select id="mfrCode" name="mfrCode" ${tolerance.toleranceId > 0 ? "disabled" : ""} >
							<option value="">Select...</option>
							<c:forEach var="make" items ="${vehicleMakeList}">
								<option value="<c:out value='{${make.mfrCode}}'/>" ${make.mfrCode == tolerance.mfrCode ? "selected" : ""}>
									<c:out value='${make.mfrCode}'/>
								</option>
							</c:forEach>
						</select>
					</div>
					<div id="errMake" class="error hidden col-xs-12">
						<img src="${commonStaticUrl}/images/warning.png">
						<span class="errorMsg">Please select Make</span>
					</div>
				</div>

				<div class="form-group">
					<div class="col-xs-4">
						<label for="tolerance">Cost Tolerance</label>
					</div>
					<div class="col-xs-6">
						<input id="tolerance" name="tolerance" type="text" autocomplete="off" value="${tolerance.tolerance}" />
					</div>
					<div id="errTolerance" class="error hidden col-xs-12">
						<img src="${commonStaticUrl}/images/warning.png">
						<span class="errorMsg">Please enter Cost Tolerance as numeric value</span>
					</div>
				</div>

			</div>
		</form>
	</div>

	<div class="modal-footer">
		<a href="#" id="cancelButton" class="secondaryLink" >Cancel</a>
		<c:if test="${tolerance.toleranceId gt 0}">
			<a href="#" id="actionButton" class="buttonPrimary clear-left buttonDisabled" onclick="doSave(); return false;" >Save</a>
		</c:if>
		<c:if test="${tolerance.toleranceId eq 0}">
			<a href="#" id="actionButton" class="buttonPrimary clear-left buttonDisabled" onclick="doAdd(); return false;" >Add</a>
		</c:if>
	</div>
</div>
