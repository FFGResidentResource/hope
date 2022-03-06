/**
 * 
 */
package com.ffg.rrn.dao;

import static java.lang.Math.toIntExact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ffg.rrn.mapper.PropertyMapper;
import com.ffg.rrn.mapper.ServiceCoordinatorMapper;
import com.ffg.rrn.model.Property;
import com.ffg.rrn.model.ServiceCoordinator;

/**
 * @author FFGRRNTeam
 *
 */
@Repository
@Transactional
public class ServiceCoordinatorDAO extends JdbcDaoSupport {

	private final static String INSERTION_SQL_SERVICECOORDINATOR = "INSERT INTO SERVICE_COORDINATOR " + "( USER_NAME, ENCRYPTED_PASSWORD, ACTIVE, EMAIL, CREATED_ON, ASSIGNED_PROPERTY)"
			+ " VALUES ( ?,?,true,?, NOW(), to_json(?::json))";
	
	private final static String INSERT_ADMIN_ROLE = "INSERT INTO USER_ROLE ( USER_ID, ROLE_ID) VALUES ( ? , 1)";

	private final static String INSERT_USER_ROLE = "INSERT INTO USER_ROLE ( USER_ID, ROLE_ID) VALUES ( ? , 2)";

	private final static String DELETE_ROLE = "DELETE FROM USER_ROLE WHERE USER_ID = ?";

	private final static String INSERTION_SQL_SERVICECOORDINATOR_ADMINS = "INSERT INTO SERVICE_COORDINATOR " + "( USER_NAME, ENCRYPTED_PASSWORD, ACTIVE, EMAIL, CREATED_ON)"
			+ " VALUES ( ?,?,true,?, NOW())";

	private final static String UPDATE_SQL_SERVICECOORDINATOR_ADMIN = "UPDATE SERVICE_COORDINATOR SET EMAIL = ?, ENCRYPTED_PASSWORD = ? , DATE_MODIFIED = NOW() where USER_NAME = ? ";

	private final static String UPDATE_SQL_SERVICECOORDINATOR_NONADMIN = "UPDATE SERVICE_COORDINATOR SET EMAIL = ?, ENCRYPTED_PASSWORD = ? , DATE_MODIFIED = NOW(), ASSIGNED_PROPERTY = to_json(?::json) where USER_NAME = ? ";

	private final static String INACTIVATE_SQL_SERVICECOORDINATOR = "UPDATE SERVICE_COORDINATOR SET ACTIVE = ?, DATE_MODIFIED = NOW() where USER_NAME = ? ";

	private final static String UPDATE_SQL_SERVICECOORDINATOR_LAST_LOGIN = "UPDATE SERVICE_COORDINATOR SET LAST_LOGIN = NOW() where USER_NAME = ?";

