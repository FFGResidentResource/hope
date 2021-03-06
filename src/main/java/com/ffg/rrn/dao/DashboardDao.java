/**
 * 
 */
package com.ffg.rrn.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.ffg.rrn.model.CategoryPercentage;
import com.ffg.rrn.model.Dashboard;
import com.ffg.rrn.model.Property;
import com.ffg.rrn.model.QuarterCount;
import com.ffg.rrn.model.ResidentScoreGoal;

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

	private static final String SQL_DISTINCT_YEAR = "select distinct extract(year from date_added) as _year from resident order by _year desc";

	@Autowired
	public DashboardDao(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	public List<String> getDistinctCounties() {
		return this.getJdbcTemplate().queryForList("select distinct county from property order by county",
				String.class);
	}

	public List<String> getDistinctCity() {
		return this.getJdbcTemplate().queryForList("select distinct city from property order by city", String.class);
	}

	public List<String> getDistinctState() {
		return this.getJdbcTemplate().queryForList("select distinct state from property order by state", String.class);
	}

	public List<ResidentScoreGoal> getIndividualScoreCard(String residentId) {

		List<ResidentScoreGoal> rsgList = new ArrayList<ResidentScoreGoal>();
		String SQL_ = "select resident_id, life_domain, score, on_this_date from resident_score_goal rsg  where resident_id = ? order by on_this_date ";

		rsgList = this.getJdbcTemplate().query(SQL_, new Object[] { Long.valueOf(residentId) }, (rs, rowNumber) -> {

			try {
				ResidentScoreGoal rsg = new ResidentScoreGoal();
				rsg.setResidentId(rs.getLong("resident_id"));
				rsg.setLifeDomain(rs.getString("life_domain"));
				rsg.setScore(rs.getInt("score"));
				rsg.setOnThisDate(rs.getDate("on_this_date"));

				return rsg;
			} catch (SQLException e) {
				throw new RuntimeException("your error message", e); // or other unchecked exception here
			}
		});

		return rsgList;

	}

	public List<QuarterCount> getMovingUpQuarterly(String selectedProperties, String year) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<QuarterCount> qcs = new ArrayList<QuarterCount>();

		String SQL_ = "select count(*) as total, \"QUARTER\" as quarter from moving_up_residents_view murv where \"PROP_ID\" in (:properties) and \"YEAR\" = :year group by \"QUARTER\" ";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_ = SQL_.replace(":properties", selectedProperties);
			if (null == year) {
				year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			}
			SQL_ = SQL_.replace(":year", '\'' + year + '\'');

			qcs = this.getJdbcTemplate().query(SQL_, (rs, rowNumber) -> {
				try {
					QuarterCount cp = new QuarterCount();
					cp.setCount(rs.getInt("total"));
					cp.setQuarter(Integer.valueOf(rs.getString("quarter")));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});

		} else {

			QuarterCount qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(1);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(2);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(3);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(4);

			qcs.add(qc);

		}

		return qcs;
	}

	public List<QuarterCount> getMovingDownQuarterly(String selectedProperties, String year) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<QuarterCount> qcs = new ArrayList<QuarterCount>();

		String SQL_ = "select count(*) as total, \"QUARTER\" as quarter from moving_down_residents_view murv where \"PROP_ID\" in (:properties) and \"YEAR\" = :year group by \"QUARTER\" ";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_ = SQL_.replace(":properties", selectedProperties);
			if (null == year) {
				year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			}
			SQL_ = SQL_.replace(":year", '\'' + year + '\'');

			qcs = this.getJdbcTemplate().query(SQL_, (rs, rowNumber) -> {
				try {
					QuarterCount cp = new QuarterCount();
					cp.setCount(rs.getInt("total"));
					cp.setQuarter(Integer.valueOf(rs.getString("quarter")));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});

		} else {

			QuarterCount qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(1);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(2);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(3);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(4);

			qcs.add(qc);

		}

		return qcs;
	}

	public List<QuarterCount> getReferralReasonQuarterly(String selectedProperties, String year) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<QuarterCount> qcs = new ArrayList<QuarterCount>();

		String SQL_ = "select COUNT(*) as total, \"QUARTER\" as quarter, \"REASONS\" as category from referral_reason_view rrv join resident r on r.resident_id = \"RES_ID\" and r.prop_id in (:properties) where \"YEAR\"= :year group by \"REASONS\", \"QUARTER\" ";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_ = SQL_.replace(":properties", selectedProperties);
			if (null == year) {
				year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			}
			SQL_ = SQL_.replace(":year", '\'' + year + '\'');

			qcs = this.getJdbcTemplate().query(SQL_, (rs, rowNumber) -> {
				try {
					QuarterCount cp = new QuarterCount();
					cp.setCount(rs.getInt("total"));
					cp.setQuarter(Integer.valueOf(rs.getString("quarter")));
					cp.setCategory(rs.getString("category"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});

		} else {

			QuarterCount qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(1);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(2);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(3);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(4);

			qcs.add(qc);

		}

		return qcs;
	}

	public List<QuarterCount> getResidentServedQuarterly(String selectedProperties, String year) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<QuarterCount> qcs = new ArrayList<QuarterCount>();

		String SQL_ = "select count(*) as total, 1 as quarter from resident_served_view rsv where (\"RESQ\" = 1 or \"SSMQ\" = 1 or \"CNQ\" = 1 or \"APQ\" = 1) and (\"RESY\" = :year or \"SSMY\" = :year or \"CNY\" = :year or \"APY\" = :year) and \"PROP_ID\" in (:properties) "
				+ "		union "
				+ "		select count(*) as total, 2 as quarter from resident_served_view rsv where (\"RESQ\" = 2 or \"SSMQ\" = 2 or \"CNQ\" = 2 or \"APQ\" = 2) and (\"RESY\" = :year or \"SSMY\" = :year or \"CNY\" = :year or \"APY\" = :year) and \"PROP_ID\" in (:properties) "
				+ "		union "
				+ "		select count(*) as total, 3 as quarter from resident_served_view rsv where (\"RESQ\" = 3 or \"SSMQ\" = 3 or \"CNQ\" = 3 or \"APQ\" = 3) and (\"RESY\" = :year or \"SSMY\" = :year or \"CNY\" = :year or \"APY\" = :year) and \"PROP_ID\" in (:properties) "
				+ "		union "
				+ "		select count(*) as total, 4 as quarter from resident_served_view rsv where (\"RESQ\" = 4 or \"SSMQ\" = 4 or \"CNQ\" = 4 or \"APQ\" = 4) and (\"RESY\" = :year or \"SSMY\" = :year or \"CNY\" = :year or \"APY\" = :year) and \"PROP_ID\" in (:properties) ";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_ = SQL_.replace(":properties", selectedProperties);
			if (null == year) {
				year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			}
			SQL_ = SQL_.replace(":year", '\'' + year + '\'');

			qcs = this.getJdbcTemplate().query(SQL_, (rs, rowNumber) -> {
				try {
					QuarterCount cp = new QuarterCount();
					cp.setCount(rs.getInt("total"));
					cp.setQuarter(Integer.valueOf(rs.getString("quarter")));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});

		} else {

			QuarterCount qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(1);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(2);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(3);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(4);

			qcs.add(qc);

		}

		return qcs;
	}

	public List<QuarterCount> getServiceAgencyQuarterly(String selectedProperties, String year) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<QuarterCount> qcs = new ArrayList<QuarterCount>();

		String SQL_ = "select count(*) as total, \"QUARTER\" as quarter from agency_resident_view acv where \"PROP_ID\" in (:properties) and \"YEAR\" = :year group by \"QUARTER\" ";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_ = SQL_.replace(":properties", selectedProperties);
			if (null == year) {
				year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			}
			SQL_ = SQL_.replace(":year", '\'' + year + '\'');

			qcs = this.getJdbcTemplate().query(SQL_, (rs, rowNumber) -> {
				try {
					QuarterCount cp = new QuarterCount();
					cp.setCount(rs.getInt("total"));
					cp.setQuarter(Integer.valueOf(rs.getString("quarter")));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});

		} else {

			QuarterCount qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(1);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(2);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(3);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(4);

			qcs.add(qc);

		}

		return qcs;
	}

	public List<QuarterCount> getAssessmentCompletedQuarterly(String selectedProperties, String year) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<QuarterCount> qcs = new ArrayList<QuarterCount>();

		String SQL_ = "select count(*) as total, \"QUARTER\" as quarter from assessment_completed_view acv where \"PROP_ID\" in (:properties) and \"YEAR\" = :year group by \"QUARTER\" ";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_ = SQL_.replace(":properties", selectedProperties);
			if (null == year) {
				year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			}
			SQL_ = SQL_.replace(":year", '\'' + year + '\'');

			qcs = this.getJdbcTemplate().query(SQL_, (rs, rowNumber) -> {
				try {
					QuarterCount cp = new QuarterCount();
					cp.setCount(rs.getInt("total"));
					cp.setQuarter(Integer.valueOf(rs.getString("quarter")));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});

		} else {

			QuarterCount qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(1);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(2);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(3);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(4);

			qcs.add(qc);

		}

		return qcs;
	}

	public List<QuarterCount> getNoShowsQuarterly(String selectedProperties, String year) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<QuarterCount> qcs = new ArrayList<QuarterCount>();

		String SQL_ = "select Count(\"ID\") as total, \"CNQ\" as quarter  from CONTACT_NOTE_NO_SHOW_VIEW cnv join Resident r on r.resident_id = \"ID\" and r.prop_id in (:properties) where \"CNY\" = :year group by \"CNY\", \"CNQ\" order by \"CNQ\"";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_ = SQL_.replace(":properties", selectedProperties);
			if (null == year) {
				year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			}
			SQL_ = SQL_.replace(":year", '\'' + year + '\'');

			qcs = this.getJdbcTemplate().query(SQL_, (rs, rowNumber) -> {
				try {
					QuarterCount cp = new QuarterCount();
					cp.setCount(rs.getInt("total"));
					cp.setQuarter(Integer.valueOf(rs.getString("quarter")));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});

		} else {

			QuarterCount qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(1);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(2);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(3);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(4);

			qcs.add(qc);

		}

		return qcs;
	}

	public List<QuarterCount> getRefTypeQuarterly(String selectedProperties, String year) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<QuarterCount> qcs = new ArrayList<QuarterCount>();

		String SQL_ = "select COUNT(*) as total, \"QUARTER\" as quarter, \"REF_TYPE\" as category from RESIDENT_REF_TYPE_VIEW rrv join resident r on r.resident_id = \"RES_ID\" and r.prop_id in (:properties) where \"YEAR\"= :year group by \"REF_TYPE\", \"QUARTER\" ";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_ = SQL_.replace(":properties", selectedProperties);
			if (null == year) {
				year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			}
			SQL_ = SQL_.replace(":year", '\'' + year + '\'');

			qcs = this.getJdbcTemplate().query(SQL_, (rs, rowNumber) -> {
				try {
					QuarterCount cp = new QuarterCount();
					cp.setCount(rs.getInt("total"));
					cp.setQuarter(Integer.valueOf(rs.getString("quarter")));
					cp.setCategory(rs.getString("category"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});

		} else {

			QuarterCount qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(1);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(2);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(3);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(4);

			qcs.add(qc);

		}

		return qcs;
	}

	public List<QuarterCount> getOutcomeAchievedQuarterly(String selectedProperties, String year) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<QuarterCount> qcs = new ArrayList<QuarterCount>();

		String SQL_ = "select COUNT(*) as total, \"QUARTER\" as quarter, \"OUTCOMES\" as category from OUTCOMES_ACHIEVED_VIEW OAV join resident r on r.resident_id = \"RES_ID\" and r.prop_id in (:properties) where \"YEAR\"= :year group by \"OUTCOMES\", \"QUARTER\" ";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_ = SQL_.replace(":properties", selectedProperties);
			if (null == year) {
				year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			}
			SQL_ = SQL_.replace(":year", '\'' + year + '\'');

			qcs = this.getJdbcTemplate().query(SQL_, (rs, rowNumber) -> {
				try {
					QuarterCount cp = new QuarterCount();
					cp.setCount(rs.getInt("total"));
					cp.setQuarter(Integer.valueOf(rs.getString("quarter")));
					cp.setCategory(rs.getString("category"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});

		} else {

			QuarterCount qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(1);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(2);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(3);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(4);

			qcs.add(qc);

		}

		return qcs;
	}

	public List<QuarterCount> getServicesProvidedQuarterly(String selectedProperties, String year) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<QuarterCount> qcs = new ArrayList<QuarterCount>();

		String SQL_ = "select COUNT(*) as total, \"QUARTER\" as quarter, \"SRVC_CAT\" as category from service_category_view scv join resident r on r.resident_id = \"RES_ID\" and r.prop_id in (:properties) where \"YEAR\"= :year group by \"SRVC_CAT\", \"QUARTER\" ";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_ = SQL_.replace(":properties", selectedProperties);
			if (null == year) {
				year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			}
			SQL_ = SQL_.replace(":year", '\'' + year + '\'');

			qcs = this.getJdbcTemplate().query(SQL_, (rs, rowNumber) -> {
				try {
					QuarterCount cp = new QuarterCount();
					cp.setCount(rs.getInt("total"));
					cp.setQuarter(Integer.valueOf(rs.getString("quarter")));
					cp.setCategory(rs.getString("category"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});

		} else {

			QuarterCount qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(1);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(2);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(3);

			qcs.add(qc);

			qc = new QuarterCount();

			qc.setCount(0);
			qc.setQuarter(4);

			qcs.add(qc);

		}

		return qcs;
	}

	public List<CategoryPercentage> getGenderPercentage(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_GENDER_BY_PROPERTY = "select 'Male' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and gender = 'Male' "
				+ " union "
				+ " select 'Female' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and gender = 'Female'"
				+ " union "
				+ " select 'Transgendered Male to Female' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and gender = 'Transgendered Male to Female'"
				+ " union "
				+ " select 'Transgendered Female to Male' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and gender = 'Transgendered Female to Male'"
				+ " union "
				+ " select 'Other' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and gender = 'Other'"
				+ " union "
				+ " select 'Information not collected' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and gender = 'Information not collected'"
				+ " union "
				+ " select 'Individual refused' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and gender = 'Individual refused'"
				+ " union "
				+ " select 'Individual does not know' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and gender = 'Individual does not know'";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_GENDER_BY_PROPERTY = SQL_GENDER_BY_PROPERTY.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_GENDER_BY_PROPERTY, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("Female");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Male");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Transgendered Male to Female");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Transgendered Female to Male");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Other");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Information not collected");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Individual refused");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Individual does not know");
			cp.setPercentage(0);

			cpList.add(cp);

		}

		return cpList;
	}

	public List<CategoryPercentage> getEthnicityPercentage(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_ETHNICITY = "select 'Hispanic/Latino' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and ethnicity = 'Hispanic/Latino' "
				+ " union "
				+ " select 'Not Hispanic/Latino' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and ethnicity = 'Not Hispanic/Latino'"
				+ " union "
				+ " select 'Information not collected' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and ethnicity = 'Information not collected'"
				+ " union "
				+ " select 'Individual refused' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and ethnicity = 'Individual refused'"
				+ " union "
				+ " select 'Individual does not know' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and ethnicity = 'Individual does not know'"
				+ " union "
				+ " select 'Information not collected' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and ethnicity = 'Information not collected'";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_ETHNICITY = SQL_ETHNICITY.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_ETHNICITY, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("Hispanic/Latino");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Not Hispanic/Latino");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Information not collected");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Individual refused");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Individual does not know");
			cp.setPercentage(0);

			cpList.add(cp);

		}

		return cpList;
	}

	public List<CategoryPercentage> getLanguagePercentage(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_LANGUAGE = "select 'English' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and pri_language = 'English' "
				+ " union "
				+ " select 'Spanish' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and pri_language = 'Spanish'"
				+ " union "
				+ " select 'Other' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and pri_language = 'Other'";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_LANGUAGE = SQL_LANGUAGE.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_LANGUAGE, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("English");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Spanish");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Other");
			cp.setPercentage(0);

			cpList.add(cp);

		}

		return cpList;
	}

	public List<CategoryPercentage> getMaritalStatusPercentage(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_MARITAL = "select 'Married' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and marital_status = 'Married' "
				+ " union "
				+ " select 'Single' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and marital_status = 'Single'"
				+ " union "
				+ " select 'Significant Other' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and marital_status = 'Significant Other'";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_MARITAL = SQL_MARITAL.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_MARITAL, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("Married");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Single");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Significant Other");
			cp.setPercentage(0);

			cpList.add(cp);

		}
		return cpList;
	}

	public List<CategoryPercentage> getRacePercentage(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_RACE = "select 'American Indian or Alaska Native' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and race = 'American Indian or Alaska Native' "
				+ " union "
				+ " select 'Asian' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and race = 'Asian'"
				+ " union "
				+ " select 'Black or African American' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and race = 'Black or African American'"
				+ " union "
				+ " select 'Native Hawaiian or Other Pacific Islander' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and race = 'Native Hawaiian or Other Pacific Islander'"
				+ " union "
				+ " select 'White' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and race = 'White'"
				+ " union "
				+ " select 'Mixed Race' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and race = 'Mixed Race'"
				+ " union "
				+ " select 'Information not collected' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and race = 'Information not collected'"
				+ " union "
				+ " select 'Individual refused' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and race = 'Individual refused'"
				+ " union "
				+ " select 'Individual does not know' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and race = 'Individual does not know'";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_RACE = SQL_RACE.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_RACE, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("American Indian or Alaska Native");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Asian");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Black or African American");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Native Hawaiian or Other Pacific Islander");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("White");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Mixed Race");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Information not collected");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Individual refused");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Individual does not know");
			cp.setPercentage(0);

			cpList.add(cp);

		}

		return cpList;
	}

	public List<CategoryPercentage> getHouseholdPercentage(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_HOH = "select 'Yes' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and h_o_h = 'Yes' "
				+ " union "
				+ " select 'No' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and h_o_h = 'No'"
				+ " union "
				+ " select 'Information not collected' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and h_o_h = 'Information not collected'"
				+ " union "
				+ " select 'Individual refused' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and h_o_h = 'Individual refused'"
				+ " union "
				+ " select 'Individual does not know' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and h_o_h = 'Individual does not know'";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_HOH = SQL_HOH.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_HOH, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("Yes");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("No");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Information not collected");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Individual refused");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Individual does not know");
			cp.setPercentage(0);

			cpList.add(cp);

		}

		return cpList;
	}

	public List<CategoryPercentage> getVeteranPercentage(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_VETERAN = "select 'Yes' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and veteran = 'Yes' "
				+ " union "
				+ " select 'No' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and veteran = 'No'"
				+ " union "
				+ " select 'Information not collected' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and veteran = 'Information not collected'"
				+ " union "
				+ " select 'Individual refused' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and veteran = 'Individual refused'"
				+ " union "
				+ " select 'Individual does not know' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and veteran = 'Individual does not know'";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_VETERAN = SQL_VETERAN.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_VETERAN, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("Yes");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("No");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Information not collected");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Individual refused");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Individual does not know");
			cp.setPercentage(0);

			cpList.add(cp);

		}

		return cpList;
	}

	public List<CategoryPercentage> getDisabilityPercentage(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_DISABILITY = "select 'Yes, individual indicates a disability as defined in ADA' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and disability = 'Yes, individual indicates a disability as defined in ADA' "
				+ " union "
				+ " select 'No, individual indicates a disability as defined in ADA' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and disability = 'No, individual indicates a disability as defined in ADA'"
				+ " union "
				+ " select 'Information not collected' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and disability = 'Information not collected'"
				+ " union "
				+ " select 'Individual refused' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and disability = 'Individual refused'"
				+ " union "
				+ " select 'Individual does not know' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and disability = 'Individual does not know'"
				+ " union "
				+ " select 'N/A' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and disability = 'N/A'";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_DISABILITY = SQL_DISABILITY.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_DISABILITY, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("Yes, individual indicates a disability as defined in ADA");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("No, individual indicates a disability as defined in ADA");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Information not collected");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Individual refused");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Individual does not know");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("N/A");
			cp.setPercentage(0);

			cpList.add(cp);

		}

		return cpList;
	}

	public List<CategoryPercentage> getReturningCitizenPercentage(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_CITIZEN = "select 'Yes' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and rc_or_ex_off = 'Yes' "
				+ " union "
				+ " select 'Information not collected' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and rc_or_ex_off = 'Information not collected'"
				+ " union "
				+ " select 'N/A' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and rc_or_ex_off = 'N/A'";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_CITIZEN = SQL_CITIZEN.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_CITIZEN, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("Yes");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Information not collected");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("N/A");
			cp.setPercentage(0);

			cpList.add(cp);

		}

		return cpList;
	}

	public List<CategoryPercentage> getSSIPercentage(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_SSI = "select 'Yes' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and ssi = 'Yes' "
				+ " union "
				+ " select 'Information not collected' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and ssi = 'Information not collected'"
				+ " union "
				+ " select 'N/A' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and ssi = 'N/A'";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_SSI = SQL_SSI.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_SSI, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("Yes");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Information not collected");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("N/A");
			cp.setPercentage(0);

			cpList.add(cp);

		}

		return cpList;
	}

	public List<CategoryPercentage> getSSDIPercentage(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_SSDI = "select 'Yes' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and ssdi = 'Yes' "
				+ " union "
				+ " select 'Information not collected' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and ssdi = 'Information not collected'"
				+ " union "
				+ " select 'N/A' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and ssdi = 'N/A'";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_SSDI = SQL_SSDI.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_SSDI, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("Yes");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Information not collected");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("N/A");
			cp.setPercentage(0);

			cpList.add(cp);

		}

		return cpList;
	}

	public List<CategoryPercentage> getHealthPercentage(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_HEALTH = "select 'Yes, covered through employer or union (current or former)' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and health_coverage = 'Yes, covered through employer or union (current or former)' "
				+ " union "
				+ " select 'Yes, purchased insurance from insurance company' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and health_coverage = 'Yes, purchased insurance from insurance company'"
				+ " union "
				+ " select 'TRICARE or other military health care' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and health_coverage = 'TRICARE or other military health care'"
				+ " union "
				+ " select 'VA health care' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and health_coverage = 'VA health care'"
				+ " union "
				+ " select 'Indian Health Service' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and health_coverage = 'Indian Health Service'"
				+ " union "
				+ " select 'Other health insurance or health coverage plan' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and health_coverage = 'Other health insurance or health coverage plan'"
				+ " union "
				+ " select 'No coverage' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and health_coverage = 'No coverage'"
				+ " union "
				+ " select 'Information not collected' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and health_coverage = 'Information not collected'"
				+ " union "
				+ " select 'Individual refused' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and health_coverage = 'Individual refused'"
				+ " union "
				+ " select 'Individual does not know' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and health_coverage = 'Individual does not know'"
				+ " union "
				+ " select 'N/A' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and health_coverage = 'N/A'"
				+ " union "
				+ " select 'Medicare' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and health_coverage = 'Medicare'"
				+ " union "
				+ " select 'Medicaid/Medical Assistant' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and health_coverage = 'Medicaid/Medical Assistant'";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_HEALTH = SQL_HEALTH.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_HEALTH, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("Yes, covered through employer or union (current or former)");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Yes, purchased insurance from insurance company");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("TRICARE or other military health care");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("VA health care");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Indian Health Service");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Other health insurance or health coverage plan");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("No coverage");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Information not collected");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Individual refused");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Individual does not know");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("N/A");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Medicare");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Medicaid/Medical Assistant");
			cp.setPercentage(0);

			cpList.add(cp);

		}

		return cpList;
	}

	public List<CategoryPercentage> getFoodShortagesPercentage(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_ = "select 'No' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and EXP_FOOD_SHORT = 'Yes' "
				+ " union "
				+ " select 'Beginning of the Month' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and EXP_FOOD_SHORT = 'Beginning of the Month'"
				+ " union "
				+ " select 'Middle of the Month' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and EXP_FOOD_SHORT = 'Middle of the Month'"
				+ " union "
				+ " select 'End of the Month' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and EXP_FOOD_SHORT = 'End of the Month'";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_ = SQL_.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("No");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Beginning of the Month");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Middle of the Month");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("End of the Month");
			cp.setPercentage(0);

			cpList.add(cp);

		}

		return cpList;
	}

	public List<CategoryPercentage> feeSafeDayPercentage(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_ = "select 'Somewhat safe' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and SAFE_DAY = 'Somewhat safe' "
				+ " union "
				+ " select 'Somewhat unsafe' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and SAFE_DAY = 'Somewhat unsafe'"
				+ " union "
				+ " select 'Very safe' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and SAFE_DAY = 'Very safe'"
				+ " union "
				+ " select 'Very unsafe' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and SAFE_DAY = 'Very unsafe'";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_ = SQL_.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("Somewhat safe");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Somewhat unsafe");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Very safe");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Very unsafe");
			cp.setPercentage(0);

			cpList.add(cp);

		}

		return cpList;
	}

	public List<CategoryPercentage> feeSafeNightPercentage(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_ = "select 'Somewhat safe' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and SAFE_NIGHT = 'Somewhat safe' "
				+ " union "
				+ " select 'Somewhat unsafe' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and SAFE_NIGHT = 'Somewhat unsafe'"
				+ " union "
				+ " select 'Very safe' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and SAFE_NIGHT = 'Very safe'"
				+ " union "
				+ " select 'Very unsafe' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and SAFE_NIGHT = 'Very unsafe'";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_ = SQL_.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("Somewhat safe");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Somewhat unsafe");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Very safe");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Very unsafe");
			cp.setPercentage(0);

			cpList.add(cp);

		}

		return cpList;
	}

	public List<CategoryPercentage> getSignUpPercentage(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_ = "select 'Sign Up Completed' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where ack_pr = true and prop_id  in (:properties)";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_ = SQL_.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("Sign Up Completed");
			cp.setPercentage(0);

			cpList.add(cp);

		}

		return cpList;
	}

	public List<CategoryPercentage> getInterestResCouncil(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_ = "select 'Yes' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and INT_RES_COUNCIL = 'Yes' "
				+ " union "
				+ " select 'No' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and INT_RES_COUNCIL = 'No'";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_ = SQL_.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("Yes");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("No");
			cp.setPercentage(0);

			cpList.add(cp);
		}

		return cpList;
	}

	public List<CategoryPercentage> getInternetAccessPercentage(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_ = "select 'No, but my phone has internet access' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and INTERNET_ACCESS = 'No, but my phone has internet access' "
				+ " union "
				+ " select 'No, I have no internet access' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and INTERNET_ACCESS = 'No, I have no internet access'"
				+ " union "
				+ " select 'Yes' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and INTERNET_ACCESS = 'Yes'";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_ = SQL_.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("No, but my phone has internet access");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("No, I have no internet access");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Yes");
			cp.setPercentage(0);

			cpList.add(cp);
		}

		return cpList;
	}

	public List<CategoryPercentage> getHouseholdType(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_ = "select 'No Children' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and HOH_TYPE = 'No Children' "
				+ " union "
				+ " select 'One Parent' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and HOH_TYPE = 'One Parent'"
				+ " union "
				+ " select 'Two Parent' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and HOH_TYPE = 'Two Parent'";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_ = SQL_.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("No Children");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("One Parent");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Two Parent");
			cp.setPercentage(0);

			cpList.add(cp);
		}

		return cpList;
	}

	public List<CategoryPercentage> prefferedContactMethodPercentage(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_ = "select 'Email' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and via_email = true "
				+ "				 union  "
				+ "				 select 'Phone Call' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and via_voicemail = true "
				+ "				 union  "
				+ "				 select 'Text Message' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and via_text = true ";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_ = SQL_.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("Email");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Phone Call");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Text Message");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Mail");
			cp.setPercentage(0);

			cpList.add(cp);
		}

		return cpList;
	}

	public List<CategoryPercentage> occupancyLengthPercentage(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_ = "select 'Less than 1 Year' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and occupancy_length = 'Less than 1 Year' "
				+ "				 union  "
				+ "				 select '1-3 Years' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and occupancy_length = '1-3 Years' "
				+ "				 union  "
				+ "				 select '4-6 Years' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and occupancy_length = '4-6 Years' "
				+ "				 union "
				+ "				 select '7-9 Years' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and occupancy_length = '7-9 Years' "
				+ "				 union "
				+ "				 select '10 or more Years' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and occupancy_length = '10 or more Years' ";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_ = SQL_.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("Less than 1 Year");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("1-3 Years");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("4-6 Years");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("7-9 Years");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("10 or more Years");
			cp.setPercentage(0);

			cpList.add(cp);
		}

		return cpList;
	}

	public List<CategoryPercentage> getUnEmpReasonPercentage(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_ = "select 'Caregiver' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and UNEMP_REASON = 'Caregiver' "
				+ " union "
				+ " select 'Criminal record' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and UNEMP_REASON = 'Criminal record'"
				+ " union "
				+ " select 'Disabled' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and UNEMP_REASON = 'Disabled'"
				+ " union "
				+ " select 'Health problems' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and UNEMP_REASON = 'Health problems'"
				+ " union "
				+ " select 'In school' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and UNEMP_REASON = 'In school'"
				+ " union "
				+ " select 'No child care' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and UNEMP_REASON = 'No child care'"
				+ " union "
				+ " select 'No driver''s license' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and UNEMP_REASON = 'No driver''s license'"
				+ " union "
				+ " select 'No transportation' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and UNEMP_REASON = 'No transportation'"
				+ " union "
				+ " select 'Retired' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and UNEMP_REASON = 'Retired'"
				+ " union "
				+ " select 'Stay at home parent' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and UNEMP_REASON = 'Stay at home parent'";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_ = SQL_.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("Caregiver");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Criminal record");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Disabled");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Health problems");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("In school");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("No child care");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("No driver's license");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("No transportation");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Retired");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Stay at home parent");
			cp.setPercentage(0);

			cpList.add(cp);

		}

		return cpList;
	}

	public List<CategoryPercentage> getBarrierToEducationPercentage(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_ = "select 'Criminal record' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and BARRIER_TO_EDU = 'Criminal record' "
				+ " union "
				+ " select 'FAFSA application record' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and BARRIER_TO_EDU = 'FAFSA application'"
				+ " union "
				+ " select 'Lack of childcare' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and BARRIER_TO_EDU = 'Lack of childcare'"
				+ " union "
				+ " select 'Lack of funding' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and BARRIER_TO_EDU = 'Lack of funding'"
				+ " union "
				+ " select 'No HS diploma/GED' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and BARRIER_TO_EDU = 'No HS diploma/GED'"
				+ " union "
				+ " select 'Owe money to a school' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and BARRIER_TO_EDU = 'Owe money to a school'"
				+ " union "
				+ " select 'Poor grades' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and BARRIER_TO_EDU = 'Poor grades'"
				+ " union "
				+ " select 'Transportation' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and BARRIER_TO_EDU = 'Transportation'"
				+ " union "
				+ " select 'Unsure of a career field' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and BARRIER_TO_EDU = 'Unsure of a career field'"
				+ " union "
				+ " select 'Work' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and BARRIER_TO_EDU = 'Work'";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_ = SQL_.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("Criminal record");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("FAFSA application");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Lack of childcare");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Lack of funding");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("No HS diploma/GED");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Owe money to a school");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Poor grades");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Transportation");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Unsure of a career field");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Work");
			cp.setPercentage(0);

			cpList.add(cp);

		}

		return cpList;
	}

	public List<CategoryPercentage> getPSAdultPercentage(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_ = "select 'College preparation' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and PROGRAM_SRVC_ADULT = 'College preparation' "
				+ " union "
				+ " select 'Counselling' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and PROGRAM_SRVC_ADULT = 'Counselling'"
				+ " union "
				+ " select 'Emergency assist' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and PROGRAM_SRVC_ADULT = 'Emergency assist'"
				+ " union "
				+ " select 'GED/ESL' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and PROGRAM_SRVC_ADULT = 'GED/ESL'"
				+ " union "
				+ " select 'Home ownership education' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and PROGRAM_SRVC_ADULT = 'Home ownership education'"
				+ " union "
				+ " select 'Jobs' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and PROGRAM_SRVC_ADULT = 'Jobs'"
				+ " union "
				+ " select 'Small business dev' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and PROGRAM_SRVC_ADULT = 'Small business dev'"
				+ " union "
				+ " select 'Vocational education' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and PROGRAM_SRVC_ADULT = 'Vocational education'";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_ = SQL_.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("College preparation");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Counselling");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Emergency assist");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("GED/ESL");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Home ownership education");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Jobs");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Small business dev");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Vocational education");
			cp.setPercentage(0);

			cpList.add(cp);

		}

		return cpList;
	}

	public List<CategoryPercentage> getHCPercentage(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_ = "select 'Asthma' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and HEALTH_CONDITION = 'Asthma' "
				+ " union "
				+ " select 'Cancer' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and HEALTH_CONDITION = 'Cancer'"
				+ " union "
				+ " select 'Diabetes' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and HEALTH_CONDITION = 'Diabetes'"
				+ " union "
				+ " select 'High blood pressure' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and HEALTH_CONDITION = 'High blood pressure'"
				+ " union "
				+ " select 'Heart disease' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and HEALTH_CONDITION = 'Heart disease'"
				+ " union "
				+ " select 'Mental health/depression' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and HEALTH_CONDITION = 'Mental health/depression'"
				+ " union "
				+ " select 'Substance abuse' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and HEALTH_CONDITION = 'Substance abuse'"
				+ " union "
				+ " select 'Other' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and HEALTH_CONDITION = 'Other'";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_ = SQL_.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("Asthma");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Cancer");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Diabetes");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("High blood pressure");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Heart disease");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Mental health/depression");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Substance abuse");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Other");
			cp.setPercentage(0);

			cpList.add(cp);

		}

		return cpList;
	}

	public List<CategoryPercentage> getPSyouthPercentage(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_ = "select 'Arts and Crafts' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and PROGRAM_SRVC_YOUTH = 'Arts and crafts' "
				+ " union "
				+ " select 'After school program' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and PROGRAM_SRVC_YOUTH = 'After school program'"
				+ " union "
				+ " select 'Computer education' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and PROGRAM_SRVC_YOUTH = 'Computer education'"
				+ " union "
				+ " select 'Delinquency prevention' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and PROGRAM_SRVC_YOUTH = 'Delinquency prevention'"
				+ " union "
				+ " select 'Drug prevention' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and PROGRAM_SRVC_YOUTH = 'Drug prevention'"
				+ " union "
				+ " select 'Field trips' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and PROGRAM_SRVC_YOUTH = 'Field trips'"
				+ " union "
				+ " select 'GED/ESL' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and PROGRAM_SRVC_YOUTH = 'GED/ESL'"
				+ " union "
				+ " select 'Jobs' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and PROGRAM_SRVC_YOUTH = 'Jobs'"
				+ " union "
				+ " select 'Mentoring' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and PROGRAM_SRVC_YOUTH = 'Mentoring'"
				+ " union "
				+ " select 'Music' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and PROGRAM_SRVC_YOUTH = 'Music'"
				+ " union "
				+ " select 'Sports' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and PROGRAM_SRVC_YOUTH = 'Sports'"
				+ " union "
				+ " select 'Tutoring' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and PROGRAM_SRVC_YOUTH = 'Tutoring'";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_ = SQL_.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("Arts and crafts");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("After school program");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Computer education");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Delinquency prevention");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Drug prevention");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Field trips");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("GED/ESL");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Jobs");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Mentoring");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Music");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Sports");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Tutoring");
			cp.setPercentage(0);

			cpList.add(cp);

		}

		return cpList;
	}

	public List<CategoryPercentage> getModeOfTransportationPercentage(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_ = "select 'Bus' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and MODE_TRANSPORT = 'Bus' "
				+ " union "
				+ " select 'Personal Vehicle' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and MODE_TRANSPORT = 'Personal Vehicle'"
				+ " union "
				+ " select 'Someone drives me' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and MODE_TRANSPORT = 'Someone drives me'"
				+ " union "
				+ " select 'Walk/Bike' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and MODE_TRANSPORT = 'Walk/Bike'";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_ = SQL_.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("Bus");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Personal vehicle");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Someone drives me");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Walk/Bike");
			cp.setPercentage(0);

			cpList.add(cp);

		}

		return cpList;
	}

	public List<CategoryPercentage> getEducationPercentage(String selectedProperties) {

		selectedProperties = selectedProperties.replace("\"", "");
		selectedProperties = selectedProperties.replace("[", "");
		selectedProperties = selectedProperties.replace("]", "");

		List<CategoryPercentage> cpList = new ArrayList<CategoryPercentage>();
		String SQL_EDUCATION = "select 'No schooling completed, Nursery school, or Kindergarten' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and highest_edu = 'No schooling completed, Nursery school, or Kindergarten' "
				+ " union "
				+ " select '12th grade, no diploma' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and highest_edu = '12th grade, no diploma'"
				+ " union "
				+ " select 'High School Diploma' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and highest_edu = 'High School Diploma'"
				+ " union "
				+ " select 'Grade 1 GED or alternative credentials' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and highest_edu = 'Grade 1 GED or alternative credentials'"
				+ " union "
				+ " select 'Grade 2 Less than 1 year of college credit' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and highest_edu = 'Grade 2 Less than 1 year of college credit'"
				+ " union "
				+ " select 'Grade 3 One or more yearss of college dredit, no degree' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and highest_edu = 'Grade 3 One or more yearss of college dredit, no degree'"
				+ " union "
				+ " select 'Grade 4 Associate''s degree' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and highest_edu = 'Grade 4 Associate''s degree'"
				+ " union "
				+ " select 'Grade 5 Bachelor''s degree' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and highest_edu = 'Grade 5 Bachelor''s degree'"
				+ " union"
				+ " select 'Grade 6 Master''s degree' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where prop_id in (:properties) and highest_edu = 'Grade 6 Master''s degree' "
				+ " union "
				+ " select 'Grade 7 Professional degree (e.g. MD, DDS, DVM, LLB, JD)' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and highest_edu = 'Grade 7 Professional degree (e.g. MD, DDS, DVM, LLB, JD)'"
				+ " union "
				+ " select 'Grade 8 Doctorate degree' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and highest_edu = 'Grade 8 Doctorate degree'"
				+ " union "
				+ " select 'Grade 9 Individual refused' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and highest_edu = 'Grade 9 Individual refused'"
				+ " union "
				+ " select 'Grade 10Individual does not know' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and highest_edu = 'Grade 10Individual does not know'"
				+ " union "
				+ " select 'Grade 11 N/A' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and highest_edu = 'Grade 11 N/A'"
				+ " union "
				+ " select 'Information not collected' as category, ROUND((count(*) / (select case when count(*) > 0 then count(*) else 1 END from resident where prop_id  in (:properties))::float)* 100) as percentage from resident where  prop_id in (:properties) and highest_edu = 'Information not collected'";

		if (StringUtils.isNotBlank(selectedProperties)) {
			SQL_EDUCATION = SQL_EDUCATION.replace(":properties", selectedProperties);

			cpList = this.getJdbcTemplate().query(SQL_EDUCATION, (rs, rowNumber) -> {
				try {
					CategoryPercentage cp = new CategoryPercentage();
					cp.setCategory(rs.getString("category"));
					cp.setPercentage(rs.getInt("percentage"));
					return cp;
				} catch (SQLException e) {
					throw new RuntimeException("your error message", e); // or other unchecked exception here
				}
			});
		}

		else {

			CategoryPercentage cp = new CategoryPercentage();
			cp.setCategory("No schooling completed, Nursery school, or Kindergarten");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("12th grade, no diploma");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("High School Diploma");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Grade 1 GED or alternative credentials");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Grade 2 Less than 1 year of college credit");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Grade 3 One or more yearss of college dredit, no degree");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Grade 4 Associate's degree");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Grade 5 Bachelor's degree");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Grade 6 Master's degree");
			cp.setPercentage(0);

			cpList.add(cp);

			cp = new CategoryPercentage();
			cp.setCategory("Grade 7 Professional degree (e.g. MD, DDS, DVM, LLB, JD)");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Grade 8 Doctorate degree");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Grade 9 Individual refused");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Grade 10Individual does not know");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Grade 11 N/A");
			cp.setPercentage(0);

			cpList.add(cp);
			cp = new CategoryPercentage();
			cp.setCategory("Information not collected");
			cp.setPercentage(0);

			cpList.add(cp);

		}

		return cpList;
	}

	public List<String> getAllYears() {

		return (List<String>) this.getJdbcTemplate().query(SQL_DISTINCT_YEAR, (rs, rowNum) -> {
			return rs.getString(1);
		});
	}

	public Dashboard pullDashboard(Dashboard dashboard) {

		String RESIDENT_SERVED = "select count(*) from resident_served_view where \r\n"
				+ "(\"RESQ\" = ? OR \"SSMQ\" = ? OR \"CNQ\" = ? OR \"APQ\" = ?) AND \r\n"
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
			SQL_RESIDENT_COMPLETED_SIGNUP_PER_QUARTER = SQL_RESIDENT_COMPLETED_SIGNUP_PER_QUARTER.replace(":ids",
					idString.substring(0, idString.length() - 1));
			ASSESSMENT_COMPLETED = ASSESSMENT_COMPLETED.replace(":ids", idString.substring(0, idString.length() - 1));
			RESIDENT_SERVED = RESIDENT_SERVED.replace(":ids", idString.substring(0, idString.length() - 1));
			ACTION_PLAN_BY_DOMAIN = ACTION_PLAN_BY_DOMAIN.replace(":ids", idString.substring(0, idString.length() - 1));
			REFERRAL_REASON = REFERRAL_REASON.replace(":ids", idString.substring(0, idString.length() - 1));
		} else {
			SQL_RESIDENT_COMPLETED_SIGNUP_PER_QUARTER = SQL_RESIDENT_COMPLETED_SIGNUP_PER_QUARTER
					.replace("and prop_id in (:ids)", "");
			ASSESSMENT_COMPLETED = ASSESSMENT_COMPLETED.replace("and \"PROP_ID\" in (:ids)", "");
			RESIDENT_SERVED = RESIDENT_SERVED.replace("and \"PROP_ID\" in (:ids)", "");
			ACTION_PLAN_BY_DOMAIN = ACTION_PLAN_BY_DOMAIN.replace("and \"PROP_ID\" in (:ids)", "");
			REFERRAL_REASON = REFERRAL_REASON.replace("and \"PROP_ID\" in (:ids)", "");
		}

		int year = Integer.parseInt(dashboard.getYear());

		// Referral Reason 1 to 14

		try {
			dashboard.setRr1q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 1, year, "Childcare/afterschool care" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr1q1Count(0l);
		}

		try {
			dashboard.setRr1q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 2, year, "Childcare/afterschool care" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr1q2Count(0l);
		}
		try {
			dashboard.setRr1q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 3, year, "Childcare/afterschool care" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr1q3Count(0l);
		}
		try {
			dashboard.setRr1q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 4, year, "Childcare/afterschool care" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr1q4Count(0l);
		}

		try {
			dashboard.setRr2q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 1, year, "Education/job training" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr2q1Count(0l);
		}

		try {
			dashboard.setRr2q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 2, year, "Education/job training" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr2q2Count(0l);
		}
		try {
			dashboard.setRr2q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 3, year, "Education/job training" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr2q3Count(0l);
		}
		try {
			dashboard.setRr2q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 4, year, "Education/job training" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr2q4Count(0l);
		}

		try {
			dashboard.setRr3q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 1, year, "Employment/job readiness" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr3q1Count(0l);
		}
		try {
			dashboard.setRr3q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 2, year, "Employment/job readiness" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr3q2Count(0l);
		}
		try {
			dashboard.setRr3q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 3, year, "Employment/job readiness" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr3q3Count(0l);
		}
		try {
			dashboard.setRr3q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 4, year, "Employment/job readiness" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr3q4Count(0l);
		}

		try {
			dashboard.setRr4q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 1, year, "Healthcare/medical issues" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr4q1Count(0l);
		}

		try {
			dashboard.setRr4q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 2, year, "Healthcare/medical issues" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr4q2Count(0l);
		}
		try {
			dashboard.setRr4q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 3, year, "Healthcare/medical issues" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr4q3Count(0l);
		}
		try {
			dashboard.setRr4q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 4, year, "Healthcare/medical issues" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr4q4Count(0l);
		}

		try {
			dashboard.setRr5q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 1, year, "Housekeeping/home management" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr5q1Count(0l);
		}

		try {
			dashboard.setRr5q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 2, year, "Housekeeping/home management" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr5q2Count(0l);
		}
		try {
			dashboard.setRr5q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 3, year, "Housekeeping/home management" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr5q3Count(0l);
		}
		try {
			dashboard.setRr5q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 4, year, "Housekeeping/home management" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr5q4Count(0l);
		}

		try {
			dashboard.setRr6q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 1, year, "Lease violation for:" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr6q1Count(0l);
		}

		try {
			dashboard.setRr6q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 2, year, "Lease violation for:" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr6q2Count(0l);
		}
		try {
			dashboard.setRr6q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 3, year, "Lease violation for:" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr6q3Count(0l);
		}
		try {
			dashboard.setRr6q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 4, year, "Lease violation for:" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr6q4Count(0l);
		}

		try {
			dashboard.setRr7q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 1, year, "Non/late payment of rent" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr7q1Count(0l);
		}

		try {
			dashboard.setRr7q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 2, year, "Non/late payment of rent" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr7q2Count(0l);
		}
		try {
			dashboard.setRr7q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 3, year, "Non/late payment of rent" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr7q3Count(0l);
		}
		try {
			dashboard.setRr7q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 4, year, "Non/late payment of rent" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr7q4Count(0l);
		}

		try {
			dashboard.setRr8q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 1, year, "Noticeable change in:" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr8q1Count(0l);
		}

		try {
			dashboard.setRr8q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 2, year, "Noticeable change in:" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr8q2Count(0l);
		}
		try {
			dashboard.setRr8q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 3, year, "Noticeable change in:" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr8q3Count(0l);
		}
		try {
			dashboard.setRr8q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 4, year, "Noticeable change in:" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr8q4Count(0l);
		}

		try {
			dashboard.setRr9q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 1, year, "Other:" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr9q1Count(0l);
		}

		try {
			dashboard.setRr9q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 2, year, "Other:" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr9q2Count(0l);
		}
		try {
			dashboard.setRr9q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 3, year, "Other:" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr9q3Count(0l);
		}
		try {
			dashboard.setRr9q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 4, year, "Other:" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr9q4Count(0l);
		}

		try {
			dashboard.setRr10q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 1, year, "Resident-to-resident conflict issues" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr10q1Count(0l);
		}

		try {
			dashboard.setRr10q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 2, year, "Resident-to-resident conflict issues" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr10q2Count(0l);
		}
		try {
			dashboard.setRr10q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 3, year, "Resident-to-resident conflict issues" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr10q3Count(0l);
		}
		try {
			dashboard.setRr10q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 4, year, "Resident-to-resident conflict issues" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr10q4Count(0l);
		}

		try {
			dashboard.setRr11q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 1, year, "Safety" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr11q1Count(0l);
		}

		try {
			dashboard.setRr11q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 2, year, "Safety" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr11q2Count(0l);
		}
		try {
			dashboard.setRr11q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 3, year, "Safety" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr11q3Count(0l);
		}
		try {
			dashboard.setRr11q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 4, year, "Safety" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr11q4Count(0l);
		}

		try {
			dashboard.setRr12q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 1, year, "Suspected abuse/domestic violence/exploitation" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr12q1Count(0l);
		}

		try {
			dashboard.setRr12q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 2, year, "Suspected abuse/domestic violence/exploitation" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr12q2Count(0l);
		}
		try {
			dashboard.setRr12q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 3, year, "Suspected abuse/domestic violence/exploitation" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr12q3Count(0l);
		}
		try {
			dashboard.setRr12q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 4, year, "Suspected abuse/domestic violence/exploitation" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr12q4Count(0l);
		}

		try {
			dashboard.setRr13q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 1, year, "Transportation" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr13q1Count(0l);
		}

		try {
			dashboard.setRr13q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 2, year, "Transportation" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr13q2Count(0l);
		}
		try {
			dashboard.setRr13q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 3, year, "Transportation" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr13q3Count(0l);
		}
		try {
			dashboard.setRr13q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 4, year, "Transportation" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr13q4Count(0l);
		}

		try {
			dashboard.setRr14q1Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 1, year, "Utility Shut-off, scheduled for (Date):" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr14q1Count(0l);
		}

		try {
			dashboard.setRr14q2Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 2, year, "Utility Shut-off, scheduled for (Date):" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr14q2Count(0l);
		}
		try {
			dashboard.setRr14q3Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 3, year, "Utility Shut-off, scheduled for (Date):" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr14q3Count(0l);
		}
		try {
			dashboard.setRr14q4Count(this.getJdbcTemplate().queryForObject(REFERRAL_REASON,
					new Object[] { 4, year, "Utility Shut-off, scheduled for (Date):" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setRr14q4Count(0l);
		}

		// REferral_reason - END

		// Action Plan developed per domain

		try {
			dashboard.setHousingQ1Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN,
					new Object[] { 1, year, "HOUSING" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setHousingQ1Count(0l);
		}

		try {
			dashboard.setHousingQ2Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN,
					new Object[] { 2, year, "HOUSING" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setHousingQ2Count(0l);
		}
		try {
			dashboard.setHousingQ3Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN,
					new Object[] { 3, year, "HOUSING" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setHousingQ3Count(0l);
		}
		try {
			dashboard.setHousingQ4Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN,
					new Object[] { 4, year, "HOUSING" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setHousingQ4Count(0l);
		}

		try {
			dashboard.setMmQ1Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN,
					new Object[] { 1, year, "MONEY MANAGEMENT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setMmQ1Count(0l);
		}

		try {
			dashboard.setMmQ2Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN,
					new Object[] { 2, year, "MONEY MANAGEMENT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setMmQ2Count(0l);
		}
		try {
			dashboard.setMmQ3Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN,
					new Object[] { 3, year, "MONEY MANAGEMENT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setMmQ3Count(0l);
		}
		try {
			dashboard.setMmQ4Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN,
					new Object[] { 4, year, "MONEY MANAGEMENT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setMmQ4Count(0l);
		}

		try {
			dashboard.setEmpQ1Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN,
					new Object[] { 1, year, "EMPLOYMENT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setEmpQ1Count(0l);
		}

		try {
			dashboard.setEmpQ2Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN,
					new Object[] { 2, year, "EMPLOYMENT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setEmpQ2Count(0l);
		}
		try {
			dashboard.setEmpQ3Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN,
					new Object[] { 3, year, "EMPLOYMENT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setEmpQ3Count(0l);
		}
		try {
			dashboard.setEmpQ4Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN,
					new Object[] { 4, year, "EMPLOYMENT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setEmpQ4Count(0l);
		}

		try {
			dashboard.setEduQ1Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN,
					new Object[] { 1, year, "EDUCATION" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setEduQ1Count(0l);
		}

		try {
			dashboard.setEduQ2Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN,
					new Object[] { 2, year, "EDUCATION" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setEduQ2Count(0l);
		}
		try {
			dashboard.setEduQ3Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN,
					new Object[] { 3, year, "EDUCATION" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setEduQ3Count(0l);
		}
		try {
			dashboard.setEduQ4Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN,
					new Object[] { 4, year, "EDUCATION" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setEduQ4Count(0l);
		}

		try {
			dashboard.setNsQ1Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN,
					new Object[] { 1, year, "NETWORK SUPPORT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setNsQ1Count(0l);
		}

		try {
			dashboard.setNsQ2Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN,
					new Object[] { 2, year, "NETWORK SUPPORT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setNsQ2Count(0l);
		}
		try {
			dashboard.setNsQ3Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN,
					new Object[] { 3, year, "NETWORK SUPPORT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setNsQ3Count(0l);
		}
		try {
			dashboard.setNsQ4Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN,
					new Object[] { 4, year, "NETWORK SUPPORT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setNsQ4Count(0l);
		}

		try {
			dashboard.setHhQ1Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN,
					new Object[] { 1, year, "HOUSEHOLD MANAGEMENT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setHhQ1Count(0l);
		}

		try {
			dashboard.setHhQ2Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN,
					new Object[] { 2, year, "HOUSEHOLD MANAGEMENT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setHhQ2Count(0l);
		}
		try {
			dashboard.setHhQ3Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN,
					new Object[] { 3, year, "HOUSEHOLD MANAGEMENT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setHhQ3Count(0l);
		}
		try {
			dashboard.setHhQ4Count(this.getJdbcTemplate().queryForObject(ACTION_PLAN_BY_DOMAIN,
					new Object[] { 4, year, "HOUSEHOLD MANAGEMENT" }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setHhQ4Count(0l);
		}

		// REsident served per Quarter

		try {
			dashboard.setResidentServedQ1(this.getJdbcTemplate().queryForObject(RESIDENT_SERVED,
					new Object[] { 1, 1, 1, 1, year, year, year, year }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setResidentServedQ1(0l);
		}

		try {
			dashboard.setResidentServedQ2(this.getJdbcTemplate().queryForObject(RESIDENT_SERVED,
					new Object[] { 2, 2, 2, 2, year, year, year, year }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setResidentServedQ2(0l);
		}
		try {
			dashboard.setResidentServedQ3(this.getJdbcTemplate().queryForObject(RESIDENT_SERVED,
					new Object[] { 3, 3, 3, 3, year, year, year, year }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setResidentServedQ3(0l);
		}
		try {
			dashboard.setResidentServedQ4(this.getJdbcTemplate().queryForObject(RESIDENT_SERVED,
					new Object[] { 4, 4, 4, 4, year, year, year, year }, Long.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setResidentServedQ4(0l);
		}

		// Assessment Completed per Quarter

		try {
			dashboard.setQ1AssessmentComplete(this.getJdbcTemplate().queryForObject(ASSESSMENT_COMPLETED,
					new Object[] { 1, year }, Integer.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setQ1AssessmentComplete(0);
		}

		try {
			dashboard.setQ2AssessmentComplete(this.getJdbcTemplate().queryForObject(ASSESSMENT_COMPLETED,
					new Object[] { 2, year }, Integer.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setQ2AssessmentComplete(0);
		}

		try {
			dashboard.setQ3AssessmentComplete(this.getJdbcTemplate().queryForObject(ASSESSMENT_COMPLETED,
					new Object[] { 3, year }, Integer.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setQ3AssessmentComplete(0);
		}

		try {
			dashboard.setQ4AssessmentComplete(this.getJdbcTemplate().queryForObject(ASSESSMENT_COMPLETED,
					new Object[] { 4, year }, Integer.class));
		} catch (EmptyResultDataAccessException ers) {
			dashboard.setQ4AssessmentComplete(0);
		}

		List<QuarterCount> qcList = new ArrayList<QuarterCount>();
		qcList = this.getJdbcTemplate().query(SQL_RESIDENT_COMPLETED_SIGNUP_PER_QUARTER,
				new Object[] { Integer.parseInt(dashboard.getYear()) }, (rs, rowNumber) -> {
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
