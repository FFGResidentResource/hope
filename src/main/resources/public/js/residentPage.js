window.onbeforeprint = function() {
	$('#residentFullName').text($('#firstName').val() + " " + $('#middle').val() + " " + $('#lastname').val());
	$('#printreferralType').text($('#_referral option:selected').text());
	$('#printproperty').text($('#_property option:selected').text());
	$('#printaddress').text($('#address').val());
	if(!$('#allowcontact').prop("checked") && $('#viaemail').prop("checked")){
		$('#printEmail').prop('checked', true);
		$('#printEmailText').text($('#email').val());
	}
	if(!$('#allowcontact').prop("checked") && $('#viavoicemail').prop("checked")){
		$('#printvoicemail').prop('checked', true);
		$('#printvm').text($('#voicemail').val());
	}
	if(!$('#allowcontact').prop("checked") && $('#viatext').prop("checked")){
		$('#printtext').prop('checked', true);
		$('#printtxt').text($('#text').val());
	}
};

jQuery(document).ready(function() {
	console.log($('#allowcontact').prop("checked"));
	if($('#allowcontact').prop("checked")){
		document.getElementById('viaemail').disabled = true
		document.getElementById('email').disabled = true;
		document.getElementById('viavoicemail').disabled = true;
		document.getElementById('voicemail').disabled = true;
		document.getElementById('viatext').disabled = true;
		document.getElementById('text').disabled = true;
		document.getElementById("viaemail").checked = false;
		document.getElementById("viavoicemail").checked = false;
		document.getElementById("viatext").checked = false;
		document.getElementById('email').value = "";
		document.getElementById('voicemail').value = "";
		document.getElementById('text').value = "";
	};
	
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
  if(!confirm("Are you sure you want to deactivate this resident?"))
  {
	  return;
  }

  if(!isSubmitted){
     form.action="deactivateResident";
     form.submit();
     jQuery("#btnDeactivateResident").attr("disabled", true);
     isSubmitted = true;
  } else{
    alert("System is processing, please be patient.");
  }
};

function reactivateResident(btn){
  var form = btn.form;
  if(!confirm("Are you sure you want to reactivate this resident?"))
  {
	  return;
  }

  if(!isSubmitted){
     form.action="reactivateResident";
     form.submit();
     jQuery("#btnReactivateResident").attr("disabled", true);
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
	if(this.checked){
		document.getElementById("viaemail").checked = false;
		document.getElementById("viavoicemail").checked = false;
		document.getElementById("viatext").checked = false;
		document.getElementById('email').value = "";
		document.getElementById('voicemail').value = "";
		document.getElementById('text').value = "";
	}
};

function reset(chk){
	$('.setable').val(''); $('.setable').prop('checked', false);$(chk).trigger('change');
}