	@Autowired
	public ServiceCoordinatorDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	public ServiceCoordinator findUserAccount(String userName) {
		// Select .. from App_User u Where u.User_Name = ?
		String sql = ServiceCoordinatorMapper.BASE_SQL + " where u.User_Name = ? and u.active = 'TRUE' LIMIT 1  ";

		Object[] params = new Object[] { userName };
		ServiceCoordinatorMapper mapper = new ServiceCoordinatorMapper();
		try {
			ServiceCoordinator serviceCordInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
			return serviceCordInfo;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public ServiceCoordinator calculateEngagementAndIntakePending(final ServiceCoordinator serviceCordInfo) {
		
		if(null==serviceCordInfo.getAdmin()) {
			
			String engagementSql = "select round(count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where active = TRUE and prop_id  in (SELECT P.PROP_ID FROM  PROPERTY P where P.ACTIVE = 'TRUE' " 
					+			" and P.prop_id in ( select json_array_elements(assigned_property)::text::int from service_coordinator sc where json_typeof(assigned_property) != 'null' and SC.USER_NAME = ? )))::float* 100) as percentage "
					+ 			" from resident where active = TRUE and ack_pr = true and prop_id  in (SELECT P.PROP_ID FROM  PROPERTY P where P.ACTIVE = 'TRUE' "  
					+ 			" and P.prop_id in ( select json_array_elements(assigned_property)::text::int from service_coordinator sc where json_typeof(assigned_property) != 'null' and SC.USER_NAME = ? )) ";
			
			Integer percentage = this.getJdbcTemplate().queryForObject(engagementSql, new Object[] {serviceCordInfo.getUserName(), serviceCordInfo.getUserName()}, Integer.class);
			serviceCordInfo.setEngagementPercentage(percentage);
			
			String intakePendingSQL = "select count(*) from resident where active = TRUE and ack_pr = FALSE "
					+ "and prop_id in ( select json_array_elements(assigned_property)::text::int from service_coordinator sc where json_typeof(assigned_property) != 'null' and SC.USER_NAME = ? ) ";
			
			Integer totalIntakePending = this.getJdbcTemplate().queryForObject(intakePendingSQL, new Object[] {serviceCordInfo.getUserName()}, Integer.class);
			serviceCordInfo.setIntakePending(totalIntakePending);
									
		} else {
			
			String engagementSql = "select round(count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where active = TRUE)::float* 100) as percentage "
					+ 			" from resident where active = TRUE and ack_pr = true ";
			
			Integer percentage = this.getJdbcTemplate().queryForObject(engagementSql, Integer.class);
			serviceCordInfo.setEngagementPercentage(percentage);
			
			String intakePendingSQL = "select count(*) from resident where ack_pr = FALSE and active = TRUE";
			
			Integer totalIntakePending = this.getJdbcTemplate().queryForObject(intakePendingSQL, Integer.class);
			serviceCordInfo.setIntakePending(totalIntakePending);	
			
		}
		
		return serviceCordInfo;
	}

	public List<ServiceCoordinator> getAllServiceCoordinators() {
		ServiceCoordinatorMapper rowMapper = new ServiceCoordinatorMapper();
		List<ServiceCoordinator> scList = this.getJdbcTemplate().query(ServiceCoordinatorMapper.BASE_SQL, rowMapper);

		for (ServiceCoordinator serviceCoordinator : scList) {
			serviceCoordinator.setAdmin((null!=serviceCoordinator.getAssignedProperties()) ? Boolean.FALSE : Boolean.TRUE);
		}
		return scList;
	}

	private PreparedStatement updateLastLoginPS(Connection conn, String userName) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(UPDATE_SQL_SERVICECOORDINATOR_LAST_LOGIN);
		ps.setString(1, userName);
		return ps;
	}
	
	public void updateLastLogin(String userName) {
		 this.getJdbcTemplate().update(conn -> updateLastLoginPS(conn, userName));
	}
	
	public Long inactivateOrActivateSC(String userName, boolean active) {

		final KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] pkColumnNames = new String[] { "sc_id" };

		this.getJdbcTemplate().update(conn -> buildinactivateOrActivatePS(conn, userName, active, pkColumnNames), keyHolder);

		return keyHolder.getKey().longValue();
	}

	private PreparedStatement buildinactivateOrActivatePS(Connection conn, String userName, boolean active, String[] pkColumnNames) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(INACTIVATE_SQL_SERVICECOORDINATOR, pkColumnNames);

		ps.setBoolean(1, active);
		ps.setString(2, userName);

