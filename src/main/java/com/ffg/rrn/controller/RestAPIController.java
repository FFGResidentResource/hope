/**
 * 
 */
package com.ffg.rrn.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import com.ffg.rrn.dao.DashboardDao;
import com.ffg.rrn.model.AssessmentByDateAndScoreGoal;
import com.ffg.rrn.model.CategoryPercentage;
import com.ffg.rrn.model.Dashboard;
import com.ffg.rrn.model.Property;
import com.ffg.rrn.model.Resident;
import com.ffg.rrn.model.ResidentAssessmentQuestionnaire;
import com.ffg.rrn.model.ServiceCoordinator;
import com.ffg.rrn.model.WizardStepCounter;
import com.ffg.rrn.service.ResidentServiceImpl;
import com.ffg.rrn.service.ServiceCoordinatorServiceImpl;

/**
 * @author FFGRRNTEam
 *
 */
@RestController
public class RestAPIController {

	@Autowired
	private ResidentServiceImpl residentService;

	@Autowired
	private ServiceCoordinatorServiceImpl serviceCoordinatorService;
	
	@Autowired
	private DashboardDao dashDao;
	

	@PostMapping("/getAllResidents")
	public ResponseEntity<?> getAllResidents() {

		List<Resident> allResident = residentService.getAllResident();

		// Create a new ArrayList
		ArrayList<Resident> newList = new ArrayList<Resident>();

		// Traverse through the first list
		for (Resident element : allResident) {

			// If this element is not present in newList
			// then add it
			if (!newList.contains(element)) {
				newList.add(element);
			}
		}

		return ResponseEntity.ok(newList);
	}

	@PostMapping("/getAllProperty")
	public ResponseEntity<?> getAllProperty(@RequestParam("serviceCoordinator") String srvcCoord) {

		List<Property> allProperty = residentService.getAllProperty(srvcCoord);

		return ResponseEntity.ok(allProperty);
	}
	
	@PostMapping("/genderPercentage")
	public ResponseEntity<?> generateGenderReport(@RequestBody String selectedProperties)  {
		
		List<CategoryPercentage> cp = dashDao.getGenderPercentage(selectedProperties);

		return ResponseEntity.ok(cp);
	}
	
	@PostMapping("/ethnicityPercentage")
	public ResponseEntity<?> generateEthinicityReport(@RequestBody String selectedProperties)  {
		
		List<CategoryPercentage> cp = dashDao.getEthnicityPercentage(selectedProperties);

		return ResponseEntity.ok(cp);
	}
	
	@PostMapping("/racePercentage")
	public ResponseEntity<?> generateRaceReport(@RequestBody String selectedProperties)  {
		
		List<CategoryPercentage> cp = dashDao.getRacePercentage(selectedProperties);

		return ResponseEntity.ok(cp);
	}
	
	@PostMapping("/languagePercentage")
	public ResponseEntity<?> generateLanguageReport(@RequestBody String selectedProperties)  {
		
		List<CategoryPercentage> cp = dashDao.getLanguagePercentage(selectedProperties);

		return ResponseEntity.ok(cp);
	}
	
	@PostMapping("/healthPercentage")
	public ResponseEntity<?> generateHealthReport(@RequestBody String selectedProperties)  {
		
		List<CategoryPercentage> cp = dashDao.getHealthPercentage(selectedProperties);

		return ResponseEntity.ok(cp);
	}
	
	@PostMapping("/ssiPercentage")
	public ResponseEntity<?> generateSsiReport(@RequestBody String selectedProperties)  {
		
		List<CategoryPercentage> cp = dashDao.getSSIPercentage(selectedProperties);

		return ResponseEntity.ok(cp);
	}
	
	@PostMapping("/ssdiPercentage")
	public ResponseEntity<?> generateSsdiReport(@RequestBody String selectedProperties)  {
		
		List<CategoryPercentage> cp = dashDao.getSSDIPercentage(selectedProperties);

		return ResponseEntity.ok(cp);
	}
	
	@PostMapping("/educationPercentage")
	public ResponseEntity<?> generateEducationReport(@RequestBody String selectedProperties)  {
		
		List<CategoryPercentage> cp = dashDao.getEducationPercentage(selectedProperties);

		return ResponseEntity.ok(cp);
	}
	
	
	@PostMapping("/exOffenderPercentage")
	public ResponseEntity<?> generateExOffReport(@RequestBody String selectedProperties)  {
		
		List<CategoryPercentage> cp = dashDao.getReturningCitizenPercentage(selectedProperties);

		return ResponseEntity.ok(cp);
	}
	
	@PostMapping("/veteranPercentage")
	public ResponseEntity<?> generateVeteranReport(@RequestBody String selectedProperties)  {
		
		List<CategoryPercentage> cp = dashDao.getVeteranPercentage(selectedProperties);

		return ResponseEntity.ok(cp);
	}
	
