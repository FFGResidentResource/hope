/**
 * 
 */
package com.rrn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ffgteam
 *
 */
@Controller
public class WelcomeController {
	
	@GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="Under Construction...") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
	
}