		return ps;
	}

	public Long saveServiceCoordinator(ServiceCoordinator sc) {
				
		final KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] pkColumnNames = new String[] { "sc_id" };

		try {
			// If no records found, it will go in Catch Exception else next line
			this.getJdbcTemplate().queryForObject("select 1 from service_coordinator where user_name = ? and email = ? ", new Object[] { sc.getUserName(), sc.getEmail() }, String.class);
			this.getJdbcTemplate().update(conn -> buildUpdateServiceCoordinatorPreparedStatement(conn, sc, pkColumnNames), keyHolder);

			long scId = keyHolder.getKey().longValue();
			sc.setScId(toIntExact(scId));

			// Delete and build fresh User_ROLE
			this.getJdbcTemplate().update(conn -> buildDeleteUserRolePS(conn, sc));
			this.getJdbcTemplate().update(conn -> buildInsertUserRolePS(conn, sc));

			// for admins - default USER_ROLE needs to be inserted as well
			if (sc.getAdmin()) {
				this.getJdbcTemplate().update(conn -> buildInsertDefaultUserRolePS(conn, sc));
			}

		}
		// When no resident found in action_plan we do fresh insert
		catch (EmptyResultDataAccessException e) {
			this.getJdbcTemplate().update(conn -> buildInsertServiceCoordinatorPreparedStatement(conn, sc, pkColumnNames), keyHolder);
			sc.setScId(toIntExact(keyHolder.getKey().longValue()));

			this.getJdbcTemplate().update(conn -> buildInsertUserRolePS(conn, sc));

			// for admins - default USER_ROLE needs to be inserted as well
			if (sc.getAdmin()) {
				this.getJdbcTemplate().update(conn -> buildInsertDefaultUserRolePS(conn, sc));
			}

		}

		return Long.valueOf(sc.getScId());
	}
	
	private PreparedStatement buildInsertDefaultUserRolePS(Connection conn, ServiceCoordinator sc) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(INSERT_USER_ROLE);
		ps.setInt(1, sc.getScId());
		return ps;
	}

	private PreparedStatement buildDeleteUserRolePS(Connection conn, ServiceCoordinator sc) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(DELETE_ROLE);
		ps.setInt(1, sc.getScId());
		return ps;
	}

	private PreparedStatement buildInsertUserRolePS(Connection conn, ServiceCoordinator sc) throws SQLException {

		if (sc.getAdmin()) {
			PreparedStatement ps = conn.prepareStatement(INSERT_ADMIN_ROLE);
			ps.setInt(1, sc.getScId());
			return ps;
		} else {

			PreparedStatement ps = conn.prepareStatement(INSERT_USER_ROLE);
			ps.setInt(1, sc.getScId());
			return ps;

		}
	}

	private PreparedStatement buildUpdateServiceCoordinatorPreparedStatement(Connection connection, ServiceCoordinator sc, String[] pkColumnNames) throws SQLException {

		String sql = UPDATE_SQL_SERVICECOORDINATOR_NONADMIN;

		if (sc.getAdmin()) {
			sql = UPDATE_SQL_SERVICECOORDINATOR_ADMIN;
		}

		PreparedStatement ps = connection.prepareStatement(sql, pkColumnNames);
		
		ps.setString(1, sc.getEmail());
		ps.setString(2, sc.getEncrytedPassword());

		if (sc.getAdmin()) {
			ps.setString(3, sc.getUserName());
		} else {			
			ps.setString(3,buildJsonSelectedProperties(sc));
			ps.setString(4, sc.getUserName());
		}

		return ps;
	}
	
	private String buildJsonSelectedProperties(final ServiceCoordinator sc) {
		
		return "[" + sc.getPropertyList()
        .stream()
        .map(a -> {
     	   if(a.getChecked()) {
     		   return String.valueOf(a.getPropertyId());
     	   }else {
     		   return null;
     	   }
        }).filter(s -> s!=null).collect(Collectors.joining(",")) + "]";
		
	}

	private PreparedStatement buildInsertServiceCoordinatorPreparedStatement(Connection connection, ServiceCoordinator sc, String[] pkColumnNames) throws SQLException {

		if (sc.getAdmin()) {
			PreparedStatement ps = connection.prepareStatement(INSERTION_SQL_SERVICECOORDINATOR_ADMINS, pkColumnNames);
			ps.setString(1, sc.getUserName());
			ps.setString(2, sc.getEncrytedPassword());
			ps.setString(3, sc.getEmail());
			return ps;
		} else {
			PreparedStatement ps = connection.prepareStatement(INSERTION_SQL_SERVICECOORDINATOR, pkColumnNames);
			ps.setString(1, sc.getUserName());
			ps.setString(2, sc.getEncrytedPassword());
			ps.setString(3, sc.getEmail());
			ps.setString(4, buildJsonSelectedProperties(sc));
			return ps;
		}

	}

	public List<Property> getAllProperty() {
		PropertyMapper rowMapper = new PropertyMapper();
		return this.getJdbcTemplate().query(PropertyMapper.PROPERTY_SQL, rowMapper);
	}

	public List<String> getAllTakenEmails() {
		return this.getJdbcTemplate().queryForList("SELECT EMAIL FROM SERVICE_COORDINATOR", String.class);
	}

	public List<String> getAllTakenUserNames() {
		return this.getJdbcTemplate().queryForList("SELECT USER_NAME FROM SERVICE_COORDINATOR", String.class);
	}

}
