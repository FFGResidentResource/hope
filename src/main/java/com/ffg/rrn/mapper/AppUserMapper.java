/**
 * 
 */
package com.ffg.rrn.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;

import com.ffg.rrn.model.AppUser;

/**
 * @author mycomputer
 *
 */
public class AppUserMapper implements RowMapper<AppUser> {
	 
    public static final String BASE_SQL //
            = "Select u.User_Id, u.User_Name, u.Encryted_Password, u.active, u.email, u.created_on, u.last_login From App_User u ";
 
    @Override
    public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
 
        Integer userId = rs.getInt("User_Id");
        String userName = rs.getString("User_Name");
        String encrytedPassword = rs.getString("Encryted_Password");
        String active = rs.getString("active");
        String email = rs.getString("email");
        Timestamp createdOn = rs.getTimestamp("created_on");
        Timestamp lastLogin = rs.getTimestamp("last_login");
 
        return new AppUser(userId, userName, encrytedPassword, active, email, createdOn, lastLogin);
    }
 
}
