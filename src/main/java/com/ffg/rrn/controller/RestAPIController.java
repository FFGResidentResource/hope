/**
 * 
 */
package com.ffg.rrn.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ffg.rrn.model.Resident;

/**
 * @author FFGRRNTEam
 *
 */
@RestController
public class RestAPIController {
	
	@PostMapping("/getAllResidents")
    public ResponseEntity<?> getAllResidents() {        
        
        List<Resident> data = new ArrayList<Resident>();	
        
        Date date = new Date();
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        date = cal.getTime();
        
        
		
		Resident r = new Resident(1L, false, "Rishabh", "", "Sharma", "Cutter Apts", "6141234567", "6141234567", "rishabh@rishabh.com", true, false, false, date, "sc1");
		data.add(r);
		
		r = new Resident(2L, false, "Eric", null, "Podolsky", "The Meadows (CMHA)", "1234567890", "1234567890", "eric@eric.com", true, false, false, date, "sc1");
		data.add(r);
		
		r = new Resident(3L, false, "Joshua", "M", "Schraivogel", "The Meadows (Marrysville)", "6147654321", "6147654321", "joshua@joshua.com", true, false, false, date, "sc1");
		data.add(r);
		
		r = new Resident(4L, false, "Jeremy", "M", "Clark", "Rosewind", "6141223344", "6141223344", "jeremy@jeremy.com", true, false, false, date, "sc1");		
		data.add(r);
		
		r = new Resident(5L, false, "Alexander", null, "Giovannelli", "Thornwood", "6141122334", "6141122334", "gio@gio.com", true, false, false, date, "sc1");
		data.add(r);
		
		r = new Resident(6L, false, "John", null, "Ransom", "Fair Park", "6141234566", "6141234566", "john@john.com", true, false, false, date, "sc2");
		data.add(r);
		
		r = new Resident(7L, false, "Alexander", null, "Giovannelli", "Faith Village", "6141234577", "6141234577", "alex@alex.com", true, false, false, date, "sc2");
		data.add(r);    
 
        return ResponseEntity.ok(data); 
    }

}
