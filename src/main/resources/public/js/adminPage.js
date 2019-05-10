var table;
var currentRow;

jQuery(document).ready(
	function() {
	    
	    jQuery('a[id^="_load"]').attr('disabled', true);
	    
	    jQuery.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/getAllServiceCoordinators",
		dataType : 'json',
		cache : false,
		timeout : 600000,
		success : function(data) {
		    table = jQuery('#scTable').DataTable(
			    {
				"data" : data,
				"columns" : [
				    {data : 'active',
					render : function(t, type, row) {
					    if (row.active) {return 'ACTIVE'; } else {return 'INACTIVE';}						
					    }
				    },
				    {data : 'userName'},
				    {data : 'email'},
				    {data : 'createdOn.time',
					    render : {
						_ : 'display',
						sort : 'timestamp'
					    },
					    render : function(t, type, row) {
						return moment(row.createdOn).format("MM/DD/YY hh:mm A");
					    }},
				    {data : 'lastLogin.time',
					    render : {
						_ : 'display',
						sort : 'timestamp'
					    },
					    render : function(t, type, row) {
						if(row.lastLogin != null){
						    return moment(row.lastLogin).format("MM/DD/YY hh:mm A");
						}else{
						    return '';
						}
					    }
				    },				   
				    {data : 'propName'}				    
				    ],
				"order" : [ [ 3, "desc" ] ],
				pageLength : 8,
				pagingType : "full_numbers",
				"initComplete" : function(settings, json) {
				    
				    // This prints all radio Options on
				    // AllResident DataTables.
				    jQuery('.dataTables_length').addClass('hideme');				   
				}
			    });
		    
		    jQuery('#scTable tbody').on('click', 'tr', function() {

			var tr = $(this);
			currentRow = table.row(this).data();

			console.log(currentRow);

			if ($(this).hasClass('selected')) {
			    $(this).removeClass('selected');
			    jQuery('a[id^="_load"]').attr('disabled', true);
			    
			    jQuery('select').prop('selectedIndex', 0);
			    jQuery('input:text').val('');
			    jQuery('#inputEmail1').val('');
			    jQuery('#inputPassword1').val('');
			    jQuery('#inputPassword2').val('');
			    jQuery('#_isAdmin').prop('checked', false);
			    jQuery('#_property').prop('disabled', false);
			    
			} else {
			    
			    table.$('tr.selected').removeClass('selected');
			    $(this).addClass('selected');
			    
			    jQuery("#inputEmail1").val(currentRow.email);
			    jQuery("#userName1").val(currentRow.userName);		    
			    jQuery('select').val(currentRow.propertyId);
			    jQuery('#_isAdmin').prop('checked', currentRow.admin);
			    
			    jQuery('a[id^="_load"]').attr('disabled', false);
			    
			    var suffix = '&userName=' + currentRow.userName;
			    var assessmentLinks = jQuery('a[id^="_load"]');

			    jQuery.each(assessmentLinks, function(idx, obj) {
				var currHref = jQuery(obj).attr('href');
				var prefix = currHref.split('&');
				jQuery(obj).attr('href', prefix[0] + suffix);
			    });
			}
		    });
		},
		error : function(e) {
		    console.log("ERROR : ", e);
		}
	    });
	});



/**
 * 
 * @returns
 */
function validateFields(){
    
    debugger;
}
