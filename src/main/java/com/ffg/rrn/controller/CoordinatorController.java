package com.ffg.rrn.controller;

import com.ffg.rrn.model.ServiceCoordinator;
import com.ffg.rrn.service.ServiceCoordinatorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;

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
