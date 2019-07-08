/**
 * 
 */
package com.ffg.rrn.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ffg.rrn.model.Dashboard;
import com.ffg.rrn.model.Property;
import com.ffg.rrn.model.QuarterCount;

/**
 * @author FFGRRNTeam
 *
 *
 */
@Repository
@Transactional
public class DashboardDao extends JdbcDaoSupport {

	private static final String SQL_TOTAL_RESIDENT = "select count(*) from resident where active = 'TRUE'";

	private static final String SQL_NEW_RESIDENT_TOTAL = "select count(*) from NEW_RESIDENT_VIEW";

	private static final String SQL_ONGOING_RESIDENT_TOTAL = "select count(*) from ONGOING_RESIDENT_VIEW";

	@Autowired
	public DashboardDao(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	public Dashboard pullDashboard(Dashboard dashboard) {


		String RESIDENT_SERVED = "select count(*) from resident_served_view where \r\n" + "(\"RESQ\" = ? OR \"SSMQ\" = ? OR \"CNQ\" = ? OR \"APQ\" = ?) AND \r\n"
				+ "(\"RESY\" = ? OR \"SSMY\" = ? OR \"CNY\" = ? OR \"APY\" = ?) and \"PROP_ID\" in (:ids) ";

		String ASSESSMENT_COMPLETED = "select count(*) from assessment_completed_view where \"QUARTER\" = ? and \"YEAR\" = ? and \"PROP_ID\" in (:ids) ";

		String SQL_RESIDENT_COMPLETED_SIGNUP_PER_QUARTER = "select count(*) as count, extract(quarter from date_Added) as quarter from resident where extract(year from date_added) = ? and prop_id in (:ids) and ack_pr = 'TRUE' group by extract(quarter from date_Added) ";

		String ACTION_PLAN_BY_DOMAIN = "select count(*) from RESIDENT_ACTION_PLAN_VIEW where \"QUARTER\" = ? and \"YEAR\" = ? and \"LIFE_DOMAIN\" = ? and \"PROP_ID\" in (:ids)  group by \"LIFE_DOMAIN\" ";

		String REFERRAL_REASON = "select count(*) from REFERRAL_REASON_VIEW where \"QUARTER\" = ? and \"YEAR\" = ? and \"REASONS\" = ? and \"PROP_ID\" in (:ids)  group by \"REASONS\" ";

		List<Property> properties = dashboard.getProperties();
		Set<Integer> ids = new HashSet<Integer>();

		String idString = "";

		// How many Properties are checked on home page
		for (Property property : properties) {

			if (property.getChecked()) {
				ids.add(property.getPropertyId());

				idString = idString + String.valueOf(property.getPropertyId()) + ",";
			}
		}


		if (StringUtils.isNotEmpty(idString)) {
			SQL_RESIDENT_COMPLETED_SIGNUP_PER_QUARTER = SQL_RESIDENT_COMPLETED_SIGNUP_PER_QUARTER.replace(":ids", idString.substring(0, idString.length() - 1));
			ASSESSMENT_COMPLETED = ASSESSMENT_COMPLETED.replace(":ids", idString.substring(0, idString.length() - 1));
			RESIDENT_SERVED = RESIDENT_SERVED.replace(":ids", idString.substring(0, idString.length() - 1));
			ACTION_PLAN_BY_DOMAIN = ACTION_PLAN_BY_DOMAIN.replace(":ids", idString.substring(0, idString.length() - 1));
			REFERRAL_REASON = REFERRAL_REASON.replace(":ids", idString.substring(0, idString.length() - 1));
		} else {
			SQL_RESIDENT_COMPLETED_SIGNUP_PER_QUARTER = SQL_RESIDENT_COMPLETED_SIGNUP_PER_QUARTER.replace("and prop_id in (:ids)", "");
			ASSESSMENT_COMPLETED = ASSESSMENT_COMPLETED.replace("and \"PROP_ID\" in (:ids)", "");
			RESIDENT_SERVED = RESIDENT_SERVED.replace("and \"PROP_ID\" in (:ids)", "");
			ACTION_PLAN_BY_DOMAIN = ACTION_PLAN_BY_DOMAIN.replace("and \"PROP_ID\" in (:ids)", "");
			REFERRAL_REASON = REFERRAL_REASON.replace("and \"PROP_ID\" in (:ids)", "");
		}

		int year = Integer.parseInt(dashboard.getYear());

		// Referral Reason 1 to 14

		try {
			dashboard.setRr1q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 1, year, "Childcare/afterschool care" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr1q1Count(0l);
		}

		try {
			dashboard.setRr1q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 2, year, "Childcare/afterschool care" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr1q2Count(0l);
		}
		try {
			dashboard.setRr1q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 3, year, "Childcare/afterschool care" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr1q3Count(0l);
		}
		try {
			dashboard.setRr1q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 4, year, "Childcare/afterschool care" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr1q4Count(0l);
		}

		try {
			dashboard.setRr2q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 1, year, "Education/job training" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr2q1Count(0l);
		}

		try {
			dashboard.setRr2q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 2, year, "Education/job training" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr2q2Count(0l);
		}
		try {
			dashboard.setRr2q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 3, year, "Education/job training" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr2q3Count(0l);
		}
		try {
			dashboard.setRr2q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 4, year, "Education/job training" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr2q4Count(0l);
		}

		try {
			dashboard.setRr3q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 1, year, "Employment/job readiness" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr3q1Count(0l);
		}
		try {
			dashboard.setRr3q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 2, year, "Employment/job readiness" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr3q2Count(0l);
		}
		try {
			dashboard.setRr3q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 3, year, "Employment/job readiness" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr3q3Count(0l);
		}
		try {
			dashboard.setRr3q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 4, year, "Employment/job readiness" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr3q4Count(0l);
		}

		try {
			dashboard.setRr4q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 1, year, "Healthcare/medical issues" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr4q1Count(0l);
		}

		try {
			dashboard.setRr4q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 2, year, "Healthcare/medical issues" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr4q2Count(0l);
		}
		try {
			dashboard.setRr4q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 3, year, "Healthcare/medical issues" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr4q3Count(0l);
		}
		try {
			dashboard.setRr4q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 4, year, "Healthcare/medical issues" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr4q4Count(0l);
		}

		try {
			dashboard.setRr5q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 1, year, "Housekeeping/home management" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr5q1Count(0l);
		}

		try {
			dashboard.setRr5q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 2, year, "Housekeeping/home management" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr5q2Count(0l);
		}
		try {
			dashboard.setRr5q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 3, year, "Housekeeping/home management" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr5q3Count(0l);
		}
		try {
			dashboard.setRr5q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 4, year, "Housekeeping/home management" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr5q4Count(0l);
		}

		try {
			dashboard.setRr6q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 1, year, "Lease violation for:" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr6q1Count(0l);
		}

		try {
			dashboard.setRr6q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 2, year, "Lease violation for:" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr6q2Count(0l);
		}
		try {
			dashboard.setRr6q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 3, year, "Lease violation for:" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr6q3Count(0l);
		}
		try {
			dashboard.setRr6q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 4, year, "Lease violation for:" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr6q4Count(0l);
		}

		try {
			dashboard.setRr7q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 1, year, "Non/late payment of rent" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr7q1Count(0l);
		}

		try {
			dashboard.setRr7q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 2, year, "Non/late payment of rent" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr7q2Count(0l);
		}
		try {
			dashboard.setRr7q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 3, year, "Non/late payment of rent" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr7q3Count(0l);
		}
		try {
			dashboard.setRr7q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 4, year, "Non/late payment of rent" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr7q4Count(0l);
		}

		try {
			dashboard.setRr8q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 1, year, "Noticeable change in:" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr8q1Count(0l);
		}

		try {
			dashboard.setRr8q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 2, year, "Noticeable change in:" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr8q2Count(0l);
		}
		try {
			dashboard.setRr8q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 3, year, "Noticeable change in:" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr8q3Count(0l);
		}
		try {
			dashboard.setRr8q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 4, year, "Noticeable change in:" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr8q4Count(0l);
		}

		try {
			dashboard.setRr9q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 1, year, "Other:" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr9q1Count(0l);
		}

		try {
			dashboard.setRr9q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 2, year, "Other:" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr9q2Count(0l);
		}
		try {
			dashboard.setRr9q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 3, year, "Other:" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr9q3Count(0l);
		}
		try {
			dashboard.setRr9q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 4, year, "Other:" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr9q4Count(0l);
		}

		try {
			dashboard.setRr10q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 1, year, "Resident-to-resident conflict issues" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr10q1Count(0l);
		}

		try {
			dashboard.setRr10q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 2, year, "Resident-to-resident conflict issues" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr10q2Count(0l);
		}
		try {
			dashboard.setRr10q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 3, year, "Resident-to-resident conflict issues" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr10q3Count(0l);
		}
		try {
			dashboard.setRr10q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 4, year, "Resident-to-resident conflict issues" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr10q4Count(0l);
		}

		try {
			dashboard.setRr11q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 1, year, "Safety" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr11q1Count(0l);
		}

		try {
			dashboard.setRr11q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 2, year, "Safety" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr11q2Count(0l);
		}
		try {
			dashboard.setRr11q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 3, year, "Safety" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr11q3Count(0l);
		}
		try {
			dashboard.setRr11q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 4, year, "Safety" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr11q4Count(0l);
		}

		try {
			dashboard.setRr12q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 1, year, "Suspected abuse/domestic violence/exploitation" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr12q1Count(0l);
		}

		try {
			dashboard.setRr12q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 2, year, "Suspected abuse/domestic violence/exploitation" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr12q2Count(0l);
		}
		try {
			dashboard.setRr12q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 3, year, "Suspected abuse/domestic violence/exploitation" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr12q3Count(0l);
		}
		try {
			dashboard.setRr12q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 4, year, "Suspected abuse/domestic violence/exploitation" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr12q4Count(0l);
		}

		try {
			dashboard.setRr13q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 1, year, "Transportation" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr13q1Count(0l);
		}

		try {
			dashboard.setRr13q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 2, year, "Transportation" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr13q2Count(0l);
		}
		try {
			dashboard.setRr13q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 3, year, "Transportation" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr13q3Count(0l);
		}
		try {
			dashboard.setRr13q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 4, year, "Transportation" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr13q4Count(0l);
		}

		try {
			dashboard.setRr14q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 1, year, "Utility Shut-off, scheduled for (Date):" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr14q1Count(0l);
		}

		try {
			dashboard.setRr14q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 2, year, "Utility Shut-off, scheduled for (Date):" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr14q2Count(0l);
		}
		try {
			dashboard.setRr14q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 3, year, "Utility Shut-off, scheduled for (Date):" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr14q3Count(0l);
		}
		try {
			dashboard.setRr14q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON, new Object[] { 4, year, "Utility Shut-off, scheduled for (Date):" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr14q4Count(0l);
		}

		// REferral_reason - END

		// Action Plan developed per domain

		try {
			dashboard.setHousingQ1Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN, new Object[] { 1, year, "HOUSING" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setHousingQ1Count(0l);
		}

		try {
			dashboard.setHousingQ2Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN, new Object[] { 2, year, "HOUSING" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setHousingQ2Count(0l);
		}
		try {
			dashboard.setHousingQ3Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN, new Object[] { 3, year, "HOUSING" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setHousingQ3Count(0l);
		}
		try {
			dashboard.setHousingQ4Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN, new Object[] { 4, year, "HOUSING" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setHousingQ4Count(0l);
		}

		try {
			dashboard.setMmQ1Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN, new Object[] { 1, year, "MONEY MANAGEMENT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setMmQ1Count(0l);
		}

		try {
			dashboard.setMmQ2Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN, new Object[] { 2, year, "MONEY MANAGEMENT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setMmQ2Count(0l);
		}
		try {
			dashboard.setMmQ3Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN, new Object[] { 3, year, "MONEY MANAGEMENT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setMmQ3Count(0l);
		}
		try {
			dashboard.setMmQ4Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN, new Object[] { 4, year, "MONEY MANAGEMENT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setMmQ4Count(0l);
		}

		try {
			dashboard.setEmpQ1Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN, new Object[] { 1, year, "EMPLOYMENT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setEmpQ1Count(0l);
		}

		try {
			dashboard.setEmpQ2Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN, new Object[] { 2, year, "EMPLOYMENT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setEmpQ2Count(0l);
		}
		try {
			dashboard.setEmpQ3Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN, new Object[] { 3, year, "EMPLOYMENT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setEmpQ3Count(0l);
		}
		try {
			dashboard.setEmpQ4Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN, new Object[] { 4, year, "EMPLOYMENT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setEmpQ4Count(0l);
		}

		try {
			dashboard.setEduQ1Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN, new Object[] { 1, year, "EDUCATION" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setEduQ1Count(0l);
		}

		try {
			dashboard.setEduQ2Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN, new Object[] { 2, year, "EDUCATION" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setEduQ2Count(0l);
		}
		try {
			dashboard.setEduQ3Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN, new Object[] { 3, year, "EDUCATION" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setEduQ3Count(0l);
		}
		try {
			dashboard.setEduQ4Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN, new Object[] { 4, year, "EDUCATION" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setEduQ4Count(0l);
		}

		try {
			dashboard.setNsQ1Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN, new Object[] { 1, year, "NETWORK SUPPORT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setNsQ1Count(0l);
		}

		try {
			dashboard.setNsQ2Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN, new Object[] { 2, year, "NETWORK SUPPORT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setNsQ2Count(0l);
		}
		try {
			dashboard.setNsQ3Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN, new Object[] { 3, year, "NETWORK SUPPORT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setNsQ3Count(0l);
		}
		try {
			dashboard.setNsQ4Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN, new Object[] { 4, year, "NETWORK SUPPORT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setNsQ4Count(0l);
		}

		try {
			dashboard.setHhQ1Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN, new Object[] { 1, year, "HOUSEHOLD MANAGEMENT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setHhQ1Count(0l);
		}

		try {
			dashboard.setHhQ2Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN, new Object[] { 2, year, "HOUSEHOLD MANAGEMENT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setHhQ2Count(0l);
		}
		try {
			dashboard.setHhQ3Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN, new Object[] { 3, year, "HOUSEHOLD MANAGEMENT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setHhQ3Count(0l);
		}
		try {
			dashboard.setHhQ4Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN, new Object[] { 4, year, "HOUSEHOLD MANAGEMENT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setHhQ4Count(0l);
		}

		// REsident served per Quarter

		try {
			dashboard.setResidentServedQ1(this.getJdbcTemplate().queryForObject(RESIDENT_SERVED, new Object[] { 1, 1, 1, 1, year, year, year, year }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setResidentServedQ1(0l);
		}

		try {
			dashboard.setResidentServedQ2(this.getJdbcTemplate().queryForObject(RESIDENT_SERVED, new Object[] { 2, 2, 2, 2, year, year, year, year }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setResidentServedQ2(0l);
		}
		try {
			dashboard.setResidentServedQ3(this.getJdbcTemplate().queryForObject(RESIDENT_SERVED, new Object[] { 3, 3, 3, 3, year, year, year, year }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setResidentServedQ3(0l);
		}
		try {
			dashboard.setResidentServedQ4(this.getJdbcTemplate().queryForObject(RESIDENT_SERVED, new Object[] { 4, 4, 4, 4, year, year, year, year }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setResidentServedQ4(0l);
		}

		// Assessment Completed per Quarter

		try {
			dashboard.setQ1AssessmentComplete(this.getJdbcTemplate().queryForObject(ASSESSMENT_COMPLETED, new Object[] { 1, year }, Integer.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setQ1AssessmentComplete(0);
		}

		try {
			dashboard.setQ2AssessmentComplete(this.getJdbcTemplate().queryForObject(ASSESSMENT_COMPLETED, new Object[] { 2, year }, Integer.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setQ2AssessmentComplete(0);
		}

		try {
			dashboard.setQ3AssessmentComplete(this.getJdbcTemplate().queryForObject(ASSESSMENT_COMPLETED, new Object[] { 3, year }, Integer.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setQ3AssessmentComplete(0);
		}

		try {
			dashboard.setQ4AssessmentComplete(this.getJdbcTemplate().queryForObject(ASSESSMENT_COMPLETED, new Object[] { 4, year }, Integer.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setQ4AssessmentComplete(0);
		}

		List<QuarterCount> qcList = new ArrayList<QuarterCount>();
		qcList = this.getJdbcTemplate().query(SQL_RESIDENT_COMPLETED_SIGNUP_PER_QUARTER, new Object[] { Integer.parseInt(dashboard.getYear()) }, (rs, rowNumber) -> {
			try {
				QuarterCount qc = new QuarterCount();
				qc.setQuarter(rs.getInt("quarter"));
				qc.setCount(rs.getInt("count"));

				return qc;
			} catch (SQLException e) {
				throw new RuntimeException("your error message", e); // or other unchecked exception here
			}
		});

		for (QuarterCount quarterCount : qcList) {

			if (quarterCount.getQuarter() == 1) {
				dashboard.setQ1SignUpComplete(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 2) {
				dashboard.setQ2SignUpComplete(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 3) {
				dashboard.setQ3SignUpComplete(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 4) {
				dashboard.setQ4SignUpComplete(quarterCount.getCount());
			}
		}


		return dashboard;
	}

	public Long getTotalActiveResident() {
		return this.getJdbcTemplate().queryForObject(SQL_TOTAL_RESIDENT, Long.class);
	}

	public Long getNewResidents() {
		return this.getJdbcTemplate().queryForObject(SQL_NEW_RESIDENT_TOTAL, Long.class);
	}

	public Long getOngoingResidents() {
		return this.getJdbcTemplate().queryForObject(SQL_ONGOING_RESIDENT_TOTAL, Long.class);
	}

}
