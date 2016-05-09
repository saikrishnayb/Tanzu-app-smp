$(document).ready(function(){ 			
				$('.menu').fixedMenu();
 				$( "#tabs" ).tabs();
				selectCurrentNavigation('Home','');
				
			$('#modalPopup').dialog({
			autoOpen: false,
			modal: true,
			dialogClass: 'popupModal',
			width: 500,
			minHeight: 300,
			position: { my: "center top", at: "center top", of: window },
			resizable: false,
			title: 'PO Details',
			closeOnEscape: false
		});
 
				$('#viewDetail').on('click', function() {
					$('#modalPopup').dialog('open');
				});
		
				$('.actionCount').on('click', function() {
					$this = $(this).attr('id');
					$('#itemDetail').load("${context}/jsp/confirmation/"+$this+".jsp");
					$("[id$='summary'] > [id$='Container']").removeClass('selectedItem');
					$("#"+$this+" > [id$='Container']").addClass('selectedItem');
				});
 
				$('#accordionMain').accordion({					
					activate: 0,
					collapsible: true							
			        
				});
				$('#accordionMain1').accordion({
					activate: 1,
					collapsible: true   
				});	
				$('#accordionMain2').accordion({
					activate: 2,
					collapsible: true      
				});	
				$('#accordionMain4').accordion({
					activate: 2,
					collapsible: true      
				});				
				
				
				$.get("${context}/src/accordion.html", function(data) {	
						$("#viewSource").append(data);	
					},"html");
			
 
				$('.menu').fixedMenu();
				  $('#Clear').click( function (e) {	                         
	                          
	                            $('#ResetForm')[0].reset()
	                            
	                            });	

			$('.actionCount:first').trigger('click');	
			
});


	
	
	

