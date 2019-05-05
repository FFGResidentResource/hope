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
public class CaseNotesDAO extends JdbcDaoSupport {

	private static final String SQL_INSERT_CASE_NOTES = "INSERT INTO CASE_NOTES (CASE_NOTES_ID, DESCRIPTION, ASSESSMENT, PLAN, RESIDENT_ID, SERVICE_COORD) VALUES (nextval('CN_SQ'),?,?,?,?,?)";
	private static final String SQL_UPDATE_CASE_NOTES = "UPDATE CASE_NOTES SET DESCRIPTION =?, ASSESSMENT = ?, PLAN = ?, SERVICE_COORD = ?,  DATE_MODIFIED = NOW() WHERE RESIDENT_ID = ?";


    @Autowired
	public CaseNotesDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
	}

	/**
	 *
	 * @throws ParseException
	 * @throws DataAccessException
	 * @Params action plan to save
	 */
	public long saveCaseNotes(@Valid Resident resident) {

		final KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] pkColumnNames = new String[] { "case_notes_id" };

		try {
			Long residentIdInActionPlan = this.getJdbcTemplate().queryForObject("select resident_id from case_notes where resident_id = ?", new Object[] { resident.getResidentId() }, Long.class);

			if (residentIdInActionPlan != null) {
				this.getJdbcTemplate().update(conn -> buildUpdateCaseNotes(conn, resident, pkColumnNames), keyHolder);
			}
		}
		// When no resident found in action_plan we do fresh insert
		catch (EmptyResultDataAccessException e) {
			this.getJdbcTemplate().update(conn -> buildInsertCaseNotes(conn, resident, pkColumnNames), keyHolder);
		}

		long actionPlanId = keyHolder.getKey().longValue();
		return actionPlanId;

	}

	private PreparedStatement buildUpdateCaseNotes(Connection connection, @Valid Resident resident, String[] pkColumnNames) throws SQLException {

		PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_CASE_NOTES, pkColumnNames);

		ps.setString(1, resident.getDescription().trim());
		ps.setString(2, resident.getAssessment().trim());
		ps.setString(3, resident.getPlan().trim());
		ps.setString(4, resident.getServiceCoord());
		ps.setLong(5, resident.getResidentId());
		return ps;
	}

	private PreparedStatement buildInsertCaseNotes(Connection connection, @Valid Resident resident, String[] pkColumnNames) throws SQLException {

		PreparedStatement ps = connection.prepareStatement(SQL_INSERT_CASE_NOTES, pkColumnNames);
		ps.setString(1, resident.getDescription().trim());
		ps.setString(2, resident.getAssessment().trim());
		ps.setString(3, resident.getPlan().trim());
		ps.setLong(4, resident.getResidentId());
		ps.setString(5, resident.getServiceCoord());

		return ps;
	}
}