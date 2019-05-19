/**
 * 
 */
package com.ffg.rrn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.validation.Valid;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
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

	private static final String SQL_INSERT_ACTION_PLAN = "INSERT INTO ACTION_PLAN (ACTION_PLAN_ID, RESIDENT_ID, RESIDENT_CONCERNS, FOCUS_ON_DOMAIN, PLAN_OF_ACTION, PLAN_DETAILS, ANTICIPATED_OUTCOMES, OUTCOME_ACHIEVED,  ACHIEVED_SSM, COMPLETION_DATE, FOLLOWUP_NOTES, SERVICE_COORD) VALUES (nextval('AP_SQ'),?,?,to_json(?::json),to_json(?::json),to_json(?::json),to_json(?::json),to_json(?::json), to_json(?::json), to_json(?::json),?,?)";

	private static final String SQL_UPDATE_ACTION_PLAN = "UPDATE ACTION_PLAN SET RESIDENT_CONCERNS =?, FOCUS_ON_DOMAIN = to_json(?::json), PLAN_OF_ACTION = to_json(?::json), PLAN_DETAILS = to_json(?::json), ANTICIPATED_OUTCOMES = to_json(?::json),  OUTCOME_ACHIEVED = to_json(?::json), ACHIEVED_SSM = to_json(?::json), COMPLETION_DATE = to_json(?::json), FOLLOWUP_NOTES = ?, DATE_MODIFIED = NOW() WHERE RESIDENT_ID = ?";

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

		try {
			Long residentIdInActionPlan = this.getJdbcTemplate().queryForObject("select resident_id from action_plan where resident_id = ?", new Object[] { resident.getResidentId() }, Long.class);

			if (residentIdInActionPlan != null) {
				this.getJdbcTemplate().update(conn -> buildUpdateActionPlan(conn, resident, pkColumnNames), keyHolder);
			}
		}
		// When no resident found in action_plan we do fresh insert
		catch (EmptyResultDataAccessException e) {
			this.getJdbcTemplate().update(conn -> buildInsertActionPlan(conn, resident, pkColumnNames), keyHolder);
		}

		long actionPlanId = keyHolder.getKey().longValue();
		return actionPlanId;

	}

	private PreparedStatement buildUpdateActionPlan(Connection connection, @Valid Resident resident,
			String[] pkColumnNames) throws SQLException {

		PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_ACTION_PLAN, pkColumnNames);

		ps.setString(1, resident.getResidentReportedConcern());
		ps.setString(2, resident.getFocusOnActionPlan());
		ps.setString(3, resident.getPlanOfAction());
		ps.setString(4, resident.getPlanDetails());
		ps.setString(5, resident.getAnticipatedOutcome());
		ps.setString(6, resident.getOutcomesAchieved());
		ps.setString(7, resident.getAchievedGoals());
		ps.setString(8, resident.getCompletionDates());
		ps.setString(9, resident.getFollowUpNotes().trim());
		ps.setLong(10, resident.getResidentId());
		return ps;
	}

	private PreparedStatement buildInsertActionPlan(Connection connection, @Valid Resident resident, String[] pkColumnNames) throws SQLException {

		PreparedStatement ps = connection.prepareStatement(SQL_INSERT_ACTION_PLAN, pkColumnNames);
		ps.setLong(1, resident.getResidentId());
		ps.setString(2, resident.getResidentReportedConcern());
		ps.setString(3, resident.getFocusOnActionPlan());
		ps.setString(4, resident.getPlanOfAction());
		ps.setString(5, resident.getPlanDetails());
		ps.setString(6, resident.getAnticipatedOutcome());
		ps.setString(7, resident.getOutcomesAchieved());
		ps.setString(8, resident.getAchievedGoals());
		ps.setString(9, resident.getCompletionDates());
		ps.setString(10, resident.getFollowUpNotes().trim());
		ps.setString(11, resident.getServiceCoord());
		
		return ps;
	}

}
