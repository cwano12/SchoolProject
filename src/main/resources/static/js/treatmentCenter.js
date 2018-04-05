$(document).ready(function(){

    addCustomRegexMethods();

    validateForm();

    showSuccessMessage();

    showErrorMessage();
});

function addCustomRegexMethods() {
    $.validator.addMethod("validcity", function(value, element) {
        return this.optional(element) || /^[A-Z]([A-Za-z\s]+)$/.test(value);
    }, "Please enter a valid city.");
    $.validator.addMethod("validstate", function(value, element) {
        return this.optional(element) || /[A-Z][A-Z]/.test(value);
    }, "Please enter a valid state.");
    $.validator.addMethod("validzip", function(value, element) {
        return this.optional(element) || /^([0-9]+)$/.test(value);
    }, "Please enter a valid zip code.");
    $.validator.addMethod("validphone", function(value, element) {
        return this.optional(element) || /^([0-9]+)$/.test(value);
    }, "Please enter a valid phone number.");
}

// custom form validation
function validateForm() {
    $('#treatmentCenterForm').validate({
        rules: {
            name: {
                required: true,
                minlength: 4
            },
            address: {
                required: true,
                minlength: 5
            },
            city: {
                required: true,
                minlength: 2,
                validcity: '^[A-Z]([A-Za-z\s]+)$'
            },
            state: {
                required: true,
                minlength: 2,
                maxlength: 2,
                validstate: '[A-Z][A-Z]'
            },
            zip: {
                required: true,
                minlength: 5,
                maxlength: 5,
                validzip: '^([0-9]+)$'
            },
            phoneNumber: {
                required: true,
                minlength: 10,
                maxlength: 10,
                validphone: '^([0-9]+)$'
            }
        },
        messages: {
            name: {
                required: "Name is required.",
            },
            address: {
                required: "Address is required.",
            },
            city: {
                required: "City is required.",
            },
            state: {
                required: "State is required.",
            },
            zip: {
                required: "Zip is required.",
            },
            phoneNumber: {
                required: "Phone number is required."
            }
        }
    });
 }

function showSuccessMessage() {
    if($('#success').html() != "" && $('#success').html() != null) {
        $('.alert.alert-success.hidden').removeClass('hidden');
    }
}

function showErrorMessage() {
    if($('#error').html() != "" && $('#error').html() != null && ($('#success').html() == null || $('#success').html() == "")) {
        $('.alert.alert-danger.hidden').removeClass('hidden');
    }
}



