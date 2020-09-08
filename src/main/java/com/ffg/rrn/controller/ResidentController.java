/**
 * 
 */
package com.ffg.rrn.controller;

import java.security.Principal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

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

import com.ffg.rrn.model.Property;
import com.ffg.rrn.model.Resident;
import com.ffg.rrn.model.ResidentAssessmentQuestionnaire;
import com.ffg.rrn.service.ResidentServiceImpl;
import com.ffg.rrn.utils.AppConstants;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author FFGRRNTeam
 *
 */
@Controller
public class ResidentController extends BaseController {

	@Autowired
	private ResidentServiceImpl residentService;

	private Map<String, String> lifeDomainNextUrlMap = new HashMap<>();

	@PostConstruct
	public void init() {
		lifeDomainNextUrlMap.put(AppConstants.LIFE_DOMAIN_SERVICE_HOUSING, AppConstants.LIFE_DOMAIN_URL_MONEY_MGMT);
		lifeDomainNextUrlMap.put(AppConstants.LIFE_DOMAIN_SERVICE_MONEY_MANAGEMENT, AppConstants.LIFE_DOMAIN_URL_EMPLOYMENT);
		lifeDomainNextUrlMap.put(AppConstants.LIFE_DOMAIN_SERVICE_EMPLOYMENT, AppConstants.LIFE_DOMAIN_URL_EDUCATION);
		lifeDomainNextUrlMap.put(AppConstants.LIFE_DOMAIN_SERVICE_EDUCATION, AppConstants.LIFE_DOMAIN_URL_NETWORK_SUPPORT);
		lifeDomainNextUrlMap.put(AppConstants.LIFE_DOMAIN_SERVICE_NETWORK_SUPPORT, AppConstants.LIFE_DOMAIN_URL_HOUSEHOLD);
		lifeDomainNextUrlMap.put(AppConstants.LIFE_DOMAIN_SERVICE_HOUSEHOLD_MANAGEMENT, null);
		
		
	}

	@RequestMapping(value = "/getResidentById", method = { RequestMethod.GET, RequestMethod.POST })
	public String residents(@RequestParam("residentId") Long residentId, @RequestParam("entryPoint") String entryPoint, Model model, Principal principal) throws Exception {

		// (1) (en)
		// After user login successfully.
		String serviceCoord = null;
		if (principal != null) {
			serviceCoord = populateSCinModel(model, principal);
		}

		Resident resident = residentService.getResidentById(residentId, serviceCoord, "new", "getResident");

		// Grants will never be null - either "All" or some Property
		
		if (null != resident && null != resident.getResidentId() && null != checkResidentPropertyBelongsToSC(resident)) {

		resident = residentService.getAllQuestionnaire(resident);

		model.addAttribute("resident", resident);
		model.addAttribute("message", "Save new resident first or load existing Resident from All Resident Tab.");

		return "residentPage";
		} else {

			if (null != resident && null != resident.getResidentId()) {
				model.addAttribute("message", "You do not have Grants to view resident on This Property: " + resident.getPropertyName());
			} else {
				model.addAttribute("message", "There is no resident found with this Id: " + residentId);
			}
			return "403Page";
		}

	}

	private Property checkResidentPropertyBelongsToSC(Resident resident) {
		
		return resident.getPropertyList().stream()
		.filter(x -> resident.getPropertyName().equals(x.getPropertyName()))
		.findAny().orElse(null);
		
	}

	@RequestMapping(value = "/onboarding", method = RequestMethod.GET)
	public String onboarding(@RequestParam(name = "residentId", required = false, defaultValue = "0") Long residentId, Model model, Principal principal) throws Exception {

		// (1) (en)
		// After user login successfully.
		String serviceCoord = null;
		if (principal != null) {
			serviceCoord = populateSCinModel(model, principal);
		}

		Resident resident = residentService.getResidentById(0l, serviceCoord, "new", "onboarding");
		resident = residentService.getAllQuestionnaire(resident);

		model.addAttribute("resident", resident);
		model.addAttribute("message", "Please select resident from All Resident Table first");

		return "onboarding";

	}

