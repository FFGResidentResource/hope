/**
 * 
 */
package com.ffg.rrn.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ffg.rrn.model.Property;

/**
 * @author FFGRRNTeam
 *
 */
public class PropertyMapper implements RowMapper<Property> {
	
	public static final String PROPERTY_SQL //
    = "SELECT PROP_ID, PROP_NAME, UNIT, UNIT_FEE, ACTIVE FROM PROPERTY";

	@Override
	public Property mapRow(ResultSet rs, int row) throws SQLException {

		Property p = new Property();	
		
		p.setPropertyId(rs.getInt("PROP_ID"));
		p.setPropertyName(rs.getString("PROP_NAME"));
		p.setUnit(rs.getInt("UNIT"));
		p.setUnitFee(rs.getInt("UNIT_FEE"));
		p.setActive(rs.getBoolean("ACTIVE"));
		
		return p;
	}
	
	

}
