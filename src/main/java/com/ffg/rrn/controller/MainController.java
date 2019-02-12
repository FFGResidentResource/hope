/**
 * 
 */
package com.ffg.rrn.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ffg.rrn.model.Resident;
import com.ffg.rrn.service.ResidentServiceImpl;
import com.ffg.rrn.utils.WebUtils;

/**
 * @author FFGRRNTEam
 *
 */
@Controller
public class MainController {
	
	@Autowired
	private ResidentServiceImpl residentService;

	// Either you don't pass anything or pass welcome from the url, it hit below API
	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public String welcomePage(Model model) {
		model.addAttribute("title", "Welcome");
		model.addAttribute("message", "Welcome to Resident Resource Hope portal");
		return "welcomePage";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage(Model model, Principal principal) {

		if (principal != null) {
			populateSCinModel(model, principal);
		}

		return "adminPage";
	}

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String loginPage(Model model) {

		return "loginPage";
	}

	@RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
	public String logoutSuccessfulPage(Model model) {
		model.addAttribute("title", "Logout");
		return "logoutSuccessfulPage";
	}

	@RequestMapping(value = "/residents", method = RequestMethod.GET)
	public String residents(Model model, Principal principal) {

		// (1) (en)
		// After user login successfully.
		String serviceCoord = null;
		if (principal != null) {
			serviceCoord = populateSCinModel(model, principal);
		}		
		model.addAttribute("resident", new Resident(residentService.getAllProperty(), serviceCoord));		
		
		return "residentPage";
		
	}
	
	@PostMapping("/addResident")
    public String submit(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) {      
        
		if (bindingResult.hasErrors()) {
            return "residentPage";
        }
		
            
        
        return "residentPage";
    }
	
	

	private String populateSCinModel(Model model, Principal principal) {

		User loggedinUser = (User) ((Authentication) principal).getPrincipal();
		String serviceCordInfo = WebUtils.toString(loggedinUser);
		model.addAttribute("serviceCordInfo", serviceCordInfo);
		return loggedinUser.getUsername();
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
