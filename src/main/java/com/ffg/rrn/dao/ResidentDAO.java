/**
 * 
 */
package com.ffg.rrn.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ffg.rrn.mapper.AssessmentMapper;
import com.ffg.rrn.mapper.ChildrenMapper;
import com.ffg.rrn.mapper.PropertyMapper;
import com.ffg.rrn.mapper.ReferralMapper;
import com.ffg.rrn.mapper.ResidentMapper;
import com.ffg.rrn.model.AssessmentQuestionnaire;
import com.ffg.rrn.model.AssessmentType;
import com.ffg.rrn.model.CategoryPercentage;
import com.ffg.rrn.model.Child;
import com.ffg.rrn.model.Choice;
import com.ffg.rrn.model.Property;
import com.ffg.rrn.model.QuestionChoice;
import com.ffg.rrn.model.Referral;
import com.ffg.rrn.model.Resident;
import com.ffg.rrn.model.ResidentAssessmentQuestionnaire;
import com.ffg.rrn.model.ResidentAudit;
import com.ffg.rrn.model.ResidentScoreGoal;
import com.ffg.rrn.model.WizardStepCounter;
import com.ffg.rrn.utils.AppConstants;

/**
 * @author FFGRRNTeam
 *
 */
@Repository
@Transactional
public class ResidentDAO extends JdbcDaoSupport {

	private final static String SQL_INSERT_RESIDENT = "INSERT INTO RESIDENT (FIRST_NAME, MIDDLE, LAST_NAME, PROP_ID, ADDRESS, "
			+ "REF_TYPE, IS_RESIDENT, TEXT_NO, SERVICE_COORD) VALUES (?,?,?,?,?,?,?,?,?)";

	private final static String SQL_UPDATE_RESIDENT = "UPDATE RESIDENT SET FIRST_NAME=?, MIDDLE=?, LAST_NAME=?, PROP_ID=?, "
			+ "VOICEMAIL_NO=?, TEXT_NO=?, EMAIL=?, ADDRESS=?, ACK_PR=?, ALLOW_CONTACT=?, WANTS_SURVEY=?, PHOTO_RELEASE=?, SERVICE_COORD=?,"
			+ " REF_TYPE=?, VIA_VOICEMAIL=?, VIA_TEXT=?, VIA_EMAIL=? ,DATE_MODIFIED=?,MODIFIED_BY=?,IS_RESIDENT=?, "
			+ " AGE=?, PRI_LANGUAGE=?, MARITAL_STATUS=?, ANNUAL_GROSS=?, GENDER=?, ETHNICITY=?, RACE=?, H_O_H=?, VETERAN=?, DISABILITY=?, "
			+ " RC_OR_EX_OFF=?, SSI=?, SSDI=?, HEALTH_COVERAGE=?, HIGHEST_EDU=?, SAFE_DAY=?, SAFE_NIGHT=?, "
			+ " OCCUPANCY_LENGTH= ? , INT_RES_COUNCIL = ?, MODE_TRANSPORT = ?, EXP_FOOD_SHORT = ?, INTERNET_ACCESS = ?, HOH_TYPE = ?, UNEMP_REASON = ?, BARRIER_TO_EDU = ?, HEALTH_CONDITION = ?, PROGRAM_SRVC_YOUTH = ?, PROGRAM_SRVC_ADULT = ?  WHERE RESIDENT_ID=?";

	private final static String SQL_CHANGE_STATUS_OF_RESIDENT = "UPDATE RESIDENT SET ACTIVE=?, DATE_MODIFIED=?, MODIFIED_BY=? "
			+ " WHERE RESIDENT_ID=?";

	private final static String SQL_INSERT_CHILD = "INSERT INTO CHILD ( FULL_NAME, PARENT_ID, PVR_FLAG) VALUES ( ?,?,?)";

	private final static String SQL_DELETE_CHILD = "DELETE FROM CHILD WHERE PARENT_ID=?";

	private final static String SQL_INSERT_RESIDENT_ASSESSMENT_QUES = "INSERT INTO RESIDENT_ASSESSMENT_QUESTIONNAIRE ( RESIDENT_ID, QUESTION_ID, CHOICE_ID, LIFE_DOMAIN, ON_THIS_DATE) "
			+ "VALUES ( ?, ?, ?, ?, ?)";

