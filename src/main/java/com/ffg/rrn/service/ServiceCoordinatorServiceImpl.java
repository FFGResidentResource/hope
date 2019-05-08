/**
 * 
 */
package com.ffg.rrn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffg.rrn.dao.ServiceCoordinatorDAO;
import com.ffg.rrn.model.Property;
import com.ffg.rrn.model.ServiceCoordinator;
import com.ffg.rrn.utils.PasswordUtils;

/**
 * @author FFGRRNTeam
 *
 */
@Service
public class ServiceCoordinatorServiceImpl {

	@Autowired
	private ServiceCoordinatorDAO serviceCoordinatorDao;
	
	public List<Property> getAllProperty() {
		return this.serviceCoordinatorDao.getAllProperty();
	}

	public List<String> getAllTakenEmails() {
		return this.serviceCoordinatorDao.getAllTakenEmails();
	}

	public List<String> getAllTakenUserNames() {
		return this.serviceCoordinatorDao.getAllTakenUserNames();
	}

	public List<ServiceCoordinator> getAllServiceCoordinators() {
		return this.serviceCoordinatorDao.getAllServiceCoordinators();
	}

	public Long saveServiceCoordinator(final ServiceCoordinator sc) {
		sc.setEncrytedPassword(PasswordUtils.encryptPassword(sc.getPassword()));
		return serviceCoordinatorDao.saveServiceCoordinator(sc);
	}

	public Long inactivateOrActivateSC(String userName, boolean active) {
		return serviceCoordinatorDao.inactivateOrActivateSC(userName, active);
	}

}
