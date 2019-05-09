window.onbeforeprint = function() {
	$('#_rrcPrint').text($('#_rrc').val());
	$('#_housingPOA').text($('#_planOfActionHousing').val());
	$('#_mmPOA').text($('#_planOfActionMM').val());
	$('#_empPOA').text($('#_planOfActionEmp').val());
	$('#_eduPOA').text($('#_planOfActionEdu').val());
	$('#_nsPOA').text($('#_planOfActionNS').val());
	$('#_hhPOA').text($('#_planOfActionHH').val());
	$('#_housingAnOut').text($('#_anticipatedOutcomeHousing').val());
	$('#_mmAnOut').text($('#_anticipatedOutcomeMM').val());
	$('#_empAnOut').text($('#_anticipatedOutcomeEmp').val());
	$('#_eduAnOut').text($('#_anticipatedOutcomeEdu').val());
	$('#_nsAnOut').text($('#_anticipatedOutcomeNS').val());
	$('#_hhAnOut').text($('#_anticipatedOutcomeHH').val());
	$('#_followUpNotesPrint').text($('#_followUpNotes').val());
	$('#_housingOutAch').text($('#_outcomesAchievedHousing').val());
	$('#_mmOutAch').text($('#_outcomesAchievedMM').val());
	$('#_empOutAch').text($('#_outcomesAchievedEmp').val());
	$('#_eduOutAch').text($('#_outcomesAchievedEdu').val());
	$('#_nsOutAch').text($('#_outcomesAchievedNS').val());
	$('#_hhOutAch').text($('#_outcomesAchievedHH').val());
};


jQuery(document).ready(function() {

    jQuery.ajax({
	type : "POST",
	contentType : "application/json",
	url : "/getAllLatestScoreGoal?residentId=" + jQuery('#_residentIdText').text(),
	dataType : 'json',
	cache : false,
	timeout : 600000,
	success : function(data) {
	    // Following method highlight background in actionPlan SSM Grid as
	    // per Current Score of resident.
	    highlightSSMGridAsPerCurrentScore(data);
	    //This method is useful in case of modify use case
	    populateEachActionPlanFromJsonData();

	},
	error : function(e) {
	    console.log("ERROR : ", e);
	}
    });

    // set current date
    $("#_currentDate").text(new Date().toLocaleDateString());
});

/** This method populates action plan fields for existing saved actionPlan
*/
function populateEachActionPlanFromJsonData(){
    
    var focusOnDomainJson = JSON.parse(jQuery("#_focusString").val());
    
    if(focusOnDomainJson["HOUSING"] == 'true'){
	jQuery("#_housingCheck").prop('checked', true);
    }
    if(focusOnDomainJson["MONEY MANAGEMENT"] == 'true'){
	jQuery("#_mmCheck").prop('checked', true);
    }
    if(focusOnDomainJson["EMPLOYMENT"] == 'true'){
	jQuery("#_empCheck").prop('checked', true);
    }
    if(focusOnDomainJson["EDUCATION"] == 'true'){
	jQuery("#_eduCheck").prop('checked', true);
    }
    if(focusOnDomainJson["NETWORK SUPPORT"] == 'true'){
	jQuery("#_nsCheck").prop('checked', true);
    }
    if(focusOnDomainJson["HOUSEHOLD MANAGEMENT"] == 'true'){
	jQuery("#_hhCheck").prop('checked', true);
    }
    
    
    var planOfActionJson = JSON.parse(jQuery("#_planOfActionString").val());
    
    jQuery("#_planOfActionHousing").val(planOfActionJson["HOUSING"]);
    jQuery("#_planOfActionMM").val(planOfActionJson["MONEY MANAGEMENT"]);
    jQuery("#_planOfActionEmp").val(planOfActionJson["EMPLOYMENT"]);
    jQuery("#_planOfActionEdu").val(planOfActionJson["EDUCATION"]);
    jQuery("#_planOfActionNS").val(planOfActionJson["NETWORK SUPPORT"]);
    jQuery("#_planOfActionHH").val(planOfActionJson["HOUSEHOLD MANAGEMENT"]);
    
    var anticipatedOutcomeJson = JSON.parse(jQuery("#_anticipatedOutcomeString").val());
    
    jQuery("#_anticipatedOutcomeHousing").val(anticipatedOutcomeJson["HOUSING"]);
    jQuery("#_anticipatedOutcomeMM").val(anticipatedOutcomeJson["MONEY MANAGEMENT"]);
    jQuery("#_anticipatedOutcomeEmp").val(anticipatedOutcomeJson["EMPLOYMENT"]);
    jQuery("#_anticipatedOutcomeEdu").val(anticipatedOutcomeJson["EDUCATION"]);
    jQuery("#_anticipatedOutcomeNS").val(anticipatedOutcomeJson["NETWORK SUPPORT"]);
    jQuery("#_anticipatedOutcomeHH").val(anticipatedOutcomeJson["HOUSEHOLD MANAGEMENT"]);
    
    var outcomesAchievedJson = JSON.parse(jQuery("#_outcomesAchievedString").val());
    
    jQuery("#_outcomesAchievedHousing").val(outcomesAchievedJson["HOUSING"]);
    jQuery("#_outcomesAchievedMM").val(outcomesAchievedJson["MONEY MANAGEMENT"]);
    jQuery("#_outcomesAchievedEmp").val(outcomesAchievedJson["EMPLOYMENT"]);
    jQuery("#_outcomesAchievedEdu").val(outcomesAchievedJson["EDUCATION"]);
    jQuery("#_outcomesAchievedNS").val(outcomesAchievedJson["NETWORK SUPPORT"]);
    jQuery("#_outcomesAchievedHH").val(outcomesAchievedJson["HOUSEHOLD MANAGEMENT"]);
    
    
}