	@RequestMapping(value = "/newResident", method = RequestMethod.GET)
	public String createResident(Model model, Principal principal) throws Exception {

		// (1) (en)
		// After user login successfully.
		String serviceCoord = null;
		if (principal != null) {
			serviceCoord = populateSCinModel(model, principal);
		}

		Resident resident = residentService.getResidentById(0l, serviceCoord, "new", "newResident");
		resident = residentService.getAllQuestionnaire(resident);

		model.addAttribute("resident", resident);
		model.addAttribute("message", "Please select resident from All Resident Table first");

		return "residentPage";

	}

	@RequestMapping(value = "/getCurrentAssessment", method = { RequestMethod.GET, RequestMethod.POST })
	public String getCurrentAssessment(@RequestParam("residentId") Long residentId, @RequestParam("lifeDomain") String lifeDomain,
			@RequestParam(name = "onThisDate", required = false, defaultValue = "new") String onThisDate, Model model, Principal principal) throws Exception {

		// (1) (en)
		// After user login successfully.
		String serviceCoord = null;
		if (principal != null) {
			serviceCoord = populateSCinModel(model, principal);
		}

		Resident resident = residentService.getResidentById(residentId, serviceCoord, onThisDate, "currentAssessment");

		// Grants will never be null - either "All" or some Property
		
		if (null != resident && null != resident.getResidentId() && null != checkResidentPropertyBelongsToSC(resident)) {

		resident = residentService.getAllQuestionnaire(resident);

		model.addAttribute("resident", resident);
		model.addAttribute("message", "Please select resident from All Resident Table first");

		// This is very important in returning respective Page
		return lifeDomain;
		} else {

			if (null != resident && null != resident.getResidentId()) {
				model.addAttribute("message", "You do not have Grants to view resident on This Property: " + resident.getPropertyName());
			} else {
				model.addAttribute("message", "There is no resident found with this Id: " + residentId);
			}
			return "403Page";
		}

	}

	@RequestMapping(value = "/getReferralForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String getReferralForm(@RequestParam("residentId") Long residentId, @RequestParam("entryPoint") String entryPoint,
			@RequestParam(name = "onThisDate", required = false, defaultValue = "new") String onThisDate, Model model, Principal principal) throws Exception {

			// (1) (en)
			// After user login successfully.
			String serviceCoord = null;
			if (principal != null) {
				serviceCoord = populateSCinModel(model, principal);
			}

		// Grants will never be null - either "All" or some Property
		
		Resident resident = residentService.getResidentById(residentId, serviceCoord, onThisDate, "referralForm");

		if (null != resident && null != resident.getResidentId() && null != checkResidentPropertyBelongsToSC(resident)) {

			if (StringUtils.isEmpty(resident.getReferralReason())) {
				resident.setReferralReason(
						"{\"Non/late payment of rent\": \"false\", \"Utility Shut-off, scheduled for (Date):\":\"\", \"Housekeeping/home management\":\"false\", \"Lease violation for:\": \"\", \"Employment/job readiness\":\"false\", \"Education/job training\":\"false\", \"Noticeable change in:\":\"\", \"Resident-to-resident conflict issues\":\"false\", \"Suspected abuse/domestic violence/exploitation\":\"false\", \"Childcare/afterschool care\":\"false\", \"Transportation\":\"false\", \"Safety\":\"false\", \"Healthcare/medical issues\":\"false\", \"Other:\":\"\" }");
			}
			if (StringUtils.isEmpty(resident.getSelfSufficiency())) {
				resident.setSelfSufficiency(
						"{\"Improve knowledge of resources\":\"false\", \"Improve educational status\":\"false\", \"Obtain/maintain employment\":\"false\", \"Move to home ownership\":\"false\" }");
			}
			if (StringUtils.isEmpty(resident.getHousingStability())) {
				resident.setHousingStability("{\"Avoid  eviction\":\"false\", \"Resolve lease violation\":\"false\"}");
			}
			if (StringUtils.isEmpty(resident.getSafeSupportiveCommunity())) {
				resident.setSafeSupportiveCommunity("{\"Greater sense of satisfaction\":\"false\",\"Greater sense of safety\":\"false\", \"Greater sense of community/support\":\"false\"}");
			}
			if (StringUtils.isEmpty(resident.getResidentAppointmentScheduled())) {
				resident.setResidentAppointmentScheduled("{\"Resident Appointment Scheduled?\":\"\"}");
			}

			model.addAttribute("resident", resident);
			model.addAttribute("message", "Please select resident from All Resident Table first");

			// This is very important in returning respective Page
		return "referralForm";
		} else {

			if (null != resident && null != resident.getResidentId()) {
				model.addAttribute("message", "You do not have Grants to view resident on This Property: " + resident.getPropertyName());
			} else {
				model.addAttribute("message", "There is no resident found with this Id: " + residentId);
			}
			return "403Page";
		}

	}

