<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="add-association-outer-div">
							
						<form id="add-association-form">
							
							
							<div class="add-association">
								<label  class="leftAlign">PO Category<span class=requiredField>*</span></label>
									<select id="po-category" tabindex=1 > 
										<option>Select</option>
										<c:forEach items="${categoryList}" var= "PO">
										<option value="${PO.categoryId}">${PO.categoryName}</option>
										</c:forEach>
									</select>
							 </div>
							 <div class="add-association">
								<label  class="leftAlign">Sub-Category<span class=requiredField>*</span></label>
									<select id="sub-category" tabindex=2 >
										<option>Select</option>
									</select>
							 </div>
							<div class="close-save" style="width:100%">
								<div style="float: right">
									<a  class="secondaryLink cancel" tabindex=3>Cancel</a>
									<a  class="buttonPrimary save-association" tabindex=4>Add</a>
								</div>
								<div class="error-messages-container displayNone" style="float: left;padding-left: 10px;">
									<img src="${commonStaticUrl}/images/warning.png"></img>
								<span class=errorMsg></span>
				</div>
							</div>
						</form>
	</div>
						