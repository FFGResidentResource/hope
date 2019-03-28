/**
 * 
 */
package com.ffg.rrn.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ffg.rrn.model.AssessmentType;

/**
 * @author FFGRRNTeam
 *
 */
public class AssessmentMapper implements RowMapper<AssessmentType> {

	public static final String A_SQL //
			= "SELECT A_ID,A_VALUE FROM ASSESSMENT_TYPE";

	@Override
	public AssessmentType mapRow(ResultSet rs, int row) throws SQLException {

		AssessmentType a = new AssessmentType();

		a.setAId(rs.getInt("A_ID"));
		a.setAValue(rs.getString("A_VALUE"));

		return a;
	}

}