	@PostMapping("/disabilityPercentage")
	public ResponseEntity<?> generateDisReport(@RequestBody String selectedProperties)  {
		
		List<CategoryPercentage> cp = dashDao.getDisabilityPercentage(selectedProperties);

		return ResponseEntity.ok(cp);
	}
	
	@PostMapping("/householdPercentage")
	public ResponseEntity<?> generateHouseholdReport(@RequestBody String selectedProperties)  {
		
		List<CategoryPercentage> cp = dashDao.getHouseholdPercentage(selectedProperties);

		return ResponseEntity.ok(cp);
	}
	
	
	
	@PostMapping("/maritalStatusPercentage")
	public ResponseEntity<?> generateMSReport(@RequestBody String selectedProperties)  {
		
		List<CategoryPercentage> cp = dashDao.getMaritalStatusPercentage(selectedProperties);

		return ResponseEntity.ok(cp);
	}

	@PostMapping("/getAllServiceCoordinators")
	public ResponseEntity<?> getAllServiceCoordinators() {

		List<ServiceCoordinator> allServiceCoordinators = serviceCoordinatorService.getAllServiceCoordinators();

		return ResponseEntity.ok(allServiceCoordinators);
	}
	
	@PostMapping("/expFoodShortagePercentage")
	public ResponseEntity<?> getExpFoodShortagePercentage(@RequestBody String selectedProperties) {

		List<CategoryPercentage> cp = dashDao.getFoodShortagesPercentage(selectedProperties);

		return ResponseEntity.ok(cp);
	}
	
	@PostMapping("/modeOfTransportationPercentage")
	public ResponseEntity<?> getModeOfTransportationPercentage(@RequestBody String selectedProperties) {

		List<CategoryPercentage> cp = dashDao.getModeOfTransportationPercentage(selectedProperties);

		return ResponseEntity.ok(cp);
	}
	
	@PostMapping("/internetAccessPercentage")
	public ResponseEntity<?> getInternetAccessPercentage(@RequestBody String selectedProperties) {

		List<CategoryPercentage> cp = dashDao.getInternetAccessPercentage(selectedProperties);

		return ResponseEntity.ok(cp);
	}
	
	@PostMapping("/prefContactPercentage")
	public ResponseEntity<?> getPrefContactPercentage(@RequestBody String selectedProperties) {

		List<CategoryPercentage> cp = dashDao.prefferedContactMethodPercentage(selectedProperties);

		return ResponseEntity.ok(cp);
	}
	
	@PostMapping("/safeDayPercentage")
	public ResponseEntity<?> getSafeDayPercentage(@RequestBody String selectedProperties) {

		List<CategoryPercentage> cp = dashDao.feeSafeDayPercentage(selectedProperties);

		return ResponseEntity.ok(cp);
	}
	
	@PostMapping("/safeNightPercentage")
	public ResponseEntity<?> getSafeNightPercentage(@RequestBody String selectedProperties) {

		List<CategoryPercentage> cp = dashDao.feeSafeNightPercentage(selectedProperties);

		return ResponseEntity.ok(cp);
	}
	
	@PostMapping("/intResCouncilPercentage")
	public ResponseEntity<?> getIntResCouncilPercentage(@RequestBody String selectedProperties) {

		List<CategoryPercentage> cp = dashDao.getInterestResCouncil(selectedProperties);

		return ResponseEntity.ok(cp);
	}
	
	@PostMapping("/occupancyLengthPercentage")
	public ResponseEntity<?> getOccLengthPercentage(@RequestBody String selectedProperties) {

		List<CategoryPercentage> cp = dashDao.occupancyLengthPercentage(selectedProperties);

		return ResponseEntity.ok(cp);
	}
	
	@PostMapping("/hohTypePercentage")
	public ResponseEntity<?> getHohTypePercentage(@RequestBody String selectedProperties) {

		List<CategoryPercentage> cp = dashDao.getHouseholdType(selectedProperties);

		return ResponseEntity.ok(cp);
	}

	@PostMapping("/getHistoricalAssessmentByResidentIdAndLifeDomain")
	public ResponseEntity<?> getHistoricalAssessmentByResidentIdAndLifeDomain(
			@RequestParam("residentId") String residentId, @RequestParam("onThisDate") String onThisDate,
			@RequestParam String lifeDomain) {

		if (!StringUtils.isEmpty(residentId) && !StringUtils.isEmpty(onThisDate) && !StringUtils.isEmpty(lifeDomain) && !StringUtils.equals("NewAssessment", onThisDate)) {
			List<ResidentAssessmentQuestionnaire> historicalAssessmentByResidentIdAndLifeDomain = residentService
					.getHistoricalAssessmentByResidentIdAndLifeDomain(Long.valueOf(residentId), onThisDate, lifeDomain);
								
			AssessmentByDateAndScoreGoal assessmentAndScoreGoal = new AssessmentByDateAndScoreGoal();
			
			assessmentAndScoreGoal.setRaqs(historicalAssessmentByResidentIdAndLifeDomain);
			assessmentAndScoreGoal.setRsg(residentService.getResidentScoreGoalByDate(Long.valueOf(residentId), onThisDate, lifeDomain));
			
			return ResponseEntity.ok(assessmentAndScoreGoal);
		}
		return ResponseEntity.ok("");
	}
	
