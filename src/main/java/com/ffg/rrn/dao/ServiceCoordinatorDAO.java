/**
 * 
 */
package com.ffg.rrn.dao;

import static java.lang.Math.toIntExact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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

	private final static String INSERTION_SQL_SERVICECOORDINATOR = "INSERT INTO SERVICE_COORDINATOR " + "(SC_ID, USER_NAME, ENCRYPTED_PASSWORD, ACTIVE, EMAIL, CREATED_ON, PROP_ID)"
			+ " VALUES (nextval('SC_SQ'), ?,?,true,?, NOW(), ?)";
	
	private final static String INSERTION_SQL_SERVICECOORDINATOR_ADMINS = "INSERT INTO SERVICE_COORDINATOR " + "(SC_ID, USER_NAME, ENCRYPTED_PASSWORD, ACTIVE, EMAIL, CREATED_ON)"
			+ " VALUES (nextval('SC_SQ'), ?,?,true,?, NOW())";

	private final static String UPDATE_SQL_SERVICECOORDINATOR = "UPDATE SERVICE_COORDINATOR SET EMAIL = ?, ENCRYPTED_PASSWORD = ? , PROP_ID = ?, MODIFIED_DATE = NOW() where USER_NAME = ? ";

	private final static String INACTIVATE_SQL_SERVICECOORDINATOR = "UPDATE SERVICE_COORDINATOR SET ACTIVE = ?, MODIFIED_DATE = NOW() where USER_NAME = ? ";

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

	public List<ServiceCoordinator> getAllServiceCoordinators() {
		ServiceCoordinatorMapper rowMapper = new ServiceCoordinatorMapper();
		return this.getJdbcTemplate().query(ServiceCoordinatorMapper.BASE_SQL, rowMapper);
	}

	public Long inactivateOrActivateSC(String userName, boolean active) {

		final KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] pkColumnNames = new String[] { "sc_id" };

		this.getJdbcTemplate().update(conn -> buildInactivatePS(conn, userName, active, pkColumnNames), keyHolder);

		return keyHolder.getKey().longValue();
	}

	private PreparedStatement buildInactivatePS(Connection conn, String userName, boolean active, String[] pkColumnNames) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(INACTIVATE_SQL_SERVICECOORDINATOR, pkColumnNames);

		ps.setBoolean(1, active);
		ps.setString(2, userName);

		return ps;
	}

	public Long saveServiceCoordinator(ServiceCoordinator sc) {

		final KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] pkColumnNames = new String[] { "sc_id" };

		try {
			String userName = this.getJdbcTemplate().queryForObject("select user_name from service_coordinator where user_name = ? ", new Object[] { sc.getUserName() }, String.class);

			if (userName != null) {

				this.getJdbcTemplate().update(conn -> buildUpdateServiceCoordinatorPreparedStatement(conn, sc, pkColumnNames), keyHolder);
			}
		}
		// When no resident found in action_plan we do fresh insert
		catch (EmptyResultDataAccessException e) {
			this.getJdbcTemplate().update(conn -> buildInsertServiceCoordinatorPreparedStatement(conn, sc, pkColumnNames), keyHolder);
		}

		long scId = keyHolder.getKey().longValue();
		sc.setScId(toIntExact(scId));

		return scId;
	}
	
	private PreparedStatement buildUpdateServiceCoordinatorPreparedStatement(Connection connection, ServiceCoordinator sc, String[] pkColumnNames) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(UPDATE_SQL_SERVICECOORDINATOR, pkColumnNames);
		
		ps.setString(1, sc.getEmail());
		ps.setString(2, sc.getEncrytedPassword());
		ps.setInt(3, (sc.getPropertyId() == 0) ? null : sc.getPropertyId());
		ps.setString(4, sc.getUserName());
		return ps;
	}

	private PreparedStatement buildInsertServiceCoordinatorPreparedStatement(Connection connection, ServiceCoordinator sc, String[] pkColumnNames) throws SQLException {

		if (sc.getPropertyId() == 0) {
			PreparedStatement ps = connection.prepareStatement(INSERTION_SQL_SERVICECOORDINATOR, pkColumnNames);
			ps.setString(1, sc.getUserName());
			ps.setString(2, sc.getEncrytedPassword());
			ps.setString(3, sc.getEmail());
			return ps;
		} else {
			PreparedStatement ps = connection.prepareStatement(INSERTION_SQL_SERVICECOORDINATOR_ADMINS, pkColumnNames);
			ps.setString(1, sc.getUserName());
			ps.setString(2, sc.getEncrytedPassword());
			ps.setString(3, sc.getEmail());
			ps.setInt(4, sc.getPropertyId());
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
