	var existingBuddyList=$("#existingBuddyList").val();

	var existingBuddyArray=new Array();
	var newBuddyArray=new Array();

$(document).ready(function() 
{
	$('li:has("ul")>div').parent('li').addClass('expanded');
	$('li:has("ul")>div').html('&minus;');
	 
     //Handle plus/minus tree expansion
     $('li>div').on('click', function (e) {
         if($(this).parent('li').hasClass('expanded')) {
             $(this).siblings('ul').slideUp();
             $(this).parent('li').removeClass('expanded');
             $(this).html('&plus;');
         } else {
             $(this).siblings('ul').slideDown();
             $(this).parent('li').addClass('expanded');
             $(this).html('&minus;');
         }
     });

	if(existingBuddyList !== undefined && existingBuddyList.length>0)
	{
		existingBuddyArray = existingBuddyList.split("[")[1];
		existingBuddyArray=existingBuddyArray.split("]")[0];
		existingBuddyArray=existingBuddyArray.split(",");
		for(var b=0;b<existingBuddyArray.length;b++)
		{
			var id=existingBuddyArray[b].trim();
			$("#"+id).prop('checked',true);	
			if(id==="allCheckBox")
			{	
				selectAll('all');
			}
		}
	}
	
	$('#clearBuddyList').on('click', clearBuddyList);
	
});


function saveBuddyList()
{
	blockPopupModal("popupModal");
	
	var allcheckBoxValue=$("#userSelection input.allCheckboxClass[type=checkbox]:checked").val();
	
	if($("#userSelection input.allCheckboxClass[type=checkbox]:checked").val()=="allCheckBox"){	//if All cehck box is selected
		newBuddyArray.push(allcheckBoxValue);
		
	}else{
		
		var allPlannerChkBxValue=$("#userSelection input.planCheckBoxClass[type=checkbox]:checked").val();//if AllPlanning is checked
		if(allPlannerChkBxValue=="planCheckBox"){
			newBuddyArray.push(allPlannerChkBxValue);
		}else{
			newBuddyArray.push("randomCheckBoxes");
		}
		
		$("#userSelection input.plannerCheckBoxValues[type=checkbox]:checked ").each(function(){	//loading selected planning users
			
			newBuddyArray.push($(this).val());
		});
		
		
		var allBuyerChkBoxValue=$("#userSelection input.buyCheckBoxClass[type=checkbox]:checked").val();
		
		if(allBuyerChkBoxValue=="buyCheckBox"){ //if All buyer checkbox is selected
			newBuddyArray.push(allBuyerChkBoxValue);
		}else{
			newBuddyArray.push("randomCheckBoxes");
		}
		
		
		$("#userSelection input.buyerCheckBoxValues[type=checkbox]:checked ").each(function(){ //loading selected buyer users
			
			newBuddyArray.push($(this).val());
		});
		
	}
		
	
	
	/*$("#userSelection input[type=checkbox]:checked").each(function() 
			{
				newBuddyArray.push($(this).val());
			}
	);*/
	var url= getContextRoot()+"/userController/deleteBuddyList";
	
	
	 $.ajax({
         url : url,
         cache:false,
         data:{newBuddies:newBuddyArray.join(),existingBuddyList:existingBuddyList},
         success : function(data) 
         {
        		$("#buddyPopup").dialog('close');
        		unBlockPopupModal("popupModal"); 
        		showLoadingForPage();
        		refreshPage();
        		$("#hasBuddies").css("display", data);
        		
        		
          },
         error: function(jqXHR, textStatus, errorThrown) 
         {
         		unBlockPopupModal("popupModal"); 
					alertModal("info","Error");
               //$(".commonException").show();
               return;
         }
	 });
}
function selectAll(decider)
{
	
	if(decider=='all')
	{
		if($('#allCheckBox').is(':checked'))
		{
			$("#buyCheckBox").prop('checked',false);
			$('#planCheckBox').prop('checked',false);
			$('.plannerCheckBox').prop('checked',false);
			$(".buyerCheckBox").prop("checked",false);
			$('.plannerCheckBox').prop("disabled", true);
			$(".buyerCheckBox").prop("disabled", true);
			$("#buyCheckBox").prop("disabled",true);
			$('#planCheckBox').prop("disabled",true);
		}
		else
			{
			$("#buyCheckBox").removeAttr("disabled");
			$('#planCheckBox').removeAttr("disabled");
			$('.plannerCheckBox').removeAttr("disabled");
			$(".buyerCheckBox").removeAttr("disabled");
			
			}
	}
	if(decider=="buy")
	if($("#buyCheckBox").is(':checked'))
	{
		$(".buyerCheckBox").prop("checked",true);
	}
	else 
	{
		$(".buyerCheckBox").prop("checked",false);
	}
	
	if(decider=='plan')
	{
		if($('#planCheckBox').is(':checked'))
		{
			$('.plannerCheckBox').prop('checked',true);
		} 
		else
		{
			$('.plannerCheckBox').prop('checked',false);
		}
	}
	
	//to select All checkbox if planning and buyer is selected
	if(decider!="all"){
		if($('#planCheckBox').is(':checked')&&$("#buyCheckBox").is(':checked')){
			$('#allCheckBox').prop('checked',true);
			selectAll("all");
		}
	}
}
function unSelectHeader(decider)
{
	if(decider=='plan')
	{
		var bool=true;
		$('#plannerDiv .plannerCheckBox').each(function(index){
			while(!($('#'+$(this).attr('id')).is(':checked'))){
				bool=false;
				break;
			};
		});
		if(bool)
		{
			$("#planCheckBox").prop('checked',true);
		}
		else
			{
			$("#planCheckBox").prop('checked',false);
			}
	}
	if(decider==='buy')
	{	 
		
			var bool=true;
			$('#buyerDiv .buyerCheckBox').each(function(index){
				while(!($('#'+$(this).attr('id')).is(':checked'))){
					bool=false;
					break;
				};
			});
			if(bool)
			{
				$("#buyCheckBox").prop('checked',true);
			}
			else
				{
				$("#buyCheckBox").prop('checked',false);
				}
	}
	
}
function closePopup(){
	$("#buddyPopup").dialog('close');
}

function clearBuddyList() {
	console.log('inside clearBuddyList');
	$('#userSelection input:checkbox:not(:disabled)').prop('checked', false);
	$('#userSelection input:checkbox:not(:checked)').prop('disabled', false);//In case of All selected
	
}