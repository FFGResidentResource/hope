/**
 * 
 */
package com.ffg.rrn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ffg.rrn.model.Resident;
import com.ffg.rrn.service.ResidentServiceImpl;

/**
 * @author FFGRRNTEam
 *
 */
@RestController
public class RestAPIController {
	
	@Autowired
	private ResidentServiceImpl residentService;
	
	@PostMapping("/getAllResidents")
    public ResponseEntity<?> getAllResidents() {        
        
        List<Resident> allResident = residentService.getAllResident();    
 
        return ResponseEntity.ok(allResident); 
    }

}
