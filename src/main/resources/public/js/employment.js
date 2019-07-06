jQuery(document).ready(function() {
    
    validateAndShowMessage();
    
});


function calculateEmploymentScore() {

    jQuery("#_empLifeDomain").val('EMPLOYMENT');

    jQuery('#currentEmpScore').val('');
    jQuery('#currentEmpGoal').val('');
    jQuery('#empst_6_body').text('');
    jQuery('#empst_7_body').text('');
    jQuery('[id^=empst_]').removeClass('danger').removeClass('success').removeClass('info').removeClass('warning');

    debugger;
    var qChoice1 = jQuery('input[id^=_employmentQuestionnaire_19]:radio');
    var qChoice2 = jQuery('input[id^=_employmentQuestionnaire_20]:radio');
    var qChoice3 = jQuery('input[id^=_employmentQuestionnaire_21]:radio');
    var qChoice4 = jQuery('input[id^=_employmentQuestionnaire_22]:radio');
    var qChoice5 = jQuery('input[id^=_employmentQuestionnaire_23]:radio');
    var qChoice6 = jQuery('input[id^=_employmentQuestionnaire_24]:radio');
    var qChoice7 = jQuery('input[id^=_employmentQuestionnaire_25]:radio');

    if (qChoice1[0].checked == false) {

	if (qChoice2[1].checked == true) {

	    jQuery('[id^=empst_1_]').removeClass('danger').addClass('danger');
	    jQuery('#currentEmpScore').val(1);
	    jQuery('#empst_6_body').text(1);
	    jQuery('#currentEmpGoal').val(2);
	    jQuery('#empst_7_body').text(2);

	    jQuery('input[id^=_employmentQuestionnaire_21]').prop('checked', false);
	    jQuery('input[id^=_employmentQuestionnaire_22]').prop('checked', false);
	    jQuery('input[id^=_employmentQuestionnaire_23]').prop('checked', false);
	    jQuery('input[id^=_employmentQuestionnaire_24]').prop('checked', false);
	    jQuery('input[id^=_employmentQuestionnaire_25]').prop('checked', false);

	    jQuery('input[id^=_employmentQuestionnaire_21]').prop('disabled', true);
	    jQuery('input[id^=_employmentQuestionnaire_22]').prop('disabled', true);
	    jQuery('input[id^=_employmentQuestionnaire_23]').prop('disabled', true);
	    jQuery('input[id^=_employmentQuestionnaire_24]').prop('disabled', true);
	    jQuery('input[id^=_employmentQuestionnaire_25]').prop('disabled', true);

	} else {

	    jQuery('input[id^=_employmentQuestionnaire_21]').prop('disabled', false);
	    jQuery('input[id^=_employmentQuestionnaire_22]').prop('disabled', false);
	    jQuery('input[id^=_employmentQuestionnaire_23]').prop('disabled', false);
	    jQuery('input[id^=_employmentQuestionnaire_24]').prop('disabled', false);
	    jQuery('input[id^=_employmentQuestionnaire_25]').prop('disabled', false);

	    if (qChoice3[1].checked == true) {

		jQuery('[id^=empst_2_]').removeClass('warning').addClass('warning');
		jQuery('#currentEmpScore').val(2);
		jQuery('#empst_6_body').text(2);
		jQuery('#currentEmpGoal').val(3);
		jQuery('#empst_7_body').text(3);

		jQuery('input[id^=_employmentQuestionnaire_22]').prop('checked', false);
		jQuery('input[id^=_employmentQuestionnaire_23]').prop('checked', false);
		jQuery('input[id^=_employmentQuestionnaire_24]').prop('checked', false);
		jQuery('input[id^=_employmentQuestionnaire_25]').prop('checked', false);

		jQuery('input[id^=_employmentQuestionnaire_22]').prop('disabled', true);
		jQuery('input[id^=_employmentQuestionnaire_23]').prop('disabled', true);
		jQuery('input[id^=_employmentQuestionnaire_24]').prop('disabled', true);
		jQuery('input[id^=_employmentQuestionnaire_25]').prop('disabled', true);

	    } else {

		jQuery('input[id^=_employmentQuestionnaire_22]').prop('disabled', false);
		jQuery('input[id^=_employmentQuestionnaire_23]').prop('disabled', false);
		jQuery('input[id^=_employmentQuestionnaire_24]').prop('disabled', false);
		jQuery('input[id^=_employmentQuestionnaire_25]').prop('disabled', false);

		if (qChoice7[1].checked == true) {

		    jQuery('[id^=empst_2_]').removeClass('warning').addClass('warning');
		    jQuery('#currentEmpScore').val(2);
		    jQuery('#empst_6_body').text(2);
		    jQuery('#currentEmpGoal').val(3);
		    jQuery('#empst_7_body').text(3);

		} else {
		    if (qChoice7[0].checked == true) {

			jQuery('[id^=empst_5_]').removeClass('success').addClass('success');
			jQuery('#currentEmpScore').val(5);
			jQuery('#empst_6_body').text(5);
			jQuery('#currentEmpGoal').val(5);
			jQuery('#empst_7_body').text(5);
		    } else {
			if (qChoice6[0].checked == true) {

			    jQuery('[id^=empst_4_]').removeClass('success').addClass('success');
			    jQuery('#currentEmpScore').val(4);
			    jQuery('#empst_6_body').text(4);
			    jQuery('#currentEmpGoal').val(5);
			    jQuery('#empst_7_body').text(5);
			} else {
			    if (qChoice4[0].checked == true || qChoice5[0].checked == true) {

				jQuery('[id^=empst_3_]').removeClass('info').addClass('info');
				jQuery('#currentEmpScore').val(3);
				jQuery('#empst_6_body').text(3);
				jQuery('#currentEmpGoal').val(4);
				jQuery('#empst_7_body').text(4);
			    }
			}
		    }
		}
	    }
	}
    }
}