	@PostMapping("/getOnboardingStepStatus")
	public ResponseEntity<?> getOnboardingStepStatus(@RequestParam("residentId") Long residentId) {

		WizardStepCounter wsc = new WizardStepCounter();
		
		wsc.setReferralFormComplete(residentService.isReferralFormComplete(residentId));
		wsc.setSignUpComplete(residentService.isIntakeComplete(residentId));
		wsc.setHousingComplete(residentService.isHousingComplete(residentId));
		wsc.setMoneyMgmtComplete(residentService.isMoneyMgmtComplete(residentId));
		wsc.setEmploymentComplete(residentService.isEmploymentComplete(residentId));
		wsc.setEducationComplete(residentService.isEducationComplete(residentId));
		wsc.setNetSuppComplete(residentService.isNetSuppComplete(residentId));
		wsc.setHouseholdComplete(residentService.isHouseholdComplete(residentId));
		wsc.setDisPhysicalComplete(residentService.isDisPhysicalComplete(residentId));
		wsc.setActionPlanComplete(residentService.isActionPlanComplete(residentId));
		wsc.setContactNotesComplete(residentService.isContactNotesComplete(residentId));

		return ResponseEntity.ok(wsc);
	}

	@PostMapping("/getAllLatestScoreGoal")
	public ResponseEntity<?> getAllLatestScoreGoal(@RequestParam("residentId") String residentId){
		
		Map<String, String> scoreGoalPerLifeDomain = new HashMap<String, String>();		
		
		if (!StringUtils.isEmpty(residentId)){
			String latestScoreGoal = residentService.getLatestScoreGoal(Long.valueOf(residentId),"HOUSING");
			
			if(!StringUtils.isEmpty(latestScoreGoal)) {
				scoreGoalPerLifeDomain.put("HOUSING", latestScoreGoal);
			}
			
			latestScoreGoal = residentService.getLatestScoreGoal(Long.valueOf(residentId),"MONEY MANAGEMENT");			
			if(!StringUtils.isEmpty(latestScoreGoal)) {
				scoreGoalPerLifeDomain.put("MONEY MANAGEMENT", latestScoreGoal);
			}
			
			latestScoreGoal = residentService.getLatestScoreGoal(Long.valueOf(residentId),"EMPLOYMENT");			
			if(!StringUtils.isEmpty(latestScoreGoal)) {
				scoreGoalPerLifeDomain.put("EMPLOYMENT", latestScoreGoal);
			}
			
			latestScoreGoal = residentService.getLatestScoreGoal(Long.valueOf(residentId),"EDUCATION");			
			if(!StringUtils.isEmpty(latestScoreGoal)) {
				scoreGoalPerLifeDomain.put("EDUCATION", latestScoreGoal);
			}
			
			latestScoreGoal = residentService.getLatestScoreGoal(Long.valueOf(residentId),"NETWORK SUPPORT");			
			if(!StringUtils.isEmpty(latestScoreGoal)) {
				scoreGoalPerLifeDomain.put("NETWORK SUPPORT", latestScoreGoal);
			}
			
			latestScoreGoal = residentService.getLatestScoreGoal(Long.valueOf(residentId),"HOUSEHOLD MANAGEMENT");			
			if(!StringUtils.isEmpty(latestScoreGoal)) {
				scoreGoalPerLifeDomain.put("HOUSEHOLD MANAGEMENT", latestScoreGoal);
			}
			
			latestScoreGoal = residentService.getLatestScoreGoal(Long.valueOf(residentId),"DISABILITY AND PHYSICAL HEALTH");			
			if(!StringUtils.isEmpty(latestScoreGoal)) {
				scoreGoalPerLifeDomain.put("DISABILITY AND PHYSICAL HEALTH", latestScoreGoal);
			}
			
		}
		
		return ResponseEntity.ok(scoreGoalPerLifeDomain);
	}

	@RequestMapping(value = "/pullDashboard", method = RequestMethod.POST)
	public ResponseEntity<?> handleUserRequest(RequestEntity<Dashboard> requestEntity) {

		Dashboard dashboard = requestEntity.getBody();

		dashboard = residentService.pullDashboard(dashboard);

		return ResponseEntity.ok(dashboard);

	}
			

}
