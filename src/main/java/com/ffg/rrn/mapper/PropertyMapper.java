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
	
	public static final String PROPERTY_SQL_FOR_NON_ADMIN_SC //
			= "SELECT P.PROP_ID, P.PROP_NAME, P.UNIT, P.UNIT_FEE, P.ACTIVE, P.TOTAL_RESIDENTS, P.RESIDENT_COUNCIL, P.CITY, P.STATE, P.COUNTY " + 
					"FROM  PROPERTY P where P.ACTIVE = 'TRUE' " + 
					"and P.prop_id in ( select json_array_elements(assigned_property)::text::int from service_coordinator sc where json_typeof(assigned_property) != 'null' and SC.USER_NAME = ?)";

	public static final String PROPERTY_SQL //
			= "SELECT P.PROP_ID, P.PROP_NAME, P.UNIT, P.UNIT_FEE, P.ACTIVE, P.TOTAL_RESIDENTS, P.RESIDENT_COUNCIL, P.CITY, P.STATE, P.COUNTY FROM  PROPERTY P  where P.ACTIVE = 'TRUE'";

	@Override
	public Property mapRow(ResultSet rs, int row) throws SQLException {

		Property p = new Property();	
		
		p.setPropertyId(rs.getInt("PROP_ID"));
		p.setPropertyName(rs.getString("PROP_NAME"));
		p.setUnit(rs.getInt("UNIT"));
		p.setUnitFee(rs.getInt("UNIT_FEE"));
		p.setActive(rs.getBoolean("ACTIVE"));
		p.setNoOfResident(rs.getInt("TOTAL_RESIDENTS"));
		p.setResidentCouncil(rs.getBoolean("RESIDENT_COUNCIL"));
		p.setCity(rs.getString("CITY"));
		p.setState(rs.getString("STATE"));
		p.setCounty(rs.getString("COUNTY"));
		
		return p;
	}
	
	

}
