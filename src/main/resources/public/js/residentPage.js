jQuery(document).ready(function() {

	jQuery('a').parent().removeClass('active');
	var path = window.location.pathname;
	if(path == '/newResident' || path == "/getResidentById"){
		jQuery("a[href='/newResident']").parent().addClass('active');
	} else if (path == '/admin') {
		jQuery("a[href='/admin']").parent().addClass('active');
	} else if (path == '/allResident') {
		jQuery("a[href='/allResident']").parent().addClass('active');
	}
});

function toggleForm(prefix) {

	var formName = "#" + prefix;

	// Toggle Form
	jQuery("[id$='_Form']").removeClass('hideme')
	jQuery("[id$='_Form']").addClass('hideme');

	jQuery(formName + "_Form").removeClass("hideme");

	// Toggle Wizard
	jQuery("[id$='_View']").removeClass('disabled');
	jQuery("[id$='_View']").addClass('disabled');

	jQuery(formName + "_View").removeClass("disabled");
	jQuery(formName + "_View").removeClass('active');
	jQuery(formName + "_View").addclass('active');

}