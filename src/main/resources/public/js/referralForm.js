window.onbeforeprint = function() {
	$('#_commentsPrint').text($('#_commentsOrExplanation').val());
	$('#_prevAttemptsPrint').text($('#_prevAttemps').val());
	$('#_followupPrint').text($('#_rfFollowupNotes').val());
	
	if($('#_inputDateTextResAppSch').val() == ""){
		$('#_inputDateTextResAppSch').removeAttr('placeholder');
	}
	
	if($('#_ssOther').val() == ""){
		$('#_ssOther').removeAttr('placeholder');
	}
	
	if($('#_hsOther').val() == ""){
		$('#_hsOther').removeAttr('placeholder');
	}
	
	if($('#_safeSuppOther').val() == ""){
		$('#_safeSuppOther').removeAttr('placeholder');
	}
};

jQuery(document).ready(function() {
    
    populateReferralReasonsWithValues();
    populateSelfSufficiencyWithValues();
    populateHousingStabilityWithValues();
    populateSafeSupportiveCommunitiesWithValues();
    populateResidentAppScheduledDate();
    
    // set current date
    if( jQuery("#_currentDate").text()==''){
	jQuery("#_currentDate").text(new Date().toLocaleDateString());
    }
});


function populateReferralReasonsWithValues() {

   referralReasons = JSON.parse(jQuery("#_referralReason").val());   
    var row = '';    
    var inputBox = '';
    var chkBox = '';

    jQuery.each(referralReasons, function(idx, obj) {

	inputBox = '';
	chkBox = '';

	if (!(obj == 'true' || obj == 'false')) {
	
	    if(idx == 'Utility Shut-off, scheduled for (Date):'){	    
	    	inputBox = '<input id="_inputDateTextUtilityShutOff" placeholer="MM/DD/YYYY" class="my-input-sm" value="'+obj+'">';
	    }else{
		    inputBox = '<input class="my-input-sm" value="'+obj+'">';
		}
	}
	    
	if(obj != '' && obj != 'false'){
	    chkBox = 'checked';
	}	    
	row = row + '<div style="display:inline"><input class="setable" type="checkbox" '+chkBox+'>&nbsp;&nbsp;' + idx + '&nbsp;&nbsp; '+inputBox+'</div><br/>';	
    });

    jQuery("#_refReasons").html(row);

}

function populateSelfSufficiencyWithValues(){
    
    selfSuff = JSON.parse(jQuery("#_selfSufficiency").val());   
    var ssChkBoxes = '';
    var chkBox = '';
  

    jQuery.each(selfSuff, function(idx, obj) {
	
	chkBox = '';
	if(obj == 'true'){
	    chkBox = 'checked';
	}
	
	if(idx!='Other') {	
	    ssChkBoxes = ssChkBoxes + '<div style="display:inline">&nbsp;&nbsp;<input type="checkbox" '+chkBox+'>&nbsp;&nbsp;'+ idx +'</div><br/>';
	}
	
    });

    jQuery("#_ssColumn").html(ssChkBoxes);
    jQuery("#_ssOther").val(selfSuff["Other"]);
    
}

function populateHousingStabilityWithValues(){
    
    hs = JSON.parse(jQuery("#_housingStability").val());   
    var hsChkBoxes = '';
    var chkBox = '';
  

    jQuery.each(hs, function(idx, obj) {
	
	chkBox = '';
	if(obj == 'true'){
	    chkBox = 'checked';
	}
	if(idx!='Other') {
	    hsChkBoxes = hsChkBoxes + '<div style="display:inline">&nbsp;&nbsp;<input type="checkbox" '+chkBox+'>&nbsp;&nbsp;'+ idx +'</div><br/>';
	}
	
    });

    jQuery("#_hsColumn").html(hsChkBoxes);
    jQuery("#_hsOther").val(hs["Other"]);
}

function populateSafeSupportiveCommunitiesWithValues(){
    
    var ssc = JSON.parse(jQuery("#_safeSupportiveCommunity").val());   
    var ssCommChkBoxes = '';
    var chkBox = '';
  

    jQuery.each(ssc, function(idx, obj) {
	
	chkBox = '';
	if(obj == 'true'){
	    chkBox = 'checked';
	}
	if(idx!='Other') {
	    ssCommChkBoxes = ssCommChkBoxes + '<div style="display:inline">&nbsp;&nbsp;<input type="checkbox" '+chkBox+'>&nbsp;&nbsp;'+ idx +'</div><br/>';
	}
	
    });   
    
    jQuery("#_safeSuppColumn").html(ssCommChkBoxes);
    jQuery("#_safeSuppOther").val(ssc["Other"]);
}

