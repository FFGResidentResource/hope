/**
 * 
 */
package com.ffg.rrn.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author FFGRRNTeam
 *
 */
@Repository
@Transactional
public class ResidentDAO extends JdbcDaoSupport  {
	
	 @Autowired
	    public ResidentDAO(DataSource dataSource) {
	        this.setDataSource(dataSource);
	    }

}
