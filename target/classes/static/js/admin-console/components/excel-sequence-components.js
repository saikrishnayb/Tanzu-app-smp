$("#componetsTable tbody").sortable({
  cancel : ".group .pointer",
  helper : fixWidthHelper,
  start : function(e, ui) {
    // modify ui.placeholder however you like
    ui.placeholder.find('tr').outerHeight(ui.item.outerHeight(true));
  },
  update : function(e, ui) {
    resetSequence('#componetsTable');
    $('#submitComponentSeq').removeClass("buttonDisabled");
    $("#ErrorMsg").hide();
  },
  placeholder : "ui-state-highlight",
  forcePlaceholderSize : true,
  items : "tr:not(.group)",
  connectWith : "table tbody"
}).disableSelection();

function fixWidthHelper(e, ui) {
  ui.children().each(function() {
    $(this).width($(this).width());
  });
  return ui;
}

//Renumber table rows
function resetSequence(tableID) {
  $("#componetsTable tr").each(function() {
    count = $(this).parent().children().index($(this)) + 1;
    $(this).find('.seq').html(count);
    $(this).find('.seq').val(count);
  });

}

//serach for  components 
$("#componentSearch").keyup(function() {
  $this = this;
  // Show only matching TR, hide rest of them
  $.each($("#componetsTable tbody tr"), function() {

    //Implementing smart search as like datatables
    var searchKeys = new Array();
    searchKeys = $($this).val().toLowerCase().split(" ");
    var recFound = true;
    for (var index = 0; index < searchKeys.length; index++) {
      if (recFound) {
        if ($(this).text().toLowerCase().indexOf(searchKeys[index]) === -1) {
          recFound = false;
          $(this).hide();
        } else {
          recFound = true;
          $(this).show();
        }
      }
    }

  });

});

/* to submit the form */
function submitComponentSeqForm() {
  if ($("#submitComponentSeq").hasClass('buttonDisabled')) {
    return false;
  }
  //addFormBindingData();
  var $excelSeqCompForm = $("#excelSeqCompForm");
  $excelSeqCompForm.attr("action", "update-template-componenet-sequence");
  processingImageAndTextHandler('visible', 'updating data...');
  $.ajax({
    type : "POST",
    url : "./update-template-componenet-sequence",
    data : $("#excelSeqCompForm").serialize(),
    processData : false,
    success : function(data) {
      processingImageAndTextHandler('hidden');
      ModalUtil.closeModal($templateModal);
    },
    error : function() {
      $("#ErrorMsg").show();
      processingImageAndTextHandler('hidden');
    }
  });

}
