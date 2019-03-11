/**
 * 
 */
package com.ffg.rrn.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ffg.rrn.mapper.AssessmentMapper;
import com.ffg.rrn.mapper.ChildrenMapper;
import com.ffg.rrn.mapper.PropertyMapper;
import com.ffg.rrn.mapper.ReferralMapper;
import com.ffg.rrn.mapper.ResidentMapper;
import com.ffg.rrn.model.AssessmentQuestionnaire;
import com.ffg.rrn.model.AssessmentType;
import com.ffg.rrn.model.Child;
import com.ffg.rrn.model.Choice;
import com.ffg.rrn.model.Property;
import com.ffg.rrn.model.QuestionChoice;
import com.ffg.rrn.model.Referral;
import com.ffg.rrn.model.Resident;
import com.ffg.rrn.model.WizardStepCounter;

/**
 * @author FFGRRNTeam
 *
 */
@Repository
@Transactional
public class ResidentDAO extends JdbcDaoSupport {

	@Autowired
	public ResidentDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	public List<AssessmentQuestionnaire> getAllQuestionnaire() {
		List<AssessmentQuestionnaire> aqList = this.getJdbcTemplate().query(
				"SELECT * FROM ASSESSMENT_QUESTIONNAIRE order by sort",
				new BeanPropertyRowMapper(AssessmentQuestionnaire.class));
		return aqList;
	}

	public List<QuestionChoice> getChoicesPerQuestion() {
		List<QuestionChoice> quesChoiceList = this.getJdbcTemplate().query("SELECT * FROM QUESTION_CHOICE",
				new BeanPropertyRowMapper(QuestionChoice.class));
		return quesChoiceList;
	}
	
	public List<Choice> getChoices() {
		List<Choice> choiceList = this.getJdbcTemplate().query("SELECT * FROM CHOICE",
				new BeanPropertyRowMapper(Choice.class));
		return choiceList;
	}

	/**
	 * 
	 * @return
	 */
	public List<Property> getAllProperty() {
		PropertyMapper rowMapper = new PropertyMapper();
		return this.getJdbcTemplate().query(PropertyMapper.PROPERTY_SQL, rowMapper);
	}

	public List<AssessmentType> getAllAType() {
		AssessmentMapper rowMapper = new AssessmentMapper();
		return this.getJdbcTemplate().query(AssessmentMapper.A_SQL, rowMapper);
	}

	public List<Referral> getAllReferral() {
		ReferralMapper rowMapper = new ReferralMapper();
		return this.getJdbcTemplate().query(ReferralMapper.REF_SQL, rowMapper);
	}

	public List<Resident> getAllResident() {
		ResidentMapper rowMapper = new ResidentMapper();
		return this.getJdbcTemplate().query(ResidentMapper.RESIDENT_SQL, rowMapper);
	}

	public Resident getResidentByEmail(String email, String serviceCoord) {

		ResidentMapper rowMapper = new ResidentMapper();
		Resident resident = new Resident();

		resident = this.getJdbcTemplate().queryForObject(ResidentMapper.RESIDENT_SQL + " where r.email = ? ",
				new Object[] { email }, rowMapper);

		resident.setPropertyList(this.getAllProperty());
		resident.setRefList(this.getAllReferral());
		resident.setAtList(this.getAllAType());

		return resident;
	}

