$(document).ready(function() {
	selectCurrentNavigation("tab-components", "left-nav-category-association");
	
	var $categoryAssociationTable = $('#category-association-table');
	var $addAssociationModal =$('#add-association-modal');
	var $addCategoryAssociation = $('.add-category-association');
	var commonStaticUrl = sessionStorage.getItem('commonStaticUrl');
	var iDisplayLength = 10;// tableRowLengthCalc();
	
	$categoryAssociationTable.dataTable( { //All of the below are optional
		"aaSorting": [[ 1, "desc" ]], //default sort column
        "bPaginate": true, //enable pagination
        "bAutoWidth": false, //cray cray
        "bStateSave": true,
        "bLengthChange": true, //enable change of records per page, not recommended
        "bFilter": true, //Allows dynamic filtering of results, do not enable if using ajax for pagination
        "bSort": true, //Allow sorting by column header
        "bInfo": true, //Showing 1 to 10 of 11 entries
        "aoColumnDefs": [
                         {"bSortable": false, "aTargets": [ 0 ]}, //stops first column from being sortable
		                 { "sWidth": "100px", "aTargets": [ 0 ] },
		                 {"bSearchable": false, "aTargets": [0]}
                         ],
        "sPaginationType": "full_numbers", //Shows first/previous 1,2,3,4 next/last buttons
        "iDisplayLength": 100 , //number of records per page for pagination
        "oLanguage": {"sEmptyTable": "&nbsp;"}, //Message displayed when no records are found
        "fnDrawCallback": function() { //This will hide the pagination menu if we only have 1 page.
      		 var paginateRow = $(this).parent().children('div.dataTables_paginate');
      		 var pageCount = Math.ceil((this.fnSettings().fnRecordsDisplay()) / this.fnSettings()._iDisplayLength);
      		 
      		 if (pageCount > 1)  {
      		        paginateRow.css("display", "block");
      		 } else {
      		        paginateRow.css("display", "none");
      		 }
      		 
      		 //This will hide "Showing 1 to 5 of 11 entries" if we have 0 rows.
      		 var infoRow = $(this).parent().children('div.dataTables_info');
      		 var rowCount = this.fnSettings().fnRecordsDisplay();
      		 if (rowCount > 0) {
      		        infoRow.css("display", "block");
      		 } else {
      		        infoRow.css("display", "none");
      		 }
      		 
		        }
		 } );
	
		$addAssociationModal.dialog({
			autoOpen: false,
			modal: true,
			dialogClass: 'popupModal',
			width: 'auto',
			minHeight:150,
			resizable: false,
			closeOnEscape: false,
			open: function(event, ui) { }
		});

		
		$('#deactivate-category-association-modal').dialog({
			autoOpen: false,
			modal: true,
			dialogClass: 'popupModal',
			width: 400,
			minHeight: 150,
			resizable: false,
			title: 'PO Category Deactivate Confirmation',
			closeOnEscape: false,
			open: function(event, ui) { }
		});
		
		$('#activate-category-association-modal').dialog({
			autoOpen: false,
			modal: true,
			dialogClass: 'popupModal',
			width: 400,
			minHeight: 150,
			resizable: false,
			title: 'PO Category Activate Confirmation',
			closeOnEscape: false,
			open: function(event, ui) { }
		});
		
		$('#error-modal').dialog({
			autoOpen: false,
			modal: true,
			dialogClass: 'errorModal',
			width: 500,
			minHeight: 0,
			resizable: false,
			closeOnEscape: true
		});	
		
		//deactivate association
		$categoryAssociationTable.on("click",'.delete-association',function(){
			$('#deactivate-category-association-modal').empty();
			var $this =  $(this);
			var associationId = $this.closest('.category-association-row').find('.editable').find('.association-id').val();
			var poCatId= $(this).closest('.category-association-row').find('.poCategory-id').val();
			var subCatId=$(this).closest('.category-association-row').find('.subCategory-id').val();
			var poCatName = $this.closest('.category-association-row').find('.po-cat-name').text();
			var subCatName = $this.closest('.category-association-row').find('.sub-cat-name').text();
			
			$('#deactivate-category-association-modal').prepend('<p>Are you sure you want to deactivate this PO Category</p><ul><li>' + poCatName + '-' + subCatName + '<input type="input" class="displayNone" id="ass-id" value="' + associationId + '"/>'+
					'<input type="input" class="displayNone" id="poCatId" value="' + poCatId + '"/><input type="input" class="displayNone" id="subCatId" value="' + subCatId + '"/></li></ul>'
					+'<p> <h3><u>Note:</u> By deactivate the association. PO Category & Sub Category will be deactivate,if there is no other association contains PO Category & Sub Category.</h3></p>'
					+ '<div style="position:absolute;bottom:3px;right:5px;"><a class="secondaryLink cancel" tabIndex="1">No, Cancel</a><a class="buttonPrimary delete" tabIndex="2">Yes, Delete</a></div>');
			
			openModal($('#deactivate-category-association-modal'));
		 });
		
		//edit association
		$categoryAssociationTable.on("click",'.edit-category-association',function(){
	
			var $this =$(this);
			var associationId = $this.closest('.category-association-row').find('.editable').find('.association-id').val();
			var $getEditCategoryAssociationModalContentPromise  =$.get("get-edit-category-association-modal-content",{associationId:associationId});
			
			$getEditCategoryAssociationModalContentPromise.done(function(data){
				$addAssociationModal.html(data);
				$('#add-association-modal').dialog({title: 'Edit Association'});
				$('.associated-fields').css({"display":"inline"},{"width":"198px"});
				$('.checkboxStyle').css({"padding-left":"152px"});
				
				openModal($addAssociationModal);
				
		    });
			
		 });
		
		
		//activate association
		$categoryAssociationTable.on("click",'.activat-association',function(){
			$('#activate-category-association-modal').empty();
			var $this =  $(this);
			var associationId = $this.closest('.category-association-row').find('.editable').find('.association-id').val();
			var poCatName = $this.closest('.category-association-row').find('.po-cat-name').text();
			var subCatName = $this.closest('.category-association-row').find('.sub-cat-name').text();
			var poCatId= $(this).closest('.category-association-row').find('.poCategory-id').val();
			var subCatId=$(this).closest('.category-association-row').find('.subCategory-id').val();
			$('#activate-category-association-modal').prepend('<p>Are you sure you want to activate this PO Category</p><ul><li>' + poCatName + '-' + subCatName + '<input type="input" class="displayNone" id="ass-id" value="' + associationId + '"/>'+
					'<input type="input" class="displayNone" id="poCatId" value="' + poCatId + '"/><input type="input" class="displayNone" id="subCatId" value="' + subCatId + '"/></li></ul>'
						+'<p> <h3><u>Note:</u> By activating the association  PO Category & Sub Category will be activated.</h3></p>'
						+ '<div style="position:absolute;bottom:3px;right:5px;"><a class="secondaryLink cancel" tabIndex="-1">No, Cancel</a><a class="buttonPrimary activateConfirm" tabIndex="-1">Yes, Activate</a></div>');
			openModal($('#activate-category-association-modal'));
		 });
		
		//deactivate association MODAL
		$('#deactivate-category-association-modal').on("click",'.delete',function(){
			var assId = $(this).closest('.modal').find('#ass-id').val();
			var poCatId= $(this).closest('.modal').find('#poCatId').val();
			var subCatId=$(this).closest('.modal').find('#subCatId').val();
			var deleteAssociationPromise = $.post('change-category-association-status',
												{assId:assId,status:'0',poCatId:poCatId,subCatId:subCatId});
				deleteAssociationPromise.done(function(data){
					location.assign('./category-association');
					$('#deactivate-category-association-modal').empty();
					closeModal($('#deactivate-category-association-modal'));
			});
			
		});
	
		//activate association MODAL
		$('#activate-category-association-modal').on("click",'.activateConfirm',function(e){
			var assId = $(this).closest('.modal').find('#ass-id').val();
			var poCatId= $(this).closest('.modal').find('#poCatId').val();
			var subCatId=$(this).closest('.modal').find('#subCatId').val();
			var $activateAssociationPromise = $.post('change-category-association-status',
												{assId:assId,status:'1',poCatId:poCatId,subCatId:subCatId});
			
			$activateAssociationPromise.done(function(data){
				location.assign('./category-association');
				$('#activate-category-association-modal').empty();
				closeModal($('#activate-category-association-modal'));
			}),
			$activateAssociationPromise.fail( function(xhr, textStatus, errorThrown) {
				$('#activate-category-association-modal').html('<p> Error occured while activating this PO Category. Retry once again'
						+'<div style="position:absolute;bottom:3px;right:5px;"><a class="secondaryLink cancel" tabIndex="-1">ok</a></div>');
				e.stopPropagation();
				
		    });
			//location.assign(
			//return false;
		});
		
		$addCategoryAssociation.on("click",function(){
		
			var $getAddCategoryAssociationModalContentPromise  =$.get("get-add-category-association-modal-content");
				
			$getAddCategoryAssociationModalContentPromise.done(function(data){
				$addAssociationModal.html(data);
				$('#add-association-modal').dialog({title: 'Add Association'});
				openModal($addAssociationModal);
				
		    });
		
		});
		
		$addAssociationModal.on('keypress', function(e) {
			if (e.which == 13) {
				addAssociation();
				event.preventDefault();
			}
		});
		
		$addAssociationModal.on("change",'#po-category',function(){
			var $this =$(this);
			var poCategoryId = $this.val();
			var poCategoryText = $(this).find("option:selected").text();
			if(poCategoryText=='MISC' ){
				$(".associated-fields").show();
				$('#makeModelYearRequired').prop('checked',true);
				$('#vehicleSizeRequired').prop('checked',true);
				$('#vehicleTypeRequired').prop('checked',true);
			}else{
				$(".associated-fields").hide();
				$('#makeModelYearRequired').prop('checked',false);
				$('#vehicleSizeRequired').prop('checked',false);
				$('#vehicleTypeRequired').prop('checked',false);
			}
			var $getPoSubCategoriesPromise =$.get("get-sub-categories-association",{poCategoryId:poCategoryId,poCategoryText:poCategoryText});
			var $poSubCategoryDropDown = $('#sub-category');
			$getPoSubCategoriesPromise.done(function(data){
				
				$poSubCategoryDropDown.attr('disabled', false);
				$poSubCategoryDropDown.empty().append("<option value="+''+">Select</option>");
				var poSubCategoryList =data["subCategoryList"];
				var makeModelYearReqdAsDefault = data["makeModelYearReqdAsDefault"];
				var vehicleTypeReqdAsDefault = data["vehicleTypeReqdAsDefault"];
				var vehicleSizeReqdAsDefault = data["vehicleSizeReqdAsDefault"];
				
				if(poSubCategoryList !=null && poSubCategoryList.length>0){
					 $('.error-messages-container').addClass('displayNone');
					 $('.error-messages-container').find('.errorMsg').text('');
					$.each(poSubCategoryList,function(key,value){
						var poSubCategory = value;
						$poSubCategoryDropDown.append('<option value = "'+poSubCategory.subCategoryId+'">'+poSubCategory.subCategoryName +'</option>');
						
					});
				}else{
					$poSubCategoryDropDown.attr('disabled', true);
					 $('.error-messages-container').removeClass('displayNone');
					$('.error-messages-container').find('.errorMsg').text("PO Category-'"+poCategoryText+"' associated to all Sub-Categories");
				}
				
				if(poCategoryText!='MISC'){
					if(makeModelYearReqdAsDefault == true){
					$('#makeModelYearRequired').prop('checked',true);
					}
					if(vehicleSizeReqdAsDefault  == true){
					$('#vehicleSizeRequired').prop('checked',true);
					}
					if(vehicleTypeReqdAsDefault  == true){
					$('#vehicleTypeRequired').prop('checked',true);
					}
				}
		
			});
			
		});
		
		$addAssociationModal.on("click",'.save-association',function(){
			addAssociation();
		});
		
		function addAssociation(){

			var $error = $addAssociationModal.find('.error');
			var $selectedPoCategory = $('#po-category').find('option:selected');
			var poCategoryId= $selectedPoCategory.val();
			var $selectedSubCategory = $('#sub-category').find('option:selected');
			var subCategoryId= $selectedSubCategory.val();
			var makeModelYearRequired = $('#makeModelYearRequired').is(':checked')?true:false;
			var vehicleSizeRequired = $('#vehicleSizeRequired').is(':checked')?true:false;
			var vehicleTypeRequired = $('#vehicleTypeRequired').is(':checked')?true:false;
			
			 $('.error-messages-container').addClass('displayNone');
			 $('.error-messages-container').find('.errorMsg').text('');
			if(validate(poCategoryId,subCategoryId)){
				var $addCategoryAssociationPromise =$.post("add-category-association",{poCategoryId:poCategoryId,subCategoryId:subCategoryId,makeModelYearRequired:makeModelYearRequired,vehicleSizeRequired:vehicleSizeRequired,vehicleTypeRequired:vehicleTypeRequired});
				$error.hide();
				$addCategoryAssociationPromise.done(function(data){
					
				
					location.assign('./category-association');
				});
				
				$addCategoryAssociationPromise.fail( function(xhr, textStatus, errorThrown) {
					 if(xhr.responseText.indexOf('POCategory-SubCategory association already exist')>0){
						 $('.error-messages-container').removeClass('displayNone');
						 $('.error-messages-container').find('.errorMsg').text("'POCategory-SubCategory association already exist'");
					  }			
			    });
			}else{
				 $('.error-messages-container').removeClass('displayNone');
				 $('.error-messages-container').find('.errorMsg').text("A required field is missing.");
			}
			
		
		}
		
		$addAssociationModal.on("click",'.update-association',function(){
			updateAssociation();
		});
		
		function updateAssociation(){

			var $error = $addAssociationModal.find('.error');
			var associationId = $('#associationId').val();
			var makeModelYearRequired = $('#makeModelYearRequired').is(':checked')?true:false;
			var vehicleSizeRequired = $('#vehicleSizeRequired').is(':checked')?true:false;
			var vehicleTypeRequired = $('#vehicleTypeRequired').is(':checked')?true:false;
			 $('.error-messages-container').addClass('displayNone');
			 $('.error-messages-container').find('.errorMsg').text('');
			if(associationId!=null){
				var $updateCategoryAssociationPromise =$.post("update-category-association",{associationId:associationId,makeModelYearRequired:makeModelYearRequired,vehicleSizeRequired:vehicleSizeRequired,vehicleTypeRequired:vehicleTypeRequired});
				$error.hide();
				$updateCategoryAssociationPromise.done(function(data){
					
				location.assign('./category-association');
				});
				
			}else{
				 $('.error-messages-container').removeClass('displayNone');
				 $('.error-messages-container').find('.errorMsg').text("Error occured during Insert");
				//  return view();
			}
			
		
		}
});

		function validate(poCategoryId,subCategoryId){
			var flag=true;
			if(poCategoryId=='Select' ){
				flag=false;
			}
			
			if(subCategoryId=='Select' || subCategoryId==''){
				flag=false;
			}
			
			return flag;
		}


