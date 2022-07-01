var $addRuleAssociationModal = $('#rule-association-modal');

$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-loadsheet-management");
	$templateTable=$("#component-table");
	//component table
	$templateTable.dataTable( { //All of the below are optional
		"aaSorting"			: [[ 0, "asc" ],[ 1, "asc" ],[ 2, "asc" ]], //default sort column
		"bPaginate"			: false, //enable pagination
		"bStateSave"		: true,	//To retrieve the data on click of back button
		"aoColumnDefs"		: [{ 'bSortable': false, 'aTargets': [5] } ],//disable sorting for specific column indexes
		"sScrollY"			: "400px",
		"sScrollX"			: "100%",
		"bInfo" 			: false,
		"bScrollCollapse"	: true,
		"bAutoWidth"		: true, //cray cray
		"bFilter"			: true, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		"bSort"				: true, //Allow sorting by column header
		"oLanguage"			: {"sEmptyTable": "No Results Found"}, //Message displayed when no records are found
		"search"			: {
								type: "text",
								bRegex: true,
								bSmart: true
								},
		"fnDrawCallback"	: function(settings, json) {
									//to highlight the selected component   
									var sletedComp=$("#selectedComponentId").val();
									if(sletedComp){
										   $("#"+sletedComp).addClass("row_selected");
										   if($("tr.row_selected").offset() != null) // highlight only if search results contains selectd row.
										   $(".dataTables_scrollBody").scrollTop( $("tr.row_selected").offset().top - $(".dataTables_scrollBody").height() );
									}
									   
									   
								},
		"fnStateSave"		: function (oSettings, oData) {
						            localStorage.setItem( 'loadSheetCompTable', JSON.stringify(oData) );
						        },
		"fnStateLoad"		: function (oSettings) {
						            return JSON.parse( localStorage.getItem('loadSheetCompTable') );
						        }
	} );
	
	var compRequestedFrom=$("#compRequestedFrom").val();
	var strHTML='<div id="org-desc-div" style="float: right; text-align: right;margin-bottom: 1%;">'+
	'<a class="buttonSecondary floatLeft clear-left back" href="goBack-componets?requestedFrom='+compRequestedFrom+'">Back</a>'+
'</div>';
$("#component-table_wrapper").prepend(strHTML);


$('#rule-association-modal').dialog({
	autoOpen: false,
	modal: true,
	dialogClass: 'popupModal',
	width: 500,
	minHeight: 300,
	resizable: false,
	title: 'Rule Association',
	closeOnEscape: false,
	close: function(event, ui)
    {
    
		var url=window.location.href;
		if (url.indexOf("requestedFrom") >= 0){	// if request came from Back button in create rule
			window.location.href = url.split('?')[0]+"?componentId="+$("#componentIdforAddRule").val();
		}else if(url.indexOf("create-rule") >= 0){//if request came from Save button in create rule
			url=url.replace("create-rule","goBack-createRule");
			window.location.href = url.split('?')[0]+"?componentId="+$("#componentIdforAddRule").val();
		}else{
			//add component id while reloading to scroll to the correct component.
			var url=window.location.href;
			//i frequest comes from loadsheetmanagement page
			if(url.indexOf("get-loadsheet-components") >=0){
				if(url.indexOf("?componentId") >=0){
					window.location.href = url.split('?')[0]+"?componentId="+$("#componentIdforAddRule").val();
				}
				url=url.substring(0,url.indexOf("&componentId", 0));
				if(url==''){
				window.location.href=window.location.href +"&componentId="+$("#componentIdforAddRule").val();
				}else{
					window.location.href=url +"&componentId="+$("#componentIdforAddRule").val();
				} 
				
			}else{//else from create rule back/save button
				window.location.href = url.split('?')[0]+"?componentId="+$("#componentIdforAddRule").val();
			}
		}
		processingImageAndTextHandler('visible','Loading data...');
    }
});

/* ------------- Adding A Rule -------------- */
$('.add-rule-association').on('click', function() {
	
	//remove previously highlighted row.
	var sletedComp=$("#selectedComponentId").val();
	if(sletedComp){
		   $("#"+sletedComp).removeClass("row_selected");
	}
	var val = $(this).attr('id');
	var displayName = $(this).data('value');
	var values=val.split('-');
	$("#componentIdforAddRule").val(values[0]);
	$.post('./get-rule-association-modal-data',
			{'componentId':values[0],'componentVisibleId':values[1],'viewMode':values[2],'displayName':displayName},
			function(data) {
				$('#rule-association-modal').html(data);
				$('#rule-association-modal').dialog('open');
				if(values[2]!='Y'){
					$(".ui-dialog-titlebar").prepend('<a class="buttonPrimary" style="margin-left: 37%;" onclick="processingImageAndTextHandler(\'visible\',\'Loading data...\');" href="./load-create-rule?requestedFrom=ADD_RULE" >Create Rule</a>');
				}
			});
});

//To Auto open the Rule Association popup
var componentId=$("#componentId").val();
var visibilityId=$("#visibilityId").val();
var viewMode=$("#viewMode").val();
if(componentId!=''&&visibilityId!=''&viewMode!=''){
	var $divId=componentId+"-"+visibilityId+"-"+viewMode;
	$("#componentIdforAddRule").val(componentId);
	$('#'+$divId)[0].click();
	
}

});









