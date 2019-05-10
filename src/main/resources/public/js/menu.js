jQuery(document).ready(function() {

	jQuery('a').parent().removeClass('active');
	var path = window.location.pathname;
	if (path == '/newResident' || path == "/getResidentById") {
		jQuery("a[href='/newResident']").parent().addClass('active');
	} else if (path == '/admin' || path == "/saveServiceCoordinator" ) {
		jQuery("a[href='/admin']").parent().addClass('active');
	} else if (path == '/allResident') {
		jQuery("a[href='/allResident']").parent().addClass('active');
	} else if (path == '/actionPlans') {
		jQuery("a[href='/actionPlans']").parent().addClass('active');
	} else if (path == '/caseNotes') {
		jQuery("a[href='/caseNotes']").parent().addClass('active');
	} else if (path == '/dashboard') {
		jQuery("a[href='/dashboard']").parent().addClass('active');
	}
	
});