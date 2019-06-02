jQuery(document).ready(function() {

    var now = new Date();
    var year = now.getFullYear();
    var ddl = document.getElementById("ddlYear");
    for (var y = 0; y < 5; y++) {
	var opt = document.createElement("option");
	opt.value = (year - y).toString();
	opt.innerText = (year - y).toString();
	ddl.appendChild(opt);
    }

    var quarter = Math.floor((now.getMonth() + 3) / 3);
    jQuery("#ddlQuarter").val(quarter);
    
/*   jQuery.ajax({
	type : "POST",
	contentType : "application/json",
	url : "/getAllProperty?serviceCoordinator=" + jQuery('#_userName').text(),
	dataType : 'json',
	cache : false,
	timeout : 600000,
	success : function(data) {
	    debugger;
	    var row = '';

	    jQuery.each(data, function(idx, obj) {

		row = row + '<input class="form-input" type="checkbox" checked>&nbsp;&nbsp;' + obj.propertyName + '&nbsp;&nbsp; <br\>';
	    });

	    jQuery("#_properties").html(row);
	},
	error : function(e) {
	    console.log("ERROR : ", e);
	}
    });*/
    
    
    
    

});


function pullDashboard(){    
   
    var checkString = '';    
    $(':checkbox').each(function() {
	checkString = checkString + '{"propertyId":"' +this.value +'","checked":"' + this.checked + '"},';
    });
    
    checkString = checkString.substring(0, checkString.length - 1);    
    var dash = '{"year":"'+jQuery("#ddlYear").val()+ '",' +'"quarter":"' +jQuery("#ddlQuarter").val()+ '",' +'"properties": [ '+checkString+' ]}';
    
    jQuery.ajax({
	type : "POST",
	contentType: "application/json; charset=utf-8",
	url : "/pullDashboard",
	data: dash,
	dataType : 'json',
	cache : false,
	timeout : 600000,
	success : function(data) {
	    debugger;
	    
	    
	},
	error : function(e) {
	    console.log("ERROR : ", e);
	}
    });
    
}
