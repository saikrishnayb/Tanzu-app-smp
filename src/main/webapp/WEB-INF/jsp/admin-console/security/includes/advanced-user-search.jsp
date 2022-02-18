<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="col-xs-12 advanced-user-search-content">
	<div class="row">
		<div class="col-xs-12">
			<span id="advancedSearch" class="expandableContent handCursor 
				<c:if test="${hasBeenSearched eq false}">collapsedImage</c:if>
				<c:if test="${hasBeenSearched eq true}">expandedImage</c:if>
				 pull-right margin-bottom"
				onclick="toggleContent('search-content','advancedSearch');">
				<c:if test="${hasBeenSearched eq false}">Show Search Criteria</c:if>
				<c:if test="${hasBeenSearched eq true}">Hide Search Criteria</c:if></span>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12">
			<div id="search-content" class="displayNone tableBorder margin-top clear-both search-content row" 
				data-has-been-searched="${hasBeenSearched}" 
				data-vendor-user-search="${vendorUsersPage}">
				<div class="row">
					<form id="${vendorUsersPage ? search-vendor-user-form : search-user-form}" action="./users-search.htm" method="GET">
						<div class="col-xs-4">
							<div class="row">
								<div class="col-xs-4 text-right">
									<label>First Name</label>
								</div>
								<div class="col-xs-8">
									<input id="search-first-name" tabindex=1 name="firstName" class="supportingLabel input alpha alpha-name optional" type="text" value="<c:out value="${userSearchForm.firstName}"/>"/>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-4 text-right">
									<label>Role</label>
								</div>
								<div class="col-xs-8">
									<select id="search-role" tabindex=4 name="roleId" class="input numeric numeric-whole optional">
										<option></option>
										<c:forEach items="${roleList}" var="role">
											<option value="${role.roleId}" <c:if test="${userSearchForm.roleId eq role.roleId}"> selected </c:if>>${role.roleName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
					
						<div class="col-xs-4">
							<div class="row">
								<div class="col-xs-4 text-right">
									<label>Last Name</label>
								</div>
								<div class="col-xs-8">
									<input id="search-last-name" tabindex=2 name="lastName" class="supportingLabel input alpha alpha-name optional" type="text" value="<c:out value="${userSearchForm.lastName}"/>"/>
								</div>
							</div>
							<c:if test="${vendorUsersPage}">
								<div class="row">
									<div class="col-xs-4 text-right">
		              					<label>Org</label>
		              				</div>
		              				<div class="col-xs-8">
							            <select id="search-org" tabindex=4 name="orgIds" class="input numeric numeric-whole optional" multiple>
											<c:forEach items="${orgList}" var="org">
												<c:if test="${hasBeenSearched}">
													<c:set var="orgSelected" value =""/>
													<c:forEach items="${userSearchForm.orgIds}" var="searchOrgId">
														<c:if test="${org.orgId eq searchOrgId}">
															<c:set var="orgSelected" value ="selected"/>
														</c:if>
													</c:forEach>
												</c:if>
												<option value="${org.orgId}" ${orgSelected}>${org.orgName}</option>
											</c:forEach>
										</select>
							        </div>
								</div>
							</c:if>
						</div>
					
						<div class="col-xs-4">
							<div class="row">
								<div class="col-xs-4 text-right">
									<label>Email</label>
								</div>
								<div class="col-xs-8">
									<input id="search-email" tabindex=3 name="email" class="supportingLabel input alpha alpha-email optional" type="text" value="<c:out value="${userSearchForm.email}"/>"/>
								</div>
							</div>
						</div>
						<input type="hidden" name="vendorSearch" value="${vendorUsersPage}">
					</form>
				</div>
				<div class="row">
					<div class="col-xs-12">
						<div id="search-buttons-div" class="search-buttons-div pull-right">
							<c:set var="searchButtonClass" value='${vendorUsersPage ? "vendorUsersSearch" : "usersSearch"}' />
							<a class="buttonSecondary floatRight ${searchButtonClass}" tabindex=4 href="#">Search</a>
							<a class="buttonSecondary floatRight margin-right reset" tabindex=5 href="#">Reset</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="${baseUrl}/js/admin-console/security/includes/advanced-user-search.js" type="text/javascript"></script>
</div>