function calculatNetworkSupportScore(){
    
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

	if (qChoice1[1].checked == true) {

		jQuery('[id^=nsst_1_]').removeClass('danger').addClass('danger');
		jQuery('#currentHousingScore').val(1);
		jQuery('#nsst_6_body').text(1);
		jQuery('#currentHousingGoal').val(2);
		jQuery('#nsst_7_body').text(2);

		jQuery('input[id^=_netSupportQuestionnaire_2]').prop('checked', false);
		jQuery('input[id^=_netSupportQuestionnaire_3]').prop('checked', false);
		jQuery('input[id^=_netSupportQuestionnaire_4]').prop('checked', false);
		jQuery('input[id^=_netSupportQuestionnaire_5]').prop('checked', false);
		jQuery('input[id^=_netSupportQuestionnaire_6]').prop('checked', false);
		jQuery('input[id^=_netSupportQuestionnaire_7]').prop('checked', false);
		jQuery('input[id^=_netSupportQuestionnaire_8]').prop('checked', false);

		jQuery('input[id^=_netSupportQuestionnaire_2]').prop('disabled', true);
		jQuery('input[id^=_netSupportQuestionnaire_3]').prop('disabled', true);
		jQuery('input[id^=_netSupportQuestionnaire_4]').prop('disabled', true);
		jQuery('input[id^=_netSupportQuestionnaire_5]').prop('disabled', true);
		jQuery('input[id^=_netSupportQuestionnaire_6]').prop('disabled', true);
		jQuery('input[id^=_netSupportQuestionnaire_7]').prop('disabled', true);
		jQuery('input[id^=_netSupportQuestionnaire_8]').prop('disabled', true);

	} else {

		jQuery('input[id^=_netSupportQuestionnaire_2]').prop('disabled', false);
		jQuery('input[id^=_netSupportQuestionnaire_3]').prop('disabled', false);
		jQuery('input[id^=_netSupportQuestionnaire_4]').prop('disabled', false);
		jQuery('input[id^=_netSupportQuestionnaire_5]').prop('disabled', false);
		jQuery('input[id^=_netSupportQuestionnaire_6]').prop('disabled', false);
		jQuery('input[id^=_netSupportQuestionnaire_7]').prop('disabled', false);
		jQuery('input[id^=_netSupportQuestionnaire_8]').prop('disabled', false);
		
		if (qChoice2[0].checked == true) {

			jQuery('[id^=nsst_1_]').removeClass('danger').addClass('danger');
			jQuery('#currentHousingScore').val(1);
			jQuery('#nsst_6_body').text(1);
			jQuery('#currentHousingGoal').val(2);
			jQuery('#nsst_7_body').text(2);

			jQuery('input[id^=_netSupportQuestionnaire_3]').prop('checked', false);
			jQuery('input[id^=_netSupportQuestionnaire_4]').prop('checked', false);
			jQuery('input[id^=_netSupportQuestionnaire_5]').prop('checked', false);
			jQuery('input[id^=_netSupportQuestionnaire_6]').prop('checked', false);
			jQuery('input[id^=_netSupportQuestionnaire_7]').prop('checked', false);
			jQuery('input[id^=_netSupportQuestionnaire_8]').prop('checked', false);

			jQuery('input[id^=_netSupportQuestionnaire_3]').prop('disabled', true);
			jQuery('input[id^=_netSupportQuestionnaire_4]').prop('disabled', true);
			jQuery('input[id^=_netSupportQuestionnaire_5]').prop('disabled', true);
			jQuery('input[id^=_netSupportQuestionnaire_6]').prop('disabled', true);
			jQuery('input[id^=_netSupportQuestionnaire_7]').prop('disabled', true);
			jQuery('input[id^=_netSupportQuestionnaire_8]').prop('disabled', true);

		}

		else{
			
			jQuery('input[id^=_netSupportQuestionnaire_3]').prop('disabled', false);
			jQuery('input[id^=_netSupportQuestionnaire_4]').prop('disabled', false);
			jQuery('input[id^=_netSupportQuestionnaire_5]').prop('disabled', false);
			jQuery('input[id^=_netSupportQuestionnaire_6]').prop('disabled', false);
			jQuery('input[id^=_netSupportQuestionnaire_7]').prop('disabled', false);
			jQuery('input[id^=_netSupportQuestionnaire_8]').prop('disabled', false);

			if (qChoice3[0].checked == true) {
				jQuery('input[id^=_netSupportQuestionnaire_4]').prop('checked',false);
				jQuery('input[id^=_netSupportQuestionnaire_5]').prop('checked',false);
				jQuery('input[id^=_netSupportQuestionnaire_4]').prop('disabled',true);
				jQuery('input[id^=_netSupportQuestionnaire_5]').prop('disabled',true);
			} 
			
			else {
	
				jQuery('input[id^=_netSupportQuestionnaire_4]').prop('disabled',false);
				jQuery('input[id^=_netSupportQuestionnaire_5]').prop('disabled',false);
			

				if (qChoice4[1].checked == true) {

				jQuery('[id^=nsst_2_]').removeClass('warning').addClass('warning');
				jQuery('#currentHousingScore').val(2);
				jQuery('#nsst_6_body').text(2);
				jQuery('#currentHousingGoal').val(3);
				jQuery('#nsst_7_body').text(3);

				jQuery('input[id^=_netSupportQuestionnaire_5]').prop('checked',false);
				jQuery('input[id^=_netSupportQuestionnaire_6]').prop('checked',false);
				jQuery('input[id^=_netSupportQuestionnaire_7]').prop('checked',false);
				jQuery('input[id^=_netSupportQuestionnaire_8]').prop('checked',false);

				jQuery('input[id^=_netSupportQuestionnaire_5]').prop('disabled',true);
				jQuery('input[id^=_netSupportQuestionnaire_6]').prop('disabled',true);
				jQuery('input[id^=_netSupportQuestionnaire_7]').prop('disabled',true);
				jQuery('input[id^=_netSupportQuestionnaire_8]').prop('disabled',true);

				} 
				else{				
				
					jQuery('input[id^=_netSupportQuestionnaire_5]').prop('disabled', false);
					jQuery('input[id^=_netSupportQuestionnaire_6]').prop('disabled', false);
					jQuery('input[id^=_netSupportQuestionnaire_7]').prop('disabled', false);
					jQuery('input[id^=_netSupportQuestionnaire_8]').prop('disabled', false);
					
					if (qChoice5[0].checked == true) {
	
						jQuery('input[id^=_netSupportQuestionnaire_6]').prop('checked', false);
						jQuery('input[id^=_netSupportQuestionnaire_7]').prop('checked', false);
						jQuery('input[id^=_netSupportQuestionnaire_8]').prop('checked', false);
	
						jQuery('input[id^=_netSupportQuestionnaire_6]').prop('disabled', true);
						jQuery('input[id^=_netSupportQuestionnaire_7]').prop('disabled', true);
						jQuery('input[id^=_netSupportQuestionnaire_8]').prop('disabled', true);
	
						jQuery('[id^=nsst_2_]').removeClass('warning').addClass('warning');
						jQuery('#currentHousingScore').val(2);
						jQuery('#nsst_6_body').text(2);
						jQuery('#currentHousingGoal').val(3);
						jQuery('#nsst_7_body').text(3);
	
					}
	
					else{
						
						jQuery('input[id^=_netSupportQuestionnaire_6]').prop('disabled', false);
						jQuery('input[id^=_netSupportQuestionnaire_7]').prop('disabled', false);
						jQuery('input[id^=_netSupportQuestionnaire_8]').prop('disabled', false);
						
						if (qChoice6[0].checked == true) {
	
							jQuery('input[id^=_netSupportQuestionnaire_7]').prop('checked', false);
							jQuery('input[id^=_netSupportQuestionnaire_8]').prop('checked', false);
		
							jQuery('input[id^=_netSupportQuestionnaire_7]').prop('disabled', true);
							jQuery('input[id^=_netSupportQuestionnaire_8]').prop('disabled', true);
		
							jQuery('[id^=nsst_2_]').removeClass('warning').addClass('warning');
							jQuery('#currentHousingScore').val(2);
							jQuery('#nsst_6_body').text(2);
							jQuery('#currentHousingGoal').val(3);
							jQuery('#nsst_7_body').text(3);
	
						}
	
						else {
							
							jQuery('input[id^=_netSupportQuestionnaire_7]').prop('disabled', false);
							jQuery('input[id^=_netSupportQuestionnaire_8]').prop('disabled', false);
		
							
								if (qChoice7[1].checked == true) {
		
									jQuery('[id^=nsst_3_]').removeClass('info').addClass('info');
									jQuery('#currentHousingScore').val(3);
									jQuery('#nsst_6_body').text(3);
									jQuery('#currentHousingGoal').val(4);
									jQuery('#nsst_7_body').text(4);
		
								} else if (qChoice8[0].checked == true) {
		
									jQuery('[id^=nsst_4_]').removeClass('success').addClass('success');
									jQuery('#currentHousingScore').val(4);
									jQuery('#nsst_6_body').text(4);
									jQuery('#currentHousingGoal').val(5);
									jQuery('#nsst_7_body').text(5);
		
								} else if (qChoice8[1].checked == true) {
									jQuery('[id^=nsst_5_]').removeClass('success').addClass('success');
									jQuery('#currentHousingScore').val(5);
									jQuery('#nsst_6_body').text(5);
									jQuery('#currentHousingGoal').val(5);
									jQuery('#nsst_7_body').text(5);
								}
						}
					}
				}
			}
		}
	}
}