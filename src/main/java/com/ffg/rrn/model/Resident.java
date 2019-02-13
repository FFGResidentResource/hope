/**
 * 
 */
package com.ffg.rrn.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

/**
 * @author FFGRRNTeam
 * 
	RESIDENT_ID		BIGINT PRIMARY KEY NOT NULL,
	ACTIVE			BOOLEAN DEFAULT FALSE,
	FIRST_NAME		VARCHAR(20),
	MIDDLE			VARCHAR(20),
	LAST_NAME		VARCHAR(20),
	PROP_ID			INT REFERENCES PROERTY(PROP_ID),
	VOICEMAIL_NO	VARCHAR(20),
	TEXT_NO			VARCHAR(20),
	EMAIL			VARCHAR(128),
	ALLOW_CONTACT	BOOLEAN DEFAULT FALSE,
	WANTS_SURVEY	BOOLEAN DEFAULT FALSE,
	PHOTO_RELEASE	BOOLEAN DEFAULT FALSE,
	DATE_ADDED		TIMESTAMP DEFAULT NOW(),
	DATE_MODIFIED	TIMESTAMP,
	MODIFIED_BY		INT REFERENCES SERVICE_COORDINATOR(SC_ID),
	SERVICE_COORD	INT REFERENCES SERVICE_COORDINATOR(SC_ID)
**/ 

@Data
@JsonView
public class Resident {
	
	private Long residentId;	
	private Boolean active;
	@NotEmpty
	private String firstName;
	private String middle;	
	private String lastName;

	private Integer propertyId;
	private String propertyName;	
	private String voiceMail;
	private String text;
	@Email
	private String email;
	private String address;
	private Boolean ackRightToPrivacy;
	private Boolean allowContact;
	private Boolean wantSurvey;
	private Boolean photoRelease;
	private Date dateAdded;
	private Date modifiedDate;
	private String modifiedBy;
	private String serviceCoord;
	
	private String child1;
	private String child2;	
	private String child3;
	private String child4;	
	private String child5;
	private String child6;
	private String child7;
	private String child8;
	
	@JsonView
	private List<Property> propertyList;
	
	
	public Resident() {
		
	}
	
	public Resident(List<Property> propertyList, String serviceCoord) {
		this.propertyList = propertyList;	
		this.serviceCoord = serviceCoord;		
	}


	public Resident(Long residentId, Boolean active, String firstName, String middle, String lastName, String propertyName,
			String voiceMail, String text, String email, Boolean allowContact, Boolean wantSurvey, Boolean photoRelease,
			Date dateAdded, String serviceCoord) {
		super();
		this.residentId = residentId;
		this.active = active;
		this.firstName = firstName;
		this.middle = middle;
		this.lastName = lastName;
		this.propertyName = propertyName;
		this.voiceMail = voiceMail;
		this.text = text;
		this.email = email;
		this.allowContact = allowContact;
		this.wantSurvey = wantSurvey;
		this.photoRelease = photoRelease;
		this.dateAdded = dateAdded;
		this.serviceCoord = serviceCoord;
	}

}