	public Resident getResidentById(Long residentId, String serviceCoord) throws Exception {

		ResidentMapper rowMapper = new ResidentMapper();
		ChildrenMapper childMapper = new ChildrenMapper();
		Resident resident = new Resident();

		try {
			resident = this.getJdbcTemplate().queryForObject(ResidentMapper.RESIDENT_SQL + " where r.resident_id = ? ",
					new Object[] { residentId }, rowMapper);

			WizardStepCounter wsCounter = new WizardStepCounter();

			if (!StringUtils.isEmpty(resident.getFirstName()) && !StringUtils.isEmpty(resident.getLastName())
					&& resident.getRefId() != null && resident.getAckRightToPrivacy()
					&& resident.getPropertyId() != null
					&& ((resident.getAllowContact() && (!StringUtils.isEmpty(resident.getEmail())
							|| !StringUtils.isEmpty(resident.getVoiceMail())
							|| !StringUtils.isEmpty(resident.getText()))) || (resident.getAllowContact() == false))) {
				wsCounter.setSignUpComplete(true);
			}

			if (resident.getAId() != null && resident.getAId() > 0L ) {
				wsCounter.setAssessmentComplete(true);
			}

			List<Child> children = this.getJdbcTemplate().query(ChildrenMapper.CHILDREN_SQL_BY_RESIDENT_ID,
					new Object[] { residentId }, childMapper);
			if (!CollectionUtils.isEmpty(children)) {

				for (Child child : children) {
					if (StringUtils.isEmpty(resident.getChild1())) {
						resident.setChild1(child.getFullName());
						resident.setPvrChild1(child.getPvrFlag());
					} else if (StringUtils.isEmpty(resident.getChild2())) {
						resident.setChild2(child.getFullName());
						resident.setPvrChild2(child.getPvrFlag());
					} else if (StringUtils.isEmpty(resident.getChild3())) {
						resident.setChild3(child.getFullName());
						resident.setPvrChild3(child.getPvrFlag());
					} else if (StringUtils.isEmpty(resident.getChild4())) {
						resident.setChild4(child.getFullName());
						resident.setPvrChild4(child.getPvrFlag());
					} else if (StringUtils.isEmpty(resident.getChild5())) {
						resident.setChild5(child.getFullName());
						resident.setPvrChild5(child.getPvrFlag());
					} else if (StringUtils.isEmpty(resident.getChild6())) {
						resident.setChild6(child.getFullName());
						resident.setPvrChild6(child.getPvrFlag());
					} else if (StringUtils.isEmpty(resident.getChild7())) {
						resident.setChild7(child.getFullName());
						resident.setPvrChild7(child.getPvrFlag());
					} else if (StringUtils.isEmpty(resident.getChild8())) {
						resident.setChild8(child.getFullName());
						resident.setPvrChild8(child.getPvrFlag());
					}
				}

			}

			resident.setWsCounter(wsCounter);

		} catch (EmptyResultDataAccessException ex) {
			// When No resident found - Page will open for NewResident
			Resident r = new Resident(this.getAllProperty(), this.getAllAType(), this.getAllReferral(), serviceCoord);
			WizardStepCounter wsCounter = new WizardStepCounter();
			r.setWsCounter(wsCounter);
			return r;
		}

		resident.setPropertyList(this.getAllProperty());
		resident.setRefList(this.getAllReferral());
		resident.setAtList(this.getAllAType());

		return resident;
	}

	/**
	 * 
	 * @param resident
	 */
	public int saveAssessment(Resident resident) {

		int count = this.getJdbcTemplate().update(
				"UPDATE RESIDENT SET A_TYPE = ?, A_DATE = NOW() where RESIDENT_ID = ?", resident.getAId(),
				resident.getResidentId());

		return count;
	}

	/**
	 * 
	 * @param resident
	 */
	public Long saveResident(Resident resident) {

		Long residentId = resident.getResidentId();

		// Logic on when to insert vs update existing Resident
		if (null == residentId) {
			residentId = insertNewResident(resident);
		} else {
			residentId = updateExistingResident(resident);
		}

		return residentId;
	}

	private long updateExistingResident(Resident resident) {
		// TODO - Modify Resident use case is all pending
		return -1;
	}

