function calculateEducationScore() {

    jQuery("#_eduLifeDomain").val('EDUCATION');

    jQuery('#edust_6_body').text('');
    jQuery('#edust_7_body').text('');
    jQuery('[id^=edust_]').removeClass('danger').removeClass('success').removeClass('info').removeClass('warning');

    debugger;
    var qChoice1 = jQuery('input[id^=_educationQuestionnaire_26]:radio');
    var qChoice2 = jQuery('input[id^=_educationQuestionnaire_27]:radio');
    var qChoice3 = jQuery('input[id^=_educationQuestionnaire_28]:radio');
    var qChoice4 = jQuery('input[id^=_educationQuestionnaire_29]:radio');
    var qChoice5 = jQuery('input[id^=_educationQuestionnaire_30]:radio');
    var qChoice6 = jQuery('input[id^=_educationQuestionnaire_31]:radio');

    if (qChoice1[0].checked == true) {

	jQuery('input[id^=_educationQuestionnaire_27]').prop('checked', false);
	jQuery('input[id^=_educationQuestionnaire_28]').prop('checked', false);
	jQuery('input[id^=_educationQuestionnaire_29]').prop('checked', false);
	jQuery('input[id^=_educationQuestionnaire_30]').prop('checked', false);

	jQuery('input[id^=_educationQuestionnaire_27]').prop('disabled', true);
	jQuery('input[id^=_educationQuestionnaire_28]').prop('disabled', true);
	jQuery('input[id^=_educationQuestionnaire_29]').prop('disabled', true);
	jQuery('input[id^=_educationQuestionnaire_30]').prop('disabled', true);

	if (qChoice6[0].checked == true) {

	    jQuery('[id^=edust_5_]').removeClass('success').addClass('success');
	    jQuery('#currentEducationScore').val(5);
	    jQuery('#edust_6_body').text(5);
	    jQuery('#currentEducationGoal').val(5);
	    jQuery('#edust_7_body').text(5);
	}

	else if (qChoice6[1].checked == true) {

	    jQuery('[id^=edust_4_]').removeClass('success').addClass('success');
	    jQuery('#currentEducationScore').val(4);
	    jQuery('#edust_6_body').text(4);
	    jQuery('#currentEducationGoal').val(5);
	    jQuery('#edust_7_body').text(5);
	}

    } else {

	jQuery('input[id^=_educationQuestionnaire_27]').prop('disabled', false);
	jQuery('input[id^=_educationQuestionnaire_28]').prop('disabled', false);
	jQuery('input[id^=_educationQuestionnaire_29]').prop('disabled', false);
	jQuery('input[id^=_educationQuestionnaire_30]').prop('disabled', false);

	if (qChoice2[1].checked == true) {

	    jQuery('[id^=edust_1_]').removeClass('danger').addClass('danger');
	    jQuery('#currentEducationScore').val(1);
	    jQuery('#edust_6_body').text(1);
	    jQuery('#currentEducationGoal').val(2);
	    jQuery('#edust_7_body').text(2);

	    jQuery('input[id^=_educationQuestionnaire_28]').prop('checked', false);
	    jQuery('input[id^=_educationQuestionnaire_29]').prop('checked', false);
	    jQuery('input[id^=_educationQuestionnaire_30]').prop('checked', false);
	    jQuery('input[id^=_educationQuestionnaire_31]').prop('checked', false);

	    jQuery('input[id^=_educationQuestionnaire_28]').prop('disabled', true);
	    jQuery('input[id^=_educationQuestionnaire_29]').prop('disabled', true);
	    jQuery('input[id^=_educationQuestionnaire_30]').prop('disabled', true);
	    jQuery('input[id^=_educationQuestionnaire_31]').prop('disabled', true);
	} else {
	    jQuery('input[id^=_educationQuestionnaire_28]').prop('disabled', false);
	    jQuery('input[id^=_educationQuestionnaire_29]').prop('disabled', false);
	    jQuery('input[id^=_educationQuestionnaire_30]').prop('disabled', false);
	    jQuery('input[id^=_educationQuestionnaire_31]').prop('disabled', false);

	    if (qChoice3[0].checked == true) {

		jQuery('[id^=edust_2_]').removeClass('warning').addClass('warning');
		jQuery('#currentEducationScore').val(2);
		jQuery('#edust_6_body').text(2);
		jQuery('#currentEducationGoal').val(3);
		jQuery('#edust_7_body').text(3);

		jQuery('input[id^=_educationQuestionnaire_29]').prop('checked', false);
		jQuery('input[id^=_educationQuestionnaire_30]').prop('checked', false);
		jQuery('input[id^=_educationQuestionnaire_31]').prop('checked', false);

		jQuery('input[id^=_educationQuestionnaire_29]').prop('disabled', true);
		jQuery('input[id^=_educationQuestionnaire_30]').prop('disabled', true);
		jQuery('input[id^=_educationQuestionnaire_31]').prop('disabled', true);

	    } else {

		jQuery('input[id^=_educationQuestionnaire_29]').prop('disabled', false);
		jQuery('input[id^=_educationQuestionnaire_30]').prop('disabled', false);
		jQuery('input[id^=_educationQuestionnaire_31]').prop('disabled', false);

		if (qChoice4[1].checked == true) {

		    jQuery('[id^=edust_2_]').removeClass('warning').addClass('warning');
		    jQuery('#currentEducationScore').val(2);
		    jQuery('#edust_6_body').text(2);
		    jQuery('#currentEducationGoal').val(3);
		    jQuery('#edust_7_body').text(3);

		    jQuery('input[id^=_educationQuestionnaire_30]').prop('checked', false);
		    jQuery('input[id^=_educationQuestionnaire_31]').prop('checked', false);

		    jQuery('input[id^=_educationQuestionnaire_30]').prop('disabled', true);
		    jQuery('input[id^=_educationQuestionnaire_31]').prop('disabled', true);

		}

		else {

		    jQuery('input[id^=_educationQuestionnaire_30]').prop('disabled', false);
		    jQuery('input[id^=_educationQuestionnaire_31]').prop('disabled', false);

		    if (qChoice6[0].checked == true) {
			jQuery('[id^=edust_5_]').removeClass('success').addClass('success');
			jQuery('#currentEducationScore').val(5);
			jQuery('#edust_6_body').text(5);
			jQuery('#currentEducationGoal').val(5);
			jQuery('#edust_7_body').text(5);
		    } else if (qChoice6[1].checked == true) {
			jQuery('[id^=edust_4_]').removeClass('success').addClass('success');
			jQuery('#currentEducationScore').val(4);
			jQuery('#edust_6_body').text(4);
			jQuery('#currentEducationGoal').val(5);
			jQuery('#edust_7_body').text(5);
		    } else if (qChoice4[0].checked == true || qChoice5[1].checked == true) {

			jQuery('[id^=edust_3_]').removeClass('info').addClass('info');
			jQuery('#currentEducationScore').val(3);
			jQuery('#edust_6_body').text(3);
			jQuery('#currentEducationGoal').val(4);
			jQuery('#edust_7_body').text(4);
		    }

		}
	    }
	}
    }
}
