function calculatNetworkSupportScore() {

    jQuery("#_lifeDomain").val('NETWORK SUPPORT');
    jQuery('#nsst_6_body').text('');
    jQuery('#nsst_7_body').text('');
    jQuery('[id^=nsst_]').removeClass('danger').removeClass('success')
        .removeClass('info').removeClass('warning');

    debugger;
    var qChoice1 = jQuery('input[id^=_netSupportQuestionnaire_32]:radio');
    var qChoice2 = jQuery('input[id^=_netSupportQuestionnaire_33]:radio');
    var qChoice3 = jQuery('input[id^=_netSupportQuestionnaire_34]:radio');
    var qChoice4 = jQuery('input[id^=_netSupportQuestionnaire_35]:radio');
    var qChoice5 = jQuery('input[id^=_netSupportQuestionnaire_36]:radio');
    var qChoice6 = jQuery('input[id^=_netSupportQuestionnaire_37]:radio');
    var qChoice7 = jQuery('input[id^=_netSupportQuestionnaire_38]:radio');
    var qChoice8 = jQuery('input[id^=_netSupportQuestionnaire_39]:radio');

    var emotionalSupportValue = checkSupportOptions(qChoice4);
    var financialSupportValue = checkSupportOptions(qChoice5);
    var materialSupportValue = checkSupportOptions(qChoice6);

    if (qChoice1[0].checked == true) {
        jQuery('input[id^=_netSupportQuestionnaire_33]').prop('checked', false);
        jQuery('input[id^=_netSupportQuestionnaire_33]').prop('disabled', true);

    }
    if (qChoice2[1].checked == true) {
        jQuery('[id^=nsst_1_]').removeClass('danger').addClass('danger');
        jQuery('#currentNetSupportScore').val(1);
        jQuery('#nsst_6_body').text(1);
        jQuery('#currentNetSupportGoal').val(2);
        jQuery('#nsst_7_body').text(2);

        jQuery('input[id^=_netSupportQuestionnaire_34]').prop('checked', false);
        jQuery('input[id^=_netSupportQuestionnaire_35]').prop('checked', false);
        jQuery('input[id^=_netSupportQuestionnaire_36]').prop('checked', false);
        jQuery('input[id^=_netSupportQuestionnaire_37]').prop('checked', false);
        jQuery('input[id^=_netSupportQuestionnaire_38]').prop('checked', false);
        jQuery('input[id^=_netSupportQuestionnaire_39]').prop('checked', false);

        jQuery('input[id^=_netSupportQuestionnaire_34]').prop('disabled', true);
        jQuery('input[id^=_netSupportQuestionnaire_35]').prop('disabled', true);
        jQuery('input[id^=_netSupportQuestionnaire_36]').prop('disabled', true);
        jQuery('input[id^=_netSupportQuestionnaire_37]').prop('disabled', true);
        jQuery('input[id^=_netSupportQuestionnaire_38]').prop('disabled', true);
        jQuery('input[id^=_netSupportQuestionnaire_39]').prop('disabled', true);
    } else if (qChoice2[0].checked == true) {
        jQuery('[id^=nsst_2_]').removeClass('warning').addClass('warning');
        jQuery('#currentNetSupportScore').val(2);
        jQuery('#nsst_6_body').text(2);
        jQuery('#currentNetSupportGoal').val(3);
        jQuery('#nsst_7_body').text(3);

        jQuery('input[id^=_netSupportQuestionnaire_34]').prop('checked', false);
        jQuery('input[id^=_netSupportQuestionnaire_35]').prop('checked', false);
        jQuery('input[id^=_netSupportQuestionnaire_36]').prop('checked', false);
        jQuery('input[id^=_netSupportQuestionnaire_37]').prop('checked', false);
        jQuery('input[id^=_netSupportQuestionnaire_38]').prop('checked', false);
        jQuery('input[id^=_netSupportQuestionnaire_39]').prop('checked', false);

        jQuery('input[id^=_netSupportQuestionnaire_34]').prop('disabled', true);
        jQuery('input[id^=_netSupportQuestionnaire_35]').prop('disabled', true);
        jQuery('input[id^=_netSupportQuestionnaire_36]').prop('disabled', true);
        jQuery('input[id^=_netSupportQuestionnaire_37]').prop('disabled', true);
        jQuery('input[id^=_netSupportQuestionnaire_38]').prop('disabled', true);
        jQuery('input[id^=_netSupportQuestionnaire_39]').prop('disabled', true);

    }
    else {
        if(!qChoice1[0].checked){
           jQuery('input[id^=_netSupportQuestionnaire_33]').prop('disabled', false);
        }
        jQuery('input[id^=_netSupportQuestionnaire_34]').prop('disabled', false);
        jQuery('input[id^=_netSupportQuestionnaire_35]').prop('disabled', false);
        jQuery('input[id^=_netSupportQuestionnaire_36]').prop('disabled', false);
        jQuery('input[id^=_netSupportQuestionnaire_37]').prop('disabled', false);
        jQuery('input[id^=_netSupportQuestionnaire_38]').prop('disabled', false);
        jQuery('input[id^=_netSupportQuestionnaire_39]').prop('disabled', false);

        if (qChoice3[0].checked == true) {
            jQuery('[id^=nsst_2_]').removeClass('warning').addClass('warning');
            jQuery('#currentNetSupportScore').val(2);
            jQuery('#nsst_6_body').text(2);
            jQuery('#currentNetSupportGoal').val(3);
            jQuery('#nsst_7_body').text(3);

            jQuery('input[id^=_netSupportQuestionnaire_35]').prop('checked', false);
            jQuery('input[id^=_netSupportQuestionnaire_36]').prop('checked', false);
            jQuery('input[id^=_netSupportQuestionnaire_37]').prop('checked', false);
            jQuery('input[id^=_netSupportQuestionnaire_38]').prop('checked', false);
            jQuery('input[id^=_netSupportQuestionnaire_39]').prop('checked', false);

            jQuery('input[id^=_netSupportQuestionnaire_35]').prop('disabled', true);
            jQuery('input[id^=_netSupportQuestionnaire_36]').prop('disabled', true);
            jQuery('input[id^=_netSupportQuestionnaire_37]').prop('disabled', true);
            jQuery('input[id^=_netSupportQuestionnaire_38]').prop('disabled', true);
            jQuery('input[id^=_netSupportQuestionnaire_39]').prop('disabled', true);
        }
         else if ((emotionalSupportValue != "0" && emotionalSupportValue == "1") &&
            (financialSupportValue != "0" && financialSupportValue == "1")) {
                jQuery('[id^=nsst_3_]').removeClass('success').addClass('success');
                jQuery('#currentNetSupportScore').val(3);
                jQuery('#nsst_6_body').text(3);
                jQuery('#currentNetSupportGoal').val(5);
                jQuery('#nsst_7_body').text(5);              
        }
        else if (qChoice7[0].checked == true) {
            jQuery('[id^=nsst_5_]').removeClass('success').addClass('success');
            jQuery('#currentNetSupportScore').val(5);
            jQuery('#nsst_6_body').text(5);
            jQuery('#currentNetSupportGoal').val(5);
            jQuery('#nsst_7_body').text(5);
        } else if (qChoice8[0].checked == true) {

            jQuery('[id^=nsst_4_]').removeClass('success').addClass('success');
            jQuery('#currentNetSupportScore').val(4);
            jQuery('#nsst_6_body').text(4);
            jQuery('#currentNetSupportGoal').val(5);
            jQuery('#nsst_7_body').text(5);
        } else if (qChoice8[1].checked == true) {
            jQuery('[id^=nsst_2_]').removeClass('warning').addClass('warning');
            jQuery('#currentNetSupportScore').val(2);
            jQuery('#nsst_6_body').text(2);
            jQuery('#currentNetSupportGoal').val(3);
            jQuery('#nsst_7_body').text(3); 
        }
    }
}

function checkSupportOptions(qchoice) {
    var selectedValue = "0";
    for (i = 0; i < 4; i++) {
        if (qchoice[i].checked == true) {
            selectedValue = qchoice[i].value;
            break;
        }
    }
    return selectedValue;
}




