/**
 * 
 */
package com.ffg.rrn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ffg.rrn.dao.AppRoleDAO;
import com.ffg.rrn.dao.ServiceCoordinatorDAO;
import com.ffg.rrn.model.ServiceCoordinator;

/**
 * @author FFGRRNTeam
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    private ServiceCoordinatorDAO serviceCoordinatorDAO;
 
    @Autowired
    private AppRoleDAO appRoleDAO;
 
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    	ServiceCoordinator sc = this.serviceCoordinatorDAO.findUserAccount(userName);
 
        if (sc == null) {
            System.out.println("User not found! " + userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }
 
        System.out.println("Found User: " + sc);
 
        // [ROLE_USER, ROLE_ADMIN,..]
        List<String> roleNames = this.appRoleDAO.getRoleNames(sc.getScId());
 
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();

		GrantedAuthority authority = null;

        if (roleNames != null) {
            for (String role : roleNames) {
                // ROLE_USER, ROLE_ADMIN,..
				authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }

		authority = new SimpleGrantedAuthority("ROLE_" + sc.getPropName());
		grantList.add(authority);
 
        UserDetails userDetails = (UserDetails) new User(sc.getUserName(), //
        		sc.getEncrytedPassword(), grantList);
 
        return userDetails;
    }
 
}
