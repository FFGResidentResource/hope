/**
 * 
 */
package com.ffg.rrn.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ffg.rrn.model.Resident;

/**
 * @author FFGRRNTEam
 *
 */
public class ResidentMapper implements RowMapper<Resident> {

	public static final String RESIDENT_SQL //
			= "select r.RESIDENT_ID, r.ACTIVE, r.FIRST_NAME, r.MIDDLE, r.LAST_NAME, r.PROP_ID,r.VOICEMAIL_NO, r.TEXT_NO, r.EMAIL, r.ADDRESS, r.ACK_PR, "
					+ " r.ALLOW_CONTACT, r.WANTS_SURVEY, r.PHOTO_RELEASE, r.SERVICE_COORD, r.REF_TYPE, r.A_TYPE, "
					+ " r.date_added, r.date_modified, r.modified_by, p.prop_name, ref.ref_value, a.a_value, "
					+ " (select string_agg(full_name, ', ') from child where parent_id = r.resident_id) as children "
					+ " from Resident r join referral ref on ref.ref_id = r.ref_type"
					+ " join property p on p.prop_id = r.prop_id"
					+ " left join assessment_type a on a.a_id = r.a_type;";

	@Override
	public Resident mapRow(ResultSet rs, int row) throws SQLException {

		Resident r = new Resident();

		r.setResidentId(rs.getLong("RESIDENT_ID"));
		r.setActive(rs.getBoolean("ACTIVE"));
		r.setFirstName(rs.getString("FIRST_NAME"));
		r.setMiddle(rs.getString("MIDDLE"));
		r.setLastName(rs.getString("LAST_NAME"));
		r.setPropertyId(rs.getInt("PROP_ID"));
		r.setVoiceMail(rs.getString("VOICEMAIL_NO"));
		r.setText(rs.getString("TEXT_NO"));
		r.setEmail(rs.getString("EMAIL"));
		r.setAddress(rs.getString("ADDRESS"));
		r.setAckRightToPrivacy(rs.getBoolean("ACK_PR"));
		r.setAllowContact(rs.getBoolean("ALLOW_CONTACT"));
		r.setWantSurvey(rs.getBoolean("WANTS_SURVEY"));
		r.setPhotoRelease(rs.getBoolean("PHOTO_RELEASE"));
		r.setServiceCoord(rs.getString("SERVICE_COORD"));
		r.setRefId(rs.getInt("REF_TYPE"));
		r.setAId(rs.getInt("A_TYPE"));
		r.setPropertyName(rs.getString("PROP_NAME"));
		r.setRefValue(rs.getString("REF_VALUE"));
		r.setAValue(rs.getString("A_VALUE"));
		r.setDateAdded(rs.getTimestamp("DATE_ADDED"));
		r.setDateModified(rs.getTimestamp("DATE_MODIFIED"));
		r.setModifiedBy(rs.getString("MODIFIED_BY"));
		r.setChildList(rs.getString("children"));

		return r;

	}

}
