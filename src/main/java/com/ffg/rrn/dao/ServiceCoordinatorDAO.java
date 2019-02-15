/**
 * 
 */
package com.ffg.rrn.dao;

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
public class ServiceCoordinatorDAO extends JdbcDaoSupport{

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
}