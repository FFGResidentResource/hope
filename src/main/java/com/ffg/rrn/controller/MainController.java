/**
 * 
 */
package com.ffg.rrn.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author FFGRRNTeam
 *
 */
@Controller
public class MainController extends BaseController{

	
	
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
	
	@RequestMapping(value = { "/actionPlans" }, method = RequestMethod.GET)
	public String actionsPlansPage(Model model, Principal principal) {
		
		if (principal != null) {
			populateSCinModel(model, principal);
		}
		
		return "actionPlans";
	}

}
