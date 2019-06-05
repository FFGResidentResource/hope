/**
 * 
 */
package com.ffg.rrn.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ffg.rrn.model.Dashboard;
import com.ffg.rrn.model.Property;
import com.ffg.rrn.service.ResidentServiceImpl;

/**
 * @author FFGRRNTeam
 *
 */
@Controller
public class DashboardController extends BaseController {

	@Autowired
	private ResidentServiceImpl residentService;

	@RequestMapping(value = "/dashboard", method = { RequestMethod.GET, RequestMethod.POST })
	public String getDashboard(Model model, Principal principal) throws Exception {

		// (1) (en)
		// After user login successfully.
		String serviceCoord = null;
		if (principal != null) {
			serviceCoord = populateSCinModel(model, principal);
		}

		List<Property> allProperty = residentService.getAllProperty(getSessionUsername());
		for (Property property : allProperty) {
			property.setChecked(true);
		}

		Dashboard dashboard = new Dashboard();
		dashboard.setProperties(allProperty);

		model.addAttribute("dashboard", dashboard);

		return "dashboard";
	}


}
