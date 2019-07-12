jQuery(document).ready(function() {
    
    validateAndShowMessage();
    
});


function calculateHouseholdScore() {

    jQuery("#_lifeDomainhh").val('HOUSEHOLD MANAGEMENT');

    jQuery('#currentHHScore').val('');
    jQuery('#currentHHGoal').val('');
    jQuery('#hhmst_6_body').text('');
    jQuery('#hhmst_7_body').text('');
    jQuery('[id^=hhmst_]').removeClass('danger').removeClass('success').removeClass('info').removeClass('warning');

    debugger;
    var qChoice1 = jQuery('input[id^=_householdQuestionnaire_40]:radio');
    var qChoice2 = jQuery('input[id^=_householdQuestionnaire_41]:radio');
    var qChoice3 = jQuery('input[id^=_householdQuestionnaire_42]:radio');
    var qChoice4 = jQuery('input[id^=_householdQuestionnaire_43]:radio');
    var qChoice5 = jQuery('input[id^=_householdQuestionnaire_44]:radio');
    var qChoice6 = jQuery('input[id^=_householdQuestionnaire_45]:radio');
    var qChoice7 = jQuery('input[id^=_householdQuestionnaire_46]:radio');
    var qChoice8 = jQuery('input[id^=_householdQuestionnaire_47]:radio');

    if (qChoice1[0].checked == true) {

	jQuery('[id^=hhmst_1_]').removeClass('danger').addClass('danger');
	jQuery('#currentHHScore').val(1);
	jQuery('#hhmst_6_body').text(1);
	jQuery('#currentHHGoal').val(2);
	jQuery('#hhmst_7_body').text(2);

	jQuery('input[id^=_householdQuestionnaire_41]').prop('checked', false);
	jQuery('input[id^=_householdQuestionnaire_42]').prop('checked', false);
	jQuery('input[id^=_householdQuestionnaire_43]').prop('checked', false);
	jQuery('input[id^=_householdQuestionnaire_44]').prop('checked', false);
	jQuery('input[id^=_householdQuestionnaire_45]').prop('checked', false);
	jQuery('input[id^=_householdQuestionnaire_46]').prop('checked', false);
	jQuery('input[id^=_householdQuestionnaire_47]').prop('checked', false);

	jQuery('input[id^=_householdQuestionnaire_41]').prop('disabled', true);
	jQuery('input[id^=_householdQuestionnaire_42]').prop('disabled', true);
	jQuery('input[id^=_householdQuestionnaire_43]').prop('disabled', true);
	jQuery('input[id^=_householdQuestionnaire_44]').prop('disabled', true);
	jQuery('input[id^=_householdQuestionnaire_45]').prop('disabled', true);
	jQuery('input[id^=_householdQuestionnaire_46]').prop('disabled', true);
	jQuery('input[id^=_householdQuestionnaire_47]').prop('disabled', true);

    } else {

	jQuery('input[id^=_householdQuestionnaire_41]').prop('disabled', false);
	jQuery('input[id^=_householdQuestionnaire_42]').prop('disabled', false);
	jQuery('input[id^=_householdQuestionnaire_43]').prop('disabled', false);
	jQuery('input[id^=_householdQuestionnaire_44]').prop('disabled', false);
	jQuery('input[id^=_householdQuestionnaire_45]').prop('disabled', false);
	jQuery('input[id^=_householdQuestionnaire_46]').prop('disabled', false);
	jQuery('input[id^=_householdQuestionnaire_47]').prop('disabled', false);

	if (qChoice2[0].checked == true) {

	    jQuery('input[id^=_householdQuestionnaire_42]').prop('checked', false);
	    jQuery('input[id^=_householdQuestionnaire_43]').prop('checked', false);
	    jQuery('input[id^=_householdQuestionnaire_44]').prop('checked', false);

	    jQuery('input[id^=_householdQuestionnaire_42]').prop('disabled', true);
	    jQuery('input[id^=_householdQuestionnaire_43]').prop('disabled', true);
	    jQuery('input[id^=_householdQuestionnaire_44]').prop('disabled', true);
	    
	    checkChoice678(qChoice6, qChoice7,qChoice8);

	}

	else {

	    jQuery('input[id^=_householdQuestionnaire_42]').prop('disabled', false);
	    jQuery('input[id^=_householdQuestionnaire_43]').prop('disabled', false);
	    jQuery('input[id^=_householdQuestionnaire_44]').prop('disabled', false);

	    if (qChoice3[1].checked == true) {

		jQuery('[id^=hhmst_2_]').removeClass('warning').addClass('warning');
		jQuery('#currentHHScore').val(2);
		jQuery('#hhmst_6_body').text(2);
		jQuery('#currentHHGoal').val(3);
		jQuery('#hhmst_7_body').text(3);

		jQuery('input[id^=_householdQuestionnaire_43]').prop('checked', false);
		jQuery('input[id^=_householdQuestionnaire_44]').prop('checked', false);
		jQuery('input[id^=_householdQuestionnaire_45]').prop('checked', false);
		jQuery('input[id^=_householdQuestionnaire_46]').prop('checked', false);
		jQuery('input[id^=_householdQuestionnaire_47]').prop('checked', false);

		jQuery('input[id^=_householdQuestionnaire_43]').prop('disabled', true);
		jQuery('input[id^=_householdQuestionnaire_44]').prop('disabled', true);
		jQuery('input[id^=_householdQuestionnaire_45]').prop('disabled', true);
		jQuery('input[id^=_householdQuestionnaire_46]').prop('disabled', true);
		jQuery('input[id^=_householdQuestionnaire_47]').prop('disabled', true);

	    }

	    else {

		jQuery('input[id^=_householdQuestionnaire_43]').prop('disabled', false);
		jQuery('input[id^=_householdQuestionnaire_44]').prop('disabled', false);
		jQuery('input[id^=_householdQuestionnaire_45]').prop('disabled', false);
		jQuery('input[id^=_householdQuestionnaire_46]').prop('disabled', false);
		jQuery('input[id^=_householdQuestionnaire_47]').prop('disabled', false);

		if (qChoice4[1].checked == true) {

		    jQuery('[id^=hhmst_2_]').removeClass('warning').addClass('warning');
		    jQuery('#currentHHScore').val(2);
		    jQuery('#hhmst_6_body').text(2);
		    jQuery('#currentHHGoal').val(3);
		    jQuery('#hhmst_7_body').text(3);

		    jQuery('input[id^=_householdQuestionnaire_44]').prop('checked', false);
		    jQuery('input[id^=_householdQuestionnaire_45]').prop('checked', false);
		    jQuery('input[id^=_householdQuestionnaire_46]').prop('checked', false);
		    jQuery('input[id^=_householdQuestionnaire_47]').prop('checked', false);

		    jQuery('input[id^=_householdQuestionnaire_44]').prop('disabled', true);
		    jQuery('input[id^=_householdQuestionnaire_45]').prop('disabled', true);
		    jQuery('input[id^=_householdQuestionnaire_46]').prop('disabled', true);
		    jQuery('input[id^=_householdQuestionnaire_47]').prop('disabled', true);

		} else {

		    jQuery('input[id^=_householdQuestionnaire_44]').prop('disabled', false);
		    jQuery('input[id^=_householdQuestionnaire_45]').prop('disabled', false);
		    jQuery('input[id^=_householdQuestionnaire_46]').prop('disabled', false);
		    jQuery('input[id^=_householdQuestionnaire_47]').prop('disabled', false);

		    if (qChoice5[1].checked == true) {

			jQuery('[id^=hhmst_2_]').removeClass('warning').addClass('warning');
			jQuery('#currentHHScore').val(2);
			jQuery('#hhmst_6_body').text(2);
			jQuery('#currentHHGoal').val(3);
			jQuery('#hhmst_7_body').text(3);

			jQuery('input[id^=_householdQuestionnaire_45]').prop('checked', false);
			jQuery('input[id^=_householdQuestionnaire_46]').prop('checked', false);
			jQuery('input[id^=_householdQuestionnaire_47]').prop('checked', false);

			jQuery('input[id^=_householdQuestionnaire_45]').prop('disabled', true);
			jQuery('input[id^=_householdQuestionnaire_46]').prop('disabled', true);
			jQuery('input[id^=_householdQuestionnaire_47]').prop('disabled', true);

		    } else {

			jQuery('input[id^=_householdQuestionnaire_45]').prop('disabled', false);
			jQuery('input[id^=_householdQuestionnaire_46]').prop('disabled', false);
			jQuery('input[id^=_householdQuestionnaire_47]').prop('disabled', false);
			
			checkChoice678(qChoice6,qChoice7,qChoice8);
		    }

		}

	    }

	}

    }

}

