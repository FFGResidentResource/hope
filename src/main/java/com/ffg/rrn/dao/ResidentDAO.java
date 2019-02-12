/**
 * 
 */
package com.ffg.rrn.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ffg.rrn.mapper.PropertyMapper;
import com.ffg.rrn.model.Property;

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
	 
	 public List<Property> getAllProperty(){		 
		 PropertyMapper rowMapper = new PropertyMapper();
		 return this.getJdbcTemplate().query(PropertyMapper.PROPERTY_SQL, rowMapper);		 
	 }

}