	private final static String SQL_INSERT_RESIDENT_SCORE_GOAL = "INSERT INTO RESIDENT_SCORE_GOAL ( RESIDENT_ID, LIFE_DOMAIN, SCORE, GOAL, ON_THIS_DATE) "
			+ "VALUES ( ?, ?, ?, ?, ?)";

	private final static String SQL_UPDATE_RESIDENT_ASSESSMENT_QUES = "UPDATE RESIDENT_ASSESSMENT_QUESTIONNAIRE SET RESIDENT_ID = ?, QUESTION_ID = ?, CHOICE_ID = ?, LIFE_DOMAIN = ? WHERE RESIDENT_ID = ? and LIFE_DOMAIN = ? and ON_THIS_DATE = ? AND QUESTION_ID = ?";

	private final static String SQL_UPDATE_RESIDENT_SCORE_GOAL = "UPDATE RESIDENT_SCORE_GOAL SET RESIDENT_ID = ?, LIFE_DOMAIN = ?, SCORE = ?, GOAL = ? WHERE RESIDENT_ID = ? and LIFE_DOMAIN = ? and ON_THIS_DATE = ?";

	@Autowired
	public ResidentDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	public List<AssessmentQuestionnaire> getAllQuestionnaire() {
		List<AssessmentQuestionnaire> aqList = this.getJdbcTemplate().query(
				"SELECT * FROM ASSESSMENT_QUESTIONNAIRE order by sort",
				new BeanPropertyRowMapper<AssessmentQuestionnaire>(AssessmentQuestionnaire.class));
		return aqList;
	}

	public List<QuestionChoice> getChoicesPerQuestion() {
		List<QuestionChoice> quesChoiceList = this.getJdbcTemplate().query("SELECT * FROM QUESTION_CHOICE",
				new BeanPropertyRowMapper<QuestionChoice>(QuestionChoice.class));
		return quesChoiceList;
	}

	public List<Choice> getChoices() {
		List<Choice> choiceList = this.getJdbcTemplate().query("SELECT * FROM CHOICE",
				new BeanPropertyRowMapper<Choice>(Choice.class));
		return choiceList;
	}

	/**
	 * This will display as dropdown on each LifeDomain assessment Step
	 * 
	 * @param residentId
	 * @return
	 */
	public List<String> getAssessmentDatesByResidentIdAndLifeDomain(Long residentId, String lifeDomain) {
		List<String> sortedDatesOfAssessments = (List<String>) this.getJdbcTemplate().queryForList(
				"select TO_CHAR(ON_THIS_DATE, '" + AppConstants.DATE_PATTERN_POSTGRE
						+ "') FROM RESIDENT_ASSESSMENT_QUESTIONNAIRE"
						+ " WHERE RESIDENT_ID = ? AND LIFE_DOMAIN = ?  ORDER BY ON_THIS_DATE DESC",
				new Object[] { residentId, lifeDomain }, String.class);
		if (CollectionUtils.isEmpty(sortedDatesOfAssessments)) {
			return Collections.emptyList();
		}
		return sortedDatesOfAssessments.stream().distinct().collect(Collectors.toList());
	}

	/**
	 * This will display as dropdown on Contact Notes Form
	 * 
	 * @param residentId
	 * @return
	 */
	public List<String> getContactNoteDates(Long residentId) {
		List<String> sortedDatesOfContactNotes = (List<String>) this.getJdbcTemplate().queryForList(
				"select TO_CHAR(DATE_ADDED, '" + AppConstants.DATE_PATTERN_POSTGRE + "') FROM CASE_NOTES "
						+ " WHERE RESIDENT_ID = ?  ORDER BY DATE_ADDED DESC",
				new Object[] { residentId }, String.class);
		if (CollectionUtils.isEmpty(sortedDatesOfContactNotes)) {
			return Collections.emptyList();
		}
		return sortedDatesOfContactNotes.stream().distinct().collect(Collectors.toList());
	}

