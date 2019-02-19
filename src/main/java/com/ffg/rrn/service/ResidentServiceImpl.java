/**
 * 
 */
package com.ffg.rrn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffg.rrn.dao.ResidentDAO;
import com.ffg.rrn.model.AssessmentType;
import com.ffg.rrn.model.Property;
import com.ffg.rrn.model.Referral;
import com.ffg.rrn.model.Resident;

/**
 * @author FFGRRNTeam
 *
 */
@Service
public class ResidentServiceImpl {

	@Autowired
	private ResidentDAO residentDao;
	
	public List<Property> getAllProperty(){		
		return this.residentDao.getAllProperty();
	}
	
	public int saveResident(final Resident resident) {		
		return residentDao.saveResident(resident);		
	}

	public List<Referral> getAllReferral() {		
		return this.residentDao.getAllReferral();
	}

	public List<AssessmentType> getAllAType() {
		return this.residentDao.getAllAType();
	}
	
	public List<Resident> getAllResident() {
		return this.residentDao.getAllResident();
	}
	
}
