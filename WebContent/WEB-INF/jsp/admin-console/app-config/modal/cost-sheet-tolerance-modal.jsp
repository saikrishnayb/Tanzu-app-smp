<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">
	var makesObjectArray = JSON.parse('${makesJson}');
</script>

<form id="tolerance-form">
	<input name="toleranceId" type="hidden" value="${tolerance.toleranceId}"/>

	<div class="form-group">
		<label for="poCategory" class="control-label">PO Category</label>
		<div class="control-div">
			<select id="poCategory" name="poCategory" ${tolerance.toleranceId > 0 ? "disabled" : ""} >
				<option value="">Select...</option>
				<c:forEach var="category" items ="${poCategoryList}">
					<option value="${category.poCategoryName}" ${category.poCategoryName == tolerance.poCategory.poCategoryName ? "selected" : ""}>
						${category.poCategoryName}
					</option>
				</c:forEach>
			</select>
		</div>
		<div id="errCategory" class="error hidden">
			<img src="${commonStaticUrl}/images/warning.png">
			<span class="errorMsg">Please select PO Category</span>
		</div>
	</div>

	<div class="form-group">
		<label for="mfrCode" class="control-label">Make</label>
		<div class="control-div">
			<select id="mfrCode" name="mfrCode" ${tolerance.toleranceId > 0 ? "disabled" : ""} >
				<option value="">Select...</option>
				<c:forEach var="make" items ="${vehicleMakeList}">
					<option value="<c:out value='{${make.mfrCode}}'/>" ${make.mfrCode == tolerance.mfrCode ? "selected" : ""}>
						<c:out value='${make.mfrCode}'/>
					</option>
				</c:forEach>
			</select>
		</div>
		<div id="errMake" class="error hidden">
			<img src="${commonStaticUrl}/images/warning.png">
			<span class="errorMsg">Please select Make</span>
		</div>
	</div>

	<div class="form-group">
		<label for="tolerance" class="control-label">Cost Tolerance</label>
		<div class="control-div">
			<input id="tolerance" name="tolerance" type="text" autocomplete="off" value="${tolerance.tolerance}" />
		</div>
		<div id="errTolerance" class="error hidden">
			<img src="${commonStaticUrl}/images/warning.png">
			<span class="errorMsg">Please enter Cost Tolerance as numeric value</span>
		</div>
	</div>

	<div class="blank"></div>

	<div class="floatRight">
		<a href="#" class="secondaryLink floatLeft cancel" >Cancel</a>
		<c:if test="${tolerance.toleranceId gt 0}">
			<a href="#" id="actionButton" class="buttonPrimary clear-left buttonDisabled" onclick="doSave(); return false;" >Save</a>
		</c:if>
		<c:if test="${tolerance.toleranceId eq 0}">
			<a href="#" id="actionButton" class="buttonPrimary clear-left buttonDisabled" onclick="doAdd(); return false;" >Add</a>
		</c:if>
	</div>

</form>