function checkChoice678(qChoice6,qChoice7,qChoice8){
    
    
	if (qChoice6[1].checked == true) {

	    jQuery('[id^=hhmst_2_]').removeClass('warning').addClass('warning');
	    jQuery('#currentHHScore').val(2);
	    jQuery('#hhmst_6_body').text(2);
	    jQuery('#currentHHGoal').val(3);
	    jQuery('#hhmst_7_body').text(3);

	    jQuery('input[id^=_householdQuestionnaire_46]').prop('checked', false);
	    jQuery('input[id^=_householdQuestionnaire_47]').prop('checked', false);

	    jQuery('input[id^=_householdQuestionnaire_46]').prop('disabled', true);
	    jQuery('input[id^=_householdQuestionnaire_47]').prop('disabled', true);

	} else {

	    jQuery('input[id^=_householdQuestionnaire_46]').prop('disabled', false);
	    jQuery('input[id^=_householdQuestionnaire_47]').prop('disabled', false);

	    if (qChoice7[1].checked == true) {

		jQuery('[id^=hhmst_3_]').removeClass('info').addClass('info');
		jQuery('#currentHHScore').val(3);
		jQuery('#hhmst_6_body').text(3);
		jQuery('#currentHHGoal').val(4);
		jQuery('#hhmst_7_body').text(4);

		jQuery('input[id^=_householdQuestionnaire_47]').prop('checked', false);
		jQuery('input[id^=_householdQuestionnaire_47]').prop('disabled', true);

	    } else {
		jQuery('input[id^=_householdQuestionnaire_47]').prop('disabled', false);
		if (qChoice8[0].checked == true) {

		    jQuery('[id^=hhmst_5_]').removeClass('success').addClass('success');
		    jQuery('#currentHHScore').val(5);
		    jQuery('#hhmst_6_body').text(5);
		    jQuery('#currentHHGoal').val(5);
		    jQuery('#hhmst_7_body').text(5);

		} else {

		    if (qChoice8[1].checked == true) {

			jQuery('[id^=hhmst_4_]').removeClass('success').addClass('success');
			jQuery('#currentHHScore').val(4);
			jQuery('#hhmst_6_body').text(4);
			jQuery('#currentHHGoal').val(5);
			jQuery('#hhmst_7_body').text(5);

		    }

		}
	    }

	}
}
