var $addRuleAssociationModal = $('#rule-association-modal');
$(document).ready(function() {
	selectCurrentNavigation("tab-app-config", "left-nav-loadsheet-management");
	$templateTable=$("#component-table");
	//component table
	$templateTable.dataTable( { //All of the below are optional
		"aaSorting": [[ 0, "asc" ]], //default sort column
		"bPaginate": false, //enable pagination
		"aoColumnDefs"		: [{ 'bSortable': false, 'aTargets': [5] } ],//disable sorting for specific column indexes
		"sScrollY": "400px",
		"sScrollX": "100%",
		"bInfo" : false,
		"bScrollCollapse": true,
		"bAutoWidth": true, //cray cray
		"bFilter": true, //Allows dynamic filtering of results, do not enable if using ajax for pagination
		"bSort": true, //Allow sorting by column header
		"oLanguage": {"sEmptyTable": "No Results Found"}, //Message displayed when no records are found
		"search": {
			type: "text",
			bRegex: true,
			bSmart: true
			 }
		
	} );
	
	var strHTML='<div id="org-desc-div" style="float: right; text-align: right;margin-right: 2%;">'+
	'<a class="buttonSecondary floatLeft clear-left back" href="loadsheet-management.htm">Back</a>'+
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
			window.location.href = url.split('?')[0];
		}else if(url.indexOf("create-rule.htm") >= 0){//if request came from Save button in create rule
			window.location.href=url.replace("create-rule.htm","goBack-createRule.htm");
		}else{
			location.reload();	
		}
		processingImageAndTextHandler('visible','Loading data...');
    }
});

/* ------------- Adding A Rule -------------- */
$('.add-rule-association').on('click', function() {
	var val = $(this).attr('id');
	var values=val.split('-');
	$.post('./get-rule-association-modal-data.htm',
			{'componentId':values[0],'componentVisibleId':values[1],'viewMode':values[2]},
			function(data) {
				$('#rule-association-modal').html(data);
				$('#rule-association-modal').dialog('open');
				if(values[2]!='Y'){
					$(".ui-dialog-titlebar").prepend('<a class="buttonPrimary" style="margin-left: 37%;" onclick="processingImageAndTextHandler(\'visible\',\'Loading data...\');" href="load-create-rule.htm?requestedFrom=ADD_RULE" >Create Rule</a>');
				}
			});
});

//To Auto open the Rule Association popup
var componentId=$("#componentId").val();
var visibilityId=$("#visibilityId").val();
var viewMode=$("#viewMode").val();
if(componentId!=''&&visibilityId!=''&viewMode!=''){
	var $divId=componentId+"-"+visibilityId+"-"+viewMode;
	$('#'+$divId)[0].click();
	
}

});









