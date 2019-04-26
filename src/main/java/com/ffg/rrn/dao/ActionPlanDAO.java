/**
 * 
 */
package com.ffg.rrn.dao;

import com.ffg.rrn.model.ActionPlan;
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
public class ActionPlanDAO extends JdbcDaoSupport {

    private static final String SQL_INSERT_ACTION_PLAN= "INSERT ACTION_PLAN (ACTION_PLAN_ID, RESIDENT_ID, RESIDENT_CONCERN, ACTIVE, FOCUS_ON_DOMAIN, OUTCOME_DATE, FOLLOWUP_NOTES, DATE_ADDED, DATE_MODIFIED, SERVICE_COORD) VALUES (?,?,?,?,?,?,?,?,?,?)";

    // TODO: need to change to actual update field later 4/24/19
    private static final String SQL_UPDATE_ACTION_PLAN = "UPDATE ACTION_PLAN SET RESIDENT_CONCERN=? WHERE ACTION_PLAN_ID=?";

    @Autowired
    public ActionPlanDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    /**
     *
     * @Params action plan to save
     */
    public int saveActionPlan(ActionPlan actioPlan) {
        int count = this.getJdbcTemplate().update(
                SQL_INSERT_ACTION_PLAN, actioPlan.getActionPlanId(),
                actioPlan.getResidentId(),
                actioPlan.getResidentConcern(),
                actioPlan.getActive(),
				actioPlan.getFocusOnDomain(),
				actioPlan.getOutcomeDate(),
				actioPlan.getFollowupNotes(),
				actioPlan.getDateAdded(),
				actioPlan.getDateModified(),
				actioPlan.getServiceCoord());
        return count;
    }

    /**
     *
     * @Params action plan to update
     */
    public int updateActionPlan(ActionPlan actioPlan) {
        String mockUpdateBody = "{\n" +
                "  \"housing\": \"is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem\",\n" +
                "  \"Anticipated outcome\": {\n" +
                "    \"time\" : \"2019\",\n" +
                "    \"action\": \"unknown\",\n" +
                "    \"actor\": \"self\"\n" +
                "  },\n" +
                "  \"string\": \"Hello World\"\n" +
                "}";
        int count = this.getJdbcTemplate().update(SQL_UPDATE_ACTION_PLAN, mockUpdateBody, actioPlan.getActionPlanId());
        return count;
    }
     
}
