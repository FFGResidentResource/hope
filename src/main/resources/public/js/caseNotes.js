window.onbeforeprint = function() {
	$('#_cnDesc_P').text($('#_cnDesc').val());
	$('#_cnAssessment_P').text($('#_cnAssessment').val());
	$('#_cnPlan_P').text($('#_cnPlan').val());
	
	if($('#_cnNoShowDate').val() == ""){
		$('#_cnNoShowDate').removeAttr('placeholder');
	}
	
};


jQuery(document).ready(function() {
    
    validateAndShowMessage();
    
    var resId = jQuery.urlParam('residentId');
    var onThisDate = jQuery.urlParam('onThisDate');
    
    var currHref = jQuery("#_loadCaseNotes").attr('href');
    var prefix = currHref.split('&');
    
    if(jQuery("#_dates").val() != 'new') {
	jQuery('#_loadCaseNotes').attr('href', prefix[0] + '&residentId='+resId + '&onThisDate='+onThisDate );
    }else{
	jQuery('#_loadCaseNotes').attr('href', prefix[0] + '&residentId='+resId);
    }
    
});

function validateAndShowMessage(){
    
    var todaysDate = moment().format('DD-MMM-YYYY').toUpperCase();
    
    //check for Today's Date
    var todaysContactNoteExists = false;
    jQuery("#_dates option").each(function(){
	
	if(jQuery(this).val() == todaysDate){
	    todaysContactNoteExists = true;
	}	
    });
    
    var selOption = jQuery("#_dates option:selected").val();
    
    if(todaysContactNoteExists == true && selOption.indexOf('new') > -1){
	jQuery(".text-danger").removeClass('hideme');
	jQuery("input[type='submit']").prop('disabled', true);
    }else{
	jQuery(".text-danger").removeClass('hideme').addClass('hideme');
	jQuery("input[type='submit']").prop('disabled', false);
    }
}

function buildAchorTagForSelectedDate(that, residentId){
    
    var resId = jQuery.urlParam('residentId');
    var currHref = jQuery("#_loadCaseNotes").attr('href');
    var prefix = currHref.split('&');
    var suffix = '&residentId='+residentId+'&onThisDate='+that.value;
    
    if(that.value != 'new'){	   
        jQuery('#_loadCaseNotes').attr('href', prefix[0] + suffix);
    }else{	
	jQuery('#_loadCaseNotes').attr('href', prefix[0] + '&residentId='+resId);
    }
    
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

jQuery("input[id^='_cnNoShowDate']").keyup(function(e) {
   if(!e.ctrlKey && !e.metaKey && (e.keyCode == 32 || e.keyCode > 46))
      doFormat(e.target)
});

