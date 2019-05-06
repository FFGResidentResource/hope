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
public class ReferralFormDAO extends JdbcDaoSupport {

	private static final String SQL_INSERT_REFERRAL_FORM = "INSERT INTO REFERRAL_FORM (REFERRAL_FORM_ID, RESIDENT_ID, INTERPRETATION, REFERRED_BY, DATE_ADDED, REFERRAL_REASON, COMMENTS, PREVIOUS_ATTEMPTS, SELF_SUFFICIENCY, RF_HOUSING_STABILITY, SAFE_SUPPORTIVE_COMMUNITY, RF_FOLLOWUP_NOTES, RES_APP_SCHEDULED, SERVICE_COORD) VALUES (nextval('RF_SQ'),?,?,?, NOW(), to_json(?::json),?,?, to_json(?::json),to_json(?::json),to_json(?::json),?,to_json(?::json),?)";

	private static final String SQL_UPDATE_REFERRAL_FORM = "UPDATE REFERRAL_FORM SET INTERPRETATION =?, REFERRED_BY = ?, DATE_MODIFIED = NOW(), REFERRAL_REASON= to_json(?::json), COMMENTS= ?, PREVIOUS_ATTEMPTS= ?, SELF_SUFFICIENCY = to_json(?::json), RF_HOUSING_STABILITY = to_json(?::json), SAFE_SUPPORTIVE_COMMUNITY = to_json(?::json), RF_FOLLOWUP_NOTES= ?, RES_APP_SCHEDULED = to_json(?::json) WHERE RESIDENT_ID = ?";

	@Autowired
	public ReferralFormDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	/**
	 *
	 * @throws ParseException
	 * @throws DataAccessException
	 * @Params action plan to save
	 */
	public long saveReferralForm(@Valid Resident resident) {

		final KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] pkColumnNames = new String[] { "referral_form_id" };

		try {
			Long residentIdInReferralForm = this.getJdbcTemplate().queryForObject("select resident_id from REFERRAL_FORM where resident_id = ?", new Object[] { resident.getResidentId() }, Long.class);

			if (residentIdInReferralForm != null) {
				this.getJdbcTemplate().update(conn -> buildUpdateReferralForm(conn, resident, pkColumnNames), keyHolder);
			}
		}
		// When no resident found in action_plan we do fresh insert
		catch (EmptyResultDataAccessException e) {
			this.getJdbcTemplate().update(conn -> buildInsertReferralForm(conn, resident, pkColumnNames), keyHolder);
		}

		long referralFormId = keyHolder.getKey().longValue();
		return referralFormId;

	}

	private PreparedStatement buildUpdateReferralForm(Connection connection, @Valid Resident resident,
			String[] pkColumnNames) throws SQLException {

		PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_REFERRAL_FORM, pkColumnNames);

		ps.setBoolean(1, resident.isInterpretation());
		ps.setString(2, resident.getReferredBy());
		ps.setString(3, resident.getReferralReason());
		ps.setString(4, resident.getCommentsOrExplanation().trim());
		ps.setString(5, resident.getPreviousAttempts().trim());
		ps.setString(6, resident.getSelfSufficiency());
		ps.setString(7, resident.getHousingStability());
		ps.setString(8, resident.getSafeSupportiveCommunity());
		ps.setString(9, resident.getRfFollowUpNotes().trim());
		ps.setString(10, resident.getResidentAppointmentScheduled());
		ps.setLong(11, resident.getResidentId());
		return ps;
	}

	private PreparedStatement buildInsertReferralForm(Connection connection, @Valid Resident resident, String[] pkColumnNames) throws SQLException {

		PreparedStatement ps = connection.prepareStatement(SQL_INSERT_REFERRAL_FORM, pkColumnNames);
		ps.setLong(1, resident.getResidentId());
		ps.setBoolean(2, resident.isInterpretation());
		ps.setString(3, resident.getReferredBy());
		ps.setString(4, resident.getReferralReason());
		ps.setString(5, resident.getCommentsOrExplanation().trim());
		ps.setString(6, resident.getPreviousAttempts().trim());
		ps.setString(7, resident.getSelfSufficiency());
		ps.setString(8, resident.getHousingStability());
		ps.setString(9, resident.getSafeSupportiveCommunity());
		ps.setString(10, resident.getRfFollowUpNotes().trim());
		ps.setString(11, resident.getResidentAppointmentScheduled());
		ps.setString(12, resident.getServiceCoord());
		
		return ps;
	}

}