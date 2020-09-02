/**
 * 
 */
package com.ffg.rrn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;
import javax.validation.Valid;

import org.apache.tomcat.util.json.ParseException;
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
public class CaseNotesDAO extends JdbcDaoSupport {

	private static final String SQL_INSERT_CASE_NOTES = "INSERT INTO CASE_NOTES (CASE_NOTES_ID, DESCRIPTION, ASSESSMENT, PLAN, NO_SHOW_DATE, RESIDENT_ID, SERVICE_COORD) VALUES (nextval('CN_SQ'),?,?,?,?,?,?)";
	private static final String SQL_UPDATE_CASE_NOTES = "UPDATE CASE_NOTES SET DESCRIPTION =?, ASSESSMENT = ?, PLAN = ?, NO_SHOW_DATE = ?, SERVICE_COORD = ?,  DATE_MODIFIED = NOW() WHERE RESIDENT_ID = ? and DATE_ADDED = ? ";

	

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

		this.getJdbcTemplate().update(conn -> buildInsertCaseNotes(conn, resident, pkColumnNames), keyHolder);

		long actionPlanId = keyHolder.getKey().longValue();
		return actionPlanId;

	}

	public long updateCaseNotes(@Valid Resident resident) {

		final KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] pkColumnNames = new String[] { "case_notes_id" };

		this.getJdbcTemplate().update(conn -> {
			try {
				return buildUpdateCaseNotes(conn, resident, pkColumnNames);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}, keyHolder);

		long actionPlanId = keyHolder.getKey().longValue();
		return actionPlanId;

	}

	private PreparedStatement buildUpdateCaseNotes(Connection connection, @Valid Resident resident, String[] pkColumnNames) throws SQLException, java.text.ParseException {

		PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_CASE_NOTES, pkColumnNames);

		ps.setString(1, resident.getDescription().trim());
		ps.setString(2, resident.getAssessment().trim());
		ps.setString(3, resident.getPlan().trim());
		ps.setString(4,  resident.getNoShowDate());
		ps.setString(5, resident.getServiceCoord());
		ps.setLong(6, resident.getResidentId());
		ps.setDate(7, parseMyDate(resident.getSelectedDate()));
		return ps;
	}

	private java.sql.Date parseMyDate(String selectedDate) throws java.text.ParseException {

		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
		java.util.Date parsed = format.parse(selectedDate);
		return new java.sql.Date(parsed.getTime());
	}

	private PreparedStatement buildInsertCaseNotes(Connection connection, @Valid Resident resident, String[] pkColumnNames) throws SQLException {

		PreparedStatement ps = connection.prepareStatement(SQL_INSERT_CASE_NOTES, pkColumnNames);
		ps.setString(1, resident.getDescription().trim());
		ps.setString(2, resident.getAssessment().trim());
		ps.setString(3, resident.getPlan().trim());
		ps.setString(4,  resident.getNoShowDate());
		ps.setLong(5, resident.getResidentId());
		ps.setString(6, resident.getServiceCoord());

		return ps;
	}

}