jQuery(document).ready(function() {

	debugger;
	jQuery('a').parent().removeClass('active');
	var path = window.location.pathname;
	if (path == '/newResident' || path == "/getResidentById") {
		jQuery("a[href='/newResident']").parent().addClass('active');
	} else if (path == '/admin') {
		jQuery("a[href='/admin']").parent().addClass('active');
	} else if (path == '/allResident') {
		jQuery("a[href='/allResident']").parent().addClass('active');
	}
	
	var btn = $("#btnDeactivateResident");
	var isActive = !$(btn).attr("title") || $(btn).attr("title") == "true";
	isActive ? $(btn).attr("title", "true") : $(btn).attr("title", "false");
	isActive ? $(btn).val("Deactivate") : $(btn).val("Reactivate");
	
});

var isSubmitted = false;
function saveOrUpdateResident(form){
  if(!isSubmitted){
     form.action="saveResident";
     form.submit();
     jQuery("#btnSubmit").attr("disabled", true);
     isSubmitted = true;
  } else{
    alert("System is processing, please be patient.");
  }
};


function deactivateResident(btn){
  var form = btn.form;
  if(!confirm("Are you sure you want to " + $(btn).val().toLowerCase() + " this resident?"))
  {
	  return;
  }
  var isActive = !$(btn).attr("title") || $(btn).attr("title") == "true";
  isActive ? $(btn).attr("title", "false") : $(btn).attr("title", "true");
  isActive ? $(btn).val("Reactivate") : $(btn).val("Deactivate");

  
  if(!isSubmitted){
     form.action="deactivateResident";
     form.submit();
     jQuery("#btnDeactivateResident").attr("disabled", true);
     isSubmitted = true;
  } else{
    alert("System is processing, please be patient.");
  }
};

document.getElementById('allowcontact').onchange = function() {
	document.getElementById('viaemail').disabled = this.checked;
	document.getElementById('email').disabled = this.checked;
	document.getElementById('viavoicemail').disabled = this.checked;
	document.getElementById('voicemail').disabled = this.checked;
	document.getElementById('viatext').disabled = this.checked;
	document.getElementById('text').disabled = this.checked;	
};

function reset(chk){
	$('.setable').val(''); $('.setable').prop('checked', false);$(chk).trigger('change');
}

