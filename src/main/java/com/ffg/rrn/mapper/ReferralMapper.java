/**
 * 
 */
package com.ffg.rrn.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ffg.rrn.model.Referral;

/**
 * @author FFGRRNTeam
 *
 */
public class ReferralMapper implements RowMapper<Referral> {

	public static final String REF_SQL //
			= "SELECT REF_ID, REF_VALUE FROM REFERRAL";

	@Override
	public Referral mapRow(ResultSet rs, int row) throws SQLException {

		Referral r = new Referral();

		r.setRefId(rs.getInt("REF_ID"));
		r.setRefValue(rs.getString("REF_VALUE"));

		return r;
	}

}
