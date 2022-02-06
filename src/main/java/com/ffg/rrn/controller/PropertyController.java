package com.ffg.rrn.controller;

import com.ffg.rrn.model.Property;
import com.ffg.rrn.model.ServiceCoordinator;
import com.ffg.rrn.service.PropertyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
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

        List<Property> allProperty = propertyService.getAllProperties();

        Property aProperty = new Property();
        aProperty.setPropertyList(allProperty);

        model.addAttribute("property", aProperty);

        return "propertyPage";
    }

    @PostMapping("/saveProperty")
    public String saveProperty(Model model, Principal principal, @ModelAttribute("property") @Valid Property property,
                                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            List<Property> allProperty = propertyService.getAllProperties();
            property.setPropertyList(allProperty);

            model.addAttribute("property", property);

            return "propertyPage";
        }
        propertyService.saveProperty(property);

        return "redirect:/property";
    }
}
