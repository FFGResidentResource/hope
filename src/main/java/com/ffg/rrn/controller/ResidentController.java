/**
 * 
 */
package com.ffg.rrn.controller;

import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import com.ffg.rrn.utils.AppConstants;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import com.ffg.rrn.model.ActionPlan;
import com.ffg.rrn.model.Resident;
import com.ffg.rrn.model.ResidentAssessmentQuestionnaire;
import com.ffg.rrn.service.ResidentServiceImpl;

/**
 * @author FFGRRNTeam
 *
 */
@Controller
public class ResidentController extends BaseController {

	@Autowired
	private ResidentServiceImpl residentService;	

	private Map<String, String> lifeDomainUriMap = new HashMap<>();

	@PostConstruct
	public void init(){
		lifeDomainUriMap.put(AppConstants.LIFE_DOMAIN_SERVICE_HOUSING, AppConstants.LIFE_DOMAIN_URL_HOUSING);
		lifeDomainUriMap.put(AppConstants.LIFE_DOMAIN_SERVICE_MONEY_MANAGEMENT, AppConstants.LIFE_DOMAIN_URL_MONEY_MGMT);
		lifeDomainUriMap.put(AppConstants.LIFE_DOMAIN_SERVICE_EMPLOYMENT, AppConstants.LIFE_DOMAIN_URL_EMPLOYMENT);
		lifeDomainUriMap.put(AppConstants.LIFE_DOMAIN_SERVICE_EDUCATION, AppConstants.LIFE_DOMAIN_URL_EDUCATION);
		lifeDomainUriMap.put(AppConstants.LIFE_DOMAIN_SERVICE_NETWORK_SUPPORT, AppConstants.LIFE_DOMAIN_URL_NETWORK_SUPPORT);
		lifeDomainUriMap.put(AppConstants.LIFE_DOMAIN_SERVICE_HOUSEHOLD_MANAGEMENT, AppConstants.LIFE_DOMAIN_URL_HOUSEHOLD);
	}

	@RequestMapping(value = "/getResidentById", method = { RequestMethod.GET, RequestMethod.POST })
	public String residents(@RequestParam("residentId") Long residentId, Model model, Principal principal)
			throws Exception {

		// (1) (en)
		// After user login successfully.
		String serviceCoord = null;
		if (principal != null) {
			serviceCoord = populateSCinModel(model, principal);
		}

		Resident resident = residentService.getResidentById(residentId, serviceCoord);
		resident = residentService.getAllQuestionnaire(resident);

		model.addAttribute("resident", resident);
		model.addAttribute("message", "Save new resident first or load existing Resident from All Resident Tab.");

		return "residentPage";

	}

	@RequestMapping(value = "/newResident", method = RequestMethod.GET)
	public String createResident(Model model, Principal principal) throws Exception {

		// (1) (en)
		// After user login successfully.
		String serviceCoord = null;
		if (principal != null) {
			serviceCoord = populateSCinModel(model, principal);
		}

		Resident resident = residentService.getResidentById(0l, serviceCoord);
		resident = residentService.getAllQuestionnaire(resident);

		model.addAttribute("resident", resident);
		model.addAttribute("message", "Please select resident from All Resident Table first");

		return "residentPage";

	}
	
	
	@RequestMapping(value = "/getCurrentAssessment", method = { RequestMethod.GET, RequestMethod.POST })
	public String getCurrentAssessment(@RequestParam("residentId") Long residentId, @RequestParam("lifeDomain") String lifeDomain,Model model, Principal principal) throws Exception {

		// (1) (en)
		// After user login successfully.
		String serviceCoord = null;
		if (principal != null) {
			serviceCoord = populateSCinModel(model, principal);
		}

		Resident resident = residentService.getResidentById(residentId, serviceCoord);
		resident = residentService.getAllQuestionnaire(resident);

		model.addAttribute("resident", resident);
		model.addAttribute("message", "Please select resident from All Resident Table first");

		//This is very important in returning respective Page
		return lifeDomain;

	}

	@RequestMapping(value = "/getActionPlan", method = { RequestMethod.GET, RequestMethod.POST })
	public String getActionPlan(@RequestParam("residentId") Long residentId, Model model, Principal principal) throws Exception {

		// (1) (en)
		// After user login successfully.
		String serviceCoord = null;
		if (principal != null) {
			serviceCoord = populateSCinModel(model, principal);
		}

		Resident resident = residentService.getResidentById(residentId, serviceCoord);
		resident = residentService.getAllQuestionnaire(resident);
		resident.setMostRecentSSMDate(residentService.getMostRecentSSMDate(residentId));

		model.addAttribute("resident", resident);
		model.addAttribute("message", "Please select resident from All Resident Table first");

		// This is very important in returning respective Page
		return "actionPlan";

	}

