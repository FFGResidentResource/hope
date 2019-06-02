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

	@Autowired
	public DashboardDao(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	public Dashboard pullDashboard(Dashboard dashboard) {

		String SQL_ALL_RESIDENT_PER_QUARTER = "select extract(quarter from date_Added) as quarter, count(*) as count from resident where extract(year from date_added) = ? and prop_id in (:ids) group by extract(quarter from date_Added)";

		String SQL_ALL_UNITS_PER_QUARTER = "select count(*) * p.unit as count, extract(quarter from date_Added) as quarter from resident r join Property p on r.prop_id = p.prop_id "
				+ " 	where extract(year from date_added) = ? and p.prop_id in (:ids) group by p.unit, extract(quarter from date_Added)";

		String SQL_TOTAL_RESIDENT = "select count(*) from resident where active = 'TRUE'";

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
		} else {
			SQL_ALL_RESIDENT_PER_QUARTER = SQL_ALL_RESIDENT_PER_QUARTER.replace("and prop_id in (:ids)", "");
			SQL_ALL_UNITS_PER_QUARTER = SQL_ALL_UNITS_PER_QUARTER.replace("and p.prop_id in (:ids)", "");
			SQL_RESIDENT_COMPLETED_SIGNUP_PER_QUARTER = SQL_RESIDENT_COMPLETED_SIGNUP_PER_QUARTER.replace("and prop_id in (:ids)", "");
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

		return dashboard;
	}
}
