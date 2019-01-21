/**
 * 
 */
package com.ffg.rrn.controller;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ffg.rrn.utils.WebUtils;

/**
 * @author FFGRRNTEam
 *
 */
@Controller
public class MainController {

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
			populateModel(model, principal);
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

	@RequestMapping(value = "/residentInfo", method = RequestMethod.GET)
	public String serviceCoordinatorInfo(Model model, Principal principal) {

		// (1) (en)
		// After user login successfully.
		if (principal != null) {
			populateModel(model, principal);
		}

		return "residentInfoPage";
	}

	private void populateModel(Model model, Principal principal) {

		User loggedinUser = (User) ((Authentication) principal).getPrincipal();
		String serviceCordInfo = WebUtils.toString(loggedinUser);
		model.addAttribute("serviceCordInfo", serviceCordInfo);

	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Model model, Principal principal) {

		if (principal != null) {
			populateModel(model, principal);

			String message = "Hi " + principal.getName() //
					+ "<br> You do not have permission to access this page!";
			model.addAttribute("message", message);

		}

		return "403Page";
	}

}
