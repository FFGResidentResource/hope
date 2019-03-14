/**
 * 
 */
package com.ffg.rrn.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ffg.rrn.mapper.ServiceCoordinatorMapper;
import com.ffg.rrn.model.ServiceCoordinator;

/**
 * @author FFGRRNTeam
 *
 */
@Repository
@Transactional
public class ServiceCoordinatorDAO extends JdbcDaoSupport {

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

	public void updateServiceCoordinator(List<Integer> scIDs, boolean active) {
		String sql = "UPDATE SERVICE_COORDINATOR SET ACTIVE = :active WHERE SC_ID in (:scIDs)";
		Map parameters = new HashMap<String, Object>();
		parameters.put("active", active);
		parameters.put("scIDs", scIDs);
		this.getJdbcTemplate().update(sql, parameters);
	}
	
	public List<ServiceCoordinator> getAllServiceCoordinators() {
		ServiceCoordinatorMapper rowMapper = new ServiceCoordinatorMapper();
		return this.getJdbcTemplate().query(ServiceCoordinatorMapper.BASE_SQL, rowMapper);
	}

}
