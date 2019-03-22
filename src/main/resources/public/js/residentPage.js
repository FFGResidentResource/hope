jQuery(document).ready(function() {

	jQuery('a').parent().removeClass('active');
	var path = window.location.pathname;
	if (path == '/newResident' || path == "/getResidentById") {
		jQuery("a[href='/newResident']").parent().addClass('active');
	} else if (path == '/admin') {
		jQuery("a[href='/admin']").parent().addClass('active');
	} else if (path == '/allResident') {
		jQuery("a[href='/allResident']").parent().addClass('active');
	}

	stepCounter();
});

function stepCounter() {

	if (res != null && res.wsCounter.signUpComplete) {
		jQuery('#addResident_View').attr('class',
				'col-xs-1 bs-wizard-step complete');
	}
	if (res != null && res.wsCounter.assessmentComplete) {
		jQuery('#ssm_View').attr('class', 'col-xs-1 bs-wizard-step complete');
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
	jQuery(formName + "_View").addClass('active');

}

document.getElementById('allowcontact').onchange = function() {
	document.getElementById('viaemail').disabled = this.checked;
	document.getElementById('email').disabled = this.checked;
	document.getElementById('viavoicemail').disabled = this.checked;
	document.getElementById('voicemail').disabled = this.checked;
	document.getElementById('viatext').disabled = this.checked;
	document.getElementById('text').disabled = this.checked;	
};

function reset(chk){
	$('.setable').val(''); $('.setable').prop('checked', false);$(chk).trigger('change');
}

function calculateHousingScoreAndGoal(){

	jQuery('#hst_6_body').text('');
	jQuery('[id^=hst_]').removeClass('danger').removeClass('success').removeClass('info').removeClass('warning');
	
	debugger;
	var qChoice1 = jQuery('input[id^=housingQuestionnaire0]');
	var qChoice2 = jQuery('input[id^=housingQuestionnaire1]');
	var qChoice3 = jQuery('input[id^=housingQuestionnaire2]');
	var qChoice4 = jQuery('input[id^=housingQuestionnaire3]');
	var qChoice5 = jQuery('input[id^=housingQuestionnaire4]');
	var qChoice6 = jQuery('input[id^=housingQuestionnaire5]');
	var qChoice7 = jQuery('input[id^=housingQuestionnaire6]');
	var qChoice8 = jQuery('input[id^=housingQuestionnaire7]');
	
	if(qChoice1[1].checked == true){		
		jQuery('[id^=hst_1_]').removeClass('danger').addClass('danger');
		jQuery('#currentHousingScore').val(1);
		jQuery('#hst_6_body').text(1);
		
	}else if(qChoice2[0].checked == true){		
		jQuery('[id^=hst_1_]').removeClass('danger').addClass('danger');
		jQuery('#currentHousingScore').val(1);
		jQuery('#hst_6_body').text(1);
						
	}else if(qChoice4[1].checked == true){		
		jQuery('[id^=hst_2_]').removeClass('warning').addClass('warning');
		jQuery('#currentHousingScore').val(2);
		jQuery('#hst_6_body').text(2);
				
	}
	else if(qChoice5[0].checked == true){		
		jQuery('[id^=hst_2_]').removeClass('warning').addClass('warning');
		jQuery('#currentHousingScore').val(2);
		jQuery('#hst_6_body').text(2);
					
	}
	else if(qChoice6[0].checked == true){		
		jQuery('[id^=hst_2_]').removeClass('warning').addClass('warning');
		jQuery('#currentHousingScore').val(2);
		jQuery('#hst_6_body').text(2);
					
	}
	else if(qChoice7[1].checked == true){		
		jQuery('[id^=hst_3_]').removeClass('info').addClass('info');
		jQuery('#currentHousingScore').val(3);
		jQuery('#hst_6_body').text(3);		
		
	}
	else if(qChoice8[0].checked == true){		
		jQuery('[id^=hst_4_]').removeClass('success').addClass('success');
		jQuery('#currentHousingScore').val(4);
		jQuery('#hst_6_body').text(4);
		
	}
	else if(qChoice8[1].checked == true){		
		jQuery('[id^=hst_5_]').removeClass('success').addClass('success');
		jQuery('#currentHousingScore').val(5);
		jQuery('#hst_6_body').text(5);		
	}
	
	if(qChoice3[0].checked == true){		
		jQuery('input[id^=housingQuestionnaire3]').attr('disabled', true);
		jQuery('input[id^=housingQuestionnaire4]').attr('disabled', true);			
	}else if (qChoice3[0].checked == false){	
		jQuery('input[id^=housingQuestionnaire3]').attr('disabled', false);
		jQuery('input[id^=housingQuestionnaire4]').attr('disabled', false);		
	}
	
}