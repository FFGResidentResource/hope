/**
 * 
 */
package com.ffg.rrn.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

/**
 * @author FFGRRNTeam
 * 
 * USER_ID           	INT NOT NULL,
 * USER_NAME         	VARCHAR(36) NOT NULL,
 * ENCRYTED_PASSWORD 	VARCHAR(128) NOT NULL,
 * ACTIVE            	INT NOT NULL,
 * EMAIL				VARCHAR (355) UNIQUE NOT NULL,
 * CREATED_ON			TIMESTAMP NOT NULL DEFAULT NOW(),
 * LAST_LOGIN			TIMESTAMP 
 *
 */
@Data
@JsonView
public class ServiceCoordinator {
	 
    private Integer scId;
    private String userName;
    private String encrytedPassword;
    private String active;
    private String email;
    private Timestamp createdOn;
    private Timestamp lastLogin;
 
    public ServiceCoordinator() {
 
    }
 
    public ServiceCoordinator(Integer scId, String userName, String encrytedPassword, String active, String email, Timestamp createdOn, Timestamp lastLogin) {
        this.scId = scId;
        this.userName = userName;
        this.encrytedPassword = encrytedPassword;
        this.active = active;
        this.email = email;
        this.createdOn = createdOn;
        this.lastLogin = lastLogin;
       
    }
    
    @Override
    public String toString() {
        return this.userName + "/" + this.encrytedPassword;
    }
}
