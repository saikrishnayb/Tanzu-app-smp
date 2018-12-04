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
				
				
				$('.menu').fixedMenu();
				  $('#Clear').click( function (e) {	                         
	                          
	                            $('#ResetForm')[0].reset()
	                            
	                            });	

			$('.actionCount:first').trigger('click');	
			
});


	
	
	

