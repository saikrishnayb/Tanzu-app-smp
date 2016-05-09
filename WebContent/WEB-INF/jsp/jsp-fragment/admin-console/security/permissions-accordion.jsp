<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="context" value="${pageContext.request.contextPath}"
	scope="page" />
		<c:if test="${isHeaderRoleInfo eq true}">
			<div class="role-modal-blurb">
				<p>Based on the current role of <i>${roleName}</i> you have access to the following application functionality</p>
			</div>
		</c:if>
		<c:choose>
    		<c:when test="${tabPermissionsMap != null}">
				<c:forEach items="${tabPermissionsMap}" var="entry">
					<div id="permission-tab-accordions" class="width-full permission-tab-accordions accordion-headers">
						<h3 class="width-full">
							<span class="ui-icon ui-icon-triangle-1-e"></span> 
							<a href="#" class="initial-accordion">${entry.key}</a>
						</h3>
						<div>
							<c:forEach items="${entry.value}" var="permission">
								<div class="permissions-checkbox" style="padding: 2px;">
									${permission.description}
								</div>
							</c:forEach>
						</div>
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<h3><center>No permission attached to this Role</center></h3>
			</c:otherwise>
	</c:choose>
<script src="${context}/js/admin-console/security/permissions-accordion.js" type="text/javascript"></script>
