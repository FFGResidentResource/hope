/**
 * 
 */
package com.ffg.rrn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.lang.Math.toIntExact;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ffg.rrn.mapper.ServiceCoordinatorMapper;
import com.ffg.rrn.model.Resident;
import com.ffg.rrn.model.ServiceCoordinator;

/**
 * @author FFGRRNTeam
 *
 */
@Repository
@Transactional
public class ServiceCoordinatorDAO extends JdbcDaoSupport {

	private final static String INSERTION_SQL_SERVICECOORDINATOR="INSERT INTO SERVICE_COORDINATOR (SC_ID, USER_NAME, ENCRYPTED_PASSWORD, ACTIVE, EMAIL)"
			+ " VALUES (nextval('SC_SQ'), ?,?,true,?)";
	
	@Autowired
	public ServiceCoordinatorDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	public ServiceCoordinator findUserAccount(String userName) {
		// Select .. from App_User u Where u.User_Name = ?
		String sql = ServiceCoordinatorMapper.BASE_SQL + " where u.User_Name = ? ";

		Object[] params = new Object[] { userName };
		ServiceCoordinatorMapper mapper = new ServiceCoordinatorMapper();
		try {
			ServiceCoordinator serviceCordInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
			return serviceCordInfo;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public void updateServiceCoordinator(List <ServiceCoordinator> scIDs, boolean active) {
		String sql = "UPDATE SERVICE_COORDINATOR SET ACTIVE = :active WHERE SC_ID in (:scIDs)";
		//String sql = "UPDATE SERVICE_COORDINATOR SET ACTIVE = :active WHERE SC_ID = ? ";
		Map parameters = new HashMap<String, Object>();
		parameters.put("active", active);
		parameters.put("scIDs", scIDs);
		//Object[] params = new Object[] { scID };
		this.getJdbcTemplate().update(sql, parameters);
	}
	
	public List<ServiceCoordinator> getAllServiceCoordinators() {
		ServiceCoordinatorMapper rowMapper = new ServiceCoordinatorMapper();
		return this.getJdbcTemplate().query(ServiceCoordinatorMapper.BASE_SQL, rowMapper);
	}

	public Long saveServiceCoordinator(ServiceCoordinator sc) {
		// TODO Auto-generated method stub
		
		Long scID = Long.valueOf(sc.getScId());

		
		scID = insertNewServiceCoordinator(sc);

		return scID;
	}
	
	private long insertNewServiceCoordinator(ServiceCoordinator sc) {
		
		final KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] pkColumnNames = new String[] {"user_id"};
		this.getJdbcTemplate().update(conn ->buildInsertServiceCoordinatorPreparedStatement(conn, sc, pkColumnNames), keyHolder);

		long newSCId = keyHolder.getKey().longValue();
		sc.setScId(toIntExact(newSCId));
/**		if (newSCId > 0) {
			insertNewChildren(resident);
		} */

		return newSCId;
	}
	
	private PreparedStatement buildInsertServiceCoordinatorPreparedStatement(Connection connection, ServiceCoordinator sc, String[] pkColumnNames) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(INSERTION_SQL_SERVICECOORDINATOR, pkColumnNames);
		ps.setString(1, sc.getUserName());
		ps.setString(2, sc.getEncrytedPassword());
		ps.setString(3, sc.getEmail());
		return ps;
	}

}
