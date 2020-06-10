package com.ffg.rrn.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ffg.rrn.model.DemographicChoice;

/**
 * @author FFGRRNTeam
 *
 */

public class VeteranMapper implements RowMapper<DemographicChoice> {
	
	public static final String VET_SQL //
	= "select choice_id, choice from DEMOGRAPHICS_CHOICES where type='Veteran'"; 

@Override
public DemographicChoice mapRow(ResultSet rs, int row) throws SQLException {

	DemographicChoice r = new DemographicChoice();

r.setChoiceId(rs.getInt("choice_id"));
r.setChoiceValue(rs.getString("choice"));

return r;
}

}







