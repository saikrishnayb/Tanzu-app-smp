/**
 * 
 */
$(document).ready(function() {
	selectCurrentNavigation("tab-components", "left-nav-excel-seq");
	
	$(".templateNameClass").hover(function () {
	    $(this).toggleClass("seqNameHover");
	 });
	
	
	$("#excelSeqName-Table tr").click(function(){
		   $(this).find('td').addClass('selectedName');
		   $(this).siblings().children().removeClass('selectedName');
		   
		   var templateId=$(this).find('td').attr('id');
		   if (typeof templateId != 'undefined'){
		   //load component details
		   $("#componentDetails").hide();
		   $("#showWait").show();
			
		   displayFlag=false;
		   $.ajax({
				  type: "POST",
				  url: "./get-template-component-sequence.htm",
				  cache:false,
				  data: {templateId : templateId},
				  success: function(data){
					  
					   	$("#componentDetails").show();
              			$("#componentDetails").html(data);
              			 $("#showWait").hide();
              			$('#submitComponentSeq').addClass("buttonDisabled");
              			
					
				  },error: function(jqXHR, textStatus, errorThrown) {
					  $("#showWait").hide();
				},
				complete:function(){
					execChildScript();
					
				}
				});
		   }
		   
		   
	});
	
	
});