function highlightSSMGridAsPerCurrentScore(data) {

    if (data["HOUSING"][0] == '1') {
	jQuery("#_housingSSM1").addClass('danger');
    } else if (data["HOUSING"][0] == '2') {
	jQuery("#_housingSSM2").addClass('warning');
    } else if (data["HOUSING"][0] == '3') {
	jQuery("#_housingSSM3").addClass('info');
    } else if (data["HOUSING"][0] == '4') {
	jQuery("#_housingSSM4").addClass('success');
    } else if (data["HOUSING"][0] == '5') {
	jQuery("#_housingSSM5").addClass('success');
    }

    if (data["MONEY MANAGEMENT"][0] == '1') {
	jQuery("#_moneySSM1").addClass('danger');
    } else if (data["MONEY MANAGEMENT"][0] == '2') {
	jQuery("#_moneySSM2").addClass('warning');
    } else if (data["MONEY MANAGEMENT"][0] == '3') {
	jQuery("#_moneySSM3").addClass('info');
    } else if (data["MONEY MANAGEMENT"][0] == '4') {
	jQuery("#_moneySSM4").addClass('success');
    } else if (data["MONEY MANAGEMENT"][0] == '5') {
	jQuery("#_moneySSM5").addClass('success');
    }

    if (data["EDUCATION"][0] == '1') {
	jQuery("#_educationSSM1").addClass('danger');
    } else if (data["EDUCATION"][0] == '2') {
	jQuery("#_educationSSM2").addClass('warning');
    } else if (data["EDUCATION"][0] == '3') {
	jQuery("#_educationSSM3").addClass('info');
    } else if (data["EDUCATION"][0] == '4') {
	jQuery("#_educationSSM4").addClass('success');
    } else if (data["EDUCATION"][0] == '5') {
	jQuery("#_educationSSM5").addClass('success');
    }

    if (data["EMPLOYMENT"][0] == '1') {
	jQuery("#_employmentSSM1").addClass('danger');
    } else if (data["EMPLOYMENT"][0] == '2') {
	jQuery("#_employmentSSM2").addClass('warning');
    } else if (data["EMPLOYMENT"][0] == '3') {
	jQuery("#_employmentSSM3").addClass('info');
    } else if (data["EMPLOYMENT"][0] == '4') {
	jQuery("#_employmentSSM4").addClass('success');
    } else if (data["EMPLOYMENT"][0] == '5') {
	jQuery("#_employmentSSM5").addClass('success');
    }

    if (data["NETWORK SUPPORT"][0] == '1') {
	jQuery("#_networkSSM1").addClass('danger');
    } else if (data["NETWORK SUPPORT"][0] == '2') {
	jQuery("#_networkSSM2").addClass('warning');
    } else if (data["NETWORK SUPPORT"][0] == '3') {
	jQuery("#_networkSSM3").addClass('info');
    } else if (data["NETWORK SUPPORT"][0] == '4') {
	jQuery("#_networkSSM4").addClass('success');
    } else if (data["NETWORK SUPPORT"][0] == '5') {
	jQuery("#_networkSSM5").addClass('success');
    }

    if (data["HOUSEHOLD MANAGEMENT"][0] == '1') {
	jQuery("#_householdSSM1").addClass('danger');
    } else if (data["HOUSEHOLD MANAGEMENT"][0] == '2') {
	jQuery("#_householdSSM2").addClass('warning');
    } else if (data["HOUSEHOLD MANAGEMENT"][0] == '3') {
	jQuery("#_householdSSM3").addClass('info');
    } else if (data["HOUSEHOLD MANAGEMENT"][0] == '4') {
	jQuery("#_householdSSM4").addClass('success');
    } else if (data["HOUSEHOLD MANAGEMENT"][0] == '5') {
	jQuery("#_householdSSM5").addClass('success');
    }
}

