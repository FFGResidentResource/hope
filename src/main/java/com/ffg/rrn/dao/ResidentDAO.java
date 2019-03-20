/**
 * 
 */
package com.ffg.rrn.dao;

import com.ffg.rrn.mapper.*;
import com.ffg.rrn.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author FFGRRNTeam
 *
 */
@Repository
@Transactional
public class ResidentDAO extends JdbcDaoSupport {
	
	private final static String INSERTION_SQL_RESIDENT="INSERT INTO RESIDENT (RESIDENT_ID, ACTIVE, FIRST_NAME, MIDDLE, LAST_NAME, PROP_ID, " +
			"VOICEMAIL_NO, TEXT_NO, EMAIL, ADDRESS, ACK_PR, ALLOW_CONTACT, WANTS_SURVEY, PHOTO_RELEASE, SERVICE_COORD," +
			" REF_TYPE, VIA_VOICEMAIL, VIA_TEXT, VIA_EMAIL) VALUES (nextval('RESIDENT_SQ'), true, "+
			" ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private final static String UPDATE_SQL_RESIDENT="UPDATE RESIDENT SET FIRST_NAME=?, MIDDLE=?, LAST_NAME=?, PROP_ID=?, " +
			"VOICEMAIL_NO=?, TEXT_NO=?, EMAIL=?, ADDRESS=?, ACK_PR=?, ALLOW_CONTACT=?, WANTS_SURVEY=?, PHOTO_RELEASE=?, SERVICE_COORD=?," +
			" REF_TYPE=?, VIA_VOICEMAIL=?, VIA_TEXT=?, VIA_EMAIL=? WHERE RESIDENT_ID=?";
	private final static String INSERTION_SQL_CHILD="INSERT INTO CHILD (CHILD_ID, FULL_NAME, PARENT_ID, PVR_FLAG) VALUES (nextval('CHILD_SQ'), ?,?,?)";
	private final static String DELETION_SQL_CHILD="DELETE FROM CHILD WHERE PARENT_ID=?";

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
		if (residentId==null || residentId==0) {
			residentId = insertNewResident(resident);
		} else {
			residentId = updateExistingResident(resident);
		}

		return residentId;
	}

	private long updateExistingResident(Resident resident) {
		//do we need to retrieve the record first and then update fields with new values?
		this.getJdbcTemplate().update(conn ->buildUpdateResidentPreparedStatement(conn,resident));

		deleteAllChildrenByResidentId(resident.getResidentId());
		insertNewChildren(resident);

		return resident.getResidentId();
	}

	private long insertNewResident(Resident resident) {
		final KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] pkColumnNames = new String[] {"resident_id"};
		this.getJdbcTemplate().update(conn ->buildInsertResidentPreparedStatement(conn,resident, pkColumnNames), keyHolder);

		long newResidentId = keyHolder.getKey().longValue();
		resident.setResidentId(newResidentId);
		if (newResidentId > 0) {
			insertNewChildren(resident);
		}

		return newResidentId;
	}

	private void insertNewChildren(Resident resident) {
		createOneChild(resident.getChild1(),resident.getResidentId(), resident.getPvrChild1());
		createOneChild(resident.getChild2(),resident.getResidentId(), resident.getPvrChild2());
		createOneChild(resident.getChild3(),resident.getResidentId(), resident.getPvrChild3());
		createOneChild(resident.getChild4(),resident.getResidentId(), resident.getPvrChild4());
		createOneChild(resident.getChild5(),resident.getResidentId(), resident.getPvrChild5());
		createOneChild(resident.getChild6(),resident.getResidentId(), resident.getPvrChild6());
		createOneChild(resident.getChild7(),resident.getResidentId(), resident.getPvrChild7());
		createOneChild(resident.getChild8(),resident.getResidentId(), resident.getPvrChild8());
	}

	private void createOneChild(String childName, long residentId, Boolean pvrChild) {
		if (StringUtils.isEmpty(childName)) {
			return;
		}
		this.getJdbcTemplate().update(INSERTION_SQL_CHILD, childName, residentId, pvrChild);
	}

	private void deleteAllChildrenByResidentId(long residentId){
		this.getJdbcTemplate().update(DELETION_SQL_CHILD,residentId);
	}
	private PreparedStatement buildInsertResidentPreparedStatement(Connection connection,
																   Resident resident, String[] pkColumnNames) throws SQLException {
		PreparedStatement ps = connection
				.prepareStatement(INSERTION_SQL_RESIDENT, pkColumnNames);
		ps.setString(1, resident.getFirstName());
		ps.setString(2, resident.getMiddle());
		ps.setString(3, resident.getLastName());
		ps.setInt(4, resident.getPropertyId());
		ps.setString(5, resident.getVoiceMail());
		ps.setString(6, resident.getText());
		ps.setString(7, resident.getEmail());
		ps.setString(8, resident.getAddress());
		ps.setBoolean(9, resident.getAckRightToPrivacy());
		ps.setBoolean(10, resident.getAllowContact());
		ps.setBoolean(11, resident.getWantSurvey());
		ps.setBoolean(12, resident.getPhotoRelease());
		ps.setString(13, resident.getServiceCoord());
		ps.setInt(14, resident.getRefId());
		ps.setBoolean(15, resident.getViaVoicemail());
		ps.setBoolean(16, resident.getViaText());
		ps.setBoolean(17, resident.getViaEmail());
		return ps;
	}

	private PreparedStatement buildUpdateResidentPreparedStatement(Connection connection,
																   Resident resident) throws SQLException {
		PreparedStatement ps = connection
				.prepareStatement(UPDATE_SQL_RESIDENT);
		ps.setString(1, resident.getFirstName());
		ps.setString(2, resident.getMiddle());
		ps.setString(3, resident.getLastName());
		ps.setInt(4, resident.getPropertyId());
		ps.setString(5, resident.getVoiceMail());
		ps.setString(6, resident.getText());
		ps.setString(7, resident.getEmail());
		ps.setString(8, resident.getAddress());
		ps.setBoolean(9, resident.getAckRightToPrivacy());
		ps.setBoolean(10, resident.getAllowContact());
		ps.setBoolean(11, resident.getWantSurvey());
		ps.setBoolean(12, resident.getPhotoRelease());
		ps.setString(13, resident.getServiceCoord());
		ps.setInt(14, resident.getRefId());
		ps.setBoolean(15, resident.getViaVoicemail());
		ps.setBoolean(16, resident.getViaText());
		ps.setBoolean(17, resident.getViaEmail());
		ps.setLong(18, resident.getResidentId());
		return ps;
	}
}
