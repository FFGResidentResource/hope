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

import com.ffg.rrn.model.Property;
import com.ffg.rrn.model.ServiceCoordinator;
import com.ffg.rrn.service.ServiceCoordinatorServiceImpl;

@Controller
public class CoordinatorController extends BaseController {
    @Autowired
    private ServiceCoordinatorServiceImpl serviceCoordinatorService;

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

    @PostMapping("/saveServiceCoordinator")
    public String saveServiceCoordinator(Model model,@Valid @ModelAttribute ServiceCoordinator sc,
                                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "adminPage";
        }
        //This will be new scID always
        Long scID = serviceCoordinatorService.saveServiceCoordinator(sc);

        model.addAttribute("serviceCoordinator", new ServiceCoordinator());
        return "adminPage";
    }

}