	/**
	 * This will display as dropdown on Action Plan Form
	 * 
	 * @param residentId
	 * @return
	 */
	public List<String> getActionPlanDates(Long residentId) {
		List<String> sortedDatesOfActionPlan = (List<String>) this.getJdbcTemplate().queryForList(
				"select TO_CHAR(DATE_ADDED, '" + AppConstants.DATE_PATTERN_POSTGRE + "') FROM ACTION_PLAN "
						+ " WHERE RESIDENT_ID = ?  ORDER BY DATE_ADDED DESC",
				new Object[] { residentId }, String.class);
		if (CollectionUtils.isEmpty(sortedDatesOfActionPlan)) {
			return Collections.emptyList();
		}
		return sortedDatesOfActionPlan.stream().distinct().collect(Collectors.toList());
	}

	public List<ResidentAssessmentQuestionnaire> getAllAssessment(Long residentId, String onThisDate) {
		return this.getJdbcTemplate().query(
				"SELECT * FROM RESIDENT_ASSESSMENT_QUESTIONNAIRE WHERE ON_THIS_DATE = TO_DATE(?,'DD-MON-YYYY') and RESIDENT_ID = ?",
				new Object[] { onThisDate, residentId },
				new BeanPropertyRowMapper<ResidentAssessmentQuestionnaire>(ResidentAssessmentQuestionnaire.class));
	}

	public List<ResidentAssessmentQuestionnaire> getHistoricalAssessmentByResidentIdAndLifeDomain(Long residentId,
			String onThisDate, String lifeDomain) {
		return this.getJdbcTemplate().query(
				"SELECT * FROM RESIDENT_ASSESSMENT_QUESTIONNAIRE WHERE ON_THIS_DATE = TO_DATE(?,'DD-MON-YYYY') and RESIDENT_ID = ? and LIFE_DOMAIN = ?",
				new Object[] { onThisDate, residentId, lifeDomain },
				new BeanPropertyRowMapper<ResidentAssessmentQuestionnaire>(ResidentAssessmentQuestionnaire.class));
	}

	public List<ResidentScoreGoal> getResidentScoreGoal(Long residentId) {
		return this.getJdbcTemplate().query("SELECT * FROM RESIDENT_SCORE_GOAL WHERE RESIDENT_ID = ?",
				new Object[] { residentId }, new BeanPropertyRowMapper<ResidentScoreGoal>(ResidentScoreGoal.class));
	}

	public ResidentScoreGoal getResidentScoreGoalByDate(Long residentId, String onThisDate, String lifeDomain) {
		return this.getJdbcTemplate().queryForObject(
				"SELECT * FROM RESIDENT_SCORE_GOAL WHERE RESIDENT_ID = ? AND ON_THIS_DATE = TO_DATE(?,'DD-MON-YYYY') AND LIFE_DOMAIN = ? LIMIT 1 ",
				new Object[] { residentId, onThisDate, lifeDomain },
				new BeanPropertyRowMapper<ResidentScoreGoal>(ResidentScoreGoal.class));
	}

	/**
	 * 
	 * @param serviceCoord
	 * @return
	 */
	public List<Property> getAllProperty(String serviceCoord) {

		try {

			this.getJdbcTemplate().queryForObject(
					"select role_name from service_coordinator sc join user_role ur on ur.user_id = sc.sc_id "
							+ "join app_role ar on ar.role_id = ur.role_id and sc.user_name = ? and ar.role_name = 'ROLE_ADMIN'",
					new Object[] { serviceCoord }, String.class);
		} catch (EmptyResultDataAccessException ex) {

			List<Property> properties = new ArrayList<Property>();

			properties = this.getJdbcTemplate().query(PropertyMapper.PROPERTY_SQL_FOR_NON_ADMIN_SC,
					new Object[] { serviceCoord }, (rs, rowNumber) -> {
						try {
							Property p = new Property();

							p.setPropertyId(rs.getInt("PROP_ID"));
							p.setPropertyName(rs.getString("PROP_NAME"));
							p.setUnit(rs.getInt("UNIT"));
							p.setUnitFee(rs.getInt("UNIT_FEE"));
							p.setActive(rs.getBoolean("ACTIVE"));
							p.setNoOfResident(rs.getInt("TOTAL_RESIDENTS"));
							p.setResidentCouncil(rs.getBoolean("RESIDENT_COUNCIL"));

							return p;
						} catch (SQLException e) {
							throw new RuntimeException("your error message", e); // or other unchecked exception here
						}
					});

			return properties;
		}

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

		resident.setPropertyList(this.getAllProperty(serviceCoord));
		resident.setRefList(this.getAllReferral());
		resident.setAtList(this.getAllAType());

		return resident;
	}

