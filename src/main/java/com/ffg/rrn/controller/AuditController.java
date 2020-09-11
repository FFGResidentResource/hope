/**
 * 
 */
package com.ffg.rrn.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ffg.rrn.model.Audit;
import com.ffg.rrn.model.ResidentAudit;
import com.ffg.rrn.service.ResidentServiceImpl;

/**
 * @author FFGTeam
 *
 */
@Controller
public class AuditController extends BaseController {
	
	@Autowired
    private ResidentServiceImpl residentService;
	
	@ModelAttribute("residentAudits")
	public Audit initialize() {
		return new Audit();
	}
	
    @RequestMapping(value = "/audits", method = RequestMethod.GET)
    public String auditPage(@ModelAttribute Audit audit,
                            Model model, Principal principal) {
        if (principal != null) {
            populateSCinModel(model, principal);
        }

        Audit au = new Audit();
        model.addAttribute("residentAudits", au);
		
        return "auditPage";
    }
    
    @PostMapping("/pullAuditReports")
	public String pullAuditReports(Model model, Principal principal,  @ModelAttribute("audit") @Valid Audit au) {

		if (principal != null) {
			populateSCinModel(model, principal);
		}

		List<ResidentAudit> residentAudits = residentService.getResidentAudits(au.getResIdForAudit());
		au.setAuditListPerResident(residentAudits);
		
		model.addAttribute("residentAudits", au);
        
		return "auditPage";
    }

}
