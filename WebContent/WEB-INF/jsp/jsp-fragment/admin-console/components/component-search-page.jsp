<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
$(document).ready(function() {
	initSearchPage();
	$('.back').on('click', function(){
		closeModal($('#component-search-modal'));
		return false;
	});
});
</script>
<form id="search-component-form">
	<input id="val" type="hidden" value="${val}"/>
	<div class="full-width">
		<table id="search-Component-table" >
			<thead>
				<tr>
					<th class="viewCol"></th> 
					<th>Component Name</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${allComponent}" var="comps" varStatus="status">
				<tr class="search-Component-row">
					<td class="component-id"><input id="${comps.componentId}" name="componentId" type="radio" value="${comps.componentId}"></td>
					<td class="component-val" id="compName_${comps.componentId}">${comps.fullName}</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="edit-buttons-100">
				<a class="secondaryLink back" tabIndex="-1">Cancel</a> 
				<a id="select-component" class="buttonPrimary select" tabIndex="-1">Select</a>
		</div>
		<div class="error-messages-container displayNone" style="float: right;">
			<img src="${commonStaticUrl}/images/warning.png"></img>
			<span class=errorMsg>Component selection invalid!</span>
		</div>
	</div>
</form>