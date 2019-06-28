/**
 * 
 */
package com.ffg.rrn.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ffg.rrn.dao.ActionPlanDAO;
import com.ffg.rrn.dao.CaseNotesDAO;
import com.ffg.rrn.dao.DashboardDao;
import com.ffg.rrn.dao.ReferralFormDAO;
import com.ffg.rrn.dao.ResidentDAO;
import com.ffg.rrn.model.AssessmentQuestionnaire;
import com.ffg.rrn.model.AssessmentType;
import com.ffg.rrn.model.Choice;
import com.ffg.rrn.model.Dashboard;
import com.ffg.rrn.model.Property;
import com.ffg.rrn.model.QuestionChoice;
import com.ffg.rrn.model.Referral;
import com.ffg.rrn.model.Resident;
import com.ffg.rrn.model.ResidentAssessmentQuestionnaire;
import com.ffg.rrn.model.ResidentScoreGoal;

/**
 * @author FFGRRNTeam
 *
 */
@Service
public class ResidentServiceImpl {

	@Autowired
	private ResidentDAO residentDao;

	@Autowired
	private ActionPlanDAO actionPlanDao;

	@Autowired
	private CaseNotesDAO caseNotesDao;

	@Autowired
	private ReferralFormDAO referralFormDao;

	@Autowired
	private DashboardDao dashboardDao;

	/**
	 * Get all Assessment Questions as ref data to display on Page for Resident.
	 * 
	 * @param resident
	 * @return
	 */
	public Resident getAllQuestionnaire(Resident resident) {

		List<AssessmentQuestionnaire> allQuestionnaireRef = this.residentDao.getAllQuestionnaire();
		List<QuestionChoice> choicesPerQuestionRef = this.residentDao.getChoicesPerQuestion();
		List<Choice> choicesRef = this.residentDao.getChoices();

		List<ResidentAssessmentQuestionnaire> housingQuestionnaire = new ArrayList<ResidentAssessmentQuestionnaire>();
		List<ResidentAssessmentQuestionnaire> moneyMgmtQuestionnaire = new ArrayList<ResidentAssessmentQuestionnaire>();
		List<ResidentAssessmentQuestionnaire> employmentQuestionnaire = new ArrayList<ResidentAssessmentQuestionnaire>();
		List<ResidentAssessmentQuestionnaire> educationQuestionnaire = new ArrayList<ResidentAssessmentQuestionnaire>();
		List<ResidentAssessmentQuestionnaire> netSupportQuestionnaire = new ArrayList<ResidentAssessmentQuestionnaire>();
		List<ResidentAssessmentQuestionnaire> householdMgmtQuestionnaire = new ArrayList<ResidentAssessmentQuestionnaire>();

		for (AssessmentQuestionnaire question : allQuestionnaireRef) {

			ResidentAssessmentQuestionnaire raq = new ResidentAssessmentQuestionnaire();
			List<Choice> choices = new ArrayList<Choice>();

			for (QuestionChoice questionChoice : choicesPerQuestionRef) {

				Choice c = new Choice();
				if (question.getQuestionId() == questionChoice.getQuestionId()) {

					for (Choice choice : choicesRef) {

						if (questionChoice.getChoiceId() == choice.getChoiceId()) {

							c.setChoiceId(questionChoice.getChoiceId());
							c.setChoice(choice.getChoice());
							choices.add(c);
						}
					}
					raq.setChoices(choices);
				}
			}
			raq.setResidentId(resident.getResidentId());
			raq.setQuestionId(question.getQuestionId());
			raq.setQuestionNumber(question.getQuestionNumber());
			raq.setQuestion(question.getQuestion());

			switch (question.getLifeDomain()) {
			case "HOUSING":
				raq.setLifeDomain(question.getLifeDomain());
				housingQuestionnaire.add(raq);
				break;
			case "MONEY MANAGEMENT":
				raq.setLifeDomain(question.getLifeDomain());
				moneyMgmtQuestionnaire.add(raq);
				break;
			case "EMPLOYMENT":
				raq.setLifeDomain(question.getLifeDomain());
				employmentQuestionnaire.add(raq);
				break;
			case "EDUCATION":
				raq.setLifeDomain(question.getLifeDomain());
				educationQuestionnaire.add(raq);
				break;
			case "NETWORK SUPPORT":
				raq.setLifeDomain(question.getLifeDomain());
				netSupportQuestionnaire.add(raq);
				break;
			case "HOUSEHOLD MANAGEMENT":
				raq.setLifeDomain(question.getLifeDomain());
				householdMgmtQuestionnaire.add(raq);
				break;
			}
		}

		resident.setHousingQuestionnaire(housingQuestionnaire);
		resident.setMoneyMgmtQuestionnaire(moneyMgmtQuestionnaire);
		resident.setEmploymentQuestionnaire(employmentQuestionnaire);
		resident.setEducationQuestionnaire(educationQuestionnaire);
		resident.setNetSupportQuestionnaire(netSupportQuestionnaire);
		resident.setHouseholdMgmtQuestionnaire(householdMgmtQuestionnaire);

		return resident;
	}

