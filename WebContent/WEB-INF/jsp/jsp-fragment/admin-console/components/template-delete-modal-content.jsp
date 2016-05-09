<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		
		<p>Are you sure you want to delete this template</p>
							<div>
								<span>
									<ul>
									
										<li id="template" >
									
										${vendorTemplate.manufacture},${vendorTemplate.state}-${vendorTemplate.vendorNumber}
									<input type="hidden" value="${vendorTemplate.templateId}" name="templateId"/>
										</li>
										
									</ul>
								</span>
							</div>
							<div style="position:absolute;bottom:3px;right:5px;">
								<a href="#" class="secondaryLink cancel" tabIndex="-1">No, Cancel</a>
								<a href="#" class="buttonPrimary delete" tabIndex="-1">Yes, Delete</a>
							</div>