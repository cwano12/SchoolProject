function confirmDeleteCenter(centerNumber, centerName, centerCity, centerState) {
	$("#centerNumber").val(centerNumber);
	$('#centerName').html(centerName);
	$('#centerCity').html(centerCity);
	$('#centerState').html(centerState);
	$('#deleteConfirmationPopup').modal('show');
}

function deleteCenter() {
   	$("#treatmentCenterForm").submit();
}

function cancelDeleteCenter() {
	$('#deleteConfirmationPopup').dialog('close');
}