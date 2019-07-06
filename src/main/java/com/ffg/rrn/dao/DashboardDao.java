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

	private static final String SQL_REFERRAL_REASON_1 = "select count(*) as count , extract(quarter from date_Added) as quarter from referral_form "
			+ " where (referral_reason ->> 'Childcare/afterschool care') not in ('false', '') and extract(year from date_added) = ? " + "  group by extract(quarter from date_Added)";

	private static final String SQL_REFERRAL_REASON_2 = "select count(*) as count , extract(quarter from date_Added) as quarter from referral_form "
			+ " where (referral_reason ->> 'Education/job training') not in ('false', '') and extract(year from date_added) = ? " + "  group by extract(quarter from date_Added)";

	private static final String SQL_REFERRAL_REASON_3 = "select count(*) as count , extract(quarter from date_Added) as quarter from referral_form "
			+ " where (referral_reason ->> 'Employment/job readiness') not in ('false', '') and extract(year from date_added) = ? " + "  group by extract(quarter from date_Added)";

	private static final String SQL_REFERRAL_REASON_4 = "select count(*) as count , extract(quarter from date_Added) as quarter from referral_form "
			+ " where (referral_reason ->> 'Healthcare/medical issues') not in ('false', '') and extract(year from date_added) = ? " + "  group by extract(quarter from date_Added)";

	private static final String SQL_REFERRAL_REASON_5 = "select count(*) as count , extract(quarter from date_Added) as quarter from referral_form "
			+ " where (referral_reason ->> 'Housekeeping/home management') not in ('false', '') and extract(year from date_added) = ? " + "  group by extract(quarter from date_Added)";

	private static final String SQL_REFERRAL_REASON_6 = "select count(*) as count , extract(quarter from date_Added) as quarter from referral_form "
			+ " where (referral_reason ->> 'Lease violation for:') not in ('false', '') and extract(year from date_added) = ? " + "  group by extract(quarter from date_Added)";

	private static final String SQL_REFERRAL_REASON_7 = "select count(*) as count , extract(quarter from date_Added) as quarter from referral_form "
			+ " where (referral_reason ->> 'Non/late payment of rent') not in ('false', '') and extract(year from date_added) = ? " + "  group by extract(quarter from date_Added)";

	private static final String SQL_REFERRAL_REASON_8 = "select count(*) as count , extract(quarter from date_Added) as quarter from referral_form "
			+ " where (referral_reason ->> 'Noticeable change in:') not in ('false', '') and extract(year from date_added) = ? " + "  group by extract(quarter from date_Added)";

	private static final String SQL_REFERRAL_REASON_9 = "select count(*) as count , extract(quarter from date_Added) as quarter from referral_form "
			+ " where (referral_reason ->> 'Other:') not in ('false', '') and extract(year from date_added) = ? " + "  group by extract(quarter from date_Added)";

	private static final String SQL_REFERRAL_REASON_10 = "select count(*) as count , extract(quarter from date_Added) as quarter from referral_form "
			+ " where (referral_reason ->> 'Resident-to-resident conflict issues') not in ('false', '') and extract(year from date_added) = ? " + "  group by extract(quarter from date_Added)";

	private static final String SQL_REFERRAL_REASON_11 = "select count(*) as count , extract(quarter from date_Added) as quarter from referral_form "
			+ " where (referral_reason ->> 'Safety') not in ('false', '') and extract(year from date_added) = ? " + "  group by extract(quarter from date_Added)";

	private static final String SQL_REFERRAL_REASON_12 = "select count(*) as count , extract(quarter from date_Added) as quarter from referral_form "
			+ " where (referral_reason ->> 'Suspected abuse/domestic violence/exploitation') not in ('false', '') and extract(year from date_added) = ? "
			+ "  group by extract(quarter from date_Added)";

	private static final String SQL_REFERRAL_REASON_13 = "select count(*) as count , extract(quarter from date_Added) as quarter from referral_form "
			+ " where (referral_reason ->> 'Transportation') not in ('false', '') and extract(year from date_added) = ? " + "  group by extract(quarter from date_Added)";

	private static final String SQL_REFERRAL_REASON_14 = "select count(*) as count , extract(quarter from date_Added) as quarter from referral_form "
			+ " where (referral_reason ->> 'Utility Shut-off, scheduled for (Date):') not in ('false', '') and extract(year from date_added) = ? " + "  group by extract(quarter from date_Added)";

	private static final String SQL_HOUSING_COUNT = "select count(*) as count, extract(quarter from date_Added) as quarter FROM ACTION_PLAN "
			+ " where  plan_of_action->>'HOUSING' not in ('') and extract (year from date_added) = ?" + " GROUP BY extract(quarter from date_Added)";

	private static final String SQL_MM_COUNT = "select count(*) as count, extract(quarter from date_Added) as quarter FROM ACTION_PLAN "
			+ " where  plan_of_action->>'MONEY MANAGEMENT' not in ('') and extract (year from date_added) = ?" + " GROUP BY extract(quarter from date_Added)";

	private static final String SQL_EMP_COUNT = "select count(*) as count, extract(quarter from date_Added) as quarter FROM ACTION_PLAN "
			+ " where  plan_of_action->>'EMPLOYMENT' not in ('') and extract (year from date_added) = ?" + " GROUP BY extract(quarter from date_Added)";

	private static final String SQL_EDU_COUNT = "select count(*) as count, extract(quarter from date_Added) as quarter FROM ACTION_PLAN "
			+ " where  plan_of_action->>'EDUCATION' not in ('') and extract (year from date_added) = ?" + " GROUP BY extract(quarter from date_Added)";

	private static final String SQL_NS_COUNT = "select count(*) as count, extract(quarter from date_Added) as quarter FROM ACTION_PLAN "
			+ " where  plan_of_action->>'NETWORK SUPPORT' not in ('') and extract (year from date_added) = ?" + " GROUP BY extract(quarter from date_Added)";

	private static final String SQL_HH_COUNT = "select count(*) as count, extract(quarter from date_Added) as quarter FROM ACTION_PLAN "
			+ " where  plan_of_action->>'HOUSEHOLD MANAGEMENT' not in ('') and extract (year from date_added) = ?" + " GROUP BY extract(quarter from date_Added)";

	private static final String SQL_NEW_RESIDENT_TOTAL = "select count(*) from NEW_RESIDENT_VIEW";

	private static final String SQL_ONGOING_RESIDENT_TOTAL = "select count(*) from ONGOING_RESIDENT_VIEW";

	@Autowired
	public DashboardDao(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	public Dashboard pullDashboard(Dashboard dashboard) {


		String RESIDENT_SERVED = "select count(*) from resident_served_view where \r\n" + "(\"RESQ\" = ? OR \"SSMQ\" = ? OR \"CNQ\" = ? OR \"APQ\" = ?) AND \r\n"
				+ "(\"RESY\" = ? OR \"SSMY\" = ? OR \"CNY\" = ? OR \"APY\" = ?)";

		int year = Integer.parseInt(dashboard.getYear());

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

		String ASSESSMENT_COMPLETED = "select count(*) from assessment_completed_view where \"QUARTER\" = ? and \"YEAR\" = ? and \"PROP_ID\" in (:ids) ";

		String SQL_RESIDENT_COMPLETED_SIGNUP_PER_QUARTER = "select count(*) as count, extract(quarter from date_Added) as quarter from resident where extract(year from date_added) = ? and prop_id in  (:ids) and ack_pr = 'TRUE' group by extract(quarter from date_Added) ";

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
		} else {
			SQL_RESIDENT_COMPLETED_SIGNUP_PER_QUARTER = SQL_RESIDENT_COMPLETED_SIGNUP_PER_QUARTER.replace("and prop_id in (:ids)", "");
			ASSESSMENT_COMPLETED = ASSESSMENT_COMPLETED.replace("and \"PROP_ID\" in (:ids)", "");
		}

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

		/* REferral Reasons Begin 1 to 14 */

		qcList = new ArrayList<QuarterCount>();
		qcList = this.getJdbcTemplate().query(SQL_REFERRAL_REASON_1, new Object[] { Integer.parseInt(dashboard.getYear()) }, (rs, rowNumber) -> {
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
				dashboard.setRr1q1Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 2) {
				dashboard.setRr1q2Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 3) {
				dashboard.setRr1q3Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 4) {
				dashboard.setRr1q4Count(quarterCount.getCount());
			}
		}

		qcList = new ArrayList<QuarterCount>();
		qcList = this.getJdbcTemplate().query(SQL_REFERRAL_REASON_2, new Object[] { Integer.parseInt(dashboard.getYear()) }, (rs, rowNumber) -> {
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
				dashboard.setRr2q1Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 2) {
				dashboard.setRr2q2Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 3) {
				dashboard.setRr2q3Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 4) {
				dashboard.setRr2q4Count(quarterCount.getCount());
			}
		}

		qcList = new ArrayList<QuarterCount>();
		qcList = this.getJdbcTemplate().query(SQL_REFERRAL_REASON_3, new Object[] { Integer.parseInt(dashboard.getYear()) }, (rs, rowNumber) -> {
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
				dashboard.setRr3q1Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 2) {
				dashboard.setRr3q2Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 3) {
				dashboard.setRr3q3Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 4) {
				dashboard.setRr3q4Count(quarterCount.getCount());
			}
		}

		qcList = new ArrayList<QuarterCount>();
		qcList = this.getJdbcTemplate().query(SQL_REFERRAL_REASON_4, new Object[] { Integer.parseInt(dashboard.getYear()) }, (rs, rowNumber) -> {
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
				dashboard.setRr4q1Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 2) {
				dashboard.setRr4q2Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 3) {
				dashboard.setRr4q3Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 4) {
				dashboard.setRr4q4Count(quarterCount.getCount());
			}
		}

		qcList = new ArrayList<QuarterCount>();
		qcList = this.getJdbcTemplate().query(SQL_REFERRAL_REASON_5, new Object[] { Integer.parseInt(dashboard.getYear()) }, (rs, rowNumber) -> {
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
				dashboard.setRr5q1Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 2) {
				dashboard.setRr5q2Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 3) {
				dashboard.setRr5q3Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 4) {
				dashboard.setRr5q4Count(quarterCount.getCount());
			}
		}

		qcList = new ArrayList<QuarterCount>();
		qcList = this.getJdbcTemplate().query(SQL_REFERRAL_REASON_6, new Object[] { Integer.parseInt(dashboard.getYear()) }, (rs, rowNumber) -> {
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
				dashboard.setRr6q1Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 2) {
				dashboard.setRr6q2Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 3) {
				dashboard.setRr6q3Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 4) {
				dashboard.setRr6q4Count(quarterCount.getCount());
			}
		}

		qcList = new ArrayList<QuarterCount>();
		qcList = this.getJdbcTemplate().query(SQL_REFERRAL_REASON_7, new Object[] { Integer.parseInt(dashboard.getYear()) }, (rs, rowNumber) -> {
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
				dashboard.setRr7q1Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 2) {
				dashboard.setRr7q2Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 3) {
				dashboard.setRr7q3Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 4) {
				dashboard.setRr7q4Count(quarterCount.getCount());
			}
		}

		qcList = new ArrayList<QuarterCount>();
		qcList = this.getJdbcTemplate().query(SQL_REFERRAL_REASON_8, new Object[] { Integer.parseInt(dashboard.getYear()) }, (rs, rowNumber) -> {
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
				dashboard.setRr8q1Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 2) {
				dashboard.setRr8q2Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 3) {
				dashboard.setRr8q3Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 4) {
				dashboard.setRr8q4Count(quarterCount.getCount());
			}
		}

		qcList = new ArrayList<QuarterCount>();
		qcList = this.getJdbcTemplate().query(SQL_REFERRAL_REASON_9, new Object[] { Integer.parseInt(dashboard.getYear()) }, (rs, rowNumber) -> {
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
				dashboard.setRr9q1Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 2) {
				dashboard.setRr9q2Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 3) {
				dashboard.setRr9q3Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 4) {
				dashboard.setRr9q4Count(quarterCount.getCount());
			}
		}

		qcList = new ArrayList<QuarterCount>();
		qcList = this.getJdbcTemplate().query(SQL_REFERRAL_REASON_10, new Object[] { Integer.parseInt(dashboard.getYear()) }, (rs, rowNumber) -> {
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
				dashboard.setRr10q1Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 2) {
				dashboard.setRr10q2Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 3) {
				dashboard.setRr10q3Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 4) {
				dashboard.setRr10q4Count(quarterCount.getCount());
			}
		}

		qcList = new ArrayList<QuarterCount>();
		qcList = this.getJdbcTemplate().query(SQL_REFERRAL_REASON_11, new Object[] { Integer.parseInt(dashboard.getYear()) }, (rs, rowNumber) -> {
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
				dashboard.setRr11q1Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 2) {
				dashboard.setRr11q2Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 3) {
				dashboard.setRr11q3Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 4) {
				dashboard.setRr11q4Count(quarterCount.getCount());
			}
		}

		qcList = new ArrayList<QuarterCount>();
		qcList = this.getJdbcTemplate().query(SQL_REFERRAL_REASON_12, new Object[] { Integer.parseInt(dashboard.getYear()) }, (rs, rowNumber) -> {
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
				dashboard.setRr12q1Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 2) {
				dashboard.setRr12q2Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 3) {
				dashboard.setRr12q3Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 4) {
				dashboard.setRr12q4Count(quarterCount.getCount());
			}
		}

		qcList = new ArrayList<QuarterCount>();
		qcList = this.getJdbcTemplate().query(SQL_REFERRAL_REASON_13, new Object[] { Integer.parseInt(dashboard.getYear()) }, (rs, rowNumber) -> {
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
				dashboard.setRr13q1Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 2) {
				dashboard.setRr13q2Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 3) {
				dashboard.setRr13q3Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 4) {
				dashboard.setRr13q4Count(quarterCount.getCount());
			}
		}

		qcList = new ArrayList<QuarterCount>();
		qcList = this.getJdbcTemplate().query(SQL_REFERRAL_REASON_14, new Object[] { Integer.parseInt(dashboard.getYear()) }, (rs, rowNumber) -> {
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
				dashboard.setRr14q1Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 2) {
				dashboard.setRr14q2Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 3) {
				dashboard.setRr14q3Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 4) {
				dashboard.setRr14q4Count(quarterCount.getCount());
			}
		}

		qcList = new ArrayList<QuarterCount>();
		qcList = this.getJdbcTemplate().query(SQL_HOUSING_COUNT, new Object[] { Integer.parseInt(dashboard.getYear()) }, (rs, rowNumber) -> {
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
				dashboard.setHousingQ1Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 2) {
				dashboard.setHousingQ2Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 3) {
				dashboard.setHousingQ3Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 4) {
				dashboard.setHousingQ4Count(quarterCount.getCount());
			}
		}

		qcList = new ArrayList<QuarterCount>();
		qcList = this.getJdbcTemplate().query(SQL_MM_COUNT, new Object[] { Integer.parseInt(dashboard.getYear()) }, (rs, rowNumber) -> {
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
				dashboard.setMmQ1Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 2) {
				dashboard.setMmQ2Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 3) {
				dashboard.setMmQ3Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 4) {
				dashboard.setMmQ4Count(quarterCount.getCount());
			}
		}

		qcList = new ArrayList<QuarterCount>();
		qcList = this.getJdbcTemplate().query(SQL_EMP_COUNT, new Object[] { Integer.parseInt(dashboard.getYear()) }, (rs, rowNumber) -> {
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
				dashboard.setEmpQ1Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 2) {
				dashboard.setEmpQ2Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 3) {
				dashboard.setEmpQ3Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 4) {
				dashboard.setEmpQ4Count(quarterCount.getCount());
			}
		}

		qcList = new ArrayList<QuarterCount>();
		qcList = this.getJdbcTemplate().query(SQL_EDU_COUNT, new Object[] { Integer.parseInt(dashboard.getYear()) }, (rs, rowNumber) -> {
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
				dashboard.setEduQ1Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 2) {
				dashboard.setEduQ2Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 3) {
				dashboard.setEduQ3Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 4) {
				dashboard.setEduQ4Count(quarterCount.getCount());
			}
		}

		qcList = new ArrayList<QuarterCount>();
		qcList = this.getJdbcTemplate().query(SQL_NS_COUNT, new Object[] { Integer.parseInt(dashboard.getYear()) }, (rs, rowNumber) -> {
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
				dashboard.setNsQ1Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 2) {
				dashboard.setNsQ2Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 3) {
				dashboard.setNsQ3Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 4) {
				dashboard.setNsQ4Count(quarterCount.getCount());
			}
		}

		qcList = new ArrayList<QuarterCount>();
		qcList = this.getJdbcTemplate().query(SQL_HH_COUNT, new Object[] { Integer.parseInt(dashboard.getYear()) }, (rs, rowNumber) -> {
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
				dashboard.setHhQ1Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 2) {
				dashboard.setHhQ2Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 3) {
				dashboard.setHhQ3Count(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 4) {
				dashboard.setHhQ4Count(quarterCount.getCount());
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
