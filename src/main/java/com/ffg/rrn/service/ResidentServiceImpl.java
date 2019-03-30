/**
 * 
 */
package com.ffg.rrn.service;

import java.util.ArrayList;
import java.util.List;

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
			raq.setQuestionNumber(question.getQuestionNumber());
			raq.setQuestion(question.getQuestion());

			if (StringUtils.equals(question.getLifeDomain(), "HOUSING")) {
				raq.setLifeDomain(question.getLifeDomain());
				housingQuestionnaire.add(raq);
			}

			if (StringUtils.equals(question.getLifeDomain(), "MONEY MANAGEMENT")) {
				raq.setLifeDomain(question.getLifeDomain());
				moneyMgmtQuestionnaire.add(raq);
			}

			if (StringUtils.equals(question.getLifeDomain(), "EMPLOYMENT")) {
				raq.setLifeDomain(question.getLifeDomain());
				employmentQuestionnaire.add(raq);
			}

			if (StringUtils.equals(question.getLifeDomain(), "EDUCATION")) {
				raq.setLifeDomain(question.getLifeDomain());
				educationQuestionnaire.add(raq);
			}

			if (StringUtils.equals(question.getLifeDomain(), "NETWORK SUPPORT")) {
				raq.setLifeDomain(question.getLifeDomain());
				netSupportQuestionnaire.add(raq);
			}

			if (StringUtils.equals(question.getLifeDomain(), "HOUSEHOLD MANAGEMENT")) {
				raq.setLifeDomain(question.getLifeDomain());
				householdMgmtQuestionnaire.add(raq);
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

}
