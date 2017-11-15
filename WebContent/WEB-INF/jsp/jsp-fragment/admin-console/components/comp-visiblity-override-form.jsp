<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<div class="visiblity-form-container" style="width: 50%">
		<form id="visiblity-override-form"   method="POST">
			 <c:if test="${isCreatePage eq false}">
			 	<input id="visiblityOverrideId" name="visiblityOverrideId" type="hidden" value="${editOverride.visiblityOverrideId}">
			 </c:if>
			<div class="single-line-content" style="margin-top: 10px;">
				<label for="pocategory">PO Category <span class=requiredField>*</span></label> 
				<select id="poCategoryAssocId" tabindex=1 class="input numeric numeric-whole" name="poCategoryAssocId" style="width: 190px;">
						<option value=''>Select</option>
						<c:forEach items="${allPoAssocList}" var="poAssocList">
							 <c:if test="${isCreatePage eq false}">
								<option value="${poAssocList.poCatAssocID}" <c:if test="${editOverride.poCategoryAssocId eq poAssocList.poCatAssocID}">selected</c:if> >${poAssocList.poCatSubCatDesc}</option>
								</c:if>
							<c:if test="${isCreatePage eq true}">
								<option value="${poAssocList.poCatAssocID}">${poAssocList.poCatSubCatDesc}</option>
							</c:if>
						</c:forEach>
				</select>
			</div>
			<div class="single-line-content" style="margin-top: 10px;">
				<label for="dependentComponent">Dependent Component<span class=requiredField>*</span></label> 
				<input id="dependentComponentName"  name="dependentComponentName" type="text" size="23" class="input alpha alpha-numeric" value="${editOverride.dependentComponentName}"  readonly="readonly"/>&nbsp;<a id="dependentComponentSel"><img tabindex=2 src="${commonStaticUrl}/images/search.png"/></a>
				<input id="dependentComponentId" name="dependentComponentId" type="hidden" value="${editOverride.dependentComponentId}" class="input numeric numeric-whole" />
			</div>
			<div class="single-line-content" style="margin-top: 10px;">
				<label for="overrideTypes">Override Types<span class=requiredField>*</span></label> 
				<select id="overrideType" tabindex=3 class="input alpha alpha-numeric" name="overrideType" style="width: 190px;">
					<option value=''>        Select         </option>
					<c:forEach items="${overrideTypes}" var="oTypes">
						 <c:if test="${isCreatePage eq false}">
							<option value="${oTypes.lookUpValue}" <c:if test="${editOverride.overrideType eq oTypes.lookUpValue}">selected</c:if> >${oTypes.lookUpDesc}</option>
						</c:if>
						<c:if test="${isCreatePage eq true}">
							<option value="${oTypes.lookUpValue}">${oTypes.lookUpDesc}</option>
						</c:if>
					</c:forEach>
				</select>
			</div>
			<div class="single-line-content" style="margin-top: 10px;">
				<label for="controllingComponent">Controlling Component</label> 
				<input id="controllingComponentName"   name="controllingComponentName" type="text" size="23" class="input alpha alpha-numeric optional" value="${editOverride.controllingComponentName}" readonly="readonly"/>&nbsp;<a id="controllingComponentSel"><img tabindex=4 src="${commonStaticUrl}/images/search.png"/></a>
				<input id="controllingComponentId" name="controllingComponentId" type="hidden" value="${editOverride.controllingComponentId}" />
			</div>
			<div class="single-line-content" style="margin-top: 18px;">
				<label for="controllingValue" style="vertical-align: top;">Controlling Value</label> 
				<textarea rows="5" cols="24" tabindex=5 id="controllingValue" name="controllingValue" class="input alpha alpha-numeric optional">${editOverride.controllingValue}</textarea>
			</div>
		</form>
	</div>
<div id="component-search-modal" class="modal" title="Select Component"></div>