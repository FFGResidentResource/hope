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
import org.springframework.util.StringUtils;

import com.ffg.rrn.mapper.AssessmentMapper;
import com.ffg.rrn.mapper.PropertyMapper;
import com.ffg.rrn.mapper.ReferralMapper;
import com.ffg.rrn.mapper.ResidentMapper;
import com.ffg.rrn.model.AssessmentType;
import com.ffg.rrn.model.Property;
import com.ffg.rrn.model.Referral;
import com.ffg.rrn.model.Resident;

/**
 * @author FFGRRNTeam
 *
 */
@Repository
@Transactional
public class ResidentDAO extends JdbcDaoSupport {

	@Autowired
	public ResidentDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}	
		
	/**
	 * 
	 * @return
	 */
	public List<Property> getAllProperty() {
		PropertyMapper rowMapper = new PropertyMapper();
		return this.getJdbcTemplate().query(PropertyMapper.PROPERTY_SQL, rowMapper);
	}
	
	public List<AssessmentType> getAllAType() {
		AssessmentMapper rowMapper = new AssessmentMapper();
		return this.getJdbcTemplate().query(AssessmentMapper.A_SQL, rowMapper);
	}
	
	public List<Referral> getAllReferral() {
		ReferralMapper rowMapper = new ReferralMapper();
		return this.getJdbcTemplate().query(ReferralMapper.REF_SQL, rowMapper);
	}
	
	public List<Resident> getAllResident() {
		ResidentMapper rowMapper = new ResidentMapper();
		return this.getJdbcTemplate().query(ResidentMapper.RESIDENT_SQL, rowMapper);
	}

	/**
	 * 
	 * @param resident
	 */
	public int saveResident(Resident resident) {

		int count = this.getJdbcTemplate().update(
				"INSERT INTO RESIDENT (RESIDENT_ID, ACTIVE, FIRST_NAME, MIDDLE, LAST_NAME, PROP_ID, VOICEMAIL_NO, TEXT_NO, EMAIL, ADDRESS, ACK_PR, ALLOW_CONTACT, WANTS_SURVEY, PHOTO_RELEASE, SERVICE_COORD, REF_TYPE) VALUES (nextval('RESIDENT_SQ'), true, '"
						+ resident.getFirstName() + "','" + resident.getMiddle() + "','" + resident.getLastName() + "',"
						+ resident.getPropertyId() + ",'" + resident.getVoiceMail() + "','" + resident.getText() + "','"
						+ resident.getEmail() + "','" + resident.getAddress() + "'," + resident.getAckRightToPrivacy()
						+ "," + resident.getAllowContact() + "," + resident.getWantSurvey() + ","
						+ resident.getPhotoRelease() + ",'" + resident.getServiceCoord() + "',"	+ resident.getRefId() + ")");

		if (count > 0) {
			if (!StringUtils.isEmpty(resident.getChild1())) {
				this.getJdbcTemplate()
						.update("INSERT INTO CHILD (CHILD_ID, FULL_NAME, PARENT_ID) VALUES (nextval('CHILD_SQ'),'"
								+ resident.getChild1() + "', currval('RESIDENT_SQ'))");
			}
			if (!StringUtils.isEmpty(resident.getChild2())) {
				this.getJdbcTemplate()
						.update("INSERT INTO CHILD (CHILD_ID, FULL_NAME, PARENT_ID) VALUES (nextval('CHILD_SQ'),'"
								+ resident.getChild2() + "', currval('RESIDENT_SQ'))");
			}
			if (!StringUtils.isEmpty(resident.getChild3())) {
				this.getJdbcTemplate()
						.update("INSERT INTO CHILD (CHILD_ID, FULL_NAME, PARENT_ID) VALUES (nextval('CHILD_SQ'),'"
								+ resident.getChild3() + "', currval('RESIDENT_SQ'))");
			}
			if (!StringUtils.isEmpty(resident.getChild4())) {
				this.getJdbcTemplate()
						.update("INSERT INTO CHILD (CHILD_ID, FULL_NAME, PARENT_ID) VALUES (nextval('CHILD_SQ'),'"
								+ resident.getChild4() + "', currval('RESIDENT_SQ'))");
			}
			if (!StringUtils.isEmpty(resident.getChild5())) {
				this.getJdbcTemplate()
						.update("INSERT INTO CHILD (CHILD_ID, FULL_NAME, PARENT_ID) VALUES (nextval('CHILD_SQ'),'"
								+ resident.getChild5() + "', currval('RESIDENT_SQ'))");
			}
			if (!StringUtils.isEmpty(resident.getChild6())) {
				this.getJdbcTemplate()
						.update("INSERT INTO CHILD (CHILD_ID, FULL_NAME, PARENT_ID) VALUES (nextval('CHILD_SQ'),'"
								+ resident.getChild6() + "', currval('RESIDENT_SQ'))");
			}
			if (!StringUtils.isEmpty(resident.getChild7())) {
				this.getJdbcTemplate()
						.update("INSERT INTO CHILD (CHILD_ID, FULL_NAME, PARENT_ID) VALUES (nextval('CHILD_SQ'),'"
								+ resident.getChild7() + "',currval('RESIDENT_SQ'))");
			}
			if (!StringUtils.isEmpty(resident.getChild8())) {
				this.getJdbcTemplate()
						.update("INSERT INTO CHILD (CHILD_ID, FULL_NAME, PARENT_ID) VALUES (nextval('CHILD_SQ'),'"
								+ resident.getChild8() + "', currval('RESIDENT_SQ'))");
			}
		}

		return count;
	}

	



}
