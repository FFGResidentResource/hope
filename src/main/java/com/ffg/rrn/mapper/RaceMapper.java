package com.ffg.rrn.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ffg.rrn.model.DemographicChoice;

/**
 * @author FFGRRNTeam
 *
 */

public class RaceMapper implements RowMapper<DemographicChoice> {
	
	public static final String RACE_SQL //
	= "select choice_id, choice from DEMOGRAPHICS_CHOICES where type='Race'"; 

@Override
public DemographicChoice mapRow(ResultSet rs, int row) throws SQLException {

	DemographicChoice r = new DemographicChoice();

r.setChoiceId(rs.getInt("choice_id"));
r.setChoiceValue(rs.getString("choice"));

return r;
}

}