	public Resident getResidentById(Long residentId, String serviceCoord, String onThisDate, String stepName)
			throws Exception {

		ResidentMapper rowMapper = new ResidentMapper();
		ChildrenMapper childMapper = new ChildrenMapper();
		Resident resident = new Resident();

		try {

			String dateSuffix = "";

			switch (stepName) {
			case "ActionPlan":

				dateSuffix = (StringUtils.equals("new", onThisDate) ? ""
						: " and ap.DATE_ADDED = TO_DATE('" + onThisDate + "','DD-MON-YYYY') ");

				break;
			case "ContactNotes":

				dateSuffix = (StringUtils.equals("new", onThisDate) ? ""
						: " and cn.DATE_ADDED = TO_DATE('" + onThisDate + "','DD-MON-YYYY') ");

				break;
			}

			resident = this.getJdbcTemplate().queryForObject(
					ResidentMapper.RESIDENT_SQL + " where r.resident_id = ? " + dateSuffix + " LIMIT 1",
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

			if (resident.getAId() != null && resident.getAId() > 0L) {
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
			Resident r = new Resident(this.getAllProperty(serviceCoord), this.getAllAType(), this.getAllReferral(),
					serviceCoord);
			WizardStepCounter wsCounter = new WizardStepCounter();
			r.setWsCounter(wsCounter);
			return r;
		}

		resident.setPropertyList(this.getAllProperty(serviceCoord));
		resident.setRefList(this.getAllReferral());
		resident.setAtList(this.getAllAType());

		// All Historical Dates that are populated on each Assessment Step Dropdown
		resident.setHousingDates(this.getAssessmentDatesByResidentIdAndLifeDomain(residentId, "HOUSING"));
		resident.setMoneymgmtDates(this.getAssessmentDatesByResidentIdAndLifeDomain(residentId, "MONEY MANAGEMENT"));
		resident.setEmploymentDates(this.getAssessmentDatesByResidentIdAndLifeDomain(residentId, "EMPLOYMENT"));
		resident.setEducationDates(this.getAssessmentDatesByResidentIdAndLifeDomain(residentId, "EDUCATION"));
		resident.setNetSupportDates(this.getAssessmentDatesByResidentIdAndLifeDomain(residentId, "NETWORK SUPPORT"));
		resident.setHouseholdDates(
				this.getAssessmentDatesByResidentIdAndLifeDomain(residentId, "HOUSEHOLD MANAGEMENT"));
		resident.setDisPhysicalDates(
				this.getAssessmentDatesByResidentIdAndLifeDomain(residentId, "DISABILITY AND PHYSICAL HEALTH"));

		resident.setActionPlanDates(this.getActionPlanDates(residentId));
		resident.setContactNoteDates(this.getContactNoteDates(residentId));

		// resident.setHousingScoreGoal(this.getLatestScoreGoal(residentId, "HOUSING"));
		// resident.setMoneyMgmtScoreGoal(this.getLatestScoreGoal(residentId, "MONEY
		// MANAGEMENT"));
		// resident.setEmploymentScoreGoal(this.getLatestScoreGoal(residentId,
		// "EMPLOYMENT"));
		// resident.setEducationScoreGoal(this.getLatestScoreGoal(residentId,
		// "EDUCATION"));
		// resident.setNetSupportScoreGoal(this.getLatestScoreGoal(residentId, "NETWORK
		// SUPPORT"));
		// resident.setHouseholdScoreGoal(this.getLatestScoreGoal(residentId, "HOUSEHOLD
		// MANAGEMENT"));

		return resident;
	}

	// This will display on each Thumbnail on AllResidentPage
	public String getLatestScoreGoal(Long residentId, String lifeDomain) {
		try {
			String query = this.getJdbcTemplate().queryForObject(
					"select score||' / '||goal as scoreGoal from resident_score_goal where resident_id = ? and LIFE_DOMAIN = ? order by on_this_Date desc LIMIT 1",
					new Object[] { residentId, lifeDomain }, String.class);
			return query;
		} catch (EmptyResultDataAccessException e) {
			return "-- / --";
		}
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
		if (residentId == null || residentId == 0) {
			residentId = insertNewResident(resident);
		} else {
			residentId = updateExistingResident(resident);
		}

		return residentId;
	}

	public void updateResidentStatus(Resident resident) {
		this.getJdbcTemplate().update(conn -> buildChangeStatusOfResidentPS(conn, resident));
	}

	private long updateExistingResident(Resident resident) {
		// do we need to retrieve the record first and then update fields with new
		// values?
		this.getJdbcTemplate().update(conn -> buildUpdateResidentPS(conn, resident));

		deleteAllChildrenByResidentId(resident.getResidentId());
		insertNewChildren(resident);

		return resident.getResidentId();
	}

	private long insertNewResident(Resident resident) {
		final KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] pkColumnNames = new String[] { "resident_id" };
		this.getJdbcTemplate().update(conn -> buildInsertResidentPS(conn, resident, pkColumnNames), keyHolder);

		long newResidentId = keyHolder.getKey().longValue();
		resident.setResidentId(newResidentId);
		if (newResidentId > 0) {
			insertNewChildren(resident);
		}

		return newResidentId;
	}

	private void insertNewChildren(Resident resident) {
		createOneChild(resident.getChild1(), resident.getResidentId(), resident.getPvrChild1());
		createOneChild(resident.getChild2(), resident.getResidentId(), resident.getPvrChild2());
		createOneChild(resident.getChild3(), resident.getResidentId(), resident.getPvrChild3());
		createOneChild(resident.getChild4(), resident.getResidentId(), resident.getPvrChild4());
		createOneChild(resident.getChild5(), resident.getResidentId(), resident.getPvrChild5());
		createOneChild(resident.getChild6(), resident.getResidentId(), resident.getPvrChild6());
		createOneChild(resident.getChild7(), resident.getResidentId(), resident.getPvrChild7());
		createOneChild(resident.getChild8(), resident.getResidentId(), resident.getPvrChild8());
	}

	private void createOneChild(String childName, long residentId, Boolean pvrChild) {
		if (StringUtils.isEmpty(childName)) {
			return;
		}
		this.getJdbcTemplate().update(SQL_INSERT_CHILD, StringUtils.capitalize(childName.trim().toLowerCase()),
				residentId, pvrChild);
	}

	private void deleteAllChildrenByResidentId(long residentId) {
		this.getJdbcTemplate().update(SQL_DELETE_CHILD, residentId);
	}

	private PreparedStatement buildInsertResidentPS(Connection connection, Resident resident, String[] pkColumnNames)
			throws SQLException {
		PreparedStatement ps = connection.prepareStatement(SQL_INSERT_RESIDENT, pkColumnNames);
		ps.setString(1, StringUtils.capitalize(resident.getFirstName().trim().toLowerCase()));
		ps.setString(2, StringUtils.capitalize(resident.getMiddle().trim().toLowerCase()));
		ps.setString(3, StringUtils.capitalize(resident.getLastName().trim().toLowerCase()));
		ps.setInt(4, resident.getPropertyId());
		ps.setString(5, StringUtils.capitalize(resident.getAddress().trim().toLowerCase()));
		ps.setInt(6, resident.getRefId());
		ps.setBoolean(7, resident.getIsResident());
		ps.setString(8, resident.getText());
		ps.setString(9, resident.getServiceCoord());

		return ps;
	}

	private PreparedStatement buildUpdateResidentPS(Connection connection, Resident resident) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_RESIDENT);
		ps.setString(1, StringUtils.capitalize(resident.getFirstName().trim().toLowerCase()));
		ps.setString(2, StringUtils.capitalize(resident.getMiddle().trim().toLowerCase()));
		ps.setString(3, StringUtils.capitalize(resident.getLastName().trim().toLowerCase()));
		ps.setInt(4, resident.getPropertyId());
		ps.setString(5, (StringUtils.isBlank(resident.getVoiceMail()) ? null : resident.getVoiceMail().trim()));
		ps.setString(6, (StringUtils.isBlank(resident.getText()) ? null : resident.getText().trim()));
		ps.setString(7, (StringUtils.isBlank(resident.getEmail()) ? null : resident.getEmail().trim().toLowerCase()));
		ps.setString(8, StringUtils.capitalize(resident.getAddress().trim().toLowerCase()));
		ps.setBoolean(9, resident.getAckRightToPrivacy());
		ps.setBoolean(10, resident.getAllowContact());
		ps.setBoolean(11, resident.getWantSurvey());
		ps.setBoolean(12, resident.getPhotoRelease());
		ps.setString(13, resident.getServiceCoord());
		ps.setInt(14, resident.getRefId());
		ps.setBoolean(15, resident.getViaVoicemail());
		ps.setBoolean(16, resident.getViaText());
		ps.setBoolean(17, resident.getViaEmail());
		ps.setTimestamp(18, Timestamp.valueOf(LocalDateTime.now()));// modify date
		ps.setString(19, resident.getModifiedBy());
		ps.setBoolean(20, resident.getIsResident());
		ps.setString(21, resident.getAge());
		ps.setString(22, resident.getPrimaryLanguage());
		ps.setString(23, resident.getMaritalStatus());
		ps.setString(24, resident.getAnnualGross());
		ps.setString(25, resident.getGender());
		ps.setString(26, resident.getEthnicity());
		ps.setString(27, resident.getRace());
		ps.setString(28, resident.getHouseHold());
		ps.setString(29, resident.getVeteran());
		ps.setString(30, resident.getDisabilityStatus());
		ps.setString(31, resident.getRcOrExOff());
		ps.setString(32, resident.getSsi());
		ps.setString(33, resident.getSsdi());
		ps.setString(34, resident.getHealthCoverage());
		ps.setString(35, resident.getHighestEdu());
		ps.setString(36, resident.getSafeDuringDayChoice());
		ps.setString(37, resident.getSafeDuringNightChoice());

