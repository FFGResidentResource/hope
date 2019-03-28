/**
 * 
 */
package com.ffg.rrn.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ffg.rrn.model.Child;

/**
 * @author FFGRRNTEam
 *
 */
public class ChildrenMapper implements RowMapper<Child> {

	public static final String CHILDREN_SQL_BY_RESIDENT_ID //
			= "SELECT CHILD_ID, FULL_NAME, PARENT_ID, PVR_FLAG FROM CHILD WHERE PARENT_ID = ?";

	@Override
	public Child mapRow(ResultSet rs, int row) throws SQLException {

		Child c = new Child();

		c.setChildId(rs.getLong("CHILD_ID"));
		c.setFullName(rs.getString("FULL_NAME"));
		c.setParentId(rs.getLong("PARENT_ID"));
		c.setPvrFlag(rs.getBoolean("PVR_FLAG"));

		return c;

	}

}
