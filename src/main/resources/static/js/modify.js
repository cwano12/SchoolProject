$(document).ready(function() {
    showSuccessMessage();

    $('#myTable').dataTable();

});

function showSuccessMessage() {
    if($('#success').html() != "") {
       $('.alert.alert-success.hidden').removeClass('hidden');
    }
}