function populateResidentAppScheduledDate(){
    
    resAppSch = JSON.parse(jQuery("#_residentAppointmentScheduled").val());     
    jQuery("#_inputDateTextResAppSch").val(resAppSch["Resident Appointment Scheduled?"]);
}

/**
 * This method calls onclick of save button and put complete JSON String on each hidden fields on referralForm.html which is bind to Resident Object.
 * @returns
 */
function buildEachJSONString(){
    
    var jsonString = '';
    var currentValue = '';
    
    //Read Referral Reasons Checkboxes and TextBoxes value and put it in hidden field 
    jQuery.each(jQuery("#_refReasons div"), function(idx, obj) {
	
	currentValue = '';
	
	//For ReferralReasons, If textbox is there in front of checkbox then DB will save textbox value else checkbox true/false value
	if(jQuery(obj).find("input:text").length > 0){
	    currentValue = jQuery(obj).find("input:text").val().trim();  
	}else {
	    currentValue = jQuery(obj).find("input:checkbox").prop('checked');
	}	
	jsonString = jsonString + '"' +obj.innerText.trim() + '":"' + currentValue + '",';	
    });
    
    jsonString = jsonString.substring(0, jsonString.length - 1);
    jQuery("#_referralReason").val('{'+jsonString+'}'); 
    jsonString = '';
    
    //Read Self Sufficiency Column Checkboxes and put it in hidden field
    jQuery.each(jQuery("#_ssColumn div"), function(idx, obj) {
	
	currentValue = '';
	currentValue = jQuery(obj).find("input:checkbox").prop('checked');
		
	jsonString = jsonString + '"' +obj.innerText.trim() + '":"' + currentValue + '",';	
    });
    
    jsonString = jsonString + '"Other":' +'"' + jQuery("#_ssOther").val().trim() + '"';
    
    jQuery("#_selfSufficiency").val('{'+jsonString+'}'); 
    jsonString = '';
    
    //Read Housing Stability Column Checkboxes and put it in hidden field
    jQuery.each(jQuery("#_hsColumn div"), function(idx, obj) {
	
	currentValue = '';
	currentValue = jQuery(obj).find("input:checkbox").prop('checked');
		
	jsonString = jsonString + '"' +obj.innerText.trim() + '":"' + currentValue + '",';	
    });
    
    jsonString = jsonString + '"Other":' +'"' + jQuery("#_hsOther").val().trim() + '"';
    jQuery("#_housingStability").val('{'+jsonString+'}'); 
    jsonString = '';
    
    //Read Safe and More Supportive Community Column Checkboxes and put it in hidden field
    jQuery.each(jQuery("#_safeSuppColumn div"), function(idx, obj) {
	
	currentValue = '';
	currentValue = jQuery(obj).find("input:checkbox").prop('checked');
		
	jsonString = jsonString + '"' +obj.innerText.trim() + '":"' + currentValue + '",';	
    });
    
    jsonString = jsonString + '"Other":' +'"' + jQuery("#_safeSuppOther").val().trim() + '"';
    jQuery("#_safeSupportiveCommunity").val('{'+jsonString+'}'); 
    jsonString = '';
    
    //Read Textbox value for resident Appointment Scheduled?
    
    jQuery("#_residentAppointmentScheduled").val('{"Resident Appointment Scheduled?":"'+jQuery("#_inputDateTextResAppSch").val().trim()+'"}')
    
}


var format = "MM/DD/YYYY";
var match = new RegExp(format
    .replace(/(\w+)\W(\w+)\W(\w+)/, "^\\s*($1)\\W*($2)?\\W*($3)?([0-9]*).*")
    .replace(/M|D|Y/g, "\\d"));
var replace = "$1/$2/$3$4"
    .replace(/\//g, format.match(/\W/));

function doFormat(target)
{
    target.value = target.value
        .replace(/(^|\W)(?=\d\W)/g, "$10")   // padding
        .replace(match, replace)             // fields
        .replace(/(\W)+/g, "$1");            // remove repeats
}

jQuery("input[id^='_inputDateText']").keyup(function(e) {
   if(!e.ctrlKey && !e.metaKey && (e.keyCode == 32 || e.keyCode > 46))
      doFormat(e.target)
});

