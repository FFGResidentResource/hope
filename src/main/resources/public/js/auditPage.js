jQuery(document).ready(
	function() {
				
		debugger;
		
		/* logic to highlight what fields get changed in successive row */
		
			
			for(var idx=jQuery("table tbody tr").length-1; idx > 0; idx--){
				
				if(jQuery("#_auditDate-"+idx).text() != jQuery("#_auditDate-"+(idx-1)).text()){						
					jQuery("#_auditDate-"+(idx-1)).addClass('highlightChange');						
				}			
				if(jQuery("#_active-"+idx).text() != jQuery("#_active-"+(idx-1)).text()){						
					jQuery("#_active-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_isResident-"+idx).text() != jQuery("#_isResident-"+(idx-1)).text()){						
					jQuery("#_isResident-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_ackpr-"+idx).text() != jQuery("#_ackpr-"+(idx-1)).text()){						
					jQuery("#_ackpr-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_fullName--"+idx).text() != jQuery("#_fullName--"+(idx-1)).text()){						
					jQuery("#_fullName--"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_refType-"+idx).text() != jQuery("#_refType-"+(idx-1)).text()){						
					jQuery("#_refType-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_propId-"+idx).text() != jQuery("#_propId-"+(idx-1)).text()){						
					jQuery("#_propId-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_voicemail-"+idx).text() != jQuery("#_voicemail-"+(idx-1)).text()){						
					jQuery("#_voicemail-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_viaVoicemail-"+idx).text() != jQuery("#_viaVoicemail-"+(idx-1)).text()){						
					jQuery("#_viaVoicemail-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_text-"+idx).text() != jQuery("#_text-"+(idx-1)).text()){						
					jQuery("#_text-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_viaText-"+idx).text() != jQuery("#_viaText-"+(idx-1)).text()){						
					jQuery("#_viaText-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_email-"+idx).text() != jQuery("#_email-"+(idx-1)).text()){						
					jQuery("#_email-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_viaEmail-"+idx).text() != jQuery("#_viaEmail-"+(idx-1)).text()){						
					jQuery("#_viaEmail-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_address-"+idx).text() != jQuery("#_address-"+(idx-1)).text()){						
					jQuery("#_address-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_allowContact-"+idx).text() != jQuery("#_allowContact-"+(idx-1)).text()){						
					jQuery("#_allowContact-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_surveyChoice-"+idx).text() != jQuery("#_surveyChoice-"+(idx-1)).text()){						
					jQuery("#_surveyChoice-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_pvr-"+idx).text() != jQuery("#_pvr-"+(idx-1)).text()){						
					jQuery("#_pvr-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_mdf-"+idx).text() != jQuery("#_mdf-"+(idx-1)).text()){						
					jQuery("#_mdf-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_age-"+idx).text() != jQuery("#_age-"+(idx-1)).text()){						
					jQuery("#_age-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_lang-"+idx).text() != jQuery("#_lang-"+(idx-1)).text()){						
					jQuery("#_lang-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_ms-"+idx).text() != jQuery("#_ms-"+(idx-1)).text()){						
					jQuery("#_ms-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_gender-"+idx).text() != jQuery("#_gender-"+(idx-1)).text()){						
					jQuery("#_gender-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_ethn-"+idx).text() != jQuery("#_ethn-"+(idx-1)).text()){						
					jQuery("#_ethn-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_race-"+idx).text() != jQuery("#_race-"+(idx-1)).text()){						
					jQuery("#_race-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_hoh-"+idx).text() != jQuery("#_hoh-"+(idx-1)).text()){						
					jQuery("#_hoh-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_vet-"+idx).text() != jQuery("#_vet-"+(idx-1)).text()){						
					jQuery("#_vet-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_hc-"+idx).text() != jQuery("#_hc-"+(idx-1)).text()){						
					jQuery("#_hc-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_hedu-"+idx).text() != jQuery("#_hedu-"+(idx-1)).text()){						
					jQuery("#_hedu-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_dis-"+idx).text() != jQuery("#_dis-"+(idx-1)).text()){						
					jQuery("#_dis-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_exoff-"+idx).text() != jQuery("#_exoff-"+(idx-1)).text()){						
					jQuery("#_exoff-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_ssi-"+idx).text() != jQuery("#_ssi-"+(idx-1)).text()){						
					jQuery("#_ssi-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_ssdi-"+idx).text() != jQuery("#_ssdi-"+(idx-1)).text()){						
					jQuery("#_ssdi-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_income-"+idx).text() != jQuery("#_income-"+(idx-1)).text()){						
					jQuery("#_income-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_sday-"+idx).text() != jQuery("#_sday-"+(idx-1)).text()){						
					jQuery("#_sday-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_snight-"+idx).text() != jQuery("#_snight-"+(idx-1)).text()){						
					jQuery("#_snight-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_occlength-"+idx).text() != jQuery("#_occlength-"+(idx-1)).text()){						
					jQuery("#_occlength-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_intres-"+idx).text() != jQuery("#_intres-"+(idx-1)).text()){						
					jQuery("#_intres-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_mdTran-"+idx).text() != jQuery("#_mdTran-"+(idx-1)).text()){						
					jQuery("#_mdTran-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_expfood-"+idx).text() != jQuery("#_expfood-"+(idx-1)).text()){						
					jQuery("#_expfood-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_intAccess-"+idx).text() != jQuery("#_intAccess-"+(idx-1)).text()){						
					jQuery("#_intAccess-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_hohType-"+idx).text() != jQuery("#_hohType-"+(idx-1)).text()){						
					jQuery("#_hohType-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_unEmpReason-"+idx).text() != jQuery("#_unEmpReason-"+(idx-1)).text()){						
					jQuery("#_unEmpReason-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_bedu-"+idx).text() != jQuery("#_bedu-"+(idx-1)).text()){						
					jQuery("#_bedu-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_hcond-"+idx).text() != jQuery("#_hcond-"+(idx-1)).text()){						
					jQuery("#_hcond-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_psdy-"+idx).text() != jQuery("#_psdy-"+(idx-1)).text()){						
					jQuery("#_psdy-"+(idx-1)).addClass('highlightChange');						
				}
				if(jQuery("#_psda-"+idx).text() != jQuery("#_psda-"+(idx-1)).text()){						
					jQuery("#_psda-"+(idx-1)).addClass('highlightChange');						
				}
			}
			
		});