	@RequestMapping(value = "/getActionPlan", method = { RequestMethod.GET, RequestMethod.POST })
	public String getActionPlan(@RequestParam("residentId") Long residentId, @RequestParam(name = "onThisDate", required = false, defaultValue = "new") String onThisDate, Model model,
			Principal principal) throws Exception {

		// (1) (en)
		// After user login successfully.
		String serviceCoord = null;
		if (principal != null) {
			serviceCoord = populateSCinModel(model, principal);
		}

		Resident resident = residentService.getResidentById(residentId, serviceCoord, onThisDate, "ActionPlan");

		resident.setSelectedDate(onThisDate);

		if (StringUtils.equals("new", onThisDate)) {

			resident.setPlanOfAction(null);
			resident.setPlanDetails(null);
			resident.setReferralPartner(null);
			resident.setAnticipatedDates(null);
			resident.setAnticipatedOutcome(null);
			resident.setFollowUpNotes(null);
			resident.setOutcomesAchieved(null);
			resident.setCompletionDates(null);
			resident.setAchievedGoals(null);

		}

		// Grants will never be null - either "All" or some Property
		
		if (null != resident && null != resident.getResidentId() && null != checkResidentPropertyBelongsToSC(resident)) {

		resident = residentService.getAllQuestionnaire(resident);
		resident.setMostRecentSSMDate(residentService.getMostRecentSSMDate(residentId));

		List<String> anticipatedOutcomes = new ArrayList<String>();

		if (StringUtils.isEmpty(resident.getSelfSufficiency())) {
			resident.setSelfSufficiency(
					"{\"Improve knowledge of resources\":\"false\", \"Improve educational status\":\"false\", \"Obtain/maintain employment\":\"false\", \"Move to home ownership\":\"false\" }");
		}
		if (StringUtils.isEmpty(resident.getHousingStability())) {
			resident.setHousingStability("{\"Avoid  eviction\":\"false\", \"Resolve lease violation\":\"false\"}");
		}
		if (StringUtils.isEmpty(resident.getSafeSupportiveCommunity())) {
			resident.setSafeSupportiveCommunity("{\"Greater sense of satisfaction\":\"false\",\"Greater sense of safety\":\"false\", \"Greater sense of community/support\":\"false\"}");
		}

		JsonObject jsonObject = (new JsonParser()).parse(resident.getSelfSufficiency()).getAsJsonObject();

		Set<String> keySet = jsonObject.keySet();

		for (String key : keySet) {

			if (key.equals("Other")) {
				if (!StringUtils.isEmpty(jsonObject.get(key).getAsString())) {
					anticipatedOutcomes.add(jsonObject.get(key).getAsString());
				}
			} else {
				if (jsonObject.get(key).getAsBoolean() == true) {
					anticipatedOutcomes.add(key);
				}
			}
		}

		jsonObject = (new JsonParser()).parse(resident.getHousingStability()).getAsJsonObject();
		keySet = jsonObject.keySet();

		for (String key : keySet) {

			if (key.equals("Other")) {
				if (!StringUtils.isEmpty(jsonObject.get(key).getAsString())) {
					anticipatedOutcomes.add(jsonObject.get(key).getAsString());
				}
			} else {
				if (jsonObject.get(key).getAsBoolean() == true) {
					anticipatedOutcomes.add(key);
				}
			}
		}

		jsonObject = (new JsonParser()).parse(resident.getSafeSupportiveCommunity()).getAsJsonObject();
		keySet = jsonObject.keySet();

		for (String key : keySet) {

			if (key.equals("Other")) {
				if (!StringUtils.isEmpty(jsonObject.get(key).getAsString())) {
					anticipatedOutcomes.add(jsonObject.get(key).getAsString());
				}
			} else {
				if (jsonObject.get(key).getAsBoolean() == true) {
					anticipatedOutcomes.add(key);
				}
			}
		}

		resident.setAnticipatedOutcomesList(anticipatedOutcomes);

			resident.setRefPartners(residentService.getAllReferralPartners());

		model.addAttribute("resident", resident);
		model.addAttribute("message", "Please select resident from All Resident Table first");

		// This is very important in returning respective Page
		return "actionPlan";

		}
		else {

			if (null != resident && null != resident.getResidentId()) {
				model.addAttribute("message", "You do not have Grants to view resident on This Property: " + resident.getPropertyName());
			} else {
				model.addAttribute("message", "There is no resident found with this Id: " + residentId);
			}
			return "403Page";
		}

	}

