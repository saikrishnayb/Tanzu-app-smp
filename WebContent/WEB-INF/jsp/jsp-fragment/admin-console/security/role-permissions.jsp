<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}"  scope="page" />

<c:forEach var="tab" items="${tabs}">
   <c:if test="${tab.permissionsCount > 0}">
      <div class="floatLeft width-full clear-left tab">
      	<div class="accordion floatLeft width-full">
      		<h3 class="floatLeft accordion-header">
      			<a class="floatLeft">${tab.tabName}</a>
      			<span class="floatRight header-span"><input type="checkbox" />Check to apply all tab functions.</span>
      		</h3>
      			
      		<div>
               
                  <c:forEach var="function" items="${tab.permissions}">
                      <div class="function-name floatLeft">
                          <input type="hidden" class="function-id" value="${function.securityFunctionId}" />
                          <input type="checkbox" class="function-check" <c:if test="${function.available}">checked</c:if> />
                          <span>${function.description}</span>
                      </div>
                  </c:forEach>
      		</div>
      	</div>
      </div>

   </c:if>
</c:forEach>

<script src="${context}/js/admin-console/security/role-permissions.js" type="text/javascript"></script>