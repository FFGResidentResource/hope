/**
 * 
 */
package com.ffg.rrn.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;

import com.ffg.rrn.model.ServiceCoordinator;

/**
 * @author FFGRRNTeam
 *
 */
public class ServiceCoordinatorMapper implements RowMapper<ServiceCoordinator> {
	 
    public static final String BASE_SQL //
            = "Select u.sc_Id, u.User_Name, u.Encrypted_Password, u.active, u.email, u.created_on, u.last_login From service_coordinator u ";
 
    @Override
    public ServiceCoordinator mapRow(ResultSet rs, int rowNum) throws SQLException {
 
        Integer scId = rs.getInt("sc_Id");
        String userName = rs.getString("User_Name");
        String encryptedPassword = rs.getString("Encrypted_Password");
        String active = rs.getString("active");
        String email = rs.getString("email");
        Timestamp createdOn = rs.getTimestamp("created_on");
        Timestamp lastLogin = rs.getTimestamp("last_login");
 
        return new ServiceCoordinator(scId, userName, encryptedPassword, active, email, createdOn, lastLogin);
    }
 
}
