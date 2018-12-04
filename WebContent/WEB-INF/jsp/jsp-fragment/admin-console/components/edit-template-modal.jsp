
		
			<div class="leftNavAdjacentContainer">
				 
				 <div id="jstree-mfr-div">
				 	<div id="demo1" class="demo">
				 	<c:forEach items="${manufacture}" var="vendor">
					 	<ul >
					 	<li id="phtml_1" class="mfr">
							<input type="checkbox" class="parent-mfr" value="${vendor.manufacture}"><a>	${vendor.manufacture}</a>
						<ul>
						  <c:forEach items="${vendor.corpCodes}"  var="corp">
								<li id="phtml_1" class="corp">
									<input type="checkbox" class="parent-corp" value="${corp.corpCode}"><a>	${corp.corpCode}</a>
									<ul>
									<c:forEach items="${corp.vendorLocation }" var="location">
									
										<li id="phtml_2">
										<input type="checkbox" class="child-vendor" value="${location.vendorNumber}"><a>		${location.manufacturer}-${location.state}Area-${location.vendorNumber}</a>
										</li>
										</c:forEach>
									</ul>
								</li>
								</c:forEach>
						</ul>
						</li>
						</ul>
					</c:forEach>
				</div>
				 </div>
					<div class="po-category-accordion-container">
					</div>
					<div class="add-category">
					<span class="floatRight addRow add-category">
							<a >Add Category</a>
							<img src="${commonStaticUrl}/images/add.png" class="centerImage handCursor add-category" alt="Add Row"/>
						</span>
					</div>
					<div id="delete-category" class="delete-confirm modal"></div>
					<div id="add-category-modal" class="add-category-modal modal"></div>
					
					
			</div>
		</div> 
	
		
	</body>
	<!-- Scripts -->
	<script src="${baseUrl}/js/admin-console/components/create-template.js" type="text/javascript"></script>
</html>