	@RequestMapping(value = "/getCaseNotes", method = { RequestMethod.GET, RequestMethod.POST })
	public String getCaseNotes(@RequestParam("residentId") Long residentId, @RequestParam(name = "onThisDate", required = false, defaultValue = "new") String onThisDate, Model model,
			Principal principal) throws Exception {

		// (1) (en)
		// After user login successfully.
		String serviceCoord = null;
		if (principal != null) {
			serviceCoord = populateSCinModel(model, principal);
		}

		Resident resident = residentService.getResidentById(residentId, serviceCoord, onThisDate, "ContactNotes");
		resident.setSelectedDate(onThisDate);

		if (StringUtils.equals("new", onThisDate)) {
			resident.setPlan(null);
			resident.setDescription(null);
			resident.setAssessment(null);
			resident.setNoShowDate(null);
		}

		// Grants will never be null - either "All" or some Property
		
		if (null != resident && null != resident.getResidentId() && null != checkResidentPropertyBelongsToSC(resident)) {

		model.addAttribute("resident", resident);
		model.addAttribute("message", "Please select resident from All Resident Table first");

		// This is very important in returning respective Page
		return "caseNotes";
		} else {

			if (null != resident && null != resident.getResidentId()) {
				model.addAttribute("message", "You do not have Grants to view resident on This Property: " + resident.getPropertyName());
			} else {
				model.addAttribute("message", "There is no resident found with this Id: " + residentId);
			}
			return "403Page";
		}

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
	public String saveOrUpdate(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) throws Exception {

		// This will be new ResidentId always
		// by default this new resident is active
		resident.setActive(true);
		resident.setModifiedBy(getSessionUsername());
		resident.setServiceCoord(getSessionUsername());
		residentService.saveResident(resident);

		return "redirect:/onboarding?residentId=" + resident.getResidentId();
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

		return "redirect:/onboarding?residentId=" + resident.getResidentId();
	}

	@PostMapping("/reactivateResident")
	public String reactivateResident(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			setupDropdownList(resident);
			return "onboarding";
		}

		resident.setActive(true);
		resident.setModifiedBy(getSessionUsername());
		residentService.updateResidentStatus(resident);

		return "redirect:/onboarding?residentId=" + resident.getResidentId();
	}

	private void setupDropdownList(Resident resident) {
		resident.setPropertyList(residentService.getAllProperty(resident.getServiceCoord()));
		resident.setRefList(residentService.getAllReferral());
		resident.setAtList(residentService.getAllAType());
	}

	@PostMapping(value = "/saveReferralForm")
	public String saveReferralForm(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) throws DataAccessException, ParseException {
		residentService.saveReferralForm(resident);

		return "redirect:/onboarding?residentId=" + resident.getResidentId();
	}

