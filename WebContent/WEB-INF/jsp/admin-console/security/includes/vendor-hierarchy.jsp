<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>
<c:if test="${not empty vendorList}">
	<c:forEach items="${vendorList}" var="vendor" varStatus="status">
		<ul class="groups">
			<li id="role-hierarchy-trees${status.index}">
				<a><span class="role selected-roles" id="role-hierarchy${status.index}">${vendor.vendorName}</span></a>
					<c:forEach items="${vendor.vendorNumberList}" var="vendorNum"  varStatus="status1">
						<ul class="subgroups">
							<li id="sub-role-hierarchy-trees-${vendorNum.vendorId}">
								<a><span class="sub-role selected-roles" id="${vendorNum.vendorId}">${vendorNum.vendorNumber}   ${vendorNum.corpCode} - ${vendorNum.city}, ${vendorNum.state} ${vendorNum.zipCode}</span></a>
							</li>
						</ul> 
					</c:forEach>
			</li>
		</ul>
	</c:forEach>
</c:if>
<c:if test="${empty vendorList}">
	<div class="single-line-content"> 
		No record found for the parent org / applied filter. Please choose different parent org / refine the filter.
	</div>
</c:if>