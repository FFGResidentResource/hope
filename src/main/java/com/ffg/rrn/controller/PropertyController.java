package com.ffg.rrn.controller;

import com.ffg.rrn.model.Property;
import com.ffg.rrn.model.ServiceCoordinator;
import com.ffg.rrn.service.PropertyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
public class PropertyController extends BaseController {

    @Autowired
    private PropertyServiceImpl propertyService;

    @ModelAttribute("propertyCoordinator")
    public Property initialize() {
        return new Property();
    }

    @RequestMapping(value = "/property", method = RequestMethod.GET)
    public String propertyPage(@ModelAttribute Property property,
                               Model model, Principal principal) {
        if (principal != null) {
            populateSCinModel(model, principal);
        }

        List<Property> allProperty = propertyService.getAllProperty();

        Property aProperty = new Property();
        aProperty.setPropertyList(allProperty);

        model.addAttribute("property", aProperty);

        return "propertyPage";
    }
}
