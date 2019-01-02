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

import com.ffg.rrn.mapper.AppUserMapper;
import com.ffg.rrn.model.AppUser;

/**
 * @author mycomputer
 *
 */
@Repository
@Transactional
public class AppUserDAO extends JdbcDaoSupport {
 
    @Autowired
    public AppUserDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }
 
    public AppUser findUserAccount(String userName) {
        // Select .. from App_User u Where u.User_Name = ?
        String sql = AppUserMapper.BASE_SQL + " where u.User_Name = ? ";
 
        Object[] params = new Object[] { userName };
        AppUserMapper mapper = new AppUserMapper();
        try {
            AppUser serviceCordInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return serviceCordInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
 
}
