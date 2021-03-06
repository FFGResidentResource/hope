jQuery(document).ready(function() {

    jQuery('a').parent().removeClass('active');
    var path = window.location.pathname;
    if (path == '/newResident' || path == "/getResidentById") {
	jQuery("a[href='/newResident']").parent().addClass('active');
    } else if (path == '/admin' || path == "/saveServiceCoordinator") {
	jQuery("a[href='/admin']").parent().addClass('active');
    } else if (path == '/allResident') {
	jQuery("a[href='/allResident']").parent().addClass('active');
    } else if (path == '/onboarding' || path == "/saveResident" || path == "/saveReferralForm" || path == "/saveActionPlan" || path == "/saveCaseNotes") {
	jQuery("a[href='/onboarding']").parent().addClass('active');
    } else if (path == '/caseNotes') {
	jQuery("a[href='/caseNotes']").parent().addClass('active');
    } else if (path == '/dashboard') {
	jQuery("a[href='/dashboard']").parent().addClass('active');
    }else if (path == '/audits') {
	jQuery("a[href='/audits']").parent().addClass('active');
    }else if (path == '/pullAuditReports') {
	jQuery("a[href='/audits']").parent().addClass('active');
    }

});

function validateInput(textArea) {
    textArea.value = textArea.value.replace(/[^-.?_(),+= 0-9a-zA-Z\r\n]/g, "");
}

jQuery.urlParam = function(name){
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results==null) {
       return null;
    }
    return decodeURI(results[1]) || 0;
}