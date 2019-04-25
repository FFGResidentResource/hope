/**
 * 
 */
package com.ffg.rrn.dao;

import com.ffg.rrn.model.ActionPlan;
import com.ffg.rrn.model.CaseNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

/**
 * @author FFGRRNTeam
 *
 */
@Repository
@Transactional
public class CaseNoteDAO extends JdbcDaoSupport {

    @Autowired
    public CaseNoteDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    private static final String SQL_INSERT_CASE_NOTE= "";

    /**
     *
     * @Params action plan to save
     */
    public int saveActionPlan(CaseNote actioPlan) {
        int count = this.getJdbcTemplate().update(
                SQL_INSERT_CASE_NOTE);
        return count;
    }
     
}