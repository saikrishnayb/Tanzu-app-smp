<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}" scope="page" />
<c:choose>
	<c:when test="${canDelete eq false}">
		<div id="message-text" class="floatLeft clear-left">
			<h3 class="floatLeft clear-left errorMsg">This Org cannot be deactivated.</h3>
			<span class="floatLeft clear-left">There are users assigned to this org or one of its sub-org.</span>
		</div>
		<span class="floatLeft clear-left">Org Name:</span>
		<ul class="floatLeft clear-both margin-bottom-10">
			<li>${orgName}</li>
		</ul>
		<input id="org-id" type="hidden" value="${orgId}"/>
		<div class="floatRight clearLeft button-div">
			<a class="buttonPrimary cancel">Close</a>
		</div>
	</c:when>
	<c:otherwise>
		<p>
		<h3>
			<b>This operation cannot be undone!</b>
		</h3>
		<p>Are you sure you want to deactivate the Org</p>
		
		<ul>
			<li>${orgName}</li>
		</ul>
		<c:if test="${not empty childOrgName }">
			<h3><span class="floatLeft clear-left">Below Descendant Org also will get deactivated</span></h3>
			<ul class="floatLeft clear-both margin-bottom-10">
				<c:forEach items="${childOrgName}" var="childOrg">
					<li>${childOrg.orgName}</li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${not empty roles }">
			<h3><span class="floatLeft clear-left">Below Organization's Role and its Descendant also will get deactivated</span></h3>
			<ul class="floatLeft clear-both margin-bottom-10">
				<c:forEach items="${roles}" var="roleName">
					<li>${roleName}</li>
				</c:forEach>
			</ul>
		</c:if>
		<input id="org-id" type="hidden" value="${orgId}"/>
		<div class="deactivate-buttons-div">
			<a class="secondaryLink cancel" tabIndex="-1">No, Cancel</a> 
			<a class="buttonPrimary deactivate-confirm" tabIndex="-1">Yes, Deactivate</a>
		</div>
    </c:otherwise>   

</c:choose>
