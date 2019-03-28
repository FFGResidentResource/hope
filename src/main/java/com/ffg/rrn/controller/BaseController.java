package com.ffg.rrn.controller;

import com.ffg.rrn.utils.WebUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;

import java.security.Principal;

public class BaseController {

    protected String populateSCinModel(Model model, Principal principal) {

        User loggedinUser = (User) ((Authentication) principal).getPrincipal();
        String serviceCordInfo = WebUtils.toString(loggedinUser);
        model.addAttribute("serviceCordInfo", serviceCordInfo);
        return loggedinUser.getUsername();
    }
}
