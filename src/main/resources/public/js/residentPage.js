
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
	
	stepCounter();	
});

function stepCounter(){
	
	if(res !=null && res.wsCounter.signUpComplete){
    	jQuery('#addResident_View').attr('class','col-xs-1 bs-wizard-step complete');
    }
    if(res !=null && res.wsCounter.assessmentComplete){
    	jQuery('#ssm_View').attr('class','col-xs-1 bs-wizard-step complete');
    }	
}

function toggleForm(prefix) {

	var formName = "#" + prefix;

	// Toggle Form
	jQuery("[id$='_Form']").removeClass('hideme')
	jQuery("[id$='_Form']").addClass('hideme');

	jQuery(formName + "_Form").removeClass("hideme");

	// Toggle Wizard
	jQuery("[id$='_View']").removeClass('disabled');
	jQuery("[id$='_View']").addClass('disabled');

	stepCounter();
	
	jQuery(formName + "_View").removeClass("disabled");
	jQuery(formName + "_View").removeClass('active');
	jQuery(formName + "_View").addclass('active');
	
		

}

document.getElementById('allowcontact').onchange = function() {
    document.getElementById('viaemail').disabled = this.checked;
    document.getElementById('email').disabled = this.checked;
    document.getElementById('viavoicemail').disabled = this.checked;
    document.getElementById('voicemail').disabled = this.checked;
    document.getElementById('viatext').disabled = this.checked;
    document.getElementById('text').disabled = this.checked;
    document.getElementById('wantSurvey').disabled = this.checked;
};