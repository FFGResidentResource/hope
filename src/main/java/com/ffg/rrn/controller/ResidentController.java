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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ffg.rrn.model.Resident;
import com.ffg.rrn.model.ResidentAssessmentQuestionnaire;
import com.ffg.rrn.service.ResidentServiceImpl;
import com.ffg.rrn.service.ServiceCoordinatorServiceImpl;

/**
 * @author FFGRRNTeam
 *
 */
@Controller
public class ResidentController extends BaseController{
	
	@Autowired
	private ResidentServiceImpl residentService;

	@Autowired
	private ServiceCoordinatorServiceImpl serviceCoordinatorService;

	@RequestMapping(value = "/getResidentById",  method = { RequestMethod.GET, RequestMethod.POST })
	public String residents(@RequestParam("residentId") Long residentId, Model model, Principal principal) throws Exception{

		// (1) (en)
		// After user login successfully.
		String serviceCoord = null;
		if (principal != null) {
			serviceCoord = populateSCinModel(model, principal);
		}

		Resident resident  = residentService.getResidentById(residentId, serviceCoord);
		resident = residentService.getAllQuestionnaire(resident);

		model.addAttribute("resident", resident);
		model.addAttribute("message", "Save new resident first or load existing Resident from All Resident Tab.");

		return "residentPage";

	}

	@RequestMapping(value = "/newResident", method = RequestMethod.GET)
	public String createResident(Model model, Principal principal) throws Exception{

		// (1) (en)
		// After user login successfully.
		String serviceCoord = null;
		if (principal != null) {
			serviceCoord = populateSCinModel(model, principal);
		}

		Resident resident  = residentService.getResidentById(0l, serviceCoord);
		resident = residentService.getAllQuestionnaire(resident);

		model.addAttribute("resident", resident);
		model.addAttribute("message", "Please select resident from All Resident Table first");

		return "residentPage";

	}

	@RequestMapping(value = "/allResident", method = RequestMethod.GET)
	public String getAllResidents(Model model, Principal principal) throws Exception{

		// (1) (en)
		// After user login successfully.

		if (principal != null) {
			populateSCinModel(model, principal);
		}

		return "allResident";
	}

	@PostMapping("/saveResident")
	public String saveOrUpdate(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			setupDropdownList(resident);
			return "residentPage";
		}
		//This will be new ResidentId always
		//by default this new resident is active
		resident.setActive(true);
		resident.setModifiedBy(getSessionUsername());
		Long residentId = residentService.saveResident(resident);

		return "redirect:/allResident";
	}

	@PostMapping("/deactivateResident")
	public String deactivateResident(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			setupDropdownList(resident);
			return "residentPage";
		}

		resident.setActive(false);
		resident.setModifiedBy(getSessionUsername());
		residentService.updateResidentStatus(resident);

		return "redirect:/allResident";
	}

	private void setupDropdownList(Resident resident){
		resident.setPropertyList(residentService.getAllProperty());
		resident.setRefList(residentService.getAllReferral());
		resident.setAtList(residentService.getAllAType());
	}
	
	@PostMapping(value = "/saveAssessment")
	public String ssmAssessment(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) {

		int count = residentService.saveAssessment(resident);		
		
		return "redirect:/getResidentById?residentId="+resident.getResidentId();		
	}
	
	@PostMapping("/saveHousing")
    public String saveHousingAssessment(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) {      
        
		saveAssessmentAndScore(resident,resident.getHousingQuestionnaire(), "HOUSING" );		
		return "redirect:/getResidentById?residentId="+resident.getResidentId();
    }
	
	@PostMapping("/saveMoneyMgmt")
    public String saveMoneyMgmtAssessment(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) {   
     
		saveAssessmentAndScore(resident,resident.getMoneyMgmtQuestionnaire(), "MONEY MANAGEMENT" );			
		return "redirect:/getResidentById?residentId="+resident.getResidentId();
    }
	
	@PostMapping("/saveEmployment")
    public String saveEmploymentAssessment(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) {      
       
		saveAssessmentAndScore(resident,resident.getEmploymentQuestionnaire(), "EMPLOYMENT" );		
		return "redirect:/getResidentById?residentId="+resident.getResidentId();
    }
	
	@PostMapping("/saveEducation")
    public String saveEducation(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) {      
        
		saveAssessmentAndScore(resident,resident.getEducationQuestionnaire(), "EDUCATION" );		
		return "redirect:/getResidentById?residentId="+resident.getResidentId();
    }
	
	@PostMapping("/saveNetworkSupport")
    public String saveNetworkSupport(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) {      
       
		saveAssessmentAndScore(resident, resident.getNetSupportQuestionnaire(),"NETWORK SUPPORT" );		
		return "redirect:/getResidentById?residentId="+resident.getResidentId();
    }
	
	@PostMapping("/saveHouseHoldMgmt")
    public String saveHouseHoldMgmt(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) {      
        
		saveAssessmentAndScore(resident,resident.getHouseholdMgmtQuestionnaire(),"HOUSEHOLD MANAGEMENT" );		
		return "redirect:/getResidentById?residentId="+resident.getResidentId();
    }
	
	private void saveAssessmentAndScore(Resident resident, List<ResidentAssessmentQuestionnaire> questionnaireList, String lifeDomain) {
		
			
		for (ResidentAssessmentQuestionnaire residentAssessmentQuestionnaire : questionnaireList) {
			if(residentAssessmentQuestionnaire.getQuestionNumber()!=null || residentAssessmentQuestionnaire.getChoiceId() != null) {
				residentAssessmentQuestionnaire.setResidentId(resident.getResidentId());
				residentService.saveResidentAssessmentQuestionnaire(residentAssessmentQuestionnaire, lifeDomain);
			}
		}		
        
		residentService.saveResidentScoreGoal(resident, lifeDomain);
	}

}
