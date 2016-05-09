<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- <script src="${context}/js/admin-console/components/add-visibility.js" type="text/javascript"></script> --%>
<div class="add-visibility-outer-div">
							
						<form id="add-visibility-form">
							<div class="requiredFieldsCheck" id="errorMessage">
								<img src="${commonStaticUrl}/images/warning.png" /> <span
								class="errorMsg">please enter required fields</span>
							</div>	
							 
							 <div class="add-visibility">
								<label  class="leftAlign"><span class="requiredField">*</span>Component Name</label>
									<select id ="component-name" class="numeric-whole">
										<option >Select</option>
										<c:forEach var="name" items="${componentNames}" >
											<option value ="${name.componentId}" data-is-component-vehicle ="${name.isComponentVehicle}" >${name.componentName}</option>
										</c:forEach>
									</select>
							</div>
							<div class="add-visibility">
								<label  class="leftAlign"><span class="requiredField">*</span>PO Category</label>
									<select id="po-category" class="numeric-whole"> 
											<option>Select</option>
									<!--  	<c:forEach var="category" items="${categoriesList}" >
											<option value ="${category.categoryId}" data-name="${category.categoryName}" >${category.categoryName} </option>
										</c:forEach>
									-->
									</select>
							 </div>
							 <div class="add-visibility">
								<label  class="leftAlign"><span class="requiredField">*</span>Sub-Category</label>
									<select id="sub-category" class="numeric-whole">
										<option>Select</option>
										
									</select>
							 </div>
							<div class="close-save" >
								<a  class="secondaryLink cancel">Cancel</a>
								<a  class="buttonPrimary save-visibility" >Save</a>
							</div>
						</form>
	</div>
						
					