/**
 * 
 */
package com.ffg.rrn.model;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;
import com.ffg.rrn.utils.FieldsValueMatch;
import com.ffg.rrn.utils.PropertyCheck;

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
@FieldsValueMatch(field = "password", fieldMatch = "confirmPassword", message = "The password fields must match!")
@PropertyCheck(field = "admin", fieldCheck = "propertyId", message = "Property is Mandatory for non Admins!")
public class ServiceCoordinator {
	 
    private Integer scId;
	@NotEmpty
	@Pattern(regexp = "^[\\p{Alnum}]{1,36}$")
    private String userName;
	@NotEmpty
	@Size(max = 10)
    private String password;

	@NotEmpty
	@Size(max = 10)
	private String confirmPassword;

    private String encrytedPassword;
	private Boolean active;
    @Email
    @NotEmpty
    private String email;
    private Timestamp createdOn;
    private Timestamp lastLogin;
    
	private Integer roleId;

	private String roleName;
	private String propName;

	private Boolean admin;

	@JsonView
	private List<Property> propertyList;

	private List<String> allTakenEmails;

	private List<String> allTakenUserNames;
	
	private String assignedProperties;
	
	private Integer engagementPercentage;
	
	private Integer intakePending;
	
	private Long resIdForAudit; 
	
	private List<ResidentAudit> residentAudits;
 
	public ServiceCoordinator(Integer scId, String userName, String encrytedPassword, boolean active, String email, Timestamp createdOn, Timestamp lastLogin, String assignedProperties) {
        this.scId = scId;
        this.userName = userName;
        this.encrytedPassword = encrytedPassword;
        this.active = active;
        this.email = email;
        this.createdOn = createdOn;
		this.lastLogin = lastLogin;
		this.assignedProperties = assignedProperties;
    }
    
    public ServiceCoordinator() {
	}

	@Override
    public String toString() {
		return this.userName + " Logs In";
    }
}