	@RequestMapping(value = "/allResident", method = RequestMethod.GET)
	public String getAllResidents(Model model, Principal principal) throws Exception {

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
		// This will be new ResidentId always
		// by default this new resident is active
		resident.setActive(true);
		resident.setModifiedBy(getSessionUsername());
		residentService.saveResident(resident);

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

	@PostMapping("/reactivateResident")
	public String reactivateResident(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			setupDropdownList(resident);
			return "residentPage";
		}

		resident.setActive(true);
		resident.setModifiedBy(getSessionUsername());
		residentService.updateResidentStatus(resident);

		return "redirect:/allResident";
	}

	private void setupDropdownList(Resident resident) {
		resident.setPropertyList(residentService.getAllProperty());
		resident.setRefList(residentService.getAllReferral());
		resident.setAtList(residentService.getAllAType());
	}

	@PostMapping(value = "/saveActionPlan")
	public String saveActionPlan(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) throws DataAccessException, ParseException {
		residentService.saveActionPlan(resident);
		return "redirect:/allResident";
	}	

	@PostMapping(value = "/saveAssessmentType")
	public String ssmAssessment(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) {

		residentService.saveAssessment(resident);
		return "redirect:/getResidentById?residentId=" + resident.getResidentId();
	}

	@PostMapping("/saveEachAssessment")
	public String saveEachAssessment(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) throws SQLException {
		
		List<ResidentAssessmentQuestionnaire> raqs = new ArrayList<ResidentAssessmentQuestionnaire>();
		
		switch (resident.getLifeDomain()) {
			case AppConstants.LIFE_DOMAIN_SERVICE_HOUSING:
				raqs = resident.getHousingQuestionnaire();
				break;
			case AppConstants.LIFE_DOMAIN_SERVICE_MONEY_MANAGEMENT:
				raqs = resident.getMoneyMgmtQuestionnaire();
				break;
			case AppConstants.LIFE_DOMAIN_SERVICE_EMPLOYMENT:
				raqs = resident.getEmploymentQuestionnaire();
			break;
			case AppConstants.LIFE_DOMAIN_SERVICE_EDUCATION:
				raqs = resident.getEducationQuestionnaire();
			break;
			case AppConstants.LIFE_DOMAIN_SERVICE_NETWORK_SUPPORT:
				raqs = resident.getNetSupportQuestionnaire();
			break;
			case AppConstants.LIFE_DOMAIN_SERVICE_HOUSEHOLD_MANAGEMENT:
				raqs = resident.getHouseholdMgmtQuestionnaire();
			break;
		}		

		if (StringUtils.equals(resident.getSelectedDate(), "NewAssessment")) {
			saveAssessmentAndScore(resident, raqs, resident.getLifeDomain());			
		} else {
			updateAssessmentAndScore(resident, raqs, resident.getLifeDomain());			
		}

		getCurrentAssessment

		return "redirect:/allResident";
	}

	/**
	 * This is when Saving New Assessment
	 * 
	 * @param resident
	 * @param questionnaireList
	 * @param lifeDomain
	 */
	private void saveAssessmentAndScore(Resident resident, List<ResidentAssessmentQuestionnaire> questionnaireList,
			String lifeDomain) {
		for (ResidentAssessmentQuestionnaire residentAssessmentQuestionnaire : questionnaireList) {
			if (residentAssessmentQuestionnaire.getQuestionId() != null	&& residentAssessmentQuestionnaire.getChoiceId() != null) {
				residentAssessmentQuestionnaire.setResidentId(resident.getResidentId());
				residentService.saveResidentAssessmentQuestionnaire(residentAssessmentQuestionnaire, lifeDomain);
			}
		}
		residentService.saveResidentScoreGoal(resident, lifeDomain);
	}

	/**
	 * This is called when you updating existing historical Assessment.
	 * 
	 * @param resident
	 * @param questionnaireList
	 * @param lifeDomain
	 * @throws SQLException 
	 */
	private void updateAssessmentAndScore(@Valid Resident resident,
			List<ResidentAssessmentQuestionnaire> questionnaireList, String lifeDomain) throws SQLException {

		for (ResidentAssessmentQuestionnaire residentAssessmentQuestionnaire : questionnaireList) {
			if (residentAssessmentQuestionnaire.getQuestionId() != null && residentAssessmentQuestionnaire.getChoiceId() != null) {
				residentAssessmentQuestionnaire.setResidentId(resident.getResidentId());
				residentService.updateResidentAssessmentQuestionnaire(resident.getSelectedDate(), residentAssessmentQuestionnaire, lifeDomain);
			}
		}
		residentService.updateResidentScoreGoal(resident, lifeDomain);

	}

}