function buildEachJSONString() {

    var focusString = '{"HOUSING":"' + jQuery("#_housingCheck").prop('checked') + '",' + '"MONEY MANAGEMENT":"' + jQuery("#_mmCheck").prop('checked') + '",' + '"EMPLOYMENT":"'
	    + jQuery("#_empCheck").prop('checked') + '",' + '"EDUCATION":"' + jQuery("#_eduCheck").prop('checked') + '",' + '"NETWORK SUPPORT":"' + jQuery("#_nsCheck").prop('checked') + '",'
	    + '"HOUSEHOLD MANAGEMENT":"' + jQuery("#_hhCheck").prop('checked') + '"}';

    var planOfActionString = '{"HOUSING":"' + jQuery("#_planOfActionHousing").val().trim() + '",' + '"MONEY MANAGEMENT":"' + jQuery("#_planOfActionMM").val().trim() + '",' + '"EMPLOYMENT":"'
	    + jQuery("#_planOfActionEmp").val().trim() + '",' + '"EDUCATION":"' + jQuery("#_planOfActionEdu").val().trim() + '",' + '"NETWORK SUPPORT":"' + jQuery("#_planOfActionNS").val().trim() + '",'
	    + '"HOUSEHOLD MANAGEMENT":"' + jQuery("#_planOfActionHH").val().trim() + '"}';

    var anticipatedOutcomeString = '{"HOUSING":"' + jQuery("#_anticipatedOutcomeHousing").val().trim() + '",' + '"MONEY MANAGEMENT":"' + jQuery("#_anticipatedOutcomeMM").val().trim() + '",' + '"EMPLOYMENT":"'
	    + jQuery("#_anticipatedOutcomeEmp").val().trim() + '",' + '"EDUCATION":"' + jQuery("#_anticipatedOutcomeEdu").val().trim() + '",' + '"NETWORK SUPPORT":"' + jQuery("#_anticipatedOutcomeNS").val().trim() + '",'
	    + '"HOUSEHOLD MANAGEMENT":"' + jQuery("#_anticipatedOutcomeHH").val().trim() + '"}';

    var outcomesAchievedString = '{"HOUSING":"' + jQuery("#_outcomesAchievedHousing").val().trim() + '",' + '"MONEY MANAGEMENT":"' + jQuery("#_outcomesAchievedMM").val().trim() + '",' + '"EMPLOYMENT":"'
	    + jQuery("#_outcomesAchievedEmp").val().trim() + '",' + '"EDUCATION":"' + jQuery("#_outcomesAchievedEdu").val().trim() + '",' + '"NETWORK SUPPORT":"' + jQuery("#_outcomesAchievedNS").val().trim() + '",'
	    + '"HOUSEHOLD MANAGEMENT":"' + jQuery("#_outcomesAchievedHH").val().trim() + '"}';

    // These are all setting into hidden variables before saving Object
    jQuery("#_focusString").val(focusString);
    jQuery("#_planOfActionString").val(planOfActionString);
    jQuery("#_anticipatedOutcomeString").val(anticipatedOutcomeString);
    jQuery("#_outcomesAchievedString").val(outcomesAchievedString);

}

function validateInput(textArea){
    textArea.value = textArea.value.replace(/[^a-zA-Z ]/g, "");
}