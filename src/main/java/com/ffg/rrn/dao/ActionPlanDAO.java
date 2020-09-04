/**
 * 
 */
package com.ffg.rrn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ffg.rrn.model.Resident;

/**
 * @author FFGRRNTeam
 *
 */
@Repository
@Transactional
public class ActionPlanDAO extends JdbcDaoSupport {

	private static final String SQL_INSERT_ACTION_PLAN = "INSERT INTO ACTION_PLAN (ACTION_PLAN_ID, RESIDENT_ID, PLAN_OF_ACTION, PLAN_DETAILS, REFERRAL_PARTNER, ANTICIPATED_OUTCOMES, ANTICIPATED_DATE, OUTCOME_ACHIEVED,  ACHIEVED_SSM, COMPLETION_DATE, FOLLOWUP_NOTES, SERVICE_COORD) VALUES (nextval('AP_SQ'),?,to_json(?::json),to_json(?::json),to_json(?::json),to_json(?::json),to_json(?::json),to_json(?::json), to_json(?::json), to_json(?::json),?,?)";

	private static final String SQL_UPDATE_ACTION_PLAN = "UPDATE ACTION_PLAN SET PLAN_OF_ACTION = to_json(?::json), PLAN_DETAILS = to_json(?::json), REFERRAL_PARTNER = to_json(?::json), ANTICIPATED_OUTCOMES = to_json(?::json),  ANTICIPATED_DATE = to_json(?::json), OUTCOME_ACHIEVED = to_json(?::json), ACHIEVED_SSM = to_json(?::json), COMPLETION_DATE = to_json(?::json), FOLLOWUP_NOTES = ?, DATE_MODIFIED = NOW() WHERE RESIDENT_ID = ? and DATE_ADDED = ? ";

	@Autowired
	public ActionPlanDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	/**
	 *
	 * @throws ParseException
	 * @throws DataAccessException
	 * @Params action plan to save
	 */
	public long saveActionPlan(@Valid Resident resident) {

		final KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] pkColumnNames = new String[] { "action_plan_id" };

		this.getJdbcTemplate().update(conn -> buildInsertActionPlan(conn, resident, pkColumnNames), keyHolder);

		long actionPlanId = keyHolder.getKey().longValue();
		return actionPlanId;

	}

	/**
	 *
	 * @throws ParseException
	 * @throws DataAccessException
	 * @Params action plan to save
	 */
	public long updateActionPlan(@Valid Resident resident) {

		final KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] pkColumnNames = new String[] { "action_plan_id" };

		
		this.getJdbcTemplate().update(conn -> {
			try {
				return buildUpdateActionPlan(conn, resident, pkColumnNames);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}, keyHolder);
		

		long actionPlanId = keyHolder.getKey().longValue();
		return actionPlanId;

	}

	private PreparedStatement buildUpdateActionPlan(Connection connection, @Valid Resident resident,
			String[] pkColumnNames) throws SQLException, ParseException {

		PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_ACTION_PLAN, pkColumnNames);

		ps.setString(1, resident.getPlanOfAction());
		ps.setString(2, resident.getPlanDetails());
		ps.setString(3, resident.getReferralPartner());
		ps.setString(4, resident.getAnticipatedOutcome());
		ps.setString(5, resident.getAnticipatedDates());
		ps.setString(6, resident.getOutcomesAchieved());
		ps.setString(7, resident.getAchievedGoals());
		ps.setString(8, resident.getCompletionDates());
		ps.setString(9, resident.getFollowUpNotes().trim());
		ps.setLong(10, resident.getResidentId());
		ps.setDate(11, parseMyDate(resident.getSelectedDate()));
		return ps;
	}

	private java.sql.Date parseMyDate(String selectedDate) throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
		java.util.Date parsed = format.parse(selectedDate);
		return new java.sql.Date(parsed.getTime());
	}

	private PreparedStatement buildInsertActionPlan(Connection connection, @Valid Resident resident, String[] pkColumnNames) throws SQLException {

		PreparedStatement ps = connection.prepareStatement(SQL_INSERT_ACTION_PLAN, pkColumnNames);
		
		ps.setLong(1, resident.getResidentId());
		//Begin - These values will be never empty as actionPlan.js is filling these atleaset as '{ "HOUSING": "", "MONEY MANAGEMENT": "", "EMPLOYMENT": "", "EDUCATION": "", "NETWORK SUPPORT": "", "HOUSEHOLD MANAGEMENT": "" }'
		ps.setString(2, resident.getPlanOfAction());
		ps.setString(3, resident.getPlanDetails());
		ps.setString(4, resident.getReferralPartner());
		ps.setString(5, resident.getAnticipatedOutcome());
		ps.setString(6, resident.getAnticipatedDates());
		ps.setString(7, resident.getOutcomesAchieved());
		ps.setString(8, resident.getAchievedGoals());
		ps.setString(9, resident.getCompletionDates());
		//End - These values will be never empty as actionPlan.js is filling these atleaset as '{ "HOUSING": "", "MONEY MANAGEMENT": "", "EMPLOYMENT": "", "EDUCATION": "", "NETWORK SUPPORT": "", "HOUSEHOLD MANAGEMENT": "" }'
		ps.setString(10, resident.getFollowUpNotes().trim());
		ps.setString(11, resident.getServiceCoord());
		
		return ps;
	}

}
