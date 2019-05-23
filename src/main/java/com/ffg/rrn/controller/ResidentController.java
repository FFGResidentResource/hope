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
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

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
	public String residents(@RequestParam("residentId") Long residentId, Model model, Principal principal) throws Exception {

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

	@RequestMapping(value = "/onboarding", method = RequestMethod.GET)
	public String onboarding(Model model, Principal principal) throws Exception {

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

		Resident resident = residentService.getResidentById(0l, serviceCoord);
		resident = residentService.getAllQuestionnaire(resident);

		model.addAttribute("resident", resident);
		model.addAttribute("message", "Please select resident from All Resident Table first");

		return "residentPage";

	}

	@RequestMapping(value = "/getCurrentAssessment", method = { RequestMethod.GET, RequestMethod.POST })
	public String getCurrentAssessment(@RequestParam("residentId") Long residentId, @RequestParam("lifeDomain") String lifeDomain, Model model, Principal principal) throws Exception {

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

		// This is very important in returning respective Page
		return lifeDomain;

	}

	@RequestMapping(value = "/getReferralForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String getReferralForm(@RequestParam("residentId") Long residentId, @RequestParam("entryPoint") String entryPoint, Model model, Principal principal) throws Exception {

			// (1) (en)
			// After user login successfully.
			String serviceCoord = null;
			if (principal != null) {
				serviceCoord = populateSCinModel(model, principal);
			}

		Resident resident = residentService.getResidentById(residentId, serviceCoord);

			if (StringUtils.isEmpty(resident.getReferralReason())) {
				resident.setReferralReason(
						"{\"Non/late payment of rent\": \"false\", \"Utility Shut-off, scheduled for (Date):\":\"\", \"Housekeeping/home management\":\"false\", \"Lease violation for:\": \"\", \"Employment/job readiness\":\"false\", \"Education/job training\":\"false\", \"Noticeable change in:\":\"\", \"Resident-to-resident conflict issues\":\"false\", \"Suspected abuse/domestic violence/exploitation\":\"false\", \"Childcare/afterschool care\":\"false\", \"Transportation\":\"false\", \"Safety\":\"false\", \"Healthcare/medical issues\":\"false\", \"Other:\":\"\" }");
			}
			if (StringUtils.isEmpty(resident.getSelfSufficiency())) {
				resident.setSelfSufficiency(
						"{\"Improve knowledge of resources\":\"false\", \"Improve educational status\":\"false\", \"Obtain/maintain employment\":\"false\", \"Move to home ownership\":\"false\" }");
			}
			if (StringUtils.isEmpty(resident.getHousingStability())) {
				resident.setHousingStability("{\"Avoid  eviction\":\"false\", \"resolve lease violation\":\"false\"}");
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

		List<String> anticipatedOutcomes = new ArrayList<String>();

		if (StringUtils.isEmpty(resident.getSelfSufficiency())) {
			resident.setSelfSufficiency(
					"{\"Improve knowledge of resources\":\"false\", \"Improve educational status\":\"false\", \"Obtain/maintain employment\":\"false\", \"Move to home ownership\":\"false\" }");
		}
		if (StringUtils.isEmpty(resident.getHousingStability())) {
			resident.setHousingStability("{\"Avoid  eviction\":\"false\", \"resolve lease violation\":\"false\"}");
		}
		if (StringUtils.isEmpty(resident.getSafeSupportiveCommunity())) {
			resident.setSafeSupportiveCommunity("{\"Greater sense of satisfaction\":\"false\",\"Greater sense of safety\":\"false\", \"Greater sense of community/support\":\"false\"}");
		}

		JsonObject jsonObject = (new JsonParser()).parse(resident.getSelfSufficiency()).getAsJsonObject();

		Set<String> keySet = jsonObject.keySet();

		for (String key : keySet) {

			if (key.equals("Other")) {
				anticipatedOutcomes.add(jsonObject.get(key).getAsString());
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
				anticipatedOutcomes.add(jsonObject.get(key).getAsString());
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
				anticipatedOutcomes.add(jsonObject.get(key).getAsString());
			} else {
				if (jsonObject.get(key).getAsBoolean() == true) {
					anticipatedOutcomes.add(key);
				}
			}
		}

		resident.setAnticipatedOutcomesList(anticipatedOutcomes);

		model.addAttribute("resident", resident);
		model.addAttribute("message", "Please select resident from All Resident Table first");

		// This is very important in returning respective Page
		return "actionPlan";

	}

	@RequestMapping(value = "/getCaseNotes", method = { RequestMethod.GET, RequestMethod.POST })
	public String getCaseNotes(@RequestParam("residentId") Long residentId, Model model, Principal principal) throws Exception {

		// (1) (en)
		// After user login successfully.
		String serviceCoord = null;
		if (principal != null) {
			serviceCoord = populateSCinModel(model, principal);
		}

		Resident resident = residentService.getResidentById(residentId, serviceCoord);

		model.addAttribute("resident", resident);
		model.addAttribute("message", "Please select resident from All Resident Table first");

		// This is very important in returning respective Page
		return "caseNotes";

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

		if (bindingResult.hasErrors()) {
			setupDropdownList(resident);
			return "onboarding";
		}
		// This will be new ResidentId always
		// by default this new resident is active
		resident.setActive(true);
		resident.setModifiedBy(getSessionUsername());
		resident.setServiceCoord(getSessionUsername());
		residentService.saveResident(resident);

		setupDropdownList(resident);

		return "onboarding";
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

		return "onboarding";
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

		return "onboarding";
	}

	private void setupDropdownList(Resident resident) {
		resident.setPropertyList(residentService.getAllProperty(resident.getServiceCoord()));
		resident.setRefList(residentService.getAllReferral());
		resident.setAtList(residentService.getAllAType());
	}

	@PostMapping(value = "/saveReferralForm")
	public String saveReferralForm(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) throws DataAccessException, ParseException {
		residentService.saveReferralForm(resident);
		return "onboarding";
	}

	@PostMapping(value = "/saveActionPlan")
	public String saveActionPlan(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) throws DataAccessException, ParseException {
		residentService.saveActionPlan(resident);
		return "onboarding";
	}

	@PostMapping(value = "/saveCaseNotes")
	public String saveCaseNotes(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) throws DataAccessException, ParseException {
		residentService.saveCaseNotes(resident);
		return "onboarding";
	}

	@PostMapping(value = "/saveAssessmentType")
	public String ssmAssessment(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) {

		residentService.saveAssessment(resident);
		return "redirect:/getResidentById?residentId=" + resident.getResidentId();
	}

	@PostMapping("/saveAssessment")
	public String saveAssessment(@Valid @ModelAttribute Resident resident, BindingResult bindingResult, Model model, Principal principal) throws Exception {

		List<ResidentAssessmentQuestionnaire> questionnaires = getResidentAssessmentQuestionnaires(resident);

		if (StringUtils.equals(resident.getSelectedDate(), "NewAssessment")) {
			saveAssessmentAndScore(resident, questionnaires, resident.getLifeDomain());
		} else {
			updateAssessmentAndScore(resident, questionnaires, resident.getLifeDomain());
		}

		return "onboarding";
	}

	@PostMapping("/saveAssessmentAndGoToNext")
	public String saveAssessmentAndGoToNext(@Valid @ModelAttribute Resident resident, BindingResult bindingResult, Model model, Principal principal) throws Exception {

		List<ResidentAssessmentQuestionnaire> questionnaires = getResidentAssessmentQuestionnaires(resident);

		if (StringUtils.equals(resident.getSelectedDate(), "NewAssessment")) {
			saveAssessmentAndScore(resident, questionnaires, resident.getLifeDomain());
		} else {
			updateAssessmentAndScore(resident, questionnaires, resident.getLifeDomain());
		}

		String nextUrl = lifeDomainNextUrlMap.get(resident.getLifeDomain());

		return (nextUrl == null) ? "redirect:/allResident" : getCurrentAssessment(resident.getResidentId(), nextUrl, model, principal);
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
		for (ResidentAssessmentQuestionnaire residentAssessmentQuestionnaire : questionnaireList) {
			if (residentAssessmentQuestionnaire.getQuestionId() != null && residentAssessmentQuestionnaire.getChoiceId() != null) {
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
	private void updateAssessmentAndScore(@Valid Resident resident, List<ResidentAssessmentQuestionnaire> questionnaireList, String lifeDomain) throws SQLException {

		for (ResidentAssessmentQuestionnaire residentAssessmentQuestionnaire : questionnaireList) {
			if (residentAssessmentQuestionnaire.getQuestionId() != null && residentAssessmentQuestionnaire.getChoiceId() != null) {
				residentAssessmentQuestionnaire.setResidentId(resident.getResidentId());
				residentService.updateResidentAssessmentQuestionnaire(resident.getSelectedDate(), residentAssessmentQuestionnaire, lifeDomain);
			}
		}
		residentService.updateResidentScoreGoal(resident, lifeDomain);

	}

}
