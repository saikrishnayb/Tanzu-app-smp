<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="add-association-outer-div">
							
						<form id="add-association-form" modelattribute="addAssociationForm">
							
						<input type=hidden id='associationId'	value='${categoryAssociation.associationId}'/>
						<input type=hidden id='isEditPage'	value='${isEditPage}'/>
							<div class="add-association">
								<label  class="leftAlign">PO Category<span class=requiredField>*</span></label>
								<c:if test="${isEditPage eq false}">
									<select id="po-category" tabindex=1 > 
										<option>Select</option>
										<c:forEach items="${categoryList}" var= "PO">
										<option value="${PO.categoryId}">${PO.categoryName}</option>
										</c:forEach>
									</select>
								</c:if>
									<c:if test="${isEditPage eq true}">
										<span>${categoryAssociation.poCategoryName}</span>
									</c:if>
							 </div>
							 <div class="add-association">
								
									<label  class="leftAlign">Sub-Category<span class=requiredField>*</span></label>
									 <c:if test="${isEditPage eq false}">
										<select id="sub-category" tabindex=2 >
											<option>Select</option>
										</select>
									</c:if>
									<c:if test="${isEditPage eq true}">
										<span>${categoryAssociation.subCategoryName}</span>
									</c:if>
							 </div>
							  <div class="add-association associated-fields" >
								  <div class="reqField">
								  	<label  class="leftAlign">Required Fields<span class=requiredField>*</span></label>
								  </div>
								 <div class="checkboxStyle">	
									 <input type="checkbox" id="makeModelYearRequired" <c:if test="${categoryAssociation.makeModelYearRequired == true}">checked</c:if> />Make/Model/Year<br/>
									 <input type="checkbox" id="vehicleSizeRequired"  <c:if test="${categoryAssociation.vehicleSizeRequired == true}">checked</c:if> />Vehicle Size<br/>
									 <input type="checkbox" id="vehicleTypeRequired"  <c:if test="${categoryAssociation.vehicleTypeRequired == true}">checked</c:if>/>Vehicle Type<br/>
								  </diV> 
							  </div>
							  	
							
							<div class="close-save" style="width:100%">
								<div style="float: right;padding-top: 12px">
									<a  class="secondaryLink cancel" tabindex=3>Cancel</a>
									<c:if test="${isEditPage eq false}">
									<a  class="buttonPrimary save-association" tabindex=4>Add</a></c:if>
									<c:if test="${isEditPage eq true}">
										<a  class="buttonPrimary update-association" tabindex=4>Update</a></c:if>
								</div>
								<div class="error-messages-container displayNone" style="float: left;padding-left: 10px;">
									<img src="${commonStaticUrl}/images/warning.png"></img>
								<span class=errorMsg></span>
				</div>
							</div>
						</form>
	</div>
						