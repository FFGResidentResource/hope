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
				"order" : [ [ 4, "desc" ] ],
				pageLength : 15,
				pagingType : "full_numbers",
				"initComplete" : function(settings, json) {
				    var radioHtml = '&nbsp;&nbsp;&nbsp;&nbsp;<span><input type="radio" name="serviceCoordinators" value="all" onchange="filterActives(this);"> All '
					    + ' <input type="radio" name="serviceCoordinators" value="t" onchange="filterActives(this);"> Active '
					    + ' <input type="radio" name="serviceCoordinators" value="f" onchange="filterActives(this);"> Inactive </span>';

				    // This prints all radio Options on
				    // AllResident DataTables.
				    var pre = jQuery('.dataTables_length').html();
				    jQuery('.dataTables_length').html(pre + radioHtml);
				}
			    });
		    
		    jQuery('#scTable tbody').on('click', 'tr', function() {

			var tr = $(this);
			currentRow = table.row(this).data();

			console.log(currentRow);

			if ($(this).hasClass('selected')) {
			    $(this).removeClass('selected');
			    jQuery('a[id^="_load"]').attr('disabled', true);
			    
			} else {
			    
			    table.$('tr.selected').removeClass('selected');
			    $(this).addClass('selected');
			    
			    jQuery('a[id^="_load"]').attr('disabled', false);
			    
			    var suffix = '&scUserName=' + currentRow.userName;
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

function filterActives(dat) {
    // Toggles Active
    if (dat.value != "all") {
	table.columns(2).search(dat.value).draw();
    } else {
	table.columns(2).search('').draw();
    }
}

/**
 * 
 * @returns
 */
function validateFields(){
    
    debugger;
}
