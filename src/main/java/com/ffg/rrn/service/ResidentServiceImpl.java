/**
 * 
 */
package com.ffg.rrn.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.ffg.rrn.dao.ResidentDAO;
import com.ffg.rrn.model.AssessmentQuestionnaire;
import com.ffg.rrn.model.AssessmentType;
import com.ffg.rrn.model.Choice;
import com.ffg.rrn.model.Property;
import com.ffg.rrn.model.QuestionChoice;
import com.ffg.rrn.model.Referral;
import com.ffg.rrn.model.Resident;
import com.ffg.rrn.model.ResidentAssessmentQuestionnaire;

/**
 * @author FFGRRNTeam
 *
 */
@Service
public class ResidentServiceImpl {

	@Autowired
	private ResidentDAO residentDao;

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

	public List<Property> getAllProperty() {
		return this.residentDao.getAllProperty();
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

	public Resident getResidentById(Long residentId, String serviceCoord) throws Exception {
		return this.residentDao.getResidentById(residentId, serviceCoord);
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
	

}