		ps.setString(38, resident.getOccupancyLength());
		ps.setString(39, resident.getInterestInResCouncil());
		ps.setString(40, resident.getModeOfTransportation());
		ps.setString(41, resident.getExperienceFoodShortage());
		ps.setString(42, resident.getInternetAccess());
		ps.setString(43, resident.getHouseholdType());
		ps.setString(44, resident.getUnEmpReason());
		ps.setString(45, resident.getBarrierToEducation());
		ps.setString(46, resident.getHealthCondition());
		ps.setString(47, resident.getProgramSrvcYouth());
		ps.setString(48, resident.getProgramSrvcAdult());

		ps.setLong(49, resident.getResidentId()); // where ? is 44th in number in SQL_UPDATE_RESIDENT
		return ps;
	}

	private PreparedStatement buildChangeStatusOfResidentPS(Connection connection, Resident resident)
			throws SQLException {
		PreparedStatement ps = connection.prepareStatement(SQL_CHANGE_STATUS_OF_RESIDENT);
		ps.setBoolean(1, resident.getActive());
		ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));// modify date
		ps.setString(3, resident.getModifiedBy());
		ps.setLong(4, resident.getResidentId());
		return ps;
	}

	public long saveResidentAssessmentQuestionnaire(
			final ResidentAssessmentQuestionnaire residentAssessmentQuestionnaire, String lifeDomain,
			String selectedDate) {

		final KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] pkColumnNames = new String[] { "raq_id" };
		this.getJdbcTemplate().update(conn -> {
			try {
				return buildInsertResidentAssessmentQuestionnairePS(conn, residentAssessmentQuestionnaire,
						pkColumnNames, lifeDomain, selectedDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}, keyHolder);

		long raqId = keyHolder.getKey().longValue();
		return raqId;
	}

	private PreparedStatement buildInsertResidentAssessmentQuestionnairePS(Connection connection,
			final ResidentAssessmentQuestionnaire residentAssessmentQuestionnaire, String[] pkColumnNames,
			String lifeDomain, String selectedDate) throws SQLException, ParseException {
		PreparedStatement ps = connection.prepareStatement(SQL_INSERT_RESIDENT_ASSESSMENT_QUES, pkColumnNames);
		ps.setLong(1, residentAssessmentQuestionnaire.getResidentId());
		ps.setInt(2, residentAssessmentQuestionnaire.getQuestionId());
		ps.setInt(3, residentAssessmentQuestionnaire.getChoiceId());
		ps.setString(4, lifeDomain);
		ps.setDate(5, (StringUtils.isBlank(selectedDate)) ? Date.valueOf(LocalDate.now()) : parseMyDate(selectedDate));
		return ps;
	}

	private void calculateScore(final Resident resident, String lifeDomain, String onThisDate) {

		// Calculate Score first with Algorithm in LIFE_DOMAIN_sCORE_GOAL_TABLE
		try {

			if (onThisDate.equals("TODAY")) {
				onThisDate = " current_date";
			} else {
				onThisDate = " TO_DATE('" + resident.getSelectedDate() + "','DD-MON-YYYY')";
			}
			Integer calculatedScore = this.getJdbcTemplate().queryForObject(
					"select ldsg.score from life_domain_score_guide ldsg join resident_assessment_questionnaire raq on raq.life_domain = ldsg.life_domain and raq.question_id = ldsg.question_no and raq.choice_id = ldsg.choice_id where raq.resident_id = "
							+ resident.getResidentId() + " and raq.on_this_date = " + onThisDate
							+ " and raq.life_domain = '" + lifeDomain + "' order by ldsg.priority limit 1",
					Integer.class);

			if (null != calculatedScore) {
				resident.setCurrentScore(calculatedScore);
				resident.setGoal((calculatedScore == 5) ? 5 : calculatedScore + 1);
			}
		} catch (EmptyResultDataAccessException ex) {
			resident.setCurrentScore(0);
			resident.setGoal(0);
		}
	}

	public long saveResidentScoreGoal(final @Valid Resident resident, String lifeDomain) {

		calculateScore(resident, lifeDomain, "TODAY");

		final KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] pkColumnNames = new String[] { "rsg_id" };
		this.getJdbcTemplate().update(conn -> buildInsertResidentScoreGoalPS(conn, resident, pkColumnNames, lifeDomain),
				keyHolder);

		long rsgId = keyHolder.getKey().longValue();
		return rsgId;
	}

	private PreparedStatement buildInsertResidentScoreGoalPS(Connection connection, @Valid Resident resident,
			String[] pkColumnNames, String lifeDomain) throws SQLException {

		PreparedStatement ps = connection.prepareStatement(SQL_INSERT_RESIDENT_SCORE_GOAL, pkColumnNames);
		ps.setLong(1, resident.getResidentId());
		ps.setString(2, lifeDomain);
		ps.setInt(3, (resident.getCurrentScore() == null) ? 0 : resident.getCurrentScore());
		ps.setInt(4, (resident.getGoal() == null) ? 0 : resident.getGoal());
		ps.setDate(5, Date.valueOf(LocalDate.now()));
		return ps;
	}

	public int updateResidentAssessmentQuestionnaire(String selectedDate, ResidentAssessmentQuestionnaire raqs,
			String lifeDomain) {
		return this.getJdbcTemplate().update(conn -> {
			try {
				return buildUpateResidentAssessmentQuestionnaire(conn, raqs, lifeDomain, parseMyDate(selectedDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		});
	}

	private PreparedStatement buildUpateResidentAssessmentQuestionnaire(Connection conn,
			ResidentAssessmentQuestionnaire raqs, String lifeDomain, Date sqlDate) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RESIDENT_ASSESSMENT_QUES);
		ps.setLong(1, raqs.getResidentId());
		ps.setInt(2, raqs.getQuestionId());
		ps.setInt(3, raqs.getChoiceId());
		ps.setString(4, lifeDomain);
		ps.setLong(5, raqs.getResidentId());
		ps.setString(6, lifeDomain);
		ps.setDate(7, sqlDate);
		ps.setInt(8, raqs.getQuestionId());
		return ps;
	}

	public int updateResidentScoreGoal(@Valid Resident resident, String lifeDomain) throws SQLException {

		calculateScore(resident, lifeDomain, resident.getSelectedDate());

		return this.getJdbcTemplate().update(conn -> {
			try {
				return buildUpateResidentScoreGoal(conn, resident, lifeDomain);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		});
	}

	private PreparedStatement buildUpateResidentScoreGoal(Connection conn, @Valid Resident resident, String lifeDomain)
			throws SQLException, ParseException {
		PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RESIDENT_SCORE_GOAL);
		ps.setLong(1, resident.getResidentId());
		ps.setString(2, lifeDomain);
		ps.setInt(3, (resident.getCurrentScore() == null) ? 0 : resident.getCurrentScore());
		ps.setInt(4, (resident.getGoal() == null) ? 0 : resident.getGoal());
		ps.setLong(5, resident.getResidentId());
		ps.setString(6, lifeDomain);
		ps.setDate(7, parseMyDate(resident.getSelectedDate()));
		return ps;
	}

	private java.sql.Date parseMyDate(String selectedDate) throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
		java.util.Date parsed = format.parse(selectedDate);
		return new java.sql.Date(parsed.getTime());
	}

	// This will display date of most recent self sufficiency Assessment Date
	public String getMostRecentSSMDate(Long residentId) {
		try {
			String stringDate = this.getJdbcTemplate().queryForObject(
					"select TO_CHAR(on_this_date, 'mm/dd/YYYY') from resident_score_goal where resident_id = ? order by on_this_date desc Limit 1",
					new Object[] { residentId }, String.class);
			return stringDate;
		} catch (EmptyResultDataAccessException e) {
			return "--";
		}
	}

	public List<String> getAllReferralPartners() {
		List<String> refPartners = (List<String>) this.getJdbcTemplate().queryForList(
				"SELECT referral_partner_name from referral_partner  ORDER BY referral_partner_name", String.class);
		if (CollectionUtils.isEmpty(refPartners)) {
			return Collections.emptyList();
		}
		return refPartners.stream().distinct().collect(Collectors.toList());
	}

	public List<ResidentAudit> getResidentAudits(Long residentId) {

		return this.getJdbcTemplate().query("select * from resident_audits where resident_Id = "+ residentId +" order by audit_date desc",
				(rs, rowNum) -> new ResidentAudit(rs.getLong("audit_id"), rs.getLong("resident_id"),
						rs.getBoolean("active"), rs.getBoolean("is_resident"), rs.getInt("ref_type"),
						rs.getString("first_name"), rs.getString("middle"), rs.getString("last_name"), rs.getInt("prop_id"),
						rs.getBoolean("via_voicemail"), rs.getBoolean("via_text"), rs.getBoolean("via_email"), rs.getString("voicemail_no"), rs.getString("text_no"), rs.getString("email"), rs.getString("address"),
						rs.getBoolean("ack_pr"), rs.getBoolean("allow_contact"), rs.getBoolean("wants_survey"), rs.getBoolean("photo_release"), rs.getDate("audit_date"),
						rs.getString("modified_by"), rs.getString("service_coord"), rs.getString("age"), rs.getString("pri_language"),
						rs.getString("marital_Status"), rs.getString("annual_gross"), rs.getString("gender"),
						rs.getString("ethnicity"), rs.getString("race"), rs.getString("h_o_h"), rs.getString("veteran"), rs.getString("disability"),
						rs.getString("rc_or_ex_off"), rs.getString("ssi"), rs.getString("ssdi"), rs.getString("health_coverage"), rs.getString("highest_edu"), rs.getString("safe_day"), rs.getString("safe_night"),
						rs.getString("occupancy_length"), rs.getString("int_res_council"), rs.getString("mode_transport"), rs.getString("exp_food_short"),
						rs.getString("internet_access"), rs.getString("hoh_type"), rs.getString("unemp_reason"), rs.getString("barrier_to_edu"), rs.getString("health_condition"),
						rs.getString("program_srvc_youth"), rs.getString("program_srvc_adult")));
	}

}