	private long insertNewResident(Resident resident) {
		int count = this.getJdbcTemplate().update(
				"INSERT INTO RESIDENT (RESIDENT_ID, ACTIVE, FIRST_NAME, MIDDLE, LAST_NAME, PROP_ID, VOICEMAIL_NO, TEXT_NO, EMAIL, ADDRESS, ACK_PR, ALLOW_CONTACT, WANTS_SURVEY, PHOTO_RELEASE, SERVICE_COORD, REF_TYPE, VIA_VOICEMAIL, VIA_TEXT, VIA_EMAIL) VALUES (nextval('RESIDENT_SQ'), true, '"
						+ resident.getFirstName() + "','" + resident.getMiddle() + "','" + resident.getLastName() + "',"
						+ resident.getPropertyId() + ",'" + resident.getVoiceMail() + "','" + resident.getText() + "','"
						+ resident.getEmail() + "','" + resident.getAddress() + "'," + resident.getAckRightToPrivacy()
						+ "," + resident.getAllowContact() + "," + resident.getWantSurvey() + ","
						+ resident.getPhotoRelease() + ",'" + resident.getServiceCoord() + "'," + resident.getRefId()
						+ "," + resident.getViaVoicemail() + "," + resident.getViaText() + "," + resident.getViaEmail()
						+ ")");

		long residentId = 0l;

		if (count > 0) {

			residentId = (this.getResidentByEmail(resident.getEmail(), resident.getServiceCoord())).getResidentId();

			if (!StringUtils.isEmpty(resident.getChild1())) {
				this.getJdbcTemplate().update(
						"INSERT INTO CHILD (CHILD_ID, FULL_NAME, PARENT_ID, PVR_FLAG) VALUES (nextval('CHILD_SQ'),'"
								+ resident.getChild1() + "', currval('RESIDENT_SQ') ," + resident.getPvrChild1()
								+ " )");
			}
			if (!StringUtils.isEmpty(resident.getChild2())) {
				this.getJdbcTemplate().update(
						"INSERT INTO CHILD (CHILD_ID, FULL_NAME, PARENT_ID, PVR_FLAG) VALUES (nextval('CHILD_SQ'),'"
								+ resident.getChild2() + "', currval('RESIDENT_SQ') ," + resident.getPvrChild2()
								+ " )");
			}
			if (!StringUtils.isEmpty(resident.getChild3())) {
				this.getJdbcTemplate().update(
						"INSERT INTO CHILD (CHILD_ID, FULL_NAME, PARENT_ID, PVR_FLAG) VALUES (nextval('CHILD_SQ'),'"
								+ resident.getChild3() + "', currval('RESIDENT_SQ') ," + resident.getPvrChild3()
								+ " )");
			}
			if (!StringUtils.isEmpty(resident.getChild4())) {
				this.getJdbcTemplate().update(
						"INSERT INTO CHILD (CHILD_ID, FULL_NAME, PARENT_ID, PVR_FLAG) VALUES (nextval('CHILD_SQ'),'"
								+ resident.getChild4() + "', currval('RESIDENT_SQ') ," + resident.getPvrChild4()
								+ " )");
			}
			if (!StringUtils.isEmpty(resident.getChild5())) {
				this.getJdbcTemplate().update(
						"INSERT INTO CHILD (CHILD_ID, FULL_NAME, PARENT_ID, PVR_FLAG) VALUES (nextval('CHILD_SQ'),'"
								+ resident.getChild5() + "', currval('RESIDENT_SQ') ," + resident.getPvrChild5()
								+ " )");
			}
			if (!StringUtils.isEmpty(resident.getChild6())) {
				this.getJdbcTemplate().update(
						"INSERT INTO CHILD (CHILD_ID, FULL_NAME, PARENT_ID, PVR_FLAG) VALUES (nextval('CHILD_SQ'),'"
								+ resident.getChild6() + "', currval('RESIDENT_SQ') ," + resident.getPvrChild6()
								+ " )");
			}
			if (!StringUtils.isEmpty(resident.getChild7())) {
				this.getJdbcTemplate().update(
						"INSERT INTO CHILD (CHILD_ID, FULL_NAME, PARENT_ID, PVR_FLAG) VALUES (nextval('CHILD_SQ'),'"
								+ resident.getChild7() + "',currval('RESIDENT_SQ')," + resident.getPvrChild7() + ")");
			}
			if (!StringUtils.isEmpty(resident.getChild8())) {
				this.getJdbcTemplate().update(
						"INSERT INTO CHILD (CHILD_ID, FULL_NAME, PARENT_ID, PVR_FLAG) VALUES (nextval('CHILD_SQ'),'"
								+ resident.getChild8() + "', currval('RESIDENT_SQ')," + resident.getPvrChild8() + ")");
			}

		}

		return residentId;

	}

}
