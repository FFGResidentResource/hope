package com.ffg.rrn.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ffg.rrn.model.Property;
import com.ffg.rrn.model.ServiceCoordinator;
import com.ffg.rrn.service.ServiceCoordinatorServiceImpl;

@Controller
public class CoordinatorController extends BaseController {
    @Autowired
    private ServiceCoordinatorServiceImpl serviceCoordinatorService;

	@ModelAttribute("serviceCoordinator")
	public ServiceCoordinator initialize() {
		return new ServiceCoordinator();
	}

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(@ModelAttribute ServiceCoordinator serviceCoordinator,
                            Model model, Principal principal) {
        if (principal != null) {
            populateSCinModel(model, principal);
        }

		List<Property> allProperty = serviceCoordinatorService.getAllProperty();

		ServiceCoordinator sc = new ServiceCoordinator();
		sc.setPropertyList(allProperty);
		sc.setAllTakenEmails(serviceCoordinatorService.getAllTakenEmails());
		sc.setAllTakenUserNames(serviceCoordinatorService.getAllTakenUserNames());
		
		model.addAttribute("serviceCoordinator", sc);

        return "adminPage";
    }

	@RequestMapping(value = "/property", method = RequestMethod.GET)
	public String propertyPage(@ModelAttribute ServiceCoordinator serviceCoordinator,
							Model model, Principal principal) {
		if (principal != null) {
			populateSCinModel(model, principal);
		}

		List<Property> allProperty = serviceCoordinatorService.getAllProperty();

		ServiceCoordinator sc = new ServiceCoordinator();
		sc.setPropertyList(allProperty);
		sc.setAllTakenEmails(serviceCoordinatorService.getAllTakenEmails());
		sc.setAllTakenUserNames(serviceCoordinatorService.getAllTakenUserNames());

		model.addAttribute("serviceCoordinator", sc);

		return "propertyPage";
	}

    @PostMapping("/saveServiceCoordinator")
	public String saveServiceCoordinator(Model model, Principal principal, @ModelAttribute("serviceCoordinator") @Valid ServiceCoordinator sc,
			BindingResult bindingResult) {

		if (principal != null) {
			populateSCinModel(model, principal);
		}

        if (bindingResult.hasErrors()) {

			List<Property> allProperty = serviceCoordinatorService.getAllProperty();
			sc.setPropertyList(allProperty);

			model.addAttribute("serviceCoordinator", sc);

			return "adminPage";
        }
        //This will be new scID always
        Long scID = serviceCoordinatorService.saveServiceCoordinator(sc);

        model.addAttribute("serviceCoordinator", new ServiceCoordinator());
		return "redirect:/admin";
    }

	@RequestMapping(value = { "/inactivateSC", "/activateSC" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String inactivateSC(@RequestParam("userName") String userName, @RequestParam("active") Boolean active, Model model, Principal principal) throws Exception {

		if (principal != null) {
			populateSCinModel(model, principal);
		}

		serviceCoordinatorService.inactivateOrActivateSC(userName, active);
		return "redirect:/admin";
	}



}
