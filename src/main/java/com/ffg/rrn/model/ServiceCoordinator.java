/**
 * 
 */
package com.ffg.rrn.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @NotEmpty
    @Size(max=36)
    private String userName;
    @NotEmpty
    @Size(max=10)
    private String password;
    private String encrytedPassword;
    private String active;
    @Email
    @NotEmpty
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
