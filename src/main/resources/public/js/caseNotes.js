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
