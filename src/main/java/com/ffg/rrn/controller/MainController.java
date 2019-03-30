/**
 * 
 */
package com.ffg.rrn.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ffg.rrn.model.Resident;
import com.ffg.rrn.service.ResidentServiceImpl;

/**
 * @author FFGRRNTeam
 *
 */
@Controller
public class MainController extends BaseController{

	@Autowired
	private ResidentServiceImpl residentService;
	
	// Either you don't pass anything or pass welcome from the url, it hit below API
	@RequestMapping(value = { "/welcome" }, method = RequestMethod.GET)
	public String welcomePage(Model model) {
		model.addAttribute("title", "Resident Resource Network");
		model.addAttribute("message", "Welcome to Resident Resource Hope portal");
		return "welcomePage";
	}

	@RequestMapping(value = { "/", "/login", "/logoutSuccessful" }, method = RequestMethod.GET)
	public String loginPage(Model model) {

		return "loginPage";
	}

/**	@RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
	public String logoutSuccessfulPage(Model model) {
		model.addAttribute("title", "Logout");
		return "logoutSuccessfulPage";
	}*/
	
	@PostMapping(value = "/saveAssessment")
	public String ssmAssessment(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) {

		int count = residentService.saveAssessment(resident);		
		
		return "redirect:/getResidentById?residentId="+resident.getResidentId();		
	}
	
	@PostMapping("/saveHousing")
    public String saveHousingAssessment(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) {      
        
		if (bindingResult.hasErrors()) {
            return "residentPage";
        }
		//This will be new ResidentId always
        //Long residentId = residentService.saveResident(resident);    
        
        return "allResident";
    }

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Model model, Principal principal) {

		if (principal != null) {
			populateSCinModel(model, principal);

			String message = "Hi " + principal.getName() //
					+ "<br> You do not have permission to access this page!";
			model.addAttribute("message", message);

		}
		return "403Page";
	}	

}
