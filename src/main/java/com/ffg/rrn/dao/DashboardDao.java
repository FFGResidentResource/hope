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

	@Autowired
	public DashboardDao(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	public Dashboard pullDashboard(Dashboard dashboard) {

		String SQL_ALL_RESIDENT_PER_QUARTER = "select extract(quarter from date_Added) as quarter, count(*) as count from resident where extract(year from date_added) = ? and prop_id in (:ids) group by extract(quarter from date_Added)";

		String SQL_ALL_UNITS_PER_QUARTER = "select count(*) * p.unit as count, extract(quarter from date_Added) as quarter from resident r join Property p on r.prop_id = p.prop_id "
				+ " 	where extract(year from date_added) = ? and p.prop_id in (:ids) group by p.unit, extract(quarter from date_Added)";

		String SQL_ASSESSMENT_COMPLETED = "select count(*) as count, extract(quarter from on_this_date) as quarter from resident_score_goal rsg"
				+ " join resident r on r.resident_id = rsg.resident_id and r.prop_id in (:ids) where extract(year from on_this_Date) = ? group by extract(quarter from on_this_Date)";

		String SQL_RESIDENT_COMPLETED_SIGNUP_PER_QUARTER = "select count(*) as count, extract(quarter from date_Added) as quarter from resident where extract(year from date_added) = ? and prop_id in  (:ids) and ack_pr = 'TRUE' group by extract(quarter from date_Added) ";

		String SQL_NO_OF_PROP_ACTIVE_RESIDENT = "";

		List<Property> properties = dashboard.getProperties();
		Set<Integer> ids = new HashSet<Integer>();

		String idString = "";

		for (Property property : properties) {

			if (property.getChecked()) {
				ids.add(property.getPropertyId());

				idString = idString + String.valueOf(property.getPropertyId()) + ",";
			}
		}

		if (StringUtils.isNotEmpty(idString)) {
			SQL_ALL_RESIDENT_PER_QUARTER = SQL_ALL_RESIDENT_PER_QUARTER.replace(":ids", idString.substring(0, idString.length() - 1));
			SQL_ALL_UNITS_PER_QUARTER = SQL_ALL_UNITS_PER_QUARTER.replace(":ids", idString.substring(0, idString.length() - 1));
			SQL_RESIDENT_COMPLETED_SIGNUP_PER_QUARTER = SQL_RESIDENT_COMPLETED_SIGNUP_PER_QUARTER.replace(":ids", idString.substring(0, idString.length() - 1));
			SQL_ASSESSMENT_COMPLETED = SQL_ASSESSMENT_COMPLETED.replace(":ids", idString.substring(0, idString.length() - 1));
		} else {
			SQL_ALL_RESIDENT_PER_QUARTER = SQL_ALL_RESIDENT_PER_QUARTER.replace("and prop_id in (:ids)", "");
			SQL_ALL_UNITS_PER_QUARTER = SQL_ALL_UNITS_PER_QUARTER.replace("and p.prop_id in (:ids)", "");
			SQL_RESIDENT_COMPLETED_SIGNUP_PER_QUARTER = SQL_RESIDENT_COMPLETED_SIGNUP_PER_QUARTER.replace("and prop_id in (:ids)", "");
			SQL_ASSESSMENT_COMPLETED = SQL_ASSESSMENT_COMPLETED.replace("and r.prop_id in (:ids)", "");
		}

		List<QuarterCount> qcList = this.getJdbcTemplate().query(SQL_ALL_RESIDENT_PER_QUARTER, new Object[] { Integer.parseInt(dashboard.getYear()) }, (rs, rowNumber) -> {
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
				dashboard.setQ1Residents(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 2) {
				dashboard.setQ2Residents(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 3) {
				dashboard.setQ3Residents(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 4) {
				dashboard.setQ4Residents(quarterCount.getCount());
			}
		}

		qcList = new ArrayList<QuarterCount>();
		qcList = this.getJdbcTemplate().query(SQL_ALL_UNITS_PER_QUARTER, new Object[] { Integer.parseInt(dashboard.getYear()) }, (rs, rowNumber) -> {
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
				dashboard.setQ1Units(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 2) {
				dashboard.setQ2Units(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 3) {
				dashboard.setQ3Units(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 4) {
				dashboard.setQ4Units(quarterCount.getCount());
			}
		}

		qcList = new ArrayList<QuarterCount>();
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

		qcList = new ArrayList<QuarterCount>();
		qcList = this.getJdbcTemplate().query(SQL_ASSESSMENT_COMPLETED, new Object[] { Integer.parseInt(dashboard.getYear()) }, (rs, rowNumber) -> {
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
				dashboard.setQ1AssessmentComplete(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 2) {
				dashboard.setQ2AssessmentComplete(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 3) {
				dashboard.setQ3AssessmentComplete(quarterCount.getCount());
			}
			if (quarterCount.getQuarter() == 4) {
				dashboard.setQ4AssessmentComplete(quarterCount.getCount());
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

		Integer total = this.getJdbcTemplate().queryForObject(SQL_TOTAL_RESIDENT, Integer.class);
		dashboard.setTotalActiveResident(total);

		return dashboard;
	}
}