	@PostMapping(value = "/saveActionPlan")
	public String saveActionPlan(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) throws DataAccessException, ParseException {

		if (StringUtils.equals(resident.getSelectedDate(), "new")) {

			residentService.saveActionPlan(resident);
		} else {
			residentService.updateActionPlan(resident);
		}

		return "redirect:/onboarding?residentId=" + resident.getResidentId();
	}

	@PostMapping(value = "/saveCaseNotes")
	public String saveCaseNotes(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) throws DataAccessException, ParseException {

		if (StringUtils.equals(resident.getSelectedDate(), "new")) {

			residentService.saveCaseNotes(resident);
		} else {
			residentService.updateCaseNotes(resident);
		}

		return "redirect:/onboarding?residentId=" + resident.getResidentId();
	}

	@PostMapping(value = "/saveAssessmentType")
	public String ssmAssessment(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) {

		residentService.saveAssessment(resident);
		return "redirect:/onboarding?residentId=" + resident.getResidentId();
	}

	@PostMapping("/saveAssessment")
	public String saveAssessment(@Valid @ModelAttribute Resident resident, BindingResult bindingResult, Model model, Principal principal) throws Exception {

		List<ResidentAssessmentQuestionnaire> questionnaires = getResidentAssessmentQuestionnaires(resident);

		if (StringUtils.equals(resident.getSelectedDate(), "NewAssessment")) {

			saveAssessmentAndScore(resident, questionnaires, resident.getLifeDomain());
		} else {
			updateAssessmentAndScore(resident, questionnaires, resident.getLifeDomain());
		}

		return "redirect:/onboarding?residentId=" + resident.getResidentId();
	}

	private List<ResidentAssessmentQuestionnaire> getResidentAssessmentQuestionnaires(Resident resident) {
		List<ResidentAssessmentQuestionnaire> raqs = new ArrayList<>();

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
		case AppConstants.LIFE_DOMAIN_SERVICE_DISABILITY_PHYSICAL:	
			raqs = resident.getDisabilityPhysicalQuestionnaire();
		break;
		}
		
		return raqs;
	}

	/**
	 * This is when Saving New Assessment
	 * 
	 * @param resident
	 * @param questionnaireList
	 * @param lifeDomain
	 */
	private void saveAssessmentAndScore(Resident resident, List<ResidentAssessmentQuestionnaire> questionnaireList, String lifeDomain) {

		long row = 0l;
		for (ResidentAssessmentQuestionnaire residentAssessmentQuestionnaire : questionnaireList) {
			if (residentAssessmentQuestionnaire.getQuestionId() != null && residentAssessmentQuestionnaire.getChoiceId() != null) {
				residentAssessmentQuestionnaire.setResidentId(resident.getResidentId());
				row = residentService.saveResidentAssessmentQuestionnaire(residentAssessmentQuestionnaire, lifeDomain, null);
			}
		}
		
		if(row > 0) {
			residentService.saveResidentScoreGoal(resident, lifeDomain);
		}
	}

	/**
	 * This is called when you updating existing historical Assessment.
	 * 
	 * @param resident
	 * @param questionnaireList
	 * @param lifeDomain
	 * @throws SQLException
	 */
	private void updateAssessmentAndScore(@Valid Resident resident, List<ResidentAssessmentQuestionnaire> questionnaireList, String lifeDomain) throws SQLException {

		for (ResidentAssessmentQuestionnaire residentAssessmentQuestionnaire : questionnaireList) {
			if (residentAssessmentQuestionnaire.getQuestionId() != null && residentAssessmentQuestionnaire.getChoiceId() != null) {
				residentAssessmentQuestionnaire.setResidentId(resident.getResidentId());
				int row = residentService.updateResidentAssessmentQuestionnaire(resident.getSelectedDate(), residentAssessmentQuestionnaire, lifeDomain);
				
				if(row < 1) {
					residentService.insertResidentAssessmentQuestionnaire(resident.getSelectedDate(), residentAssessmentQuestionnaire, lifeDomain);
				}
			}
		}
		residentService.updateResidentScoreGoal(resident, lifeDomain);

	}

}
