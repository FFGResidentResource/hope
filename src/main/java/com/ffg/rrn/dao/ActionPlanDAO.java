package com.ffg.rrn.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ffg.rrn.model.ActionPlan;

@Repository
@Transactional
public class ActionPlanDAO extends JdbcDaoSupport{
	
	private final static String SQL_INSERT_ACTION_PLAN= "INSERT ACTION_PLAN (ACTION_PLAN_ID, RESIDENT_ID, RESIDENT_CONCERN, ACTIVE) VALUES (?,?,?,?)";
	
	
	/**
	 * 
	 * @param Action plan
	 */
	public int saveActionPlan(List<ActionPlan> actioPlans) {

		int[] count=this.getJdbcTemplate().batchUpdate(SQL_INSERT_ACTION_PLAN, new BatchPreparedStatementSetter(){
			  public void setValues(PreparedStatement ps, int i) throws SQLException{
			     ActionPlan  actionplan =actioPlans.get(i);
			     ps.setInt(1,actionplan.getActionPlanId());
			     ps.setInt(2,actionplan.getResidentId());
			     ps.setString(3,actionplan.getResidentConcern());
			     ps.setBoolean(4,actionplan.getActive());
			    }

			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return actioPlans.size();
			}
			   
			});
		

		return count.length;
	}

}