	public List<Property> getAllProperty(String serviceCoord) {
		return this.residentDao.getAllProperty(serviceCoord);
	}

	public void updateResidentStatus(final Resident resident) {
		residentDao.updateResidentStatus(resident);
	}

	public Long saveResident(final Resident resident) {
		return residentDao.saveResident(resident);
	}

	public int saveAssessment(final Resident resident) {
		return residentDao.saveAssessment(resident);
	}

	public List<Referral> getAllReferral() {
		return this.residentDao.getAllReferral();
	}

	public List<AssessmentType> getAllAType() {
		return this.residentDao.getAllAType();
	}

	public List<Resident> getAllResident() {
		return this.residentDao.getAllResident();
	}

	public Resident getResidentById(Long residentId, String serviceCoord, String onThisDate, String stepName) throws Exception {
		return this.residentDao.getResidentById(residentId, serviceCoord, onThisDate, stepName);
	}

	public long saveResidentAssessmentQuestionnaire(
			final ResidentAssessmentQuestionnaire residentAssessmentQuestionnaire, String lifeDomain) {
		return residentDao.saveResidentAssessmentQuestionnaire(residentAssessmentQuestionnaire, lifeDomain);
	}

	public long saveResidentScoreGoal(final Resident resident, String lifeDomain) {
		return residentDao.saveResidentScoreGoal(resident, lifeDomain);
	}

	public List<ResidentAssessmentQuestionnaire> getHistoricalAssessmentByResidentIdAndLifeDomain(Long residentId,
			String onThisDate, String lifeDomain) {
		return residentDao.getHistoricalAssessmentByResidentIdAndLifeDomain(residentId, onThisDate, lifeDomain);
	}

	public int updateResidentAssessmentQuestionnaire(String selectedDate, ResidentAssessmentQuestionnaire raqs,
			String lifeDomain) {		

		return residentDao.updateResidentAssessmentQuestionnaire(selectedDate, raqs, lifeDomain);
	}

	public int updateResidentScoreGoal(@Valid Resident resident, String lifeDomain) throws SQLException {
		return residentDao.updateResidentScoreGoal(resident, lifeDomain);
	}

	public String getLatestScoreGoal(Long residentId, String lifeDomain) {
		return residentDao.getLatestScoreGoal(residentId, lifeDomain);
		
	}

	public long saveActionPlan(Resident resident) throws DataAccessException, ParseException {
		return actionPlanDao.saveActionPlan(resident);
	}
	
	public long updateActionPlan(Resident resident) throws DataAccessException, ParseException {
		return actionPlanDao.updateActionPlan(resident);
	}

	public String getMostRecentSSMDate(Long residentId) {
		return residentDao.getMostRecentSSMDate(residentId);

	}
	
	public List<ResidentScoreGoal> getResidentScoreGoal(Long residentId) {
		return residentDao.getResidentScoreGoal(residentId);
	}

	public long saveCaseNotes(@Valid Resident resident) {
		return caseNotesDao.saveCaseNotes(resident);

	}

	public long saveReferralForm(@Valid Resident resident) {
		return referralFormDao.saveReferralForm(resident);

	}

	public Boolean isReferralFormComplete(Long residentId) {
		return referralFormDao.isReferralFormComplete(residentId);
	}

	public Boolean isIntakeComplete(Long residentId) {
		return referralFormDao.isIntakeComplete(residentId);
	}

	public Boolean isActionPlanComplete(Long residentId) {
		return referralFormDao.isActionPlanComplete(residentId);
	}

	public Boolean isContactNotesComplete(Long residentId) {
		return referralFormDao.isContactNotesComplete(residentId);
	}

	public Boolean isHousingComplete(Long residentId) {
		return referralFormDao.isHousingComplete(residentId);
	}

	public Boolean isMoneyMgmtComplete(Long residentId) {
		return referralFormDao.isMoneyMgmtComplete(residentId);
	}

	public Boolean isEmploymentComplete(Long residentId) {
		return referralFormDao.isEmploymentComplete(residentId);
	}

	public Boolean isEducationComplete(Long residentId) {
		return referralFormDao.isEducationComplete(residentId);
	}

	public Boolean isNetSuppComplete(Long residentId) {
		return referralFormDao.isNetSuppComplete(residentId);
	}

	public Boolean isHouseholdComplete(Long residentId) {
		return referralFormDao.isHouseholdComplete(residentId);
	}

	public Dashboard pullDashboard(Dashboard dashboard) {
		return dashboardDao.pullDashboard(dashboard);
	}

	public long updateCaseNotes(@Valid Resident resident) {
		return caseNotesDao.updateCaseNotes(resident);

	}

	public List<String> getAllReferralPartners() {
		return residentDao.getAllReferralPartners();
	}

	}
